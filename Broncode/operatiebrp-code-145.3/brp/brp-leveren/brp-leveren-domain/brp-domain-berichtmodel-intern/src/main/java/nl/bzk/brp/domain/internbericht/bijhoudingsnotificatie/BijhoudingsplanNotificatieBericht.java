/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.internbericht.bijhoudingsnotificatie;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

/**
 * Bijhoudingsplan Notificatie bericht.
 */
public final class BijhoudingsplanNotificatieBericht {

    @JsonProperty
    private String ontvangendePartijCode;
    @JsonProperty
    private Short ontvangendePartijId;
    @JsonProperty
    private String zendendePartijCode;
    @JsonProperty
    private String ontvangendeSysteem;
    @JsonProperty
    private String zendendeSysteem;
    @JsonProperty
    private String referentieNummer;
    @JsonProperty
    private String crossReferentieNummer;
    @JsonProperty
    private Long tijdstipVerzending;
    @JsonProperty
    private Long administratieveHandelingId;
    @JsonProperty
    private String verwerkBijhoudingsplanBericht;

    /**
     * Default constructor.
     */
    public BijhoudingsplanNotificatieBericht() {
        //public constructor v json deserialisatie
    }

    /**
     * Zet de ontvangende partij code.
     *
     * @param ontvangendepartijCode ontvangende partij code
     * @return het bijhoudingsPlanNotificatieBericht
     */
    public BijhoudingsplanNotificatieBericht setOntvangendePartijCode(final String ontvangendepartijCode) {
        this.ontvangendePartijCode = ontvangendepartijCode;
        return this;
    }

    /**
     * Zet het id van de ontvangende partij.
     *
     * @param partijId ontvangende partij id
     * @return het bijhoudingsPlanNotificatieBericht
     */
    public BijhoudingsplanNotificatieBericht setOntvangendePartijId(final Short partijId) {
        this.ontvangendePartijId = partijId;
        return this;
    }

    /**
     * Zet de zendende partij code.
     *
     * @param zendendepartijCode zendende partij code
     * @return het bijhoudingsPlanNotificatieBericht
     */
    public BijhoudingsplanNotificatieBericht setZendendePartijCode(final String zendendepartijCode) {
        this.zendendePartijCode = zendendepartijCode;
        return this;
    }

    /**
     * Zet het ontvangende systeem.
     *
     * @param ontvangendSysteem ontvangend systeem
     * @return het bijhoudingsPlanNotificatieBericht
     */
    public BijhoudingsplanNotificatieBericht setOntvangendeSysteem(final String ontvangendSysteem) {
        this.ontvangendeSysteem = ontvangendSysteem;
        return this;
    }

    /**
     * Zet het zendende systeem.
     *
     * @param zendendSysteem zendend systeem
     * @return het bijhoudingsPlanNotificatieBericht
     */
    public BijhoudingsplanNotificatieBericht setZendendeSysteem(final String zendendSysteem) {
        this.zendendeSysteem = zendendSysteem;
        return this;
    }

    /**
     * Zet het referentienummer.
     *
     * @param referentieNr referentienummer
     * @return het bijhoudingsPlanNotificatieBericht
     */
    public BijhoudingsplanNotificatieBericht setReferentieNummer(final String referentieNr) {
        this.referentieNummer = referentieNr;
        return this;
    }

    /**
     * Zet het crossreferentienummer.
     *
     * @param crossReferentieNr crossreferentienummer
     * @return het bijhoudingsPlanNotificatieBericht
     */
    public BijhoudingsplanNotificatieBericht setCrossReferentieNummer(final String crossReferentieNr) {
        this.crossReferentieNummer = crossReferentieNr;
        return this;
    }

    /**
     * Zet het tijdstip van verzending.
     *
     * @param datumTijdVerzending tijdstip van verzending
     * @return het bijhoudingsPlanNotificatieBericht
     */
    public BijhoudingsplanNotificatieBericht setTijdstipVerzending(final Date datumTijdVerzending) {
        this.tijdstipVerzending = datumTijdVerzending.getTime();
        return this;
    }

    /**
     * Zet het administratieve handeling id.
     *
     * @param admHandelingId administratieve handeling id
     * @return het bijhoudingsPlanNotificatieBericht
     */
    public BijhoudingsplanNotificatieBericht setAdministratieveHandelingId(final Long admHandelingId) {
        this.administratieveHandelingId = admHandelingId;
        return this;
    }

    /**
     * Zet het bijhoudingsnotificatiebericht.
     *
     * @param bericht het bijhoudingsnotificatie bericht
     * @return het bijhoudingsPlanNotificatieBericht
     */
    public BijhoudingsplanNotificatieBericht setVerwerkBijhoudingsplanBericht(final String bericht) {
        this.verwerkBijhoudingsplanBericht = bericht;
        return this;
    }

    /**
     * @return geeft het verwerkBijhoudingsplanBericht terug.
     */
    public String getVerwerkBijhoudingsplanBericht() {
        return verwerkBijhoudingsplanBericht;
    }

    /**
     * @return geeft de ontvangende partij code terug.
     */
    public String getOntvangendePartijCode() {
        return ontvangendePartijCode;
    }

    /**
     * @return retourneert het id van de ontvangende partij.
     */
    public Short getOntvangendePartijId() {
        return ontvangendePartijId;
    }

    /**
     * @return retourneert zendende partij code
     */
    public String getZendendePartijCode() {
        return zendendePartijCode;
    }

    /**
     * @return retourneert het ontvangende systeem.
     */
    public String getOntvangendeSysteem() {
        return ontvangendeSysteem;
    }

    /**
     * @return retourneert het zendende systeem.
     */
    public String getZendendeSysteem() {
        return zendendeSysteem;
    }

    /**
     * @return retourneert het referentienummer.
     */
    public String getReferentieNummer() {
        return referentieNummer;
    }

    /**
     * @return retourneert het crossreferentienummer.
     */
    public String getCrossReferentieNummer() {
        return crossReferentieNummer;
    }

    /**
     * @return retourneert het id van de administratieve handeling.
     */
    public Long getAdministratieveHandelingId() {
        return administratieveHandelingId;
    }

    /**
     * @return retourneert het tijdstip verzending.
     */
    public Long getTijdstipVerzending() {
        return tijdstipVerzending;
    }

}
