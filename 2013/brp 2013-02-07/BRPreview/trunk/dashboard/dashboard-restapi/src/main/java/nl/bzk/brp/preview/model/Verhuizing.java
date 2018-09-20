/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;


/**
 * De klasse Verhuizing.
 */
public class Verhuizing extends AdministratieveHandeling {

    /**
     * De persoon.
     */
    private Persoon persoon;

    /**
     * De oud adres.
     */
    private Adres oudAdres;

    /**
     * De nieuw adres.
     */
    private Adres nieuwAdres;

    /**
     * Instantieert een nieuwe verhuizing.
     *
     * @param administratieveHandeling de administratieve handeling
     */
    public Verhuizing(final AdministratieveHandeling administratieveHandeling) {
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
     * Haalt een oud adres op.
     *
     * @return oud adres
     */
    public Adres getOudAdres() {
        return oudAdres;
    }


    /**
     * Instellen van oud adres.
     *
     * @param oudAdres de nieuwe oud adres
     */
    public void setOudAdres(final Adres oudAdres) {
        this.oudAdres = oudAdres;
    }


    /**
     * Haalt een nieuw adres op.
     *
     * @return nieuw adres
     */
    public Adres getNieuwAdres() {
        return nieuwAdres;
    }


    /**
     * Instellen van nieuw adres.
     *
     * @param nieuwAdres de nieuwe nieuw adres
     */
    public void setNieuwAdres(final Adres nieuwAdres) {
        this.nieuwAdres = nieuwAdres;
    }


    /**
     * Instellen van persoon.
     *
     * @param persoon de nieuwe persoon
     */
    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }


}
