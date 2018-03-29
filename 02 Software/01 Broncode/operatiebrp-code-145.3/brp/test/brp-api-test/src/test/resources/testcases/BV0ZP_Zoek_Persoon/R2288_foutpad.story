Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2288

Narrative:
Een zoekvraag voor 'Zoek persoon' dient tenminste één zoekcriterium te bevatten dat geen adresgegeven is
Persoon
Persoon (Jan Jansen):
BSN: 606417801
Geboorte datum: 1960-08-21
Adres:
Baron Schimmelpenninck van der Oyelaan 16
2252EB Voorschoten


Scenario: 1.    Zoek Persoon met 2 zoekcriteria = adres, foutmelding R2288
                LT: R2288_LT03
                - Jan wordt ingeladen met juiste postcode en woonplaats
                Uitwerking:
                - Persoon.Adres.Postcode = 2252EB
                - Persoon.Adres.Woonplaatsnaam' = Voorschoten
                Verwacht resultaat:
                - Foutmelding: R2288 De zoekvraag bevat onvoldoende persoonsgegevens, omdat alleen gezocht wordt op adres gegevens.


Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2252EB
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Woonplaatsnaam,Waarde=Voorschoten


Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'soortNaam' in 'melding' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2288    | De zoekvraag bevat alleen adresgegevens            |

