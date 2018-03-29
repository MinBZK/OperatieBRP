Meta:
@status             Klaar
@usecase            LV.0.MB
@regels             R1805
@sleutelwoorden     Maak BRP bericht

Narrative:
Een aantal gegevens kunnen bij een Persoon gelijktijdig meermalen aanwezig zijn,
zoals voornamen en nationaliteiten.
In uitgaande berichten willen we deze gegevens zo sorteren,
dat gegevens die logisch bij elkaar horen ook bij elkaar blijven.
Pas daarbinnen willen de normale sortering op groepen toepassen, zoals beschreven in R1806 - Volgorde historie in een bericht.

Dit betekent dat de volgende objecttypen eerst (oplopend) gesorteerd worden op de bijbehorende logische kenmerken:

Persoon \ Geslachtsnaamcomponent.Identiteit op Persoon \ Geslachtsnaamcomponent.Volgnummer
Persoon \ Indicatie.Identiteit op Persoon \ Indicatie.Soort
Persoon \ Nationaliteit.Identiteit op Persoon \ Nationaliteit.Nationaliteit
Onderzoek op Onderzoek.Datum aanvang
Persoon \ Verificatie.Identiteit op Persoon \ Verificatie.Partij en daarbinnen op Persoon \ Verificatie.Soort
Persoon \ Voornaam.Identiteit op Persoon \ Voornaam.Volgnummer

Dit met volgende sorteer-regels:
Onbekende delen (00) in een datum worden gesorteerd volgens de normale systematiek voor getallen


Scenario: 1.    sortering meervoudige voorkomende Voornamen
                LT: R1805_LT02
                Verwacht resultaat: sortering op basis van volgnummer

Given leveringsautorisatie uit autorisatie/Abo_geen_popbep_doelbinding_Haarlem
Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Abo geen popbep doelbinding Haarlem'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|595891305
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Abo geen popbep doelbinding Haarlem is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep	  | nummer    | attribuut             | verwachteWaarde   |
| voornaam| 1         | naam            	  | Anne              |
| voornaam| 1         | volgnummer            | 1                 |
| voornaam| 3         | naam            	  | Mies              |
| voornaam| 3         | volgnummer            | 2                 |
| voornaam| 5         | naam            	  | Lizette           |
| voornaam| 5         | volgnummer            | 3                 |


Scenario: 2.    sortering meervoudige voorkomende Onderzoek
                LT: R1805_LT03
                Verwacht resultaat: sortering op basis van datum aanvang

Given persoonsbeelden uit specials:/MaakBericht/R1805_Anne_Bakker_Meerdere_Onderzoeken_xls
Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

!-- Controle op Onderzoek op Onderzoek.Datum aanvang
Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut    | verwachteWaarde                         |
| onderzoek          | 2      | datumAanvang | 2014-01-01                              |
| onderzoek          | 4      | datumAanvang | 2014-07-30                              |
| onderzoek          | 6      | datumAanvang | 2015-12-31                              |

Scenario: 3. sortering meervoudige voorkomende groepen in volledig bericht
            LT: R1805_LT05
            Verwacht resultaat: groep 		                | Sorteer volgorde
                                nationaliteit               | gesorteerd op nationaliteit code
                                verificatie                 | gesorteerd op partij en daarbinnen op soort

Given persoonsbeelden uit specials:/MaakBericht/R1805_Anne_Bakker_meerdere_verificatie_code_soort_xls
Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep         | nummer | attribuut         | verwachteWaarde |
| nationaliteit | 1      | nationaliteitCode | 0001            |
| nationaliteit | 2      | nationaliteitCode | 0067            |


Scenario:   4.a  Meerdere voorkomens persoon indicatie indentiteit, check sortering op soort indicatie
            LT: R1805_LT04
            Verwacht resultaat:
            - Indicatie 1: onderCuratele
            - Indicatie 2: vastgesteldNietNederlander
            Uitwerking:
            1: Derde heeft gezag?
            2: Onder curatele?
            3: Volledige verstrekkingsbeperking?
            4: Vastgesteld niet Nederlander?
            5: Behandeld als Nederlander?
            6: Signalering met betrekking tot verstrekken reisdocument?
            7: Staatloos
            8: Bijzondere verblijfsrechtelijke positie?
            - In de test zijn de indicaties, Onder curatele? en vastgesteld Niet Nederlander? opgenomen


Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:specials/Jan_R1805_xls
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken

Then heeft het bericht voor xpath name(//brp:indicaties/*[1]) de waarde brp:onderCuratele
Then heeft het bericht voor xpath name(//brp:indicaties/*[2]) de waarde brp:vastgesteldNietNederlander

Scenario: 4.b   Meerdere voorkomens persoon indicatie indentiteit, check sortering op soort indicatie
            LT: R1805_LT04
            Verwacht resultaat:
            - Indicatie 1: onderCuratele
            - Indicatie 2: staatloos
            Uitwerking:
            1: Derde heeft gezag?
            2: Onder curatele?
            3: Volledige verstrekkingsbeperking?
            4: Vastgesteld niet Nederlander?
            5: Behandeld als Nederlander?
            6: Signalering met betrekking tot verstrekken reisdocument?
            7: Staatloos
            8: Bijzondere verblijfsrechtelijke positie?
            - In de test zijn de indicaties, Onder curatele? en Staatloos? opgenomen

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken

Then heeft het bericht voor xpath name(//brp:indicaties/*[1]) de waarde brp:derdeHeeftGezag
Then heeft het bericht voor xpath name(//brp:indicaties/*[2]) de waarde brp:onderCuratele

Scenario: 5 NATI08C20T10_xls Initiele vulling persoon met buitenlandspersoonsnummer
            LT: R1805_LT07
            Verwacht resultaat:
            Meerdere actuele voorkomens van buitenlandspersoonsnummer
            Sortering van voorkomens van buitenlandspersoonsnummer op Buitenlands persoonsnummer.Nummer

Given leveringsautorisatie uit autorisatie/autorisatiealles_bijhouder
Given persoonsbeelden uit specials:BuitenlandsPersoonsNummer/NATI08C20T10_xls

Given verzoek geef details persoon:
| key                      | value                        |
| leveringsautorisatieNaam | 'autorisatiealles bijhouder' |
| zendendePartijNaam       | 'College'                    |
| rolNaam                  | 'Bijhoudingsorgaan College'  |
| bsn                      | 151281701                    |

Then heeft het antwoordbericht verwerking Geslaagd

!-- Controle op sortering van voorkomens van buitenlandspersoonsnummer op autoriteit van afgifte
Then hebben in het antwoordbericht de attributen in de groepen de volgende waarde:
| groep                     | attribuut                | verwachteWaardes |
| buitenlandsPersoonsnummer | autoriteitVanAfgifteCode | 0027,0027        |
| buitenlandsPersoonsnummer | nummer                   | 1,2              |

Then is het antwoordbericht gelijk aan expecteds/R1805_expected_scenario_5.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: 6 NATI08C20T20_xls Initiele vulling persoon met buitenlandspersoonsnummer
            LT: R1805_LT07
            Verwacht resultaat:
            Meerdere actuele voorkomens van buitenlandspersoonsnummer
            Sortering van voorkomens van buitenlandspersoonsnummer op autoriteit van afgifte

Given leveringsautorisatie uit autorisatie/autorisatiealles_bijhouder
Given persoonsbeelden uit specials:BuitenlandsPersoonsNummer/NATI08C20T20_xls

Given verzoek geef details persoon:
| key                      | value                        |
| leveringsautorisatieNaam | 'autorisatiealles bijhouder' |
| zendendePartijNaam       | 'College'                    |
| rolNaam                  | 'Bijhoudingsorgaan College'  |
| bsn                      | 364054633                    |

Then heeft het antwoordbericht verwerking Geslaagd

!-- Controle op sortering van voorkomens van buitenlandspersoonsnummer op autoriteit van afgifte
Then hebben in het antwoordbericht de attributen in de groepen de volgende waarde:
| groep                     | attribuut                | verwachteWaardes |
| buitenlandsPersoonsnummer | autoriteitVanAfgifteCode | 0027,0028        |
| buitenlandsPersoonsnummer | nummer                   | 1,1              |

Then is het antwoordbericht gelijk aan expecteds/R1805_expected_scenario_6.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R
