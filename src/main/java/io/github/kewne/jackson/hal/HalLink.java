package io.github.kewne.jackson.hal;

import com.damnhandy.uri.template.UriTemplate;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Maps a single link object in HAL.
 */
public final class HalLink {

    private final UriTemplate href;

    private final String type;

    private final URI deprecationUri;
    private final URI profileUri;

    @JsonCreator
    private HalLink(@JsonProperty("href") String href,
                    @JsonProperty("type") String type,
                    @JsonProperty("deprecation") URI deprecationUri,
                    @JsonProperty("profile") URI profileUri) {
        this.href = UriTemplate.fromTemplate(Objects.requireNonNull(href));
        this.type = type;
        this.deprecationUri = deprecationUri;
        this.profileUri = profileUri;
    }

    /**
     * The same as {@link #linkTo(String, Consumer)} with a no-op consumer.
     *
     * @param href the href to set in the link
     * @return a link to the given href
     */
    public static HalLink linkTo(String href) {
        return linkTo(href, spec -> {
        });

    }

    /**
     * Creates a link conforming to a link specification.
     *
     * @param href        the href to set in the link
     * @param specBuilder a consumer used to customize the specification of the link to build.
     *                    The specification passed in has all optional fields set to {@code null}.
     * @return a link to the given href obeying the specification
     */
    public static HalLink linkTo(String href, Consumer<HalLinkSpecification> specBuilder) {
        HalLinkSpecification spec = new HalLinkSpecification();
        specBuilder.accept(spec);
        return spec.build(href);
    }

    /**
     * @return the "href" property in HAL links, which could be a URI template
     */
    @JsonProperty
    public String getHref() {
        return href.getTemplate();
    }

    /**
     * @return {@code true} if the link is a URI template, {@code false} otherwise
     */
    @JsonProperty
    public boolean isTemplated() {
        return href.expressionCount() > 0;
    }

    /**
     * @return the
     */
    @JsonProperty
    public String getType() {
        return type;
    }

    @JsonIgnore
    public boolean isDeprecated() {
        return deprecationUri != null;
    }

    @JsonProperty("deprecation")
    public URI getDeprecationUri() {
        return deprecationUri;
    }

    @JsonProperty("profile")
    public URI getProfileUri() {
        return profileUri;
    }

    public static final class HalLinkSpecification {

        private String type;

        private URI profileUri;

        private URI deprecationUri;

        private HalLinkSpecification() {

        }

        /**
         * Sets the type of link produced by the specification.
         *
         * @param type the type to set
         * @return this specification
         */
        public HalLinkSpecification type(String type) {
            this.type = type;
            return this;
        }

        /**
         * Sets the link as deprecated.
         *
         * @param deprecationUri a URI for a resource describing the reason for deprecation
         * @return this specification
         */
        public HalLinkSpecification deprecated(URI deprecationUri) {
            this.deprecationUri = deprecationUri;
            return this;
        }

        private HalLink build(String href) {
            return new HalLink(href, type, deprecationUri, profileUri);
        }

        public HalLinkSpecification profile(URI profileUri) {
            this.profileUri = profileUri;
            return this;
        }
    }
}
