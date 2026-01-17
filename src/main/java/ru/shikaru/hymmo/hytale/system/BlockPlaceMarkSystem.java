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
import com.hypixel.hytale.server.core.event.events.ecs.PlaceBlockEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import ru.shikaru.hymmo.HyMMOPlugin;
import ru.shikaru.hymmo.hytale.component.PlayerPlacedBlockComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.UUID;

public class BlockPlaceMarkSystem extends EntityEventSystem<EntityStore, PlaceBlockEvent> {
    public BlockPlaceMarkSystem() {
        super(PlaceBlockEvent.class);
    }

    @Override
    public void handle(
            final int index,
            @Nonnull final ArchetypeChunk<EntityStore> archetypeChunk,
            @Nonnull final Store<EntityStore> store,
            @Nonnull final CommandBuffer<EntityStore> cb,
            @Nonnull final PlaceBlockEvent event
    ) {
        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
        Player player = store.getComponent(ref, Player.getComponentType());

        var uuid = store.getComponent(ref, UUIDComponent.getComponentType());
        var blockTarget = event.getTargetBlock();

        var world = player.getWorld();

        var worldChunkStore = world.getChunkStore();

        long chunkIndex = ChunkUtil.indexChunkFromBlock(blockTarget.x, blockTarget.z);
        Ref<ChunkStore> chunkRef = worldChunkStore.getChunkReference(chunkIndex);
        if(chunkRef != null && chunkRef.isValid()) {
            var blockIndex = ChunkUtil.indexBlockInColumn(blockTarget.x, blockTarget.y, blockTarget.z);
            var chunkStore = chunkRef.getStore();
            var component = chunkStore.getComponent(chunkRef, PlayerPlacedBlockComponent.getComponentType());
            if(component == null) {
                var hashMap = new Object2IntOpenHashMap<UUID>();
                hashMap.addTo(uuid.getUuid(), blockIndex);
                component = chunkStore.putComponent(chunkRef, new PlayerPlacedBlockComponent(hashMap));
            }

//            var worldChunkComponent = chunkStore.getComponent(chunkRef, WorldChunk.getComponentType());
//            assert worldChunkComponent != null;
//
        }
    }

    @Nullable
    @Override
    public Query<EntityStore> getQuery() {
        return PlayerRef.getComponentType();
    }
}
