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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EffectAfnemerindicaties;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.gba.domain.afnemerindicatie.AfnemerindicatieOnderhoudOpdracht;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwijderAfnemersindicatieVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.synchronisatie.runtime.bericht.sync.GeenBericht;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

/**
 * Verwerken van het verwijder afnemersindicatie verzoek. Er wordt niet direct een antwoord terug gegeven aangezien de
 * verwerking binnen BRP asynchroon wordt afgehandeld.
 */
public final class VerwijderAfnemersindicatieService implements SynchronisatieBerichtService<VerwijderAfnemersindicatieVerzoekBericht, GeenBericht> {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private final JmsTemplate jmsTemplate;

    private final Destination destination;

    /**
     * 
     * constructor.
     * @param jmsTemplate jms
     * @param destination dest
     */
    @Inject
    public VerwijderAfnemersindicatieService(@Named(value = "brpQueueJmsTemplate") final JmsTemplate jmsTemplate,
                                             @Named(value = "gbaAfnemerindicaties") final Destination destination) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService#getServiceNaam()
     */
    @Override
    public String getServiceNaam() {
        return "VerwijderAfnemersindicatieService";
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService#getVerzoekType()
     */
    @Override
    public Class<VerwijderAfnemersindicatieVerzoekBericht> getVerzoekType() {
        return VerwijderAfnemersindicatieVerzoekBericht.class;
    }

    @Override

    public GeenBericht verwerkBericht(final VerwijderAfnemersindicatieVerzoekBericht verzoek)
            throws OngeldigePersoonslijstException, BerichtSyntaxException {
        final AfnemerindicatieOnderhoudOpdracht opdracht = new AfnemerindicatieOnderhoudOpdracht();
        opdracht.setBsn(verzoek.getBsn());
        opdracht.setPartijCode(verzoek.getPartijCode());
        opdracht.setEffectAfnemerindicatie(EffectAfnemerindicaties.VERWIJDERING);

        try {
            jmsTemplate.send(destination, session -> {
                final String berichtTekst;
                berichtTekst = createBerichtText(opdracht);

                final Message message = session.createTextMessage(berichtTekst);
                message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, verzoek.getMessageId());
                return message;

            });
        } catch (final JmsException e) {
            LOG.info("Verzenden van AfnemerindicatieOnderhoudOpdracht (voor verwijderen) is mislukt.", e);
        }

        return null;
    }

    private String createBerichtText(final AfnemerindicatieOnderhoudOpdracht opdracht) throws JMSException {
        final String berichtTekst;
        try {
            berichtTekst = JSON_MAPPER.writeValueAsString(opdracht);
        } catch (final IOException e) {
            final JMSException jmsException = new JMSException("Kan bericht niet serializeren", e.getMessage());
            jmsException.setLinkedException(e);
            throw jmsException;
        }
        return berichtTekst;
    }
}
