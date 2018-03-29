Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R2247
@usecase                BY.1.AA
@sleutelwoorden         AUAH01C40T10, Geslaagd, Logging

Narrative: R2247 De toegang bijhoudingsautorisatie is geldig

Scenario: 1. DB init
          preconditie

Given alle personen zijn verwijderd
And de database is aangepast met: update autaut.his_toegangbijhautorisatie set datingang = to_number((to_char(now() + interval '1 day', 'YYYYMMDD')),'99999999') where toegangbijhautorisatie in (select tb.id from autaut.toegangbijhautorisatie tb join autaut.bijhautorisatie b on tb.bijhautorisatie = b.id where b.naam = 'Alles')

Given maak bijhouding caches leeg

Scenario: 2. AUAH01C40T10 Toegang bijhoudingsautorisatie is niet geldig(datum voor datingang)
          LT: AUAH01C40T10

Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C40T10-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C40T10-danny.xls

When voer een bijhouding uit AUAH01C40T10.xml namens partij 'Gemeente BRP 1'

Then komt de tekst 'Autorisatie faalt voor regel: R2106' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2115' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2246' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2247' voor in de logging
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
And is het antwoordbericht gelijk aan /testcases/bijhouding/AUAH/expected/AUAH01C40T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 556586505 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 628256905 niet als PARTNER betrokken bij een HUWELIJK

Scenario: 3. DB reset
          postconditie
Given de database is aangepast met: update autaut.his_toegangbijhautorisatie set datingang = to_number((to_char(now(), 'YYYYMMDD')), '99999999') where toegangbijhautorisatie in (select tb.id from autaut.toegangbijhautorisatie tb join autaut.bijhautorisatie b on tb.bijhautorisatie = b.id where b.naam ='Alles')


