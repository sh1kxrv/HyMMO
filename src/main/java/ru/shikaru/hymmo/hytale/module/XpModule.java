package ru.shikaru.hymmo.hytale.module;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import javax.annotation.Nonnull;

public final class XpModule extends JavaPlugin {
    private static XpModule instance;

    public static XpModule get(){
        return instance;
    }

    public XpModule(@Nonnull JavaPluginInit init) {
        super(init);
        instance = this;
    }

    @Override
    protected void setup(){

    }
}
