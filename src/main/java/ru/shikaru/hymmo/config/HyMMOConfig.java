package ru.shikaru.hymmo.config;

import com.hypixel.hytale.codec.builder.BuilderCodec;

public class HyMMOConfig {
    public static final BuilderCodec<HyMMOConfig> CODEC = BuilderCodec.builder(HyMMOConfig.class, HyMMOConfig::new).build();
}
