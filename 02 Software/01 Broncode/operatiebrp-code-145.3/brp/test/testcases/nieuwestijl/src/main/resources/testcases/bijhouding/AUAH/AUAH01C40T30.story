Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R2247
@usecase                BY.1.AA
@sleutelwoorden         AUAH01C40T30, Geslaagd, Logging

Narrative: R2247 De toegang bijhoudingsautorisatie is geldig

Scenario: 1. DB init
          preconditie


Given de database is aangepast met: update autaut.his_toegangbijhautorisatie set dateinde = to_number((to_char(now(), 'YYYYMMDD')), '99999999') where toegangbijhautorisatie = (select t.id from autaut.toegangbijhautorisatie t join kern.partijrol r on r.id = t.geautoriseerde join kern.partij p on p.id = r.partij where p.naam = 'Gemeente BRP 3')
And de database is aangepast met: update autaut.toegangbijhautorisatie set dateinde = to_number((to_char(now(), 'YYYYMMDD')), '99999999') where geautoriseerde = (select r.id from kern.partijrol r join kern.partij p on p.id = r.partij where p.naam = 'Gemeente BRP 3')


Scenario: 2. AUAH01C40T30 Toegang bijhoudingsautorisatie is niet geldig(datum op dateinde)
          LT: AUAH01C40T30
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C40T30-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C40T30-danny.xls

When voer een bijhouding uit AUAH01C40T30.xml namens partij 'Gemeente BRP 3'

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
And is het antwoordbericht gelijk aan /testcases/bijhouding/AUAH/expected/AUAH01C40T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 337352057 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 449253065 niet als PARTNER betrokken bij een HUWELIJK

Scenario: 3. DB reset
          postconditie
Given de database is aangepast met: update autaut.his_toegangbijhautorisatie set dateinde = to_number((to_char(now() + interval '4 day', 'YYYYMMDD')), '99999999') where toegangbijhautorisatie = (select t.id from autaut.toegangbijhautorisatie t join kern.partijrol r on r.id = t.geautoriseerde join kern.partij p on p.id = r.partij where p.naam = 'Gemeente BRP 3')
And de database is aangepast met: update autaut.toegangbijhautorisatie set dateinde = to_number((to_char(now() + interval '4 day', 'YYYYMMDD')), '99999999') where geautoriseerde = (select r.id from kern.partijrol r join kern.partij p on p.id = r.partij where p.naam = 'Gemeente BRP 3')

