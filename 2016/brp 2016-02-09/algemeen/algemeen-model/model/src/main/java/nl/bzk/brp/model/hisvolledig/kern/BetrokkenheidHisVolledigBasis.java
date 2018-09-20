/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheidAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;
import nl.bzk.brp.model.operationeel.kern.HisBetrokkenheidModel;

/**
 * Interface voor Betrokkenheid.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface BetrokkenheidHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Integer>, BrpObject {

    /**
     * Retourneert de historie van His Betrokkenheid.
     *
     * @return Historie met His Betrokkenheid
     */
    FormeleHistorieSet<HisBetrokkenheidModel> getBetrokkenheidHistorie();

    /**
     * Retourneert Relatie van Betrokkenheid.
     *
     * @return Relatie van Betrokkenheid
     */
    RelatieHisVolledig getRelatie();

    /**
     * Retourneert Rol van Betrokkenheid.
     *
     * @return Rol van Betrokkenheid
     */
    SoortBetrokkenheidAttribuut getRol();

    /**
     * Retourneert Persoon van Betrokkenheid.
     *
     * @return Persoon van Betrokkenheid
     */
    PersoonHisVolledig getPersoon();

}
