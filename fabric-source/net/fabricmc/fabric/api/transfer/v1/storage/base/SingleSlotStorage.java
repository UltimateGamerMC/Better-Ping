package net.fabricmc.fabric.api.transfer.v1.storage.base;

import java.util.Iterator;

import net.fabricmc.fabric.api.transfer.v1.storage.SlottedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.impl.transfer.TransferApiImpl;

/**
 * A storage that is also its only storage view.
 * It can be used in APIs for storages that are wrappers around a single "slot", or for slightly more convenient implementation.
 *
 * @param <T> The type of the stored resource.
 */
public interface SingleSlotStorage<T> extends SlottedStorage<T>, StorageView<T> {
	@Override
	default Iterator<StorageView<T>> iterator() {
		return TransferApiImpl.singletonIterator(this);
	}

	@Override
	default int getSlotCount() {
		return 1;
	}

	@Override
	default SingleSlotStorage<T> getSlot(int slot) {
		if (slot != 0) {
			throw new IndexOutOfBoundsException("Slot " + slot + " does not exist in a single-slot storage.");
		}

		return this;
	}
}
