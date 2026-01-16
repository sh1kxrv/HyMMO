package ru.shikaru.hymmo.hytale.component;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import ru.shikaru.hymmo.HyMMOPlugin;

import javax.annotation.Nonnull;
import java.util.UUID;

public final class PlayerPlacedBlockComponent implements Component<EntityStore> {
    public UUID placedBy;

    public PlayerPlacedBlockComponent(){
        this(null);
    }

    public PlayerPlacedBlockComponent(UUID placedBy) {
        this.placedBy = placedBy;
    }

    @Override
    public Component<EntityStore> clone() {
        return new PlayerPlacedBlockComponent(this.placedBy);
    }

    @Nonnull
    public static ComponentType<EntityStore, PlayerPlacedBlockComponent> getComponentType(){
        return HyMMOPlugin.get().getPlayerPlacedBlockComponent();
    }

    public static final BuilderCodec<PlayerPlacedBlockComponent> CODEC = BuilderCodec.builder(PlayerPlacedBlockComponent.class, PlayerPlacedBlockComponent::new)
            .append(
                    new KeyedCodec<>("PlacedBy", Codec.UUID_STRING),
                    (p, o) -> p.placedBy = o,
                    (g) -> g.placedBy
            )
            .add()
            .build();
}
