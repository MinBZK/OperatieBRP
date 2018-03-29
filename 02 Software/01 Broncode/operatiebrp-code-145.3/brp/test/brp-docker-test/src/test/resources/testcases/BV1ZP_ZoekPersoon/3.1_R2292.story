Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon, archivering
@regels             R2292

Narrative:
Indien een zoekcriterium de optie 'Klein' bevat, dat dient er een match te zijn met de opgegeven waarde na de omzetting
van hoofdletters en diakritische tekens naar de bijbehorende kleine letters. Deze optie is alleen mogelijk bij tekstvelden:
De lengte en de geconverteerde inhoud dient gelijk te zijn (dus na omzetting van hoofdletters en diakritische tekens)

Scenario:   1.  Zoek persoon, Zoekcriterium klein, zoekactie goed gespeld.
                LT: R2292_LT01, R1269_LT09, R1270_L04
                Uitwerking: Jansen - Jansen
                Verwacht resultaat:
                R1269_LT09 - persoon in inkomend verzoek niet gearchiveerd in ber.pers tabel

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2292_1.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] een node aanwezig in het antwoord bericht
Then heeft het antwoordbericht 1 groepen 'persoon'

!-- R1269_LT09
Then bestaat er geen voorkomen in berpers tabel voor referentie 0000000A-3000-7000-0000-000000000000 en srt lvg_bvgZoekPersoon

!-- R1270_LT04
Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| bsn                   | 606417801                                   |
| richting                   | 2                                   |

Scenario:   2.  Zoek persoon, Zoekcriterium klein, zoekactie met misplaatste hoofdletter gespeld.
                LT: R2292_LT02
                Uitwerking: JanSen - Jansen

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2292_2.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] een node aanwezig in het antwoord bericht

Scenario:   3.  Zoek persoon, Zoekcriterium klein, zoekactie met misplaatste kleine letter gespeld.
                LT: R2292_LT03
                Uitwerking: jansen - Jansen

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2292_3.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] een node aanwezig in het antwoord bericht

Scenario:   4.  Zoek persoon, Zoekcriterium klein, zoekactie met misplaatste diakritsch teken gespeld.
                LT: R2292_LT04
                Uitwerking: Jänsen - Jansen

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2292_4.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] een node aanwezig in het antwoord bericht

Scenario:   5.  Zoek persoon, Zoekcriterium klein, zoekactie met misplaatste diakritsch teken gespeld.
                LT: R2292_LT05
                Uitwerking: Said - Saïd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Said.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2292_5.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:geslachtsnaamstam[text()='Saïd'] een node aanwezig in het antwoord bericht


Scenario:   6.  Zoek persoon, Zoekcriterium klein, zoekactie met teveel letters gespeld.
                LT: R2292_LT06
                Uitwerking: Jansens - Jansen

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2292_6.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] geen node aanwezig in het antwoord bericht

Scenario:   7.  Zoek persoon, Zoekcriterium klein, zoekactie met te weinig letters gespeld.
                LT: R2292_LT07
                Uitwerking: Janse - Jansen

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2292_7.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] geen node aanwezig in het antwoord bericht

Scenario:   8.  Zoek persoon, Zoekcriterium klein, zoekactie met spelfout gespeld.
                LT: R2292_LT08
                Uitwerking: Jaxsen - Jansen

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2292_8.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] geen node aanwezig in het antwoord bericht

Scenario:   9.  Zoek persoon, Zoekcriterium klein, zoekactie met spelfout door diakritsch teken gespeld.
                LT: R2292_LT09
                Uitwerking: Jönsen - Jansen

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2292_9.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] geen node aanwezig in het antwoord bericht