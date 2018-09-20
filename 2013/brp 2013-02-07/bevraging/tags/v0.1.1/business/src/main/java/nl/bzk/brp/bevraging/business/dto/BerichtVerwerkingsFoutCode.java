/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto;

/**
 * Enumeratie voor de verschillende 'codes' die een fout kan hebben.
 */
public enum BerichtVerwerkingsFoutCode {

    /** 100: Onbekende fout. */
    ONBEKENDE_FOUT(100, "Onbekende fout opgetreden"),
    /** 200: Algemene authenticatie en/of autorisatie fout. */
    ALGEMENE_SECURITY_FOUT(200, "Algemene authenticatie en/of autorisatie fout."),
    /** 201: Partij functioneel niet gerechtigd tot uitvoer bericht.  */
    FUNCTIONELE_AUTORISATIE_FOUT(201, "Partij functioneel niet gerechtigd tot uitvoer bericht."),
    /** 210: Partij onbekend. */
    PARTIJ_ONBEKEND(210, "Partij niet beschikbaar of onbekend."),
    /** 211: Partij en Abonnement combinatie onbekend of niet uniek. */
    PARTIJ_ABONNEMENT_COMBI_ONBEKEND_OF_NIET_UNIEK(211, "Partij en Abonnement combinatie onbekend of niet uniek.");

    private final int    code;
    private final String standaardBericht;

    /**
     * Constructor voor de fout code die de code en het standaard bericht initialiseerd.
     *
     * @param code de fout code.
     * @param standaardBericht het standaardbericht bij deze fout code.
     */
    private BerichtVerwerkingsFoutCode(final int code, final String standaardBericht) {
        this.code = code;
        this.standaardBericht = standaardBericht;
    }

    /**
     * Retourneert de fout code.
     * @return de fout code.
     */
    public int getCode() {
        return code;
    }

    /**
     * Retourneert het standaard bericht behorende bij deze fout code.
     * @return het standaard bericht behorende bij deze fout code.
     */
    public String getStandaardBericht() {
        return standaardBericht;
    }
}
