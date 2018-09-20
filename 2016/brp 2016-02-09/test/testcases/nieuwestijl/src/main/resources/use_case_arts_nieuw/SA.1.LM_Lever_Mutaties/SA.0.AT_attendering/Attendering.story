Meta:
@epic               Verbeteren testtooling
@auteur             kedon
@status             Klaar
@usecase            SA.0.AT
@regels             R1334,R1983,R1993,R1994,R2000,R2001,R2002
@sleutelwoorden     Attendering

Narrative:
        Aanmaken volledigbericht.
        Indien door een administratieve handeling één of meerdere personen in de doelgroep van een abonnement
        met een geldige dienst Attendering van de afnemer vallen en er wordt voor die persoon of personen voldaan aan het attenderingscriterium,
        dan wordt een volledig bericht voor die persoon of personen aangemaakt.

        Het abonnement Attendering populatiebeperking op basis van geboortedatum heeft de populatiebeperking persoon.geboorte.datum >= 1997/03/15 en
        attenderingscriterium WAAR.
        De expressie op de populatiebeperking zal WAAR evalueren voor een persoon met de gedeeltelijk onbekende geboortedatum 1997/11/00.
        De expressie op de populatiebeperking zal NULL evalueren voor een persoon met de gedeeltelijk onbekende geboortedatum 1997/03/00.
        Log melding bij scenario 4: Populatiebeperking evalueert naar waarde NULL (onbekend) voor leveringsautorisatie: 'Attendering populatiebeperking op basis van geboortedatum' en persoon met id: 2129331

Scenario: 1.    Geboortedatum 1997/11/00 Populatiebeperking evalueert WAAR, Attenderingscriterium = WAAR.
                Logisch testgeval: R1334_01, R1559_02, R1560_02, R1983_02, R1993_01
                Logisch testgeval: R1994 LO3 koppelvlak
                Verwacht resultaat: Leveringsbericht
                    Met vulling:
                    -  Soort bericht = Volledigbericht
                    -  Persoon = De betreffende Persoon uit het bericht

Given leveringsautorisatie uit /levering_autorisaties/attendering_populatiebeperking_op_basis_van_geboortedatum
Given de personen 420075768 zijn verwijderd
Given de standaardpersoon Vanilla met bsn 420075768 en anr 1054894566 met extra gebeurtenissen:
verbeteringGeboorteakte(partij: 54101, aanvang: 19971101, toelichting:'onbekend en WAAR', registratieDatum: 20101010){
    op '1997/11/00' te 'Delft' gemeente 503
}

When voor persoon 420075768 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Attendering populatiebeperking op basis van geboortedatum is ontvangen en wordt bekeken
Then is het bericht xsd-valide


Scenario: 2.    Geboortedatum 1997/11/20 Populatiebeperking evalueert WAAR, Attenderingscriterium = WAAR, datum ingang abo = systeemdatum.
                Logisch testgeval: R1334_02, R1559_02, R1560_02, R1993_01
                Logisch testgeval: R1994 LO3 koppelvlak
                Verwacht resultaat: Leveringsbericht
                    Met vulling:
                    -  Soort bericht = Volledigbericht
                    -  Persoon = De betreffende Persoon uit het bericht


Given de personen 420075768 zijn verwijderd
Given de standaardpersoon Vanilla met bsn 420075768 en anr 1054894566 met extra gebeurtenissen:
verbeteringGeboorteakte(partij: 54101, aanvang: 19971120, toelichting:'onbekend en WAAR', registratieDatum: 20101010){
    op '1997/11/20' te 'Delft' gemeente 503
}

Given de database is aangepast met: update autaut.levsautorisatie set datingang ='${vandaagsql()}' where naam = 'Attendering populatiebeperking op basis van geboortedatum'
Given de cache is herladen

When voor persoon 420075768 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Attendering populatiebeperking op basis van geboortedatum is ontvangen en wordt bekeken
Then is het bericht xsd-valide


Scenario: 3.    Geboortedatum 1997/02/28 Populatiebeperking evalueert ONWAAR, Attenderingscriterium = WAAR.
                Logisch testgeval: R1334_03, R1559_02, R1560_02, R1993_01
                Logisch testgeval: R1994 LO3 koppelvlak
                Verwacht resultaat: GEEN volledig bericht

Given leveringsautorisatie uit /levering_autorisaties/attendering_populatiebeperking_op_basis_van_geboortedatum
Given de personen 420075768 zijn verwijderd
Given de standaardpersoon Vanilla met bsn 420075768 en anr 1054894566 met extra gebeurtenissen:
verbeteringGeboorteakte(partij: 54101, aanvang: 20101010, toelichting:'bekend en WAAR', registratieDatum: 20101010){
    op '1997/02/28' te 'Delft' gemeente 503
}
When voor persoon 420075768 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen

When volledigbericht voor leveringsautorisatie Attendering populatiebeperking op basis van geboortedatum wordt bekeken
Then is er geen synchronisatiebericht gevonden

When mutatiebericht voor leveringsautorisatie Attendering populatiebeperking op basis van geboortedatum wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 4.    Geboortedatum 1997/03/00 Populatiebeperking evalueert NULL, Attenderingscriterium = WAAR.
                Logisch testgeval: R1334_04, R1559_01, R1560_02, R1993_01
                Logisch testgeval: R1994 LO3 koppelvlak
                Verwacht resultaat: GEEN volledig bericht

Given leveringsautorisatie uit /levering_autorisaties/attendering_populatiebeperking_op_basis_van_geboortedatum
Given de personen 420075768 zijn verwijderd
Given de standaardpersoon Vanilla met bsn 420075768 en anr 1054894566 met extra gebeurtenissen:
verbeteringGeboorteakte(partij: 54101, aanvang: 20101010, toelichting:'onbekend en NULL', registratieDatum: 20101010){
    op '1967/03/00' te 'Delft' gemeente 503
}
When voor persoon 420075768 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen

When volledigbericht voor leveringsautorisatie Attendering populatiebeperking op basis van geboortedatum wordt bekeken
Then is er geen synchronisatiebericht gevonden

When mutatiebericht voor leveringsautorisatie Attendering populatiebeperking op basis van geboortedatum wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 5.    Geboortedatum 1997/11/20 Populatiebeperking evalueert WAAR, Attenderingscriterium = WAAR, datum einde abo = systeemdatum.
                Logisch testgeval: R1334_07, R1559_02, R1560_02, R1993_01
                Logisch testgeval: R1994 LO3 koppelvlak
                Verwacht resultaat: GEEN volledig bericht
                (Bevinding: DatumEindeGeldigheid = vandaag, wordt wel geleverd, maar hoort niet te leveren)
                    JIRA ISSUE: TEAMBRP-3992

Meta:
@status     Onderhanden
Given leveringsautorisatie uit /levering_autorisaties/attendering_populatiebeperking_op_basis_van_geboortedatum
Given de personen 420075768 zijn verwijderd
Given de standaardpersoon Vanilla met bsn 420075768 en anr 1054894566 met extra gebeurtenissen:
verbeteringGeboorteakte(partij: 54101, aanvang: 19671120, toelichting:'onbekend en WAAR', registratieDatum: 20101010){
    op '1967/11/20' te 'Delft' gemeente 503
}
Given de database is aangepast met: update autaut.levsautorisatie set dateinde ='${vandaagsql()}' where naam = 'Attendering populatiebeperking op basis van geboortedatum'
Given de cache is herladen

When voor persoon 420075768 wordt de laatste handeling geleverd
When volledigbericht voor leveringsautorisatie Attendering populatiebeperking op basis van geboortedatum wordt bekeken
Then is er geen synchronisatiebericht gevonden

When mutatiebericht voor leveringsautorisatie Attendering populatiebeperking op basis van geboortedatum wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 6.    Geboortedatum 1997/11/20 Populatiebeperking evalueert WAAR, Attenderingscriterium = WAAR, datum ingang abo > systeemdatum
                Logisch testgeval: R1334_08, R1559_02, R1560_02, R1993_01
                Logisch testgeval: R1994 LO3 koppelvlak
                Verwacht resultaat: GEEN volledig bericht

Given leveringsautorisatie uit /levering_autorisaties/attendering_populatiebeperking_op_basis_van_geboortedatum
Given de personen 420075768 zijn verwijderd
Given de standaardpersoon Vanilla met bsn 420075768 en anr 1054894566 met extra gebeurtenissen:
verbeteringGeboorteakte(partij: 54101, aanvang: 19971120, toelichting:'onbekend en WAAR', registratieDatum: 20101010){
    op '1997/11/20' te 'Delft' gemeente 503
}
Given de database is aangepast met: update autaut.levsautorisatie set datingang = '${vandaagsql(0,0,1)}' where naam = 'Attendering populatiebeperking op basis van geboortedatum'
Given de cache is herladen

When voor persoon 420075768 wordt de laatste handeling geleverd
When volledigbericht voor leveringsautorisatie Attendering populatiebeperking op basis van geboortedatum wordt bekeken
Then is er geen synchronisatiebericht gevonden

When mutatiebericht voor leveringsautorisatie Attendering populatiebeperking op basis van geboortedatum wordt bekeken
Then is er geen synchronisatiebericht gevonden


Scenario: 7.    Geboortedatum 1997/11/20 Populatiebeperking evalueert WAAR, Attenderingscriterium = WAAR, datum einde abo < systeemdatum
                Logisch testgeval: R1334_L09, R1559_02, R1560_02, R1993_01
                Logisch testgeval: R1994 LO3 koppelvlak
                Verwacht resultaat: GEEN volledig bericht

Given leveringsautorisatie uit /levering_autorisaties/attendering_populatiebeperking_op_basis_van_geboortedatum
Given de personen 420075768 zijn verwijderd
Given de standaardpersoon Vanilla met bsn 420075768 en anr 1054894566 met extra gebeurtenissen:
verbeteringGeboorteakte(partij: 54101, aanvang: 19971120, toelichting:'onbekend en WAAR', registratieDatum: 20101010){
    op '1997/11/20' te 'Delft' gemeente 503
}

Given de database is aangepast met: update autaut.levsautorisatie set dateinde = '${vandaagsql(0,0,-1)}' where naam='Attendering populatiebeperking op basis van geboortedatum'
Given de cache is herladen


When voor persoon 420075768 wordt de laatste handeling geleverd
When volledigbericht voor leveringsautorisatie Attendering populatiebeperking op basis van geboortedatum wordt bekeken
Then is er geen synchronisatiebericht gevonden

When mutatiebericht voor leveringsautorisatie Attendering populatiebeperking op basis van geboortedatum wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 8.1.  NIET RELEVANT VOOR USE CASE TESTSCENARIO
                Johnny en Anita gaan een voltrekking huwelijk in Nederland aan, check expressietaal, bericht wordt geleverd
                attenderingscriterium = Waar

Given leveringsautorisatie uit /levering_autorisaties/attendering_obv_wijziging_in_relatiegegevens_huwelijk
Given de personen 556645959 zijn verwijderd
Given de database is gereset voor de personen 126477735, 64258099
Given de persoon beschrijvingen:
def moederJohnny   = uitDatabase bsn: 64258099
def vaderJohnny    = uitDatabase bsn: 126477735

Johnny = uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19951225, toelichting: '1e kind', registratieDatum: 19951225) {
        op '1995/12/25' te 'Delft' gemeente 503
        geslacht 'MAN'
        namen {
            voornamen 'Johnny', 'James'
            geslachtsnaam 'Jansen'

        }
        ouders moeder: moederJohnny, vader: vaderJohnny
        identificatienummers bsn: 556645959, anummer: 5607075602
    }
}
slaOp(Johnny)
Given de database is gereset voor de personen 306867837, 306741817
Given de personen 754209878 zijn verwijderd
Given de persoon beschrijvingen:
def moederAnita = uitDatabase bsn: 306741817
def vaderAnita  = uitDatabase bsn: 306867837
def Johnny      = uitDatabase bsn: 556645959

Anita = uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19960108, toelichting: '1e kind', registratieDatum: 19960108) {
        op '1996/01/08' te 'Delft' gemeente 503
        geslacht 'VROUW'
        namen {
            voornamen 'Anita', 'Karen'
            geslachtsnaam 'Corner'

        }
        ouders moeder: moederAnita, vader: vaderAnita
        identificatienummers bsn: 754209878, anummer: 6248016146
    }
    huwelijk(aanvang: 20150501, registratieDatum: 20150501) {
          op 20150501 te 'Delft' gemeente 'Delft'
          met Johnny
    }
}
slaOp(Anita)
When voor persoon 754209878 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie attendering obv wijziging in relatiegegevens - huwelijk is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep   | nummer | attribuut    | verwachteWaarde |
| relatie | 2      | datumAanvang | 2015-05-01      |

Scenario: 8.2.  NIET RELEVANT VOOR USE CASE TESTSCENARIO
                Geslachtsnaam wordt gewijzigd van Kindje van Johnny en Anita, check expressietaal, bericht wordt geleverd
                attenderingscriterium = WAAR

Given leveringsautorisatie uit /levering_autorisaties/attendering_obv_wijziging_in_relatiegegevens_geslachtsnaam
Given de personen 564568041 zijn verwijderd
Given de persoon beschrijvingen:
def moederKindje = uitDatabase bsn: 754209878
def vaderKindje  = uitDatabase bsn: 556645959

Kindje = uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 20150530, toelichting: '1e kind', registratieDatum: 20150601) {
        op '2015/05/30' te 'Delft' gemeente 503
        geslacht 'VROUW'
        namen {
            voornamen 'Jennifer', 'Anita'
            geslachtsnaam 'Jansen'

        }
        ouders moeder: moederKindje, vader: vaderKindje
        identificatienummers bsn: 564568041, anummer: 3549026834
    }
    naamswijziging(aanvang:20150601, registratieDatum: 20150601) {
            geslachtsnaam(stam:'Jansen').wordt(stam:'Vries', voorvoegsel:'van')
    }
}
slaOp(Kindje)
When voor persoon 564568041 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie attendering obv wijziging in relatiegegevens - geslachtsnaam is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut         | verwachteWaarde |
| samengesteldeNaam  | 4      | geslachtsnaamstam | Vries           |


Scenario: 8.3.  Voornaam wordt gewijzigd van Kindje van Johnny en Anita, Populatiebeperking evalueert WAAR, Attenderingscriterium = ONWAAR.
                Logisch testgeval: R1334_L05, R1557_06, R1559_02, R1560_02, R1993_01
                Logisch testgeval: R1994 LO3 koppelvlak
                Verwacht resultaat: GEEN volledig bericht

Given leveringsautorisatie uit /levering_autorisaties/attendering_obv_wijziging_in_relatiegegevens_geslachtsnaam
Given de persoon beschrijvingen:
def Kindje = uitDatabase bsn: 564568041

Persoon.nieuweGebeurtenissenVoor(Kindje) {

    naamswijziging(aanvang:20150601, registratieDatum: 20150601) {
            geslachtsnaam(voorvoegsel:'van').wordt(voorvoegsel:'de')
    }
}
slaOp(Kindje)
When voor persoon 564568041 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie attendering obv wijziging in relatiegegevens - geslachtsnaam is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 9.    Geboortedatum 1997/11/00 Verstrekkingsbeperking voor partij
                Logisch testgeval: R1983_01
                Logisch testgeval: R1994 LO3 koppelvlak
                Verwacht resultaat: GEEN bericht
                Bevinding: bericht wordt wel geleverd


Given leveringsautorisatie uit /levering_autorisaties/attendering_populatiebeperking_op_basis_van_geboortedatum
Given de database is aangepast met: update kern.partij set indverstrbeperkingmogelijk = 'TRUE' where id=544
Given de cache is herladen
Given de database is aangepast met: update kern.partij set indverstrbeperkingmogelijk = 'FALSE' where id=544


Given de personen 420075768 zijn verwijderd
Given de standaardpersoon Vanilla met bsn 420075768 en anr 1054894566 met extra gebeurtenissen:
verbeteringGeboorteakte(partij: 54101, aanvang: 19670201, toelichting:'onbekend en WAAR', registratieDatum: 20101010){
    op '1967/02/01' te 'Delft' gemeente 503
}
verstrekkingsbeperking(aanvang: 20150601, registratieDatum: 20150601) {
    registratieBeperkingen( partij: 54101 )
}


When voor persoon 420075768 wordt de laatste handeling geleverd
When volledigbericht voor leveringsautorisatie Attendering populatiebeperking op basis van geboortedatum wordt bekeken
Then is er geen synchronisatiebericht gevonden

When mutatiebericht voor leveringsautorisatie Attendering populatiebeperking op basis van geboortedatum wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 10.    Geboortedatum 1997/11/00 Populatiebeperking evalueert WAAR, Attenderingscriterium = NULL.
                Logisch testgeval: R1334_06, R1559_02, R1560_02, R1983_02, R1993_01
                Logisch testgeval: R1994 LO3 koppelvlak
                Verwacht resultaat: Geen bericht
                Bevinding: bericht wordt wel geleverd
                    JIRA ISSUE: TEAMBRP-4490


Meta:
@status     Onderhanden
Given leveringsautorisatie uit /levering_autorisaties/attendering_populatiebeperking_op_basis_van_geboortedatum
Given de database is aangepast met: update autaut.dienst set attenderingscriterium =NULL
Given de cache is herladen

Given de personen 420075768 zijn verwijderd
Given de standaardpersoon Vanilla met bsn 420075768 en anr 1054894566 met extra gebeurtenissen:
verbeteringGeboorteakte(partij: 54101, aanvang: 19971101, toelichting:'onbekend en WAAR', registratieDatum: 20101010){
    op '1997/11/00' te 'Delft' gemeente 503
}

When voor persoon 420075768 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Attendering populatiebeperking op basis van geboortedatum is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
