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
    /** 201: Ongeldige authenticatie. */
    ONGELDIGE_AUTHENTICATIE("AUT0001", "Authenticatie op basis van certificaat mislukt voor dit bericht."),
    /** 202: Partij functioneel niet gerechtigd tot uitvoer bericht. */
    FUNCTIONELE_AUTORISATIE_FOUT("AUT0002", "Partij functioneel niet gerechtigd tot uitvoer bericht."),
    /** 210: Partij onbekend. */
    PARTIJ_ONBEKEND("AUT0003", "Partij niet beschikbaar of onbekend."),

    /** Code voor als er in de POC fouten zijn in de bedrijfsregels. */
    POC_BEDRIJFSREGEL_FOUT("POC_BR_001", "Er zijn bedrijfsregel fouten opgetreden."),

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
    BRPE0009_01_BEPALEN_RESULTAAT_DUURDE_TE_LANG("BRPE0009-01", "De zoekvraag heeft te lang geduurd en is afgebroken."),
    /**
     * BRVE0008-01: Er zijn geen persoonsgegevens geleverd, omdat het protocolleren van de gegevens mislukt is.
     */
    BRVE0008_01_PROTOCOLLERING_MISLUKT("BRVE0008-01",
            "Er zijn geen persoonsgegevens geleverd, omdat het protocolleren van de gegevens mislukt is."),
    /**
     * BRAU0046-01: Er is een fout opgetreden in het parseren van de populatie criteria. Waarschijnlijk is uw
     *     abonnement verkeerd geconfigureerd.
     */
    BRAU0046_01_POPULATIE_CRITERIA_PARSEER_FOUT("BRAU0046-01",
            "Er is een fout opgetreden in het parseren van de populatie criteria. Waarschijnlijk "
                + "is uw abonnement verkeerd geconfigureerd.");

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
