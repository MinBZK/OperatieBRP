Meta:
@auteur             kedon
@status             Klaar
@usecase            SA.0.VA, SA.1.VA, SA.1.VA.CI, LV.1.VA, LV.0.AV, LV.1.AV, LV.1.AB, LV.1.XV, LV.1.AU, LV.1.AL, LV.1.MR, LV.1.VB, LV1.AB
@regels             R1401,R1403,R1404,R1409,R2061
@sleutelwoorden     Verwijdering afnemerindicatie, Verwijder afnemerindicatie, Controleer inhoud, Verwijder afnemerindicatie, Afhandeling verzoek, Afhandelen verzoek, Archiveer bericht, XSD validatie, Authenticatie, Autorisatie levering, Maak responsebericht, Verstuur bericht, Archiveer bericht

Narrative:
Afnemerindicatie voor de opgegeven persoon moet bestaan.
Er moet een persoon bestaan met het opgegeven burgerservicenummer.
Plaatsen of verwijderen afnemerindicatie mag alleen op uniek burgerservicenummer.
Verstrek resultaatbericht bij geslaagd plaatsen of verwijderen van afnemerindicatie

Scenario: 1.Voor de persoon wordt een afnemeridicatie geplaatst en vervolgens verwijderd, door dezelfde partij, met hetzelfde abonnement, voor dezelfde persoon
            Logische testgevallen SA.1.VA Verwijder Afnemerindicatie: R1401_01,R1403_01,R1404_01,R1409_01,R2061_03
            Logische testgevallen LV.1.MB Maak BRP bericht LV.1.MB: R1267_02
            Verwacht resultaat: synchroon responsebericht
                Met vulling:
                -  Verwerking = geslaagd
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd

Given de personen 299054457, 743274313, 606417801 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 1.2_Plaats_afnemerindicatie_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 1.3_Verwijder_afnemerindicatie_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waarde '199903'
Then heeft in het antwoordbericht 'zendendeSysteem' in 'stuurgegevens' de waarde 'BRP'
Then is in antwoordbericht de aanwezigheid van 'referentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'crossReferentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'tijdstipVerzending' in 'stuurgegevens' nummer 1 ja

Then heeft in het antwoordbericht 'verwerking' in 'resultaat' de waarde 'Geslaagd'
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'

Then in kern select bsn from kern.pers where id=(select pers from autaut.persafnemerindicatie where id=(select pe.persafnemerindicatie from autaut.his_persafnemerindicatie pe where dienstverval=2)) de volgende gegevens:
| veld                 | waarde     |
| bsn                  | 606417801  |

Then heeft in het antwoordbericht 'partijCode' in 'verwijderingAfnemerindicatie' de waarde '017401'
Then is in antwoordbericht de aanwezigheid van 'tijdstipRegistratie' in 'verwijderingAfnemerindicatie' nummer 1 ja
Then heeft in het antwoordbericht 'burgerservicenummer' in 'verwijderingAfnemerindicatie' de waarde '606417801'

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 2.    Verwijderen afnemerindicatie, met 2 personen op 1 BSN
                Nadere.bijhoudingsaard van 1 persoon op = FOUT zetten.
                Selectie op basis van nadere.bijhoudingsaard zou er voor moeten zorgen dat er 1 geldige persoon overblijft
                Voor deze persoon moet de afnemerindicatie verwijdert worden.
                    Logische testgevallen SA.1.VA Verwijder Afnemerindicatie: R1401_01, R1403_02, R1404_04, R1409_01, R2061_03
                    Logische testgevallen LV.1.MB Maak BRP bericht LV.1.MB: R1267_02
                    Verwacht resultaat: synchroon responsebericht
                                    Met vulling:
                                    -  Verwerking = geslaagd
                                    -  Persoon = De betreffende Persoon uit het bericht
                                    -  Afnemer = De Partij waarvoor de Dienst wordt geleverd

Given de personen 299054457, 743274313, 606417801 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 1.4_Plaats_afnemerindicatie_persoon_1.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given de personen 299054457, 743274313, 215473401 zijn verwijderd
Given de standaardpersoon UC_Kainny met bsn 215473401 en anr 2092843538 zonder extra gebeurtenissen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 1.5_Plaats_afnemerindicatie_persoon_2.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given de database is aangepast met: update kern.his_persbijhouding set naderebijhaard=7 where pers in (select id from kern.pers where bsn = 215473401 )
Given de database is aangepast met: update kern.pers set naderebijhaard=7 where bsn = 215473401
Given de database is aangepast met: update kern.pers set bsn=606417801 where bsn = 215473401
Given de database is aangepast met: update kern.his_persids set bsn=606417801 where bsn = 215473401

Given de cache is herladen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 1.6_Verwijder_afnemerindicatie_persoon_1_2.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'
Then heeft in het antwoordbericht 'partijCode' in 'verwijderingAfnemerindicatie' de waarde '017401'

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide