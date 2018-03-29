Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Persoonsbeelden GBA en BRP (huwelijk en ontbinding)

Scenario:   Huwelijk tussen GBA persoon en GBA pseudo en ontbinding ervan
            LT: PERS01C10T40

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-PERS/PERS01C10T40-001.xls

!-- Ontbinding Libby-Marie (I) en Pieter (P)

Given pas laatste relatie van soort 1 aan tussen persoon 803995209 en persoon 793558025 met relatie id 70000001 en betrokkenheid id 70000001

When voer een bijhouding uit PERS01C10T30c.xml namens partij 'Gemeente BRP 1'

Then is in de database de persoon met bsn 803995209 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 793558025 niet als PARTNER betrokken bij een HUWELIJK






