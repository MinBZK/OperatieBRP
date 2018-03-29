Meta:
@status                 Klaar
@regels                 R1871
@usecase                UCS-BY.HG

Narrative: R1871 Landgebied aanvang moet geldig zijn op datum aanvang

Scenario: 1. DB init
          preconditie

Given voer enkele update uit: UPDATE kern.landgebied
                              SET dataanvgel='20161231'
                              WHERE code=(select landgebied.code from kern.landgebied where naam='Zuid-Soedan')

Given maak bijhouding caches leeg

Scenario: 2. Relatie.Land/gebied aanvang op de Relatie.Datum aanvang nog niet aangevangen
             LT: AGBL02C50T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL-002.xls

When voer een bijhouding uit AGBL02C50T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGBL/expected/AGBL02C50T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 480112745 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 195517441 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Scenario: 3. DB reset
          postconditie
Given de database is aangepast met: UPDATE kern.landgebied
                                    SET dataanvgel='20110709'
                                    WHERE code=(select landgebied.code from kern.landgebied where naam='Zuid-Soedan')

Given maak bijhouding caches leeg