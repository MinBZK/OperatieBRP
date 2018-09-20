/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.prot;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;

/**
 * Interface voor Leveringsaantekening \ Persoon.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface LeveringsaantekeningPersoonHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Long>, BrpObject {

    /**
     * Retourneert Leveringsaantekening van Leveringsaantekening \ Persoon.
     *
     * @return Leveringsaantekening van Leveringsaantekening \ Persoon
     */
    LeveringsaantekeningHisVolledig getLeveringsaantekening();

    /**
     * Retourneert Persoon van Leveringsaantekening \ Persoon.
     *
     * @return Persoon van Leveringsaantekening \ Persoon
     */
    Integer getPersoonId();

}
