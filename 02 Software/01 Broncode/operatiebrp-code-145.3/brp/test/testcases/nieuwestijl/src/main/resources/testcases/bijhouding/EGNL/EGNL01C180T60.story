Meta:
@status                 Klaar
@regels                 R1863
@usecase                UCS-BY.HG

Narrative: R1863 Gemeente einde H/GP moet gelijk zijn aan gemeente aanvang

Scenario: Gemeente einde ongelijk Gemeente aanvang; Relatie.reden.einde "Vermissing van een persoon gevolgd door andere verbintenis"
          LT: EGNL01C180T60

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL01C180T60-001.xls

Given pas laatste relatie van soort 2 aan tussen persoon 272204857 en persoon 358309177 met relatie id 4000006 en betrokkenheid id 4000006

When voer een bijhouding uit EGNL01C180T60.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL01C180T60.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 272204857 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
