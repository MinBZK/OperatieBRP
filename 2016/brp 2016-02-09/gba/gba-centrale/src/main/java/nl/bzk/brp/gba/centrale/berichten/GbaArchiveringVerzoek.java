/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.centrale.berichten;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Richting;

/**
 * GBA Archivering verzoek.
 */
public final class GbaArchiveringVerzoek {

    @JsonProperty
    private String soortBericht;
    @JsonProperty
    private Richting richting;
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
     * Geef soort bericht.
     *
     * @return the soort bericht
     */
    public String getSoortBericht() {
        return soortBericht;
    }

    /**
     * Zet soort bericht.
     *
     * @param soortBericht
     *            soort bericht
     */
    public void setSoortBericht(final String soortBericht) {
        this.soortBericht = soortBericht;
    }

    /**
     * Geef richting.
     *
     * @return the richting
     */
    public Richting getRichting() {
        return richting;
    }

    /**
     * Zet richting.
     *
     * @param richting
     *            richting
     */
    public void setRichting(final Richting richting) {
        this.richting = richting;
    }

    /**
     * Geef zendende partij code.
     *
     * @return the zendende partij code
     */
    public Integer getZendendePartijCode() {
        return zendendePartijCode;
    }

    /**
     * Zet zendende partij code.
     *
     * @param zendendePartijCode
     *            zendende partij code
     */
    public void setZendendePartijCode(final Integer zendendePartijCode) {
        this.zendendePartijCode = zendendePartijCode;
    }

    /**
     * Geef zendende systeem.
     *
     * @return the zendende systeem
     */
    public String getZendendeSysteem() {
        return zendendeSysteem;
    }

    /**
     * Zet zendende systeem.
     *
     * @param zendendeSysteem
     *            zendende systeem
     */
    public void setZendendeSysteem(final String zendendeSysteem) {
        this.zendendeSysteem = zendendeSysteem;
    }

    /**
     * Geef ontvangende partij code.
     *
     * @return the ontvangende partij code
     */
    public Integer getOntvangendePartijCode() {
        return ontvangendePartijCode;
    }

    /**
     * Zet ontvangende partij code.
     *
     * @param ontvangendePartijCode
     *            ontvangende partij code
     */
    public void setOntvangendePartijCode(final Integer ontvangendePartijCode) {
        this.ontvangendePartijCode = ontvangendePartijCode;
    }

    /**
     * Geef ontvangende systeem.
     *
     * @return the ontvangende systeem
     */
    public String getOntvangendeSysteem() {
        return ontvangendeSysteem;
    }

    /**
     * Zet ontvangende systeem.
     *
     * @param ontvangendeSysteem
     *            ontvangende systeem
     */
    public void setOntvangendeSysteem(final String ontvangendeSysteem) {
        this.ontvangendeSysteem = ontvangendeSysteem;
    }

    /**
     * Geef referentienummer.
     *
     * @return the referentienummer
     */
    public String getReferentienummer() {
        return referentienummer;
    }

    /**
     * Zet referentienummer.
     *
     * @param referentienummer
     *            referentienummer
     */
    public void setReferentienummer(final String referentienummer) {
        this.referentienummer = referentienummer;
    }

    /**
     * Geef cross referentienummer.
     *
     * @return the cross referentienummer
     */
    public String getCrossReferentienummer() {
        return crossReferentienummer;
    }

    /**
     * Zet cross referentienummer.
     *
     * @param crossReferentienummer
     *            cross referentienummer
     */
    public void setCrossReferentienummer(final String crossReferentienummer) {
        this.crossReferentienummer = crossReferentienummer;
    }

    /**
     * Geef tijdstip verzending.
     *
     * @return the tijdstip verzending
     */
    public Date getTijdstipVerzending() {
        return tijdstipVerzending == null ? null : new Date(tijdstipVerzending.getTime());
    }

    /**
     * Zet tijdstip verzending.
     *
     * @param tijdstipVerzending
     *            tijdstip verzending
     */
    public void setTijdstipVerzending(final Date tijdstipVerzending) {
        this.tijdstipVerzending = tijdstipVerzending == null ? null : new Date(tijdstipVerzending.getTime());
    }

    /**
     * Geef tijdstip ontvangst.
     *
     * @return the tijdstip ontvangst
     */
    public Date getTijdstipOntvangst() {
        return tijdstipOntvangst == null ? null : new Date(tijdstipOntvangst.getTime());
    }

    /**
     * Zet tijdstip ontvangst.
     *
     * @param tijdstipOntvangst
     *            tijdstip ontvangst
     */
    public void setTijdstipOntvangst(final Date tijdstipOntvangst) {
        this.tijdstipOntvangst = tijdstipOntvangst == null ? null : new Date(tijdstipOntvangst.getTime());
    }

    /**
     * Geef data.
     *
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * Zet data.
     *
     * @param data
     *            data
     */
    public void setData(final String data) {
        this.data = data;
    }

}
