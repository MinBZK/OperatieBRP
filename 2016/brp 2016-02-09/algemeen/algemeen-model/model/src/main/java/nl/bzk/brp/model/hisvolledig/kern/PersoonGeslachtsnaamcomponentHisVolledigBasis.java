/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;
import nl.bzk.brp.model.basis.VolgnummerBevattend;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsnaamcomponentModel;

/**
 * Interface voor Persoon \ Geslachtsnaamcomponent.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface PersoonGeslachtsnaamcomponentHisVolledigBasis extends ModelPeriode, VolgnummerBevattend, ModelIdentificeerbaar<Integer>, BrpObject {

    /**
     * Retourneert de historie van His Persoon \ Geslachtsnaamcomponent.
     *
     * @return Historie met His Persoon \ Geslachtsnaamcomponent
     */
    MaterieleHistorieSet<HisPersoonGeslachtsnaamcomponentModel> getPersoonGeslachtsnaamcomponentHistorie();

    /**
     * Retourneert Persoon van Persoon \ Geslachtsnaamcomponent.
     *
     * @return Persoon van Persoon \ Geslachtsnaamcomponent
     */
    PersoonHisVolledig getPersoon();

    /**
     * Retourneert Volgnummer van Persoon \ Geslachtsnaamcomponent.
     *
     * @return Volgnummer van Persoon \ Geslachtsnaamcomponent
     */
    VolgnummerAttribuut getVolgnummer();

}
