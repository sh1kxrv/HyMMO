package ru.shikaru.hymmo.api.manager;

import ru.shikaru.hymmo.core.api.IManager;

import java.util.UUID;

public interface IPlayerManager extends IManager {
    int getPlayerLevel(UUID playerId);
    long getPlayerXp(UUID playerId);
    void addXp(UUID playerId, long xp);
    void drainXp(UUID playerId, long xp);
    void setXp(UUID playerId, long xp);
}
