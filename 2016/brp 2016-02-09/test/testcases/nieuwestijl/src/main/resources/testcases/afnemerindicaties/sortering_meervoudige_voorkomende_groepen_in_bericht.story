Meta:
@auteur                 dihoe
@sprintnummer           61
@epic                   Mutatielevering op basis van afnemerindicatie
@jiraIssue              TEAMBRP-1804
@regels                 VR00092
@status                 Klaar

Narrative:
    In order to een logisch overzicht hebben van bij elkaar horende historie elementen
    As a afnemer
    I want to een logisch gegroepeerde volgorde van meervouding voorkomende groepen in een berich

Scenario: sortering meervoudige voorkomende groepen in bericht

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de database is gereset voor de personen 340014155
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand plaatsing_afnemerindicatie_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
groep 		                | attribuut            | verwachteWaardes
nationaliteit               | nationaliteitCode    | 0001,0001,0001,0001,0001,0027
geslachtsnaamcomponent      | volgnummer           | 1,2,2,2
voornaam                    | volgnummer           | 1,2,3,3,3
verificatie                 | partijCode           | 199900, 199900
verificatie                 | soort                | Attestie de vita, SoortVerificatie
