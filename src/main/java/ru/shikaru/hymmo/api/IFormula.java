package ru.shikaru.hymmo.api;

public interface IFormula {
    long getXpForLevel(int level);
    int getLevelForXp(long xp);
}
