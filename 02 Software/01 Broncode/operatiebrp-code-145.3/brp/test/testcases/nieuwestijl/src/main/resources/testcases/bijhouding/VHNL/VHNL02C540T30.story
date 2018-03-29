Meta:
@status                 Klaar
@regels                 R2030
@usecase                UCS-BY.HG

Narrative:
R2030 Bij aanvang relatie met Land/gebied <> Nederland zijn geen Nederlandse locatiegegevens toegestaan

Scenario:   Woonplaatsnaam aanvang ingevuld bij het aangaan van een relatie in het buitenland
            LT: VHNL02C540T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C540T30.xls

When voer een bijhouding uit VHNL02C540T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C540T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 657218121 wel als PARTNER betrokken bij een HUWELIJK