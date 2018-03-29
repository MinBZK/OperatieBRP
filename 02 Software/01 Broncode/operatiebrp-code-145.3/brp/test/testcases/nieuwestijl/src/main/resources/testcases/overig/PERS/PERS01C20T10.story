Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Persoonsbeelden GBA en BRP (geboorte)

Scenario:   Geboorte BRP persoon met 2 ouders
            LT: PERS01C20T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-PERS/PERS01C20T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-PERS/PERS01C20T10-002.xls

!-- Geboorte Libby-Marie
When voer een bijhouding uit PERS01C20T10a.xml namens partij 'Gemeente BRP 1'









