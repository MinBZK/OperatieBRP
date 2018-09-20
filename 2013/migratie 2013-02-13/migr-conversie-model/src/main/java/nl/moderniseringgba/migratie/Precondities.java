/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Enumeratie van alle te implementeren requirements.
 * 
 */
public enum Precondities {

    /**
     * Land is altijd gevuld; deze mag gevuld zijn met de standaardwaarde 0000. Het moet een land zijn dat voorkomt in
     * de GBA landentabel.
     */
    PRE001(Stelsel.LO3,
            "Land is altijd gevuld; deze mag gevuld zijn met de standaardwaarde 0000. Het moet een land zijn dat "
                    + "voorkomt in de GBA landentabel."),
    /**
     * Als het land Nederland is, dan moet de Plaats een gemeentecode bevatten. Het moet een gemeente zijn die voorkomt
     * in de GBA gemeentetabel. Er mag geen andere dan wel extra informatie in Plaats staan.
     */
    PRE002(
            Stelsel.LO3,
            "Als het land Nederland is, dan moet de Plaats een gemeentecode bevatten. Het moet een gemeente zijn die "
                    + "voorkomt in de GBA gemeentetabel. Er mag geen andere dan wel extra informatie in Plaats staan."),
    /**
     * Als Gemeente ingevuld is, dan moet het land Nederland zijn.
     */
    PRE003(Stelsel.BRP, "Als Gemeente ingevuld is, dan moet het land Nederland zijn"),
    /**
     * Als Buitenlandse plaats eventueel in combinatie met Buitenlandse regio ingevuld is, dan is het land niet
     * Nederland.
     */
    PRE004(Stelsel.BRP,
            "Als Buitenlandse plaats eventueel in combinatie met Buitenlandse regio ingevuld is, dan is het land "
                    + "niet Nederland"),
    /**
     * In iedere Persoon categorie (categorie 01 dan wel historische categorie 51) is het element 01.10 gevuld.
     */
    PRE005(Stelsel.LO3,
            "In iedere Persoon categorie (categorie 01 dan wel historische categorie 51) is het element 01.10 gevuld."),
    /**
     * In het element Voornamen kunnen meerdere namen achter elkaar opgesomd worden. De scheiding tussen namen wordt
     * altijd (en alleen) weergegeven door middel van minstens één spatie.
     */
    PRE006(Stelsel.LO3,
            "In het element Voornamen kunnen meerdere namen achter elkaar opgesomd worden. De scheiding tussen "
                    + "namen wordt altijd (en alleen) weergegeven door middel van minstens één spatie."),
    /**
     * Als er geboortegegevens zijn, dan moeten in ieder geval het geboorteland en de geboortedatum opgenomen zijn. (dit
     * mogen zogenaamde standaardwaarden zijn)
     */
    PRE007(Stelsel.LO3,
            "Als er geboortegegevens zijn, dan moeten in ieder geval het geboorteland en de geboortedatum "
                    + "opgenomen zijn. (dit mogen zogenaamde standaardwaarden zijn)"),
    /**
     * Preconditie PRE001, specifiek voor de LO3-groep 03 Geboorte.
     */
    PRE008(Stelsel.LO3, "Preconditie PRE001, specifiek voor de LO3-groep 03 Geboorte"),
    /**
     * Als er overlijdensgegevens zijn, dan moeten in ieder geval het land en de datum opgenomen zijn. (dit mogen
     * zogenaamde standaardwaarden zijn).
     */
    PRE009(Stelsel.LO3, "Als er overlijdensgegevens zijn, dan moeten in ieder geval het land en de datum opgenomen "
            + "zijn. (dit mogen zogenaamde standaardwaarden zijn)"),
    /**
     * Preconditie PRE001, specifiek voor de LO3-groep 08 Overlijden.
     */
    PRE010(Stelsel.LO3, "Preconditie PRE001, specifiek voor de LO3-groep 08 Overlijden"),
    /**
     * Als categorie 12 betrekking heeft op een Nederlands reisdocument, dan moeten in ieder geval 35.10 Soort NL
     * reisdocument, 35.20 Nummer Nederlands reisdocument, 35.30 Datum uitgifte Nederlands reisdocument, 35.40
     * Autoriteit van afgifte Nederlands reisdocument en 35.50 Datum einde geldigheid Nederlands reisdocument opgenomen
     * zijn. (dit mogen zogenaamde standaardwaarden zijn).
     */
    PRE011(Stelsel.LO3,
            "Als categorie 12 betrekking heeft op een Nederlands reisdocument, dan moeten in ieder geval "
                    + "35.10 Soort NL reisdocument, 35.20 Nummer Nederlands reisdocument, "
                    + "35.30 Datum uitgifte Nederlands reisdocument, "
                    + "35.40 Autoriteit van afgifte Nederlands reisdocument en "
                    + "35.50 Datum einde geldigheid Nederlands reisdocument opgenomen zijn. "
                    + "(dit mogen zogenaamde standaardwaarden zijn)."),
    /**
     * Als er verblijfsrechtgegevens zijn, dan moeten in ieder geval 39.10 Aanduiding verblijfstitel en 39.30
     * Ingangsdatum verblijfstitel opgenomen zijn. (dit mogen zogenaamde standaardwaarden zijn)
     */
    PRE012(Stelsel.LO3,
            "Als er verblijfsrechtgegevens zijn, dan moeten in ieder geval 39.10 Aanduiding verblijfstitel "
                    + "en 39.30 Ingangsdatum verblijfstitel opgenomen zijn. (dit mogen zogenaamde standaardwaarden "
                    + "zijn)"),
    /**
     * Buitenlands adres regel 4, Buitenlands adres regel 5 en Buitenlands adres regel 6 wordt gedurende de
     * migratieperiode nog niet gebruikt, en zijn derhalve ook nooit gevuld.
     */
    PRE013(Stelsel.BRP, "Buitenlands adres regel 4, Buitenlands adres regel 5 en Buitenlands adres regel 6 "
            + "wordt gedurende de migratieperiode nog niet gebruikt, en zijn derhalve ook nooit gevuld."),
    /**
     * Buitenlands adres regel 1, Buitenlands adres regel 2 en Buitenlands adres regel 3 bevatten gedurende de
     * migratieperiode nooit meer dan 35 tekens.
     */
    PRE014(Stelsel.BRP, "Buitenlands adres regel 1, Buitenlands adres regel 2 en Buitenlands adres regel 3 bevatten "
            + "gedurende de migratieperiode nooit meer dan 35 tekens."),
    /**
     * Als 20.10 Vorig A-nummer is opgenomen, dan moet er in het stelsel (ten minste) één persoon zijn opgenomen die dit
     * A-nummer heeft.
     */
    PRE015(Stelsel.LO3, "Als 20.10 Vorig A-nummer is opgenomen, dan moet er in het stelsel (ten minste) "
            + "één persoon zijn opgenomen  die dit A-nummer heeft."),
    /**
     * Als 20.20 Volgend A-nummer is opgenomen, dan moet er in het stelsel precies één persoon zijn opgenomen die dit
     * A-nummer heeft.
     */
    PRE016(Stelsel.LO3, "Als 20.20 Volgend A-nummer is opgenomen, dan moet er in het stelsel precies "
            + "één persoon zijn opgenomen die dit A-nummer heeft."),
    /**
     * Als er is een actuele indicatie Geprivilegieerde? is, dan moet tenminste 1 nationaliteit opgenomen zijn.
     */
    PRE017(Stelsel.BRP,
            "Als er is een actuele indicatie Geprivilegieerde? is, dan moet tenminste 1 nationaliteit opgenomen zijn."),
    /**
     * In een gevulde (niet lege) categorie Huwelijk/geregistreerd partnerschap (05 of 55) moet 05.15.10 Soort
     * verbintenis opgenomen zijn.
     * 
     * Er is in dit geval uitsluitend sprake van een lege categorie Huwelijk/geregistreerd partnerschap als de volgende
     * groepen dan wel hun elementen ontbreken: 01 Identificatienummers; 02 Naam; 03 Geboorte; 04 Geslacht; 06
     * Huwelijkssluiting/aangaan geregistreerd partnerschap; 07 Ontbinding huwelijk/geregistreerd partnerschap; 15 Soort
     * verbintenis
     * 
     */
    PRE018(Stelsel.LO3, "In een gevulde (niet lege) categorie Huwelijk/geregistreerd partnerschap (05 of 55) moet "
            + "05.15.10 Soort verbintenis opgenomen zijn. Er is in dit geval uitsluitend sprake van een lege "
            + "categorie Huwelijk/geregistreerd partnerschap als de volgende groepen dan wel hun elementen "
            + "ontbreken: 01 Identificatienummers; 02 Naam; 03 Geboorte; 04 Geslacht; "
            + "06 Huwelijkssluiting/aangaan geregistreerd partnerschap; "
            + "07 Ontbinding huwelijk/geregistreerd partnerschap; 15 Soort verbintenis"),
    /**
     * Een afzonderlijke voornaam bevat geen spatie(s). Dit impliceert dat in het attribuut voornaam uit het Objecttype
     * Persoon \ voornaam nooit een spatie kan voorkomen.
     */
    PRE019(Stelsel.BRP, "Een afzonderlijke voornaam bevat geen spatie(s). Dit impliceert dat in het "
            + "attribuut voornaam uit het Objecttype Persoon \\ voornaam nooit een spatie kan voorkomen."),
    /**
     * In een categorie-rij is nooit zowel groep 82 Document als een Groep 81 Akte opgenomen.
     */
    PRE020(Stelsel.LO3, "In een categorie-rij is nooit zowel groep 82 Document als een Groep 81 Akte opgenomen."),
    /**
     * De combinatie van Voorvoegsel en Scheidingsteken (als deze geen spatie bevat) komt voor in GBA Tabel 36
     * Voorvoegselstabel. (Zie [GWB-BRP], Attribuuttype Voorvoegsel)
     * 
     * Als het Scheidingsteken een spatie bevat, komt het Voorvoegsel (dus zonder de spatie) voor in GBA Tabel 36.
     */
    PRE021(Stelsel.BRP, "De combinatie van Voorvoegsel en Scheidingsteken (als deze geen spatie bevat) komt voor "
            + "in GBA Tabel 36 Voorvoegselstabel. (Zie [GWB-BRP], Attribuuttype Voorvoegsel)"
            + "Als het Scheidingsteken een spatie bevat, komt het Voorvoegsel (dus zonder de spatie) voor in "
            + "GBA Tabel 36."),
    /**
     * Het voorvoegsel en scheidingsteken zijn beiden gevuld of beiden leeg.
     */
    PRE022(Stelsel.BRP, "Het voorvoegsel en scheidingsteken zijn beiden gevuld of beiden leeg"),
    /**
     * Als 64.10 Reden verlies Nederlandse nationaliteit is gevuld, zijn 05.10 Nationaliteit en 63.10 Reden verkrijging
     * Nederlandse nationaliteit niet gevuld.
     */
    PRE023(Stelsel.LO3, "Als 64.10 Reden verlies Nederlandse nationaliteit is gevuld, zijn 05.10 Nationaliteit en "
            + "63.10 Reden verkrijging Nederlandse nationaliteit niet gevuld."),
    /**
     * Preconditie PRE001, specifiek voor de LO3-groep 06 Huwelijkssluiting.
     */
    PRE024(Stelsel.LO3, "Preconditie PRE001, specifiek voor de LO3-groep 06 Huwelijkssluiting"),
    /**
     * Preconditie PRE002, specifiek voor de LO3-groep 03 Geboorte.
     */
    PRE025(Stelsel.LO3, "Preconditie PRE002, specifiek voor de LO3-groep 03 Geboorte"),
    /**
     * Preconditie PRE002, specifiek voor de LO3-groep 08 Overlijden.
     */
    PRE026(Stelsel.LO3, "Preconditie PRE002, specifiek voor de LO3-groep 08 Overlijden"),
    /**
     * Preconditie PRE002, specifiek voor de LO3-groep 06 Huwelijkssluiting.
     */
    PRE027(Stelsel.LO3, "Preconditie PRE002, specifiek voor de LO3-groep 06 Huwelijkssluiting"),
    /**
     * Preconditie PRE001, specifiek voor de LO3-groep 07 Huwelijksontbinding.
     */
    PRE028(Stelsel.LO3, "Preconditie PRE001, specifiek voor de LO3-groep 07 Huwelijksontbinding"),
    /**
     * Preconditie PRE002, specifiek voor de LO3-groep 07 Huwelijksontbinding.
     */
    PRE029(Stelsel.LO3, "Preconditie PRE002, specifiek voor de LO3-groep 07 Huwelijksontbinding"),
    /**
     * Het element 85.10 Ingangsdatum geldigheid moet gevuld zijn (deze mag de standaardwaarde hebben of gedeeltelijk
     * onbekend zijn).
     */
    PRE030(Stelsel.LO3, "Het element 85.10 Ingangsdatum geldigheid moet gevuld zijn (deze mag de standaardwaarde "
            + "hebben of gedeeltelijk onbekend zijn)"),
    /**
     * Het element 86.10 Datum van opneming moet gevuld zijn en mag geen standaardwaarde hebben en mag niet gedeeltelijk
     * onbekend zijn, daar waar dit element in een categorie is gedefinieerd.
     */
    PRE031(Stelsel.LO3,
            "Het element 86.10 Datum van opneming moet gevuld zijn en mag geen standaardwaarde hebben en mag niet "
                    + "gedeeltelijk onbekend zijn, daar waar dit element in een categorie is gedefinieerd."),
    /**
     * In een LO3-persoonslijst moet categorie 07 Inschrijving aanwezig zijn.
     */
    PRE032(Stelsel.LO3, "In een LO3-persoonslijst moet categorie 07 Inschrijving aanwezig zijn"),
    /**
     * In een LO3-persoonslijst moet categorie 08 Verblijfplaats aanwezig zijn.
     */
    PRE033(Stelsel.LO3, "In een LO3-persoonslijst moet categorie 08 Verblijfplaats aanwezig zijn"),
    /**
     * Voor iedere categorie-rij uit categorie 01/51 Persoon geldt dat de geslachtsnaam verplicht is.
     */
    PRE034(Stelsel.LO3,
            "Voor iedere categorie-rij uit categorie 01/51 Persoon geldt dat de geslachtsnaam verplicht is"),
    /**
     * Bij categorie 12 Reisdocument zijn altijd van precies één van de onderstaande groepen elementen ingevuld:
     * <ul>
     * <li>groep 35 Nederlands reisdocument</li>
     * <li>groep 36 Signalering</li>
     * <li>groep 37 Buitenlands reisdocument.</li>
     * </ul>
     */
    PRE035(Stelsel.LO3, "Bij categorie 12 Reisdocument zijn altijd van precies één van de onderstaande groepen "
            + "elementen ingevuld: groep 35 Nederlands reisdocument, groep 36 Signalering, "
            + "groep 37 Buitenlands reisdocument"),
    /**
     * Gemeente van inschrijving en de datum waarop de inschrijving heeft plaatsgevonden moeten gevuld zijn. Dit
     * betreffen de element 09.10 Gemeente van inschrijving en 09.20 Datum inschrijving uit categorie 08/58
     * Verblijfsadres.
     */
    PRE036(Stelsel.LO3, "Gemeente van inschrijving  en de datum waarop de inschrijving heeft plaatsgevonden "
            + "moeten gevuld zijn. Dit betreffen de element 09.10 Gemeente van inschrijving en "
            + "09.20 Datum inschrijving uit categorie 08/58 Verblijfsadres."),
    /**
     * Versienummer en Datum eerste inschrijving GBA moeten gevuld zijn. Dit betreft de elementen 80.10 Versienummer en
     * 68.10 Datum eerste inschrijving GBA uit categorie 07 Inschrijving.
     */
    PRE037(Stelsel.LO3, "Versienummer en Datum eerste inschrijving GBA moeten gevuld zijn. Dit betreft de "
            + "elementen 80.10 Versienummer en 68.10 Datum eerste inschrijving GBA uit categorie 07 Inschrijving."),
    /**
     * De Datumtijdstempel moet gevuld zijn. Dit betreft element 80.20 Datumtijdstempel uit categorie 07 Inschrijving
     */
    PRE038(Stelsel.LO3,
            "De Datumtijdstempel moet gevuld zijn en mag geen standaardwaarde hebben en mag niet gedeeltelijk "
                    + "onbekend. Dit betreft element 80.20 Datumtijdstempel uit categorie 07 Inschrijving"),
    /**
     * Als bij een Huwelijk/geregistreerd partnerschap (categorie 05/55) gegevens uit de LO3-groep 01, 03 en/of 04
     * gevuld zijn, dan moet de Geslachtsnaam ook gevuld zijn.
     */
    PRE039(Stelsel.LO3,
            "Als bij een Huwelijk/geregistreerd partnerschap (categorie 05/55) gegevens uit de LO3-groep 01, 03 en/of "
                    + "04 gevuld zijn, dan moet de Geslachtsnaam ook gevuld zijn."),
    /**
     * Als er gegevens met betrekking tot de ontbinding zijn gevuld, dan mogen er geen gegevens met betrekking tot de
     * sluiting zijn gevuld, en vice versa. Dit betekent dat de LO3-groepen 06 Huwelijkssluiting/aangaan geregistreerd
     * partnerschap en 07 Ontbinding huwelijk/geregistreerd partnerschap niet in dezelfde categorie-rij mogen voorkomen.
     */
    PRE040(Stelsel.LO3, "Als er gegevens met betrekking tot de ontbinding zijn gevuld, dan mogen er geen gegevens "
            + "met betrekking tot de sluiting zijn gevuld, en vice versa. Dit betekent dat de LO3-groepen "
            + "06 Huwelijkssluiting/aangaan geregistreerd partnerschap en 07 Ontbinding huwelijk/geregistreerd "
            + "partnerschap niet in dezelfde categorie-rij mogen voorkomen."),
    /**
     * Als er gegevens met betrekking tot de ontbinding of sluiting zijn gevuld, dan moet de geslachtsnaam ook gevuld
     * zijn. Dit betekent dat als de LO3-groep 06 Huwelijkssluiting/aangaan geregistreerd partnerschap of 07 Ontbinding
     * huwelijk/geregistreerd partnerschap voorkomt, dat ook het element 02.40 Geslachtsnaam echtgenoot/geregistreerd
     * partner gevuld moet zijn.
     */
    PRE041(Stelsel.LO3, "Als er gegevens met betrekking tot de ontbinding of sluiting zijn gevuld, dan moet de "
            + "geslachtsnaam ook gevuld zijn. Dit betekent dat als de LO3-groep 06 Huwelijkssluiting/aangaan "
            + "geregistreerd partnerschap of 07 Ontbinding huwelijk/geregistreerd partnerschap voorkomt, "
            + "dat ook het element 02.40 Geslachtsnaam echtgenoot/geregistreerd partner gevuld moet zijn."),
    /**
     * Als 15.10 Soort verbintenis is gevuld, dan mag deze uitsluitend gevuld zijn met ‘H’ of ‘P’. NB: dit betekent dat
     * ook de standaardwaarde ‘.’ niet wordt toegestaan.
     */
    PRE042(Stelsel.LO3,
            "Als 15.10 Soort verbintenis is gevuld, dan mag deze uitsluitend gevuld zijn met ‘H’ of ‘P’. "
                    + "NB: dit betekent dat ook de standaardwaarde ‘.’ niet wordt toegestaan."),
    // TODO: specificatie niet compleet.
    /**
     * In een BRP-persoonslijst is altijd een actuele geslachtsnaam opgenomen.
     */
    PRE043(Stelsel.BRP, "In een BRP-persoonslijst is altijd een actuele geslachtsnaam opgenomen"),
    /**
     * TODO: specificeren.
     */
    PRE044(Stelsel.BRP, "TODO: 044"),
    /**
     * TODO: specificeren.
     */
    PRE045(Stelsel.BRP, "TODO: 045"),
    /**
     * TODO: specificeren.
     */
    PRE046(Stelsel.BRP, "TODO: 046"),
    /**
     * In een LO3-persoonslijst moet categorie 01 Persoon aanwezig zijn.
     */
    PRE047(Stelsel.LO3, "In een LO3-persoonslijst moet categorie 01 Persoon aanwezig zijn"),
    /**
     * Als bij een Kind (categorie 09/59) gegevens uit de LO3-groep 01, 03 en/of 04 gevuld zijn, dan moet de
     * Geslachtsnaam ook gevuld zijn.
     */
    PRE048(Stelsel.LO3, "Als bij een Kind (categorie 09/59) gegevens uit de LO3-groep 01, 03 en/of 04 gevuld zijn, "
            + "dan moet de Geslachtsnaam ook gevuld zijn."),
    /**
     * Als bij een Ouder (categorie 02/52 en categorie 03/53) gegevens uit de LO3-groep 01, 03, 04 en/of 62 gevuld zijn,
     * dan moet de Geslachtsnaam ook gevuld zijn.
     */
    PRE049(Stelsel.LO3, "Als bij een Ouder (categorie 02/52 en categorie 03/53) gegevens uit de LO3-groep "
            + "01, 03, 04 en/of 62 gevuld zijn, dan moet de Geslachtsnaam ook gevuld zijn."),
    /**
     * Als er een lege categorie-rij voorkomt in een stapel (een actuele dan wel een historische categorie-rij), dan
     * moet er ook een gevulde categorie-rij zijn, met een 86.10 Datum van opneming die hetzelfde of ouder is. Categorie
     * 02 en 03 vormen hierop een uitzondering, daarvoor hoeft bovenstaande niet te gelden.
     */
    PRE050(
            Stelsel.LO3,
            "Als er een lege categorie-rij voorkomt in een stapel (een actuele dan wel een historische categorie-rij), "
                    + "dan moet er ook een gevulde categorie-rij zijn, met een 86.10 Datum van opneming die hetzelfde "
                    + "of ouder is. Categorie 02 en 03 vormen hierop een uitzondering, daarvoor hoeft bovenstaande "
                    + "niet te gelden."),
    /**
     * Binnen één categorie 04 Nationaliteit-stapel komt altijd maar 1 nationaliteitcode voor, ook in de onjuiste
     * categorie-rijen. Daarnaast mag de nationaliteitcode wel leeg zijn.
     */
    PRE051(Stelsel.LO3, "Binnen één categorie 04 Nationaliteit-stapel komt altijd maar 1 nationaliteitcode voor, "
            + "ook in de onjuiste categorie-rijen. Daarnaast mag de nationaliteitcode wel leeg zijn."),
    /**
     * Binnen één categorie 04 Nationaliteit-stapel mag in geval van de Nederlandse nationaliteit nooit uitsluitend een
     * categorie-rij voorkomen over het verlies van de nationaliteit (64.10 Reden verlies Nederlandse nationaliteit is
     * gevuld) ; er moet ook een categorie-rij zijn over de verkrijging van de nationaliteit waarbij 63.10 Reden
     * verkrijging Nederlandse nationaliteit gevuld moet zijn.
     */
    PRE052(Stelsel.LO3,
            "Binnen één categorie 04 Nationaliteit-stapel mag in geval van de Nederlandse nationaliteit nooit "
                    + "uitsluitend een categorie-rij voorkomen over het verlies van de nationaliteit "
                    + "(64.10 Reden verlies Nederlandse nationaliteit is gevuld) ; er moet ook een "
                    + "categorie-rij zijn over de verkrijging van de nationaliteit waarbij "
                    + "63.10 Reden verkrijging Nederlandse nationaliteit gevuld moet zijn."),
    /**
     * Als er gegevens aan de hand van een conversietabel vertaald moeten worden (denk bijvoorbeeld aan de codering van
     * adellijke titels en predikaten), dan moet de desbetreffende waarde voorkomen in de conversietabel, of het gegeven
     * moet niet ingevuld zijn.
     */
    PRE054(Stelsel.BEIDEN,
            "Als er gegevens aan de hand van een conversietabel vertaald moeten worden (denk bijvoorbeeld "
                    + "aan de codering van adellijke titels en predikaten), dan moet de desbetreffende waarde "
                    + "voorkomen in de conversietabel, of het gegeven moet niet ingevuld zijn."),
    /**
     * De categorie-rij met de meest recente 86.10 Datum van opneming mag niet onjuist zijn. NB: Als er meerdere rijen
     * zijn die deze zelfde Datum van opneming hebben, dan moet er minstens één niet onjuist zijn.
     */
    PRE055(Stelsel.LO3, "De categorie-rij met de meest recente 86.10 Datum van opneming mag niet onjuist zijn. "
            + "NB: Als er meerdere rijen zijn die deze zelfde Datum van opneming hebben, dan moet er minstens "
            + "één niet onjuist zijn."),
    /**
     * Als er een lege categorie-rij is, moet de datum geldigheid van die categorie-rij ook voorkomen in een gevulde,
     * onjuiste categorie-rij die een eerdere of gelijke datum van opneming heeft. Dit geldt voor de volgende
     * categorieën: categorie 05/55 Huwelijk/geregistreerd partnerschap; categorie 06/56 Overlijden
     */
    PRE056(Stelsel.LO3,
            "Als er een lege categorie-rij is, moet de datum geldigheid van die categorie-rij ook voorkomen in een "
                    + "gevulde, onjuiste categorie-rij die een eerdere of gelijke datum van opneming heeft. "
                    + "Dit geldt voor de volgende categorieën: "
                    + "categorie 05/55 Huwelijk/geregistreerd partnerschap; categorie 06/56 Overlijden"),
    /**
     * In geval van een Nederlands verblijfadres moet 11.70 Woonplaatsnaam een bestaande (actuele danwel historische)
     * woonplaats zijn. Kortom het moet een in BAG voorkomende naam van een woonplaats bevatten. Dit betekent dat de
     * letterlijke vulling van 11.70 Woonplaatsnaam moet voorkomen in de BRP stamtabel Plaats.
     * 
     * Uitzondering hierop is de standaardwaarde ‘.’. Dit wordt behandeld als <geen waarde>
     */
    PRE057(Stelsel.LO3,
            "In geval van een Nederlands verblijfadres moet 11.70 Woonplaatsnaam een bestaande (actuele danwel "
                    + "historische) woonplaats zijn. Kortom het moet een in BAG voorkomende naam van een woonplaats "
                    + "bevatten. Dit betekent dat de letterlijke vulling van 11.70 Woonplaatsnaam moet voorkomen in "
                    + "de BRP stamtabel Plaats. Uitzondering hierop is de standaardwaarde ‘.’. "
                    + "Dit wordt behandeld als <geen waarde>"),
    /**
     * Bij een persoon kunnen de volgende twee indicaties niet gelijktijdig geldig zijn: 'Vastgesteld niet Nederlander?'
     * en 'Behandeld als Nederlander'.
     */
    PRE058(Stelsel.BRP, "Bij een persoon kunnen de volgende twee indicaties niet gelijktijdig geldig zijn: "
            + "'Vastgesteld niet Nederlander?' en 'Behandeld als Nederlander'"),

    /**
     * In de BRP mogen niet gelijktijdig twee ouders en een derde gezag hebben. Dit betekent dat de volgende drie
     * attributen niet gelijktijdig de waarde ‘Ja’ mogen bevatten:
     * <ul>
     * <li>Ouder1 heeft gezag</li>
     * <li>Ouder2 heeft gezag</li>
     * <li>Derde heeft gezag</li>
     * </ul>
     */
    PRE059(Stelsel.BRP, "In de BRP mogen niet gelijktijdig twee ouders en een derde gezag hebben. "
            + "Dit betekent dat de volgende drie attributen niet gelijktijdig de waarde ‘Ja’ mogen bevatten: "
            + "Ouder1 heeft gezag; Ouder2 heeft gezag; Derde heeft gezag"),

    /**
     * Een (actueel) A-nummer in categorie 01 mag niet voorkomen als actueel A-nummer op een andere persoonslijst. NB
     * dit kan nu al niet voorkomen in de GBA-V.
     */
    PRE060(Stelsel.BRP, "Een (actueel) A-nummer in categorie 01 mag niet voorkomen als actueel A-nummer op "
            + "een andere persoonslijst. NB dit kan nu al niet voorkomen in de GBA-V."),

    /**
     * Een (actueel) BSN in categorie 01 mag niet voorkomen als actueel BSN op een andere persoonslijst.
     */
    PRE061(Stelsel.BRP,
            "Een (actueel) BSN in categorie 01 mag niet voorkomen als actueel BSN op een andere persoonslijst"),

    /**
     * Bij een relatie naar een andere persoon (categorieën 02/52, 03/53, 05/55, 09/59) mogen het A-nummer en het BSN
     * niet voorkomen als actueel A-nummer of actueel BSN op meer dan één (1) persoonslijst.
     */
    PRE062(Stelsel.BRP, "Bij een relatie naar een andere persoon (categorieën 02/52, 03/53, 05/55, 09/59) mogen het "
            + "A-nummer en het BSN niet voorkomen als actueel A-nummer of actueel BSN op meer dan "
            + "één (1) persoonslijst."),

    /**
     * Voor categorie 02/52 Ouder1, 03/53 Ouder2, 05/55 Huwelijk/geregistreerd partnerschap en 09/59 Kind geldt dat als
     * groep 02 voorkomt, dat geslachtsnaam verplicht is.
     */
    PRE064(Stelsel.LO3, "Voor categorie 02/52 Ouder1, 03/53 Ouder2, 05/55 Huwelijk/geregistreerd partnerschap en "
            + "09/59 Kind geldt dat als groep 02 voorkomt, dat geslachtsnaam verplicht is."),

    /**
     * In een LO3-persoonslijst moet categorie 02 Ouder1 aanwezig zijn.
     */
    PRE065(Stelsel.BRP, "In een LO3-persoonslijst moet categorie 02 Ouder1 aanwezig zijn."),

    /**
     * In een LO3-persoonslijst moet categorie 03 Ouder2 aanwezig zijn.
     */
    PRE066(Stelsel.BRP, "In een LO3-persoonslijst moet categorie 03 Ouder2 aanwezig zijn."),

    /**
     * Datum document mag geen (gedeeltelijk) onbekend datum bevatten.
     */
    PRE067(Stelsel.LO3, "Datum document mag geen (gedeeltelijk) onbekend datum bevatten."),

    /**
     * "Als er een gevuld juist categorie voorkomen is, dan mag er geen leeg juist voorkomens zijn. Dit geldt voor de
     * volgende categorieën: categorie 05/55 en 06/56"
     */
    PRE068(Stelsel.LO3, "Als er een gevuld juist categorie voorkomen is, dan mag er geen leeg juist voorkomens zijn"),
    /**
     * Als bij een Ouder (categorie 02/52 en categorie 03/53) de Geslachtsnaam gevuld is, dan moet ook 62.10 Datum
     * ingang familierechtelijke betrekking gevuld zijn.
     */
    PRE069(Stelsel.LO3, "Als bij een Ouder (categorie 02/52 en categorie 03/53) de Geslachtsnaam gevuld is, "
            + "dan moet ook 62.10 Datum ingang familierechtelijke betrekking gevuld zijn."),

    /**
     * Als groep 82 Document is opgenomen, dan moeten zowel 82.10 Gemeente document, 82.20 Datum document als 82.30
     * Beschrijving document openomen zijn.
     */
    PRE070(Stelsel.LO3, "Als groep 82 Document is opgenomen, dan moeten zowel 82.10 Gemeente document, "
            + "82.20 Datum document als 82.30 Beschrijving document openomen zijn.");

    /**
     * Heeft de preconditie betrekking op LO3 of BRP?
     */
    private enum Stelsel {
        LO3, BRP, BEIDEN
    }

    private String omschrijving;
    private Stelsel stelsel;

    private Precondities(final Stelsel stelsel, final String omschrijving) {
        this.omschrijving = omschrijving;
        this.stelsel = stelsel;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public Stelsel getStelsel() {
        return stelsel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("code", name())
                .append("stelsel", stelsel).append("omschrijving", omschrijving).toString();
    }
}
