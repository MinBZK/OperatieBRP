Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         join kern met ber query
@regels                 R1572
@usecase                UCS-BY.HG

Narrative:
R1572 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R1572 Ouder en wijzigen Geslachtsnaamcomponent.stam en Ouder.Ouderschap.DAG overlapt met Geslachtsnaamcomponent.Standaard.DAG
          LT: ARBR04C10T120

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/AGNL01C800T10-Marjan.xls
Given enkel initiele vulling uit bestand /LO3PL-AGNL/AGNL01C800T10-Victor.xls

When voer een bijhouding uit ARBR04C10T120.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
And is het antwoordbericht gelijk aan /testcases/bijhouding/ARBR/expected/ARBR04C10T120.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

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
| berichtSoort            | bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap      |
| richting                | Ingaand                                                 |
| zendendePartij          | 507013                                                  |
| zendendesysteem         | BRP                                                     |
| referentienr            | 00000000-0000-0000-0042-200000000104                    |
| crossreferentienr       | NULL                                                    |
| verwerkingswijze        | Bijhouding                                              |
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
| berichtSoort            | bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R    |
| richting                | Uitgaand                                                |
| zendendePartij          | 199901                                                  |
| zendendesysteem         | BRP                                                     |
| crossreferentienr       | 00000000-0000-0000-0042-200000000104                    |
| verwerkingswijze        | Bijhouding                                              |
| rol                     | NULL                                                    |
| verwerking              | Geslaagd                                                |
| bijhouding              | Verwerkt                                                |
| hoogstemeldingsniveau   | Waarschuwing                                            |
| admhnd                  | Aangaan geregistreerd partnerschap in Nederland         |

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
| bsn            | 188518113            |
----
| bsn            | 188518113            |
----
| bsn            | 485927081            |
----
| bsn            | 485927081            |
!-- Controleer totaal aantal gearchiveerde personen in ber.berpers tabel
Then in kern heeft SELECT COUNT(ber) FROM ber.berpers de volgende gegevens:
| veld                      | waarde |
| count                     | 4      |
