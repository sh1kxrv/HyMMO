package ru.shikaru.hymmo.formula;

import ru.shikaru.hymmo.api.IFormula;

public class ExpFormula implements IFormula {
    private final double baseXp;
    private final double exponent;
    private final int maxLevel;
    private final double invExponent;

    public ExpFormula(double baseXp, double exponent, int maxLevel) {
        if (baseXp <= 0) {
            throw new IllegalArgumentException("baseXp must be > 0");
        }
        if (exponent <= 0) {
            throw new IllegalArgumentException("exponent must be > 0");
        }
        if (maxLevel < 1) {
            throw new IllegalArgumentException("maxLevel must be >= 1");
        }

        this.baseXp = baseXp;
        this.exponent = exponent;
        this.maxLevel = maxLevel;
        this.invExponent = 1.0 / exponent;
    }

    @Override
    public long getXpForLevel(int level) {
        if (level < 1) {
            throw new IllegalArgumentException("level must be >= 1");
        }

        if (level == 1) {
            return 0L;
        }

        // (level - 1)^exponent * baseXp
        double value = baseXp * Math.pow(level - 1, exponent);

        if (!Double.isFinite(value) || value >= Long.MAX_VALUE) {
            return Long.MAX_VALUE;
        }

        return (long) Math.ceil(value);
    }

    @Override
    public int getLevelForXp(long xp) {
        if (xp < 0) {
            throw new IllegalArgumentException("xp must be >= 0");
        }

        if (xp < baseXp) {
            return 1;
        }

        double raw = Math.pow((double) xp / baseXp, invExponent);
        int level = (int) raw + 1;

        if (level <= 1) {
            return 1;
        }
        if (level >= maxLevel) {
            return maxLevel;
        }

        long xpAtLevel = getXpForLevel(level);

        if (xpAtLevel > xp) {
            return level - 1;
        }

        long xpAtNext = getXpForLevel(level + 1);
        if (xpAtNext <= xp) {
            return level + 1;
        }

        return level;
    }
}
