Meta:
@status                 Onderhanden
@usecase                UCS-BY.0.AS

Narrative: R2181 Een persoon wordt of aangewezen met een objectsleutel of het betreft een pseudo persoon

Scenario: Een Pseudo-persoon (P) wordt aangewezen met objectsleutel
          LT: GBNL02C150T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL02C150T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL02C150T10-002.xls

When voer een bijhouding uit GBNL02C150T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL02C150T10.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
