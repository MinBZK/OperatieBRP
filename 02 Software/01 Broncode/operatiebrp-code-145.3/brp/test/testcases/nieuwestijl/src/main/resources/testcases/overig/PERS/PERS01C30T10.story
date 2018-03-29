Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Persoonsbeelden GBA en BRP (verhuzing)

Scenario:   Verhuizing BRP persoon intergemeentelijk
            LT: PERS01C30T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-PERS/PERS01C30T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-PERS/PERS01C30T10-002.xls

!-- Geboorte Libby-Marie
When voer een bijhouding uit PERS01C30T10a.xml namens partij 'Gemeente BRP 1'

!-- Verhuizing Libby-Marie
When voer een bijhouding uit PERS01C30T10b.xml namens partij 'Gemeente BRP 2'









