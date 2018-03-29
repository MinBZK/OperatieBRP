Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R2299
@sleutelwoorden         AUAH01C100T10, Geslaagd, Logging
@usecase                BY.1.AA

Narrative: R2299 De bijhoudingsautorisatie is geldig

Scenario: 1. DB init
          preconditie

Given de database is aangepast met: update autaut.his_bijhautorisatie set datingang = to_number((to_char(now() + interval '1 day', 'YYYYMMDD')), '99999999') where naam = 'Alles'
And de database is aangepast met: update autaut.bijhautorisatie set datingang = to_number((to_char(now() + interval '1 day', 'YYYYMMDD')), '99999999') where naam = 'Alles'

Scenario: 2. De bijhoudingsautorisatie is niet geldig(datum voor datingang)
          LT: AUAH01C100T10
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C100T10-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C100T10-danny.xls

When voer een bijhouding uit AUAH01C100T10.xml namens partij 'Gemeente BRP 1'

Then komt de tekst 'Autorisatie faalt voor regel: R2106' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2115' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2246' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2247' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2248' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2268' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2269' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2270' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2271' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2299' voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2250' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2251' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2252' NIET voor in de logging

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AUAH/expected/AUAH01C100T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Scenario: 3. DB reset
          postconditie
Given de database is aangepast met: update autaut.his_bijhautorisatie set datingang = 20010101 where naam = 'Alles'
And de database is aangepast met: update autaut.bijhautorisatie set datingang = 20010101 where naam = 'Alles'

