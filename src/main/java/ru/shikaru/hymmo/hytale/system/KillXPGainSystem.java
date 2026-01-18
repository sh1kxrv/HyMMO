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
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.playerdata.PlayerStorage;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

import ru.shikaru.hymmo.hytale.component.PlayerXpComponent;
import ru.shikaru.hymmo.hytale.lang.Lang;

public final class KillXPGainSystem extends DeathSystems.OnDeathSystem {
    private final double DEFAULT_XP_GAIN_PERCENTAGE = 0.25;

    @Override
    public Query<EntityStore> getQuery() {
        return PlayerRef.getComponentType();
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
                var playerXpData = store.getComponent(attackerRef, PlayerXpComponent.getComponentType());
                if (playerXpData == null) {
                    return;
                }

                var oldLevel = playerXpData.getLevel();

                playerXpData.addXp(xpAmount);
                player.sendMessage(
                    Lang.GAIN_XP.param("xp", xpAmount)
                );

                var newLevel = playerXpData.getLevel();
                if(newLevel > oldLevel) {
                    player.sendMessage(
                        Lang.LVLUP
                            .param("oldLevel", oldLevel)
                            .param("newLevel", newLevel)
                    );
                }
            }
        }
    }
}
