Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R2269
@usecase                BY.1.AA
@sleutelwoorden         AUAH01C70T30, Geslaagd, Logging

Narrative: R2269 De ondertekenaar is een geldige partij

Scenario: 1. DB init
          preconditie

Given voer enkele update uit: update autaut.toegangbijhautorisatie set ondertekenaar = '27015' where geautoriseerde = (select partijrol.id from kern.partijrol where partijrol.partij = (select partij.id from kern.partij where partij.naam = 'Gemeente voor autorisatietest'))
Given voer enkele update uit: update kern.partij set datingang = to_number((to_char(now()- interval '1 day', 'YYYYMMDD')),'99999999'), dateinde = to_number((to_char(now(), 'YYYYMMDD')),'99999999') where naam = 'Gemeente BRP beeindigd 1'
Given voer enkele update uit: update kern.his_partij set datingang = to_number((to_char(now()- interval '1 day', 'YYYYMMDD')),'99999999'), dateinde = to_number((to_char(now() , 'YYYYMMDD')),'99999999') where naam = 'Gemeente BRP beeindigd 1'

Scenario: 2. De ondertekenaar is geen geldige partij(datum op dateinde)
          LT: AUAH01C70T30
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C70T30-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C70T30-danny.xls

When voer een bijhouding uit AUAH01C70T30.xml met ondertekenaar 'Gemeente BRP beeindigd 1', en transporteur 'Gemeente voor autorisatietest'

Then komt de tekst 'Autorisatie faalt voor regel: R2106' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2115' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2246' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2247' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2248' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2268' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2269' voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2270' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2271' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2299' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2250' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2251' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2252' NIET voor in de logging

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/AUAH/expected/AUAH01C70T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 578163305 niet als PARTNER betrokken bij een HUWELIJK

Scenario: 3. DB reset
          postconditie

Given voer enkele update uit: update autaut.toegangbijhautorisatie set ondertekenaar = null where geautoriseerde = (select partijrol.id from kern.partijrol where partijrol.partij = (select partij.id from kern.partij where partij.naam = 'Gemeente voor autorisatietest'))
Given voer enkele update uit: update kern.partij set datingang = '20150101', dateinde = '20160101' where naam = 'Gemeente BRP beeindigd 1'
Given voer enkele update uit: update kern.his_partij set datingang = '20150101', dateinde = '20160101' where naam = 'Gemeente BRP beeindigd 1'
