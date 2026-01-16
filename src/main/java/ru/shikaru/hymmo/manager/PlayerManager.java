package ru.shikaru.hymmo.manager;

import ru.shikaru.hymmo.api.manager.IPlayerManager;

import javax.annotation.Nonnull;

public record PlayerManager(DataSourceManager dataSourceManager) implements IPlayerManager {
    public PlayerManager(
            @Nonnull
            DataSourceManager dataSourceManager
    ) {
        this.dataSourceManager = dataSourceManager;
    }

    @Override
    public int getPlayerLevel(String playerId) {
        return 0;
    }

    @Override
    public long getPlayerXp(String playerId) {
        return 0;
    }

    @Override
    public void addXp(String playerId, long xp) {
        var playerRepository = this.dataSourceManager.playerRepository;
        var player = playerRepository.get(playerId);
    }

    @Override
    public void setXp(String playerId, long xp) {

    }

    @Override
    public void drainXp(String playerId, long xp) {

    }
}
