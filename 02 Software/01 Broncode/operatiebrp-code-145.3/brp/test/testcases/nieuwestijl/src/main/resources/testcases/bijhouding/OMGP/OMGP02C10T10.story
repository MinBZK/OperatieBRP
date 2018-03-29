Meta:
@status                 Klaar
@regels                 R1863
@usecase                UCS-BY.HG

Narrative: R1863 Gemeente einde H/GP moet gelijk zijn aan gemeente aanvang

Scenario: Gemeente einde ongelijk Gemeente aanvang; Relatie.reden.einde "Omzetting"
          LT: OMGP02C10T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-OMGP/OMGP02C10T10-001.xls

Given pas laatste relatie van soort 2 aan tussen persoon 368360441 en persoon 179579113 met relatie id 42000001 en betrokkenheid id 42000001

When voer een bijhouding uit OMGP02C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OMGP/expected/OMGP02C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 368360441 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
