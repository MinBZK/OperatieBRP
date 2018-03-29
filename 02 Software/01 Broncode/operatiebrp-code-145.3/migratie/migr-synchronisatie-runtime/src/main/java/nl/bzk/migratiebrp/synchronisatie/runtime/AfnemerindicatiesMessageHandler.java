/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.gba.domain.afnemerindicatie.AfnemerindicatieOnderhoudAntwoord;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AfnemersindicatieFoutcodeType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkAfnemersindicatieAntwoordBericht;
import nl.bzk.migratiebrp.synchronisatie.runtime.exception.ServiceException;
import nl.bzk.migratiebrp.synchronisatie.runtime.util.MessageId;
import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;

/**
 * Message handler voor BRP afnemerindicatie antwoorden. Deze worden 'vertaald' en doorgestuurd naar ISC.
 */
public final class AfnemerindicatiesMessageHandler extends AbstractMessageHandler implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Logger LOGGER = LoggerFactory.getBerichtVerkeerLogger();

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    /**
     * Constructor.
     * @param destination destination (queue sync antwoord)
     * @param connectionFactory queue connection factory
     */
    @Inject
    public AfnemerindicatiesMessageHandler(@Named("queueSyncAntwoord") final Destination destination,
                                           @Named("queueConnectionFactory") final ConnectionFactory connectionFactory) {
        super(destination, connectionFactory);
    }

    @Override
    public void onMessage(final Message message) {
        try {
            LOGGER.info("Start verwerken binnenkomend bericht ...");
            final String correlatieReferentie = message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE);

            LOGGER.info("Lees bericht inhoud ...");
            final String berichtInhoud = bepaalBerichtInhoud(message);

            LOGGER.info("Parse bericht inhoud ...");
            final AfnemerindicatieOnderhoudAntwoord antwoord = JSON_MAPPER.readValue(berichtInhoud, AfnemerindicatieOnderhoudAntwoord.class);

            LOGGER.info("Verwerk bericht inhoud ...");
            final SyncBericht resultaat = verwerkAntwoord(antwoord, correlatieReferentie);

            LOGGER.info("Versturen antwoord");
            stuurAntwoord(resultaat);
            LOGGER.info("Gereed (antwoord verstuurd)");
            LOG.info(FunctioneleMelding.SYNC_AFNEMERSINDICATIE_VERWERKT);
        } catch (final JMSException | IOException | BerichtLeesException e) {
            LOG.error("Er is een fout opgetreden bij het lezen van een binnenkomend bericht.", e);
            LOGGER.info("Exceptie", e);
            throw new ServiceException(e);
        }
    }

    private SyncBericht verwerkAntwoord(final AfnemerindicatieOnderhoudAntwoord antwoord, final String correlatieId) {
        final VerwerkAfnemersindicatieAntwoordBericht verwerkAfnemersindicatieAntwoord = new VerwerkAfnemersindicatieAntwoordBericht();
        if (antwoord.getFoutcode() == null) {
            verwerkAfnemersindicatieAntwoord.setStatus(StatusType.OK);
        } else {
            verwerkAfnemersindicatieAntwoord.setStatus(StatusType.FOUT);
            verwerkAfnemersindicatieAntwoord.setFoutcode(AfnemersindicatieFoutcodeType.valueOf(antwoord.getFoutcode().toString()));
        }
        verwerkAfnemersindicatieAntwoord.setMessageId(MessageId.generateSyncMessageId());
        verwerkAfnemersindicatieAntwoord.setCorrelationId(correlatieId);
        return verwerkAfnemersindicatieAntwoord;
    }

}
