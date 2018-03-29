Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R2115
@sleutelwoorden         AUAH01C20T10, Geslaagd, Logging
@usecase                BY.1.AA

Narrative: R2115 De bijhoudingsautorisatie is niet geblokkeerd

Scenario: 1. DB init
          preconditie

Given de database is aangepast met: update autaut.his_bijhautorisatie set indblok = true where naam = 'Alles'
And de database is aangepast met: update autaut.bijhautorisatie set indblok = true where naam = 'Alles'

Scenario: 2. De bijhoudingsautorisatie is wel geblokkeerd
          LT: AUAH01C20T10

Given alle personen zijn verwijderd
And enkel initiele vulling uit bestand /LO3PL/VHNL01C10T10-sandy.xls
And enkel initiele vulling uit bestand /LO3PL/VHNL01C10T10-danny.xls

When voer een bijhouding uit AUAH01C20T10.xml namens partij 'Gemeente BRP 1'

Then komt de tekst 'Autorisatie faalt voor regel: R2106' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2115' voor in de logging
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

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AUAH/expected/AUAH01C20T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Scenario: 3. DB reset
          postconditie
Given de database is aangepast met: update autaut.his_bijhautorisatie set indblok = null where naam = 'Alles'
And de database is aangepast met: update autaut.bijhautorisatie set indblok = null where naam = 'Alles'
