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
    ACT0001("Onbekende (of niet ondersteunde) actie of ontbrekende actie."),
    /** ACT0002: Missend basis (root) object in actie. */
    ACT0002("Ontbrekend basisobject (root) in actie."),
    /** ALG001: Algemene fout met een meestal onbekende oorzaak. */
    ALG0001("Algemene fout."),
    /** ALG002: Een voor de actie/bericht verplicht element is niet aanwezig. */
    ALG0002("Verplicht element niet aanwezig"),
    /** AUTH0001: Partij is niet geauthenticeerd voor bijhoudingen. */
    AUTH0001("Partij niet geauthenticeerd."),
    /** REF0001: Een onbekende referentie of code is gebruikt. */
    REF0001("Onbekende referentie gebruikt"),
    /** BRBER00121: Geen dubbele BSN in BRP bij de inschrijving. */
    BRBER00121("Het opgegeven BSN is al bekend in de BRP."),
    /** BRAL0001: Administratienummer voorschrift. */
    BRAL0001("A-nummer is ongeldig."),
    /** BRAL0012: Business rule - ongeldige bsn. */
    BRAL0012("Het opgegeven BSN is niet geldig."),
    /** BRAL2024: Business rule - ongeldige postcode. */
    BRAL2024("Postcode is niet geldig."),
    /** BRAL2032: Business rule - soort adres verplicht voor NL adressen. */
    BRAL2032("Soort adres verplicht voor Nederlandse adressen."),
    /**
     * BRAL2033: Business rule - Verplichte velden voor Nederlandse adressen: gemeente, reden wijziging, datum aanvang
     * adreshouding.
     */
    BRAL2033("Verplichte velden voor Nederlandse adressen: gemeente, reden wijziging, datum aanvang adreshouding."),
    /** BRAL2101: Gemeente aanvang H/P verplicht voor land aanvang NL. */
    BRAL2101("De gemeente moet worden opgegeven als land Nederland is."),
    /** BRAL0102: Business rule - Datum (deels) onbekend. */
    BRAL0102("Datumformaat is niet geldig."),
    /** BRAL9003: Geen bijhouden gegevens na opschorting persoon. */
    BRAL9003("Het is niet toegestaan na de opschorting van de persoon gegevens bij te houden."),
    /** MR0502: Geef een waarschuwing indien er al iemand op het adres ingeschreven staat. */
    MR0502("Er is reeds een persoon ingeschreven op dit adres."),
    /** Volgnummers validatie op voornamen. **/
    // TODO code INC001 is tijdelijk
    INC001("Voornamen met hetzelfde volgnummer zijn niet toegestaan."),
    /** Volgnummers validatie op Geslachtsnaamcomponenten. **/
    // TODO code INC002 is tijdelijk
    INC002("Geslachtsnaamcomponenten met hetzelfde volgnummer zijn niet toegestaan."),
    /** Validatie opgegeven nationaliteiten. **/
    INC003("Een of meer van de opgegeven nationaliteiten van de persoon zijn gelijk."),
    /** BRPUC00120: De geboortedatum van nieuwgeborene moet liggen na de datum aanvang adreshouding van moeder. **/
    BRPUC00120("De adresgevende ouder is verhuisd na de geboorte. "
        + "Het kind dient wellicht ook te worden verhuisd."),
    /**
     * BRBY0106: Geslachtsnaam van kind moet overeenkomen met die van een van de ouders en eventuele eerder
     * gekregen kinderen.
     */
    BRBY0106("De opgegeven geslachtsnaam is niet toegestaan. De geslachtsnaam moet gelijk zijn aan die van een van de "
        + "ouders en aan die van eerder geboren kinderen van deze ouders."),
    /** BRBY0014:  Persoon moet aanwezig zijn voor bijhouden. */
    BRBY0014("De gevraagde persoon komt niet voor in het systeem."),
    /**
     * BRBY0401: De minimumleeftijd voor trouwen of geregistreerd partnerschap aangaan is 18 jaar
     * voor partners met Nederlandse nationaliteit.
     **/
    BRBY0401("De minimumleeftijd voor trouwen of geregistreerd partnerschap aangaan is 18 jaar "
        + "voor partners met Nederlandse nationaliteit."),
    /**
     * BRBY0403: Een persoon die Nederlandse nationaliteit heeft mag niet trouwen
     * (of een geregistreerd partnerschap aangaan) als hij of zij op de datum aanvang huwelijk
     * (of geregistreerd partnerschap) onder curatele staat.
     */
    BRBY0403("De partner is onder curatele, trouwen of geregistreerd partnerschap aangaan is niet toegestaan."),
    /**
     * Trouwen (of geregistreerd partnerschap aangaan) mag niet worden uitgevoerd als er verwantschap is tussen beoogde
     * huwelijkspartners (of geregistreerde partners) en beoogde partners Nederlandse nationaliteit hebben.
     */
    BRBY0409("Er is verwantschap tussen de partners, trouwen of geregistreerd partnerschap aangaan is niet toegestaan."),
    /**
     * De partner heeft al een niet- beeindigd huwelijk of geregistreerd partnerschap, trouwen of geregistreerd
     * partnerschap aangaan is niet toegestaan.
     */
    BRBY0417("De partner heeft al een niet- beeindigd huwelijk of geregistreerd partnerschap, trouwen of"
        + " geregistreerd partnerschap aangaan is niet toegestaan."),
    /**
     * De partner heeft al een niet- beeindigd huwelijk of geregistreerd partnerschap, trouwen of geregistreerd
     * partnerschap aangaan is niet toegestaan.
     */
    BRBY0429("Gemeente is niet correct"),
    /**
     * Datum aanvang huwelijk mag niet in de toekomst liggen.
     */
    BRBY0438("Datum aanvang huwelijk mag niet in de toekomst liggen."),
    /**
     * Een persoon komt 1x voor in een relatie.
     */
    BRAL0202("Een persoon mag een keer voorkomen in een relatie.");

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
