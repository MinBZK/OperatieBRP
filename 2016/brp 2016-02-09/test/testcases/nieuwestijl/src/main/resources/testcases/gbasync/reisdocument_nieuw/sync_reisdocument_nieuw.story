Meta:
@sprintnummer       77
@epic               PDT & aanpassen SW Leveren tbv productiedata 3.1
@auteur             aapos
@jiraIssue          TEAMBRP-3219
@status             Onderhanden
@sleutelwoorden     PDT, Conversie GBA, Reisdocumenten

Narrative:          Als Team BRP
                    Wil ik het BRP lever koppelvlak middels geconverteerde GBA testdata testen
                    Zodat ik kan bepalen of geconverteerde data wel/niet goed wordt geleverd

Scenario:           BRP levering van een PL met een verwijderd reisdocument
                    Verwacht resultaat:
                    -   Asynchroon bericht van de type "GBA - Synchronisatie"
                    -   1 reisdocumenten
                    -   1 toegevoegde groep reisdocument

Given een initiele vulling uit bestand sync_reisdocument_nieuw_initieel.xls
Given een sync uit bestand sync_reisdocument_nieuw_synchronisatie.xls

When voor persoon 260794521 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes                           |
| synchronisatie       | categorie           | GBA - Synchronisatie, GBA - Synchronisatie |
| identificatienummers | burgerservicenummer | 260794521                                  |

And heeft het bericht 1 groepen 'reisdocument'

And hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep        | nummer | verwerkingssoort |
| reisdocument | 1      | Toevoeging       |

And hebben attributen in voorkomens de volgende waardes:
| groep        | nummer | attribuut            | verwachteWaarde |
| reisdocument | 1      | soortCode            | PD              |
| reisdocument | 1      | nummer               | NA2010111       |
| reisdocument | 1      | autoriteitVanAfgifte | B1901           |
| reisdocument | 1      | datumIngangDocument  | 1990-01-10      |
| reisdocument | 1      | datumEindeDocument   | 2000-01-10      |
| reisdocument | 1      | datumUitgifte        | 1990-01-10      |
