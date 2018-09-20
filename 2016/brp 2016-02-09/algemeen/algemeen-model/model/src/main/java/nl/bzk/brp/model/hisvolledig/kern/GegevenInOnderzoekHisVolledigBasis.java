/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;

/**
 * Interface voor Gegeven in onderzoek.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface GegevenInOnderzoekHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Integer>, BrpObject {

    /**
     * Retourneert Onderzoek van Gegeven in onderzoek.
     *
     * @return Onderzoek van Gegeven in onderzoek
     */
    OnderzoekHisVolledig getOnderzoek();

    /**
     * Retourneert Element van Gegeven in onderzoek.
     *
     * @return Element van Gegeven in onderzoek
     */
    ElementAttribuut getElement();

    /**
     * Retourneert Object sleutel gegeven van Gegeven in onderzoek.
     *
     * @return Object sleutel gegeven van Gegeven in onderzoek
     */
    SleutelwaardeAttribuut getObjectSleutelGegeven();

    /**
     * Retourneert Voorkomen sleutel gegeven van Gegeven in onderzoek.
     *
     * @return Voorkomen sleutel gegeven van Gegeven in onderzoek
     */
    SleutelwaardeAttribuut getVoorkomenSleutelGegeven();

}
