/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocumentAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;
import nl.bzk.brp.model.operationeel.kern.HisPersoonReisdocumentModel;

/**
 * Interface voor Persoon \ Reisdocument.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface PersoonReisdocumentHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Integer>, BrpObject {

    /**
     * Retourneert de historie van His Persoon \ Reisdocument.
     *
     * @return Historie met His Persoon \ Reisdocument
     */
    FormeleHistorieSet<HisPersoonReisdocumentModel> getPersoonReisdocumentHistorie();

    /**
     * Retourneert Persoon van Persoon \ Reisdocument.
     *
     * @return Persoon van Persoon \ Reisdocument
     */
    PersoonHisVolledig getPersoon();

    /**
     * Retourneert Soort van Persoon \ Reisdocument.
     *
     * @return Soort van Persoon \ Reisdocument
     */
    SoortNederlandsReisdocumentAttribuut getSoort();

}
