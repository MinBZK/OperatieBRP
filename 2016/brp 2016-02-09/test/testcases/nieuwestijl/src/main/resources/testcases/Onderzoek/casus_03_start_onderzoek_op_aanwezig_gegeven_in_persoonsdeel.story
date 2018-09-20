Meta:
@auteur                 aapos
@regels                 VR00116,R2063,R2065,R1319,R1562,1973,R2051
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek,casus3
@status                 Klaar

Narrative: Casus 3. Een administratieve handeling die bestaand gegeven in onderzoek plaatst.

Scenario: 1. start onderzoek op aanwezig gegeven in persoonsdeel

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
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
Given extra waardes:
| SLEUTEL                                 | WAARDE                                          |
| zoekcriteriaPersoon.burgerservicenummer | 373463881                                       |
| stuurgegevens.zendendePartij            | 034401                                          |

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

Meta:
@status     Onderhanden

Given leveringsautorisatie uit /levering_autorisaties/abo_geef_details_persoon
Given verzoek voor leveringsautorisatie 'Abo GeefDetailsPersoon' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_bvgGeefDetailsPersoon
And testdata uit bestand geefDetailsPersoon_1.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep              | attribuut              | verwachteWaardes                            |
| onderzoek          | datumAanvang           | 2015-01-01                                  |
| onderzoek          | verwachteAfhandeldatum | 2015-08-01                                  |
| onderzoek          | omschrijving           | Casus 3. Onderzoek is gestart op huisnummer |
| onderzoek          | statusNaam             | In uitvoering                               |
| gegevenInOnderzoek | elementNaam            | Persoon.Adres.Huisnummer                    |
