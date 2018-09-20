/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOnderzoekModel;

/**
 * Interface voor Persoon \ Onderzoek.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface PersoonOnderzoekHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Integer>, BrpObject {

    /**
     * Retourneert de historie van His Persoon \ Onderzoek.
     *
     * @return Historie met His Persoon \ Onderzoek
     */
    FormeleHistorieSet<HisPersoonOnderzoekModel> getPersoonOnderzoekHistorie();

    /**
     * Retourneert Persoon van Persoon \ Onderzoek.
     *
     * @return Persoon van Persoon \ Onderzoek
     */
    PersoonHisVolledig getPersoon();

    /**
     * Retourneert Onderzoek van Persoon \ Onderzoek.
     *
     * @return Onderzoek van Persoon \ Onderzoek
     */
    OnderzoekHisVolledig getOnderzoek();

}
