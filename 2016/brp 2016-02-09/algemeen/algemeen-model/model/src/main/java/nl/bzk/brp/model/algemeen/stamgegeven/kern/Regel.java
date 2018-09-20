/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import java.util.Arrays;
import nl.bzk.brp.model.algemeen.stamgegeven.brm.SoortRegel;


/**
 * Een Regel is een door de BRP te bewaken wetmatigheid.
 * <p/>
 * Het onderkennen van regels gebeurt vanuit het oogpunt van het bewaken van de kwaliteit van de gegevens in de BRP, alsmede vanuit het kunnen garanderen
 * van een correcte werking van de BRP. Hiertoe worden regels onderkend, die vanuit verschillende contexten of situaties kunnen worden gecontroleerd. De
 * implementatie van deze regels gebeurt in principe releasematig; het kunnen uit- of aanzetten van regels is iets dat runtime kan. In het objecttype Regel
 * worden die eigenschappen gemodelleerd die alleen releasematig kunnen worden beheerd; zaken als het effect van een regel, en eventuele filtering die moet
 * plaatsvinden (bijv. alleen als in bericht van soort X) is de verantwoordelijkheid van de RegelManager.
 * <p/>
 */
public enum Regel {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY(null, "Dummy", null),
    /**
     * ACT0001: Onbekende of missende actie.
     */
    ACT0001(null, "Onbekende (of niet ondersteunde) actie of ontbrekende actie.", null),
    /**
     * ACT0002: Missend basis (root) object in actie.
     */
    ACT0002(null, "Ontbrekend basisobject (root) in actie.", null),
    /**
     * ALG001: Algemene fout met een meestal onbekende oorzaak.
     */
    ALG0001(null, "Algemene fout.", null),
    /**
     * ALG002: Een voor de actie/bericht verplicht element is niet aanwezig.
     */
    ALG0002(null, "Verplicht element niet aanwezig", null),
    /**
     * ALG003: Gegeven in onderzoek.
     */
    ALG0003(null, "Gegeven in onderzoek.", null),
    /**
     * ALG004: Gegeven geraakt met nieuwe datum aanvang geldigheid.
     */
    ALG0004(null, "Gegeven geraakt met nieuwe datum aanvang geldigheid.", null),
    /**
     * AUTH0001: Partij is niet geauthenticeerd voor bijhoudingen.
     */
    AUTH0001(null, "Partij niet geauthenticeerd.", null),
    /**
     * REF0001: Een onbekende referentie of code is gebruikt.
     */
    REF0001(null, "Onbekende referentie gebruikt", null),
    /**
     * BRBER00121: Geen dubbele BSN in BRP bij de inschrijving.
     */
    BRBER00121(null, "Het opgegeven BSN is al bekend in de BRP.", null),

    /**
     * BRAL0001: Administratienummer voorschrift.
     */
    BRAL0001(null, "A-nummer is ongeldig.", null),
    /**
     * BRAL0004: Geen dubbele BSN in BRP bij de inschrijving, was BRBER00121.
     */
    BRAL0004(null, "Het BSN is reeds toegekend aan een andere ingeschrevene.", null),
    /**
     * BRAL0012: Business rule - ongeldige bsn.
     */
    BRAL0012(null, "Het opgegeven BSN is niet geldig.", null),
    /**
     * BRAL0013: A-nummer moet uniek zijn.
     */
    BRAL0013(null, "Het A-nummer is reeds toegekend aan een andere ingeschrevene.", null),
    /**
     * BRAL0014: Persoon sleutel in bericht mag niet verlopen zijn.
     */
    BRAL0014(null, "Objectsleutel %s is verlopen.", null),
    /**
     * BRAL0015: Persoon sleutel in bericht moet aan partij geleverd zijn.
     */
    BRAL0015(null, "Objectsleutel mag niet door partij gebruikt worden.", null),
    /**
     * BRAL0016: Persoon sleutel in bericht moet valide zijn.
     */
    BRAL0016(null, "Objectsleutel is niet correct.", null),
    /**
     * BRAL0102: Business rule - Datum (deels) onbekend.
     */
    BRAL0102(null, "Datumformaat is niet geldig.", null),
    /**
     * BRAL0181: Land/gebied migratie moet verwijzen naar bestaand stamgegeven.
     */
    BRAL0181(null, "Land/gebied %s is geen bestaande waarde.", null, ""),
    /**
     * Een persoon komt 1x voor in een relatie.
     */
    BRAL0202(null, "Een persoon mag een keer voorkomen in een relatie.", null),
    /**
     * BRAL0205: Toegestane/verplichte groepen voor een Niet-Ingeschrevene.
     */
    BRAL0205(null, "De opgegeven gegevens zijn niet overeenstemming met die voor een niet-ingeschrevene.", null),
    /**
     * Een niet ingeschreven persoon mag maar 1 keer in een relatie in de BRP voorkomen.
     */
    BRAL0207(null, "Een niet-ingeschreven persoon moet betrokken zijn in precies één relatie.", null),

    /** VALIDATIE REGELS ROND LOCATIE - BEGIN */
    /**
     * BRAL0216 Buitenlandse regio is optioneel i.c.m. buitenlandse plaats.
     */
    BRAL0216(null, "Buitenlandse regio mag alleen gevuld zijn als ook buitenlandse plaats gevuld is.", null),
    /**
     * BRAL0208 Buitenlandse plaats is optioneel in een buitenlandse locatie.
     */
    BRAL0208(null, "Buitenlandse plaats mag alleen gevuld worden in een buitenlandse locatie.", null),
    /**
     * BRAL0209 Woonplaats is optioneels i.c.m. Gemeente.
     */
    BRAL0209(null, "Woonplaats mag geen waarde hebben als Gemeente niet is ingevuld.", null),
    /**
     * BRAL0210: Gemeente is verplicht in NL locatie.
     */
    BRAL0210(null, "Gemeente is verplicht in Nederlandse locatie, anders mag het geen waarde hebben.", null),
    /**
     * BRAL0218: Omschrijving locatie is verplicht in buitenlandse locatie zonder plaats.
     */
    BRAL0218(null, "Omschrijving locatie is verplicht in een buitenlandse locatie zonder plaats, "
        + "maar anders niet toegestaan.", null),
    /** VALIDATIE REGELS ROND LOCATIE - EINDE */

    /**
     * Specieke partij moet verwijzen naar bestaand stamgegeven.
     */
    BRAL0220(null, "Partij %s is geen bestaande waarde.", null, "partijCode"),
    /**
     * Gemeente verordening moet verwijzen naar bestaand stamgegeven.
     */
    BRAL0221(null, "Gemeente verordening %s is geen bestaande waarde.", null, "gemeenteVerordeningCode"),

    /**
     * De lengte van de concatenatie van alle actuele exemplaren van Voornaam van een Persoon gescheiden door spaties, mag niet langer dan 200 karakters
     * zijn.
     */
    BRAL0211(null, "De lengte van de voornamen mag bij elkaar opgeteld niet langer " + "dan 200 karakters zijn.", null),
    /**
     * Het attribuut Scheidingsteken moet een waarde hebben als ook Voorvoegsel een waarde heeft, anders mag Scheidingsteken geen waarde hebben.
     */
    BRAL0212(null, "Voorvoegsel en scheidingsteken hebben of beide een waarde,of beide geen waarde.", null),
    /**
     * Predikaat en adellijke titel sluiten elkaar uit.
     */
    BRAL0213(null, "Predikaat en adellijke titel mogen niet beide gevuld zijn.", null),
    /**
     * BRAL0219: Staatloos verplicht indien Nationaliteit ontbreekt.
     */
    BRAL0219(null, "Er is geen nationaliteit - ook geen onbekende - en ook geen indicatie staatloos aanwezig.", null),
    /**
     * BRAL0501: In de voornaam 'voornaam' is de spatie niet toegestaan. *
     */
    BRAL0501(null, "In de voornaam '%s' is de spatie niet toegestaan.", null, "standaard.naam"),
    /**
     * BRAL0502: Namenreeks en Voornaam sluiten elkaar uit.
     */
    BRAL0502(null, "Voornaam mag niet voorkomen als er sprake is van een Namenreeks.", null),
    /**
     * In de groep Voornaam moeten de Volgnummers van Naam aaneensluitend genummerd zijn beginnend met de waarde "1".
     */
    BRAL0503(null, "Volgnummers in Voornaam moeten uniek zijn en aaneensluitend genummerd"
        + " met beginwaarde  \"1\" .", null),
    /**
     * Het volgnummer in de groep Geslachtsnaamcomponent heeft altijd de waarde 1.
     */
    BRAL0504(null, "Volgnummer van Geslachtsnaamcomponent moet \"1\" zijn.", null),
    /**
     * BRAL0505: Namenreeks en Voorvoegsel sluiten elkaar uit.
     */
    BRAL0505(null, "Voorvoegsel mag niet voorkomen als er sprake is van een Namenreeks", null),
    /**
     * BRAL0512: Aanschrijving algoritmisch afgeleid (waarde 'nee') algemeen regel.
     */
    BRAL0512(null, "Geen waarden in de groep Aanschrijving toegestaan (geslachtsnaam, voornamen, "
        + "predikaat of adellijke titel)  als \"aanschrijving algoritmisch afgeleid?\" is \"Ja\".", null),
    /**
     * BRAL0516: Geslachtsnaam verplicht als aanschrijving niet algoritmisch afgeleid.
     */
    BRAL0516(null, "Geslachtsnaam moet ingevuld zijn als de aanschrijving niet algoritmisch" + " wordt afgeleid.",
        null),
    /**
     * .
     */
    BRAL1001(null, "Land %s bestaat niet.", null, ""),
    /**
     * BRAL1002L Waarde moet voorkomen in stamgegeven "Gemeente". Opgesplitst omdat deze geldt voor 2 verschillende attributen.
     */
    BRAL1002(null, "Gemeente %s bestaat niet.", null),
    /**
     * BRAL1002A.
     */
    BRAL1002A(null, "Gemeente %s bestaat niet.", null, "partijCode"),
    /**
     * BRAL1002B.
     */
    BRAL1002B(null, "Gemeente %s bestaat niet.", null, "afnemerCode"),
    /**
     * BRAL1007: Reden wijziging adres moet verwijzen naar bestaand stamgegeven.
     */
    BRAL1007(null, "Reden wijziging code %s bestaat niet.", null),
    /**
     * BRAL1008: Aangever adreshouding moet verwijzen naar bestaand stamgegeven.
     */
    BRAL1008(null, "Aangever adreshouding code %s bestaat niet.", null),
    /**
     * BRAL1012: Waarde moet voorkomen in stamgegeven "Soort relatie".
     */
    BRAL1012(null, "Soort relatie %s bestaat niet.", null),
    /**
     * BRAL1015: Waarde moet voorkomen in stamgegeven "AdellijkeTitel".
     */
    BRAL1015(null, "Adellijke titel %s bestaat niet.", null),
    /**
     * BRAL1017: Waarde moet voorkomen in stamgegeven "Nationaliteit".
     */
    BRAL1017(null, "Nationaliteit %s bestaat niet.", null),
    /**
     * BRAL1018: Waarde moet voorkomen in stamgegeven "Predikaat".
     */
    BRAL1018(null, "Predikaat %s bestaat niet.", null),
    /**
     * BRAL1019: Waarde moet voorkomen in Stamgegeven "RedenBeëindigingRelatie".
     */
    BRAL1019(null, "Reden beëindiging relatie %s bestaat niet.", null),
    /**
     * BRAL1020: Waarde moet voorkomen in stamgegeven "Plaats".
     */
    BRAL1020(null, "Woonplaats %s bestaat niet.", null),
    /**
     * BRAL1021: Waarde moet voorkomen in stamgegeven "Reden verkrijging NL nationaliteit".
     */
    BRAL1021(null, "Reden verkrijging Nederlandse nationaliteit %s bestaat niet.", null),
    /**
     * BRAL1022: Waarde moet voorkomen in stamgegeven "Reden verlies NL nationaliteit".
     */
    BRAL1022(null, "Reden verlies Nederlandse nationaliteit %s bestaat niet.", null),
    /**
     * BRAL1023: Reden onttrekking reisdocument moet verwijzen naar bestaand stamgegeven.
     */
    BRAL1023(null, "Reden vervallen reisdocument %s bestaat niet in stamgegeven.", null),
    /**
     * BRAL1025: Soort reisdocument moet verwijzen naar bestaand stamgegeven.
     */
    BRAL1025(null, "Soort reisdocument %s moet voorkomen in stamgegeven.", null),
    /**
     * BRAL1026: Soort document %s bestaat niet.".
     */
    BRAL1026(null, "Soort document %s bestaat niet.", null),
    /**
     * In de groep Standaard van Document moet de waarde van Partij voorkomen in Stamgegeven Partij.
     */
    BRAL1030(null, "Partij %s bestaat niet als stamgegeven.", null, "standaard.partijCode"),
    /**
     * BRAL1031: Combinatie Scheidingsteken en Voorvoegsel moet voorkomen als stamgegeven.".
     */
    BRAL1031(null, "De combinatie scheidingsteken en voorvoegsel moet voorkomen als stamgegeven.", null),

    /**
     * BRAL1118: Aangever adreshouding moet gevuld worden indien reden wijziging adres is "Aangifte door persoon".
     */
    BRAL1118(
        null,
        "De aangever is verplicht bij reden wijziging verblijf P (Aangifte door persoon), maar niet toegestaan bij geen of een andere reden.",
        null),
    /**
     * BRAL2013: Staatloos en Nationaliteit sluiten elkaar uit.
     */
    BRAL2013(null, "Een persoon mag niet een nationaliteit bezitten en tegelijkertijd staatloos zijn.", null),
    /**
     * BRAL2017: Vastgesteld niet Nederlander en Nederlandse nationaliteit sluiten elkaar uit.
     */
    BRAL2017(null, "Een vastgesteld niet-Nederlander mag niet de Nederlandse nationaliteit hebben.", null),
    /**
     * BRAL2018: Behandeld als Nederlander en Nederlandse nationaliteit sluiten elkaar uit.
     */
    BRAL2018(null, "Een persoon met de indicatie behandeld als Nederlander mag niet tegelijkertijd de Nederlandse "
        + "nationaliteit hebben.", null),
    /**
     * De reden verkrijging mag alleen gevuld zijn voor de Nederlandse nationaliteit.
     */
    BRAL2010(null, "Alleen bij een betrokkenheid 'ouder' mag een persoon ontbreken", null),
    /**
     * De reden verkrijging mag alleen gevuld zijn voor de Nederlandse nationaliteit.
     */
    BRAL2011(null, "Bij verkrijging van de Nederlandse nationaliteit moet een reden worden vermeld,"
        + " bij andere nationaliteiten mag geen reden worden vermeld.", null),
    /**
     * BRAL2024: Business rule - ongeldige postcode.
     */
    BRAL2024(null, "Formaat van de Postcode %s is niet juist.", null, "standaard.postcode.waarde"),
    /**
     * BRAL2025: Locatie omschrijving is optioneels in NL adres zonder straatnaam.
     */
    BRAL2025(null, "Alleen in een Nederlands adres zonder afgekorte naam openbare ruimte, mag locatie"
        + "omschrijving een waarde hebben.", null),
    /**
     * BRAL2027: Straatnaam is verplicht bij aangifte door persoon van NL adres.
     */
    BRAL2027(null, "Afgekorte naam openbare ruimte is verplicht bij aangifte door persoon van Nederlands" + " adres",
        null),
    /**
     * BRAL2031: Adresseerbaar object (BAG adres) is optioneel in NL adres (BR).
     */
    BRAL2031(null, "Adresseerbaar object mag niet gevuld worden in buitenlands adres.", null),
    /**
     * BRAL2032: Business rule - soort adres verplicht voor NL adressen.
     */
    BRAL2032(null, "Adresseerbaar object is verplicht voor een Nederlands adres.", null),
    /**
     * BRAL2033: In Nederlands adres zijn zekere gegevens verplicht.
     */
    BRAL2033(null,
        "In een Nederlands adres zijn Gemeente,Soort adres en Datum aanvang adreshouding verplicht,maar niet"
            + " toegestaan in een buitenlands adres.", null),
    /**
     * BRAL2035: Gemeentedeel is optioneel icm Gemeente. *
     */
    BRAL2035(null, "Gemeentedeel mag alleen gevuld zijn i.c.m. een Gemeente", null),
    /**
     * BRAL2038: Binnen de buitenlandse adresregels is regel twee verplicht.
     */
    BRAL2038(null, "Binnen de buitenlandse adresregels is regel twee verplicht.", null),
    /**
     * BRAL2039: Buitenlandse adresregel 1 is verplicht als regel 3 is ingevuld.
     */
    BRAL2039(null, "Buitenlandse adresregel 1 is verplicht als regel 3 is ingevuld.", null),
    /**
     * BRAL2083: Huisnummer is verplicht bij straatnaam. *
     */
    BRAL2083(null, "Huisnummer moet een waarde hebben als straatnaam is ingevuld", null),
    /**
     * BRAL2084: Huisletter is optioneels bij huisnummer.
     */
    BRAL2084(null, "Huisletter mag alleen worden gebruikt bij een huisnummer", null),
    /**
     * BRAL2085: Huisnummertoevoeging is optioneels bij huisnummer.
     */
    BRAL2085(null, "Huisnummertoevoeging mag alleen worden gebruikt bij een huisnummer", null),
    /**
     * BRAL2086: Locatie tov adres is optioneels bij huisnummer als toevoeging ontbreekt.
     */
    BRAL2086(null, "Locatie ten opzichte van adres mag alleen worden gebruikt bij een huisnummer"
        + " als een huisnummertoevoeging ontbreekt.", null),
    /**
     * BRAL2094: Postcode is optioneel i.c.m. straatnaam en huisnummer.
     */
    BRAL2094(null, "Postcode mag alleen waarde hebben i.c.m. afgekorte naam openbare ruimte en huisnummer", null),
    /**
     * BRAL2095: In een BAG-adres zijn Adresseerbaar object Identificatiecode nummeraanduiding, Naam openbare ruimte en Woonplaats verplicht, maar niet
     * toegestaan in een niet BAG-adres.
     */
    BRAL2095(null, "In een BAG-adres zijn Adresseerbaar object, "
        + "Identificatiecode nummeraanduiding, Naam openbare ruimte en Woonplaats verplicht, maar niet toegestaan "
        + "in een niet BAG-adres", null),
    /**
     * BRAL2102: Datum aanvang H/P moet geldige kalenderdatum zijn bij aanvang in NL.
     */
    BRAL2102(null, "De datum aanvang van de relatie is niet een correcte kalenderdatum.", null),
    /**
     * BRAL2103: Datum einde H/P moet geldige kalenderdatum zijn bij einde in NL.
     */
    BRAL2103(null, "De datum einde van de relatie is niet een correcte kalenderdatum.", null),
    /**
     * BRAL2104: H/P mag alleen door betrokken gemeente worden geregistreerd.
     */
    BRAL2104(null, "Alleen de gemeente aanvang, gemeente einde en de bijhoudingsgemeente(n) van de partners "
        + "mogen het huwelijk of partnerschap actualiseren.", null),
    /**
     * BRAL2110: Gemeente aanvang H/P moet registergemeente zijn.
     */
    BRAL2110(null, "De gemeente aanvang van het %s moet gelijk zijn aan de registergemeente.", null, "soortRelatie"),
    /**
     * BRAL2111: In een H/P-relatie zijn twee partners betrokken.
     */
    BRAL2111(null, "In een huwelijk of geregistreerd partnerschap moeten twee partners zijn betrokken.", null),
    /**
     * BRAL2112: Gemeente einde H/P moet gelijk zijn aan gemeente aanvang.
     */
    BRAL2112(null, "De gemeente einde van het %s moet gelijk zijn aan de "
        + "gemeente aanvang of een opvolgende gemeente daarvan.", null, "soortRelatie"),
    /**
     * BRAL2113: Datum einde relatie moet op of na datum aanvang relatie liggen.
     */
    BRAL2113(null, "Datum einde van het %s mag niet vóór datum aanvang liggen. ", null, "soortRelatie"),
    /**
     * BRAL2203: Datum aanvang actie mag niet voor geboortedatum liggen.
     */
    BRAL2203(null, "Een datum aanvang mag niet voor de geboortedatum liggen.", null),
    /**
     * BRAL9003: Geen bijhouden gegevens na overlijden persoon.
     */
    BRAL9003(null, "De persoon mag niet worden bijgehouden, vanwege overlijden.", null),
    /**
     * BRAL9025: Locatie omschrijving mag uit niet meer dan 35 karakters bestaan.
     */
    BRAL9025(null, "Locatie omschrijving is langer dan 35 karakters, de standaard voor adressering.", null),
    /**
     * BRAL9027: Buitenlandse adresregel mag niet uit meer dan 35 karakters bestaan.
     */
    BRAL9027(null, "Buitenlandse adresregel mag niet meer dan 35 karakters bevatten.", null),
    /**
     * Volgnummers validatie op voornamen.
     */
    INC001(null, "Voornamen met hetzelfde volgnummer zijn niet toegestaan.", null),
    /**
     * Volgnummers validatie op Geslachtsnaamcomponenten.
     */
    INC002(null, "Geslachtsnaamcomponenten met hetzelfde volgnummer zijn niet toegestaan.", null),
    /**
     * Validatie opgegeven nationaliteiten.
     */
    INC003(null, "Een of meer van de opgegeven nationaliteiten van de persoon zijn gelijk.", null),

    /**
     * Datum aanvang geldigheid mag niet groter zijn dan actuele datum.
     */
    BRBY0011(null, "Datum aanvang geldigheid in de toekomst is niet toegestaan.", null),
    /**
     * Datum einde geldigheid mag niet groter zijn dan actuele datum.
     */
    BRBY0012(null, "Datum einde geldigheid mag niet groter zijn dan actuele datum.", null),
    /**
     * BRBY0014: Persoon moet aanwezig zijn voor bijhouden.
     */
    BRBY0014(null, "Het in het bericht geïdentificeerde object met ID %s bestaat niet of is niet van de juiste soort.",
        null),
    /**
     * De verschillende periodes geldigheid mogen elkaar onderling niet overlappen.
     */
    BRBY0024(null, "De verschillende periodes geldigheid mogen elkaar onderling niet overlappen.", null),
    /**
     * Datum einde geldigheid dient na datum aanvang geldigheid te liggen.
     */
    BRBY0032(null, "Datum einde geldigheid dient na datum aanvang geldigheid te liggen.", null),

    /**
     * BRBY01032: Gemeente geboorte moet geldig zijn op geboortedatum.
     */
    BRBY01032(null, "Gegeven %s heeft geen geldige waarde met als peildatum de geboortedatum.", null,
        "geboorte.gemeenteGeboorte.waarde.naam"),
    /**
     * BRBY01037: Land Gebied geboorte moet geldig zijn op geboortedatum.
     */
    BRBY01037(null, "Gegeven %s heeft geen geldige waarde met als peildatum de geboortedatum.", null,
        "geboorte.landGebiedGeboorte.waarde.naam.waarde"),

    /**
     * Oma/opa artikel (nationaliteit kind).
     */
    BRBY0105M(null, "Het kind zou de Nederlandse nationaliteit moeten hebben omdat minstens één ouder"
        + " in Nederland is geboren of voor zijn/haar 18e jaar voor de eerste keer in Nederland is gevestigd.",
        null),
    /**
     * BRBY0106: Geslachtnaam van kind wijkt af van geslachtsnaam van de ouder(s).
     */
    BRBY0106(null, "Geslachtnaam van kind wijkt af van geslachtsnaam van de ouder(s).", null),
    /**
     * BRBY0107: Geslachtsnaam kind moet overeenkomen met broer/zus.
     */
    BRBY0107(null, "Geslachtnaam kind wijkt af van een ander kind met dezelfde ouders.", null),
    /**
     * Bij de ouder(s) staat reeds een kind met identieke personalia ingeschreven.
     */
    BRBY0126(null, "Bij de ouder(s) staat reeds een kind met identieke personalia ingeschreven.", null),
    /**
     * De geboortedatum van het kind moet na de geboortedatum van de ouder(s) liggen.
     */
    BRBY0129(null, "De geboortedatum van het kind moet na de geboortedatum van de ouder(s) liggen.", null),
    /**
     * BRBY0131: Uitsluiting NL kiesrecht moet meerderjarige betreffen.
     */
    BRBY0131(null, "De persoon die uitgesloten wordt van het Nederlands kiesrecht moet meerderjarig zijn.", null),
    /**
     * BRBY0132: EU verkiezingen moet niet-Nederlandse EU onderdaan betreffen.
     */
    BRBY0132(null, "Europees verkiezingen is alleen van toepassing bij een niet-Nederlander.", null),
    /**
     * BRBY0133: Datum einde uitsluiting optioneel bij uitsluiting EU verkiezingen.
     */
    BRBY0133(null, "Datum voorzien einde in geval uitsluiting EU kiesrecht mag alleen gevuld worden bij"
        + " uitsluiting van EUverkiezingen.", null),
    /**
     * BRBY0134 Ouders mogen niet verwant zijn (verwantschap definitie BRBY00001.
     */
    BRBY0134(null, "Omdat er een verwantschap bestaat tussen de ouders, mag de nieuwe familierechtelijke"
        + "betrekking niet worden geregistreerd", null),
    /**
     * BRBY0135: Datum einde uitsluiting EU verkiezingen na datum aanleiding aanpassing.
     */
    BRBY0135(null, "Datum einde uitsluiting EU verkiezingen moet na datum aanleiding aanpassing liggen.", null),
    /**
     * BRBY0136: Erkenner mag niet zijn overleden.
     */
    BRBY0136(null, "Omdat de ouder reeds is overleden, is de registratie niet toegestaan.", null),
    /**
     * BRBY0137: EU verkiezingen moet meerderjarige betreffen.
     */
    BRBY0137(null, "De persoon waarvoor EU-verkiezingen wordt geregistreerd, moet meerderjarig zijn.", null),
    /**
     * BRBY0141: De nationaliteit &nationaliteit staat reeds bij de persoon geregistreerd.
     */
    BRBY0141(null, "De nationaliteit %s staat reeds bij de persoon geregistreerd.", null, "nationaliteitCode"),
    /**
     * BRBY0143: Erkenner moet minstens 16 jaar zijn.
     */
    BRBY0143(null, "De erkenner moet minstens 16 jaar oud zijn.", null),
    /**
     * BRBY0148: Indicator Staatloos mag niet opnieuw worden toegekend.
     */
    BRBY0148(null, "Persoon kan alleen staatloos worden als de persoon dat nog niet is.", null),
    /**
     * BRBY0151: Nederlander mag geen namenreeks hebben.
     */
    BRBY0151(null, "Persoon mag geen namenreeks hebben volgens Nederlands recht.", null),
    /**
     * BRBY0152: Een nevenactie mag alleen betrekking hebben op bij de hoofdactie betrokken ingezetenen.
     */
    BRBY0152(null, "De nevenactie mag niet op deze persoon worden uitgevoerd.", null),
    /**
     * BRBY0153: Nederlander moet een voornaam hebben.
     */
    BRBY0153(null, "Een persoon met de Nederlandse nationaliteit moet een voornaam hebben.", null),
    /**
     * BRBY0157: Verkrijger Nederlandschap mag hoogstens één H/P relatie hebben.
     */
    BRBY0157(null, "Verkrijger Nederlandschap mag hoogstens één verbintenis hebben.", null),
    /**
     * BRBY0158: Indiener naturalisatieverzoek moet voldoen aan termijn.
     */
    BRBY0158(null, "Aanvrager moet ten minste 5 jaar in Nederland wonen of ten minste 3 jaar"
        + " partner/echtgeno(o)t(e) zijn van een Nederlander.", null),
    /**
     * BRBY0162: Naturalisatiebesluit moet tijdig verwerkt worden.
     */
    BRBY0162(null, "De geldigheid van het naturalisatiebesluit is na één jaar verlopen.", null),
    /**
     * BRBY0163: Verkregen nationaliteit moet de Nederlandse zijn.
     */
    BRBY0163(null, "Met deze handeling kan alleen de Nederlandse nationaliteit worden verkregen.", null),
    /**
     * BRBY0164: Verkregen nationaliteit moet een vreemde zijn.
     */
    BRBY0164(null, "Met deze handeling kan alleen een vreemde nationaliteit worden verkregen.", null),
    /**
     * BRBY0166: Geboortedatum mag niet onbekend zijn bij geboorte in Nederland.
     */
    BRBY0166(null, "De geboortedatum mag geen (deels) onbekende datum zijn als de "
        + "geboorte in Nederland heeft plaatsgevonden.", null),
    /**
     * BRBY0167: Omschrijving derde verplicht indien partij ontbreekt.
     */
    BRBY0167(null, "Omschrijving derde moet (en mag alleen) ingevuld worden indien een partij ontbreekt.", null),
    /**
     * Er moet precies 1 ouder uit wie het kind geboren is aanwezig zijn in het bericht.
     */
    BRBY0168(null, "Er moet precies 1 ouder uit wie het kind geboren is aanwezig zijn in het bericht.", null),
    /**
     * Moeder van kind moet ingezetene zijn.
     */
    BRBY0169(null, "De ouder uit wie het kind is voortgekomen, moet een ingezetene zijn.", null),

    /**
     * De ouder uit wie kind niet geboren is moet een man zijn.
     */
    BRBY0170(null, "De ouder uit wie kind niet geboren is moet een man zijn.", null),
    /**
     * BRBY0172: Het land moet geldig zijn op de datum van aanvang adreshouding.
     */
    BRBY0172(null, "Land %s is niet geldig op datum aanvang adreshouding %s.", null,
        "standaard.landGebied.waarde.code", "standaard.datumAanvangAdreshouding"),
    /**
     * BRBY0173: Nationaliteit moet verwijzen naar geldig stamgegeven.
     */
    BRBY0173(null, "Nationaliteit %s is niet geldig op datum aanvang geldigheid.", null, "nationaliteit.waarde.naam"),

    /**
     * BRBY0175: Gemeente (adres) moet verwijzen naar geldig stamgegeven.
     */
    BRBY0175(null, "De gemeente %s is niet geldig op datum aanvang adreshouding %s.", null,
        "standaard.gemeente.waarde.code", "standaard.datumAanvangAdreshouding"),

    /**
     * BRBY0176: Reden verkrijging moet verwijzen naar geldig stamgegeven.
     */
    BRBY0176(null, "Reden verkrijging %s is niet geldig op datum aanvang nationaliteit.", null,
        "standaard.redenVerkrijging.waarde.code"),

    /**
     * BRBY0177: Datum aanvang geldigheid actie gelijk aan datum geboorte.
     */
    BRBY0177(null, "De datum aanvang geldigheid van de Actie moet dezelfde waarde hebben als de datum van Geboorte.",
        null),

    /**
     * BRBY0178: Persoon mag geen kinderen hebben als nationaliteit wijzigt.
     */
    BRBY0178(null, "De wijziging van gegevens heeft mogelijk consequenties voor de kinderen.", null),
    /**
     * BRBY0179: Gemeente verordening verplicht bij omschrijving derde.
     */
    BRBY0179(null, "Gemeente verordening moet (en mag alleen) ingevuld worden als ook omschrijving derde is ingevuld.",
        null),
    /**
     * BRBY0180: Land/gebied migratie moet verwijzen naar geldig stamgegeven.
     */
    BRBY0180(null, "Land/gebied %s is niet geldig op datum aanvang geldigheid migratie %s.", null,
        "migratie.landGebiedMigratie.waarde.naam", "migratie.datumAanvangGeldigheid"),
    /**
     * BRBY0182: Volledige en specifieke verstrekkingsbeperking sluiten elkaar uit.
     */
    BRBY0182(null,
        "Bij een persoon mag niet zowel een volledige als specifieke verstrekkingsbeperking worden geregistreerd.",
        null),
    /**
     * BRBY0183: Naamwijziging mag geen gevolgen hebben voor kinderen.
     */
    BRBY0183(null, "De bijhouding van gegevens moet mogelijk ook bij kinderen worden doorgevoerd.", null),

    /**
     * Kind moet '9 maanden' jonger zijn dan andere kinderen uit 'Moeder'.
     */
    BRBY0187(null,
        "Kind heeft (stief)broers of (stief)zussen van dezelfde moeder die minder dan 9 maanden ouder zijn.", null),
    /**
     * Oma/opa artikel bij erkenning.
     */
    BRBY0189(null, "Het kind zou de Nederlandse nationaliteit moeten hebben omdat minstens een ouder"
        + " in Nederland is geboren of voor zijn/haar 18e jaar voor de eerste keer in Nederland is gevestigd.",
        null),

    /**
     * Specieke partij moet verwijzen naar geldig stamgegeven.
     */
    BRBY0191(null, "Partij %s is niet geldig op tijdstip registratie van de verstrekkingsbeperking.", null,
        "partij.waarde.naam.waarde"),
    /**
     * Verstekkingsbeperking moet mogelijk zijn voor specieke partij.
     */
    BRBY0192(null, "Voor partij %s is geen verstrekkingsbeperking mogelijk.", null, "partij.waarde.naam.waarde"),
    /**
     * Gemeente verordening moet verwijzen naar geldig stamgegeven.
     */
    BRBY0193(null, "Gemeente verordening %s is niet geldig op tijdstip registratie van de verstrekkingsbeperking.",
        null, "gemeenteVerordening.waarde.naam.waarde"),
    /**
     * Gemeente verordening betreft een partij van de soort gemeente.
     */
    BRBY0194(null, "Gemeente verordening %s moet een partij van de soort gemeente zijn.", null,
        "gemeenteVerordening.waarde.naam.waarde"),

    /**
     * BRBY0401: De minimumleeftijd voor trouwen of geregistreerd partnerschap aangaan is 18 jaar voor partners met Nederlandse nationaliteit.
     */
    BRBY0401(null, "Een persoon die als partner bij een te registreren huwelijk of geregistreerd partnerschap "
        + "is betrokken heeft niet de vereiste minimumleeftijd van 18 jaar.", null),
    /**
     * BRBY0402: Minimumleeftijd partner (overig).
     */
    BRBY0402(null, "Een persoon die als partner bij een te registreren huwelijk of geregistreerd partnerschap "
        + "is betrokken heeft niet de vereiste minimumleeftijd van 15 jaar.", null),

    /**
     * BRBY0403: Een persoon die Nederlandse nationaliteit heeft mag niet trouwen (of een geregistreerd partnerschap aangaan) als hij of zij op de datum
     * aanvang huwelijk (of geregistreerd partnerschap) onder curatele staat.
     */
    BRBY0403(null, "De partner is onder curatele, trouwen of geregistreerd partnerschap aangaan is niet toegestaan.",
        null),
    /**
     * BRBY0409: Geen verwantschap tussen partners bij voltrekking H/P in Nederland.
     */
    BRBY0409(null, "Er bestaat verwantschap tussen de partners die bij een te registreren "
        + "huwelijk of geregistreerd partnerschap zijn betrokken.", null),

    /**
     * BRBY0417: De partner heeft al een niet- beeindigd huwelijk of geregistreerd partnerschap, trouwen of geregistreerd partnerschap aangaan is niet
     * toegestaan.
     */
    BRBY0417(null, "De partner heeft al een niet- beeindigd huwelijk of geregistreerd partnerschap, "
        + "trouwen of geregistreerd partnerschap aangaan is niet toegestaan.", null),

    /**
     * BRBY0429: Op datum aanvang van een gebeurtenis moet de opgegeven gemeentecode geldig zijn.
     */
    BRBY0429(null, "Gemeente %s is niet correct", null, "standaard.gemeenteAanvang.waarde.code"),

    /**
     * BRBY0430: Op datum aanvang van een gebeurtenis moet de opgegeven landcode geldig zijn.
     */
    BRBY0430(null, "Land %s is niet correct", null, "standaard.landGebiedAanvang.waarde.code"),

    /**
     * BRBY0437: Geslachtsnaamwijziging bij H/P in Nederland alleen voor niet-Nederlanders.
     */
    BRBY0437(null, "Het wijzigen van de geslachtsnaam voor een partner met Nederlandse nationaliteit is niet"
        + " toegestaan bij gelegenheid van een huwelijk of partnerschap in Nederland.", null),

    /**
     * BRBY0438: Datum aanvang huwelijk mag niet in de toekomst liggen.
     */
    BRBY0438(null, "Datum aanvang huwelijk mag niet in de toekomst liggen.", null),

    /**
     * BRBY0442: Land aanvang mag niet NL zijn bij voltrekking H/P in buitenland.
     */
    BRBY0442(null, "Bij een huwelijk of geregistreerd partnerschap dat in het buitenland "
        + "is voltrokken mag Nederland niet als land van aanvang worden opgegeven.", null),
    /**
     * BRBY0443: Te beëindigen relatie mag niet al zijn beëindigd.
     */
    BRBY0443(null, "Een relatie kan niet worden beëindigd als deze al beëindigd was.", null),
    /**
     * BRBY0444: Datum einde huwelijk of partnerschap mag niet in de toekomst liggen.
     */
    BRBY0444(null, "Datum einde van het huwelijk of geregistreerd partnerschap mag niet in de toekomst liggen.", null),
    /**
     * BRBY0445: Reden beëindiging moet geldig zijn in context.
     */
    BRBY0445(null, "De opgegeven reden beëindiging '%s' is niet toegestaan bij de administratieve handeling '%s'.",
        null, "rootObject.standaard.redenEinde.waarde.code.waarde", "administratieveHandeling.soort.waarde.naam"),
    /**
     * BRBY0446: Land einde mag niet NL zijn bij voltrekking H/P in buitenland.
     */
    BRBY0446(null, "Bij een huwelijk of geregistreerd partnerschap dat in het buitenland "
        + "is ontbonden mag Nederland niet als land einde worden opgegeven.", null),
    /**
     * BRBY0451: Gemeente einde H/P moet geldig zijn op datum einde.
     */
    BRBY0451(null, "De gemeente %s is niet geldig.", null, "standaard.gemeenteEindeCode"),
    /**
     * BRBY0452: Land einde H/P moet geldig zijn op datum einde.
     */
    BRBY0452(null, "De landcode %s is niet geldig", null, "standaard.landGebiedEindeCode"),
    /**
     * BRBY0454: Datum aanvang geldigheid actie gelijk aan datum aanvang H/P .
     */
    BRBY0454(null, "De datum aanvang geldigheid van de Actie moet dezelfde waarde hebben als"
        + " de datum aanvang van het huwelijk of partnerschap.", null),
    /**
     * BRBY0455: Datum aanvang geldigheid actie gelijk aan datum einde H/P.
     */
    BRBY0455(null, "De datum aanvang geldigheid van de Actie moet dezelfde waarde hebben als de datum einde van het"
        + " huwelijk of partnerschap.", null),
    /**
     * BRBY0502: Geef een waarschuwing indien er al iemand op het adres ingeschreven staat.
     */
    BRBY0502(null, "Er is reeds een persoon ingeschreven op dit adres.", null),
    /**
     * BRBY0521: Datum aanvang adreshouding voor de datum inschrijving BRP is niet toegestaan.
     */
    BRBY0521(null, "Datum aanvang adreshouding voor de datum inschrijving BRP is niet toegestaan.", null),
    /**
     * BRBY0525: Na verwerking van alle adrescorrecties uit het bericht moet gelden: bij een adres dient de datum aanvang adreshouding gelijk te zijn aan
     * datum aanvang geldigheid (bij het vastleggen van een staartadres ten gevolge van een adrescorrectie).
     */
    BRBY0525(null, "De opgegeven adrescorrectie of een als gevolg daarvan gecreëerd adresrecord heeft een "
        + "datum aanvang adreshouding die niet gelijk is aan de datum aanvang geldigheid.", null),
    /**
     * BRBY0532: De adrescorrecties in 1 bericht moeten betrekking hebben op dezelfde persoon.
     */
    BRBY0532(null, "De adrescorrecties in 1 bericht moeten betrekking hebben op dezelfde persoon.", null),
    /**
     * BRBY0540: Land/gebied migratie mag niet Nederland zijn.
     */
    BRBY0540(null, "Het land/gebied van migratie mag niet Nederland zijn.", null),
    /**
     * BRBY0543: Buitenlands adres mag alleen bij emigratie naar bekend land.
     */
    BRBY0543(null, "Alleen bij emigratie naar een bekend land mag een buitenlands adres worden ingevuld.", null),
    /**
     * BRBY0593: Land/gebied migratie is verplicht tenzij VOW.
     */
    BRBY0593(null, "Land/gebied is verplicht behalve bij ambtshalve vertrek onbekend waarheen.", null),
    /**
     * Datum overlijden moet op of na datum eerste inschrijving liggen en mag niet in de toekomst liggen.
     */
    BRBY0902(null,
        "Datum overlijden moet op of na datum eerste inschrijving liggen en mag niet in de toekomst liggen.", null),
    /**
     * BRYBY0903: De gemeente van overlijden moet verwijzen naar een geldige gemeente op datum overlijden. *
     */
    BRBY0903(null, "De gemeente van overlijden moet verwijzen naar een geldige gemeente op datum overlijden.", null),
    /**
     * BRBY0904: Het land overlijden moet verwijzen naar een geldig voorkomen in stamgegeven "Land" op peildatum datum overlijden.
     */
    BRBY0904(null, "Het land van overlijden moet verwijzen naar een geldig land op datum overlijden.", null),
    /**
     * BRBY0906: Land overlijden ongelijk NL bij overlijden in buitenland.
     */
    BRBY0906(null, "In geval een buitenlands brondocument mag het land van overlijden niet Nederland zijn.", null),
    /**
     * Er zijn al rechtsfeiten geregistreerd die hebben plaatsgevonden na de datum van overlijden.
     */
    BRBY0907(null, "Er zijn al rechtsfeiten geregistreerd die hebben plaatsgevonden na de datum van overlijden.", null),
    /**
     * De datum aanvang geldigheid van de actie Overlijden moet gelijk zijn aan de datum overlijden. *
     */
    BRBY0908(null, "De datum aanvang geldigheid van de actie Overlijden moet gelijk zijn aan de datum overlijden.",
        null),
    /**
     * Overledene mag geen minderjarige kinderen hebben. *
     */
    BRBY0909(null, "De overleden persoon heeft tenminste 1 minderjarig kind.", null),
    /**
     * De Persoon waarvan het Overlijden moet worden geregistreerd moet een Ingezetene zijn op de Datum Overlijden. *
     */
    BRBY0913(null, "Persoon moet een ingezetene zijn.", null),
    /**
     * Geen rechtsfeiten geregistreerd na onder curatelestelling.
     */
    BRBY2012(null, "Er zijn al rechtsfeiten geregistreerd die hebben plaatsgevonden na datum aanvang"
        + " curatelestelling.", null),
    /**
     * BRBY2013: Ouderlijk gezag alleen mogelijk als ouder ouderschap heeft.
     */
    BRBY2013(null, "Gezag kan alleen geregistreerd worden bij een ouder met juridisch ouderschap.", null),
    /**
     * BRBY2014: Onder curatele gestelde moet meerderjarig zijn.
     */
    BRBY2014(null, "Een onder curatele gestelde persoon moet meerderjarig zijn.", null),
    /**
     * Onder gezag van derde gestelde moet minderjarig zijn.
     */
    BRBY2015(null, "Een onder gezag gestelde moet minderjarig zijn.", null),
    /**
     * Kind onder ouderlijk gezag moet minderjarig zijn.
     */
    BRBY2016(null, "Een onder gezag gestelde moet minderjarig zijn.", null),
    /**
     * BRBY2018: Gehuwde ouders sluit ouderlijk gezag uit.
     */
    BRBY2018(null, "Omdat ouders van het kind met elkaar getrouwd zijn, is toekenning ouderlijk gezag"
        + " onwaarschijnlijk.", null),
    /**
     * Hoogstens twee personen kunnen gezag hebben over kind.
     */
    BRBY2017(null, "Een derde en andere ouder hebben reeds gezag over kind.", null),
    /**
     * Gezag door derde is niet mogelijk als beide ouders gezag hebben.
     */
    BRBY2019(null, "Beide ouders hebben reeds gezag over kind.", null),
    /**
     * De ouder uit wie het kind geboren is, moet een vrouw zijn.
     */
    BRPUC00112(null, "De ouder uit wie het kind geboren is, moet een vrouw zijn.", null),
    /**
     * BRPUC00120: De geboortedatum van nieuwgeborene moet liggen na de datum aanvang adreshouding van moeder. *
     */
    BRPUC00120(null,
        "De adresgevende ouder is verhuisd na de geboorte. Het kind dient wellicht ook te worden verhuisd.", null),
    /**
     * BRPUC50110: Kandidaat vader. *
     */
    BRPUC50110(null, "Kandidaat-vader kan niet worden bepaald.", null),
    /**
     * BRBY0033: Vader moet voldoen aan kandidaatregels.
     */
    BRBY0033(null, "De opgegeven vader voldoet niet aan de regels gesteld in Nederlandse of vreemde wetgeving.", null),
    /**
     * BRBY0033: Vader / kandidaat vader.
     */
    BRBY0036(null, "Er is geen vader opgegeven, terwijl er wel een kandidaat in de BRP staat geregistreerd", null),
    /**
     * BRBY0103: Adresgevende ouder mag geen briefadres hebben.
     */
    BRBY0103(null, "Het adres van de adresgevende ouder mag geen briefadres zijn.", null),
    /**
     * CommunicatieID in bericht moet uniek zijn.
     */
    BRBY9901(null, "Elk communicatieID in het bericht moet uniek zijn", null),
    /**
     * ReferentieID in request moet verwijzen naar communicatieID in request.
     */
    BRBY9902(null, "Elk referentieID in het request moet verwijzen naar een communicatieID in het request", null),
    /**
     * ReferentieID in response moet verwijzen naar communicatieID in request.
     */
    BRBY9903(null, "Elk referentieID in antwoord moet verwijzen naar een communicatieID in " + "de bijhouding", null),
    /**
     * Gedeblokkeerde meldingen moeten in verwerking daadwerkelijk optreden.
     */
    BRBY9904(null, "De gedeblokkeerde melding is niet opgetreden", null),
    /**
     * In request mag niet naar een verwijzend element worden verwezen.
     */
    BRBY9905(null, "Een request mag geen keten- of zelfverwijzing bevatten.", null),
    /**
     * In request moeten bij verwijzing betrokken elementen van zelfde type zijn.
     */
    BRBY9906(null, "Een referentieID moet naar een element in het request verwijzen met hetzelfde entiteittype", null),
    /**
     * Verschillende ObjectIDs in bericht moet verwijzen naar gerelateerde objecten in de BRP.
     */
    BRBY9910(null, "Binnen actie geidentificeerde objecten moeten gerelateerd zijn in de BRP.", null),
    /**
     * Soort reisdocument moet verwijzen naar geldig stamgegeven.
     */
    BRBY1026(null, "Soort reisdocument %s reisdocument moet geldig zijn op %s", null, "soortCode",
        "standaard.datumUitgifte.waarde"),
    /**
     * Geldigheidsduur van reisdocument is maximaal is 10 jaar.
     */
    BRBY0037(null, "De geldigheidsduur van een reisdocument is maximaal 10 jaar", null),
    /**
     * Reisdocumenthouder mag geen belemmering verstrekking hebben.
     */
    BRBY0040(null, "Reisdocumenthouder mag geen belemmering tot verstrekking hebben.", null),
    /**
     * Hoogstens één reisdocument van een zekere soort.
     */
    BRBY0042(null, "Persoon mag niet verschillende exemplaren van een zelfde soort reisdocument hebben.", null),
    /**
     * BRBY0044: Te onttrekken reisdocument moet geldig reisdocument van persoon zijn.
     */
    BRBY0044(null, "Te onttrekken reisdocument mag niet reeds onttrokken zijn.", null),
    /**
     * BRBY0045: Aanduiding verplicht indien datum onttrekking gevuld.
     */
    BRBY0045(null,
        "Aanduiding onttrekking moet een waarde hebben dan en slechts dan als datum onttrekking een waarde heeft.",
        null),

    /**
     * BRBY05111: Adreshouder moet bevoegd zijn als aangever ingeschrevene is.
     */
    BRBY05111(null, "De verhuizende persoon moet ten minste 16 jaar zijn en mag niet onder curatele staan.", null),

    /**
     * VR0001: Verwerking van de standaard groep van voornaam.
     */
    VR00001(null, "Verwerking van de standaard groep van voornaam.", null),
    /**
     * VR00001a: Afgeleide beeindiging Voornaam.
     */
    VR00001a(null, "Afgeleide beeindiging Voornaam.", null),
    /**
     * VR0002: Verwerking van de standaard groep van geslachtsnaamcomponent.
     */
    VR00002(null, "Verwerking van de standaard groep van geslachtsnaamcomponent.", null),
    /**
     * VR0003: Verwerking van de groep samengestelde naam.
     */
    VR00003(null, "Verwerking van de groep samengestelde naam.", null),
    /**
     * VR00003a: Afleiden van de standaard groep van samengestelde naam.
     */
    VR00003a(null, "Afleiden van de standaard groep samengestelde naam.", null),
    /**
     * VR00004: Verwerken Groep Nationaliteit.
     */
    VR00004(null, "Verwerken Groep Nationaliteit", null),
    /**
     * VR00005: Verwerking van de indicatie staatloos.
     */
    VR00005(null, "Verwerking van de indicatie staatloos.", null),
    /**
     * VR00005a: Afgeleide beeindiging Staatloos.
     */
    VR00005a(null, "Afgeleide beeindiging Staatloos.", null),
    /**
     * VR00006: Verwerken Groep Vastgesteld niet-Nederlander.
     */
    VR00006(null, "Verwerken Groep Vastgesteld niet-Nederlander", null),
    /**
     * VR00006a: Afgeleide beeindiging Vastgesteld niet Nederlander.
     */
    VR00006a(null, "Afgeleide beeindiging Vastgesteld niet Nederlander.", null),
    /**
     * VR00007: Verwerking van de groep behandeld als nederlander.
     */
    VR00007(null, "Verwerking van de groep behandeld als nederlander.", null),
    /**
     * VR00007a: Afgeleide beeindiging Behandeld als Nederlander.
     */
    VR00007a(null, "Afgeleide beeindiging Behandeld als Nederlander.", null),
    /**
     * VR00009: Verwerking Groep Naamgebruik.
     */
    VR00009(null, "Verwerken Groep Naamgebruik", null),
    /**
     * VR00009a: Afleiden van de groep naamgebruik.
     */
    VR00009a(null, "Afleiden van de groep Naamgebruik.", null),
    /**
     * VR00010: Verwerkingsregel van de groep geslachtsaanduiding.
     */
    VR00010(null, "Verwerkingsregel van de groep geslachtsaanduiding.", null),
    /**
     * VR00011: Verwerking van de groep geboorte.
     */
    VR00011(null, "Verwerking van de groep geboorte.", null),
    /**
     * VR00012: Verwerking van de groep overlijden.
     */
    VR00012(null, "Verwerking van de groep overlijden.", null),
    /**
     * VR00013: Verwerken Groep Adres.
     */
    VR00013(null, "Verwerken Groep Adres.", null),
    /**
     * VR00013a: Afgeleide registratie Adres van kind bij geboorte.
     */
    VR00013a(null, "Afgeleide registratie Adres van kind bij geboorte", null),
    /**
     * VR00013b: Afgeleide beeindiging Adres bij Emigratie.
     */
    VR00013b(null, "Afgeleide beeindiging Adres bij Emigratie", null),
    /**
     * VR00014b: Afgeleide registratie Bijhoudingsgemeente door Verhuizing.
     */
    VR00014b(null, "Afgeleide registratie Bijhoudingsgemeente door Verhuizing", null),
    /**
     * VR00015: Verwerken Groep Bijhouding.
     */
    VR00015(null, "Verwerken Groep Bijhouding", null),
    /**
     * VR00015a: Afgeleide registratie Bijhoudingsaard door Opschorting.
     */
    VR00015a(null, "Afgeleide registratie Bijhoudingsaard door Opschorting", null),
    /**
     * VR00015b: Afgeleide registratie Bijhouding door Emigratie.
     */
    VR00015b(null, "Afgeleide registratie Bijhouding door Emigratie", null),
    /**
     * VR00015c: Afgeleide registratie Bijhoudingsaard door Immigratie.
     */
    VR00015c(null, "Afgeleide registratie Bijhoudingsaard door Immigratie", null),
    /**
     * VR00016: Verwerken Groep EU verkiezingen.
     */
    VR00016(null, "Verwerken Groep EU verkiezingen", null),
    /**
     * VR00016a: Afgeleide beeindiging Deelname EU verkiezingen door Emigratie.
     */
    VR00016a(null, "De deelname aan EU verkiezingen komt te vervallen.", null),
    /**
     * VR00016b: Afgeleide beeindiging EU verkiezingen door Nederlanderschap.
     */
    VR00016b(null, "Afgeleide beeindiging EU verkiezingen door Nederlanderschap", null),
    /**
     * VR00017: Verwerken Groep Curatele.
     */
    VR00017(null, "Verwerken Groep Curatele", null),
    /**
     * VR00018: Verwerken Groep Gezag derde.
     */
    VR00018(null, "Verwerken Groep Gezag derde", null),
    /**
     * VR00019: Verwerken Groep Belemmering verstrekking reisdocument.
     */
    VR00019(null, "Verwerken Groep Belemmering verstrekking reisdocument", null),
    /**
     * VR00020: Verwerken Groep Reisdocument.
     */
    VR00020(null, "Verwerken Groep Reisdocument", null),
    /**
     * VR00021: Verwerken Groep Verstrekkingsbeperking.
     */
    VR00021(null, "Verwerken Groep Verstrekkingsbeperking", null),
    /**
     * VR00021b: Afgeleide beeindiging Volledige verstrekkingsbeperking vanwege Specifieke.
     */
    VR00021b(null, "De volledige verstrekkingsbeperking wordt door deze bijhouding beëindigd.", null),
    /**
     * VR00022: Verwerken Groep Uitsluiting NL kiesrecht.
     */
    VR00022(null, "Verwerken Groep Uitsluiting NL kiesrecht", null),
    /**
     * VR00024a: Afleiden van de groep inschrijving.
     */
    VR00024a(null, "Afleiden van de groep inschrijving", null),
    /**
     * VR00025: Verwerking van de groep identificatienummers.
     */
    VR00025(null, "Verwerking van de groep identificatienummers.", null),
    /**
     * VR00027a: Afleiden Groep Afgeleid administratief.
     */
    VR00027a(null, "Afleiden Groep Afgeleid administratief.", null),
    /**
     * VR00028: Verwerken Groep Migratie.
     */
    VR00028(null, "Verwerken Groep Migratie", null),
    /**
     * VR00028a: Persoon opschorten naar aanleiding van overlijden.
     */
    VR00028a(null, "Persoon opschorten naar aanleiding van overlijden.", null),
    /**
     * VR00029: Plaatsen van afnemerindicatie tbv leveren.
     */
    VR00029(null, "Verwerken plaatsen afnemerindicatie.", null),
    /**
     * VR00030: Verwijderen van afnemerindicatie tbv leveren.
     */
    VR00030(null, "Verwerken verwijderen afnemerindicatie.", null),
    /**
     * VR00034: Verwerken Groep Specifieke verstrekkingsbeperking.
     */
    VR00034(null, "Verwerken Groep Specifieke verstrekkingsbeperking", null),
    /**
     * VR00034a: Afgeleide beeindiging Specifieke verstrekkingsbeperkingen vanwege Volledige.
     */
    VR00034a(null, "Specifieke verstrekkingsbeperkingen worden door deze bijhouding beëindigd.", null),
    /**
     * VR00041 Protocollering verstrekking op verzoek afnemer.
     */
    VR00041(null, "Protocollering verstrekking op verzoek afnemer.", null),
    /**
     * VR00042 Protocollering verstrekking op administratieve handeling.
     */
    VR00042(null, "Protocollering verstrekking op administratieve handeling.", null),
    /**
     * VR00043 Protocollering verstrekking door systeemactie.
     */
    VR00043(null, "Protocollering verstrekking door systeemactie.", null),
    /**
     * VR00044 Afleiding Datum materieel selectie.
     */
    VR00044(null, "Afleiding Datum materieel selectie.", null),
    /**
     * VR00045 Afleiding Datum aanvang materiele periode resultaat.
     */
    VR00045(null, "Afleiding Datum aanvang materiele periode resultaat.", null),
    /**
     * VR00046 Afleiding Datum einde materiele periode resultaat.
     */
    VR00046(null, "Afleiding Datum einde materiele periode resultaat.", null),
    /**
     * VR00047 Afleiding Datum/tijd aanvang formele periode resultaat.
     */
    VR00047(null, "Afleiding Datum/tijd aanvang formele periode resultaat.", null),
    /**
     * VR00048 Afleiding Datum/tijd einde formele periode resultaat.
     */
    VR00048(null, "Afleiding Datum/tijd einde formele periode resultaat.", null),
    /**
     * VR00049 Autorisatiefilter op Vulbericht (Deze regel is vervallen).
     */
    VR00049(null, "Autorisatiefilter op Vulbericht.", null),
    /**
     * VR00050 Vulling stuurgegevens in uitgaand bericht bij synchroon response op een request.
     */
    VR00050(null, "Vulling stuurgegevens in uitgaand bericht bij synchroon response op een request.", null),
    /**
     * VR00051 Vulling stuurgegevens in uitgaand bericht bij asynchroon leveringsbericht.
     */
    VR00051(null, "Vulling stuurgegevens in uitgaand bericht bij asynchroon leveringsbericht.", null),
    /**
     * VR00052 Autorisatiefilter op Juridische Persoonslijst.
     */
    VR00052(null, "Autorisatiefilter op Juridische Persoonslijst.", null),
    /**
     * VR00053 Vulling berichtarchivering.
     */
    VR00053(null, "Vulling berichtarchivering.", null),
    /**
     * VR00054 Berichtarchivering persoonsreferentie ingaand bericht.
     */
    VR00054(null, "Berichtarchivering persoonsreferentie ingaand bericht.", null),
    /**
     * VR00055 Berichtarchivering persoonsreferentie uitgaand bericht.
     */
    VR00055(null, "Berichtarchivering persoonsreferentie uitgaand bericht.", null),
    /**
     * VR00056 Mutatielevering Persoon nieuw in doelbinding.
     */
    VR00056(null, "Mutatielevering Persoon nieuw in doelbinding.", null),
    /**
     * VR00057 Mutatielevering Bijhouding op persoon in doelbinding.
     */
    VR00057(null, "Mutatielevering Bijhouding op persoon in doelbinding.", null),
    /**
     * VR00059 Autorisatiefilter historie/verantwoording op Vulbericht.
     */
    VR00059(null, "Autorisatiefilter historie/verantwoording op Vulbericht.", null),
    /**
     * VR00060 Autorisatiefilter historie/verantwoording op kennisgevingsbericht.
     */
    VR00060(null, "Autorisatiefilter historie/verantwoording op kennisgevingsbericht.", null),
    /**
     * VR00061 Samenstellen van een kennisgevingsbericht.
     */
    VR00061(null, "Samenstellen van een kennisgevingsbericht.", null),
    /**
     * VR00062 Verwerkingslogica Dienst Attendering.
     */
    VR00062(null, "Verwerkingslogica Dienst Attendering.", null),
    /**
     * VR00063 Automatisch plaatsen afnemerindicatie via leveringsdienst.
     */
    VR00063(null, "Automatisch plaatsen afnemerindicatie via leveringsdienst.", null),
    /**
     * VR00066 Plaatsen van afnemerindicatie door Attendering is mislukt omdat de indicatie reeds bestond voor persoon.
     */
    VR00066(null, "Plaatsen van afnemerindicatie door Attendering is mislukt omdat de indicatie reeds bestond voor "
        + "persoon [{}].", null),
    /**
     * VR00067 Aantal geleverde personen in VULBERICHT / MUTATIEBERICHT.
     */
    VR00067(null, "Aantal geleverde personen in VULBERICHT / MUTATIEBERICHT {}.", null),
    /**
     * VR00071 Logging 'Melding'.
     */
    VR00071(null, "Logging 'Melding'", null),
    /**
     * VR00073 Bepalen verwerkingssoort van groepsvoorkomens.
     */
    VR00073(null, "Bepalen verwerkingssoort van groepsvoorkomens", null),
    /**
     * VR00075 Verplicht leveren van ABO-partij en rechtsgrond.
     */
    VR00075(null, "VR00075 Verplicht leveren van ABO-partij en rechtsgrond", null),
    /**
     * VR00078 Vervallen groepen alleen opnemen bij autorisatie voor formele historie.
     */
    VR00078(null, "Vervallen groepen alleen opnemen bij autorisatie voor formele historie.", null),
    /**
     * VR00079 Een mutatielevering bevat slechts groepen die in de administratieve handeling geraakt zijn plus de identificerende groepen.
     */
    VR00079(null, "Een mutatielevering bevat slechts groepen die in de administratieve handeling geraakt zijn plus de"
        + " identificerende groepen.", null),
    /**
     * VR00081 Leveren van DatumAanvangGeldigheid en DatumEindeGeldigheid mag alleen bij autorisatie op materiele historie.
     */
    VR00081(null, "Leveren van DatumAanvangGeldigheid en DatumEindeGeldigheid mag alleen bij autorisatie op materiele"
        + " historie.", null),
    /**
     * VR00082 Leveren van TijdstipRegistratie en TijdstipVerval mag alleen bij autorisatie voor formele historie.
     */
    VR00082(null, "Leveren van TijdstipRegistratie en TijdstipVerval mag alleen bij autorisatie voor formele historie"
        + ".", null),
    /**
     * VR00083 Leveren van ActieInhoud, ActieAanpassingGeldigheid en ActieVerval mag alleen bij autorisatie voor verantwoordingsinformatie.
     */
    VR00083(null, "Leveren van ActieInhoud, ActieAanpassingGeldigheid en ActieVerval mag alleen bij autorisatie voor"
        + " verantwoordingsinformatie.", null),
    /**
     * VR00086 Alleen Acties die verantwoording vormen voor inhoudelijke groepen meeleveren.
     */
    VR00086(null, "Alleen Acties die verantwoording vormen voor inhoudelijke groepen meeleveren.", null),
    /**
     * VR00087 Alleen de Administratieve handelingen waarnaar daadwerkelijk wordt verwezen als verantwoording leveren.
     */
    VR00087(null, "Alleen de Administratieve handelingen waarnaar daadwerkelijk wordt verwezen als verantwoording "
        + "leveren.", null),
    /**
     * VR00088 Geen lege groepen in een bericht leveren.
     */
    VR00088(null, "Geen lege groepen in een bericht leveren.", null),
    /**
     * VR00089 Geen mutatielevering indien niets gewijzigd voor het Abonnement.
     */
    VR00089(null, "Geen mutatielevering indien niets gewijzigd voor het Abonnement.", null),
    /**
     * VR00090 Sortering verantwoordingsinformatie.
     */
    VR00090(null, "Sortering verantwoordingsinformatie.", null),
    /**
     * VR00091 Logging Vulbericht bevat geen gegevens.
     */
    VR00091(null, "Logging Vulbericht bevat geen gegevens", null),
    /**
     * VR00092 Volgorde van meervoudig voorkomende groepen in een bericht.
     */
    VR00092(null, "Volgorde van meervoudig voorkomende groepen in een bericht.", null),
    /**
     * VR00093 Als afnemer wil ik geen mutatiebericht waarin alleen tijdstip laatste wijziging is aangepast.
     */
    VR00093(null, "Als afnemer wil ik geen mutatiebericht waarin alleen tijdstip laatste wijziging is aangepast.", null),
    /**
     * VR00094 Als stelselbeheerder wil ik dat afnemers alleen de actuele waarde van documenten ontvangen.
     */
    VR00094(null, "Als stelselbeheerder wil ik dat afnemers alleen de actuele waarde van documenten ontvangen.", null),
    /**
     * VR00095 Alleen relevante actieverwijzingen opnemen in mutatiebericht.
     */
    VR00095(null, "Alleen relevante actieverwijzingen opnemen in mutatiebericht", null),
    /**
     * VR00097 Regel om een persoon na een specifieke administratieve handeling te reconstrueren.
     */
    VR00097(null, "Regel om een persoon na een specifieke administratieve handeling te reconstrueren", null),
    /**
     * VR00108 Als afnemer wil ik geen bericht van Attendering met plaatsen afnemerindicatie als er al een afnemerindicatie bestaat, zodat ik geen dubbele
     * notificatie krijg.
     */
    VR00108(null, "Als afnemer wil ik geen bericht van Attendering met plaatsen afnemerindicatie als er al een afnemerindicatie bestaat, zodat ik geen "
        + "dubbele notificatie krijg", null),
    /**
     * VR00109 Loggen resultaat NULL uit evaluatie van expressie Populatiebeperking.
     */
    VR00109(null, "Loggen resultaat NULL uit evaluatie van expressie Populatiebeperking.", null),

    /**
     * VR00111 Synchronisatielevering Elementtabel.
     */
    VR00111(null, "Synchronisatielevering Elementtabel", null),

    /**
     * VR00120 Alleen groepen waarvoor autorisatie bestaat worden geleverd.
     */
    VR00120(null, "Alleen groepen waarvoor autorisatie bestaat worden geleverd.", null),

    /**
     * VR00121 Alleen objecten waarvoor autorisatie bestaat worden geleverd.
     */
    VR00121(null, "Alleen objecten waarvoor autorisatie bestaat worden geleverd.", null),

    /**
     * VR00123 Geslaagd melding bij webservice Onderhouden afnemerindicatie.
     */
    VR00123(null, "Geslaagd melding bij webservice Onderhouden afnemerindicatie.", null),

    /**
     * VR00125 Voorkomen voor levering mutatie niet opnemen in VolledigBericht.
     */
    VR00125(null, "Voorkomen voor levering mutatie niet opnemen in VolledigBericht.", null),

    /**
     * VR00126 Verwerkingslogica Dienst Mutatielevering op Afnemerindicatie.
     */
    VR00126(null, "Verwerkingslogica Dienst Mutatielevering op Afnemerindicatie.", null),

    /**
     * VR00127 Geslaagd melding bij webservice Synchroniseer persoon.
     */
    VR00127(null, "Geslaagd melding bij webservice Synchroniseer persoon.", null),

    /**
     * VR00128 Geslaagd melding bij webservice Synchroniseer stamgegeven.
     */
    VR00128(null, "Geslaagd melding bij webservice Synchroniseer stamgegeven.", null),

    /**
     * VR01002: Verwerking van de groep ouderschap.
     */
    VR01002(null, "Verwerking van de groep ouderschap.", null),

    /**
     * VR01002a: Afgeleide registratie ouderschap.
     */
    VR01002a(null, "Afgeleide registratie ouderschap.", null),

    /**
     * VR01003: Verwerking van de groep ouderlijk gezag.
     */
    VR01003(null, "Verwerking van de groep ouderlijk gezag.", null),

    /**
     * VR02002: Verwerken Groep Standaard Relatie.
     */
    VR02002(null, "Verwerken Groep Standaard Relatie.", null),

    /**
     * VR02002a: Beeindig huwelijk / geregistreerd partnerschap naar aanleiding van overlijden.
     */
    VR02002a(null, "Beeindig huwelijk / geregistreerd partnerschap naar aanleiding van overlijden.", null),

    /**
     * VR02002c: Afgeleide registratie Huwelijk wegens Omzetting.
     */
    VR02002c(null, "Afgeleide registratie Huwelijk wegens Omzetting.", null),

    /**
     * Een verwijzing in een bericht naar een voorkomen van Persoon/Afnemerindicatie dient correct en geldig te zijn.
     */
    BRLV0001(null, "Er bestaat geen geldige afnemerindicatie voor deze persoon binnen de opgegeven leveringsautorisatie.", null),

    /**
     * Een afnemerIndicatie kan slechts eenmaal worden vastgelegd per Persoon/Afnemer/Abonnement combinatie.
     */
    BRLV0003(null, "Er bestaat al een afnemersindicatie voor de opgegeven persoon binnen de leveringsautorisatie.", null),

    /**
     * Leveren van een persoonslijst is alleen mogelijk bij ingeschreven personen.
     */
    BRLV0006(null, "De opgegeven persoon is een niet-ingeschreven persoon. Levering niet mogelijk.", null),

    /**
     * Een verwijzing naar Abonnement moet geldig zijn.
     */
    BRLV0007(null, "De opgegeven leveringsautorisatie bestaat niet.", null),

    /**
     * Er moet een persoon bestaan met het opgegeven Burgerservicenummer.
     */
    BRLV0008(null, "Er is geen persoon geïdentificeerd met het opgegeven burgerservicenummer.", null),

    /**
     * Een BSN mag niet dubbel voorkomen.
     */
    BRLV0009(null, "Er is meer dan één persoon gevonden met het opgegeven burgerservicenummer.", null),

    /**
     * De periode waarvan we historische gegevens van een persoon vragen, moet in het verleden beginnen.
     */
    BRLV0011(null, "Datum aanvang materiele periode mag niet in de toekomst liggen.", null),

    /**
     * Het moment dat we willen stoppen met volgen, moet in de toekomst liggen.
     */
    BRLV0013(null, "Datum einde volgen moet in de toekomst liggen.", null),

    /**
     * De persoon waarbij de partij een afnemersindicatie wil plaatsen moet binnen de populatie van het overkoepelende leveringsautorisatie vallen.
     */
    BRLV0014(null,
        "De persoon valt niet binnen de populatie waarop de afnemer in deze leveringsautorisatie een indicatie mag plaatsen.",
        null),

    /**
     * De afnemerindicatie op de persoon is verlopen.
     */
    @Deprecated
    BRLV0015(null, "De afnemerindicatie op de persoon is verlopen.", null),

    /**
     * De afnemerindicatie op de persoon is verlopen. (voorheen BRLV0015)
     */
    R1314(null, "De afnemerindicatie op de persoon is verlopen.", null),

    /**
     * Een opgegeven leveringsautorisatie moet geldig zijn.
     */
    BRLV0018(null, "De leveringsautorisatie is niet (meer) geldig.", null),

    /**
     * BRLV0022: De gegevens van de opgevraagde persoon mogen niet vervallen zijn
     */
    BRLV0022(null, "Blokkerend: Er is geen geldige persoon met het opgegeven burgerservicenummer.", null),

    /**
     * De persoon waarvoor de partij een synchronisatie verzoekt, moet binnen de doelbinding van de leveringsautorisatie vallen.
     */
    BRLV0023(null, "De opgegeven persoon valt niet te synchroniseren binnen de opgegeven leveringsautorisatie.", null),

    /**
     * BRLV0024: mogelijke soorten stamgegevens.
     */
    BRLV0024(null, "De gevraagde soort stamgegeven is geen soort stamgegeven dat opgevraagd mag worden.", null),

    /**
     * Gebruikt bij mutatie leveringen.
     */
    BRLV0027(
        null,
        "De geleverde persoon valt niet meer binnen de doelbindingspopulatie van de leveringsautorisatie.", null),

    /**
     * Gebruikt bij mutatie leveringen.
     */
    BRLV0028(
        null,
        "De geleverde persoon heeft de doelbindingspopulatie verlaten. Mutatielevering voor deze persoonslijst is gestopt.",
        null),

    /**
     * Het toegang leveringsautorisatie moet geldig zijn.
     */
    BRLV0029(null, "Er is geen geldige toegang tot de leveringsautorisatie.", null),

    /**
     * Bij het plaatsen van een afnemerindicatie mag de persoon geen verstrekkingsbeperking hebbne op de verzoekende partij.
     */
    BRLV0031(null, "De persoon heeft een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden.", null),

    /**
     * Wanneer gegevens van een persoon worden geleverd en de 'Persoon heeft een verstrekkingsbeperking' dan bevat het uitgaande bericht een melding voor
     * die Persoon.
     */
    BRLV0032(null, "Waarschuwing: verstrekkingsbeperking aanwezig.", null),

    /**
     * De persoon waarvoor de partij een synchronisatie verzoekt, moet bij mutatielevering obv doelbinding binnen de leveringsautorisatie binnen de nadere
     * populatiebeperking vallen.
     */
    BRLV0038(null, "De opgegeven persoon valt niet binnen de doelbinding van mutatielevering obv doelbinding.", null),

    /**
     * De persoon waarvoor de partij een synchronisatie verzoekt, moet een persoon zijn waarop de partij een afnemerindicatie heeft.
     */
    BRLV0040(null, "De opgegeven persoon valt niet te synchroniseren binnen de opgegeven leveringsautorisatie.", null),

    /**
     * Voor synchroniseer persoon met de dienst afnemerindicatie of doelbinding aanwezig zijn.
     */
    BRLV0041(null, "Er is geen dienst mutatielevering aanwezig binnen de opgegeven leveringsautorisatie.", null),

    /**
     * BRLV0042 Een persoon wordt alleen geleverd als deze binnen de populatie van de leveringsautorisatie valt.
     */
    BRLV0042(null, "U bent niet geautoriseerd om deze persoon op te vragen.", null),

    /**
     * BRLV0048 Definitie 'Identificerende groep'.
     */
    BRLV0048(null, "Een Identificerende groep is een voorkomen van een groep dat altijd geleverd wordt zodat de Afnemer in staat is om vast te stellen "
        + "welke Persoon het betreft. ", null),

    /**
     * validatie persoon zoekcriteria voor bevragen geefDetailsPersoon voor levering.
     */
    BRBV0001(null, "Van de zoekcriteria Burgerservicenummer, Administratienummer en Technische sleutel moet er precies één zijn gevuld.", null),

    /**
     * zoekingang GeefDetailsPersoon moet één resultaat opleveren.
     */
    BRBV0006(null, "Er is geen persoon gevonden die voldoet aan de opgegeven zoekcriteria.", null),

    /**
     * zoekingang GeefDetailsPersoon mag niet meer dan één resultaat opleveren.
     */
    BRBV0007(null, "Er is meer dan één persoon gevonden die voldoet aan het opgegeven zoekcriterium.", null),

    /**
     * BRBV0043 Peilmoment mag geen onbekende datum zijn.
     */
    BRBV0043(null, "Datum in zoekparameter mag niet geheel of gedeeltelijk onbekend zijn.", null),

    /**
     * R1268 Voor elk ingaand of uitgaand bericht in de BRP wordt een voorkomen van Bericht gearchiveerd.
     */
    R1268(null, "Stel te registreren gegevens berichtarchivering vast", null),

    /**
     * R1319 Markeer gegevens in het persoonsdeel op basis van Onderzoek, zodat deze meegaan in het mutatiebericht.
     */
    R1319(null, "Markeer gegevens in het persoonsdeel op basis van Onderzoek, zodat deze meegaan in het mutatiebericht.", null),

    /**
     * R1320 Bepalen verwerkingssoort van objecten.
     */
    R1320(null, "Bepalen verwerkingssoort van objecten.", null),

    /**
     * R1328 Verwijder 'pre-relatie' gegevens uit persoonslijst.
     */
    R1328(null, "Verwijder pre-relatie gegevens uit persoonslijst", null),

    /**
     * R1335 Automatisch plaatsen afnemerindicaties.
     * <p/>
     * Voor elk Abonnement dat een geldige Dienst met "Plaatsing" bevat geldt :
     * <p/>
     * Indien deze Dienst een leveringsbericht aanmaakt met één of meer Personen, dan maakt deze Dienst per geleverde Persoon tevens een nieuwe Persoon \
     * Afnemerindicatie aan, als die nog niet bestaat:
     */
    R1335(null, "Automatisch bepalen afnemerindicatie via leveringsdienst", null),

    /**
     * R1350 Na de succesvolle registratie van een Persoon \ Afnemerindicatie op een Persoon, wordt een Volledig bericht met die Persoon geleverd aan de
     * betreffende Afnemer.
     */
    R1350(null, "Volledig bericht na plaatsen afnemerindicatie", null),

    /**
     * R1410 Als een afnemer een verzoek doet middels een synchrone service, dan krijgt die afnemer direct na het behandelen van dit verzoek een synchroon
     * responsebericht met het resultaat, waarin is aangegeven dat het verzoek succesvol is verwerkt dan wel welke fouten zijn opgetreden.
     */
    R1410(null, "Een synchroon verzoek wordt beantwoord met een synchroon responsebericht", null),

    /**
     * R1408 Deze verwerkingsregel draagt zorg voor het registreren van de afnemerindicatie, op basis van de aangeleverde te registreren gegevens.
     */
    R1408(null, "Verwerken plaatsen afnemerindicatie", null),

    /**
     * R1544 Begrens het bericht op datum aanvang materiële periode.
     */
    R1544(null, "Begrens het bericht op datum aanvang materiële periode", null),

    /**
     * R2063 Stel Volledig persoon samen
     */
    R2063(null, "Stel Volledig persoon samen", null),

    /**
     * R2051 Actie attributen alleen opnemen als actie voorkomt in verantwoording
     */
    R2051(null, "Actie attributen alleen opnemen als actie voorkomt in verantwoording", null),

    /**
     * R1561 Verwijder 'Gegeven in onderzoek' op basis van verwijdering uit het persoonsdeel.
     */
    R1561(null, "Verwijder 'Gegeven in onderzoek' op basis van verwijdering uit het persoonsdeel", null),

    /**
     * R1562 Verwijder 'Gegeven in onderzoek' op basis van ongeautoriseerde persoonsgegevens.
     */
    R1562(null, "Verwijder 'Gegeven in onderzoek' op basis van ongeautoriseerde persoonsgegevens", null),

    /**
     * R1563 Geen lege onderzoeken leveren.
     */
    R1563(null, "Geen lege onderzoeken leveren", null),

    /**
     * R2065 Onderzoek naar een ontbrekend gegeven niet leveren aan afnemers.
     */
    R2065(null, "Onderzoek naar een ontbrekend gegeven niet leveren aan afnemers", null),

    /**
     * R1613 beschrijft welke gegevens vastgelegd moeten worden bij de protocollering n.a.v. een verstrekking o.b.v. een afnemer verzoek.
     */
    R1613(null, "Vaststelling protocolleringsgegevens bij verstrekking op verzoek afnemer", null),

    /**
     * R1614 beschrijft welke gegevens vastgelegd moeten worden bij de protocollering n.a.v. een.verstrekking o.b.v een adm. handeling.
     */
    R1614(null, "Vaststelling protocolleringsgegevens bij verstrekking op administratieve handeling", null),

    /**
     * R1615 beschrijft welke gegevens vastgelegd moeten worden bij de protocollering n.a.v. een.verstrekking o.b.v een systeemactie.
     */
    R1615(null, "Vaststelling protocolleringsgegevens bij verstrekking door systeemactie", null),

    /**
     * R1978 Als het verzoek van een afnemer tot levering van een volledig bericht via synchronisatie persoon akkoord is bevonden en in gang is gezet, dan
     * wordt de afnemer daar direct van op de hoogte gesteld door een synchroon responsebericht, waarin is aangegeven dat het verzoek om levering in
     * behandeling is genomen.
     */
    R1978(null, "Verstrek synchrone melding bij geslaagd verzoek om synchronisatie persoon", null),

    /**
     * R1979 Als een afnemer of leverancier verzoekt tot levering van een stamtabel via synchronisatie stamgegeven, dan wordt in het antwoordbericht de
     * inhoud van de gevraagde stamtabel retour gegeven (de volledige inhoud, tenzij dat voor die tabel anders is gespecificeerd).
     */
    R1979(null, "Verwerkingslogica synchronisatie stamgegeven", null),

    /**
     * Op een geldig verzoek via de Dienst Synchronisatie persoon volgt het leveren van een Volledig bericht voor die Persoon aan de betreffende Partij
     * (Afnemer).
     */
    R1982(null, "Verzoek synchronisatie persoon triggert het leveren van een VolledigBericht", null),

    /**
     * R1983 Indien het systeem op grond van de dienst Attendering of Selectie een levering met betrekking tot een Persoon zou versturen aan een afnemer,
     * en de 'Persoon heeft een verstrekkingsbeperking voor die afnemer' (zie Definitie 'Persoon heeft een verstrekkingsbeperking voor Partij' (R1342)) dan
     * wordt de betreffende persoon niet geleverd aan de betreffende afnemer.
     */
    R1983(null, "Dienst verstrekt geen gegevens indien er sprake is van een verstrekkingsbeperking voor de afnemer", null),

    /**
     * R1984 Als een Bijhouding van de groep afnemerindicatie op basis van een afnemer verzoek met goed gevolg is afgerond, dan wordt de afnemer daar
     * direct van op de hoogte gesteld door een synchroon responsebericht, waarin is aangegeven dat het plaatsen of verwijderen van de afnemerindicatie is
     * geslaagd.
     */
    R1984(null, "Verstrek synchrone melding bij geslaagd onderhouden van afnemerindicatie", null),

    /**
     * R1985 Als binnen een leveringsdienst een bericht is aangemaakt dan wordt dat als volgt verstuurd: Het bericht wordt digitaal ondertekend met het PKI
     * overheidscertificaat van het Ministerie van BZK. Het bericht wordt afgeleverd op het endpoint van de afnemer, zoals vastgelegd in de Afleverwijze
     * bij de betreffende Toegang leveringsautorisatie.
     */
    R1985(null, "Verzending uitgaand bericht", null),

    /**
     * R1986 VolledigBerichten en MutatieBerichten die worden aangemaakt binnen de synchronisatiediensten Onderhouden afnemerindicatie, Mutatielevering op
     * basis van afnemerindicatie, Mutatielevering op basis van doelbinding, Synchronisatie persoon, Attendering en Selectie dienen gegarandeerd en in de
     * juiste volgorde bij de afnemer afgeleverd te worden.
     */
    R1986(null, "Berichten voor mutatielevering worden gegarandeerd en in de juiste volgorde afgeleverd.", null),

    /**
     * R1987 Op basis van het gecontroleerde en akkoord bevonden verzoek tot plaatsen van een afnemerindiactie, via de dienst Onderhouden afnemerindicatie,
     * worden de te registreren gegevens bepaald (gelijk aan het goedgekeurde invoerbericht) en doorgegeven om geregistreerd te worden door
     * verwerkingsregel R1408 - Verwerken plaatsen afnemerindicatie.
     */
    R1987(null, "Bepaal te registreren gegevens obv. verzoek tot plaatsen afnemerindicatie", null),

    /**
     * R1988 Markeer handeling als geleverd als alle acties succesvol voltooid zijn.
     */
    R1988(null, "Een administratieve handeling is 'geleverd' als alle leveringsberichten die uit die handeling voortvloeien zijn aangemaakt.", null),

    /**
     * R1989 Alleen personen met attributen die waarden bevatten worden geleverd.
     */
    R1989(null, "Alleen personen zonder lege waarden in mutatiebericht", null),

    /**
     * R1990 Als er na toepassing van Autorisatiefilters en alle overige filters voor levering voor een Abonnement geen personen resteren voor opname in
     * een mutatiebericht, dan wordt er GEEN mutatiebericht aangemaakt en geleverd.
     */
    R1990(null, "Alleen mutatielevering als personen opgenomen in mutatiebericht", null),

    /**
     * R1991 De waarde van Kanaal bepaald hoe het bericht verstuurd wordt. Als het Kanaal de waarde 'BRP' heeft wordt het bericht via het BRP koppelvlak
     * Levering naar de afnemer verstuurd. Als het bericht de waarde 'LO3 netwerk' heeft wordt het bericht voor de afnemer klaargezet en kan de afnemer via
     * het LO3 koppelvlak ophalen.
     */
    R1991(null, "Kanaal bepaalt de wijze waarop BRP het bericht verstuurd", null),

    /**
     * R1993 De waarde van Kanaal bepaalt het formaat en de structuur van het bericht. Als het Kanaal de waarde 'BRP' heeft wordt er een BRP-bericht
     * aangemaakt. Als het Kanaal de waarde 'LO3 netwerk' heeft wordt er een GBA-bericht aangemaakt.
     */
    R1993(null, "Kanaal bepaalt bericht formaat en structuur", null),

    /**
     * R1994 De definitie van deze regel wordt beschreven in ISC use case MV UC1001 Verwerken spontane gegevensverstrekking-cyclus.
     */
    R1994(null, "Bepaal type spontane gegevensverstrekking", null),

    /**
     * R1995 geeft aan dat er niet geprotocolleerd mag worden, als het protocolleringsniveau 'Geheim' is.
     */
    R1995(null, "Niet protocolleren als protocollerigsniveau 'Geheim' is", null),

    /**
     * R1996 draagt zorg voor het vastleggen van de protocollering op basis van de ontvangen berichtgegevens.
     */
    R1996(null, "Leg protocollering berichtgegevens vast", null),

    /**
     * R1997 Alle ingaande en uitgaande berichten worden gearchiveerd. Het archiveren van een inkomend bericht gaat vooraf aan de XSD-validatie, dus ook
     * foutieve/niet in behandeling genomen berichten worden gearchiveerd. Het archiveren van een uitgaand bericht vindt plaats nadat het bericht verzonden
     * is, zodat zeker is, dat de verzending heeft plaatsgevonden voordat archivering plaatsvindt.
     */
    R1997(null, "Bepaal te archiveren berichten", null),

    /**
     * R2002 Als stelselbeheerder wil ik dat afnemers geen bijhoudersgegevens ontvangen.
     */
    R2002(null, "Als stelselbeheerder wil ik dat afnemers geen bijhoudersgegevens ontvangen", null),

    /**
     * R2052 De toegang autorisatie is geblokkeerd door de beheerder.
     */
    R2052(null, "De toegang autorisatie is geblokkeerd door de beheerder", null),

    /**
     * R2053 De opgegeven leveringsautorisatie bestaat.
     */
    R2053(null, "De opgegeven leveringsautorisatie bestaat niet", null),
    /**
     * R2056  De dienstbundel van de opgegeven dienst is geblokkeerd door de beheerde.
     */
    R2056(null, "De dienstbundel van de opgegeven dienst is geblokkeerd door de beheerder", null),

    /**
     * R2106 	De handeling is toegestaan voor de toegang bijhoudingsautorisatie.
     */
    R2106(null, "De soort administratieve handeling is niet toegestaan voor deze autorisatie", null),

    /**
     * R2015 Alleen bronnen waarnaar daadwerkelijk wordt verwezen meeleveren.
     */
    R2015(null, "Alleen bronnen waarnaar daadwerkelijk wordt verwezen meeleveren", null),
    R1260(null, "De opgeven leveringsautorisatie bevat niet de opgegeven dienst", null),
    R1261(null, "De opgegeven leveringsautorisatie is niet geldig", null),
    R1262(null, "De gevraagde dienst is niet geldig", null),
    R1263(null, "De opgegeven leveringsautorisatie is geblokkeerd door de beheerder", null),
    R1264(null, "De gevraagde dienst is geblokkeerd door de beheerder", null),
    R1265(null, "Toegang leveringsautorisatie moet geldig zijn", null),
    R1339(null, "Dienst alleen mogelijk indien geen verstrekkingsbeperking voor de verzoekende afnemer aanwezig is", null),

    R1401(null, "Afnemerindicatie bij opgegeven persoon moet bestaan", null),
    R1402(null, "Binnen leveringsautorisatie hoogstens één afnemersindicatie per persoon", null),
    R1406(null, "Een opgegeven datum einde volgen moet in de toekomst liggen", null),
    R1405(null, "Een opgegeven datum aanvang materiële periode mag niet in de toekomst liggen", null),
    R1407(null, "Plaatsen afnemerindicatie alleen mogelijk indien persoon tot de doelgroep van de leveringsautorisatie behoort", null),


    R1257(null, "Authenticatie: Combinatie ondertekenaar en transporteur onjuist", null),
    R1258(null, "De toegang autorisatie is niet geldig", null),
    R2061(null, "Een afnemer mag alleen voor zichzelf een indicatie onderhouden", null),
    /**
     * R1344: Bij Synchronisatie persoon moet de persoon behoren tot de doelgroep van het abonnement
     */
    R1344(null, "Bij Synchronisatie persoon moet de persoon behoren tot de doelgroep van het abonnement", null),
    /**
     * R2122: Transporteur onjuist
     */
    R2121(null, "Authenticatie: Ondertekenaar onjuist", null),
    /**
     * R2122: Transporteur onjuist
     */
    R2122(null, "Authenticatie: Transporteur onjuist", null),

    /**
     * R2120 	Er bestaat toegang  leveringsgautorisatie voor deze Partij en Rol
     */
    R2120(null, "De gebruikte authenticatie is niet bekend", null),

    /**
     * R2130 	De leveringsautorisatie bevat de gevraagde dienst
     */
    R2130(null, "De leveringsautorisatie bevat de gevraagde dienst niet", null),
    /**
     *
     */
    R1347(null,
        "De afnemer moet voor de dienst Synchronisatie persoon ook een leveringsautorisatie hebben voor de dienst 'Mutatielevering op basis van doelbinding' of 'Mutatielevering op basis van afnemerindicatie",
        null),
    /**
     *
     */
    R1346(null, "Geplaatste afnemerindicatie vereist bij dienst Synchronisatie persoon", null),
    R2054(null, "De opgegeven dienst komt niet overeen met het soort bericht", null),

    R2055(null, "De opgegeven dienst bestaat niet", null);

    private final SoortRegel soort;
    private final String     omschrijving;
    private final String     specificatie;
    private final String[]   propertyMeldingPaden;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param soort                Soort voor Regel
     * @param omschrijving         Omschrijving voor Regel
     * @param specificatie         Specificatie voor Regel
     * @param propertyMeldingPaden de property paden naar de attributen die dienen als parameters voor meldingen.
     */
    private Regel(final SoortRegel soort, final String omschrijving, final String specificatie,
        final String... propertyMeldingPaden)
    {
        this.soort = soort;
        this.omschrijving = omschrijving;
        this.specificatie = specificatie;
        this.propertyMeldingPaden = propertyMeldingPaden;
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
     * Retourneert Code van Regel. De code is gelijk aan de enum name()
     *
     * @return Code.
     */
    public String getCode() {
        return name();
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

    /**
     * Retourneert de paden naar de properties die gerapporteerd moeten worden in een melding tekst.
     *
     * @return property pad(en) naar de properties in een Object.
     */
    public String[] getPropertyMeldingPaden() {
        return Arrays.copyOf(propertyMeldingPaden, propertyMeldingPaden.length);
    }

}
