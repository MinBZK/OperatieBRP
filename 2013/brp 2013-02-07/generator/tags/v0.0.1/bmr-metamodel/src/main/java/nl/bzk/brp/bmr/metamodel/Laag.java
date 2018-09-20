/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


/**
 * Een attribuut van een object type.
 */
@Entity
@DiscriminatorValue("L")
public class Laag extends Element {

    /**
     * Logische modellaag.
     */
    public static final int LOGISCH_MODEL      = 1749;
    /**
     * Operationele modellaag.
     */
    public static final int OPERATIONEEL_MODEL = 1751;

    public static Integer   huidigeLaag        = OPERATIONEEL_MODEL;

    /**
     * @return the huidigeLaag
     */
    public static int getHuidigeLaag() {
        return huidigeLaag;
    }

    /**
     * @param huidigeLaag the huidigeLaag to set
     */
    public static void setHuidigeLaag(final Integer huidigeLaag) {
        Laag.huidigeLaag = huidigeLaag;
    }
}
