Meta:
@auteur                 aapos
@epic                   Levering onderzoek
@jiraIssue              TEAMBRP-3579
@sleutelwoorden         onderzoek
@status                 Klaar
@regels                 VR00086, R1551, R2063,R2051

Narrative: Er wordt een onderzoek gestart naar de huwelijk relatie niet ingeschrevene,
Vervolgens wordt er een onderzoek naar de partner gestart, omdat dit een niet ingeschrevene betreft, is deze onderdeel van de hoofdpersoon
en wordt er een mutatie levering verwacht.

R2063  Stel Volledig persoon samen.
R2051 Actie attributen alleen opnemen als actie voorkomt in verantwoording.

Scenario: 1a. Registreer huwelijk met niet ingeschreven persoon zodat deze onderdeel uitmaakt van de hoofdpersoon

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 627129705, 304953337, 972345929 zijn verwijderd
Given de standaardpersoon Olivia met bsn 972345929 en anr 9270956818 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(972345929)

def partner_Olivia_NI = Persoon.nietIngeschrevene(aanvang: 19720101, registratieDatum: 19720101) {
  geboorte {
    op '1972/01/01' te 'Kyoto' land 'Japan'
  }
  samengesteldeNaam(
    stam: 'Kenshin',
    voornamen: 'Rourouni'
  )
  geslacht('MAN')
}
slaOp(partner_Olivia_NI)

nieuweGebeurtenissenVoor(persoon) {
    huwelijk(registratieDatum: 20120101) {
      op '2010/03/24' te 'Caïro' land 'Egypte'
      met partner_Olivia_NI
    }

}
slaOp(persoon)

When voor persoon 972345929 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                 | WAARDE                                          |
| zoekcriteriaPersoon.burgerservicenummer | 972345929                                       |
| stuurgegevens.zendendePartij            | 034401                                          |

When het bericht wordt verstuurd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 1b. aanvang onderzoek naar niet ingeschrevene partner, mutatie bericht met onderzoek wordt geleverd
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(972345929)
nieuweGebeurtenissenVoor(persoon) {
     onderzoek(partij: 34401, registratieDatum: 20150101) {
          gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op voornamen partner', verwachteAfhandelDatum:'2015-08-01')
          gegevensInOnderzoek('GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.Voornamen')
        }
}
slaOp(persoon)

When voor persoon 972345929 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide


Then hebben attributen in voorkomens de volgende waardes:
| groep                    | nummer | attribuut              | verwachteWaarde                                                  |
| synchronisatie           | 1      | naam                   | Aanvang onderzoek                                                |
| onderzoek                | 2      | datumAanvang           | 2015-01-01                                                       |
| onderzoek                | 2      | verwachteAfhandeldatum | 2015-08-01                                                       |
| onderzoek                | 2      | omschrijving           | Onderzoek is gestart op voornamen partner                        |
| onderzoek                | 2      | statusNaam             | In uitvoering                                                    |
| gegevenInOnderzoek       | 1      | elementNaam            | GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.Voornamen |
| administratieveHandeling | 1      | naam                   | Aanvang onderzoek                                                |

Scenario: 1c. Wijzig onderzoek, controleer dat de verantwoording goed gevuld is
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(972345929)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401, registratieDatum: 20150801) {
            wijzigOnderzoek(wijzigingsDatum:'2015-02-02', omschrijving:'Wijziging onderzoek verwachte afhandel datum', aanvangsDatum: '2015-01-01', verwachteAfhandelDatum: '2015-10-10')
        }
    }
    slaOp(persoon)

When voor persoon 972345929 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep                    | nummer | attribuut              | verwachteWaarde                                                  |
| synchronisatie           | 1      | naam                   | Wijziging onderzoek                                              |
| onderzoek                | 2      | datumAanvang           | 2015-01-01                                                       |
| onderzoek                | 2      | verwachteAfhandeldatum | 2015-10-10                                                       |
| onderzoek                | 2      | omschrijving           | Wijziging onderzoek verwachte afhandel datum                     |
| onderzoek                | 2      | statusNaam             | In uitvoering                                                    |
| administratieveHandeling | 1      | naam                   | Wijziging onderzoek                                              |


Scenario: 1d. Beeindig onderzoek, controleer dat de verantwoording goed gevuld is

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(972345929)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20151012) {
            afgeslotenOp(eindDatum:'2015-10-12')
        }
    }
slaOp(persoon)

When voor persoon 972345929 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep                    | nummer | attribuut              | verwachteWaarde                                                  |
| synchronisatie           | 1      | naam                   | Beeindiging onderzoek                                            |
| onderzoek                | 2      | datumAanvang           | 2015-01-01                                                       |
| onderzoek                | 2      | verwachteAfhandeldatum | 2015-10-10                                                       |
| onderzoek                | 2      | omschrijving           | Wijziging onderzoek verwachte afhandel datum                     |
| onderzoek                | 2      | statusNaam             | Afgesloten                                                       |
| gegevenInOnderzoek       | 1      | elementNaam            | GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.Voornamen |
| administratieveHandeling | 1      | naam                   | Beeindiging onderzoek                                            |



Scenario: 1e. Start onderzoek op samengestelde naam van de ouder, het onderzoek en adm handeling komen enkel voor bij de hoofdpersoon

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(972345929)
nieuweGebeurtenissenVoor(persoon) {
     onderzoek(partij: 34401, registratieDatum: 20151014) {
          gestartOp(aanvangsDatum:'2015-10-14', omschrijving:'Onderzoek is gestart op samengestelde naam van de ouder', verwachteAfhandelDatum:'2015-08-01')
          gegevensInOnderzoek('GerelateerdeOuder.Persoon.SamengesteldeNaam.Voornamen')
        }
}
slaOp(persoon)

When voor persoon 972345929 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide


Scenario: 1f. Er is geen onderzoek -of adm. handeling mbt het onderzoek aanwezig bij de ouder

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                 | WAARDE                                          |
| zoekcriteriaPersoon.burgerservicenummer | 627129705                                       |
| stuurgegevens.zendendePartij            | 034401                                          |
| parameters.abonnementNaam               | Geen pop.bep. levering op basis van doelbinding |

When het bericht wordt verstuurd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut               | aanwezig  |
| gegevenInOnderzoek | 1      | elementNaam             | nee       |
| gegevenInOnderzoek | 1      | voorkomenSleutelGegeven | nee       |

Then is er voor xpath //brp:administratieveHandeling/brp:naam[contains(text(),'Aanvang onderzoek')] geen node aanwezig in het levering bericht


Scenario: Casus 41. Start onderzoek gelijktijdig met het opvoeren van het ontbrekend gegeven

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand DELTAOND05C10T170.xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep                    | nummer | attribuut              | verwachteWaarde              |
| synchronisatie           | 1      | naam                   | GBA - Bijhouding actueel     |
| onderzoek                | 2      | datumAanvang           | 2015-01-01                   |
| onderzoek                | 2      | verwachteAfhandeldatum | 0000                         |
| onderzoek                | 2      | omschrijving           | Conversie GBA: 050620        |
| onderzoek                | 2      | statusNaam             | In uitvoering                |
| gegevenInOnderzoek       | 1      | elementNaam            | Huwelijk.GemeenteAanvangCode |
| administratieveHandeling | 1      | naam                   | GBA - Bijhouding actueel     |

Then is er voor xpath //brp:betrokkenheden/brp:partner[@brp:verwerkingssoort="Toevoeging"] een node aanwezig in het levering bericht
