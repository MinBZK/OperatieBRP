Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2290
@versie             1

Narrative:
Voor alle zoekcriteria in een zoekvraag moet gelden dat:

Zoekcriterium.Element moet als geautoriseerd Element voorkomen in Dienstbundel \ Groep \ Attribuut
bij de betreffende Dienstbundel.

Scenario:   1.  Dienstbundel heeft GEEN autorisatie op huisnummer. Zoekcriterium is huisnummer.
                LT: R2290_LT02
                Verwacht resultaat:
                - Foutmelding: Er bestaat geen autorisatie voor het zoekcriterium

Given leveringsautorisatie uit autorisatie/R2264/Zoek_Persoon_huisnummer_niet_geautoriseerd

Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Huisnummer niet geautoriseerd'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummer,Waarde=33

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2290     | Er bestaat geen autorisatie voor het zoekcriterium        |

Scenario:   2.  Dienstbundel heeft GEEN autorisatie op huisnummer. Zoekcriterium is huisnummer. Zoekvriterium 2 is postcode
                LT: R2290_LT03
                Verwacht resultaat:
                - Foutmelding: Er bestaat geen autorisatie voor het zoekcriterium

Given leveringsautorisatie uit autorisatie/R2264/Zoek_Persoon_huisnummer_niet_geautoriseerd

Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Huisnummer niet geautoriseerd'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummer,Waarde=33
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2000AA

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2290     | Er bestaat geen autorisatie voor het zoekcriterium        |