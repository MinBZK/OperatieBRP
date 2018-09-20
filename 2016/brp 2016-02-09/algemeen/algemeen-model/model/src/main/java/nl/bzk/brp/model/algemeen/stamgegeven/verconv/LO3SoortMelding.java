/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.verconv;

import javax.annotation.Generated;

/**
 * Categorisatie van LO3 melding.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum LO3SoortMelding {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("-1", "Dummy", null),
    /**
     * De categorie mag het element niet bevatten.
     */
    E_L_E_M_E_N_T("ELEMENT", "De categorie mag het element niet bevatten", LO3CategorieMelding.SYNTAX),
    /**
     * Het element bevat tekens die niet in de teletex-set voorkomen.
     */
    T_E_L_E_T_E_X("TELETEX", "Het element bevat tekens die niet in de teletex-set voorkomen", LO3CategorieMelding.SYNTAX),
    /**
     * Het element bevat niet uitsluitend cijfers.
     */
    N_U_M_E_R_I_E_K("NUMERIEK", "Het element bevat niet uitsluitend cijfers", LO3CategorieMelding.SYNTAX),
    /**
     * Het element heeft een niet toegestane lengte.
     */
    L_E_N_G_T_E("LENGTE", "Het element heeft een niet toegestane lengte", LO3CategorieMelding.SYNTAX),
    /**
     * Land is altijd gevuld; deze mag gevuld zijn met de standaardwaarde 0000. Het moet een land zijn dat voorkomt in
     * de GBA landentabel..
     */
    P_R_E001("PRE001",
            "Land is altijd gevuld; deze mag gevuld zijn met de standaardwaarde 0000. Het moet een land zijn dat voorkomt in de GBA landentabel.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als het land Nederland is, dan moet de Plaats een gemeentecode bevatten. Het moet een gemeente zijn die voorkomt
     * in de GBA gemeentetabel. Er mag geen andere dan wel extra informatie in Plaats staan..
     */
    P_R_E002(
            "PRE002",
            "Als het land Nederland is, dan moet de Plaats een gemeentecode bevatten. Het moet een gemeente zijn die voorkomt in de GBA gemeentetabel. Er mag geen andere dan wel extra informatie in Plaats staan.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als Gemeente ingevuld is, dan moet het land Nederland zijn..
     */
    P_R_E003("PRE003", "Als Gemeente ingevuld is, dan moet het land Nederland zijn.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Als Buitenlandse plaats eventueel in combinatie met Buitenlandse regio ingevuld is, dan is het land niet
     * Nederland..
     */
    P_R_E004("PRE004", "Als Buitenlandse plaats eventueel in combinatie met Buitenlandse regio ingevuld is, dan is het land niet Nederland.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * In iedere Persoon categorie (categorie 01 dan wel historische categorie 51) is het element 01.10 gevuld..
     */
    P_R_E005("PRE005", "In iedere Persoon categorie (categorie 01 dan wel historische categorie 51) is het element 01.10 gevuld.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * In het element Voornamen kunnen meerdere namen achter elkaar opgesomd worden. De scheiding tussen namen wordt
     * altijd (en alleen) weergegeven door middel van minstens één spatie..
     */
    P_R_E006(
            "PRE006",
            "In het element Voornamen kunnen meerdere namen achter elkaar opgesomd worden. De scheiding tussen namen wordt altijd (en alleen) weergegeven door middel van minstens \u00E9\u00E9n spatie.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als er geboortegegevens zijn, dan moeten in ieder geval het geboorteland en de geboortedatum opgenomen zijn. (dit
     * mogen zogenaamde standaardwaarden zijn).
     */
    P_R_E007(
            "PRE007",
            "Als er geboortegegevens zijn, dan moeten in ieder geval het geboorteland en de geboortedatum opgenomen zijn. (dit mogen zogenaamde standaardwaarden zijn)",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Preconditie PRE001, specifiek voor de LO3-groep 03 Geboorte..
     */
    P_R_E008("PRE008", "Preconditie PRE001, specifiek voor de LO3-groep 03 Geboorte.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Als er overlijdensgegevens zijn, dan moeten in ieder geval het land en de datum opgenomen zijn. (dit mogen
     * zogenaamde standaardwaarden zijn)..
     */
    P_R_E009(
            "PRE009",
            "Als er overlijdensgegevens zijn, dan moeten in ieder geval het land en de datum opgenomen zijn. (dit mogen zogenaamde standaardwaarden zijn).",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Preconditie PRE001, specifiek voor de LO3-groep 08 Overlijden..
     */
    P_R_E010("PRE010", "Preconditie PRE001, specifiek voor de LO3-groep 08 Overlijden.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Als categorie 12 betrekking heeft op een Nederlands reisdocument, dan moeten in ieder geval 35.10 Soort NL
     * reisdocument, 35.20 Nummer Nederlands reisdocument, 35.30 Datum uitgifte Nederlands reisdocument, 35.40
     * Autoriteit van afgifte Nederlands reisdocument en 35.50 Datum einde geldigheid Nederlands reisdocument opgenomen
     * zijn. (dit mogen zogenaamde standaardwaarden zijn)..
     */
    P_R_E011(
            "PRE011",
            "Als categorie 12 betrekking heeft op een Nederlands reisdocument, dan moeten in ieder geval 35.10 Soort NL reisdocument, 35.20 Nummer Nederlands reisdocument, 35.30 Datum uitgifte Nederlands reisdocument, 35.40 Autoriteit van afgifte Nederlands reisdocument en 35.50 Datum einde geldigheid Nederlands reisdocument opgenomen zijn. (dit mogen zogenaamde standaardwaarden zijn).",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als er verblijfsrechtgegevens zijn, dan moeten in ieder geval 39.10 Aanduiding verblijfstitel en 39.30
     * Ingangsdatum verblijfstitel opgenomen zijn. (dit mogen zogenaamde standaardwaarden zijn).
     */
    P_R_E012(
            "PRE012",
            "Als er verblijfsrechtgegevens zijn, dan moeten in ieder geval 39.10 Aanduiding verblijfstitel en 39.30 Ingangsdatum verblijfstitel opgenomen zijn. (dit mogen zogenaamde standaardwaarden zijn)",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Buitenlands adres regel 4, Buitenlands adres regel 5 en Buitenlands adres regel 6 wordt gedurende de
     * migratieperiode nog niet gebruikt, en zijn derhalve ook nooit gevuld..
     */
    P_R_E013(
            "PRE013",
            "Buitenlands adres regel 4, Buitenlands adres regel 5 en Buitenlands adres regel 6 wordt gedurende de migratieperiode nog niet gebruikt, en zijn derhalve ook nooit gevuld.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Buitenlands adres regel 1, Buitenlands adres regel 2 en Buitenlands adres regel 3 bevatten gedurende de
     * migratieperiode nooit meer dan 35 tekens..
     */
    P_R_E014(
            "PRE014",
            "Buitenlands adres regel 1, Buitenlands adres regel 2 en Buitenlands adres regel 3 bevatten gedurende de migratieperiode nooit meer dan 35 tekens.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als er een actuele indicatie Bijzondere verblijfsrechtelijke positie is, dan moet tenminste 1 nationaliteit
     * opgenomen zijn..
     */
    P_R_E017("PRE017", "Als er een actuele indicatie Bijzondere verblijfsrechtelijke positie is, dan moet tenminste 1 nationaliteit opgenomen zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * In een gevulde (niet lege) categorie Huwelijk/geregistreerd partnerschap (05 of 55) moet 05.15.10 Soort
     * verbintenis opgenomen zijn. Er is in dit geval uitsluitend sprake van een lege categorie Huwelijk/geregistreerd
     * partnerschap als de volgende groepen dan wel hun elementen ontbreken: 01,02,03,04,06,07,15..
     */
    P_R_E018(
            "PRE018",
            "In een gevulde (niet lege) categorie Huwelijk/geregistreerd partnerschap (05 of 55) moet 05.15.10 Soort verbintenis opgenomen zijn. Er is in dit geval uitsluitend sprake van een lege categorie Huwelijk/geregistreerd partnerschap als de volgende groepen dan wel hun elementen ontbreken: 01,02,03,04,06,07,15.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Een afzonderlijke voornaam bevat geen spatie(s). Dit impliceert dat in het attribuut voornaam uit het Objecttype
     * Persoon \ voornaam nooit een spatie kan voorkomen..
     */
    P_R_E019(
            "PRE019",
            "Een afzonderlijke voornaam bevat geen spatie(s). Dit impliceert dat in het attribuut voornaam uit het Objecttype Persoon \\ voornaam nooit een spatie kan voorkomen.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * In een categorie-rij is nooit zowel groep 82 Document als een Groep 81 Akte opgenomen..
     */
    P_R_E020("PRE020", "In een categorie-rij is nooit zowel groep 82 Document als een Groep 81 Akte opgenomen.", LO3CategorieMelding.PRECONDITIE),
    /**
     * De combinatie van Voorvoegsel en Scheidingsteken (als deze geen spatie bevat) komt voor in GBA Tabel 36
     * Voorvoegselstabel. (Zie [GWB-BRP], Attribuuttype Voorvoegsel) Als het Scheidingsteken een spatie bevat, komt het
     * Voorvoegsel (dus zonder de spatie) voor in GBA Tabel 36..
     */
    P_R_E021(
            "PRE021",
            "De combinatie van Voorvoegsel en Scheidingsteken (als deze geen spatie bevat) komt voor in GBA Tabel 36 Voorvoegselstabel. (Zie [GWB-BRP], Attribuuttype Voorvoegsel)  Als het Scheidingsteken een spatie bevat, komt het Voorvoegsel (dus zonder de spatie) voor in GBA Tabel 36.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Het voorvoegsel en scheidingsteken zijn beiden gevuld of beiden leeg..
     */
    P_R_E022("PRE022", "Het voorvoegsel en scheidingsteken zijn beiden gevuld of beiden leeg.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Als 64.10 Reden verlies Nederlandse nationaliteit is gevuld, zijn 05.10 Nationaliteit en 63.10 Reden verkrijging
     * Nederlandse nationaliteit niet gevuld..
     */
    P_R_E023(
            "PRE023",
            "Als 64.10 Reden verlies Nederlandse nationaliteit is gevuld, zijn 05.10 Nationaliteit en 63.10 Reden verkrijging Nederlandse nationaliteit niet gevuld.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Preconditie PRE001, specifiek voor de LO3-groep 06 Huwelijkssluiting..
     */
    P_R_E024("PRE024", "Preconditie PRE001, specifiek voor de LO3-groep 06 Huwelijkssluiting.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Preconditie PRE002, specifiek voor de LO3-groep 03 Geboorte..
     */
    P_R_E025("PRE025", "Preconditie PRE002, specifiek voor de LO3-groep 03 Geboorte.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Preconditie PRE002, specifiek voor de LO3-groep 08 Overlijden..
     */
    P_R_E026("PRE026", "Preconditie PRE002, specifiek voor de LO3-groep 08 Overlijden.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Preconditie PRE002, specifiek voor de LO3-groep 06 Huwelijkssluiting..
     */
    P_R_E027("PRE027", "Preconditie PRE002, specifiek voor de LO3-groep 06 Huwelijkssluiting.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Preconditie PRE001, specifiek voor de LO3-groep 07 Huwelijksontbinding..
     */
    P_R_E028("PRE028", "Preconditie PRE001, specifiek voor de LO3-groep 07 Huwelijksontbinding.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Preconditie PRE002, specifiek voor de LO3-groep 07 Huwelijksontbinding..
     */
    P_R_E029("PRE029", "Preconditie PRE002, specifiek voor de LO3-groep 07 Huwelijksontbinding.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Het element 85.10 Ingangsdatum geldigheid moet gevuld zijn (deze mag de standaardwaarde hebben of gedeeltelijk
     * onbekend zijn)..
     */
    P_R_E030("PRE030", "Het element 85.10 Ingangsdatum geldigheid moet gevuld zijn (deze mag de standaardwaarde hebben of gedeeltelijk onbekend zijn).",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Het element 86.10 Datum van opneming moet gevuld zijn en mag geen standaardwaarde hebben en mag niet gedeeltelijk
     * onbekend zijn, daar waar dit element in een categorie is gedefinieerd..
     */
    P_R_E031(
            "PRE031",
            "Het element 86.10 Datum van opneming moet gevuld zijn en mag geen standaardwaarde hebben en mag niet gedeeltelijk onbekend zijn, daar waar dit element in een categorie is gedefinieerd.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * In een LO3-persoonslijst moet categorie 07 Inschrijving aanwezig zijn..
     */
    P_R_E032("PRE032", "In een LO3-persoonslijst moet categorie 07 Inschrijving aanwezig zijn.", LO3CategorieMelding.PRECONDITIE),
    /**
     * In een LO3-persoonslijst moet categorie 08 Verblijfplaats aanwezig zijn..
     */
    P_R_E033("PRE033", "In een LO3-persoonslijst moet categorie 08 Verblijfplaats aanwezig zijn.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Voor iedere categorie-rij uit categorie 01/51 Persoon geldt dat de geslachtsnaam verplicht is..
     */
    P_R_E034("PRE034", "Voor iedere categorie-rij uit categorie 01/51 Persoon geldt dat de geslachtsnaam verplicht is.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Bij categorie 12 Reisdocument zijn altijd van precies één van de onderstaande groepen elementen ingevuld:
     * <ul>
     * <li>groep 35 Nederlands reisdocument</li>
     * <li>groep 36 Signalering</li>
     * <li>groep 37 Buitenlands reisdocument.</li>
     * </ul>
     * .
     */
    P_R_E035(
            "PRE035",
            "Bij categorie 12 Reisdocument zijn altijd van precies \u00E9\u00E9n van de onderstaande groepen elementen ingevuld: <ul> <li>groep 35 Nederlands reisdocument</li> <li>groep 36 Signalering</li> <li>groep 37 Buitenlands reisdocument.</li> </ul>",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Gemeente van inschrijving en de datum waarop de inschrijving heeft plaatsgevonden moeten gevuld zijn. Dit
     * betreffen de element 09.10 Gemeente van inschrijving en 09.20 Datum inschrijving uit categorie 08/58
     * Verblijfsadres..
     */
    P_R_E036(
            "PRE036",
            "Gemeente van inschrijving en de datum waarop de inschrijving heeft plaatsgevonden moeten gevuld zijn. Dit betreffen de element 09.10 Gemeente van inschrijving en 09.20 Datum inschrijving uit categorie 08/58 Verblijfsadres.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Versienummer en Datum eerste inschrijving GBA moeten gevuld zijn. Dit betreft de elementen 80.10 Versienummer en
     * 68.10 Datum eerste inschrijving GBA uit categorie 07 Inschrijving..
     */
    P_R_E037(
            "PRE037",
            "Versienummer en Datum eerste inschrijving GBA moeten gevuld zijn. Dit betreft de elementen 80.10 Versienummer en 68.10 Datum eerste inschrijving GBA uit categorie 07 Inschrijving.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * De Datumtijdstempel moet gevuld zijn. Dit betreft element 80.20 Datumtijdstempel uit categorie 07 Inschrijving.
     */
    P_R_E038("PRE038", "De Datumtijdstempel moet gevuld zijn. Dit betreft element 80.20 Datumtijdstempel uit categorie 07 Inschrijving",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als bij een Huwelijk/geregistreerd partnerschap (categorie 05/55) gegevens uit de LO3-groep 01, 03 en/of 04
     * gevuld zijn, dan moet de Geslachtsnaam ook gevuld zijn..
     */
    P_R_E039(
            "PRE039",
            "Als bij een Huwelijk/geregistreerd partnerschap (categorie 05/55) gegevens uit de LO3-groep 01, 03 en/of 04 gevuld zijn, dan moet de Geslachtsnaam ook gevuld zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als er gegevens met betrekking tot de ontbinding zijn gevuld, dan mogen er geen gegevens met betrekking tot de
     * sluiting zijn gevuld, en vice versa. Dit betekent dat de LO3-groepen 06 Huwelijkssluiting/aangaan geregistreerd
     * partnerschap en 07 Ontbinding huwelijk/geregistreerd partnerschap niet in dezelfde categorie-rij mogen
     * voorkomen..
     */
    P_R_E040(
            "PRE040",
            "Als er gegevens met betrekking tot de ontbinding zijn gevuld, dan mogen er geen gegevens met betrekking tot de sluiting zijn gevuld, en vice versa. Dit betekent dat de LO3-groepen 06 Huwelijkssluiting/aangaan geregistreerd partnerschap en 07 Ontbinding huwelijk/geregistreerd partnerschap niet in dezelfde categorie-rij mogen voorkomen.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als er gegevens met betrekking tot de ontbinding of sluiting zijn gevuld, dan moet de geslachtsnaam ook gevuld
     * zijn. Dit betekent dat als de LO3-groep 06 Huwelijkssluiting/aangaan geregistreerd partnerschap of 07 Ontbinding
     * huwelijk/geregistreerd partnerschap voorkomt, dat ook het element 02.40 Geslachtsnaam echtgenoot/geregistreerd
     * partner gevuld moet zijn..
     */
    P_R_E041(
            "PRE041",
            "Als er gegevens met betrekking tot de ontbinding of sluiting zijn gevuld, dan moet de geslachtsnaam ook gevuld zijn. Dit betekent dat als de LO3-groep 06 Huwelijkssluiting/aangaan geregistreerd partnerschap of 07 Ontbinding huwelijk/geregistreerd partnerschap voorkomt, dat ook het element 02.40 Geslachtsnaam echtgenoot/geregistreerd partner gevuld moet zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als 15.10 Soort verbintenis is gevuld, dan mag deze uitsluitend gevuld zijn met ‘H’ of ‘P’. NB: dit betekent dat
     * ook de standaardwaarde ‘.’ niet wordt toegestaan..
     */
    P_R_E042(
            "PRE042",
            "Als 15.10 Soort verbintenis is gevuld, dan mag deze uitsluitend gevuld zijn met \u2018H\u2019 of \u2018P\u2019. NB: dit betekent dat ook de standaardwaarde \u2018.\u2019 niet wordt toegestaan.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * In een BRP-persoonslijst is altijd een actuele geslachtsnaam opgenomen..
     */
    P_R_E043("PRE043", "In een BRP-persoonslijst is altijd een actuele geslachtsnaam opgenomen.", LO3CategorieMelding.PRECONDITIE),
    /**
     * In een BRP-persoonslijst moeten actuele geboortegegevens opgenomen zijn..
     */
    P_R_E044("PRE044", "In een BRP-persoonslijst moeten actuele geboortegegevens opgenomen zijn.", LO3CategorieMelding.PRECONDITIE),
    /**
     * In een BRP-persoonslijst moet een actuele Adres opgenomen zijn..
     */
    P_R_E045("PRE045", "In een BRP-persoonslijst moet een actuele Adres opgenomen zijn.", LO3CategorieMelding.PRECONDITIE),
    /**
     * In een BRP-persoonslijst moet een actuele Administratienummer opgenomen zijn..
     */
    P_R_E046("PRE046", "In een BRP-persoonslijst moet een actuele Administratienummer opgenomen zijn.", LO3CategorieMelding.PRECONDITIE),
    /**
     * In een LO3-persoonslijst moet categorie 01 Persoon aanwezig zijn..
     */
    P_R_E047("PRE047", "In een LO3-persoonslijst moet categorie 01 Persoon aanwezig zijn.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Als bij een Kind (categorie 09/59) gegevens uit de LO3-groep 01 en/of 03 gevuld zijn, dan moet de Geslachtsnaam
     * ook gevuld zijn..
     */
    P_R_E048("PRE048", "Als bij een Kind (categorie 09/59) gegevens uit de LO3-groep 01 en/of 03 gevuld zijn, dan moet de Geslachtsnaam ook gevuld zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als bij een Ouder (categorie 02/52 en categorie 03/53) gegevens uit de LO3-groep 01, 03, 04 en/of 62 gevuld zijn,
     * dan moet de Geslachtsnaam ook gevuld zijn..
     */
    P_R_E049(
            "PRE049",
            "Als bij een Ouder (categorie 02/52 en categorie 03/53) gegevens uit de LO3-groep 01, 03, 04 en/of 62 gevuld zijn, dan moet de Geslachtsnaam ook gevuld zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als er een lege categorie-rij of het verlies van de Nederlandse nationaliteit voorkomt in een stapel (een actuele
     * dan wel een historische categorie-rij), dan moet er ook een gevulde categorie-rij zijn, met 86.10 Datum van
     * opneming die hetzelfde of ouder is en 85.10 Datum geldigheid die hetzelfde of ouder is. Categorie 02, 03, 05 en
     * 06 vormen hierop een uitzondering..
     */
    P_R_E050(
            "PRE050",
            "Als er een lege categorie-rij of het verlies van de Nederlandse nationaliteit voorkomt in een stapel (een actuele dan wel een historische categorie-rij), dan moet er ook een gevulde categorie-rij zijn, met 86.10 Datum van opneming die hetzelfde of ouder is en 85.10 Datum geldigheid die hetzelfde of ouder is. Categorie 02, 03, 05 en 06 vormen hierop een uitzondering.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Binnen één categorie 04 Nationaliteit-stapel komt altijd maar 1 nationaliteitcode voor, ook in de onjuiste
     * categorie-rijen. Daarnaast mag de nationaliteitcode wel leeg zijn..
     */
    P_R_E051(
            "PRE051",
            "Binnen \u00E9\u00E9n categorie 04 Nationaliteit-stapel komt altijd maar 1 nationaliteitcode voor, ook in de onjuiste categorie-rijen. Daarnaast mag de nationaliteitcode wel leeg zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als er gegevens aan de hand van een conversietabel vertaald moeten worden (denk bijvoorbeeld aan de codering van
     * adellijke titels en predikaten), dan moet de desbetreffende waarde voorkomen in de conversietabel, of het gegeven
     * moet niet ingevuld zijn..
     */
    P_R_E054(
            "PRE054",
            "Als er gegevens aan de hand van een conversietabel vertaald moeten worden (denk bijvoorbeeld aan de codering van adellijke titels en predikaten), dan moet de desbetreffende waarde voorkomen in de conversietabel, of het gegeven moet niet ingevuld zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * De actuele categorie mag niet onjuist zijn..
     */
    P_R_E055("PRE055", "De actuele categorie mag niet onjuist zijn.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Als er een lege categorie-rij is, moet de datum geldigheid van die categorie-rij ook voorkomen in een gevulde,
     * onjuiste categorie-rij die een eerdere of gelijke datum van opneming heeft. Dit geldt voor de volgende
     * categorieën: categorie 05/55 Huwelijk/geregistreerd partnerschap; categorie 06/56 Overlijden.
     */
    P_R_E056(
            "PRE056",
            "Als er een lege categorie-rij is, moet de datum geldigheid van die categorie-rij ook voorkomen in een gevulde, onjuiste categorie-rij die een eerdere of gelijke datum van opneming heeft. Dit geldt voor de volgende categorie\u00EBn: categorie 05/55 Huwelijk/geregistreerd partnerschap; categorie 06/56 Overlijden",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * In geval van een Nederlands verblijfadres moet 11.70 Woonplaatsnaam een bestaande (actuele danwel historische)
     * woonplaats zijn. Kortom het moet een in BAG voorkomende naam van een woonplaats bevatten. Dit betekent dat de
     * letterlijke vulling van 11.70 Woonplaatsnaam moet voorkomen in de BRP stamtabel Plaats. Uitzondering hierop is de
     * standaardwaarde ‘.’. Dit wordt behandeld als <geen waarde>.
     */
    P_R_E057(
            "PRE057",
            "In geval van een Nederlands verblijfadres moet 11.70 Woonplaatsnaam een bestaande (actuele danwel historische) woonplaats zijn. Kortom het moet een in BAG voorkomende naam van een woonplaats bevatten. Dit betekent dat de letterlijke vulling van 11.70 Woonplaatsnaam moet voorkomen in de BRP stamtabel Plaats. Uitzondering hierop is de standaardwaarde \u2018.\u2019. Dit wordt behandeld als <geen waarde>",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Bij een persoon kunnen de volgende twee indicaties niet gelijktijdig geldig zijn: 'Vastgesteld niet Nederlander?'
     * en 'Behandeld als Nederlander'..
     */
    P_R_E058(
            "PRE058",
            "Bij een persoon kunnen de volgende twee indicaties niet gelijktijdig geldig zijn: 'Vastgesteld niet Nederlander?' en 'Behandeld als Nederlander'.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * In de BRP mogen niet gelijktijdig twee ouders en een derde gezag hebben. Dit betekent dat de volgende drie
     * attributen niet gelijktijdig de waarde ‘Ja’ mogen bevatten:
     * <ul>
     * <li>Ouder1 heeft gezag</li>
     * <li>Ouder2 heeft gezag</li>
     * <li>Derde heeft gezag</li>
     * </ul>
     * .
     */
    P_R_E059(
            "PRE059",
            "In de BRP mogen niet gelijktijdig twee ouders en een derde gezag hebben. Dit betekent dat de volgende drie attributen niet gelijktijdig de waarde \u2018Ja\u2019 mogen bevatten: <ul> <li>Ouder1 heeft gezag</li> <li>Ouder2 heeft gezag</li> <li>Derde heeft gezag</li> </ul>",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Een (actueel) A-nummer in categorie 01 mag niet voorkomen als actueel A-nummer op een andere persoonslijst. NB
     * dit kan nu al niet voorkomen in de GBA-V..
     */
    P_R_E060(
            "PRE060",
            "Een (actueel) A-nummer in categorie 01 mag niet voorkomen als actueel A-nummer op een andere persoonslijst. NB dit kan nu al niet voorkomen in de GBA-V.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Een (actueel) BSN in categorie 01 mag niet voorkomen als actueel BSN op een andere persoonslijst..
     */
    P_R_E061("PRE061", "Een (actueel) BSN in categorie 01 mag niet voorkomen als actueel BSN op een andere persoonslijst.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Bij een relatie naar een andere persoon (categorieën 02/52, 03/53, 05/55, 09/59) mogen het A-nummer en het BSN
     * niet voorkomen als actueel A-nummer of actueel BSN op meer dan één (1) persoonslijst..
     */
    P_R_E062(
            "PRE062",
            "Bij een relatie naar een andere persoon (categorie\u00EBn 02/52, 03/53, 05/55, 09/59) mogen het A-nummer en het BSN niet voorkomen als actueel A-nummer of actueel BSN op meer dan \u00E9\u00E9n (1) persoonslijst.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Voor categorie 02/52 Ouder1, 03/53 Ouder2, 05/55 Huwelijk/geregistreerd partnerschap en 09/59 Kind geldt dat als
     * groep 02 voorkomt, dat geslachtsnaam verplicht is..
     */
    P_R_E064(
            "PRE064",
            "Voor categorie 02/52 Ouder1, 03/53 Ouder2, 05/55 Huwelijk/geregistreerd partnerschap en 09/59 Kind geldt dat als groep 02 voorkomt, dat geslachtsnaam verplicht is.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * In een LO3-persoonslijst moet categorie 02 Ouder1 aanwezig zijn..
     */
    P_R_E065("PRE065", "In een LO3-persoonslijst moet categorie 02 Ouder1 aanwezig zijn.", LO3CategorieMelding.PRECONDITIE),
    /**
     * In een LO3-persoonslijst moet categorie 03 Ouder2 aanwezig zijn..
     */
    P_R_E066("PRE066", "In een LO3-persoonslijst moet categorie 03 Ouder2 aanwezig zijn.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Datum document mag geen (gedeeltelijk) onbekend datum bevatten..
     */
    P_R_E067("PRE067", "Datum document mag geen (gedeeltelijk) onbekend datum bevatten.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Als bij een Ouder (categorie 02/52 en categorie 03/53) de Geslachtsnaam gevuld is, dan moet ook 62.10 Datum
     * ingang familierechtelijke betrekking gevuld zijn..
     */
    P_R_E069(
            "PRE069",
            "Als bij een Ouder (categorie 02/52 en categorie 03/53) de Geslachtsnaam gevuld is, dan moet ook 62.10 Datum ingang familierechtelijke betrekking gevuld zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als groep 82 Document is opgenomen, dan moeten zowel 82.10 Gemeente document, 82.20 Datum document als 82.30
     * Beschrijving document openomen zijn..
     */
    P_R_E070(
            "PRE070",
            "Als groep 82 Document is opgenomen, dan moeten zowel 82.10 Gemeente document, 82.20 Datum document als 82.30 Beschrijving document openomen zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als groep 81 Akte is opgenomen, dan moet zowel 81.10 Registergemeente akte als 81.20 Aktenummer gevuld zijn..
     */
    P_R_E071("PRE071", "Als groep 81 Akte is opgenomen, dan moet zowel 81.10 Registergemeente akte als 81.20 Aktenummer gevuld zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Op een persoonslijst moeten alle actuele kind categorieen (categorie 09) een verschillend A-nummer hebben..
     */
    P_R_E073("PRE073", "Op een persoonslijst moeten alle actuele kind categorieen (categorie 09) een verschillend A-nummer hebben.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Voor een huwelijk/geregistreerd partnerschap stapel (categorie 05/55) geldt dat twee elkaar opvolgende juiste
     * voorkomens, waarbij 15.10 gelijk is en bij beiden groep 06 aanwezig is, alle elementen uit deze groep 06 gelijk
     * moeten zijn..
     */
    P_R_E074(
            "PRE074",
            "Voor een huwelijk/geregistreerd partnerschap stapel (categorie 05/55) geldt dat twee elkaar opvolgende juiste voorkomens, waarbij 15.10 gelijk is en bij beiden groep 06 aanwezig is, alle elementen uit deze groep 06 gelijk moeten zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Voor een huwelijk/geregistreerd partnerschap stapel (categorie 05/55) geldt dat twee elkaar opvolgende juiste
     * voorkomens, waarbij 15.10 gelijk is en bij beiden groep 07 aanwezig is, alle elementen uit deze groep 07 gelijk
     * moeten zijn..
     */
    P_R_E075(
            "PRE075",
            "Voor een huwelijk/geregistreerd partnerschap stapel (categorie 05/55) geldt dat twee elkaar opvolgende juiste voorkomens, waarbij 15.10 gelijk is en bij beiden groep 07 aanwezig is, alle elementen uit deze groep 07 gelijk moeten zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als 84.10 Indicatie onjuist dan wel strijdigheid met de openbare orde is gevuld, dan mag deze uitsluitend gevuld
     * zijn met ‘O’ of ‘S’..
     */
    P_R_E076(
            "PRE076",
            "Als 84.10 Indicatie onjuist dan wel strijdigheid met de openbare orde is gevuld, dan mag deze uitsluitend gevuld zijn met \u2018O\u2019 of \u2018S\u2019.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als in categorie 07 het element 87.10 PK-gegevens volledig meegeconverteerd is gevuld, dan mag deze uitsluitend
     * gevuld zijn met een 'P'..
     */
    P_R_E077("PRE077",
            "Als in categorie 07 het element 87.10 PK-gegevens volledig meegeconverteerd is gevuld, dan mag deze uitsluitend gevuld zijn met een 'P'.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als 32.10 Indicatie gezag minderjarige gevuld is, dan mag deze uitsluitend één of een combinatie van de volgende
     * tekens bevatten: '1', '2', 'D'..
     */
    P_R_E078(
            "PRE078",
            "Als 32.10 Indicatie gezag minderjarige gevuld is, dan mag deze uitsluitend \u00E9\u00E9n of een combinatie van de volgende tekens bevatten: '1', '2', 'D'.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als in categorie 08/58 Verblijfplaats groep 14 voorkomt, dan moeten zowel 14.10 Land vanwaar gevestigd als 14.20
     * Datum vestiging in Nederland ingevuld zijn..
     */
    P_R_E079(
            "PRE079",
            "Als in categorie 08/58 Verblijfplaats groep 14 voorkomt, dan moeten zowel 14.10 Land vanwaar gevestigd als 14.20 Datum vestiging in Nederland ingevuld zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * In Categorie 08/58 Verblijfplaats moet groep 10 voorkomen of moet groep 13 voorkomen..
     */
    P_R_E080("PRE080", "In Categorie 08/58 Verblijfplaats moet groep 10 voorkomen of moet groep 13 voorkomen.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Als in categorie 08/58 Verblijfplaats groep 13 voorkomt, dan moet 13.10 Land vanwaar vertrokken ingevuld zijn..
     */
    P_R_E081("PRE081", "Als in categorie 08/58 Verblijfplaats groep 13 voorkomt, dan moet 13.10 Land vanwaar vertrokken ingevuld zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Er mogen geen nationaliteitstapels zijn die betrekking hebben op dezelfde nationaliteit. Dit betekent dat in de
     * juiste voorkomens van de verschillen stapels niet dezelfde waarde in 05.10 Nationaliteit mag staan..
     */
    P_R_E082(
            "PRE082",
            "Er mogen geen nationaliteitstapels zijn die betrekking hebben op dezelfde nationaliteit. Dit betekent dat in de juiste voorkomens van de verschillen stapels niet dezelfde waarde in 05.10 Nationaliteit mag staan.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als in categorie 04/54 Nationaliteit 63.10 Reden verkrijging Nederlandse nationaliteit gevuld is, dan moet 05.10
     * Nationaliteit ook gevuld zijn..
     */
    P_R_E083(
            "PRE083",
            "Als in categorie 04/54 Nationaliteit 63.10 Reden verkrijging Nederlandse nationaliteit gevuld is, dan moet 05.10 Nationaliteit ook gevuld zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als in categorie 08/58 Verblijfplaats groep 10 voorkomt, dan moet tevens 11.10 Straatnaam of 12.10
     * Locatieomschrijving ingevuld zijn..
     */
    P_R_E084("PRE084",
            "Als in categorie 08/58 Verblijfplaats groep 10 voorkomt, dan moet tevens 11.10 Straatnaam of 12.10 Locatieomschrijving ingevuld zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Bij een Document is altijd precies één van de volgende twee attributen gevuld: Aktenummer of Omschrijving..
     */
    P_R_E085("PRE085", "Bij een Document is altijd precies \u00E9\u00E9n van de volgende twee attributen gevuld: Aktenummer of Omschrijving.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als in categorie 07 groep 67 voorkomt, dan moeten zowel 67.10 Datum opschorting bijhouding als 67.20 Omschrijving
     * reden opschorting bijhouding ingevuld zijn..
     */
    P_R_E087(
            "PRE087",
            "Als in categorie 07 groep 67 voorkomt, dan moeten zowel 67.10 Datum opschorting bijhouding als 67.20 Omschrijving reden opschorting bijhouding ingevuld zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als categorie 05/55 groep 06 voorkomt, dan moet 06.10 Datum Huwelijkssluiting/aangaan geregistreerd partnerschap
     * ingevuld zijn..
     */
    P_R_E088("PRE088", "Als categorie 05/55 groep 06 voorkomt, dan moet 06.10 Datum Huwelijkssluiting/aangaan geregistreerd partnerschap ingevuld zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als in categorie 13 groep 38 voorkomt, dan moet 38.10 Aanduiding uitgesloten kiesrecht ingevuld zijn..
     */
    P_R_E089("PRE089", "Als in categorie 13 groep 38 voorkomt, dan moet 38.10 Aanduiding uitgesloten kiesrecht ingevuld zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als in categorie 13 groep 31 voorkomt, dan moet 31.10 Aanduiding Europees kiesrecht ingevuld zijn..
     */
    P_R_E090("PRE090", "Als in categorie 13 groep 31 voorkomt, dan moet 31.10 Aanduiding Europees kiesrecht ingevuld zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als 11.50 Aanduiding bij huisnummer gevuld is, dan mag deze uitsluitend gevuld zijn met een van de volgende
     * waarden: ‘by' (= bij), ‘to’ (= tegenover)..
     */
    P_R_E091(
            "PRE091",
            "Als 11.50 Aanduiding bij huisnummer gevuld is, dan mag deze uitsluitend gevuld zijn met een van de volgende waarden: \u2018by' (= bij), \u2018to\u2019 (= tegenover).",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Er mag geen onjuist voorkomen zijn, die betrekking heeft op dezelfde nationaliteit als een juist dan wel onjuist
     * voorkomen in een andere stapel. Dit betekent dat in de genoemde voorkomens niet dezelfde waarde in 05.10
     * Nationaliteit mag staan..
     */
    P_R_E093(
            "PRE093",
            "Er mag geen onjuist voorkomen zijn, die betrekking heeft op dezelfde nationaliteit als een juist dan wel onjuist voorkomen in een andere stapel. Dit betekent dat in de genoemde voorkomens niet dezelfde waarde in 05.10 Nationaliteit mag staan.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Afnemersindicaties: a-nummer is verplicht..
     */
    A_F_N001("AFN001", "Afnemersindicaties: a-nummer is verplicht.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Afnemersindicaties: Er moet in minimaal 1 categorievoorkomen een afnemerindicatie gevuld zijn en alle gevulde
     * afnemersindicaties in een stapel moeten gelijk zijn..
     */
    A_F_N002(
            "AFN002",
            "Afnemersindicaties: Er moet in minimaal 1 categorievoorkomen een afnemerindicatie gevuld zijn en alle gevulde afnemersindicaties in een stapel moeten gelijk zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Afnemersindicaties: Ingangsdatum mag niet (gedeeltelijk) onbekend zijn..
     */
    A_F_N003("AFN003", "Afnemersindicaties: Ingangsdatum mag niet (gedeeltelijk) onbekend zijn.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Autorisaties: Afnemersindicatie is verplicht..
     */
    A_U_T001("AUT001", "Autorisaties: Afnemersindicatie is verplicht.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Autorisaties: Ingangsdatum mag niet (gedeeltelijk) onbekend zijn..
     */
    A_U_T002("AUT002", "Autorisaties: Ingangsdatum mag niet (gedeeltelijk) onbekend zijn.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Autorisaties: Ingangsdatum is verplicht..
     */
    A_U_T003("AUT003", "Autorisaties: Ingangsdatum is verplicht.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Autorisaties: Afnemersnaam is verplicht..
     */
    A_U_T004("AUT004", "Autorisaties: Afnemersnaam is verplicht.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Autorisaties: Verstrekkingsbeperking is verplicht..
     */
    A_U_T005("AUT005", "Autorisaties: Verstrekkingsbeperking is verplicht.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Autorisaties: De einddatum mag niet voor de begindatum liggen..
     */
    A_U_T006("AUT006", "Autorisaties: De einddatum mag niet voor de begindatum liggen.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Er is een geboorte geregistreerd in een onbekend land of een internationaal gebied..
     */
    B_I_J_Z_C_O_N_V_L_B001("BIJZ_CONV_LB001", "Er is een geboorte geregistreerd in een onbekend land of een internationaal gebied.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Er is een overlijden geregistreerd in een onbekend land of een internationaal gebied..
     */
    B_I_J_Z_C_O_N_V_L_B002("BIJZ_CONV_LB002", "Er is een overlijden geregistreerd in een onbekend land of een internationaal gebied.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Er is een huwelijk gesloten/geregistreerd partnerschap aangegaan in een onbekend land of een internationaal
     * gebied..
     */
    B_I_J_Z_C_O_N_V_L_B003("BIJZ_CONV_LB003",
            "Er is een huwelijk gesloten/geregistreerd partnerschap aangegaan in een onbekend land of een internationaal gebied.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Er is een huwelijk/geregistreerd partnerschap ontbonden in een onbekend land of een internationaal gebied..
     */
    B_I_J_Z_C_O_N_V_L_B004("BIJZ_CONV_LB004",
            "Er is een huwelijk/geregistreerd partnerschap ontbonden in een onbekend land of een internationaal gebied.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Het A-nummer is gewijzigd omdat er verschillende personen waren met hetzelfde A-nummer (zie [HUP], par 7.8):..
     */
    B_I_J_Z_C_O_N_V_L_B005("BIJZ_CONV_LB005",
            "Het A-nummer is gewijzigd omdat er verschillende personen waren met hetzelfde A-nummer (zie [HUP], par 7.8):.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Er is sprake van een dubbele inschrijving, waarbij één persoon met verschillende PL'en is ingeschreven met
     * hetzelfde A-nummer (zie [HUP], par 7.9). Het betreft hier de overblijvende PL..
     */
    B_I_J_Z_C_O_N_V_L_B006(
            "BIJZ_CONV_LB006",
            "Er is sprake van een dubbele inschrijving, waarbij \u00E9\u00E9n persoon met verschillende PL'en is ingeschreven met hetzelfde A-nummer (zie [HUP], par 7.9). Het betreft hier de overblijvende PL.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Er is sprake van een dubbele inschrijving, waarbij één persoon met verschillende PL'en is ingeschreven met
     * hetzelfde A-nummer (zie [HUP], par 7.9). Het betreft hier de overbodige/vervallen PL..
     */
    B_I_J_Z_C_O_N_V_L_B007(
            "BIJZ_CONV_LB007",
            "Er is sprake van een dubbele inschrijving, waarbij \u00E9\u00E9n persoon met verschillende PL'en is ingeschreven met hetzelfde A-nummer (zie [HUP], par 7.9). Het betreft hier de overbodige/vervallen PL.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Er is sprake van een dubbele inschrijving, waarbij één persoon met verschillende PL'en is ingeschreven met
     * verschillende A-nummers (zie [HUP], par 7.10). Het betreft hier de overbodige/vervallen PL..
     */
    B_I_J_Z_C_O_N_V_L_B008(
            "BIJZ_CONV_LB008",
            "Er is sprake van een dubbele inschrijving, waarbij \u00E9\u00E9n persoon met verschillende PL'en is ingeschreven met verschillende A-nummers (zie [HUP], par 7.10). Het betreft hier de overbodige/vervallen PL.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Er is sprake van een gedeeltelijke verstrekkingsbeperking..
     */
    B_I_J_Z_C_O_N_V_L_B009("BIJZ_CONV_LB009", "Er is sprake van een gedeeltelijke verstrekkingsbeperking.", LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * De persoonslijst is opgeschort met reden Onbekend..
     */
    B_I_J_Z_C_O_N_V_L_B010("BIJZ_CONV_LB010", "De persoonslijst is opgeschort met reden Onbekend.", LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * De persoonslijst is opgeschort met reden Fout..
     */
    B_I_J_Z_C_O_N_V_L_B011("BIJZ_CONV_LB011", "De persoonslijst is opgeschort met reden Fout.", LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * De persoon is geprivilegieerd..
     */
    B_I_J_Z_C_O_N_V_L_B012("BIJZ_CONV_LB012", "De persoon is geprivilegieerd.", LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Er is sprake van een onbekende ouder..
     */
    B_I_J_Z_C_O_N_V_L_B013("BIJZ_CONV_LB013", "Er is sprake van een onbekende ouder.", LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Er is sprake van juridisch gezien geen ouder..
     */
    B_I_J_Z_C_O_N_V_L_B014("BIJZ_CONV_LB014", "Er is sprake van juridisch gezien geen ouder.", LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * In één Huwelijk/geregistreerd partnerschap stapel zijn gegevens geregistreerd over zowel een huwelijk als een
     * geregistreerd partnerschap..
     */
    B_I_J_Z_C_O_N_V_L_B015(
            "BIJZ_CONV_LB015",
            "In \u00E9\u00E9n Huwelijk/geregistreerd partnerschap stapel zijn gegevens geregistreerd over zowel een huwelijk als een geregistreerd partnerschap.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * In Ouder1 dan wel Ouder2 zijn meerdere personen opgenomen..
     */
    B_I_J_Z_C_O_N_V_L_B016("BIJZ_CONV_LB016", "In Ouder1 dan wel Ouder2 zijn meerdere personen opgenomen.", LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * De geldigheid van het Ouderlijk gezag loopt nog door terwijl de geldigheid van desbetreffende persoon al
     * beëindigd is..
     */
    B_I_J_Z_C_O_N_V_L_B017("BIJZ_CONV_LB017",
            "De geldigheid van het Ouderlijk gezag loopt nog door terwijl de geldigheid van desbetreffende persoon al be\u00EBindigd is.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Zwakke adoptie..
     */
    B_I_J_Z_C_O_N_V_L_B021("BIJZ_CONV_LB021", "Zwakke adoptie.", LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Gegevens zijn strijdig met de openbare orde..
     */
    B_I_J_Z_C_O_N_V_L_B022("BIJZ_CONV_LB022", "Gegevens zijn strijdig met de openbare orde.", LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Er is een juiste historische categorie die een recentere ingangsdatum geldigheid heeft dan de actuele categorie..
     */
    B_I_J_Z_C_O_N_V_L_B024("BIJZ_CONV_LB024",
            "Er is een juiste historische categorie die een recentere ingangsdatum geldigheid heeft dan de actuele categorie.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Er is een overlijden geregistreerd waarbij 85.10 Ingangsdatum geldigheid niet gelijk is aan 08.10 Datum
     * overlijden..
     */
    B_I_J_Z_C_O_N_V_L_B025("BIJZ_CONV_LB025",
            "Er is een overlijden geregistreerd waarbij 85.10 Ingangsdatum geldigheid niet gelijk is aan 08.10 Datum overlijden.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Er is een signalering verstrekking Nederlands reisdocument geregistreerd waarbij 85.10 Ingangsdatum geldigheid
     * niet gelijk is aan 86.10 Datum van opneming..
     */
    B_I_J_Z_C_O_N_V_L_B026(
            "BIJZ_CONV_LB026",
            "Er is een signalering verstrekking Nederlands reisdocument geregistreerd waarbij 85.10 Ingangsdatum geldigheid niet gelijk is aan 86.10 Datum van opneming.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Bij conversie naar BRP is dit LO3-voorkomen genegeerd..
     */
    B_I_J_Z_C_O_N_V_L_B027("BIJZ_CONV_LB027", "Bij conversie naar BRP is dit LO3-voorkomen genegeerd.", LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Deze beëindiging (lege categorie) kan niet gekoppeld worden aan een (nog niet beëindigde) start (gevulde
     * categorie). Categorie wordt genegeerd..
     */
    B_I_J_Z_C_O_N_V_A_F_N007(
            "BIJZ_CONV_AFN007",
            "Deze be\u00EBindiging (lege categorie) kan niet gekoppeld worden aan een (nog niet be\u00EBindigde) start (gevulde categorie). Categorie wordt genegeerd.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Element bevat geen geldige datum..
     */
    S_T_R_U_C_D_A_T_U_M("STRUC_DATUM", "Element bevat geen geldige datum.", LO3CategorieMelding.STRUCTUUR),
    /**
     * Identificatienummer voldoet niet aan de inhoudelijke voorwaarden..
     */
    S_T_R_U_C_I_D_E_N_T_I_F_I_C_A_T_I_E("STRUC_IDENTIFICATIE", "Identificatienummer voldoet niet aan de inhoudelijke voorwaarden.",
            LO3CategorieMelding.STRUCTUUR),
    /**
     * Element is verplicht..
     */
    S_T_R_U_C_V_E_R_P_L_I_C_H_T("STRUC_VERPLICHT", "Element is verplicht.", LO3CategorieMelding.STRUCTUUR),
    /**
     * Categorie 07: Inschrijving voldoet niet aan de inhoudelijke voorwaarden..
     */
    S_T_R_U_C_C_A_T_E_G_O_R_I_E7("STRUC_CATEGORIE_7", "Categorie 07: Inschrijving voldoet niet aan de inhoudelijke voorwaarden.",
            LO3CategorieMelding.STRUCTUUR),
    /**
     * Element 80.10: Versienummer bevat geen geldige waarde..
     */
    S_T_R_U_C_V_E_R_S_I_E_N_U_M_M_E_R("STRUC_VERSIENUMMER", "Element 80.10: Versienummer bevat geen geldige waarde.", LO3CategorieMelding.STRUCTUUR),
    /**
     * Categorie 12: Reisdocument mag alleen actuele categorie voorkomens hebben..
     */
    S_T_R_U_C_C_A_T_E_G_O_R_I_E12("STRUC_CATEGORIE_12", "Categorie 12: Reisdocument mag alleen actuele categorie voorkomens hebben.",
            LO3CategorieMelding.STRUCTUUR),
    /**
     * Categorie 13: Kiesrecht mag alleen actuele categorie voorkomens hebben..
     */
    S_T_R_U_C_C_A_T_E_G_O_R_I_E13("STRUC_CATEGORIE_13", "Categorie 13: Kiesrecht mag alleen actuele categorie voorkomens hebben.",
            LO3CategorieMelding.STRUCTUUR),
    /**
     * Afnemersindicaties: Er kan geen abonnement worden gevonden om deze afnemersindicatie aan te koppelen..
     */
    V_E_R_W_A_F_N001("VERW_AFN001", "Afnemersindicaties: Er kan geen abonnement worden gevonden om deze afnemersindicatie aan te koppelen.",
            LO3CategorieMelding.VERWERKING),
    /**
     * Afnemersindicaties: Er is een fout opgetreden bij het opslaan van de afnemer indicatie. Zie logbestand voor
     * details..
     */
    V_E_R_W_A_F_N002("VERW_AFN002",
            "Afnemersindicaties: Er is een fout opgetreden bij het opslaan van de afnemer indicatie. Zie logbestand voor details.",
            LO3CategorieMelding.VERWERKING),
    /**
     * Meerdere (overlappende) stapels voor 1 afnemer bij persoonslijst. Deze stapel is genegeerd..
     */
    B_I_J_Z_C_O_N_V_A_F_N001("BIJZ_CONV_AFN001", "Meerdere (overlappende) stapels voor 1 afnemer bij persoonslijst. Deze stapel is genegeerd.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Meerdere (overlappende) stapels voor 1 afnemer bij persoon. Deze (meest actuele) stapel is geconverteerd..
     */
    B_I_J_Z_C_O_N_V_A_F_N002("BIJZ_CONV_AFN002",
            "Meerdere (overlappende) stapels voor 1 afnemer bij persoon. Deze (meest actuele) stapel is geconverteerd.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Afnemersindicatie stapel gevonden zonder afnemersindicatie. Stapel wordt genegeerd..
     */
    B_I_J_Z_C_O_N_V_A_F_N003("BIJZ_CONV_AFN003", "Afnemersindicatie stapel gevonden zonder afnemersindicatie. Stapel wordt genegeerd.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Historie binnen stapel is ongeldig. Actuele categorie geeft geen levering aan; volledige stapel wordt genegeerd..
     */
    B_I_J_Z_C_O_N_V_A_F_N004("BIJZ_CONV_AFN004",
            "Historie binnen stapel is ongeldig. Actuele categorie geeft geen levering aan; volledige stapel wordt genegeerd.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Historie binnen stapel is ongeldig. Actuele categorie geeft levering aan; enkel actuele categorie wordt
     * geconverteerd..
     */
    B_I_J_Z_C_O_N_V_A_F_N005("BIJZ_CONV_AFN005",
            "Historie binnen stapel is ongeldig. Actuele categorie geeft levering aan; enkel actuele categorie wordt geconverteerd.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Meerdere (niet overlappende) stapels voor 1 afnemer bij persoon. Stapels zijn samengevoegd..
     */
    B_I_J_Z_C_O_N_V_A_F_N006("BIJZ_CONV_AFN006", "Meerdere (niet overlappende) stapels voor 1 afnemer bij persoon. Stapels zijn samengevoegd.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * In Categorie 08/58 Verblijfplaats mag niet zowel groep 13 Adres buitenland als groep 14 Immigratie voorkomen..
     */
    P_R_E094("PRE094", "In Categorie 08/58 Verblijfplaats mag niet zowel groep 13 Adres buitenland als groep 14 Immigratie voorkomen.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Buitenlands adres regel 4, Buitenlands adres regel 5 en Buitenlands adres regel 6 wordt gedurende de
     * migratieperiode nog niet gebruikt, en zijn derhalve ook nooit gevuld..
     */
    P_R_E095(
            "PRE095",
            "Buitenlands adres regel 4, Buitenlands adres regel 5 en Buitenlands adres regel 6 wordt gedurende de migratieperiode nog niet gebruikt, en zijn derhalve ook nooit gevuld.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Buitenlands adres regel 1, Buitenlands adres regel 2 en Buitenlands adres regel 3 bevatten gedurende de
     * migratieperiode nooit meer dan 35 tekens..
     */
    P_R_E096(
            "PRE096",
            "Buitenlands adres regel 1, Buitenlands adres regel 2 en Buitenlands adres regel 3 bevatten gedurende de migratieperiode nooit meer dan 35 tekens.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als groep 71 Verificatie is opgenomen, dan moeten zowel 71.10 Datum verificatie als 71.20 Omschrijving
     * verificatie opgenomen zijn..
     */
    P_R_E097("PRE097",
            "Als groep 71 Verificatie is opgenomen, dan moeten zowel 71.10 Datum verificatie als 71.20 Omschrijving verificatie opgenomen zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als groep 88 RNI-deelnemer is opgenomen, dan moet 88.10 RNI-deelnemer opgenomen zijn..
     */
    P_R_E098("PRE098", "Als groep 88 RNI-deelnemer is opgenomen, dan moet 88.10 RNI-deelnemer opgenomen zijn.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Als groep 83 Procedure is opgenomen, dan moet zowel 83.10 Aanduiding gegevens in onderzoek als 83.20 Datum ingang
     * onderzoek gevuld zijn..
     */
    P_R_E099("PRE099",
            "Als groep 83 Procedure is opgenomen, dan moet zowel 83.10 Aanduiding gegevens in onderzoek als 83.20 Datum ingang onderzoek gevuld zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als in categorie 08/58 Verblijfplaats groep 10 voorkomt, dan moet 10.10 Functie adres ingevuld zijn..
     */
    P_R_E100("PRE100", "Als in categorie 08/58 Verblijfplaats groep 10 voorkomt, dan moet 10.10 Functie adres ingevuld zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Er is een Adres buitenland geregistreerd waarbij 85.10 Ingangsdatum geldigheid niet gelijk is aan 13.20 Datum
     * aanvang adres buitenland..
     */
    B_I_J_Z_C_O_N_V_L_B028("BIJZ_CONV_LB028",
            "Er is een Adres buitenland geregistreerd waarbij 85.10 Ingangsdatum geldigheid niet gelijk is aan 13.20 Datum aanvang adres buitenland.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Er is een verificatie uitgevoerd zonder dat de RNI-deelnemer geregistreerd is..
     */
    B_I_J_Z_C_O_N_V_L_B030("BIJZ_CONV_LB030", "Er is een verificatie uitgevoerd zonder dat de RNI-deelnemer geregistreerd is.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Er is in categorie 07 een RNI-deelnemer geregistreerd zonder dat er een verificatie is uitgevoerd..
     */
    B_I_J_Z_C_O_N_V_L_B031("BIJZ_CONV_LB031", "Er is in categorie 07 een RNI-deelnemer geregistreerd zonder dat er een verificatie is uitgevoerd.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Er is in categorie 07 een verdrag geregistreerd..
     */
    B_I_J_Z_C_O_N_V_L_B032("BIJZ_CONV_LB032", "Er is in categorie 07 een verdrag geregistreerd.", LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Bij een buitenlands adres is uitsluitend Regel 1 adres buitenland opgenomen..
     */
    B_I_J_Z_C_O_N_V_L_B033("BIJZ_CONV_LB033", "Bij een buitenlands adres is uitsluitend Regel 1 adres buitenland opgenomen.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * In categorie 07 Inschrijving is 66.20 Datum ingang blokkering PL opgenomen..
     */
    B_I_J_Z_C_O_N_V_L_B034("BIJZ_CONV_LB034", "In categorie 07 Inschrijving is 66.20 Datum ingang blokkering PL opgenomen.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * In categorie 01/51 is een ongeldige combinatie van adellijke titel/predicaat en geslachtsaanduiding opgenomen..
     */
    B_I_J_Z_C_O_N_V_L_B035("BIJZ_CONV_LB035",
            "In categorie 01/51 is een ongeldige combinatie van adellijke titel/predicaat en geslachtsaanduiding opgenomen.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * In categorie 08/58 is Woonplaatsnaam gevuld met standaardwaarde en is minstens een van de volgende elementen niet
     * opgenomen: Naam openbare ruimte, Identificatiecode verblijfplaats of Identificatiecode nummeraanduiding.
     */
    B_I_J_Z_C_O_N_V_L_B036(
            "BIJZ_CONV_LB036",
            "In categorie 08/58 is Woonplaatsnaam gevuld met standaardwaarde en is minstens een van de volgende elementen niet opgenomen: Naam openbare ruimte, Identificatiecode verblijfplaats of Identificatiecode nummeraanduiding",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Als 67.20 Omschrijving reden opschorting bijhouding is gevuld, dan mag deze niet gevuld zijn met de
     * standaardwaarde ‘.’..
     */
    P_R_E101("PRE101",
            "Als 67.20 Omschrijving reden opschorting bijhouding is gevuld, dan mag deze niet gevuld zijn met de standaardwaarde \u2018.\u2019.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als in categorie 08/58 Verblijfplaats groep 10 niet voorkomt, dan mogen groep 11 en groep 12 ook niet voorkomen..
     */
    P_R_E102("PRE102", "Als in categorie 08/58 Verblijfplaats groep 10 niet voorkomt, dan mogen groep 11 en groep 12 ook niet voorkomen.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Er mogen geen verschillende nationaliteitstapels zijn waarvan de ene stapel betrekking heeft op de Nederlandse
     * nationaliteit en de andere stapel op bijzonder Nederlanderschap..
     */
    P_R_E103(
            "PRE103",
            "Er mogen geen verschillende nationaliteitstapels zijn waarvan de ene stapel betrekking heeft op de Nederlandse nationaliteit en de andere stapel op bijzonder Nederlanderschap.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Er mag geen onjuist voorkomen zijn, die betrekking heeft op de Nederlandse nationaliteit als in een juist dan wel
     * onjuist voorkomen in een andere stapel bijzonder Nederlanderschap voorkomt of vice versa..
     */
    P_R_E104(
            "PRE104",
            "Er mag geen onjuist voorkomen zijn, die betrekking heeft op de Nederlandse nationaliteit als in een juist dan wel onjuist voorkomen in een andere stapel bijzonder Nederlanderschap voorkomt of vice versa.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als 65.10 Aanduiding bijzonder Nederlanderschap is gevuld, zijn 05.10 Nationaliteit, 63.10 Reden verkrijging
     * Nederlandse nationaliteit en 64.10 Reden verlies Nederlandse nationaliteit niet gevuld..
     */
    P_R_E105(
            "PRE105",
            "Als 65.10 Aanduiding  bijzonder Nederlanderschap is gevuld, zijn 05.10 Nationaliteit, 63.10 Reden verkrijging Nederlandse nationaliteit en 64.10 Reden verlies Nederlandse nationaliteit niet gevuld.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Als 05.10 Nationaliteit is gevuld, zijn 64.10 Reden verlies Nederlandse nationaliteit en 65.10 Aanduiding
     * bijzonder Nederlanderschap niet gevuld..
     */
    P_R_E106(
            "PRE106",
            "Als 05.10 Nationaliteit is gevuld, zijn 64.10 Reden verlies Nederlandse nationaliteit en 65.10 Aanduiding bijzonder Nederlanderschap niet gevuld.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Autorisaties: Er kan geen expressie gevonden worden voor de sleutelrubriek..
     */
    A_U_T007("AUT007", "Autorisaties: Er kan geen expressie gevonden worden voor de sleutelrubriek.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Autorisaties: Er kan geen (conv.convlo3)rubriek gevonden worden voor de filterrubriek..
     */
    A_U_T008("AUT008", "Autorisaties: Er kan geen (conv.convlo3)rubriek gevonden worden voor de filterrubriek.", LO3CategorieMelding.PRECONDITIE),
    /**
     * In categorie 08/58 is Woonplaatsnaam niet opgenomen, maar alle volgende elementen zijn wel opgenomen: Naam
     * openbare ruimte, Identificatiecode verblijfplaats en Identificatiecode nummeraanduiding..
     */
    B_I_J_Z_C_O_N_V_L_B037(
            "BIJZ_CONV_LB037",
            "In categorie 08/58 is Woonplaatsnaam niet opgenomen, maar alle volgende elementen zijn wel opgenomen: Naam openbare ruimte, Identificatiecode verblijfplaats en Identificatiecode nummeraanduiding.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Een onderzoek dat in GBA, groep 83 Procedure is opgenomen, heeft niet geleid tot plaatsen van gegevens in
     * onderzoek in de BRP.
     */
    B_I_J_Z_C_O_N_V_L_B039("BIJZ_CONV_LB039",
            "Een onderzoek dat in GBA, groep 83 Procedure is opgenomen, heeft niet geleid tot plaatsen van gegevens in onderzoek in de BRP",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Er mogen niet meerdere categorie 12 Reisdocument zijn waarbij groep 36 Signalering is opgenomen..
     */
    P_R_E107("PRE107", "Er mogen niet meerdere categorie 12 Reisdocument zijn waarbij groep 36 Signalering is opgenomen.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Als aan een Actie meerdere Documenten zijn gekoppeld, dan moet in al deze Documenten dezelfde Partij opgenomen
     * zijn..
     */
    P_R_E108("PRE108", "Als aan een Actie meerdere Documenten zijn gekoppeld, dan moet in al deze Documenten dezelfde Partij opgenomen zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Er mag geen nationaliteitstapel zijn die betrekking heeft op zowel buitenlandse nationaliteit als op bijzonder
     * Nederlanderschap..
     */
    P_R_E109("PRE109", "Er mag geen nationaliteitstapel zijn die betrekking heeft op zowel buitenlandse nationaliteit als op bijzonder Nederlanderschap.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * Er mag geen onjuist voorkomen zijn, die betrekking heeft op de buitenlandse nationaliteit als in een juist dan
     * wel onjuist voorkomen in deze stapel bijzonder Nederlanderschap voorkomt of vice versa..
     */
    P_R_E110(
            "PRE110",
            "Er mag geen onjuist voorkomen zijn, die betrekking heeft op de buitenlandse nationaliteit als in een juist dan wel onjuist voorkomen in deze stapel bijzonder Nederlanderschap voorkomt of vice versa.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * In categorie 08/58 Verblijfsadres moet het element 72.10 Omschrijving van de aangifte adreshouding ingevuld
     * zijn..
     */
    P_R_E111("PRE111", "In categorie 08/58 Verblijfsadres moet het element 72.10 Omschrijving van de aangifte adreshouding ingevuld zijn.",
            LO3CategorieMelding.PRECONDITIE),
    /**
     * In categorie 01/51 is Aanduiding naamgebruik niet opgenomen..
     */
    B_I_J_Z_C_O_N_V_L_B040("BIJZ_CONV_LB040", "In categorie 01/51 is Aanduiding naamgebruik niet opgenomen.", LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Autorisaties: De voorwaarde op aantekening (15.42.10) is omgezet naar een standaard waarde..
     */
    A_U_T009("AUT009", "Autorisaties: De voorwaarde op aantekening (15.42.10) is omgezet naar een standaard waarde.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Autorisaties: De voorwaarde op blokkering (07.66.20) is omgezet naar een standaard waarde..
     */
    A_U_T010("AUT010", "Autorisaties: De voorwaarde op blokkering (07.66.20) is omgezet naar een standaard waarde.",
            LO3CategorieMelding.BIJZONDERE_SITUATIE),
    /**
     * Autorisaties: Einddatum mag niet (gedeeltelijk) onbekend zijn..
     */
    A_U_T011("AUT011", "Autorisaties: Einddatum mag niet (gedeeltelijk) onbekend zijn.", LO3CategorieMelding.PRECONDITIE),
    /**
     * Als in een stapel alle gevulde voorkomens onjuist zijn, dan moet er minstens één niet-onjuist leeg voorkomen zijn
     * waarvan 85.10 Ingangsdatum geldigheid overeenkomt met 85.10 Ingangsdatum geldigheid van minstens één gevuld
     * voorkomen..
     */
    P_R_E112(
            "PRE112",
            "Als in een stapel alle gevulde voorkomens onjuist zijn, dan moet er minstens \u00E9\u00E9n niet-onjuist leeg voorkomen zijn waarvan 85.10 Ingangsdatum geldigheid overeenkomt met 85.10 Ingangsdatum geldigheid van minstens \u00E9\u00E9n gevuld voorkomen.",
            LO3CategorieMelding.PRECONDITIE);

    private final String code;
    private final String omschrijving;
    private final LO3CategorieMelding categorieMelding;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param code Code voor LO3SoortMelding
     * @param omschrijving Omschrijving voor LO3SoortMelding
     * @param categorieMelding CategorieMelding voor LO3SoortMelding
     */
    private LO3SoortMelding(final String code, final String omschrijving, final LO3CategorieMelding categorieMelding) {
        this.code = code;
        this.omschrijving = omschrijving;
        this.categorieMelding = categorieMelding;
    }

    /**
     * Retourneert Code van LO3 Soort melding.
     *
     * @return Code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Retourneert Omschrijving van LO3 Soort melding.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert Categorie melding van LO3 Soort melding.
     *
     * @return Categorie melding.
     */
    public LO3CategorieMelding getCategorieMelding() {
        return categorieMelding;
    }

}
