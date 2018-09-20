/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import nl.bzk.brp.expressietaal.parser.BRPExpressies;
import nl.bzk.brp.expressietaal.parser.ParserResultaat;
import nl.bzk.brp.levering.business.cache.CacheVerversEvent;
import nl.bzk.brp.levering.dataaccess.repository.alleenlezen.StamTabelRepository;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortElement;
import org.springframework.context.ApplicationContext;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * StamTabelCache implementatie.  {@link  nl.bzk.brp.levering.algemeen.cache.StamTabelCache}.
 */
@Component
@ManagedResource(
    objectName = "nl.bzk.brp.levering.algemeen.cache:name=StamTabelCache",
    description = "Het herladen van toegang stam tabel cache.")
public final class StamTabelCacheImpl implements StamTabelCache {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private StamTabelRepository stamTabelRepository;

    @Inject
    private ApplicationContext applicationContext;

    private Data data;

    /**
     * Laadt de cache initieel.
     */
    @PostConstruct
    public void naMaak() {
        herlaad();
    }

    @Override
    @ManagedOperation(description = "herlaadViaJmx")
    public void herlaadViaJmx() {
        herlaad();
    }

    @Override
    public Collection<Element> geefAlleElementen() {
        return data.alleElementen;
    }

    @Override
    public Element geefElementById(final int id) {
        return this.data.elementIdElementMap.get(id);
    }

    @Override
    public Map<String, ParserResultaat> geefAlleExpressieParserResultatenVanAttributeElementen() {
        return data.parserResultaatMap;
    }

    @Override
    @Scheduled(cron = "${stamtabel.cache.cron:0 0 0 * * *}")
    public void herlaad() {
        herlaadImpl();
    }

    /**
     * Herlaad de stam tabel.
     */
    public void herlaadImpl() {
        LOGGER.debug("Start herladen cache");
        final Collection<Element> alleElementen = stamTabelRepository.geefAlleElementen();
        final Map<String, ParserResultaat> parserResultaatMap = maakParserResultaatMap(alleElementen);
        final Map<Integer, Element> elementIdElementMap = maakElementIdNaarElementMap(alleElementen);

        this.data = new Data(alleElementen, parserResultaatMap, elementIdElementMap);
        LOGGER.debug("Einde herladen cache");
        // publiceer event voor cache ververs
        applicationContext.publishEvent(new CacheVerversEvent(this));
        LOGGER.debug("Einde publiceer event voor cache verversing");
    }

    private Map<Integer, Element> maakElementIdNaarElementMap(final Collection<Element> alleElementen) {
        final Map<Integer, Element> elementIdElementMap = new HashMap<>();
        for (final Element element : alleElementen) {
            elementIdElementMap.put(element.getID(), element);
        }
        return elementIdElementMap;
    }

    private Map<String, ParserResultaat> maakParserResultaatMap(final Collection<Element> alleElementen) {
        final Map<String, ParserResultaat> parserResultaatMap = new HashMap<>();
        for (final Element element : alleElementen) {
            if (element.getSoort() == SoortElement.ATTRIBUUT && element.getExpressie() != null) {
                final ParserResultaat geparsdeTotaleExpressie = BRPExpressies.parse(element.getExpressie().getWaarde());
                if (geparsdeTotaleExpressie.succes()) {
                    parserResultaatMap.put(element.getExpressie().getWaarde(), geparsdeTotaleExpressie);
                } else {
                    LOGGER.error("Fout in het parsen van expressie {}", element.getExpressie().getWaarde());
                }
            }
        }
        return parserResultaatMap;
    }

    /**
     * Data. Data holder for swap in
     */
    private static class Data {
        private final Map<String, ParserResultaat> parserResultaatMap;
        private final Collection<Element>          alleElementen;
        private final Map<Integer, Element>        elementIdElementMap;

        Data(final Collection<Element> alleElementen, final Map<String, ParserResultaat> parserResultaatMap,
            final Map<Integer, Element> elementIdElementMap)
        {
            this.alleElementen = alleElementen;
            this.parserResultaatMap = parserResultaatMap;
            this.elementIdElementMap = elementIdElementMap;
        }
    }
}
