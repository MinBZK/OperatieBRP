/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.afnemerindicatie;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.archivering.service.algemeen.ArchiefService;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.OnderhoudAfnemerindicatieAntwoordBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.algemeen.util.MeldingUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link OnderhoudAfnemerindicatieService}
 */
@Bedrijfsregel(Regel.R1269)
@Service
final class OnderhoudAfnemerindicatieServiceImpl implements OnderhoudAfnemerindicatieService {

    @Inject
    private ArchiefService archiveringService;
    @Inject
    private OnderhoudAfnemerindicatieSynchroonBerichtFactory onderhoudAfnemerindicatieSynchroonBerichtFactory;
    @Inject
    private OnderhoudAfnemerindicatie.AfnemerindicatieVerzoekService afnemerindicatieVerzoekService;


    private OnderhoudAfnemerindicatieServiceImpl() {
    }

    @Override
    @Transactional(transactionManager = "masterTransactionManager")
    public void onderhoudAfnemerindicatie(final AfnemerindicatieVerzoek verzoek, final RegistreerAfnemerindicatieCallback<String>
            registreerAfnemerindicatieCallback) {
        final OnderhoudResultaat verzoekResultaat = afnemerindicatieVerzoekService.verwerkVerzoek(verzoek);
        final OnderhoudAfnemerindicatieAntwoordBericht antwoordBericht = onderhoudAfnemerindicatieSynchroonBerichtFactory
                .maakAntwoordBericht(verzoekResultaat);
        registreerAfnemerindicatieCallback.verwerkResultaat(verzoek.getSoortDienst(), antwoordBericht);
        archiveer(verzoek, verzoekResultaat, registreerAfnemerindicatieCallback.getResultaat());
    }

    private void archiveer(final AfnemerindicatieVerzoek verzoek, final OnderhoudResultaat verzoekResultaat, final String antwoordBericht) {
        final Autorisatiebundel autorisatiebundel = verzoekResultaat.getAutorisatiebundel();
        if (!slaArchiveringOver(verzoek, autorisatiebundel)) {

            //INGAAND
            final ArchiveringOpdracht dtoIn = new ArchiveringOpdracht(Richting.INGAAND, ZonedDateTime.now());
            dtoIn.setData(verzoek.getXmlBericht());
            dtoIn.setReferentienummer(verzoek.getStuurgegevens().getReferentieNummer());
            dtoIn.setZendendeSysteem(verzoek.getStuurgegevens().getZendendSysteem());
            dtoIn.setSoortBericht(SoortBericht.LVG_SYN_REGISTREER_AFNEMERINDICATIE);
            if (autorisatiebundel != null) {
                dtoIn.setDienstId(autorisatiebundel.getDienst() == null ? null : autorisatiebundel.getDienst().getId());
                dtoIn.setLeveringsAutorisatieId(autorisatiebundel.getLeveringsautorisatieId());
                dtoIn.setZendendePartijId(autorisatiebundel.getPartij() == null ? null : autorisatiebundel.getPartij().getId());
            }
            dtoIn.setTijdstipOntvangst(BrpNu.get().getDatum());
            dtoIn.setTijdstipVerzending(verzoek.getStuurgegevens().getTijdstipVerzending());
            archiveringService.archiveer(dtoIn);

            //UITGAAND
            final ArchiveringOpdracht dtoUit = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
            if (verzoekResultaat.getPersoonslijst() != null) {
                dtoUit.addTeArchiverenPersoon(verzoekResultaat.getPersoonslijst().getId());
            }
            dtoUit.setData(antwoordBericht);
            dtoUit.setTijdstipVerzending(verzoekResultaat.getTijdstipVerzending());
            dtoUit.setSoortBericht(SoortBericht.LVG_SYN_REGISTREER_AFNEMERINDICATIE_R);
            dtoUit.setReferentienummer(verzoekResultaat.getReferentienummerAntwoordbericht());
            dtoUit.setCrossReferentienummer(verzoek.getStuurgegevens().getReferentieNummer());
            dtoUit.setLeveringsAutorisatieId(
                    autorisatiebundel == null ? null : autorisatiebundel.getToegangLeveringsautorisatie().getLeveringsautorisatie().getId());
            dtoUit.setVerwerkingsresultaat(verzoekResultaat.getMeldingList().isEmpty() ? VerwerkingsResultaat.GESLAAGD : VerwerkingsResultaat.FOUTIEF);
            dtoUit.setHoogsteMeldingsNiveau(MeldingUtil.bepaalHoogsteMeldingNiveau(verzoekResultaat.getMeldingList()));
            dtoUit.setTijdstipVerzending(verzoekResultaat.getTijdstipVerzending());
            dtoUit.setZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM);
            dtoUit.setZendendePartijId(verzoekResultaat.getBrpPartij().getId());
            dtoUit.setOntvangendePartijId(autorisatiebundel != null && autorisatiebundel.getPartij() != null ? autorisatiebundel.getPartij().getId() : null);
            archiveringService.archiveer(dtoUit);
        }
    }

    /**
     * Bepaalt op basis van het verzoek en de autorisatiebundel of de archivering van het verzoek en antwoord bericht overgeslagen moet worden. Dit overslaan
     * betreft alleen GBA berichten. GBA berichten worden gearchiveerd via de VOISC; indien er iets misgaat in de service, dan worden verzoek en antwoord voor
     * GBA ook niet gearchiveerd aangezien dit door het proces wordt afgehandeld.
     * @param verzoek Het afnemerindicatie verzoek
     * @param autorisatiebundel De autorisatiebundel
     * @return True indien de archivering overgeslagen dient te worden, false in de overige gevallen.
     */
    private boolean slaArchiveringOver(AfnemerindicatieVerzoek verzoek, Autorisatiebundel autorisatiebundel) {
        // Indien iets in de service misgaat zal de autorisatiebundel niet gevuld zijn, in dat geval controleren we op het zendendsysteem.
        return (autorisatiebundel == null && Stelsel.GBA.getNaam().equals(verzoek.getStuurgegevens().getZendendSysteem())) ||
                (autorisatiebundel != null && autorisatiebundel.getLeveringsautorisatie().getStelsel() == Stelsel.GBA);
    }
}
