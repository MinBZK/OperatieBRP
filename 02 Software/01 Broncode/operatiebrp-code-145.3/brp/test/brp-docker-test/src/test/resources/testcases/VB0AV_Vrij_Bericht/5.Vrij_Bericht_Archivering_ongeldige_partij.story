Meta:
@status             Klaar
@sleutelwoorden     Vrij bericht
@regels             R2499

Narrative:
Zendende partij is leeg als Bericht.Zendende partij niet verwijst naar een Geldig voorkomen stamgegeven op peildatum
(R1284) op 'Systeemdatum' (R2016) in Partij.

Scenario: 1.    Archivering goedpad stuur vrij bericht
                LT: R2499_LT04
                -Verwacht resultaat:
                - Foutief
                - Archivering volgens R2499

Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZenderOngeldig' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/5.2_Partij_ongeldig.xml

Then heeft het antwoordbericht verwerking Foutief

Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| srt                   | 159                                  |
| richting              | 1                                    |
| zendendepartij        | null                                 |
| zendendesysteem       | BRP                                  |
| ontvangendepartij     | null                                 |
| crossreferentienr     | null                                 |
