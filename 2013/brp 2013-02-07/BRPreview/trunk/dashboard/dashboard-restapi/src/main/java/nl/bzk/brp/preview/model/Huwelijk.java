/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

/**
 * De Class Huwelijk.
 */
public class Huwelijk extends AdministratieveHandeling {

    /** De datum aanvang. */
    private Datum datumAanvang = new Datum();

    /** De plaats. */
    private Plaats plaats;

    /** De persoon1. */
    private Persoon persoon1;

    /** De persoon2. */
    private Persoon persoon2;

    /**
     * Instantieert een nieuwe huwelijk.
     *
     * @param administratieveHandeling de administratieve handeling
     */
    public Huwelijk(final AdministratieveHandeling administratieveHandeling) {
        super(administratieveHandeling);
    }

    /**
     * Haalt een plaats op.
     *
     * @return plaats
     */
    public Plaats getPlaats() {
        return plaats;
    }

    /**
     * Instellen van plaats.
     *
     * @param plaats de nieuwe plaats
     */
    public void setPlaats(final Plaats plaats) {
        this.plaats = plaats;
    }

    /**
     * Haalt een persoon1 op.
     *
     * @return persoon1
     */
    public Persoon getPersoon1() {
        return persoon1;
    }

    /**
     * Haalt een persoon2 op.
     *
     * @return persoon2
     */
    public Persoon getPersoon2() {
        return persoon2;
    }

    /**
     * Haalt een datum aanvang tekst op.
     *
     * @return datum aanvang tekst
     */
    public String getDatumAanvangTekst() {
        return datumAanvang.getTekst();
    }

    /**
     * Instellen van persoon1.
     *
     * @param persoon1 de nieuwe persoon1
     */
    public void setPersoon1(final Persoon persoon1) {
        this.persoon1 = persoon1;
    }

    /**
     * Instellen van persoon2.
     *
     * @param persoon2 de nieuwe persoon2
     */
    public void setPersoon2(final Persoon persoon2) {
        this.persoon2 = persoon2;
    }

    /**
     * Haalt een datum aanvang op.
     *
     * @return datum aanvang
     */
    public Datum getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * Instellen van datum aanvang.
     *
     * @param datumAanvang de nieuwe datum aanvang
     */
    public void setDatumAanvang(final Datum datumAanvang) {
        this.datumAanvang = datumAanvang;
    }
}
