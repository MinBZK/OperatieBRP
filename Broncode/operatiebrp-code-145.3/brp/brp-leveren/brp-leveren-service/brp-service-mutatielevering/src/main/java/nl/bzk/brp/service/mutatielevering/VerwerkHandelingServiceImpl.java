/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering;

import com.google.common.collect.Lists;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.internbericht.admhndpublicatie.HandelingVoorPublicatie;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.dalapi.AdministratieveHandelingRepository;
import nl.bzk.brp.service.mutatielevering.brp.MaakBrpBerichtService;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiebericht;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiehandeling;
import nl.bzk.brp.service.mutatielevering.dto.Mutatielevering;
import nl.bzk.brp.service.mutatielevering.leveringbepaling.MutatieleveringService;
import nl.bzk.brp.service.mutatielevering.lo3.MaakLo3BerichtService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatie klasse van de AdministratieveHandendelingVerwerkerService. Verzorgt de verwerking van mutaties/ administratieve handelingen.
 */
@Service
@Bedrijfsregel(Regel.R1988)
final class VerwerkHandelingServiceImpl implements VerwerkHandelingService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private MaakMutatiehandelingService maakMutatiehandelingService;
    @Inject
    private MutatieleveringService mutatieleveringService;
    @Inject
    private MaakBrpBerichtService maakBrpBerichtService;
    @Inject
    private MaakLo3BerichtService maakLo3BerichtService;
    @Inject
    private AttenderingPlaatsAfnemerindicatieService attenderingPlaatsAfnemerindicatieService;
    @Inject
    private VerstuurAfnemerBerichtService verstuurAfnemerBerichtService;
    @Inject
    private AdministratieveHandelingRepository administratieveHandelingRepository;

    private VerwerkHandelingServiceImpl() {

    }

    @Transactional(transactionManager = "masterTransactionManager")
    @Override
    public void verwerkAdministratieveHandeling(final HandelingVoorPublicatie handelingVoorPublicatie) throws VerwerkHandelingException {
        final Long handelingId = handelingVoorPublicatie.getAdmhndId();
        try {
            //bepaal de handeling, met de bijhgehouden personen
            final Mutatiehandeling handeling = maakMutatiehandelingService.maakHandeling(handelingVoorPublicatie);
            //bepaal per autorisatie de personen welke geleverd moeten worden
            final List<Mutatielevering> leveringList = mutatieleveringService.bepaalLeveringen(handeling);
            //maak BRP & LO3 berichten
            final List<Mutatiebericht> berichten = Lists.newLinkedList();
            LOGGER.debug("Start BRP verwerking voor administratieve handeling {}", handelingId);
            berichten.addAll(maakBrpBerichtService.maakBerichten(leveringList, handeling));
            LOGGER.debug("Start LO3 verwerking voor administratieve handeling {}", handelingId);
            berichten.addAll(maakLo3BerichtService.maakBerichten(leveringList, handeling));
            //plaats afnemerindicaties, voor de relevante autorisaties en personen
            attenderingPlaatsAfnemerindicatieService.plaatsAfnemerindicaties(handeling.getAdministratieveHandelingId(), berichten);
            //markeer de handeling als verwerkt
            final int updatecount = administratieveHandelingRepository.markeerAdministratieveHandelingAlsVerwerkt(handelingId);
            if (updatecount == 0) {
                LOGGER.debug(String.format("administratieve handeling met id %d kon niet gemarkeerd worden als verwerkt, is mogelijk al verwerkt",
                        handelingId));
            } else {
                //plaats de berichten op de queue zodat verzending het kan versturen
                verstuurAfnemerBerichtService.verstuurBerichten(berichten);
            }
        } catch (StapException | BlobException e) {
            throw new VerwerkHandelingException(e);
        }
    }

    @Transactional(transactionManager = "masterTransactionManager")
    @Override
    public void markeerHandelingAlsFout(final HandelingVoorPublicatie handelingVoorPublicatie) {
        administratieveHandelingRepository.markeerAdministratieveHandelingAlsFout(handelingVoorPublicatie.getAdmhndId());
    }
}
