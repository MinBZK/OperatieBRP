/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.brp.AbstractBrpBericht;
import nl.bzk.migratiebrp.bericht.model.brp.factory.BrpBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeBerichtResultaatBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.VerwerkingsresultaatNaamS;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisAntwoordBericht;
import nl.bzk.migratiebrp.synchronisatie.runtime.exception.ServiceException;
import nl.bzk.migratiebrp.synchronisatie.runtime.util.MessageId;
import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;

/**
 * Message handler voor BRP toevallige gebeurtenis antwoorden. Deze worden 'vertaald' en doorgestuurd naar ISC.
 */
public final class ToevalligeGebeurtenisMessageHandler extends AbstractMessageHandler implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Logger VERKEER_LOG = LoggerFactory.getBerichtVerkeerLogger();

    /**
     * Constructor.
     * @param destination destination (queue sync antwoord)
     * @param connectionFactory queue connection factory
     */
    @Inject
    public ToevalligeGebeurtenisMessageHandler(@Named("queueSyncAntwoord") final Destination destination,
                                               @Named("queueConnectionFactory") final ConnectionFactory connectionFactory) {
        super(destination, connectionFactory);

    }

    @Override
    public void onMessage(final Message message) {
        try {
            VERKEER_LOG.info("[Start verwerken binnenkomend bericht ...");
            final String correlatieReferentie = message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE);

            VERKEER_LOG.info("Lees bericht inhoud ...");
            final String berichtInhoud = bepaalBerichtInhoud(message);

            LOG.info("CorrelatieId=" + correlatieReferentie + " inhoud: " + berichtInhoud);

            VERKEER_LOG.info("Parse bericht inhoud ...");
            final Object vertaaldBericht = BrpBerichtFactory.SINGLETON.getBericht(berichtInhoud);

            final SyncBericht resultaat;
            if (vertaaldBericht instanceof nl.bzk.migratiebrp.bericht.model.brp.impl.OngeldigBericht) {
                throw new IllegalArgumentException(
                        "Bericht van BRP is ongeldig: " + ((nl.bzk.migratiebrp.bericht.model.brp.impl.OngeldigBericht) vertaaldBericht).getMelding());
            } else {
                VERKEER_LOG.info("Verwerk bericht inhoud ...");
                resultaat = verwerkAntwoord((AbstractBrpBericht<?>) vertaaldBericht, correlatieReferentie);
            }

            VERKEER_LOG.info("Versturen antwoord");
            stuurAntwoord(resultaat);
            VERKEER_LOG.info("Gereed (antwoord verstuurd)");
            LOG.info(FunctioneleMelding.SYNC_TOEVALLIGEGEBEURTENIS_VERWERKT);

        } catch (final
        JMSException
                | BerichtLeesException e) {
            LOG.error("Er is een fout opgetreden bij het lezen van een binnenkomend bericht. Er is geen antwoord gestuurd.", e);
            VERKEER_LOG.info("Gereed (bericht lees exceptie)", e);
            throw new ServiceException(e);
        }
    }

    private SyncBericht verwerkAntwoord(final AbstractBrpBericht<?> brpAntwoordBericht, final String correlatieId) {
        final SyncBericht resultaat;

        final ObjecttypeBerichtResultaatBijhouding antwoordBijhouding = (ObjecttypeBerichtResultaatBijhouding) brpAntwoordBericht.getInhoud();
        if (antwoordBijhouding.getResultaat() == null
                || antwoordBijhouding.getResultaat().getValue() == null
                || antwoordBijhouding.getResultaat().getValue().getVerwerking() == null) {
            throw new ServiceException("Geen verwerkingsresultaat in BRP bericht");
        } else {
            final VerwerkToevalligeGebeurtenisAntwoordBericht antwoordBericht = new VerwerkToevalligeGebeurtenisAntwoordBericht();
            resultaat = antwoordBericht;

            if (VerwerkingsresultaatNaamS.FOUTIEF.equals(antwoordBijhouding.getResultaat().getValue().getVerwerking().getValue().getValue())) {
                throw new ServiceException("Verwerkingsresultaat uit BRP bericht was 'Foutief'.");
            } else {
                antwoordBericht.setStatus(StatusType.OK);
            }
        }
        resultaat.setMessageId(MessageId.generateSyncMessageId());
        resultaat.setCorrelationId(correlatieId);
        return resultaat;
    }

}
