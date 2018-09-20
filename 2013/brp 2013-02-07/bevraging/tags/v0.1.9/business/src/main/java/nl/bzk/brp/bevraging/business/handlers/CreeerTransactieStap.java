/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.bevraging.business.configuratie.BrpConfiguratie;
import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


/**
 * De stap creert een transactie voor 'de bussines', het updaten van persoons gegevens e.d.
 */

public class CreeerTransactieStap extends AbstractBerichtVerwerkingsStap {

    private static final Logger        LOG                     = LoggerFactory.getLogger(CreeerTransactieStap.class);
    static final String                TRANSACTION_NAME_PREFIX = "Transactie t.b.v. berichtId ";

    @Inject
    private PlatformTransactionManager transactionManager;

    @PersistenceContext
    private EntityManager              em;

    private static final int           MSEC_IN_SECONDS         = 1000;

    /**
     * Start de transactie voor de business. {@inheritDoc}
     */
    @Override
    public <T extends BerichtAntwoord> boolean voerVerwerkingsStapUitVoorBericht(final BerichtVerzoek<T> verzoek,
            final BerichtContext context, final T antwoord)
    {
        if (context.getBusinessTransactionStatus() == null) {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setName(TRANSACTION_NAME_PREFIX + context.getIngaandBerichtId());

            // TODO: transactie rollbackony+readonly maken o.b.v. bericht

            TransactionStatus businessTransactionStatus = transactionManager.getTransaction(def);
            context.setBusinessTransactionStatus(businessTransactionStatus);

            //TimeOut op de DefaultTransactionDefinition werkt niet, want het is nog niet geimplementeerd in Postgres.
            //Dus wordt er een native query gebruikt.
            em.createNativeQuery("SET statement_timeout TO "
                                 + (BrpConfiguratie.getDatabaseTimeOutProperty() * MSEC_IN_SECONDS))
                    .executeUpdate();
            em.flush();

            return DOORGAAN_MET_VERWERKING;
        } else {
            LOG.error("bericht.getContext().getBusinessTransactionStatus() != null");
        }

        return BerichtVerwerkingsStap.STOP_VERWERKING;
    }

    /**
     * Commit/rollback de transactie voor de business. {@inheritDoc}
     */
    @Override
    public void naVerwerkingsStapVoorBericht(final BerichtVerzoek<? extends BerichtAntwoord> verzoek,
            final BerichtContext context)
    {
        try {
            if (!context.getBusinessTransactionStatus().isRollbackOnly()) {
                transactionManager.commit(context.getBusinessTransactionStatus());
            } else {
                transactionManager.rollback(context.getBusinessTransactionStatus());
            }
        } catch (Throwable e) {
            LOG.error("Business commit failed, roling back", e);

            if (!context.getBusinessTransactionStatus().isCompleted()) {
                transactionManager.rollback(context.getBusinessTransactionStatus());
            }
        } finally {
            context.clearBusinessTransactionStatus();
        }
    }
}
