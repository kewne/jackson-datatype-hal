package io.github.kewne.jackson.hal;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * A resource represented as HAL.
 *
 * @param <T> the contained resource
 */
public class WrappedHalResource<T> extends HalResource {

    @JsonUnwrapped
    private final T resource;

    public WrappedHalResource(T resource) {
        this.resource = resource;
    }

    public WrappedHalResource(T resource, HalLinks links) {
        super(links);
        this.resource = resource;
    }
}
