Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative: R2429 Nadere aanduiding verval moet een geldige waarde hebben

Scenario:   Nadere aanduiding verval is gevuld met de waarde "O"
            LT: ONHW01C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-ONHW/ONHW-001.xls

Given pas laatste relatie van soort 1 aan tussen persoon 661372169 en persoon 512400969 met relatie id 50000001 en betrokkenheid id 50000001

When voer een bijhouding uit ONHW01C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/ONHW/expected/ONHW01C10T10.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 661372169 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 512400969 niet als PARTNER betrokken bij een HUWELIJK
