package ru.shikaru.hymmo;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import javax.annotation.Nonnull;

import ru.shikaru.hymmo.datasource.DataSourceFactory;

public class HyMMOPlugin extends JavaPlugin {
    private static HyMMOPlugin instance;

    public HyMMOPlugin(@Nonnull JavaPluginInit init) {
        super(init);
        instance = this;

        getLogger().at(Level.INFO).log("Creating table's in Database");

        var ds = DataSourceFactory.create("jdbc:sqlite:hymmo.db", 4);
        var sql = """
            CREATE TABLE IF NOT EXISTS hymmo_players (
                id VARCHAR(36) PRIMARY KEY,
                xp BIGINT NOT NULL
            )
            """;

        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        registerEvents();
    }

    private void registerEvents() {

    }


    public HyMMOPlugin getInstance(){
        return instance;
    }
}
