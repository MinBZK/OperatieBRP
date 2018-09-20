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

    /**
     * De constante serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * De id van het bericht in de database.
     */
    private Long berichtId;

    /**
     * De partij.
     */
    private String partij;

    /**
     * De tekst.
     */
    private String bericht;

    /**
     * De tekst details.
     */
    private String berichtDetails;

    /**
     * De aantal meldingen.
     */
    private int aantalMeldingen;

    /**
     * De tijdstempel.
     */

    private Calendar verzondenOp;

    /**
     * De burger zaken module.
     */
    private String burgerZakenModule;

    /**
     * De soort bijhouding (Verhuizing of Geboorte in dit geval).
     */
    private OndersteundeBijhoudingsTypes soortBijhouding = OndersteundeBijhoudingsTypes.ONBEKEND;

    /**
     * De prevalidatie.
     */
    private boolean prevalidatie = false;

    /**
     * De burgerservicenummers.
     */
    private List<Integer> burgerservicenummers;


    /**
     * Gets the partij.
     *
     * @return the partij
     */
    public String getPartij() {
        return partij;
    }

    /**
     * Instellen van partij.
     *
     * @param partij de nieuwe partij
     */
    public void setPartij(final String partij) {
        this.partij = partij;
    }

    /**
     * Haalt een aantal meldingen op.
     *
     * @return aantal meldingen
     */
    public int getAantalMeldingen() {
        return aantalMeldingen;
    }

    /**
     * Instellen van aantal meldingen.
     *
     * @param aantalMeldingen de nieuwe aantal meldingen
     */
    public void setAantalMeldingen(final int aantalMeldingen) {
        this.aantalMeldingen = aantalMeldingen;
    }

    /**
     * Haalt een burger zaken module op.
     *
     * @return burger zaken module
     */
    public String getBurgerZakenModule() {
        return burgerZakenModule;
    }

    /**
     * Instellen van burger zaken module.
     *
     * @param burgerZakenModule de nieuwe burger zaken module
     */
    public void setBurgerZakenModule(final String burgerZakenModule) {
        this.burgerZakenModule = burgerZakenModule;
    }

    /**
     * Haalt een bericht op.
     *
     * @return bericht
     */
    public String getBericht() {
        return bericht;
    }

    /**
     * Instellen van bericht.
     *
     * @param bericht de nieuwe bericht
     */
    public void setBericht(final String bericht) {
        this.bericht = bericht;
    }

    /**
     * Haalt een bericht details op.
     *
     * @return bericht details
     */
    public String getBerichtDetails() {
        return berichtDetails;
    }

    /**
     * Instellen van bericht details.
     *
     * @param berichtDetails de nieuwe bericht details
     */
    public void setBerichtDetails(final String berichtDetails) {
        this.berichtDetails = berichtDetails;
    }

    /**
     * Haalt een soort bijhouding op.
     *
     * @return soort bijhouding
     */
    public OndersteundeBijhoudingsTypes getSoortBijhouding() {
        return soortBijhouding;
    }

    /**
     * Instellen van soort bijhouding.
     *
     * @param soortBijhouding de nieuwe soort bijhouding
     */
    public void setSoortBijhouding(final OndersteundeBijhoudingsTypes soortBijhouding) {
        this.soortBijhouding = soortBijhouding;
    }

    /**
     * Haalt een verzonden op op.
     *
     * @return verzonden op
     */
    public Calendar getVerzondenOp() {
        return verzondenOp;
    }

    /**
     * Instellen van verzonden op.
     *
     * @param verzondenOp de nieuwe verzonden op
     */
    public void setVerzondenOp(final Calendar verzondenOp) {
        this.verzondenOp = verzondenOp;
    }

    /**
     * Controleert of de waarde gelijk is aan prevalidatie.
     *
     * @return true, als waarde gelijk is aan prevalidatie
     */
    public boolean isPrevalidatie() {
        return prevalidatie;
    }

    /**
     * Instellen van prevalidatie.
     *
     * @param prevalidatie de nieuwe prevalidatie
     */
    public void setPrevalidatie(final boolean prevalidatie) {
        this.prevalidatie = prevalidatie;
    }

    /**
     * Haalt een lijst burgerservicenummers op.
     *
     * @return burgerservicenummers
     */
    public List<Integer> getBurgerservicenummers() {
        return burgerservicenummers;
    }

    /**
     * Zet de burgerservicenummers.
     *
     * @param burgerservicenummers the new burgerservicenummers
     */
    public void setBurgerservicenummers(final List<Integer> burgerservicenummers) {
        this.burgerservicenummers = burgerservicenummers;
    }

    /**
     * Haalt een bericht id op.
     *
     * @return bericht id
     */
    public Long getBerichtId() {
        return berichtId;
    }

    /**
     * Instellen van bericht id.
     *
     * @param berichtId de nieuwe bericht id
     */
    public void setBerichtId(final Long berichtId) {
        this.berichtId = berichtId;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String berichtIdString = "";
        if (berichtId != null) {
            berichtIdString = "berichtId=" + berichtId + ", ";
        }

        String partijString = "";
        if (partij != null) {
            partijString = "partij=" + partij + ", ";
        }

        String berichtString = "";
        if (bericht != null) {
            berichtString = "bericht=" + bericht + ", ";
        }

        String berichtDetailsString = "";
        if (berichtDetails != null) {
            berichtDetailsString = "berichtDetails=" + berichtDetails + ", ";
        }

        String aantalMeldingenString = "aantalMeldingen=" + aantalMeldingen + ", ";

        String verzondenOpString = "";
        if (verzondenOp != null) {
            verzondenOpString = "verzondenOp=" + verzondenOp + ", ";
        }

        String burgerZakenModuleString = "";
        if (burgerZakenModule != null) {
            burgerZakenModuleString = "burgerZakenModule=" + burgerZakenModule + ", ";
        }

        String soortBijhoudingString = "";
        if (soortBijhouding != null) {
            soortBijhoudingString = "soortBijhouding=" + soortBijhouding + ", ";
        }

        String prevalidatieString = "prevalidatie=" + prevalidatie + ", ";

        String burgerservicenummersString = "";
        if (burgerservicenummers != null) {
            burgerservicenummersString = "burgerservicenummers=" + burgerservicenummers;
        }

        return "Bericht ["
                + berichtIdString
                + partijString
                + berichtString
                + berichtDetailsString
                + aantalMeldingenString
                + verzondenOpString
                + burgerZakenModuleString
                + soortBijhoudingString
                + prevalidatieString
                + burgerservicenummersString
                + "]";
    }

}
