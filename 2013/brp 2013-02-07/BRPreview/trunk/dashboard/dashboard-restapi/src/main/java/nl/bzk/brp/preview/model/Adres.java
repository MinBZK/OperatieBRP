/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.util.StringUtils;


/**
 * De Class Adres.
 */
public class Adres {

    /** De gemeente. */
    private Gemeente gemeente;

    /** De straat. */
    private String straat;

    /** De huisnummer. */
    private String huisnummer;

    /** De huisnummertoevoeging. */
    private String huisnummertoevoeging;

    /** De plaats. */
    private Plaats plaats;

    /** De datum aanvang. */
    private final Datum datumAanvang = new Datum();

    /** De datum einde. */
    private Datum datumEinde;

    /**
     * Instantieert een nieuwe adres.
     */
    public Adres() {
    }

    /**
     * Instantieert een nieuwe adres.
     *
     * @param gemeente de gemeente
     */
    public Adres(final Gemeente gemeente) {
        this.gemeente = gemeente;
    }

    /**
     * Haalt een gemeente op.
     *
     * @return gemeente
     */
    public Gemeente getGemeente() {
        return gemeente;
    }

    /**
     * Instellen van gemeente.
     *
     * @param gemeente de nieuwe gemeente
     */
    public void setGemeente(final Gemeente gemeente) {
        this.gemeente = gemeente;
    }

    /**
     * Haalt een straat op.
     *
     * @return straat
     */
    public String getStraat() {
        return straat;
    }

    /**
     * Instellen van straat.
     *
     * @param straat de nieuwe straat
     */
    public void setStraat(final String straat) {
        this.straat = straat;
    }

    /**
     * Haalt een huisnummer op.
     *
     * @return huisnummer
     */
    public String getHuisnummer() {
        String huisnummerString = "nr. onbekend";
        if (huisnummer != null) {
            huisnummerString = huisnummer;
        }
        return huisnummerString;
    }

    /**
     * Instellen van huisnummer.
     *
     * @param huisnummer de nieuwe huisnummer
     */
    public void setHuisnummer(final String huisnummer) {
        this.huisnummer = huisnummer;
    }

    /**
     * Haalt een huisnummertoevoeging op.
     *
     * @return huisnummertoevoeging
     */
    public String getHuisnummertoevoeging() {
        return huisnummertoevoeging;
    }

    /**
     * Instellen van huisnummertoevoeging.
     *
     * @param huisnummertoevoeging de nieuwe huisnummertoevoeging
     */
    public void setHuisnummertoevoeging(final String huisnummertoevoeging) {
        this.huisnummertoevoeging = huisnummertoevoeging;
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
     * Haalt een datum aanvang op.
     *
     * @return datum aanvang
     */
    public int getDatumAanvang() {
        return datumAanvang.getDecimalen();
    }

    /**
     * Instellen van datum aanvang.
     *
     * @param datumAanvang de nieuwe datum aanvang
     */
    public void setDatumAanvang(final Integer datumAanvang) {
        this.datumAanvang.setDecimalen(datumAanvang);
    }

    /**
     * Haalt een datum einde op.
     *
     * @return datum einde
     */
    public Integer getDatumEinde() {
        Integer datumEindeInteger = null;
        if (datumEinde != null) {
            datumEindeInteger = datumEinde.getDecimalen();
        }
        return datumEindeInteger;
    }

    /**
     * Instellen van datum einde.
     *
     * @param datumEinde de nieuwe datum einde
     */
    public void setDatumEinde(final Integer datumEinde) {
        if (datumEinde == null) {
            this.datumEinde = null;
        } else {
            this.datumEinde = new Datum(datumEinde);
        }
    }

    /**
     * Haalt een huisnummer volledig op.
     *
     * @return huisnummer volledig
     */
    @JsonIgnore
    public String getHuisnummerVolledig() {
        if (StringUtils.hasText(huisnummertoevoeging)) {
            return getHuisnummer() + " " + huisnummertoevoeging;
        } else {
            return getHuisnummer();
        }
    }

    /**
     * Haalt een tekst op.
     *
     * @return tekst
     */
    @JsonIgnore
    public String getTekst() {
        String resultaat = straat + " " + getHuisnummerVolledig();
        if (plaats != null && plaats.getNaam() != null && plaats.getNaam().length() != 0) {
            resultaat += String.format(" (%s)", plaats.getNaam());
        }
        return resultaat;
    }

    /**
     * Haalt een tekst met periode op.
     *
     * @return tekst met periode
     */
    @JsonIgnore
    public String getTekstMetPeriode() {
        String format = " Periode: %s tot %s. Adres: %s.";
        return String.format(format, getDatumAanvangTekst(), getDatumEindeTekst(), getTekst());
    }

    /**
     * Haalt een datum aanvang tekst op.
     *
     * @return datum aanvang tekst
     */
    @JsonIgnore
    public String getDatumAanvangTekst() {
        return datumAanvang.getTekst();
    }

    /**
     * Haalt een datum einde tekst op.
     *
     * @return datum einde tekst
     */
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
