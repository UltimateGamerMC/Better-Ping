package net.fabricmc.fabric.impl.transfer;

import java.util.AbstractList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.component.ComponentChanges;
import net.minecraft.component.ComponentType;

import net.fabricmc.fabric.api.transfer.v1.storage.SlottedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;

public class TransferApiImpl {
	public static final Logger LOGGER = LoggerFactory.getLogger("fabric-transfer-api-v1");
	public static final AtomicLong version = new AtomicLong();
	@SuppressWarnings("rawtypes")
	public static final Storage EMPTY_STORAGE = new Storage() {
		@Override
		public boolean supportsInsertion() {
			return false;
		}

		@Override
		public long insert(Object resource, long maxAmount, TransactionContext transaction) {
			return 0;
		}

		@Override
		public boolean supportsExtraction() {
			return false;
		}

		@Override
		public long extract(Object resource, long maxAmount, TransactionContext transaction) {
			return 0;
		}

		@Override
		public Iterator<StorageView> iterator() {
			return Collections.emptyIterator();
		}

		@Override
		public long getVersion() {
			return 0;
		}

		@Override
		public String toString() {
			return "EmptyStorage";
		}
	};

	public static <T> Iterator<T> singletonIterator(T it) {
		return new Iterator<T>() {
			boolean hasNext = true;

			@Override
			public boolean hasNext() {
				return hasNext;
			}

			@Override
			public T next() {
				if (!hasNext) {
					throw new NoSuchElementException();
				}

				hasNext = false;
				return it;
			}
		};
	}

	public static <T> List<SingleSlotStorage<T>> makeListView(SlottedStorage<T> storage) {
		return new AbstractList<>() {
			@Override
			public SingleSlotStorage<T> get(int index) {
				return storage.getSlot(index);
			}

			@Override
			public int size() {
				return storage.getSlotCount();
			}
		};
	}

	public static ComponentChanges mergeChanges(ComponentChanges base, ComponentChanges applied) {
		ComponentChanges.Builder builder = ComponentChanges.builder();

		writeChangesTo(base, builder);
		writeChangesTo(applied, builder);

		return builder.build();
	}

	@SuppressWarnings("unchecked")
	private static void writeChangesTo(ComponentChanges changes, ComponentChanges.Builder builder) {
		for (Map.Entry<ComponentType<?>, Optional<?>> entry : changes.entrySet()) {
			if (entry.getValue().isPresent()) {
				builder.add((ComponentType<Object>) entry.getKey(), entry.getValue().get());
			} else {
				builder.remove(entry.getKey());
			}
		}
	}
}
