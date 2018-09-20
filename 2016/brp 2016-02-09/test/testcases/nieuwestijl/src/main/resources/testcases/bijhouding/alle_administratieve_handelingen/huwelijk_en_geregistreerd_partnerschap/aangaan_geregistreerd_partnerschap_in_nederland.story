Meta:
@status                 Klaar
@sleutelwoorden         aangaanGeregistreerdPartnerschapInNederland,dianap

Narrative:
Huwelijk en geregistreerd partnerschap, met administratieve handeling geregistreerd partnerschap in Nederland,
en actie registratie aanvang huwelijk geregistreerd partnerschap

Scenario: Personen Sandy Olsson en Danny Zuko gaan een geregistreerd partnerschap aan in Nederland, daarna
          wordt hun geregistreerd partnerschap beeindigd

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,141901317,631512457,342755286,641595451 zijn verwijderd
Given de standaardpersoon Sandy met bsn 342755286 en anr 1010160607 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 641595451 en anr 1010262852 zonder extra gebeurtenissen

Given administratieve handeling van type aangaanGeregistreerdPartnerschapInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand aangaan_geregistreerd_partnerschap_in_nederland_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 342755286 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
