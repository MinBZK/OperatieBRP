Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1815 Geen nationaliteit - ook geen onbekende - en ook geen indicatie staatloos aanwezig

Scenario:   Beeindiging nationaliteit zonder registratie staatloos en zonder registratie nieuw nationaliteit
            LT: GNNG01C90T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG04C90T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG04C90T10-002.xls

When voer een bijhouding uit GNNG01C90T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GNNG/expected/GNNG01C90T10.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R


