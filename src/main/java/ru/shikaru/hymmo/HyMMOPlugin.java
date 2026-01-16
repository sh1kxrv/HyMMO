package ru.shikaru.hymmo;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import ru.shikaru.hymmo.core.manager.ManagerStore;
import ru.shikaru.hymmo.manager.DataSourceManager;
import ru.shikaru.hymmo.manager.PlayerManager;

import javax.annotation.Nonnull;
import java.util.logging.Level;

public class HyMMOPlugin extends JavaPlugin {
    private static HyMMOPlugin instance;

    public HyMMOPlugin(@Nonnull JavaPluginInit init) {
        super(init);
        instance = this;

        registerManagers();
        registerEvents();
    }

    private void registerEvents() {

    }

    private void registerManagers() {
        getLogger().at(Level.INFO).log("Registering managers..");

        var dataSourceManager = new DataSourceManager();
        var playerManager = new PlayerManager();

        dataSourceManager.init();

        ManagerStore.add(dataSourceManager, playerManager);

        getLogger().at(Level.INFO).log("Manager's is setting up");
    }

    public HyMMOPlugin getInstance(){
        return instance;
    }
}
