package ru.shikaru.hymmo.core.api;

import javax.annotation.Nonnull;
import javax.sql.DataSource;

public abstract class Repository {
    protected DataSource dataSource;

    public Repository(@Nonnull DataSource ds) {
        this.dataSource = ds;
    }
    public abstract void createTableIfNotExists();
}
