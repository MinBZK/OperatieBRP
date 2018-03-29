Meta:

@status             Klaar
@usecase            BV.0.GD
@sleutelwoorden     Geef Details Persoon
@regels             R2215


Narrative:
De door de gebruiker gevraagde scoping (berichtparameter 'Gevraagde attributen') mag uitsluitend Elementen bevatten
die voorkomen in Dienstbundel \ Groep \ Attribuut van de gevraagde Dienst

Scenario: 1.    Scoping op attribuut dat in de dienstbundel voorkomt
                LT: R2215_LT01, R2400_LT03
                Uitwerking:
                - Scoping op adres.huisnummer
                - Dienstbundel geautoriseerd op alle attributen
                Verwacht resultaat:
                - attribuut huisnummer in bericht

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C10T10_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|270433417
|scopingElementen|Persoon.Adres.Huisnummer

Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'huisnummer' in 'adres' nummer 1 ja

Scenario: 2.    Scoping op attribuut dat NIET in de dienstbundel voorkomt
                LT: R2215_LT02
                Uitwerking:
                - Scoping op adres.huisnummer
                - Dienstbundel NIET geautoriseerd op huisnummer
                Verwacht resultaat:
                - Foutmelding Scoping bevat niet-geautoriseerde attributen

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_obv_afnemerindicatie_huisnummer_niet_geautoriseerd
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C10T10_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering obv afnemerindicatie huisnummer niet geautoriseerd'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|270433417
|scopingElementen|Persoon.Adres.Huisnummer

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R2215     | Scoping bevat niet-geautoriseerde attributen

Scenario: 3.    Scoping op attributen die in de dienstbundel voorkomen
                LT: R2215_LT03
                Uitwerking:
                - Scoping op adres.huisnummer
                - scoping op samengesteldeNaam.voornaam
                - Dienstbundel geautoriseerd op alle attributen
                Verwacht resultaat:
                - attribuut huisnummer in bericht
                - attribuut voornaam in het bericht

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C10T10_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|270433417
|scopingElementen|Persoon.Adres.Huisnummer,Persoon.SamengesteldeNaam.Voornamen

Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'huisnummer' in 'adres' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'voornamen' in 'samengesteldeNaam' nummer 1 ja

Scenario: 4.    Scoping op attributen die NIET in de dienstbundel voorkomen
                LT: R2215_LT04
                Uitwerking:
                - Scoping op adres.huisnummer
                - scoping op samengesteldeNaam.voornaam
                - Dienstbundel NIET geautoriseerd op attribuut huisnummer
                Verwacht resultaat:
                - Foutmelding Scoping bevat niet-geautoriseerde attributen

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_obv_afnemerindicatie_huisnummer_niet_geautoriseerd
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C10T10_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering obv afnemerindicatie huisnummer niet geautoriseerd'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|270433417
|scopingElementen|Persoon.Adres.Huisnummer,Persoon.SamengesteldeNaam.Voornamen

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R2215     | Scoping bevat niet-geautoriseerde attributen

Scenario: 5.    Scoping zonder attribuut mee te geven
                LT: R2215_LT05
                Verwacht resultaat:
                - Alle attributen, zoals normaal in geefdetailspersoon

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C10T10_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|270433417

Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'huisnummer' in 'adres' nummer 1 ja
