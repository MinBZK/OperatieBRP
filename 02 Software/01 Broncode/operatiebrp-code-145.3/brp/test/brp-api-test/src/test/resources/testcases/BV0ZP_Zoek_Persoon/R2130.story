Meta:

@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2130

Narrative:
Bij het in behandeling nemen van een leveringsverzoek geldt het volgende:

Als de Leveringsautorisatie zoals aangegeven door berichtparameter Bericht.Leveringsautorisatie en
De dienst die gevraagd wordt (R2085) beide bestaan, dan dient die Leveringsautorisatie die
De dienst die gevraagd wordt (R2085) te bevatten.

Scenario: 1.    Leveringautorisatie bevat dienst niet
                LT: R2130_LT18
                Verwacht resultaat:
                - Foutief
                - R2130: De leveringsautorisatie bevat de gevraagde dienst niet.

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Zoek_Persoon, autorisatie/Zoek_Persoon_Dienst_Ontbreekt

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon dienst ontbreekt'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Vanaf klein,ElementNaam=Persoon.Overlijden.BuitenlandsePlaats,Waarde=Woerd
|dienstId|2

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                        |
| R2343    | De opgeven leveringsautorisatie bevat niet de opgegeven dienst |

Then is er een autorisatiefout gelogd met regelcode R2130
