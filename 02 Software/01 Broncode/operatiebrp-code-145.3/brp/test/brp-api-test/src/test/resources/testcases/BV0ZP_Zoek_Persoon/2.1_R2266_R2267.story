Meta:

@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2266, R2267

Narrative:
R2266
Indien een Bericht \ Zoekcriteria.Zoekcriterium als Zoekcriterium.Optie waarde "Leeg" heeft,
dan mag Zoekcriterium.Waarde niet gevuld zijn.

R2267

Indien een Bericht \ Zoekcriteria.Zoekcriterium als Zoekcriterium.Optie niet de waarde "Leeg" heeft,
dan dient Zoekcriterium.Waarde gevuld te zijn met een

Scenario: 1.    ZoekOptie Leeg, Waarde Leeg.
                LT: R2266_LT01, R2267_LT01, R2130_LT17
                Verwacht resultaat: Zoek Persoon geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Leeg,ElementNaam=Persoon.Adres.Huisnummer
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Geslachtsnaamstam,Waarde=Jan

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 2.    ZoekOptie Leeg, Waarde gevuld
                LT: R2266_LT02, R2267_LT02
                Verwacht resultaat:
                - Foutmelding: R2266: Waarde moet leeg zijn bij zoekcriterium Elementnaam

Given leveringsautorisatie uit autorisatie/Zoek_Persoon

Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Leeg,ElementNaam=Persoon.Adres.Huisnummer,Waarde=15
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Geslachtsnaamstam,Waarde=Jan

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2266     | Waarde moet leeg zijn bij zoekcriterium %Elementnaam%     |



Scenario: 3.    Zoekoptie Gevuld, Waarde Gevuld
                LT: R2266_LT03, R2267_LT03
                Verwacht resultaat: Zoek Persoon geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon

Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummer,Waarde=15
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Geslachtsnaamstam,Waarde=Jan

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 4.    Zoekoptie Gevuld, Waarde Leeg
                LT: R2266_LT04, R2267_LT04
                - Foutmelding: R2267: Waarde moet gevuld zijn bij zoekcriterium %Elementnaam%

Given leveringsautorisatie uit autorisatie/Zoek_Persoon

Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummer

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2267     | Waarde moet gevuld zijn bij zoekcriterium %Elementnaam%   |

