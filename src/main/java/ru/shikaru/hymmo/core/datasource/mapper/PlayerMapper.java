package ru.shikaru.hymmo.core.datasource.mapper;

import ru.shikaru.hymmo.core.datasource.entity.PlayerEntity;
import ru.shikaru.hymmo.core.util.RowMapper;

import java.util.UUID;

public final class PlayerMapper {
    public static final RowMapper<PlayerEntity> MAPPER = rs -> new PlayerEntity(
            UUID.fromString(rs.getString("id")),
            rs.getLong("xp")
    );
}
