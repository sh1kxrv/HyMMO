package ru.shikaru.hymmo.core.api;

import ru.shikaru.hymmo.core.util.RowMapper;
import ru.shikaru.hymmo.core.util.StatementBinder;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public abstract class Repository {
    protected DataSource dataSource;

    public Repository(@Nonnull DataSource ds) {
        this.dataSource = ds;
    }

    public abstract void createTableIfNotExists();

    protected <T> Optional<T> getOne(
            String sql,
            RowMapper<T> mapper,
            StatementBinder binder
    ) {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            binder.bind(ps);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                return Optional.of(mapper.map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
