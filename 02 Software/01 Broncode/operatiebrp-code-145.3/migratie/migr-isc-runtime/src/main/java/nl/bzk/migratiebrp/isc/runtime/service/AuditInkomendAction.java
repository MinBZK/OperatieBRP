/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.service;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao.Direction;
import nl.bzk.migratiebrp.isc.runtime.message.Message;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Audit inkomend bericht.
 */
public final class AuditInkomendAction implements Action {

    private static final Logger LOG = LoggerFactory.getLogger();

    private String kanaal;

    @Autowired
    private BerichtenDao berichtenDao;

    @Override
    public void setKanaal(final String kanaal) {
        this.kanaal = kanaal;
    }

    @Override
    public boolean verwerk(final Message message) {
        final String messageId = message.getMessageId();
        final String correlatieId = message.getCorrelatieId();
        final String originator = message.getOriginator();
        final String recipient = message.getRecipient();
        final Long msSeqNumber = message.getMsSequenceNumber();
        final String contents = message.getContent();
        LOG.info(
                "Verwerk binnenkomend bericht: msgId={}, corrId={}, originator={}, recipient={}, msSeqNr={}",
                messageId, correlatieId, originator, recipient, msSeqNumber);

        final Long berichtId =
                berichtenDao.bewaar(kanaal, Direction.INKOMEND, messageId, correlatieId, contents, originator, recipient, msSeqNumber, null);
        LOG.info("Binnenkomend bericht opgeslagen onder id: {}", berichtId);
        message.setBerichtId(berichtId);

        LOG.debug("[Bericht: {}]: audit ok", message.getBerichtId());
        return true;
    }

}
