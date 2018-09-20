Meta:
@status                 Klaar
@sleutelwoorden         verhuizingIntergemeentelijk,intaketest
@regels                 R1384,BRBY0524

Narrative:
De Gemeente van een Persoon \ Adres dat met Verhuizing intergemeentelijk wordt geregistreerd,
moet ongelijk zijn aan de Gemeente van het Persoon \ Adres dat door diezelfde Bijhouding komt te vervallen.

Scenario: 1. Een persoon is verhuisd naar een nieuwe gemeente

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/bijhoudingautorisatie.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,931519433 zijn verwijderd
Given de standaardpersoon Sandy met bsn 931519433 en anr 8192086802 zonder extra gebeurtenissen

And administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand verhuizing_intergemeentelijk_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And is het antwoordbericht gelijk aan /testcases/bijhouding/intake/verblijf_en_adres/expected_verblijf_en_adres_berichten/expected_verhuizing_intergemeentelijk_scenario_1.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

