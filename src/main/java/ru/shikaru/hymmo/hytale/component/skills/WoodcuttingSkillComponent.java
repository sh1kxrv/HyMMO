package ru.shikaru.hymmo.hytale.component.skills;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

import ru.shikaru.hymmo.HyMMOPlugin;
import ru.shikaru.hymmo.hytale.component.SkillComponent;
import ru.shikaru.hymmo.hytale.lang.Lang;

public final class WoodcuttingSkillComponent extends SkillComponent implements Component<EntityStore> {
    public WoodcuttingSkillComponent() {
        super("woodcutting", Lang.WOODCUTTING_SKILL_NAME.getRawText(), 0L);
    }

    public WoodcuttingSkillComponent(WoodcuttingSkillComponent other){
        super(other.skillId, other.skillName, other.xp);
    }

    @Override
    public Component<EntityStore> clone() {
        var skill = new WoodcuttingSkillComponent();
        skill.xp = this.xp;
        skill.skillName = Lang.WOODCUTTING_SKILL_NAME.getRawText();
        skill.skillId = this.skillId;
        return skill;
    }

    @Nonnull
    public static ComponentType<EntityStore, WoodcuttingSkillComponent> getComponentType(){
        return HyMMOPlugin.get().getWoodcuttingSkillComponent();
    }

    public static final BuilderCodec<WoodcuttingSkillComponent> CODEC =
            BuilderCodec.builder(
                            WoodcuttingSkillComponent.class,
                            WoodcuttingSkillComponent::new
                    )
                    .append(
                            new KeyedCodec<>("Xp", Codec.LONG),
                            (p, o) -> p.xp = o,
                            g -> g.xp
                    )
                    .add()
                    .append(
                            new KeyedCodec<>("SkillId", Codec.STRING),
                            (p, o) -> p.skillId = o,
                            g -> g.skillId
                    )
                    .add()
                    .build();
}
