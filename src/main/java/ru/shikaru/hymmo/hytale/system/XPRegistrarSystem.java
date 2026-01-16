package ru.shikaru.hymmo.hytale.system;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.player.PlayerSystems;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

import ru.shikaru.hymmo.hytale.component.PlayerXpComponent;

public class XPRegistrarSystem extends PlayerSystems.PlayerSpawnedSystem {
    @Nonnull
    public Query<EntityStore> getQuery(){
        return Player.getComponentType();
    }

    @Override
    public void onEntityAdded(@Nonnull Ref ref, @Nonnull AddReason addReason, @Nonnull Store store, @Nonnull CommandBuffer commandBuffer) {
        Player player = (Player) store.getComponent(ref, Player.getComponentType());
        assert player != null;
        player.getWorld().execute(() -> {
            var playerXpData = store.getComponent(ref, PlayerXpComponent.getComponentType());
            if(playerXpData == null) {
                playerXpData = new PlayerXpComponent();
                store.addComponent(ref, PlayerXpComponent.getComponentType(), playerXpData);
            }
        });
    }

    @Override
    public void onEntityRemove(@Nonnull Ref ref, @Nonnull RemoveReason removeReason, @Nonnull Store store, @Nonnull CommandBuffer commandBuffer) {
        Player player = (Player) store.getComponent(ref, Player.getComponentType());
        assert player != null;
    }
}
