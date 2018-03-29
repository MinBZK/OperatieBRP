Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Persoonsbeelden GBA en BRP (huwelijk en ontbinding)

Scenario:   Huwelijk tussen GBA persoon en GBA pseudo
            LT: PERS01C10T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-PERS/PERS01C10T20-001.xls

!-- Huwelijk Libby-Marie (I) en Pieter (P)
When voer een bijhouding uit PERS01C10T10b.xml namens partij 'Gemeente BRP 1'

Then is in de database de persoon met bsn 306706921 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 793558025 wel als PARTNER betrokken bij een HUWELIJK








