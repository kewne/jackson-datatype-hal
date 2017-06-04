package io.github.kewne.jackson.hal;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
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

    public static HalLinkBuilder builder(URI selfUri) {
        return new HalLinkBuilder(selfUri);
    }

    public static final class HalLinkBuilder {

        private final Map<String, URI> linkMap = new HashMap<>();

        private HalLinkBuilder(URI selfUri) {
            linkMap.put("self", selfUri);
        }

        public HalLinks build() {
            return new HalLinks(new HashMap<>(linkMap));
        }

        public HalLinkBuilder withRel(String rel, URI uri) {
            linkMap.put(rel, uri);
            return this;
        }
    }
}
