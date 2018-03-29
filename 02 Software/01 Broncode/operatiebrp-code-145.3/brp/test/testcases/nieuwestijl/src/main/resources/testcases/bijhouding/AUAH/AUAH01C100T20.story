Meta:
@status                 Klaar
@regels                 R2299
@sleutelwoorden         AUAH01C100T20, Geslaagd, Logging
@usecase                BY.1.AA

Narrative: R2299 De bijhoudingsautorisatie is geldig

Scenario: 1. DB init
          preconditie


Given de database is aangepast met: update autaut.his_bijhautorisatie set datingang = to_number((to_char(now(), 'YYYYMMDD')), '99999999') where naam = 'Huwelijk'

Scenario: 2. AUAH01C100T20 De bijhoudingsautorisatie is wel geldig(datum op datingang)
          LT: AUAH01C100T20
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C100T10-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C100T10-danny.xls

When voer een bijhouding uit AUAH01C100T20.xml namens partij 'Gemeente BRP 1'

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
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AUAH/expected/AUAH01C100T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 791953993 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 244039033 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 8492545313 uit database en vergelijk met expected AUAH01C100T20-persoon1.xml
Then lees persoon met anummer 5913281825 uit database en vergelijk met expected AUAH01C100T20-persoon2.xml

Scenario: 3. DB reset
          postconditie
Given de database is aangepast met: update autaut.his_bijhautorisatie set datingang = 20010101 where naam = 'Huwelijk'

