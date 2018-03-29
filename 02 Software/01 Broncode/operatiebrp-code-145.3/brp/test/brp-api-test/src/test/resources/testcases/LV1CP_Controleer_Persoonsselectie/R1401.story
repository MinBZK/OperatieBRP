Meta:
@status             Klaar
@usecase            LV.1.CPI
@regels             R1401
@sleutelwoorden     Controleer Persoonsselectie


Narrative:
Er moet een Persoon \ Afnemerindicatie aanwezig zijn bij de opgegeven Persoon
voor de betreffende Partij (Afnemer) en voor de (in de berichtparameters) opgegeven Leveringsautorisatie.

Scenario:   1. Synchroniseer Persoon service voor leveringsautorisatie met Mutatatie levering obv Afnemerindicatie zonder afnemerindicatie bij persoon
               LT: R1401_LT02
               Verwacht resultaat: Foutmelding: Er bestaat geen geldige afnemerindicatie voor deze persoon binnen de opgegeven leveringsautorisatie.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                              |
| R1401     | Er bestaat geen geldige afnemerindicatie voor deze persoon binnen de opgegeven leveringsautorisatie. |


Scenario:   2. Synchroniseer Persoon service voor leveringsautorisatie met verkeerde partij
               LT: R1401_LT03
               Verwacht resultaat: Foutmelding: Er bestaat geen geldige afnemerindicatie voor deze persoon binnen de opgegeven leveringsautorisatie.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie_2_Partijen
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie 2 Partijen'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie 2 Partijen'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                              |
| R1401     | Er bestaat geen geldige afnemerindicatie voor deze persoon binnen de opgegeven leveringsautorisatie. |


Scenario:   3. Synchroniseer persoon, geen pers.afnemerindicatie voor leveringsautorisatie
               LT: R1401_LT04
               Verwacht resultaat: Foutmelding: Er bestaat geen geldige afnemerindicatie voor deze persoon binnen de opgegeven leveringsautorisatie.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie, autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie_attendering
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie attendering'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                              |
| R1401     | Er bestaat geen geldige afnemerindicatie voor deze persoon binnen de opgegeven leveringsautorisatie. |


Scenario: 4. Als de dienst verwijdering afnemerindicatie wordt aangeroepen om een afnemerindicatie te verwijderen,
            LT: R1401_LT06
            dan dient de opgegeven afnemerindicatie aanwezig te zijn bij de opgegeven persoon voor de betreffende afnemer en voor het opgegeven leveringsautorisatie.
            De persoon voor wie de afnemerindicatie verwijdert wordt valt niet binnen de leveringsautorisatie van de afnemende partij
            Verwacht resultaat:  synchroon responsebericht
                Met vulling:
                    Met vulling:
                    -  Verwerking = Fout
                    -  Code     =   R1401
                    -  Melding  =   Er bestaat geen geldige afnemerindicatie voor deze persoon binnen de opgegeven leveringsautorisatie.
                    -  Persoon = De betreffende Persoon uit het bericht
                    -  Afnemer = De Partij waarvoor de Dienst wordt geleverd


Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek verwijder afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waarde '199903'
Then heeft in het antwoordbericht 'zendendeSysteem' in 'stuurgegevens' de waarde 'BRP'
Then is in antwoordbericht de aanwezigheid van 'referentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'crossReferentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'tijdstipVerzending' in 'stuurgegevens' nummer 1 ja

Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'

And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                              |
| R1401     | Er bestaat geen geldige afnemerindicatie voor deze persoon binnen de opgegeven leveringsautorisatie. |

Then heeft in het antwoordbericht 'partijCode' in 'verwijderingAfnemerindicatie' de waarde '034401'
Then is in antwoordbericht de aanwezigheid van 'tijdstipRegistratie' in 'verwijderingAfnemerindicatie' nummer 1 ja
Then heeft in het antwoordbericht 'burgerservicenummer' in 'verwijderingAfnemerindicatie' de waarde '606417801'


Scenario: 5. Als de dienst verwijdering afnemerindicatie wordt aangeroepen om een afnemerindicatie te verwijderen,
            LT: R1401_LT07, R2061_LT04
            dan dient de opgegeven afnemerindicatie aanwezig te zijn bij de opgegeven persoon voor de betreffende afnemer en voor het opgegeven Leveringsautorisatie.
            De partij om de afnemerindicatie mee te verwijderen is niet de partij waar de afnemerindicatie door geplaatst is
            Verwacht resultaat:  synchroon responsebericht
                Met vulling:
                    Met vulling:
                    -  Verwerking = Fout
                    -  Code     =   R1401
                    -  Melding  =   Er bestaat geen geldige afnemerindicatie voor deze persoon binnen de opgegeven leveringsautorisatie.
                    -  Persoon = De betreffende Persoon uit het bericht
                    -  Afnemer = De Partij waarvoor de Dienst wordt geleverd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie_2_Partijen
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie 2 Partijen'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

Given verzoek verwijder afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie 2 Partijen'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|606417801

Then heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waarde '199903'
Then heeft in het antwoordbericht 'zendendeSysteem' in 'stuurgegevens' de waarde 'BRP'
Then is in antwoordbericht de aanwezigheid van 'referentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'crossReferentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'tijdstipVerzending' in 'stuurgegevens' nummer 1 ja

Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                              |
| R1401     | Er bestaat geen geldige afnemerindicatie voor deze persoon binnen de opgegeven leveringsautorisatie. |

Then heeft in het antwoordbericht 'partijCode' in 'verwijderingAfnemerindicatie' de waarde '039201'
Then is in antwoordbericht de aanwezigheid van 'tijdstipRegistratie' in 'verwijderingAfnemerindicatie' nummer 1 ja
Then heeft in het antwoordbericht 'burgerservicenummer' in 'verwijderingAfnemerindicatie' de waarde '606417801'


Scenario: 6.    Als de dienst verwijdering afnemerindicatie wordt aangeroepen om een afnemerindicatie te verwijderen,
                LT:  R1401_LT08
                dan dient de opgegeven afnemerindicatie aanwezig te zijn bij de opgegeven persoon voor de betreffende afnemer en voor de opgegeven leveringsautorisatie.
                Opgegeven partij om afnemerindicatie mee te verwijderen beschikt niet over het juiste Leveringsautorisatie
                Verwacht resultaat:  synchroon responsebericht
                    Met vulling:
                    -  Verwerking = Fout
                    -  Code     =   R1401
                    -  Melding  =   Er bestaat geen geldige afnemerindicatie voor deze persoon binnen de opgegeven leveringsautorisatie.
                    -  Persoon = De betreffende Persoon uit het bericht
                    -  Afnemer = De Partij waarvoor de Dienst wordt geleverd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie_2_Partijen, autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

Given verzoek verwijder afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie 2 Partijen'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waarde '199903'
Then heeft in het antwoordbericht 'zendendeSysteem' in 'stuurgegevens' de waarde 'BRP'
Then is in antwoordbericht de aanwezigheid van 'referentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'crossReferentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'tijdstipVerzending' in 'stuurgegevens' nummer 1 ja

Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'

And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                              |
| R1401     | Er bestaat geen geldige afnemerindicatie voor deze persoon binnen de opgegeven leveringsautorisatie. |

Then heeft in het antwoordbericht 'partijCode' in 'verwijderingAfnemerindicatie' de waarde '034401'
Then is in antwoordbericht de aanwezigheid van 'tijdstipRegistratie' in 'verwijderingAfnemerindicatie' nummer 1 ja
Then heeft in het antwoordbericht 'burgerservicenummer' in 'verwijderingAfnemerindicatie' de waarde '606417801'


Scenario:   7. GeefDetailsPersoon service voor persoon zonder afnemerindicatie op een uniek persoon
               LT: R1401_LT10, R1403_LT01
               Verwacht resultaat: Vervolg use case

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd
