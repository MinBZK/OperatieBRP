Meta:
@status             Klaar
@usecase            LV.0.MB, LV.0.MB.VB
@regels             R1317
@sleutelwoorden     Maak BRP bericht, Maak mutatiebericht

Narrative:
Met de 'verwerkingssoort' wordt de afnemer per geleverd object en groepsvoorkomen geïnformeerd wat er in de BRP is 'gemuteerd'. Voor groepsvoorkomens wordt dit afgeleid op basis van de ActieInhoud/ActieAanpassingGeldigheid/ActieVerval/ActieVervalTbvLeveringMutaties, op de volgende manier:

ActieVerval is gelijk aan:
•	ActieVerval als ActieVervalTbvLeveringMutaties geen waarde heeft
•	ActieVervalTbvLeveringMutaties als deze wel een waarde heeft

1.	Ga uit van de 'gereconstrueerde persoon' na de onderhanden handeling (robuustheid,'Gereconstrueerde persoon na Administratieve handeling' (R1556))
2.	Stel een lijst met Acties samen die behoren bij de Administratieve handeling die behandeld wordt
3.	Kijk voor elke groep of er een match tussen ActieInhoud, ActieAanpassingGeldigheid en ActieVerval en de lijst met Acties van de Administratieve handeling:

•	Er is een match met ActieInhoud , dan is de verwerkingssoort 'Toevoeging'
•	Er is geen match met ActieInhoud maar wel met ActieAanpassingGeldigheid, dan is de verwerkingssoort 'Wijziging'
•	Er is geen match met ActieInhoud en ActieAanpassingGeldigheid maar wel met ActieVerval, dan is de verwerkingssoort 'Verval'
•	Als het een 'Identificerende groep' (R1542) betreft, en er is geen match met ActieInhoud, ActieAanpassingGeldigheid of ActieVerval, en de groep heeft geen DatumEindeGeldigheid, dan is de verwerkingssoort 'Identificatie'.
•	In alle overige gevallen is de verwerkingssoort 'Referentie'

(Toelichting 1: De verwerkingssoort beschrijft wat er gebeurd is vanuit het gezichtspunt van de BRP en kan dus per handeling worden afgeleid)

(Toelichting 2: Voorkomens met verwerkingssoort 'Referentie' komen alleen in een mutatiebericht als het wettelijk verplicht te leveren gegevens betreft of omdat een bijgehouden Onderzoek naar die groep verwijst)


Scenario:   1.  Verwerkingsoorten, identificatie, toevoeging, wijziging, verval
                LT: R1317_LT01, R1317_LT02, R1317_LT03, R1317_LT06
                Verwacht resultaat:
                - Mutatie bericht met
                    Actieinhoud bij groep identificatienummers, verwacht verwerkingssoort 'Toevoeging'
                    ActieAanpassingGeldigheid bij groep identificatienummers, verwacht verwerkingssoort 'Wijziging'
                    Actieverval en ActieVervalTbvLeveringMutaties bij groep identificatienummers, verwacht verwerkingssoort 'Verval'
                    Actieverval bij groep afgeleidAdministratief, verwacht verwerkingssoort 'Verval'

                - Soort bericht: Mutatiebericht

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_Bakker_GBA_Bijhouding_Identificerende_Groepen_xls

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                               | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 595891305 | 'Geen pop.bep. levering op basis van afnemerindicatie' | 'Gemeente Utrecht' |                  |                              | 2014-01-01 T00:00:00Z | 1        |


When voor persoon 595891305 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep                  | nummer | verwerkingssoort |
| afgeleidAdministratief | 2      | Verval           |
| identificatienummers   | 1      | Toevoeging       |
| identificatienummers   | 2      | Wijziging        |
| identificatienummers   | 3      | Verval           |

Then is het synchronisatiebericht gelijk aan expecteds/R1317_expected_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 2.   Onderzoek op adres, adres groep heeft verwerkingssoort Referentie, identificerende groep heeft verwerkingssoort Identificatie
                LT: R1317_LT05, R1317_LT04
                Onderzoek wordt gestart op adres, aanvangsdatum 2015-12-31
                Verwacht resultaat:
                - adres heeft verwerkingssoort referentie

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_Bakker_Onderzoek_Aanvang_xls

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                               | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 595891305 | 'Geen pop.bep. levering op basis van afnemerindicatie' | 'Gemeente Utrecht' |                  |                              | 2014-01-01 T00:00:00Z | 1        |

When voor persoon 595891305 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then heeft het bericht 2 groepen 'onderzoek'
Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep                | nummer | verwerkingssoort |
| identificatienummers | 1      | Identificatie    |
| adres                | 1      | Referentie       |
| onderzoek            | 2      | Toevoeging       |

Then is het synchronisatiebericht gelijk aan expecteds/R1317_expected_scenario_2.xml voor expressie //brp:lvg_synVerwerkPersoon