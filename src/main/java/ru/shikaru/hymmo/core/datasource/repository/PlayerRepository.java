package ru.shikaru.hymmo.core.datasource.repository;

import ru.shikaru.hymmo.core.api.Repository;
import ru.shikaru.hymmo.core.datasource.entity.PlayerEntity;
import ru.shikaru.hymmo.core.datasource.mapper.PlayerMapper;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public final class PlayerRepository extends Repository {
    public PlayerRepository(@Nonnull DataSource ds) {
        super(ds);
    }

    @Override
    public void createTableIfNotExists() {
        var sql = """
            CREATE TABLE IF NOT EXISTS hymmo_players (
                id VARCHAR(36) PRIMARY KEY,
                xp BIGINT NOT NULL,
                level INTEGER NOT NULL
            )
            """;

        try (
            Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement()
        ) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<PlayerEntity> get(String playerId) {
        var sql = """
            SELECT id, xp, level
            FROM hymmo_players
            WHERE id = ?
        """;

        return getOne(
                sql,
                PlayerMapper.MAPPER,
                ps -> ps.setString(1, playerId)
        );
    }

    public void create(String playerId, long xp, int level) {
        var sql = """
            INSERT INTO hymmo_players (id, xp)
            VALUES (?, ?, ?)
        """;

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, playerId);
            ps.setLong(2, xp);
            ps.setInt(3, level);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to create player record: " + playerId,
                    e
            );
        }
    }

    public void update(String playerId, long xp, int level) {
        var sql = """
            UPDATE hymmo_players
            SET xp = ?, level = ?
            WHERE id = ?
        """;

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setLong(1, xp);
            ps.setLong(2, level);
            ps.setString(3, playerId);

            int updated = ps.executeUpdate();
            if (updated == 0) {
                throw new IllegalStateException(
                        "Player not found for update: " + playerId
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to update player record: " + playerId,
                    e
            );
        }
    }
}
