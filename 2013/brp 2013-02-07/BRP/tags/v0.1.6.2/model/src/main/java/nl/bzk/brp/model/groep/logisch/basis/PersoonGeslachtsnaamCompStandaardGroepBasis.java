/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.logisch.basis;

import nl.bzk.brp.model.attribuuttype.GeslachtsnaamComponent;
import nl.bzk.brp.model.attribuuttype.ScheidingsTeken;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Predikaat;


/**
 * Interface standaard groep object type Persoon Geslachtsnaam component.
 */
public interface PersoonGeslachtsnaamCompStandaardGroepBasis extends Groep {

    /**
     * Retourneert het Predikaat.
     *
     * @return Predikaat.
     */
    Predikaat getPredikaat();

    /**
     * Retourneert adellijke titel.
     *
     * @return Adellijke titel.
     */
    AdellijkeTitel getAdellijkeTitel();

    /**
     * Retourneert voovoegsel.
     *
     * @return Voorvoegsel.
     */
    Voorvoegsel getVoorvoegsel();

    /**
     * Retourneert scheidingsteken.
     *
     * @return Scheidingsteken.
     */
    ScheidingsTeken getScheidingsteken();

    /**
     * Retourneert geslachtsnaam.
     *
     * @return Geslachtsnaam.
     */
    GeslachtsnaamComponent getNaam();
}
