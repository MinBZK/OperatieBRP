/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;


/**
 * Deze stap commit de transactie voor 'de bussines'.
 */

public class CommitTransactieStap extends AbstractBerichtVerwerkingsStap {

    private static final Logger LOG = LoggerFactory.getLogger(CommitTransactieStap.class);

    @Inject
    private AbstractPlatformTransactionManager transactionManager;

    /**
     * Commit de transactie, de transactie wordt uit de context van het bericht gehaalt.
     * {@inheritDoc}
     */

    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final BrpBerichtCommand bericht) {
        try {
            transactionManager.commit(bericht.getContext().getBusinessTransactionStatus());
            return DOORGAAN_MET_VERWERKING;
        } catch (Exception e) {
            LOG.error("Commit failt, roling back", e);

            if (!bericht.getContext().getBusinessTransactionStatus().isCompleted()) {
                transactionManager.rollback(bericht.getContext().getBusinessTransactionStatus());
            }

            return STOP_VERWERKING;
        }
    }

}
