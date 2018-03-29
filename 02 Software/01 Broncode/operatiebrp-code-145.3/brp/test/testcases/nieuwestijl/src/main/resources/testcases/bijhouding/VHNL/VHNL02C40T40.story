Meta:
@status                 Klaar
@regels                 R1469
@usecase                UCS-BY.HG

Narrative: R1469 Datum geboorte mag niet onbekend zijn bij geboorte in Nederland

Scenario: Geboortedatum deels onbekend bij geboorte in Nederland(19660100) Deblokkeer de regel
          LT: VHNL02C40T40

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C40T40.xls

When voer een bijhouding uit VHNL02C40T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C40T40.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422088201 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 8518291730 uit database en vergelijk met expected VHNL02C40T40-persoon1.xml
