/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.leveringsautorisatie.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import nl.bzk.brp.dataaccess.repository.ToegangLeveringsautorisatieRepository;
import nl.bzk.brp.levering.business.cache.CacheVerversEvent;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienstbundel;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import org.springframework.context.ApplicationContext;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
@ManagedResource(objectName = "nl.bzk.brp.levering.business.toegang.abonnement.cache:name=AutAutCache",
    description = "Het herladen van toegang levering autorisatie cache.")
public class LeveringAutorisatieCacheImpl implements LeveringAutorisatieCache {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private Data data;

    private DatumAttribuut datumGeldigheidsControle;

    @Inject
    private ToegangLeveringsautorisatieRepository toegangLeveringsautorisatieRepository;

    @Inject
    private ApplicationContext applicationContext;

    /**
     * Laadt de cache initieel.
     */
    @PostConstruct
    public final void naMaak() {
        herlaad();
    }

    /**
     * Herlaad via jmx. Managed operation.
     */
    @Override
    @ManagedOperation(description = "herlaadViaJmx")
    public final void herlaadViaJmx() {
        herlaad();
    }

    @Override
    @Scheduled(cron = "${toegang.abonnement.cache.cron:0 0 0 * * *}")
    public final void herlaad() {
        herlaadImpl();
    }

    /**
     * Herlaad de toegang leveringsautorisatie cache en de expressie cache.
     *
     * @brp.bedrijfsregel R1263
     * @brp.bedrijfsregel R1261
     * @brp.bedrijfsregel R2052
     */
    @Regels({ Regel.R1263, Regel.R1261, Regel.R2052 })
    private void herlaadImpl() {
        LOGGER.debug("Start herladen cache");
        // Ophalen ToegangLeveringsautorisaties uit database
        final List<ToegangLeveringsautorisatie> alleToegangLeveringsautorisaties = toegangLeveringsautorisatieRepository
            .haalAlleToegangLeveringsautorisatieOp();
        LOGGER.debug("ToegangLeveringsautorisaties opgehaald uit database");
        // vandaag
        this.datumGeldigheidsControle = DatumAttribuut.vandaag();
        bouwDataMap(alleToegangLeveringsautorisaties);

    }

    private void bouwDataMap(final List<ToegangLeveringsautorisatie> alleToegangLeveringsautorisaties) {
        // bouw toegangleveringsautorisaties mappen
        final Map<String, ToegangLeveringsautorisatie> alleToegangLeveringsautorisatiesMetLeveringsautorisatieEnPartij = new HashMap<>();
        final Map<Integer, ToegangLeveringsautorisatie> alleGeldigeToegangLeveringsautorisatieMetId = new HashMap<>();
        final Map<Integer, Leveringsautorisatie> alleGeldigeLeveringsautorisatiesMap = new HashMap<>();
        final Map<Integer, Leveringsautorisatie> alleLeveringautorisatiesMap = new HashMap<>();
        final Map<Integer, Dienst> alleDiensten = new HashMap<>();
        // bouw de mappen
        for (final ToegangLeveringsautorisatie toegangLeveringsautorisatie : alleToegangLeveringsautorisaties) {
            if (isGeldig(toegangLeveringsautorisatie)) {
                alleGeldigeToegangLeveringsautorisatieMetId.put(toegangLeveringsautorisatie.getID(), toegangLeveringsautorisatie);
            }
            final Leveringsautorisatie leveringsautorisatie = toegangLeveringsautorisatie.getLeveringsautorisatie();
            alleToegangLeveringsautorisatiesMetLeveringsautorisatieEnPartij
                .put(maakKey(leveringsautorisatie.getID(), toegangLeveringsautorisatie.getGeautoriseerde().getPartij().getCode().getWaarde()),
                    toegangLeveringsautorisatie);
            if (leveringsautorisatie.isGeldigOp(datumGeldigheidsControle)) {
                alleGeldigeLeveringsautorisatiesMap.put(leveringsautorisatie.getID(), leveringsautorisatie);
            }
            alleLeveringautorisatiesMap.put(leveringsautorisatie.getID(), leveringsautorisatie);
            if (leveringsautorisatie.heeftDienstbundels()) {
                for (Dienstbundel dienstbundel : leveringsautorisatie.getDienstbundels()) {
                    for (Dienst dienst : dienstbundel.getDiensten()) {
                        alleDiensten.put(dienst.getID(), dienst);
                    }
                }
            }
        }

        // swap cache data
        this.data =
            new Data(
                new ArrayList<>(alleGeldigeToegangLeveringsautorisatieMetId.values()),
                alleGeldigeLeveringsautorisatiesMap,
                alleLeveringautorisatiesMap,
                alleToegangLeveringsautorisatiesMetLeveringsautorisatieEnPartij,
                alleGeldigeToegangLeveringsautorisatieMetId,
                alleDiensten);
        LOGGER.debug("Einde herladen cache");
        // publiceer event voor cache ververs
        applicationContext.publishEvent(new CacheVerversEvent(this));
        LOGGER.debug("Einde publiceer event voor cache verversing");
    }

    private boolean isGeldig(final ToegangLeveringsautorisatie toegangLeveringsautorisatie) {
        final Leveringsautorisatie leveringsautorisatie = toegangLeveringsautorisatie.getLeveringsautorisatie();
        return !toegangLeveringsautorisatie.isGeblokkeerd() && toegangLeveringsautorisatie.isGeldigOp(datumGeldigheidsControle)
            && !leveringsautorisatie.isGeblokkeerd()
            && leveringsautorisatie.isGeldigOp(datumGeldigheidsControle);
    }

    @Override
    public final List<ToegangLeveringsautorisatie> geefGeldigeToegangleveringsautorisaties() {
        return data.alleGeldigeToegangLeveringsautorisaties;
    }

    @Override
    public final Leveringsautorisatie geefLeveringsautorisatie(final int leveringautorisatieId) {
        return this.data.alleGeldigeLeveringsautorisatiesMap.get(leveringautorisatieId);
    }

    @Override
    public final Leveringsautorisatie geefLeveringsautorisatieZonderControle(final int leveringautorisatieId) {
        return this.data.alleLeveringsautorisatiesMap.get(leveringautorisatieId);
    }

    @Override
    public final ToegangLeveringsautorisatie geefToegangleveringautorisatieZonderControle(final int leveringautorisatieId, final int partijCode) {
        return this.data.alleToegangLeveringsautorisatiesMetLeveringsautorisatieEnPartij.get(maakKey(leveringautorisatieId, partijCode));
    }

    @Override
    public final ToegangLeveringsautorisatie geefToegangleveringsautorisatie(final int leveringautorisatieId, final int partijCode) {
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie =
            this.data.alleToegangLeveringsautorisatiesMetLeveringsautorisatieEnPartij.get(maakKey(leveringautorisatieId, partijCode));
        if (isGeldig(toegangLeveringsautorisatie)) {
            return toegangLeveringsautorisatie;
        }
        return null;
    }

    @Override
    public final ToegangLeveringsautorisatie geefToegangleveringautorisatieZonderControle(final Integer iD) {
        return this.data.alleGeldigeToegangLeveringsautorisatiesMetId.get(iD);
    }

    @Override
    public Dienst geefDienst(final int dienstId) {
        return this.data.alleDiensten.get(dienstId);
    }


    private String maakKey(final int leveringautorisatieId, final int partijCode) {
        return leveringautorisatieId + "|" + partijCode;
    }

    /**
     * Data. Data holder for swap in
     */
    private static class Data {
        private final Map<Integer, Leveringsautorisatie>        alleGeldigeLeveringsautorisatiesMap;
        private final Map<Integer, Leveringsautorisatie>        alleLeveringsautorisatiesMap;
        private final List<ToegangLeveringsautorisatie>         alleGeldigeToegangLeveringsautorisaties;
        private final Map<String, ToegangLeveringsautorisatie>  alleToegangLeveringsautorisatiesMetLeveringsautorisatieEnPartij;
        private final Map<Integer, ToegangLeveringsautorisatie> alleGeldigeToegangLeveringsautorisatiesMetId;
        private final Map<Integer, Dienst>                      alleDiensten;


        Data(
            final List<ToegangLeveringsautorisatie> alleGeldigeToegangLeveringsautorisaties,
            final Map<Integer, Leveringsautorisatie> alleGeldigeLeveringsautorisatiesMap,
            final Map<Integer, Leveringsautorisatie> alleLeveringsautorisatiesMap,
            final Map<String, ToegangLeveringsautorisatie> alleToegangLeveringsautorisatiesMetLeveringsautorisatieEnPartij,
            final Map<Integer, ToegangLeveringsautorisatie> alleGeldigeToegangLeveringsautorisatiesMetId,
            final Map<Integer, Dienst> alleDiensten)

        {
            this.alleGeldigeToegangLeveringsautorisaties = alleGeldigeToegangLeveringsautorisaties;
            this.alleGeldigeLeveringsautorisatiesMap = alleGeldigeLeveringsautorisatiesMap;
            this.alleLeveringsautorisatiesMap = alleLeveringsautorisatiesMap;
            this.alleToegangLeveringsautorisatiesMetLeveringsautorisatieEnPartij = alleToegangLeveringsautorisatiesMetLeveringsautorisatieEnPartij;
            this.alleGeldigeToegangLeveringsautorisatiesMetId = alleGeldigeToegangLeveringsautorisatiesMetId;
            this.alleDiensten = alleDiensten;
        }
    }
}

