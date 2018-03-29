Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Geboorte in Nederland met erkenning na geboortedatum: nevenactie Registratie nationaliteit (perspectief datum van geboorte)

Scenario:   Registratie nationaliteit via "Geboorte in Nederland met erkenning na geboortedatum" met DAG geboortedatum
            LT: GNNG04C30T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG04C30T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG04C30T10-002.xls

When voer een bijhouding uit GNNG04C30T10.xml namens partij 'Gemeente BRP 1'

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
| veld                      | waarde                                                    |
| actieinhoud               | Registratie geborene                                      |
| actieverval               | NULL                                                      |
| voornamen                 | Henk                                                      |
| admhndnaam                | Geboorte in Nederland met erkenning na geboortedatum      |
| sorteervolgorde           | 1                                                         |
----
| actieinhoud               | Registratie geborene                                      |
| actieverval               | NULL                                                      |
| voornamen                 | Jan                                                       |
| admhndnaam                | Geboorte in Nederland met erkenning na geboortedatum      |
| sorteervolgorde           | 1                                                         |
----
| actieinhoud               | Registratie geborene                                      |
| actieverval               | NULL                                                      |
| voornamen                 | Marie                                                     |
| admhndnaam                | Geboorte in Nederland met erkenning na geboortedatum      |
| sorteervolgorde           | 1                                                         |

!-- Controleer vastlegging in de database van de nationaliteit
Then in kern heeft select sainh.naam as actieInhoud, saav.naam as actieVerval, n.naam as nationaliteit, pn.rdnverk, hpn.dataanvgel, hpn.dateindegel
                   from kern.persnation pn
                   left join kern.his_persnation hpn on hpn.persnation = pn.id
                   left join kern.actie ainh on ainh.id = hpn.actieinh
                   left join kern.actie av on av.id = hpn.actieverval
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left outer join kern.pers pe on pe.id = pn.pers
                   left join kern.nation n on n.id = pn.nation
                   where sainh.naam ='Registratie nationaliteit' and pe.voornamen = 'Jan' de volgende gegevens:
| veld                      | waarde                            |
| actieInhoud               | Registratie nationaliteit         |
| actieVerval               | NULL                              |
| nationaliteit             | Nederlandse                       |
| rdnverk                   | 18                                |
| dataanvgel                | 20160101                          |
| dateindegel               | NULL                              |