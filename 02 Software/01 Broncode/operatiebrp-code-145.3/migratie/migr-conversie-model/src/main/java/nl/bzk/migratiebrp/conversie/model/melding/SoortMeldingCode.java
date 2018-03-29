/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.melding;

/**
 * Bevat de lijst met unieke melding codes.
 */
public enum SoortMeldingCode {

    /**
     * De categorie mag het element niet bevatten.
     */
    ELEMENT,

    /**
     * Het element bevat tekens die niet in de teletex-set voorkomen.
     */
    TELETEX,

    /**
     * Het element bevat niet uitsluitend cijfers.
     */
    NUMERIEK,

    /**
     * Het element heeft een niet toegestane lengte.
     */
    LENGTE,

    /**
     * Land is altijd gevuld; deze mag gevuld zijn met de standaardwaarde 0000. Het moet een land zijn dat voorkomt in
     * de GBA landentabel.
     */
    PRE001,

    /**
     * Als het land Nederland is, dan moet de Plaats een gemeentecode bevatten. Het moet een gemeente zijn die voorkomt
     * in de GBA gemeentetabel. Er mag geen andere dan wel extra informatie in Plaats staan.
     */
    PRE002,

    /**
     * Als Gemeente ingevuld is, dan moet het land Nederland zijn.
     */
    PRE003,

    /**
     * Als Buitenlandse plaats eventueel in combinatie met Buitenlandse regio ingevuld is, dan is het land niet
     * Nederland.
     */
    PRE004,

    /**
     * In iedere Persoon categorie (categorie 01 dan wel historische categorie 51) is het element 01.10 gevuld.
     */
    PRE005,

    /**
     * In het element Voornamen kunnen meerdere namen achter elkaar opgesomd worden. De scheiding tussen namen wordt
     * altijd (en alleen) weergegeven door middel van minstens één spatie.
     */
    PRE006,

    /**
     * Als er geboortegegevens zijn, dan moeten in ieder geval het geboorteland en de geboortedatum opgenomen zijn. (dit
     * mogen zogenaamde standaardwaarden zijn)
     */
    PRE007,

    /**
     * Preconditie PRE001, specifiek voor de LO3-groep 03 Geboorte.
     */
    PRE008,

    /**
     * Als er overlijdensgegevens zijn, dan moeten in ieder geval het land en de datum opgenomen zijn. (dit mogen
     * zogenaamde standaardwaarden zijn).
     */
    PRE009,

    /**
     * Preconditie PRE001, specifiek voor de LO3-groep 08 Overlijden.
     */
    PRE010,

    /**
     * Als categorie 12 betrekking heeft op een Nederlands reisdocument, dan moeten in ieder geval 35.10 Soort NL
     * reisdocument, 35.20 Nummer Nederlands reisdocument, 35.30 Datum uitgifte Nederlands reisdocument, 35.40
     * Autoriteit van afgifte Nederlands reisdocument en 35.50 Datum einde geldigheid Nederlands reisdocument opgenomen
     * zijn. (dit mogen zogenaamde standaardwaarden zijn).
     */
    PRE011,

    /**
     * Als er verblijfsrechtgegevens zijn, dan moeten in ieder geval 39.10 Aanduiding verblijfstitel en 39.30
     * Ingangsdatum verblijfstitel opgenomen zijn. (dit mogen zogenaamde standaardwaarden zijn)
     */
    PRE012,

    /**
     * Buitenlands adres regel 4, Buitenlands adres regel 5 en Buitenlands adres regel 6 wordt gedurende de
     * migratieperiode nog niet gebruikt, en zijn derhalve ook nooit gevuld.
     */
    PRE013,

    /**
     * Buitenlands adres regel 1, Buitenlands adres regel 2 en Buitenlands adres regel 3 bevatten gedurende de
     * migratieperiode nooit meer dan 35 tekens.
     */
    PRE014,

    /**
     * Als er een actuele indicatie Bijzondere verblijfsrechtelijke positie is, dan moet tenminste 1 nationaliteit
     * opgenomen zijn.
     */
    PRE017,

    /**
     * In een gevulde (niet lege) categorie Huwelijk/geregistreerd partnerschap (05 of 55) moet 05.15.10 Soort
     * verbintenis opgenomen zijn. Er is in dit geval uitsluitend sprake van een lege categorie Huwelijk/geregistreerd
     * partnerschap als de volgende groepen dan wel hun elementen ontbreken: 01,02,03,04,06,07,15.
     */
    PRE018,

    /**
     * Een afzonderlijke voornaam bevat geen spatie(s). Dit impliceert dat in het attribuut voornaam uit het Objecttype
     * Persoon \ voornaam nooit een spatie kan voorkomen.
     */
    PRE019,

    /**
     * In een categorie-rij is nooit zowel groep 82 Document als een Groep 81 Akte opgenomen.
     */
    PRE020,

    /**
     * De combinatie van Voorvoegsel en Scheidingsteken (als deze geen spatie bevat) komt voor in GBA Tabel 36
     * Voorvoegselstabel. (Zie [GWB-BRP], Attribuuttype Voorvoegsel)
     *
     * Als het Scheidingsteken een spatie bevat, komt het Voorvoegsel (dus zonder de spatie) voor in GBA Tabel 36.
     */
    PRE021,

    /**
     * Het voorvoegsel en scheidingsteken zijn beiden gevuld of beiden leeg.
     */
    PRE022,

    /**
     * Als 64.10 Reden verlies Nederlandse nationaliteit is gevuld, zijn 05.10 Nationaliteit, 63.10 Reden verkrijging
     * Nederlandse nationaliteit en 65.10 Aanduiding bijzonder Nederlanderschap.
     */
    PRE023,

    /**
     * Preconditie PRE001, specifiek voor de LO3-groep 06 Huwelijkssluiting.
     */
    PRE024,

    /**
     * Preconditie PRE002, specifiek voor de LO3-groep 03 Geboorte.
     */
    PRE025,

    /**
     * Preconditie PRE002, specifiek voor de LO3-groep 08 Overlijden.
     */
    PRE026,

    /**
     * Preconditie PRE002, specifiek voor de LO3-groep 06 Huwelijkssluiting.
     */
    PRE027,

    /**
     * Preconditie PRE001, specifiek voor de LO3-groep 07 Huwelijksontbinding.
     */
    PRE028,

    /**
     * Preconditie PRE002, specifiek voor de LO3-groep 07 Huwelijksontbinding.
     */
    PRE029,

    /**
     * Het element 85.10 Ingangsdatum geldigheid moet gevuld zijn (deze mag de standaardwaarde hebben of gedeeltelijk
     * onbekend zijn).
     */
    PRE030,

    /**
     * Het element 86.10 Datum van opneming moet gevuld zijn en mag geen standaardwaarde hebben en mag niet gedeeltelijk
     * onbekend zijn, daar waar dit element in een categorie is gedefinieerd.
     */
    PRE031,

    /**
     * In een LO3-persoonslijst moet categorie 07 Inschrijving aanwezig zijn.
     */
    PRE032,

    /**
     * In een LO3-persoonslijst moet categorie 08 Verblijfplaats aanwezig zijn.
     */
    PRE033,

    /**
     * Voor iedere categorie-rij uit categorie 01/51 Persoon geldt dat de geslachtsnaam verplicht is.
     */
    PRE034,

    /**
     * Bij categorie 12 Reisdocument zijn altijd van precies één van de onderstaande groepen elementen ingevuld.
     * <ul>
     * <li>groep 35 Nederlands reisdocument</li>
     * <li>groep 36 Signalering</li>
     * </ul>
     */
    PRE035,

    /**
     * Gemeente van inschrijving en de datum waarop de inschrijving heeft plaatsgevonden moeten gevuld zijn. Dit
     * betreffen de element 09.10 Gemeente van inschrijving en 09.20 Datum inschrijving uit categorie 08/58
     * Verblijfsadres.
     */
    PRE036,

    /**
     * Versienummer en Datum eerste inschrijving GBA moeten gevuld zijn. Dit betreft de elementen 80.10 Versienummer en
     * 68.10 Datum eerste inschrijving GBA uit categorie 07 Inschrijving.
     */
    PRE037,

    /**
     * De Datumtijdstempel moet gevuld zijn en mag geen standaardwaarde hebben en mag niet gedeeltelijk onbekend. Dit
     * betreft element 80.20 Datumtijdstempel uit categorie 07 Inschrijving
     */
    PRE038,

    /**
     * Als bij een Huwelijk/geregistreerd partnerschap (categorie 05/55) gegevens uit de LO3-groep 01, 03 en/of 04
     * gevuld zijn, dan moet de Geslachtsnaam ook gevuld zijn.
     */
    PRE039,

    /**
     * Als er gegevens met betrekking tot de ontbinding zijn gevuld, dan mogen er geen gegevens met betrekking tot de
     * sluiting zijn gevuld, en vice versa. Dit betekent dat de LO3-groepen 06 Huwelijkssluiting/aangaan geregistreerd
     * partnerschap en 07 Ontbinding huwelijk/geregistreerd partnerschap niet in dezelfde categorie-rij mogen voorkomen.
     */
    PRE040,

    /**
     * Als er gegevens met betrekking tot de ontbinding of sluiting zijn gevuld, dan moet de geslachtsnaam ook gevuld
     * zijn. Dit betekent dat als de LO3-groep 06 Huwelijkssluiting/aangaan geregistreerd partnerschap of 07 Ontbinding
     * huwelijk/geregistreerd partnerschap voorkomt, dat ook het element 02.40 Geslachtsnaam echtgenoot/geregistreerd
     * partner gevuld moet zijn.
     */
    PRE041,

    /**
     * Als 15.10 Soort verbintenis is gevuld, dan mag deze uitsluitend gevuld zijn met ‘H’ of ‘P’. NB: dit betekent dat
     * ook de standaardwaarde ‘.’ niet wordt toegestaan.
     */
    PRE042,

    /**
     * In een BRP-persoonslijst is altijd een actuele geslachtsnaam opgenomen.
     */
    PRE043,

    /**
     * Gereserveerd voor toekomst
     */
    PRE044,

    /**
     * Gereserveerd voor toekomst
     */
    PRE045,

    /**
     * Gereserveerd voor toekomst
     */
    PRE046,

    /**
     * In een LO3-persoonslijst moet categorie 01 Persoon aanwezig zijn.
     */
    PRE047,

    /**
     * Als bij een Kind (categorie 09/59) gegevens uit de LO3-groep 01 en/of 03 gevuld zijn, dan moet de Geslachtsnaam
     * ook gevuld zijn.
     */
    PRE048,

    /**
     * Als bij een Ouder (categorie 02/52 en categorie 03/53) gegevens uit de LO3-groep 01, 03, 04 en/of 62 gevuld zijn,
     * dan moet de Geslachtsnaam ook gevuld zijn.
     */
    PRE049,

    /**
     * Als er een lege categorie-rij of beeindiging nationaliteit voorkomt in een stapel (een actuele dan wel een
     * historische categorie-rij), dan moet er ook een gevulde categorie-rij zijn, met 86.10 Datum van opneming die
     * hetzelfde of ouder is en 85.10 Datum geldigheid die hetzelfde of ouder is. Categorie 02, 03, 05 en 06 vormen
     * hierop een uitzondering.
     */
    PRE050,

    /**
     * Binnen één categorie 04 Nationaliteit-stapel komt altijd maar 1 nationaliteitcode voor, ook in de onjuiste
     * categorie-rijen. Daarnaast mag de nationaliteitcode wel leeg zijn.
     */
    PRE051,

    /**
     * Als er gegevens aan de hand van een conversietabel vertaald moeten worden (denk bijvoorbeeld aan de codering van
     * adellijke titels en predikaten), dan moet de desbetreffende waarde voorkomen in de conversietabel, of het gegeven
     * moet niet ingevuld zijn.
     */
    PRE054,

    /**
     * De actuele categorie mag niet onjuist zijn.
     */
    PRE055,

    /**
     * Als er een lege categorie-rij is, moet de datum geldigheid van die categorie-rij ook voorkomen in een gevulde,
     * onjuiste categorie-rij die een eerdere of gelijke datum van opneming heeft. Dit geldt voor de volgende
     * categorieën: categorie 05/55 Huwelijk/geregistreerd partnerschap; categorie 06/56 Overlijden
     */
    PRE056,

    /**
     * In geval van een Nederlands verblijfadres moet 11.70 Woonplaatsnaam een bestaande (actuele danwel historische)
     * woonplaats zijn. Kortom het moet een in BAG voorkomende woonplaats zijn. Dit wordt gecontroleerd door na te gaan
     * of de woonplaatsnaam voorkomt in de BRP stamtabel Plaats.
     *
     * Uitzondering hierop is de standaardwaarde ‘.’. Dit wordt behandeld als {@literal <geen waarde>}.
     */
    PRE057,

    /**
     * Bij een persoon kunnen de volgende twee indicaties niet gelijktijdig geldig zijn: 'Vastgesteld niet Nederlander?'
     * en 'Behandeld als Nederlander'.
     */
    PRE058,

    /**
     * In de BRP mogen niet gelijktijdig twee ouders en een derde gezag hebben. Dit betekent dat de volgende drie
     * attributen niet gelijktijdig de waarde ‘Ja’ mogen bevatten:
     * <ul>
     * <li>Ouder1 heeft gezag</li>
     * <li>Ouder2 heeft gezag</li>
     * <li>Derde heeft gezag</li>
     * </ul>
     */
    PRE059,

    /**
     * Een (actueel) A-nummer in categorie 01 mag niet voorkomen als actueel A-nummer op een andere persoonslijst. NB
     * dit kan nu al niet voorkomen in de GBA-V.
     */
    PRE060,

    /**
     * Een (actueel) BSN in categorie 01 mag niet voorkomen als actueel BSN op een andere persoonslijst.
     */
    PRE061,

    /**
     * Bij een relatie naar een andere persoon (categorieën 02/52, 03/53, 05/55, 09/59) mogen het A-nummer en het BSN
     * niet voorkomen als actueel A-nummer of actueel BSN op meer dan één (1) persoonslijst.
     */
    PRE062,

    /**
     * Voor categorie 02/52 Ouder1, 03/53 Ouder2, 05/55 Huwelijk/geregistreerd partnerschap en 09/59 Kind geldt dat als
     * groep 02 voorkomt, dat geslachtsnaam verplicht is.
     */
    PRE064,

    /**
     * In een LO3-persoonslijst moet categorie 02 Ouder1 aanwezig zijn.
     */
    PRE065,

    /**
     * In een LO3-persoonslijst moet categorie 03 Ouder2 aanwezig zijn.
     */
    PRE066,

    /**
     * Datum document mag geen (gedeeltelijk) onbekend datum bevatten.
     */
    PRE067,

    /**
     * Als bij een Ouder (categorie 02/52 en categorie 03/53) de Geslachtsnaam gevuld is, dan moet ook 62.10 Datum
     * ingang familierechtelijke betrekking gevuld zijn.
     */
    PRE069,

    /**
     * Als groep 82 Document is opgenomen, dan moeten zowel 82.10 Gemeente document, 82.20 Datum document als 82.30
     * Beschrijving document openomen zijn.
     */
    PRE070,

    /**
     * Als groep 81 Akte is opgenomen, dan moet zowel 81.10 Registergemeente akte als 81.20 Aktenummer gevuld zijn.
     */
    PRE071,

    /**
     * Op een persoonslijst moeten alle actuele kind categorieen (categorie 09) een verschillend A-nummer hebben.
     */
    PRE073,

    /**
     * Voor een huwelijk/geregistreerd partnerschap stapel (categorie 05/55) geldt dat twee elkaar opvolgende juiste
     * voorkomens, waarbij 15.10 gelijk is en bij beiden groep 06 aanwezig is, alle elementen uit deze groep 06 gelijk
     * moeten zijn.
     */
    PRE074,

    /**
     * Voor een huwelijk/geregistreerd partnerschap stapel (categorie 05/55) geldt dat twee elkaar opvolgende juiste
     * voorkomens, waarbij 15.10 gelijk is en bij beiden groep 07 aanwezig is, alle elementen uit deze groep 07 gelijk
     * moeten zijn.
     */
    PRE075,

    /**
     * Als 84.10 Indicatie onjuist dan wel strijdigheid met de openbare orde is gevuld, dan mag deze uitsluitend gevuld
     * zijn met ‘O’ of ‘S’.
     */
    PRE076,

    /**
     * Als in categorie 07 het element 87.10 PK-gegevens volledig meegeconverteerd is gevuld, dan mag deze uitsluitend
     * gevuld zijn met een 'P'.
     */
    PRE077,

    /**
     * Als 32.10 Indicatie gezag minderjarige gevuld is, dan mag deze uitsluitend één of een combinatie van de volgende
     * tekens bevatten: '1', '2', 'D'.
     */
    PRE078,

    /**
     * Als in categorie 08/58 Verblijfplaats groep 14 voorkomt, dan moeten zowel 14.10 Land vanwaar gevestigd als 14.20
     * Datum vestiging in Nederland ingevuld zijn.
     */
    PRE079,

    /**
     * In Categorie 08/58 Verblijfplaats moet groep 10 voorkomen of moet groep 13 voorkomen.
     */
    PRE080,

    /**
     * Als in categorie 08/58 Verblijfplaats groep 13 voorkomt, dan moet 13.10 Land adres buitenland en 13.20 Datum
     * aanvang adres buitenland ingevuld zijn.
     */
    PRE081,

    /**
     * "Er mogen geen nationaliteitstapels zijn die betrekking hebben op dezelfde nationaliteit. Dit betekent dat in de
     * juiste voorkomens van de verschillen stapels niet dezelfde waarde in 05.10 Nationaliteit mag staan."
     */
    PRE082,

    /**
     * Als in categorie 04/54 Nationaliteit 63.10 Reden opname nationaliteit gevuld is, dan moet 05.10 Nationaliteit of
     * 65.10 Aanduiding bijzonder Nederlanderschap ook gevuld zijn.
     */
    PRE083,

    /**
     * Als in categorie 08/58 Verblijfplaats groep 10 voorkomt, dan moet tevens 11.10 Straatnaam of 12.10
     * Locatieomschrijving ingevuld zijn.
     */
    PRE084,

    /**
     * Bij een Document is altijd precies één van de volgende twee attributen gevuld: Aktenummer of Omschrijving.
     */
    PRE085,

    /**
     * Als in categorie 07 groep 67 voorkomt, dan moeten zowel 67.10 Datum opschorting bijhouding als 67.20 Omschrijving
     * reden opschorting bijhouding ingevuld zijn.
     */
    PRE087,

    /**
     * Als categorie 05/55 groep 06 voorkomt, dan moet 06.10 Datum Huwelijkssluiting/aangaan geregistreerd partnerschap
     * ingevuld zijn.
     */
    PRE088,

    /**
     * Als in categorie 13 groep 38 voorkomt, dan moet 38.10 Aanduiding uitgesloten kiesrecht ingevuld zijn.
     */
    PRE089,

    /**
     * Als in categorie 13 groep 31 voorkomt, dan moet 31.10 Aanduiding Europees kiesrecht ingevuld zijn.
     */
    PRE090,

    /**
     * Als 11.50 Aanduiding bij huisnummer gevuld is, dan mag deze uitsluitend gevuld zijn met een van de volgende
     * waarden: ‘by' (= bij), ‘to’ (= tegenover).
     */
    PRE091,

    /**
     * Er mag geen onjuist voorkomen zijn, die betrekking heeft op dezelfde nationaliteit als een juist dan wel onjuist
     * voorkomen in een andere stapel. Dit betekent dat in de genoemde voorkomens niet dezelfde waarde in 05.10
     * Nationaliteit mag staan.
     */
    PRE093,

    /**
     * In Categorie 08/58 Verblijfplaats mag niet zowel groep 13 Adres buitenland als groep 14 Immigratie voorkomen.
     */
    PRE094,

    /**
     * Buitenlands adres regel 4, Buitenlands adres regel 5 en Buitenlands adres regel 6 wordt gedurende de
     * migratieperiode nog niet gebruikt, en zijn derhalve ook nooit gevuld.
     */
    PRE095,

    /**
     * Buitenlands adres regel 1, Buitenlands adres regel 2 en Buitenlands adres regel 3 bevatten gedurende de
     * migratieperiode nooit meer dan 35 tekens.
     */
    PRE096,

    /**
     * Als groep 71 Verificatie is opgenomen, dan moeten zowel 71.10 Datum verificatie als 71.20 Omschrijving
     * verificatie opgenomen zijn.
     */
    PRE097,

    /**
     * Als groep 88 RNI-deelnemer is opgenomen, dan moet 88.10 RNI-deelnemer opgenomen zijn.
     */
    PRE098,

    /**
     * Als groep 83 Procedure is opgenomen, dan moet zowel 83.10 Aanduiding gegevens in onderzoek als 83.20 Datum ingang
     * onderzoek gevuld zijn.
     */
    PRE099,

    /**
     * Als in categorie 08/58 Verblijfplaats groep 10 voorkomt, dan moet 10.10 Functie adres ingevuld zijn.
     */
    PRE100,

    /**
     * Als 67.20 Omschrijving reden opschorting bijhouding is gevuld, dan mag deze niet gevuld zijn met de
     * standaardwaarde ‘.’.
     */
    PRE101,

    /**
     * Als in categorie 08/58 Verblijfplaats groep 10 niet voorkomt, dan mogen groep 11 en groep 12 ook niet voorkomen.
     */
    PRE102,

    /**
     * Er mogen geen verschillende nationaliteitstapels zijn waarvan de ene stapel betrekking heeft op de Nederlandse
     * nationaliteit en de andere stapel op bijzonder Nederlanderschap. Dit betekent dat in de juiste voorkomens van een
     * stapel niet 05.10 Nationaliteit met de waarde ‘0001’ mag staan en in een ander stapel in de juiste voorkomens
     * 65.10 Bijzonder Nederlanderschap is gevuld.
     */
    PRE103,

    /**
     * Er mag geen onjuist voorkomen zijn, die betrekking heeft op de Nederlandse nationaliteit als in een juist dan wel
     * onjuist voorkomen in een andere stapel bijzonder Nederlanderschap voorkomt of vice versa.
     */
    PRE104,

    /**
     * Als 65.10 Aanduiding bijzonder Nederlanderschap is gevuld, zijn 05.10 Nationaliteit en 64.10 Reden beeindiging
     * nationaliteit niet gevuld.
     */
    PRE105,

    /**
     * Als 05.10 Nationaliteit is gevuld, zijn 64.10 Reden verlies Nederlandse nationaliteit en 65.10 Aanduiding
     * bijzonder Nederlanderschap niet gevuld.
     */
    PRE106,

    /**
     * Er mogen niet meerdere categorie 12 Reisdocument zijn waarbij groep 36 Signalering is opgenomen.
     */
    PRE107,

    /**
     * Als aan een Actie meerdere Documenten zijn gekoppeld, dan moet in al deze Documenten dezelfde Partij opgenomen
     * zijn.
     */
    PRE108,

    /**
     * Er mag geen nationaliteitstapel zijn die betrekking heeft op zowel buitenlandse nationaliteit als op bijzonder
     * Nederlanderschap.
     */
    PRE109,

    /**
     * Er mag geen onjuist voorkomen zijn, die betrekking heeft op de buitenlandse nationaliteit als in een juist dan
     * wel onjuist voorkomen in deze stapel bijzonder Nederlanderschap voorkomt of vice versa.
     */
    PRE110,

    /**
     * In categorie 08/58 Verblijfsadres moet het element 72.10 Omschrijving van de aangifte adreshouding ingevuld zijn.
     */
    PRE111,

    /**
     * Als in een stapel alle gevulde voorkomens onjuist zijn, dan moet er minstens één niet-onjuist leeg voorkomen zijn
     * waarvan 85.10 Ingangsdatum geldigheid overeenkomt met 85.10 Ingangsdatum geldigheid van minstens één gevuld
     * voorkomen.
     */
    PRE112,

    /**
     * Element 01.10 A-nummer uit een niet onjuiste categorie 02/52 Ouder1, 03/53 Ouder2, 05/55 Huwelijk/geregistreerd
     * partnerschap en 09/59 Kind mag niet overeenkomen met 01.01.10 A-nummer.
     */
    PRE113,
    /**
     * Als er sprake is van omzetting bij cat05, dan moet ieder substapel minstens 1 juiste sluiting bevatten.
     */
    PRE114,
    /**
     * Als 73.10 EU-persoonsnummer is gevuld, dan moet 05.10 Nationaliteit ook gevuld zijn.
     */
    PRE115,
    /**
     * Afnemersindicaties: a-nummer is verplicht.
     */
    AFN001,

    /**
     * Afnemersindicaties: stapel zonder afnemersindicatie.
     */
    AFN002,

    /**
     * Afnemersindicaties: ingangsdatum mag niet (gedeeltelijk) onbekend zijn.
     */
    AFN003,

    /**
     * Afnemersindicaties: meerdere stapels gevonden met dezelfde afnemersindicatie; deze stapel wordt genegeerd.
     */
    AFN004,

    /**
     * Afnemersindicaties: stapel met meerdere verschillende afnemersindicaties.
     */
    AFN005,

    /**
     * Afnemersindicaties: historie binnen stapel is ongeldig. Actuele categorie geeft geen levering aan; volledige
     * stapel wordt genegeerd.
     */
    AFN006,

    /**
     * Afnemersindicaties: historie binnen stapel is ongeldig. Actuele categorie geeft levering aan; enkel actuele
     * categorie wordt geconverteerd.
     */
    AFN007,

    /**
     * Afnemersindicaties: beëindiging (lege categorie) kan niet gekoppeld worden aan een (nog niet beëindigde) start
     * (gevulde categorie).
     */
    AFN008,

    /**
     * Afnemersindicaties: persoon niet gevonden op basis van a-nummer.
     */
    AFN009,

    /**
     * Afnemersindicaties: partij niet gevonden op basis van afnemersindicatie.
     */
    AFN010,

    /**
     * Afnemersindicaties: geen leveringsautorisatie gevonden voor partij.
     */
    AFN011,

    /**
     * Afnemersindicaties: afnemersindicatie kan niet worden opgeslagen.
     */
    AFN012,

    /**
     * Autorisaties: Afnemersindicatie is verplicht.
     */
    AUT001,

    /**
     * Autorisaties: Ingangsdatum mag niet (gedeeltelijk) onbekend zijn.
     */
    AUT002,

    /**
     * Autorisaties: Ingangsdatum is verplicht.
     */
    AUT003,

    /**
     * Autorisaties: Afnemersnaam is verplicht.
     */
    AUT004,

    /**
     * Autorisaties: Verstrekkingsbeperking is verplicht.
     */
    AUT005,

    /**
     * Autorisaties: De einddatum mag niet voor de begindatum liggen.
     */
    AUT006,

    /**
     * Autorisaties: Er kan geen expressie gevonden worden voor de sleutelrubriek.
     */
    AUT007,

    /**
     * Autorisaties: Er kan geen (conv.convlo3)rubriek gevonden worden voor de filterrubriek.
     */
    AUT008,

    /**
     * Autorisaties: de voorwaarde op aantekening (15.42.10) is omgezet naar een standaard waarde.
     */
    AUT009,

    /**
     * Autorisaties: de voorwaarde op blokkering (07.66.20) is omgezet naar een standaard waarde.
     */
    AUT010,

    /**
     * Autorisaties: Einddatum mag niet (gedeeltelijk) onbekend zijn.
     */
    AUT011,

    /**
     * Autorisaties: Er mogen niet meerdere lege einddatums binnen een stapel van een autorisatie zijn voor een afnemer.
     */
    AUT012,

    /**
     * Autorisaties: plaatsingsbevoegd, maar geen spontaan autorisatie.
     */
    AUT013,

    /**
     * Autorisaties: partij kan niet gevonden worden obv afnemersindicatie.
     */
    AUT014,

    /**
     * Er is een geboorte geregistreerd in een onbekend land of een internationaal gebied.
     */
    BIJZ_CONV_LB001,

    /**
     * Er is een overlijden geregistreerd in een onbekend land of een internationaal gebied.
     */
    BIJZ_CONV_LB002,

    /**
     * Er is een huwelijk gesloten/geregistreerd partnerschap aangegaan in een onbekend land of een internationaal
     * gebied.
     */
    BIJZ_CONV_LB003,

    /**
     * Er is een huwelijk/geregistreerd partnerschap ontbonden in een onbekend land of een internationaal gebied.
     */
    BIJZ_CONV_LB004,

    /**
     * Het A-nummer is gewijzigd omdat er verschillende personen waren met hetzelfde A-nummer (zie [HUP], par 7.8):.
     */
    BIJZ_CONV_LB005,

    /**
     * Er is sprake van een dubbele inschrijving, waarbij één persoon met verschillende PL'en is ingeschreven met
     * hetzelfde A-nummer (zie [HUP], par 7.9). Het betreft hier de overblijvende PL.
     */
    BIJZ_CONV_LB006,

    /**
     * Er is sprake van een dubbele inschrijving, waarbij één persoon met verschillende PL'en is ingeschreven met
     * hetzelfde A-nummer (zie [HUP], par 7.9). Het betreft hier de overbodige/vervallen PL.
     */
    BIJZ_CONV_LB007,

    /**
     * Er is sprake van een dubbele inschrijving, waarbij één persoon met verschillende PL'en is ingeschreven met
     * verschillende A-nummers (zie [HUP], par 7.10). Het betreft hier de overbodige/vervallen PL.
     */
    BIJZ_CONV_LB008,

    /**
     * Er is sprake van een gedeeltelijke verstrekkingsbeperking.
     */
    BIJZ_CONV_LB009,

    /**
     * De persoonslijst is opgeschort met reden Onbekend.
     */
    BIJZ_CONV_LB010,

    /**
     * De persoonslijst is opgeschort met reden Fout.
     */
    BIJZ_CONV_LB011,

    /**
     * De persoon is geprivilegieerd.
     */
    BIJZ_CONV_LB012,

    /**
     * Er is sprake van een onbekende ouder.
     */
    BIJZ_CONV_LB013,

    /**
     * Er is sprake van juridisch gezien geen ouder.
     */
    BIJZ_CONV_LB014,

    /**
     * In één Huwelijk/geregistreerd partnerschap stapel zijn gegevens geregistreerd over zowel een huwelijk als een
     * geregistreerd partnerschap.
     */
    BIJZ_CONV_LB015,

    /**
     * In Ouder1 dan wel Ouder2 zijn meerdere personen opgenomen.
     */
    BIJZ_CONV_LB016,

    /**
     * De geldigheid van het Ouderlijk gezag loopt nog door terwijl de geldigheid van desbetreffende persoon al
     * beëindigd is.
     */
    BIJZ_CONV_LB017,

    /**
     * Zwakke adoptie.
     */
    BIJZ_CONV_LB021,

    /**
     * Gegevens zijn strijdig met de openbare orde.
     */
    BIJZ_CONV_LB022,

    /**
     * Er is een juiste historische categorie die een recentere ingangsdatum geldigheid heeft dan de actuele categorie.
     */
    BIJZ_CONV_LB024,

    /**
     * Er is een overlijden geregistreerd waarbij 85.10 Ingangsdatum geldigheid niet gelijk is aan 08.10 Datum
     * overlijden.
     */
    BIJZ_CONV_LB025,

    /**
     * Er is een signalering verstrekking Nederlands reisdocument geregistreerd waarbij 85.10 Ingangsdatum geldigheid
     * niet gelijk is aan 86.10 Datum van opneming.
     */
    BIJZ_CONV_LB026,

    /**
     * Bij historie conversie zijn gegevens genegeerd.
     */
    BIJZ_CONV_LB027,

    /**
     * Er is een Adres buitenland geregistreerd waarbij 85.10 Ingangsdatum geldigheid niet gelijk is aan 13.20 Datum
     * aanvang adres buitenland.
     */
    BIJZ_CONV_LB028,

    /**
     * Er is een verificatie uitgevoerd zonder dat de RNI-deelnemer geregistreerd is.
     */
    BIJZ_CONV_LB030,

    /**
     * Er is in categorie 07 een RNI-deelnemer geregistreerd zonder dat er een verificatie is uitgevoerd.
     */
    BIJZ_CONV_LB031,

    /**
     * Er is in categorie 07 een verdrag geregistreerd.
     */
    BIJZ_CONV_LB032,

    /**
     * Bij een buitenlands adres is uitsluitend Regel 1 adres buitenland opgenomen.
     */
    BIJZ_CONV_LB033,

    /**
     * In categorie 07 Inschrijving is 66.20 Datum ingang blokkering PL opgenomen.
     */
    BIJZ_CONV_LB034,

    /**
     * In categorie 01/51 is een ongeldige combinatie van adellijke titel/predicaat en geslachtsaanduiding opgenomen.
     */
    BIJZ_CONV_LB035,

    /**
     * In categorie 08/58 is Woonplaatsnaam gevuld met standaardwaarde en is minstens een van de volgende elementen niet
     * opgenomen: Naam openbare ruimte, Identificatiecode verblijfplaats of Identificatiecode nummeraanduiding.
     */
    BIJZ_CONV_LB036,

    /**
     * In categorie 08/58 is Woonplaatsnaam niet opgenomen, maar alle volgende elementen zijn wel opgenomen: Naam
     * openbare ruimte, Identificatiecode verblijfplaats en Identificatiecode nummeraanduiding.
     */
    BIJZ_CONV_LB037,

    /**
     * Een onderzoek dat in GBA, groep 83 Procedure is opgenomen, heeft niet geleid tot plaatsen van gegevens in
     * onderzoek in de BRP.
     */
    BIJZ_CONV_LB039,

    /**
     * In categorie 01/51 is Aanduiding naamgebruik niet opgenomen.
     */
    BIJZ_CONV_LB040,

    /**
     * Bij splitsing cat05 zijn er 1 of meer substapels (zijnde niet de laatste) die een juiste ontbinding bevat.
     */
    BIJZ_CONV_LB041,

    /**
     * Het Ouderlijk gezag start op een datum waarop geen familierechtelijke betrekking met een ouder bestaat.
     */
    BIJZ_CONV_LB042,

    /**
     * Element bevat geen geldige datum.
     */
    STRUC_DATUM,

    /**
     * Identificatienummer voldoet niet aan de inhoudelijke voorwaarden.
     */
    STRUC_IDENTIFICATIE,

    /**
     * Element is verplicht.
     */
    STRUC_VERPLICHT,

    /**
     * Categorie 07: Inschrijving voldoet niet aan de inhoudelijke voorwaarden.
     */
    STRUC_CATEGORIE_7,

    /**
     * Element 80.10: Versienummer bevat geen geldige waarde.
     */
    STRUC_VERSIENUMMER,

    /**
     * Categorie 12: Reisdocument mag alleen actuele categorie voorkomens hebben.
     */
    STRUC_CATEGORIE_12,

    /**
     * Categorie 13: Kiesrecht mag alleen actuele categorie voorkomens hebben.
     */
    STRUC_CATEGORIE_13,

    /**
     * Toevallige gebeurtenis: Groep 20 A-nummerverwijzingen niet toegestaan.
     */
    TG001,

    /**
     * Toevallige gebeurtenis: Groep 61 Naamgebruik niet toegestaan.
     */
    TG002,

    /**
     * Toevallige gebeurtenis: Groep 88 RNI-deelnemer niet toegestaan.
     */
    TG003,

    /**
     * Toevallige gebeurtenis: Groep 82 Document niet toegestaan.
     */
    TG005,

    /**
     * Toevallige gebeurtenis: Groep 83 Procedure niet toegestaan.
     */
    TG006,

    /**
     * Toevallige gebeurtenis: Groep 84 Onjuist niet toegestaan.
     */
    TG007,

    /**
     * Toevallige gebeurtenis: Groep 86 Opneming niet toegestaan.
     */
    TG009,

    /**
     * Toevallige gebeurtenis: De ontvangende gemeente (mailbox metadata) moet een geldige Nederlandse gemeente zijn.
     */
    TG010,

    /**
     * Toevallige gebeurtenis: De verzendende gemeente (mailbox metadata) moet een geldige Nederlandse gemeente zijn.
     */
    TG011,

    /**
     * Toevallige gebeurtenis: Het aktenummer moet een 'bekend' soort akte zijn.
     */
    TG012,

    /**
     * Toevallige gebeurtenis: Groep 08 Overlijden is verplicht in categorie 06 Overlijden.
     */
    TG013,

    /**
     * Toevallige gebeurtenis: Groep 02 Naam is verplicht in categorie 02/03/52/53 Ouder 1/2.
     */
    TG014,

    /**
     * Toevallige gebeurtenis: Groep 03 Geboorte is verplicht in categorie 02/03/52/53 Ouder 1/2.
     */
    TG015,

    /**
     * Toevallige gebeurtenis: Groep 04 Geslacht is verplicht in categorie 02/03 Ouder 1/2.
     */
    TG016,

    /**
     * Toevallige gebeurtenis: Groep 62 Familierechtelijke betrekking is verplicht in categorie 02/03/52/53 Ouder 1/2.
     */
    TG017,

    /**
     * Toevallige gebeurtenis: Er mag naast een actuele maximaal 1 historische categorie voorkomen.
     */
    TG018,

    /**
     * Toevallige gebeurtenis: Groep 02 Naam is verplicht in categorie 05/55 Huwelijk/Geregistreerde partnerschap.
     */
    TG019,

    /**
     * Toevallige gebeurtenis: Groep 03 Geboorte is verplicht in categorie 05/55 Huwelijk/Geregistreerde partnerschap.
     */
    TG020,

    /**
     * Toevallige gebeurtenis: Groep 07 Ontbinding is niet toegestaan in categorie 55 Huwelijk/Geregistreerde
     * partnerschap.
     */
    TG021,

    /**
     * Toevallige gebeurtenis: Groep 15 Soort verbintenis is verplicht in categorie 05/55 Huwelijk/Geregistreerde
     * partnerschap.
     */
    TG022,

    /**
     * Toevallige gebeurtenis: Element 81.10 Registergemeente akte moet overeenkomen met de verzendende gemeente.
     */
    TG023,

    /**
     * Toevallige gebeurtenis: Element 81.20 Nummer akte moet overeenkomen met het aktenummer in de header.
     */
    TG024,

    /**
     * Toevallige gebeurtenis: Alle 85.10 Ingangsdatum geldigheid in het bericht dienen overeen te komen.
     */
    TG025,

    /**
     * Toevallige gebeurtenis: Categorie 01 moet verplicht voorkomen.
     */
    TG026,

    /**
     * Toevallige gebeurtenis: Voor deze soort akte mag deze categorie alleen actueel voorkomen.
     */
    TG027,

    /**
     * Toevallige gebeurtenis: Voor deze soort akte mag deze categorie niet voorkomen.
     */
    TG028,

    /**
     * Toevallige gebeurtenis: Voor deze soort akte moet categorie 06 voorkomen.
     */
    TG029,

    /**
     * Toevallige gebeurtenis: Voor deze soort akte moet categorie 05 voorkomen.
     */
    TG031,

    /**
     * Toevallige gebeurtenis: Voor deze soort akte moet groep 06 Sluiting in categorie 05 Huwelijk/Geregistreerd
     * partnerschap voorkomen.
     */
    TG032,

    /**
     * Toevallige gebeurtenis: Voor deze soort akte mag groep 07 Ontbinding niet in categorie 05 Huwelijk/Geregistreerd
     * partnerschap voorkomen.
     */
    TG033,

    /**
     * Toevallige gebeurtenis: Voor deze soort akte moet groep 15 Soort verbintenis de een waarde hebben die
     * overeenkomst met de soort akte.
     */
    TG034,

    /**
     * Toevallige gebeurtenis: Groep 04 Geslacht is verplicht in categorie 05 Huwelijk/Geregistreerd partnerschap.
     */
    TG035,

    /**
     * Toevallige gebeurtenis: Groep 06 Sluiting is verplicht in categorie 55 Huwelijk/Geregistreerd partnerschap.
     */
    TG036,

    /**
     * Toevallige gebeurtenis: Voor deze soort akte moet categorie 55 voorkomen.
     */
    TG037,

    /**
     * Toevallige gebeurtenis: Voor deze soort akte mag groep 06 Sluiting niet in categorie 05 Huwelijk/Geregistreerd
     * partnerschap voorkomen.
     */
    TG038,

    /**
     * Toevallige gebeurtenis: Voor deze soort akte moet groep 07 Ontbinding in categorie 05 Huwelijk/Geregistreerd
     * partnerschap voorkomen.
     */
    TG039,

    /**
     * Toevallige gebeurtenis: Groep 02 uit categorie 05 moet overeenkomen met groep 02 uit categorie 55.
     */
    TG040,

    /**
     * Toevallige gebeurtenis: Groep 03 uit categorie 05 moet overeenkomen met groep 03 uit categorie 55.
     */
    TG041,

    /**
     * Toevallige gebeurtenis: Voor deze soort akte moet categorie 51 voorkomen.
     */
    TG042,

    /**
     * Toevallige gebeurtenis: Voor deze soort akte moeten element 02.30 en 02.40 uit categorie 01 overeenkomen met
     * elementen 02.30 en 02.40 uit categorie 51.
     */
    TG044,

    /**
     * Toevallige gebeurtenis: Groep 03 uit categorie 01 moet overeenkomen met groep 03 uit categorie 51.
     */
    TG045,

    /**
     * Toevallige gebeurtenis: Voor deze soort akte moet element 04.10 uit categorie 01 overeenkomen met element 04.10
     * uit categorie 51.
     */
    TG046,

    /**
     * Toevallige gebeurtenis: Groep 81 Akte niet toegestaan.
     */
    TG047,

    /**
     * Toevallige gebeurtenis: Groep 81 Akte moet voorkomen.
     */
    TG048,

    /**
     * Toevallige gebeurtenis: Groep 85 Geldigheid niet toegestaan.
     */
    TG049,

    /**
     * Toevallige gebeurtenis: Groep 85 Geldigheid moet voorkomen.
     */
    TG050,

    /**
     * Toevallige gebeurtenis: Groep 01 Identificatienummers is verplicht in categorie 01 Persoon.
     */
    TG051,

    /**
     * Toevallige gebeurtenis: Groep 02 Naam is verplicht in categorie 01/51 Persoon.
     */
    TG052,

    /**
     * Toevallige gebeurtenis: Groep 03 Geboorte is verplicht in categorie 01/51 Persoon.
     */
    TG053,

    /**
     * Toevallige gebeurtenis: Groep 04 Geslachtsaanduiding is verplicht in categorie 01/51 Persoon.
     */
    TG054,

    /**
     * Toevallige gebeurtenis: Groep 04 Geslachtsaanduiding is niet toegestaan in categorie 55 Huwelijk/Geregistreerd
     * partnerschap.
     */
    TG055,

    /**
     * Toevallige gebeurtenis: Groep 01 Identificatienummers is niet toegestaan in categorie 55 Huwelijk/Geregistreerd
     * partnerschap.
     */
    TG056,

    /**
     * Toevallige gebeurtenis: Voor deze soort akte moeten element 02.20 uit categorie 01 overeenkomen met element 02.20
     * uit categorie 51.
     */
    TG057,

    /**
     * Toevallige gebeurtenis: Voor deze soort akte moeten element 02.10 uit categorie 01 overeenkomen met element 02.10
     * uit categorie 51.
     */
    TG058,

    /**
     * Toevallige gebeurtenis: Groep 01 Identificatienummers is niet toegestaan in categorie 52/53 Ouder 1/2.
     */
    TG059,

    /**
     * Toevallige gebeurtenis: Groep 04 Geslachtsaanduiding is niet toegestaan in categorie 52/53 Ouder 1/2.
     */
    TG060,

    /**
     * Toevallige gebeurtenis: Voor deze soort akte moeten elementen 02.10, 03.10, 03.20, 03.30 en 04.10 uit categorie
     * 01 overeenkomen met elementen 02.10, 03.10, 03.20, 03.30 en 04.10 uit categorie 51.
     */
    TG061,

    /**
     * Toevallige gebeurtenis: De toevallig gebeurtenis moet plaatsvinden in Nederland. Hierdoor moet de landcode
     * overeenkomen met Nederland (6030).
     */
    TG062;

    /**
     * Geeft aan of het een preconditie betreft.
     * @return true als het een preconditie is, anders false
     */
    public boolean isPreconditie() {
        return name().startsWith("PRE");
    }

    /**
     * Geeft aan of het een structuurfout betreft.
     * @return true als het een structuurfout is, anders false
     */
    public boolean isStructuurFout() {
        return name().startsWith("STRUC");
    }
}
