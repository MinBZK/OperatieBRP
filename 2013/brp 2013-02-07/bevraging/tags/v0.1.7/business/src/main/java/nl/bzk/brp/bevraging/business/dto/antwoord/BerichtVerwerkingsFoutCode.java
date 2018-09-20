/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto.antwoord;

/**
 * Enumeratie voor de verschillende 'codes' die een fout kan hebben.
 */
public enum BerichtVerwerkingsFoutCode {

    /** 100: Onbekende fout. */
    ONBEKENDE_FOUT("ALG0001", "Onbekende fout opgetreden"),
    /** 200: Algemene authenticatie en/of autorisatie fout. */
    ALGEMENE_SECURITY_FOUT("AUT0001", "Algemene authenticatie en/of autorisatie fout."),
    /** 201: Partij functioneel niet gerechtigd tot uitvoer bericht. */
    FUNCTIONELE_AUTORISATIE_FOUT("AUT0002", "Partij functioneel niet gerechtigd tot uitvoer bericht."),
    /** 210: Partij onbekend. */
    PARTIJ_ONBEKEND("AUT0003", "Partij niet beschikbaar of onbekend."),
    /** 211: Partij en Abonnement combinatie onbekend of niet uniek. */
    PARTIJ_ABONNEMENT_COMBI_ONBEKEND_OF_NIET_UNIEK("AUT0004",
            "Partij en Abonnement combinatie onbekend of niet uniek."),

    /** UC5004.1-01: Geen enkele persoon voldoet. */
    UC500401_01_GEEN_PERSOON_GEVONDEN("UC400-4.1-01", "Geen enkele persoon voldoet aan de selectie."),
    /** UC5004.1-02: Geen unieke selectie opgegeven. */
    UC500401_02_GEEN_UNIEKE_SELECTIE_OPGEGEVEN("UC400-4.1-02", "Geen unieke selectie opgegeven."),
    /**
     * BRPE0001-01: Het abonnement is niet opgegeven.
     */
    BRPE0001_01_ABONNEMENT_NIET_OPGEGEVEN("BRPE0001-01", "Het abonnement is niet opgegeven."),
    /**
     * BRPE0001-02: Het opgegeven abonnement behoort niet bij de afnemen.
     */
    BRPE0001_02_ABONNEMENT_BEHOORT_NIET_BIJ_AFNEMER("BRPE0001-02",
            "Het opgegeven abonnement behoort niet bij de afnemer."),
    /**
     * BRPE0001-03: Het opgegeven abonnement is ongeldig.
     */
    BRPE0001_03_ABONNEMENT_ONGELDIG("BRPE0001-03", "Het opgegeven abonnement is ongeldig."),
    /**
     * BRPE0001-04: Het opgegeven abonnement bestaat niet.
     */
    BRPE0001_04_ABONNEMENT_BESTAAT_NIET("BRPE0001-04", "Het opgegeven abonnement bestaat niet."),
    /**
     * BRPE0009: Resultaat van de selectie kon niet binnen de opgegeven tijd worden bepaald.
     */
    BRPE0009_BEPALEN_RESULTAAT_DUURDE_TE_LANG("BRPE0009",
            "Het resultaat kon niet binnen de opgegeven tijd worden bepaald."),
    /**
     * BRVE0008-01: Er zijn geen persoonsgegevens geleverd, omdat het protocolleren van de gegevens mislukt is.
     */
    BRVE0008_01_PROTOCOLLERING_MISLUKT("BRVE0008-01",
            "Er zijn geen persoonsgegevens geleverd, omdat het protocolleren van de gegevens mislukt is.");

    private final String code;
    private final String standaardBericht;

    /**
     * Constructor voor de fout code die de code en het standaard bericht initialiseerd.
     *
     * @param code de fout code.
     * @param standaardBericht het standaardbericht bij deze fout code.
     */
    private BerichtVerwerkingsFoutCode(final String code, final String standaardBericht) {
        this.code = code;
        this.standaardBericht = standaardBericht;
    }

    /**
     * Retourneert de fout code.
     *
     * @return de fout code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Retourneert het standaard bericht behorende bij deze fout code.
     *
     * @return het standaard bericht behorende bij deze fout code.
     */
    public String getStandaardBericht() {
        return standaardBericht;
    }

}
