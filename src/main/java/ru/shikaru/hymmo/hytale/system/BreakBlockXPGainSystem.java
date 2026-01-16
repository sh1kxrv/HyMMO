package ru.shikaru.hymmo.hytale.system;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.ecs.BreakBlockEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import java.util.logging.Level;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ru.shikaru.hymmo.HyMMOPlugin;
import ru.shikaru.hymmo.hytale.component.skills.WoodcuttingSkillComponent;
import ru.shikaru.hymmo.hytale.lang.Lang;

public class BreakBlockXPGainSystem extends EntityEventSystem<EntityStore, BreakBlockEvent> {
    public BreakBlockXPGainSystem() {
        super(BreakBlockEvent.class);
    }

    @Override
    public void handle(
            final int index,
           @Nonnull final ArchetypeChunk<EntityStore> archetypeChunk,
           @Nonnull final Store<EntityStore> store,
           @Nonnull final CommandBuffer<EntityStore> commandBuffer,
           @Nonnull final BreakBlockEvent event
    ) {
        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
        Player player = store.getComponent(ref, Player.getComponentType());

        var playerInv = player.getInventory();
        if (playerInv != null) {
            var itemHand = player.getInventory().getItemInHand();
            if(itemHand != null) HyMMOPlugin.get().pluginLogger.at(Level.INFO).log("Item hand" + itemHand.getItemId());
        }


        var block = event.getBlockType();
        block.
        var blockInteractions = block.getInteractions();

        HyMMOPlugin.get().pluginLogger.at(Level.INFO).log("Block interaction's" + String.join(", ", blockInteractions.values()));


        var blockId = block.getId();
        if(blockId.contains("Wood_") || blockId.endsWith("_Trunk")) {
            var skill = store.getComponent(ref, WoodcuttingSkillComponent.getComponentType());
            if (skill == null) {
                return;
            }

            var xpAmount = 1;

            var oldLevel = skill.getLevel();

            skill.addXp(xpAmount);
            player.sendMessage(
                    Lang.GAIN_XP.param("xp", xpAmount)
            );

            var newLevel = skill.getLevel();
            if(newLevel > oldLevel) {
                player.sendMessage(
                        Lang.LVLUP
                                .param("oldLevel", oldLevel)
                                .param("newLevel", newLevel)
                );
            }
        }
    }

    @Nullable
    @Override
    public Query<EntityStore> getQuery() {
        return PlayerRef.getComponentType();
    }
}
