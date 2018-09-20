Meta:
@status                 Klaar
@sleutelwoorden         wijzigingVerstrekkingsbeperking,intaketest

Narrative:
Document, verzoek en mededeling: administratieve handeling wijziging verstrekkingsbeperking met de actie registratie verstrekkingsbeperking

Scenario: Persoon heeft een volledige verstrekkingsbeperking

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,290071641 zijn verwijderd
Given de standaardpersoon Sandy met bsn 290071641 en anr 6045216018 zonder extra gebeurtenissen

And administratieve handeling van type wijzigingVerstrekkingsbeperking , met de acties registratieVerstrekkingsbeperking
And testdata uit bestand wijziging_verstrekkingsbeperking_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/intake/document_verzoek_en_mededeling/expected_document_verzoek_en_mededeling_berichten/expected_wijziging_verstrekkingsbeperking_scenario_1.xml voor expressie //brp:bhg_dvmRegistreerMededelingVerzoek_R

