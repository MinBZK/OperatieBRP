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
import nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht;
import nl.bzk.migratiebrp.isc.runtime.message.Message;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Lees en update het uitgaande bericht in de ISC datastore.
 */
public final class AuditUitgaandAction implements Action {

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
        LOG.debug("[Bericht: {}]: process", message.getBerichtId());

        final Long berichtId;
        if (message.getBerichtId() == null) {
            berichtId = Long.valueOf(message.getContent());
            message.setBerichtId(berichtId);
        } else {
            berichtId = message.getBerichtId();
        }

        // Lezen bericht
        berichtenDao.updateKanaalEnRichting(berichtId, kanaal, Direction.UITGAAND);
        final Bericht bericht = berichtenDao.getBericht(berichtId);

        // Body
        message.setContent(bericht.getBericht());

        // Properties
        message.setMessageId(bericht.getMessageId());
        message.setCorrelatieId(bericht.getCorrelationId());
        message.setOriginator(bericht.getVerzendendePartij());
        message.setRecipient(bericht.getOntvangendePartij());
        message.setRequestNonReceiptNotification(Boolean.TRUE.equals(bericht.getRequestNonReceipt()));

        return true;
    }

}
