Synchronisatie Stamgegeven

Meta:
@status         Klaar
@sprintnummer   70
@jiraIssue      TEAMBRP-2422

Narrative:
In order to verifieren dat de dienst Synchronisatie Stamgegeven werkt
As a lid van Team Delta
I want to verschillende requests doen

Scenario: Valideer waardes in antwoord op een synchronisatie stamgegeven verzoek
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand synchronisatie_stamgegeven_basis.yml
And extra waardes:
| SLEUTEL                        | WAARDE
| parameters.stamgegeven         | <stamgegeven>

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht '<attribuut>' in '<groep>' de waardes '<waarde>'

Examples:
| <stamgegeven>                          | groep                                  | attribuut | waarde
| BijhoudingsaardTabel                   | bijhoudingsaardTabel                   | naam      | Ingezetene, Niet-ingezetene, Onbekend
| BijhoudingsresultaatTabel              | bijhoudingsresultaat                   | naam      | Verwerkt, Uitgesteld, Direct, Conform plan
| CategorieAdministratieveHandelingTabel | categorieAdministratieveHandelingTabel | naam      | Actualisering, Correctie, Synchronisatie, GBA - Initiele vulling, GBA - Synchronisatie
| EffectAfnemerindicatiesTabel           | effectAfnemerindicaties                | naam      | Plaatsing, Verwijdering
| FunctieAdresTabel                      | functieAdres                           | naam      | Woonadres, Briefadres
| GeslachtsaanduidingTabel               | geslachtsaanduiding                    | code      | M, V, O
| NaamgebruikTabel                       | naamgebruik                            | code      | E, P, V, N
| PredicaatTabel                         | predicaat                              | code      | K, H, J
| RedenWijzigingVerblijfTabel            | redenWijzigingVerblijf                 | code      | P, A, M, B, I, ?
| RolTabel                               | rol                                    | naam      | Afnemer, Bijhoudingsorgaan College, Bijhoudingsorgaan Minister
| SoortMeldingTabel                      | soortMelding                           | naam      | Geen, Informatie, Waarschuwing, Deblokkeerbaar, Fout
| SoortMigratieTabel                     | soortMigratie                          | naam      | Immigratie, Emigratie
| SoortPartijOnderzoekTabel              | soortPartijOnderzoek                   | naam      | Eigenaar, Gevoegde
| SoortPersoonTabel                      | soortPersoon                           | naam      | Ingeschrevene, Niet-ingeschrevene, Onbekend
| SoortPersoonOnderzoekTabel             | soortPersoonOnderzoek                  | naam      | Direct, Indirect
| SoortSynchronisatieTabel               | soortSynchronisatie                    | naam      | Mutatiebericht, Volledigbericht
| StatusOnderzoekTabel                   | statusOnderzoek                        | naam      | In uitvoering, Gestaakt, Afgesloten
| VerwerkingsresultaatTabel              | verwerkingsresultaat                   | naam      | Geslaagd, Foutief
| VerwerkingswijzeTabel                  | verwerkingswijze                       | naam      | Bijhouding, Prevalidatie


Scenario: Tel waardes in antwoord op een synchronisatie stamgegeven verzoek
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand synchronisatie_stamgegeven_basis.yml
And extra waardes:
| SLEUTEL                        | WAARDE
| parameters.stamgegeven         | <stamgegeven>

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht <x> groepen '<groep>'

Examples:
| <stamgegeven>                                 | groep                                     | x
| AanduidingInhoudingVermissingReisdocumentTabel| aanduidingInhoudingVermissingReisdocument | 3
| AanduidingVerblijfsrechtTabel                 | aanduidingVerblijfsrecht                  | 41
| AangeverTabel                                 | aangever                                  | 8
| AdellijkeTitelTabel                           | adellijkeTitel                            | 6
| AutoriteittypeVanAfgifteReisdocumentTabel     | autoriteittypeVanAfgifteReisdocument      | 12
| GemeenteTabel                                 | gemeente                                  | 1496
| LandGebiedTabel                               | landGebied                                | 415
| NadereBijhoudingsaardTabel                    | nadereBijhoudingsaard                     | 8
| NationaliteitTabel                            | nationaliteit                             | 209
| PartijTabel                                   | partij                                    | 2394
| PlaatsTabel                                   | plaats                                    | 2675
| RedenEindeRelatieTabel                        | redenEindeRelatie                         | 8
| RedenVerkrijgingNLNationaliteitTabel          | redenVerkrijgingNLNationaliteit           | 120
| RedenVerliesNLNationaliteitTabel              | redenVerliesNLNationaliteit               | 69
| SoortAdministratieveHandelingTabel            | soortAdministratieveHandeling             | 130
| SoortDocumentTabel                            | soortDocument                             | 41
| SoortNederlandsReisdocumentTabel              | soortNederlandsReisdocument               | 30
| StatusTerugmeldingTabel                       | statusTerugmelding                        | 5
| VoorvoegselTabel                              | voorvoegsel                               | 674


Scenario: Onbekende stamtabel kan niet worden gesynchroniseerd
Meta:
@regels BRLV0024
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'

Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand synchronisatie_stamgegeven_basis.yml
And extra waardes:
SLEUTEL                        | WAARDE
parameters.stamgegeven         | SoortRelatieTabel

When het bericht wordt verstuurd
Then is het antwoordbericht een soapfault
