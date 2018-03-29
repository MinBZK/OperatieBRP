Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1815 Geen nationaliteit - ook geen onbekende - en ook geen indicatie staatloos aanwezig

Scenario:   Registratie geboorte in NL zonder nationaliteit en zonder staatloos
            LT: GBNL01C190T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/mama.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/papa.xls

When voer een bijhouding uit GBNL01C190T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C190T10.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R

