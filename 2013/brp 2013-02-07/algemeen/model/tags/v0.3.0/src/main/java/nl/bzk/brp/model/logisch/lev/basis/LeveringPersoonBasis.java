/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.lev.basis;

import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.lev.Levering;


/**
 * Het betrokken zijn van een Persoon in een Levering.
 *
 * Bij een Levering van Persoonsgegevens, zijn ��n of meer Personen het "onderwerp" van de Levering. Indien een Persoon
 * onderwerp is van een Levering, dan wordt de koppeling tussen deze Levering en de Persoon vastgelegd.
 *
 *
 *
 */
public interface LeveringPersoonBasis extends ObjectType {

    /**
     * Retourneert Levering van Levering \ Persoon.
     *
     * @return Levering.
     */
    Levering getLevering();

    /**
     * Retourneert Persoon van Levering \ Persoon.
     *
     * @return Persoon.
     */
    Persoon getPersoon();

}
