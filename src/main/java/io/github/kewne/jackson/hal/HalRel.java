package io.github.kewne.jackson.hal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

}
