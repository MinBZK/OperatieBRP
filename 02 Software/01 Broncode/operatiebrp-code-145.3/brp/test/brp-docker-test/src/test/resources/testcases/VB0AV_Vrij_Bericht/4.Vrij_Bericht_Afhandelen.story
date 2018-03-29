Meta:
@status             Klaar
@sleutelwoorden     Vrij bericht


Narrative:
Als operatieBRP wil ik dat vrije berichten correct afgehandeld worden

Scenario: 1.    Vrijbericht foutief verzoek Partijcodes

Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/4.1_Foutiefverzoek_partijCodes.xml

Then is het antwoordbericht een soapfault


Scenario: 2.    Vrijbericht foutief verzoek Dubbele inhoud

Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/4.2_Foutiefverzoek_InhoudDubbel.xml

Then is het antwoordbericht een soapfault


Scenario: 3.    Vrijbericht foutief verzoek Meerdere Parameter voorkomens

Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/4.3_Foutiefverzoek_MeerdereParameters.xml

Then is het antwoordbericht een soapfault


Scenario: 4.    Vrijbericht verzoek met inhoud

Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/4.4_Geslaagdverzoek_metInhoud.xml

Then heeft het antwoordbericht verwerking Geslaagd
When alle berichten zijn geleverd
Then is er een vrijbericht ontvangen voor partij Gemeente VrijBerichtOntvanger


Scenario: 5.    Vrijbericht verzoek met inhoud verzonden door Gemeente VrijberichtZender aan BRP Voorziening

Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/4.5_Geslaagdverzoek_metInhoud.xml

Then heeft het antwoordbericht verwerking Geslaagd