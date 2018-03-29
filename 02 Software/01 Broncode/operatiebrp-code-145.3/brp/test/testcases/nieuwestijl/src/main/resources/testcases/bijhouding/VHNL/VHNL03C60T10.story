Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: R2132 Melding als samengestelde naam wordt gewijzigd en namenreeks is gelijk aan ja

Scenario: 1. DB init
          preconditie

Given de database is aangepast met: update kern.gem set dataanvgel = 19000101 where naam = 'Gemeente BRP 1'
And de database is aangepast met: update kern.partij set datingang = 19000101 where naam = 'Gemeente BRP 1'
And de database is aangepast met: update kern.his_partij set datingang = 19000101 where naam = 'Gemeente BRP 1'

Given maak bijhouding caches leeg

Scenario: 2. Personen Libby Thatcher (Ingeschrevene-Ingezetene, Niet NL Nat) en Piet Jansen (Ingeschrevene-Ingezetene, NL Nat) gaan trouwen, stam wijzigt en namenreeks = JA
          LT: VHNL03C60T10

!-- Laden PL Moeder en aanstaande
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL03C60T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL03C60T10-002.xls

!-- Geboorte met NAMENREEKS=J
When voer een bijhouding uit VHNL03C60T10a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

!-- Huwelijk met aanpassing geslachtsnaam
When voer een bijhouding uit VHNL03C60T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL03C60T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R


Scenario: 3. DB reset
          postconditie
Given de database is aangepast met: update kern.gem set dataanvgel = 20160101 where naam = 'Gemeente BRP 1'
And de database is aangepast met: update kern.partij set datingang = 20160101 where naam = 'Gemeente BRP 1'
And de database is aangepast met: update kern.his_partij set datingang = 20160101 where naam = 'Gemeente BRP 1'
Given maak bijhouding caches leeg







