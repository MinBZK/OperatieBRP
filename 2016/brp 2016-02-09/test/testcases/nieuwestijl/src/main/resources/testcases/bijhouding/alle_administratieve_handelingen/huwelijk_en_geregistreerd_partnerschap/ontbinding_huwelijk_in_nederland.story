Meta:
@status                 Klaar
@sleutelwoorden         ontbindingHuwelijkInNederland

Narrative:
Huwelijk en geregistreerd partnerschap, met administratieve handeling Ontbinding huwelijk in Nederland,
en actie registratie einde huwelijk geregistreerd partnerschap

Scenario: 2 personen (uit de database) zijn getrouwd, hun huwelijk wordt ontbonden

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de database is gereset voor de personen 302533928,303937828

Given administratieve handeling van type ontbindingHuwelijkInNederland , met de acties registratieEindeHuwelijkGeregistreerdPartnerschap
And testdata uit bestand ontbinding_huwelijk_in_nederland_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 302533928 niet als PARTNER betrokken bij een HUWELIJK

