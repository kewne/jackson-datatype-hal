package io.github.kewne.jackson.hal;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public static HalLinks self(String selfRel) {
        return new HalLinks(Collections.singletonMap("self", single(selfRel)));
    }

    public static HalLinksBuilder builder(String selfUri) {
        return new HalLinksBuilder(single(selfUri));
    }

    public static HalLinksBuilder builder(HalRel selfUri) {
        return new HalLinksBuilder(selfUri);
    }

    Map<String, HalRel> getRelMap() {
        return relMap;
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

        public HalLinksBuilder withRel(String rel, String uri) {
            linkMap.put(rel, HalRel.single(uri));
            return this;
        }

        public HalLinksBuilder withRel(String rel, HalRel uri) {
            linkMap.put(rel, uri);
            return this;
        }
    }
}
