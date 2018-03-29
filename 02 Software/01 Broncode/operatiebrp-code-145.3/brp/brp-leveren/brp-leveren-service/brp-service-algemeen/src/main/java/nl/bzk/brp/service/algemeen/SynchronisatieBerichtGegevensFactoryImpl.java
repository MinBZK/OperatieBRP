/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import java.time.ZonedDateTime;
import java.util.stream.Collectors;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.protocollering.domain.algemeen.ProtocolleringOpdracht;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * SynchronisatieBerichtGegevensFactoryImpl.
 */
@Component
public final class SynchronisatieBerichtGegevensFactoryImpl implements SynchronisatieBerichtGegevensFactory {

    private static final int START_GBA_BERICHTTYPE = 8;
    private static final int EINDE_GBA_BERICHTTYPE = 12;
    @Inject
    private BepaalGeleverdePersonenService bepaalGeleverdePersonenService;
    @Autowired
    private MaakPersoonBerichtService maakPersoonBerichtService;
    @Autowired
    private MaakPersoonBerichtService maakGbaPersoonBerichtService;

    private SynchronisatieBerichtGegevensFactoryImpl() {
    }

    @Override
    public SynchronisatieBerichtGegevens maak(final VerwerkPersoonBericht bericht, final Autorisatiebundel autorisatiebundel,
                                              final Integer datumAanvangMaterielePeriode) throws StapException {

        final String inhoudelijkBericht;
        if (Stelsel.GBA == autorisatiebundel.getLeveringsautorisatie().getStelsel()) {
            inhoudelijkBericht = maakGbaPersoonBerichtService.maakPersoonBericht(bericht);
        } else {
            inhoudelijkBericht = maakPersoonBerichtService.maakPersoonBericht(bericht);
        }

        final SynchronisatieBerichtGegevens jsonBericht = maakJsonBericht(bericht, autorisatiebundel, datumAanvangMaterielePeriode, inhoudelijkBericht);

        if (Stelsel.GBA == autorisatiebundel.getLeveringsautorisatie().getStelsel()) {
            jsonBericht.getArchiveringOpdracht().setCrossReferentienummer(bericht.getBasisBerichtGegevens().getStuurgegevens().getCrossReferentienummer());
        }
        return jsonBericht;
    }

    /**
     * Zet het xmlbericht op een queue.
     */
    @Bedrijfsregel(Regel.R1615)
    @Bedrijfsregel(Regel.R1617)
    @Bedrijfsregel(Regel.R1618)
    @Bedrijfsregel(Regel.R1619)
    @Bedrijfsregel(Regel.R1620)
    private SynchronisatieBerichtGegevens maakJsonBericht(final VerwerkPersoonBericht bericht, final Autorisatiebundel autorisatiebundel,
                                                          final Integer datumAanvangMaterielePeriode, final String inhoudelijkBericht) {
        final BepaalGeleverdePersonenService.Resultaat resultaat = bepaalGeleverdePersonenService
                .bepaal(autorisatiebundel.getSoortDienst(), autorisatiebundel.getLeveringsautorisatieId(),
                        bericht.getBijgehoudenPersonen().stream().map(BijgehoudenPersoon::getPersoonslijst).collect(Collectors.toList()),
                        datumAanvangMaterielePeriode);

        final ProtocolleringOpdracht protocolleringOpdracht = maakProtocolleringOpdracht(bericht, autorisatiebundel, resultaat);
        final ArchiveringOpdracht archiveringOpdracht = maakArchiveringOpdracht(bericht, autorisatiebundel, inhoudelijkBericht, resultaat);

        final SynchronisatieBerichtGegevens.Builder params = SynchronisatieBerichtGegevens.builder()
                .metSoortDienst(autorisatiebundel.getDienst().getSoortDienst())
                .metStelsel(autorisatiebundel.getLeveringsautorisatie().getStelsel())
                .metProtocolleringsniveau(autorisatiebundel.getToegangLeveringsautorisatie().getLeveringsautorisatie().getProtocolleringsniveau())
                .metBrpEndpointURI(autorisatiebundel.getToegangLeveringsautorisatie().getAfleverpunt())
                .metProtocolleringOpdracht(protocolleringOpdracht)
                .metArchiveringOpdracht(archiveringOpdracht);

        return params.build();
    }

    private ArchiveringOpdracht maakArchiveringOpdracht(final VerwerkPersoonBericht bericht, final Autorisatiebundel autorisatiebundel,
                                                        final String inhoudelijkBericht, final BepaalGeleverdePersonenService.Resultaat resultaat) {
        final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        archiveringOpdracht.setSoortBericht(Stelsel.BRP == autorisatiebundel.getLeveringsautorisatie().getStelsel() ? SoortBericht.LVG_SYN_VERWERK_PERSOON
                : SoortBericht.parseIdentifier(inhoudelijkBericht.substring(START_GBA_BERICHTTYPE, EINDE_GBA_BERICHTTYPE)));
        archiveringOpdracht.setReferentienummer(bericht.getBasisBerichtGegevens().getStuurgegevens().getReferentienummer());
        archiveringOpdracht.setTijdstipVerzending(bericht.getBasisBerichtGegevens().getStuurgegevens().getTijdstipVerzending());
        archiveringOpdracht.setOntvangendePartijId(bericht.getBasisBerichtGegevens().getStuurgegevens().getOntvangendePartij().getId());
        archiveringOpdracht.setZendendePartijId(bericht.getBasisBerichtGegevens().getStuurgegevens().getZendendePartij().getId());
        archiveringOpdracht.setZendendeSysteem(bericht.getBasisBerichtGegevens().getStuurgegevens().getZendendeSysteem());
        archiveringOpdracht.setLeveringsAutorisatieId(autorisatiebundel.getLeveringsautorisatieId());
        archiveringOpdracht.setRolId(autorisatiebundel.getRol().getId());
        archiveringOpdracht.setData(inhoudelijkBericht);
        resultaat.getGeleverdePersoonIds().forEach(archiveringOpdracht::addTeArchiverenPersoon);
        archiveringOpdracht.setDienstId(autorisatiebundel.getDienst().getId());
        archiveringOpdracht.setSoortSynchronisatie(bericht.getBasisBerichtGegevens().getParameters().getSoortSynchronisatie());
        return archiveringOpdracht;
    }

    private ProtocolleringOpdracht maakProtocolleringOpdracht(final VerwerkPersoonBericht bericht, final Autorisatiebundel autorisatiebundel,
                                                              final BepaalGeleverdePersonenService.Resultaat resultaat) {
        final ProtocolleringOpdracht protocolleringOpdracht = new ProtocolleringOpdracht();
        protocolleringOpdracht.setDatumTijdEindeFormelePeriodeResultaat(bericht.getBasisBerichtGegevens().getStuurgegevens().getTijdstipVerzending());
        protocolleringOpdracht.setDienstId(autorisatiebundel.getDienst().getId());
        protocolleringOpdracht.setToegangLeveringsautorisatieId(autorisatiebundel.getToegangLeveringsautorisatie().getId());
        protocolleringOpdracht.setSoortSynchronisatie(bericht.getBasisBerichtGegevens().getParameters().getSoortSynchronisatie());
        protocolleringOpdracht.setSoortSynchronisatie(bericht.getBasisBerichtGegevens().getParameters().getSoortSynchronisatie());
        protocolleringOpdracht.setDatumTijdKlaarzettenLevering(bericht.getBasisBerichtGegevens().getStuurgegevens().getTijdstipVerzending());
        protocolleringOpdracht.setDatumAanvangMaterielePeriodeResultaat(resultaat.getDatumAanvangMaterielePeriodeResultaat());
        protocolleringOpdracht.setGeleverdePersonen(resultaat.getLeveringPersonen());
        return protocolleringOpdracht;
    }
}
