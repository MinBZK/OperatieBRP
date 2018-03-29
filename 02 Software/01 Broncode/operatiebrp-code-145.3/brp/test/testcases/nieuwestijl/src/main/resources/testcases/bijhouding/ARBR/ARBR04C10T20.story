Meta:
@status                 Klaar
@usecase                UCS-BY.1.AB
@sleutelwoorden         join kern met ber query

Narrative: Archiveer bericht

Scenario:   Inkomend bericht is verwerkingswijze Prevalidatie
            LT: ARBR04C10T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW02C10T30-001.xls

Given pas laatste relatie van soort 1 aan tussen persoon 602055209 en persoon 196070569 met relatie id 30010001 en betrokkenheid id 30010001
And de database is aangepast met: update kern.his_relatie
                                  set    id = 9999
				  where  id = (
					         select hr.id
					         from   kern.his_relatie hr
					         join   kern.relatie r
					         on     r.id       = hr.relatie
					         where  r.srt      = 1
					         and    hr.dataanv = 20160510
					      )

Then is in de database de persoon met bsn 602055209 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 196070569 wel als PARTNER betrokken bij een HUWELIJK

!-- Correctie van het huwelijk
When voer een bijhouding uit ARBR04C10T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/ARBR/expected/ARBR04C10T20.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R

!-- Controleer de archivering van het ingaande bericht
Then in kern heeft select sb.identifier as berichtSoort, r.naam as richting, p.code as zendendePartij, b.zendendesysteem, b.referentienr, b.crossreferentienr,
                   vw.naam as verwerkingswijze, b.rol, vr.naam as verwerking, br.naam as bijhouding, sm.naam as hoogstemeldingsniveau, sa.naam as admhnd
                   from ber.ber b
                   left join kern.richting r on r.id = b.richting
                   left join kern.srtber sb on sb.id = b.srt
                   left join kern.partij p on p.id = b.zendendepartij
                   left join kern.verwerkingswijze vw on vw.id = b.verwerkingswijze
                   left join kern.verwerkingsresultaat vr on vr.id = b.verwerking
                   left join kern.bijhresultaat br on br.id = b.bijhouding
                   left join kern.srtmelding sm on sm.id = b.hoogstemeldingsniveau
                   left join kern.admhnd a on a.id = b.admhnd
                   left join kern.srtadmhnd sa on sa.id = a.srt
                   where b.tsreg is not null
                   and b.tsverzending is not null
                   and b.tsontv is not null
                   and b.data is not null de volgende gegevens:
| veld                    | waarde                                                  |
| berichtSoort            | bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap       |
| richting                | Ingaand                                                 |
| zendendePartij          | 507013                                                  |
| zendendesysteem         | BRP                                                     |
| referentienr            | 00000000-0000-0000-0042-200000000104                    |
| crossreferentienr       | NULL                                                    |
| verwerkingswijze        | Prevalidatie                                            |
| rol                     | NULL                                                    |
| verwerking              | NULL                                                    |
| bijhouding              | NULL                                                    |
| hoogstemeldingsniveau   | NULL                                                    |
| admhnd                  | NULL                                                    |

!-- Controleer de archivering van het uitgaande bericht
Then in kern heeft select sb.identifier as berichtSoort, r.naam as richting, p.code as zendendePartij, b.zendendesysteem, b.crossreferentienr,
                   vw.naam as verwerkingswijze, b.rol, vr.naam as verwerking, br.naam as bijhouding, sm.naam as hoogstemeldingsniveau, sa.naam as admhnd
                   from ber.ber b
                   left join kern.richting r on r.id = b.richting
                   left join kern.srtber sb on sb.id = b.srt
                   left join kern.partij p on p.id = b.zendendepartij
                   left join kern.verwerkingswijze vw on vw.id = b.verwerkingswijze
                   left join kern.verwerkingsresultaat vr on vr.id = b.verwerking
                   left join kern.bijhresultaat br on br.id = b.bijhouding
                   left join kern.srtmelding sm on sm.id = b.hoogstemeldingsniveau
                   left join kern.admhnd a on a.id = b.admhnd
                   left join kern.srtadmhnd sa on sa.id = a.srt
                   where b.tsreg is not null
                   and b.tsverzending is not null
                   and b.referentienr is not null
                   and b.tsontv is null
                   and b.data is not null de volgende gegevens:
| veld                    | waarde                                                  |
| berichtSoort            | bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R     |
| richting                | Uitgaand                                                |
| zendendePartij          | 199901                                                  |
| zendendesysteem         | BRP                                                     |
| crossreferentienr       | 00000000-0000-0000-0042-200000000104                    |
| verwerkingswijze        | Prevalidatie                                            |
| rol                     | NULL                                                    |
| verwerking              | Geslaagd                                                |
| bijhouding              | Verwerkt                                                |
| hoogstemeldingsniveau   | Waarschuwing                                            |
| admhnd                  | NULL                                                    |

!-- Controleer totaal aantal gearchiveerde berichten in ber.ber tabel
Then in kern heeft SELECT COUNT(id) FROM ber.ber de volgende gegevens:
| veld                      | waarde |
| count                     | 2      |

!-- Controleer gearchiveerde personen in ber.berpers tabel
Then in kern heeft select p.bsn
                   from ber.berpers bp
                   left join kern.pers p on p.id = bp.pers
                   order by p.bsn de volgende gegevens:
| veld           | waarde               |
| bsn            | 602055209            |

!-- Controleer totaal aantal gearchiveerde personen in ber.berpers tabel
Then in kern heeft SELECT COUNT(ber) FROM ber.berpers de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |


