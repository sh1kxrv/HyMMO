package ru.shikaru.hymmo.core.datasource.repository;

import ru.shikaru.hymmo.core.api.IRepository;
import ru.shikaru.hymmo.core.manager.ManagerStore;
import ru.shikaru.hymmo.manager.DataSourceManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PlayerSkillRepository implements IRepository {
    @Override
    public void createTableIfNotExists() {
        var dsManager = ManagerStore.getOrThrow(DataSourceManager.class);

        var sql = """
            CREATE TABLE IF NOT EXISTS player_skills (
                id INTEGER PRIMARY KEY,
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
