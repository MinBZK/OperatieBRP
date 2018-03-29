Meta:
@auteur                 fuman
@regels                 R1863
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG


Narrative:
Gemeente einde H/GP moet gelijk zijn aan gemeente aanvang

Scenario:   Personen Anne Bakker (Ingeschrevene-Ingezetene) en Jan Pietersen (Onbekende) gaan scheiden, Relatie.Gemeente.einde (reden "S") = Relatie.Gemeente.aanvang
            LT: OHNL01C10T10

Gemeente BRP 1

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/OHNL-Anne.xls

Given pas laatste relatie van soort 1 aan tussen persoon 590984809 en persoon 773201993 met relatie id 2000073 en betrokkenheid id 2000074

When voer een bijhouding uit OHNL01C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHNL/expected/OHNL01C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 590984809 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 773201993 wel als PARTNER betrokken bij een HUWELIJK
