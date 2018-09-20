/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;

/**
 * Interface voor Administratieve handeling \ Gedeblokkeerde melding.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Long>, BrpObject {

    /**
     * Retourneert Administratieve handeling van Administratieve handeling \ Gedeblokkeerde melding.
     *
     * @return Administratieve handeling van Administratieve handeling \ Gedeblokkeerde melding
     */
    AdministratieveHandelingHisVolledig getAdministratieveHandeling();

    /**
     * Retourneert Gedeblokkeerde melding van Administratieve handeling \ Gedeblokkeerde melding.
     *
     * @return Gedeblokkeerde melding van Administratieve handeling \ Gedeblokkeerde melding
     */
    GedeblokkeerdeMeldingHisVolledig getGedeblokkeerdeMelding();

}
