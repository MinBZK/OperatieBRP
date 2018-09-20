Meta:
@auteur                 aapos
@regels                 VR00116,R2063,R2065,R1319,R1562
@epic                   Levering onderzoek
@jiraIssue              TEAMBRP-2571,TEAMBRP-2568
@sleutelwoorden         onderzoek
@status                 Uitgeschakeld

Narrative: Stel het onderzoeksdeel samen voor een bericht

Scenario: 1. start onderzoek op aanwezig gegeven in persoonsdeel
Casus 3

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 627129705, 304953337, 373463881 zijn verwijderd
Given de standaardpersoon Olivia met bsn 373463881 en anr 9724643090 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(373463881)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Casus 3. Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(persoon)

When voor persoon 373463881 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut               | aanwezig |
| gegevenInOnderzoek | 1      | elementNaam             | ja       |
| gegevenInOnderzoek | 1      | voorkomenSleutelGegeven | ja       |

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut              | verwachteWaarde                             |
| onderzoek          | 2      | datumAanvang           | 2015-01-01                                  |
| onderzoek          | 2      | verwachteAfhandeldatum | 2015-08-01                                  |
| onderzoek          | 2      | omschrijving           | Casus 3. Onderzoek is gestart op huisnummer |
| onderzoek          | 2      | statusNaam             | In uitvoering                               |
| gegevenInOnderzoek | 1      | elementNaam            | Persoon.Adres.Huisnummer                    |

Scenario: 2. Synchroniseer persoon met onderzoek
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                 | WAARDE                                          |
| zoekcriteriaPersoon.burgerservicenummer | 373463881                                       |
| stuurgegevens.zendendePartij            | 034401                                          |
| parameters.abonnementNaam               | Geen pop.bep. levering op basis van doelbinding |

When het bericht wordt verstuurd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut               | aanwezig |
| gegevenInOnderzoek | 1      | elementNaam             | ja       |
| gegevenInOnderzoek | 1      | voorkomenSleutelGegeven | ja       |

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut              | verwachteWaarde                             |
| onderzoek          | 2      | datumAanvang           | 2015-01-01                                  |
| onderzoek          | 2      | verwachteAfhandeldatum | 2015-08-01                                  |
| onderzoek          | 2      | omschrijving           | Casus 3. Onderzoek is gestart op huisnummer |
| onderzoek          | 2      | statusNaam             | In uitvoering                               |
| gegevenInOnderzoek | 1      | elementNaam            | Persoon.Adres.Huisnummer                    |

Scenario: 3. geefdetails persoon met onderzoek Bevraging Levering

Given leveringsautorisatie uit Abo GeefDetailsPersoon
Given verzoek van bericht lvg_bvgGeefDetailsPersoon
And extra waardes:
| SLEUTEL                                 | WAARDE                 |
| stuurgegevens.zendendePartij            | 036101                 |
| parameters.abonnementNaam               | Abo GeefDetailsPersoon |
| parameters.peilmomentMaterieelResultaat | []                     |
| parameters.peilmomentFormeelResultaat   | []                     |
| parameters.historievorm                 | []                     |
| zoekcriteriaPersoon.burgerservicenummer | 373463881              |
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep              | attribuut              | verwachteWaardes                            |
| onderzoek          | datumAanvang           | 2015-01-01                                  |
| onderzoek          | verwachteAfhandeldatum | 2015-08-01                                  |
| onderzoek          | omschrijving           | Casus 3. Onderzoek is gestart op huisnummer |
| onderzoek          | statusNaam             | In uitvoering                               |
| gegevenInOnderzoek | elementNaam            | Persoon.Adres.Huisnummer                    |

Scenario: 4. start onderzoek op aanwezig gegeven zonder attribuut autorisatie, er wordt niet geleverd
Casus 4

Given leveringsautorisatie uit /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep
Given de personen 627129705, 304953337, 562437897 zijn verwijderd
Given de standaardpersoon Olivia met bsn 562437897 en anr 2029101010 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(562437897)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Casus 4. Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(persoon)

When voor persoon 562437897 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
When het volledigbericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 5. leveren, start een onderzoek, waarvoor geen attribuut autorisatie geldt voor 1 van de 2 onderzoeksregels
Casus 5
Narrative: Er wordt een onderzoek gestart op huisnummer en geboortedatum, voor het mutatiebericht geldt dat er enkel autorisatie is voor geboortedatum

Given leveringsautorisatie uit /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep
Given de personen 627129705, 304953337, 225252089 zijn verwijderd
Given de standaardpersoon Olivia met bsn 225252089 en anr 5817201938 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(225252089)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Casus 5. Onderzoek is gestart op huisnummer en geboortedatum', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
        gegevensInOnderzoek('Persoon.Geboorte.Datum')
    }
}
slaOp(persoon)

When voor persoon 225252089 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 6. leveren, start onderzoek op groep, waarvoor slechts 1 attribuut is geautoriseerd
Casus 6
Narrative: Er wordt een onderzoek gestart op huisnummer en huisnummer toevoeging, vervolgens wordt het mutatie bericht bekeken met een abonnement waarvoor
geen autorisatie aanwezig is op het attribuut huisnummer, verwacht is dat er een mutatie bericht geleverd wordt met het adres voorkomen, maar zonder huisnummer

Given leveringsautorisatie uit /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep
Given de personen 627129705, 304953337, 533679849 zijn verwijderd
Given de standaardpersoon Olivia met bsn 533679849 en anr 4201576978 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(533679849)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Casus 6. Onderzoek is gestart op huisnummer en toevoeging', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }

}
slaOp(persoon)

When voor persoon 533679849 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 7. leveren, wijziging op onderzoek gegevens voor een lopend onderzoek op een aanwezig gegeven
Casus 9
Narrative: het huisnummer van de persoon wordt in onderzoek gezet, vervolgens wordt de verwachte afhandeldatum van het onderzoek gewijzigd

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 627129705, 304953337, 630151593 zijn verwijderd
Given de standaardpersoon Olivia met bsn 630151593 en anr 4313036562 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(630151593)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Casus 9. Start onderzoek op een aanwezig gegeven', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(persoon)


When voor persoon 630151593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 8. mutatie bericht na wijzigen onderzoek
Casus 9 b

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(630151593)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150202) {
        wijzigOnderzoek(wijzigingsDatum:'2015-02-02', omschrijving:'Casus 9. Onderzoek gegevens gewijzigd', aanvangsDatum: '2015-01-01', verwachteAfhandelDatum: '2015-10-10')
    }
}
slaOp(persoon)

When voor persoon 630151593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut              | verwachteWaarde                             |
| synchronisatie     | 1      | naam                   | Wijziging onderzoek                         |
| onderzoek          | 2      | datumAanvang           | 2015-01-01                                  |
| onderzoek          | 2      | verwachteAfhandeldatum | 2015-10-10                                  |
| onderzoek          | 2      | omschrijving           | Casus 9. Onderzoek gegevens gewijzigd       |
| onderzoek          | 2      | statusNaam             | In uitvoering                               |
| gegevenInOnderzoek | 1      | elementNaam            | Persoon.Adres.Huisnummer                    |

Scenario: 9. mutatiebericht na beeindigen onderzoek
Casus 9 c

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(630151593)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20151012) {
        afgeslotenOp(eindDatum:'2015-10-12')
    }
}
slaOp(persoon)

When voor persoon 630151593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut              | verwachteWaarde                             |
| synchronisatie     | 1      | naam                   | Beeindiging onderzoek                       |
| onderzoek          | 2      | datumEinde             | 2015-10-12                                  |
| onderzoek          | 2      | omschrijving           | Casus 9. Onderzoek gegevens gewijzigd       |
| onderzoek          | 2      | statusNaam             | Afgesloten                                  |
| gegevenInOnderzoek | 1      | elementNaam            | Persoon.Adres.Huisnummer                    |


Scenario: 10. niet leveren, wijziging op onderzoek gegevens voor een lopend onderzoek op een niet geautoriseerd gegeven
Casus 11
Narrative: Er wordt een onderzoek gestart op huisnummer, vervolgens wordt de verwachte afhandeldatum voor het onderzoek gewijzigd,
verwacht is dat er geen Mutatie bericht wordt ontvangen voor het abonnement dat geen autorisatie heeft op huisnummer

Given leveringsautorisatie uit /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep
Given de personen 627129705, 304953337, 630151593 zijn verwijderd
Given de standaardpersoon Olivia met bsn 630151593 en anr 4313036562 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(630151593)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Casus 11. Wijziging onderzoek op een aanwezig gegeven', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(persoon)

When voor persoon 630151593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(630151593)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401) {
        wijzigOnderzoek(wijzigingsDatum:'2015-03-01', omschrijving:'Casus 11. Onderzoek gewijzigd', aanvangsDatum: '2015-01-01', verwachteAfhandelDatum: '2015-12-31')
    }
}
slaOp(persoon)

When voor persoon 630151593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
When het volledigbericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 11. leveren beeindig onderzoek naar een aanwezig gegeven
Casus 15
Narrative: Er wordt een onderzoek gestart naar geboortedatum, vervolgens wordt het onderzoek afgesloten,
Verwacht is dat er een mutatie bericht wordt ontvangen met het afgesloten onderzoek

Given leveringsautorisatie uit /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep
Given de personen 627129705, 304953337, 459232617 zijn verwijderd
Given de standaardpersoon Olivia met bsn 459232617 en anr 7484567314 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(459232617)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Casus 15. Wijziging onderzoek op een aanwezig gegeven', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Geboorte.Datum')
    }
}
slaOp(persoon)

When voor persoon 459232617 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(459232617)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401) {
        afgeslotenOp(eindDatum:'2015-10-01')
    }
}
slaOp(persoon)

When voor persoon 459232617 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 12. niet leveren beëindig onderzoek naar een niet geautoriseerd gegeven
Casus 17
Narrative: Er wordt een onderzoek gestart naar het huisnummer, vervolgens wordt het onderzoek afgesloten,
Verwacht is dat er geen mutatie bericht wordt ontvangen met het afgesloten onderzoek

Given leveringsautorisatie uit /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep
Given de personen 627129705, 304953337, 557892041 zijn verwijderd
Given de standaardpersoon Olivia met bsn 557892041 en anr 6193070354 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(557892041)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Casus 11. Wijziging onderzoek op een aanwezig gegeven', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(persoon)

When voor persoon 557892041 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(557892041)
nieuweGebeurtenissenVoor(persoon) {
     onderzoek(partij: 34401) {
            afgeslotenOp(eindDatum:'2015-10-01')
        }
}
slaOp(persoon)

When voor persoon 557892041 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
When het volledigbericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 13. leveren en beeindig onderzoek naar een groep waarvoor slechts 1 attribuut is geautoriseerd
Casus 18a

Given leveringsautorisatie uit /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep
Given de personen 627129705, 304953337, 792937417 zijn verwijderd
Given de standaardpersoon Olivia met bsn 792937417 en anr 4919794962 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(792937417)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Casus 18. Wijziging onderzoek op een aanwezig gegeven', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(persoon)

When voor persoon 792937417 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut   | verwachteWaarde        |
| synchronisatie     | 1      | naam        | Aanvang onderzoek      |
| onderzoek          | 2      | statusNaam  | In uitvoering          |

And heeft het bericht 1 groepen 'gegevenInOnderzoek'

Scenario: 14. Beeindigen onderzoek waarvoor slechts 1 attribuut is geautoriseerd
Casus 18b

Given leveringsautorisatie uit /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(792937417)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401) {
        afgeslotenOp(eindDatum:'2015-10-01')
    }
}
slaOp(persoon)

When voor persoon 792937417 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep          | nummer | attribuut  | verwachteWaarde       |
| synchronisatie | 1      | naam       | Beeindiging onderzoek |
| onderzoek      | 2      | statusNaam | Afgesloten            |
