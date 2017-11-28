/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.LeverwijzeSelectie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.gba.dataaccess.Lo3FilterRubriekRepository;
import nl.bzk.brp.levering.lo3.bericht.Bericht;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.service.algemeen.MeldingBepalerService;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.SynchronisatieBerichtGegevensFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * VerwerkPersoonServiceImpl. Zet een persoon om tot fragmenten of volledige berichten voor een reeks autorisaties.
 */
@Service(value = "verwerkPersoonService")
final class VerwerkPersoonServiceImpl implements VerwerkPersoonService {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private SelectieResultaatBerichtFactory selectieBerichtFactory;
    @Inject
    private SelectieMaakFragmentBerichtService maakSelectiePersoonBerichtService;
    @Inject
    private SelectieBepaalLeverenService selectieBepaalLeverenService;
    @Inject
    private SynchronisatieBerichtGegevensFactory synchronisatieBerichtGegevensFactory;
    @Inject
    private MeldingBepalerService meldingBepalerService;
    @Inject
    private BerichtFactory gbaBerichtFactory;
    @Inject
    private Lo3FilterRubriekRepository gbaFilterRubriekRepository;

    private VerwerkPersoonServiceImpl() {
    }

    @Transactional(transactionManager = "masterTransactionManager")
    @Override
    public List<VerwerkPersoonResultaat> verwerk(final MaakSelectieResultaatOpdracht opdracht) throws StapException {
        final List<VerwerkPersoonResultaat> verwerkPersoonResultaten = new ArrayList<>();
        final Persoonslijst persoonslijst = opdracht.getPersoonslijst();
        final Set<SelectieAutorisatiebundel> autorisatiebundelsInSelectie = bepaalInSelectie(opdracht);
        final List<VerwerkPersoonBericht>
                verwerkPersoonBerichten =
                selectieBerichtFactory.maakBerichten(autorisatiebundelsInSelectie, persoonslijst);
        final Map<Autorisatiebundel, VerwerkPersoonBericht>
                autorisatiebundelPersoonMap =
                verwerkPersoonBerichten.stream().collect(Collectors.toMap(VerwerkPersoonBericht::getAutorisatiebundel, v -> v));
        final ConversieCache gbaConversieCache = new ConversieCache();
        for (SelectieAutorisatiebundel selectieAutorisatiebundel : autorisatiebundelsInSelectie) {
            final VerwerkPersoonBericht verwerkPersoonBericht = autorisatiebundelPersoonMap.get(selectieAutorisatiebundel.getAutorisatiebundel());
            final Autorisatiebundel autorisatiebundel = verwerkPersoonBericht.getAutorisatiebundel();
            final VerwerkPersoonResultaat selectieResultaat = new VerwerkPersoonResultaat();
            selectieResultaat.setSelectieRunId(opdracht.getSelectieRunId());
            selectieResultaat.setPersoonslijst(persoonslijst);
            selectieResultaat.setAutorisatiebundel(autorisatiebundel);
            selectieResultaat.setSelectieTaakId(selectieAutorisatiebundel.getSelectieAutorisatieBericht().getSelectietaakId());
            if (verwerkPersoonBericht != null) {
                if (isDirecteVerzending(autorisatiebundel)) {
                    maakBericht(verwerkPersoonBericht, autorisatiebundel, selectieResultaat);
                } else {
                    //maakt een persoons deel fragment van selectie resultaat berichten. Altijd maar 1 bijgehouden persoon in selectie.
                    verwerkPersoonBericht.getBijgehoudenPersonen().stream().findFirst().ifPresent(b -> maakFragment(selectieResultaat, b, gbaConversieCache));
                }
            }
            verwerkPersoonResultaten.add(selectieResultaat);
        }
        return verwerkPersoonResultaten;
    }

    private void maakBericht(VerwerkPersoonBericht verwerkPersoonBericht, Autorisatiebundel autorisatiebundel, VerwerkPersoonResultaat selectieResultaat)
            throws StapException {
        final SynchronisatieBerichtGegevens synchronisatieBerichtGegevens = synchronisatieBerichtGegevensFactory
                .maak(verwerkPersoonBericht, autorisatiebundel, null);
        selectieResultaat.setSynchronisatieBerichtGegevens(synchronisatieBerichtGegevens);
    }

    private boolean isDirecteVerzending(Autorisatiebundel autorisatiebundel) {
        final Dienst dienst = autorisatiebundel.getDienst();
        return isAfnemerindicatieMetBericht(dienst) || isLeverwijzeBericht(dienst);
    }

    private boolean isLeverwijzeBericht(Dienst dienst) {
        return dienst.getLeverwijzeSelectie()
                != null && LeverwijzeSelectie.BERICHT.getId() == dienst.getLeverwijzeSelectie();
    }

    private boolean isAfnemerindicatieMetBericht(Dienst dienst) {
        return dienst.getSoortSelectie() != SoortSelectie.STANDAARD_SELECTIE.getId() && Boolean.TRUE
                .equals(dienst.getIndVerzVolBerBijWijzAfniNaSelectie());
    }

    private void maakFragment(final VerwerkPersoonResultaat selectieResultaat, final BijgehoudenPersoon bijgehoudenPersoon, ConversieCache gbaConversieCache) {
        if (selectieResultaat.getAutorisatiebundel().getLeveringsautorisatie().getStelsel() == Stelsel.GBA) {
            LOG.info("Fragment voor GBA maken voor verzending via bestand");
            // Converteer
            final Bericht sv01Bericht = gbaBerichtFactory.maakSv01Bericht(selectieResultaat.getPersoonslijst());
            sv01Bericht.converteerNaarLo3(gbaConversieCache);

            // Filter
            final List<String> gbaFilterRubrieken = gbaFilterRubriekRepository.haalLo3FilterRubriekenVoorDienstbundel(
                    selectieResultaat.getAutorisatiebundel().getDienst().getDienstbundel().getId());
            sv01Bericht.filterRubrieken(gbaFilterRubrieken);

            // Registreer bericht
            final String sv01 = sv01Bericht.maakUitgaandBericht();
            LOG.info("SV01: {}", sv01);
            selectieResultaat.setPersoonFragment(sv01);
        } else {
            final List<Melding> meldingen = meldingBepalerService.geefWaarschuwingen(bijgehoudenPersoon);
            final String fragmentString = maakSelectiePersoonBerichtService.maakPersoonFragment(bijgehoudenPersoon, meldingen);
            selectieResultaat.setPersoonFragment(fragmentString);
        }
    }

    private Set<SelectieAutorisatiebundel> bepaalInSelectie(final MaakSelectieResultaatOpdracht opdracht) {
        final Collection<SelectieAutorisatiebundel> autorisatiebundels = opdracht.getAutorisatiebundels();
        final Persoonslijst persoonslijst = opdracht.getPersoonslijst();
        final Set<SelectieAutorisatiebundel> autorisatiebundelsInSelectie = new HashSet<>();
        for (SelectieAutorisatiebundel autorisatiebundel : autorisatiebundels) {
            if (selectieBepaalLeverenService
                    .inSelectie(persoonslijst, autorisatiebundel.getAutorisatiebundel(), opdracht.getSelectieStartDatum(), opdracht.getSelectieRunId(),
                            autorisatiebundel.getSelectieAutorisatieBericht().isLijstGebruiken(),
                            autorisatiebundel.getSelectieAutorisatieBericht().getSelectietaakId())) {
                LOG.info("VerwerkPersoonServiceImpl.verwerk; bepaalInSelectie: is in selectie");
                autorisatiebundelsInSelectie.add(autorisatiebundel);
            }
        }
        return autorisatiebundelsInSelectie;
    }
}
