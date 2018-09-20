/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.service;

import nl.bzk.migratiebrp.bericht.model.Bericht;
import nl.bzk.migratiebrp.bericht.model.BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.runtime.message.Message;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Pase het binnenkomende bericht (text -> object).
 */
public final class ParseBerichtAction implements Action {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Autowired
    private BerichtenDao berichtenDao;

    private BerichtFactory berichtFactory;

    @Override
    public void setKanaal(final String kanaal) {
        switch (kanaal) {
            case "VOSPG":
                berichtFactory = new Lo3BerichtFactory();
                break;
            case "SYNC":
                berichtFactory = SyncBerichtFactory.SINGLETON;
                break;
            default:
                throw new IllegalArgumentException("Kanaal '" + kanaal + "' onbekend.");
        }
    }

    @Override
    public boolean verwerk(final Message message) {
        LOG.debug("[Bericht: {}]: parse", message.getBerichtId());
        final String berichtAlsString = message.getContent();
        final Bericht bericht = berichtFactory.getBericht(berichtAlsString);

        // Zet berichttype in bericht audit.
        berichtenDao.updateNaam(message.getBerichtId(), bericht.getBerichtType());
        message.setBerichtType(bericht.getBerichtType());
        LOG.debug("[Bericht: {}]: berichtType={}", message.getBerichtId(), bericht.getBerichtType());

        // Set messageId en correlationId op bericht
        bericht.setMessageId(message.getMessageId());
        bericht.setCorrelationId(message.getCorrelatieId());

        // LO3 Bericht verrijken
        if (bericht instanceof Lo3Bericht) {
            final Lo3Bericht lo3Bericht = (Lo3Bericht) bericht;
            lo3Bericht.setBronGemeente(message.getOriginator());
            lo3Bericht.setDoelGemeente(message.getRecipient());
        }

        message.setBericht(bericht);
        LOG.debug("[Bericht: {}]: parse ok", message.getBerichtId());
        return true;
    }

}
