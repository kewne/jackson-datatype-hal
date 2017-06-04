package io.github.kewne.jackson.hal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import static io.github.kewne.jackson.hal.HalLink.to;
import static io.github.kewne.jackson.hal.HalLinks.builder;
import static io.github.kewne.jackson.hal.HalRel.multiple;
import static io.github.kewne.jackson.hal.HalRel.single;
import static org.junit.Assert.assertEquals;

public class SerializationTests {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @BeforeClass
    public static void setUp() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new HalModule());
    }

    @Test
    public void simpleObjectWithoutLinks() throws IOException {
        HalResource<SimpleObject> resource = new HalResource<>(new SimpleObject());
        assertEquals(
                "{}",
                objectMapper.writeValueAsString(resource));
    }

    @Test
    public void simpleObjectWithSingleRel() throws IOException {
        HalResource<SimpleObject> resource = new HalResource<>(
                new SimpleObject(),
                HalLinks.self(URI.create("http://example.com")));
        assertEquals(
                "{\"_links\":{\"self\":{\"href\":\"http://example.com\"}}}",
                objectMapper.writeValueAsString(resource));
    }

    @Test
    public void simpleObjectWithMultipleRels() throws IOException {
        HalResource<SimpleObject> resource = new HalResource<>(
                new SimpleObject(),
                builder(URI.create("http://example.com"))
                        .withRel("other", URI.create("http://example.com"))
                        .build());
        assertEquals(
                "{\"_links\":{" +
                        "\"self\":{\"href\":\"http://example.com\"}," +
                        "\"other\":{\"href\":\"http://example.com\"}" +
                        "}}",
                objectMapper.writeValueAsString(resource));
    }

    @Test
    public void simpleObjectWithMultipleLinksForSingleRel() throws IOException {
        HalResource<SimpleObject> resource = new HalResource<>(
                new SimpleObject(),
                builder(single(URI.create("http://example.com")))
                        .withRel("other",
                                multiple(
                                        Arrays.asList(
                                                to(URI.create("http://example.com")),
                                                to(URI.create("http://example.com")))))
                        .build());
        assertEquals(
                "{\"_links\":{" +
                        "\"self\":{\"href\":\"http://example.com\"}," +
                        "\"other\":[" +
                        "{\"href\":\"http://example.com\"}," +
                        "{\"href\":\"http://example.com\"}" +
                        "]" +
                        "}}",
                objectMapper.writeValueAsString(resource));
    }
}
