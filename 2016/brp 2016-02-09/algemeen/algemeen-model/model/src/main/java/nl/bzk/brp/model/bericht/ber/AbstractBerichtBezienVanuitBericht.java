/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.logisch.ber.BerichtBezienVanuitBasis;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractBerichtBezienVanuitBericht implements BrpObject, BerichtBezienVanuitBasis {

    private BerichtBericht bericht;
    private PersoonBericht persoon;

    /**
     * Retourneert Bericht van Bericht \ Bezien vanuit.
     *
     * @return Bericht.
     */
    public BerichtBericht getBericht() {
        return bericht;
    }

    /**
     * Retourneert Persoon van Bericht \ Bezien vanuit.
     *
     * @return Persoon.
     */
    public PersoonBericht getPersoon() {
        return persoon;
    }

    /**
     * Zet Bericht van Bericht \ Bezien vanuit.
     *
     * @param bericht Bericht.
     */
    public void setBericht(final BerichtBericht bericht) {
        this.bericht = bericht;
    }

    /**
     * Zet Persoon van Bericht \ Bezien vanuit.
     *
     * @param persoon Persoon.
     */
    public void setPersoon(final PersoonBericht persoon) {
        this.persoon = persoon;
    }

}
