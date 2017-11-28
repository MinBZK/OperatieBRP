/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.repositories;

import java.io.Serializable;
import java.util.List;

/**
 * Definieert de methodes die elke stamtabel repository bevat.
 * @param <T> het type entiteit voor deze repository
 * @param <I> (ID) het type van de primary key van de entiteit van deze repository
 * @param <C> het type van de key die gebruikt wordt als unieke sleutel in de cache
 */
public interface StamtabelRepository<T, I extends Serializable, C> extends BasisRepository<T, I>, CacheClearable {

    /**
     * Zoekt een stamgegeven aan de hand van de gegeven unieke sleutel.
     * @param key de unieke sleutel voor het stamgegeven
     * @return het stamgegeven
     */
    T findByKey(C key);

    /**
     * De lijst met alle voorkomens van deze stamtabel.
     * @return de lijst met stamgegevens
     */
    List<T> findAll();
}
