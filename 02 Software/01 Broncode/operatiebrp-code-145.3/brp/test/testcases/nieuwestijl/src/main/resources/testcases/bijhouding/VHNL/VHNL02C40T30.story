Meta:
@auteur                 jozon
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R1469
@usecase                UCS-BY.HG

Narrative: R1469 Datum geboorte mag niet onbekend zijn bij geboorte in Nederland

Scenario: Geboortedatum deels onbekend bij geboorte in buitenland(1966)
          LT: VHNL02C40T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C40T30.xls

When voer een bijhouding uit VHNL02C40T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C40T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL02C40T30-persoon1.xml




