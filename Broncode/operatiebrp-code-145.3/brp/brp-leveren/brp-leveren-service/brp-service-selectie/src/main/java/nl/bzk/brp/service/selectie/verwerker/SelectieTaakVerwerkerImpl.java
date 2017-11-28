/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.LeverwijzeSelectie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAfnemerindicatieTaak;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAutorisatieBericht;
import nl.bzk.brp.domain.internbericht.selectie.SelectieTaakResultaat;
import nl.bzk.brp.domain.internbericht.selectie.SelectieVerwerkTaakBericht;
import nl.bzk.brp.domain.internbericht.selectie.TypeResultaat;
import nl.bzk.brp.domain.internbericht.verzendingmodel.AfnemerBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.PlaatsAfnemerBerichtService;
import nl.bzk.brp.service.selectie.publicatie.SelectieTaakResultaatPublicatieService;
import nl.bzk.brp.service.selectie.verwerker.cache.VerwerkerCache;
import nl.bzk.brp.service.selectie.verwerker.persoonsbeelden.PersoonsBeeldenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatie van {@link SelectieTaakVerwerker}.
 *
 * Vertaalt de {@link SelectieVerwerkTaakBericht} naar {@link Autorisatiebundel}s en {@link Persoonslijst} objecten.
 */
//@Bedrijfsregel(Regel.R2723)
@Service
final class SelectieTaakVerwerkerImpl implements SelectieTaakVerwerker {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private SelectieTaakResultaatPublicatieService selectieTaakResultaatPublicatieService;
    @Inject
    private VerwerkerPublicatieService verwerkerPublicatieService;
    @Inject
    private PersoonsBeeldenService persoonsBeeldenService;
    @Inject
    private VerwerkPersoonExecutorService persoonBerichtService;
    @Inject
    private VerwerkerCache verwerkerAutorisatieCache;
    @Inject
    private AfnemerindicatieVerzoekPublicatieService afnemerindicatieVerzoekPublicatieService;
    @Inject
    private PlaatsAfnemerBerichtService plaatsAfnemerBerichtService;

    private SelectieTaakVerwerkerImpl() {
    }

    @Override
    @Transactional(transactionManager = "masterTransactionManager")
    public void verwerkSelectieTaak(final SelectieVerwerkTaakBericht selectieTaak) {
        try {
            LOGGER.info("Start verwerk selectietaak");
            final List<SelectieAutorisatiebundel> autorisatiebundels = maakAutorisatieBundels(selectieTaak);
            final Collection<Persoonslijst> lijstMetPersonen = persoonsBeeldenService.maakPersoonsBeelden(selectieTaak);
            //filter selectietaken met personen en peilmoment voor gba wijziging
            final Set<Integer> ongeldigeSelectietaken = filter(autorisatiebundels, lijstMetPersonen);
            final Collection<VerwerkPersoonResultaat> resultaten = persoonBerichtService.verwerkPersonen(selectieTaak, lijstMetPersonen, autorisatiebundels);
            //splits selectie maak resultaat schrijf taken, directe verzending en afnemerindicatie resultaat taken
            final List<VerwerkPersoonResultaat> selectieBestandResultaatTaken = new ArrayList<>();
            final List<VerwerkPersoonResultaat> selectieAfnemerindicatieTaken = new ArrayList<>();
            final List<AfnemerBericht> selectieDirectVerzendenTaken = new ArrayList<>();
            for (VerwerkPersoonResultaat verwerkPersoonResultaat : resultaten) {
                final Dienst dienst = verwerkPersoonResultaat.getDienst();
                if (isDirectVerzenden(dienst)) {
                    voegDirectVerzendenToe(selectieDirectVerzendenTaken, verwerkPersoonResultaat);
                } else if (isStandaardSelectie(dienst)) {
                    voegBestandToe(selectieBestandResultaatTaken, verwerkPersoonResultaat);
                } else {
                    selectieAfnemerindicatieTaken.add(verwerkPersoonResultaat);
                }
            }
            //publiceer schrijf taken
            final int aantalGepubliceerdeSchrijfTaken = verwerkerPublicatieService.publiceerSchrijfTaken(selectieTaak, selectieBestandResultaatTaken);
            //publiceer afnemerindicatie taken
            final Collection<SelectieAfnemerindicatieTaak>
                    afnemerindicatieVerzoeken =
                    maakAfnemerindicatieVerzoeken(selectieAfnemerindicatieTaken, selectieTaak);
            afnemerindicatieVerzoekPublicatieService.publiceerAfnemerindicatieVerzoeken(afnemerindicatieVerzoeken);
            final int aantalAfnemerindicatieTaken = afnemerindicatieVerzoeken.size();
            //publiceer directe verzending berichten naar verzender
            plaatsAfnemerBerichtService.plaatsAfnemerberichten(selectieDirectVerzendenTaken);
            publiceerProcesResultaatNaarLezer(selectieTaak.getSelectieRunId(), aantalGepubliceerdeSchrijfTaken, aantalAfnemerindicatieTaken,
                    selectieDirectVerzendenTaken.size(), ongeldigeSelectietaken);
            LOGGER.info("Einde verwerk selectietaak");
        } catch (Exception e) {
            LOGGER.error("error verwerken selectie taak", e);
            selectieTaakResultaatPublicatieService.publiceerFout();
        }
    }

    private void voegBestandToe(List<VerwerkPersoonResultaat> selectieBestandResultaatTaken, VerwerkPersoonResultaat verwerkPersoonResultaat) {
        if (verwerkPersoonResultaat.getPersoonFragment() != null) {
            selectieBestandResultaatTaken.add(verwerkPersoonResultaat);
        }
    }

    private void voegDirectVerzendenToe(List<AfnemerBericht> selectieDirectVerzendenTaken, VerwerkPersoonResultaat verwerkPersoonResultaat) {
        if (verwerkPersoonResultaat.getSynchronisatieBerichtGegevens() != null) {
            selectieDirectVerzendenTaken.add(new AfnemerBericht(verwerkPersoonResultaat.getSynchronisatieBerichtGegevens(),
                    verwerkPersoonResultaat.getAutorisatiebundel().getToegangLeveringsautorisatie()));
        }
    }

    private boolean isStandaardSelectie(Dienst dienst) {
        return dienst.getSoortSelectie() == SoortSelectie.STANDAARD_SELECTIE.getId();
    }

    private boolean isDirectVerzenden(Dienst dienst) {
        return isStandaardSelectie(dienst) && dienst.getLeverwijzeSelectie() != null
                && LeverwijzeSelectie.BERICHT.getId() == dienst.getLeverwijzeSelectie();
    }

    private Set<Integer> filter(final List<SelectieAutorisatiebundel> autorisatiebundels, final Collection<Persoonslijst> lijstMetPersonen) {
        final Set<Integer> ongeldigeSelectietaken = Sets.newHashSet();
        final Map<Long, ZonedDateTime> gbaSystematiekMap = Maps.newHashMap();
        for (Persoonslijst persoonslijst : lijstMetPersonen) {
            //we berekenen dit nu altijd. We zouden ook kunnen detecteren dat peilmoment formeel niet is gezet vanuit beheer maar op 'nu' is gezet in
            // selectie run voor alle taken.
            final ZonedDateTime laatsteWijzigingGBASystematiek = persoonslijst.bepaalTijdstipLaatsteWijzigingGBASystematiek();
            gbaSystematiekMap.put(persoonslijst.getId(), laatsteWijzigingGBASystematiek);
        }
        for (SelectieAutorisatiebundel autorisatiebundel : autorisatiebundels) {
            //alleen voor standaard selectie relevant en als peilmoment formeel gezet
            if (isStandaardSelectie(autorisatiebundel.getAutorisatiebundel().getDienst())
                    && autorisatiebundel.getSelectieAutorisatieBericht().getPeilmomentFormeel() != null) {
                bepaalOngeldigeSelectietaak(lijstMetPersonen, ongeldigeSelectietaken, gbaSystematiekMap, autorisatiebundel);
            }
        }
        return ongeldigeSelectietaken;
    }

    private void bepaalOngeldigeSelectietaak(Collection<Persoonslijst> lijstMetPersonen, Set<Integer> ongeldigeSelectietaken,
                                             Map<Long, ZonedDateTime> gbaSystematiekMap, SelectieAutorisatiebundel autorisatiebundel) {
        for (Persoonslijst persoonslijst : lijstMetPersonen) {
            final ZonedDateTime laatsteWijzigingGBASystematiek = gbaSystematiekMap.get(persoonslijst.getId());
            if (laatsteWijzigingGBASystematiek != null && autorisatiebundel.getSelectieAutorisatieBericht().getPeilmomentFormeel().isBefore(
                    laatsteWijzigingGBASystematiek)) {
                //ongeldig
                LOGGER.warn(String.format("Laatste wijziging gba systematiek ligt na formeel peilmoment, selectie taak [%d] zal afgebroken worden",
                        autorisatiebundel.getSelectieAutorisatieBericht().getSelectietaakId()));
                ongeldigeSelectietaken.add(autorisatiebundel.getSelectieAutorisatieBericht().getSelectietaakId());
            }
        }
    }

    private Collection<SelectieAfnemerindicatieTaak> maakAfnemerindicatieVerzoeken(
            final List<VerwerkPersoonResultaat> resultaten, final SelectieVerwerkTaakBericht selectieTaak) {
        final Collection<SelectieAfnemerindicatieTaak> verzoeken = Lists.newArrayList();
        for (VerwerkPersoonResultaat verwerkPersoonResultaat : resultaten) {
            final SelectieAfnemerindicatieTaak taak = new SelectieAfnemerindicatieTaak();
            taak.setSoortSelectie(SoortSelectie.parseId(verwerkPersoonResultaat.getDienst().getSoortSelectie()));
            taak.setBericht(verwerkPersoonResultaat.getPersoonFragment());
            taak.setSynchronisatieBerichtGegevens(verwerkPersoonResultaat.getSynchronisatieBerichtGegevens());
            taak.setDienstId(verwerkPersoonResultaat.getDienst().getId());
            taak.setToegangLeveringsautorisatieId(verwerkPersoonResultaat.getToegangLeverinsautorisatieId());
            taak.setSelectieTaakId(verwerkPersoonResultaat.getSelectieTaakId());
            taak.setSelectieRunId(selectieTaak.getSelectieRunId());
            taak.setSelectietaakDatumUitvoer(selectieTaak.getSelectieStartDatum());
            taak.setPersoonId(verwerkPersoonResultaat.getPersoonslijst().getId());
            taak.setTijdstipLaatsteWijziging(verwerkPersoonResultaat.getPersoonslijst().bepaalTijdstipLaatsteWijziging());
            verzoeken.add(taak);
        }
        return verzoeken;
    }

    private List<SelectieAutorisatiebundel> maakAutorisatieBundels(SelectieVerwerkTaakBericht selectieTaak) {
        final List<SelectieAutorisatiebundel> autorisatiebundels = new ArrayList<>();
        for (SelectieAutorisatieBericht selectieAutorisatieBericht : selectieTaak.getSelectieAutorisaties()) {
            final Autorisatiebundel
                    autorisatiebundel =
                    verwerkerAutorisatieCache.getAutorisatiebundel(selectieAutorisatieBericht, selectieTaak.getSelectieRunId());
            new SelectieAutorisatiebundel(autorisatiebundel, selectieAutorisatieBericht);
            autorisatiebundels.add(new SelectieAutorisatiebundel(autorisatiebundel, selectieAutorisatieBericht));
        }
        return autorisatiebundels;
    }

    private void publiceerProcesResultaatNaarLezer(int selectieRunId, int aantalSchrijfTaken, int aantalAfnemerindicatieTaken,
                                                   int aantalVerwerktePersonenNetwerk, Set<Integer> ongeldigeSelectietaken) {
        final SelectieTaakResultaat selectieTaakResultaat = new SelectieTaakResultaat();
        selectieTaakResultaat.setType(TypeResultaat.VERWERK);
        selectieTaakResultaat.setSchrijfTaken(aantalSchrijfTaken);
        selectieTaakResultaat.setAantalAfnemerindicatieTaken(aantalAfnemerindicatieTaken);
        selectieTaakResultaat.setAantalVerwerktePersonenNetwerk(aantalVerwerktePersonenNetwerk);
        selectieTaakResultaat.setSelectieRunId(selectieRunId);
        selectieTaakResultaat.setOngeldigeTaken(ongeldigeSelectietaken);
        selectieTaakResultaatPublicatieService.publiceerSelectieTaakResultaat(selectieTaakResultaat);
    }
}
