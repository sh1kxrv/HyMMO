package com.example.examplePlugin;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import javax.annotation.Nonnull;

public class ExamplePlugin extends JavaPlugin {

    private static ExamplePlugin INSTANCE;

    public ExamplePlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    public static ExamplePlugin getInstance() {
        return INSTANCE;
    }
}
