package ru.shikaru.hymmo.formula;

import ru.shikaru.hymmo.api.IFormula;

public class LinearFormula implements IFormula {
    private final long xpPerLevel;
    private final int maxLevel;

    public LinearFormula(long xpPerLevel, int maxLevel) {
        if (xpPerLevel <= 0) {
            throw new IllegalArgumentException("xpPerLevel must be > 0");
        }
        if (maxLevel < 1) {
            throw new IllegalArgumentException("maxLevel must be >= 1");
        }
        this.xpPerLevel = xpPerLevel;
        this.maxLevel = maxLevel;
    }

    @Override
    public long getXpForLevel(int level) {
        if (level < 1) {
            throw new IllegalArgumentException("level must be >= 1");
        }

        if (level == 1) {
            return 0L;
        }

        long value = xpPerLevel * (long) (level - 1);

        if (value < 0 || value >= Long.MAX_VALUE) {
            return Long.MAX_VALUE;
        }

        return value;
    }

    @Override
    public int getLevelForXp(long xp) {
        if (xp < 0) {
            throw new IllegalArgumentException("xp must be >= 0");
        }

        long level = (xp / xpPerLevel) + 1;

        if (level >= maxLevel) {
            return maxLevel;
        }

        return (int) level;
    }
}
