Meta:
@status                 Klaar
@sleutelwoorden         aangaanGeregistreerdPartnerschapInBuitenland

Narrative:
Huwelijk en geregistreerd partnerschap, met administratieve handeling geregistreerd partnerschap in buitenland,
en actie registratie aanvang huwelijk geregistreerd partnerschap

Scenario: Personen Sandy Olsson en Danny Zuko gaan een geregistreerd partnerschap aan in het buitenland

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,141901317,631512457,108591232,754407433 zijn verwijderd
Given de standaardpersoon Sandy met bsn 108591232 en anr 2182317842 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 754407433 en anr 2523451410 zonder extra gebeurtenissen

Given administratieve handeling van type aangaanGeregistreerdPartnerschapInBuitenland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand aangaan_geregistreerd_partnerschap_in_buitenland_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 108591232 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 754407433 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

