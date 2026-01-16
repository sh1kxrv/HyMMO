package ru.shikaru.hymmo.hytale.component.api;

import ru.shikaru.hymmo.HyMMOPlugin;

public abstract class AbstractExperiencedComponent {
    protected long xp;

    public AbstractExperiencedComponent(long xp) {
        this.xp = xp;
    }

    public void addXp(long xp){
        this.xp += xp;
    }

    public void drainXp(long xp) {
        this.xp = Math.max(0, this.xp - xp);
    }

    public long getXp() {
        return this.xp;
    }

    public int getLevel(){
        return HyMMOPlugin.get().getLevelFormula().getLevelForXp(this.xp);
    }

    public long getXpForLevel(int level) {
        if (level <= 1) {
            return 0L;
        }

        return HyMMOPlugin.get().getLevelFormula().getXpForLevel(level);
    }

}
