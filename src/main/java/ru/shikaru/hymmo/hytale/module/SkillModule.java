package ru.shikaru.hymmo.hytale.module;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import javax.annotation.Nonnull;

public final class SkillModule extends JavaPlugin {
    private static SkillModule instance;

    public SkillModule(@Nonnull JavaPluginInit init) {
        super(init);
        instance = this;
    }

    public static SkillModule get(){
        return instance;
    }

    @Override
    protected void setup(){

    }
}
