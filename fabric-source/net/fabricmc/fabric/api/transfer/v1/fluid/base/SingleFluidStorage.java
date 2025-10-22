package net.fabricmc.fabric.api.transfer.v1.fluid.base;

import java.util.Objects;

import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.storage.TransferVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;

/**
 * A storage that can store a single fluid variant at any given time.
 * Implementors should at least override {@link #getCapacity(TransferVariant) getCapacity(FluidVariant)},
 * and probably {@link #onFinalCommit} as well for {@code markDirty()} and similar calls.
 *
 * <p>This is a convenient specialization of {@link SingleVariantStorage} for fluids that additionally offers methods
 * to deserialize the contents of the storage.
 */
public abstract class SingleFluidStorage extends SingleVariantStorage<FluidVariant> {
	/**
	 * Create a fluid storage with a fixed capacity and a change handler.
	 *
	 * @param capacity Fixed capacity of the fluid storage. Must be non-negative.
	 * @param onChange Change handler, generally for {@code markDirty()} or similar calls. May not be null.
	 */
	public static SingleFluidStorage withFixedCapacity(long capacity, Runnable onChange) {
		StoragePreconditions.notNegative(capacity);
		Objects.requireNonNull(onChange, "onChange may not be null");

		return new SingleFluidStorage() {
			@Override
			protected long getCapacity(FluidVariant variant) {
				return capacity;
			}

			@Override
			protected void onFinalCommit() {
				onChange.run();
			}
		};
	}

	@Override
	protected final FluidVariant getBlankVariant() {
		return FluidVariant.blank();
	}

	/**
	 * Simple implementation of reading from {@link ReadView}, to match what is written by {@link #writeData}.
	 * Other formats are allowed, this is just a suggestion.
	 */
	public void readData(ReadView data) {
		SingleVariantStorage.readData(this, FluidVariant.CODEC, FluidVariant::blank, data);
	}

	/**
	 * Simple implementation of writing to {@link WriteView}. Other formats are allowed, this is just a convenient suggestion.
	 */
	public void writeData(WriteView data) {
		SingleVariantStorage.writeData(this, FluidVariant.CODEC, data);
	}
}
