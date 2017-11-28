/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.stuf;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.archivering.service.algemeen.ArchiefService;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.Bericht;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.algemeen.util.MeldingUtil;
import org.springframework.stereotype.Service;

@Service
final class StufBerichtArchiveringServiceImpl implements StufBerichtArchiveringService {

    @Inject
    private ArchiefService archiefService;
    @Inject
    private PartijService partijService;

    private StufBerichtArchiveringServiceImpl() {
    }

    @Override
    public void archiveer(final StufBerichtResultaat berichtResultaat,
                          final Bericht antwoordBericht, final String resultaat) {
        final StufBerichtVerzoek verzoek = berichtResultaat.getVerzoek();
        final Autorisatiebundel autorisatiebundel = berichtResultaat.getAutorisatiebundel();
        //Ingaand
        final ArchiveringOpdracht opdrachtIngaandBericht = new ArchiveringOpdracht(Richting.INGAAND, ZonedDateTime.now());
        opdrachtIngaandBericht.setSoortBericht(SoortBericht.STV_STV_GEEF_STUFBG_BERICHT);
        opdrachtIngaandBericht.setZendendeSysteem(verzoek.getStuurgegevens().getZendendSysteem());
        opdrachtIngaandBericht.setReferentienummer(verzoek.getStuurgegevens().getReferentieNummer());
        opdrachtIngaandBericht.setTijdstipVerzending(verzoek.getStuurgegevens().getTijdstipVerzending());
        opdrachtIngaandBericht.setTijdstipOntvangst(BrpNu.get().getDatum());
        opdrachtIngaandBericht.setData(verzoek.getXmlBericht());
        if (autorisatiebundel != null) {
            opdrachtIngaandBericht.setLeveringsAutorisatieId(autorisatiebundel.getLeveringsautorisatieId());
            opdrachtIngaandBericht.setZendendePartijId(autorisatiebundel.getPartij() == null ? null : autorisatiebundel.getPartij().getId());
        }
        archiefService.archiveer(opdrachtIngaandBericht);

        //Uitgaand
        final ArchiveringOpdracht opdrachtUitgaandResultaat = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        opdrachtUitgaandResultaat.setSoortBericht(SoortBericht.STV_STV_GEEF_STUFBG_BERICHT_R);
        opdrachtUitgaandResultaat.setZendendePartijId(partijService.geefBrpPartij().getId());
        opdrachtUitgaandResultaat.setZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM);
        opdrachtUitgaandResultaat.setReferentienummer(antwoordBericht.getBasisBerichtGegevens().getStuurgegevens().getReferentienummer());
        opdrachtUitgaandResultaat.setCrossReferentienummer(antwoordBericht.getBasisBerichtGegevens().getStuurgegevens().getCrossReferentienummer());
        opdrachtUitgaandResultaat.setData(resultaat);
        opdrachtUitgaandResultaat.setTijdstipVerzending(antwoordBericht.getBasisBerichtGegevens().getStuurgegevens().getTijdstipVerzending());
        opdrachtUitgaandResultaat.setVerwerkingsresultaat(MeldingUtil.bepaalVerwerking(antwoordBericht.getBasisBerichtGegevens().getMeldingen()));
        opdrachtUitgaandResultaat.setHoogsteMeldingsNiveau(MeldingUtil.bepaalHoogsteMeldingNiveau(antwoordBericht.getBasisBerichtGegevens().getMeldingen()));
        opdrachtUitgaandResultaat
                .setOntvangendePartijId(autorisatiebundel != null && autorisatiebundel.getPartij() != null ? autorisatiebundel.getPartij().getId() : null);
        opdrachtUitgaandResultaat.setLeveringsAutorisatieId(
                autorisatiebundel == null ? null : autorisatiebundel.getToegangLeveringsautorisatie().getLeveringsautorisatie().getId());

        archiefService.archiveer(opdrachtUitgaandResultaat);
    }

}
