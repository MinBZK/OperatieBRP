/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.adhoczoeken;

import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.gba.domain.bevraging.Basisvraag;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Verzender van adhoc zoek vragen naar brp.
 */
@Component
class AdHocZoekenNaarBrpVerzender {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final JmsTemplate jmsTemplate;

    /**
     * Constructor.
     * @param jmsTemplate de jmstemplate voor het versturen van berichten
     */
    @Inject
    AdHocZoekenNaarBrpVerzender(@Named("brpQueueJmsTemplate") final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * Verstuurd een adhoc zoeken vraag naar brp.
     * @param vraag te versturen vraag
     * @param verzoekBerichtId message id van bericht
     */
    void verstuurAdHocZoekenVraag(final Basisvraag vraag, final String destination, final String verzoekBerichtId) {
        LOG.debug("AdHoc Zoeken vraag wordt verstuurd");
        jmsTemplate.send(destination, new AdHocZoekenNaarBrpMessageCreator(vraag, verzoekBerichtId));
    }
}
