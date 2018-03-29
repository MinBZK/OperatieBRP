Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: R1868 Geen verwantschap tussen partners bij voltrekking H/GP

Scenario: 1. DB init
          preconditie

Given de database is aangepast met: update kern.gem set dataanvgel = 19000101 where naam = 'Gemeente BRP 1'
And de database is aangepast met: update kern.partij set datingang = 19000101 where naam = 'Gemeente BRP 1'
And de database is aangepast met: update kern.his_partij set datingang = 19000101 where naam = 'Gemeente BRP 1'

Given maak bijhouding caches leeg

Scenario: 2. Bij Relatie.Datum aanvang tussen F broer en B zus is er een verwantschap
          LT: VHNL02C380T30

!-- Laden PL Moeder A
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C380T30.xls

!-- geboorte broer F
When voer een bijhouding uit VHNL02C380T30a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

!-- geboorte zus D
When voer een bijhouding uit VHNL02C380T30b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

!-- Huwelijk
When voer een bijhouding uit VHNL02C380T30c.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C380T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 159247913 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK


Scenario: 3. DB reset
          postconditie
Given de database is aangepast met: update kern.gem set dataanvgel = 20160101 where naam = 'Gemeente BRP 1'
And de database is aangepast met: update kern.partij set datingang = 20160101 where naam = 'Gemeente BRP 1'
And de database is aangepast met: update kern.his_partij set datingang = 20160101 where naam = 'Gemeente BRP 1'
Given maak bijhouding caches leeg
