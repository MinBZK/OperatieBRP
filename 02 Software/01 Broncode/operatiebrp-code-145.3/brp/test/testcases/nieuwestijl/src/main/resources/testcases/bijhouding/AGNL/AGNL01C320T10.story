Meta:
@auteur                 fuman
@status                 Klaar
@regels                 R1870
@usecase                UCS-BY.HG

Narrative: R1870 Gemeente aanvang relatie niet geldig op datum aanvang relatie

Scenario: 1. DB init
          preconditie

Given voer enkele update uit: UPDATE kern.partij
                              SET dateinde='20220101'
                              WHERE code=(select partij.code from kern.partij where naam = 'Gemeente BRP beeindigd 1')
Given voer enkele update uit: UPDATE kern.his_partij
                              SET dateinde='20220101'
                              where partij=(select his_partij.partij from kern.his_partij where naam = 'Gemeente BRP beeindigd 1')

Given maak bijhouding caches leeg

Scenario: 2. R1870 Personen Libby Thatcher (Ingeschrevene-Ingezetene, Niet NL Nat) en Piet Jansen (Onbekende) gaan trouwen, Gemeente aanvang relatie niet geldig op datum aanvang relatie
             LT: AGNL01C320T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Victor.xls

When voer een bijhouding uit AGNL01C320T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C320T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 110477200 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Scenario: 3. DB reset
          postconditie
Given de database is aangepast met: UPDATE kern.partij
                                    SET dateinde='20160101'
                                    WHERE code=(select partij.code from kern.partij where naam = 'Gemeente BRP beeindigd 1')
Given de database is aangepast met: UPDATE kern.his_partij
                                    SET dateinde='20160101'
                                    where partij=(select his_partij.partij from kern.his_partij where naam = 'Gemeente BRP beeindigd 1')

Given maak bijhouding caches leeg