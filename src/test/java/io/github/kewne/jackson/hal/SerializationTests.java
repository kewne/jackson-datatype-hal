package io.github.kewne.jackson.hal;

import com.damnhandy.uri.template.jackson.datatype.UriTemplateModule;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
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
        objectMapper.registerModule(new UriTemplateModule());
    }

    @Test
    public void simpleObjectWithoutLinks() throws IOException {
        HalResource resource = new WrappedHalResource<>(new SimpleObject());
        assertEquals(
                "{}",
                objectMapper.writeValueAsString(resource));
    }

    @Test
    public void simpleObjectWithSingleRel() throws IOException {
        HalResource resource = new WrappedHalResource<>(
                new SimpleObject(),
                HalLinks.self("http://example.com"));
        assertEquals(
                "{\"_links\":{\"self\":{\"href\":\"http://example.com\",\"templated\":false}}}",
                objectMapper.writeValueAsString(resource));
    }

    @Test
    public void simpleObjectWithMultipleRels() throws IOException {
        HalResource resource = new WrappedHalResource<>(
                new SimpleObject(),
                builder("http://example.com")
                        .withRel("other", "http://example.com")
                        .build());
        assertEquals(
                "{\"_links\":{" +
                        "\"self\":{\"href\":\"http://example.com\",\"templated\":false}," +
                        "\"other\":{\"href\":\"http://example.com\",\"templated\":false}" +
                        "}}",
                objectMapper.writeValueAsString(resource));
    }

    @Test
    public void simpleObjectWithMultipleLinksForSingleRel() throws IOException {
        HalResource resource = new WrappedHalResource<>(
                new SimpleObject(),
                builder(single("http://example.com"))
                        .withRel("other",
                                multiple(
                                        Arrays.asList(
                                                to("http://example.com"),
                                                to("http://example.com"))))
                        .build());
        assertEquals(
                "{\"_links\":{" +
                        "\"self\":{\"href\":\"http://example.com\",\"templated\":false}," +
                        "\"other\":[" +
                        "{\"href\":\"http://example.com\",\"templated\":false}," +
                        "{\"href\":\"http://example.com\",\"templated\":false}" +
                        "]" +
                        "}}",
                objectMapper.writeValueAsString(resource));
    }
}
