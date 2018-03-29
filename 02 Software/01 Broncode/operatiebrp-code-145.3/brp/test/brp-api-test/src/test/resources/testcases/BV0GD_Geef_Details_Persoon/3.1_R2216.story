Meta:

@status             Klaar
@usecase            BV.0.GD
@sleutelwoorden     Geef Details Persoon
@regels             R2216

Narrative:
De door de gebruiker gevraagde scoping (berichtparameter 'Gevraagde attributen') mag uitsluitend Elementen bevatten
die voorkomen in Dienstbundel \ Groep \ Attribuut van de gevraagde Dienst

Scenario: 1.    Scoping bevat alleen attributen (element soort = 3)
                LT: R2216_LT01
                Uitwerking:
                - Scoping op adres.huisnummer
                - Dienstbundel geautoriseerd op alle attributen
                Verwacht resultaat:
                - attribuut huisnummer in bericht

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|scopingElementen|Persoon.Adres.Huisnummer

Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'huisnummer' in 'adres' nummer 1 ja

Scenario: 2.    Scoping bevat attribuut + object element soort = 1
                LT: R2216_LT02
                Uitwerking:
                - Scoping op adres.huisnummer
                - Scoping op Persoon.Adres
                Verwacht resultaat:
                - Foutmelding 	Scope bevat ongeldige elementen.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|scopingElementen|Persoon.Adres.Huisnummer,Persoon.Adres

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R2216     | Scope bevat ongeldige elementen.

Scenario: 3.    Scoping bevat attribuut + groep (element soort = 2)
                LT: R2216_LT03
                Uitwerking:
                - Scoping op adres.huisnummer
                - Scoping op Persoon.Adres.Identiteit
                Verwacht resultaat:
                - Foutmelding 	Scope bevat ongeldige elementen.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|scopingElementen|Persoon.Adres.Huisnummer,Persoon.Adres.Identiteit

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R2216     | Scope bevat ongeldige elementen.

Scenario: 4.    Scoping bevat object element soort = 1
                LT: R2216_LT04
                Uitwerking:
                - Scoping op Persoon.Adres
                Verwacht resultaat:
                - Foutmelding 	Scope bevat ongeldige elementen.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|scopingElementen|Persoon.Adres

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R2216     | Scope bevat ongeldige elementen.

Scenario: 5.    Scoping bevat groep (element soort = 2)
                LT: R2216_LT05
                Uitwerking:
                - Scoping op Persoon.Adres.Identiteit
                Verwacht resultaat:
                - Foutmelding 	Scope bevat ongeldige elementen.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|scopingElementen|Persoon.Adres.Identiteit

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R2216     | Scope bevat ongeldige elementen.

Scenario: 6.    Scoping op leeg attribuut (onderzoek.ActieVerval, maar geen onderzoek gestart en afgesloten)
                LT: R2216_LT06
                Verwacht resultaat:
                - Alle attributen, zoals normaal in geefdetailspersoon

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|scopingElementen|Huwelijk.DatumAanvang

Then heeft het antwoordbericht verwerking Geslaagd
Then is er voor xpath //brp:voornamen[text()='Jan'] geen node aanwezig in het antwoord bericht