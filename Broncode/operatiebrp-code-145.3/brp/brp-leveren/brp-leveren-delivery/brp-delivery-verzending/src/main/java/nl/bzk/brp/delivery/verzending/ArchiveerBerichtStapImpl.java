/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.verzending;

import com.google.common.collect.Maps;
import java.time.ZonedDateTime;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingswijze;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.MDC;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.archivering.service.algemeen.ArchiefService;
import nl.bzk.brp.domain.internbericht.bijhoudingsnotificatie.BijhoudingsplanNotificatieBericht;
import nl.bzk.brp.service.algemeen.logging.LeveringVeld;
import nl.bzk.brp.service.cache.PartijCache;
import org.springframework.stereotype.Component;


/**
 * Implementatie van InterneApi.ArchiveerBerichtStapImpl.
 */
@Component
final class ArchiveerBerichtStapImpl implements Verzending.ArchiveerBerichtStap {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private ArchiefService archiefService;
    @Inject
    private PartijCache partijCache;

    private ArchiveerBerichtStapImpl() {
    }

    @Override
    public void archiveerSynchronisatieBericht(final ArchiveringOpdracht archiveringOpdracht) {
        LOGGER.debug("Archiveer te verzenden synchronisatiebericht");
        LOGGER.info("Archiveren van uitgaand synchronisatiebericht mbt administratieve handeling id: [{}] naar leveringsautoriatie {}",
                archiveringOpdracht.getAdministratieveHandelingId(), archiveringOpdracht.getLeveringsAutorisatieId());
        final Map<String, String> mdcMap = Maps.newHashMap();
        mdcMap.put(LeveringVeld.MDC_ADMINISTRATIEVE_HANDELING.getVeld(), String.valueOf(archiveringOpdracht.getAdministratieveHandelingId()));
        MDC.voerUit(mdcMap, () -> archiefService.archiveer(archiveringOpdracht));
    }

    @Override
    public void archiveerBijhoudingsNotificatieBericht(final BijhoudingsplanNotificatieBericht bijhoudingsplanNotificatieBericht) {
        LOGGER.info("Archiveren van uitgaand bijhoudingsnotificatiebericht mbt partij : {}", bijhoudingsplanNotificatieBericht.getZendendePartijCode());
        final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        archiveringOpdracht.setSoortBericht(SoortBericht.BHG_SYS_VERWERK_BIJHOUDINGSPLAN);
        archiveringOpdracht.setOntvangendePartijId(partijCache.geefPartij(bijhoudingsplanNotificatieBericht.getOntvangendePartijCode()).getId());
        archiveringOpdracht.setZendendePartijId(partijCache.geefPartij(bijhoudingsplanNotificatieBericht.getZendendePartijCode()).getId());
        archiveringOpdracht.setZendendeSysteem(bijhoudingsplanNotificatieBericht.getZendendeSysteem());
        archiveringOpdracht.setReferentienummer(bijhoudingsplanNotificatieBericht.getReferentieNummer());
        archiveringOpdracht.setCrossReferentienummer(bijhoudingsplanNotificatieBericht.getCrossReferentieNummer());
        archiveringOpdracht.setTijdstipVerzending(DatumUtil.vanLongNaarZonedDateTime(bijhoudingsplanNotificatieBericht.getTijdstipVerzending()));
        archiveringOpdracht.setAdministratieveHandelingId(bijhoudingsplanNotificatieBericht.getAdministratieveHandelingId());
        archiveringOpdracht.setData(bijhoudingsplanNotificatieBericht.getVerwerkBijhoudingsplanBericht());
        archiveringOpdracht.setVerwerkingswijze(Verwerkingswijze.BIJHOUDING);
        archiefService.archiveer(archiveringOpdracht);
    }

    @Override
    public void archiveerVrijBericht(ArchiveringOpdracht archiveringOpdracht) {
        LOGGER.debug("Archiveer te verzenden vrij bericht");
        LOGGER.info("Archiveren van uitgaand vrij bericht met refnr: [{}]", archiveringOpdracht.getReferentienummer());

        final Map<String, String> mdcMap = Maps.newHashMap();
        mdcMap.put(LeveringVeld.MDC_BERICHT_SOORT.getVeld(), String.valueOf(archiveringOpdracht.getSoortBericht()));
        MDC.voerUit(mdcMap, () -> archiefService.archiveer(archiveringOpdracht));
    }

}
