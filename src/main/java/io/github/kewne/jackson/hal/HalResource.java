package io.github.kewne.jackson.hal;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * A resource represented as HAL.
 *
 * @param <T> the contained resource
 */
public class HalResource<T> {

    @JsonUnwrapped
    private final T resource;

    public HalResource(T resource) {
        this.resource = resource;
    }
}
