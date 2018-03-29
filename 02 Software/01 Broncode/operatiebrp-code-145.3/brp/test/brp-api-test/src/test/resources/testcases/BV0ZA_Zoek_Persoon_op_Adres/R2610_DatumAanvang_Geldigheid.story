Meta:

@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon op adres
@regels             R2610


Narrative:

Voor alle zoekcriteria in een zoekvraag moet gelden dat:

Zoekcriterium.Element mag niet het attribuut 'Datum aanvang geldigheid' van een groep zijn.

Scenario:  1.   Zoeken op DatumAanvangGeldigheid van een groep (Hoofdpersoon Persoon.Adres.DatumAanvangGeldigheid)
                LT: R2610_LT02
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.NaamOpenbareRuimte,Waarde=Bertram van oost laan
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Woonplaatsnaam,Waarde=Uithoorn
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

