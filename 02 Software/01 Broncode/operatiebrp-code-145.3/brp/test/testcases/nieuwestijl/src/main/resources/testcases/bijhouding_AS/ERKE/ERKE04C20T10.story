Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Erkenning: Nevenactie Registratie geslachtsnaam voornaam 

Scenario:   Erkenning zorgt ervoor dat de voornamen wordt opgenomen (door namenreeks)
            LT: ERKE04C20T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE04C20T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE04C20T10-002.xls

When voer een bijhouding uit ERKE04C20T10a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.bsn in ('142856721') and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.bsn in ('142856721') and r.srt=3

When voer een bijhouding uit ERKE04C20T10b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/ERKE/expected/ERKE04C20T10.xml voor expressie /

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
                   where b.relatie in (select relatie from kern.betr where pers in (select id from kern.pers where bsn='142856721'))
                   order by p.voornamen de volgende gegevens:
| veld                      | waarde                                        |
| soortrol                  | Ouder                                         |
| srtpersoon                | Ingeschrevene                                 |
| voornamen                 | Henk                                          |
----
| soortrol                  | Kind                                          |
| srtpersoon                | Ingeschrevene                                 |
| voornamen                 | Kees Koos                                     |
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

!-- Controleer persoon samengesteldenaam indicatie namenreeks
Then in kern heeft select sm.indnreeks
                   from kern.his_perssamengesteldenaam sm
                   left outer join kern.actie a on a.id = sm.actieinh
                   left outer join kern.srtactie sa on sa.id = a.srt
                   left outer join kern.pers pe on pe.id = sm.pers
                   where sa.naam = 'Registratie geslachtsnaam/voornaam'
		           and  pe.geslnaamstam = 'Jansens' de volgende gegevens:
| veld      | waarde |
| indnreeks | false  |

!-- Controleer dat de voornamen zijn opgenomen
Then in kern heeft select sainh.naam as actieInhoud, saav.naam as actieVerval, hpv.dataanvgel, hpv.dateindegel, pv.volgnr, hpv.naam, saaa.naam as actieAanp
                   from kern.his_persvoornaam hpv
                   left join kern.persvoornaam pv on pv.id = hpv.persvoornaam
                   left join kern.pers p on p.id = pv.pers
                   left join kern.actie ainh on ainh.id = hpv.actieinh
                   left join kern.actie av on av.id = hpv.actieverval
                   left join kern.actie aa on aa.id = hpv.actieaanpgel
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left join kern.srtactie saaa on aa.srt = saaa.id
                   where p.bsn ='142856721'
                   order by hpv.naam de volgende gegevens:
| veld          | waarde                               |
| actieinhoud   | Registratie geslachtsnaam/voornaam   |
| actieverval   | NULL                                 |
| dataanvgel    | 20160102                             |
| dateindegel   | NULL                                 |
| volgnr        | 1                                    |
| naam          | Kees                                 |
| actieAanp     | NULL                                 |
----
| actieinhoud   | Registratie geslachtsnaam/voornaam   |
| actieverval   | NULL                                 |
| dataanvgel    | 20160102                             |
| dateindegel   | NULL                                 |
| volgnr        | 2                                    |
| naam          | Koos                                 |
| actieAanp     | NULL                                 |

!-- Controleer dat persgeslachtsnaamcomponent is bijgewerkt op Materiële Historie
Then in kern heeft select sainh.naam as actieInhoud, saav.naam as actieVerval, hpg.dataanvgel, hpg.dateindegel, saaa.naam as actieAanp, hpg.predicaat, hpg.adellijketitel, hpg.voorvoegsel,
                   hpg.scheidingsteken, hpg.stam
                   from kern.his_persgeslnaamcomp hpg
                   left join kern.persgeslnaamcomp pg on pg.id = hpg.persgeslnaamcomp
                   left join kern.pers p on p.id = pg.pers
                   left join kern.actie ainh on ainh.id = hpg.actieinh
                   left join kern.actie av on av.id = hpg.actieverval
                   left join kern.actie aa on aa.id = hpg.actieaanpgel
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left join kern.srtactie saaa on aa.srt = saaa.id
                   where p.bsn ='142856721'
                   and hpg.tsverval is NULL
                   order by hpg.dataanvgel de volgende gegevens:
| veld              | waarde                               |
| actieinhoud       | Registratie geborene                 |
| actieverval       | NULL                                 |
| dataanvgel        | 20160101                             |
| dateindegel       | 20160102                             |
| actieAanp         | Registratie geslachtsnaam/voornaam   |
| predicaat         | NULL                                 |
| adellijketitel    | NULL                                 |
| voorvoegsel       | NULL                                 |
| scheidingsteken   | NULL                                 |
| stam              | Jansen                               |
----
| actieinhoud       | Registratie geslachtsnaam/voornaam   |
| actieverval       | NULL                                 |
| dataanvgel        | 20160102                             |
| dateindegel       | NULL                                 |
| actieAanp         | NULL                                 |
| predicaat         | NULL                                 |
| adellijketitel    | 1                                    |
| voorvoegsel       | l                                    |
| scheidingsteken   | '                                    |
| stam              | Jansens                              |

!-- Controleer dat persgeslachtsnaamcomponent is bijgewerkt op Formele Historie
Then in kern heeft select sainh.naam as actieInhoud, saav.naam as actieVerval, hpg.dataanvgel, hpg.dateindegel, saaa.naam as actieAanp, hpg.predicaat, hpg.adellijketitel, hpg.voorvoegsel,
                   hpg.scheidingsteken, hpg.stam
                   from kern.his_persgeslnaamcomp hpg
                   left join kern.persgeslnaamcomp pg on pg.id = hpg.persgeslnaamcomp
                   left join kern.pers p on p.id = pg.pers
                   left join kern.actie ainh on ainh.id = hpg.actieinh
                   left join kern.actie av on av.id = hpg.actieverval
                   left join kern.actie aa on aa.id = hpg.actieaanpgel
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left join kern.srtactie saaa on aa.srt = saaa.id
                   where p.bsn ='142856721'
                   and hpg.tsverval is NOT NULL
                   order by hpg.dataanvgel de volgende gegevens:
| veld              | waarde                               |
| actieinhoud       | Registratie geborene                 |
| actieverval       | Registratie geslachtsnaam/voornaam   |
| dataanvgel        | 20160101                             |
| dateindegel       | NULL                                 |
| actieAanp         | NULL                                 |
| predicaat         | NULL                                 |
| adellijketitel    | NULL                                 |
| voorvoegsel       | NULL                                 |
| scheidingsteken   | NULL                                 |
| stam              | Jansen                               |

!-- Controleer persoon naamgebruik.
Then in kern heeft select sainh.naam as actieInhoud, saav.naam as actieVerval, CASE WHEN ng.tsverval IS NULL THEN 'Leeg' ELSE 'Gevuld' END AS tsverval, ng.naamgebruik, ng.voornamennaamgebruik,
                   ng.voorvoegselnaamgebruik, ng.scheidingstekennaamgebruik, ng.geslnaamstamnaamgebruik, ng.predicaatnaamgebruik, ng.adellijketitelnaamgebruik,
                   ng.indnaamgebruikafgeleid
                   from kern.his_persnaamgebruik ng
                   left join kern.pers p on p.id = ng.pers
                   left join kern.actie ainh on ainh.id = ng.actieinh
                   left join kern.actie av on av.id = ng.actieverval
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   where p.bsn ='142856721'
                   order by sainh.naam de volgende gegevens:
| veld                        | waarde                               |
| actieinhoud                 | Registratie geborene                 |
| actieverval                 | Registratie geslachtsnaam/voornaam   |
| tsverval                    | Gevuld                               |
| naamgebruik                 | 1                                    |
| voornamennaamgebruik        | NULL                                 |
| voorvoegselnaamgebruik      | NULL                                 |
| scheidingstekennaamgebruik  | NULL                                 |
| geslnaamstamnaamgebruik     | Jansen                               |
| predicaatnaamgebruik        | NULL                                 |
| adellijketitelnaamgebruik   | NULL                                 |
| indnaamgebruikafgeleid      | true                                 |
----
| actieinhoud                 | Registratie geslachtsnaam/voornaam   |
| actieverval                 | NULL                                 |
| tsverval                    | Leeg                                 |
| naamgebruik                 | 1                                    |
| voornamennaamgebruik        | Kees Koos                            |
| voorvoegselnaamgebruik      | l                                    |
| scheidingstekennaamgebruik  | '                                    |
| geslnaamstamnaamgebruik     | Jansens                              |
| predicaatnaamgebruik        | NULL                                 |
| adellijketitelnaamgebruik   | 1                                    |
| indnaamgebruikafgeleid      | true                                 |

!-- Controleer persoon samengesteldenaam.
Then in kern heeft select sainh.naam as actieInhoud, saav.naam as actieVerval, CASE WHEN sn.tsverval IS NULL THEN 'Leeg' ELSE 'Gevuld' END AS tsverval, sn.dataanvgel, sn.dateindegel,
                   saaa.naam as actieAanp, sn.voornamen, sn.voorvoegsel, sn.scheidingsteken, sn.geslnaamstam, sn.predicaat, sn.adellijketitel, sn.indafgeleid
                   from kern.his_perssamengesteldenaam sn
                   left join kern.pers p on p.id = sn.pers
                   left join kern.actie ainh on ainh.id = sn.actieinh
                   left join kern.actie av on av.id = sn.actieverval
                   left join kern.actie aa on aa.id = sn.actieaanpgel
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left join kern.srtactie saaa on aa.srt = saaa.id
                   where p.bsn ='142856721'
                   order by saav.naam, sn.dateindegel de volgende gegevens:
| veld                | waarde                               |
| actieinhoud         | Registratie geborene                 |
| actieverval         | Registratie geslachtsnaam/voornaam   |
| tsverval            | Gevuld                               |
| dataanvgel          | 20160101                             |
| dateindegel         | NULL                                 |
| actieaanp           | NULL                                 |
| voornamen           | NULL                                 |
| voorvoegsel         | NULL                                 |
| scheidingsteken     | NULL                                 |
| geslnaamstam        | Jansen                               |
| predicaat           | NULL                                 |
| adellijketitel      | NULL                                 |
| indafgeleid         | true                                 |
----
| actieinhoud         | Registratie geborene                 |
| actieverval         | NULL                                 |
| tsverval            | Leeg                                 |
| dataanvgel          | 20160101                             |
| dateindegel         | 20160102                             |
| actieaanp           | Registratie geslachtsnaam/voornaam   |
| voornamen           | NULL                                 |
| voorvoegsel         | NULL                                 |
| scheidingsteken     | NULL                                 |
| geslnaamstam        | Jansen                               |
| predicaat           | NULL                                 |
| adellijketitel      | NULL                                 |
| indafgeleid         | true                                 |
----
| actieinhoud         | Registratie geslachtsnaam/voornaam   |
| actieverval         | NULL                                 |
| tsverval            | Leeg                                 |
| dataanvgel          | 20160102                             |
| dateindegel         | NULL                                 |
| actieaanp           | NULL                                 |
| voornamen           | Kees Koos                            |
| voorvoegsel         | l                                    |
| scheidingsteken     | '                                    |
| geslnaamstam        | Jansens                              |
| predicaat           | NULL                                 |
| adellijketitel      | 1                                    |
| indafgeleid         | true                                 |