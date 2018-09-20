Meta:
@sprintnummer           77
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@auteur                 aapos
@jiraIssue              TEAMBRP-3219
@status                 Onderhanden
@sleutelwoorden         Voorbeeld_Conversie_GBA

Narrative: Leveren geconverteerde PL

Scenario: 3 lever geconverteerde PL met verhuizing naar een andere gemeente
Given een initiele vulling uit bestand Voorbeeld_Import_Geconverteerde_GBA_Data.story_Initieel.xls
Given een sync uit bestand Voorbeeld_Import_Geconverteerde_GBA_Data.story_Synchronisatie.xls


When voor persoon 580074985 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
