package ru.shikaru.hymmo.core.datasource.repository;

import ru.shikaru.hymmo.core.api.Repository;
import ru.shikaru.hymmo.core.manager.ManagerStore;
import ru.shikaru.hymmo.manager.DataSourceManager;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PlayerSkillRepository extends Repository {
    public PlayerSkillRepository(@Nonnull DataSource ds) {
        super(ds);
    }

    @Override
    public void createTableIfNotExists() {
        var dsManager = ManagerStore.getOrThrow(DataSourceManager.class);

        var sql = """
            CREATE TABLE IF NOT EXISTS player_skills (
                player_id  VARCHAR(36) NOT NULL,
                skill_id   INTEGER NOT NULL,
                xp         BIGINT NOT NULL,
        
                PRIMARY KEY (player_id, skill_id),
        
                FOREIGN KEY (player_id) REFERENCES hymmo_players(id),
                FOREIGN KEY (skill_id) REFERENCES skills(id)
            )
            """;

        try (
                Connection conn = dsManager.dataSource.getConnection();
                Statement stmt = conn.createStatement()
        ) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
