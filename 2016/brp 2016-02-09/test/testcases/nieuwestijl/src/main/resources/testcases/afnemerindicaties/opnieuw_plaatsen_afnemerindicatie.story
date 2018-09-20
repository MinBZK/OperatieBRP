Meta:
@sprintnummer           66
@epic                   Plaatsing en verwijdering afnemerindicatie
@auteur                 dihoe
@jiraIssue              TEAMBRP-2360
@status                 Klaar

Narrative:
    Het opnieuw plaatsen van een afnemerindicatie gaat fout (op de proeftuin) - bug

Scenario: 1.opnieuw plaatsen van een afnemerindicatie op bsn geeft een correct antwoord

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de database is gereset voor de personen 340014155

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand opnieuw_plaatsen_afnemerindicatie_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then wacht tot alle berichten zijn ontvangen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand opnieuw_plaatsen_afnemerindicatie_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then wacht tot alle berichten zijn ontvangen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand opnieuw_plaatsen_afnemerindicatie_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then wacht tot alle berichten zijn ontvangen
