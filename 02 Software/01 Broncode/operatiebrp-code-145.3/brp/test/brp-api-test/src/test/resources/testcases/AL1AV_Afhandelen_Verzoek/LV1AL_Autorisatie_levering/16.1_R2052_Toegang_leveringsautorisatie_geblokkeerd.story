Meta:
@status             Klaar
@usecase            LV.1.AL
@regels             R2052
@sleutelwoorden     Authorisatie levering

Narrative:

Bij het afhandelen van een leveringsverzoek dient in De toegang leveringsautorisatie van een verzoekbericht (R2050)
Toegang leveringsautorisatie.Geblokkeerd? ongelijk aan "Ja" te zijn.

Scenario:   1.      De opgegeven toegang leveringsautorisatie is geblokkeerd
                    LT: R2052_LT02
                    Verwacht resultaat:
                    - Foutmelding R2343: Er is een autorisatiefout opgetreden.
                    - logging R2052:


Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Toegang_leveringsautorisatie_geblokkeerd
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|622389609

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2052