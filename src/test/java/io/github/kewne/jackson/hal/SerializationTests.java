package io.github.kewne.jackson.hal;

import com.damnhandy.uri.template.jackson.datatype.UriTemplateModule;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import static io.github.kewne.jackson.hal.HalLink.linkTo;
import static io.github.kewne.jackson.hal.HalLinks.builder;
import static io.github.kewne.jackson.hal.HalRel.multiple;
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
                builder("http://example.com")
                        .withRel("other",
                                multiple(
                                        Arrays.asList(
                                                linkTo("http://example.com"),
                                                linkTo("http://example.com"))))
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

    @Test
    public void linkType() throws JsonProcessingException {
        HalResource resource = new HalResource(
                HalLinks.singleRel("assoc",
                        linkTo("http://example.com/test",
                                spec -> spec.type("application/hal+json"))));
        assertEquals(
                "{\"_links\":{" +
                        "\"assoc\":{\"href\":\"http://example.com/test\",\"type\":\"application/hal+json\",\"templated\":false}" +
                        "}}",
                objectMapper.writeValueAsString(resource));
    }

    @Test
    public void deprecatedLink() throws JsonProcessingException {
        HalResource resource = new HalResource(
                HalLinks.singleRel("assoc",
                        linkTo("http://example.com/test",
                                spec -> spec.deprecated(URI.create("http://example.com/deprecation")))));
        assertEquals(
                "{\"_links\":{" +
                        "\"assoc\":{\"href\":\"http://example.com/test\",\"deprecation\":\"http://example.com/deprecation\",\"templated\":false}" +
                        "}}",
                objectMapper.writeValueAsString(resource));
    }

    @Test
    public void linkWithProfile() throws JsonProcessingException {
        HalResource resource = new HalResource(
                HalLinks.singleRel("assoc",
                        linkTo("http://example.com/test",
                                spec -> spec.profile(URI.create("http://example.com/profile")))));
        assertEquals(
                "{\"_links\":{" +
                        "\"assoc\":{\"href\":\"http://example.com/test\",\"profile\":\"http://example.com/profile\",\"templated\":false}" +
                        "}}",
                objectMapper.writeValueAsString(resource));
    }

    @Test
    public void namedLink() throws JsonProcessingException {
        HalResource resource = new HalResource(
                HalLinks.singleRel("assoc",
                        linkTo("http://example.com/test",
                                spec -> spec.named("namedLink"))));
        assertEquals(
                "{\"_links\":{" +
                        "\"assoc\":{\"href\":\"http://example.com/test\",\"name\":\"namedLink\",\"templated\":false}" +
                        "}}",
                objectMapper.writeValueAsString(resource));
    }

}
