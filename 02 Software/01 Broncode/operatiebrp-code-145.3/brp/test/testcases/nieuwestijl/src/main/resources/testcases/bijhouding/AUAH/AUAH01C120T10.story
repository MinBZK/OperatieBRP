Meta:
@status                 Klaar
@regels                 R2251
@usecase                BY.1.AA
@sleutelwoorden         Logging

Narrative: R2251 Er moet een toegang bijhoudingsautorisatie voor de opgegeven partij rol en ondertekenaar bestaan

Scenario: R2251 Er is geen toegangbijhoudingsautorisatie voor partij en rol en ondertekenaar
          LT: AUAH01C120T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C90T10-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C90T10-danny.xls

When voer een bijhouding uit AUAH01C130T10.xml met ondertekenaar 'Gemeente BRP 3', en transporteur 'Gemeente BRP 2'

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
Then komt de tekst 'Autorisatie faalt voor regel: R2251' voor in de logging
Then komt de tekst 'Autorisatie faalt voor regel: R2252' NIET voor in de logging

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/AUAH/expected/AUAH01C120T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 777692041 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 784851785 niet als PARTNER betrokken bij een HUWELIJK

