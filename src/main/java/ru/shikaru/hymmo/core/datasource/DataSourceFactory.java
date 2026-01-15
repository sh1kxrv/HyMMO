package ru.shikaru.hymmo.core.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import ru.shikaru.hymmo.exception.DataSourceConfigurationException;

// https://github.com/AzureDoom/LevelingCore
public final class DataSourceFactory {

    private DataSourceFactory() {}

    public static HikariDataSource create(
            String jdbcUrl,
            int maxPoolSize
    ) {
        validateBasicConfig(jdbcUrl,  maxPoolSize);

        var cfg = new HikariConfig();
        cfg.setJdbcUrl(jdbcUrl);

        cfg.setDriverClassName(driverClassNameFor(jdbcUrl));

        cfg.setMaximumPoolSize(maxPoolSize);
        cfg.setMinimumIdle(1);

        cfg.setInitializationFailTimeout(10_000);
        cfg.setConnectionTimeout(10_000);
        cfg.setValidationTimeout(5_000);

        HikariDataSource ds;
        try {
            ds = new HikariDataSource(cfg);
        } catch (RuntimeException e) {
            throw DataSourceConfigurationException.from("Failed to initialize connection pool", jdbcUrl, e);
        }

        try (var c = ds.getConnection()) {
            c.isValid(2);
        } catch (Exception e) {
            try {
                ds.close();
            } catch (Exception ignored) {}
            throw DataSourceConfigurationException.from("Database connection test failed", jdbcUrl, e);
        }

        return ds;
    }


    private static void validateBasicConfig(String jdbcUrl, int maxPoolSize) {
        if (jdbcUrl == null || jdbcUrl.isBlank()) {
            throw new IllegalArgumentException(
                    "jdbcUrl is missing/blank (example: jdbc:sqlite:db.db)"
            );
        }

        if (!jdbcUrl.toLowerCase().startsWith("jdbc:")) {
            throw new IllegalArgumentException(
                    "jdbcUrl must start with 'jdbc:' (got: " + jdbcUrl + ")"
            );
        }

        if (maxPoolSize < 1) {
            throw new IllegalArgumentException(
                    "maxPoolSize must be >= 1 (got: " + maxPoolSize + ")"
            );
        }
    }


    private static String driverClassNameFor(String jdbcUrl) {
        String url = jdbcUrl.toLowerCase();

        if (url.startsWith("jdbc:sqlite:"))
            return "org.sqlite.JDBC";
        if (url.startsWith("jdbc:h2:"))
            return "org.h2.Driver";

        throw new IllegalArgumentException(
                "Unsupported jdbcUrl scheme. Supported: sqlite, h2. Got: " + jdbcUrl
        );
    }
}
