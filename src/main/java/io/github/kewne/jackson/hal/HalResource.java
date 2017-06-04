package io.github.kewne.jackson.hal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * A resource represented as HAL.
 *
 * @param <T> the contained resource
 */
public class HalResource<T> {

    @JsonUnwrapped
    private final T resource;

    @JsonProperty
    private final HalLinks _links;

    public HalResource(T resource) {
        this(resource, null);
    }

    public HalResource(T resource, HalLinks links) {
        this.resource = resource;
        _links = links;
    }
}
