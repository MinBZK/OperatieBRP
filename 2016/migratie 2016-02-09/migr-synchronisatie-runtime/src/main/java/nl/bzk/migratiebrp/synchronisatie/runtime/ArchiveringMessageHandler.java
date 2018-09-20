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
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ArchiveringVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import nl.bzk.migratiebrp.synchronisatie.runtime.bericht.brp.GbaArchiveringVerzoek;
import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.logging.MDC;
import nl.bzk.migratiebrp.util.common.logging.MDC.MDCCloser;
import org.springframework.jms.UncategorizedJmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Message handler voor archivering verzoeken. Deze worden 'vertaald' en doorgestuurd naar BRP.
 */
public final class ArchiveringMessageHandler implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Logger VERKEER_LOG = LoggerFactory.getBerichtVerkeerLogger();

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Inject
    @Named("gbaArchief")
    private Destination gbaArchiefQueue;

    @Inject
    @Named("brpQueueConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Inject
    private Lo3AttribuutConverteerder converteerder;

    @Override
    @SuppressWarnings("checkstyle:illegalcatch")
    public void onMessage(final Message message) {
        try (MDCCloser verwerkingCloser = MDC.startVerwerking()) {
            LOG.info("Verwerken bericht");
            VERKEER_LOG.info("Lees bericht inhoud ...");
            final String berichtInhoud = bepaalBerichtInhoud(message);

            VERKEER_LOG.info("Parse bericht inhoud ...");
            final SyncBericht syncBericht = SyncBerichtFactory.SINGLETON.getBericht(berichtInhoud);
            if (syncBericht instanceof ArchiveringVerzoekBericht) {
                VERKEER_LOG.info("Converteren bericht ...");
                final GbaArchiveringVerzoek brpBericht = converteerVerzoek((ArchiveringVerzoekBericht) syncBericht);

                VERKEER_LOG.info("Versturen bericht naar BRP");
                verstuurVerzoekNaarBrp(brpBericht);
                VERKEER_LOG.info("Gereed");
            } else {
                throw new IllegalArgumentException("Onbekend type bericht ontvangen.");
            }
            LOG.info(FunctioneleMelding.SYNC_ARCHIVERING_VERWERKT);

        } catch (final Exception e) {
            LOG.error("Er is een fout opgetreden.", e);
            VERKEER_LOG.info("Gereed (exceptie)", e);
            throw e;
        }
    }

    private GbaArchiveringVerzoek converteerVerzoek(final ArchiveringVerzoekBericht syncBericht) {
        final GbaArchiveringVerzoek resultaat = new GbaArchiveringVerzoek();
        resultaat.setSoortBericht(syncBericht.getSoortBericht());
        resultaat.setRichting(syncBericht.getRichting().name());

        resultaat.setZendendePartijCode(converteerNaarPartijCode(syncBericht.getZendendeAfnemerCode(), syncBericht.getZendendeGemeenteCode()));
        resultaat.setZendendeSysteem(syncBericht.getZendendeSysteem().value());
        resultaat.setOntvangendePartijCode(converteerNaarPartijCode(syncBericht.getOntvangendeAfnemerCode(), syncBericht.getOntvangendeGemeenteCode()));
        resultaat.setOntvangendeSysteem(syncBericht.getOntvangendeSysteem().value());
        resultaat.setReferentienummer(syncBericht.getReferentienummer());
        resultaat.setCrossReferentienummer(syncBericht.getCrossReferentienummer());
        resultaat.setTijdstipOntvangst(syncBericht.getTijdstipOntvangst());
        resultaat.setTijdstipVerzending(syncBericht.getTijdstipVerzending());
        resultaat.setData(syncBericht.getData());

        return resultaat;
    }

    private Integer converteerNaarPartijCode(final String afnemerCode, final String gemeenteCode) {
        final Integer resultaat;
        if (afnemerCode != null && !"".equals(afnemerCode)) {
            // Afnemercode is een partijcode
            resultaat = Integer.valueOf(afnemerCode);
        } else if (gemeenteCode != null && !"".equals(gemeenteCode)) {
            // Converteer gemeentecode naar partijcode
            resultaat = converteerder.converteerLo3GemeenteCodeNaarBrpPartijCode(new Lo3GemeenteCode(gemeenteCode)).getWaarde();
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    @SuppressWarnings("PMD.PreserveStackTrace")
    private void verstuurVerzoekNaarBrp(final GbaArchiveringVerzoek brpBericht) {
        final JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.send(gbaArchiefQueue, new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                final String berichtTekst;
                try {
                    berichtTekst = JSON_MAPPER.writeValueAsString(brpBericht);
                } catch (final IOException e) {
                    final JMSException jmsException = new JMSException("Kan bericht niet serializeren", e.getMessage());
                    jmsException.setLinkedException(e);
                    throw jmsException;
                }
                return session.createTextMessage(berichtTekst);
            }
        });
    }

    /**
     * Lees bericht inhoud.
     *
     * @param message
     *            message
     * @return inhoud
     */
    protected String bepaalBerichtInhoud(final Message message) {
        final String result;
        try {
            if (message instanceof TextMessage) {
                result = ((TextMessage) message).getText();
            } else {
                throw new UncategorizedJmsException("Het JMS bericht is niet van het type TextMessage");
            }
        } catch (final JMSException e) {
            throw new UncategorizedJmsException("Het JMS bericht kon niet worden gelezen door een fout: ", e);
        }
        return result;
    }

}
