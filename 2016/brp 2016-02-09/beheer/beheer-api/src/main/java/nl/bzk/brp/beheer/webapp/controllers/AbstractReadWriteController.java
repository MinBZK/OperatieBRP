/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Basis implementatie read/write controller.
 *
 * @param <T> model type
 * @param <I> id type
 */
public abstract class AbstractReadWriteController<T, I extends Serializable> extends AbstractReadonlyController<T, I> implements ReadWriteController<T, I> {

    private final ReadWriteRepository<T, I> repository;
    private final List<HistorieVerwerker<T, ?>> historieVerwerkers;

    private TransactionTemplate transactionTemplate;

    /**
     * Constructor.
     *
     * @param repository repository
     */
    protected AbstractReadWriteController(final ReadWriteRepository<T, I> repository) {
        this(repository, Collections.<Filter<?>>emptyList(), null, null);
    }

    /**
     * Constructor.
     *
     * @param repository repository
     * @param filters filters
     */
    protected AbstractReadWriteController(final ReadWriteRepository<T, I> repository, final List<Filter<?>> filters) {
        this(repository, filters, null, null);
    }

    /**
     * Constructor.
     *
     * @param repository repository
     * @param filters filters
     * @param historieVerwerkers historie verwerkers
     */
    protected AbstractReadWriteController(
            final ReadWriteRepository<T, I> repository, final List<Filter<?>> filters, final List<HistorieVerwerker<T, ?>> historieVerwerkers)
    {
        this(repository, filters, historieVerwerkers, null);
    }

    /**
     * Constructor.
     *
     * @param repository repository
     * @param filters filters
     * @param historieVerwerkers historie verwerkers
     * @param allowedSorts toegestane sorteringen (eerste voorkomen is default)
     */
    protected AbstractReadWriteController(
            final ReadWriteRepository<T, I> repository,
            final List<Filter<?>> filters,
            final List<HistorieVerwerker<T, ?>> historieVerwerkers,
            final List<String> allowedSorts)
    {
        super(repository, filters, allowedSorts);
        this.repository = repository;
        this.historieVerwerkers = historieVerwerkers == null ? Collections.<HistorieVerwerker<T, ?>>emptyList() : historieVerwerkers;
    }

    /**
     * Zet de transaction manager.
     *
     * @param transactionManager transaction manager
     */
    @Autowired
    public final void setTransactionManagerReadWrite(final PlatformTransactionManager transactionManager) {
        transactionTemplate = new TransactionTemplate(transactionManager, new DefaultTransactionDefinition());
    }

    /**
     * Slaat een gewijzigd item op.
     *
     * @param item item
     * @return item
     * @throws NotFoundException als het te wijzigen item niet gevonden kan worden.
     */
    @Override
    public final T save(@Valid @RequestBody final T item) throws NotFoundException {
        final SaveResult<T> result = transactionTemplate.execute(new SaveTransactionCallback(item));

        if (result.getException() != null) {
            throw result.getException();
        }

        return result.getResult();

    }

    /**
     * Wijzig item voor opslaan.
     *
     * @param item het te wijzigen object voor opslag
     * @throws NotFoundException als het te wijzigen item niet gevonden kan worden.
     */
    protected void wijzigObjectVoorOpslag(final T item) throws NotFoundException {
        // Hook
    }

    /**
     * Class om item op te slaan binnen een transactie.
     *
     * @param <T> type entity
     */
    private class SaveTransactionCallback implements TransactionCallback<SaveResult<T>> {

        private final T item;

        /**
         * Constructor for this class.
         *
         * @param item item to save
         */
        public SaveTransactionCallback(final T item) {
            this.item = item;
        }

        @Override
        public SaveResult<T> doInTransaction(final TransactionStatus status) {
            final SaveResult<T> result;
            try {
                wijzigObjectVoorOpslag(item);
                if (historieVerwerkers.isEmpty()) {
                    result = new SaveResult<>(repository.save(item));
                } else {
                    final T managedItem = repository.findOrPersist(item);
                    for (final HistorieVerwerker<T, ?> historieVerwerker : historieVerwerkers) {
                        historieVerwerker.verwerkHistorie(item, managedItem);
                    }
                    result = new SaveResult<>(repository.save(managedItem));
                }
            } catch (final NotFoundException e) {
                return new SaveResult<>(e);
            }
            return result;
        }
    }

    /**
     * Saves resultaat.
     *
     * @param <T> type entity
     */
    private static final class SaveResult<T> {

        private final NotFoundException exception;
        private final T result;

        /**
         * Constructor.
         *
         * @param exception exceptie
         */
        SaveResult(final NotFoundException exception) {
            this.exception = exception;
            result = null;
        }

        /**
         * Constructor.
         *
         * @param result resultaat
         */
        SaveResult(final T result) {
            exception = null;
            this.result = result;
        }

        /**
         * Foutmelding wanneer de entiteit niet wordt gevonden.
         *
         * @return NotFoundException
         */
        public NotFoundException getException() {
            return exception;
        }

        /**
         * Geeft de gevonden entiteit terug.
         *
         * @return de gevonden entiteit.
         */
        public T getResult() {
            return result;
        }

    }

}
