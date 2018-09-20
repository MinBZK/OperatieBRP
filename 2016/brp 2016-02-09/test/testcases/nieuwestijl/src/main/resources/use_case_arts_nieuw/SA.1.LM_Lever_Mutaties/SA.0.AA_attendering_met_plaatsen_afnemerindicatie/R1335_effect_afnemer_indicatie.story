Meta:
@sprintnummer           91
@epic                   Autenticatie levering
@auteur                 aapos
@jiraIssue              TEAMBRP-4532
@status                 Klaar
@regels                 R1335

Narrative:
Indien een dienst die afnemerindicaties automatisch plaatst,
een levering doet over één of meer personen,
dan maakt deze dienst per geleverde persoon bij het abonnement van de afnemer tevens een nieuwe afnemerindicatie aan, als die nog niet bestaat.


Scenario: R1335_01. Automatisch plaatsen afnemerindicaties
Narrative:
Effect afnemerindicatie = plaatsen, SoortDienst = Attendering, Voldoet aan attenderingscriterium = JA, soort bericht = Volledig
Resultaat = er wordt een afnemerindicatie geplaatst bij de persoon voor de betreffende partij

Given leveringsautorisatie uit /levering_autorisaties/attendering_met_afn_ind/R1335_attendering_met_plaatsing
Given de personen 627129705, 304953337, 810117769 zijn verwijderd
Given de standaardpersoon Olivia met bsn 810117769 en anr 9527634706 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(810117769)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Delft', aanvang: 20100212, registratieDatum: 20100212) {
            naarGemeente 'Delft',
                straat: 'Markt', nummer: 87, postcode: '2611GW', woonplaats: "Delft"
        }
}
slaOp(persoon)

When voor persoon 810117769 wordt de laatste handeling geleverd
When het volledigbericht voor partij 50301 en leveringsautorisatie R1335 attendering met afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: R1335_02. Automatisch plaatsen afnemerindicaties
Narrative:
Effect afnemerindicatie = plaatsen, SoortDienst = Attendering, Voldoet aan attenderingscriterium = JA, soort bericht = Mutatie
Resultaat = mutatie bericht, er wordt geen afnemerindicatie geplaatst bij de persoon voor de betreffende partij

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(810117769)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Delft', aanvang: 20160112) {
            naarGemeente 'Delft',
                straat: 'Geestkerkhof', nummer: 25, postcode: '2611HP', woonplaats: "Delft"
        }
}
slaOp(persoon)

When voor persoon 810117769 wordt de laatste handeling geleverd
When het mutatiebericht voor partij 50301 en leveringsautorisatie R1335 attendering met afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Given verzoek voor leveringsautorisatie 'R1335 attendering met afnemerindicatie' en partij 'Gemeente Delft' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_verwijder.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Scenario: R1335_03. Automatisch plaatsen afnemerindicaties
Narrative:
Effect afnemerindicatie = plaatsen, SoortDienst = Attendering, Voldoet aan attenderingscriterium = NEE, soort bericht = Volledig
Resultaat = er wordt geen afnemerindicatie geplaatst bij de persoon voor de betreffende partij

Given leveringsautorisatie uit /levering_autorisaties/attendering_met_afn_ind/R1335_attendering_met_plaatsing
Given de personen 627129705, 304953337, 810117769 zijn verwijderd
Given de standaardpersoon Olivia met bsn 810117769 en anr 9527634706 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(810117769)
nieuweGebeurtenissenVoor(persoon) {
   overlijden() {
     op '2010/03/24' te 'Monster' gemeente 'Westland'
   }
}
slaOp(persoon)

When voor persoon 810117769 wordt de laatste handeling geleverd
When het volledigbericht voor partij 50301 en leveringsautorisatie R1335 attendering met afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Given verzoek voor leveringsautorisatie 'R1335 attendering met afnemerindicatie' en partij 'Gemeente Delft' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_verwijder.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

Scenario: R1335_04. Automatisch plaatsen afnemerindicaties
Narrative:
Effect afnemerindicatie = verwijderen, SoortDienst = Attendering, Voldoet aan attenderingscriterium = JA, soort bericht = Volledig
Resultaat = er wordt geen afnemerindicatie geplaatst bij de persoon voor de betreffende partij

Given leveringsautorisatie uit /levering_autorisaties/attendering_met_afn_ind/R1335_attendering_met_verwijderen
Given de personen 627129705, 304953337, 810117769 zijn verwijderd
Given de standaardpersoon Olivia met bsn 810117769 en anr 9527634706 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(810117769)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Delft', aanvang: 20100212, registratieDatum: 20100212) {
            naarGemeente 'Delft',
                straat: 'Markt', nummer: 87, postcode: '2611GW', woonplaats: "Delft"
        }
}
slaOp(persoon)

When voor persoon 810117769 wordt de laatste handeling geleverd
When het volledigbericht voor partij 50301 en leveringsautorisatie R1335 attendering met verwijderen is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Given verzoek voor leveringsautorisatie 'R1335 attendering met verwijderen' en partij 'Gemeente Delft' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_verwijder.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

Scenario: R1335_05. Automatisch plaatsen afnemerindicaties
Narrative:
Effect afnemerindicatie = NULL, SoortDienst = Attendering, Voldoet aan attenderingscriterium = JA, soort bericht = Volledig
Resultaat = er wordt geen afnemerindicatie geplaatst bij de persoon voor de betreffende partij

Given leveringsautorisatie uit /levering_autorisaties/attendering_met_afn_ind/R1335_attendering_zonder_plaatsing
Given de personen 627129705, 304953337, 810117769 zijn verwijderd
Given de standaardpersoon Olivia met bsn 810117769 en anr 9527634706 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(810117769)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Delft', aanvang: 20100212, registratieDatum: 20100212) {
            naarGemeente 'Delft',
                straat: 'Markt', nummer: 87, postcode: '2611GW', woonplaats: "Delft"
        }
}
slaOp(persoon)

When voor persoon 810117769 wordt de laatste handeling geleverd
When het volledigbericht voor partij 50301 en leveringsautorisatie R1335 attendering zonder plaatsing is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Given verzoek voor leveringsautorisatie 'R1335 attendering zonder plaatsing' en partij 'Gemeente Delft' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_verwijder.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

Scenario: R1335_06. Automatisch plaatsen afnemerindicaties
Narrative:
Effect afnemerindicatie = plaatsen, SoortDienst <> Attendering, Voldoet aan attenderingscriterium = NVT, soort bericht = Volledig
Resultaat = er wordt geen afnemerindicatie geplaatst bij de persoon voor de betreffende partij

Given leveringsautorisatie uit /levering_autorisaties/attendering_met_afn_ind/R1335_sync_persoon_met_plaatsing
Given de personen 627129705, 304953337, 810117769 zijn verwijderd
Given de standaardpersoon Olivia met bsn 810117769 en anr 9527634706 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(810117769)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Delft', aanvang: 20100212, registratieDatum: 20100212) {
            naarGemeente 'Delft',
                straat: 'Markt', nummer: 87, postcode: '2611GW', woonplaats: "Delft"
        }
}
slaOp(persoon)

When voor persoon 810117769 wordt de laatste handeling geleverd
When het volledigbericht voor partij 50301 en leveringsautorisatie R1335 sync persoon met afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Given verzoek voor leveringsautorisatie 'R1335 sync persoon met afnemerindicatie' en partij 'Gemeente Delft' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
When het volledigbericht voor leveringsautorisatie R1335 sync persoon met afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Given verzoek voor leveringsautorisatie 'R1335 sync persoon met afnemerindicatie' en partij 'Gemeente Delft' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_verwijder.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief