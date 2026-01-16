package ru.shikaru.hymmo.manager;

import ru.shikaru.hymmo.api.manager.IPlayerManager;
import ru.shikaru.hymmo.core.datasource.entity.PlayerEntity;

import javax.annotation.Nonnull;
import java.util.UUID;

public record PlayerManager(DataSourceManager dataSourceManager) implements IPlayerManager {
    public PlayerManager(
            @Nonnull
            DataSourceManager dataSourceManager
    ) {
        this.dataSourceManager = dataSourceManager;
    }

    @Override
    public int getPlayerLevel(UUID playerId) {
        var playerRepository = this.dataSourceManager.playerRepository;
        var playerOptional = playerRepository.get(playerId.toString());
        return playerOptional.map(PlayerEntity::level).orElse(0);
    }

    @Override
    public long getPlayerXp(UUID playerId) {
        var playerRepository = this.dataSourceManager.playerRepository;
        var playerOptional = playerRepository.get(playerId.toString());
        return playerOptional.map(PlayerEntity::xp).orElse(0L);
    }

    @Override
    public void addXp(UUID playerId, long xp) {
        var playerRepository = this.dataSourceManager.playerRepository;
        var playerOptional = playerRepository.get(playerId.toString());
        playerOptional.ifPresent(player -> playerRepository.update(playerId.toString(), player.xp() + xp, player.level()));
    }

    @Override
    public void setXp(UUID playerId, long xp) {

    }

    @Override
    public void drainXp(UUID playerId, long xp) {
        var playerRepository = this.dataSourceManager.playerRepository;
        var playerOptional = playerRepository.get(playerId.toString());
        playerOptional.ifPresent(player -> {
            playerRepository.update(playerId.toString(), Math.max(0, player.xp() - xp), player.level());
        });
    }
}
