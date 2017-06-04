package io.github.kewne.jackson.hal;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

public final class HalLinks {

    @JsonProperty
    private final Map<String, URI> linkMap;

    private HalLinks(Map<String, URI> linkMap) {
        this.linkMap = linkMap;
    }

    public static HalLinks self(URI uri) {
        return new HalLinks(Collections.singletonMap("self", uri));
    }

    Map<String, URI> getLinkMap() {
        return linkMap;
    }
}
