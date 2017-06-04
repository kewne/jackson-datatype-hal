package io.github.kewne.jackson.hal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;

import static org.junit.Assert.assertEquals;

public class SerializationTests {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @BeforeClass
    public static void setUp() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new HalModule());
    }

    @Test
    public void shouldSerializeSimpleObjectWithoutLinks() throws IOException {
        HalResource<SimpleObject> resource = new HalResource<>(new SimpleObject());
        assertEquals(
                "{}",
                objectMapper.writeValueAsString(resource));
    }

    @Test
    public void shouldSerializeSimpleObjectWithLinks() throws IOException {
        HalResource<SimpleObject> resource = new HalResource<>(
                new SimpleObject(),
                HalLinks.self(URI.create("http://example.com")));
        assertEquals(
                "{\"_links\":{\"self\":{\"href\":\"http://example.com\"}}}",
                objectMapper.writeValueAsString(resource));
    }
}
