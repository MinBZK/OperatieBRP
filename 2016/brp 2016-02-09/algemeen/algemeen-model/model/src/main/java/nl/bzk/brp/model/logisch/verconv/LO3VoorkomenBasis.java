/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.verconv;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3CategorieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * Een voorkomen (of het ontbreken daarvan) van een
 *
 * Een LO3 bericht bevat één of meer categorieën, sommige daarvan repeterend (te zien aan LO3 stapelvolgnummer); elke
 * (repetitie van een) categorië bevat één of meer voorkomens. In gevallen waarbij het LO3 bericht een voorkomen hád
 * moeten hebben, maar dit niet heeft, wordt dit ontbrekende voorkomen hier alsnog vastgelegd, zodat een LO3 melding
 * hierover kan worden vastgelegd.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface LO3VoorkomenBasis extends BrpObject {

    /**
     * Retourneert LO3 Bericht van LO3 Voorkomen.
     *
     * @return LO3 Bericht.
     */
    LO3Bericht getLO3Bericht();

    /**
     * Retourneert LO3 categorie van LO3 Voorkomen.
     *
     * @return LO3 categorie.
     */
    LO3CategorieAttribuut getLO3Categorie();

    /**
     * Retourneert LO3 stapelvolgnummer van LO3 Voorkomen.
     *
     * @return LO3 stapelvolgnummer.
     */
    VolgnummerAttribuut getLO3Stapelvolgnummer();

    /**
     * Retourneert LO3 voorkomenvolgnummer van LO3 Voorkomen.
     *
     * @return LO3 voorkomenvolgnummer.
     */
    VolgnummerAttribuut getLO3Voorkomenvolgnummer();

    /**
     * Retourneert Mapping van LO3 Voorkomen.
     *
     * @return Mapping.
     */
    LO3VoorkomenMappingGroep getMapping();

}
