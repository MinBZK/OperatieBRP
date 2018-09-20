/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.centrale.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.inject.Inject;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.gba.centrale.berichten.GbaBijhoudingNotificatieBericht;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.internbericht.AdministratieveHandelingVerwerktOpdracht;
import org.springframework.jms.JmsException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Verwerk bijhouding notificatie vanuit de migratievoorzieningen.
 */
public final class BijhoudingService implements GbaService {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Inject
    private BlobifierService blobifierService;

    @Override
    @Transactional(transactionManager = "lezenSchrijvenTransactionManager")
    public String verwerk(final String bericht, final String berichtReferentie) {
        // Unmarshal JSON
        final GbaBijhoudingNotificatieBericht notificatie;
        try {
            notificatie = JSON_MAPPER.readValue(bericht, GbaBijhoudingNotificatieBericht.class);
        } catch (final IOException e) {
            throw new JmsException("Kan bericht niet deserialiseren naar GbaBijhoudingNotificatieBericht.", e) {
                private static final long serialVersionUID = 1L;
            };
        }

        // Verwerk notificatie
        final AdministratieveHandelingVerwerktOpdracht resultaat = verwerkNotificatie(notificatie);

        // Marshall JSON
        final String resultaatTekst;
        try {
            resultaatTekst = JSON_MAPPER.writeValueAsString(resultaat);
        } catch (final JsonProcessingException e) {
            throw new JmsException("Kan bericht niet serialiseren naar AdministratieveHandelingVerwerktOpdracht.", e) {
                private static final long serialVersionUID = 1L;
            };
        }
        return resultaatTekst;
    }

    private AdministratieveHandelingVerwerktOpdracht verwerkNotificatie(final GbaBijhoudingNotificatieBericht notificatie) {
        // Roep de blobifier service aan voor de persoon id's
        for (final Integer bijgehoudenPersoonId : notificatie.getBijgehoudenPersoonIds()) {
            // Blobify de persoon
            LOGGER.info("Blobify persoon:{}", bijgehoudenPersoonId);
            blobifierService.blobify(bijgehoudenPersoonId, true);
        }

        // Retourneer de BRP specifieke notificatie voor verwerking door BRP
        return new AdministratieveHandelingVerwerktOpdracht(notificatie.getAdministratieveHandelingId(), null, notificatie.getBijgehoudenPersoonIds());
    }
}
