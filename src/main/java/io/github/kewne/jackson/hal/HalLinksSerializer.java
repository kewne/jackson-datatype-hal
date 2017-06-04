package io.github.kewne.jackson.hal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
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
        for (Map.Entry<String, HalRel> entry : value.getRelMap().entrySet()) {
            gen.writeFieldName(entry.getKey());
            gen.writeObject(entry.getValue());
        }
        gen.writeEndObject();
    }
}
