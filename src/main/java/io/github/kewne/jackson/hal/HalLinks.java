package io.github.kewne.jackson.hal;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static io.github.kewne.jackson.hal.HalRel.single;

public final class HalLinks {

    @JsonProperty
    private final Map<String, HalRel> relMap;

    private HalLinks(Map<String, HalRel> relMap) {
        this.relMap = relMap;
    }

    public static HalLinks self(URI selfRel) {
        return new HalLinks(Collections.singletonMap("self", single(selfRel)));
    }

    Map<String, HalRel> getRelMap() {
        return relMap;
    }

    public static HalLinksBuilder builder(URI selfUri) {
        return new HalLinksBuilder(single(selfUri));
    }

    public static HalLinksBuilder builder(HalRel selfUri) {
        return new HalLinksBuilder(selfUri);
    }

    public static final class HalLinksBuilder {

        private final Map<String, HalRel> linkMap = new HashMap<>();

        private HalLinksBuilder(HalRel selfUri) {
            linkMap.put("self", selfUri);
        }

        HalLinksBuilder() {
        }

        public HalLinks build() {
            return new HalLinks(new HashMap<>(linkMap));
        }

        public HalLinksBuilder withRel(String rel, URI uri) {
            linkMap.put(rel, HalRel.single(uri));
            return this;
        }

        public HalLinksBuilder withRel(String rel, HalRel uri) {
            linkMap.put(rel, uri);
            return this;
        }
    }
}
