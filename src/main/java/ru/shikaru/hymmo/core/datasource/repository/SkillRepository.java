package ru.shikaru.hymmo.core.datasource.repository;

import ru.shikaru.hymmo.core.api.Repository;
import ru.shikaru.hymmo.core.datasource.registry.SkillRegistry;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public final class SkillRepository extends Repository {
    public SkillRepository(@Nonnull DataSource ds) {
        super(ds);
    }

    @Override
    public void createTableIfNotExists() {
        var sql = """
            CREATE TABLE IF NOT EXISTS skills (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                code TEXT NOT NULL UNIQUE,
                name TEXT NOT NULL
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

    public void create(int id, String code, String name) {
        var sql = """
            INSERT INTO skills (id, code, name)
            VALUES (?, ?, ?)
        """;

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ps.setString(2, code);
            ps.setString(3, name);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to create skill: id=" + id + ", code=" + code,
                    e
            );
        }
    }

    public void update(int id, String code, String name) {
        var sql = """
            UPDATE skills
            SET code = ?, name = ?
            WHERE id = ?
        """;

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, code);
            ps.setString(2, name);
            ps.setInt(3, id);

            if (ps.executeUpdate() == 0) {
                throw new IllegalStateException(
                        "Skill not found: id=" + id
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to update skill: id=" + id,
                    e
            );
        }
    }

    public void bootstrapFromRegistry() {
        var sql = """
            INSERT INTO skills (code, name)
            VALUES (?, ?)
            ON CONFLICT (code)
            DO UPDATE SET name = EXCLUDED.name
        """;

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            for (SkillRegistry skill : SkillRegistry.values()) {
                ps.setString(1, skill.code);
                ps.setString(2, skill.name);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to bootstrap skills", e);
        }
    }
}
