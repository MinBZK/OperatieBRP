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

    private String msSequenceNumber;

    /**
     * Constructor (testbericht = null).
     */
    public Bericht() {
        testBericht = null;
    }

    /**
     * Constructor.
     *
     * @param testBericht
     *            test bericht
     */
    public Bericht(final TestBericht testBericht) {
        this.testBericht = testBericht;
    }

    /**
     * Geef de waarde van test bericht.
     *
     * @return test bericht
     */
    public TestBericht getTestBericht() {
        return testBericht;
    }

    /**
     * Geef de waarde van bericht referentie.
     *
     * @return bericht referentie
     */
    public String getBerichtReferentie() {
        return berichtReferentie;
    }

    /**
     * Zet de waarde van bericht referentie.
     *
     * @param berichtReferentie
     *            bericht referentie
     */
    public void setBerichtReferentie(final String berichtReferentie) {
        this.berichtReferentie = berichtReferentie;
    }

    /**
     * Geef de waarde van correlatie referentie.
     *
     * @return correlatie referentie
     */
    public String getCorrelatieReferentie() {
        return correlatieReferentie;
    }

    /**
     * Zet de waarde van correlatie referentie.
     *
     * @param correlatieReferentie
     *            correlatie referentie
     */
    public void setCorrelatieReferentie(final String correlatieReferentie) {
        this.correlatieReferentie = correlatieReferentie;
    }

    /**
     * Geef de waarde van inhoud.
     *
     * @return inhoud
     */
    public String getInhoud() {
        return inhoud;
    }

    /**
     * Zet de waarde van inhoud.
     *
     * @param inhoud
     *            inhoud
     */
    public void setInhoud(final String inhoud) {
        this.inhoud = inhoud;
    }

    /**
     * Geef de waarde van verzendende partij.
     *
     * @return verzendende partij
     */
    public String getVerzendendePartij() {
        return verzendendePartij;
    }

    /**
     * Zet de waarde van verzendende partij.
     *
     * @param verzendendePartij
     *            verzendende partij
     */
    public void setVerzendendePartij(final String verzendendePartij) {
        this.verzendendePartij = verzendendePartij;
    }

    /**
     * Geef de waarde van ontvangende partij.
     *
     * @return ontvangende partij
     */
    public String getOntvangendePartij() {
        return ontvangendePartij;
    }

    /**
     * Zet de waarde van ontvangende partij.
     *
     * @param ontvangendePartij
     *            ontvangende partij
     */
    public void setOntvangendePartij(final String ontvangendePartij) {
        this.ontvangendePartij = ontvangendePartij;
    }

    /**
     * Geef de waarde van ms sequence number.
     *
     * @return ms sequence number
     */
    public String getMsSequenceNumber() {
        return msSequenceNumber;
    }

    /**
     * Zet de waarde van ms sequence number.
     *
     * @param msSequenceNumber
     *            ms sequence number
     */
    public void setMsSequenceNumber(final String msSequenceNumber) {
        this.msSequenceNumber = msSequenceNumber;
    }

    @Override
    public String toString() {
        return "Bericht [berichtReferentie="
               + berichtReferentie
               + ", correlatieReferentie="
               + correlatieReferentie
               + ", verzendendePartij="
               + verzendendePartij
               + ", ontvangendePartij="
               + ontvangendePartij
               + ", inhoud="
               + inhoud
               + ", msSequenceNumber="
               + msSequenceNumber
               + "]";
    }

}
