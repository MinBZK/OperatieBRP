Meta:
@status                 Klaar
@regels                 R2271
@usecase                BY.1.AA
@sleutelwoorden         AUAH01C90T10, Geslaagd, Logging

Narrative: R2271 De partij\rol voor toegang bijhoudingsautorisatie is geldig

Scenario: 1. DB init
          preconditie

Given de database is aangepast met: update kern.his_partijrol set datingang = to_number((to_char(now() + interval '1 day', 'YYYYMMDD')), '99999999')
where partijrol = (select r.id from kern.partij p join kern.partijrol r on p.id = r.partij where p.naam ='Gemeente BRP 2')

Given maak bijhouding caches leeg

Scenario: 2. De partij rol voor toegang bijhoudingsautorisatie is niet geldig(datum voor datingang)
          LT: AUAH01C90T10
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C90T10-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C90T10-danny.xls

When voer een bijhouding uit AUAH01C90T10.xml namens partij 'Gemeente BRP 2'

Then komt de tekst 'Autorisatie faalt voor regel: R2106' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2115' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2246' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2247' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2248' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2268' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2269' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2270' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2271' voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2299' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2250' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2251' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2252' NIET voor in de logging

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/AUAH/expected/AUAH01C90T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 889175305 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 367219049 niet als PARTNER betrokken bij een HUWELIJK

Scenario: 3. DB reset
          postconditie
Given de database is aangepast met: update kern.his_partijrol set datingang = 19900101 where partijrol = (select r.id from kern.partij p join kern
.partijrol r on p.id = r.partij where p.naam ='Gemeente BRP 2')

