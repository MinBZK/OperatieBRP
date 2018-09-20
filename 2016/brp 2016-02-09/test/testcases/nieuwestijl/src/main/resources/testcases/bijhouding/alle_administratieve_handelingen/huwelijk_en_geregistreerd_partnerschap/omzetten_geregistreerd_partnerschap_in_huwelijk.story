Meta:
@status                 Klaar
@sleutelwoorden         omzettingGeregistreerdPartnerschapInHuwelijk

Narrative:
Huwelijk en geregistreerd partnerschap, met administratieve handeling omzetten geregistreerd partnerschap in huwelijk,
en actie registratie einde huwelijk geregistreerd partnerschap

Scenario: 2 Personen (uit de database) gaan hun geregistreerd partnerschap omzetten in een huwelijk

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de database is gereset voor de personen 301953818,200442193

Given administratieve handeling van type omzettingGeregistreerdPartnerschapInHuwelijk , met de acties registratieEindeHuwelijkGeregistreerdPartnerschap
And testdata uit bestand omzetten_geregistreerd_partnerschap_in_huwelijk_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 301953818 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 200442193 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 301953818 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 200442193 wel als PARTNER betrokken bij een HUWELIJK
