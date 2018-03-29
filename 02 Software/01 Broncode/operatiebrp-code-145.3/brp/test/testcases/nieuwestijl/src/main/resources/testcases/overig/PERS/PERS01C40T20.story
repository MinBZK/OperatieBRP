Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Persoonsbeelden GBA en BRP (huwelijk en ontbinding)

Scenario:   Huwelijk tussen GBA persoon en GBA pseudo en ontbinding ervan
            LT: PERS01C40T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-PERS/PERS01C40T20-001.xls

Then is in de database de persoon met bsn 663844745 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 944441993 niet als PARTNER betrokken bij een HUWELIJK








