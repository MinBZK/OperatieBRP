/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GegevenswaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementAttribuut;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * Een door de terugmelder aangeduid gegeven waarover (gerede) twijfel is.
 *
 * Bij onderzoek is het linken naar gegevens 'schaars': verreweg de meeste gegevens staan niet in onderzoek en hebben
 * ook niet in onderzoek gestaan. Daarom wordt er hier verwezen naar het gegeven vanuit het onderzoek en niet andersom
 * (vanuit het gegeven naar het onderzoek zoals bij verantwoording).
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface GegevenInTerugmeldingBasis extends BrpObject {

    /**
     * Retourneert Terugmelding van Gegeven in terugmelding.
     *
     * @return Terugmelding.
     */
    Terugmelding getTerugmelding();

    /**
     * Retourneert Element van Gegeven in terugmelding.
     *
     * @return Element.
     */
    ElementAttribuut getElement();

    /**
     * Retourneert Betwijfelde waarde van Gegeven in terugmelding.
     *
     * @return Betwijfelde waarde.
     */
    GegevenswaardeAttribuut getBetwijfeldeWaarde();

    /**
     * Retourneert Verwachte waarde van Gegeven in terugmelding.
     *
     * @return Verwachte waarde.
     */
    GegevenswaardeAttribuut getVerwachteWaarde();

}
