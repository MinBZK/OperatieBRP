/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.query;

import java.io.Serializable;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;

/**
 * Refentie value adapter.
 *
 * @param <T> waarde type
 * @param <I> id type
 */
public final class ReferenceValueAdapter<T, I extends Serializable> implements ValueAdapter<T> {

    private final ValueAdapter<I> baseValueAdapter;
    private final ReadonlyRepository<T, I> repository;

    /**
     * Constructor.
     *
     * @param baseValueAdapter basis value adapter (voor id)
     * @param repository repository om de referentie mee te zoeken
     */
    public ReferenceValueAdapter(final ValueAdapter<I> baseValueAdapter, final ReadonlyRepository<T, I> repository) {
        this.baseValueAdapter = baseValueAdapter;
        this.repository = repository;
    }

    @Override
    public T adaptValue(final String value) {
        return repository.getOne(baseValueAdapter.adaptValue(value));
    }
}
