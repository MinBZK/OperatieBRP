Meta:
@auteur                 dihoe
@status                 Klaar
@regels                 BRAL2111,R1636
@sleutelwoorden         voltrekkingHuwelijkInNederland,intaketest

Narrative:
Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: 1. In een H/P-relatie zijn twee partners betrokken

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,141901317,631512457,140678177,698938057 zijn verwijderd
Given de standaardpersoon Sandy met bsn 140678177 en anr 3679783698 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 698938057 en anr 7049482514 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand voltrekking_huwelijk_in_nederland_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/intake/huwelijk_en_geregistreerd_partnerschap/expected_huwelijk_en_geregistreerd_partnerschap_berichten/expected_voltrekking_huwelijk_in_nederland_scenario_1.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 140678177 wel als PARTNER betrokken bij een HUWELIJK
