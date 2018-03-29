Meta:
@status             Klaar
@sleutelwoorden     Vrij bericht Inhoudelijke Checks

Narrative:
In deze story zijn testen te vinden m.b.t. de inhoud van het vrije bericht
In de scenario's wordt omschreven waarop getest wordt.
Aan de testen zijn geen bedrijfsregels gekoppeld en dus ook geen logische testgevallen

Scenario: 1.    Vrij bericht waarbij er geen inhoud in het bericht zit
                LT: -
                Verwacht resultaat:
                - Foutief


Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/3.1_Inhoud_is_Leeg.xml

Then is het antwoordbericht een soapfault


Scenario: 2.    Vrij bericht waarbij er alleen een spatie in het bericht zit
                LT: -
                Verwacht resultaat:
                - Geslaagd


Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/3.2_Inhoud_Alleen_Spatie.xml

Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 3.    Vrij bericht waarbij er alleen een getal in het bericht zit
                LT: -
                Verwacht resultaat:
                - Geslaagd


Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/3.3_Inhoud_Alleen_Getal.xml

Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 4.    Vrij bericht waarbij er alleen een leesteken in het bericht zit
                LT: -
                Verwacht resultaat:
                - Geslaagd


Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/3.4_Inhoud_Alleen_Leesteken.xml

Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 5.    Vrij bericht waarbij er teveel leestekens 100.000 (max 99999) in het bericht zit
                LT: -
                Verwacht resultaat:
                - Geslaagd


Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/3.5_Inhoud_teveel_leestekens.xml

Then is het antwoordbericht een soapfault


Scenario: 6.    Vrij bericht waarbij er het maximaal leestekens 99999 (max 99999) in het bericht zit
                LT: -
                Verwacht resultaat:
                - Geslaagd


Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/3.6_Inhoud_99999_leestekens.xml

Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 7.    Vrij bericht waarbij een buitenlands leesteken gevuld is
                LT: -
                Verwacht resultaat:
                - Geslaagd


Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/3.7_Inhoud_Alleen_Buitenlands_Leesteken.xml

Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 8.    Vrij bericht waarbij er XML tekens in het bericht zitten
                LT: -
                Verwacht resultaat:
                - Geslaagd

Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/3.8_Inhoud_Met_XML_Tekens.xml

Then is het antwoordbericht een soapfault

