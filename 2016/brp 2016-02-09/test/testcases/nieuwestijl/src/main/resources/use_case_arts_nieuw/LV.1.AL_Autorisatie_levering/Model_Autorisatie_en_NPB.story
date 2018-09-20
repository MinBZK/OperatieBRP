Meta:
@sprintnummer           91
@epic                   Autenticatie levering
@auteur                 aapos
@jiraIssue              TEAMBRP-4519
@status                 Klaar
@regels                 R1260,R1261

Narrative: Als beheerder van het BRP wil ik modelautorisaties kunnen gebruiken om verschillende partijen toegang te geven tot het BRP met dezelfde autorisaties

Scenario:   1. Modelautorisatie Bevraging

!-- Protocollering lijkt nog niet goed plaats te vinden, alle verzoeken die via de model autorisatie plaats vinden moeten geprotocolleerd worden

Given leveringsautorisatie uit /levering_autorisaties/modelAutorisaties/model_autorisatie_bevraging

Given de personen 627129705, 304953337, 767781193 zijn verwijderd
Given de standaardpersoon Olivia met bsn 767781193 en anr 9620946706 zonder extra gebeurtenissen

Given verzoek voor leveringsautorisatie 'model autorisatie voor bevraging' en partij 'Gemeente Delft' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_bvgGeefDetailsPersoon
And testdata uit bestand geefDetailsPersoon_1.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes                |
| identificatienummers | burgerservicenummer | 767781193, 627129705, 304953337 |


Given verzoek voor leveringsautorisatie 'model autorisatie voor bevraging' en partij 'Gemeente Alkmaar' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_bvgGeefDetailsPersoon
And testdata uit bestand geefDetailsPersoon_1.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes                |
| identificatienummers | burgerservicenummer | 767781193, 627129705, 304953337 |

Scenario:   2A. Modelautorisatie Mutatielevering op afnemerindicatie
Narrative:
 Plaatsen van afnemerindicaties voor specfieke partij zit in TEAMBRP-4390
A. Plaats afnemer indicatie met partij x bij persoon, Partij x ontvangt een volledig bericht
B. Mutatie bij persoon, enkel partij x ontvangt mutatie bericht
C. Partij y plaatst ook afn ind bij persoon, enkel partij y ontvangt volledigbericht
D. Mutatie bij persoon, nu ontvangen partij x & y mutatie bericht
E. Verwijder afnemer indicatie bij partij y, partij x kan synchroniseren, partij y niet

Given leveringsautorisatie uit /levering_autorisaties/modelAutorisaties/model_autorisatie_levering_obv_afn_ind
Given de personen 627129705, 304953337, 630668681 zijn verwijderd
Given de standaardpersoon Olivia met bsn 630668681 en anr 8654063890 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(630668681)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Delft', aanvang: 20100212, registratieDatum: 20100212) {
            naarGemeente 'Delft',
                straat: 'Markt', nummer: 87, postcode: '2611GW', woonplaats: "Delft"
        }
}
slaOp(persoon)

When voor persoon 630668681 wordt de laatste handeling geleverd

Given verzoek voor leveringsautorisatie 'model autorisatie obv afnemerindicatie' en partij 'Gemeente Delft' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_plaats.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor partij 50301 en leveringsautorisatie model autorisatie obv afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

When het volledigbericht voor partij 36101 en leveringsautorisatie model autorisatie obv afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario:   2B. Modelautorisatie Mutatielevering op afnemerindicatie
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(630668681)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Delft', aanvang: 20160120) {
            naarGemeente 'Delft',
                straat: 'Geestkerkhof', nummer: 25, postcode: '2611HP', woonplaats: "Delft"
        }
}
slaOp(persoon)

When voor persoon 630668681 wordt de laatste handeling geleverd
When het mutatiebericht voor partij 50301 en leveringsautorisatie model autorisatie obv afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

When het mutatiebericht voor partij 36101 en leveringsautorisatie model autorisatie obv afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 2C. Modelautorisatie Mutatielevering op afnemerindicatie
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(630668681)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Alkmaar', aanvang: 20160121) {
            naarGemeente 'Alkmaar',
                straat: 'Mallegatsplein', nummer: 12, postcode: '1815AG', woonplaats: "Alkmaar"
        }
}
slaOp(persoon)

Given verzoek voor leveringsautorisatie 'model autorisatie obv afnemerindicatie' en partij 'Gemeente Alkmaar' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_plaats.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 630668681 wordt de laatste handeling geleverd
When het mutatiebericht voor partij 50301 en leveringsautorisatie model autorisatie obv afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| melding	            | 1         | soortNaam 	        | Waarschuwing    |
| melding	            | 1         | regelCode 	        | BRLV0027    |
| melding	            | 1         | melding    	        | De geleverde persoon valt niet meer binnen de doelbindingspopulatie van de leveringsautorisatie.|

When het volledigbericht voor partij 36101 en leveringsautorisatie model autorisatie obv afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 2D. Modelautorisatie Mutatielevering op afnemerindicatie

Given verzoek voor leveringsautorisatie 'model autorisatie obv afnemerindicatie' en partij 'Gemeente Delft' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_verwijder.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Given wacht 60 seconden

Scenario: 2E. Modelautorisatie Mutatielevering op afnemerindicatie
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(630668681)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Alkmaar', aanvang: 20160122) {
            naarGemeente 'Alkmaar',
                straat: 'Mallegatsplein', nummer: 22, postcode: '1815AG', woonplaats: "Alkmaar"
        }
}
slaOp(persoon)
When voor persoon 630668681 wordt de laatste handeling geleverd

When het mutatiebericht voor partij 36101 en leveringsautorisatie model autorisatie obv afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

When het mutatiebericht voor partij 50301 en leveringsautorisatie model autorisatie obv afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden


Scenario:   3A. Modelautorisatie nadere populatie beperking en Attendering met plaatsing afn ind
Narrative: Er is een NPB opgenomen in toegangleveringsautorisatie die de levering naar de verschillende partijen afbakend

- Via de dienst attendering met plaatsing wordt er een afn ind geplaatst bij persoon (bijv nav een verhuizing)
- Een persoon komt in een gebied van gemeente x, waardoor gemeente x een volledig bericht ontvangt van de persoon en gemeente y niet
- Gemeente Y kan geen afn ind bij de persoon plaatsen (valt niet binnen doelbinding)
- Persoon verhuist naar gemeente Y, gemeente x ontvangt met mutatie bericht met melding, gemeente Y ontvangt volledig bericht nav auto plaatsen afn. ind.
- Gemeente X verwijdert afnemer indicatie bij persoon

Given leveringsautorisatie uit /levering_autorisaties/modelAutorisaties/model_autorisatie_npb_attendering
Given de personen 627129705, 304953337, 704181897 zijn verwijderd
Given de standaardpersoon Olivia met bsn 704181897 en anr 6176182034 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(704181897)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Delft', aanvang: 20100212, registratieDatum: 20100212) {
            naarGemeente 'Delft',
                straat: 'Markt', nummer: 87, postcode: '2611GW', woonplaats: "Delft"
        }
}
slaOp(persoon)

When voor persoon 704181897 wordt de laatste handeling geleverd
When het volledigbericht voor partij 50301 en leveringsautorisatie model autorisatie obv attendering met afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

When het volledigbericht voor partij 36101 en leveringsautorisatie model autorisatie obv attendering met afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 3B. Modelautorisatie Attendering met plaatsing

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(704181897)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Delft', aanvang: 20160112) {
            naarGemeente 'Delft',
                straat: 'Geestkerkhof', nummer: 25, postcode: '2611HP', woonplaats: "Delft"
        }
}
slaOp(persoon)

When voor persoon 704181897 wordt de laatste handeling geleverd
When het mutatiebericht voor partij 50301 en leveringsautorisatie model autorisatie obv attendering met afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

When het mutatiebericht voor partij 36101 en leveringsautorisatie model autorisatie obv attendering met afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 3C. Modelautorisatie Attendering met plaatsing
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(704181897)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Alkmaar', aanvang: 20160114) {
            naarGemeente 'Alkmaar',
                straat: 'Mallegatsplein', nummer: 12, postcode: '1815AG', woonplaats: "Alkmaar"
        }
}
slaOp(persoon)

When voor persoon 704181897 wordt de laatste handeling geleverd
When het mutatiebericht voor partij 50301 en leveringsautorisatie model autorisatie obv attendering met afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| melding	            | 1         | soortNaam 	        | Waarschuwing    |
| melding	            | 1         | regelCode 	        | BRLV0027    |
| melding	            | 1         | melding    	        | De geleverde persoon valt niet meer binnen de doelbindingspopulatie van de leveringsautorisatie.|

When het volledigbericht voor partij 36101 en leveringsautorisatie model autorisatie obv attendering met afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide


Scenario: 3D. Modelautorisatie Attendering met plaatsing
Given verzoek voor leveringsautorisatie 'model autorisatie obv attendering met afnemerindicatie' en partij 'Gemeente Delft' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_verwijder_2.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given verzoek voor leveringsautorisatie 'model autorisatie obv attendering met afnemerindicatie' en partij 'Gemeente Alkmaar' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_verwijder_3.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 4a. Modelautorisatie Mutatielevering op doelbinding
Narrative: Er is een NPB opgenomen in de toegangleveringsautorisatie die levering naar de verschillende partijen afbakend

a. Persoon komt nieuw binnen doelbinding (door bijv een verhuizing), gemeente X ontvangt een volledigbericht
b. Mutatie bij persoon, blijft binnen doelbinding, gemeente X krijgt mutatie bericht
c. Persoon verhuist naar gemeente Y, gemeente Y ontvangt volledig bericht, gemeente X ontvangt mutatie bericht met melding dat persoon doelbinding heeft verlaten
d. Gemeente X kan de persoon nu niet meer synchroniseren, Gemeente Y kan de persoon wel synchroniseren

Gemeente Alkmaar = 36101
Gemeente Delft = 50301

Given leveringsautorisatie uit /levering_autorisaties/modelAutorisaties/model_autorisatie_doelbinding
Given de personen 627129705, 304953337, 533493225 zijn verwijderd
Given de standaardpersoon Olivia met bsn 533493225 en anr 7415937298 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(533493225)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Delft', aanvang: 20100212, registratieDatum: 20100212) {
            naarGemeente 'Delft',
                straat: 'Markt', nummer: 87, postcode: '2611GW', woonplaats: "Delft"
        }
}
slaOp(persoon)

When voor persoon 533493225 wordt de laatste handeling geleverd
When het volledigbericht voor partij 50301 en leveringsautorisatie model autorisatie obv doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut              | verwachteWaarde                       |

When het volledigbericht voor partij 36101 en leveringsautorisatie model autorisatie obv doelbinding is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 4b. Modelautorisatie Mutatielevering op doelbinding

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(533493225)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Delft', aanvang: 20110312, registratieDatum: 20110312) {
            naarGemeente 'Delft',
                straat: 'Geestkerkhof', nummer: 25, postcode: '2611HP', woonplaats: "Delft"
        }
}
slaOp(persoon)

When voor persoon 533493225 wordt de laatste handeling geleverd
When het mutatiebericht voor partij 50301 en leveringsautorisatie model autorisatie obv doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

When het mutatiebericht voor partij 36101 en leveringsautorisatie model autorisatie obv doelbinding is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 4c. Modelautorisatie Mutatielevering op doelbinding
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(533493225)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Alkmaar', aanvang: 20120412, registratieDatum: 20120412) {
            naarGemeente 'Alkmaar',
                straat: 'Mallegatsplein', nummer: 12, postcode: '1815AG', woonplaats: "Alkmaar"
        }
}
slaOp(persoon)

When voor persoon 533493225 wordt de laatste handeling geleverd
When het mutatiebericht voor partij 50301 en leveringsautorisatie model autorisatie obv doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| melding	            | 1         | soortNaam 	        | Waarschuwing    |
| melding	            | 1         | regelCode 	        | BRLV0028    |
| melding	            | 1         | melding    	        | De geleverde persoon heeft de doelbindingspopulatie verlaten. Mutatielevering voor deze persoonslijst is gestopt.|

When voor persoon 533493225 wordt de laatste handeling geleverd
When het volledigbericht voor partij 36101 en leveringsautorisatie model autorisatie obv doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 4d. Modelautorisatie Mutatielevering op doelbinding - synchroniseer persoon

Given verzoek voor leveringsautorisatie 'model autorisatie obv doelbinding' en partij 'Gemeente Alkmaar' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor partij 36101 en leveringsautorisatie model autorisatie obv doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Given verzoek voor leveringsautorisatie 'model autorisatie obv doelbinding' en partij 'Gemeente Delft' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                                    |
| BRLV0023 | De opgegeven persoon valt niet te synchroniseren binnen de opgegeven leveringsautorisatie. |
