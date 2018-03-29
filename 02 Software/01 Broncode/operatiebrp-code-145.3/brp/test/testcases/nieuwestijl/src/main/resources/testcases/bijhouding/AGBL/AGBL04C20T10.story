Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Registratie aangaan GP in Buitenland tussen I-I en Pseudo-persoon

Scenario: Registratie aangaan GP in Buitenland tussen I-I en Pseudo-persoon met meegeven persoonsgegevens
          LT: AGBL04C20T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL04C20T10-001.xls

When voer een bijhouding uit AGBL04C20T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGBL/expected/AGBL04C20T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 515157417 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 249487561 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then lees persoon met anummer 2691706913 uit database en vergelijk met expected AGBL04C20T10-persoon1.xml
