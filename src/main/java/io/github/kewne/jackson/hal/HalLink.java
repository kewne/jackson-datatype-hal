package io.github.kewne.jackson.hal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.Objects;

public class HalLink {

    @JsonProperty
    private final URI href;

    @JsonCreator
    HalLink(@JsonProperty("href") URI href) {
        this.href = Objects.requireNonNull(href);
    }

    public static HalLink to(URI uri) {
        return new HalLink(uri);
    }

    public URI getHref() {
        return href;
    }
}
