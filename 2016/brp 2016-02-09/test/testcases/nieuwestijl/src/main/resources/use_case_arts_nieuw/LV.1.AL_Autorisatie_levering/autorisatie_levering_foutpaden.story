Meta:
@sprintnummer           91
@epic                   Authenticatie en autorisatie
@auteur                 miuib
@jiraIssue              TEAMBRP-4511
@status                 Klaar
@regels                 R1260, R1261, R1262, R1263, R1264, R2054, R2053, R2055, R2056, R2130



Narrative:
Als BRP wil ik dat afnemers alleen een dienst kunnen bevragen waarvoor een leveringsautorisatie is
zodat de levering plaats vind aan alleen geautoriseerde afnemers


Scenario:   1. R2054_L02, De soort van de opgegeven dienst correspondeert niet met het gebruikte soort bericht
            Logische testgevallen LV.1.AL R2054_L02
            Verwacht resultaat:     1. Foutief bericht met de melding "De opgegeven dienst komt niet overeen met het soort bericht"
            Let op omdat de dienstid dynamisch is dit scenario als eerste laten staan, dienstid 1 wordt gebruikt en moet in de levsautorisatie zitten


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht'
Given de database is aangepast met: update autaut.dienst set id=4445 where srt=1 and dienstbundel=(select id from autaut.dienstbundel where naam = 'Bewerker autorisatie')
Given de cache is herladen
Given verzoek van bericht lvg_bvgGeefDetailsPersoon
And testdata uit bestand geefDetailsPersoon_ongeldige_dienstId.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                        |
| R2054    | De opgegeven dienst komt niet overeen met het soort bericht    |


Scenario:   2. R2130_L02, Leveringsautorisatie bevat dienst niet.
            Logische testgevallen LV.1.AL R2130_L02
            Verwacht resultaat:     1. Foutief bericht met de melding "De opgeven leveringsautorisatie bevat de gevraagde dienst niet"


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/ongeldige_autorisaties/Leverings_autorisatie_bevat_dienst_niet
Given verzoek voor leveringsautorisatie 'Leverings autorisatie bevat dienst niet' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synGeefSynchronisatiePersoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                |
| R2130    | De leveringsautorisatie bevat de gevraagde dienst niet |


Scenario:   3. R1261_L04, Leveringsautorisatie is niet geldig, datumingang ligt na de systeemdatum
            Logische testgevallen LV.1.AL R1261_L04
            Verwacht resultaat:     1. Foutief bericht met de melding "De opgegeven leveringsautorisatie is niet geldig"


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given de database is aangepast met: update autaut.levsautorisatie set datingang='${vandaagsql(0,0,1)}' where naam ='Bewerker autorisatie'
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synGeefSynchronisatiePersoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R1261    | De opgegeven leveringsautorisatie is niet geldig   |


Scenario:   4. R1261_L05, Leveringsautorisatie is niet geldig, datumeinde is gelijk aan de systeemdatum
            Logische testgevallen LV.1.AL R1261_L05
            Verwacht resultaat:     1. Foutief bericht met de melding "De opgegeven leveringsautorisatie is niet geldig"


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given de database is aangepast met: update autaut.levsautorisatie set datingang='${vandaagsql(0,0,-1)}', dateinde='${vandaagsql(0,0,0)}' where naam ='Bewerker autorisatie'
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synGeefSynchronisatiePersoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R1261    | De opgegeven leveringsautorisatie is niet geldig   |


Scenario:   5. R1261_L06, Leveringsautorisatie is niet geldig, datumeinde is kleiner dan de systeemdatum
            Logische testgevallen LV.1.AL R1261_L06
            Verwacht resultaat:     1. Foutief bericht met de melding "De opgegeven leveringsautorisatie is niet geldig"


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given de database is aangepast met: update autaut.levsautorisatie set datingang='${vandaagsql(0,0,-1)}', dateinde='${vandaagsql(0,0,-1)}' where naam ='Bewerker autorisatie'
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synGeefSynchronisatiePersoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R1261    | De opgegeven leveringsautorisatie is niet geldig   |


Scenario:   6. R1262_L04, De gevraagde dienst is niet geldig, datumingang is groter dan de systeemdatum
            Logische testgevallen LV.1.AL R1262_L04
            Verwacht resultaat:     1. Foutief bericht met de melding "De gevraagde dienst is niet geldig"


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given de database is aangepast met: update autaut.dienst set datingang='${vandaagsql(0,0,1)}', dateinde='${vandaagsql(0,0,2)}' where srt =9
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synGeefSynchronisatiePersoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R1262    | De gevraagde dienst is niet geldig                 |


Scenario:   7. R1262_L05, De gevraagde dienst is niet geldig, datumeinde is gelijk aan de systeemdatum
            Logische testgevallen LV.1.AL R1262_L05
            Verwacht resultaat:     1. Foutief bericht met de melding "De gevraagde dienst is niet geldig"


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given de database is aangepast met: update autaut.dienst set datingang='${vandaagsql(0,0,-1)}', dateinde='${vandaagsql(0,0,0)}' where srt =9
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synGeefSynchronisatiePersoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R1262    | De gevraagde dienst is niet geldig                 |


Scenario:   8. R1262_L06, De gevraagde dienst is niet geldig, datumeinde is kleiner dan de systeemdatum
            Logische testgevallen LV.1.AL R1262_L06
            Verwacht resultaat:     1. Foutief bericht met de melding "De gevraagde dienst is niet geldig"


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given de database is aangepast met: update autaut.dienst set datingang='${vandaagsql(0,0,-2)}', dateinde='${vandaagsql(0,0,-1)}' where srt =9
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synGeefSynchronisatiePersoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R1262    | De gevraagde dienst is niet geldig                 |



Scenario:   9. R1263_L02, De opgegeven leveringsautorisatie is geblokkeerd
            Logische testgevallen LV.1.AL R1263_L02
            Verwacht resultaat:     1. Foutief bericht met de melding "De opgegeven leveringsautorisatie is geblokkeerd door de beheerder"


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given de database is aangepast met: update autaut.levsautorisatie set indblok=true
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synGeefSynchronisatiePersoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                             |
| R1263    | De opgegeven leveringsautorisatie is geblokkeerd door de beheerder  |


Scenario:   10. R1264_L02, De gevraagde dienst is geblokkeerd
            Logische testgevallen LV.1.AL R1264_L02
            Verwacht resultaat:     1. Foutief bericht met de melding "De gevraagde dienst is geblokkeerd door de beheerder"


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given de database is aangepast met: update autaut.dienst set indblok=true where srt=9
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synGeefSynchronisatiePersoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                               |
| R1264    | De gevraagde dienst is geblokkeerd door de beheerder  |


Scenario:   11. R2053_L02, De opgegeven leveringsautorisatie bestaat niet
            Logische testgevallen LV.1.AL R2053_L02
            Verwacht resultaat:     1. Foutief bericht met de melding "De opgegeven leveringsautorisatie bestaat niet"


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synGeefSynchronisatiePersoon_ongeldige_levsautorisatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                         |
| R2053    | De opgegeven leveringsautorisatie bestaat niet  |


Scenario:   12. R2055_L02, De gevraagde dienst bestaat niet
            Logische testgevallen LV.1.AL R2055_L02
            Verwacht resultaat:     1. Foutief bericht met de melding "De gevraagde dienst bestaat niet"


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_bvgGeefDetailsPersoon
And testdata uit bestand geefDetailsPersoon_nietbestaande_dienstId.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                            |
| R2055    | De gevraagde dienst bestaat niet   |


Scenario:   13. R2056_L02, De dienstenbundel is geblokkeerd
            Logische testgevallen LV.1.AL R2056_L02
            Verwacht resultaat:     1. Foutief bericht met de melding "De dienstbundel van de opgegeven dienst is geblokkeerd door de beheerder"


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/Bewerker_autorisatie
Given de database is aangepast met: update autaut.dienstbundel set indblok=true
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synGeefSynchronisatiePersoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                    |
| R2056    | De dienstbundel van de opgegeven dienst is geblokkeerd door de beheerder   |



Scenario:   14. R1260_L02, Leveringsautorisatie bevat dienst niet.
            Logische testgevallen LV.1.AL R1260_L02
            Om foutgeval te creeren worden 2 leveringsautorisaties ingelezen, vervolgens wordt de dienst geefDetailsPersoon geupdate naar een andere leveringsautorisatie
            anders kan de dsl het bericht niet samenstellen en niet een juist dienstid meegeven in het request.
            Verwacht resultaat:     1. Foutief bericht met de melding "De opgeven leveringsautorisatie bevat niet de opgegeven dienst"


Given de personen 299054457, 743274313, 622389609 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 622389609 en anr 4151625234 zonder extra gebeurtenissen
Given leveringsautorisatie uit /levering_autorisaties/ongeldige_autorisaties/Leverings_autorisatie_bevat_dienst_niet, /levering_autorisaties/ongeldige_autorisaties/Leverings_aturoisatie_bevat_dienst_niet2
Given verzoek voor leveringsautorisatie 'Leverings autorisatie bevat dienst niet' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_bvgGeefDetailsPersoon
And testdata uit bestand geefDetailsPersoon_2.yml
Given de database is aangepast met: update autaut.dienst set dienstbundel=(select id from autaut.dienstbundel where naam = 'Leverings autorisatie2') where srt=8 and dienstbundel=(select id from autaut.dienstbundel where naam = 'Leverings autorisatie bevat dienst niet')
Given de cache is herladen
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                        |
| R1260    | De opgeven leveringsautorisatie bevat niet de opgegeven dienst |
