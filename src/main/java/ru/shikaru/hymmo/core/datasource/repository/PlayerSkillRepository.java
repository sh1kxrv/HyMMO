package ru.shikaru.hymmo.core.datasource.repository;

import ru.shikaru.hymmo.core.api.Repository;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public final class PlayerSkillRepository extends Repository {
    public PlayerSkillRepository(@Nonnull DataSource ds) {
        super(ds);
    }

    @Override
    public void createTableIfNotExists() {
        var sql = """
            CREATE TABLE IF NOT EXISTS player_skills (
                player_id  VARCHAR(36) NOT NULL,
                skill_id   INTEGER NOT NULL,
                xp         BIGINT NOT NULL,
                level      INTEGER NOT NULL,
        
                PRIMARY KEY (player_id, skill_id),
        
                FOREIGN KEY (player_id) REFERENCES hymmo_players(id),
                FOREIGN KEY (skill_id) REFERENCES skills(id)
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

    public void create(String playerId, int skillId, long xp, int level) {
        var sql = """
        INSERT INTO player_skills (player_id, skill_id, xp, level)
        VALUES (?, ?, ?, ?)
        """;

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, playerId);
            ps.setInt(2, skillId);
            ps.setLong(3, xp);
            ps.setInt(4, level);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to create player skill: playerId="
                            + playerId + ", skillId=" + skillId,
                    e
            );
        }
    }

    public void update(String playerId, int skillId, long xp, int level) {
        var sql = """
        UPDATE player_skills
        SET xp = ?, level = ?
        WHERE player_id = ?
          AND skill_id = ?
        """;

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setLong(1, xp);
            ps.setInt(2, level);
            ps.setString(3, playerId);
            ps.setInt(4, skillId);

            int updated = ps.executeUpdate();
            if (updated == 0) {
                throw new IllegalStateException(
                        "Player skill not found: playerId="
                                + playerId + ", skillId=" + skillId
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to update player skill: playerId="
                            + playerId + ", skillId=" + skillId,
                    e
            );
        }
    }
}
