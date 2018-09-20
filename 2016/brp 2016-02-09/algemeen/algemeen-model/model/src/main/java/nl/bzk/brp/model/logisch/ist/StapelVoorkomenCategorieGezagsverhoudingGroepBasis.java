/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ist;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface StapelVoorkomenCategorieGezagsverhoudingGroepBasis extends Groep {

    /**
     * Retourneert Ouder 1 heeft gezag? van Categorie gezagsverhouding.
     *
     * @return Ouder 1 heeft gezag?.
     */
    JaAttribuut getIndicatieOuder1HeeftGezag();

    /**
     * Retourneert Ouder 2 heeft gezag? van Categorie gezagsverhouding.
     *
     * @return Ouder 2 heeft gezag?.
     */
    JaAttribuut getIndicatieOuder2HeeftGezag();

    /**
     * Retourneert Derde heeft gezag? van Categorie gezagsverhouding.
     *
     * @return Derde heeft gezag?.
     */
    JaAttribuut getIndicatieDerdeHeeftGezag();

    /**
     * Retourneert Onder curatele? van Categorie gezagsverhouding.
     *
     * @return Onder curatele?.
     */
    JaAttribuut getIndicatieOnderCuratele();

}
