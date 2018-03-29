Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Registratie aangaan GP in Buitenland tussen I-I en I-I

Scenario: Registratie aangaan GP in Buitenland tussen I-I en I-I
          LT: AGBL04C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL04C10T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL04C10T10-002.xls

When voer een bijhouding uit AGBL04C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGBL/expected/AGBL04C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 381137673 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 215340681 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then lees persoon met anummer 6151483169 uit database en vergelijk met expected AGBL04C10T10-persoon1.xml
Then lees persoon met anummer 6039057185 uit database en vergelijk met expected AGBL04C10T10-persoon2.xml
