Meta:
@status             Klaar
@usecase            LV.0.MB
@regels             R1806
@sleutelwoorden     Maak BRP bericht

Narrative:
Als van een groep een repeterende voorkomens van materiele en/of formele historie is opgenomen in een bericht,
dan is de sortering als volgt:

Als sorteervolgorde van actuele/historische verschijningen van een gegevensgroep:
a) Verwerkingssoort - Volgens logica sorteren verwerkingssoort
b) "Datum\Tijd verval" - Aflopend
c) DatumAanvangGeldigheid - Aflopend
d) DatumEindeGeldigheid - Aflopend
e) "Datum\Tijd registratie" - Aflopend

Dit met volgende sorteer-regels:
Verwerkingssoort in de volgorde Identificatie, Toevoeging, Wijziging, Verval, Verwijdering, Referentie
"Datum\Tijd verval" als eerste sorteerelement om zo de materiële en formele historie te scheiden
'aflopend' om meest actuele situatie vooraan te hebben
indien "Datum\Tijd verval" en DatumEindeGeldigheid niet zijn gevuld, dan wordt hiervoor ten behoeve van de sortering de 'eeuwigheidswaarde' genomen (voor DatumEindeGeldigheid dus 99991231)
Onbekende delen (00) in een datum worden gesorteerd volgens de normale systematiek voor getallen
"Datum\Tijd registratie" speelt in de sortering alleen maar een rol bij groepen met alleen formele historie

Scenario: 1. Volgorde historie in mutatie bericht
             LT: R1806_LT01
             Verwacht Resultaat: Mutatiebericht wordt als volgt gesorteerd:
                                 a) Verwerkingssoort - Volgens logica sorteren verwerkingssoort
                                 b) "Datum\Tijd verval" - Aflopend
                                 c) DatumAanvangGeldigheid - Aflopend
                                 d) DatumEindeGeldigheid - Aflopend
                                 e) "Datum\Tijd registratie" - Aflopend

Given leveringsautorisatie uit autorisatie/R1989_1
Given persoonsbeelden uit specials:MaakBericht/R1806_DELTAVERS08C10T10_xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo geen popbep doelbinding Haarlem is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/R1806_expected_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep                 | nummer | verwerkingssoort |
| adres                 | 1      | Toevoeging       |
| adres                 | 2      | Wijziging        |
| adres                 | 3      | Verval           |

Scenario: 2. Volgorde historie in volledig bericht
             LT: R1806_LT02
             Verwacht resultaat: Volledigbericht wordt als volgt gesorteerd:
                                 b) "Datum\Tijd verval" - Aflopend
                                 c) DatumAanvangGeldigheid - Aflopend
                                 d) DatumEindeGeldigheid - Aflopend
                                 e) "Datum\Tijd registratie" - Aflopend


Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem
Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken

!-- Omdat datum/tijdstip velden als tijdstipVerval niet te voorspellen zijn qua waarde, zijn deze niet opgenomen in de expecteds
Then hebben attributen in voorkomens de volgende waardes:
| groep       | nummer | attribuut                  | verwachteWaarde|
| adres       | 1      | datumAanvangGeldigheid     | 2016-01-02     |
| adres       | 2      | datumAanvangGeldigheid     | 2016-01-01     |
| adres       | 2      | datumEindeGeldigheid       | 2016-01-02     |
| adres       | 3      | datumAanvangGeldigheid     | 2015-12-31     |
| adres       | 3      | datumEindeGeldigheid       | 2016-01-01     |
| adres       | 4      | datumAanvangGeldigheid     | 2010-12-31     |
| adres       | 4      | datumEindeGeldigheid       | 2015-12-31     |

Then is het synchronisatiebericht gelijk aan expecteds/R1806_expected_scenario_2.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 3. Volgorde historie in volledig bericht
             LT: R1806_LT03
             Verwacht resultaat: Volledigbericht wordt als volgt gesorteerd:
                                 b) "Datum\Tijd verval" - Aflopend
                                 c) DatumAanvangGeldigheid - Aflopend
                                 d) DatumEindeGeldigheid - Aflopend
                                 e) "Datum\Tijd registratie" - Aflopend


Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem
Given persoonsbeelden uit specials:specials/Anne_met_Historie_gedeeltelijk_onb_dat_xls
When voor persoon 986096969 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken

!-- Omdat datum/tijdstip velden als tijdstipVerval niet te voorspellen zijn qua waarde, zijn deze niet opgenomen in de expecteds
Then hebben attributen in voorkomens de volgende waardes:
| groep       | nummer | attribuut                  | verwachteWaarde   |
| adres       | 1      | datumAanvangGeldigheid     | 2016-02           |
| adres       | 2      | datumAanvangGeldigheid     | 2016-01           |
| adres       | 2      | datumEindeGeldigheid       | 2016-02           |
| adres       | 3      | datumAanvangGeldigheid     | 2016              |
| adres       | 3      | datumEindeGeldigheid       | 2016-01           |
| adres       | 4      | datumAanvangGeldigheid     | 2010-12-31        |
| adres       | 4      | datumEindeGeldigheid       | 2016              |

Then is het synchronisatiebericht gelijk aan expecteds/R1806_expected_scenario_3.xml voor expressie //brp:lvg_synVerwerkPersoon
