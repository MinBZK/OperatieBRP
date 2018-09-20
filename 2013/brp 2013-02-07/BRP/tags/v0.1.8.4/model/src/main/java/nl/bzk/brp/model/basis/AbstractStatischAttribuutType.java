/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

/**
 * Type voor alle statische attributen en technische id's.
 * @param <T> Basistype van het attribuut.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractStatischAttribuutType<T> extends AbstractAttribuutType<T> {

    /**
     * De (op dit moment) enige constructor voor de immutable AttribuutType classen.
     * @param waarde de waarde
     */
    protected AbstractStatischAttribuutType(final T waarde) {
        super(waarde);
    }
}
