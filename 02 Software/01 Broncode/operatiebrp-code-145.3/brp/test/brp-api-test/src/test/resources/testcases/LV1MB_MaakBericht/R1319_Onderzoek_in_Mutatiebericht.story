Meta:
@status             Klaar
@usecase            LV.0.MB
@regels             R1319
@sleutelwoorden     Maak BRP bericht


Narrative:
Een mutatiebericht bevat het door de handeling bijgehouden Onderzoek én de gegevens uit het persoonsdeel waarop het onderzoek betrekking heeft.

Deze regel heeft tot doel om ervoor zorg te dragen dat op basis van de verwijzingen uit Onderzoek,
de gegevens in het Persoonsdeel blijven staan in een mutatiebericht.
Hiermee worden de persoonsgegevens die in het onderzoek zijn betrokken ook meegeleverd in een mutatiebericht,
ook al zijn deze persoonsgegevens zelf niet gewijzigd.

Er wordt als volgt te werk gegaan:
Uitgaande van een volledig gevuld persoonsdeel voor een te leveren bericht
Markeer alle attributen, groepen en objecten binnen het persoonsdeel,
die door een onderzoeksregel worden aangewezen
(via een Gegeven in onderzoek.Element, een Gegeven in onderzoek.Object sleutel gegeven of een Gegeven in onderzoek.Voorkomen sleutel gegeven)
Voer na deze markeringen pas R1973 - Een mutatiebericht bevat slechts groepen die in de administratieve handeling zijn geraakt,
gemarkeerd zijn als 'in onderzoek' of een identificerende groep zijn uit

Zie ook 'Onderzoeksgroep' (R1543) voor de definitie van de "Onderzoeksgroep" en de daarin gehanteerde begrippen.


Scenario: 1.    Mutatiebericht aanvang onderzoek
                LT:R1319_LT02
                Verwacht resultaat: Onderzoek met Gegeven in onderzoek.Voorkomen sleutelgegeven welke verwijst naar adres voorkomen
                Adres voorkomen en onderzoek in mutatie bericht

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:MaakBericht/R1319_ElisaBeth_aanvang_onderzoek_adres_xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut    | verwachteWaarde          |
| onderzoek          | 2      | datumAanvang | 2016-01-01               |
| gegevenInOnderzoek | 1      | elementNaam  | Persoon.Adres.Huisnummer |

Then is het synchronisatiebericht gelijk aan expecteds/R1319_expected_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 2.    Mutatiebericht met verhuizing, onderzoek wijst naar een niet identificerend gegeven en naar een groep die niet is geraakt door de administratieve handeling, onderzoek niet in bericht
                LT: R1319_LT03
                Verwacht Resultaat: Mutatie bericht bevat enkel de gewijzigde groepen en de identificerende groepen
                Omdat het onderzoek wijst naar de ongewijzigde gerelateerde ouder en niet naar de gewijzigde adres groep, is het onderzoek niet aanwezig

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:MaakBericht/R1319_ElisaBeth_Verhuizing_na_aanvang_onderzoek_naamgebruik_xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken

!-- Controle dat het onderzoek niet aanwezig is
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep       | nummer | attribuut  | aanwezig |
| adres       | 1      | huisnummer | ja       |
| onderzoeken | 1      | onderzoek  | nee      |

Then is het synchronisatiebericht gelijk aan expecteds/R1319_expected_scenario_3.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 3.    Mutatie bericht na aanvang onderzoek op aanwezig en ontbrekend deel op de persoonslijst, Onderzoek gestart op huisnummer (aanwezig op PL) en op huwelijk datum einde (niet aanwezig op PL)
                LT: R1319_LT01, R1319_LT02, R1319_LT04
                Issue: R1319_LT01, ROOD-1027
                Gegeven in onderzoek.Element met Gegeven in onderzoek.Object sleutel gegeven (adres)
                LT: R1319_LT01
                Verwacht resultaat: Persoon.adres met objectsleutel in onderzoek EN adres behouden in persoonsdeel
                Gegeven in onderzoek.Element met Gegeven in onderzoek.Voorkomen sleutel gegeven (samengestelde naam ouder)
                LT: R1319_LT02
                Verwacht resultaat: GerelateerdeOuder.Persoon.SamengesteldeNaam met Voorkomen Sleutel in ondezoek EN
                samengestelde naam ouder behouden in persoonsdeel mutatiebericht
                onderzoeksgegevens niet in persoonslijst (datum aanvang huwelijk)
                LT: R1319_LT04
                Verwacht resultaat: Huwelijk.DatumEinde NIET geleverd in mutatiebericht, want verwijst naar een ontbrekend gegeven


!-- R1319_LT01 Niet mogelijk om vanuit conversie een onderzoek op een object te starten

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:MaakBericht/R1319_ElisaBeth_aanvang_meerdere_onderzoeken_xls
When voor persoon 270433417 wordt de laatste handeling geleverd

!-- Volledigbericht omdat de administratieve handeling van het type GBA - Bijhouding overig is
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken

!-- Controleer dat er een onderzoek naar het huisnummer aanwezig is
Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut   | verwachteWaarde          |
| gegevenInOnderzoek | 1      | elementNaam | Persoon.Adres.Huisnummer |

!-- Controleer dat er geen gegeven in onderzoek voorkomt naar Huwelijk.DatumEinde
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut                   | aanwezig  |
| gegevenInOnderzoek | 1      | objectSleutelGegeven        | nee       |
| gegevenInOnderzoek | 1      | voorkomenSleutelGegeven     | ja        |
| gegevenInOnderzoek | 2      | voorkomenSleutelGegeven     | nee       |

Then is er voor xpath //brp:gegevenInOnderzoek/brp:elementNaam[text()='Huwelijk.DatumEinde'] geen node aanwezig in het levering bericht

Then is het synchronisatiebericht gelijk aan expecteds/R1319_expected_scenario_4.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 4.    Mutatiebericht aanvang onderzoek Buitenlandspersoonsnummer
                LT:R1319_LT02

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:BuitenlandsPersoonsNummer/NATI08C10T10_onderzoek_xls
When voor persoon 749001161 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/R1319_expected_scenario_5.xml voor expressie //brp:lvg_synVerwerkPersoon