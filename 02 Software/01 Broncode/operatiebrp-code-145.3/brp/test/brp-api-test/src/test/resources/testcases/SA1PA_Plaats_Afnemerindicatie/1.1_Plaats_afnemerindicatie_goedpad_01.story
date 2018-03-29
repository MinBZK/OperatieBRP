Meta:
@status             Klaar
@usecase            SA.0.PA
@sleutelwoorden     Plaats afnemerindicatie

Narrative:
Plaatsing afnemersindicatie:
Het afhandelen van een afnemerverzoek tot het plaatsen van een afnemerindicatie,
zodat de afnemer voor de opgegeven Persoon een eerste Volledig bericht ontvangt
en zolang de afnemerindicatie nog geldig is alle geautoriseerde wijzigingen ontvangt via een mutatiebericht.

Scenario:   1. Gemeente Utrecht plaatst een afnemerindicatie op persoon Jan met bsn = 606417801, vanuit de leverautorisatie Geen_pop.bep_levering_op_basis_van_afnemerindicatie.
            LT: R1257_LT01, R1258_LT03, R2061_LT01, R1339_LT03, R1402_LT01, R2052_LT01, R1406_LT03, R2120_LT02, R2121_LT01, R2122_LT01, R1261_LT03, R1262_LT07, R1263_LT01, R1264_LT03, R2053_LT01, R2055_LT01, R2056_LT03,
            LT: R2130_LT03, R1350_LT01, R2061_LT01, R1402_LT01, R1405_LT01, R1406_LT03, R1539_LT01, R1587_LT01, R1267_LT01, R1975_LT01, R1976_LT01, R1980_LT01, R1539_LT01, R1350_LT01, R1983_LT34

            Uitwerking:
            Deze leverautorisatie beschikt over de diensten: Plaatsen afnemerindicatie, Verwijderen afnemerindicatie,
            Mutatielevering op basis van afnemerindicatie en Synchronisatie persoon
            De datum aanvang materiele periode van de te plaatsen afnemerindicatie is kleiner dan de systeemdatum
            en de datum einde geldigheid is gevuld met de dag na de systeemdatum
            Verwacht resultaat: zie hieronder

            AL.1.AV Afhandelen Verzoek AL.1.XV:
            Geen testgevallen

            AL.1.AV Afhandelen Verzoek AL.1.AU:
            LT: R1257_LT01, R1258_LT03, R2052_LT01, R2120_LT02, R2121_LT01, R2122_LT01
            Verwacht resultaat: Bericht komt door de controles heen, geen expliciete controle van toepassing

            AL.1.AV Afhandelen Verzoek LV.1.AL:
            Datum ingang < systeem datum en datum einde = leeg voor leveringsautorisatie en dienst en beide (net als de dienstbundel) zijn niet geblokkeerd
            LT: R1261_LT03, R1262_LT07, R1263_LT01, R1264_LT03, R2053_LT01, R2055_LT01, R2056_LT03, R2130_LT03
            Verwacht resultaat: bericht komt door autorisatie heen, geen expliciete controles vereist

            SA.1.PA Plaats Afnemerindicatie:
            LT: R1350_LT01
            Verwacht resultaat: Volledig bericht

            SA.1.PA.CA Controleer autorisatie:
            LT: R2061_LT01
            Verwacht resultaat: Vervolg use case, geen expliciete controle van toepassing

            SA.1.PA.CI Controleer inhoud:
            LT: R1339_LT03, R1402_LT01, R1405_LT01, R1406_LT03, R1539_LT01, R1587_LT01
            Verwacht resultaat: Plaatsen afnemerindicatie geslaagd, geen expliciete controle van toepassing

            LV.0.MB Maak BRP bericht
            LT: R1267_LT01
            Verwacht resultaat: A synchroon bericht met vulling:
            Bericht.Zendende partij : '199903'
            Bericht.Zendende systeem : 'BRP'
            Bericht.Ontvangende partij :
            Partij \ Rol.Partij (Partijcode van de afnemer voor wie het bericht bedoeld is, bepaald via de
            Toegang leveringsautorisatie.Geautoriseerde van het
            Toegang leveringsautorisatie waarvoor geleverd wordt)
            Bericht.Referentienummer: unieke ID genereren
            Bericht.Datum/tijd verzending : Datum\tijd systeem bij aanmaken van het bericht inclusief de tijdzone (bijvoorbeeld: '2012-04-18T15:32:03.234+01:00')

            Geautoriseerde groepen moeten geleverd worden
            LT: R1975_LT01
            Verwacht resultaat: autorisatie op alle groepen dus
            Then heeft het bericht 1 groep 'adres'
            Then heeft het bericht 1 groep 'persoon'
            Then heeft het bericht 1 groep 'samengesteldeNaam'
            Then heeft het bericht 1 groep 'geboorte'
            Then heeft het bericht 1 groep 'geslachtsaanduiding'
            Then heeft het bericht 1 groep 'identificatienummers'

            Indien een object over een of meerdere geautoriseerde attributen beschikt, die lager in de hirarchie zitten,
            dan mag dit object NIET gefiltert worden.
            LT: R1976_LT01
            Verwacht resultaat: Groepen worden geleverd incl attributen

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumAanvangMaterielePeriode|gisteren
|datumEindeVolgen|morgen

Then heeft het antwoordbericht verwerking Geslaagd

!-- R1350_LT01
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

!-- R1267_LT01
Then hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| stuurgegevens 	    | 1         | zendendeSysteem 	    | BRP             |
| stuurgegevens 	    | 1         | zendendePartij 	    | 199903          |
| stuurgegevens         | 1         | ontvangendePartij     | 034401          |

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep 	            | nummer | attribuut					| aanwezig 	|
| stuurgegevens	        | 1      | referentienummer  			| ja       	|
| stuurgegevens	        | 1      | tijdstipVerzending  			| ja       	|

!-- R1975_LT01
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut            | aanwezig |
| persoon            | 1      | adressen             | ja       |
| persoon            | 1      | samengesteldeNaam    | ja       |
| persoon            | 1      | geboorte             | ja       |
| persoon            | 1      | geslachtsaanduiding  | ja       |
| persoon            | 1      | identificatienummers | ja       |
| persoon            | 1      | samengesteldeNaam    | ja       |

!-- R1976_01
Then hebben attributen in voorkomens de volgende waardes:
| groep                | nummer | attribuut           | verwachteWaarde |
| identificatienummers | 1      | burgerservicenummer | 606417801       |
| identificatienummers | 2      | burgerservicenummer | 463095145       |
| identificatienummers | 3      | burgerservicenummer | 823306185       |

Then heeft het bericht 3 groepen 'persoon'
