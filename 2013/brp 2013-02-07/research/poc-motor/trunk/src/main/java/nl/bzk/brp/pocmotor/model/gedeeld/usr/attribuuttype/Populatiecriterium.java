/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype;

import javax.persistence.Embeddable;

import nl.bzk.brp.pocmotor.model.gedeeld.gen.attribuuttype.AbstractPopulatiecriterium;
import java.lang.String;

/**
 * Populatiecriterium

 */
@Embeddable
public class Populatiecriterium extends AbstractPopulatiecriterium {

    public Populatiecriterium() {
        super();
    }

    public Populatiecriterium(final String waarde) {
        this();
        setWaarde(waarde);
    }

}
