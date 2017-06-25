package io.github.kewne.jackson.hal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;

import static org.junit.Assert.assertEquals;

public class DeserializationTests {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @BeforeClass
    public static void setUp() {
        objectMapper.registerModule(new HalModule());
    }

    @Test
    public void singleRel() throws IOException {
        HalResource result =
                objectMapper.readValue("{\"_links\":{\"self\":{\"href\":\"http://example.com\"}}}",
                        new TypeReference<HalResource>() {
                        });

        assertEquals(URI.create("http://example.com"), result.getRel("self").getSingleLink().getHref());
    }

    @Test
    public void singleRelMultipleLinsk() throws IOException {
        HalResource result =
                objectMapper.readValue("{\"_links\":{\"assoc\":[" +
                                "{\"href\":\"http://example.com\"}," +
                                "{\"href\":\"http://example.com\"}" +
                                "]}}",
                        new TypeReference<HalResource>() {
                        });

        assertEquals(2, result.getRel("assoc").getMultipleLinks().size());
    }
}
