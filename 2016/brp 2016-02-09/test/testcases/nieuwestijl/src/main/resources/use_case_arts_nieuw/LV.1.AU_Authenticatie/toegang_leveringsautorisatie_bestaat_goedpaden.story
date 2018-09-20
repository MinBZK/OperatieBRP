Meta:
@sprintnummer           90
@epic                   Autorisatie levering
@auteur                 miuij
@jiraIssue              TEAMBRP-4479, TEAMBRP-4480
@status                 Klaar
@regels                 R1257, R1258, R1261, R1262, R1263, R1263, R1264, R2050, R2052, R2053, R2054, R2055, R2056, R2122, R2121, R2130




Narrative:
Als BRP wil ik dat alleen geautoriseerde afnemers een dienst kunnen bevragen
zodat de levering plaats vind aan alleen geautoriseerde afnemers


Scenario:   R1257_LO1, R1258_L01, R2052_L01, R2056_L01, R2122_L01, R2122_L01 De toegang leveringsautorisatie bestaat, ondertekenaar en transporteur zijn leeg en rol is niet opgegeven,
            R1258_L01 toegangleveringsautorisatie datum ingang kleiner dan systeemdatum, datum einde is groter dan systeemdatum
            R1261_L01 levsautorisatie datum ingang kleiner dan systeemdatum, datum einde is groter dan systeemdatum
            R1262_L01 De gevraagde dienst datum ingang kleiner dan systeemdatum, datum einde is groter dan systeemdatum
            R1263_L01 De opgegeven leveringsautorisatie is niet geblokkeerd
            R1264_L01 De gevraagde dienst is niet geblokkeerd
            R2053_L01 De opgevraagde leveringsautorisatie bestaat
            R2054_L01 De soort van de opgegeven dienst correspondeert met het gebruikte soort bericht
            R2055_L01 De gevraagde dienst bestaat
            R2056_L01 Dienstbundel is niet geblokkeerd
            R2130_L01 De leveringsautorisatie bevat de gevraagde dienst
            Logische testgevallen LV.1.AU: R1257_L01, R1258_L01, R1261_L01, R1262_L01, R1263_L01, R1264_l01, R2053_L01, R2054_L01, R2055_L01, R2056_L01, R2130_l01
            Verwacht resultaat:     1. Valide bericht met verwerking Geslaagd


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given de database is aangepast met: update autaut.toeganglevsautorisatie set datingang='${vandaagsql(0,0,-1)}', dateinde='${vandaagsql(0,0,1)}'
Given de database is aangepast met: update autaut.levsautorisatie set datingang='${vandaagsql(0,0,-1)}', dateinde='${vandaagsql(0,0,1)}' where naam = 'Bewerker autorisatie'
Given de database is aangepast met: update autaut.dienst set datingang='${vandaagsql(0,0,-1)}', dateinde='${vandaagsql(0,0,1)}' where dienstbundel=(select id from autaut.dienstbundel where naam = 'Bewerker_autorisatie')
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Scenario:   R1257_LO2, R2122_L01 De toegang leveringsautorisatie bestaat, ondertekenaar en transporteur zijn leeg en rol is Afnemer,
            Logische testgevallen LV.1.AU: R1257_L02, R2122_L01, R1260_L01
            Verwacht resultaat:     1. Valide bericht met verwerking Geslaagd


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_bvgGeefDetailsPersoon
And testdata uit bestand geefDetailsPersoon_1.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd


Scenario:   R1257_LO3, R1258_L02 De toegang leveringsautorisatie bestaat, ondertekenaar en transporteur zijn gevuld en rol is leeg,
            R1258_L02 toegangleveringsautorisatie datum ingang is gelijk aan systeemdatum, datum einde is groter dan systeemdatum
            R1261_L02 leveringsautorisatie datum ingang is gelijk aan systeemdatum, datum einde is groter dan systeemdatum
            R1262_L02 De gevraagde dienst is geldig, datum ingang is gelijk aan systeemdatum, datum einde is groter dan systeemdatum
            Logische testgevallen LV.1.AU: R1257_L03, R1258_L02, R1261_L02, R1262_L02
            Verwacht resultaat:     1. Valide bericht met verwerking Geslaagd


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given de database is aangepast met: update autaut.toeganglevsautorisatie set datingang='${vandaagsql(0,0,0)}', dateinde='${vandaagsql(0,0,1)}'
Given de database is aangepast met:  update autaut.toeganglevsautorisatie set ondertekenaar='396', transporteur='396' where geautoriseerde = 3248
Given de database is aangepast met: update autaut.levsautorisatie set datingang='${vandaagsql(0,0,0)}', dateinde='${vandaagsql(0,0,1)}'
Given de database is aangepast met: update autaut.dienst set datingang='${vandaagsql(0,0,0)}', dateinde='${vandaagsql(0,0,1)}' where dienstbundel=(select id from autaut.dienstbundel where naam = 'Bewerker_autorisatie')
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd


Scenario:   R1257_LO4, R1258_L03 De toegang leveringsautorisatie bestaat, ondertekenaar en transporteur zijn gevuld en ongelijk aan elkaar en rol is Afnemer
            R1258_L03 toegangleveringsautorisatie datum ingang is kleiner dan systeemdatum, datum einde is leeg
            R1261_L03 levsautorisatie datum ingang is kleiner dan systeemdatum, datum einde is leeg
            R1262_L03 De gevraagde dienst is geldig, datum ingang is kleiner dan systeemdatum, datum einde is leeg
            Logische testgevallen LV.1.AU: R1257_L04, R1258_L03, R1261_L03, R1262_L03
            Verwacht resultaat:     1. Valide bericht met verwerking Geslaagd


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given de database is aangepast met: update autaut.toeganglevsautorisatie set datingang='${vandaagsql(0,0,-1)}'
Given de database is aangepast met:  update autaut.toeganglevsautorisatie set ondertekenaar='396', transporteur='178' where geautoriseerde = 3248
Given de database is aangepast met: update autaut.levsautorisatie set datingang='${vandaagsql(0,0,-1)}', dateinde=null
Given de database is aangepast met: update autaut.dienst set datingang='${vandaagsql(0,0,-1)}', dateinde=null where dienstbundel=(select id from autaut.dienstbundel where naam = 'Bewerker_autorisatie')
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht' met ondertekenaar 00000001001005650000 en transporteur 00000001001932603000
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd