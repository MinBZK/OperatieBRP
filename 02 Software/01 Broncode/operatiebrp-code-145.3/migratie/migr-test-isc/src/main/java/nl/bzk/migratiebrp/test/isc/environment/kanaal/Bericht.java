/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal;

import nl.bzk.migratiebrp.test.isc.bericht.TestBericht;

/**
 * Bericht.
 */
public final class Bericht {

    private final TestBericht testBericht;

    private String berichtReferentie;
    private String correlatieReferentie;

    private String inhoud;

    private String verzendendePartij;
    private String ontvangendePartij;

    private Integer moederActiviteitType;
    private Integer activiteitType;
    private Integer activiteitSubtype;
    private String activiteitDatum;
    private Integer activiteitToestand;

    private String msSequenceNumber;
    private Boolean requestNonReceiptNotification;

    private String oinOndertekenaar;
    private String oinTransporteur;

    private boolean magFalen;

    /**
     * Constructor (testbericht = null).
     */
    public Bericht() {
        testBericht = null;
    }

    /**
     * Constructor.
     * @param testBericht test bericht
     */
    public Bericht(final TestBericht testBericht) {
        this.testBericht = testBericht;
    }

    /**
     * Geef de waarde van test bericht.
     * @return test bericht
     */
    public TestBericht getTestBericht() {
        return testBericht;
    }

    /**
     * Geef de waarde van bericht referentie.
     * @return bericht referentie
     */
    public String getBerichtReferentie() {
        return berichtReferentie;
    }

    /**
     * Zet de waarde van bericht referentie.
     * @param berichtReferentie bericht referentie
     */
    public void setBerichtReferentie(final String berichtReferentie) {
        this.berichtReferentie = berichtReferentie;
    }

    /**
     * Geef de waarde van correlatie referentie.
     * @return correlatie referentie
     */
    public String getCorrelatieReferentie() {
        return correlatieReferentie;
    }

    /**
     * Zet de waarde van correlatie referentie.
     * @param correlatieReferentie correlatie referentie
     */
    public void setCorrelatieReferentie(final String correlatieReferentie) {
        this.correlatieReferentie = correlatieReferentie;
    }

    /**
     * Geef de waarde van inhoud.
     * @return inhoud
     */
    public String getInhoud() {
        return inhoud;
    }

    /**
     * Zet de waarde van inhoud.
     * @param inhoud inhoud
     */
    public void setInhoud(final String inhoud) {
        this.inhoud = inhoud;
    }

    /**
     * Geef de waarde van verzendende partij.
     * @return verzendende partij
     */
    public String getVerzendendePartij() {
        return verzendendePartij;
    }

    /**
     * Zet de waarde van verzendende partij.
     * @param verzendendePartij verzendende partij
     */
    public void setVerzendendePartij(final String verzendendePartij) {
        this.verzendendePartij = verzendendePartij;
    }

    /**
     * Geef de waarde van ontvangende partij.
     * @return ontvangende partij
     */
    public String getOntvangendePartij() {
        return ontvangendePartij;
    }

    /**
     * Zet de waarde van ontvangende partij.
     * @param ontvangendePartij ontvangende partij
     */
    public void setOntvangendePartij(final String ontvangendePartij) {
        this.ontvangendePartij = ontvangendePartij;
    }

    public Integer getMoederActiviteitType() {
        return moederActiviteitType;
    }

    public void setMoederActiviteitType(Integer moederActiviteitType) {
        this.moederActiviteitType = moederActiviteitType;
    }

    public Integer getActiviteitType() {
        return activiteitType;
    }

    public void setActiviteitType(Integer activiteitType) {
        this.activiteitType = activiteitType;
    }

    public Integer getActiviteitSubtype() {
        return activiteitSubtype;
    }

    public void setActiviteitSubtype(Integer activiteitSubtype) {
        this.activiteitSubtype = activiteitSubtype;
    }

    /**
     * Geef de waarde van activiteit datum.
     * @return datum
     */
    public String getActiviteitDatum() {
        return activiteitDatum;
    }

    /**
     * Zet de waarde van activiteit datum.
     * @param activiteitDatum datum
     */
    public void setActiviteitDatum(final String activiteitDatum) {
        this.activiteitDatum = activiteitDatum;
    }

    /**
     * Geef activiteit toestand.
     * @return toestand
     */
    public Integer getActiviteitToestand() {
        return activiteitToestand;
    }

    /**
     * Zet activiteit toestand.
     * @param activiteitToestand toestand
     */
    public void setActiviteitToestand(final Integer activiteitToestand) {
        this.activiteitToestand = activiteitToestand;
    }

    /**
     * Geef de waarde van ms sequence number.
     * @return ms sequence number
     */
    public String getMsSequenceNumber() {
        return msSequenceNumber;
    }

    /**
     * Zet de waarde van ms sequence number.
     * @param msSequenceNumber ms sequence number
     */
    public void setMsSequenceNumber(final String msSequenceNumber) {
        this.msSequenceNumber = msSequenceNumber;
    }

    /**
     * Geef de waarde van request non receipt notification.
     * @return request non receipt notification
     */
    public Boolean getRequestNonReceiptNotification() {
        return requestNonReceiptNotification;
    }

    /**
     * Zet de waarde van request non receipt notification.
     * @param requestNonReceiptNotification request non receipt notification
     */
    public void setRequestNonReceiptNotification(final Boolean requestNonReceiptNotification) {
        this.requestNonReceiptNotification = requestNonReceiptNotification;
    }

    /**
     * Geef waarde van ondertekenaar.
     * @return oin
     */
    public String getOinOndertekenaar() {
        return oinOndertekenaar;
    }

    /**
     * Zet waarde van ondertekenaar.
     * @param oinOndertekenaar oin
     */
    public void setOinOndertekenaar(String oinOndertekenaar) {
        this.oinOndertekenaar = oinOndertekenaar;
    }

    /**
     * Geef waarde van transporteur.
     * @return oin
     */
    public String getOinTransporteur() {
        return oinTransporteur;
    }

    /**
     * Zet waarde van transporteur.
     * @param oinTransporteur oin
     */
    public void setOinTransporteur(String oinTransporteur) {
        this.oinTransporteur = oinTransporteur;
    }

    /**
     * mag falen. Geeft aan of de stap mag falen.
     * @return indicatie of stap mag falen
     */
    public boolean isMagFalen() {
        return magFalen;
    }

    /**
     * Zet indicatie of stap mag falen.
     * @param magFalen indicatie of stap mag falen
     */
    public void setMagFalen(final boolean magFalen) {
        this.magFalen = magFalen;
    }

    @Override
    public String toString() {
        return "Bericht [testBericht="
                + testBericht
                + ", berichtReferentie="
                + berichtReferentie
                + ", correlatieReferentie="
                + correlatieReferentie
                + ", inhoud="
                + inhoud
                + ", verzendendePartij="
                + verzendendePartij
                + ", ontvangendePartij="
                + ontvangendePartij
                + ", msSequenceNumber="
                + msSequenceNumber
                + ", requestNonReceiptNotification="
                + requestNonReceiptNotification
                + "]";
    }

}
