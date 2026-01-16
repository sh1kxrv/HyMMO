package ru.shikaru.hymmo.hytale.system;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathSystems;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import ru.shikaru.hymmo.core.manager.ManagerStore;
import ru.shikaru.hymmo.manager.PlayerManager;

import javax.annotation.Nonnull;

public final class XPSystem extends DeathSystems.OnDeathSystem {
    private final double DEFAULT_XP_GAIN_PERCENTAGE = 0.5;

    @Override
    public Query<EntityStore> getQuery() {
        return Query.any();
    }

    @Override
    public void onComponentAdded(
            @Nonnull Ref<EntityStore> ref,
            @Nonnull DeathComponent component,
            @Nonnull Store<EntityStore> store,
            @Nonnull CommandBuffer<EntityStore> commandBuffer
    ) {
        var deathInfo = component.getDeathInfo();
        if (deathInfo == null)
            return;

        if (deathInfo.getSource() instanceof Damage.EntitySource entitySource) {
            var attackerRef = entitySource.getRef();
            if (attackerRef.isValid()) {
                var player = store.getComponent(attackerRef, Player.getComponentType());
                if (player == null)
                    return;
                var statMap = store.getComponent(ref, EntityStatMap.getComponentType());
                if (statMap == null)
                    return;
                var healthIndex = EntityStatType.getAssetMap().getIndex("Health");
                var healthStat = statMap.get(healthIndex);
                if (healthStat == null)
                    return;
                var maxHealth = healthStat.getMax();
                var xpAmount = Math.max(1, (long) (maxHealth * DEFAULT_XP_GAIN_PERCENTAGE));
                var playerManager = ManagerStore.getOrThrow(PlayerManager.class);
                var playerUUID = player.getUuid();
                if (playerUUID == null) {
                    return;
                }
                playerManager.addXp(player.getUuid(), xpAmount);
//                LevelingCoreApi.getLevelServiceIfPresent().ifPresent(levelService -> {
//                    var levelBefore = levelService.getLevel(player.getUuid());
//                    // Checks that the SimpleParty plugin is installed
//                    if (PluginManager.get().getPlugin(new PluginIdentifier("net.justmadlime", "SimpleParty")) != null) {
//                        // INFO: Handle XP gain for SimpleParty plugin when it's installed
//                        SimplePartyCompat.onXPGain(xpAmount, player.getUuid(), levelService, config, player);
//                    } else {
//                        // Fallback to default XP gain if SimpleParty is not installed
//                        levelService.addXp(player.getUuid(), xpAmount);
//                        if (config.get().isEnableXPChatMsgs())
//                            player.sendMessage(CommandLang.GAINED.param("xp", xpAmount));
//                    }
//                    var levelAfter = levelService.getLevel(player.getUuid());
//                    if (levelAfter > levelBefore) {
//                        if (config.get().isEnableLevelChatMsgs())
//                            player.sendMessage(CommandLang.LEVEL_UP.param("level", levelAfter));
//                    }
//                });
            }
        }
    }
}
