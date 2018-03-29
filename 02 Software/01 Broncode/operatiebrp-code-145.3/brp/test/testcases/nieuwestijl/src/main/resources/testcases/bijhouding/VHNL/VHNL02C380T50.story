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

Scenario: 2. Bij Relatie.Datum aanvang tussen C nicht en B tante is er geen verwantschap
          LT: VHNL02C380T50

!--Laden PL Moeder A plus geboorte Dochter B Pseudo
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C380T50.xls

When voer een bijhouding uit VHNL02C380T50a.xml namens partij 'Gemeente BRP 1'

!-- Geboorte  Dochter F
When voer een bijhouding uit VHNL02C380T50b.xml namens partij 'Gemeente BRP 1'

!-- Geboorte  Dochter C
When voer een bijhouding uit VHNL02C380T50c.xml namens partij 'Gemeente BRP 1'

!-- Huwelijk B Pseudo en C
When voer een bijhouding uit VHNL02C380T50d.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C380T50.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R


Scenario: 3. DB reset
          postconditie
Given de database is aangepast met: update kern.gem set dataanvgel = 20160101 where naam = 'Gemeente BRP 1'
And de database is aangepast met: update kern.partij set datingang = 20160101 where naam = 'Gemeente BRP 1'
And de database is aangepast met: update kern.his_partij set datingang = 20160101 where naam = 'Gemeente BRP 1'
Given maak bijhouding caches leeg
