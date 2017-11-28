/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.algemeenbrp.dal.repositories.jpa.BasisRepositoryImpl;
import nl.bzk.algemeenbrp.dal.repositories.jpa.StamtabelRepositoryImpl;

/**
 * Implementatie van {@link BasisRepositoryImpl} specifiek voor de Master database.
 *
 * @param <T> het type entiteit voor deze repository
 * @param <I> het type van de primary key van de entiteit van deze repository
 * @param <C> het type van de key die gebruikt wordt als unieke sleutel in de cache
 */
public abstract class AbstractMasterRepositoryImpl<T, I extends Serializable, C> extends StamtabelRepositoryImpl<T, I, C> {

    /**
     * Constructor.
     *
     * @param entityType type entity voor deze repository
     * @param keyField de naam van het veld van de entity dat overeenkomt de de unieke sleutel van
     *        het stamgegeven
     */
    public AbstractMasterRepositoryImpl(final Class<T> entityType, final String... keyField) {
        super(entityType, keyField);
    }

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    @Override
    protected void setEntityManager(final EntityManager entityManager) {
        super.setEntityManager(entityManager);
    }
}
