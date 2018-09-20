/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.dashboard;

import nl.bzk.brp.model.objecttype.logisch.PersoonAdres;

/**
 * Adresgegevens.
 */
public class Adres {

    private Gemeente    gemeente;

    private String      straat;

    private String      huisnummer;

    private String      huisnummertoevoeging;

    private Plaats      plaats;

    private final Datum datumAanvangAdreshouding = new Datum();

    /**
     * Constructor voor een dashboard adres op basis van een logisch adres.
     * @param adres logisch adres
     */
    public Adres(final PersoonAdres adres) {
        gemeente = new Gemeente(adres.getGegevens().getGemeente());
        if (adres.getGegevens() != null) {
            if (adres.getGegevens().getAfgekorteNaamOpenbareRuimte()  != null) {
                straat = adres.getGegevens().getAfgekorteNaamOpenbareRuimte().getWaarde();
            } else {
                straat = adres.getGegevens().getNaamOpenbareRuimte().getWaarde();
            }
            if (adres.getGegevens().getHuisnummer() != null) {
                huisnummer = adres.getGegevens().getHuisnummer().getWaarde();
            }
            if (adres.getGegevens().getHuisnummertoevoeging() != null) {
                huisnummertoevoeging = adres.getGegevens().getHuisnummertoevoeging().getWaarde();
            }
            if (adres.getGegevens().getWoonplaats() != null) {
                plaats = new Plaats(adres.getGegevens().getWoonplaats());
            }
            datumAanvangAdreshouding.setDecimalen(adres.getGegevens().getDatumAanvangAdreshouding().getWaarde());
        }
    }

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
        return datumAanvangAdreshouding.getDecimalen();
    }

    /**
     * @param datumAanvangAdreshouding datum waarin nul waardes zijn toegestaan
     */
    public void setDatumAanvangAdreshouding(final int datumAanvangAdreshouding) {
        this.datumAanvangAdreshouding.setDecimalen(datumAanvangAdreshouding);
    }

}
