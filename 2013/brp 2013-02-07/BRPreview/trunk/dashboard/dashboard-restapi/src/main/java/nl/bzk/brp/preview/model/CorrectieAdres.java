/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import java.util.List;


/**
 * De klasse CorrectieAdres.
 */
public class CorrectieAdres extends AdministratieveHandeling {

    /**
     * De persoon.
     */
    private Persoon persoon;

    /**
     * De adressen.
     */
    private List<Adres> adressen;

    /**
     * Instantieert een nieuwe correctie adres.
     *
     * @param administratieveHandeling de administratieve handeling
     */
    public CorrectieAdres(final AdministratieveHandeling administratieveHandeling) {
        super(administratieveHandeling);
    }


    /**
     * Haalt een persoon op.
     *
     * @return persoon
     */
    public Persoon getPersoon() {
        return persoon;
    }


    /**
     * Instellen van persoon.
     *
     * @param persoon de nieuwe persoon
     */
    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }


    /**
     * Haalt adressen op.
     *
     * @return adressen
     */
    public List<Adres> getAdressen() {
        return adressen;
    }

    /**
     * Instellen van adressen.
     *
     * @param adressen de nieuwe adressen
     */
    public void setAdressen(final List<Adres> adressen) {
        this.adressen = adressen;
    }


}
