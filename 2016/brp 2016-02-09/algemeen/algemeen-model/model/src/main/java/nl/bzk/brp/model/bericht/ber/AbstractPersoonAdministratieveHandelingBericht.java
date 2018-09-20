/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.logisch.ber.PersoonAdministratieveHandelingBasis;

/**
 * De in het bericht opgenomen administratieve handelingen die betrekking hebben op een persoon.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonAdministratieveHandelingBericht implements BrpObject, PersoonAdministratieveHandelingBasis {

    private PersoonBericht persoon;
    private AdministratieveHandelingBericht administratieveHandeling;

    /**
     * Retourneert Persoon van Persoon \ Administratieve handeling.
     *
     * @return Persoon.
     */
    public PersoonBericht getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Administratieve handeling van Persoon \ Administratieve handeling.
     *
     * @return Administratieve handeling.
     */
    public AdministratieveHandelingBericht getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Zet Persoon van Persoon \ Administratieve handeling.
     *
     * @param persoon Persoon.
     */
    public void setPersoon(final PersoonBericht persoon) {
        this.persoon = persoon;
    }

    /**
     * Zet Administratieve handeling van Persoon \ Administratieve handeling.
     *
     * @param administratieveHandeling Administratieve handeling.
     */
    public void setAdministratieveHandeling(final AdministratieveHandelingBericht administratieveHandeling) {
        this.administratieveHandeling = administratieveHandeling;
    }

}
