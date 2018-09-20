Meta:
@status                 Klaar
@sleutelwoorden         wijzigingUitsluitingKiesrecht,intaketest

Narrative:
Verkiezingen, uitsluiting kiesrecht met actie registratie uitsluiting kiesrecht

Scenario: 1. Het hele response bericht wordt gecontroleerd, en ook een aantal waardes in de database.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,985325641 zijn verwijderd
Given de standaardpersoon Sandy met bsn 985325641 en anr 9120518930 zonder extra gebeurtenissen

And administratieve handeling van type wijzigingUitsluitingKiesrecht , met de acties registratieUitsluitingKiesrecht
And testdata uit bestand wijziging_uitsluiting_kiesrecht_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/intake/verkiezingen/expected_verkiezingen_berichten/expected_wijziging_uitsluiting_kiesrecht_scenario_1.xml voor expressie //brp:bhg_vkzRegistreerKiesrecht_R

Then in kern select induitslkiesr from kern.pers where bsn = 985325641 de volgende gegevens:
| veld          | waarde |
| induitslkiesr | true   |
