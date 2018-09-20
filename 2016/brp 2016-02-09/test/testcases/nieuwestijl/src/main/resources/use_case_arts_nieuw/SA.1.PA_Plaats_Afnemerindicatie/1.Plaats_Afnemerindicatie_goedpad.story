Meta:
@auteur             jowil
@status             Klaar
@usecase            SA.0.PA,SA.1.PA,SA.1.PA.AL,SA.1.PA.CI,LV.1.PA,LV.0.AV,LV.1.AV,LV.1.AB,LV.1.XV,LV.1.AU,LV.1.AL,LV.1.MR,LV.1.VB,LV1.AB
@regels             R1336,R1339,R1350,R1402,R1403,R1404,R1405,R1406,R1407,R1408,R1538,R1539,R1984,R2016,R2061
@sleutelwoorden     Plaats afnemerindicatie, Controleer inhoud, Verwijder afnemerindicatie, Afhandeling verzoek, Afhandelen verzoek, Archiveer bericht, XSD validatie, Authenticatie, Autorisatie levering, Maak responsebericht, Verstuur bericht, Archiveer bericht

Narrative:
Plaatsing afnemersindicatie:
Het afhandelen van een afnemerverzoek tot het plaatsen van een afnemerindicatie,
zodat de afnemer voor de opgegeven Persoon een eerste Volledig bericht ontvangt
en zolang de afnemerindicatie nog geldig is alle geautoriseerde wijzigingen ontvangt via een mutatiebericht.

Scenario:   1. Plaatsen afnemerindicatie met datum aanvang materiele periode is kleiner dan systeem datum EN datum einde volgen = gevuld
            Logische testgevallen AL.1.AV Afhandelen Verzoek AL.1.AB:   R1268_11, R1269_06
            Logische testgevallen AL.1.AV Afhandelen Verzoek AL.1.XV:   Geen testgevallen
            Logische testgevallen AL.1.AV Afhandelen Verzoek AL.1.AU:   R1257_01, R1258_03, R2052_01
            Logische testgevallen AL.1.AV Afhandelen Verzoek AL.1.AL:   R1260_01, R1261_03, R1262_03, R1263_01, R1264_01, R2053_01, R2055_01, R2056_01
            Logische testgevallen SA.1.PA Plaats Afnemerindicatie:      R1339_03, R1342_03, R1350_01, R1402_01, R1403_01, R1404_01, R1405_01
                                                                        R1406_03, R1407_01, R1408_01, R1538_01, R1539_01, R1984_01, R1987_01, R2016_01
            Logische testgevallen AL.1.AV Afhandelen Verzoek AL.1.MR:   R1266_01, R1410_01
            Logische testgevallen AL.1.AV Afhandelen Verzoek AL.1.AB:   R1268_12, R1270_08

            Logische testgevallen LV.1.MB Maak BRP bericht LV.1.MB:     R1267_01, R1341_01, R1315_03, R1316_05, R1551_01, R1552_01, R1555_01, R1621_01, R1974_01, R1975_01
            Logische testgevallen LV.1.MB Maak BRP bericht LV.1.MB.VB:  R1353_02, R1546_02, R1622_04,
            Logische testgevallen LV.1.LE Leveren LV.1.PB:              R1615_01, R1616_02, R1617_01, R1618_07, R1619_05, R1620_03, R1996_01,
            Logische testgevallen AL.1.VE Leveren AL.1.VE:              R1985_01, R1991_01
            Logische testgevallen LV.1.LE Leveren AL.1.AB:   R1268_10, R1270_09
            Logische testgevallen LV.1.LE:                              R1612_01, R1995_02, R1997_01, R1997_03
            Verwacht resultaat: 1. Synchroon responsebericht
                                Met vulling:
                                -  Verwerking = geslaagd
                                -  Hoogste meldings niveau = Geen
                                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                                2. XSD valide asynchroon resoponsebericht
            Verwacht resultaat: 2. Asynchroon bericht met vulling
                         •	Bericht.Zendende partij : '199903'
                         •	Bericht.Zendende systeem : 'BRP'
                         •	Bericht.Ontvangende partij : 017401
                         •	Bericht.Ontvangende systeem : 'Leveringsysteem'
                         •	Bericht.Referentienummer: unieke ID genereren
                         •	Bericht.Datum/tijd verzending : Datum\tijd systeem bij aanmaken van het bericht inclusief de tijdzone (bijvoorbeeld: '2012-04-18T15:32:03.234+01:00')
            Bevinding JIRA ISSUE TEAMBRP-4592

Given de personen 299054457, 743274313, 606417801 zijn verwijderd
And de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand 1.Plaats_Afnemerindicatie_goedpad_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waarde '199903'
Then heeft in het antwoordbericht 'zendendeSysteem' in 'stuurgegevens' de waarde 'BRP'
Then is in antwoordbericht de aanwezigheid van 'referentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'crossReferentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'tijdstipVerzending' in 'stuurgegevens' nummer 1 ja

Then heeft in het antwoordbericht 'verwerking' in 'resultaat' de waarde 'Geslaagd'
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'

Then heeft in het antwoordbericht 'partijCode' in 'plaatsingAfnemerindicatie' de waarde '017401'
Then is in antwoordbericht de aanwezigheid van 'tijdstipRegistratie' in 'plaatsingAfnemerindicatie' nummer 1 ja
Then heeft in het antwoordbericht 'burgerservicenummer' in 'plaatsingAfnemerindicatie' de waarde '606417801'

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

And hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| stuurgegevens 	    | 1         | zendendeSysteem 	    | BRP             |
| stuurgegevens 	    | 1         | zendendePartij 	    | 199903          |
| stuurgegevens         | 1         | ontvangendePartij     | 017401          |
| stuurgegevens 	    | 1         | ontvangendeSysteem 	| Leveringsysteem |
| identificatienummers  | 1         | burgerservicenummer   | 606417801       |

And hebben attributen in voorkomens de volgende aanwezigheid:
| groep 	            | nummer | attribuut					| aanwezig 	|
| stuurgegevens	        | 1      | referentienummer  			| ja       	|
| stuurgegevens	        | 1      | tijdstipVerzending  			| ja       	|
| bijgehoudenActies     | 1      | actie            			| ja       	|
| actie                 | 1      | soortNaam            		| ja       	|
| actie                 | 2      | soortNaam            		| ja       	|
| actie                 | 1      | bron                 		| nee       |
| actie                 | 1      | document                 	| nee       |


Then in kern select p.* from kern.pers p where p.anr=1383746930 de volgende gegevens:
| veld                 | waarde     |
| geslnaamstam         | McCormick  |

Then is er geprotocolleerd voor persoon 606417801 en soortdienst Plaatsen afnemerindicatie en soortSynchronisatie Volledigbericht

Then in ber select q.* from ber.ber q left join ber.berpers qp on (q.id = qp.ber) left join kern.pers p on (qp.pers = p.id) where p.bsn=606417801 and q.zendendepartij=2001 de volgende gegevens:
| veld                 | waarde             |
| srt                  | 23                 |
| zendendesysteem      | BRP                |
| ontvangendepartij    | 178                |
| ontvangendesysteem   | Leveringsysteem    |
| richting             | 2                  |
| srtsynchronisatie    | 2                  |
| levsautorisatie      | 1                  |
| dienst               | 1                  |



Scenario:   2. Plaatsen afnemerindicatie met datum aanvang materiele periode = systeem datum EN datum einde volgen = leeg
            Logische testgevallen Use Case: R1336_02, R1339_03, R1342_03, R1350_01, R1402_01, R1403_01, R1404_01, R1405_02,
                                            R1406_04, R1407_01, R1408_01, R1538_01, R1539_01, R1984_01, R1987_01
            Verwacht resultaat: 1. Synchroon responsebericht
                                Met vulling:
                                -  Verwerking = geslaagd
                                -  Hoogste meldings niveau = Geen
                                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                                2. XSD valide asynchroon resoponsebericht

Given de personen 299054457, 743274313, 606417801 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand 1.Plaats_Afnemerindicatie_goedpad_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waarde '199903'
Then heeft in het antwoordbericht 'zendendeSysteem' in 'stuurgegevens' de waarde 'BRP'
Then is in antwoordbericht de aanwezigheid van 'referentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'crossReferentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'tijdstipVerzending' in 'stuurgegevens' nummer 1 ja

Then heeft in het antwoordbericht 'verwerking' in 'resultaat' de waarde 'Geslaagd'
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'

Then heeft in het antwoordbericht 'partijCode' in 'plaatsingAfnemerindicatie' de waarde '017401'
Then is in antwoordbericht de aanwezigheid van 'tijdstipRegistratie' in 'plaatsingAfnemerindicatie' nummer 1 ja

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide



Scenario:   3. Plaatsen afnemerindicatie met datum aanvang materiele periode = leeg EN datum einde volgen = leeg
            Logische testgevallen Use Case: R1336_02, R1339_03, R1342_03, R1350_01, R1402_01, R1403_01, R1404_01, R1405_04,
                                            R1406_04, R1407_01, R1408_01, R1538_01, R1539_01, R1984_01, R1987_01
            Verwacht resultaat: 1. Synchroon responsebericht
                                Met vulling:
                                -  Verwerking = geslaagd
                                -  Hoogste meldings niveau = Geen
                                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                                2. XSD valide asynchroon resoponsebericht

Given de personen 299054457, 743274313, 606417801 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 1.Plaats_Afnemerindicatie_goedpad_03.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waarde '199903'
Then heeft in het antwoordbericht 'zendendeSysteem' in 'stuurgegevens' de waarde 'BRP'
Then is in antwoordbericht de aanwezigheid van 'referentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'crossReferentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'tijdstipVerzending' in 'stuurgegevens' nummer 1 ja

Then heeft in het antwoordbericht 'verwerking' in 'resultaat' de waarde 'Geslaagd'
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'

Then heeft in het antwoordbericht 'partijCode' in 'plaatsingAfnemerindicatie' de waarde '017401'
Then is in antwoordbericht de aanwezigheid van 'tijdstipRegistratie' in 'plaatsingAfnemerindicatie' nummer 1 ja

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   4. Plaatsen afnemerindicatie met datum aanvang materiele periode = systeem datum EN datum einde volgen = leeg
            + gerelateerde persoon1 = ingeschrevene, gerelateerde persoon 2 <> ingeschrevene
            Logische testgevallen Use Case: R1336_02, R1339_03, R1342_03, R1350_01, R1402_01, R1403_01, R1404_01, R1405_02,
                                            R1406_04, R1407_01, R1408_01, R1538_02, R1539_01, R1984_01, R1987_01
            Verwacht resultaat: 1. Synchroon responsebericht
                                Met vulling:
                                -  Verwerking = geslaagd
                                -  Hoogste meldings niveau = Geen
                                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                                2. XSD valide asynchroon resoponsebericht
                                Met vulling:
                                - Persoon 1 als gerelateerde in bericht
                                - Persoon 2 Niet in bericht

Given de personen 299054457, 743274313, 606417801 zijn verwijderd
And de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given de database is aangepast met: update kern.pers set srt=2 where bsn = 743274313

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 1.Plaats_Afnemerindicatie_goedpad_04.yml

When het bericht wordt verstuurd
Then de database wordt opgeruimd met: update kern.pers set srt=1 where bsn = 743274313
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waarde '199903'
Then heeft in het antwoordbericht 'zendendeSysteem' in 'stuurgegevens' de waarde 'BRP'
Then is in antwoordbericht de aanwezigheid van 'referentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'crossReferentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'tijdstipVerzending' in 'stuurgegevens' nummer 1 ja

Then heeft in het antwoordbericht 'verwerking' in 'resultaat' de waarde 'Geslaagd'
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'

Then heeft in het antwoordbericht 'partijCode' in 'plaatsingAfnemerindicatie' de waarde '017401'
Then is in antwoordbericht de aanwezigheid van 'tijdstipRegistratie' in 'plaatsingAfnemerindicatie' nummer 1 ja

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

And hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| identificatienummers 	| 1         | burgerservicenummer 	| 606417801       |
| identificatienummers 	| 2         | burgerservicenummer 	| 299054457       |
| identificatienummers 	| 3         | burgerservicenummer 	| 743274313       |

