Meta:
@regels                 R2031
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
R2031 Bij einde relatie met Land/gebied ongelijk aan Nederland zijn geen Nederlandse locatiegegevens toegestaan

Scenario:   Gemeente einde ingevuld bij het beëindigen van een relatie in Nederland
            LT: OHNL02C10T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/OHNL02C10T30.xls

Given pas laatste relatie van soort 1 aan tussen persoon 382625001 en persoon 789062153 met relatie id 2000097 en betrokkenheid id 2000098

When voer een bijhouding uit OHNL02C10T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHNL/expected/OHNL02C10T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 382625001 niet als PARTNER betrokken bij een HUWELIJK