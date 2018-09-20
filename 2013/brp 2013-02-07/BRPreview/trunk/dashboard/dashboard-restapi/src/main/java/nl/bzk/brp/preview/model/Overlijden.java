/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

/**
 * De klasse Overlijden, gebruikt om de administratieve handeling vanuit de database op te halen.
 */
public class Overlijden extends AdministratieveHandeling {

    /** De persoon. */
    private Persoon persoon;

    /** De gemeente overlijden. */
    private Gemeente gemeenteOverlijden;

    /** De datum overlijden. */
    private Datum datumOverlijden;

    /**
     * Instantieert een nieuwe overlijden object.
     */
    public Overlijden() {
    }

    /**
     * Instantieert een nieuwe overlijden.
     *
     * @param administratieveHandeling de administratieve handeling
     */
    public Overlijden(final AdministratieveHandeling administratieveHandeling) {
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
     * Haalt een gemeente overlijden op.
     *
     * @return gemeente overlijden
     */
    public Gemeente getGemeenteOverlijden() {
        return gemeenteOverlijden;
    }

    /**
     * Instellen van gemeente overlijden.
     *
     * @param gemeenteOverlijden de nieuwe gemeente overlijden
     */
    public void setGemeenteOverlijden(final Gemeente gemeenteOverlijden) {
        this.gemeenteOverlijden = gemeenteOverlijden;
    }

    /**
     * Haalt een datum overlijden op.
     *
     * @return datum overlijden
     */
    public Datum getDatumOverlijden() {
        return datumOverlijden;
    }

    /**
     * Instellen van datum overlijden.
     *
     * @param datumOverlijden de nieuwe datum overlijden
     */
    public void setDatumOverlijden(final Datum datumOverlijden) {
        this.datumOverlijden = datumOverlijden;
    }

}
