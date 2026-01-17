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
    private static final ArrayCodec<String> STRING_ARRAY_CODEC = new ArrayCodec<>(Codec.STRING, String[]::new);

    public static final BuilderCodec<PlayerPlacedBlockComponent> CODEC =
            BuilderCodec.builder(PlayerPlacedBlockComponent.class, PlayerPlacedBlockComponent::new)
            .append(
                    new KeyedCodec<>("PlacedBlocksIDs", STRING_ARRAY_CODEC),
                    (ppbc, data, extra) -> {
                        ppbc.placedBlocksIDs.clear();
                        if(data == null) return;
                        ppbc.placedBlocksIDs.addAll(Arrays.asList(data));
                    },
                    (ppbc, extra) ->  ppbc.placedBlocksIDs.toArray(new String[]{})
            )
            .add().build();

    private final Set<String> placedBlocksIDs;

    public PlayerPlacedBlockComponent() {
        this(new HashSet<String>());
    }

    public PlayerPlacedBlockComponent(HashSet<String> list) {
        this.placedBlocksIDs = list;
    }

    public PlayerPlacedBlockComponent(String[] arr) {
        var hashSet = new HashSet<String>();
        hashSet.addAll(List.of(arr));
        this.placedBlocksIDs = hashSet;
    }

    public Set<String> getPlacedBlocksIDs() {
        return placedBlocksIDs;
    }

    public boolean has(UUID playerId, int blockIndex){
        var fullId = playerId + ":" + blockIndex;
        return this.placedBlocksIDs.contains(fullId);
    }

    @Override
    public Component<ChunkStore> clone() {
        return new PlayerPlacedBlockComponent(this.placedBlocksIDs.toArray(new String[]{}));
    }

    @Nonnull
    public static ComponentType<ChunkStore, PlayerPlacedBlockComponent> getComponentType() {
        return HyMMOPlugin.get().getPlayerPlacedBlockComponent();
    }
}
