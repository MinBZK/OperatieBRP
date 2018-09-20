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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatieAttribuut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;

/**
 * Interface voor Relatie.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface RelatieHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Integer>, HisVolledigRootObject {

    /**
     * Retourneert de historie van His Relatie.
     *
     * @return Historie met His Relatie
     */
    FormeleHistorieSet<HisRelatieModel> getRelatieHistorie();

    /**
     * Retourneert Soort van Relatie.
     *
     * @return Soort van Relatie
     */
    SoortRelatieAttribuut getSoort();

    /**
     * Retourneert lijst van Betrokkenheid.
     *
     * @return lijst van Betrokkenheid
     */
    Set<? extends BetrokkenheidHisVolledig> getBetrokkenheden();

}
