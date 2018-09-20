/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ber;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * De opsomming van in een antwoord betrokken personen.
 *
 * Het betreft een constructie met als doel het genereren van de gewenste structuren in de XSD's.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface BerichtPersoonBasis extends BrpObject {

    /**
     * Retourneert Bericht van Bericht \ Persoon.
     *
     * @return Bericht.
     */
    Bericht getBericht();

    /**
     * Retourneert Persoon van Bericht \ Persoon.
     *
     * @return Persoon.
     */
    Integer getPersoonId();

}
