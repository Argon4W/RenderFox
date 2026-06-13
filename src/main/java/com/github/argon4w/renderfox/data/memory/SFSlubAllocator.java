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

import com.github.argon4w.renderfox.data.size.ISize;
import com.github.argon4w.renderfox.util.MathUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Arrays;

import static com.github.argon4w.renderfox.data.memory.BuddyPFNAllocator.MAX_COUNT;
import static com.github.argon4w.renderfox.data.memory.BuddyPFNAllocator.orderCheck;

public class SFSlubAllocator {

	private static final int	DEFAULT_MIN_OBJECT_FACTOR	= 0;
	private static final int	DEFAULT_MAX_ALIGN_ORDER		= 20;
	private static final int	DEFAULT_MIN_PAGE_ORDER		= 4;
	private static final int	DEFAULT_MAX_PAGE_ORDER		= 12;
	private static final int	DEFAULT_MAX_PAGE_EMPTY		= 1;
	private static final int	DEFAULT_MAX_CACHED_ORDER	= 5;

	private final	Object2ObjectMap<ISize, SlabCache>	cachesObjsMap;
	private final	BuddyAllocator						buddyPageAllocator;
	private final	int									minGrowthFactor;
	private final	int									maxAlignOrder;
	private final	int									minBuddyPageOrder;
	private final	int									maxBuddyPageOrder;
	private final	int									maxSlabPageEmpty;
	private final	int									maxCachedAlignOrder;

	public SFSlubAllocator(
			BuddyAllocator	buddyPageAllocator,
			int				minGrowthFactor,
			int				maxAlignOrder,
			int				minBuddyPageOrder,
			int				maxBuddyPageOrder,
			int				maxSlabPageEmpty,
			int				maxCachedAlignOrder
	) {
		orderCheck(maxAlignOrder,		"MaxAlignOrder");
		orderCheck(minBuddyPageOrder,	"MinBuddyPageOrder");
		orderCheck(maxBuddyPageOrder,	"MaxBuddyPageOrder");
		orderCheck(maxCachedAlignOrder,	"MaxCachedAlignOrder");

		this.cachesObjsMap			= new Object2ObjectOpenHashMap<>();
		this.buddyPageAllocator		= buddyPageAllocator;
		this.minGrowthFactor		= minGrowthFactor;
		this.maxAlignOrder			= maxAlignOrder;
		this.minBuddyPageOrder		= minBuddyPageOrder;
		this.maxBuddyPageOrder		= maxBuddyPageOrder;
		this.maxSlabPageEmpty		= maxSlabPageEmpty;
		this.maxCachedAlignOrder	= maxCachedAlignOrder;
	}

	public SFSlubAllocator(BuddyAllocator buddyPageAllocator) {
		this(
				buddyPageAllocator,
				DEFAULT_MIN_OBJECT_FACTOR,
				DEFAULT_MAX_ALIGN_ORDER,
				DEFAULT_MIN_PAGE_ORDER,
				DEFAULT_MAX_PAGE_ORDER,
				DEFAULT_MAX_PAGE_EMPTY,
				DEFAULT_MAX_CACHED_ORDER
		);
	}

	public long allocate(ISize type, int count) {
		if (count <= 0) {
			throw new IllegalArgumentException("Count (%d) cannot be less than or equal to zero.".formatted(count));
		}

		if (type == null) {
			throw new IllegalArgumentException("Type cannot be null.");
		}

		if (type.getSize() <= 0) {
			throw new IllegalArgumentException("Size (%d) cannot be less than or equal to zero.".formatted(type.getSize()));
		}

		if (type.getAlign() <= 0) {
			throw new IllegalArgumentException("Align (%d) cannot be less than or equal to zero.".formatted(type.getAlign()));
		}

		var cache = cachesObjsMap.get(type);

		if (cache == null) {
			cache = new SlabCache(type);
		}

		return cache.allocate(count);
	}

	public void free(ISize type, long address) {
		if (address < buddyPageAllocator.getBaseAddress()) {
			throw new IllegalArgumentException("The address (%d) is not allocated by this specific slab allocator.".formatted(address));
		}

		if (address > buddyPageAllocator.getMaxAddress()) {
			throw new IllegalArgumentException("The address (%d) is not allocated by this specific slab allocator.".formatted(address));
		}

		if (address < 0) {
			throw new IllegalArgumentException("Address (%d) cannot be negative.".formatted(address));
		}

		if (type == null) {
			throw new IllegalArgumentException("Type cannot be null.");
		}

		var cache = cachesObjsMap.get(type);

		if (cache == null) {
			throw new IllegalArgumentException("The address (%d) is not allocated by this specific allocator.".formatted(address));
		}

		cache.free(address);
	}

	public class SlabCache {

		private final	int				localMaxBuddyPageOrder;
		private final	int				objectSize;
		private final	int				objectAlign;
		private final	int	[]			alignOrderFreelistHeads;
		private final	int	[]			alignOrderFreelistSizes;
		private final	SlabPage[][]	alignOrderFreelistLists;
		private			int				alignOrderFreelistBitmap;
		private			int				alignOrderGrowth;
		private			int				registrySize;
		private			int				registrySparseSize;
		private			int				registryDenseSize;
		private			int	[]			registryIdSparse;
		private			int	[]			registryIdDense;
		private			SlabPage[]		registryObjDense;

		private SlabCache(ISize objectType) {
			if (objectType == null) {
				throw new IllegalArgumentException("Object type cannot be null.");
			}

			if (objectType.getSize() <= 0) {
				throw new IllegalArgumentException("Size (%d) cannot be less than or equal to zero.".formatted(objectType.getSize()));
			}

			if (objectType.getAlign() <= 0) {
				throw new IllegalArgumentException("Align (%d) cannot be less than or equal to zero.".formatted(objectType.getAlign()));
			}

			var objectSize		= objectType.getSize	();
			var objectAlign		= objectType.getAlign	();

			this.objectSize		= objectSize;
			this.objectAlign	= objectSize % objectAlign == 0 ? objectSize : objectAlign;

			var limit = 				this.objectAlign * MAX_COUNT;
			var order = SFSlubAllocator.this.buddyPageAllocator.getOrder(limit);
			var count = SFSlubAllocator.this.buddyPageAllocator.getSize	(order) / this.objectAlign;

			if (count > MAX_COUNT) {
				order --;
			}

			this.localMaxBuddyPageOrder = Math.min(order, maxBuddyPageOrder);

			this.alignOrderFreelistLists	= new SlabPage	[maxAlignOrder + 1][];
			this.alignOrderFreelistSizes	= new int		[maxAlignOrder + 1];
			this.alignOrderFreelistHeads	= new int		[maxAlignOrder + 1];
			this.alignOrderFreelistBitmap	= 0;

			this.registrySize		= 8;
			this.registrySparseSize	= 0;
			this.registryDenseSize	= 0;
			this.registryIdSparse	= new int		[this.registrySize];
			this.registryIdDense	= new int		[this.registrySize];
			this.registryObjDense	= new SlabPage	[this.registrySize];

			for (var i = 0; i < this.registrySize; i ++) {
				this.registryIdSparse	[i] = -1;
				this.registryIdDense	[i] = -1;
				this.registryObjDense	[i] = null;
			}

			for (var i = 0; i <= maxAlignOrder; i ++) {
				this.alignOrderFreelistLists[i] = new SlabPage[4];
				this.alignOrderFreelistSizes[i] = 4;
				this.alignOrderFreelistHeads[i] = 0;
			}

			cachesObjsMap.put(objectType, this);
		}

		public long allocate(int count) {
			var alignOrder = getAlignOrder(count);

			if (alignOrder > maxAlignOrder) {
				throw new OutOfMemoryError("Cannot allocate address because there is no free space.");
			}

			if (alignOrder < 0) {
				throw new IllegalStateException("AlignOrder (%d) cannot be negative.".formatted(alignOrder));
			}

			var slabPage = findSlabPage(alignOrder);

			if (slabPage == null) {
				slabPage = newSlabPage(alignOrder);
			}

			var address	= slabPage.allocate(alignOrder);

			if (!slabPage.isPageFull()) {
				pushSlabPage(slabPage);
			}

			return address;
		}

		public void free(long address) {
			var slabPageId = buddyPageAllocator.getMetadata(address);

			if (slabPageId >= registrySparseSize) {
				throw new IllegalArgumentException("The slabPageId (%d) of the address to be freed exceeds the bounds of registrySparseSize (%d).".formatted(slabPageId, registrySparseSize));
			}

			if (slabPageId < 0) {
				throw new IllegalArgumentException("SlabPageId (%d) cannot be negative.".formatted(slabPageId));
			}

			var slabPage = registryObjDense[registryIdSparse[slabPageId]];

			if (slabPage == null) {
				throw new IllegalArgumentException("Address (%d) is not allocated by this specific slab cache.".formatted(address));
			}

			if (slabPage.isPageEmpty()) {
				throw new IllegalArgumentException("Address (%d) is not allocated by this specific slab cache.".formatted(address));
			}

			if (!slabPage.isInvalid()) {
				removeSlabPage(slabPage);
			}

			slabPage.free(address);

			if (isSlabPageSaturated(slabPage)) {
				slabPage.close();
				return;
			}

			pushSlabPage(slabPage);
		}

		private SlabPage findSlabPage(int alignOrder) {
			var free = alignOrder;

			if (alignOrder < 0) {
				throw new IllegalArgumentException("AlignOrder (%d) cannot be negative.".formatted(alignOrder));
			}

			if (alignOrder > maxAlignOrder) {
				throw new IllegalArgumentException("AlignOrder (%d) exceeds the bounds of maxAlignOrder (%d).".formatted(alignOrder, maxAlignOrder));
			}

			if (isAlignOrderEmpty(alignOrder)) {
				free = firstFreeAlignOrder(alignOrder + 1);
			}

			if (free > maxAlignOrder) {
				return null;
			}

			if (alignOrderGrowth > 0) {
				alignOrderGrowth--;
			}

			return popSlabPage(free);
		}

		private void pushSlabPage(SlabPage slabPage) {
			if (slabPage == null) {
				throw new IllegalArgumentException("Page cannot be null.");
			}

			var alignOrder = slabPage.biggestFreeOrder();

			if (alignOrder < 0) {
				throw new IllegalArgumentException("AlignOrder (%d) cannot be negative.".formatted(alignOrder));
			}

			if (alignOrder > maxAlignOrder)  {
				throw new IllegalArgumentException("AlignOrder (%d) exceeds the bounds of maxAlignOrder (%d).".formatted(alignOrder, maxAlignOrder));
			}

			if (isAlignOrderFull(alignOrder)) {
				var newList = Arrays.copyOf(
						alignOrderFreelistLists[alignOrder],
						alignOrderFreelistSizes[alignOrder] * 2
				);

				alignOrderFreelistLists[alignOrder] = newList;
				alignOrderFreelistSizes[alignOrder] = newList.length;
			}

			slabPage.setFreelistIndex(alignOrderFreelistHeads[alignOrder]);
			slabPage.setFreelistOrder(alignOrder);

			alignOrderFreelistLists[alignOrder][alignOrderFreelistHeads[alignOrder] ++]	= slabPage;
			alignOrderFreelistBitmap |=	1 << alignOrder;
		}

		private SlabPage popSlabPage(int alignOrder) {
			if (alignOrder < 0) {
				throw new IllegalArgumentException("AlignOrder (%d) cannot be negative.".formatted(alignOrder));
			}

			if (alignOrder > maxAlignOrder) {
				throw new IllegalArgumentException("AlignOrder (%d) exceeds the bounds of maxAlignOrder (%d).".formatted(alignOrder, maxAlignOrder));
			}

			var slabPage =	alignOrderFreelistLists[alignOrder][--	alignOrderFreelistHeads[alignOrder]];
							alignOrderFreelistLists[alignOrder][	alignOrderFreelistHeads[alignOrder]] = null;

			if (slabPage == null) {
				throw new IllegalStateException("SlabPage cannot be null.");
			}

			slabPage.setFreelistIndex(-1);
			slabPage.setFreelistOrder(-1);

			if (alignOrderFreelistHeads[alignOrder] <= 0) {
				alignOrderFreelistBitmap &= ~(1 << alignOrder);
			}

			return slabPage;
		}

		private void removeSlabPage(SlabPage slabPage) {
			if (slabPage == null) {
				throw new IllegalArgumentException("Page cannot be null.");
			}

			var listOrder = slabPage.getFreelistOrder();
			var listIndex = slabPage.getFreelistIndex();

			if (listOrder < 0) {
				throw new IllegalArgumentException("ListOrder (%d) cannot be negative.".formatted(listOrder));
			}

			if (listIndex < 0) {
				throw new IllegalArgumentException("ListIndex (%d) cannot be negative.".formatted(listIndex));
			}

			if (listOrder > maxAlignOrder) {
				throw new IllegalArgumentException("ListOrder (%d) exceeds the bounds of maxAlignOrder (%d).".formatted(listOrder, maxAlignOrder));
			}

			if (listIndex >= alignOrderFreelistSizes[listOrder]) {
				throw new IllegalArgumentException("ListIndex (%d) exceeds the bounds of alignOrderFreelistSizes[listOrder (%d)] (%d).".formatted(listIndex, listOrder, alignOrderFreelistSizes[listOrder]));
			}

			if (isAlignOrderEmpty(listOrder)) {
				throw new IllegalArgumentException("No slabPage present in the freelist order (%d).".formatted(listOrder));
			}

			var tailSlabPage = alignOrderFreelistLists[listOrder][-- alignOrderFreelistHeads[listOrder]];

			tailSlabPage	.setFreelistIndex(listIndex);
			slabPage		.setFreelistIndex(-1);
			slabPage		.setFreelistOrder(-1);

			alignOrderFreelistLists[listOrder][listIndex]							= tailSlabPage;
			alignOrderFreelistLists[listOrder][alignOrderFreelistHeads[listOrder]]	= null;
		}

		private SlabPage newSlabPage(int alignOrder) {
			if (alignOrder < 0) {
				throw new IllegalArgumentException("AlignOrder (%d) cannot be negative.".formatted(alignOrder));
			}

			if (alignOrder > maxAlignOrder) {
				throw new IllegalArgumentException("AlignOrder (%d) exceeds the bounds of maxAlignOrder (%d).".formatted(alignOrder, maxAlignOrder));
			}

			if (getBuddyPageOrder(alignOrder) > localMaxBuddyPageOrder) {
				throw new OutOfMemoryError("Cannot allocate address because it exceeds the limit of localMaxBuddyPageOrder (%d).".formatted(localMaxBuddyPageOrder));
			}

			alignOrderGrowth += 2;

					if (alignOrderGrowth <= 4)	{ alignOrder += minGrowthFactor;							}
			else	if (alignOrderGrowth <= 8)	{ alignOrder += minGrowthFactor + 1;						}
			else	if (alignOrderGrowth <= 20)	{ alignOrder += minGrowthFactor + alignOrderGrowth / 2 - 1;	}
			else								{ alignOrder += minGrowthFactor + 10;						}

			if (alignOrder > maxAlignOrder) {
				alignOrder = maxAlignOrder;
			}

			var buddyPageOrder = getBuddyPageOrder(alignOrder);

			if (buddyPageOrder > localMaxBuddyPageOrder) {
				buddyPageOrder = localMaxBuddyPageOrder;
			}

			if (buddyPageOrder < minBuddyPageOrder) {
				buddyPageOrder = minBuddyPageOrder;
			}

			return new SlabPage(buddyPageOrder);
		}

		private int registerSlabPage(SlabPage slabPage) {
			if (slabPage == null) {
				throw new IllegalArgumentException("Page cannot be null.");
			}

			if (isRegistryFull()) {
				registrySize		*=	2;
				registryIdSparse	=	Arrays.copyOf(registryIdSparse,	registrySize);
				registryIdDense		=	Arrays.copyOf(registryIdDense,	registrySize);
				registryObjDense	=	Arrays.copyOf(registryObjDense,	registrySize);
			}

			if (canReuseSlabPageId()) {
				registryObjDense[registryDenseSize] = slabPage;
			} else {
				registryObjDense[registryDenseSize] = slabPage;
				registryIdSparse[registryDenseSize] = registryDenseSize;
				registryIdDense	[registryDenseSize] = registryDenseSize;

				registrySparseSize ++;
			}

			return registryIdDense[registryDenseSize ++];
		}

		private boolean unregisterSlabPage(int slabPageId) {
			if (slabPageId < 0) {
				throw new IllegalArgumentException("SlabPageId (%d) cannot be negative.".formatted(slabPageId));
			}

			if (slabPageId >= registrySparseSize) {
				throw new IllegalArgumentException("SlabPageId (%d) to be removed exceeds the bounds of registrySparseSize (%d).".formatted(slabPageId, registrySparseSize));
			}

			var tailDenseId		= registryDenseSize - 1;
			var tailSparseId	= registryIdDense	[tailDenseId];
			var tailValue		= registryObjDense	[tailDenseId];
			var swapDenseId		= registryIdSparse	[slabPageId];
			var swapExists		= registryObjDense	[swapDenseId] != null;

			if (tailDenseId == swapDenseId) {
				registryObjDense[tailDenseId] = null;
			} else {
				registryObjDense[swapDenseId]	= tailValue;
				registryIdDense	[swapDenseId]	= tailSparseId;
				registryIdSparse[tailSparseId]	= swapDenseId;

				registryObjDense[tailDenseId]	= null;
				registryIdDense	[tailDenseId]	= slabPageId;
				registryIdSparse[slabPageId]	= tailDenseId;
			}

			registryDenseSize --;

			return swapExists;
		}

		private boolean isSlabPageSaturated(SlabPage slabPage) {
			return slabPage.isPageEmpty() && (slabPage.getMaxOrder() > maxCachedAlignOrder || alignOrderFreelistHeads[slabPage.getMaxOrder()] > maxSlabPageEmpty);
		}

		private int firstFreeAlignOrder(int alignOrder) {
			return Integer.numberOfTrailingZeros(alignOrderFreelistBitmap & (-1 << (alignOrder & 0x1F)));
		}

		private int getBuddyPageOrder(int alignOrder) {
			return buddyPageAllocator.getOrder(objectAlign * (1 + (1L << alignOrder)));
		}

		private int getAlignCount(int objectCount) {
			return (int) MathUtils.ceilDiv((long) objectCount * (long) objectSize, objectAlign);
		}

		private int getAlignOrder(int objectCount) {
			return 32 - Integer.numberOfLeadingZeros(getAlignCount(objectCount) - 1);
		}

		private boolean isAlignOrderFull(int order) {
			return alignOrderFreelistHeads[order] >= alignOrderFreelistSizes[order];
		}

		private boolean isAlignOrderEmpty(int order) {
			return (alignOrderFreelistBitmap & (1 << order)) == 0;
		}

		private boolean isRegistryFull() {
			return registryDenseSize >= registrySize;
		}

		private boolean canReuseSlabPageId() {
			return registryDenseSize < registrySparseSize;
		}

		public class SlabPage {

			private final	BuddyPFNAllocator	alignAllocator;
			private final	long				buddyPageAddress;
			private final	long				buddyPageSize;
			private final	long				baseAddress;
			private final	long				maxAddress;
			private final	long				maxObjects;
			private final	int					slabPageId;
			private			int					freelistOrder;
			private			int					freelistIndex;
			private			boolean				closed;

			private SlabPage(int buddyPageOrder) {
				if (buddyPageOrder < 0) {
					throw new IllegalArgumentException("BuddyPageOrder (%d) cannot be negative.".formatted(buddyPageOrder));
				}

				if (buddyPageOrder > localMaxBuddyPageOrder) {
					throw new IllegalArgumentException("BuddyPageOrder (%d) exceeds the limit of localMaxBuddyPageOrder (%d).".formatted(buddyPageOrder, localMaxBuddyPageOrder));
				}

				this.buddyPageAddress	= buddyPageAllocator.allocateOrder	(buddyPageOrder);
				this.buddyPageSize		= buddyPageAllocator.getSize		(buddyPageOrder);
				this.freelistOrder		= -1;
				this.freelistIndex		= -1;
				this.closed				= false;

				var offset	= 				this.buddyPageAddress				% objectAlign;
				var padding	= (	SlabCache.	this.objectAlign 	- offset	)	% objectAlign;
				var objects	= ( 			this.buddyPageSize	- padding	)	/ objectAlign;

				this.baseAddress	= this.buddyPageAddress +	padding;
				this.maxAddress		= this.baseAddress		+	objects * objectAlign;
				this.maxObjects		=							objects;

				this.alignAllocator = new BuddyPFNAllocator((int) objects);

				this.slabPageId = registerSlabPage(this);
			}

			public long allocate(int alignOrder) {
				if (closed) {
					throw new IllegalStateException("This slab page has already been closed.");
				}

				if (alignOrder < 0) {
					throw new IllegalArgumentException("AlignOrder (%d) cannot be negative.".formatted(alignOrder));
				}

				if (alignOrder > getMaxOrder()) {
					throw new IllegalArgumentException("Cannot allocate address because there is no free space.");
				}

				return buddyPageAllocator.setMetadata(getRealAddress(alignAllocator.allocatePFN(alignOrder)), slabPageId);
			}

			public void free(long address) {
				if (closed) {
					throw new IllegalStateException("This slab page has already been closed.");
				}

				if (address < 0) {
					throw new IllegalArgumentException("Address (%d) cannot be negative.".formatted(address));
				}

				if (address < baseAddress) {
					throw new IllegalArgumentException("Address (%d) is not allocated by this specific slab page.".formatted(address));
				}

				if (address > maxAddress) {
					throw new IllegalArgumentException("Address (%d) is not allocated by this specific slab page.".formatted(address));
				}

				if ((address - baseAddress) % objectAlign != 0) {
					throw new IllegalArgumentException("Address (%d) is not allocated by this specific slab page.".formatted(address));
				}

				alignAllocator.freePFN(getPfn(address));
			}

			public void close() {
				if (closed) {
					throw new IllegalStateException("This slab page has already been closed.");
				}

				if (!isPageEmpty()) {
					throw new IllegalStateException("This slab page is not empty.");
				}

				if (buddyPageAllocator.isClosed()) {
					throw new IllegalStateException("This slab page has already been closed.");
				}

				if (!unregisterSlabPage(slabPageId)) {
					throw new IllegalStateException("This slab page is not allocated by this specific slab cache.");
				}

				buddyPageAllocator.free(buddyPageAddress);

				closed = true;
			}

			public int biggestFreeOrder() {
				return Math.min(alignAllocator.biggestFreeOrder(), maxAlignOrder);
			}

			private void setFreelistIndex(int freelistIndex) {
				this.freelistIndex = freelistIndex;
			}

			private void setFreelistOrder(int freelistOrder) {
				this.freelistOrder = freelistOrder;
			}

			public long getRealAddress(long pfn) {
				return baseAddress + pfn * objectAlign;
			}

			public long getPfn(long address) {
				return (address - baseAddress) / objectAlign;
			}

			public int getMaxOrder() {
				return alignAllocator.getMaxOrder();
			}

			public boolean isPageEmpty() {
				return alignAllocator.isHeapEmpty();
			}

			public boolean isPageFull() {
				return alignAllocator.isHeapFull();
			}

			public long getBuddyPageAddress() {
				return buddyPageAddress;
			}

			public long getMaxObjects() {
				return maxObjects;
			}

			public long getBuddyPageSize() {
				return buddyPageSize;
			}

			public int getSlabPageId() {
				return slabPageId;
			}

			public int getFreelistOrder() {
				return freelistOrder;
			}

			public int getFreelistIndex() {
				return freelistIndex;
			}

			public boolean isInvalid() {
				return closed || freelistOrder == -1 || freelistIndex == -1;
			}
		}
	}
}
