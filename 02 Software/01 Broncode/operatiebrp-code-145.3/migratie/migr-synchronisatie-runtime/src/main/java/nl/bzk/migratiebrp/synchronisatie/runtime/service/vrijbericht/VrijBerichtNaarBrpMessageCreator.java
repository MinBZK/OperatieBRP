/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.vrijbericht;

import com.fasterxml.jackson.core.JsonProcessingException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.MDCProcessor;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonMapper;
import nl.bzk.brp.gba.domain.vrijbericht.VrijBerichtOpdracht;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import org.springframework.jms.core.MessageCreator;

/**
 * MessagCreator voor versturen Vrijbericht.
 */
public class VrijBerichtNaarBrpMessageCreator implements MessageCreator {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final VrijBerichtOpdracht opdracht;
    private final String verzoekBerichtId;

    /**
     * Maakt MessagCreator.
     * @param opdracht te versturen opdracht
     * @param verzoekBerichtId bericht id
     */
    public VrijBerichtNaarBrpMessageCreator(final VrijBerichtOpdracht opdracht, final String verzoekBerichtId) {
        this.opdracht = opdracht;
        this.verzoekBerichtId = verzoekBerichtId;
    }

    @Override
    public Message createMessage(final Session session) throws JMSException {
        try {
            final String berichtTekst = JsonMapper.writer().writeValueAsString(opdracht);

            LOG.debug("Bericht: " + berichtTekst);
            final Message message = session.createTextMessage(berichtTekst);
            MDCProcessor.registreerVerwerkingsCode(message);
            LOG.debug("Message: " + message);
            message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, verzoekBerichtId);
            return message;
        } catch (JsonProcessingException e) {
            final JMSException jmsException = new JMSException("Er kon geen valide json gemaakt worden van object");
            jmsException.setLinkedException(e);
            throw jmsException;
        }
    }
}
