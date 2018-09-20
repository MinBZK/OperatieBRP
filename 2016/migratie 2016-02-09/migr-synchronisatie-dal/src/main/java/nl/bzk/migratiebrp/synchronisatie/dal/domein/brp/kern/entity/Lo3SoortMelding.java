/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;

/**
 * Dit is de lijst met soort meldingen die mogelijk zijn tijdens de conversie. Deze lijst moet mappen met
 * SoortMeldingCode.
 *
 * Om de enumeratie te re-gegereren aan de hand van de database tabel, gebruik het volgende postgreSQL statement: <code>
select format(E'/** {@link SoortMeldingCode#%s}. *\/\n%s((short) %s, SoortMeldingCode.%s, "%s", Lo3CategorieMelding.%s),',
    code, code, s.id, code, oms, replace(upper(c.naam),' ','_'))
from
    verconv.lo3srtmelding s
    left join verconv.lo3categoriemelding c on s.categoriemelding = c.id
order by s.id
</code>
 *
 * Gebruik in psql het commando '\a' om de formatting van de output te beperken.
 *
 * Gebruik in psql het commando '\o <bestandsnaam>' om de uitvoer in een bestand op te slaan.
 *
 * Voer daarna bovenstaand SQL statement uit.
 *
 * LETOP! De velden die een id van 900 of hoger hebben, moeten eerst uit dit bestand gekopieerd worden, om later na
 * generatie opnieuw te worden toegevoegd. Dit geldt ook voor het bestand 3_brp_vulling_dynamisch.sql en
 * brpStamgegevens-verconv.xml
 *
 * @see SoortMeldingCode
 */
@SuppressWarnings("checkstyle:multiplestringliterals")
public enum Lo3SoortMelding implements Enumeratie {

    /** {@link SoortMeldingCode#ELEMENT}. */
    ELEMENT((short) 1, SoortMeldingCode.ELEMENT, "De categorie mag het element niet bevatten", Lo3CategorieMelding.SYNTAX),
    /** {@link SoortMeldingCode#TELETEX}. */
    TELETEX((short) 2, SoortMeldingCode.TELETEX, "Het element bevat tekens die niet in de teletex-set voorkomen", Lo3CategorieMelding.SYNTAX),
    /** {@link SoortMeldingCode#NUMERIEK}. */
    NUMERIEK((short) 3, SoortMeldingCode.NUMERIEK, "Het element bevat niet uitsluitend cijfers", Lo3CategorieMelding.SYNTAX),
    /** {@link SoortMeldingCode#LENGTE}. */
    LENGTE((short) 4, SoortMeldingCode.LENGTE, "Het element heeft een niet toegestane lengte", Lo3CategorieMelding.SYNTAX),
    /** {@link SoortMeldingCode#PRE001}. */
    PRE001((short) 5, SoortMeldingCode.PRE001,
            "Land is altijd gevuld; deze mag gevuld zijn met de standaardwaarde 0000. Het moet een land zijn dat voorkomt in de GBA landentabel.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE002}. */
    PRE002((short) 6, SoortMeldingCode.PRE002,
            "Als het land Nederland is, dan moet de Plaats een gemeentecode bevatten. Het moet een gemeente zijn die voorkomt in de GBA gemeentetabel. "
                                               + "Er mag geen andere dan wel extra informatie in Plaats staan.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE003}. */
    PRE003((short) 7, SoortMeldingCode.PRE003, "Als Gemeente ingevuld is, dan moet het land Nederland zijn.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE004}. */
    PRE004((short) 8, SoortMeldingCode.PRE004,
            "Als Buitenlandse plaats eventueel in combinatie met Buitenlandse regio ingevuld is, dan is het land niet Nederland.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE005}. */
    PRE005((short) 9, SoortMeldingCode.PRE005, "In iedere Persoon categorie (categorie 01 dan wel historische categorie 51) is het element 01.10 gevuld.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE006}. */
    PRE006((short) 10, SoortMeldingCode.PRE006,
            "In het element Voornamen kunnen meerdere namen achter elkaar opgesomd worden. De scheiding tussen namen wordt altijd (en alleen) weergegeven "
                                                + "door middel van minstens één spatie.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE007}. */
    PRE007((short) 11, SoortMeldingCode.PRE007,
            "Als er geboortegegevens zijn, dan moeten in ieder geval het geboorteland en de geboortedatum opgenomen zijn. (dit mogen zogenaamde "
                                                + "standaardwaarden zijn)",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE008}. */
    PRE008((short) 12, SoortMeldingCode.PRE008, "Preconditie PRE001, specifiek voor de LO3-groep 03 Geboorte.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE009}. */
    PRE009((short) 13, SoortMeldingCode.PRE009,
            "Als er overlijdensgegevens zijn, dan moeten in ieder geval het land en de datum opgenomen zijn. (dit mogen zogenaamde standaardwaarden zijn).",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE010}. */
    PRE010((short) 14, SoortMeldingCode.PRE010, "Preconditie PRE001, specifiek voor de LO3-groep 08 Overlijden.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE011}. */
    PRE011((short) 15, SoortMeldingCode.PRE011,
            "Als categorie 12 betrekking heeft op een Nederlands reisdocument, dan moeten in ieder geval 35.10 Soort NL reisdocument, 35.20 Nummer "
                                                + "Nederlands reisdocument, 35.30 Datum uitgifte Nederlands reisdocument, 35.40 Autoriteit van afgifte "
                                                + "Nederlands reisdocument en 35.50 Datum einde geldigheid Nederlands reisdocument opgenomen zijn. "
                                                + "(dit mogen zogenaamde standaardwaarden zijn).",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE012}. */
    PRE012((short) 16, SoortMeldingCode.PRE012,
            "Als er verblijfsrechtgegevens zijn, dan moeten in ieder geval 39.10 Aanduiding verblijfstitel en 39.30 Ingangsdatum verblijfstitel "
                                                + "opgenomen zijn. (dit mogen zogenaamde standaardwaarden zijn)",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE013}. */
    PRE013((short) 17, SoortMeldingCode.PRE013,
            "Buitenlands adres regel 4, Buitenlands adres regel 5 en Buitenlands adres regel 6 wordt gedurende de migratieperiode nog niet gebruikt, "
                                                + "en zijn derhalve ook nooit gevuld.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE014}. */
    PRE014((short) 18, SoortMeldingCode.PRE014,
            "Buitenlands adres regel 1, Buitenlands adres regel 2 en Buitenlands adres regel 3 bevatten gedurende de migratieperiode nooit meer dan 35 "
                                                + "tekens.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE017}. */
    PRE017((short) 19, SoortMeldingCode.PRE017,
            "Als er een actuele indicatie Bijzondere verblijfsrechtelijke positie is, dan moet tenminste 1 nationaliteit opgenomen zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE018}. */
    PRE018((short) 20, SoortMeldingCode.PRE018,
            "In een gevulde (niet lege) categorie Huwelijk/geregistreerd partnerschap (05 of 55) moet 05.15.10 Soort verbintenis opgenomen zijn. Er is in "
                                                + "dit geval uitsluitend sprake van een lege categorie Huwelijk/geregistreerd partnerschap als de volgende "
                                                + "groepen dan wel hun elementen ontbreken: 01,02,03,04,06,07,15.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE019}. */
    PRE019((short) 21, SoortMeldingCode.PRE019,
            "Een afzonderlijke voornaam bevat geen spatie(s). Dit impliceert dat in het attribuut voornaam uit het Objecttype Persoon \\ voornaam "
                                                + "nooit een spatie kan voorkomen.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE020}. */
    PRE020((short) 22, SoortMeldingCode.PRE020, "In een categorie-rij is nooit zowel groep 82 Document als een Groep 81 Akte opgenomen.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE021}. */
    PRE021((short) 23, SoortMeldingCode.PRE021,
            "De combinatie van Voorvoegsel en Scheidingsteken (als deze geen spatie bevat) komt voor in GBA Tabel 36 Voorvoegselstabel. (Zie [GWB-BRP], "
                                                + "Attribuuttype Voorvoegsel)  Als het Scheidingsteken een spatie bevat, komt het Voorvoegsel (dus zonder "
                                                + "de spatie) voor in GBA Tabel 36.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE022}. */
    PRE022((short) 24, SoortMeldingCode.PRE022, "Het voorvoegsel en scheidingsteken zijn beiden gevuld of beiden leeg.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE023}. */
    PRE023((short) 25, SoortMeldingCode.PRE023,
            "Als 64.10 Reden verlies Nederlandse nationaliteit is gevuld, zijn 05.10 Nationaliteit en 63.10 Reden verkrijging Nederlandse nationaliteit "
                                                + "niet gevuld.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE024}. */
    PRE024((short) 26, SoortMeldingCode.PRE024, "Preconditie PRE001, specifiek voor de LO3-groep 06 Huwelijkssluiting.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE025}. */
    PRE025((short) 27, SoortMeldingCode.PRE025, "Preconditie PRE002, specifiek voor de LO3-groep 03 Geboorte.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE026}. */
    PRE026((short) 28, SoortMeldingCode.PRE026, "Preconditie PRE002, specifiek voor de LO3-groep 08 Overlijden.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE027}. */
    PRE027((short) 29, SoortMeldingCode.PRE027, "Preconditie PRE002, specifiek voor de LO3-groep 06 Huwelijkssluiting.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE028}. */
    PRE028((short) 30, SoortMeldingCode.PRE028, "Preconditie PRE001, specifiek voor de LO3-groep 07 Huwelijksontbinding.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE029}. */
    PRE029((short) 31, SoortMeldingCode.PRE029, "Preconditie PRE002, specifiek voor de LO3-groep 07 Huwelijksontbinding.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE030}. */
    PRE030((short) 32, SoortMeldingCode.PRE030,
            "Het element 85.10 Ingangsdatum geldigheid moet gevuld zijn (deze mag de standaardwaarde hebben of gedeeltelijk onbekend zijn).",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE031}. */
    PRE031((short) 33, SoortMeldingCode.PRE031,
            "Het element 86.10 Datum van opneming moet gevuld zijn en mag geen standaardwaarde hebben en mag niet gedeeltelijk onbekend zijn, "
                                                + "daar waar dit element in een categorie is gedefinieerd.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE032}. */
    PRE032((short) 34, SoortMeldingCode.PRE032, "In een LO3-persoonslijst moet categorie 07 Inschrijving aanwezig zijn.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE033}. */
    PRE033((short) 35, SoortMeldingCode.PRE033, "In een LO3-persoonslijst moet categorie 08 Verblijfplaats aanwezig zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE034}. */
    PRE034((short) 36, SoortMeldingCode.PRE034, "Voor iedere categorie-rij uit categorie 01/51 Persoon geldt dat de geslachtsnaam verplicht is.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE035}. */
    PRE035((short) 37, SoortMeldingCode.PRE035,
            "Bij categorie 12 Reisdocument zijn altijd van precies één van de onderstaande groepen elementen ingevuld: <ul> <li>groep 35 Nederlands "
                                                + "reisdocument</li> <li>groep 36 Signalering</li> </ul>",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE036}. */
    PRE036((short) 38, SoortMeldingCode.PRE036,
            "Gemeente van inschrijving en de datum waarop de inschrijving heeft plaatsgevonden moeten gevuld zijn. Dit betreffen de element 09.10 "
                                                + "Gemeente van inschrijving en 09.20 Datum inschrijving uit categorie 08/58 Verblijfsadres.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE037}. */
    PRE037((short) 39, SoortMeldingCode.PRE037,
            "Versienummer en Datum eerste inschrijving GBA moeten gevuld zijn. Dit betreft de elementen 80.10 Versienummer en 68.10 Datum eerste "
                                                + "inschrijving GBA uit categorie 07 Inschrijving.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE038}. */
    PRE038((short) 40, SoortMeldingCode.PRE038,
            "De Datumtijdstempel moet gevuld zijn. Dit betreft element 80.20 Datumtijdstempel uit categorie 07 Inschrijving",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE039}. */
    PRE039((short) 41, SoortMeldingCode.PRE039,
            "Als bij een Huwelijk/geregistreerd partnerschap (categorie 05/55) gegevens uit de LO3-groep 01, 03 en/of 04 gevuld zijn, dan moet de "
                                                + "Geslachtsnaam ook gevuld zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE040}. */
    PRE040((short) 42, SoortMeldingCode.PRE040,
            "Als er gegevens met betrekking tot de ontbinding zijn gevuld, dan mogen er geen gegevens met betrekking tot de sluiting zijn gevuld, "
                                                + "en vice versa. Dit betekent dat de LO3-groepen 06 Huwelijkssluiting/aangaan geregistreerd partnerschap "
                                                + "en 07 Ontbinding huwelijk/geregistreerd partnerschap niet in dezelfde categorie-rij mogen voorkomen.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE041}. */
    PRE041((short) 43, SoortMeldingCode.PRE041,
            "Als er gegevens met betrekking tot de ontbinding of sluiting zijn gevuld, dan moet de geslachtsnaam ook gevuld zijn. Dit betekent "
                                                + "dat als de LO3-groep 06 Huwelijkssluiting/aangaan geregistreerd partnerschap of 07 Ontbinding "
                                                + "huwelijk/geregistreerd partnerschap voorkomt, dat ook het element 02.40 Geslachtsnaam echtgenoot/"
                                                + "geregistreerd partner gevuld moet zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE042}. */
    PRE042((short) 44, SoortMeldingCode.PRE042,
            "Als 15.10 Soort verbintenis is gevuld, dan mag deze uitsluitend gevuld zijn met ‘H’ of ‘P’. NB: dit betekent dat ook de standaardwaarde ‘.’ "
                                                + "niet wordt toegestaan.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE043}. */
    PRE043((short) 45, SoortMeldingCode.PRE043, "In een BRP-persoonslijst is altijd een actuele geslachtsnaam opgenomen.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE044}. */
    PRE044((short) 46, SoortMeldingCode.PRE044, "In een BRP-persoonslijst moeten actuele geboortegegevens opgenomen zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE045}. */
    PRE045((short) 47, SoortMeldingCode.PRE045, "In een BRP-persoonslijst moet een actuele Adres opgenomen zijn.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE046}. */
    PRE046((short) 48, SoortMeldingCode.PRE046, "In een BRP-persoonslijst moet een actuele Administratienummer opgenomen zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE047}. */
    PRE047((short) 49, SoortMeldingCode.PRE047, "In een LO3-persoonslijst moet categorie 01 Persoon aanwezig zijn.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE048}. */
    PRE048((short) 50, SoortMeldingCode.PRE048,
            "Als bij een Kind (categorie 09/59) gegevens uit de LO3-groep 01 en/of 03 gevuld zijn, dan moet de Geslachtsnaam ook gevuld zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE049}. */
    PRE049((short) 51, SoortMeldingCode.PRE049,
            "Als bij een Ouder (categorie 02/52 en categorie 03/53) gegevens uit de LO3-groep 01, 03, 04 en/of 62 gevuld zijn, dan moet de "
                                                + "Geslachtsnaam ook gevuld zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE050}. */
    PRE050((short) 52, SoortMeldingCode.PRE050,
            "Als er een lege categorie-rij of het verlies van de Nederlandse nationaliteit voorkomt in een stapel (een actuele dan wel een historische "
                                                + "categorie-rij), dan moet er ook een gevulde categorie-rij zijn, met 86.10 Datum van opneming die "
                                                + "hetzelfde of ouder is en 85.10 Datum geldigheid die hetzelfde of ouder is. Categorie 02, 03, 05 en 06 "
                                                + "vormen hierop een uitzondering.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE051}. */
    PRE051((short) 53, SoortMeldingCode.PRE051,
            "Binnen één categorie 04 Nationaliteit-stapel komt altijd maar 1 nationaliteitcode voor, ook in de onjuiste categorie-rijen. Daarnaast mag de "
                                                + "nationaliteitcode wel leeg zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE054}. */
    PRE054((short) 55, SoortMeldingCode.PRE054,
            "Als er gegevens aan de hand van een conversietabel vertaald moeten worden (denk bijvoorbeeld aan de codering van adellijke titels en "
                                                + "predikaten), dan moet de desbetreffende waarde voorkomen in de conversietabel, of het gegeven moet niet "
                                                + "ingevuld zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE055}. */
    PRE055((short) 56, SoortMeldingCode.PRE055, "De actuele categorie mag niet onjuist zijn.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE056}. */
    PRE056((short) 57, SoortMeldingCode.PRE056,
            "Als er een lege categorie-rij is, moet de datum geldigheid van die categorie-rij ook voorkomen in een gevulde, onjuiste categorie-rij die "
                                                + "een eerdere of gelijke datum van opneming heeft. Dit geldt voor de volgende categorieën: categorie "
                                                + "05/55 Huwelijk/geregistreerd partnerschap; categorie 06/56 Overlijden",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE057}. */
    PRE057((short) 58, SoortMeldingCode.PRE057,
            "In geval van een Nederlands verblijfadres moet 11.70 Woonplaatsnaam een bestaande (actuele danwel historische) woonplaats zijn. Kortom het "
                                                + "moet een in BAG voorkomende naam van een woonplaats bevatten. Dit betekent dat de letterlijke vulling "
                                                + "van 11.70 Woonplaatsnaam moet voorkomen in de BRP stamtabel Plaats. Uitzondering hierop is de "
                                                + "standaardwaarde ‘.’. Dit wordt behandeld als <geen waarde>",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE058}. */
    PRE058((short) 59, SoortMeldingCode.PRE058,
            "Bij een persoon kunnen de volgende twee indicaties niet gelijktijdig geldig zijn: 'Vastgesteld niet Nederlander?' en 'Behandeld als "
                                                + "Nederlander'.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE059}. */
    PRE059((short) 60, SoortMeldingCode.PRE059,
            "In de BRP mogen niet gelijktijdig twee ouders en een derde gezag hebben. Dit betekent dat de volgende drie attributen niet gelijktijdig "
                                                + "de waarde ‘Ja’ mogen bevatten: <ul> <li>Ouder1 heeft gezag</li> <li>Ouder2 heeft gezag</li> <li>Derde "
                                                + "heeft gezag</li> </ul>",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE060}. */
    PRE060((short) 61, SoortMeldingCode.PRE060,
            "Een (actueel) A-nummer in categorie 01 mag niet voorkomen als actueel A-nummer op een andere persoonslijst. NB dit kan nu al niet voorkomen "
                                                + "in de GBA-V.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE061}. */
    PRE061((short) 62, SoortMeldingCode.PRE061, "Een (actueel) BSN in categorie 01 mag niet voorkomen als actueel BSN op een andere persoonslijst.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE062}. */
    PRE062((short) 63, SoortMeldingCode.PRE062,
            "Bij een relatie naar een andere persoon (categorieën 02/52, 03/53, 05/55, 09/59) mogen het A-nummer en het BSN niet voorkomen als actueel "
                                                + "A-nummer of actueel BSN op meer dan één (1) persoonslijst.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE064}. */
    PRE064((short) 64, SoortMeldingCode.PRE064,
            "Voor categorie 02/52 Ouder1, 03/53 Ouder2, 05/55 Huwelijk/geregistreerd partnerschap en 09/59 Kind geldt dat als groep 02 voorkomt, "
                                                + "dat geslachtsnaam verplicht is.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE065}. */
    PRE065((short) 65, SoortMeldingCode.PRE065, "In een LO3-persoonslijst moet categorie 02 Ouder1 aanwezig zijn.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE066}. */
    PRE066((short) 66, SoortMeldingCode.PRE066, "In een LO3-persoonslijst moet categorie 03 Ouder2 aanwezig zijn.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE067}. */
    PRE067((short) 67, SoortMeldingCode.PRE067, "Datum document mag geen (gedeeltelijk) onbekend datum bevatten.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE069}. */
    PRE069((short) 68, SoortMeldingCode.PRE069,
            "Als bij een Ouder (categorie 02/52 en categorie 03/53) de Geslachtsnaam gevuld is, dan moet ook 62.10 Datum ingang familierechtelijke "
                                                + "betrekking gevuld zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE070}. */
    PRE070((short) 69, SoortMeldingCode.PRE070,
            "Als groep 82 Document is opgenomen, dan moeten zowel 82.10 Gemeente document, 82.20 Datum document als 82.30 Beschrijving document "
                                                + "openomen zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE071}. */
    PRE071((short) 70, SoortMeldingCode.PRE071,
            "Als groep 81 Akte is opgenomen, dan moet zowel 81.10 Registergemeente akte als 81.20 Aktenummer gevuld zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE073}. */
    PRE073((short) 71, SoortMeldingCode.PRE073,
            "Op een persoonslijst moeten alle actuele kind categorieen (categorie 09) een verschillend A-nummer hebben.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE074}. */
    PRE074((short) 72, SoortMeldingCode.PRE074,
            "Voor een huwelijk/geregistreerd partnerschap stapel (categorie 05/55) geldt dat twee elkaar opvolgende juiste voorkomens, waarbij 15.10 "
                                                + "gelijk is en bij beiden groep 06 aanwezig is, alle elementen uit deze groep 06 gelijk moeten zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE075}. */
    PRE075((short) 73, SoortMeldingCode.PRE075,
            "Voor een huwelijk/geregistreerd partnerschap stapel (categorie 05/55) geldt dat twee elkaar opvolgende juiste voorkomens, "
                                                + "waarbij 15.10 gelijk is en bij beiden groep 07 aanwezig is, alle elementen uit deze groep 07 gelijk "
                                                + "moeten zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE076}. */
    PRE076((short) 74, SoortMeldingCode.PRE076,
            "Als 84.10 Indicatie onjuist dan wel strijdigheid met de openbare orde is gevuld, dan mag deze uitsluitend gevuld zijn met ‘O’ of ‘S’.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE077}. */
    PRE077((short) 75, SoortMeldingCode.PRE077,
            "Als in categorie 07 het element 87.10 PK-gegevens volledig meegeconverteerd is gevuld, dan mag deze uitsluitend gevuld zijn met een 'P'.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE078}. */
    PRE078((short) 76, SoortMeldingCode.PRE078,
            "Als 32.10 Indicatie gezag minderjarige gevuld is, dan mag deze uitsluitend één of een combinatie van de volgende tekens bevatten: "
                                                + "'1', '2', 'D'.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE079}. */
    PRE079((short) 77, SoortMeldingCode.PRE079,
            "Als in categorie 08/58 Verblijfplaats groep 14 voorkomt, dan moeten zowel 14.10 Land vanwaar gevestigd als 14.20 Datum vestiging in "
                                                + "Nederland ingevuld zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE080}. */
    PRE080((short) 78, SoortMeldingCode.PRE080, "In Categorie 08/58 Verblijfplaats moet groep 10 voorkomen of moet groep 13 voorkomen.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE081}. */
    PRE081((short) 79, SoortMeldingCode.PRE081,
            "Als in categorie 08/58 Verblijfplaats groep 13 voorkomt, dan moet 13.10 Land vanwaar vertrokken ingevuld zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE082}. */
    PRE082((short) 80, SoortMeldingCode.PRE082,
            "Er mogen geen nationaliteitstapels zijn die betrekking hebben op dezelfde nationaliteit. Dit betekent dat in de juiste voorkomens "
                                                + "van de verschillen stapels niet dezelfde waarde in 05.10 Nationaliteit mag staan.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE083}. */
    PRE083((short) 81, SoortMeldingCode.PRE083,
            "Als in categorie 04/54 Nationaliteit 63.10 Reden verkrijging Nederlandse nationaliteit gevuld is, dan moet 05.10 Nationaliteit ook "
                                                + "gevuld zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE084}. */
    PRE084((short) 82, SoortMeldingCode.PRE084,
            "Als in categorie 08/58 Verblijfplaats groep 10 voorkomt, dan moet tevens 11.10 Straatnaam of 12.10 Locatieomschrijving ingevuld zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE085}. */
    PRE085((short) 83, SoortMeldingCode.PRE085,
            "Bij een Document is altijd precies één van de volgende twee attributen gevuld: Aktenummer of Omschrijving.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE087}. */
    PRE087((short) 84, SoortMeldingCode.PRE087,
            "Als in categorie 07 groep 67 voorkomt, dan moeten zowel 67.10 Datum opschorting bijhouding als 67.20 Omschrijving reden opschorting "
                                                + "bijhouding ingevuld zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE088}. */
    PRE088((short) 85, SoortMeldingCode.PRE088,
            "Als categorie 05/55 groep 06 voorkomt, dan moet 06.10 Datum Huwelijkssluiting/aangaan geregistreerd partnerschap ingevuld zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE089}. */
    PRE089((short) 86, SoortMeldingCode.PRE089, "Als in categorie 13 groep 38 voorkomt, dan moet 38.10 Aanduiding uitgesloten kiesrecht ingevuld zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE090}. */
    PRE090((short) 87, SoortMeldingCode.PRE090, "Als in categorie 13 groep 31 voorkomt, dan moet 31.10 Aanduiding Europees kiesrecht ingevuld zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE091}. */
    PRE091((short) 88, SoortMeldingCode.PRE091,
            "Als 11.50 Aanduiding bij huisnummer gevuld is, dan mag deze uitsluitend gevuld zijn met een van de volgende waarden: ‘by' (= bij), ‘to’ "
                                                + "(= tegenover).",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE093}. */
    PRE093((short) 90, SoortMeldingCode.PRE093,
            "Er mag geen onjuist voorkomen zijn, die betrekking heeft op dezelfde nationaliteit als een juist dan wel onjuist voorkomen in een andere "
                                                + "stapel. Dit betekent dat in de genoemde voorkomens niet dezelfde waarde in 05.10 Nationaliteit mag "
                                                + "staan.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#AFN001}. */
    AFN001((short) 92, SoortMeldingCode.AFN001, "Afnemersindicaties: a-nummer is verplicht.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#AFN002}. */
    AFN002((short) 93, SoortMeldingCode.AFN002,
            "Afnemersindicaties: Er moet in minimaal 1 categorievoorkomen een afnemerindicatie gevuld zijn en alle gevulde afnemersindicaties in "
                                                + "een stapel moeten gelijk zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#AFN003}. */
    AFN003((short) 94, SoortMeldingCode.AFN003, "Afnemersindicaties: Ingangsdatum mag niet (gedeeltelijk) onbekend zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#AUT001}. */
    AUT001((short) 95, SoortMeldingCode.AUT001, "Autorisaties: Afnemersindicatie is verplicht.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#AUT002}. */
    AUT002((short) 96, SoortMeldingCode.AUT002, "Autorisaties: Ingangsdatum mag niet (gedeeltelijk) onbekend zijn.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#AUT003}. */
    AUT003((short) 97, SoortMeldingCode.AUT003, "Autorisaties: Ingangsdatum is verplicht.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#AUT004}. */
    AUT004((short) 98, SoortMeldingCode.AUT004, "Autorisaties: Afnemersnaam is verplicht.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#AUT005}. */
    AUT005((short) 99, SoortMeldingCode.AUT005, "Autorisaties: Verstrekkingsbeperking is verplicht.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#AUT006}. */
    AUT006((short) 100, SoortMeldingCode.AUT006, "Autorisaties: De einddatum mag niet voor de begindatum liggen.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB001}. */
    BIJZ_CONV_LB001((short) 101, SoortMeldingCode.BIJZ_CONV_LB001, "Er is een geboorte geregistreerd in een onbekend land of een internationaal gebied.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB002}. */
    BIJZ_CONV_LB002((short) 102, SoortMeldingCode.BIJZ_CONV_LB002, "Er is een overlijden geregistreerd in een onbekend land of een internationaal gebied.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB003}. */
    BIJZ_CONV_LB003((short) 103, SoortMeldingCode.BIJZ_CONV_LB003,
            "Er is een huwelijk gesloten/geregistreerd partnerschap aangegaan in een onbekend land of een internationaal gebied.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB004}. */
    BIJZ_CONV_LB004((short) 104, SoortMeldingCode.BIJZ_CONV_LB004,
            "Er is een huwelijk/geregistreerd partnerschap ontbonden in een onbekend land of een internationaal gebied.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB005}. */
    BIJZ_CONV_LB005((short) 105, SoortMeldingCode.BIJZ_CONV_LB005,
            "Het A-nummer is gewijzigd omdat er verschillende personen waren met hetzelfde A-nummer (zie [HUP], par 7.8):.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB006}. */
    BIJZ_CONV_LB006((short) 106, SoortMeldingCode.BIJZ_CONV_LB006,
            "Er is sprake van een dubbele inschrijving, waarbij één persoon met verschillende PL'en is ingeschreven met hetzelfde A-nummer (zie [HUP], "
                                                                   + "par 7.9). Het betreft hier de overblijvende PL.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB007}. */
    BIJZ_CONV_LB007((short) 107, SoortMeldingCode.BIJZ_CONV_LB007,
            "Er is sprake van een dubbele inschrijving, waarbij één persoon met verschillende PL'en is ingeschreven met hetzelfde A-nummer (zie "
                                                                   + "[HUP], par 7.9). Het betreft hier de overbodige/vervallen PL.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB008}. */
    BIJZ_CONV_LB008((short) 108, SoortMeldingCode.BIJZ_CONV_LB008,
            "Er is sprake van een dubbele inschrijving, waarbij één persoon met verschillende PL'en is ingeschreven met verschillende "
                                                                   + "A-nummers (zie [HUP], par 7.10). Het betreft hier de overbodige/vervallen PL.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB009}. */
    BIJZ_CONV_LB009((short) 109, SoortMeldingCode.BIJZ_CONV_LB009, "Er is sprake van een gedeeltelijke verstrekkingsbeperking.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB010}. */
    BIJZ_CONV_LB010((short) 110, SoortMeldingCode.BIJZ_CONV_LB010, "De persoonslijst is opgeschort met reden Onbekend.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB011}. */
    BIJZ_CONV_LB011((short) 111, SoortMeldingCode.BIJZ_CONV_LB011, "De persoonslijst is opgeschort met reden Fout.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB012}. */
    BIJZ_CONV_LB012((short) 112, SoortMeldingCode.BIJZ_CONV_LB012, "De persoon is geprivilegieerd.", Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB013}. */
    BIJZ_CONV_LB013((short) 113, SoortMeldingCode.BIJZ_CONV_LB013, "Er is sprake van een onbekende ouder.", Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB014}. */
    BIJZ_CONV_LB014((short) 114, SoortMeldingCode.BIJZ_CONV_LB014, "Er is sprake van juridisch gezien geen ouder.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB015}. */
    BIJZ_CONV_LB015((short) 115, SoortMeldingCode.BIJZ_CONV_LB015,
            "In één Huwelijk/geregistreerd partnerschap stapel zijn gegevens geregistreerd over zowel een huwelijk als een geregistreerd partnerschap.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB016}. */
    BIJZ_CONV_LB016((short) 116, SoortMeldingCode.BIJZ_CONV_LB016, "In Ouder1 dan wel Ouder2 zijn meerdere personen opgenomen.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB017}. */
    BIJZ_CONV_LB017((short) 117, SoortMeldingCode.BIJZ_CONV_LB017,
            "De geldigheid van het Ouderlijk gezag loopt nog door terwijl de geldigheid van desbetreffende persoon al beëindigd is.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB021}. */
    BIJZ_CONV_LB021((short) 122, SoortMeldingCode.BIJZ_CONV_LB021, "Zwakke adoptie.", Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB022}. */
    BIJZ_CONV_LB022((short) 123, SoortMeldingCode.BIJZ_CONV_LB022, "Gegevens zijn strijdig met de openbare orde.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB024}. */
    BIJZ_CONV_LB024((short) 125, SoortMeldingCode.BIJZ_CONV_LB024,
            "Er is een juiste historische categorie die een recentere ingangsdatum geldigheid heeft dan de actuele categorie.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB025}. */
    BIJZ_CONV_LB025((short) 126, SoortMeldingCode.BIJZ_CONV_LB025,
            "Er is een overlijden geregistreerd waarbij 85.10 Ingangsdatum geldigheid niet gelijk is aan 08.10 Datum overlijden.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB026}. */
    BIJZ_CONV_LB026((short) 127, SoortMeldingCode.BIJZ_CONV_LB026,
            "Er is een signalering verstrekking Nederlands reisdocument geregistreerd waarbij 85.10 Ingangsdatum geldigheid niet gelijk is aan "
                                                                   + "86.10 Datum van opneming.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB027}. */
    BIJZ_CONV_LB027((short) 128, SoortMeldingCode.BIJZ_CONV_LB027, "Bij conversie naar BRP is dit LO3-voorkomen genegeerd.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#STRUC_DATUM}. */
    STRUC_DATUM((short) 130, SoortMeldingCode.STRUC_DATUM, "Element bevat geen geldige datum.", Lo3CategorieMelding.STRUCTUUR),
    /** {@link SoortMeldingCode#STRUC_IDENTIFICATIE}. */
    STRUC_IDENTIFICATIE((short) 131, SoortMeldingCode.STRUC_IDENTIFICATIE, "Identificatienummer voldoet niet aan de inhoudelijke voorwaarden.",
            Lo3CategorieMelding.STRUCTUUR),
    /** {@link SoortMeldingCode#STRUC_VERPLICHT}. */
    STRUC_VERPLICHT((short) 132, SoortMeldingCode.STRUC_VERPLICHT, "Element is verplicht.", Lo3CategorieMelding.STRUCTUUR),
    /** {@link SoortMeldingCode#STRUC_CATEGORIE_7}. */
    STRUC_CATEGORIE_7((short) 133, SoortMeldingCode.STRUC_CATEGORIE_7, "Categorie 07: Inschrijving voldoet niet aan de inhoudelijke voorwaarden.",
            Lo3CategorieMelding.STRUCTUUR),
    /** {@link SoortMeldingCode#STRUC_VERSIENUMMER}. */
    STRUC_VERSIENUMMER((short) 134, SoortMeldingCode.STRUC_VERSIENUMMER, "Element 80.10: Versienummer bevat geen geldige waarde.",
            Lo3CategorieMelding.STRUCTUUR),
    /** {@link SoortMeldingCode#STRUC_CATEGORIE_12}. */
    STRUC_CATEGORIE_12((short) 135, SoortMeldingCode.STRUC_CATEGORIE_12, "Categorie 12: Reisdocument mag alleen actuele categorie voorkomens hebben.",
            Lo3CategorieMelding.STRUCTUUR),
    /** {@link SoortMeldingCode#STRUC_CATEGORIE_13}. */
    STRUC_CATEGORIE_13((short) 136, SoortMeldingCode.STRUC_CATEGORIE_13, "Categorie 13: Kiesrecht mag alleen actuele categorie voorkomens hebben.",
            Lo3CategorieMelding.STRUCTUUR),
    /** {@link SoortMeldingCode#PRE094}. */
    PRE094((short) 901, SoortMeldingCode.PRE094,
            "In Categorie 08/58 Verblijfplaats mag niet zowel groep 13 Adres buitenland als groep 14 Immigratie voorkomen.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE095}. */
    PRE095((short) 902, SoortMeldingCode.PRE095,
            "Buitenlands adres regel 4, Buitenlands adres regel 5 en Buitenlands adres regel 6 wordt gedurende de migratieperiode nog niet gebruikt, "
                                                 + "en zijn derhalve ook nooit gevuld.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE096}. */
    PRE096((short) 903, SoortMeldingCode.PRE096,
            "Buitenlands adres regel 1, Buitenlands adres regel 2 en Buitenlands adres regel 3 bevatten gedurende de migratieperiode nooit meer dan 35 "
                                                 + "tekens.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE097}. */
    PRE097((short) 904, SoortMeldingCode.PRE097,
            "Als groep 71 Verificatie is opgenomen, dan moeten zowel 71.10 Datum verificatie als 71.20 Omschrijving verificatie opgenomen zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE098}. */
    PRE098((short) 905, SoortMeldingCode.PRE098, "Als groep 88 RNI-deelnemer is opgenomen, dan moet 88.10 RNI-deelnemer opgenomen zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE099}. */
    PRE099((short) 906, SoortMeldingCode.PRE099,
            "Als groep 83 Procedure is opgenomen, dan moet zowel 83.10 Aanduiding gegevens in onderzoek als 83.20 Datum ingang onderzoek gevuld zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE100}. */
    PRE100((short) 907, SoortMeldingCode.PRE100, "Als in categorie 08/58 Verblijfplaats groep 10 voorkomt, dan moet 10.10 Functie adres ingevuld zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB028}. */
    BIJZ_CONV_LB028((short) 908, SoortMeldingCode.BIJZ_CONV_LB028,
            "Er is een Adres buitenland geregistreerd waarbij 85.10 Ingangsdatum geldigheid niet gelijk is aan 13.20 Datum aanvang adres buitenland.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB030}. */
    BIJZ_CONV_LB030((short) 909, SoortMeldingCode.BIJZ_CONV_LB030, "Er is een verificatie uitgevoerd zonder dat de RNI-deelnemer geregistreerd is.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB031}. */
    BIJZ_CONV_LB031((short) 910, SoortMeldingCode.BIJZ_CONV_LB031,
            "Er is in categorie 07 een RNI-deelnemer geregistreerd zonder dat er een verificatie is uitgevoerd.", Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB032}. */
    BIJZ_CONV_LB032((short) 911, SoortMeldingCode.BIJZ_CONV_LB032, "Er is in categorie 07 een verdrag geregistreerd.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB033}. */
    BIJZ_CONV_LB033((short) 912, SoortMeldingCode.BIJZ_CONV_LB033, "Bij een buitenlands adres is uitsluitend Regel 1 adres buitenland opgenomen.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB034}. */
    BIJZ_CONV_LB034((short) 913, SoortMeldingCode.BIJZ_CONV_LB034, "In categorie 07 Inschrijving is 66.20 Datum ingang blokkering PL opgenomen.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB035}. */
    BIJZ_CONV_LB035((short) 914, SoortMeldingCode.BIJZ_CONV_LB035,
            "In categorie 01/51 is een ongeldige combinatie van adellijke titel/predicaat en geslachtsaanduiding opgenomen.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB036}. */
    BIJZ_CONV_LB036((short) 915, SoortMeldingCode.BIJZ_CONV_LB036,
            "In categorie 08/58 is Woonplaatsnaam gevuld met standaardwaarde en is minstens een van de volgende elementen niet opgenomen: Naam openbare "
                                                                   + "ruimte, Identificatiecode verblijfplaats of Identificatiecode nummeraanduiding",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#PRE101}. */
    PRE101((short) 916, SoortMeldingCode.PRE101,
            "Als 67.20 Omschrijving reden opschorting bijhouding is gevuld, dan mag deze niet gevuld zijn met de standaardwaarde ‘.’.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE102}. */
    PRE102((short) 917, SoortMeldingCode.PRE102,
            "Als in categorie 08/58 Verblijfplaats groep 10 niet voorkomt, dan mogen groep 11 en groep 12 ook niet voorkomen.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE103}. */
    PRE103((short) 918, SoortMeldingCode.PRE103,
            "Er mogen geen verschillende nationaliteitstapels zijn waarvan de ene stapel betrekking heeft op de Nederlandse nationaliteit en de andere "
                                                 + "stapel op bijzonder Nederlanderschap.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE104}. */
    PRE104((short) 919, SoortMeldingCode.PRE104,
            "Er mag geen onjuist voorkomen zijn, die betrekking heeft op de Nederlandse nationaliteit als in een juist dan wel onjuist voorkomen in een "
                                                 + "andere stapel bijzonder Nederlanderschap voorkomt of vice versa.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE105}. */
    PRE105((short) 920, SoortMeldingCode.PRE105,
            "Als 65.10 Aanduiding  bijzonder Nederlanderschap is gevuld, zijn 05.10 Nationaliteit, 63.10 Reden verkrijging Nederlandse nationaliteit en "
                                                 + "64.10 Reden verlies Nederlandse nationaliteit niet gevuld.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE106}. */
    PRE106((short) 921, SoortMeldingCode.PRE106,
            "Als 05.10 Nationaliteit is gevuld, zijn 64.10 Reden verlies Nederlandse nationaliteit en 65.10 Aanduiding bijzonder Nederlanderschap "
                                                 + "niet gevuld.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#AUT007}. */
    AUT007((short) 922, SoortMeldingCode.AUT007, "Autorisaties: Er kan geen expressie gevonden worden voor de sleutelrubriek.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#AUT008}. */
    AUT008((short) 923, SoortMeldingCode.AUT008, "Autorisaties: Er kan geen (conv.convlo3)rubriek gevonden worden voor de filterrubriek.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB037}. */
    BIJZ_CONV_LB037((short) 924, SoortMeldingCode.BIJZ_CONV_LB037,
            "In categorie 08/58 is Woonplaatsnaam niet opgenomen, maar alle volgende elementen zijn wel opgenomen: Naam openbare ruimte, "
                                                                   + "Identificatiecode verblijfplaats en Identificatiecode nummeraanduiding.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB039}. */
    BIJZ_CONV_LB039((short) 926, SoortMeldingCode.BIJZ_CONV_LB039,
            "Een onderzoek dat in GBA, groep 83 Procedure is opgenomen, heeft niet geleid tot plaatsen van gegevens in onderzoek in de BRP",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#PRE107}. */
    PRE107((short) 927, SoortMeldingCode.PRE107, "Er mogen niet meerdere categorie 12 Reisdocument zijn waarbij groep 36 Signalering is opgenomen.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE108}. */
    PRE108((short) 928, SoortMeldingCode.PRE108,
            "Als aan een Actie meerdere Documenten zijn gekoppeld, dan moet in al deze Documenten dezelfde Partij opgenomen zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE109}. */
    PRE109((short) 929, SoortMeldingCode.PRE109,
            "Er mag geen nationaliteitstapel zijn die betrekking heeft op zowel buitenlandse nationaliteit als op bijzonder Nederlanderschap.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE110}. */
    PRE110((short) 930, SoortMeldingCode.PRE110,
            "Er mag geen onjuist voorkomen zijn, die betrekking heeft op de buitenlandse nationaliteit als in een juist dan wel onjuist voorkomen in deze "
                                                 + "stapel bijzonder Nederlanderschap voorkomt of vice versa.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE111}. */
    PRE111((short) 931, SoortMeldingCode.PRE111,
            "In categorie 08/58 Verblijfsadres moet het element 72.10 Omschrijving van de aangifte adreshouding ingevuld zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE112}. */
    PRE112((short) 936, SoortMeldingCode.PRE112,
            "In categorie 08/58 Verblijfsadres moet het element 72.10 Omschrijving van de aangifte adreshouding ingevuld zijn.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#PRE113}. */
    PRE113((short) 937, SoortMeldingCode.PRE113,
            "Element 01.10 A-nummer uit een niet onjuiste categorie 02/52 Ouder1, 03/53 Ouder2, 05/55 Huwelijk/geregistreerd partnerschap "
                                                 + "en 09/59 Kind mag niet overeenkomen met 01.01.10 A-nummer.",
            Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#BIJZ_CONV_LB040}. */
    BIJZ_CONV_LB040((short) 932, SoortMeldingCode.BIJZ_CONV_LB040, "In categorie 01/51 is Aanduiding naamgebruik niet opgenomen.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#AUT009}. */
    AUT009((short) 933, SoortMeldingCode.AUT009, "Autorisaties: De voorwaarde op aantekening (15.42.10) is omgezet naar een standaard waarde.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#AUT010}. */
    AUT010((short) 934, SoortMeldingCode.AUT010, "Autorisaties: De voorwaarde op blokkering (07.66.20) is omgezet naar een standaard waarde.",
            Lo3CategorieMelding.BIJZONDERE_SITUATIE),
    /** {@link SoortMeldingCode#AUT011}. */
    AUT011((short) 935, SoortMeldingCode.AUT011, "Autorisaties: Einddatum mag niet (gedeeltelijk) onbekend zijn.", Lo3CategorieMelding.PRECONDITIE),
    /** {@link SoortMeldingCode#AUT011}. */
    AUT012((short) 938, SoortMeldingCode.AUT012,
            "Autorisaties: Er mogen niet meerdere lege einddatums binnen een stapel van een autorisatie zijn voor een afnemer.",
            Lo3CategorieMelding.PRECONDITIE);

    /**
     *
     */
    private static final EnumParser<Lo3SoortMelding> PARSER = new EnumParser<>(Lo3SoortMelding.class);
    /**
     *
     */
    private final short id;
    /**
     *
     */
    private final String code;
    /**
     *
     */
    private final String omschrijving;
    /**
     *
     */
    private final Lo3CategorieMelding categorieMelding;

    /**
     * Maak een nieuwe lo3 soort melding.
     *
     * @param meldingId
     *            database ID
     * @param meldingCode
     *            code van de melding
     * @param meldingOmschrijving
     *            Omschrijving van de melding
     * @param meldingCategorie
     *            type melding {@link Lo3CategorieMelding}
     */
    Lo3SoortMelding(
        final short meldingId,
        final SoortMeldingCode meldingCode,
        final String meldingOmschrijving,
        final Lo3CategorieMelding meldingCategorie)
    {
        id = meldingId;
        code = meldingCode.toString();
        omschrijving = meldingOmschrijving;
        categorieMelding = meldingCategorie;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id
     *            parsed de ID naar een {@link Lo3SoortMelding}
     * @return de bijbehorende {@link Lo3SoortMelding}
     */
    public static Lo3SoortMelding parseId(final Short id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     *
     * @param code
     *            parsed de code naar een {@link Lo3SoortMelding}
     * @return de bijbehorende {@link Lo3SoortMelding}
     */
    public static Lo3SoortMelding parseCode(final String code) {
        return PARSER.parseCode(code);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getId()
     */
    @Override
    public short getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * Geef de waarde van omschrijving.
     *
     * @return omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Geef de waarde van categorie melding.
     *
     * @return categorie melding
     */
    public Lo3CategorieMelding getCategorieMelding() {
        return categorieMelding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return true;
    }
}
