/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;

/**
 * Abstract readonly controller (zonder view).
 *
 * @param <T> model type model type
 * @param <I> id type id type
 */
public abstract class AbstractReadonlyController<T, I extends Serializable> extends AbstractBaseReadonlyController<T, T, I> {

    /**
     * Constructor.
     *
     * @param repository repository
     */
    protected AbstractReadonlyController(final ReadonlyRepository<T, I> repository) {
        super(repository, Collections.<Filter<?>>emptyList());
    }

    /**
     * Constructor.
     *
     * @param repository repository
     * @param filters filters
     */
    protected AbstractReadonlyController(final ReadonlyRepository<T, I> repository, final List<Filter<?>> filters) {
        super(repository, filters);
    }

    /**
     * Constructor.
     *
     * @param repository repository
     * @param filters filters
     * @param allowedSorts toegestane sorteringen (eerste voorkomen is default)
     */
    protected AbstractReadonlyController(final ReadonlyRepository<T, I> repository, final List<Filter<?>> filters, final List<String> allowedSorts) {
        super(repository, filters, allowedSorts);
    }

    /**
     * Constructor.
     *
     * @param repository repository
     * @param filters filters
     * @param allowedSorts toegestane sorteringen (eerste voorkomen is default)
     * @param queryShouldContainParameters geeft aan of er geldige parameters moeten worden gebruik om resultaten te
     *            krijgen
     */
    protected AbstractReadonlyController(
            final ReadonlyRepository<T, I> repository,
            final List<Filter<?>> filters,
            final List<String> allowedSorts,
            final boolean queryShouldContainParameters)
    {
        super(repository, filters, allowedSorts, queryShouldContainParameters);
    }

    @Override
    protected final T converteerNaarView(final T item) {
        return item;
    }

    @Override
    protected final List<T> converteerNaarView(final List<T> content) {
        return content;
    }

}
