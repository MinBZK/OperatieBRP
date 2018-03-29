/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.service;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.isc.jbpm.common.actionhandler.EsbActionHandler;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.dao.CorrelatieDao;
import nl.bzk.migratiebrp.isc.runtime.message.Message;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Correlatie gegevens opslaan.
 */
public final class CorrelatieOpslaanAction implements Action {

    private static final Logger LOG = LoggerFactory.getLogger();

    private String kanaal;

    @Autowired
    private BerichtenDao berichtenDao;
    @Autowired
    private CorrelatieDao correlatieDao;

    @Override
    public void setKanaal(final String kanaal) {
        this.kanaal = kanaal;
    }

    @Override
    public boolean verwerk(final Message message) {
        LOG.debug("[Bericht: {}]: process", message.getBerichtId());
        // Save process correlations
        final Long processInstanceId = (Long) message.getAttribute(EsbActionHandler.PROCESS_ID_ATTRIBUTE);
        if (message.getAttribute(EsbActionHandler.NODE_ID_ATTRIBUTE) != null) {
            // ESB Request/Response
            final String messageId = message.getMessageId();
            final String originator = message.getOriginator();
            final String recipient = message.getRecipient();

            final Long tokenId = (Long) message.getAttribute(EsbActionHandler.TOKEN_ID_ATTRIBUTE);
            final Long nodeId = (Long) message.getAttribute(EsbActionHandler.NODE_ID_ATTRIBUTE);

            LOG.debug("Processdata voor bericht {}: process={}, token={}, node={}", messageId, processInstanceId, tokenId, nodeId);

            correlatieDao.opslaan(messageId, kanaal, recipient, originator, processInstanceId, tokenId, nodeId);
        }

        if (processInstanceId != null) {
            berichtenDao.updateProcessInstance(message.getBerichtId(), processInstanceId);
        }

        return true;
    }

}
