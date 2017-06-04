package io.github.kewne.jackson.hal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A resource represented as HAL.
 */
public class HalResource {

    @JsonProperty
    private final HalLinks _links;

    public HalResource() {
        this(null);
    }

    public HalResource(HalLinks links) {
        _links = links;
    }

    public HalRel getRel(String rel) {
        return _links.getRelMap().get(rel);
    }
}
