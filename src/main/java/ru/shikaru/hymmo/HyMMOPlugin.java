package ru.shikaru.hymmo;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

import ru.shikaru.hymmo.api.IFormula;
import ru.shikaru.hymmo.formula.ExpFormula;
import ru.shikaru.hymmo.hytale.command.XpCommand;
import ru.shikaru.hymmo.hytale.component.PlayerXpComponent;
import ru.shikaru.hymmo.hytale.system.XPGainSystem;
import ru.shikaru.hymmo.hytale.system.XPRegistrarSystem;

public class HyMMOPlugin extends JavaPlugin {
    private static HyMMOPlugin instance;

    private ComponentType<EntityStore, PlayerXpComponent> playerXpDataComponent;
    private final IFormula levelFormula;

    public HyMMOPlugin(@Nonnull JavaPluginInit init) {
        super(init);
        instance = this;

        levelFormula = new ExpFormula(100, 1.7, 10000);
    }

    @Override
    protected void setup(){
        registerCommands();
        registerComponents();
        registerSystems();
        registerEvents();
    }

    private void registerCommands() {
        this.getCommandRegistry().registerCommand(new XpCommand());
    }

    private void registerEvents() {

    }

    private void registerSystems() {
        this.getEntityStoreRegistry().registerSystem(new XPRegistrarSystem());
        this.getEntityStoreRegistry().registerSystem(new XPGainSystem());
    }

    private void registerComponents() {
        this.playerXpDataComponent = this.getEntityStoreRegistry().registerComponent(PlayerXpComponent.class, "PlayerXpComponent", PlayerXpComponent.CODEC);
    }

    public static HyMMOPlugin get(){
        return instance;
    }

    public IFormula getLevelFormula() {
        return this.levelFormula;
    }

    public ComponentType<EntityStore, PlayerXpComponent> getPlayerXpDataComponent() {
        return playerXpDataComponent;
    }
}
