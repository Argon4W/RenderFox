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

public class BuddyAllocator {

	private final	BuddyPFNAllocator	pageAllocator;
	private final	long				baseAddress;
	private final	long				maxAddress;
	private final	long				pagePower;
	private final	long				pageSize;
	private			boolean				closed;

	public BuddyAllocator(
			int		maxOrder,
			long	baseAddress,
			long	pagePower
	) {
		this.pageAllocator	= new BuddyPFNAllocator(maxOrder, true);

		this.pagePower		=		pagePower;
		this.pageSize		= 1L <<	pagePower;
		this.baseAddress	= baseAddress;
		this.maxAddress		= baseAddress + (1L << maxOrder) * pageSize;
		this.closed			= false;
	}

	public long allocate(long size) {
		if (closed) {
			throw new IllegalStateException("Allocator has already been closed.");
		}

		if (size <= 0) {
			throw new IllegalArgumentException("Size (%d) cannot be less than or equal to zero.".formatted(size));
		}

		if (size > getAddressRange()) {
			throw new OutOfMemoryError("Cannot allocate address because there is no free space.");
		}

		return allocateOrder(getOrder(size));
	}

	public void free(long address) {
		if (closed) {
			throw new IllegalStateException("Allocator has already been closed.");
		}

		if (address < 0) {
			throw new IllegalArgumentException("Address (%d) cannot be negative.".formatted(address));
		}

		if (address < baseAddress) {
			throw new IllegalArgumentException("Address (%d) is not allocated by this specific allocator.".formatted(address));
		}

		if (address > maxAddress) {
			throw new IllegalArgumentException("Address (%d) is not allocated by this specific allocator.".formatted(address));
		}

		if ((address - baseAddress) % pageSize != 0) {
			throw new IllegalArgumentException("Address (%d) is not allocated by this specific allocator.".formatted(address));
		}

		pageAllocator.freePFN(getPfn(address));
	}

	public int getMetadata(long address) {
		if (closed) {
			throw new IllegalStateException("Allocator has already been closed.");
		}

		if (address < 0) {
			throw new IllegalArgumentException("Address (%d) cannot be negative.".formatted(address));
		}

		if (address < baseAddress) {
			throw new IllegalArgumentException("Address (%d) is not allocated by this specific allocator.".formatted(address));
		}

		if (address > maxAddress) {
			throw new IllegalArgumentException("Address (%d) is not allocated by this specific allocator.".formatted(address));
		}

		return pageAllocator.getMetadata(getPfn(address));
	}

	public long setMetadata(long address, int metadata) {
		if (closed) {
			throw new IllegalStateException("Allocator has already been closed.");
		}

		if (address < 0) {
			throw new IllegalArgumentException("Address (%d) cannot be negative.".formatted(address));
		}

		if (address < baseAddress) {
			throw new IllegalArgumentException("Address (%d) is not allocated by this specific allocator.".formatted(address));
		}

		if (address > maxAddress) {
			throw new IllegalArgumentException("Address (%d) is not allocated by this specific allocator.".formatted(address));
		}

		pageAllocator.setMetadata(getPfn(address), metadata);

		return address;
	}

	public void close() {
		if (closed) {
			throw new IllegalStateException("Allocator has already been closed.");
		}

		closed = true;
	}

	public long allocateOrder(int order) {
		return getRealAddress(pageAllocator.allocatePFN(order));
	}

	public int getOrder(long size) {
		return 32 - Integer.numberOfLeadingZeros(getPages(size) - 1);
	}

	public int getPages(long size) {
		return (int) ((size + pageSize - 1) >>> pagePower);
	}

	public long getRealAddress(long pfn) {
		return baseAddress + (pfn << pagePower);
	}

	public long getPfn(long address) {
		return (address - baseAddress) >>> pagePower;
	}

	public long getAddressRange() {
		return maxAddress - baseAddress;
	}

	public long getSize(long order) {
		return getPageSize() * (1L << order);
	}

	public long getBaseAddress() {
		return baseAddress;
	}

	public long getMaxAddress() {
		return maxAddress;
	}

	public boolean isClosed() {
		return closed;
	}

	public long getPageSize() {
		return pageSize;
	}

	public long getPagePower() {
		return pagePower;
	}

	public int getMaxOrder() {
		return pageAllocator.getMaxOrder();
	}

	public boolean isEmpty() {
		return pageAllocator.isHeapEmpty();
	}

	public boolean isFull() {
		return pageAllocator.isHeapFull();
	}
}
