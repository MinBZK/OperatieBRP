Meta:
@status                 Klaar
@regels                 R2030
@usecase                UCS-BY.HG

Narrative:
R2030 Bij aanvang relatie met Land/gebied <> Nederland zijn geen Nederlandse locatiegegevens toegestaan

Scenario:   Woonplaatsnaam aanvang ingevuld bij het aangaan van een relatie in het buitenland
            LT: VHNL02C540T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL02C540T20.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C540T20.txt

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK