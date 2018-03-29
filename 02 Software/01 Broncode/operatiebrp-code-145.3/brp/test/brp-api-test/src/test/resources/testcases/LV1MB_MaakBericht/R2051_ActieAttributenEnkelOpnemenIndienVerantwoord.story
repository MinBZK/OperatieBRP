Meta:
@status             Klaar
@usecase            LV.0.MB
@regels             R2051
@sleutelwoorden     Maak BRP Bericht

Narrative:
De attributen ActieInhoud, ActieAanpassingGeldigheid en ActieVerval van een 'Inhoudelijke groep' (R1540) of een 'Onderzoeksgroep' (R1543)
worden alleen opgenomen als ze verwijzen naar een Actie die voorkomt in de verantwoordingsinformatie die de Persoonslijst van de hoofdpersoon
hebben bijgehouden of hebben geleid tot een mutatie van het onderzoeksdeel ('Onderzoeksgroep' (R1543)).


Scenario:   1. Actie in verantwoording verwijst naar inhoudelijke groep (adres)
               LT: R2051_LT01, R2051_LT02
               Verwacht Resultaat:
               - ActieInhoud, ActieAanpassingGeldigheid en actieVerval zijn aanwezig bij de inhoudelijke groep adres
               - ActieInhoud, ActieAanpassingGeldigheid en actieVerval zijn NIET aanwezig bij andere inhoudelijke groep bv. geboorte

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:MaakBericht/R2051_Elisabeth_verhuizing_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                               | partijNaam      | tsReg                 | dienstId |
| 270433417 | 'Geen pop.bep. levering op basis van afnemerindicatie' | 'Gemeente Utrecht' | 2014-01-01 T00:00:00Z | 1        |

When voor persoon 270433417 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

Then verantwoording acties staan in persoon

!-- Groep adres heeft historievorm formeel-materieel, de actieverval komt niet voor in onderhanden adm handeling en dus ook niet in inhoudelijke groep
!-- Groep afgeleid administratief heeft historievorm formeel, actieverval komt wel in handeling en dus ook in inhoudelijke groep.
!-- Mbt adres : Op grond van de ActieVervalTbvLeveringMutaties met waarde 10 wordt het vervallen voorkomen wel in het bericht opgenomen en wordt de
!-- verwerkingssoort afgeleid op ‘vervallen’. Maar de juridische verantwoording voor het verval is nog steeds actie 4 die in ActieVerval staat.
!-- Dat is geen onderdeel van de ‘onderhanden levering’ en wordt dan door R1318 uit het bericht gestript.
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                 | nummer | attribuut                 | aanwezig |
| adres                 | 1      | actieInhoud               | ja       |
| adres                 | 2      | actieAanpassingGeldigheid | ja       |
| adres                 | 3      | actieVerval               | nee      |
| afgeleidAdministratief| 1      | actieInhoud               | ja       |
| afgeleidAdministratief| 2      | actieVerval               | ja       |
| bijgehoudenActies     | 1      | actie                     | ja       |
| geboorte              | 1      | actieInhoud               | nee      |
| geboorte              | 2      | actieAanpassingGeldigheid | nee      |
| geboorte              | 3      | actieVerval               | nee      |

!-- Het volledig bericht bevat administratieve handeling met 2 acties. de inhoudelijke groepen die hier naar verwijzen zijn afgeleidAdministratief,
!-- bijhouding en adres. Deze groepen hebben een actieInhoud of actieVerval of actieAanpassingGeldigheid, overige inhoudelijke groepen niet.
Then is het synchronisatiebericht gelijk aan expecteds/R2051_expected_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   2.     Beeindiging onderzoek. Actie in verantwoording verwijst naar onderzoeksgroep.
                    LT: R2051_LT03, R2051_LT04
                    Verwacht Resultaat:
                    - ActieInhoud, ActieAanpassingGeldigheid en actieVerval zijn aanwezig bij de onderzoeksgroep adres
                    - ActieInhoud, ActieAanpassingGeldigheid en actieVerval zijn NIET aanwezig bij groep met verwerkingssoort Identiteit (deze zijn namelijk niet geraakt door de handeling)

Given leveringsautorisatie uit autorisatie/R2051_levering_op_basis_van_doelbinding
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC70T30_xls
When voor persoon 678462409 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken
!-- Mbt actieVerval in onderzoek : Op grond van de ActieVervalTbvLeveringMutaties met waarde 11 wordt het vervallen voorkomen wel in het bericht opgenomen
!-- en wordt de verwerkingssoort afgeleid op ‘vervallen’. Maar de juridische verantwoording voor het verval is nog steeds actie 9 die in ActieVerval staat.
!-- Dat is geen onderdeel van de ‘onderhanden levering’ en wordt dan door R1318 uit het bericht gestript.
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep             | nummer | attribuut                 | aanwezig |
| onderzoek         | 2      | actieInhoud               | ja       |
| onderzoek         | 3      | actieVerval               | nee      |
| samengesteldeNaam | 1      | actieInhoud               | nee      |
| samengesteldeNaam | 1      | actieAanpassingGeldigheid | nee      |
| samengesteldeNaam | 1      | actieVerval               | nee      |
| bijgehoudenActies | 1      | actie                     | ja       |

Then is er voor xpath //brp:actie[@brp:objectSleutel=//brp:onderzoek/brp:actieInhoud[text()]] een node aanwezig in het levering bericht
!-- Het volledig bericht bevat administratieve handeling met 2 acties. de inhoudelijke groepen die hier naar verwijzen zijn afgeleidAdministratief,
!-- bijhouding en adres. Deze groepen hebben een actieInhoud of actieVerval of actieAanpassingGeldigheid, overige inhoudelijke groepen niet.
Then is het synchronisatiebericht gelijk aan expecteds/R2051_expected_scenario_2_2.xml voor expressie //brp:lvg_synVerwerkPersoon
