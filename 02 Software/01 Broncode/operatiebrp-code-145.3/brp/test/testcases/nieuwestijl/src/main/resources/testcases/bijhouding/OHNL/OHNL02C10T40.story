Meta:
@regels                 R2031
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
R2031 Bij einde relatie met Land/gebied ongelijk aan Nederland zijn geen Nederlandse locatiegegevens toegestaan

Scenario:   Gemeente einde en Woonplaatsnaam einde niet ingevuld bij het einde van een relatie in het buitenland
            LT: OHNL02C10T40

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/OHNL-Anne.xls

Given pas laatste relatie van soort 1 aan tussen persoon 590984809 en persoon 773201993 met relatie id 2000099 en betrokkenheid id 2000100

When voer een bijhouding uit OHNL02C10T40.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/OHNL/expected/OHNL02C10T40.txt

Then is in de database de persoon met bsn 590984809 wel als PARTNER betrokken bij een HUWELIJK