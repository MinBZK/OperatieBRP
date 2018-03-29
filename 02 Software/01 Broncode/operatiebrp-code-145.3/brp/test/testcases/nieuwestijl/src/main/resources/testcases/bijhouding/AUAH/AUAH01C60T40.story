Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R2268
@usecase                BY.1.AA
@sleutelwoorden         AUAH01C60T40, Geslaagd, Logging

Narrative: De geautoriseerde partij is een geldige partij(datum op dateinde)

Scenario: 1. DB init
          preconditie


Given voer enkele update uit: update kern.his_partij set dateinde = to_number((to_char((now() at time zone 'UTC') + interval '1 day', 'YYYYMMDD')),'99999999') where his_partij.id = (select his_partij.partij from kern.his_partij where naam = 'Gemeente BRP 1')

Given maak bijhouding caches leeg

Scenario: 2. De geautoriseerde partij is geen geldige partij(datum voor dateinde)
          LT: AUAH01C60T40
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C60T30-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C60T30-danny.xls

When voer een bijhouding uit AUAH01C60T40.xml namens partij 'Gemeente BRP 1'

Then komt de tekst 'Autorisatie faalt voor regel: R2106' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2115' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2246' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2247' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2248' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2268' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2269' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2270' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2271' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2299' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2250' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2251' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2252' NIET voor in de logging

Then heeft het antwoordbericht verwerking Geslaagd
And is het antwoordbericht gelijk aan /testcases/bijhouding/AUAH/expected/AUAH01C60T40.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 793373001 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 947549705 wel als PARTNER betrokken bij een HUWELIJK
Then lees persoon met anummer 5150503201 uit database en vergelijk met expected AUAH01C60T40-persoon1.xml
Then lees persoon met anummer 5101924641 uit database en vergelijk met expected AUAH01C60T40-persoon2.xml

Scenario: 3. DB reset
          postconditie
Given de database is aangepast met: update kern.his_partij set dateinde = null where his_partij.id = (select his_partij.partij from kern.his_partij where partij = 27012)

