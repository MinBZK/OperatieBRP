/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import nl.bzk.brp.model.data.kern.Persadres;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.util.StringUtils;


public class Adres {

    private Gemeente    gemeente;

    private String      straat;

    private String      huisnummer;

    private String      huisnummertoevoeging;

    private Plaats      plaats;

    private final Datum datumAanvangAdreshouding = new Datum();

    public Adres() {
    }

    public Adres(final Gemeente gemeente) {
        this.gemeente = gemeente;
    }

    public Adres(final Persadres adres) {
        gemeente = new Gemeente(adres.getGem());
        straat = getStraat(adres);
        huisnummer = adres.getHuisnr();
        huisnummertoevoeging = adres.getHuisnrtoevoeging();
        plaats = new Plaats(adres.getWpl());
    }

    private String getStraat(final Persadres adres) {
        String resultaat;
        if (StringUtils.hasText(adres.getAfgekortenor())) {
            resultaat = adres.getAfgekortenor();
        } else {
            resultaat = adres.getNor();
        }
        return resultaat;
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
        return huisnummer == null ? "nr. onbekend" : huisnummer;
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

    public void setDatumAanvangAdreshouding(final int datumAanvangAdreshouding) {
        this.datumAanvangAdreshouding.setDecimalen(datumAanvangAdreshouding);
    }

    @JsonIgnore
    public String getHuisnummerVolledig() {
        if (StringUtils.hasText(huisnummertoevoeging)) {
            return getHuisnummer() + " " + huisnummertoevoeging;
        } else {
            return getHuisnummer();
        }
    }

    @JsonIgnore
    public String getAdresVolledig() {

        String verhuisDatum = datumAanvangAdreshouding.getTekst();
        String template = "Adres per %s: %s %s%s.";

        return String.format(template, verhuisDatum, straat, getHuisnummerVolledig(), getPlaatsTekst());
    }

    @JsonIgnore
    private String getPlaatsTekst() {
        String resultaat;
        if (plaats == null || plaats.getNaam() == null || plaats.getNaam().length() == 0) {
            resultaat = "";
        } else {
            resultaat = String.format(" (%s)", plaats.getNaam());
        }
        return resultaat;
    }

}
