/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerificatieModel;

/**
 * Interface voor Persoon \ Verificatie.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface PersoonVerificatieHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Long>, BrpObject {

    /**
     * Retourneert de historie van His Persoon \ Verificatie.
     *
     * @return Historie met His Persoon \ Verificatie
     */
    FormeleHistorieSet<HisPersoonVerificatieModel> getPersoonVerificatieHistorie();

    /**
     * Retourneert Geverifieerde van Persoon \ Verificatie.
     *
     * @return Geverifieerde van Persoon \ Verificatie
     */
    PersoonHisVolledig getGeverifieerde();

    /**
     * Retourneert Partij van Persoon \ Verificatie.
     *
     * @return Partij van Persoon \ Verificatie
     */
    PartijAttribuut getPartij();

    /**
     * Retourneert Soort van Persoon \ Verificatie.
     *
     * @return Soort van Persoon \ Verificatie
     */
    NaamEnumeratiewaardeAttribuut getSoort();

}
