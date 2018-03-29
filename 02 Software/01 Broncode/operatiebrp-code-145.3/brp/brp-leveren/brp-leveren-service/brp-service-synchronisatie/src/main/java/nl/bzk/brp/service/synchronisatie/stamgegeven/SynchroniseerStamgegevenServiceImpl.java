/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie.stamgegeven;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.archivering.service.algemeen.ArchiefService;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.SynchroniseerStamgegevenBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;
import org.springframework.stereotype.Service;

/**
 * Service implementatie voor synchronisatie stamgegeven.
 */
@Service
final class SynchroniseerStamgegevenServiceImpl implements SynchroniseerStamgegevenService {

    @Inject
    private SynchroniseerStamgegeven.BepaalStamgegevenService bepaalStamgegevenService;
    @Inject
    private ArchiefService archiveringService;
    @Inject
    private SynchroniseerStamgegevenBerichtFactory synchroniseerStamgegevenBerichtFactory;

    private SynchroniseerStamgegevenServiceImpl() {

    }

    @Override
    public void maakResponse(final SynchronisatieVerzoek verzoek, final SynchroniseerStamgegevenCallback<String> stamgegevenCallback) {
        //valideer de regels resultaat object is nooit null
        final BepaalStamgegevenResultaat resultaat = bepaalStamgegevenService.maakResultaat(verzoek);

        final SynchroniseerStamgegevenBericht stamgegevenBericht = synchroniseerStamgegevenBerichtFactory.maakBericht(resultaat);
        //maak het response bericht, dit is nooit null, excepties worden omhoog gegooid
        stamgegevenCallback.verwerkResultaat(stamgegevenBericht);

        final MaakAntwoordBerichtResultaat antwoordBericht = new MaakAntwoordBerichtResultaat(
                stamgegevenCallback.getResultaat(),
                stamgegevenBericht.getBasisBerichtGegevens().getStuurgegevens().getTijdstipVerzending(),
                stamgegevenBericht.getBasisBerichtGegevens().getStuurgegevens().getReferentienummer()
        );
        archiveer(verzoek, resultaat, antwoordBericht);
    }

    private void archiveer(final SynchronisatieVerzoek verzoek, final BepaalStamgegevenResultaat resultaat,
                           final MaakAntwoordBerichtResultaat antwoordBericht) {
        final Autorisatiebundel autorisatiebundel = resultaat.getAutorisatiebundel();
        //INGAAND
        final ArchiveringOpdracht dtoIn = new ArchiveringOpdracht(Richting.INGAAND, ZonedDateTime.now());
        dtoIn.setData(verzoek.getXmlBericht());
        dtoIn.setReferentienummer(verzoek.getStuurgegevens().getReferentieNummer());
        dtoIn.setZendendeSysteem(verzoek.getStuurgegevens().getZendendSysteem());
        dtoIn.setSoortBericht(SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_STAMGEGEVEN);
        if (autorisatiebundel != null) {
            dtoIn.setDienstId(autorisatiebundel.getDienst() == null ? null : autorisatiebundel.getDienst().getId());
            dtoIn.setLeveringsAutorisatieId(autorisatiebundel.getLeveringsautorisatieId());
            dtoIn.setZendendePartijId(autorisatiebundel.getPartij() == null ? null : autorisatiebundel.getPartij().getId());
        }
        dtoIn.setTijdstipVerzending(verzoek.getStuurgegevens().getTijdstipVerzending());
        dtoIn.setTijdstipOntvangst(BrpNu.get().getDatum());
        archiveringService.archiveer(dtoIn);

        //UITGAAND
        final ArchiveringOpdracht dtoUit = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        dtoUit.setData(antwoordBericht.getXml());
        dtoUit.setTijdstipVerzending(antwoordBericht.getDatumVerzending());
        dtoUit.setSoortBericht(SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_STAMGEGEVEN_R);
        dtoUit.setReferentienummer(antwoordBericht.getReferentienummerAttribuutAntwoord());
        dtoUit.setCrossReferentienummer(verzoek.getStuurgegevens().getReferentieNummer());
        dtoUit.setLeveringsAutorisatieId(
                autorisatiebundel == null ? null : autorisatiebundel.getToegangLeveringsautorisatie().getLeveringsautorisatie().getId());
        dtoUit.setVerwerkingsresultaat(resultaat.getMeldingList().isEmpty() ? VerwerkingsResultaat.GESLAAGD : VerwerkingsResultaat.FOUTIEF);
        dtoUit.setZendendePartijId(resultaat.getBrpPartij().getId());
        dtoUit.setZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM);
        dtoUit.setOntvangendePartijId(autorisatiebundel != null && autorisatiebundel.getPartij() != null ? autorisatiebundel.getPartij().getId() : null);
        archiveringService.archiveer(dtoUit);
    }
}
