/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.util.StringUtils;

public class Adres {

    private Gemeente gemeente;

    private String   straat;

    private String   huisnummer;

    private String   huisnummertoevoeging;

    private Plaats   plaats;

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

    public String getHuisnummertoevoeging() {
        return huisnummertoevoeging;
    }

    public void setHuisnummertoevoeging(final String huisnummertoevoeging) {
        this.huisnummertoevoeging = huisnummertoevoeging;
    }

    public Plaats getPlaats() {
        return plaats;
    }

    public void setPlaats(final Plaats plaats) {
        this.plaats = plaats;
    }

    public int getDatumAanvangAdreshouding() {
        return datumAanvangAdreshouding;
    }

    public void setDatumAanvangAdreshouding(final int datumAanvangAdreshouding) {
        this.datumAanvangAdreshouding = datumAanvangAdreshouding;
    }

    @JsonIgnore
    public String getHuisnummerVolledig() {
        if (StringUtils.hasText(huisnummertoevoeging)) {
            return huisnummer + " " + huisnummertoevoeging;
        } else {
            return huisnummer;
        }
    }

}
