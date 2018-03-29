Meta:
@status             Klaar
@sleutelwoorden     Zoek Persoon
@regels             Historie

Narrative:
Als een afnemer
wil ik kunnen zoeken naar personen met historische gegevens
zodat ik een kan achter halen of ik over de juiste gegevens beschik.

Historisch zoeken dmv:
Optie Zoekbereik vullen met 'Peilmoment' (of leeg) & peilmomentMaterieel leeg of systeemdatum
resulteert in actuele gegevens('A Laag')

Optie Zoekbereik vullen met 'Peilmoment' (of leeg) & peilmomentMaterieel gevuld met een datum in het verleden
resulteert in geldigheid op het opgegeven datum

Optie zoekbereik vullen met 'Materiele periode' & peilmomentMaterieel leeg of gelijk aan systeemdatum
resulteert in geldigheid op een willekeurig moment

Optie Zoekberiek vullen met 'Materiele periode' & peilmomentMaterieel gevuld met datum in verleden
resulteert in geldigheid op of voor de opgegeven datum

Zoekbereik = 'Peilmoment' (of leeg) en 'PeilmomentMaterieel' is leeg of gelijk aan de systeemdatum: zoeken naar de actuele gegevens ('A laag').
Zoekbereik = 'Peilmoment' (of leeg) en 'PeilmomentMaterieel' ligt in het verleden: zoeken naar gegevens zoals ze geldig waren op de opgegeven datum.
Zoekbereik = 'Materiele periode' en 'PeilmomentMaterieel' is leeg of gelijk aan de systeemdatum: zoeken naar geldigheid op een willekeurig moment.
Zoekbereik = 'Materiele periode' en 'PeilmomentMaterieel' ligt in het verleden: zoeken naar gegevens zoals ze geldig waren 'op of voor' de opgegeven datum.


Scenario: 1. Historisch zoeken op relatie van type huwelijk met attributen van de partner
                Uitwerking:
                Persoon is op 2010-01-01 getrouwd met Jan Pietersen, het huwelijk is per 2015-01-01 beeindigd.
                Een zoek persoon met een peilmoment van voor die datum
                op het attribuut 'GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.Burgerservicenummer' zou dan geen resultaat mogen geven met de betreffende persoon.

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Zoek_Persoon/Anne_met_Historie2.xls

!-- Zoeken met Materiele periode tot datumaanvanggeldigheid huwelijk met attributen van de Partner, verwacht is dat er geen resultaten gevonden worden
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Historie_8.1.xml


Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:burgerservicenummer[text()='986096969'] geen node aanwezig in het antwoord bericht

!-- Zoeken met Materiele periode op datumaanvanggeldigheid huwelijk met attributen van de Partner, verwacht is dat er geen resultaten gevonden worden
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Historie_8.2.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:burgerservicenummer[text()='986096969'] geen node aanwezig in het antwoord bericht

!-- Zoeken met peilmoment voor datumaanvanggeldigheid Huwelijk, verwacht is dat er geen resultaten gevonden worden
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Historie_8.3.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:burgerservicenummer[text()='986096969'] geen node aanwezig in het antwoord bericht

!-- Zoeken met peilmoment op datumaanvanggeldigheid BSN partner (2015-01-01), verwacht is dat er resultaten gevonden worden
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Historie_8.4.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:burgerservicenummer[text()='986096969'] een node aanwezig in het antwoord bericht

!-- Zoeken met peilmoment na datum ontbinding Huwelijk, verwacht is dat er resultaten gevonden worden, omdat er geen actieVerval is bij de groep Partner
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Historie_8.5.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:burgerservicenummer[text()='986096969'] een node aanwezig in het antwoord bericht


Scenario: 2. Historisch zoeken op indicaties zoekPersoon
                Uitwerking:
                Persoon is per 2010-01-01 onder curatele gesteld. Een zoek persoon met een peilmoment van voor die datum
                op het attribuut 'Persoon.Indicatie.OnderCuratele' zou dan geen resultaat mogen geven met de betreffende persoon.

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Zoek_Persoon/Anne_met_Historie2.xls

!-- Zoeken met Materiele periode tot datumaanvanggeldigheid indicatie (OnderCuratele), verwacht is dat er geen resultaten gevonden worden
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Historie_9.2.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:burgerservicenummer[text()='986096969'] geen node aanwezig in het antwoord bericht

!-- Zoeken met Materiele periode tot datumaanvanggeldigheid indicatie (OnderCuratele), verwacht dat er wel een resultaat gevonden is
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Historie_9.3.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:burgerservicenummer[text()='986096969'] een node aanwezig in het antwoord bericht

!-- Zoeken met peilmoment voor datumaanvanggeldigheid indicatie (OnderCuratele), verwacht is dat er geen resultaten gevonden worden
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Historie_9.4.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:burgerservicenummer[text()='986096969'] geen node aanwezig in het antwoord bericht

!-- Zoeken met peilmoment op datumaanvanggeldigheid indicatie (OnderCuratele), verwacht dat er wel een resultaat gevonden is
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Historie_9.5.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:burgerservicenummer[text()='986096969'] een node aanwezig in het antwoord bericht



Scenario: 3.    Historisch zoeken op indicatie BehandeldAlsNederlander welke niet ja, persoon heeft een andere indicatie
                LT:
                Uitwerking:
                Persoon is per 2010-01-01 onder curatele gesteld. Een zoek persoon met een peilmoment van voor die datum
                op het attribuut 'BehandelAlsNederlander' zou dan geen resultaat mogen geven met de betreffende persoon omdat dit een andere indicatie is.

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Zoek_Persoon/Anne_met_Historie2.xls

!-- Zoeken met peilmoment voor datumaanvanggeldigheid indicatie (BehandelAlsNederlander), verwacht is dat er geen resultaten gevonden worden
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Historie_9.6.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:burgerservicenummer[text()='986096969'] geen node aanwezig in het antwoord bericht


Scenario: 4.    Historisch zoeken indicatie DerdeHeeftGezag welke bij persoon niet is geregistreerd
                LT:
                Uitwerking:
                Persoon is per 2010-01-01 onder curatele gesteld. Een zoek persoon met correct peilmoment maar een
                andere indicatie zou geen resultaat mogen geven.

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Zoek_Persoon/Anne_met_Historie2.xls

!-- Zoeken met peilmoment voor datumaanvanggeldigheid indicatie (DerdeHeeftGezag), verwacht is dat er geen resultaten gevonden worden
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Historie_11.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:burgerservicenummer[text()='986096969'] geen node aanwezig in het antwoord bericht


Scenario: 5.    Historisch zoeken op indicatie VolledigeVerstrekkingsBeperking welke bij persoon niet is geregistreerd
                LT:
                Uitwerking:
                Persoon is per 2010-01-01 onder curatele gesteld. Een zoek persoon met correct peilmoment maar een
                andere indicatie zou geen resultaat mogen geven.

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Zoek_Persoon/Anne_met_Historie2.xls

!-- Zoeken met peilmoment voor datumaanvanggeldigheid indicatie (VolledigeVerstrekkingsBeperking), verwacht is dat er geen resultaten gevonden worden
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Historie_12.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:burgerservicenummer[text()='986096969'] geen node aanwezig in het antwoord bericht

Scenario: 6.    Historisch zoeken op indicatie VastgesteldNietNederlander welke bij persoon niet is geregistreerd
                LT:
                Uitwerking:
                Persoon is per 2010-01-01 onder curatele gesteld. Een zoek persoon met correct peilmoment maar een
                andere indicatie zou geen resultaat mogen geven.

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Zoek_Persoon/Anne_met_Historie2.xls

!-- Zoeken met peilmoment voor datumaanvanggeldigheid indicatie (VastgesteldNietNederlander), verwacht is dat er geen resultaten gevonden worden
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Historie_13.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:burgerservicenummer[text()='986096969'] geen node aanwezig in het antwoord bericht


Scenario: 7.    Historisch zoeken op indicatie BehandelAlsNederlander welke bij persoon niet is geregistreerd
                LT:
                Uitwerking:
                Persoon is per 2010-01-01 onder curatele gesteld. Een zoek persoon met correct peilmoment maar een
                andere indicatie zou geen resultaat mogen geven.

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Zoek_Persoon/Anne_met_Historie2.xls

!-- Zoeken met peilmoment voor datumaanvanggeldigheid indicatie (BehandelAlsNederlander), verwacht is dat er geen resultaten gevonden worden
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Historie_14.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:burgerservicenummer[text()='986096969'] geen node aanwezig in het antwoord bericht


Scenario: 8.    Historisch zoeken op indicatie SignaleringMetBetrekkingTotVerstrekkenReisdocument welke bij persoon niet is geregistreerd
                LT:
                Uitwerking:
                Persoon is per 2010-01-01 onder curatele gesteld. Een zoek persoon met correct peilmoment maar een
                andere indicatie zou geen resultaat mogen geven.

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Zoek_Persoon/Anne_met_Historie2.xls

!-- Zoeken met peilmoment voor datumaanvanggeldigheid indicatie (SignaleringMetBetrekkingTotVerstrekkenReisdocument), verwacht is dat er geen resultaten gevonden worden
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Historie_15.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:burgerservicenummer[text()='986096969'] geen node aanwezig in het antwoord bericht


Scenario: 9.    Historisch zoeken op indicatie Staatloos welke bij persoon niet is geregistreerd
                LT:
                Uitwerking:
                Persoon is per 2010-01-01 onder curatele gesteld. Een zoek persoon met correct peilmoment maar een
                andere indicatie zou geen resultaat mogen geven.

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Zoek_Persoon/Anne_met_Historie2.xls

!-- Zoeken met peilmoment voor datumaanvanggeldigheid indicatie (Staatloos), verwacht is dat er geen resultaten gevonden worden
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Historie_16.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:burgerservicenummer[text()='986096969'] geen node aanwezig in het antwoord bericht


Scenario: 10.   Historisch zoeken op indicatie BijzondereVerblijfsrechtelijkePositie welke bij persoon niet is geregistreerd
                LT:
                Uitwerking:
                Persoon is per 2010-01-01 onder curatele gesteld. Een zoek persoon met correct peilmoment maar een
                andere indicatie zou geen resultaat mogen geven.

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Zoek_Persoon/Anne_met_Historie2.xls

!-- Zoeken met peilmoment voor datumaanvanggeldigheid indicatie (BijzondereVerblijfsrechtelijkePositie), verwacht is dat er geen resultaten gevonden worden
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Historie_17.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:burgerservicenummer[text()='986096969'] geen node aanwezig in het antwoord bericht


Scenario: 11.   Historisch zoeken op indicatie OnverwerktDocumentAanwezig welke bij persoon niet is geregistreerd
                LT:
                Uitwerking:
                Persoon is per 2010-01-01 onder curatele gesteld. Een zoek persoon met correct peilmoment maar een
                andere indicatie zou geen resultaat mogen geven.

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Zoek_Persoon/Anne_met_Historie2.xls

!-- Zoeken met peilmoment voor datumaanvanggeldigheid indicatie (OnverwerktDocumentAanwezig), verwacht is dat er geen resultaten gevonden worden
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Historie_18.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:burgerservicenummer[text()='986096969'] geen node aanwezig in het antwoord bericht


Scenario: 12.   Historisch zoeken op indicatie DerdeHeeftGezag welke bij persoon is geregistreerd
                LT:
                Uitwerking:
                Persoon is per 2010-01-01 onder curatele gesteld. Een zoek persoon met correct peilmoment maar een
                andere indicatie zou geen resultaat mogen geven.

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/IndicatieDerdeHeeftGezag.xls


!-- Zoeken met peilmoment voor datumaanvanggeldigheid indicatie (DerdeHeeftGezag), verwacht is dat er wel resultaten gevonden worden

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Historie_19.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:burgerservicenummer[text()='950878601'] een node aanwezig in het antwoord bericht


Scenario: 13.   Historisch zoeken op indicatie OnverwerktDocumentAanwezig welke bij persoon is geregistreerd
                LT:
                Uitwerking:
                Persoon heeft een indicatie onverwerktDocumentAanwezig is ja, persoon zou gevonden moeten worden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/IV_Onverwerkt_document_aanwezig.xls

!-- Zoeken met peilmoment voor datumaanvanggeldigheid indicatie (OnverwerktDocumentAanwezig), verwacht is dat er wel resultaten gevonden worden

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Historie_20.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:burgerservicenummer[text()='230614528'] een node aanwezig in het antwoord bericht
