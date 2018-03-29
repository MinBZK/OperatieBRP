Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2480 OUWKIG mag niet overleden zijn VOOR datum geboorte kind

Scenario:   OUWKIG is Overleden op Datum geboorte Kind
            LT: GBNL01C240T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C240.xls

When voer een bijhouding uit GBNL01C240T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C240T30.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
