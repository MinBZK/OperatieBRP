Meta:
@sprintnummer           76
@epic                   Verbeteren testtooling
@auteur                 dihoe
@jiraIssue              TEAMBRP-3080
@status                 Klaar
@sleutelwoorden         Intaketest, Synchronisatie

Narrative: Intake test synchronisatie

Scenario: 1. Synchroniseer persoon, controleer volledigbericht met een abonnement waar alles getoond wordt

Given de personen 875271467, 814591139, 999646564 zijn verwijderd
Given de standaardpersoon Gregory met bsn 999646564 en anr 999646564 met extra gebeurtenissen:
verhuizing(partij: 'Gemeente Heemstede', aanvang: 20101010, registratieDatum: 20101010) {
    naarGemeente 'Heemstede',
       straat: 'Manpadslaan', nummer: 4, postcode: '2105MA', woonplaats: "Heemstede"
}

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/intake/expected_synchronisatie_berichten/expected_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon



Scenario: 2. Synchroniseer stamgegeven
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand synchronisatie_verzoek_stamgegevens.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Then is er voor xpath //brp:elementTabel/brp:element een node aanwezig in het antwoord bericht
