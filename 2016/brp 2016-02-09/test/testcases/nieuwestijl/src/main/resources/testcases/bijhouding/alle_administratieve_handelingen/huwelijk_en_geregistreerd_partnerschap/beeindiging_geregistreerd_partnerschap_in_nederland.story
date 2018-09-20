Meta:
@status                 Klaar
@sleutelwoorden         beeindigingGeregistreerdPartnerschapInNederland

Narrative:
Huwelijk en geregistreerd partnerschap, met administratieve handeling beeindiging geregistreerd partnerschap in Nederland,
en actie registratie einde huwelijk geregistreerd partnerschap

Scenario: 2 personen (uit de database) hebben een geregistreerd partnerschap, hun relatie wordt beeindigd

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de database is gereset voor de personen 301953818,200442193

Given administratieve handeling van type beeindigingGeregistreerdPartnerschapInNederland , met de acties registratieEindeHuwelijkGeregistreerdPartnerschap
And testdata uit bestand beeindiging_geregistreerd_partnerschap_in_nederland_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 301953818 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

