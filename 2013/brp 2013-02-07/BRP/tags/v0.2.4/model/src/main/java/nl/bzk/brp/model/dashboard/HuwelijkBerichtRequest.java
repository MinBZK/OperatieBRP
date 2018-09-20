/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.dashboard;

/**
 * Geboortebericht notificatie voor het dashboard.
 */
public class HuwelijkBerichtRequest extends AbstractBerichtRequest {

    private final Datum datumAanvang = new Datum();
    private Plaats      plaats;
    private Persoon     persoon1;
    private Persoon     persoon2;

    public Plaats getPlaats() {
        return plaats;
    }

    public void setPlaats(final Plaats plaats) {
        this.plaats = plaats;
    }

    public int getDatumAanvang() {
        return datumAanvang.getDecimalen();
    }

    /**
     * @param datumAanvang datum waarin nul waardes zijn toegestaan
     */
    public void setDatumAanvang(final int datumAanvang) {
        this.datumAanvang.setDecimalen(datumAanvang);
    }

    public Persoon getPersoon1() {
        return persoon1;
    }

    public void setPersoon1(final Persoon persoon1) {
        this.persoon1 = persoon1;
    }

    public Persoon getPersoon2() {
        return persoon2;
    }

    public void setPersoon2(final Persoon persoon2) {
        this.persoon2 = persoon2;
    }

}
