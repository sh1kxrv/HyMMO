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

public class PlayerXpComponent implements Component<EntityStore> {
    public static final BuilderCodec<PlayerXpComponent> CODEC;

    private long xp;
    private int level;

    public PlayerXpComponent(){
        this(0L, 0);
    }

    public PlayerXpComponent(long xp, int level) {
        this.xp = xp;
        this.level = level;
    }

    public PlayerXpComponent(PlayerXpComponent other) {
        this.xp = other.xp;
        this.level = other.level;
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

    public void addXp(long xp){
        this.xp += xp;
    }

    public void drainXp(long xp) {
        this.xp = Math.max(0, this.xp - xp);
    }

    public long getXp() {
        return this.xp;
    }

    public int getLevel(){
        return HyMMOPlugin.get().getLevelFormula().getLevelForXp(this.xp);
    }

    public long getXpForLevel(int level) {
        if (level <= 1) {
            return 0L;
        }

        return HyMMOPlugin.get().getLevelFormula().getXpForLevel(level);
    }

    static {
        CODEC = BuilderCodec.builder(PlayerXpComponent.class, PlayerXpComponent::new)
                .append(
                    new KeyedCodec<>("Xp", Codec.LONG),
                    (p, o) -> p.xp = o,
                    (g) -> g.xp
                )
            .add()
            .append(
                    new KeyedCodec<>("Level", Codec.INTEGER),
                    (p, o) -> p.level = o,
                    (g) -> g.level
            )
            .add()
            .build();
    }
}
