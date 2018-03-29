Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2294

Narrative:
Indien een zoekcriterium de optie 'Leeg' bevat, dan is er een match als er geen waarde aanwezig is in het betreffende Element.
Deze optie is alleen mogelijk bij specifieke tekstvelden.

Toelichting: deze optie zal alleen beschikbaar zijn voor attributen waar de afwezigheid van belang kan zijn
voor het terugdringen van het aantal resultaten, zoals bij Toevoeging huisnummer.

Scenario:   1.  Zoek persoon, Zoekcriterium Leeg, Jan heeft ook geen huisletter
                LT: R2294_LT01
                Uitwerking:
                - Jan huisletter is Leeg
                -Verwacht resultaat:
                - Alleen Jan wordt gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2294.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht

Scenario:   2.  Zoek persoon, Zoekcriterium Huisletter is Leeg
                LT: R2294_LT02
                Uitwerking:
                - Jantje huisletter is Gevuld
                -Verwacht resultaat:
                - Niemand gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jantje_Huisletter.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2294_2.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:voornamen[text()='Jantje'] geen node aanwezig in het antwoord bericht

Scenario:   3.  Zoek persoon, Zoekcriterium Persoon.Indicatie.DerdeHeeftGezag.Waarde = LEEG
                LT: R2294_LT03
                Uitwerking:
                - Bij Jan is gezag derde leeg
                -Verwacht resultaat:
                - Jan wordt gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2294_derde_heeft_gezag_Leeg.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht

Scenario:   4.  Zoek persoon, Zoekcriterium Persoon.Indicatie.DerdeHeeftGezag.Waarde = LEEG
                LT: R2294_LT04
                Uitwerking:
                - Bij persoon Karel is gezag derde gevuld
                -Verwacht resultaat:
                - Persoon NIET gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/IndicatieDerdeHeeftGezag.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2294_derde_heeft_gezag_Leeg_PL_Gevuld.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:voornamen[text()='Karel'] geen node aanwezig in het antwoord bericht

Scenario:   5.  Zoek persoon, Zoekcriterium GerelateerdeOuder.OuderlijkGezag.IndicatieOuderHeeftGezag = LEEG
                LT: R2294_LT05
                Uitwerking:
                - Bij Jan is ouderlijk gezag leeg
                -Verwacht resultaat:
                - Jan wordt gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2294_Ouderlijk_gezag_Leeg.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht

Scenario:   6.  Zoek persoon, Zoekcriterium Persoon.Indicatie.DerdeHeeftGezag.Waarde = LEEG
                LT: R2294_LT06
                Uitwerking:
                - Bij persoon Karel is gezag derde gevuld vanaf 2005
                - Peilmoment 2004
                -Verwacht resultaat:
                - Persoon Karel gevonden

Meta:
@status Bug
!-- Zoeken op leeg gaat opnieuw gespecifiseerd worden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/IndicatieDerdeHeeftGezagvanaf2005.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2294_derde_gezag_Leeg_PL_Gevuld_peilmoment.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:voornamen[text()='Karel'] een node aanwezig in het antwoord bericht

Scenario:   7.  Zoek persoon, Zoekcriterium Persoon.Indicatie.DerdeHeeftGezag.Waarde = LEEG
                LT: Extra scenario kijken of het technisch mogelijk is
                Uitwerking:
                - Bij Jan is gezag derde leeg en ouderlijk gezag leeg
                -Verwacht resultaat:
                - Jan wordt gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2294_derde_heeft_gezag_Leeg_en_ouder_gezag_leeg.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht

Scenario:   8.1 Zoek persoon, Zoekcriterium Persoon.Indicatie.DerdeHeeftGezag.Waarde = LEEG, BSN gerelateerde ouder gevuld, gerelateerde huwelijkspartner LEEG
                LT: Extra scenario kijken of het technisch mogelijk is
                Uitwerking:
                - Jan BSN gerelateerde ouders aanwezig op PL, huwelijkpartners bsn is leeg
                -Verwacht resultaat:
                - Alleen Jan wordt gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2294_derde_heeft_gezag_Leeg_BSN_gerelateerde_gevuld.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht

Scenario:   8.2 Zoek persoon, Zoekcriterium Persoon.Indicatie.DerdeHeeftGezag.Waarde = LEEG, BSN gerelateerde ouder LEEG, , gerelateerde huwelijkspartner LEEG
                LT: Extra scenario kijken of het technisch mogelijk is
                Uitwerking:
                - Jan BSN gerelateerde ouders aanwezig op PL
                -Verwacht resultaat:
                - Jan wordt NIET gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2294_derde_heeft_gezag_Leeg_BSN_gerelateerde_leeg.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:voornamen[text()='Jan'] geen node aanwezig in het antwoord bericht