package net.fabricmc.fabric.impl.registry.sync;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.event.registry.RegistryIdRemapCallback;

public class RemapStateImpl<T> implements RegistryIdRemapCallback.RemapState<T> {
	private final Int2IntMap rawIdChangeMap;
	private final Int2ObjectMap<Identifier> oldIdMap;
	private final Int2ObjectMap<Identifier> newIdMap;

	public RemapStateImpl(Registry<T> registry, Int2ObjectMap<Identifier> oldIdMap, Int2IntMap rawIdChangeMap) {
		this.rawIdChangeMap = rawIdChangeMap;
		this.oldIdMap = oldIdMap;
		this.newIdMap = new Int2ObjectOpenHashMap<>();

		for (Int2IntMap.Entry entry : rawIdChangeMap.int2IntEntrySet()) {
			Identifier id = registry.getId(registry.get(entry.getIntValue()));
			newIdMap.put(entry.getIntValue(), id);
		}
	}

	@Override
	public Int2IntMap getRawIdChangeMap() {
		return rawIdChangeMap;
	}

	@Override
	public Identifier getIdFromOld(int oldRawId) {
		return oldIdMap.get(oldRawId);
	}

	@Override
	public Identifier getIdFromNew(int newRawId) {
		return newIdMap.get(newRawId);
	}
}
