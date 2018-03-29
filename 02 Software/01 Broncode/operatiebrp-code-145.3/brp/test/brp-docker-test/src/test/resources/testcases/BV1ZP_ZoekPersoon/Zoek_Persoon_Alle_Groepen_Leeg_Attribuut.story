Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon

Narrative:
Bij een test voeren we een persoon op met een leeg vorig en volgend A-nummer. Vervolgens zoeken we met een autorisatie die dat mag een persoon op met het A-nummer gelijk aan dat van de opgevoerde persoon en een leeg vorig en volgend A-nummer. De query levert echter niet de persoonslijst op die verwacht wordt. Het lijkt erop dat de indicatie IndAGNrverwijzing = true de boosdoener is; deze staat voor de betreffende persoon namelijk op 'false'.
Voor meer details omtrent testdata en de test zie BLAUW-6400.

Later toegevoegd, n.a.v. bovenstaande Bug, Zoeken op alle groepen

!-- De onderstaande scenario's testen op verschillende groepen van de PL hoofdpersoon voor optie LEEG
!-- Zoeken op groep 1 Persoon
Scenario: 1.    Zoeken op ANR, en op vorig en volgend A nummer is Leeg
                Verwacht resultaat;
                - Jan gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Vorig_volgend_Anummer_Leeg.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then heeft in het antwoordbericht 'burgerservicenummer' in 'identificatienummers' de waarde '606417801'

!-- Zoeken op groep 2 en 3 Ouder
Scenario: 2.    Zoeken op GerelateerdeOuder.Persoon.Identificatienummers.Burgerservicenummer = LEEG
                Verwacht resultaat;
                - Jan gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2294_derde_heeft_gezag_Leeg_BSN_gerelateerde_gevuld.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht

!-- Zoeken op groep 4 Nationaliteit
Scenario: 3.    Zoeken op nationaliteit LEEG,  Persoon_Nationaliteit_Leeg.xml
                Verwacht resultaat;
                - Jan NIET gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Persoon_Nationaliteit_Leeg.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht

!-- Zoeken op groep 5 Huwelijk/geregistreerd partnerschap

Scenario:   4.  Zoek persoon, gerelateerde huwelijkspartner LEEG (GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.Burgerservicenummer)
                Uitwerking:
                - Jan is niet getrouwd
                -Verwacht resultaat:
                - Jan wordt NIET gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Betrokken_Persoon_Huwelijk_Leeg.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht

!-- Zoeken op groep 6 Nederlandse akte en andere brondocumenten
!-- ??
!-- Geen attributen gevonden waar op gezocht mag worden, uitgaande van de bedrijfsregels

!-- Zoeken op groep 7 Inschrijving
Scenario:   5.  Zoek persoon, Zoekcriterium Leeg, Persoon.Inschrijving.Datum
                Uitwerking:
                - Jan huisletter is Leeg
                -Verwacht resultaat:
                - Alleen Jan wordt gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Persoon_Inschrijving_Leeg.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:voornamen[text()='Jan'] geen node aanwezig in het antwoord bericht

!-- Zoeken op groep 8 Adres
Scenario:   6.  Zoek persoon, Zoekcriterium Leeg, Jan heeft ook geen huisletter
                -Verwacht resultaat:
                - Jan wordt gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2294.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht

!-- Zoeken op groep 9 Kind
Scenario: 7.    Zoeken op ANR, en op betrokken persoon Kind is LEEG
                Verwacht resultaat;
                - Jan gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Betrokken_Persoon_Kind_Leeg.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then heeft in het antwoordbericht 'burgerservicenummer' in 'identificatienummers' de waarde '606417801'

!-- Zoeken op groep 11, indicaties
Scenario: 8.     Historisch zoeken op indicaties zoekPersoon
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

!-- Zoeken op groep 12, Reisdocument
Scenario:   9.  Zoek persoon, Persoon.Reisdocument.DatumIngangDocument LEEG
                -Verwacht resultaat:
                - Jan gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Persoon_Document_Leeg.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht

!-- Zoeken op groep 13, Kiesrecht
Scenario:   10.  Zoek persoon, Persoon.UitsluitingKiesrecht.Indicatie LEEG
                -Verwacht resultaat:
                - Jan gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Persoon_Kiesrecht_Leeg.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht


!-- Zoeken op verstrekkingsbeperking
Scenario:   11.  Zoek persoon, Persoon.Verstrekkingsbeperking.OmschrijvingDerde LEEG
                -Verwacht resultaat:
                - Jan gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Persoon_Verstrekkingsbeperking_Leeg.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht