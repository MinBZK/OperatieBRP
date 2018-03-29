/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.dockertest.component.CacheSupport;
import nl.bzk.brp.dockertest.component.Docker;
import nl.bzk.brp.dockertest.component.DockerNaam;
import nl.bzk.brp.dockertest.component.Omgeving;

/**
 * Helper klasse om de cache van een omgeving te updaten.
 */
public final class CacheHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final Omgeving omgeving;

    /**
     * Constructor.
     * @param omgeving de omgeving.
     */
    public CacheHelper(final Omgeving omgeving) {
        this.omgeving = omgeving;
    }

    /**
     * Update parallel alle caches. Blocked tot alles bijgewerkt is.
     */
    public void refresh() {
        doRefresh(omgeving.geefDockers().stream()
                .filter(dockerComponent -> dockerComponent instanceof CacheSupport).collect(Collectors.toList()));
    }

    /**
     * Update parallel de gegeven cachelinks. Blocked tot alles bijgewerkt is.
     * @param dockerNaamLijst  lijst te verversen Dockers
     */
    public void refresh(DockerNaam... dockerNaamLijst) {
        final List<Docker> collect = Arrays.stream(dockerNaamLijst).
                filter(omgeving::bevat).map(linkNaam -> (Docker) omgeving.geefDocker(linkNaam)).collect(Collectors.toList());
        doRefresh(collect);
    }

    private void doRefresh(List<Docker> links) {
        LOGGER.info("Start cache refresh");
        if (!omgeving.isGestart()) {
            throw new IllegalStateException("Omgeving niet gestart");
        }
        links.parallelStream().forEach(x -> {
            if (x instanceof CacheSupport) {
                ((CacheSupport) x).refresh();
            }
        });
        LOGGER.info("Einde cache refresh");
    }
}
