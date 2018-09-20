Meta:
@sprintnummer       77
@epic               PDT & aanpassen SW Leveren tbv productiedata 3.1
@auteur             rarij
@jiraIssue          TEAMBRP-3219
@status             Onderhanden
@sleutelwoorden     PDT, Conversie GBA, Reisdocumenten

Narrative:          Als Team BRP
                    Wil ik het BRP lever koppelvlak middels geconverteerde GBA testdata testen
                    Zodat ik kan bepalen of geconverteerde data wel/niet goed wordt geleverd

Scenario:           BRP levering van een PL met een opgeschort reisdocument
                    Verwacht resultaat:
                    -   Asynchroon bericht van de type "GBA - Synchronisatie"
                    -   2 reisdocumenten
                    -   2 toegevoegde groepen reisdocument
                        -   1 voor het reisdocument dat is opgeschort
                        -   1 voor het reisdocument dat is toegevoegd, als vervanging van de opgeschortste versie
                    -   1 vervallen groep reisdocument

Given een initiele vulling uit bestand sync_reisdocument_opgeschort_initieel.xls
And een sync uit bestand sync_reisdocument_opgeschort_synchronisatie.xls

When voor persoon 845828617 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes                           |
| synchronisatie       | categorie           | GBA - Synchronisatie, GBA - Synchronisatie |
| identificatienummers | burgerservicenummer | 845828617                                  |

And heeft het bericht 3 groepen 'reisdocument'

And hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep        | nummer | verwerkingssoort |
| reisdocument | 1      | Verval           |
| reisdocument | 2      | Toevoeging       |
| reisdocument | 2      | Toevoeging       |

And hebben attributen in voorkomens de volgende waardes:
| groep        | nummer | attribuut                         | verwachteWaarde |
| reisdocument | 1      | soortCode                         | PD              |
| reisdocument | 1      | nummer                            | NA2010111       |
| reisdocument | 1      | autoriteitVanAfgifte              | B1901           |
| reisdocument | 1      | datumIngangDocument               | 1990-01-10      |
| reisdocument | 1      | datumEindeDocument                | 2000-01-10      |
| reisdocument | 1      | datumUitgifte                     | 1990-01-10      |
| reisdocument | 1      | datumInhoudingVermissing          | 2000-01-10      |
| reisdocument | 1      | aanduidingInhoudingVermissingCode | I               |

And hebben attributen in voorkomens de volgende waardes:
| groep        | nummer | attribuut            | verwachteWaarde |
| reisdocument | 2      | soortCode            | PD              |
| reisdocument | 2      | nummer               | NA2010112       |
| reisdocument | 2      | autoriteitVanAfgifte | B1901           |
| reisdocument | 2      | datumIngangDocument  | 2000-01-10      |
| reisdocument | 2      | datumEindeDocument   | 2015-01-10      |
| reisdocument | 2      | datumUitgifte        | 2000-01-10      |

And hebben attributen in voorkomens de volgende waardes:
| groep        | nummer | attribuut                         | verwachteWaarde |
| reisdocument | 3      | soortCode                         | PD              |
| reisdocument | 3      | nummer                            | NA2010111       |
| reisdocument | 3      | autoriteitVanAfgifte              | B1901           |
| reisdocument | 3      | datumIngangDocument               | 2000-01-10      |
| reisdocument | 3      | datumEindeDocument                | 2000-01-10      |
| reisdocument | 3      | datumUitgifte                     | 1990-01-10      |
| reisdocument | 3      | datumInhoudingVermissing          | 2000-01-10      |
| reisdocument | 3      | aanduidingInhoudingVermissingCode | I               |
