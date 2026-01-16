package ru.shikaru.hymmo.core.datasource.mapper;

import ru.shikaru.hymmo.core.datasource.entity.PlayerSkillEntity;
import ru.shikaru.hymmo.core.util.RowMapper;

public final class PlayerSkillMapper {
    public static final RowMapper<PlayerSkillEntity> MAPPER = rs -> new PlayerSkillEntity(
            rs.getString("playerId"),
            rs.getInt("skillId"),
            rs.getLong("xp"),
            rs.getInt("level")
    );
}
