package net.fabricmc.fabric.api.transfer.v1.item.base;

import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.TransferVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;

/**
 * A storage that can store a single item variant at any given time.
 * Implementors should at least override {@link #getCapacity(TransferVariant) getCapacity(ItemVariant)},
 * and probably {@link #onFinalCommit} as well for {@code markDirty()} and similar calls.
 *
 * <p>This is a convenient specialization of {@link SingleVariantStorage} for items that additionally offers methods
 * to deserialize the contents of the storage.
 */
public abstract class SingleItemStorage extends SingleVariantStorage<ItemVariant> {
	@Override
	protected final ItemVariant getBlankVariant() {
		return ItemVariant.blank();
	}

	/**
	 * Simple implementation of reading from {@link ReadView}, to match what is written by {@link #writeData}.
	 * Other formats are allowed, this is just a suggestion.
	 */
	public void readData(ReadView data) {
		SingleVariantStorage.readData(this, ItemVariant.CODEC, ItemVariant::blank, data);
	}

	/**
	 * Simple implementation of writing to {@link WriteView}. Other formats are allowed, this is just a suggestion.
	 */
	public void writeData(WriteView data) {
		SingleVariantStorage.writeData(this, ItemVariant.CODEC, data);
	}
}
