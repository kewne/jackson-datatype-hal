package io.github.kewne.jackson.hal;

import com.damnhandy.uri.template.jackson.datatype.UriTemplateModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

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
    public void relWithType() throws IOException {
        HalResource result =
                objectMapper.readValue("{\"_links\":{" +
                                "\"self\":{\"href\":\"http://example.com/data\",\"type\":\"application/hal+json\"}" +
                                "}}",
                        HalResource.class);

        HalLink link = result.getRel("self").getSingleLink();
        assertEquals("application/hal+json", link.getType());
    }
}
