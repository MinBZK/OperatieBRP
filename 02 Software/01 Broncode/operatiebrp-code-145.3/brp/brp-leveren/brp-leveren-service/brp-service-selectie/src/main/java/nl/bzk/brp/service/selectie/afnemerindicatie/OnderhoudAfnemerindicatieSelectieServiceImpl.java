/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.afnemerindicatie;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.LeverwijzeSelectie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAfnemerindicatieTaak;
import nl.bzk.brp.domain.internbericht.selectie.SelectieFragmentSchrijfBericht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.AfnemerBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.PlaatsAfnemerBerichtService;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.VerstrekkingsbeperkingService;
import nl.bzk.brp.service.algemeen.afnemerindicatie.GeneriekeOnderhoudAfnemerindicatieStappen;
import nl.bzk.brp.service.algemeen.blob.AfnemerindicatieParameters;
import nl.bzk.brp.service.algemeen.blob.PersoonAfnemerindicatieService;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstService;
import nl.bzk.brp.service.cache.LeveringsAutorisatieCache;
import nl.bzk.brp.service.selectie.verwerker.SelectieSchrijfTaakPublicatieService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * OnderhoudAfnemerindicatieSelectieServiceImpl.
 */
@Service
@Bedrijfsregel(Regel.R2594)
@Bedrijfsregel(Regel.R2593)
@Bedrijfsregel(Regel.R2591)
final class OnderhoudAfnemerindicatieSelectieServiceImpl implements OnderhoudAfnemerindicatieSelectieService {
    private static final Logger LOG = LoggerFactory.getLogger();
    @Inject
    private LeveringsAutorisatieCache leveringsAutorisatieCache;
    @Inject
    private VerstrekkingsbeperkingService verstrekkingsbeperkingService;
    @Inject
    private PlaatsAfnemerBerichtService plaatsAfnemerBerichtService;
    @Inject
    private PersoonAfnemerindicatieService persoonAfnemerindicatieService;
    @Inject
    private GeneriekeOnderhoudAfnemerindicatieStappen.ValideerRegelsPlaatsing valideerRegelsPlaatsing;
    @Inject
    private GeneriekeOnderhoudAfnemerindicatieStappen.ValideerRegelsVerwijderen valideerRegelsVerwijderen;
    @Inject
    private PersoonslijstService persoonslijstService;
    @Inject
    private SelectieSchrijfTaakPublicatieService selectieSchrijfTaakPublicatieService;

    private OnderhoudAfnemerindicatieSelectieServiceImpl() {

    }

    @Override
    @Transactional(transactionManager = "masterTransactionManager")
    public List<OnderhoudAfnemerindicatieResultaat> verwerk(final Collection<SelectieAfnemerindicatieTaak> verzoeken) throws BlobException {
        if (verzoeken.isEmpty()) {
            return Lists.newArrayList();
        }
        final List<SelectieAfnemerindicatieTaak> verwerkteVerzoeken = Lists.newArrayList();
        final ZonedDateTime tijdstipRegistratie = DatumUtil.nuAlsZonedDateTime();
        final List<AfnemerBericht> teVersturenBerichten = Lists.newArrayList();
        final List<SelectieAfnemerindicatieTaak> teMakenBestanden = Lists.newArrayList();
        final Set<AfnemerindicatieParameters> afnemerindicatieParametersSet = new HashSet<>();
        final Map<Long, Persoonslijst> persoonslijstMap = new HashMap<>();
        for (SelectieAfnemerindicatieTaak verzoek : verzoeken) {
            //verzoeken kunnen dezelfde personen bevatten
            final Persoonslijst persoonslijst = persoonslijstMap.computeIfAbsent(verzoek.getPersoonId(), persoonslijstService::getById);
            if (persoonslijst == null) {
                throw new NullPointerException("persoonslijst kan niet null zijn hier");
            }
            final AfnemerindicatieParameters
                    afnemerindicatieParameters =
                    verwerkAfnemerindicatieVerzoek(tijdstipRegistratie, teVersturenBerichten, teMakenBestanden, verzoek, persoonslijst);
            if (afnemerindicatieParameters != null) {
                afnemerindicatieParametersSet.add(afnemerindicatieParameters);
                verwerkteVerzoeken.add(verzoek);
            }
        }
        if (!teVersturenBerichten.isEmpty()) {
            plaatsAfnemerBerichtService.plaatsAfnemerberichten(teVersturenBerichten);
        }
        final Map<Integer, SelectieFragmentSchrijfBericht> map = Maps.newHashMap();
        if (!teMakenBestanden.isEmpty()) {
            for (SelectieAfnemerindicatieTaak selectieAfnemerindicatieTaak : teMakenBestanden) {
                final SelectieFragmentSchrijfBericht bericht = map.computeIfAbsent(selectieAfnemerindicatieTaak.getSelectieTaakId(), i ->
                        maakBericht(selectieAfnemerindicatieTaak));
                bericht.getBerichten().add(selectieAfnemerindicatieTaak.getBericht());
                bericht.getProtocolleringPersonen()
                        .put(selectieAfnemerindicatieTaak.getPersoonId(), selectieAfnemerindicatieTaak.getTijdstipLaatsteWijziging());
            }

            selectieSchrijfTaakPublicatieService.publiceerSchrijfTaken(new ArrayList<>(map.values()));
        }

        for (AfnemerindicatieParameters afnemerindicatieParameters : afnemerindicatieParametersSet) {
            persoonAfnemerindicatieService.updateAfnemerindicatieBlob(afnemerindicatieParameters);
        }
        return verwerkteVerzoeken.stream().map(v -> new OnderhoudAfnemerindicatieResultaat(v.getSelectieTaakId(), map.containsKey(v.getSelectieTaakId())))
                .collect(
                        Collectors.toList());
    }

    private SelectieFragmentSchrijfBericht maakBericht(SelectieAfnemerindicatieTaak selectieAfnemerindicatieTaak) {
        final SelectieFragmentSchrijfBericht bericht = new SelectieFragmentSchrijfBericht();
        bericht.setSelectieRunId(selectieAfnemerindicatieTaak.getSelectieRunId());
        bericht.setDienstId(selectieAfnemerindicatieTaak.getDienstId());
        bericht.setToegangLeveringsAutorisatieId(selectieAfnemerindicatieTaak.getToegangLeveringsautorisatieId());
        bericht.setSelectietaakId(selectieAfnemerindicatieTaak.getSelectieTaakId());
        bericht.setSelectietaakDatumUitvoer(selectieAfnemerindicatieTaak.getSelectietaakDatumUitvoer());
        return bericht;
    }


    private AfnemerindicatieParameters verwerkAfnemerindicatieVerzoek(final ZonedDateTime tijdstipRegistratie, final List<AfnemerBericht> teVersturenBerichten,
                                                                      final List<SelectieAfnemerindicatieTaak> teMakenBestanden,
                                                                      final SelectieAfnemerindicatieTaak verzoek,
                                                                      Persoonslijst persoonslijst) {
        final ToegangLeveringsAutorisatie
                toegangLeveringsAutorisatie =
                leveringsAutorisatieCache.geefToegangLeveringsautorisatie(verzoek.getToegangLeveringsautorisatieId());
        final Dienst dienst = AutAutUtil.zoekDienst(toegangLeveringsAutorisatie.getLeveringsautorisatie(), verzoek.getDienstId());
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(toegangLeveringsAutorisatie, dienst);
        final Partij partij = autorisatiebundel.getToegangLeveringsautorisatie().getGeautoriseerde().getPartij();
        final AfnemerindicatieParameters afnemerindicatieParameters =
                new AfnemerindicatieParameters(verzoek.getPersoonId(), persoonslijst.getPersoonLockVersie(), persoonslijst.getAfnemerindicatieLockVersie());
        boolean berichtSturen = Boolean.TRUE.equals(dienst.getIndVerzVolBerBijWijzAfniNaSelectie());
        try {
            if (verzoek.getSoortSelectie() == SoortSelectie.SELECTIE_MET_PLAATSING_AFNEMERINDICATIE) {
                valideerRegelsPlaatsing.valideer(
                        new GeneriekeOnderhoudAfnemerindicatieStappen.ValideerPlaatsAfnemerindicatieParams(toegangLeveringsAutorisatie, persoonslijst, null,
                                null));
                persoonAfnemerindicatieService.plaatsAfnemerindicatie(afnemerindicatieParameters, partij,
                        toegangLeveringsAutorisatie.getLeveringsautorisatie().getId(), dienst.getId(),
                        null, null, tijdstipRegistratie);
            } else {
                valideerRegelsVerwijderen.valideer(toegangLeveringsAutorisatie, persoonslijst);
                persoonAfnemerindicatieService.verwijderAfnemerindicatie(afnemerindicatieParameters, partij, dienst.getId(),
                        toegangLeveringsAutorisatie.getLeveringsautorisatie().getId());
                // Indien persoon verstrekkingsbeperking heeft (alleen van toepassing op vewijderen, want anders niet in
                // selectie) niet sturen
                berichtSturen &= verstrekkingsbeperkingValide(persoonslijst, autorisatiebundel, partij);
            }
            //kijk of we bericht moeten sturen.
            if (berichtSturen && verzoek.getSynchronisatieBerichtGegevens() != null) {
                teVersturenBerichten.add(new AfnemerBericht(verzoek.getSynchronisatieBerichtGegevens(), toegangLeveringsAutorisatie));
            }
            //kijk of we nog een bestand bericht moeten aanmaken
            if (dienst.getLeverwijzeSelectie() != null && LeverwijzeSelectie.STANDAARD.getId() == dienst.getLeverwijzeSelectie()
                    && verzoek.getBericht() != null) {
                teMakenBestanden.add(verzoek);
            }
            return afnemerindicatieParameters;
        } catch (StapMeldingException e) {
            LOG.debug("afnemerindicatie niet verwerkt, regel validatie", e);
            logNietVerwerkt(persoonslijst, e.getMessage());
        } catch (OptimisticLockException e) {
            LOG.debug("afnemerindicatie of persoon gewijzigd tijdens verwerking batch", e);
            logNietVerwerkt(persoonslijst, e.getMessage());
        }
        return null;
    }

    private boolean verstrekkingsbeperkingValide(Persoonslijst persoonslijst, Autorisatiebundel autorisatiebundel, Partij partij) {
        return autorisatiebundel.getDienst().getSoortSelectie() != SoortSelectie.SELECTIE_MET_VERWIJDERING_AFNEMERINDICATIE.getId()
                || !verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(persoonslijst, partij);
    }

    private void logNietVerwerkt(Persoonslijst persoonslijst, String message) {
        LOG.info("Afnemerindicatie voor kon niet geplaatst/verwijderd worden [{}]. Melding: {}",
                persoonslijst.getMetaObject().getObjectsleutel(), message);
    }
}
