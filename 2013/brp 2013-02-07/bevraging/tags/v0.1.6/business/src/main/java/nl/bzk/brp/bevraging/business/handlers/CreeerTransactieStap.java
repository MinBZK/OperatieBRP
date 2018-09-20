/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;


/**
 * De stap creert een transactie voor 'de bussines', het updaten van persoons gegevens e.d.
 */

public class CreeerTransactieStap extends AbstractBerichtVerwerkingsStap {

    private static final Logger                LOG = LoggerFactory.getLogger(CreeerTransactieStap.class);

    private Long                               transactionTimeoutInSeconds;

    @Inject
    private AbstractPlatformTransactionManager transactionManager;

    @PersistenceContext
    private EntityManager                      em;

    private static final  int MSEC_IN_SECONDS = 1000;

    /**
     * Start een transactie voor de business.
     * {@inheritDoc}
     */

    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final BrpBerichtCommand bericht) {
        if (bericht.getContext().getBusinessTransactionStatus() == null) {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setName("Transactie t.b.v. berichtId " + bericht.getContext().getBerichtId());

            // Method org.postgresql.jdbc4.Jdbc4PreparedStatement.setQueryTimeout(int) is not yet implemented.
            // def.setTimeout(TIMEOUT_IN_SECONDS);

            TransactionStatus businessTransactionStatus = transactionManager.getTransaction(def);
            bericht.getContext().setBusinessTransactionStatus(businessTransactionStatus);

            em.createNativeQuery("SET statement_timeout TO "
                    + (transactionTimeoutInSeconds * MSEC_IN_SECONDS)).executeUpdate();
            em.flush();

            return DOORGAAN_MET_VERWERKING;
        } else {
            LOG.error("bericht.getContext().getBusinessTransactionStatus() != null");
        }

        // TODO Auto-generated method stub
        return BerichtVerwerkingsStap.STOP_VERWERKING;
    }

    public Long getTransactionTimeoutInSeconds() {
        return transactionTimeoutInSeconds;
    }

    public void setTransactionTimeoutInSeconds(final Long transactionTimeoutInSeconds) {
        this.transactionTimeoutInSeconds = transactionTimeoutInSeconds;
    }

}
