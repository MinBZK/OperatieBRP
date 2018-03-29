Meta:
@status                 Klaar
@regels                 R2165
@usecase                UCS-BY.HG

Narrative: R2165 Land/gebied aanvang relatie moet verwijzen naar een bestaand stamgegeven

Scenario: Relatie.Land/gebied aanvang is geen bestaand stamgegeven
          LT: AGBL03C10T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL-002.xls

When voer een bijhouding uit AGBL03C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGBL/expected/AGBL03C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 480112745 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 195517441 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP






