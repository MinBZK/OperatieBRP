/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.logging;

/**
 * Functionele meldingen.
 */
public enum FunctioneleMelding {

    /**
     * Dummy.
     */
    DUMMY("", ""),

    //
    // Logmeldingen algemeen.
    //

    /**
     * Algemene fout.
     */
    ALGEMEEN_FOUT("ALG0001", "Algemene fout."),

    /**
     * Algemene melding: blobificeren.
     */
    ALGEMEEN_BLOBIFICEER_PERSOON("ALG0002", "Blobificeer persoon."),

    /**
     * Algemene fout: onbekende referentie.
     */
    ALGEMEEN_ONBEKENDE_REFERENTIE("ALG0003", "Onbekende referentie gebruikt"),

    /**
     * Algemene melding: Lock op persoon.
     */
    ALGEMEEN_LOCK_PERSOON("ALG0004", "Plaats lock op persoon."),

    /**
     * Algemene melding: Partij niet geauthenticeerd.
     */
    ALGEMEEN_PARTIJ_NIET_GEAUTHENTICEERD("ALG0005", "Authenticatie Fout: Partij niet geauthenticeerd in bericht"),

    /**
     * Algemene melding: Misconfiguratie.
     */
    ALGEMEEN_PARTIJ_MISCONFIGURATIE("ALG0006",
        "Misconfiguratie: Partij beschikt over niet uniek authenticatiemiddel voor certificaat"),

    /**
     * Algemene melding: Geen certificaat.
     */
    ALGEMEEN_PARTIJ_GEEN_CERTIFICAAT("ALG0007",
        "Authenticatie fout: geen partij en/of geen certificaat aanwezig in bericht"),

    /**
     * Algemene melding: ongeldige autorisatie.
     */
    ALGEMEEN_PARTIJ_ONGELDIGE_AUTORISATIE("ALG0008", "Autorisatie error: Partij code is geen numerieke waarde."),

    /**
     * Algemene melding: Lock op persoon verwijderen.
     */
    ALGEMEEN_UNLOCK_PERSOON("ALG0009", "Verwijder locks op persoon."),

    //
    // Logmeldingen beheer.
    //

    /**
     * Beheer: Verzoek ontvangen.
     */
    BEHEER_VERZOEK_ONTVANGEN("BEH001", "Verzoek ontvangen."),

    /**
     * Beheer: Verzoek verwerken.
     */
    BEHEER_VERZOEK_VERWERKEN("BEH002", "Verzoek wordt verwerkt."),

    /**
     * Beheer: Verzoek verwerkt.
     */
    BEHEER_VERZOEK_VERWERKT("BEH003", "Verzoek verwerkt."),

    /**
     * Beheer: Onverwachte fout.
     */
    BEHEER_ONVERWACHTE_FOUT("BEH004", "Onverwachte fout opgetreden bij verwerken verzoek."),

    //
    // Logmeldingen bevraging.
    //

    /**
     * Bevraging: Fout bij het uitvoeren filteren attributen.
     */
    BEVRAGING_FILTEREN_ATTRIBUTEN("BEVR0001", "Er is een fout opgetreden tijdens de uitvoer van het attributenfilter."),

    /**
     * Bevraging: Filteren van verantwoordingsinformatie.
     */
    BEVRAGING_FILTEREN_VERANTWOORDING_PERSOON("BEVR0002", "Filteren van verantwoordingsinformatie voor persoon"),

    /**
     * Bevraging: Fout bij het uitvoeren filteren voorkomen.
     */
    BEVRAGING_FILTEREN_VOORKOMEN("BEVR0003", "Er is een fout opgetreden tijdens de uitvoer van het voorkomen filter. "),

    //
    // Logmeldingen bijhouding.
    //

    /**
     * Bijhouding: Aantal bijgehouden personen.
     */
    BIJHOUDING_AANTAL_BIJGEHOUDEN_PERSONEN("BIJH0001", "Aantal bijgehouden personen."),

    /**
     * Bijhouding: Overrulen regelcodes.
     */
    BIJHOUDING_OVERRULEN_REGELCODE("BIJH0002", "Voor het bericht zijn de regelcodes overrruled."),

    //
    // Logmeldingen levering.
    //

    /**
     * Levering: Het attenderingscriterium dient gevuld te zijn voor de leveringsautorisatie.
     */
    LEVERING_AUTORISATIE_ATTENDERINGSCRITERIUM_LEEG("LEV0001",
        "Het attenderingscriterium dient gevuld te zijn voor de leveringsautorisatie."),

    /**
     * Levering: Er is een fout opgetreden tijdens het bepalen van de leveringsautorisatie populatie map.
     */
    LEVERING_AUTORISATIE_FOUT_BEPALEN_POPULATIE("LEV0002",
        "Er is een fout opgetreden tijdens het bepalen van de leveringsautorisatie populatie map."),

    /**
     * Levering: Geen formele historie expressies gevonden voor element.
     */
    LEVERING_AUTORISATIE_GEEN_FORMELE_HISTORIE_EXPRESSIES("LEV0003",
        "Geen formele historie expressies gevonden voor element."),

    /**
     * Levering: leveringsautorisatie heeft geen (valide) expressie.
     */
    LEVERING_AUTORISATIE_GEEN_VALIDE_EXPRESSIES("LEV0004", "De leveringsautorisatie heeft geen (valide) expressie."),

    /**
     * Levering: Geen verantwoording-expressies gevonden voor element.
     */
    LEVERING_AUTORISATIE_GEEN_VERANTWOORDING_EXPRESSIES("LEV0005",
        "Geen verantwoording-expressies gevonden voor element."),

    /**
     * Levering: Het parsen van de expressie is mislukt.
     */
    LEVERING_AUTORISATIE_PARSEN_EXPRESSIE_MISLUKT("LEV0006", "Het parsen van de expressie is mislukt."),

    /**
     * Levering: Datum einde volgen is verstreken voor afnemerindicatie.
     */
    LEVERING_AFNEMERINDICATIE_DATUM_EINDE_VERLOPEN("LEV0007", "Datum einde volgen is verstreken voor afnemerindicatie."),

    /**
     * Levering: Afnemerindicatie geplaatst.
     */
    LEVERING_AFNEMERINDICATIE_GEPLAATST("LEV0008", "Afnemerindicatie geplaatst."),

    /**
     * Levering: Afnemerindicatie geplaatst met uitvoer van regels.
     */
    LEVERING_AFNEMERINDICATIE_GEPLAATST_MET_REGELS("LEV0009", "Afnemerindicatie geplaatst met uitvoer van regels."),

    /**
     * Levering: Er is een regel geconfigureerd in de afnemerindicatie-service met een niet-ondersteunde regelcontext.
     */
    LEVERING_AFNEMERINDICATIE_ONGELDIGE_REGELCONTEXT("LEV0010",
        "Er is een regel geconfigureerd in de afnemerindicatie-service met een niet-ondersteunde regelcontext."),

    /**
     * Levering: Er bevindt zich een afnemerindicatie op persoon, terwijl de persoon buiten de populatie valt.
     */
    LEVERING_AFNEMERINDICATIE_PERSOON_BUITEN_POPULATIE("LEV0011",
        "Er bevindt zich een afnemerindicatie op persoon, terwijl de persoon buiten de populatie valt."),

    /**
     * Levering: Afnemerindicatie verwijderd met uitvoer van regels.
     */
    LEVERING_AFNEMERINDICATIE_VERWIJDERD("LEV0012", "Afnemerindicatie verwijderd."),

    /**
     * Levering: Afnemerindicatie verwijderd met uitvoer van regels.
     */
    LEVERING_AFNEMERINDICATIE_VERWIJDERD_MET_REGELS("LEV0013", "Afnemerindicatie verwijderd met uitvoer van regels."),

    /**
     * Levering: Bedrijfsregel BRLV0001.
     */
    LEVERING_BEDRIJFSREGEL_BRLV0001("BRLV0001",
        "Een verwijzing in een bericht naar een voorkomen van Persoon/Afnemerindicatie dient correct en geldig te zijn."),

    /**
     * Levering: Bedrijfsregel BRLV0003.
     */
    LEVERING_BEDRIJFSREGEL_BRLV0003("BRLV0003",
        "Binnen Persoon/Afnemerindicatie is de combinatie Leveringsautorisatie/Partij/Persoon uniek."),

    /**
     * Levering: Bedrijfsregel BRLV0006.
     */
    LEVERING_BEDRIJFSREGEL_BRLV0006("BRLV0006",
        "Leveren van een persoonslijst is alleen mogelijk bij ingeschreven personen."),

    /**
     * Levering: Bedrijfsregel BRLV0007.
     */
    LEVERING_BEDRIJFSREGEL_BRLV0007("BRLV0007", "De opgegeven leveringsautorisatie bestaat niet."),

    /**
     * Levering: Bedrijfsregel BRLV0008.
     */
    LEVERING_BEDRIJFSREGEL_BRLV0008("BRLV0008", "Er is geen persoon met het opgegeven BSN."),

    /**
     * Levering: Bedrijfsregel BRLV0009.
     */
    LEVERING_BEDRIJFSREGEL_BRLV0009("BRLV0009", "Er is meer dan één persoon gevonden met het opgegeven BSN."),

    /**
     * Levering: Bedrijfsregel BRLV0011.
     */
    LEVERING_BEDRIJFSREGEL_BRLV0011("BRLV0011",
        "Datum aanvang Materiele Periode mag, indien gevuld, niet in de toekomst liggen."),

    /**
     * Levering: Bedrijfsregel BRLV0013.
     */
    LEVERING_BEDRIJFSREGEL_BRLV0013("BRLV0013", "Datum/tijd einde synchronisatie mag niet in het verleden liggen."),

    /**
     * Levering: Bedrijfsregel BRLV0014.
     */
    LEVERING_BEDRIJFSREGEL_BRLV0014(
        "BRLV0014",
        "De persoon waarbij de partij een afnemersindicatie wil plaatsen moet binnen de populatie van de overkoepelende leveringsautorisatie vallen."),

    /**
     * Levering: Bedrijfsregel R1260.
     */
    LEVERING_BEDRIJFSREGEL_R1260("R1260",
        "De opgeven leveringsautorisatie bevat niet de opgegeven dienst"),

    /**
     * Levering: Bedrijfsregel R2055.
     */
    LEVERING_BEDRIJFSREGEL_R2055("R2055",
            "De gevraagde dienst bestaat niet"),

    /**
     * Levering: Bedrijfsregel BRLV0018.
     */
    LEVERING_BEDRIJFSREGEL_BRLV0018("BRLV0018", "De opgegeven leveringsautorisatie moet geldig zijn."),

    /**
     * Levering: Bedrijfsregel R1262.
     */
    LEVERING_BEDRIJFSREGEL_R1262("R1262", "Een opgegeven dienst moet geldig zijn."),

    /**
     * Levering: Bedrijfsregel BRLV0022.
     */
    LEVERING_BEDRIJFSREGEL_BRLV0022("BRLV0022", "De gevraagde, opgegeven persoon mag niet foutief zijn."),

    /**
     * Levering: Bedrijfsregel BRLV0023.
     */
    LEVERING_BEDRIJFSREGEL_BRLV0023("BRLV0023",
        "De persoon waarvoor de partij een synchronisatie verzoekt, moet binnen de doelbinding van de leveringsautorisatie vallen."),

    /**
     * Levering: Bedrijfsregel BRLV0024.
     */
    LEVERING_BEDRIJFSREGEL_BRLV0024("BRLV0024",
        "De gevraagde soort stamgegeven is geen soort stamgegeven dat opgevraagd mag worden."),

    /**
     * Levering: Bedrijfsregel BRLV0027.
     */
    LEVERING_BEDRIJFSREGEL_BRLV0027("BRLV0027",
        "De geleverde persoon valt niet meer binnen de doelbindingspopulatie van de leveringsautorisatie."),

    /**
     * Levering: Bedrijfsregel BRLV0028.
     */
    LEVERING_BEDRIJFSREGEL_BRLV0028("BRLV0028",
        "De geleverde persoon heeft de doelbindingspopulatie verlaten. Mutatielevering voor deze persoonslijst is gestopt."),

    /**
     * Levering: Bedrijfsregel BRLV0029.
     */
    LEVERING_BEDRIJFSREGEL_BRLV0029("BRLV0029", "De toegang leveringsautorisatie moet aanwezig en geldig zijn."),

    /**
     * Levering: Bedrijfsregel BRLV0031.
     */
    LEVERING_BEDRIJFSREGEL_BRLV0031("BRLV0031",
        "Deze definitieregel controleert of de persoon een verstrekkingsbeperking voor de verzoekende partij heeft."),

    /**
     * Levering: Bedrijfsregel BRLV0035.
     */
    LEVERING_BEDRIJFSREGEL_BRLV0035("BRLV0035", "Persoon heeft een verstrekkingsbeperking voor partij"),

    /**
     * Levering: Bedrijfsregel BRLV0038.
     */
    LEVERING_BEDRIJFSREGEL_BRLV0038("BRLV0038",
        "De opgegeven persoon valt niet binnen de doelbinding van mutatielevering obv doelbinding."),

    /**
     * Levering: Bedrijfsregel BRLV0040.
     */
    LEVERING_BEDRIJFSREGEL_BRLV0040("BRLV0040",
        "De opgegeven persoon valt niet te synchroniseren binnen de opgegeven leveringsautorisatie."),

    /**
     * Levering: Bedrijfsregel BRLV0041.
     */
    LEVERING_BEDRIJFSREGEL_BRLV0041("BRLV0041",
        "Er is geen dienst mutatielevering aanwezig binnen de opgegeven leveringsautorisatie."),

    /**
     * Levering: De soort synchronisatie wordt niet ondersteund.
     */
    LEVERING_ONGELDIGE_SOORT_SYNCHRONISATIE("LEV0014", "De soort synchronisatie wordt niet ondersteund."),

    /**
     * Levering: Bedrijfsregel VR00019.
     */
    LEVERING_BEDRIJFSREGEL_VR00109("VR00109",
        "Populatiebeperking evalueert naar waarde NULL (onbekend) voor leveringsautorisatie"),

    /**
     * Levering: Bedrijfsregel VR00062.
     */
    LEVERING_BEDRIJFSREGEL_VR00062("VR00062",
        "Attenderingscriterium evalueert naar waarde NULL (onbekend) voor leveringsautorisatie."),

    /**
     * Levering: Bedrijfsregel VR00066.
     */
    LEVERING_BEDRIJFSREGEL_VR00066("VR00066",
        "Plaatsen van afnemerindicatie door Attendering is mislukt omdat de indicatie reeds bestond voor persoon."),

    /**
     * Levering: Er is een fout opgetreden tijdens de uitvoer van het attributenfilter.
     */
    LEVERING_UITVOER_ATTRIBUTEN_FILTER_MISLUKT("LEV0015",
        "Er is een fout opgetreden tijdens de uitvoer van het attributenfilter. "),

    /**
     * Levering: leveringsautorisatie is fout geconfigureerd: het bevat geen gegevens voor persoon.
     */
    LEVERING_UITVOER_FILTER_LEGE_PERSONEN("LEV0016",
        "Leveringsautorisatie is fout geconfigureerd: het bevat geen gegevens voor persoon."),

    /**
     * Levering: Er is een fout opgetreden tijdens de uitvoer van het voorkomen filter.
     */
    LEVERING_UITVOER_VOORKOMEN_FILTER_MISLUKT("LEV0017",
        "Er is een fout opgetreden tijdens de uitvoer van het voorkomen filter."),

    /**
     * Levering: Bedrijfsregel R2052.
     */
    LEVERING_BEDRIJFSREGEL_R2052("R2052",
        "De toegang tot de leveringsautorisatie is geblokkeerd door de beheerder"),

    /**
     * Levering: Bedrijfsregel R2056.
     */
    LEVERING_BEDRIJFSREGEL_R2056("R2056",
        "De dienstbundel van de opgegeven dienst is geblokkeerd door de beheerder"),

    /**
     * Levering: Bedrijfsregel R1263.
     */
    LEVERING_BEDRIJFSREGEL_R1263("R1263",
        "De opgegeven leveringsautorisatie is geblokkeerd door de beheerder"),

    /**
     * Levering: Bedrijfsregel R1264.
     */
    LEVERING_BEDRIJFSREGEL_R1264("R1264", "De toestand van de opgegeven dienst moet definitief zijn."),

    LEVERING_BEDRIJFSREGEL_R2054("R2054", "De opgegeven dienst komt niet overeen met het soort bericht"),

    LEVERING_BEDRIJFSREGEL_R2130("R2130", "De leveringsautorisatie bevat de gevraagde dienst niet");

    private final String code;
    private final String omschrijving;

    /**
     * Constructor.
     *
     * @param code         De code
     * @param omschrijving De omschrijving
     */
    FunctioneleMelding(final String code, final String omschrijving) {
        this.code = code;
        this.omschrijving = omschrijving;
    }

    /**
     * Geeft de code van de functionele melding terug.
     *
     * @return De code van de functionele melding.
     */
    public String getCode() {
        return code;
    }

    /**
     * Geeft de omschrijving van de functionele melding terug.
     *
     * @return De omschrijving van de functionele melding.
     */
    public String getOmschrijving() {
        return omschrijving;
    }
}
