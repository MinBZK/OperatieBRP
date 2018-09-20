Meta:
 @auteur             kedon
 @status             Klaar
 @usecase            SA.0.VA, SA.1.VA, SA.1.VA.CI, LV.1.VA, LV.0.AV, LV.1.AV, LV.1.AB, LV.1.XV, LV.1.AU, LV.1.AL, LV.1.MR, LV.1.VB, LV1.AB
 @regels             R1401,R1403,R1404,R1409,R2061
 @sleutelwoorden     Verwijdering afnemerindicatie, Verwijder afnemerindicatie, Controleer inhoud, Verwijder afnemerindicatie, Afhandeling verzoek, Afhandelen verzoek, Archiveer bericht, XSD validatie, Authenticatie, Autorisatie levering, Maak responsebericht, Verstuur bericht, Archiveer bericht

Narrative:
Vewijder afnemersindicatie:
Het afhandelen van een afnemerverzoek tot het verwijderen van een afnemerindicatie,
zodat de afnemer vanuit de dienst ‘Mutatielevering o.b.v. afnemerindicatie’ voor de opgegeven Persoon geen mutatieberichten meer ontvangt.
In deze story worden verschillende, per scenario beschreven, foutpaden getest.


Scenario: 3.    Als de dienst verwijdering afnemerindicatie wordt aangeroepen om een afnemerindicatie te verwijderen, dan dient de opgegeven afnemerindicatie aanwezig te zijn bij de opgegeven persoon voor de betreffende afnemer en voor het opgegeven leveringsautorisatie.
                Opgegeven partij om afnemerindicatie mee te verwijderen beschikt niet over het juiste abonnement
                Logsiche testgevallen   R1401_02, R1403_01, R1404_01
                Verwacht resultaat:  synchroon responsebericht
                    Met vulling:
                    -  Verwerking = Fout
                    -  Melding  =   Er bestaat geen geldige afnemerindicatie voor deze persoon binnen de opgegeven leveringsautorisatie.
                    -  Persoon = De betreffende Persoon uit het bericht
                    -  Afnemer = De Partij waarvoor de Dienst wordt geleverd

Given de personen 299054457, 743274313, 606417801 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_afn_ind/Mutaties_op_specifieke_personen_voor_afnemer_is_017401, /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de database is aangepast met: update autaut.levsautorisatie set populatiebeperking = 'WAAR' where naam='Mutaties op specifieke personen voor afnemer is 017401'
Given de cache is herladen

Given verzoek voor leveringsautorisatie 'Mutaties op specifieke personen voor afnemer is 017401' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 2.2_Plaats_afnemerindicatie_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd


Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 2.3_Verwijder_afnemerindicatie_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'

Then is in antwoordbericht de aanwezigheid van 'regelCode' in 'melding' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'soortNaam' in 'melding' nummer 1 ja
Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'Er bestaat geen geldige afnemerindicatie voor deze persoon binnen de opgegeven leveringsautorisatie.'

Then heeft in het antwoordbericht 'partijCode' in 'verwijderingAfnemerindicatie' de waarde '017401'
Then is in antwoordbericht de aanwezigheid van 'tijdstipRegistratie' in 'verwijderingAfnemerindicatie' nummer 1 ja


Scenario: 4. Als de dienst verwijdering afnemerindicatie wordt aangeroepen om een afnemerindicatie te verwijderen, dan dient de opgegeven afnemerindicatie aanwezig te zijn bij de opgegeven persoon voor de betreffende afnemer en voor het opgegeven Leveringsautorisatie.
            De partij om de afnemerindicatie mee te verwijderen is niet de partij waar de afnemerindicatie door geplaatst is
            Logsiche testgevallen: R1401_03, R1403_01, R1404_01, R2061_04
            Verwacht resultaat:  synchroon responsebericht
                Met vulling:
                -  Verwerking = Fout
                -  Melding  =   Er bestaat geen geldige afnemerindicatie voor deze persoon binnen de opgegeven leveringsautorisatie.
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd

Given de personen 299054457, 743274313, 606417801 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie, /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie_Stoutenburg
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 2.4_Plaats_afnemerindicatie_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie Stoutenburg' en partij 'Gemeente Stoutenburg'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 2.5_Verwijder_afnemerindicatie_02.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'Er bestaat geen geldige afnemerindicatie voor deze persoon binnen de opgegeven leveringsautorisatie.'

Then heeft in het antwoordbericht 'partijCode' in 'verwijderingAfnemerindicatie' de waarde '034301'
Then is in antwoordbericht de aanwezigheid van 'tijdstipRegistratie' in 'verwijderingAfnemerindicatie' nummer 1 ja

Scenario: 5. Als de dienst verwijdering afnemerindicatie wordt aangeroepen om een afnemerindicatie te verwijderen, dan dient de opgegeven afnemerindicatie aanwezig te zijn bij de opgegeven persoon voor de betreffende afnemer en voor het opgegeven leveringsautorisatie.
            De persoon voor wie de afnemerindicatie verwijdert wordt valt niet binnen het abonnement van de afnemende partij
            Logsiche testgevallen: R1401_04, R1403_01, R1404_01
            Verwacht resultaat:  synchroon responsebericht
                Met vulling:
                -  Verwerking = Fout
                -  Melding  =   Er bestaat geen geldige afnemerindicatie voor deze persoon binnen de opgegeven leveringsautorisatie.
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd

Given de personen 299054457, 743274313, 606417801 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 2.7_Verwijder_afnemerindicatie_03.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'Er bestaat geen geldige afnemerindicatie voor deze persoon binnen de opgegeven leveringsautorisatie.'

Then heeft in het antwoordbericht 'partijCode' in 'verwijderingAfnemerindicatie' de waarde '017401'
Then is in antwoordbericht de aanwezigheid van 'tijdstipRegistratie' in 'verwijderingAfnemerindicatie' nummer 1 ja

Scenario: 6. Indien de dienst Plaatsing afnemerindicatie, Verwijdering afnemerindicatie of Synchronisatie persoon wordt aangeroepen, moet het opgegeven burgerservicenummer minstens 1 ingeschreven persoon identificeren
            In deze test is er geen ingeschreven persoon op het gebruikte BSN
            Logsiche testgevallen: R1401_01, R1403_03, R1404_02
            Verwacht resultaat:  synchroon responsebericht
                Met vulling:
                -  Verwerking = Geslaagd
                -  Melding  =   Er is geen persoon geïdentificeerd met het opgegeven burgerservicenummer.
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd

Given de personen 299054457, 743274313, 606417801 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 2.8_Plaats_afnemerindicatie_04.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given de database is aangepast met: update kern.pers set srt=2 where bsn = 606417801

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 2.9_Verwijder_afnemerindicatie_04.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 7. Indien de dienst Plaatsing afnemerindicatie of Verwijdering afnemerindicatie wordt aangeroepen, moet het opgegeven burgerservicenummer precies 1 ingeschreven persoon identificeren, waarbij de nadere bijhoudingsaard ongelijk is aan "Fout" en ongelijk aan "Onbekend".
            In deze test: 2 Ingeschrevenen, beide nadere.bijhouding <> FOUT
            Logsiche testgevallen: R1401_01, R1403_02, R1404_03
            Verwacht resultaat:  synchroon responsebericht
                Met vulling:
                -  Verwerking = Fout
                -  Melding  =   Er is meer dan één persoon gevonden met het opgegeven burgerservicenummer
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd


Given de personen 299054457, 743274313, 606417801 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 2.10_Plaats_afnemerindicatie_05.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given de personen 299054457, 743274313, 215473401 zijn verwijderd
Given de standaardpersoon UC_Kainny met bsn 215473401 en anr 2092843538 zonder extra gebeurtenissen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 2.11_Plaats_afnemerindicatie_05.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given de database is aangepast met: update kern.pers set bsn=606417801 where bsn = 215473401
Given de database is aangepast met: update kern.his_persids set bsn=606417801 where bsn = 215473401

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 2.12_Verwijder_afnemerindicatie_05.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'Er is meer dan één persoon gevonden met het opgegeven burgerservicenummer.'
Then heeft in het antwoordbericht 'partijCode' in 'verwijderingAfnemerindicatie' de waarde '017401'
Then is in antwoordbericht de aanwezigheid van 'tijdstipRegistratie' in 'verwijderingAfnemerindicatie' nummer 1 ja

Scenario: 8. Indien de dienst Plaatsing afnemerindicatie of Verwijdering afnemerindicatie wordt aangeroepen, moet het opgegeven burgerservicenummer precies 1 ingeschreven persoon identificeren, waarbij de nadere bijhoudingsaard ongelijk is aan "Fout" en ongelijk aan "Onbekend".
            In deze test: 1 Ingeschrevene, nadere.bijhouding = FOUT
            Logsiche testgevallen: R1401_01, R1403_01, R1404_05
            Verwacht resultaat:  synchroon responsebericht
                Met vulling:
                -  Verwerking = Fout
                -  Melding  =   ?
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd

Given de personen 299054457, 743274313, 606417801 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 2.13_Plaats_afnemerindicatie_06.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given de database is aangepast met: update kern.his_persbijhouding set naderebijhaard=7 where pers in (select id from kern.pers where bsn = 606417801 )
Given de database is aangepast met: update kern.pers set naderebijhaard=7 where bsn = 606417801

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 2.14_Verwijder_afnemerindicatie_06.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'Er is geen persoon geïdentificeerd met het opgegeven burgerservicenummer.'
Then heeft in het antwoordbericht 'partijCode' in 'verwijderingAfnemerindicatie' de waarde '017401'
Then is in antwoordbericht de aanwezigheid van 'tijdstipRegistratie' in 'verwijderingAfnemerindicatie' nummer 1 ja

Scenario: 9. Indien de dienst Plaatsing afnemerindicatie of Verwijdering afnemerindicatie wordt aangeroepen, moet het opgegeven burgerservicenummer precies 1 ingeschreven persoon identificeren, waarbij de nadere bijhoudingsaard ongelijk is aan "Fout" en ongelijk aan "Onbekend".
            In deze test: 2 Ingeschrevenen, beide nadere.bijhouding = FOUT
            Logsiche testgevallen: R1401_01, R1403_02, R1404_06
            Verwacht resultaat:  synchroon responsebericht
                Met vulling:
                -  Verwerking = Fout
                -  Melding  =   ?
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd

Given de personen 299054457, 743274313, 606417801 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 2.10_Plaats_afnemerindicatie_05.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given de personen 299054457, 743274313, 215473401 zijn verwijderd
Given de standaardpersoon UC_Kainny met bsn 215473401 en anr 2092843538 zonder extra gebeurtenissen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 2.11_Plaats_afnemerindicatie_05.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given de database is aangepast met: update kern.pers set bsn=606417801 where bsn = 215473401
Given de database is aangepast met: update kern.his_persids set bsn=606417801 where bsn = 215473401
Given de database is aangepast met: update kern.his_persbijhouding set naderebijhaard=7 where pers in (select id from kern.pers where bsn = 606417801 )
Given de database is aangepast met: update kern.pers set naderebijhaard=7 where bsn = 606417801

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 2.12_Verwijder_afnemerindicatie_05.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'Er is geen persoon geïdentificeerd met het opgegeven burgerservicenummer.'
Then heeft in het antwoordbericht 'partijCode' in 'verwijderingAfnemerindicatie' de waarde '017401'
Then is in antwoordbericht de aanwezigheid van 'tijdstipRegistratie' in 'verwijderingAfnemerindicatie' nummer 1 ja

Scenario: 10. Indien de dienst Plaatsing afnemerindicatie of Verwijdering afnemerindicatie wordt aangeroepen, moet het opgegeven burgerservicenummer precies 1 ingeschreven persoon identificeren, waarbij de nadere bijhoudingsaard ongelijk is aan "Fout" en ongelijk aan "Onbekend".
            In deze test: 1 Ingeschrevene, nadere.bijhouding = ONBEKEND
            Logsiche testgevallen: R1401_01, R1403_01, R1404_07
            Verwacht resultaat:  synchroon responsebericht
                Met vulling:
                -  Verwerking = Fout
                -  Melding  =   ?
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd

Given de personen 299054457, 743274313, 606417801 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 2.13_Plaats_afnemerindicatie_06.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given de database is aangepast met: update kern.his_persbijhouding set naderebijhaard=8 where pers in (select id from kern.pers where bsn = 606417801 )
Given de database is aangepast met: update kern.pers set naderebijhaard=8 where bsn = 606417801

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 2.14_Verwijder_afnemerindicatie_06.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'Er is geen persoon geïdentificeerd met het opgegeven burgerservicenummer.'
Then heeft in het antwoordbericht 'partijCode' in 'verwijderingAfnemerindicatie' de waarde '017401'
Then is in antwoordbericht de aanwezigheid van 'tijdstipRegistratie' in 'verwijderingAfnemerindicatie' nummer 1 ja

Scenario: 11. Indien de dienst Plaatsing afnemerindicatie of Verwijdering afnemerindicatie wordt aangeroepen, moet het opgegeven burgerservicenummer precies 1 ingeschreven persoon identificeren, waarbij de nadere bijhoudingsaard ongelijk is aan "Fout" en ongelijk aan "Onbekend".
            In deze test: 2 Ingeschrevenen, beide nadere.bijhouding = ONBEKEND
            Logsiche testgevallen: R1401_01, R1403_02, R1404_08
            Verwacht resultaat:  synchroon responsebericht
                Met vulling:
                -  Verwerking = Fout
                -  Melding  =   ?
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd


Given de personen 299054457, 743274313, 606417801 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 2.10_Plaats_afnemerindicatie_05.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given de personen 299054457, 743274313, 215473401 zijn verwijderd
Given de standaardpersoon UC_Kainny met bsn 215473401 en anr 2092843538 zonder extra gebeurtenissen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 2.11_Plaats_afnemerindicatie_05.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given de database is aangepast met: update kern.pers set bsn=606417801 where bsn = 215473401
Given de database is aangepast met: update kern.his_persids set bsn=606417801 where bsn = 215473401
Given de database is aangepast met: update kern.his_persbijhouding set naderebijhaard=8 where pers in (select id from kern.pers where bsn = 606417801 )
Given de database is aangepast met: update kern.pers set naderebijhaard=8 where bsn = 606417801

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 2.14_Verwijder_afnemerindicatie_06.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'Er is geen persoon geïdentificeerd met het opgegeven burgerservicenummer.'
Then heeft in het antwoordbericht 'partijCode' in 'verwijderingAfnemerindicatie' de waarde '017401'
Then is in antwoordbericht de aanwezigheid van 'tijdstipRegistratie' in 'verwijderingAfnemerindicatie' nummer 1 ja

Scenario: R2061. Partij probeert een afnemerindicatie te verwijderen voor een andere partij, Persoon afnemerindicatie.afnemer <>Bericht zendende partij
                    R2061
                    Verwacht Resultaat: Foutmelding: Een afnemer mag alleen voor zichzelf een indicatie onderhouden.
                    Bevinding: Er is een onbekende fout opgetreden probeer later opnieuw

Meta:
@status             Bug

Given leveringsautorisatie uit /levering_autorisaties/modelAutorisaties/model_autorisatie_levering_obv_afn_ind
Given de personen 299054457, 743274313, 606417801 zijn verwijderd
And de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(606417801)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Delft', aanvang: 20100212, registratieDatum: 20100212) {
            naarGemeente 'Delft',
                straat: 'Markt', nummer: 87, postcode: '2611GW', woonplaats: "Delft"
        }
}
slaOp(persoon)

When voor persoon 606417801 wordt de laatste handeling geleverd

Given verzoek voor leveringsautorisatie 'model autorisatie obv afnemerindicatie' en partij 'Gemeente Delft' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand 9.2_Plaats_Afnemerindicatie_10.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd


Given verzoek voor leveringsautorisatie 'model autorisatie obv afnemerindicatie' en partij 'Gemeente Alkmaar' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand afnemerIndicatie_verwijder_r2061.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                        |
| R2061    | Een afnemer mag alleen voor zichzelf een indicatie onderhouden |

Given verzoek voor leveringsautorisatie 'model autorisatie obv afnemerindicatie' en partij 'Gemeente Alkmaar' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand afnemerIndicatie_verwijder_r2061_2.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                        |
| R2061    | Een afnemer mag alleen voor zichzelf een indicatie onderhouden |

Given verzoek voor leveringsautorisatie 'model autorisatie obv afnemerindicatie' en partij 'Gemeente Alkmaar' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand afnemerIndicatie_verwijder_r2061_3.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                        |
| R2061    | Een afnemer mag alleen voor zichzelf een indicatie onderhouden |