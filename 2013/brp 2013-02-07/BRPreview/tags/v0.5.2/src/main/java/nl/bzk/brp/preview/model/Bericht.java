/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;


/**
 * POJO voor het bericht van de BRP, zowel voor opslaan vanuit de BRP als voor tonen op het dashboard.
 */
public class Bericht implements Serializable {

    /** De constante serialVersionUID. */
    private static final long            serialVersionUID = 1L;

    /** De id van het bericht in de database. */
    private Long                         berichtId;

    /** De partij. */
    private String                       partij;

    /** De tekst. */
    private String                       bericht;

    /** De tekst details. */
    private String                       berichtDetails;

    /** De aantal meldingen. */
    private int                          aantalMeldingen;

    /** De tijdstempel. */

    private Calendar                     verzondenOp;

    /** De burger zaken module. */
    private String                       burgerZakenModule;

    /** De soort bijhouding (Verhuizing of Geboorte in dit geval). */
    private OndersteundeBijhoudingsTypes soortBijhouding  = OndersteundeBijhoudingsTypes.ONBEKEND;

    /** De prevalidatie. */
    private boolean                      prevalidatie     = false;

    /** De burgerservicenummers. */
    private List<Integer>                burgerservicenummers;


    /**
     * Gets the partij.
     *
     * @return the partij
     */
    public String getPartij() {
        return partij;
    }

    public void setPartij(final String partij) {
        this.partij = partij;
    }

    public int getAantalMeldingen() {
        return aantalMeldingen;
    }

    public void setAantalMeldingen(final int aantalMeldingen) {
        this.aantalMeldingen = aantalMeldingen;
    }

    public String getBurgerZakenModule() {
        return burgerZakenModule;
    }

    public void setBurgerZakenModule(final String burgerZakenModule) {
        this.burgerZakenModule = burgerZakenModule;
    }

    public String getBericht() {
        return bericht;
    }

    public void setBericht(final String bericht) {
        this.bericht = bericht;
    }

    public String getBerichtDetails() {
        return berichtDetails;
    }

    public void setBerichtDetails(final String berichtDetails) {
        this.berichtDetails = berichtDetails;
    }

    public OndersteundeBijhoudingsTypes getSoortBijhouding() {
        return soortBijhouding;
    }

    public void setSoortBijhouding(final OndersteundeBijhoudingsTypes soortBijhouding) {
        this.soortBijhouding = soortBijhouding;
    }

    public Calendar getVerzondenOp() {
        return verzondenOp;
    }

    public void setVerzondenOp(final Calendar verzondenOp) {
        this.verzondenOp = verzondenOp;
    }

    public boolean isPrevalidatie() {
        return prevalidatie;
    }

    public void setPrevalidatie(final boolean prevalidatie) {
        this.prevalidatie = prevalidatie;
    }

    public List<Integer> getBurgerservicenummers() {
        return burgerservicenummers;
    }

    /**
     * Zet de burgerservicenummers.
     * TODO: Waarom hebben we dit veld?
     *
     * @param burgerservicenummers the new burgerservicenummers
     */
    public void setBurgerservicenummers(final List<Integer> burgerservicenummers) {
        this.burgerservicenummers = burgerservicenummers;
    }

    public Long getBerichtId() {
        return berichtId;
    }

    public void setBerichtId(final Long berichtId) {
        this.berichtId = berichtId;
    }

}
