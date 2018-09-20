Meta:
@auteur                 aapos
@epic                   Levering onderzoek
@jiraIssue              TEAMBRP-3608
@sleutelwoorden         onderzoek
@status                 Klaar
@regels                 VR00086, R1551, R2063,R2051

Narrative: Wanneer het onderzoek uit het mutatie bericht gefilter is,
dan moet ook de verantwoording met de actie die verwijst naar het onderzoek uit het mutatie bericht verwijderd zijn


Scenario: 1a. Nieuw onderzoek op ongeautoriseerd attribuut, controleer dat er geen onderzoek aanwezig is en geen verantwoordingsinformatie voor het onderzoek op huisnummer

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding, /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep
Given de personen 627129705, 304953337, 816823881 zijn verwijderd
Given de standaardpersoon Olivia met bsn 816823881 en anr 7161975058 zonder extra gebeurtenissen

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(816823881)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Hillegom', aanvang: 20140607, registratieDatum: 20140607) {
            naarGemeente 'Hillegom',
                straat: 'Dorpsstraat', nummer: 40, postcode: '2180AA', woonplaats: "Hillegom"
        }
}
slaOp(persoon)

nieuweGebeurtenissenVoor(persoon) {
 onderzoek(partij: 34401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2015-04-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(persoon)

When voor persoon 816823881 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 2. Wijziging onderzoek naar een niet geautoriseerd attribuut

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding, /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(816823881)

nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401, registratieDatum: 20150324) {
        afgeslotenOp(eindDatum:'2015-03-24')
    }
}
slaOp(persoon)

When voor persoon 816823881 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 3. Volledig bericht persoon met onderzoek opvragen met abonnement zonder autorisatie, controleer dat de admn. handelingen niet voorkomen

Given leveringsautorisatie uit /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep
Given verzoek voor leveringsautorisatie 'Abo onderzoek met autorisatie op att binnen groep' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand onderzoek_verantwoordingsinformatie_filter_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken

Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut               | aanwezig  |
| gegevenInOnderzoek | 1      | elementNaam             | nee       |
| gegevenInOnderzoek | 1      | voorkomenSleutelGegeven | nee       |

Then is er voor xpath //brp:administratieveHandeling/brp:naam[contains(text(),'Aanvang onderzoek')] geen node aanwezig in het levering bericht
Then is er voor xpath //brp:administratieveHandeling/brp:naam[contains(text(),'Beeindiging onderzoek')] geen node aanwezig in het levering bericht


Scenario: 4. Volledig bericht persoon met onderzoek opvragen waarbij het abonnement wel autorisatie heeft op het onderzoek en de administratieve handelingen

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                 | WAARDE                                          |
| zoekcriteriaPersoon.burgerservicenummer | 816823881                                       |

When het bericht wordt verstuurd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then is er voor xpath //brp:administratieveHandeling/brp:naam[contains(text(),'Aanvang onderzoek')] een node aanwezig in het levering bericht
Then is er voor xpath //brp:administratieveHandeling/brp:naam[contains(text(),'Beeindiging onderzoek')] een node aanwezig in het levering bericht




