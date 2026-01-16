package ru.shikaru.hymmo.core.datasource.registry;

public enum SkillRegistry {

    WOODCUTTING("woodcutting", "Владение топором");

    public final String code;
    public final String name;

    SkillRegistry(String code, String name) {
        this.code = code;
        this.name = name;
    }
}