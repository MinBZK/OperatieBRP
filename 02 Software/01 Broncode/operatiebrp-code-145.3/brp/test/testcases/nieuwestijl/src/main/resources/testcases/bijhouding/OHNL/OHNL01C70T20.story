Meta:
@status                 Klaar
@regels                 R1883
@usecase                UCS-BY.HG

Narrative:
R1883 Datum aanvang geldigheid actie moet gelijk zijn aan datum einde relatie

Scenario: R1883 Actie.Datum aanvang geldigheid in de hoofdactie ongelijk aan Relatie.Datum einde
          LT: OHNL01C70T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/OHNL-Anne.xls

Given pas laatste relatie van soort 1 aan tussen persoon 590984809 en persoon 773201993 met relatie id 2000085 en betrokkenheid id 2000086

When voer een bijhouding uit OHNL01C70T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/OHNL/expected/OHNL01C70T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 590984809 wel als PARTNER betrokken bij een HUWELIJK