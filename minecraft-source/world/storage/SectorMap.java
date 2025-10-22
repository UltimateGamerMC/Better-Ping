/*
 * Internal private/static methods:
 *   Lnet/minecraft/world/storage/SectorMap;allocate(II)V
 */
package net.minecraft.world.storage;

import com.google.common.annotations.VisibleForTesting;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.BitSet;

public class SectorMap {
    private final BitSet bitSet = new BitSet();

    public void allocate(int start, int size) {
        this.bitSet.set(start, start + size);
    }

    public void free(int start, int size) {
        this.bitSet.clear(start, start + size);
    }

    public int allocate(int size) {
        int j = 0;
        while (true) {
            int k;
            int l;
            if ((l = this.bitSet.nextSetBit(k = this.bitSet.nextClearBit(j))) == -1 || l - k >= size) {
                this.allocate(k, size);
                return k;
            }
            j = l;
        }
    }

    @VisibleForTesting
    public IntSet getAllocatedBits() {
        return this.bitSet.stream().collect(IntArraySet::new, IntCollection::add, IntCollection::addAll);
    }
}

