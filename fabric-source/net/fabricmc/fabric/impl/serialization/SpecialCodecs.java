package net.fabricmc.fabric.impl.serialization;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;

public interface SpecialCodecs {
	Codec<long[]> LONG_ARRAY = Codec.LONG_STREAM.xmap(LongStream::toArray, LongStream::of);
	Codec<byte[]> BYTE_ARRAY = Codec.BYTE_BUFFER.xmap((buf) -> {
		if (buf.hasArray()) {
			return buf.array();
		}

		var bytes = new byte[buf.capacity()];
		buf.get(bytes);
		return bytes;
	}, ByteBuffer::wrap);

	MapCodec<Collection<String>> KEYS_EXTRACT = new MapCodec<>() {
		@Override
		public <T> DataResult<Collection<String>> decode(DynamicOps<T> ops, MapLike<T> input) {
			return DataResult.success(input.entries().map(entry -> ops.getStringValue(entry.getFirst()).getOrThrow()).toList());
		}

		@Override
		public <T> RecordBuilder<T> encode(Collection<String> input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
			return prefix;
		}

		@Override
		public <T> Stream<T> keys(DynamicOps<T> ops) {
			return Stream.empty();
		}
	};

	static MapCodec<Boolean> contains(String key) {
		return new MapCodec<>() {
			@Override
			public <T> DataResult<Boolean> decode(DynamicOps<T> ops, MapLike<T> input) {
				return DataResult.success(input.get(key) != null);
			}

			@Override
			public <T> RecordBuilder<T> encode(Boolean input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
				return prefix;
			}

			@Override
			public <T> Stream<T> keys(DynamicOps<T> ops) {
				return Stream.empty();
			}
		};
	}
}
