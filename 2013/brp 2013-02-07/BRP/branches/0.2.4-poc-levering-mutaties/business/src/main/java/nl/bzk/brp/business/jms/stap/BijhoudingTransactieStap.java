/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.jms.stap;

import javax.inject.Inject;
import javax.inject.Named;

import nl.bzk.brp.business.jms.LevMutAdmHandBerichtContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * In deze stap wordt een nieuwe transactie gecreeerd voor de bijhouding.
 */
public class BijhoudingTransactieStap extends AbstractBerichtVerwerkingsStap {

    private static final Logger LOGGER = LoggerFactory.getLogger(BijhoudingTransactieStap.class);
    private static final String TRANSACTIE_DEF_NAAM = "Transactie tbv verwerking van administatieveHandeling met id: ";

    //@Inject
    //private PlatformTransactionManager transactionManager;

    @Inject
    @Named("transactionManagerSpecial")
    private PlatformTransactionManager transactionManager;

    @Override
    public StapResultaat voerVerwerkingsStapUitVoorBericht(final LevMutAdmHandBerichtContext context) {
        final DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName(TRANSACTIE_DEF_NAAM + context.getAdministratieveHandelingId());
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        context.setTransactionStatus(transactionManager.getTransaction(def));
        return StapResultaat.DOORGAAN_MET_VERWERKING;
    }

    @Override
    public void naVerwerkingsStapVoorBericht(final LevMutAdmHandBerichtContext context) {
        final TransactionStatus transactionStatus = context.getTransactionStatus();
        if (transactionStatus != null && !transactionStatus.isRollbackOnly() && !context.isFout()) {
            transactionManager.commit(transactionStatus);
        } else {
            LOGGER.info("Transactie voor admHandeling met id '{}' krijgt een rollback.",  context.getAdministratieveHandelingId());
            transactionManager.rollback(transactionStatus);
        }
        context.clearBusinessTransactionStatus();
    }

}
