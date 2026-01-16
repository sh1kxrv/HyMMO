package ru.shikaru.hymmo.core.datasource.mapper;

import ru.shikaru.hymmo.core.datasource.entity.SkillEntity;
import ru.shikaru.hymmo.core.util.RowMapper;

public final class SkillMapper {
    public static final RowMapper<SkillEntity> MAPPER = rs -> new SkillEntity(
            rs.getInt("id"),
            rs.getString("code"),
            rs.getString("name")
    );
}
