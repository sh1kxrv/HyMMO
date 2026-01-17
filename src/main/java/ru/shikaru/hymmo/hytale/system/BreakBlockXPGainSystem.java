package ru.shikaru.hymmo.hytale.system;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.math.util.ChunkUtil;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.ecs.BreakBlockEvent;
import com.hypixel.hytale.server.core.modules.interaction.components.PlacedByInteractionComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk;
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
import com.hypixel.hytale.server.core.universe.world.meta.state.PlacedByBlockState;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import java.util.logging.Level;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ru.shikaru.hymmo.HyMMOPlugin;
import ru.shikaru.hymmo.hytale.component.skills.WoodcuttingSkillComponent;
import ru.shikaru.hymmo.hytale.lang.Lang;

public final class BreakBlockXPGainSystem extends EntityEventSystem<EntityStore, BreakBlockEvent> {
    public BreakBlockXPGainSystem() {
        super(BreakBlockEvent.class);
    }

    /**
     * Block: BlockType{id=Wood_Oak_Trunk, unknown=false, group='Wood', blockSoundSetId='Wood', blockSoundSetIndex=10, particles=null, blockParticleSetId='Wood', blockBreakingDecalId='Breaking_Decals_Wood', particleColor=com.hypixel.hytale.protocol.Color@1eeb1, effect=null, textures=[com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockTypeTextures@5269dd2a], textureSideMask='null', cubeShadingMode=Standard, customModel='null', customModelTexture=null, customModelScale=1.0, customModelAnimation='null', drawType=Cube, material=Solid, opacity=Solid, requiresAlphaBlending=false, tickProcedurenull, tintUp=null, tintDown=null, tintNorth=null, tintSouth=null, tintWest=null, tintEast=null, biomeTintUp=0, biomeTintDown=0, biomeTintNorth=0, biomeTintSouth=0, biomeTintWest=0, biomeTintEast=0, randomRotation=None, variantRotation=Pipe, flipType=SYMMETRIC, rotationYawPlacementOffset=None, transitionTexture='null', transitionToGroups=null, hitboxType='Full', hitboxTypeIndex=0, interactionHitboxType='null', interactionHitboxTypeIndex=-2147483648, light=null, movementSettings=BlockMovementSettings{isClimbable=falseisBouncy=falsebounceSpeed=0.0, climbUpSpeedMultiplier=1.0, climbDownSpeedMultiplier=1.0, climbLateralSpeedMultiplier=1.0, drag=0.82, friction=0.18, terminalVelocityModifier=1.0, horizontalSpeedMultiplier=1.0, jumpForceMultiplier=1.0}, flags=com.hypixel.hytale.protocol.BlockFlags@9e61, interactionHint='null', isTrigger=false, damageToEntities=0, allowsMultipleUsers=true, bench=null, gathering=BlockGathering{breaking=BlockBreakingDropType{gatherType='Woods', quality=0, quantity=1, itemId=Wood_Oak_Trunk, dropListId='null'}, harvest=null, harvest=null}, placementSettings=null, state=StateData{id='null', stateToBlock='{Stripped=*Wood_Oak_Trunk_State_Definitions_Stripped}'}, ambientSoundEventId='null', ambientSoundEventIndex='0', interactionSoundEventId='null', interactionSoundEventIndex='0', isLooping=false, farming=null, supportDropType=BREAK, maxSupportDistance=5
     * Block: BlockType{id=Wood_Oak_Trunk_Full, unknown=false, group='Wood', blockSoundSetId='Wood', blockSoundSetIndex=10, particles=null, blockParticleSetId='Wood', blockBreakingDecalId='Breaking_Decals_Wood', particleColor=com.hypixel.hytale.protocol.Color@1eeb1, effect=null, textures=[com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockTypeTextures@40c10d93], textureSideMask='null', cubeShadingMode=Standard, customModel='null', customModelTexture=null, customModelScale=1.0, customModelAnimation='null', drawType=Cube, material=Solid, opacity=Solid, requiresAlphaBlending=false, tickProcedurenull, tintUp=null, tintDown=null, tintNorth=null, tintSouth=null, tintWest=null, tintEast=null, biomeTintUp=0, biomeTintDown=0, biomeTintNorth=0, biomeTintSouth=0, biomeTintWest=0, biomeTintEast=0, randomRotation=None, variantRotation=Pipe, flipType=SYMMETRIC, rotationYawPlacementOffset=None, transitionTexture='null', transitionToGroups=null, hitboxType='Full', hitboxTypeIndex=0, interactionHitboxType='null', interactionHitboxTypeIndex=-2147483648, light=null, movementSettings=BlockMovementSettings{isClimbable=falseisBouncy=falsebounceSpeed=0.0, climbUpSpeedMultiplier=1.0, climbDownSpeedMultiplier=1.0, climbLateralSpeedMultiplier=1.0, drag=0.82, friction=0.18, terminalVelocityModifier=1.0, horizontalSpeedMultiplier=1.0, jumpForceMultiplier=1.0}, flags=com.hypixel.hytale.protocol.BlockFlags@9e61, interactionHint='null', isTrigger=false, damageToEntities=0, allowsMultipleUsers=true, bench=null, gathering=BlockGathering{breaking=BlockBreakingDropType{gatherType='Woods', quality=0, quantity=1, itemId=Wood_Oak_Trunk, dropListId='null'}, harvest=null, harvest=null}, placementSettings=null, state=StateData{id='null', stateToBlock='{Stripped=*Wood_Oak_Trunk_State_Definitions_Stripped}'}, ambientSoundEventId='null', ambientSoundEventIndex='0', interactionSoundEventId='null', interactionSoundEventIndex='0', isLooping=false, farming=null, supportDropType=BREAK, maxSupportDistance=5
     * @param index
     * @param archetypeChunk
     * @param store
     * @param commandBuffer
     * @param event
     */

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
//            var worldChunkComponent = chunkStore.getComponent(chunkRef, WorldChunk.getComponentType());
//            assert worldChunkComponent != null;
//            HyMMOPlugin.get().pluginLogger.at(Level.INFO).log("Getting block state");
//            var blockState = worldChunkComponent.getState(blockVec3.x, blockVec3.y, blockVec3.z);
//
//            HyMMOPlugin.get().pluginLogger.at(Level.INFO).log("Block state: %b", blockState != null);
//
//            if(blockState instanceof PlacedByBlockState) {
//                HyMMOPlugin.get().pluginLogger.at(Level.INFO).log("Instance of PlacedByBlockState");
//            }

//            BlockComponentChunk blockComponentChunk = worldChunkComponent.getBlockComponentChunk();
//            var blockRef = blockComponentChunk == null ? null : blockComponentChunk.getEntityReference(blockIndex);
//            HyMMOPlugin.get().pluginLogger.at(Level.INFO).log("BlockRef != null : %b %s", blockRef != null, blockVec3.toString());
//            if(blockRef != null) {
//                var placedByInter = chunkStore.getComponent(blockRef, PlacedByInteractionComponent.getComponentType());
//                var whoPlaced = placedByInter.getWhoPlacedUuid();
//                HyMMOPlugin.get().pluginLogger.at(Level.INFO).log("Player placed this block: %s",whoPlaced.toString());
//                return;
//            }
        }

//        long chunkIndex = ChunkUtil.indexChunkFromBlock(blockVec3.x, blockVec3.z);
//        Ref<ChunkStore> chunkRef = world.getChunkStore().getChunkReference(chunkIndex);
//        if(chunkRef != null && chunkRef.isValid()) {
//            var chunkStore = chunkRef.getStore();
//            var playerPlacedComponent = chunkStore.getComponent(chunkRef, HyMMOPlugin.get().getPlayerPlacedBlockComponent());
//            if(playerPlacedComponent != null) {
//                HyMMOPlugin.get().pluginLogger.at(Level.INFO).log("Player placed this block: %s", playerPlacedComponent.placedBy.toString());
//                return;
//            }

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
//        }
    }

    @Nullable
    @Override
    public Query<EntityStore> getQuery() {
        return PlayerRef.getComponentType();
    }
}
