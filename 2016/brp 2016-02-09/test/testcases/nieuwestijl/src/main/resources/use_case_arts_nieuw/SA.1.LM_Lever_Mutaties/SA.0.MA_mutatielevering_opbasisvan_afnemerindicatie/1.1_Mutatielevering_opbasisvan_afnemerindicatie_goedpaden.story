Meta:
@epic               Verbeteren testtooling
@auteur             kedon
@status             Klaar
@usecase            SA.0.MA
@regels             R1314,R1315,R1338,R1343,R1544,R1989,R1990,R1993,R1994,R2000,R2001,R2002
@sleutelwoorden     Mutatielevering o.b.v. afnemerindicatie

Narrative:
Mutatielevering op basis van Plaatsing afnemersindicatie:
De afnemer ontvangt voor de opgegeven Persoon een eerste Volledig bericht bij de plaatsing van de afnemerindicatie.
Zolang de afnemerindicatie nog geldig is worden alle geautoriseerde wijzigingen ontvangen via een mutatiebericht.


Scenario:   1.1 Plaatsen afnemerindicatie daarna verhuizing, waardoor de afnemer een mutatiebericht op basis van afnemerindicatie ontvangt
                Datum einde volgen is gevuld met toekomstige datum 2016-07-30
            Logische testgevallen Use Case: R1314_03, R1315_03, R1338_01, R1544_02, R1989_02, R1990_01, R1991_01, R1993_01, R2000_01, R2001_01, R2002_04
            Verwacht resultaat: Leveringsbericht & Afnemerindicatie geplaatst
                Met vulling:
                -  Soort bericht = Volledigbericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  leveringsautorisatie = Het leveringsautorisatie waarbinnen de Dienst wordt geleverd


Given de personen 299054457, 743274313, 981661129 zijn verwijderd
Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de standaardpersoon UC_Kenny met bsn 981661129 en anr 8545465106 zonder extra gebeurtenissen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 1.2_plaatsen_afnemerindicatie_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then ingaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-000000004560
Then uitgaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-000000004560

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   1.2 Plaatsen afnemerindicatie daarna verhuizing, waardoor de afnemer een mutatiebericht op basis van afnemerindicatie ontvangt
                Datum einde volgen is gevuld met toekomstige datum 2016-07-30
            Logische testgevallen Use Case: R1314_03, R1315_03, R1338_01, R1544_02, R1989_02, R1990_01, R1991_01, R1993_01, R2000_01, R2001_01, R2002_04
            Verwacht resultaat: Leveringsbericht
                Met vulling:
                -  Soort bericht = Mutatiebericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  leveringsautorisatie = Het leveringsautorisatie waarbinnen de Dienst wordt geleverd
                -  DatumEindeGeldigheid = Komt niet terug in het antwoordbericht
                -  DatumEindeVolgen = Komt niet terug in het antwoordbericht


Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 1.3_verhuizing_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'
Then heeft in het antwoordbericht 'soortNaam' in 'document' de waarde 'Aangifte met betrekking tot verblijfplaats'

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   2.1 Plaatsen afnemerindicatie daarna verhuizing, waardoor de afnemer een mutatiebericht op basis van afnemerindicatie ontvangt
                Datum einde volgen is LEEG
            Logische testgevallen Use Case: R1314_04, R1315_03, R1338_01, R1544_01, R1989_02, R1990_01, R1991_01, R1993_01, R2000_01, R2001_01, R2002_04
            Verwacht resultaat: Leveringsbericht & Afnemerindicatie geplaatst
                Met vulling:
                -  Soort bericht = Volledigbericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  leveringsautorisatie = Het leveringsautorisatie waarbinnen de Dienst wordt geleverd


Given de personen 299054457, 743274313, 981661129 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 981661129 en anr 8545465106 zonder extra gebeurtenissen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 1.4_plaatsen_afnemerindicatie_02.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide


Scenario:   2.2 Plaatsen afnemerindicatie daarna verhuizing, waardoor de afnemer een mutatiebericht op basis van afnemerindicatie ontvangt
                Datum einde volgen is LEEG
            Logische testgevallen Use Case: R1314_04, R1315_03, R1338_01, R1544_01, R1989_02, R1990_01, R1991_01, R1993_01,  R2000_01, R2001_01, R2002_04
            Verwacht resultaat: Leveringsbericht
                Met vulling:
                -  Soort bericht = Mutatiebericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  leveringsautorisatie = Het leveringsautorisatie waarbinnen de Dienst wordt geleverd
                -  DatumEindeGeldigheid = Komt niet terug in het antwoordbericht
                -  DatumEindeVolgen = Komt niet terug in het antwoordbericht

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 1.3_verhuizing_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   3.1 Plaatsen afnemerindicatie daarna huwelijk, waardoor de afnemer een mutatiebericht op basis van afnemerindicatie ontvangt
                Datum einde volgen is LEEG
            Logische testgevallen Use Case: R1314_04, R1315_03, R1338_01, R1544_01, R1989_02, R1990_01, R1991_01, R1993_01,  R2000_01, R2001_01, R2002_04
            Logisch testgeval:  R1267_01, R1268_11, R1268_12, R1269_07, R1270_10, R1270_11
            R1317_01, R1317_02, R1317_03, R1317_08, R1318_01, R1318_02, R1318_03, R1320_01, R1320_02, R1320_03, R1320_06, R1337, R1341_01, R1349_03
            R1547_03, R1548_02, R1549_01, R1550_01_LET_OP_REGEL_IS_VERVALLEN, R1551_01, R1552_01, R1555_01, R1557, R1614_02, R1617_08, R1619_01, R1620_01,
            R1621_02,
            R1974_01, R1975_01, R1976_01, R1980_01, R1986_01, R1988_01
            Verwacht resultaat: Leveringsbericht & Afnemerindicatie geplaatst
                Met vulling:
                -  Soort bericht = Volledigbericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  leveringsautorisatie = Het leveringsautorisatie waarbinnen de Dienst wordt geleverd

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de personen 556084329, 111812082 zijn verwijderd
Given de standaardpersoon UC_Kruimeltje met bsn 556084329 en anr 7863209234 zonder extra gebeurtenissen
Given de standaardpersoon UC_Lieze met bsn 111812082 en anr 4846512658 zonder extra gebeurtenissen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 1.5_plaatsen_afnemerindicatie_05.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide


Scenario:   3.2 Plaatsen afnemerindicatie daarna huwelijk, waardoor de afnemer een mutatiebericht op basis van afnemerindicatie ontvangt
                Datum einde volgen is LEEG
            Logische testgevallen Use Case: R1314_04, R1315_03, R1338_01, R1544_01, R1989_02, R1990_01, R1991_01, R1993_01, R2000_01, R2001_01, R2002_04
            Logisch testgeval:  R1267_01, R1268_01, R1268_02, R1269_07, R1270_10, R1270_11
            R1317_01, R1317_03, R1317_04, R1318_01, R1318_03, R1320_01, R1320_04, R1320_05, R1337, R1341_01, R1349_03
            R1547_02, R1548_03, R1549_03, R1550_03_LET_OP_REGEL_IS_VERVALLEN, R1551_01, R1552_01, R1557, R1614_02, R1617_08, R1619_01, R1620_01, R1621_02,
            R1974_01, R1975_01, R1976_01, R1980_01, R1986_01, R1988_01
            Verwacht resultaat: Leveringsbericht
                Met vulling:
                -  Soort bericht = Mutatiebericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  leveringsautorisatie = Het leveringsautorisatie waarbinnen de Dienst wordt geleverd
                -  DatumEindeGeldigheid = Komt niet terug in het antwoordbericht
                -  DatumEindeVolgen = Komt niet terug in het antwoordbericht
            Bevinding: Eerst werden 2 mutatieberichten verzonden, nu geen (ligt aan testomgeving)
Meta:
@regels     komkokm


Given de persoon beschrijvingen:
def UC_Lieze = uitDatabase bsn: 111812082
def UC_Kruimeltje = uitDatabase bsn: 556084329

Persoon.nieuweGebeurtenissenVoor(UC_Kruimeltje) {
huwelijk(aanvang: 20150501) {
          op 20150501 te 'Delft' gemeente 'Delft'
          met UC_Lieze
    }

}
slaOp(UC_Kruimeltje)

When voor persoon 556084329 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

And hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep                     | nummer | verwerkingssoort  |
| synchronisatie            | 1      | Toevoeging        |
| persoon                   | 1      | Wijziging         |
| afgeleidAdministratief    | 1      | Toevoeging        |
| afgeleidAdministratief    | 2      | Verval            |
| identificatienummers      | 1      | Identificatie     |

And hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| persoon 	            | 1         | soortCode 	        | I               |
| stuurgegevens 	    | 1         | zendendeSysteem 	    | BRP             |
| stuurgegevens 	    | 1         | zendendePartij 	    | 199903          |
| stuurgegevens 	    | 1         | ontvangendeSysteem 	| Leveringsysteem |
| samengesteldeNaam     | 1         | voornamen          	| Kruimeltje      |
| samengesteldeNaam     | 1         | geslachtsnaamstam     | Koster          |
| samengesteldeNaam     | 2         | voornamen          	| UC_Lieze        |
| samengesteldeNaam     | 2         | geslachtsnaamstam     | Borboni         |
| identificatienummers  | 1         | burgerservicenummer   | 556084329       |


And hebben attributen in voorkomens de volgende aanwezigheid:
| groep 	            | nummer | attribuut					| aanwezig 	|
| stuurgegevens	        | 1      | referentienummer  			| ja       	|
| stuurgegevens	        | 1      | tijdstipVerzending  			| ja       	|
| afgeleidAdministratief| 1      | actieInhoud                  | ja        |
| afgeleidAdministratief| 2      | actieVerval                  | ja        |
| stuurgegevens 	    | 1      | ontvangendePartij            | ja        |


Scenario:   4.1 Plaatsen afnemerindicatie, huisnummer in onderzoek, daarna verhuizing, waardoor de afnemer een mutatiebericht op basis van afnemerindicatie ontvangt
                Datum einde volgen is gevuld met toekomstige datum 2016-07-30
            Logische testgevallen Use Case: R1314_03, R1315_03, R1338_01, R1544_02,  R1989_03, R1990_01, R1991_01, R1993_01, R2000_01, R2001_01, R2002_04
            Verwacht resultaat: Leveringsbericht & Afnemerindicatie geplaatst
                Met vulling:
                -  Soort bericht = Volledigbericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  leveringsautorisatie = Het leveringsautorisatie waarbinnen de Dienst wordt geleverd

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de personen 299054457, 743274313, 981661129 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 981661129 en anr 8545465106 zonder extra gebeurtenissen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 1.2_plaatsen_afnemerindicatie_01.yml

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(981661129)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'20111231', omschrijving:'Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(persoon)

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then ingaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-000000004560
Then uitgaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-000000004560

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
groep 		                | attribuut            | verwachteWaardes
onderzoek                   | omschrijving         | Onderzoek is gestart op huisnummer
onderzoek                   | statusNaam           | In uitvoering

Scenario:   4.2 Plaatsen afnemerindicatie, huisnummer in onderzoek, daarna verhuizing, waardoor de afnemer een mutatiebericht op basis van afnemerindicatie ontvangt
                Datum einde volgen is gevuld met toekomstige datum 2016-07-30
            Logische testgevallen Use Case: R1314_03, R1315_03, R1338_01, R1544_02,  R1989_03, R1990_01, R1991_01, R1993_01, R2000_01, R2001_01, R2002_04
            Verwacht resultaat: Leveringsbericht & Afnemerindicatie geplaatst
                Met vulling:
                -  Soort bericht = Volledigbericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  leveringsautorisatie = Het leveringsautorisatie waarbinnen de Dienst wordt geleverd

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Olst.txt
Given bijhoudingsverzoek voor partij 'Gemeente Olst'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 1.6_verhuizing_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'
Then heeft in het antwoordbericht 'soortNaam' in 'document' de waarde 'Aangifte met betrekking tot verblijfplaats'

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

