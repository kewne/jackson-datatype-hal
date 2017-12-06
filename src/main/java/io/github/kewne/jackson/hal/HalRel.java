package io.github.kewne.jackson.hal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class HalRel {

    private final List<HalLink> links;

    private HalRel(List<HalLink> links) {
        this.links = links;
    }

    HalRel(HalLink link) {
        this.links = Collections.singletonList(link);
    }

    public static HalRel single(String href) {
        return new HalRel(
                Collections.singletonList(
                        HalLink.linkTo(href)));
    }

    public static HalRel multiple(Collection<HalLink> links) {
        return new HalRel(new ArrayList<>(links));
    }

    List<HalLink> getLinks() {
        return links;
    }

    public HalLink getSingleLink() {
        return links.get(0);
    }

    public List<HalLink> getMultipleLinks() {
        return links;
    }


    /**
     * Gets a link with the given name.
     * If more than one link has the given name, any could be returned
     *
     * @param linkName the name of the link to return
     * @return An optional containing a link with the given name, an empty optional if none exists
     */
    public Optional<HalLink> getNamedLink(String linkName) {
        Objects.requireNonNull(linkName);
        return links.stream()
                .filter(hl -> linkName.equals(hl.getName()))
                .findAny();
    }

}
