package io.github.kewne.jackson.hal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class HalLinksSerializer extends StdSerializer<HalLinks> {

    public HalLinksSerializer() {
        super(HalLinks.class);
    }

    @Override
    public void serialize(HalLinks value,
                          JsonGenerator gen,
                          SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        for (Map.Entry<String, URI> entry : value.getLinkMap().entrySet()) {
            gen.writeFieldName(entry.getKey());
            gen.writeStartObject();
            gen.writeFieldName("href");
            gen.writeObject(entry.getValue());
            gen.writeEndObject();
        }
        gen.writeEndObject();
    }
}
