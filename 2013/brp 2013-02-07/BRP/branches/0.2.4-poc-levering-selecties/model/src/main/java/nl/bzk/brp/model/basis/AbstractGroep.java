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
 * Basis klasse voor alle groepen.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractGroep implements Groep {

    /**
     * Copy constructor voor groep.
     * @param groep De te kopieren groep.
     */
    protected AbstractGroep(final Groep groep) {

    }

    /**
     * Standaard lege (protected) constructor.
     */
    protected AbstractGroep() {
    }
}
