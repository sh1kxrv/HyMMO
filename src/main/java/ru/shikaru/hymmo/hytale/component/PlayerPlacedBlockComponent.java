package ru.shikaru.hymmo.hytale.component;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;

import java.util.*;
import javax.annotation.Nonnull;

import ru.shikaru.hymmo.HyMMOPlugin;

public class PlayerPlacedBlockComponent implements Component<ChunkStore> {
    private static final ArrayCodec<Integer> INT_ARRAY_CODEC = new ArrayCodec<>(Codec.INTEGER, Integer[]::new);

    public static final BuilderCodec<PlayerPlacedBlockComponent> CODEC =
            BuilderCodec.builder(PlayerPlacedBlockComponent.class, PlayerPlacedBlockComponent::new)
            .append(
                    new KeyedCodec<>("BlockIndexes", INT_ARRAY_CODEC),
                    (ppbc, data, extra) -> {
                        ppbc.blocksIds.clear();
                        if(data == null) return;
                        ppbc.blocksIds.addAll(Arrays.asList(data));
                    },
                    (ppbc, extra) ->  ppbc.blocksIds.toArray(new Integer[]{})
            )
            .add().build();

    private final Set<Integer> blocksIds;

    public PlayerPlacedBlockComponent() {
        this(new HashSet<Integer>());
    }

    public PlayerPlacedBlockComponent(Set<Integer> list) {
        this.blocksIds = list;
    }

    public PlayerPlacedBlockComponent(Integer[] arr) {
        var hashSet = new HashSet<Integer>();
        hashSet.addAll(List.of(arr));
        this.blocksIds = hashSet;
    }

    public Set<Integer> getPlacedBlocksIDs() {
        return blocksIds;
    }

    public boolean has(int blockIndex){
        return this.blocksIds.contains(blockIndex);
    }

    public void remove(int blockIndex){
        this.blocksIds.remove(blockIndex);
    }

    @Override
    public Component<ChunkStore> clone() {
        return new PlayerPlacedBlockComponent(this.blocksIds.toArray(new Integer[]{}));
    }

    @Nonnull
    public static ComponentType<ChunkStore, PlayerPlacedBlockComponent> getComponentType() {
        return HyMMOPlugin.get().getPlayerPlacedBlockComponent();
    }
}
