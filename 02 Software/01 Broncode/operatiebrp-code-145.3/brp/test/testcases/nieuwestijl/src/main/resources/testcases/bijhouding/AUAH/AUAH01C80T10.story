Meta:
@status                 Klaar
@regels                 R2270
@usecase                BY.1.AA
@sleutelwoorden         Logging

Narrative: R2270 De transporteur is een geldige partij

Scenario: 1. DB init
          preconditie

Given de database is aangepast met: update kern.his_partij set datingang = to_number((to_char(now() + interval '1 day', 'YYYYMMDD')), '99999999') where his_partij.id = (select h.id from kern.his_partij h join kern.partij p on p.id = h.partij where p.naam = 'Gemeente BRP handmatig fiat 1')
And de database is aangepast met: update autaut.toegangbijhautorisatie set transporteur = (select p.id from kern.partij p where p.naam = 'Gemeente BRP handmatig fiat 1') where geautoriseerde = (select r.id from kern.partijrol r join kern.partij p on p.id = r.partij where p.naam = 'Gemeente BRP 2')

Given maak bijhouding caches leeg

Scenario: 2. De transporteur is geen geldige partij(datum voor datingang)
          LT: AUAH01C80T10
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C80T10-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C80T10-danny.xls

When voer een bijhouding uit AUAH01C80T10.xml met ondertekenaar 'Gemeente BRP 2', en transporteur 'Gemeente BRP handmatig fiat 1'

Then komt de tekst 'Autorisatie faalt voor regel: R2106' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2115' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2246' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2247' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2248' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2268' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2269' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2270' voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2271' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2299' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2250' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2251' NIET voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2252' NIET voor in de logging

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/AUAH/expected/AUAH01C80T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 777692041 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 784851785 niet als PARTNER betrokken bij een HUWELIJK

Scenario: 3. DB reset
          postconditie
Given de database is aangepast met: update kern.his_partij set datingang = 20160101 where his_partij.id = (select h.partij from kern.his_partij h join kern.partij p on p.id = h.partij where p.naam = 'Gemeente BRP handmatig fiat 1')
And de database is aangepast met: update autaut.toegangbijhautorisatie set transporteur = (select p.id from kern.partij p where p.naam = 'Gemeente BRP 2') where geautoriseerde = (select r.id from kern.partijrol r join kern.partij p on p.id = r.partij where p.naam = 'Gemeente BRP 2')


