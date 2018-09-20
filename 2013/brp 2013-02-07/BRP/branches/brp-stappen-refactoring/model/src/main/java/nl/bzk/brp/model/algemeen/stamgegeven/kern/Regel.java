/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import nl.bzk.brp.model.algemeen.stamgegeven.brm.SoortRegel;


/**
 * Een Regel is een door de BRP te bewaken wetmatigheid.
 *
 * Het onderkennen van regels gebeurd vanuit het oogpunt van het bewaken van de kwaliteit van de gegevens in de BRP,
 * alsmede vanuit het kunnen garanderen van een correcte werking van de BRP. Hiertoe worden regels onderkend, die vanuit
 * verschillende contexten of situaties kunnen worden gecontroleerd. De implementatie van deze regels gebeurd in
 * principe releasematig; het kunnen uit- of aanzetten van regels is iets dat runtime kan.
 * In het objecttype Regel worden die eigenschappen gemodelleerd die alleen releasematig kunnen worden beheerd; zaken
 * als het effect van een regel, en eventuele filtering die moet plaats vinden (alleen als in bericht van soort X) vindt
 * plaats in Regelgedrag.
 *
 * In Technisch ontwerp kwaliteitscontrole versie 0.1 is onderkend dat er een 'controle moment' is. Deze wordt echter
 * releasetime beheerd: de developer heeft het wel of niet ingebouwd in 'die berichtafhandelingsroutine'. Uit Javadoc
 * kan worden gehaald waar en wanneer welke controle wordt gehaald. Om die reden is het NIET nodig deze informatie hier
 * vast te leggen; er is derhalve geen objecttype 'controle moment' onderkend.
 * RvdP 19 december 2011
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:51 CET 2013.
 */
public enum Regel {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY(null, "-1", "Dummy", null),
    /** ACT0001: Onbekende of missende actie. */
    ACT0001(null, "ACT0001","Onbekende (of niet ondersteunde) actie of ontbrekende actie.", null),
    /** ACT0002: Missend basis (root) object in actie. */
    ACT0002(null, "ACT0002", "Ontbrekend basisobject (root) in actie.", null),
    /** ALG001: Algemene fout met een meestal onbekende oorzaak. */
    ALG0001(null, "ALG0001", "Algemene fout.", null),
    /** ALG002: Een voor de actie/bericht verplicht element is niet aanwezig. */
    ALG0002(null, "ALG0002", "Verplicht element niet aanwezig", null),
    /** AUTH0001: Partij is niet geauthenticeerd voor bijhoudingen. */
    AUTH0001(null, "AUTH0001", "Partij niet geauthenticeerd.", null),
    /** REF0001: Een onbekende referentie of code is gebruikt. */
    REF0001(null, "REF0001", "Onbekende referentie gebruikt", null),
    /** BRBER00121: Geen dubbele BSN in BRP bij de inschrijving. */
    BRBER00121(null, "BRBER00121", "Het opgegeven BSN is al bekend in de BRP.", null),

    /** BRAL0001: Administratienummer voorschrift. */
    BRAL0001(null, "BRAL0001", "A-nummer is ongeldig.", null),
    /** BRAL0012: Business rule - ongeldige bsn. */
    BRAL0012(null, "BRAL0012", "Het opgegeven BSN is niet geldig.", null),
    /** BRAL0102: Business rule - Datum (deels) onbekend. */
    BRAL0102(null, "BRAL0102", "Datumformaat is niet geldig.", null),
    /** Een persoon komt 1x voor in een relatie. */
    BRAL0202(null, "BRAL0202", "Een persoon mag een keer voorkomen in een relatie.", null),
    /** BRAL1001: Waarde moet voorkomen in stamgegeven "Land". */
    /** De opgegeven combinatie van locatiegegevens is niet toegestaan.*/
    BRAL0210(null, "BRAL0210", "De opgegeven combinatie van locatiegegevens is niet toegestaan.", null),
    /** BRAL0216: Een persoon moet een ingezetene zijn. **/
    BRAL0216(null, "BRAL0216", "Een persoon moet een ingezetene zijn.", null),
    /** . */
    BRAL1001(null, "BRAL1001", "Land %s bestaat niet.", null),
    /** BRAL1002L Waarde moet voorkomen in stamgegeven "Gemeente". */
    BRAL1002(null, "BRAL1002", "Gemeente %s bestaat niet.", null),
    /** BRAL1007: Waarde moet voorkomen in stamgegeven "Reden wijziging adres". */
    BRAL1007(null, "BRAL1007", "Reden wijziging adres %s bestaat niet.", null),
    /** BRAL1012: Waarde moet voorkomen in stamgegeven "Soort relatie". */
    BRAL1012(null, "BRAL1012", "Soort releatie %s bestaat niet.", null),
    /** BRAL1015: Waarde moet voorkomen in stamgegeven "AdellijkeTitel". */
    BRAL1015(null, "BRAL1015", "Adellijke titel %s bestaat niet.", null),
    /** BRAL1017: Waarde moet voorkomen in stamgegeven "Nationaliteit". */
    BRAL1017(null, "BRAL1017", "Nationaliteit %s bestaat niet.", null),
    /** BRAL1018: Waarde moet voorkomen in stamgegeven "Predikaat". */
    BRAL1018(null, "BRAL1018", "Predikaat %s bestaat niet.", null),
    /** BRAL1020: Waarde moet voorkomen in stamgegeven "Plaats". */
    BRAL1020(null, "BRAL1020", "Woonplaats %s bestaat niet.", null),
    /** BRAL1021: Waarde moet voorkomen in stamgegeven "Reden verkrijging NL nationaliteit". */
    BRAL1021(null, "BRAL1021", "Reden verkrijging Nederlandse nationaliteit %s bestaat niet.", null),
    /** BRAL1022: Waarde moet voorkomen in stamgegeven "Reden verlies NL nationaliteit". */
    BRAL1022(null, "BRAL1022", "Reden verlies Nederlandse nationaliteit %s bestaat niet.", null),
    /** BRAL1026: Soort document %s bestaat niet.". */
    BRAL1026(null, "BRAL1026", "Soort document %s bestaat niet.", null),

    /** BRAL1118: Aangever adreshouding moet gevuld worden indien reden wijziging adres is "Aangifte door persoon". */
    BRAL1118(null, "BRAL1118", "De aangever adreshouding ontbreekt, deze is verplicht als de reden adreswijziging P (Aangifte door"
        + "persoon) is.", null),
    /** BRAL2024: Business rule - ongeldige postcode. */
    BRAL2024(null, "BRAL2024", "Postcode is niet geldig.", null),
    /** BRAL2032: Business rule - soort adres verplicht voor NL adressen. */
    BRAL2032(null, "BRAL2032", "Soort adres verplicht voor Nederlandse adressen.", null),
    /**
     * BRAL2033: Business rule - Verplichte velden voor Nederlandse adressen: gemeente, reden wijziging, datum aanvang
     * adreshouding.
     */
    BRAL2033(null, "BRAL2033", "Verplichte velden voor Nederlandse adressen: gemeente, reden wijziging, datum aanvang adreshouding.", null),
    /** BRAL2101: Gemeente aanvang H/P verplicht voor land aanvang NL. */
    BRAL2101(null, "BRAL2101", "De gemeente moet worden opgegeven als land Nederland is.", null),
    /**
     * BRAL2102: Als land aanvang huwelijk (of geregistreerd partnerschap) Nederland is dan moet datum aanvang een
     * geldige kalenderdatum zijn.
     */
    BRAL2102(null, "BRAL2102", "De datum aanvang is niet correct kalenderdatum.", null),
    /** BRAL9003: Geen bijhouden gegevens na opschorting persoon. */
    BRAL9003(null, "BRAL9003", "De persoon is opgeschort en mag niet worden bijgehouden.", null),
    /** BRAL9010: De waarde van datum/tijdontlening van de Actie mag niet na de waarde van datum/tijdregistratie van de Actie liggen.**/
    BRAL9010(null, "BRAL9010", "De waarde van datum/tijdontlening van de Actie mag niet na de waarde van datum/tijdregistratie van de Actie liggen.", null),

    /** MR0502: Geef een waarschuwing indien er al iemand op het adres ingeschreven staat. */
    MR0502(null, "MR0502", "Er is reeds een persoon ingeschreven op dit adres.", null),
    /** Volgnummers validatie op voornamen. */
    // TODO code INC001 is tijdelijk
    INC001(null, "INC001", "Voornamen met hetzelfde volgnummer zijn niet toegestaan.", null),
    /** Volgnummers validatie op Geslachtsnaamcomponenten. */
    // TODO code INC002 is tijdelijk
    INC002(null, "INC002", "Geslachtsnaamcomponenten met hetzelfde volgnummer zijn niet toegestaan.", null),
    /** Validatie opgegeven nationaliteiten. */
    INC003(null, "INC003", "Een of meer van de opgegeven nationaliteiten van de persoon zijn gelijk.", null),

    /** Datum aanvang geldigheid mag niet groter zijn dan actuele datum. */
    BRBY0011(null, "BRBY0011", "Datum aanvang geldigheid in de toekomst is niet toegestaan.", null),
    /** Datum einde geldigheid mag niet groter zijn dan actuele datum. */
    BRBY0012(null, "BRBY0012", "Datum einde geldigheid mag niet groter zijn dan actuele datum.", null),
    /** BRBY0014: Persoon moet aanwezig zijn voor bijhouden. */
    BRBY0014(null, "BRBY0014", "De gevraagde persoon komt niet voor in het systeem.", null),
    /** De verschillende periodes geldigheid mogen elkaar onderling niet overlappen. */

    BRBY0024(null, "BRBY0024", "De verschillende periodes geldigheid mogen elkaar onderling niet overlappen.", null),
    /** Datum einde geldigheid dient na datum aanvang geldigheid te liggen. */
    BRBY0032(null, "BRBY0032", "Datum einde geldigheid dient na datum aanvang geldigheid te liggen.", null),
    /**
     * BRBY0106: Geslachtsnaam van kind moet overeenkomen met die van een van de ouders en eventuele eerder
     * gekregen kinderen.
     */
    BRBY0106(null, "BRBY0106", "De opgegeven geslachtsnaam is niet toegestaan. De geslachtsnaam moet gelijk zijn aan die van een van de"
        + "ouders en aan die van eerder geboren kinderen van deze ouders.", null),

    /** Bij de ouder(s) staat reeds een kind met identieke personalia ingeschreven. */
    BRBY0126(null, "BRBY0126", "Bij de ouder(s) staat reeds een kind met identieke personalia ingeschreven.", null),

    /** De geboortedatum van het kind moet na de geboortedatum van de ouder(s) liggen. */
    BRBY0129(null, "BRBY0129", "De geboortedatum van het kind moet na de geboortedatum van de ouder(s) liggen.", null),

    /** BRBY0134 Ouders mogen niet verwant zijn (verwantschap definitie BRBY00001. */
    BRBY0134 (null, "BRBY0134 ", "Omdat er een verwantschap bestaat tussen de ouders, mag de nieuwe familierechtelijke"
            + "betrekking niet worden geregistreerd", null),
    /** BRBY0152: nationaliteit mag enkel aan kind worden toegekend. */
    BRBY0152(null, "BRBY0152", "Een nationaliteit kan alleen toegekend worden aan een persoon die als nieuwgeborene ingeschreven wordt.", null),
    /**
     * Als een persoon in het bericht een nationaliteit verkrijgt met als waarde Nederlandse,
     * dan moet de reden verkrijging in de groep Nationaliteit gevuld zijn.
     */
    BRBY0165(null, "BRBY0165", "Het is verplicht om een reden verkrijging op te geven bij het verkrijgen van de Nederlandse nationaliteit", null),

    /** Er moet precies 1 ouder uit wie het kind geboren is aanwezig zijn in het bericht. */
    BRBY0168(null, "BRBY0168", "Er moet precies 1 ouder uit wie het kind geboren is aanwezig zijn in het bericht.", null),

    /** De ouder uit wie kind niet geboren is moet een man zijn. */
    BRBY0170(null, "BRBY0170", "De ouder uit wie kind niet geboren is moet een man zijn.", null),

    /** Kind moet '9 maanden' jonger zijn dan andere kinderen uit 'Moeder'. */
    BRBY0187(null, "BRBY0187", "Kind heeft (stief)broers of (stief)zussen van dezelfde moeder die minder dan 9 maanden ouder zijn.", null),
    /**
     * BRBY0401: De minimumleeftijd voor trouwen of geregistreerd partnerschap aangaan is 18 jaar
     * voor partners met Nederlandse nationaliteit.
     */
    BRBY0401(null, "BRBY0401", "De minimumleeftijd voor trouwen of geregistreerd partnerschap aangaan is 18 jaar"
        + "voor partners met Nederlandse nationaliteit.", null),
    /**
     * BRBY0403: Een persoon die Nederlandse nationaliteit heeft mag niet trouwen
     * (of een geregistreerd partnerschap aangaan) als hij of zij op de datum aanvang huwelijk
     * (of geregistreerd partnerschap) onder curatele staat.
     */
    BRBY0403(null, "BRBY0403", "De partner is onder curatele, trouwen of geregistreerd partnerschap aangaan is niet toegestaan.", null),
    /**
     * BRBY0409: Trouwen (of geregistreerd partnerschap aangaan) mag niet worden uitgevoerd als er verwantschap is
     * tussen beoogde huwelijkspartners (of geregistreerde partners) en beoogde partners Nederlandse nationaliteit
     * hebben.
     */
    BRBY0409(null, "BRBY0409","Er is verwantschap tussen de partners, trouwen of geregistreerd partnerschap aangaan is niet toegestaan.", null),

    /**
     * Het wijzigen van geslachtsnaam en/of het vastleggen van aanschrijving (wijze van gebruik geslachtsnaam)
     * bij een huwelijk (of geregistreerd partnerschap) mag alleen voor de persoon die beoogd partner is.
     */
    BRBY04011(null, "BRBY04011", "De persoon van wie geslachtsnaam of de wijze van gebruik geslachtsnaam"
            + " wordt gewijzigd moet een van partners zijn.", null),
    /**
     * BRBY0417: De partner heeft al een niet- beeindigd huwelijk of geregistreerd partnerschap, trouwen of
     * geregistreerd partnerschap aangaan is niet toegestaan.
     */
    BRBY0417(null, "BRBY0417", "De partner heeft al een niet- beeindigd huwelijk of geregistreerd partnerschap, trouwen of"
        + " geregistreerd partnerschap aangaan is niet toegestaan.", null),

    /** BRBY0428: Op datum aanvang van een gebeurtenis moet de opgegeven plaatscode geldig zijn. */
    BRBY0428(null, "BRBY0428", "Woonplaatscode %s is niet correct", null),

    /** BRBY0429: Op datum aanvang van een gebeurtenis moet de opgegeven gemeentecode geldig zijn. */
    BRBY0429(null, "BRBY0429", "Gemeente %s is niet correct", null),

    /** BRBY0430: Op datum aanvang van een gebeurtenis moet de opgegeven landcode geldig zijn.*/
    BRBY0430(null, "BRBY0430", "Land %s is niet correct", null),

    /** BRBY0438: Datum aanvang huwelijk mag niet in de toekomst liggen. */
    BRBY0438(null, "BRBY0438", "Datum aanvang huwelijk mag niet in de toekomst liggen.", null),
    /** BRBY0521: Datum aanvang adreshouding voor de datum inschrijving BRP is niet toegestaan. */
    BRBY0521(null, "BRBY0521", "Datum aanvang adreshouding voor de datum inschrijving BRP is niet toegestaan.", null),
    /**
     * BRBY0525: Na verwerking van alle adrescorrecties uit het bericht moet gelden: bij een adres dient de datum
     * aanvang adreshouding gelijk te zijn aan datum aanvang geldigheid (bij het vastleggen van een staartadres ten
     * gevolge van een adrescorrectie).
     */
    BRBY0525(null, "BRBY0525", "De opgegeven adrescorrectie of een als gevolg daarvan gecreëerd adresrecord heeft een datum aanvang "
        + "adreshouding die niet gelijk is aan de datum aanvang geldigheid.", null),
    /**
     * BRBY0527: Waarde van gemeentecode moet geldig zijn in stamgegeven "Gemeente" vanaf datum aanvang geldigheid tot
     * datum einde geldigheid van het adres.
     */
    BRBY0527(null, "BRBY0527", "De gemeente %s is niet (of niet volledig) geldig binnen de opgegeven periode.", null),
    /** BRBY0531: De woonplaats en woonplaatscode is niet (of niet volledig) geldig binnen de opgegeven periode. */
    BRBY0531(null, "BRBY0531", "De woonplaats %s is niet (of niet volledig) geldig binnen de opgegeven periode.", null),
    /** BRBY0532: De adrescorrecties in 1 bericht moeten betrekking hebben op dezelfde persoon. */
    BRBY0532(null, "BRBY0532", "De adrescorrecties in 1 bericht moeten betrekking hebben op dezelfde persoon.", null),
    /** BRBY0901 Overlijden kan niet worden geregistreerd omdat de bijhouding al is opgeschort wegens overlijden. */
    BRBY0901(null, "BRBY0901", "Overlijden kan niet worden geregistreerd omdat de bijhouding al is opgeschort wegens overlijden", null),
    /** Datum overlijden moet op of na datum eerste inschrijving liggen en mag niet in de toekomst liggen.*/
    BRBY0902(null, "BRBY0902", "Datum overlijden moet op of na datum eerste inschrijving liggen en mag niet in de toekomst liggen.", null),
    /** BRYBY0903: De gemeente van overlijden moet verwijzen naar een geldige gemeente op datum overlijden. **/
    BRBY0903(null, "BRBY0903", "De gemeente van overlijden moet verwijzen naar een geldige gemeente op datum overlijden.", null),
    /** BRBY0904: Het land overlijden moet verwijzen naar een geldig voorkomen in stamgegeven "Land" op peildatum datum overlijden. **/
    BRBY0904(null, "BRBY0904", "Het land van overlijden moet verwijzen naar een geldig land op datum overlijden.", null),
    /** De woonplaats overlijden moet verwijzen naar een geldig voorkomen in stamgegeven "Plaats" op peildatum datum overlijden. **/
    BRBY0905(null, "BRBY0905", "De plaats van overlijden moet verwijzen naar een geldige plaats op datum overlijden", null),
    /** Er zijn al rechtsfeiten geregistreerd die hebben plaatsgevonden na de datum van overlijden.*/
    BRBY0907(null, "BRBY0907", "Er zijn al rechtsfeiten geregistreerd die hebben plaatsgevonden na de datum van overlijden.", null),
    /** De datum aanvang geldigheid van de actie Overlijden moet gelijk zijn aan de datum overlijden. **/
    BRBY0908(null, "BRBY0908", "De datum aanvang geldigheid van de actie Overlijden moet gelijk zijn aan de datum overlijden.", null),
    /** De ouder uit wie het kind geboren is, moet een vrouw zijn. */
    BRPUC00112(null, "BRPUC00112", "De ouder uit wie het kind geboren is, moet een vrouw zijn.", null),
    /** BRPUC00120: De geboortedatum van nieuwgeborene moet liggen na de datum aanvang adreshouding van moeder. * */
    BRPUC00120(null, "BRPUC00120", "De adresgevende ouder is verhuisd na de geboorte. Het kind dient wellicht ook te worden verhuisd.", null),
    /** BRPUC50110: Kandidaat vader. **/
    BRPUC50110(null, "BRPUC50110", "Kandidaat-vader kan niet worden bepaald.", null),
    /** BRBY0033: Vader moet voldoen aan kandidaatregels. */
    BRBY0033(null, "BRBY0033", "De opgegeven vader voldoet niet aan de regels gesteld in Nederlandse of vreemde wetgeving.", null),
    BRBY0033_1(null, "BRBY0033_1", "Er is geen vader opgegeven, terwijl er wel een kandidaat in de BRP staat geregistreerd", null)
    ;

    private final SoortRegel soort;
    private final String     code;
    private final String     omschrijving;
    private final String     specificatie;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param soort Soort voor Regel
     * @param code Code voor Regel
     * @param omschrijving Omschrijving voor Regel
     * @param specificatie Specificatie voor Regel
     */
    private Regel(final SoortRegel soort, final String code, final String omschrijving, final String specificatie) {
        this.soort = soort;
        this.code = code;
        this.omschrijving = omschrijving;
        this.specificatie = specificatie;
    }

    /**
     * Retourneert Soort van Regel.
     *
     * @return Soort.
     */
    public SoortRegel getSoort() {
        return soort;
    }

    /**
     * Retourneert Code van Regel.
     *
     * @return Code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Retourneert Omschrijving van Regel.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert Specificatie van Regel.
     *
     * @return Specificatie.
     */
    public String getSpecificatie() {
        return specificatie;
    }

}
