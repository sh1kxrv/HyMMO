package ru.shikaru.hymmo.hytale.system;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.math.util.ChunkUtil;
import com.hypixel.hytale.server.core.entity.UUIDComponent;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.ecs.BreakBlockEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import java.util.logging.Level;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ru.shikaru.hymmo.HyMMOPlugin;
import ru.shikaru.hymmo.hytale.component.PlayerPlacedBlockComponent;
import ru.shikaru.hymmo.hytale.component.skills.WoodcuttingSkillComponent;
import ru.shikaru.hymmo.hytale.lang.Lang;

public final class BreakBlockXPGainSystem extends EntityEventSystem<EntityStore, BreakBlockEvent> {
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

        assert player != null;

        var playerInv = player.getInventory();
        if (playerInv != null) {
            var itemHand = player.getInventory().getItemInHand();
            if(itemHand != null) HyMMOPlugin.get().pluginLogger.at(Level.INFO).log("Item hand" + itemHand.getItemId());
        }

        var uuid = store.getComponent(ref, UUIDComponent.getComponentType());

        var block = event.getBlockType();
        HyMMOPlugin.get().pluginLogger.at(Level.INFO).log("Block: %s", block.toString());

        var blockVec3 = event.getTargetBlock();
        var world = player.getWorld();

        assert world != null;

        var worldChunkStore = world.getChunkStore();

        long chunkIndex = ChunkUtil.indexChunkFromBlock(blockVec3.x, blockVec3.z);
        Ref<ChunkStore> chunkRef = worldChunkStore.getChunkReference(chunkIndex);

        HyMMOPlugin.get().pluginLogger.at(Level.INFO).log("Getting chunk store's");
        if(chunkRef != null && chunkRef.isValid()) {
            var blockIndex = ChunkUtil.indexBlockInColumn(blockVec3.x, blockVec3.y, blockVec3.z);
            var chunkStore = chunkRef.getStore();
            var component = chunkStore.getComponent(chunkRef, PlayerPlacedBlockComponent.getComponentType());
            if (component != null) {
                HyMMOPlugin.get().pluginLogger.at(Level.INFO).log("Component: %b %s %d", component != null, uuid.getUuid(), blockIndex);
                var hasBlockInComponent = component.has(blockIndex);
                HyMMOPlugin.get().pluginLogger.at(Level.INFO).log("Has Block in component: %b", hasBlockInComponent);
                if (hasBlockInComponent) {
                    HyMMOPlugin.get().pluginLogger.at(Level.INFO).log("Block not generated! Skip");
                    return;
                }
                component.remove(blockIndex);
            }

            var blockId = block.getId();
            if (blockId.contains("Wood_") || blockId.endsWith("_Trunk")) {
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
                if (newLevel > oldLevel) {
                    player.sendMessage(
                            Lang.LVLUP
                                    .param("oldLevel", oldLevel)
                                    .param("newLevel", newLevel)
                    );
                }
            }
        }
    }

    @Nullable
    @Override
    public Query<EntityStore> getQuery() {
        return PlayerRef.getComponentType();
    }
}
