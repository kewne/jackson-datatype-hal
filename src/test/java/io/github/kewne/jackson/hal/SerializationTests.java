package io.github.kewne.jackson.hal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SerializationTests {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @BeforeClass
    public static void setUp() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Test
    public void shouldSerializeSimpleObjectWithoutLinks() throws IOException {
        HalResource<SimpleObject> resource = new HalResource<>(new SimpleObject());
        assertEquals(
                "{}",
                objectMapper.writeValueAsString(resource));
    }
}
