/*
 * Copyright (C) 2026  Argon4W
 *
 * This file is part of RenderFox.
 *
 * RenderFox is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RenderFox is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with RenderFox.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.argon4w.renderfox.data.memory;

public class BuddyPFNAllocator {

	public static final int MAX_ORDER = 20;
	public static final int MAX_COUNT = 1 << MAX_ORDER;

	private final	int[][]	orderFreeListsDense;
	private final	int[][]	orderFreeListsSparse;
	private final	int[]	orderFreeListsSizes;
	private final	int[]	orderFreeListsHeads;
	private final	int[]	allocatedPFNs;
	private final	int		maxOrder;
	private			int		orderBitmap;
	private			int		allocationCount;

	public BuddyPFNAllocator(int maxOrder, boolean initialize) {
		orderCheck(maxOrder, "maxOrder");

		this.maxOrder				= maxOrder;
		this.orderFreeListsDense	= new int[		this.maxOrder + 1][];
		this.orderFreeListsSparse	= new int[		this.maxOrder + 1][];
		this.orderFreeListsSizes	= new int[		this.maxOrder + 1];
		this.orderFreeListsHeads	= new int[		this.maxOrder + 1];
		this.allocatedPFNs			= new int[1 <<	this.maxOrder];
		this.orderBitmap			= 0;
		this.allocationCount		= 0;

		for (var i = 0; i <= this.maxOrder; i ++) {
			var size = 1 << (this.maxOrder - i);

			this.orderFreeListsHeads	[i] = 0;
			this.orderFreeListsSizes	[i] = size;
			this.orderFreeListsDense	[i] = new int[size];
			this.orderFreeListsSparse	[i] = new int[size];

			for (var j = 0; j < size; j ++) {
				this.orderFreeListsDense	[i][j] = -1;
				this.orderFreeListsSparse	[i][j] = -1;
			}
		}

		for (var i = 0; i < allocatedPFNs.length; i ++) {
			allocatedPFNs[i] = -1;
		}

		if (initialize) {
			pushPFN(0, this.maxOrder);
		}
	}

	public BuddyPFNAllocator(int objects) {
		this(findNextOrder(objects), false);

		var current = 0L;

		while (objects > 0) {
			var order = findMaxOrder(objects);
			var count = 1 << order;

			pushPFN(current, order);

			current = current + count;
			objects = objects - count;
		}
	}

	public long allocatePFN(int order) {
		if (order < 0) {
			throw new IllegalArgumentException("Order (%d) cannot be negative.".formatted(order));
		}

		if (order > maxOrder) {
			throw new IllegalArgumentException("Order (%d) exceeds the limit of the maximum value of order allowed (%d).".formatted(order, maxOrder));
		}

		if (!isEmpty(order)) {
			return popPFN(order);
		}

		var freeOrder = firstFree(order + 1);

		if (freeOrder > maxOrder) {
			throw new OutOfMemoryError("Cannot allocate page frame number because there is no free space.");
		}

		var pfn = popPFN(freeOrder);

		allocatedPFNs[(int) pfn] = order & 0xFFFF;
		allocationCount ++;

		while (freeOrder > order) {
			pushPFN(findBuddyPFN(pfn, -- freeOrder), freeOrder);
		}

		return pfn;
	}

	public void freePFN(long pfn) {
		var metadata = allocatedPFNs[(int) pfn];

		if (metadata == -1) {
			throw new IllegalArgumentException("The page frame number (%d) is not allocated from this specific allocator.".formatted(pfn));
		}

		var order = metadata & 0xFFFF;

		if (isFull(order)) {
			throw new IllegalStateException("Page frame number is already freed.");
		}

		if (pfn < 0) {
			throw new IllegalArgumentException("The page frame number (%d) cannot be negative.".formatted(pfn));
		}

		if (pfn >= (1L << maxOrder)) {
			throw new IllegalArgumentException("Page frame number (%d) exceeds the limit of the maximum count of pages (%d).".formatted(pfn, 1L << maxOrder));
		}

		if (order > maxOrder) {
			throw new IllegalStateException("Order (%d) exceeds the limit of the maximum value of order allowed (%d).".formatted(order, maxOrder));
		}

		if (hasPFN(pfn, order)) {
			throw new IllegalStateException("Page frame number (%d) is already freed.".formatted(pfn));
		}

		allocatedPFNs[(int) pfn] = -1;
		allocationCount --;

		if (!hasPFN(findBuddyPFN(pfn, order), order)) {
			pushPFN(pfn, order);
			return;
		}

		var mergePfn	= pfn;
		var mergeOrder	= order;
		var mergeBuddy	= findBuddyPFN(mergePfn, mergeOrder);

		while (mergeOrder < maxOrder && hasPFN(mergeBuddy, mergeOrder)) {
			removePFN(mergeBuddy, mergeOrder);

			mergePfn	= findMergePFN(mergePfn, mergeOrder ++);
			mergeBuddy	= findBuddyPFN(mergePfn, mergeOrder);
		}

		pushPFN(mergePfn, mergeOrder);
	}

	public void setMetadata(long pfn, int metadata) {
		if ((metadata >> 16) > 0) {
			throw new IllegalArgumentException("The bit count of the metadata (%d) exceeds the range of maximum bit count allowed in metadata.".formatted(metadata));
		}

		if (pfn >= (1L << maxOrder)) {
			throw new IllegalArgumentException("Page frame number (%d) exceeds the limit of the maximum count of pages (%d).".formatted(pfn, 1L << maxOrder));
		}

		if (pfn < 0) {
			throw new IllegalArgumentException("The page frame number (%d) cannot be negative.".formatted(pfn));
		}

		allocatedPFNs[(int) pfn] &=					0xFFFF;
		allocatedPFNs[(int) pfn] |= ((metadata &	0xFFFF) << 16);
	}

	public int getMetadata(long pfn) {
		var metadata = allocatedPFNs[(int) pfn];

		if (metadata == -1) {
			throw new IllegalArgumentException("The page frame number (%d) is not allocated from this specific allocator.".formatted(pfn));
		}

		if (pfn >= (1L << maxOrder)) {
			throw new IllegalArgumentException("Page frame number (%d) exceeds the limit of the maximum count of pages (%d).".formatted(pfn, 1L << maxOrder));
		}

		if (pfn < 0) {
			throw new IllegalArgumentException("The page frame number (%d) cannot be negative.".formatted(pfn));
		}

		return (metadata >> 16) & 0xFFFF;
	}

	protected void push(int sparseIndex, int order) {
		if (order < 0) {
			throw new IllegalArgumentException("Order (%d) cannot be negative.".formatted(order));
		}

		if (sparseIndex < 0) {
			throw new IllegalArgumentException("SparseIndex (%d) cannot be negative.".formatted(sparseIndex));
		}

		if (order > maxOrder) {
			throw new IllegalArgumentException("Order (%d) exceeds the limit of the maximum value of order allowed (%d).".formatted(order, maxOrder));
		}

		if (sparseIndex >= orderFreeListsSizes[order]) {
			throw new IllegalArgumentException("SparseIndex (%d) exceeds the bounds of orderFreeListsSizes[order (%d)] (%d).".formatted(sparseIndex, order, orderFreeListsSizes[order]));
		}

		if (isFull(order)) {
			throw new IllegalStateException("The free sparse list of this order (%d) is already full.".formatted(order));
		}

		if (has(sparseIndex, order)) {
			throw new IllegalStateException("The sparseIndex (%d) is already in the free sparse list of this order (%d).".formatted(sparseIndex, order));
		}

		var denseIndex = orderFreeListsHeads[order] ++;

		orderFreeListsDense	[order][denseIndex]		=	sparseIndex;
		orderFreeListsSparse[order][sparseIndex]	=	denseIndex;
		orderBitmap									|=	1 << order;
	}

	protected int pop(int order) {
		if (order < 0) {
			throw new IllegalArgumentException("Order (%d) cannot be negative.".formatted(order));
		}

		if (order > maxOrder) {
			throw new IllegalArgumentException("Order (%d) exceeds the limit of the maximum value of order allowed (%d).".formatted(order, maxOrder));
		}

		if (isEmpty(order)) {
			throw new IllegalStateException("The free sparse list of this order (%d) is already empty.".formatted(order));
		}

		var lastDenseIndex	= --	orderFreeListsHeads[order];
		var lastSparseIndex	=		orderFreeListsDense[order][lastDenseIndex];

		orderFreeListsSparse[order][lastSparseIndex] = -1;

		if (orderFreeListsHeads[order] <= 0) {
			orderBitmap &= ~(1 << order);
		}

		return lastSparseIndex;
	}

	protected void remove(int removeSparseIndex, int order) {
		if (order < 0) {
			throw new IllegalArgumentException("Order (%d) cannot be negative.".formatted(order));
		}

		if (order > maxOrder) {
			throw new IllegalArgumentException("Order (%d) exceeds the limit of the maximum value of order allowed (%d).".formatted(order, maxOrder));
		}

		if (isEmpty(order)) {
			throw new IllegalStateException("The free sparse list of this order (%d) is already empty.".formatted(order));
		}

		if (!has(removeSparseIndex, order)) {
			throw new IllegalStateException("The removeSparseIndex (%d) is already removed from free sparse list of this order (%d).".formatted(removeSparseIndex, order));
		}

		var lastDenseIndex		= orderFreeListsHeads	[order] -- - 1;
		var removeDenseIndex	= orderFreeListsSparse	[order][removeSparseIndex];
		var lastSparseIndex		= orderFreeListsDense	[order][lastDenseIndex];

		orderFreeListsDense[order][removeDenseIndex]	= lastSparseIndex;
		orderFreeListsDense[order][lastDenseIndex]		= -1;

		orderFreeListsSparse[order][lastSparseIndex]	= removeDenseIndex;
		orderFreeListsSparse[order][removeSparseIndex]	= -1;

		if (orderFreeListsHeads[order] <= 0) {
			orderBitmap &= ~(1 << order);
		}
	}

	protected boolean has(int sparseIndex, int order) {
		if (order < 0) {
			throw new IllegalArgumentException("Order (%d) cannot be negative.".formatted(order));
		}

		if (sparseIndex < 0) {
			throw new IllegalArgumentException("SparseIndex (%d) cannot be negative.".formatted(sparseIndex));
		}

		if (order > maxOrder) {
			throw new IllegalArgumentException("Order (%d) exceeds the limit of the maximum value of order allowed (%d).".formatted(order, maxOrder));
		}

		if (sparseIndex >= orderFreeListsSizes[order]) {
			throw new IllegalArgumentException("SparseIndex (%d) exceeds the bounds of orderFreeListsSizes[order (%d)] (%d).".formatted(sparseIndex, order, orderFreeListsSizes[order]));
		}

		return orderFreeListsSparse[order][sparseIndex] != -1;
	}

	public boolean isHeapFull() {
		return orderBitmap == 0;
	}

	public boolean isHeapEmpty() {
		return allocationCount == 0;
	}

	public boolean isFull(int order) {
		return orderFreeListsHeads[order] >= orderFreeListsSizes[order];
	}

	public boolean isEmpty(int order) {
		return orderFreeListsHeads[order] <= 0;
	}

	private void pushPFN(long pfn, int order) {
		push(getPFNIndex(pfn, order), order);
	}

	private long popPFN(int order) {
		return findIndexPFN(pop(order), order);
	}

	private void removePFN(long pfn, int order) {
		remove(getPFNIndex(pfn, order), order);
	}

	private boolean hasPFN(long pfn, int order) {
		return has(getPFNIndex(pfn, order), order);
	}

	private int firstFree(int order) {
		return order > maxOrder ? 32 : Integer.numberOfTrailingZeros(orderBitmap & (-1 << order));
	}

	public int biggestFreeOrder() {
		return 31 - Integer.numberOfLeadingZeros(orderBitmap & ~(-1 << maxOrder + 1));
	}

	public int getMaxOrder() {
		return maxOrder;
	}

	private static int getPFNIndex(long pfn, int order) {
		return (int) (pfn >>> order);
	}

	private static long findIndexPFN(int index, int order) {
		return ((long) index) << order;
	}

	private static long findBuddyPFN(long pfn, int order) {
		return pfn ^ (1L << order);
	}

	private static long findMergePFN(long pfn, int order) {
		return pfn & ~(1L << order);
	}

	private static int findMaxOrder(int size) {
		return 31 - Integer.numberOfLeadingZeros(size);
	}

	private static int findNextOrder(int size) {
		return 32 - Integer.numberOfLeadingZeros(size - 1);
	}

	public static void orderCheck(int value, String name) {
		if (name == null) {
			throw new IllegalArgumentException("Name cannot be null.");
		}

		if (value < 0) {
			throw new IllegalArgumentException("%s (%d) cannot be negative.".formatted(name, value));
		}

		if (value > MAX_ORDER) {
			throw new IllegalArgumentException("%s (%d) exceeds the limit of the maximum order allowed (%d).".formatted(name, value, MAX_ORDER));
		}
	}
}
