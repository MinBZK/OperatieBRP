Meta:
@auteur                 aapos
@epic                   Levering onderzoek
@jiraIssue              TEAMBRP-2569
@sleutelwoorden         onderzoek
@status                 Uitgeschakeld
@regels                 VR00114,R1563,R1561

Narrative: Geen lege onderzoeken leveren

Scenario: Casus 2. Onderzoek gestart op een ontbrekende gegeven in persoonsdeel (olivia is niet getrouwd), er wordt NIET geleverd

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 627129705, 304953337, 411372233 zijn verwijderd
Given de standaardpersoon Olivia met bsn 411372233 en anr 1086049514 zonder extra gebeurtenissen

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(411372233)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op datum aanvang huwelijk/partnerschap', verwachteAfhandelDatum:'2015-04-01')
        gegevensInOnderzoek('HuwelijkGeregistreerdPartnerschap.DatumAanvang')
    }
}
slaOp(persoon)

When voor persoon 411372233 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: Casus 14. Beeindiging onderzoek naar een ontbrekend gegeven, er wordt NIET geleverd

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(411372233)
nieuweGebeurtenissenVoor(persoon) {
   onderzoek(partij: 59401, registratieDatum: 20151012) {
           afgeslotenOp(eindDatum:'2015-10-12')
       }
}
slaOp(persoon)

When voor persoon 411372233 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: Casus 31. niet leveren start een onderzoek op een bestaand gegeven, dat reeds is beeindigd. De afnemer heeft geen autorisatie op materiele historie op deze groep

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding, /levering_autorisaties/Abo_met_alleen_verantwoordingsinfo_True
Given alle personen zijn verwijderd
Given een sync uit bestand DELTAONDC40T50.xls
When voor persoon 651919113 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut               | aanwezig |
| gegevenInOnderzoek | 1      | elementNaam             | nee      |
| gegevenInOnderzoek | 1      | voorkomenSleutelGegeven | nee      |

When het volledigbericht voor leveringsautorisatie Abo met alleen verantwoordingsinfo True is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut               | aanwezig |
| gegevenInOnderzoek | 1      | elementNaam             | nee      |
| gegevenInOnderzoek | 1      | voorkomenSleutelGegeven | nee      |


Scenario: 31b. start een onderzoek op een bestaand gegeven, dat is beeindigd. De afnemer heeft geen autorisatie op materiele historie op deze groep

Given alle personen zijn verwijderd
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding, /levering_autorisaties/Abo_met_alleen_verantwoordingsinfo_True
Given de personen 627129705, 304953337, 385165225 zijn verwijderd
Given de standaardpersoon Olivia met bsn 385165225 en anr 2826753682 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(385165225)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 50301, registratieDatum: 20090101 ) {
        gestartOp(aanvangsDatum:'2009-01-01', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-06-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(persoon)

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(385165225)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Pijnacker', aanvang: 20100312, registratieDatum: 20100312) {
        naarGemeente 'Pijnacker',
            straat: 'Fien de la Marstraat', nummer: 50, postcode: '2642BX', woonplaats: "Pijnacker"
    }
}
slaOp(persoon)

When voor persoon 385165225 wordt de laatste handeling geleverd

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                 | WAARDE                                          |
| zoekcriteriaPersoon.burgerservicenummer | 385165225                                       |
| stuurgegevens.zendendePartij            | 034401                                          |
| parameters.abonnementNaam               | Abo met alleen verantwoordingsinfo True         |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Abo met alleen verantwoordingsinfo True is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut               | aanwezig |
| gegevenInOnderzoek | 1      | elementNaam             | nee      |
| gegevenInOnderzoek | 1      | voorkomenSleutelGegeven | nee      |

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                 | WAARDE                                          |
| zoekcriteriaPersoon.burgerservicenummer | 385165225                                       |
| stuurgegevens.zendendePartij            | 034401                                          |
| parameters.abonnementNaam               | Geen pop.bep. levering op basis van doelbinding |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut               | aanwezig |
| gegevenInOnderzoek | 1      | elementNaam             | ja       |
| gegevenInOnderzoek | 1      | voorkomenSleutelGegeven | ja       |

Scenario: Casus 32. leveren (zonder onderzoek) er wordt een VolledigBericht opgevraagd, waarin zich een onderzoek bevindt dat betrekking heeft op een vervallen gegeven. De afnemer is niet geautoriseerd voor formele historie op de groep waarvan het vervallen gegeven in onderzoek staat

Given leveringsautorisatie uit /levering_autorisaties/Abo_met_alleen_verantwoordingsinfo_True
Given alle personen zijn verwijderd
Given een sync uit bestand DELTAONDC40T50.xls
When voor persoon 651919113 wordt de laatste handeling geleverd

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                 | WAARDE                                          |
| zoekcriteriaPersoon.burgerservicenummer | 651919113                                       |
| stuurgegevens.zendendePartij            | 034401                                          |
| parameters.abonnementNaam               | Abo met alleen verantwoordingsinfo True         |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Abo met alleen verantwoordingsinfo True is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut               | aanwezig |
| gegevenInOnderzoek | 1      | elementNaam             | nee      |
| gegevenInOnderzoek | 1      | voorkomenSleutelGegeven | nee      |

Scenario: Casus 33a. niet leveren, er is sprake van een bestaand onderzoek op een actueel gegeven.
Dit actuele gegeven wordt later beeindigd of komt te vervallen.
In de meest recente AH wordt het onderzoek beeindigd, dat resulteert in een mutatiebericht.
De afnemer is niet geautoriseerd voor formele en materiele historie

Meta:
@jiraIssue              TEAMBRP-4277

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 627129705, 304953337, 891833225 zijn verwijderd
Given de standaardpersoon Olivia met bsn 891833225 en anr 6715235090 zonder extra gebeurtenissen

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(891833225)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20130401) {
        gestartOp(aanvangsDatum:'2013-04-01', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-04-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(persoon)

When voor persoon 891833225 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: Casus 33b. Verhuizing, zodat het onderzoek naar een vervallen adres wijst

Meta:
@jiraIssue              TEAMBRP-4277

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding, /levering_autorisaties/Abo_met_alleen_verantwoordingsinfo_True
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(891833225)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Hillegom', aanvang: 20131017, registratieDatum: 20131017) {
        naarGemeente 'Hillegom',
            straat: 'Dorpsstraat', nummer: 30, postcode: '2180AA', woonplaats: "Hillegom"
    }
}
slaOp(persoon)

When voor persoon 891833225 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

When het mutatiebericht voor leveringsautorisatie Abo met alleen verantwoordingsinfo True is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: Casus 33c.  Onderzoek naar vervallen gegeven beeindigen
Meta:
@jiraIssue              TEAMBRP-4277

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding, /levering_autorisaties/Abo_met_alleen_verantwoordingsinfo_True
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(891833225)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20141212) {
        afgeslotenOp(eindDatum:'2014-12-12')
    }
}
slaOp(persoon)

When voor persoon 891833225 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo met alleen verantwoordingsinfo True is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: Casus 33d. Volledig bericht opvragen voor een persoon met een beeindigd onderzoek naar een vervallen gegeven

Meta:
@jiraIssue              TEAMBRP-4277

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding, /levering_autorisaties/Abo_met_alleen_verantwoordingsinfo_True
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                 | WAARDE                                          |
| zoekcriteriaPersoon.burgerservicenummer | 891833225                                       |
| stuurgegevens.zendendePartij            | 034401                                          |
| parameters.abonnementNaam               | Abo met alleen verantwoordingsinfo True         |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Abo met alleen verantwoordingsinfo True is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                 | WAARDE                                          |
| zoekcriteriaPersoon.burgerservicenummer | 891833225                                       |
| stuurgegevens.zendendePartij            | 034401                                          |
| parameters.abonnementNaam               | Geen pop.bep. levering op basis van doelbinding |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

