/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import org.springframework.transaction.support.TransactionTemplate;

/**
 * Class that wraps a lambda in a transaction and throws a NotFoundException if applicable.
 * @param <T> return type of the result of executing the transaction.
 */
public class SaveTransaction<T> {

    private final TransactionTemplate transactionTemplate;

    /**
     * Creates a new transaction based on the provided transaction template.
     * @param template the transaction template to use
     */
    public SaveTransaction(final TransactionTemplate template) {
        transactionTemplate = template;
    }

    /**
     * Executes the transaction.
     * @param proc the lambda to execute in the transaction
     * @return result of executing the lambda
     * @throws ErrorHandler.NotFoundException if thrown in the lambda
     */
    public final T execute(final NotFoundThrowingSupplier<T> proc) throws ErrorHandler.NotFoundException {
        return transactionTemplate.execute(status -> {
            try {
                return new AbstractReadWriteController.SaveResult<>(proc.get());
            } catch (ErrorHandler.NotFoundException ex) {
                return new AbstractReadWriteController.SaveResult<T>(ex);
            }
        }).getResultOrThrow();
    }
}
