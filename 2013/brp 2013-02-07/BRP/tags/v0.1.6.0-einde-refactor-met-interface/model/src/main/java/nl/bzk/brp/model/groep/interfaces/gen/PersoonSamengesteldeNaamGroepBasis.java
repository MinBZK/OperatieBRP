/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.interfaces.gen;

import nl.bzk.brp.model.attribuuttype.GeslachtsnaamComponent;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.ScheidingsTeken;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.objecttype.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.statisch.Predikaat;

/**
 * .
 */

public interface PersoonSamengesteldeNaamGroepBasis extends Groep {
    /**
     * .
     * @return .
     */
    Predikaat               getPredikaat();
    /**
     * .
     * @return .
     */
    AdellijkeTitel          getAdellijkeTitel();
    /**
     * .
     * @return .
     */
    Voornaam                getVoornamen();
    /**
     * .
     * @return .
     */
    Voorvoegsel             getVoorvoegsel();
    /**
     * .
     * @return .
     */
    ScheidingsTeken         getScheidingsteken();
    /**
     * .
     * @return .
     */
    GeslachtsnaamComponent  getGeslachtsnaam();
    /**
     * .
     * @return .
     */
    JaNee                   getIndNamenreeksAlsGeslachtsNaam();
    /**
     * .
     * @return .
     */
    JaNee                   getIndAlgorithmischAfgeleid();

    // technisch
    // AttribuutType<Integer>  getStatus();
}
