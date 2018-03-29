/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.adhoczoeken;

import java.io.IOException;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonMapper;
import nl.bzk.brp.gba.domain.bevraging.Adresantwoord;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekAntwoordFoutReden;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersonenOpAdresAntwoordBericht;
import nl.bzk.migratiebrp.synchronisatie.runtime.exception.ServiceException;
import nl.bzk.migratiebrp.synchronisatie.runtime.util.MessageId;

/**
 * MessageHandler voor de AdHocZoeken Service antwoorden.
 */
public class AdresvraagAntwoordMessageListener implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Logger LOGGER = LoggerFactory.getBerichtVerkeerLogger();
    private final AdHocZoekenNaarIscVerzender verzender;

    /**
     * Constructor.
     * @param verzender verzender om antwoord naar isc te kunnen sturen
     */
    @Inject
    public AdresvraagAntwoordMessageListener(final AdHocZoekenNaarIscVerzender verzender) {
        this.verzender = verzender;
    }

    @Override
    public void onMessage(final Message message) {
        try {
            LOGGER.info("[Start verwerken binnenkomend bericht ...");
            final String correlatieReferentie = message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE);

            LOGGER.info("Lees bericht inhoud ...");
            final String berichtInhoud;
            if (message instanceof TextMessage) {
                berichtInhoud = ((TextMessage) message).getText();
            } else {
                throw new ServiceException("Ontvangen bericht is geen TextMessage");
            }

            LOG.info("CorrelatieId=" + correlatieReferentie + " inhoud: " + berichtInhoud);
            LOGGER.info("Parse bericht inhoud ...");

            final Adresantwoord vertaaldBericht = JsonMapper.reader().forType(Adresantwoord.class).readValue(berichtInhoud);
            final AdHocZoekPersonenOpAdresAntwoordBericht resultaat = verwerkAntwoord(vertaaldBericht, correlatieReferentie);

            LOGGER.info("Versturen antwoord");
            verzender.verstuurAdHocZoekenAntwoord(resultaat);
            LOGGER.info("Gereed (antwoord verstuurd)");
        } catch (final JMSException | IOException e) {
            LOG.error("Er is een fout opgetreden bij het lezen van een binnenkomend bericht. Er is geen antwoord gestuurd.", e);
            LOGGER.info("Gereed (bericht lees exceptie)", e);
            throw new ServiceException(e);
        }
    }

    private AdHocZoekPersonenOpAdresAntwoordBericht verwerkAntwoord(Adresantwoord antwoord, String correlatieReferentie) {
        LOG.info("Verwerken antwoord");
        final AdHocZoekPersonenOpAdresAntwoordBericht resultaat = new AdHocZoekPersonenOpAdresAntwoordBericht();
        resultaat.setCorrelationId(correlatieReferentie);
        resultaat.setMessageId(MessageId.generateSyncMessageId());
        if (antwoord.getFoutreden() != null) {
            resultaat.setFoutreden(AdHocZoekAntwoordFoutReden.fromValue(antwoord.getFoutreden()));
        } else {
            resultaat.setInhoud(antwoord.getInhoud());
        }
        return resultaat;
    }
}
