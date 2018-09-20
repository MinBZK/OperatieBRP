/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * De OT:Betrokkenheid van een OT:Persoon in de _rol_ van "_Ouder_" in een OT:"Familierechtelijke Betrekking".
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface OuderBasis extends Betrokkenheid, BrpObject {

    /**
     * Retourneert Ouderschap van Ouder.
     *
     * @return Ouderschap.
     */
    OuderOuderschapGroep getOuderschap();

    /**
     * Retourneert Ouderlijk gezag van Ouder.
     *
     * @return Ouderlijk gezag.
     */
    OuderOuderlijkGezagGroep getOuderlijkGezag();

}
