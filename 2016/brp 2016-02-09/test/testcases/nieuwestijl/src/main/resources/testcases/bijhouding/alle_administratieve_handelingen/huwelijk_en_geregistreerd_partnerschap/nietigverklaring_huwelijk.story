Meta:
@status                 Klaar
@sleutelwoorden         nietigverklaringHuwelijk

Narrative:
Huwelijk en geregistreerd partnerschap, met administratieve handeling nietigverklaring huwelijk,
en actie registratie einde huwelijk geregistreerd partnerschap

Scenario: 2 personen (uit de database) zijn getrouwd, hun huwelijk wordt nietig verklaard

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de database is gereset voor de personen 302533928,303937828

Given administratieve handeling van type nietigverklaringHuwelijk , met de acties registratieEindeHuwelijkGeregistreerdPartnerschap
And testdata uit bestand nietigverklaring_huwelijk_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 302533928 niet als PARTNER betrokken bij een HUWELIJK

