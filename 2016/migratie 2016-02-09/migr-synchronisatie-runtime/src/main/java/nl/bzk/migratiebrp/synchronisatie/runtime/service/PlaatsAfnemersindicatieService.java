/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.sync.impl.PlaatsAfnemersindicatieVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.synchronisatie.runtime.bericht.brp.AfnemerindicatieOnderhoudOpdracht;
import nl.bzk.migratiebrp.synchronisatie.runtime.bericht.brp.AfnemerindicatieOnderhoudOpdracht.EffectAfnemerindicaties;
import nl.bzk.migratiebrp.synchronisatie.runtime.bericht.sync.GeenBericht;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Verwerken van het plaats afnemersindicatie verzoek. Er wordt niet direct een antwoord terug gegeven aangezien de
 * verwerking binnen BRP asynchroon wordt afgehandeld.
 */
@Service
public final class PlaatsAfnemersindicatieService implements SynchronisatieBerichtService<PlaatsAfnemersindicatieVerzoekBericht, GeenBericht> {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Inject
    @Named(value = "brpQueueJmsTemplate")
    private JmsTemplate jmsTemplate;

    @Inject
    @Named(value = "gbaAfnemerindicaties")
    private Destination destination;

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService#getServiceNaam()
     */
    @Override
    public String getServiceNaam() {
        return "PlaatsAfnemersindicatieService";
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService#getVerzoekType()
     */
    @Override
    public Class<PlaatsAfnemersindicatieVerzoekBericht> getVerzoekType() {
        return PlaatsAfnemersindicatieVerzoekBericht.class;
    }

    @Override
    @Transactional(value = "syncDalTransactionManager", propagation = Propagation.REQUIRED)
    @SuppressWarnings("PMD.PreserveStackTrace")
    public GeenBericht verwerkBericht(final PlaatsAfnemersindicatieVerzoekBericht verzoek) throws OngeldigePersoonslijstException, BerichtSyntaxException {
        final AfnemerindicatieOnderhoudOpdracht opdracht = new AfnemerindicatieOnderhoudOpdracht();
        opdracht.setReferentienummer(verzoek.getReferentie());
        opdracht.setPersoonId(verzoek.getPersoonId());
        opdracht.setToegangLeveringsautorisatieId(verzoek.getToegangLeveringsautorisatieId());
        opdracht.setDienstId(verzoek.getDienstId());
        opdracht.setEffectAfnemerindicatie(EffectAfnemerindicaties.PLAATSING);

        try {
            jmsTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(final Session session) throws JMSException {
                    LOG.info("Verzoek: " + verzoek);

                    final String berichtTekst;
                    try {
                        berichtTekst = JSON_MAPPER.writeValueAsString(opdracht);
                    } catch (final IOException e) {
                        final JMSException jmsException = new JMSException("Kan bericht niet serializeren", e.getMessage());
                        jmsException.setLinkedException(e);
                        throw jmsException;
                    }

                    final Message message = session.createTextMessage(berichtTekst);
                    LOG.info("Message: " + message);
                    message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, verzoek.getMessageId());
                    return message;
                }
            });
        } catch (final JmsException e) {
            LOG.info("Verzenden van AfnemerindicatieOnderhoudOpdracht (voor plaatsen) is mislukt.", e);
        }

        return null;
    }
}
