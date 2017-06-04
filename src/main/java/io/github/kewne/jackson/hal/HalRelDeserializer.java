package io.github.kewne.jackson.hal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HalRelDeserializer extends StdDeserializer<HalRel> {

    HalRelDeserializer() {
        super(HalRel.class);
    }

    @Override
    public HalRel deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonToken startToken = p.nextToken();
        if (startToken == JsonToken.START_ARRAY) {
            List<HalLink> links = new ArrayList<>();
            while (p.currentToken() != JsonToken.END_ARRAY) {
                links.add(ctxt.readValue(p, HalLink.class));
            }
            return HalRel.multiple(links);
        } else if (startToken == JsonToken.START_OBJECT) {
            return new HalRel(ctxt.readValue(p, HalLink.class));
        } else {
            ctxt.handleUnexpectedToken(HalRel.class, p);
            return null;
        }
    }
}
