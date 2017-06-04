package io.github.kewne.jackson.hal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class HalLinksDeserializer extends StdDeserializer<HalLinks> {

    HalLinksDeserializer() {
        super(HalLinks.class);
    }

    @Override
    public HalLinks deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        HalLinks.HalLinksBuilder builder = new HalLinks.HalLinksBuilder();
        while (p.nextToken() != JsonToken.END_OBJECT) {
            String rel = p.getCurrentName();
            HalRel halRel = ctxt.readValue(p, HalRel.class);
            builder.withRel(rel, halRel);
        }
        return builder.build();
    }
}
