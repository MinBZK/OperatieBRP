/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype;

import javax.persistence.Embeddable;

import nl.bzk.brp.pocmotor.model.gedeeld.gen.attribuuttype.AbstractISO31661Alpha2;
import java.lang.String;

/**
 * ISO 3166-1 alpha 2

 */
@Embeddable
public class ISO31661Alpha2 extends AbstractISO31661Alpha2 {

    public ISO31661Alpha2() {
        super();
    }

    public ISO31661Alpha2(final String waarde) {
        this();
        setWaarde(waarde);
    }

}
