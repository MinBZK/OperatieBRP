Meta:
@epic               Verbeteren testtooling
@auteur             kedon
@status             Klaar
@usecase            SA.0.AA, SA.1.LM, LV.1.MB, LV.1.LE
@regels             R1334,R1335,R1983,R1993,R1994,R2000,R2057,R2060,R2062
@sleutelwoorden     Attendering met plaatsen afnemerindicatie, Lever Mutaties, Maak BRP Bericht, Leveren

Narrative:
    Afnemer heeft diensten 'Mutatielevering op afnemerindicatie' en 'Attendering met plaatsen afnemerindicatie'.
    Het attenderingscriterium is ingesteld op een wijziging van de bijhoudingsgemeente. De populatiebeperking
    eist dat de geboorte.woonplaatsnaam gelijk is aan "Giessenlanden", om leveringen op andere testcases uit te sluiten.

    Scenario 1: Persoon komt binnen doelbinding. Attendering plaatst een afnemerindicatie en stuurt een VolledigBericht
    Scenario 2: De betreffende persoon wordt nogmaals bijgehouden maar blijft binnen de doelbinding. Er wordt enkel een MutatieBericht verzonden

Scenario:   1. Persoon wordt geboren en komt in doelbinding van de Leverautorisatie terecht en wordt afnemerindicatie geplaatst.
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB:   Geen testgevallen
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.XV:   Geen testgevallen
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AU:   Geen testgevallen
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AL:   Geen testgevallen
            Logische testgevallen SA.1.LM Attendering mp Afn.SA.0.AA:   R1336_02, R1335_01, R1352_01, R1983_04, R1993_01, R2000_01, R2057_02, R2060_01, R2062_01
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.MR:   Geen testgevallen
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.VB:   Geen testgevallen
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB:   Geen testgevallen
            Logische testgevallen LV.1.MB Maak BRP bericht LV.1.MB:     R1341_01, R1267_01, R1315_03, R1316_05, R1974_01, R1975_01
            Logische testgevallen LV.1.MB Maak BRP bericht LV.1.MB.VB:  R1353_02, R1546_02, R1622_04
            Logische testgevallen LV.1.LE Leveren LV.1.PB:              R1614_01, R1616_02, R1617_01, R1618_07, R1619_05, R1620_03, R1996_01,
            Logische testgevallen LV.1.LE Leveren LV.1.VB:              R1985_01, R1991_01
            Logische testgevallen LV.1.LE Leveren LV.1.AB:              R1268_10, R1270_09
            Logische testgevallen LV.1.LE:                              R1612_01, R1995_02, R1997_03

            Verwacht resultaat: Leveringsbericht
                Met vulling:
                -   Soort bericht = Volledigbericht
                -   Persoon = De betreffende Persoon uit het bericht
                -   Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -   Leverautorisatie = De Leverautorisatie waarbinnen de Dienst wordt geleverd
                -   Zendende systeem = BRP
                -   Zendende partij  = 199903
                -   Ontvangende partij = 028101
                -   Ontvangende systeem = Leveringssysteem
                -   Referentienummer = gevuld
                -   Tijdstipverzending = Gevuld
            EN
            Verwacht resultaat: Afnemerindicatie geplaatst
                Database met vulling:
                •	Persoon \ Afnemerindicatie.Persoon = De betreffende Persoon
                •	Persoon \ Afnemerindicatie.Afnemer = De Partij waarvoor de Dienst (in combinatie met de Toegang leveringsautorisatie) wordt geleverd
                •	Persoon \ Afnemerindicatie.Leveringsautorisatie = De Leveringsautorisatie waarbinnen deDienst wordt geleverd
                •	Persoon \ Afnemerindicatie.Datum aanvang materiële periode = 'leeg'
                •	Persoon \ Afnemerindicatie.Datum einde volgen= 'leeg'}

Given leveringsautorisatie uit /levering_autorisaties/attendering_met_plaatsing_afnemerindicatie
Given de personen 420178648 zijn verwijderd
And de database is gereset voor de personen 306867837, 306741817
And de persoon beschrijvingen:
def adam    = Persoon.uitDatabase(bsn: 306867837)
def eva     = Persoon.uitDatabase(bsn: 306741817)

tester = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19911111, toelichting: '1e kind', registratieDatum: 19911111) {
        op '1991/11/11' te 'Giessenlanden' gemeente 689
        geslacht 'MAN'
        namen {
            voornamen 'Petrus', 'Matheus'
            geslachtsnaam 'Smith'

        }
        ouders moeder: eva, vader: adam
        identificatienummers bsn: 420178648, anummer: 1729786258
    }
}
slaOp(tester)

When voor persoon 420178648 wordt de laatste handeling geleverd

And het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

And hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| stuurgegevens 	    | 1         | zendendeSysteem 	    | BRP             |
| stuurgegevens 	    | 1         | zendendePartij 	    | 199903          |
| stuurgegevens 	    | 1         | ontvangendeSysteem 	| Leveringsysteem |
| parameters     	    | 1         | soortSynchronisatie 	| Volledigbericht |

And hebben attributen in voorkomens de volgende aanwezigheid:
| groep 	            | nummer | attribuut					| aanwezig 	|
| stuurgegevens	        | 1      | referentienummer  			| ja       	|
| stuurgegevens	        | 1      | tijdstipVerzending  			| ja       	|


Scenario:   2. Persoon wordt gewijzigd, blijft binnen doelbinding, afnemerindicatie bestaat reeds
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB:   Geen testgevallen
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.XV:   Geen testgevallen
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AU:   Geen testgevallen
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AL:   Geen testgevallen
            Logische testgevallen SA.1.LM Attendering mp Afn.SA.0.AA:   R1336_02, R1335_02, R1352_02, R1983_04, R1993_01, R2000_01, R2057_02, R2060_01, R2062_01
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.MR:   Geen testgevallen
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.VB:   Geen testgevallen
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB:   Geen testgevallen
            Logische testgevallen LV.1.MB Maak BRP bericht LV.1.MB:     R1341_01, R1267_01, R1315_03, R1316_05, R1544_01, R1974_01, R1975_01, R1980_01
            Logische testgevallen LV.1.MB Maak BRP bericht LV.1.MB.MB:  R1317_01, R1317_02, R1318_01, R1318_02, R1318_03, R1320_01, R1320_02, R1320_04, R1320_04, R1973_01, R1990_01
            Logische testgevallen LV.1.LE Leveren LV.1.PB:              R1613_02, R1614_02, R1616_02, R1617_01, R1618_07, R1619_05, R1620_03, R1996_01,
            Logische testgevallen LV.1.LE Leveren LV.1.VB:              R1985_01, R1991_01
            Logische testgevallen LV.1.LE Leveren LV.1.AB:              R1268_10, R1270_09
            Logische testgevallen LV.1.LE:                              R1612_01, R1995_02, R1997_03

            Verwacht resultaat: Leveringsbericht & Geen nieuwe Afnemerindicatie geplaatst
                Met vulling:
                -   Soort bericht = Mutatiebericht
                -   Persoon = De betreffende Persoon uit het bericht
                -   Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -   Leverautorisatie = De Leverautorisatie waarbinnen de Dienst wordt geleverd
                -   Zendende systeem = BRP
                -   Zendende partij  = 199903
                -   Ontvangende partij = 028101
                -   Ontvangende systeem = Leveringssysteem
                -   Referentienummer = gevuld
                -   Tijdstipverzending = Gevuld


Given de database is aangepast met: update autaut.dienst set attenderingscriterium = 'WAAR'
Given de cache is herladen

Given de persoon beschrijvingen:
def tester = uitDatabase bsn: 420178648

Persoon.nieuweGebeurtenissenVoor(tester) {

    naamswijziging(aanvang:20150601) {
            geslachtsnaam(stam:'Smith').wordt(stam:'Vries', voorvoegsel:'van')
    }
}
slaOp(tester)

When voor persoon 420178648 wordt de laatste handeling geleverd

And het mutatiebericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

And hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| stuurgegevens 	    | 1         | zendendeSysteem 	    | BRP             |
| stuurgegevens 	    | 1         | zendendePartij 	    | 199903          |
| stuurgegevens 	    | 1         | ontvangendeSysteem 	| Leveringsysteem |
| parameters     	    | 1         | soortSynchronisatie 	| Mutatiebericht  |

And hebben attributen in voorkomens de volgende aanwezigheid:
| groep 	            | nummer | attribuut					| aanwezig 	|
| stuurgegevens	        | 1      | referentienummer  			| ja       	|
| stuurgegevens	        | 1      | tijdstipVerzending  			| ja       	|

Then is de verwerkingssoort van groep synchronisatie in voorkomen 1, Toevoeging
Then is de verwerkingssoort van groep persoon in voorkomen 1, Wijziging
Then is de verwerkingssoort van groep afgeleidAdministratief in voorkomen 1, Toevoeging
Then is de verwerkingssoort van groep afgeleidAdministratief in voorkomen 2, Verval
Then is de verwerkingssoort van groep identificatienummers in voorkomen 1, Identificatie
Then is de verwerkingssoort van groep samengesteldeNaam in voorkomen 1, Toevoeging
Then is de verwerkingssoort van groep samengesteldeNaam in voorkomen 2, Wijziging
Then is de verwerkingssoort van groep samengesteldeNaam in voorkomen 3, Verval
Then is de verwerkingssoort van groep geboorte in voorkomen 1, Identificatie
Then is de verwerkingssoort van groep geslachtsaanduiding in voorkomen 1, Identificatie
Then is de verwerkingssoort van groep geslachtsnaamcomponent in voorkomen 1, Toevoeging
Then is de verwerkingssoort van groep geslachtsnaamcomponent in voorkomen 2, Wijziging
Then is de verwerkingssoort van groep geslachtsnaamcomponent in voorkomen 3, Verval
Then is de verwerkingssoort van groep naamgebruik in voorkomen 1, Toevoeging
Then is de verwerkingssoort van groep naamgebruik in voorkomen 2, Verval

When het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
