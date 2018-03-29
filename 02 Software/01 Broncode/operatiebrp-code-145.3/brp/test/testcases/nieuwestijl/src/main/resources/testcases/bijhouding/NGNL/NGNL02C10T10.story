Meta:
@status                 Klaar
@regels                 R1863
@usecase                UCS-BY.HG

Narrative: R1863 Gemeente einde H/GP moet gelijk zijn aan gemeente aanvang

Scenario: Gemeente einde ongelijk Gemeente aanvang; Relatie.reden.einde "Nietigverklaring"
          LT: NGNL02C10T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-NGNL/NGNL02C10T10-001.xls

Given pas laatste relatie van soort 2 aan tussen persoon 457158505 en persoon 565398313 met relatie id 43000001 en betrokkenheid id 43000001

When voer een bijhouding uit NGNL02C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/NGNL/expected/NGNL02C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 457158505 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP






