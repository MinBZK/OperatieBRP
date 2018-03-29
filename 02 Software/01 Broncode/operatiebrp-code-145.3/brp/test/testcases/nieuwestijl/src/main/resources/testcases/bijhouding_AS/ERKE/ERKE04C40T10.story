Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Erkenning: Nevenactie Beëindiging nationaliteit

Scenario:   Beëindiging nationaliteit via "Erkenning"
            LT: ERKE04C40T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE04C40T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE04C40T10-002.xls

When voer een bijhouding uit ERKE04C40T10a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.bsn in ('518089769') and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.bsn in ('518089769') and r.srt=3
Then heeft $NATI_ID$ de waarde van de volgende query: select n.id from kern.persnation n join kern.pers p on p.id = n.pers where p.bsn = '518089769'


When voer een bijhouding uit ERKE04C40T10b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

!-- Controleer verantwoordingsgegevens van de ActieBron
Then in kern heeft select count(1),ab.doc
                   from kern.actiebron ab join kern.actie ainh on ainh.id = ab.actie left join kern.srtactie sainh on ainh.srt = sainh.id
                   where sainh.naam in ('Registratie ouder') group by ab.doc de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |

!-- Controleer betrokkenheid NOUWKIG
Then in kern heeft select sb.naam as soortrol, sp.naam as srtpersoon, p.voornamen
                   from kern.betr b
                   left outer join kern.pers p on b.pers = p.id
                   left outer join kern.srtbetr sb on sb.id = b.rol
                   left outer join kern.srtpers sp on sp.id = p.srt
                   left outer join kern.relatie re on re.id = b.relatie
                   left outer join kern.his_relatie hre on hre.relatie = re.id
                   left outer join kern.actie a on a.id = hre.actieinh
                   left outer join kern.srtactie sa on sa.id = a.srt
                   where b.relatie in (select relatie from kern.betr where pers in (select id from kern.pers where bsn='518089769'))
                   order by p.voornamen de volgende gegevens:
| veld                      | waarde                                        |
| soortrol                  | Ouder                                         |
| srtpersoon                | Ingeschrevene                                 |
| voornamen                 | Henk                                          |
----
| soortrol                  | Kind                                          |
| srtpersoon                | Ingeschrevene                                 |
| voornamen                 | Jan                                           |
----
| soortrol                  | Ouder                                         |
| srtpersoon                | Ingeschrevene                                 |
| voornamen                 | Marie                                         |

!-- Controleer datum aanvang geldigheid ouderschap OUWKIG en NOUWKIG
Then in kern heeft select oud.dataanvgel, sa.naam, p.voornamen, oud.indouderuitwiekindisgeboren
                   from kern.his_ouderouderschap oud
                   left outer join kern.betr b on b.id = oud.betr
                   left outer join kern.pers p on p.id = b.pers
                   left outer join kern.actie a on a.id = oud.actieinh
                   left outer join kern.srtactie sa on sa.id = a.srt
                   where sa.naam ='Registratie geborene'
                   or sa.naam ='Registratie ouder'
                   order by p.voornamen de volgende gegevens:
| veld                          | waarde                                        |
| dataanvgel                    | 20160102                                      |
| naam                          | Registratie ouder                             |
| voornamen                     | Henk                                          |
| indouderuitwiekindisgeboren   | NULL                                          |
----
| dataanvgel                    | 20160101                                      |
| naam                          | Registratie geborene                          |
| voornamen                     | Marie                                         |
| indouderuitwiekindisgeboren   | true                                          |

!-- Controleer beëindigen nationaliteit.
Then in kern heeft select sainh.naam as actieInhoud, saav.naam as actieVerval, CASE WHEN hpn.tsverval IS NULL THEN 'Leeg' ELSE 'Gevuld' END AS tsverval, hpn.dataanvgel, hpn.dateindegel,
                   saaa.naam as actieAanp, n.code
                   from kern.his_persnation hpn
                   left join kern.persnation pn on pn.id = hpn.persnation
                   left join kern.pers p on p.id = pn.pers
                   left join kern.actie ainh on ainh.id = hpn.actieinh
                   left join kern.actie av on av.id = hpn.actieverval
                   left join kern.actie aa on aa.id = hpn.actieaanpgel
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left join kern.srtactie saaa on aa.srt = saaa.id
                   left join kern.nation n on pn.nation = n.id
                   where p.bsn ='518089769'
                   order by hpn.dataanvgel, saav.naam de volgende gegevens:
| veld                | waarde                               |
| actieinhoud         | Registratie nationaliteit            |
| actieverval         | Beëindiging nationaliteit            |
| tsverval            | Gevuld                               |
| dataanvgel          | 20160101                             |
| dateindegel         | NULL                                 |
| actieaanp           | NULL                                 |
| code                | 0028                                 |
----
| actieinhoud         | Registratie nationaliteit            |
| actieverval         | NULL                                 |
| tsverval            | Leeg                                 |
| dataanvgel          | 20160101                             |
| dateindegel         | 20160102                             |
| actieaanp           | Beëindiging nationaliteit            |
| code                | 0028                                 |
----
| actieinhoud         | Registratie nationaliteit            |
| actieverval         | NULL                                 |
| tsverval            | Leeg                                 |
| dataanvgel          | 20160102                             |
| dateindegel         | NULL                                 |
| actieaanp           | NULL                                 |
| code                | 0328                                 |