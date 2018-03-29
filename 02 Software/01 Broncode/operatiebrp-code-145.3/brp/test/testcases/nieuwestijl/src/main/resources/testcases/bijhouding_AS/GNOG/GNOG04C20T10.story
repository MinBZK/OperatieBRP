Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Geboorte in Nederland: Nevenactie Registratie identificatienummer

Scenario:   Registratie identificatienummers (Kind)
            LT: GNOG04C20T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNOG/GNOG04C20T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNOG/GNOG04C20T10-002.xls

When voer een bijhouding uit GNOG04C20T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

!-- Controleer betrokken personen zijn gemarkeerd als bijgehouden
Then in kern heeft select sainh.naam as actieInhoud, saav.naam as actieVerval, p.voornamen, sa.naam as AdmhndNaam, hpaf.sorteervolgorde
                   from kern.his_persafgeleidadministrati hpaf
                   join kern.actie ainh on ainh.id = hpaf.actieinh
                   left join kern.actie av on av.id = hpaf.actieverval
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left join kern.pers p on hpaf.pers = p.id
                   left join kern.admhnd a on hpaf.admhnd = a.id
                   left join kern.srtadmhnd sa on a.srt = sa.id
                   where sainh.naam ='Registratie geborene'
                   order by p.voornamen de volgende gegevens:
| veld                      | waarde                                               |
| actieinhoud               | Registratie geborene                                 |
| actieverval               | NULL                                                 |
| voornamen                 | Hendrik Jan                                          |
| admhndnaam                | Geboorte in Nederland met erkenning op geboortedatum |
| sorteervolgorde           | 1                                                    |

!-- Controleer identificatienummers Kind
Then in kern heeft select id.anr, id.bsn
                   from kern.his_persids id
                   left outer join kern.actie a on a.id = id.actieinh
                   left outer join kern.srtactie sa on sa.id = a.srt
                   left outer join kern.pers pe on pe.id = id.pers
                   where sa.naam ='Registratie identificatienummers' and pe.voornamen = 'Hendrik Jan' de volgende gegevens:
| veld                      | waarde                                        |
| anr                       | 6806310162                                    |
| bsn                       | 590151113                                     |




