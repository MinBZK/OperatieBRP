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

Scenario: 2. Bij Relatie.Datum aanvang tussen E moeder en F vader is er een verwantschap met D kind maar hiervoor geldt een uitzondering
          LT: VHNL02C380T40

!-- Laden PL vader E en Moeder F
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C380T40-001.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C380T40-002.xls

!-- geboorte Dochter D
When voer een bijhouding uit VHNL02C380T40a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

!-- Huwelijk E en F
When voer een bijhouding uit VHNL02C380T40b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C380T40.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 447762825 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 321497417 wel als PARTNER betrokken bij een HUWELIJK


Scenario: 3. DB reset
          postconditie
Given de database is aangepast met: update kern.gem set dataanvgel = 20160101 where naam = 'Gemeente BRP 1'
And de database is aangepast met: update kern.partij set datingang = 20160101 where naam = 'Gemeente BRP 1'
And de database is aangepast met: update kern.his_partij set datingang = 20160101 where naam = 'Gemeente BRP 1'
Given maak bijhouding caches leeg
