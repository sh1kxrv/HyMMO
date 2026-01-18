package ru.shikaru.hymmo.hytale.command;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

import ru.shikaru.hymmo.hytale.component.PlayerXpComponent;
import ru.shikaru.hymmo.hytale.component.skills.WoodcuttingSkillComponent;
import ru.shikaru.hymmo.hytale.lang.Lang;

public class XpCommand extends AbstractPlayerCommand {
    public XpCommand(){
        super("xp", "Get current xp amount");
    }

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());
        var xpComponent = store.getComponent(ref, PlayerXpComponent.getComponentType());
        assert xpComponent != null;
        assert player != null;

        player.sendMessage(
                Lang.XP_AMOUNT
                        .param("xp", xpComponent.getXp())
                        .param("level", xpComponent.getLevel())
        );

        var woodcuttingSkill = store.getComponent(ref, WoodcuttingSkillComponent.getComponentType());

        assert woodcuttingSkill != null;

        player.sendMessage(Message.raw("XP" + woodcuttingSkill.getXp() + " / " + woodcuttingSkill.getLevel()));;
    }
}
