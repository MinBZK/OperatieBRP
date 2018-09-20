/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.bericht.brp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * GBA Archivering verzoek.
 */
public final class GbaArchiveringVerzoek {

    @JsonProperty
    private String soortBericht;
    @JsonProperty
    private String richting;
    @JsonProperty
    private Integer zendendePartijCode;
    @JsonProperty
    private String zendendeSysteem;
    @JsonProperty
    private Integer ontvangendePartijCode;
    @JsonProperty
    private String ontvangendeSysteem;
    @JsonProperty
    private String referentienummer;
    @JsonProperty
    private String crossReferentienummer;
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmssSSS", timezone = "UTC")
    private Date tijdstipVerzending;
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmssSSS", timezone = "UTC")
    private Date tijdstipOntvangst;
    @JsonProperty
    private String data;

    /**
     * Geef het soort bericht.
     *
     * @return het soort bericht
     */
    public String getSoortBericht() {
        return soortBericht;
    }

    /**
     * Zet het soort bericht.
     *
     * @param soortBericht
     *            het te zetten soort bericht
     */
    public void setSoortBericht(final String soortBericht) {
        this.soortBericht = soortBericht;
    }

    /**
     * Geef de richting.
     *
     * @return de richting
     */
    public String getRichting() {
        return richting;
    }

    /**
     * Zet de richting.
     *
     * @param richting
     *            de te zetten richting
     */
    public void setRichting(final String richting) {
        this.richting = richting;
    }

    /**
     * Geef de zendende partij code.
     *
     * @return de zendende partij code
     */
    public Integer getZendendePartijCode() {
        return zendendePartijCode;
    }

    /**
     * Zet de zendende partij code.
     *
     * @param zendendePartijCode
     *            de te zetten zendende partij code
     */
    public void setZendendePartijCode(final Integer zendendePartijCode) {
        this.zendendePartijCode = zendendePartijCode;
    }

    /**
     * Geef het zendende systeem.
     *
     * @return het zendende systeem
     */
    public String getZendendeSysteem() {
        return zendendeSysteem;
    }

    /**
     * Zet het zendende systeem.
     *
     * @param zendendeSysteem
     *            het te zetten zendende systeem
     */
    public void setZendendeSysteem(final String zendendeSysteem) {
        this.zendendeSysteem = zendendeSysteem;
    }

    /**
     * Geef de ontvangende partij code.
     *
     * @return De ontvangende partij code
     */
    public Integer getOntvangendePartijCode() {
        return ontvangendePartijCode;
    }

    /**
     * Zet de ontvangende partij code.
     *
     * @param ontvangendePartijCode
     *            de te zetten ontvangende partij code
     */
    public void setOntvangendePartijCode(final Integer ontvangendePartijCode) {
        this.ontvangendePartijCode = ontvangendePartijCode;
    }

    /**
     * Geef het ontvangende systeem.
     *
     * @return het ontvangende systeem
     */
    public String getOntvangendeSysteem() {
        return ontvangendeSysteem;
    }

    /**
     * Zet het ontvangende systeem.
     *
     * @param ontvangendeSysteem
     *            het te zetten ontvangende systeem
     */
    public void setOntvangendeSysteem(final String ontvangendeSysteem) {
        this.ontvangendeSysteem = ontvangendeSysteem;
    }

    /**
     * Geef het referentienummer.
     *
     * @return het referentienummer
     */
    public String getReferentienummer() {
        return referentienummer;
    }

    /**
     * Zet het referentienummer.
     *
     * @param referentienummer
     *            het te zetten referentienummer
     */
    public void setReferentienummer(final String referentienummer) {
        this.referentienummer = referentienummer;
    }

    /**
     * Geef het cross referentienummer.
     *
     * @return het cross referentienummer
     */
    public String getCrossReferentienummer() {
        return crossReferentienummer;
    }

    /**
     * Zet het cross referentienummer.
     *
     * @param crossReferentienummer
     *            het te zetten cross referentienummer
     */
    public void setCrossReferentienummer(final String crossReferentienummer) {
        this.crossReferentienummer = crossReferentienummer;
    }

    /**
     * Geef het tijdstip verzending.
     *
     * @return het tijdstip verzending
     */
    public Date getTijdstipVerzending() {
        return tijdstipVerzending == null ? null : new Date(tijdstipVerzending.getTime());
    }

    /**
     * Zet het tijdstip verzending.
     *
     * @param tijdstipVerzending
     *            het te zetten tijdstip verzending
     */
    public void setTijdstipVerzending(final Date tijdstipVerzending) {
        this.tijdstipVerzending = tijdstipVerzending == null ? null : new Date(tijdstipVerzending.getTime());
    }

    /**
     * Geef het tijdstip ontvangst.
     *
     * @return het tijdstip ontvangst
     */
    public Date getTijdstipOntvangst() {
        return tijdstipOntvangst == null ? null : new Date(tijdstipOntvangst.getTime());
    }

    /**
     * Zet het tijdstip ontvangst.
     *
     * @param tijdstipOntvangst
     *            het te zetten tijdstip ontvangst
     */
    public void setTijdstipOntvangst(final Date tijdstipOntvangst) {
        this.tijdstipOntvangst = tijdstipOntvangst == null ? null : new Date(tijdstipOntvangst.getTime());
    }

    /**
     * Geef de data.
     *
     * @return de data
     */
    public String getData() {
        return data;
    }

    /**
     * Zet de data.
     *
     * @param data
     *            De te zetten data
     */
    public void setData(final String data) {
        this.data = data;
    }

}
