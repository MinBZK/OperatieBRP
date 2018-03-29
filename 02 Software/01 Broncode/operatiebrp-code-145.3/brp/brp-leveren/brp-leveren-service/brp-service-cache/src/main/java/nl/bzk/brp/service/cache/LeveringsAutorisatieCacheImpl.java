/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.ElementUtil;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.expressie.Expressie;
import org.springframework.stereotype.Component;


/**
 * LeveringsAutorisatieCacheImpl : Het cache wordt initieel ingeladen via de CacheController, daarna via een scheduling mechanisme.
 */
@Bedrijfsregel(Regel.R1263)
@Bedrijfsregel(Regel.R1261)
@Bedrijfsregel(Regel.R2052)
@Bedrijfsregel(Regel.R2060)
@Component
final class LeveringsAutorisatieCacheImpl implements LeveringsAutorisatieCache {

    /**
     * lev aut cache naam.
     */
    static final String CACHE_NAAM = "LEVAUT_CACHE";

    private static final Logger LOGGER = LoggerFactory.getLogger();


    @Inject
    private LeveringsAutorisatieCacheHelper levAutCacheHelper;

    @Inject
    private BrpCache brpCache;

    private LeveringsAutorisatieCacheImpl() {

    }

    /**
     * @param leveringautorisatieId id van leveringsautorisatie
     * @param partijCode partijcode
     * @return een key als {@code String}
     */
    private static String maakKey(final int leveringautorisatieId, final String partijCode) {
        return leveringautorisatieId + "|" + partijCode;
    }

    /**
     * Herlaad de toegang leveringsautorisatie cache en de expressie cache.
     */
    @Override
    public CacheEntry herlaad(final PartijCacheImpl.Data partijData) {
        LOGGER.debug("Start herladen cache: " + levAutCacheHelper);
        final List<ToegangLeveringsAutorisatie> alleToegangLeveringsautorisaties = levAutCacheHelper.ophalenAlleToegangLeveringsautorisaties(partijData);
        final Data data = Data.immutableCopy(filterAutorisatiesMetOngeldigeExpressies(alleToegangLeveringsautorisaties));
        LOGGER.debug("Einde herladen cache: " + levAutCacheHelper);
        LOGGER.debug("Einde publiceer event voor cache verversing");
        return new CacheEntry(CACHE_NAAM, data);
    }

    @Override
    public List<ToegangLeveringsAutorisatie> geefAlleToegangleveringsautorisaties() {
        return getCache().alleToegangLeveringsautorisaties;
    }

    @Override
    public Expressie geefPopulatiebeperking(final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie, final Dienst dienst) {
        if (dienst.getSoortDienst() == SoortDienst.SELECTIE) {
            return getCache().populatieBeperkingSelectieExpressieCache.get(toegangLeveringsAutorisatie.getId()).get(dienst.getId());
        } else {
            final Map<Integer, Expressie> expressieMap = getCache().populatieBeperkingExpressieCache.get(toegangLeveringsAutorisatie.getId());
            if (expressieMap == null) {
                return null;
            }
            return expressieMap.get(dienst.getDienstbundel().getId());
        }
    }

    @Override
    public Expressie geefAttenderingExpressie(final Dienst dienst) {
        return getCache().attenderingExpressieCache.get(dienst.getId());
    }

    @Override
    public List<AttribuutElement> geefGeldigeElementen(final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie, final Dienst dienst) {
        return getCache().geldigeElementenTla.get(toegangLeveringsAutorisatie.getId()).get(dienst.getId());
    }

    @Override
    public Leveringsautorisatie geefLeveringsautorisatie(final int leveringautorisatieId) {
        return getCache().alleLeveringsautorisatiesMap.get(leveringautorisatieId);
    }

    @Override
    public List<ToegangLeveringsAutorisatie> geefToegangleveringautorisatiesVoorGeautoriseerdePartij(final String partijCode) {
        final List<ToegangLeveringsAutorisatie> toegangLeveringsautorisaties = getCache().alleToegangLeveringsautorisatiesVoorPartij
                .get(partijCode);
        return toegangLeveringsautorisaties == null ? Collections.emptyList()
                : Collections.unmodifiableList(toegangLeveringsautorisaties);
    }

    @Override
    public ToegangLeveringsAutorisatie geefToegangLeveringsautorisatie(final int leveringautorisatieId, final String partijCode) {
        return getCache().alleToegangLeveringsautorisatiesMetLeveringsautorisatieEnPartij.get(maakKey(leveringautorisatieId, partijCode));
    }

    @Override
    public ToegangLeveringsAutorisatie geefToegangLeveringsautorisatie(final Integer toegangLeveringautorisatieId) {
        return getCache().alleToegangsLeveringsautorisatiesMap.get(toegangLeveringautorisatieId);
    }

    @Override
    public List<Autorisatiebundel> geefAutorisatieBundelsVoorMutatielevering() {
        return getCache().autorisatiebundelsVoorMutatieLevering;
    }

    @Override
    public Dienst geefDienst(final int dienstId) {
        return getCache().alleDiensten.get(dienstId);
    }

    private Data getCache() {
        return (Data) this.brpCache.getCache(CACHE_NAAM);
    }

    private List<ToegangLeveringsAutorisatie> filterAutorisatiesMetOngeldigeExpressies(
            final List<ToegangLeveringsAutorisatie> toegangLeveringsAutorisaties) {
        final List<ToegangLeveringsAutorisatie> geldigeToegangautorisaties = new ArrayList<>();

        for (ToegangLeveringsAutorisatie toegangLeveringsAutorisatie : toegangLeveringsAutorisaties) {
            final String populatieBeperking = toegangLeveringsAutorisatie.getLeveringsautorisatie().getPopulatiebeperking();

            boolean success = ExpressieCacheUtil.valideerExpressie(populatieBeperking, toegangLeveringsAutorisatie.getId());
            if (success) {
                final String naderePopulatieBeperking = toegangLeveringsAutorisatie.getNaderePopulatiebeperking();
                success = ExpressieCacheUtil.valideerExpressie(naderePopulatieBeperking, toegangLeveringsAutorisatie.getId());
            }

            if (success) {
                verwijderDienstbundelsMetOngeldigeExpressie(toegangLeveringsAutorisatie);
                geldigeToegangautorisaties.add(toegangLeveringsAutorisatie);
            } else {
                LOGGER.error("Toegangleveringautorisatie {} verwijderd, bevat geen geldige expressie", toegangLeveringsAutorisatie.getId());
            }
        }
        return geldigeToegangautorisaties;
    }

    private void verwijderDienstbundelsMetOngeldigeExpressie(final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie) {
        final Set<Dienstbundel> dienstbundels = toegangLeveringsAutorisatie.getLeveringsautorisatie().getDienstbundelSet();
        final Iterator<Dienstbundel> iteratorDienstBundel = dienstbundels.iterator();
        while (iteratorDienstBundel.hasNext()) {
            final Dienstbundel dienstbundel = iteratorDienstBundel.next();
            final String naderePopulatieBeperkingDienstbundel = dienstbundel.getNaderePopulatiebeperking();
            final boolean dienstBundelSucces = ExpressieCacheUtil.valideerExpressie(naderePopulatieBeperkingDienstbundel,
                    toegangLeveringsAutorisatie.getId());
            if (!dienstBundelSucces) {
                //verwijder de dienstbundel
                iteratorDienstBundel.remove();
                LOGGER.error("Dienstbundel {} verwijderd, bevat geen geldige expressie", dienstbundel.getId());
                continue;
            }

            final Iterator<Dienst> iterator = dienstbundel.getDienstSet().iterator();
            while (iterator.hasNext()) {
                final Dienst dienst = iterator.next();
                final boolean dienstSuccesAttendering = ExpressieCacheUtil
                        .valideerExpressie(dienst.getAttenderingscriterium(), toegangLeveringsAutorisatie.getId());
                final boolean dienstSuccesSelectie = ExpressieCacheUtil
                        .valideerExpressie(dienst.getNadereSelectieCriterium(), toegangLeveringsAutorisatie.getId());
                if (!(dienstSuccesAttendering && dienstSuccesSelectie)) {
                    //verwijder de dienst
                    iterator.remove();
                    LOGGER.error("Dienst {} verwijderd, bevat geen geldige expressie", dienst.getId());
                }
            }
        }
    }

    /**
     * Data. Data holder for swap in
     */
    static final class Data {
        private Map<Integer, Leveringsautorisatie> alleLeveringsautorisatiesMap = Maps.newHashMap();
        private Map<Integer, ToegangLeveringsAutorisatie> alleToegangsLeveringsautorisatiesMap = Maps.newHashMap();
        private List<ToegangLeveringsAutorisatie> alleToegangLeveringsautorisaties = Lists.newArrayList();
        private Map<String, ToegangLeveringsAutorisatie> alleToegangLeveringsautorisatiesMetLeveringsautorisatieEnPartij = Maps.newHashMap();
        private Map<String, List<ToegangLeveringsAutorisatie>> alleToegangLeveringsautorisatiesVoorPartij = Maps.newHashMap();
        private Map<Integer, Dienst> alleDiensten = Maps.newHashMap();
        private Map<Integer, Expressie> attenderingExpressieCache = Maps.newHashMap();
        private Map<Integer, Map<Integer, Expressie>> populatieBeperkingExpressieCache = Maps.newHashMap();
        private Map<Integer, Map<Integer, Expressie>> populatieBeperkingSelectieExpressieCache = Maps.newHashMap();
        private Map<Integer, Map<Integer, List<AttribuutElement>>> geldigeElementenTla = Maps.newHashMap();
        private List<Autorisatiebundel> autorisatiebundelsVoorMutatieLevering = Lists.newArrayList();

        /**
         * @param alleToegangLeveringsautorisaties alleToegangLeveringsautorisaties
         */
        private Data(final List<ToegangLeveringsAutorisatie> alleToegangLeveringsautorisaties) {
            this.alleToegangLeveringsautorisaties.addAll(alleToegangLeveringsautorisaties);
            final int vandaag = DatumUtil.vandaag();

            // bouw de mappen
            for (final ToegangLeveringsAutorisatie toegangLeveringsautorisatie : alleToegangLeveringsautorisaties) {
                alleToegangsLeveringsautorisatiesMap.put(toegangLeveringsautorisatie.getId(), toegangLeveringsautorisatie);
                final Leveringsautorisatie leveringsautorisatie = toegangLeveringsautorisatie.getLeveringsautorisatie();
                alleToegangLeveringsautorisatiesMetLeveringsautorisatieEnPartij
                        .put(maakKey(leveringsautorisatie.getId(), toegangLeveringsautorisatie.getGeautoriseerde().getPartij().getCode()),
                                toegangLeveringsautorisatie);

                alleLeveringsautorisatiesMap.put(leveringsautorisatie.getId(), leveringsautorisatie);
                maakTlaEnPartijEntry(toegangLeveringsautorisatie, toegangLeveringsautorisatie.getGeautoriseerde());
                maakCacheEntriesVoorDienstbundel(toegangLeveringsautorisatie,
                        leveringsautorisatie, vandaag);
            }
            this.autorisatiebundelsVoorMutatieLevering =
                    AutorisatiebundelCacheUtil
                            .geefAutorisatiebundels(alleToegangLeveringsautorisaties, vandaag, AutorisatiebundelCacheUtil.LEVEREN_MUTATIES_DIENST_SOORTEN);
        }

        static Data immutableCopy(final List<ToegangLeveringsAutorisatie> alleToegangLeveringsautorisaties) {

            final Data data = new Data(alleToegangLeveringsautorisaties);
            data.alleLeveringsautorisatiesMap = Collections.unmodifiableMap(data.alleLeveringsautorisatiesMap);
            data.alleToegangsLeveringsautorisatiesMap = Collections.unmodifiableMap(data.alleToegangsLeveringsautorisatiesMap);
            data.alleToegangLeveringsautorisaties = Collections.unmodifiableList(data.alleToegangLeveringsautorisaties);
            data.alleToegangLeveringsautorisatiesMetLeveringsautorisatieEnPartij =
                    Collections.unmodifiableMap(data.alleToegangLeveringsautorisatiesMetLeveringsautorisatieEnPartij);
            data.alleToegangLeveringsautorisatiesVoorPartij = Collections.unmodifiableMap(data.alleToegangLeveringsautorisatiesVoorPartij);
            data.alleDiensten = Collections.unmodifiableMap(data.alleDiensten);
            data.attenderingExpressieCache = Collections.unmodifiableMap(data.attenderingExpressieCache);
            data.populatieBeperkingExpressieCache = Collections.unmodifiableMap(data.populatieBeperkingExpressieCache);
            data.populatieBeperkingSelectieExpressieCache = Collections.unmodifiableMap(data.populatieBeperkingSelectieExpressieCache);
            data.geldigeElementenTla = Collections.unmodifiableMap(data.geldigeElementenTla);
            data.autorisatiebundelsVoorMutatieLevering = Collections.unmodifiableList(data.autorisatiebundelsVoorMutatieLevering);
            return data;
        }

        private void maakTlaEnPartijEntry(final ToegangLeveringsAutorisatie toegangLeveringsautorisatie, final PartijRol geautoriseerde) {
            List<ToegangLeveringsAutorisatie> tlaVoorPartij = alleToegangLeveringsautorisatiesVoorPartij.get(
                    geautoriseerde.getPartij().getCode());
            if (tlaVoorPartij == null) {
                tlaVoorPartij = new ArrayList<>();
                alleToegangLeveringsautorisatiesVoorPartij.put(geautoriseerde.getPartij().getCode(), tlaVoorPartij);
            }
            tlaVoorPartij.add(toegangLeveringsautorisatie);
        }

        private void maakCacheEntriesVoorDienstbundel(final ToegangLeveringsAutorisatie toegangLeveringsautorisatie,
                                                      final Leveringsautorisatie leveringsautorisatie,
                                                      final int vandaag) {
            if (leveringsautorisatie.getDienstbundelSet() != null) {
                for (final Dienstbundel dienstbundel : leveringsautorisatie.getDienstbundelSet()) {
                    maakPopulatieBepalingExpressieCacheEntry(toegangLeveringsautorisatie, dienstbundel);
                    for (final Dienst dienst : dienstbundel.getDienstSet()) {
                        geldigeElementenTla.computeIfAbsent(toegangLeveringsautorisatie.getId(), integer -> Maps.newHashMap())
                                .put(dienst.getId(), geefGeldigeAttribuutElementen(dienst, vandaag));
                        alleDiensten.put(dienst.getId(), dienst);
                        maakAttenderingExpressieCacheEntry(toegangLeveringsautorisatie, dienst);
                        maakSelectieExpressieCacheEntry(toegangLeveringsautorisatie, dienst);
                    }
                }
            }
        }

        private void maakSelectieExpressieCacheEntry(final ToegangLeveringsAutorisatie toegangLeveringsautorisatie, final Dienst dienst) {
            if (dienst.getSoortDienst() == SoortDienst.SELECTIE) {
                final Expressie expressie = ExpressieCacheUtil.maakSelectieExpressie(toegangLeveringsautorisatie, dienst);
                populatieBeperkingSelectieExpressieCache.computeIfAbsent(toegangLeveringsautorisatie.getId(),
                        integer -> Maps.newHashMap()).put(dienst.getId(), expressie);
            }
        }

        private void maakAttenderingExpressieCacheEntry(final ToegangLeveringsAutorisatie toegangLeveringsautorisatie, final Dienst dienst) {
            if (dienst.getAttenderingscriterium() != null && dienst.getSoortDienst() == SoortDienst.ATTENDERING) {
                final Expressie attenderingExpressie = ExpressieCacheUtil.parseExpressie(dienst.getAttenderingscriterium(),
                        toegangLeveringsautorisatie.getId());
                attenderingExpressieCache.put(dienst.getId(), attenderingExpressie);
            }
        }

        private void maakPopulatieBepalingExpressieCacheEntry(final ToegangLeveringsAutorisatie toegangLeveringsautorisatie,
                                                              final Dienstbundel dienstbundel) {
            final Expressie expressie = ExpressieCacheUtil.maakTotalePopulatieBeperkingExpressie(toegangLeveringsautorisatie, dienstbundel);
            populatieBeperkingExpressieCache.computeIfAbsent(toegangLeveringsautorisatie.getId(),
                    integer -> Maps.newHashMap()).put(dienstbundel.getId(), expressie);
        }

        @Bedrijfsregel(Regel.R2002)
        private List<AttribuutElement> geefGeldigeAttribuutElementen(final Dienst dienst, final int vandaag) {
            final List<AttribuutElement> elementLijst = new LinkedList<>();
            final Set<DienstbundelGroep> dienstbundelGroepenSet = dienst.getDienstbundel().getDienstbundelGroepSet();
            for (final DienstbundelGroep dienstbundelGroep : dienstbundelGroepenSet) {
                for (final DienstbundelGroepAttribuut attribuutModel : dienstbundelGroep.getDienstbundelGroepAttribuutSet()) {
                    final AttribuutElement attribuutElement = ElementHelper.getAttribuutElement(attribuutModel.getAttribuut().getId());
                    if (ElementUtil.isElementGeldigVoorAttribuutAutorisatie(attribuutElement, vandaag)) {
                        elementLijst.add(attribuutElement);
                    }
                }
            }
            return Collections.unmodifiableList(elementLijst);
        }
    }
}

