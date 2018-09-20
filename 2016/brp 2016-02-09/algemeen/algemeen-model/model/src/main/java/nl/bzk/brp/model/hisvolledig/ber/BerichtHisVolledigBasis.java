/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.ber;

import java.util.Set;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.RichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;

/**
 * Interface voor Bericht.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface BerichtHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Long>, BrpObject {

    /**
     * Retourneert Soort van Bericht.
     *
     * @return Soort van Bericht
     */
    SoortBerichtAttribuut getSoort();

    /**
     * Retourneert Richting van Bericht.
     *
     * @return Richting van Bericht
     */
    RichtingAttribuut getRichting();

    /**
     * Retourneert lijst van Bericht \ Persoon.
     *
     * @return lijst van Bericht \ Persoon
     */
    Set<? extends BerichtPersoonHisVolledig> getPersonen();

}
