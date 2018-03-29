Meta:
@status                 Klaar
@regels                 R2131
@usecase                UCS-BY.HG

Narrative: R2131 Gemeente einde moet registergemeente zijn

Scenario: De gemeente einde is geen registergemeente
          LT: OMGP02C20T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-OMGP/OMGP02C20T10-001.xls

Given pas laatste relatie van soort 2 aan tussen persoon 737157513 en persoon 166087385 met relatie id 42000101 en betrokkenheid id 42000101

When voer een bijhouding uit OMGP02C20T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OMGP/expected/OMGP02C20T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 737157513 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 737157513 niet als PARTNER betrokken bij een HUWELIJK
