package ru.shikaru.hymmo.manager;

import ru.shikaru.hymmo.api.manager.IDataSourceManager;
import ru.shikaru.hymmo.core.datasource.DataSourceFactory;
import ru.shikaru.hymmo.core.datasource.repository.PlayerRepository;
import ru.shikaru.hymmo.core.datasource.repository.PlayerSkillRepository;
import ru.shikaru.hymmo.core.datasource.repository.SkillRepository;

import javax.sql.DataSource;

public class DataSourceManager implements IDataSourceManager {
    public DataSource dataSource;

    public PlayerRepository playerRepository;
    public PlayerSkillRepository playerSkillRepository;
    public SkillRepository skillRepository;

    public DataSourceManager(){
        dataSource = DataSourceFactory.create("jdbc:sqlite:hymmo.db", 5);
    }

    @Override
    public void init() {
        playerRepository = new PlayerRepository(this.dataSource);
        playerSkillRepository = new PlayerSkillRepository(this.dataSource);
        skillRepository = new SkillRepository(this.dataSource);

        playerRepository.createTableIfNotExists();
        skillRepository.createTableIfNotExists();
        playerSkillRepository.createTableIfNotExists();
    }
}
