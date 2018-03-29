/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.verzending;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.internbericht.bijhoudingsnotificatie.BijhoudingsplanNotificatieBericht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.domain.internbericht.vrijbericht.VrijBerichtGegevens;
import org.springframework.stereotype.Service;

/**
 * Implementatie voor het uitvoeren van de verzend stappen.
 */
@Service
@Bedrijfsregel(Regel.R1991)
final class VerzendingServiceImpl implements Verzending.VerzendingService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private Verzending.ArchiveerBerichtStap archiveerBerichtStap;
    @Inject
    private Verzending.ProtocolleringService protocolleerStap;
    @Inject
    private Verzending.BrpStelselService brpStelselService;
    @Inject
    private Verzending.GBAStelselService gbaStelselService;

    private VerzendingServiceImpl() {

    }

    @Override
    public void verwerkSynchronisatieBericht(final SynchronisatieBerichtGegevens berichtGegevens) {

        final int partijId = berichtGegevens.getArchiveringOpdracht().getOntvangendePartijId();
        LOGGER.debug("Verrijk bericht stap voltooid voor afnemer '{}'", partijId);

        // Alleen archiveren indien het stelsel BRP is. Voor GBA wordt dit via de VOISC gearchiveerd.
        if (Stelsel.BRP == berichtGegevens.getStelsel()) {
            archiveerBerichtStap.archiveerSynchronisatieBericht(berichtGegevens.getArchiveringOpdracht());
            LOGGER.debug("Archiveer stap voltooid voor afnemer '{}'", partijId);
        }

        protocolleerStap.verwerkProtocollering(berichtGegevens);
        LOGGER.debug("Protocolleer stap voltooid voor afnemer '{}'", partijId);

        switch (berichtGegevens.getStelsel()) {
            case BRP:
                brpStelselService.verzendSynchronisatieBericht(berichtGegevens);
                LOGGER.debug("Verzend BRP stap voltooid voor afnemer '{}'", partijId);
                break;
            case GBA:
                gbaStelselService.verzendLo3Bericht(berichtGegevens);
                LOGGER.debug("Verzend LO3 stap voltooid voor afnemer '{}'", partijId);
                break;
            default:
                LOGGER.warn("Onbekend JMSType voor synchronisatiebericht; geen verzend stap aangeroepen");
        }
    }

    @Override
    public void verwerkBijhoudingsNotificatieBericht(final BijhoudingsplanNotificatieBericht berichtgegevens) {
        archiveerBerichtStap.archiveerBijhoudingsNotificatieBericht(berichtgegevens);
        LOGGER.debug("Archiveer stap voltooid voor bijhoudingsnotificatiebericht");

        final Stelsel stelsel = "Bijhoudingsysteem".equals(berichtgegevens.getOntvangendeSysteem()) ? Stelsel.BRP : Stelsel.GBA;
        switch (stelsel) {
            case BRP:
                brpStelselService.verzendBijhoudingsNotificatieBericht(berichtgegevens);
                LOGGER.debug("Verzend BRP stap voltooid voor notificatiebericht");
                break;
            case GBA:
                // naar specifieke route voor GBA
                break;
            default:
                LOGGER.warn("Onbekend JMSType voor notificatiebericht; geen verzend stap aangeroepen");
        }
    }

    @Override
    public void verwerkVrijBericht(final VrijBerichtGegevens berichtGegevens) {
        final int partijCode = berichtGegevens.getArchiveringOpdracht().getOntvangendePartijId();

        archiveerBerichtStap.archiveerVrijBericht(berichtGegevens.getArchiveringOpdracht());
        LOGGER.debug("Archiveer stap voltooid voor afnemer '{}'", partijCode);

        switch (berichtGegevens.getStelsel()) {
            case BRP:
                brpStelselService.verzendVrijBericht(berichtGegevens);
                LOGGER.debug("Verzend BRP stap voltooid voor vrij bericht '{}'", partijCode);
                break;
            case GBA:
                gbaStelselService.verzendVrijBericht(berichtGegevens);
                LOGGER.debug("Verzend LO3 stap voltooid voor vrij bericht '{}'", partijCode);
                break;
            default:
                LOGGER.warn("Onbekend JMSType voor synchronisatiebericht; geen verzend stap aangeroepen");
        }
    }

}
