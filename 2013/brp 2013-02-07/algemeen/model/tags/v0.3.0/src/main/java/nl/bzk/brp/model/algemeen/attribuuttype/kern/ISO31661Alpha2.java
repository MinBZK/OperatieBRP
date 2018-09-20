/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.basis.AbstractISO31661Alpha2;


/**
 *
 *
 */
@Embeddable
public class ISO31661Alpha2 extends AbstractISO31661Alpha2 {

    /**
     * Lege (value-object) constructor voor ISO31661Alpha2, niet gedeclareerd als public om te voorkomen dat objecten
     * zonder
     * waarde worden geïnstantieerd.
     *
     */
    private ISO31661Alpha2() {
        super();
    }

    /**
     * Constructor voor ISO31661Alpha2 die de waarde toekent aan het (value-)object.
     *
     * @param waarde De waarde voor dit value-object.
     */
    @JsonCreator
    public ISO31661Alpha2(final String waarde) {
        super(waarde);
    }

}
