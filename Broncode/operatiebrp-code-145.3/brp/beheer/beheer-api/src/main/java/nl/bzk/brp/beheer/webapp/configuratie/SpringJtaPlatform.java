/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.util.Assert;

/**
 * Generic Hibernate {@link AbstractJtaPlatform} implementation that simply resolves the JTA {@link UserTransaction} and
 * {@link TransactionManager} from the Spring-configured {@link JtaTransactionManager} implementation.
 */
public final class SpringJtaPlatform extends AbstractJtaPlatform {

    private static final long serialVersionUID = 1L;

    private final JtaTransactionManager transactionManager;

    /**
     * Constructor.
     *
     * @param transactionManager JTA transaction manager
     */
    public SpringJtaPlatform(final JtaTransactionManager transactionManager) {
        Assert.notNull(transactionManager, "TransactionManager must not be null");
        this.transactionManager = transactionManager;
    }

    /**
     * Geef transaction manager.
     *
     * @return transaction manager
     */
    @Override
    protected TransactionManager locateTransactionManager() {
        return transactionManager.getTransactionManager();
    }

    /**
     * Geef user transaction.
     *
     * @return user transaction
     */
    @Override
    protected UserTransaction locateUserTransaction() {
        return transactionManager.getUserTransaction();
    }

}
