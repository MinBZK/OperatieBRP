Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2434 Geboorte van ingezetene mag alleen ingediend worden door gemeente van geboorte of bijhoudingspartij van OUWKIG

Scenario:   Administratieve handeling.Partij komt niet overeen met één van de 3 partijen uit de definitie
            LT: AUAH01C170T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C170T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C170T10-002.xls

When voer een bijhouding uit AUAH01C170T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AUAH/expected/AUAH01C170T10.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R