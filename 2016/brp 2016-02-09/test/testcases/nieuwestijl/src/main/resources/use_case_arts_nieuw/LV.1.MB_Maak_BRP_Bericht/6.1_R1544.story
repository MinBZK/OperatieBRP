Meta:
@auteur             kedon
@status             Klaar
@usecase            LV.1.MB
@regels             R1544
@sleutelwoorden     Maak BRP bericht


Narrative:
Indien een VolledigBericht of een MutatieBericht wordt aangemaakt voor een aanwezige Persoon \ Afnemerindicatie, en in die Afnemerindicatie is Persoon \ Afnemerindicatie.Datum aanvang materiële periode gevuld, dan geldt dat bepaalde historische voorkomens van groepen uit de juridische persoonslijst niet geleverd worden:

Van groepen die een materiële historie kennen, mogen alleen voorkomens waarbij geldt dat DatumEindeGeldigheid (voor Onderzoeken is dit Onderzoek.Datum einde) > Persoon \ Afnemerindicatie.Datum aanvang materiële periode in het Bericht worden opgenomen.
Deze regel geldt voor zowel de inhoudelijke groepen als voor de onderzoeksgroepen.

Toelichting:
Als Persoon \ Afnemerindicatie.Datum aanvang materiële periode leeg is, dan mogen dus alle voorkomens geleverd worden.
Als DatumEindeGeldigheid leeg is, dan mag dat voorkomen altijd geleverd worden.
Als DatumEindeGeldigheid geheel of gedeeltelijk onbekend is, dan dient de (reguliere) 'soepele' vergelijking gebruikt te worden: als de bovenstaande conditie mogelijk WAAR kan zijn, dan wordt het voorkomen in het bericht opgenomen.

Woonplaats Haarlem is de geboortestad voor UC_Kenny
Huidige woonplaats Amsterdam word gebruikt als uitgangspunt in de mutatieberichten (dit in verband met de bevinding, dat er anders niet geleverd wordt))
Woonplaats Hillegom is Inhoudelijke groep 1
Woonplaats Amsterdam is Inhoudelijke groep 2

Bevinding: Het is niet datum einde geldigheid waar nu op gemeten wordt, maar op tijdstip registratie van de mutatie,
wanneer in de test een registratiedatum mee wordt gegeven uit het verleden, lijkt het systeem geen idee meer te hebben dat er een
mutatiebericht verstuurd moet worden.


Scenario: 1.1 Datum aanvang materiele periode LEEG en datum einde geldigheid LEEG voor inhoudelijke groep 1 adres
              en Datum aanvang materiele periode LEEG en datum einde geldigheid LEEG voor inhoudelijke groep 2 naamswijziging
              Logische testgeval: R1544_01
              Verwacht resultaat: Volldig bericht + plaatsen afnemerindicatie

Given de personen 299054457, 743274313, 449809353 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 449809353 en anr 2476721810 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 6.5_R1544_plaats_afnemerindicatie.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 1.2  Datum aanvang materiele periode LEEG en datum einde geldigheid LEEG voor inhoudelijke groep 1 adres
               en Datum aanvang materiele periode LEEG en datum einde geldigheid LEEG voor inhoudelijke groep 2 naamswijziging
               Logische testgeval: R1544_01
               Verwacht resultaat: Voorkomens inhoudelijke groep 1 en 2 geleverd in mutatiebericht

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    verhuizing(partij: 'Gemeente Hillegom', aanvang: 20120514, registratieDatum: 20120514) {
                naarGemeente 'Hillegom',
                    straat: 'Dorpsstraat', nummer: 25, postcode: '2180AA', woonplaats: "Hillegom"
        }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    verhuizing(partij: 'Gemeente Amsterdam', aanvang: {vandaag(0)}) {
                naarGemeente 'Amsterdam',
                    straat: 'Kerkstraat', nummer: 30, postcode: '2020AX', woonplaats: "Amsterdam"
        }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    naamswijziging(partij: 'Gemeente Haarlem', aanvang: 20101010, toelichting:'rijmelarij', registratieDatum: 20101010) {
        geslachtsnaam([stam:'McCormick']).wordt([stam:'Southpark', voorvoegsel:'van'])
    }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    naamswijziging(partij: 'Gemeente Haarlem', aanvang: {vandaag(0)}, toelichting:'rijmelarij') {
        geslachtsnaam([stam:'Southpark']).wordt([stam:'Kid', voorvoegsel:'de'])
    }
}
slaOp(UC_Kenny)

When voor persoon 449809353 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| samengesteldeNaam     | 1         | geslachtsnaamstam  	| Kid             |
| samengesteldeNaam     | 2         | geslachtsnaamstam  	| Southpark       |

Scenario: 1.3 Datum aanvang materiele periode LEEG en datum einde geldigheid LEEG voor inhoudelijke groep 1 adres
              en Datum aanvang materiele periode LEEG en datum einde geldigheid LEEG voor inhoudelijke groep 2 naamswijziging
              Logische testgeval: R1544_01
              Verwacht resultaat: Voorkomens inhoudelijke groep 1 en 2 geleverd in volledig bericht

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 6.6_R1544_Synchroniseer_Persoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| adres         	    | 1         | woonplaatsnaam 	    | Amsterdam       |
| adres         	    | 2         | woonplaatsnaam 	    | Hillegom        |
| adres         	    | 3         | woonplaatsnaam 	    | Haarlem         |

And hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| samengesteldeNaam     | 1         | geslachtsnaamstam  	| Kid             |
| samengesteldeNaam     | 2         | geslachtsnaamstam  	| Southpark       |
| samengesteldeNaam     | 3         | geslachtsnaamstam  	| McCormick       |

Scenario: 2.1 Datum aanvang materiele periode LEEG en datum einde geldigheid y  voor inhoudelijke groep 1
              en Datum aanvang materiele periode y en datum einde geldigheid LEEG voor inhoudelijke groep 2 adres
              Logisch testgeval: R1544_02
              Verwacht resultaat: Volldig bericht + plaatsen afnemerindicatie

Given de personen 299054457, 743274313, 449809353 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 449809353 en anr 2476721810 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 6.5_R1544_plaats_afnemerindicatie.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 2.2   Datum aanvang materiele periode LEEG en datum einde geldigheid y 20160101 voor inhoudelijke groep 1 Hillegom
                en Datum aanvang materiele periode LEEG en datum einde geldigheid LEEG voor inhoudelijke groep 2 Amsterdam
                Logisch testgeval: R1544_02
                Verwacht resultaat: voorkomens inhoudelijke groep 1 en 2 geleverd in mutatiebericht


Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    verhuizing(partij: 'Gemeente Hillegom', aanvang: 20120514, registratieDatum: 20120514) {
                naarGemeente 'Hillegom',
                    straat: 'Dorpsstraat', nummer: 25, postcode: '2180AA', woonplaats: "Hillegom"
        }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    verhuizing(partij: 'Gemeente Amsterdam', aanvang: 20160101, registratieDatum: 20160101) {
                naarGemeente 'Amsterdam',
                    straat: 'Kerkstraat', nummer: 30, postcode: '2020AX', woonplaats: "Amsterdam"
        }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    verhuizing(partij: 'Gemeente Amsterdam', aanvang: {vandaag(0)}) {
                naarGemeente 'Amsterdam',
                    straat: 'Kerkstraat', nummer: 31, postcode: '2020AX', woonplaats: "Amsterdam"
        }
}
slaOp(UC_Kenny)

When voor persoon 449809353 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| adres         	    | 1         | woonplaatsnaam 	    | Amsterdam       |
| adres         	    | 2         | woonplaatsnaam 	    | Amsterdam       |
| adres         	    | 3         | woonplaatsnaam 	    | Hillegom        |
| adres         	    | 4         | woonplaatsnaam 	    | Haarlem         |

Scenario: 2.3   Datum aanvang materiele periode LEEG en datum einde geldigheid y 20160101 voor inhoudelijke groep 1 Hillegom
                en Datum aanvang materiele periode LEEG en datum einde geldigheid LEEG voor inhoudelijke groep 2 Amsterdam
                Logisch testgeval: R1544_02
                Verwacht resultaat: voorkomens inhoudelijke groep 1 en 2 geleverd in volledig bericht


Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 6.6_R1544_Synchroniseer_Persoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| adres         	    | 1         | woonplaatsnaam 	    | Amsterdam       |
| adres         	    | 2         | woonplaatsnaam 	    | Amsterdam       |
| adres         	    | 3         | woonplaatsnaam 	    | Hillegom        |
| adres         	    | 4         | woonplaatsnaam 	    | Haarlem         |


