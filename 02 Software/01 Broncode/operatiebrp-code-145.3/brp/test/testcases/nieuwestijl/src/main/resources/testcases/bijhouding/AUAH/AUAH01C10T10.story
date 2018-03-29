Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R2106
@usecase                BY.1.AA
@sleutelwoorden         AUAH01C10T10, Geslaagd, Logging

Narrative: R2106 De administratieve handeling is toegestaan voor de bijhoudingsautorisatie

Scenario: 1. DB Init
          preconditie

Given de database is aangepast met: update autaut.his_bijhautorisatiesrtadmhnd set tsverval = (now() at time zone 'UTC')

Scenario: 2. AUAH01C10T10 De administratieve handeling is niet toegestaan voor de bijhoudingsautorisatie
          LT: AUAH01C10T10
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Tim.xls

When voer een bijhouding uit AUAH01C10T10.xml namens partij 'Gemeente BRP 2'

Then komt de tekst 'Autorisatie faalt voor regel: R2106' voor in de logging
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

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/AUAH/expected/AUAH01C10T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 979562697 niet als PARTNER betrokken bij een HUWELIJK

Scenario: 3. DB reset
          postconditie
Given de database is aangepast met: update autaut.his_bijhautorisatiesrtadmhnd set tsverval = NULL
