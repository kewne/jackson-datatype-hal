package io.github.kewne.jackson.hal;

import com.damnhandy.uri.template.jackson.datatype.UriTemplateModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DeserializationTests {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @BeforeClass
    public static void setUp() {
        objectMapper.registerModule(new HalModule());
        objectMapper.registerModule(new UriTemplateModule());
    }

    @Test
    public void singleRel() throws IOException {
        HalResource result =
                objectMapper.readValue("{\"_links\":{\"self\":{\"href\":\"http://example.com\"}}}",
                        HalResource.class);

        HalLink selfLink = result.getRel("self").getSingleLink();
        assertEquals("http://example.com", selfLink.getHref());
        assertFalse(selfLink.isTemplated());
    }

    @Test
    public void singleRelMultipleLinks() throws IOException {
        HalResource result =
                objectMapper.readValue("{\"_links\":{\"assoc\":[" +
                                "{\"href\":\"http://example.com\"}," +
                                "{\"href\":\"http://example.com\"}" +
                                "]}}",
                        HalResource.class);

        List<HalLink> links = result.getRel("assoc").getMultipleLinks();
        assertEquals(2, links.size());
        assertFalse(links.stream()
                .allMatch(HalLink::isTemplated));
    }

    @Test
    public void relWithTemplatedLink() throws IOException {
        HalResource result =
                objectMapper.readValue("{\"_links\":{\"find-by-id\":{\"href\":\"http://example.com/data/{id}\"}}}",
                        HalResource.class);

        HalLink link = result.getRel("find-by-id").getSingleLink();
        assertEquals("http://example.com/data/{id}", link.getHref());
        assertTrue(link.isTemplated());
    }

    @Test
    public void linkWithType() throws IOException {
        HalResource result =
                objectMapper.readValue("{\"_links\":{" +
                                "\"self\":{\"href\":\"http://example.com/data\",\"type\":\"application/hal+json\"}" +
                                "}}",
                        HalResource.class);

        HalLink link = result.getRel("self").getSingleLink();
        assertEquals("application/hal+json", link.getType());
    }

    @Test
    public void deprecatedLink() throws IOException {
        HalResource result =
                objectMapper.readValue("{\"_links\":{" +
                                "\"self\":{\"href\":\"http://example.com/data\",\"deprecation\":\"http://example.com/deprecation\"}" +
                                "}}",
                        HalResource.class);

        HalLink link = result.getRel("self").getSingleLink();
        assertTrue(link.isDeprecated());
        assertEquals(URI.create("http://example.com/deprecation"), link.getDeprecationUri());
    }

    @Test
    public void linkWithProfile() throws IOException {
        HalResource result =
                objectMapper.readValue("{\"_links\":{" +
                                "\"self\":{\"href\":\"http://example.com/data\",\"profile\":\"http://example.com/profile\"}" +
                                "}}",
                        HalResource.class);

        HalLink link = result.getRel("self").getSingleLink();
        assertEquals(URI.create("http://example.com/profile"), link.getProfileUri());
    }


    @Test
    public void linkWithName() throws IOException {
        HalResource result =
                objectMapper.readValue("{\"_links\":{" +
                                "\"assoc\":{\"href\":\"http://example.com/data\",\"name\":\"namedLink\"}" +
                                "}}",
                        HalResource.class);

        Optional<HalLink> existingLink = result.getRel("assoc").getNamedLink("namedLink");
        assertTrue(existingLink.isPresent());
        assertEquals("http://example.com/data", existingLink.get().getHref());
        assertEquals("namedLink", existingLink.get().getName());
    }

    @Test
    public void linkWithHrefLang() throws IOException {
        HalResource result =
                objectMapper.readValue("{\"_links\":{" +
                                "\"assoc\":{\"href\":\"http://example.com/data\",\"hreflang\":\"pt\"}" +
                                "}}",
                        HalResource.class);

        HalLink existingLink = result.getRel("assoc").getSingleLink();
        assertEquals("http://example.com/data", existingLink.getHref());
        assertEquals("pt", existingLink.getHreflang());

    }
}
