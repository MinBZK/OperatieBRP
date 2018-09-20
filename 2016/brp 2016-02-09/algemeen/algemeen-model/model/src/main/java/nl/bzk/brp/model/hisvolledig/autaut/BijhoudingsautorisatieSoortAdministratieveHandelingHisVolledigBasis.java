/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.autaut;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;

/**
 * Interface voor Bijhoudingsautorisatie \ Soort administratieve handeling.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Integer>, BrpObject {

    /**
     * Retourneert Toegang bijhoudingsautorisatie van Bijhoudingsautorisatie \ Soort administratieve handeling.
     *
     * @return Toegang bijhoudingsautorisatie van Bijhoudingsautorisatie \ Soort administratieve handeling
     */
    ToegangBijhoudingsautorisatieHisVolledig getToegangBijhoudingsautorisatie();

    /**
     * Retourneert Soort administratieve handeling van Bijhoudingsautorisatie \ Soort administratieve handeling.
     *
     * @return Soort administratieve handeling van Bijhoudingsautorisatie \ Soort administratieve handeling
     */
    SoortAdministratieveHandelingAttribuut getSoortAdministratieveHandeling();

}
