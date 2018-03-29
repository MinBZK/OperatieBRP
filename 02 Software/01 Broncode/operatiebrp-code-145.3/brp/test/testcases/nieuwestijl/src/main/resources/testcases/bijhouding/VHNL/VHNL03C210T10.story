Meta:
@status                 Klaar
@regels                 R2314
@usecase                UCS-BY.HG

Narrative: R2413 Een toelichting ontlening is verplicht bij het deblokkeren van een melding

Scenario: Ontleningstoelichting niet opgegeven en niet meegestuurd
            LT : VHNL03C210T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL03C210-001.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL03C210-002.xls

When voer een bijhouding uit VHNL03C210T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL03C210T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 715347913 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 531122025 niet als PARTNER betrokken bij een HUWELIJK

