Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Registratie aangaan GP in NL tussen I-I en I-I

Scenario: Registratie aangaan GP in NL tussen I-I en I-I (Happy Flow)
          LT: AGNL02C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/AGNL02C10T10-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL-AGNL/AGNL02C10T10-Piet.xls

When voer een bijhouding uit AGNL02C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL02C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 508133129 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 261647945 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then lees persoon met anummer 9781813025 uit database en vergelijk met expected AGNL02C10T10-persoon1.xml
Then lees persoon met anummer 9564862753 uit database en vergelijk met expected AGNL02C10T10-persoon2.xml








