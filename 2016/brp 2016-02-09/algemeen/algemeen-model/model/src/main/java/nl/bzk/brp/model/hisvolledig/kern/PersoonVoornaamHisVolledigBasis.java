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
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;

/**
 * Interface voor Persoon \ Voornaam.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface PersoonVoornaamHisVolledigBasis extends ModelPeriode, VolgnummerBevattend, ModelIdentificeerbaar<Integer>, BrpObject {

    /**
     * Retourneert de historie van His Persoon \ Voornaam.
     *
     * @return Historie met His Persoon \ Voornaam
     */
    MaterieleHistorieSet<HisPersoonVoornaamModel> getPersoonVoornaamHistorie();

    /**
     * Retourneert Persoon van Persoon \ Voornaam.
     *
     * @return Persoon van Persoon \ Voornaam
     */
    PersoonHisVolledig getPersoon();

    /**
     * Retourneert Volgnummer van Persoon \ Voornaam.
     *
     * @return Volgnummer van Persoon \ Voornaam
     */
    VolgnummerAttribuut getVolgnummer();

}
