/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie;

/**
 * Enumeratie voor alle meldingscodes.
 */
public enum MeldingCode {

    /** ACT0001: Onbekende of missende actie. */
    ACT0001("Onbekende (of niet ondersteunde) actie of missende actie."),
    /** ACT0002: Missend basis (root) object in actie. */
    ACT0002("Missend basis (root) object in actie."),
    /** ALG001: Algemene fout met een meestal onbekende oorzaak. */
    ALG0001("Algemene fout."),
    /** ALG002: Een voor de actie/bericht verplicht element is niet aanwezig. */
    ALG0002("Verplicht element niet aanwezig."),
    /** VERH0001: Algemene fout opgetreden vanwege fouten bij de verhuizing. */
    VERH0001("Algemene fout opgetreden vanwege fouten bij de verhuizing."),
    /** AUTH0001: Partij is niet geauthenticeerd voor bijhoudingen. */
    AUTH0001("Partij niet geauthenticeerd."),
    /** REF0001: Een onbekende referentie of code is gebruikt. */
    REF0001("Onbekende referentie gebruikt."),
    /** BRBER00121: Geen dubbele BSN in BRP bij de inschrijving. */
    BRBER00121("Geen dubbele BSN in BRP bij de inschrijving."),
    /** BRAL0001: Administratienummer voorschrift. */
    BRAL0001("A nummer is ongeldig."),
    /** BRAL0012: Business rule - ongeldige bsn. */
    BRAL0012("Het opgegevens bsn is een niet geldig bsn."),
    /** BRAL2024: Business rule - ongeldige postcode. */
    BRAL2024("Postcode is ongeldig."),
    /** BRAL2032: Business rule - soort adres verplicht voor NL adressen.*/
    BRAL2032("Soort adres verplicht voor Nederlandse adressen."),
    /** BRAL2033: Business rule - Verplichte velden voor Nederlandse adressen: gemeente, reden wijziging, datum aanvang adreshouding.*/
    BRAL2033("Verplichte velden voor Nederlandse adressen: gemeente, reden wijziging, datum aanvang adreshouding."),
    /** BRAL0102: Business rule - Datum (deels) onbekend. */
    BRAL0102("Datum ongeldig formaat."),
    /** BRAL9003: Geen bijhouden gegevens na opschorting persoon. */
    BRAL9003("Geen bijhouden gegevens na opschorting persoon."),
    /** MR0502: Geef een waarschuwing indien er al iemand op het adres ingeschreven staat.*/
    MR0502("Reeds persoon ingeschreven op het adres."),
    /** Volgnummers validatie op voornamen.**/
    //TODO code INC001 is tijdelijk
    INC001("Voornamen met hetzelfde volgnummer zijn niet toegestaan."),
    /** Volgnummers validatie op Geslachtsnaamcomponenten.**/
    //TODO code INC002 is tijdelijk
    INC002("Geslachtsnaamcomponenten met hetzelfde volgnummer zijn niet toegestaan."),
    /** Validatie opgegeven nationaliteiten.**/
    INC003("Een of meer opgegeven nationaliteiten voor persoon zijn gelijk, dit is niet toegestaan."),
    /** BRPUC00120: De geboortedatum van nieuwgeborene moet liggen na de datum aanvang adreshouding van moeder.**/
    BRPUC00120("De geboortedatum van nieuwgeborene moet liggen na de datum aanvang adreshouding van moeder.");


    private final String omschrijving;

    /**
     * Standaard constructor die direct de standaard omschrijving van de melding initialiseert.
     *
     * @param omschrijving de standaard omschrijving van de melding.
     */
    private MeldingCode(final String omschrijving) {
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert de naam/code van de meldingcode.
     *
     * @return de naam/code van de meldingcode.
     */
    public String getNaam() {
        return name();
    }

    /**
     * Retourneert de standaard omschrijving van de melding.
     *
     * @return de standaard omschrijving van de melding.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
