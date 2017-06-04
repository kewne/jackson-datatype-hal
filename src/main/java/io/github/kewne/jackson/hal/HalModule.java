package io.github.kewne.jackson.hal;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class HalModule extends SimpleModule {

    public HalModule() {
        super("hal",
                new Version(0, 1, 0,
                        null, "io.github.kewne", "jackson-datatype-hal"));
        addSerializer(HalLinks.class, new HalLinksSerializer());
        addSerializer(HalRel.class, new HalRelSerializer());
        addDeserializer(HalLinks.class, new HalLinksDeserializer());
        addDeserializer(HalRel.class, new HalRelDeserializer());
    }
}
