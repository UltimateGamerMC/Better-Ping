package net.fabricmc.fabric.mixin.transfer;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.component.ComponentChanges;
import net.minecraft.fluid.Fluid;
import net.minecraft.sound.SoundEvent;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributeHandler;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.fabricmc.fabric.impl.transfer.fluid.FluidVariantCache;
import net.fabricmc.fabric.impl.transfer.fluid.FluidVariantImpl;

/**
 * <ul>
 *     <li>Cache the FluidVariant with a null tag inside each Fluid directly.</li>
 *     <li>Automatically uses the correct bucket filling sound for
 *     fluid attributes handlers overriding {@link FluidVariantAttributeHandler#getEmptySound}.</li>
 * </ul>
 */
@Mixin(Fluid.class)
@SuppressWarnings("unused")
public class FluidMixin implements FluidVariantCache {
	@Unique
	@SuppressWarnings("ConstantConditions")
	private final FluidVariant cachedFluidVariant = new FluidVariantImpl((Fluid) (Object) this, ComponentChanges.EMPTY);

	@Override
	public FluidVariant fabric_getCachedFluidVariant() {
		return cachedFluidVariant;
	}

	@Inject(
			method = "getBucketFillSound",
			at = @At("HEAD"),
			cancellable = true
	)
	public void hookGetBucketFillSound(CallbackInfoReturnable<Optional<SoundEvent>> cir) {
		Fluid fluid = (Fluid) (Object) this;
		Optional<SoundEvent> sound = FluidVariantAttributes.getHandlerOrDefault(fluid).getFillSound(FluidVariant.of(fluid));

		if (sound.isPresent()) {
			cir.setReturnValue(sound);
		}
	}
}
