Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R1610 In bijhouding moet partij naar Bijhoudingspartij verwijzen

Scenario: R1610 Een partij verwijst niet naar een bijhoudingspartij
          LT: AUAH01C160T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C160.xls

When voer een bijhouding uit AUAH01C160T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AUAH/expected/AUAH01C160T10.xml voor expressie //brp:bhg_vbaRegistreerVerblijfsrecht_R
