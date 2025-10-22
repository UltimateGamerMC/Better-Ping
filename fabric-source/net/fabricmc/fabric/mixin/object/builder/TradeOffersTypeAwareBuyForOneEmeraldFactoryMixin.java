package net.fabricmc.fabric.mixin.object.builder;

import java.util.Set;
import java.util.stream.Stream;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Cancellable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.registry.RegistryKey;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerType;

@Mixin(TradeOffers.TypeAwareBuyForOneEmeraldFactory.class)
public abstract class TradeOffersTypeAwareBuyForOneEmeraldFactoryMixin {
	/**
	 * Vanilla will check the "VillagerType -> Item" map in the stream and throw an exception for villager types not specified in the map.
	 * This breaks any and all custom villager types.
	 * We want to prevent this default logic so modded villager types will work.
	 * So we return an empty stream so an exception is never thrown.
	 */
	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/util/Set;stream()Ljava/util/stream/Stream;"))
	private <T> Stream<T> disableVanillaCheck(Set<RegistryKey<VillagerType>> instance) {
		return Stream.empty();
	}

	/**
	 * To prevent crashes due to passing a {@code null} item to a {@link TradedItem}, return a {@code null} trade offer
	 * early before {@code null} is passed to the constructor.
	 */
	@ModifyExpressionValue(
			method = "create",
			at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;")
	)
	private Object failOnNullItem(Object item, @Cancellable CallbackInfoReturnable<TradeOffer> cir) {
		if (item == null) {
			cir.setReturnValue(null);
		}

		return item;
	}
}
