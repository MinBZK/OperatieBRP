Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Registratie Voltrekking huwelijk in buitenland

Scenario: Registratie Voltrekking huwelijk in buitenland tussen I-I en Pseudo-persoon
          LT: VHBL04C20T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VHBL/VHBL04C20T10-001.xls

When voer een bijhouding uit VHBL04C20T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHBL/expected/VHBL04C20T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 245020937 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 919866761 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 4247946017 uit database en vergelijk met expected VHBL04C20T10-persoon1.xml
