/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.repositories;

import java.io.Serializable;

/**
 * Definieert de basis methodes die elke repository bevat.
 * @param <T> het type entiteit voor deze repository
 * @param <I> (ID) het type van de primary key van de entiteit van deze repository
 */
public interface BasisRepository<T, I extends Serializable> {

    /**
     * Slaat de entiteit op de in de database.
     * @param entity de entiteit die moet worden opgeslagen
     */
    void save(T entity);

    /**
     * Merged de gewijzigde entiteit in de database.
     * @param entity de gewijzigde entiteit die moet worden opgeslagen
     * @return de gemanagede entiteit
     */
    T merge(T entity);

    /**
     * Zoekt de entiteit aan de hand van de primary key.
     * @param primaryKey primary key
     * @return de gevonden entiteit
     */
    T findById(I primaryKey);
}
