Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Geboorte in Nederland met erkenning na geboortedatum: nevenactie Registratie staatloos

Scenario:   Registratie staatloos via "Geboorte in Nederland na geboortedatum"
            LT: GNNG04C40T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG04C40T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG04C40T10-002.xls

When voer een bijhouding uit GNNG04C40T10.xml namens partij 'Gemeente BRP 1'

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

!-- Controleer vastlegging staatloos
Then in kern heeft select sainh.naam as actieInhoud, saav.naam as actieVerval, si.naam, pi.waarde, pi.indag
                   from kern.srtindicatie si
                   left outer join kern.persindicatie pi on pi.srt = si.id
                   left outer join kern.his_persindicatie hpi on hpi.persindicatie = pi.id
                   left join kern.actie ainh on ainh.id = hpi.actieinh
                   left join kern.actie av on av.id = hpi.actieverval
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left outer join kern.pers pe on pe.id = pi.pers
                   where sainh.naam ='Registratie staatloos' and pe.voornamen = 'Jan' de volgende gegevens:
| veld                      | waarde                                        |
| actieInhoud               | Registratie staatloos                         |
| actieVerval               | NULL                                          |
| naam                      | Staatloos?                                    |
| waarde                    | true                                          |
| indag                     | true                                          |