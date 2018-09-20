Meta:
@status                 Klaar
@sleutelwoorden         overlijdenInNederland,intaketest

Narrative:
Overlijden in Nederland, registratie overlijden

Scenario: 1. Het hele response bericht wordt gecontroleerd, en ook een aantal waardes in de database.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,970541065 zijn verwijderd
Given de standaardpersoon Sandy met bsn 970541065 en anr 2147537810 zonder extra gebeurtenissen

Given administratieve handeling van type overlijdenInNederland , met de acties registratieOverlijden
And testdata uit bestand overlijden_in_nederland_01.yml
When het bericht wordt verstuurd

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/intake/overlijden/expected_overlijden_berichten/expected_overlijden_in_nederland_scenario_1.xml voor expressie //brp:bhg_ovlRegistreerOverlijden_R

Then in kern select datoverlijden, gemoverlijden, wplnaamoverlijden from kern.pers where bsn = 970541065 de volgende gegevens:
| veld              | waarde     |
| datoverlijden     | 20151205   |
| gemoverlijden     | 7019       |
| wplnaamoverlijden | Blauwestad |
