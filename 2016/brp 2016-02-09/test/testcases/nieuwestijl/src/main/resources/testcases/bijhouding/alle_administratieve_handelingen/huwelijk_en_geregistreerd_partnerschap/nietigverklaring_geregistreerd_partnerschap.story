Meta:
@status                 Klaar
@sleutelwoorden         nietigverklaringGeregistreerdPartnerschap

Narrative:
Huwelijk en geregistreerd partnerschap, met administratieve handeling nietigverklaring geregistreerd partnerschap,
en actie registratie einde huwelijk geregistreerd partnerschap

Scenario: 2 personen (uit de database) hebben een geregistreerd partnerschap, hun partnerschap wordt nietig verklaard

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de database is gereset voor de personen 302533928,303937828

Given administratieve handeling van type nietigverklaringGeregistreerdPartnerschap , met de acties registratieEindeHuwelijkGeregistreerdPartnerschap
And testdata uit bestand nietigverklaring_geregistreerd_partnerschap_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 302533928 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

