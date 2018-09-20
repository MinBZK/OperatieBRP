Meta:
@status                 Klaar
@sleutelwoorden         voltrekkingHuwelijkInBuitenland

Narrative:
Huwelijk en geregistreerd partnerschap, met administratieve handeling Voltrekking huwelijk in buitenland,
en actie registratie aanvang huwelijk geregistreerd partnerschap

Scenario: Personen Sandy Olsson en Danny Zuko gaan trouwen in het buitenland

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,141901317,631512457,959682697,804437385 zijn verwijderd
Given de standaardpersoon Sandy met bsn 959682697 en anr 5875052818 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 804437385 en anr 2487024146 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInBuitenland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand voltrekking_huwelijk_in_buitenland_01.yml

When het bericht wordt verstuurd

Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 959682697 wel als PARTNER betrokken bij een HUWELIJK
