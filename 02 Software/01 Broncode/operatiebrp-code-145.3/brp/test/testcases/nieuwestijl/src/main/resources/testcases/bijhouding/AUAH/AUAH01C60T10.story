Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R2268
@usecase                BY.1.AA
@sleutelwoorden         AUAH01C60T10, Geslaagd, Logging

Narrative: R2268 De geautoriseerde partij is een geldige partij

Scenario: 1. DB init
          preconditie

Given de database is aangepast met: update kern.partij set datingang = to_number((to_char(now() + interval '1 day', 'YYYYMMDD')), '99999999') where code = '507013'
And de database is aangepast met: update kern.his_partij set datingang = to_number((to_char(now() + interval '1 day', 'YYYYMMDD')), '99999999') where his_partij.id = (select his_partij.partij from kern.his_partij where partij = 27012)

Given maak bijhouding caches leeg

Scenario: 2. AUAH01C60T10 De geautoriseerde partij is geen geldige partij(datum voor datingang)
          LT: AUAH01C60T10
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C60T10-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C60T10-danny.xls

When voer een bijhouding uit AUAH01C60T10.xml namens partij 'Gemeente BRP 1'

Then komt de tekst 'Autorisatie faalt voor regel: R2106' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2115' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2246' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2247' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2248' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2268' voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2269' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2270' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2271' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2299' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2250' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2251' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2252' NIET voor in de logging

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/AUAH/expected/AUAH01C60T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 552375305 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 830704073 niet als PARTNER betrokken bij een HUWELIJK

Scenario: 3. DB reset
          postconditie
Given de database is aangepast met: update kern.partij set datingang = 20160101 where code = '507013'
And de database is aangepast met: update kern.his_partij set datingang = 20160101 where his_partij.id = (select his_partij.partij from kern.his_partij where partij = 27012)

