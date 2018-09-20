/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import nl.bzk.brp.levering.dataaccess.repository.alleenlezen.PartijRepository;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * PartijCache implementatie.  {@link  nl.bzk.brp.levering.algemeen.cache.PartijCache}.
 */
@Component
@ManagedResource(
    objectName = "nl.bzk.brp.levering.algemeen.cache:name=PartijCache",
    description = "Het herladen van toegang partij cache.")
public final class PartijCacheImpl implements PartijCache {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private PartijRepository partijRepository;

    private Data data;

    /**
     * Laadt de cache initieel.
     */
    @PostConstruct
    public void naMaak() {
        herlaad();
    }

    @Override
    @Scheduled(cron = "${partij.cache.cron:0 0 0 * * *}")
    public void herlaad() {
        herlaadImpl();
    }

    @Override
    @ManagedOperation(description = "herlaadViaJmx")
    public void herlaadViaJmx() {
        herlaad();
    }

    @Override
    public Partij geefPartij(final int code) {
        return this.data.partijMap.get(code);
    }

    /**
     * Herlaad de partij tabel.
     */
    public void herlaadImpl() {
        LOGGER.debug("Start herladen cache");
        final List<Partij> allePartijen = partijRepository.geefAllePartijen();
        final Map<Integer, Partij> partijMap = new HashMap<>();
        for (final Partij partij : allePartijen) {
            partijMap.put(partij.getCode().getWaarde(), partij);
        }
        this.data = new Data(partijMap);
        LOGGER.debug("Einde herladen cache");
    }


    /**
     * Data. Data holder for swap in
     */
    private static class Data {
        private final Map<Integer, Partij> partijMap;

        Data(final Map<Integer, Partij> partijMap) {
            this.partijMap = partijMap;
        }
    }
}
