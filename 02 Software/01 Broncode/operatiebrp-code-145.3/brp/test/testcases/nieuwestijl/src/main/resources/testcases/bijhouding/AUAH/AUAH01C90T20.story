Meta:
@status                 Klaar
@regels                 R2271
@usecase                BY.1.AA
@sleutelwoorden         Logging

Narrative: R2271 De partij\rol voor toegang bijhoudingsautorisatie is geldig

Scenario: 1. DB init
          preconditie

Given voer enkele update uit: update kern.his_partijrol set datingang = to_number((to_char(now() at time zone 'UTC', 'YYYYMMDD')), '99999999') where partijrol = (select pr.id from kern.partijrol pr join kern.partij p on p.id = pr.partij and p.naam = 'Gemeente BRP 1' and pr.rol != 1)

Scenario: 2. De partij rol voor toegang bijhoudingsautorisatie is wel geldig(datum op datingang)
          LT: AUAH01C90T20
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C90T10-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C90T10-danny.xls

When voer een bijhouding uit AUAH01C90T20.xml namens partij 'Gemeente BRP 1'

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
And is het antwoordbericht gelijk aan /testcases/bijhouding/AUAH/expected/AUAH01C90T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 777692041 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 784851785 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 4978974241 uit database en vergelijk met expected AUAH01C90T20-persoon1.xml
Then lees persoon met anummer 6184149281 uit database en vergelijk met expected AUAH01C90T20-persoon2.xml

Scenario: 3. DB reset
          postconditie
Given de database is aangepast met: update kern.his_partijrol set datingang = 19900101 where partijrol = (select pr.id from kern.partijrol pr join kern.partij p on p.id = pr.partij and p.naam = 'Gemeente BRP 1' and pr.rol != 1)

