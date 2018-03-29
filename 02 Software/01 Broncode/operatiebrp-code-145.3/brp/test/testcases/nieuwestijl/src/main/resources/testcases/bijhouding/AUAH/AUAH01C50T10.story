Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R2248
@sleutelwoorden         AUAH01C50T10, Geslaagd, Logging
@usecase                BY.1.AA

Narrative: R2248 De toegang bijhoudingsautorisatie is niet geblokkeerd

Scenario: 1. DB init
          preconditie

Given de database is aangepast met: update autaut.his_toegangbijhautorisatie set indblok = true where toegangbijhautorisatie in (select tb.id from autaut.toegangbijhautorisatie tb join autaut.bijhautorisatie b on tb.bijhautorisatie = b.id where b.naam = 'Alles')

Scenario: 2. AUAH01C50T10 De toegang bijhoudingsautorisatie is wel geblokkeerd
          LT: AUAH01C50T10
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C10T10-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C10T10-danny.xls

When voer een bijhouding uit AUAH01C50T10.xml namens partij 'Gemeente BRP 1'

Then komt de tekst 'Autorisatie faalt voor regel: R2106' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2115' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2246' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2247' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2248' voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2268' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2269' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2270' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2271' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2299' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2250' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2251' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2252' NIET voor in de logging

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AUAH/expected/AUAH01C50T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Scenario: 3. DB reset
          postconditie
Given de database is aangepast met: update autaut.his_toegangbijhautorisatie set indblok = null where toegangbijhautorisatie in (select tb.id from autaut.toegangbijhautorisatie tb join autaut.bijhautorisatie b on tb.bijhautorisatie = b.id where b.naam = 'Alles')

