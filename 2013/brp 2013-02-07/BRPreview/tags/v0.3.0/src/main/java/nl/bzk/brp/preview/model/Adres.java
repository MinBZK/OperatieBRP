/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

public class Adres {

    private Gemeente gemeente;

    private String   straat;

    private String   huisnummer;

    private int      datumAanvangAdreshouding;

    public Gemeente getGemeente() {
        return gemeente;
    }

    public void setGemeente(final Gemeente gemeente) {
        this.gemeente = gemeente;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(final String straat) {
        this.straat = straat;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(final String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public int getDatumAanvangAdreshouding() {
        return datumAanvangAdreshouding;
    }

    public void setDatumAanvangAdreshouding(final int datumAanvangAdreshouding) {
        this.datumAanvangAdreshouding = datumAanvangAdreshouding;
    }

}
