package ru.shikaru.hymmo.core.datasource.entity;

public record PlayerSkillEntity(String playerId, int skillId, long xp, int level) { }
