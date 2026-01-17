package ru.shikaru.hymmo;

import com.hypixel.hytale.common.plugin.PluginManifest;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

import ru.shikaru.hymmo.api.IFormula;
import ru.shikaru.hymmo.formula.ExpFormula;
import ru.shikaru.hymmo.hytale.command.XpCommand;
import ru.shikaru.hymmo.hytale.component.PlayerPlacedBlockComponent;
import ru.shikaru.hymmo.hytale.component.PlayerXpComponent;
import ru.shikaru.hymmo.hytale.component.skills.WoodcuttingSkillComponent;
import ru.shikaru.hymmo.hytale.module.SkillModule;
import ru.shikaru.hymmo.hytale.module.XpModule;
import ru.shikaru.hymmo.hytale.system.BlockPlaceMarkSystem;
import ru.shikaru.hymmo.hytale.system.BreakBlockXPGainSystem;
import ru.shikaru.hymmo.hytale.system.KillXPGainSystem;
import ru.shikaru.hymmo.hytale.system.XPRegistrarSystem;

public class HyMMOPlugin extends JavaPlugin {
    private static HyMMOPlugin instance;

    @Nonnull
    public static final PluginManifest MANIFEST = PluginManifest.corePlugin(HyMMOPlugin.class).depends(SkillModule.class, XpModule.class).build();

    private ComponentType<EntityStore, PlayerXpComponent> playerXpDataComponent;
    private ComponentType<EntityStore, WoodcuttingSkillComponent> woodcuttingSkillComponent;
    private ComponentType<ChunkStore, PlayerPlacedBlockComponent> playerPlacedBlockComponent;
    private final IFormula levelFormula;

    public final HytaleLogger pluginLogger;

    public HyMMOPlugin(@Nonnull JavaPluginInit init) {
        super(init);
        instance = this;

        levelFormula = new ExpFormula(100, 1.7, 10000);
        pluginLogger = getLogger();
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
        this.getEntityStoreRegistry().registerSystem(new KillXPGainSystem());
        this.getEntityStoreRegistry().registerSystem(new BreakBlockXPGainSystem());
        this.getEntityStoreRegistry().registerSystem(new BlockPlaceMarkSystem());
    }

    private void registerComponents() {
        this.playerXpDataComponent = this.getEntityStoreRegistry().registerComponent(PlayerXpComponent.class, "PlayerXpComponent", PlayerXpComponent.CODEC);
        this.woodcuttingSkillComponent = this.getEntityStoreRegistry().registerComponent(WoodcuttingSkillComponent.class, "WoodcuttingSkillComponent", WoodcuttingSkillComponent.CODEC);
        this.playerPlacedBlockComponent = this.getChunkStoreRegistry().registerComponent(PlayerPlacedBlockComponent.class, "PlayerPlacedBlockComponent", PlayerPlacedBlockComponent.CODEC);
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

    public ComponentType<ChunkStore, PlayerPlacedBlockComponent> getPlayerPlacedBlockComponent(){
        return playerPlacedBlockComponent;
    }

    public ComponentType<EntityStore, WoodcuttingSkillComponent> getWoodcuttingSkillComponent(){
        return woodcuttingSkillComponent;
    }
}
