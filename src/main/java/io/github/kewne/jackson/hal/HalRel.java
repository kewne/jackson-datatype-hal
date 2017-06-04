package io.github.kewne.jackson.hal;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class HalRel {

    private final List<HalLink> links;

    private HalRel(List<HalLink> links) {
        this.links = links;
    }

    public static HalRel single(URI href) {
        return new HalRel(
                Collections.singletonList(
                        new HalLink(href)));
    }

    public static HalRel multiple(Collection<HalLink> links) {
        return new HalRel(new ArrayList<>(links));
    }

    List<HalLink> getLinks() {
        return links;
    }
}
