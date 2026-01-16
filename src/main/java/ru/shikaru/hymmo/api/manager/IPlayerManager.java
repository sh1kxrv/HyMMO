package ru.shikaru.hymmo.api.manager;

import ru.shikaru.hymmo.core.api.IManager;

public interface IPlayerManager extends IManager {
    int getPlayerLevel(String playerId);
    long getPlayerXp(String playerId);
    void addXp(String playerId, long xp);
    void drainXp(String playerId, long xp);
    void setXp(String playerId, long xp);
}
