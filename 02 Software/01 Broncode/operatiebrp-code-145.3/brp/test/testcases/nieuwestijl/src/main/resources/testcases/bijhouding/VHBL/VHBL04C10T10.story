Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Registratie Voltrekking huwelijk in buitenland

Scenario: Registratie Voltrekking huwelijk in buitenland tussen I-I en I-I
          LT: VHBL04C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VHBL/VHBL04C10T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-VHBL/VHBL04C10T10-002.xls

When voer een bijhouding uit VHBL04C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHBL/expected/VHBL04C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 870956425 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 629269257 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 6510516513 uit database en vergelijk met expected VHBL04C10T10-persoon1.xml
Then lees persoon met anummer 5271624737 uit database en vergelijk met expected VHBL04C10T10-persoon2.xml
