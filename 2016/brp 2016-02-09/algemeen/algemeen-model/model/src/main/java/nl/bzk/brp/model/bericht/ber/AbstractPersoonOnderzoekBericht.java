/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import javax.annotation.Generated;

import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.bericht.kern.OnderzoekBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.logisch.ber.PersoonOnderzoekBasis;

/**
 * De onderzoeken waar de persoon in betrokken is.
 *
 * Koppelentiteit om in berichten onderzoeken onder een persoon te kunnen hangen.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonOnderzoekBericht implements BrpObject, PersoonOnderzoekBasis {

    private PersoonBericht persoon;
    private OnderzoekBericht onderzoek;

    /**
     * Retourneert Persoon van Persoon \ Onderzoek.
     *
     * @return Persoon.
     */
    public PersoonBericht getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Onderzoek van Persoon \ Onderzoek.
     *
     * @return Onderzoek.
     */
    public OnderzoekBericht getOnderzoek() {
        return onderzoek;
    }

    /**
     * Zet Persoon van Persoon \ Onderzoek.
     *
     * @param persoon Persoon.
     */
    public void setPersoon(final PersoonBericht persoon) {
        this.persoon = persoon;
    }

    /**
     * Zet Onderzoek van Persoon \ Onderzoek.
     *
     * @param onderzoek Onderzoek.
     */
    public void setOnderzoek(final OnderzoekBericht onderzoek) {
        this.onderzoek = onderzoek;
    }

}
