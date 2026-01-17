package ru.shikaru.hymmo.hytale.component;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.codecs.map.MapCodec;
import com.hypixel.hytale.codec.codecs.map.Object2IntMapCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import ru.shikaru.hymmo.HyMMOPlugin;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PlayerPlacedBlockComponent implements Component<ChunkStore> {
    public static final BuilderCodec<PlayerPlacedBlockComponent> CODEC = BuilderCodec.builder(PlayerPlacedBlockComponent.class, PlayerPlacedBlockComponent::new)
            .append(
                    new KeyedCodec<>(
                            "PlacedBlocksByUUID",
                            new Object2IntMapCodec(
                                    Codec.UUID_STRING,
                                    Object2IntOpenHashMap::new
                            )
                    ),
                    (p, o) -> p.placedBlocksByUUID = o,
                    g -> g.placedBlocksByUUID
            )
            .add()
            .build();

    public Object2IntMap<UUID> placedBlocksByUUID;

    public PlayerPlacedBlockComponent() {
        this(new Object2IntOpenHashMap<>());
    }

    public PlayerPlacedBlockComponent(Object2IntMap<UUID> placedBlocksByUUID) {
        this.placedBlocksByUUID =
                placedBlocksByUUID != null
                        ? placedBlocksByUUID
                        : new Object2IntOpenHashMap<>();
    }

    @Override
    public Component<ChunkStore> clone() {
        return new PlayerPlacedBlockComponent(
                new Object2IntOpenHashMap<>(this.placedBlocksByUUID)
        );
    }

    @Nonnull
    public static ComponentType<ChunkStore, PlayerPlacedBlockComponent> getComponentType() {
        return HyMMOPlugin.get().getPlayerPlacedBlockComponent();
    }
}
