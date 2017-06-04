package io.github.kewne.jackson.hal;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.Objects;

public class HalLink {

    @JsonProperty
    private final URI href;

    HalLink(URI href) {
        this.href = Objects.requireNonNull(href);
    }

    public static HalLink to(URI uri) {
        return new HalLink(uri);
    }
}
