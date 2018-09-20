Meta:
@sprintnummer           90
@epic                   Autorisatie levering
@auteur                 miuij
@jiraIssue              TEAMBRP-4479, TEAMBRP-4479
@status                 Klaar
@regels                 R1257,R2050, R2058, R2052, R2121, R2122, R2120




Narrative:
Als BRP wil ik dat alleen geautoriseerde afnemers een dienst kunnen bevragen
zodat de levering plaats vind aan alleen geautoriseerde afnemers

Scenario:   R2121_LO3, De toegang leveringsautorisatie bestaat, ondertekenaar is onjuist en transporteur is opgegeven en rol is niet opgegeven,
            Logische testgevallen LV.1.AU: R2121_LO3
            Verwacht resultaat:     1. Foutief bericht met de melding "Authenticatie: Ondertekenaar onjuist"


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given de database is aangepast met:  update autaut.toeganglevsautorisatie set ondertekenaar='396', transporteur='396' where geautoriseerde = 3248
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht' met ondertekenaar 00000001001569417000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING
| R2121    | Authenticatie: Ondertekenaar onjuist


Scenario:   R2122_LO2, De toegang leveringsautorisatie bestaat, ondertekenaar is opgegeven en transporteur is onjuist en rol is niet opgegeven,
            Logische testgevallen LV.1.AU: R2122_LO2
            Verwacht resultaat:     1. Foutief bericht met de melding "Authenticatie: Transporteur onjuist"

Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given de database is aangepast met:  update autaut.toeganglevsautorisatie set ondertekenaar='396', transporteur='396' where geautoriseerde = 3248
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht' met ondertekenaar 00000001001005650000 en transporteur 00000001001569417000
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING
| R2122    | Authenticatie: Transporteur onjuist


Scenario:   R2120_L03, De toegang leveringsautorisatie bestaat, ondertekenaar is onjuist en transporteur is onjuist en rol is niet opgegeven,
            Logische testgevallen LV.1.AU: R2120_L03
            Verwacht resultaat:     1. Foutief bericht met de melding "De gebruikte authenticatie is niet bekend"


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given de database is aangepast met:  update autaut.toeganglevsautorisatie set ondertekenaar='396', transporteur='396' where geautoriseerde = 3248
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_03.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING
| R2120    | De gebruikte authenticatie is niet bekend


Scenario:   R2120_LO4, De toegang leveringsautorisatie bestaat, ondertekenaar en transporteur zijn gevuld en rol is foutief,
            Logische testgevallen LV.1.AU: R2120_LO4
            Verwacht resultaat:     1. Foutief bericht met de melding: "De gebruikte authenticatie is niet bekend"


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given de database is aangepast met:  update autaut.toeganglevsautorisatie set ondertekenaar='396', transporteur='396' where geautoriseerde = 3248
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_bvgGeefDetailsPersoon
And testdata uit bestand geefDetailsPersoon_2.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING
| R2120    | De gebruikte authenticatie is niet bekend


Scenario:   R2120_L05, De toegang leveringsautorisatie bestaat niet, ondertekenaar en transporteur zijn gevuld en rol is leeg,
            Logische testgevallen LV.1.AU: R2120_L05
            Verwacht resultaat:     1. Foutief bericht met de melding: "De gebruikte authenticatie is niet bekend"


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given de database is aangepast met:  update autaut.toeganglevsautorisatie set ondertekenaar='396', transporteur='396' where geautoriseerde = 3248
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_bvgGeefDetailsPersoon
And testdata uit bestand geefDetailsPersoon_3.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING
| R2120    | De gebruikte authenticatie is niet bekend


Scenario:   R1258_LO4,De toegang leveringsautorisatie bestaat, ondertekenaar en transporteur zijn leeg en rol is niet opgegeven,
            toegangleveringsautorisatie datum ingang is leeg, datum einde is groter dan systeemdatum
            Logische testgevallen LV.1.AU: R1258_L04
            Verwacht resultaat:     1. Foutief bericht met de melding: "De toegang autorisatie is niet geldig"


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given de database is aangepast met: update autaut.toeganglevsautorisatie set datingang=NULL, dateinde='${vandaagsql(0,0,1)}'
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING
| R1258    | De toegang autorisatie is niet geldig


Scenario:   R1258_LO5,De toegang leveringsautorisatie bestaat, ondertekenaar en transporteur zijn leeg en rol is niet opgegeven,
            toegangleveringsautorisatie datum ingang is groter dan systeemdatum, datum einde is groter dan systeemdatum
            Logische testgevallen LV.1.AU: R1258_L05
            Verwacht resultaat:     1. Foutief bericht met de melding: "De toegang autorisatie is niet geldig"


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given de database is aangepast met: update autaut.toeganglevsautorisatie set datingang='${vandaagsql(0,0,1)}', dateinde='${vandaagsql(0,0,2)}'
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING
| R1258    | De toegang autorisatie is niet geldig


Scenario:   R1258_LO6,De toegang leveringsautorisatie bestaat, ondertekenaar en transporteur zijn leeg en rol is niet opgegeven,
            toegangleveringsautorisatie datum ingang is kleiner dan systeemdatum, datum einde is gelijk aan de systeemdatum
            Logische testgevallen LV.1.AU: R1258_L06
            Verwacht resultaat:     1. Foutief bericht met de melding: "De toegang autorisatie is niet geldig"


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given de database is aangepast met: update autaut.toeganglevsautorisatie set datingang='${vandaagsql(0,0,-1)}', dateinde='${vandaagsql(0,0,0)}'
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING
| R1258    | De toegang autorisatie is niet geldig


Scenario:   R1258_LO7,De toegang leveringsautorisatie bestaat, ondertekenaar en transporteur zijn leeg en rol is niet opgegeven,
            toegangleveringsautorisatie datum ingang is kleiner dan systeemdatum, datum einde is kleiner dan de systeemdatum
            Logische testgevallen LV.1.AU: R1258_L07
            Verwacht resultaat:     1. Foutief bericht met de melding: "De toegang autorisatie is niet geldig"


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given de database is aangepast met: update autaut.toeganglevsautorisatie set datingang='${vandaagsql(0,0,-2)}', dateinde='${vandaagsql(0,0,-1)}'
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING
| R1258    | De toegang autorisatie is niet geldig


Scenario:   R2052_LO2,De toegang leveringsautorisatie bestaat, ondertekenaar en transporteur zijn leeg en rol is niet opgegeven,
           Toegangsleveringsautorisatie indgeblokkeerd is true
           Logische testgevallen LV.1.AU: R2052_:02
            Verwacht resultaat:     1. Foutief bericht met de melding: "De toegang autorisatie is geblokkeerd door de beheerder"


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given de database is aangepast met: update autaut.toeganglevsautorisatie set indblok=true
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING
| R2052    | De toegang autorisatie is geblokkeerd door de beheerder


Scenario:  R1257_L05 Authenticatie Combinatie ondertekenaar en transporteur onjuist.
           Logische testgevallen LV.1.AU: R1257_L05
           Verwacht resultaat:     1. Foutief bericht met de melding: "Authenticatie: Combinatie ondertekenaar en transporteur onjuist"


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/modelAutorisaties/model_autorisatie_bevraging_meerdere_bij_partij
Given de database is aangepast met: update autaut.toeganglevsautorisatie set datingang='${vandaagsql(0,0,-1)}', dateinde='${vandaagsql(0,0,1)}'
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'model autorisatie voor bevraging' en partij 'Gemeente Delft' met ondertekenaar 00000001001932603000 en transporteur 00000001002220647000
Given verzoek van bericht lvg_bvgGeefDetailsPersoon
And testdata uit bestand geefDetailsPersoon_4.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING
| R1257 | Authenticatie: Combinatie ondertekenaar en transporteur onjuist
