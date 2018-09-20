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
Huidige woonplaats Amsterdam word gebruikt als uitgangspunt in de mutatieberichten (dit in verband met de bevinding, dat er anders niet geleverd wordt)
Woonplaats Hillegom is Inhoudelijke groep 1
Woonplaats Amsterdam is Inhoudelijke groep 2

Bevinding: Het is niet datum einde geldigheid waar nu op gemeten wordt, maar op tijdstip registratie van de mutatie,
wanneer in de test een registratiedatum mee wordt gegeven uit het verleden, lijkt het systeem geen idee meer te hebben dat er een
mutatiebericht verstuurd moet worden.



Scenario: 3.1   Datum aanvang materiele periode 2012-05-15 < datum einde geldigheid voor inhoudelijke groep 1 en 2 adres
                Logische testgeval: R1544_03
                Verwacht resultaat: Volldig bericht + plaatsen afnemerindicatie

Given de personen 299054457, 743274313, 449809353 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 449809353 en anr 2476721810 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 6.2_R1544_plaats_afnemerindicatie.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 3.2   Datum aanvang materiele periode 2012-05-15 < datum einde geldigheid voor inhoudelijke groep 1 en 2 adres
                Logisch testgeval: R1544_03
                Verwacht resultaat: Voorkomens vanaf 2012-05-15 geleverd in mutatiebericht


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
    verhuizing(partij: 'Gemeente Amsterdam', aanvang: 20150520, registratieDatum: 20150520) {
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
Then heeft het bericht 3 groepen 'adres'
And hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| adres         	    | 1         | woonplaatsnaam 	    | Amsterdam       |
| adres         	    | 2         | woonplaatsnaam 	    | Amsterdam       |
| adres         	    | 3         | woonplaatsnaam 	    | Hillegom        |


Scenario: 3.3   Datum aanvang materiele periode 2012-05-15 < datum einde geldigheid voor inhoudelijke groep 1 en 2 adres
                Logisch testgeval: R1544_03
                Verwacht resultaat: Voorkomens vanaf 2012-05-15 geleverd in volledig bericht
                Bevinding: Haarlem wordt meegeleverd, omdat deze geen datum eindegeldigheid meekrijgt, hoewel dat aan de hand van de test wel zou moeten
                Het lijkt te komen omdat synchroniseer persoon niet levert op basis van de afnemerindicatie

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

Scenario: 4.1   Datum aanvang materiele periode 2012-05-14 = datum einde geldigheid voor inhoudelijke groep 1 en
                Datum aanvang materiele periode 2012-05-14< datum einde geldigheid voor inhoudelijke groep 2
                Logisch testgval: R1544_04
                Verwacht resultaat: Volldig bericht + plaatsen afnemerindicatie

Given de personen 299054457, 743274313, 449809353 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 449809353 en anr 2476721810 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 6.3_R1544_plaats_afnemerindicatie.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 4.2   Datum aanvang materiele periode 2012-05-14 = datum einde geldigheid voor inhoudelijke groep 1 en
                Datum aanvang materiele periode 2012-05-14< datum einde geldigheid voor inhoudelijke groep 2
                Logisch testgval: R1544_04
                Verwacht resultaat: Alleen voorkomens van inhoudelijke groep 2 geleverd in mutatiebericht

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    verhuizing(partij: 'Gemeente Hillegom', aanvang: 20110514, registratieDatum: 20110514) {
                naarGemeente 'Hillegom',
                    straat: 'Dorpsstraat', nummer: 25, postcode: '2180AA', woonplaats: "Hillegom"
        }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    verhuizing(partij: 'Gemeente Amsterdam', aanvang: 20120514, registratieDatum: 20120514) {
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
Then heeft het bericht 2 groepen 'adres'
And hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| adres         	    | 1         | woonplaatsnaam 	    | Amsterdam       |
| adres         	    | 2         | woonplaatsnaam 	    | Amsterdam       |


Scenario: 4.3   Datum aanvang materiele periode 2012-05-14 = datum einde geldigheid voor inhoudelijke groep 1 en
                Datum aanvang materiele periode 2012-05-14< datum einde geldigheid voor inhoudelijke groep 2
                Logisch testgval: R1544_04
                Verwacht resultaat: Alleen voorkomens van inhoudelijke groep 2 geleverd in volledigbericht
                Bevinding: Haarlem wordt meegeleverd, omdat deze geen datum eindegeldigheid meekrijgt, hoewel dat aan de hand van de test wel zou moeten
                Het lijkt te komen omdat synchroniseer persoon niet lever op basis van de afnemerindicatie

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


Scenario: 5.1   Datum aanvang materiele periode 2012-05-13 > datum einde geldigheid voor inhoudelijke groep 1 en
                Datum aanvang materiele periode 2012-05-13 < datum einde geldigheid voor inhoudelijke groep 2
                Logisch testgeval: R1544_05
                Verwacht resultaat: Volldig bericht + plaatsen afnemerindicatie

Given de personen 299054457, 743274313, 449809353 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 449809353 en anr 2476721810 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 6.4_R1544_plaats_afnemerindicatie.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 5.2   Datum aanvang materiele periode 2012-05-13 > datum einde geldigheid voor inhoudelijke groep 1 en
                Datum aanvang materiele periode 2012-05-13 < datum einde geldigheid voor inhoudelijke groep 2
                Logisch testgeval: R1544_05
                Verwacht resultaat: Alleen voorkomens van inhoudelijke groep 2 geleverd in mutatiebericht

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    verhuizing(partij: 'Gemeente Hillegom', aanvang: 20020514, registratieDatum: 20020514) {
                naarGemeente 'Hillegom',
                    straat: 'Dorpsstraat', nummer: 25, postcode: '2180AA', woonplaats: "Hillegom"
        }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    verhuizing(partij: 'Gemeente Amsterdam', aanvang: 20120420, registratieDatum: 20120420) {
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
Then heeft het bericht 2 groepen 'adres'
And hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| adres         	    | 1         | woonplaatsnaam 	    | Amsterdam       |
| adres         	    | 2         | woonplaatsnaam 	    | Amsterdam       |

Scenario: 5.3   Datum aanvang materiele periode 2012-05-13 > datum einde geldigheid voor inhoudelijke groep 1 en
                Datum aanvang materiele periode 2012-05-13 < datum einde geldigheid voor inhoudelijke groep 2
                Logisch testgeval: R1544_05
                Verwacht resultaat: Alleen voorkomens van inhoudelijke groep 2 geleverd in volledigbericht

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

Scenario: 6.1   Datum aanvang materiele periode = LEEG en  datum einde geldigheid = geheel onbekend voor inhoudelijke groep 1 en 2
                Logisch testgeval: R1544_06
                Verwacht resultaat: Volldig bericht + plaatsen afnemerindicatie
Meta:
@status Onderhanden

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

Scenario: 6.2   Datum aanvang materiele periode = LEEG en  datum einde geldigheid = geheel onbekend voor inhoudelijke groep 1 en 2
                Logisch testgeval: R1544_06
                Verwacht resultaat: voorkomens inhoudelijke groep 1 en 2 geleverd in mutatiebericht
                Bevinding: De wijziging van het eerste gelverde adres naar het tweede komt niet mee in het antwoordbericht bij volledig onbekende datum einde geldigheid
Meta:
@status Onderhanden

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
    verhuizing(partij: 'Gemeente Amsterdam', aanvang: 00000000, registratieDatum: 00000000) {
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
Then heeft het bericht 2 groepen 'adres'
And hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| adres         	    | 1         | woonplaatsnaam 	    | Amsterdam       |
| adres         	    | 2         | woonplaatsnaam 	    | Amsterdam       |
| adres         	    | 3         | woonplaatsnaam 	    | Amsterdam       |


Scenario: 6.3   Datum aanvang materiele periode = LEEG en  datum einde geldigheid = geheel onbekend voor inhoudelijke groep 1 en 2
                Logisch testgeval: R1544_06
                Verwacht resultaat: voorkomens inhoudelijke groep 1 en 2 geleverd in volledigbericht
Meta:
@status Onderhanden

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

Scenario: 7.1   Datum aanvang materiele periode x > gedeeltijk onbekende datum einde geldigheid (soepele regel) voor inhoudelijke groep 1
                en gedeeltelijk onbekende Datum aanvang materiele periode x <  LEGE datum einde geldigheid y groep 2
                Logisch testgeval: R1544_07
                Verwacht resultaat: Volldig bericht + plaatsen afnemerindicatie
Meta:
status  Onderhanden

Given de personen 299054457, 743274313, 449809353 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 449809353 en anr 2476721810 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 6.2_R1544_plaats_afnemerindicatie.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 7.2   Datum aanvang materiele periode x > gedeeltijk onbekende datum einde geldigheid y (soepele regel) voor inhoudelijke groep 1
                en gedeeltelijk onbekende Datum aanvang materiele periode x <  LEGE datum einde geldigheid y groep 2
                Logisch testgeval: R1544_07
                Verwacht resultaat: Voorkomens van inhoudelijke groep 2 geleverd in mutatiebericht
                Bevinding: gedeeltelijk onbekende datum < Datum aanvang materiele periode wordt wel geleverd (Inh.Gr.1), terwijl de regel stelt dat
                dit niet zou mogen
Meta:
status  Onderhanden

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    verhuizing(partij: 'Gemeente Hillegom', aanvang: 20120400, registratieDatum: 20120400) {
                naarGemeente 'Hillegom',
                    straat: 'Dorpsstraat', nummer: 25, postcode: '2180AA', woonplaats: "Hillegom"
        }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    verhuizing(partij: 'Gemeente Amsterdam', aanvang: 20150500, registratieDatum: 20120500) {
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
Then heeft het bericht 2 groepen 'adres'
And hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| adres         	    | 1         | woonplaatsnaam 	    | Amsterdam       |
| adres         	    | 2         | woonplaatsnaam 	    | Amsterdam       |

Scenario: 7.3   Datum aanvang materiele periode x > gedeeltijk onbekende datum einde geldigheid y (soepele regel) voor inhoudelijke groep 1
                en gedeeltelijk onbekende Datum aanvang materiele periode x <  LEGE datum einde geldigheid y groep 2
                Logisch testgeval: R1544_07
                Verwacht resultaat: Voorkomens van inhoudelijke groep 2 geleverd in volledigbericht
Meta:
status  Onderhanden

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


Scenario: 8.1   Datum aanvang materiele periode LEEG datum einde onderzoek en LEEG voor groep 1 en
                Datum aanvang materiele periode LEEG datum einde onderzoek en LEEG voor groep 2
                Logisch testgeval: R1544_08
                Verwacht resultaat: Voorkomens beide groepen geleverd

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de personen 299054457, 743274313, 449809353 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 449809353 en anr 2476721810 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'20111231', omschrijving:'Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'20111231', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(UC_Kenny)

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 6.5_R1544_plaats_afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 449809353 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde                       |
| onderzoek             | 2         | omschrijving          | Onderzoek is gestart op huisnummer    |
| gegevenInOnderzoek    | 1         | elementNaam           | Persoon.Adres.Huisnummer              |
| onderzoek             | 4         | omschrijving          | Onderzoek is gestart op postcode      |
| gegevenInOnderzoek    | 2         | elementNaam           | Persoon.Adres.Postcode                |

Scenario: 9.1   Datum aanvang materiele periode LEEG en datum einde onderzoek LEEG voor groep 1 en
                Datum aanvang materiele periode LEEG en datum einde onderzoek gevuld voor groep 2
                Logisch testgeval: R1544_09
                Verwacht resultaat: Voorkomens beide groepen geleverd

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de personen 299054457, 743274313, 449809353 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 449809353 en anr 2476721810 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'20111231', omschrijving:'Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'20111231', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(UC_Kenny)

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 6.5_R1544_plaats_afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 449809353 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde                       |
| onderzoek             | 2         | omschrijving          | Onderzoek is gestart op huisnummer    |
| gegevenInOnderzoek    | 1         | elementNaam           | Persoon.Adres.Huisnummer              |
| onderzoek             | 4         | omschrijving          | Onderzoek is gestart op postcode      |
| gegevenInOnderzoek    | 2         | elementNaam           | Persoon.Adres.Postcode                |



