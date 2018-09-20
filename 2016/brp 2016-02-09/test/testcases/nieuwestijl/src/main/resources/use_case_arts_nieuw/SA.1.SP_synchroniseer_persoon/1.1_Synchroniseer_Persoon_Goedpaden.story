Meta:
@epic               Verbeteren testtooling
@auteur             miuij
@status             Klaar
@usecase            SA.0.SP, SA.1.SP, LV.0.AV,LV.1.AV,LV.1.AB,LV.1.XV,LV.1.AU,LV.1.AL,LV.1.MR,LV.1.VB,LV1.AB
@regels             R1339,R1342,R1344,R1345,R1346,R1347,R1403,R1538,R1539,R1978,R1982,R2002,R2016,R2059,R2062
@sleutelwoorden     Synchronisatie persoon, Synchroniseer Persoon, Controleer inhoud, Verwijder afnemerindicatie, Afhandeling verzoek, Afhandelen verzoek, Archiveer bericht, XSD validatie, Authenticatie, Autorisatie levering, Maak responsebericht, Verstuur bericht, Archiveer bericht


Narrative:
Synchroniseer persoon:
Als afnemer wil ik een afnemerverzoek tot het synchroniseren van een persoon afhandelen,
met dienst 'Mutatielevering op basis van doelbinding' of 'Mutatielevering op basis van afnemerindicatie',
zodat de afnemer voor de opgegeven Persoon een Volledig bericht ontvangt

Scenario:   1   Verzoek synchronisatie persoon, met dienst mutatielevering op basis van doelbinding en
                Datum einde volgen is gevuld met 1 dag > systeemdatum
            Logische testgevallen AL.1.AV Afhandelen Verzoek AL.1.AB:   R1268_07, R1269_04 (moet nog verwerkt worden)
            Logische testgevallen AL.1.AV Afhandelen Verzoek AL.1.XV:   Geen testgevallen
            Logische testgevallen AL.1.AV Afhandelen Verzoek AL.1.AU:   R1257_01, R1258_03, R2052_01
            Logische testgevallen AL.1.AV Afhandelen Verzoek LV.1.AL:   R1260_01, R1261_03, R1262_03, R1263_01, R1264_01, R2053_01, R2055_01, R2056_01
            Logische testgevallen SA.1.SP Synchroniseer persoon:        R1344_01, R1347_01, R1978_01, R1982_01
            Logische testgevallen LV.1.PA Plaats Afnemerindicatie:      R1339_02
            Logische testgevallen AL.1.AV Afhandelen Verzoek AL.1.MR:   R1266_01, R1410_01
            Logische testgevallen AL.1.AV Afhandelen Verzoek AL.1.VB:   R1985_01, R1991_01
            Logische testgevallen AL.1.AV Afhandelen Verzoek AL.1.AB:   R1268_08, R1270_07
            Logische testgevallen LV.1.MB Maak BRP bericht LV.1.MB:     R1267_01, R1341_01, R1315_03, R1316_05, R1621_01
            Logische testgevallen LV.1.MB Maak BRP bericht LV.1.MB.VB:  R1353_02, R1546_02, R1622_04,
            Logische testgevallen LV.1.LE Leveren LV.1.PB:              R1616_02, R1617_07, R1618_07, R1619_05, R1620_03, R1996_01,
            Logische testgevallen AL.1.LE Leveren AL.1.VE:              R1985_01, R1991_01
            Logische testgevallen LV.1.LE Leveren AL.1.AB:              R1268_10, R1270_09
            Logische testgevallen LV.1.LE:                              R1612_01, R1995_02, R1997_01, R1997_03
             Verwacht resultaat: Antwoordbericht
                Met vulling:
                - Zendende partij
                - Zendende systeem
                - referentienummer
                - tijdstip
                - abonnementnaam
                - burgerservicenummer
            Verwacht resultaat:Leveringsbericht
                Met vulling:
                -  Soort bericht = Volledigbericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 803697417, 968282441, 412670409 zijn verwijderd
Given de standaardpersoon UC_Timmy met bsn 412670409 en anr 7157893906 zonder extra gebeurtenissen
Given de database is aangepast met: update autaut.dienst set dateinde='${vandaagsql(0,0,1)}'
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 1.2_Synchroniseer_Persoon_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waarde '199903'
Then heeft in het antwoordbericht 'zendendeSysteem' in 'stuurgegevens' de waarde 'BRP'
Then is in antwoordbericht de aanwezigheid van 'referentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'tijdstipVerzending' in 'stuurgegevens' nummer 1 ja

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide


Scenario:   2.1     Verzoek synchronisatie persoon, met dienst Mutatielevering op basis van afnemerindicatie en
                    Datum einde volgen is gevuld met 1 dag > systeemdatum
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB:   R1268_07, R1269_04 (moet nog verwerkt worden)
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.XV:   Geen testgevallen
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AU:   R1257_01, R1258_03, R2052_01
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AL:   R1260_01, R1261_03, R1262_03, R1263_01, R1264_01, R2053_01, R2055_01, R2056_01
            Logische testgevallen SA.1.SP Synchroniseer persoon:        R1346_01, R1347_02, R1978_01, R1982_01, R1973_01
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.MR:   R1266_01, R1410_01
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.VB:   R1985_01, R1991_01
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB:   R1268_08, R1270_07
            Logische testgevallen LV.1.MB Maak BRP bericht LV.1.MB:     R1267_01, R1341_01, R1315_03, R1316_05, R1621_01
            Logische testgevallen LV.1.MB Maak BRP bericht LV.1.MB.VB:  R1353_02, R1546_02, R1622_04,
            Logische testgevallen LV.1.LE Leveren LV.1.PB:              R1616_02, R1617_01, R1618_07, R1619_05, R1620_03, R1996_01,
            Logische testgevallen LV.1.LE Leveren LV.1.VB:              R1985_01, R1991_01
            Logische testgevallen LV.1.LE Leveren LV.1.AB:              R1268_10, R1270_09
            Logische testgevallen LV.1.LE:                              R1612_01, R1995_02, R1997_01, R1997_03
            Verwacht resultaat: Synchroon response bericht + Leveringsbericht
                Met vulling:
                -  Soort bericht = Volledigbericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd

Given de personen 803697417, 968282441, 412670409 zijn verwijderd
Given de standaardpersoon UC_Timmy met bsn 412670409 en anr 7157893906 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 1.4_Plaats_Afnemerindicatie.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   2.2     Verzoek synchronisatie persoon, met dienst Mutatielevering op basis van afnemerindicatie en
                    Datum einde volgen is gevuld met 1 dag > systeemdatum
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB:   R1268_07, R1269_04(moet nog verwerkt worden)
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.XV:   Geen testgevallen
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AU:   R1257_01, R1258_03, R2052_01
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AL:   R1260_01, R1261_03, R1262_03, R1263_01, R1264_01, R2053_01, R2055_01, R2056_01
            Logische testgevallen SA.1.SP Synchroniseer persoon:        R1346_01, R1347_02, R1978_01, R1982_01, R1973_01
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.MR:   R1266_01, R1410_01
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.VB:   R1985_01, R1991_01
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB:   R1268_08, R1270_07
            Logische testgevallen LV.1.MB Maak BRP bericht LV.1.MB:     R1267_01, R1341_01, R1315_03, R1316_05, R1621_01
            Logische testgevallen LV.1.MB Maak BRP bericht LV.1.MB.VB:  R1353_02, R1546_02, R1622_04,
            Logische testgevallen LV.1.LE Leveren LV.1.PB:              R1616_02, R1617_01, R1618_07, R1619_05, R1620_03, R1996_01,
            Logische testgevallen LV.1.LE Leveren LV.1.VB:              R1985_01, R1991_01
            Logische testgevallen LV.1.LE Leveren LV.1.AB:              R1268_10, R1270_09
            Logische testgevallen LV.1.LE:                              R1612_01, R1995_02, R1997_01, R1997_03
            Verwacht resultaat: Synchroon response bericht + Leveringsbericht
                Met vulling:
                -  Soort bericht = Volledigbericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd


Given de database is aangepast met: update autaut.dienst set dateinde='${vandaagsql(0,0,1)}'
Given de cache is herladen
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 1.3_Synchroniseer_Persoon_2.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   3.   Verzoek synchronisatie persoon, met dienst mutatielevering op basis van doelbinding en
                begindatum dienst = vandaag
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB:   R1268_07, R1269_04(moet nog verwerkt worden)
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.XV:   Geen testgevallen
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AU:   R1257_01, R1258_03, R2052_01
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AL:   R1260_01, R1261_03, R1262_03, R1263_01, R1264_01, R2053_01, R2055_01, R2056_01
            Logische testgevallen SA.1.SP Synchroniseer persoon:        R1344_02, R1347_01, R1978_01, R1982_01
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.MR:   R1266_01, R1410_01
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.VB:   R1985_01, R1991_01
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB:   R1268_08, R1270_07
            Logische testgevallen LV.1.MB Maak BRP bericht LV.1.MB:     R1267_01, R1341_01, R1315_03, R1316_05, R1621_01
            Logische testgevallen LV.1.MB Maak BRP bericht LV.1.MB.VB:  R1353_02, R1546_02, R1622_04,
            Logische testgevallen LV.1.LE Leveren LV.1.PB:              R1616_02, R1617_07, R1618_07, R1619_05, R1620_03, R1996_01,
            Logische testgevallen LV.1.LE Leveren LV.1.VB:              R1985_01, R1991_01
            Logische testgevallen LV.1.LE Leveren LV.1.AB:              R1268_10, R1270_09
            Logische testgevallen LV.1.LE:                              R1612_01, R1995_02, R1997_01, R1997_03
            Verwacht resultaat: Synchroon response bericht + Leveringsbericht
                Met vulling:
                -  Soort bericht = Volledigbericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 803697417, 968282441, 412670409 zijn verwijderd
Given de standaardpersoon UC_Timmy met bsn 412670409 en anr 7157893906 zonder extra gebeurtenissen
Given de database is aangepast met: update autaut.dienst set datingang='${vandaagsql(0,0,0)}'
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 1.2_Synchroniseer_Persoon_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   4.1     Verzoek synchronisatie persoon, met dienst Mutatielevering op basis van afnemerindicatie en
                    Begindatum dienst = vandaag
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB:   R1268_07, R1269_04(moet nog verwerkt worden)
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.XV:   Geen testgevallen
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AU:   R1257_01, R1258_03, R2052_01
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AL:   R1260_01, R1261_03, R1262_03, R1263_01, R1264_01, R2053_01, R2055_01, R2056_01
            Logische testgevallen SA.1.SP Synchroniseer persoon:        R1346_01, R1347_02, R1978_01, R1982_01, R1973_01
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.MR:   R1266_01, R1410_01
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.VB:   R1985_01, R1991_01
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB:   R1268_08, R1270_07
            Logische testgevallen LV.1.MB Maak BRP bericht LV.1.MB:     R1267_01, R1341_01, R1315_03, R1316_05, R1621_01
            Logische testgevallen LV.1.MB Maak BRP bericht LV.1.MB.VB:  R1353_02, R1546_02, R1622_04,
            Logische testgevallen LV.1.LE Leveren LV.1.PB:              R1616_02, R1617_01, R1618_07, R1619_05, R1620_03, R1996_01,
            Logische testgevallen LV.1.LE Leveren LV.1.VB:              R1985_01, R1991_01
            Logische testgevallen LV.1.LE Leveren LV.1.AB:              R1268_10, R1270_09
            Logische testgevallen LV.1.LE:                              R1612_01, R1995_02, R1997_01, R1997_03
            Verwacht resultaat: Synchroon response bericht + Leveringsbericht
                Met vulling:
                -  Soort bericht = Volledigbericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd



Given de personen 803697417, 968282441, 412670409 zijn verwijderd
Given de standaardpersoon UC_Timmy met bsn 412670409 en anr 7157893906 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 1.4_Plaats_Afnemerindicatie.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   4.2     Verzoek synchronisatie persoon, met dienst Mutatielevering op basis van afnemerindicatie en
                    Begindatum dienst = vandaag
             Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB:   R1268_07, R1269_04(moet nog verwerkt worden)
             Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.XV:   Geen testgevallen
             Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AU:   R1257_01, R1258_03, R2052_01
             Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AL:   R1260_01, R1261_03, R1262_03, R1263_01, R1264_01, R2053_01, R2055_01, R2056_01
             Logische testgevallen SA.1.SP Synchroniseer persoon:        R1346_01, R1347_02, R1978_01, R1982_01, R1973_01
             Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.MR:   R1266_01, R1410_01
             Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.VB:   R1985_01, R1991_01
             Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB:   R1268_08, R1270_07
             Logische testgevallen LV.1.MB Maak BRP bericht LV.1.MB:     R1267_01, R1341_01, R1315_03, R1316_05, R1621_01
             Logische testgevallen LV.1.MB Maak BRP bericht LV.1.MB.VB:  R1353_02, R1546_02, R1622_04,
             Logische testgevallen LV.1.LE Leveren LV.1.PB:              R1616_02, R1617_01, R1618_07, R1619_05, R1620_03, R1996_01,
             Logische testgevallen LV.1.LE Leveren LV.1.VB:              R1985_01, R1991_01
             Logische testgevallen LV.1.LE Leveren LV.1.AB:              R1268_10, R1270_09
             Logische testgevallen LV.1.LE:                              R1612_01, R1995_02, R1997_01, R1997_03
                    Verwacht resultaat: Synchroon response bericht + Leveringsbericht
                     Met vulling:
                      -  Soort bericht = Volledigbericht
                      -  Persoon = De betreffende Persoon uit het bericht
                      -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                      -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd

Given de database is aangepast met: update autaut.dienst set datingang='${vandaagsql(0,0,0)}'
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 1.3_Synchroniseer_Persoon_2.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide