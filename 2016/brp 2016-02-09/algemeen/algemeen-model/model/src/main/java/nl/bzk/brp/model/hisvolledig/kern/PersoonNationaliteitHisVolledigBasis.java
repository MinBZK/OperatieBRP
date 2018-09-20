/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NationaliteitAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;

/**
 * Interface voor Persoon \ Nationaliteit.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface PersoonNationaliteitHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Integer>, BrpObject {

    /**
     * Retourneert de historie van His Persoon \ Nationaliteit.
     *
     * @return Historie met His Persoon \ Nationaliteit
     */
    MaterieleHistorieSet<HisPersoonNationaliteitModel> getPersoonNationaliteitHistorie();

    /**
     * Retourneert Persoon van Persoon \ Nationaliteit.
     *
     * @return Persoon van Persoon \ Nationaliteit
     */
    PersoonHisVolledig getPersoon();

    /**
     * Retourneert Nationaliteit van Persoon \ Nationaliteit.
     *
     * @return Nationaliteit van Persoon \ Nationaliteit
     */
    NationaliteitAttribuut getNationaliteit();

}
