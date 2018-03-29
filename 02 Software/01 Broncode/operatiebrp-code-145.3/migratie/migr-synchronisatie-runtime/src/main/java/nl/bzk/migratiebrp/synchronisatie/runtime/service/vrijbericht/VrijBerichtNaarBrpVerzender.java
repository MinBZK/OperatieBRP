/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.vrijbericht;

import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.gba.domain.vrijbericht.VrijBerichtOpdracht;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Verzender van vrijbericht naar brp.
 */
@Component
class VrijBerichtNaarBrpVerzender {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final JmsTemplate jmsTemplate;

    /**
     * Constructor.
     * @param jmsTemplate de jmstemplate voor het versturen van berichten
     */
    @Inject
    VrijBerichtNaarBrpVerzender(@Named("brpQueueJmsTemplate") final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * Verstuurd een vrijbericht vraag naar brp.
     * @param bericht het vrijbericht
     * @param verzoekBerichtId message id van bericht
     */
    void verstuurVrijBericht(final VrijBerichtOpdracht bericht, final String destination, final String verzoekBerichtId) {
        LOG.debug("VrijBericht wordt verstuurd");
        jmsTemplate.send(destination, new VrijBerichtNaarBrpMessageCreator(bericht, verzoekBerichtId));
    }
}
