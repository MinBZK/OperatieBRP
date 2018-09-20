/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern.basis;

import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.basis.AbstractGegevensAttribuutType;


/**
 *
 *
 */
@MappedSuperclass
public abstract class AbstractISO31661Alpha2 extends AbstractGegevensAttribuutType<String> {

    /**
     * Lege (value-object) constructor voor ISO31661Alpha2, niet gedeclareerd als public om te voorkomen dat objecten
     * zonder
     * waarde worden geïnstantieerd.
     *
     */
    protected AbstractISO31661Alpha2() {
        super(null);
    }

    /**
     * Constructor voor ISO31661Alpha2 die de waarde toekent aan het (value-)object.
     *
     * @param waarde De waarde voor dit value-object.
     */
    public AbstractISO31661Alpha2(final String waarde) {
        super(waarde);
    }

}
