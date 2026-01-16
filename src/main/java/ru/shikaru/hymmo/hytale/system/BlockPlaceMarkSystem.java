package ru.shikaru.hymmo.hytale.system;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.server.core.entity.UUIDComponent;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.ecs.PlaceBlockEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import ru.shikaru.hymmo.HyMMOPlugin;
import ru.shikaru.hymmo.hytale.component.PlayerPlacedBlockComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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

        var uuid = store.getComponent(player.getReference(), UUIDComponent.getComponentType());

        var blockEntity = event.getTargetBlock();

        // TODO: Разобраться как получить блок

//        player.getWorld().execute(() -> {
//
//        });
//
//        cb.addComponent(blockEntity, PlayerPlacedBlockComponent.getComponentType(), new PlayerPlacedBlockComponent(uuid.getUuid()));
    }

    @Nullable
    @Override
    public Query<EntityStore> getQuery() {
        return PlayerRef.getComponentType();
    }
}
