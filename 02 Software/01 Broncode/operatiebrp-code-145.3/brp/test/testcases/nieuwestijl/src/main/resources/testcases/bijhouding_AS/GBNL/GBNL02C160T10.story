Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2652 Registratie staatloos en Registratie nationaliteit mogen niet beide in een administratieve handeling voorkomen.

Scenario:   Registratie staatloos en Registratie nationaliteit zitten beide in een Administratieve handeling 
            LT: GBNL02C160T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL04C30T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL04C30T20-002.xls

When voer een bijhouding uit GBNL02C160T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL02C160T10.xml voor expressie /
