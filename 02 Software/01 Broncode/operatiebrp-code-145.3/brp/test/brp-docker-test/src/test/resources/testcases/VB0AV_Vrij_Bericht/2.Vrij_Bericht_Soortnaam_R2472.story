Meta:
@status             Klaar
@sleutelwoorden     Vrij bericht
@regels             R2472

Narrative:
R2472:
Bij het in behandeling nemen van het vrije bericht geldt dat Bericht.Soort vrij bericht
moet verwijzen naar een stamgegeven in Soort vrij bericht.

R2473:
Bij het in behandeling nemen van het vrije bericht geldt dat Bericht.Soort vrij bericht
moet verwijzen naar een Geldig voorkomen stamgegeven op peildatum (R1284) op 'Systeemdatum' (R2016) in Soort vrij bericht.

Scenario: 1.    Soort vrij bericht bestaat en is geldig
                LT: R2472_LT01, R2473_LT01
                -Verwacht resultaat:
                - Geslaagd


Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/2.1_Geslaagdverzoek.xml

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 2.    Soort vrij bericht bestaat en is geldig
                LT: R2472_LT01, R2473_LT01
                -Verwacht resultaat:
                - Geslaagd

Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/2.2_Beheerverzoek.xml

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 3.    Soort vrij bericht bestaat en is geldig
                LT: R2472_LT01, R2473_LT01
                -Verwacht resultaat:
                - Geslaagd

Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/2.3_Correctie.xml

Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 4.    Soort vrij bericht bestaat en is geldig
                LT: R2472_LT01, R2473_LT01
                -Verwacht resultaat:
                - Geslaagd


Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/2.4_Protocolerring.xml

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 5.    Soort vrij bericht bestaat en is geldig
                LT: R2472_LT01, R2473_LT01
                -Verwacht resultaat:
                - Geslaagd


Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/2.5_Onderzoekdossier.xml

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 6.    Soort vrij bericht bestaat en is geldig
                LT: R2472_LT01, R2473_LT01
                -Verwacht resultaat:
                - Geslaagd


Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/2.6_Brondocument.xml

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 7.    Soort vrij bericht bestaat en is geldig
                LT: R2472_LT01, R2473_LT01
                -Verwacht resultaat:
                - Geslaagd


Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/2.7_RPS.xml

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 8.    Soort vrij bericht bestaat en is geldig
                LT: R2472_LT01, R2473_LT01
                -Verwacht resultaat:
                - Geslaagd

Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/2.8_RNI.xml

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 9.    Soort vrij bericht bestaat en is geldig
                LT: R2472_LT01, R2473_LT01
                -Verwacht resultaat:
                - Geslaagd

Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/2.9_PIVA.xml

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 10.    Soort vrij bericht bestaat en is geldig
                LT: R2472_LT01, R2473_LT01
                -Verwacht resultaat:
                - Geslaagd


Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/2.10_GBApartij.xml

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 11.    Soort vrij bericht bestaat en is geldig
                LT: R2472_LT01, R2473_LT01
                -Verwacht resultaat:
                - Geslaagd


Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/2.11_Overig.xml

Then heeft het antwoordbericht verwerking Geslaagd