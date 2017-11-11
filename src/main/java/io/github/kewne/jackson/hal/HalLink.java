package io.github.kewne.jackson.hal;

import com.damnhandy.uri.template.UriTemplate;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import static com.damnhandy.uri.template.UriTemplate.fromTemplate;

/**
 * Maps a single link object in HAL.
 */
public class HalLink {

    @JsonProperty
    private final UriTemplate href;

    @JsonCreator
    private HalLink(@JsonProperty("href") UriTemplate href) {
        this.href = Objects.requireNonNull(href);
    }

    /**
     * Factory method for building a {@code HalLink}
     *
     * @param uri a string containing a URI or URI Template
     * @return a HAL link for the given URI
     */
    public static HalLink to(String uri) {
        return new HalLink(fromTemplate(uri));
    }

    /**
     * @return the "href" property in HAL links, which could be a URI template
     */
    public String getHref() {
        return href.getTemplate();
    }

    /**
     * @return {@code true} if the link is a URI template, {@code false} otherwise
     */
    public boolean isTemplated() {
        return href.expressionCount() > 0;
    }

}
