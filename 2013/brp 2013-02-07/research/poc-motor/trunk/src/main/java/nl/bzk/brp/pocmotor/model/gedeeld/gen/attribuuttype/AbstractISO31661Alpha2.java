/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.gedeeld.gen.attribuuttype;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractGegevensAttribuutType;

/**
 * ISO 3166-1 alpha 2

 * Generated Abstract Class
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractISO31661Alpha2 extends AbstractGegevensAttribuutType<String> {

    public AbstractISO31661Alpha2() {
        super();
    }

    public AbstractISO31661Alpha2(final String waarde) {
        this();
        setWaarde(waarde);
    }

}
