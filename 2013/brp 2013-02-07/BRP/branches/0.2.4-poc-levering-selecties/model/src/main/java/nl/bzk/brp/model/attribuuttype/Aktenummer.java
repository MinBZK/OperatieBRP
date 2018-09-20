/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.attribuuttype;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonCreator;
import nl.bzk.brp.model.attribuuttype.basis.AbstractAktenummer;

/**
 *
 * .
 *
 */
@Embeddable
public class Aktenummer extends AbstractAktenummer {

    /**
     * Lege constructor voor Aktenummer, gedeclareerd als private om te voorkomen dat objecten zonder waarde worden
     * geÃ¯nstantieerd.
     *
     */
    private Aktenummer() {
        super(null);
    }

    /**
     * Constructor voor Aktenummer die de waarde toekent aan het (value-)object.
     *
     * @param waarde De waarde voor dit value-object.
     */
    @JsonCreator
    public Aktenummer(final String waarde) {
        super(waarde);
    }

}
