Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2350 Datum mededeling verblijfsrecht moet een volledig bekende datum zijn

Scenario: Dag onbekend voor datum mededeling verblijfsrecht met deblokkeren van de melding
          LT: WZVB02C30T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-WZVB/WZVB02C30T30-001.xls

When voer een bijhouding uit WZVB02C30T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/WZVB/expected/WZVB02C30T30.xml voor expressie //brp:bhg_vbaRegistreerVerblijfsrecht_R