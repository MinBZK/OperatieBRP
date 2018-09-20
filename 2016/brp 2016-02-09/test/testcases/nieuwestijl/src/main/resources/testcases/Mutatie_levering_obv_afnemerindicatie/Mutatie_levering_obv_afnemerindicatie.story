Meta:
@sprintnummer
@epic
@auteur             aapos
@jiraIssue          TEAMBRP-4429
@status             Klaar
@regels
@sleutelwoorden     Mutatie Levering obv afnemerindicatie

Narrative:
Als afnemer met de diensten mutatie levering obv afnemerindicatie en onderhoud afnemer indicaties
wil ik mutatie leveringen ontvangen van personen uit BRP
zodat ik gewijzigde persoonsgegevens kan inzien.

Abonnement = postcode gebied Hillegom 2180 - 2182 (partij 505005)

|                              | Plaatsing                                        | Verwijdering                                     |
| buiten doelbinding           | Scenario 1                                       | Scenario 4                                       |
| binnen doelbinding           | Scenario 2                                       | Scenario 5                                       |

| Mutatie Levering obv afn ind. zonder waarschuwing | Scenario 3             |
| Mutatie Levering obv afn ind. met waarschuwing    | Scenario 4             |
| Geen Mutatie Levering obv afn ind.                | Scenario 1, Scenario 5 |

Scenario: 1. Plaatsing afnemer indicatie bij persoon die buiten doelbinding valt
Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_afn_ind/postcode_gebied_Hillegom_2180-2182

Given de personen 875271467, 814591139, 422751625 zijn verwijderd
Given de standaardpersoon Gregory met bsn 422751625 en anr 5367617810 zonder extra gebeurtenissen

Given verzoek voor leveringsautorisatie 'postcode gebied Hillegom 2180 - 2182' en partij 'SRPUC50151-5-Partij'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerindicatie_plaatsen.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'De persoon valt niet binnen de populatie waarop de afnemer in dit abonnement een indicatie mag plaatsen.'


Scenario: 2. Plaatsing afnemer indicatie bij persoon die binnen doelbinding valt

Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_afn_ind/postcode_gebied_Hillegom_2180-2182

Given de personen 875271467, 814591139, 422751625 zijn verwijderd
Given de standaardpersoon Gregory met bsn 422751625 en anr 4023907602 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(422751625)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 53401, aanvang: 20130510, registratieDatum: 20130510) {
        naarGemeente 'Hillegom',
            straat: 'Hoofdstraat', nummer: 115, postcode: '2181EC', woonplaats: "Hillegom"
    }
}
slaOp(persoon)

Given verzoek voor leveringsautorisatie 'postcode gebied Hillegom 2180 - 2182' en partij 'SRPUC50151-5-Partij'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerindicatie_plaatsen.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is er voor persoon met bsn 422751625 en leveringautorisatie postcode gebied Hillegom 2180 - 2182 een afnemerindicatie geplaatst

When voor persoon 422751625 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie postcode gebied Hillegom 2180 - 2182 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep                | nummer | attribuut           | verwachteWaarde                      |
| parameters           | 1      | categorieDienst     | Onderhouden afnemerindicatie         |
| parameters           | 1      | soortSynchronisatie | Volledigbericht                      |
| parameters           | 1      | abonnementNaam      | postcode gebied Hillegom 2180 - 2182 |
| identificatienummers | 1      | burgerservicenummer | 422751625                            |

Scenario: 3. Mutatielevering persoon met afnemerindicatie binnen doelbinding
Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_afn_ind/postcode_gebied_Hillegom_2180-2182
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(422751625)
nieuweGebeurtenissenVoor(persoon) {
   verhuizing(partij: 53401, aanvang: 20140510, registratieDatum: 20140510) {
           naarGemeente 'Hillegom',
               straat: 'Hoofdstraat', nummer: 34, postcode: '2181EC', woonplaats: "Hillegom"
       }
   }
slaOp(persoon)

When voor persoon 422751625 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie postcode gebied Hillegom 2180 - 2182 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 4. Mutatielevering met waarschuwing, verwijderen  afnemerindicatie bij persoon buiten doelbinding
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(422751625)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: '36301', aanvang: 20150510, registratieDatum: 20150510) {
        naarGemeente 'Amsterdam',
            straat: 'Basisweg', nummer: 10, postcode: '1043AP', woonplaats: "Amsterdam"
    }
}
slaOp(persoon)

When voor persoon 422751625 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie postcode gebied Hillegom 2180 - 2182 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep                | nummer | attribuut           | verwachteWaarde                                                                         |
| melding              | 1      | regelCode           | BRLV0027                                                                                |
| melding              | 1      | melding             | De geleverde persoon valt niet meer binnen de doelbindingspopulatie van het abonnement. |
| parameters           | 1      | categorieDienst     | Mutatielevering op basis van afnemerindicatie                                           |
| identificatienummers | 1      | burgerservicenummer | 422751625                                                                               |

Given verzoek voor leveringsautorisatie 'postcode gebied Hillegom 2180 - 2182' en partij 'SRPUC50151-5-Partij'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerindicatie_verwijderen.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 5. Verwijdering afnemer indicatie bij persoon die binnen doelbinding valt

Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_afn_ind/postcode_gebied_Hillegom_2180-2182

Given de personen 875271467, 814591139, 422751625 zijn verwijderd
Given de standaardpersoon Gregory met bsn 422751625 en anr 4023907602 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(422751625)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 53401, aanvang: 20130510, registratieDatum: 20130510) {
        naarGemeente 'Hillegom',
            straat: 'Hoofdstraat', nummer: 115, postcode: '2181EC', woonplaats: "Hillegom"
    }
}
slaOp(persoon)

Given verzoek voor leveringsautorisatie 'postcode gebied Hillegom 2180 - 2182' en partij 'SRPUC50151-5-Partij'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerindicatie_plaatsen.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is er voor persoon met bsn 422751625 en leveringautorisatie postcode gebied Hillegom 2180 - 2182 een afnemerindicatie geplaatst

When voor persoon 422751625 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie postcode gebied Hillegom 2180 - 2182 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Given verzoek voor leveringsautorisatie 'postcode gebied Hillegom 2180 - 2182' en partij 'SRPUC50151-5-Partij'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerindicatie_verwijderen.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
