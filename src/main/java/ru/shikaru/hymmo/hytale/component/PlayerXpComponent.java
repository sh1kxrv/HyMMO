package ru.shikaru.hymmo.hytale.component;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ru.shikaru.hymmo.HyMMOPlugin;
import ru.shikaru.hymmo.hytale.component.api.AbstractExperiencedComponent;

public class PlayerXpComponent extends AbstractExperiencedComponent implements Component<EntityStore> {
    public static final BuilderCodec<PlayerXpComponent> CODEC = BuilderCodec.builder(PlayerXpComponent.class, PlayerXpComponent::new)
            .append(
                    new KeyedCodec<>("Xp", Codec.LONG),
                    (p, o) -> p.xp = o,
                    (g) -> g.xp
            )
            .add()
            .build();

    public PlayerXpComponent(){
        this(0L);
    }

    public PlayerXpComponent(long xp) {
        super(xp);
    }

    public PlayerXpComponent(PlayerXpComponent other) {
        super(other.xp);
        this.xp = other.xp;
    }

    @Nullable
    @Override
    public Component<EntityStore> clone() {
        return new PlayerXpComponent(this);
    }

    @Nonnull
    public static ComponentType<EntityStore, PlayerXpComponent> getComponentType(){
        return HyMMOPlugin.get().getPlayerXpDataComponent();
    }
}
