Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1588 Administratienummer mag niet reeds voorkomen in de BRP

Scenario: Registratie met A-nr waarbij er reeds een Ingeschrevene met status "Emigratie" hetzelfde A-nr heeft
          LT: GBNL01C20T30

Given alle personen zijn verwijderd

!-- De ouders
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/mama.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/papa.xls

!-- Een Ingeschrevene met status "Actueel"
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C20T30-001.xls

When voer een bijhouding uit GBNL01C20T30a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

When voer een bijhouding uit GBNL01C20T30b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C20T30.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
