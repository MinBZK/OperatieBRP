Meta:

@status             Klaar
@usecase            LV.1.AL
@regels             R1261, R1262, R1263, R1264, R2053, R2054, R2055, R2056, R2130, R2016, R2085, R2129
@sleutelwoorden     Authorisatie levering

Narrative:
Bij het in behandeling nemen van een bevraging (bericht bevat parameter dienstIdentificatie) geldt het volgende:

Als de Leveringsautorisatie zoals aangegeven door berichtparameter leveringsautorisatieIdentificatie en de Dienst
zoals aangegeven door berichtparameter dienstIdentificatie beide bestaan, dan dient die Leveringsautorisatie die Dienst te bevatten.

Scenario:   1.  Leveringsautorisatie bevat dienst niet.
                LT: R2130_LT16
                Verwacht resultaat:
                - 1. Foutief bericht met de melding "De opgeven leveringsautorisatie bevat niet de opgegeven dienst"
                Uitwerking:
                - twee leveringsautorisatie ingeladen. 1 met 4 diensten, waarvan de 4e geef details persoon is
                - de andere leveringsautorisatie heeft dezelfde eerste 3 diensten, maar niet de vierde: geef details persoon
                - Zoeken op dienst id 4 (geef details perooon)

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding,autorisatie/doelbinding_zonder_geef_details_persoon

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'doelbinding zonder geef details persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|622389609
|dienstId|4

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                        |
| R2343    | De opgeven leveringsautorisatie bevat niet de opgegeven dienst |

Then is er een autorisatiefout gelogd met regelcode R2130















