/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.util.StringUtils;


public class Adres {

    private Gemeente    gemeente;

    private String      straat;

    private String      huisnummer;

    private String      huisnummertoevoeging;

    private Plaats      plaats;

    private final Datum datumAanvang = new Datum();

    private Datum       datumEinde;

    public Adres() {
    }

    public Adres(final Gemeente gemeente) {
        this.gemeente = gemeente;
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

    public int getDatumAanvang() {
        return datumAanvang.getDecimalen();
    }

    public void setDatumAanvang(final Integer datumAanvang) {
        this.datumAanvang.setDecimalen(datumAanvang);
    }

    public Integer getDatumEinde() {
        return datumEinde == null ? null : datumEinde.getDecimalen();
    }

    public void setDatumEinde(final Integer datumEinde) {
        if (datumEinde == null) {
            this.datumEinde = null;
        } else {
            this.datumEinde = new Datum(datumEinde);
        }
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
    public String getTekst() {
        String resultaat = straat + " " + getHuisnummerVolledig();
        if (plaats != null && plaats.getNaam() != null && plaats.getNaam().length() != 0) {
            resultaat += String.format(" (%s)", plaats.getNaam());
        }
        return resultaat;
    }

    @JsonIgnore
    public String getTekstMetPeriode() {
        String format = " Periode: %s tot %s. Adres: %s.";
        return String.format(format, getDatumAanvangTekst(), getDatumEindeTekst(), getTekst());
    }

    @JsonIgnore
    public String getDatumAanvangTekst() {
        return datumAanvang.getTekst();
    }

    @JsonIgnore
    public String getDatumEindeTekst() {
        String resultaat;
        if (datumEinde == null) {
            resultaat = "heden";
        } else {
            resultaat = datumEinde.getTekst();
        }
        return resultaat;
    }

}
