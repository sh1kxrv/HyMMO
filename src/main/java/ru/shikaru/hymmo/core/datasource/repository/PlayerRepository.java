package ru.shikaru.hymmo.core.datasource.repository;

import ru.shikaru.hymmo.core.api.Repository;
import ru.shikaru.hymmo.core.manager.ManagerStore;
import ru.shikaru.hymmo.manager.DataSourceManager;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class PlayerRepository extends Repository {
    public PlayerRepository(@Nonnull DataSource ds) {
        super(ds);
    }

    @Override
    public void createTableIfNotExists() {
        var dsManager = ManagerStore.getOrThrow(DataSourceManager.class);

        var sql = """
            CREATE TABLE IF NOT EXISTS hymmo_players (
                id VARCHAR(36) PRIMARY KEY,
                xp BIGINT NOT NULL
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
