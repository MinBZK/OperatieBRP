Meta:
@status             Klaar
@usecase            BV.0.GD
@sleutelwoorden     Geef Details Persoon
@regels             R2217


Narrative:
Indien in het verzoekbericht een scope van te leveren attributen is opgegeven
Dan mag het resultaatbericht alleen attributen bevatten waarvoor geldt dat:
Het een verplicht te leveren attribuut betreft (Element.Autorisatie = "Verplicht")
OF
Het een attribuut betreft dat aanwezig is in de opgegeven scope (berichtparameter 'Gevraagde attributen')

Scenario: 1.1   Geef detail persoon met scoping op verplichte attributen welke niet gevuld zijn (datum aanvang onderzoek)
                LT: R2217_LT01, R2217_LT02
                Uitwerking:
                - Persoon.Bijhouding.BijhoudingsaardCode = Verplicht + gescoped
                - Persoon.Bijhouding.NadereBijhoudingsaardCode = Verplicht + niet gescoped
                - Onderzoek.DatumAanvang = Verplicht + niet gescoped EN LEEG
                Verwacht resultaat: Verplichte attributen en element(en) in scope geleverd, lege verplichte attributen niet aanwezig in bericht (datumaanvang onderzoek)
                - Persoon.Bijhouding.BijhoudingsaardCode in bericht
                - Persoon.Bijhouding.NadereBijhoudingsaardCode in bericht
                - Onderzoek.DatumAanvang NIET in bericht

Given leveringsautorisatie uit autorisatie/geef_details_persoon_verplichte_attributen
Given persoonsbeelden uit specials:specials/Anne_adres_jaar_na_geboorte_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Abo GeefDetailsPersoon enkel verplichte attributen'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|590984809
|scopingElementen|Persoon.Bijhouding.BijhoudingsaardCode

Then heeft het antwoordbericht verwerking Geslaagd

!-- R2217_LT01: Controle op verplicht element dat ook in de scope zit
Then is in antwoordbericht de aanwezigheid van 'bijhoudingsaardCode' in 'bijhouding' nummer 1 ja

!-- R2217_LT02: Controle op verplicht element dat niet in de scope zit
Then is in antwoordbericht de aanwezigheid van 'nadereBijhoudingsaardCode' in 'bijhouding' nummer 1 ja

!-- R2217_LT02 NB: controle op verplicht element dat niet in de scope zit en leeg is
Then is in antwoordbericht de aanwezigheid van 'datumAanvang' in 'onderzoek' nummer 1 nee

Scenario: 1.2   Geef detail persoon met scoping op verplichte attributen (allemaal gevuld)
                LT: R2217_LT01, R2217_LT02
                Uitwerking:
                - Persoon.Bijhouding.BijhoudingsaardCode = Verplicht + gescoped
                - Persoon.Bijhouding.NadereBijhoudingsaardCode = Verplicht + niet gescoped
                - Onderzoek.DatumAanvang = Verplicht + niet gescoped EN Verwijst naar attribuut in persoonsdeel dat NIET gescoped is
                Verwacht resultaat: Verplichte attributen niet in scope, maar wel geleverd
                - Persoon.Bijhouding.BijhoudingsaardCode in bericht
                - Persoon.Bijhouding.NadereBijhoudingsaardCode in bericht
                - Onderzoek.DatumAanvang NIET in bericht

!-- Toevoegen van een onderzoek op de persoon.adres.huisnummer, omdat de attributen van onderzoek in de scoping zijn opgenomen.
Given persoonsbeelden uit specials:specials/Anne_adres_jaar_na_geboorte_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Abo GeefDetailsPersoon enkel verplichte attributen'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|590984809
|scopingElementen|Persoon.Bijhouding.BijhoudingsaardCode

Then heeft het antwoordbericht verwerking Geslaagd

!-- R2217_LT01: Controle op verplicht element dat ook in de scope zit
Then is in antwoordbericht de aanwezigheid van 'bijhoudingsaardCode' in 'bijhouding' nummer 1 ja

!-- R2217_LT02: Controle op verplicht element dat niet in de scope zit
Then is in antwoordbericht de aanwezigheid van 'nadereBijhoudingsaardCode' in 'bijhouding' nummer 1 ja

!-- R2217_LT02 NB: controle op verplicht element dat niet in de scope zit en niet verwijst naar een een attribuut,
!-- dat in het persoonsdeel gescoped is.
!-- Onderzoek niet aanwezig in bericht omdat het voorkomen waarnaar het onderzoek verwijst niet voorkomt in het bericht
Then is in antwoordbericht de aanwezigheid van 'datumAanvang' in 'onderzoek' nummer 1 nee


Scenario: 1.3   Geef detail persoon met scoping op verplichte attributen (allemaal gevuld)
                LT: R2217_LT01, R2217_LT02
                Uitwerking:
                - Persoon.Bijhouding.BijhoudingsaardCode = Verplicht + gescoped
                - Persoon.Bijhouding.NadereBijhoudingsaardCode = Verplicht + niet gescoped
                - Onderzoek.DatumAanvang = Verplicht + niet gescoped EN Verwijst naar attribuut in persoonsdeel dat WEL gescoped is
                - Onderzoek.DatumEind = Verplicht + niet gescoped En LEEG
                Verwacht resultaat: Verplichte attributen niet in scope, maar wel geleverd
                - Persoon.Bijhouding.BijhoudingsaardCode in bericht
                - Persoon.Bijhouding.NadereBijhoudingsaardCode in bericht
                - Onderzoek.DatumAanvang WEL in bericht
                - Onderzoek.DatumEind NIET in bericht

Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC40T90_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Abo GeefDetailsPersoon enkel verplichte attributen'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|186954761
|scopingElementen|Persoon.Bijhouding.BijhoudingsaardCode,Persoon.Adres.Huisnummer

Then heeft het antwoordbericht verwerking Geslaagd

!-- R2217_LT01: Controle op verplicht element dat ook in de scope zit
Then is in antwoordbericht de aanwezigheid van 'bijhoudingsaardCode' in 'bijhouding' nummer 1 ja

!-- R2217_LT02: Controle op verplicht element dat niet in de scope zit
Then is in antwoordbericht de aanwezigheid van 'nadereBijhoudingsaardCode' in 'bijhouding' nummer 1 ja

!-- R2217_LT02 NB: controle op verplicht element dat niet in de scope zit en WEL verwijst naar een een attribuut,
!-- dat in het persoonsdeel gescoped is.
Then is in antwoordbericht de aanwezigheid van 'datumAanvang' in 'onderzoek' nummer 2 ja

!-- R2217_LT02 NB: controle op verplicht element dat niet in de scope zit en WEL verwijst naar een een attribuut,
!-- dat in het persoonsdeel gescoped is MAAR LEEG is
Then is er voor xpath //brp:onderzoek/brp:datumEinde geen node aanwezig in het antwoord bericht
Then is er voor xpath //brp:onderzoek/brp:datumAanvang een node aanwezig in het antwoord bericht

Scenario: 1.4   Geef detail persoon met scoping op verplichte attributen (allemaal gevuld)
                LT: R2217_LT01, R2217_LT02
                Uitwerking:
                - Persoon.Bijhouding.BijhoudingsaardCode = Verplicht + gescoped
                - Persoon.Bijhouding.NadereBijhoudingsaardCode = Verplicht + niet gescoped
                - Onderzoek.DatumAanvang = Verplicht + niet gescoped EN Verwijst naar attribuut in persoonsdeel dat WEL gescoped is
                - Onderzoek.DatumEind = Verplicht + niet gescoped En GEVULD
                Verwacht resultaat: Verplichte attributen niet in scope, maar wel geleverd
                - Persoon.Bijhouding.BijhoudingsaardCode in bericht
                - Persoon.Bijhouding.NadereBijhoudingsaardCode in bericht
                - Onderzoek.DatumAanvang WEL in bericht
                - Onderzoek.DatumEind WEL in bericht

!-- Toevoegen van een onderzoek beeindiging op de persoon, omdat de attributen van onderzoek in de scoping zijn opgenomen.
!-- NB. beeindigde onderzoeken worden enkel aan partijen met rol bijhouder geleverd
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C10T160_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Abo GeefDetailsPersoon enkel verplichte attributen'
|zendendePartijNaam|'College'
|rolNaam| 'Bijhoudingsorgaan College'
|bsn|950878601
|scopingElementen|Persoon.Bijhouding.BijhoudingsaardCode,Persoon.Indicatie.DerdeHeeftGezag.Waarde

Then heeft het antwoordbericht verwerking Geslaagd

!-- R2217_LT01: Controle op verplicht element dat ook in de scope zit
Then is in antwoordbericht de aanwezigheid van 'bijhoudingsaardCode' in 'bijhouding' nummer 1 ja

!-- R2217_LT02: Controle op verplicht element dat niet in de scope zit
Then is in antwoordbericht de aanwezigheid van 'nadereBijhoudingsaardCode' in 'bijhouding' nummer 1 ja

!-- R2217_LT02 NB: controle op verplicht element dat niet in de scope zit en WEL verwijst naar een een attribuut,
!-- dat in het persoonsdeel gescoped is.
Then is er voor xpath //brp:onderzoek/brp:datumAanvang[text()='2015-11'] een node aanwezig in het antwoord bericht

!-- R2217_LT02 NB: controle op verplicht element dat niet in de scope zit en WEL verwijst naar een een attribuut,
!-- dat in het persoonsdeel gescoped is EN GEVULD is (datumEinde van onderzoek)
Then is er voor xpath //brp:onderzoek/brp:datumEinde[text()='2015-11'] een node aanwezig in het antwoord bericht

Scenario: 2.    Geef detail persoon met scoping van niet verplichte attributen, attributen die niet gescoped zijn, zijn niet aanwezig in response
                LT: R2217_LT03, R2217_LT04
                Verwacht resultaat:
                - R2217_LT03 Attributen in scope aanwezig in response
                - R2217_LT04 Attributen niet in scope en niet verplicht leveren niet in response

Given leveringsautorisatie uit autorisatie/GeefDetailsPersoon_Scoping
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C10T10_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'geefDetailsPersoonAfnemer'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|270433417
|scopingElementen|Persoon.Identificatienummers.Burgerservicenummer,Persoon.Identificatienummers.Administratienummer,Persoon.SamengesteldeNaam.Voornamen,Persoon.SamengesteldeNaam.Geslachtsnaamstam,Persoon.Geboorte.Datum,Persoon.Adres.Huisnummer,Persoon.Adres.Postcode,Persoon.Adres.Woonplaatsnaam

Then heeft het antwoordbericht verwerking Geslaagd

!-- R2217_LT03 Niet verplichte Attributen wel in scope dus aanwezig in antwoord bericht
Then is in antwoordbericht de aanwezigheid van 'burgerservicenummer' in 'identificatienummers' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'administratienummer' in 'identificatienummers' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'voornamen' in 'samengesteldeNaam' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'geslachtsnaamstam' in 'samengesteldeNaam' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'datum' in 'geboorte' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'huisnummer' in 'adres' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'postcode' in 'adres' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'woonplaatsnaam' in 'adres' nummer 1 ja

!-- R2217_LT04 Niet verplichte attributen niet in scope dus niet aanwezig in antwoordbericht
Then is in antwoordbericht de aanwezigheid van 'gemeenteCode' in 'geboorte' nummer 1 nee
Then is in antwoordbericht de aanwezigheid van 'landGebiedCode' in 'geboorte' nummer 1 nee
