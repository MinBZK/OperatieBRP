/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.algemeen;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.archivering.service.algemeen.ArchiefService;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.algemeen.Mappings;
import nl.bzk.brp.service.algemeen.util.MeldingUtil;
import org.springframework.stereotype.Service;

/**
 * Abstracte basis voor het archiveren van bevraging berichten.
 * @param <V> bevragingverzoek
 * @param <R> bevragingresultaat
 */
@Service
final class ArchiveerBerichtServiceImpl<V extends BevragingVerzoek, R extends BevragingResultaat> implements Bevraging.ArchiveerBerichtService<V, R> {

    @Inject
    private ArchiefService archiefService;

    private ArchiveerBerichtServiceImpl() {
    }

    @Override
    public void archiveer(final V bevragingVerzoek, final R berichtResultaat, final AntwoordBerichtResultaat antwoordBerichtResultaat) {
        final Autorisatiebundel autorisatiebundel = berichtResultaat.getAutorisatiebundel();

        final ArchiveringOpdracht archiveringOpdrachtIn = new ArchiveringOpdracht(Richting.INGAAND, ZonedDateTime.now());
        archiveringOpdrachtIn.setData(bevragingVerzoek.getXmlBericht());
        archiveringOpdrachtIn.setReferentienummer(bevragingVerzoek.getStuurgegevens().getReferentieNummer());
        archiveringOpdrachtIn.setZendendeSysteem(bevragingVerzoek.getStuurgegevens().getZendendSysteem());
        archiveringOpdrachtIn.setTijdstipVerzending(bevragingVerzoek.getStuurgegevens().getTijdstipVerzending());
        archiveringOpdrachtIn.setTijdstipOntvangst(BrpNu.get().getDatum());
        archiveringOpdrachtIn.setSoortBericht(getSoortBerichtIn(bevragingVerzoek.getSoortDienst()));
        if (autorisatiebundel != null) {
            if (bevragingVerzoek.getParameters().getRolNaam() != null) {
                archiveringOpdrachtIn.setRolId(bepaalRol(
                        autorisatiebundel.getPartij().getPartijRolSet(),
                        bevragingVerzoek.getParameters().getRolNaam()));
            }
            archiveringOpdrachtIn.setDienstId(autorisatiebundel.getDienst() == null ? null : autorisatiebundel.getDienst().getId());
            archiveringOpdrachtIn.setLeveringsAutorisatieId(autorisatiebundel.getLeveringsautorisatieId());
            archiveringOpdrachtIn.setZendendePartijId(autorisatiebundel.getPartij() == null ? null : autorisatiebundel.getPartij().getId());
        }
        archiefService.archiveer(archiveringOpdrachtIn);

        final ArchiveringOpdracht archiveringOpdrachtUit = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        bepaalTeArchiverenPersonen(berichtResultaat).forEach(archiveringOpdrachtUit::addTeArchiverenPersoon);
        archiveringOpdrachtUit.setData(antwoordBerichtResultaat.getXml());
        archiveringOpdrachtUit.setTijdstipVerzending(antwoordBerichtResultaat.getDatumVerzending());
        archiveringOpdrachtUit.setSoortBericht(getSoortBerichtUit(bevragingVerzoek.getSoortDienst()));
        archiveringOpdrachtUit.setReferentienummer(antwoordBerichtResultaat.getReferentienummerAttribuutAntwoord());
        archiveringOpdrachtUit.setCrossReferentienummer(bevragingVerzoek.getStuurgegevens().getReferentieNummer());
        archiveringOpdrachtUit
                .setVerwerkingsresultaat(antwoordBerichtResultaat.getMeldingList().isEmpty() ? VerwerkingsResultaat.GESLAAGD : VerwerkingsResultaat.FOUTIEF);
        archiveringOpdrachtUit.setHoogsteMeldingsNiveau(MeldingUtil.bepaalHoogsteMeldingNiveau(antwoordBerichtResultaat.getMeldingList()));
        archiveringOpdrachtUit.setLeveringsAutorisatieId(
                autorisatiebundel == null ? null : autorisatiebundel.getToegangLeveringsautorisatie().getLeveringsautorisatie().getId());
        archiveringOpdrachtUit.setZendendePartijId(berichtResultaat.getBrpPartij().getId());
        archiveringOpdrachtUit.setZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM);
        archiveringOpdrachtUit
                .setOntvangendePartijId(autorisatiebundel != null && autorisatiebundel.getPartij() != null ? autorisatiebundel.getPartij().getId() : null);
        archiefService.archiveer(archiveringOpdrachtUit);
    }

    /**
     * @param berichtResultaat berichtResultaat
     * @return te archiveren personen set
     */
    private Set<Long> bepaalTeArchiverenPersonen(final R berichtResultaat) {
        if (berichtResultaat.getBericht() != null && !berichtResultaat.getBericht().isLeeg()) {
            return berichtResultaat.getBericht().getBijgehoudenPersonen().stream().map(p -> p.getPersoonslijst().getId()).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    /**
     * @return soort bericht in
     */
    private SoortBericht getSoortBerichtIn(final SoortDienst soortDienst) {
        return Mappings.getBevragingSoortBerichten(soortDienst).getInkomendBericht();
    }

    /**
     * @return soort bericht uit
     */
    private SoortBericht getSoortBerichtUit(final SoortDienst soortDienst) {
        return Mappings.getBevragingSoortBerichten(soortDienst).getUitgaandBericht();
    }

    private Integer bepaalRol(final Set<PartijRol> rollen, final String rolNaam) {
        final Predicate<PartijRol> predicate = r -> r.getRol().getNaam().equals(rolNaam);
        final Optional<PartijRol> optional = rollen.stream().filter(predicate).findFirst();
        Integer rolId = null;
        if (optional.isPresent()) {
            rolId = optional.get().getRol().getId();
        }
        return rolId;
    }
}
