/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import java.util.Set;
import javax.annotation.Generated;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.HisVolledigRootObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;

/**
 * Interface voor Onderzoek.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface OnderzoekHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Integer>, HisVolledigRootObject {

    /**
     * Retourneert de historie van His Onderzoek.
     *
     * @return Historie met His Onderzoek
     */
    FormeleHistorieSet<HisOnderzoekModel> getOnderzoekHistorie();

    /**
     * Retourneert de historie van His Onderzoek Afgeleid administratief.
     *
     * @return Historie met His Onderzoek Afgeleid administratief
     */
    FormeleHistorieSet<HisOnderzoekAfgeleidAdministratiefModel> getOnderzoekAfgeleidAdministratiefHistorie();

    /**
     * Retourneert lijst van Gegeven in onderzoek.
     *
     * @return lijst van Gegeven in onderzoek
     */
    Set<? extends GegevenInOnderzoekHisVolledig> getGegevensInOnderzoek();

    /**
     * Retourneert lijst van Persoon \ Onderzoek.
     *
     * @return lijst van Persoon \ Onderzoek
     */
    Set<? extends PersoonOnderzoekHisVolledig> getPersonenInOnderzoek();

    /**
     * Retourneert lijst van Partij \ Onderzoek.
     *
     * @return lijst van Partij \ Onderzoek
     */
    Set<? extends PartijOnderzoekHisVolledig> getPartijenInOnderzoek();

}
