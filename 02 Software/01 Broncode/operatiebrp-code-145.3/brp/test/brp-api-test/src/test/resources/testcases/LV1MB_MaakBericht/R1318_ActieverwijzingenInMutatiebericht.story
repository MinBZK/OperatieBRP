Meta:
@status             Klaar
@usecase            LV.0.MB, LV.0.MB.VB
@regels             R1318
@sleutelwoorden     Maak BRP bericht, Maak mutatiebericht

Narrative:
In een 'Inhoudelijke groep' (R1540) van een mutatiebericht worden alleen actieverwijzingen opgenomen die samenhangen met de onderhanden Administratieve handeling:
ActieInhoud komt alleen voor als deze verwijst naar een Actie van de onderhanden Administratieve handeling
ActieAanpassingGeldigheid komt alleen voor als deze verwijst naar een Actie van de onderhanden Administratieve handeling
ActieVerval komt alleen voor als deze verwijst naar een Actie van de onderhanden Administratieve handeling


Scenario: 1.1   Mutatie, verhuizing
                LT: R1318_LT01
                Persoon met bsn 270433417
                Intragemeentelijke verhuizing binnen Haarlem van Tulpstraat 33 naar Kerkstraat 31
                Verwacht resulaat: Mutatiebericht met vulling
                - ActieInhoud voor Adres, maar niet voor andere identificerende groepen
                - ActieAanpassingGeldigheid voor adres, maar niet voor andere groepen
                - ActieVerval voor adres, maar niet voor andere groepen

Given leveringsautorisatie uit autorisatie/Abo_geen_popbep_doelbinding_Haarlem
Given persoonsbeelden uit specials:specials/ElisaBeth_Haarlem_Beverwijk_xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo geen popbep doelbinding Haarlem is ontvangen en wordt bekeken
Then heeft het bericht 3 groep 'adres'

Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep 	             | nummer  	| verwerkingssoort |
| adres                  | 1        | Toevoeging       |
| adres                  | 2        | Wijziging        |
| adres                  | 3        | Verval           |
| afgeleidAdministratief | 1        | Toevoeging       |
| afgeleidAdministratief | 2        | Verval           |


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
| identificatienummers  | 1      | actieInhoud               | nee      |
| identificatienummers  | 1      | actieAanpassingGeldigheid | nee      |
| identificatienummers  | 1      | actieVerval               | nee      |
| samengesteldeNaam     | 1      | actieInhoud               | nee      |
| samengesteldeNaam     | 1      | actieAanpassingGeldigheid | nee      |
| samengesteldeNaam     | 1      | actieVerval               | nee      |
| geboorte              | 1      | actieInhoud               | nee      |
| geboorte              | 1      | actieAanpassingGeldigheid | nee      |
| geboorte              | 1      | actieVerval               | nee      |
| geslachtsaanduiding   | 1      | actieInhoud               | nee      |
| geslachtsaanduiding   | 1      | actieAanpassingGeldigheid | nee      |
| geslachtsaanduiding   | 1      | actieVerval               | nee      |

Then is het synchronisatiebericht gelijk aan expecteds/R1318_expected_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon