Meta:
@status                 Klaar
@sleutelwoorden         wijzigingGeslachtsnaam,intaketest

Narrative:
Naam en geslacht, met administratieve handeling wijziging geslachtsnaam, met de acit registratie naam geslacht

Scenario: 1. Persoon krijgt een ander geslachtsnaam

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,192035241 zijn verwijderd
Given de standaardpersoon Sandy met bsn 192035241 en anr 2310357010 zonder extra gebeurtenissen

Given administratieve handeling van type wijzigingGeslachtsnaam , met de acties registratieNaamGeslacht
And testdata uit bestand wijziging_geslachtsnaam_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/intake/naam_en_geslacht/expected_naam_en_geslacht_berichten/expected_wijziging_geslachtsnaam_scenario_1.xml voor expressie //brp:bhg_nmgRegistreerNaamGeslacht_R

Then in kern select geslnaamstam from kern.pers where bsn = 192035241 de volgende gegevens:
| veld         | waarde |
| geslnaamstam | Smit   |
