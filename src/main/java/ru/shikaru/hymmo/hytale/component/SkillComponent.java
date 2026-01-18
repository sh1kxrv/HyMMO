package ru.shikaru.hymmo.hytale.component;

import ru.shikaru.hymmo.hytale.component.api.AbstractExperiencedComponent;

public abstract class SkillComponent extends AbstractExperiencedComponent {
    protected String skillId;
    protected String skillName;

    public SkillComponent(String skillId, String skillName, long xp) {
        super(xp);
        this.skillId = skillId;
        this.skillName = skillName;
    }
}
