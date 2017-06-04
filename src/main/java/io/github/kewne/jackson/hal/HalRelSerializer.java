package io.github.kewne.jackson.hal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.List;

public class HalRelSerializer extends StdSerializer<HalRel> {

    HalRelSerializer() {
        super(HalRel.class);
    }

    @Override
    public void serialize(HalRel value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        List<HalLink> links = value.getLinks();
        if (links.size() == 1) {
            gen.writeObject(links.get(0));
        } else {
            gen.writeStartArray();
            for (HalLink link : links) {
                gen.writeObject(link);
            }
            gen.writeEndArray();
        }
    }
}
