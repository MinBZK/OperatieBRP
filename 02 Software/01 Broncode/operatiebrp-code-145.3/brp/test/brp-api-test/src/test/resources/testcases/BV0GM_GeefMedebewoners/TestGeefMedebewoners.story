Meta:
@status             Klaar
@usecase            BV.0.GM, BV.1.GM
@sleutelwoorden     Test geef medebewoners

Narrative:
Test geef medebewoners met volledigPersoon ie.
- Voorkomens van emigratie aanwezig
- Voorkomen van overlijden aanwezig
- Voorkomen van buitenlandspersoonsnummer aanwezig
- Voorkomen van onderzoek aanwezig
- Voorkomen van curatele aanwezig

Scenario: 1. Geef medebewoners VolledigPersoon Overleden met buitenlandsadres
             Toelichting: Persoon is overleden en heeft een buitenlandsadres (Voorkomen migratie aanwezig)
             op het gegeven peilmoment heeft de persoon echter een nederlandsadres
             Verwacht resultaat:
             - Voorkomen overlijden aanwezig
             - Voorkomen van nederlands adres aanwezig
             - Voorkomen curatele indicatie aanwezig


Given leveringsautorisatie uit autorisatie/GeefMedebewoners_BIJHOUDER
Given personen uit specials:VolledigBericht/Anne_Bakker_Volledig_Overleden_xls

Given verzoek geef medebewoners van persoon:
| key                               | value                         |
| leveringsautorisatieNaam          | 'Geef Medebewoners bijhouder' |
| zendendePartijNaam                | 'College'                     |
| rolNaam                           | 'Bijhoudingsorgaan College'   |
|peilmomentMaterieel|'2014-12-31'|
|burgerservicenummer|'595891305' |

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan expected/GM_volledig_Persoon_1.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R

Scenario: 2. Geef medebewoners VolledigPersoon Overleden
             Toelichting: Persoon is overleden en heeft meerdere nationaliteiten met een voorkomen van buitenlandspersoonsnummer
             op het gegeven peilmoment heeft de persoon een nederlandsadres
             Verwacht resultaat:
             - Voorkomen overlijden aanwezig
             - Voorkomen van nederlands adres aanwezig
             - Adelijke titel aanwezig in resultaat bericht


Given leveringsautorisatie uit autorisatie/GeefMedebewoners_BIJHOUDER
Given personen uit specials:VolledigBericht/Anne_Bakker_Volledig_Overleden_2_xls

Given verzoek geef medebewoners van persoon:
| key                               | value                         |
| leveringsautorisatieNaam          | 'Geef Medebewoners bijhouder' |
| zendendePartijNaam                | 'College'                     |
| rolNaam                           | 'Bijhoudingsorgaan College'   |
|peilmomentMaterieel|'2014-12-31'|
|burgerservicenummer|'595891305' |

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan expected/GM_volledig_Persoon_2.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R

Scenario: 3. Geef medebewoners VolledigPersoon
             Toelichting: Onderzoek aanwezig, want verwijst naar een voorkomen van het adres in het resultaat bericht.

Given leveringsautorisatie uit autorisatie/GeefMedebewoners_BIJHOUDER
Given personen uit specials:VolledigBericht/Anne_Bakker_Volledig_xls

Given verzoek geef medebewoners van persoon:
| key                               | value                         |
| leveringsautorisatieNaam          | 'Geef Medebewoners bijhouder' |
| zendendePartijNaam                | 'College'                     |
| rolNaam                           | 'Bijhoudingsorgaan College'   |
| identificatiecodeNummeraanduiding | '0626200010016003'            |

Then heeft het antwoordbericht verwerking Geslaagd

Then is er voor xpath //brp:adres een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan expected/GM_volledig_Persoon_3.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R

Scenario: 4. Geef medebewoners met VolledigPersoon
             Toelichting: Onderzoek niet aanwezig in bericht, want verwijst niet naar het voorkomen van het adres in het resultaat bericht.

Given leveringsautorisatie uit autorisatie/GeefMedebewoners_BIJHOUDER
Given personen uit specials:VolledigBericht/Anne_Bakker_Volledig_xls

Given verzoek geef medebewoners van persoon:
| key                      | value                         |
| leveringsautorisatieNaam | 'Geef Medebewoners bijhouder' |
| zendendePartijNaam       | 'College'                     |
| rolNaam                  | 'Bijhoudingsorgaan College'   |
| peilmomentMaterieel      | '2014-12-31'                  |
| burgerservicenummer      | '595891305'                   |

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan expected/GM_volledig_Persoon_4.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R