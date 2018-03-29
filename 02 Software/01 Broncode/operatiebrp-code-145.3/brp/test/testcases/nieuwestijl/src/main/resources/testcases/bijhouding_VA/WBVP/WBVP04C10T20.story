Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: Verwerking Wijziging Bijzondere Verblijfsrechtelijke Positie

Scenario: Beëindiging Bijzondere Verblijfsrechtelijke Positie van niet-Nederlander
          LT: WBVP04C10T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-WBVP/WBVP04C10T20.xls

When voer een bijhouding uit WBVP04C10T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/WBVP/expected/WBVP04C10T20.xml voor expressie //brp:bhg_vbaRegistreerVerblijfsrecht_R

Then in kern heeft select count(*) from kern.his_persafgeleidadministrati where actieverval is null and actieinh in (select id from kern.actie where srt=3) and actieinh in (select actieverval from kern.his_persafgeleidadministrati where actieinh in (select id from kern.actie where srt=7)) de volgende gegevens:
| veld  | waarde |
| count |      1 |

Then in kern heeft select count(*) from kern.his_persbijhouding de volgende gegevens:
| veld  | waarde |
| count |      1 |

Then in kern heeft select count(*),sa.naam from kern.his_persindicatie i join kern.actie a on i.actieverval=a.id join kern.srtactie sa on a.srt=sa.id where waarde='t' and persindicatie in (select id from kern.persindicatie where srt=8 and pers in (select id from kern.pers where bsn='916838985')) group by sa.naam de volgende gegevens:
| veld  | waarde                                              |
| count |                                                   1 |
| naam  | Beëindiging bijzondere verblijfsrechtelijke positie |

Then in kern heeft select count(*) from kern.actie where srt=3 and admhnd in (select id from kern.admhnd where srt=63) de volgende gegevens:
| veld  | waarde |
| count |      1 |