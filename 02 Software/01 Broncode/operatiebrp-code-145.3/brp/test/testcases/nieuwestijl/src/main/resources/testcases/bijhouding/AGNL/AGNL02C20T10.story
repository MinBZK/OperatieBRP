Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Registratie aangaan GP in NL tussen I-I en Pseudo-persoon

Scenario: Registratie aangaan GP in NL tussen I-I en Pseudo-persoon met meegeven persoonsgegevens
          LT: AGNL02C20T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/AGNL02C20T10-Libby.xls

When voer een bijhouding uit AGNL02C20T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL02C20T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 372625897 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 173407353 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then lees persoon met anummer 7461965089 uit database en vergelijk met expected AGNL02C20T10-persoon1.xml









