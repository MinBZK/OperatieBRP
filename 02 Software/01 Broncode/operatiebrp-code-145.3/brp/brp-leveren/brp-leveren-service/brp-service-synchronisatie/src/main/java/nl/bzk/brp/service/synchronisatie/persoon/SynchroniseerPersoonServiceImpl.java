/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie.persoon;

import java.time.ZonedDateTime;
import java.util.UUID;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.archivering.service.algemeen.ArchiefService;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.SynchroniseerPersoonAntwoordBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.algemeen.util.MeldingUtil;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * SynchroniseerPersoonServiceImpl.
 */
@Service
@Bedrijfsregel(Regel.R1982)
@Bedrijfsregel(Regel.R1268)
final class SynchroniseerPersoonServiceImpl implements SynchroniseerPersoonService {

    @Inject
    private ArchiefService archiveringService;
    @Inject
    private SynchroniseerPersoon.MaakSynchronisatieBerichtService synchronisatieBerichtService;
    @Inject
    private PartijService partijService;
    @Inject
    private SynchroniseerPersoonBerichtFactory synchroniseerPersoonBerichtFactory;

    private SynchroniseerPersoonServiceImpl() {
    }

    @Override
    @Transactional(transactionManager = "masterTransactionManager")
    public void synchroniseer(final SynchronisatieVerzoek verzoek, final SynchronisatieCallback<String> synchronisatieCallback) {
        //resultaat = nooit null, excepties zitten in resultaat.meldingen
        final MaakSynchronisatieBerichtResultaat resultaat = synchronisatieBerichtService.verwerkVerzoek(verzoek);
        //antwoordBerichtResultaat = nooit null, excepties zijn fatal
        final ZonedDateTime datumVerzending = DatumUtil.nuAlsZonedDateTime();
        final String refnrAttribuutAntwoord = UUID.randomUUID().toString();
        final Partij partijBrpvoorziening = partijService.geefBrpPartij();
        final SoortMelding hoogsteMeldingNiveau = MeldingUtil.bepaalHoogsteMeldingNiveau(resultaat.getMeldingList());
        final MaakAntwoordBerichtResultaat antwoordBerichtResultaat = new MaakAntwoordBerichtResultaat(datumVerzending, refnrAttribuutAntwoord,
                resultaat.getMeldingList(), partijBrpvoorziening,
                verzoek.getStuurgegevens().getReferentieNummer(), hoogsteMeldingNiveau);
        final SynchroniseerPersoonAntwoordBericht bericht = synchroniseerPersoonBerichtFactory.apply(antwoordBerichtResultaat);

        synchronisatieCallback.verwerkResultaat(bericht);
        archiveer(resultaat, antwoordBerichtResultaat, synchronisatieCallback.getResultaat());
    }

    private void archiveer(final MaakSynchronisatieBerichtResultaat asynchroonBerichtResultaat,
                           final MaakAntwoordBerichtResultaat antwoordbericht, final String resultaat) {
        final SynchronisatieVerzoek verzoek = asynchroonBerichtResultaat.getVerzoek();
        final Autorisatiebundel autorisatiebundel = asynchroonBerichtResultaat.getAutorisatiebundel();

        //INGAAND
        final ArchiveringOpdracht dtoIn = new ArchiveringOpdracht(Richting.INGAAND, ZonedDateTime.now());
        dtoIn.setData(verzoek.getXmlBericht());
        dtoIn.setReferentienummer(verzoek.getStuurgegevens().getReferentieNummer());
        dtoIn.setZendendeSysteem(verzoek.getStuurgegevens().getZendendSysteem());
        dtoIn.setSoortBericht(SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_PERSOON);
        if (autorisatiebundel != null) {
            dtoIn.setDienstId(autorisatiebundel.getDienst() == null ? null : autorisatiebundel.getDienst().getId());
            dtoIn.setLeveringsAutorisatieId(autorisatiebundel.getLeveringsautorisatie().getId());
            dtoIn.setZendendePartijId(autorisatiebundel.getPartij().getId());
        }
        dtoIn.setTijdstipVerzending(verzoek.getStuurgegevens().getTijdstipVerzending());
        dtoIn.setTijdstipOntvangst(BrpNu.get().getDatum());
        archiveringService.archiveer(dtoIn);

        //UITGAAND
        final ArchiveringOpdracht dtoUit = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        if (asynchroonBerichtResultaat.getPersoonslijst() != null) {
            dtoUit.addTeArchiverenPersoon(asynchroonBerichtResultaat.getPersoonslijst().getId());
        }
        dtoUit.setData(resultaat);
        dtoUit.setTijdstipVerzending(antwoordbericht.getDatumVerzending());
        dtoUit.setSoortBericht(SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_PERSOON_R);
        dtoUit.setReferentienummer(antwoordbericht.getReferentienummerAttribuutAntwoord());
        dtoUit.setCrossReferentienummer(verzoek.getStuurgegevens().getReferentieNummer());
        dtoUit.setVerwerkingsresultaat(antwoordbericht.getMeldingList().isEmpty() ? VerwerkingsResultaat.GESLAAGD : VerwerkingsResultaat.FOUTIEF);
        dtoUit.setHoogsteMeldingsNiveau(MeldingUtil.bepaalHoogsteMeldingNiveau(antwoordbericht.getMeldingList()));
        dtoUit.setLeveringsAutorisatieId(
                autorisatiebundel == null ? null : autorisatiebundel.getToegangLeveringsautorisatie().getLeveringsautorisatie().getId());
        dtoUit.setZendendePartijId(antwoordbericht.getPartijBrpvoorziening().getId());
        dtoUit.setZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM);
        dtoUit.setOntvangendePartijId(autorisatiebundel != null && autorisatiebundel.getPartij() != null ? autorisatiebundel.getPartij().getId() : null);
        archiveringService.archiveer(dtoUit);
    }
}
