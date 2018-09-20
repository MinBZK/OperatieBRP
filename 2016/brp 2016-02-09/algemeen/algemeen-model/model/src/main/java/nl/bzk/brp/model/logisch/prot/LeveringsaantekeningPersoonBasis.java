/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.prot;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * Het betrokken zijn van een Persoon in een Levering.
 *
 * Bij een Levering van Persoonsgegevens, zijn één of meer Personen het "onderwerp" van de Levering. Indien een Persoon
 * onderwerp is van een Levering, dan wordt de koppeling tussen deze Levering en de Persoon vastgelegd.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface LeveringsaantekeningPersoonBasis extends BrpObject {

    /**
     * Retourneert Leveringsaantekening van Leveringsaantekening \ Persoon.
     *
     * @return Leveringsaantekening.
     */
    Leveringsaantekening getLeveringsaantekening();

    /**
     * Retourneert Persoon van Leveringsaantekening \ Persoon.
     *
     * @return Persoon.
     */
    Integer getPersoonId();

}
