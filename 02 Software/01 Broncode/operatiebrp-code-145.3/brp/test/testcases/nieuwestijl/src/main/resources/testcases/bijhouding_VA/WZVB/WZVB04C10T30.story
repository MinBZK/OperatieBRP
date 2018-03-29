Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: Verwerking Hoofdactie Registratie verblijfsrecht

Scenario: 1. DB init
          preconditie

Given de database is aangepast met: Update kern.partijRol
                                    set rol = 4
                                    where id = (select pr.id from kern.partijrol pr join kern.partij p on p.id = pr.partij and p.naam = 'Gemeente BRP 1' and pr.rol != 1)

Given maak bijhouding caches leeg

Scenario: 2. Registratie verblijfsrecht van niet-ingezetene
             LT: WZVB04C10T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-WZVB/WZVB04C10T30-001.xls

When voer een bijhouding uit WZVB04C10T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/WZVB/expected/WZVB04C10T30.xml voor expressie //brp:bhg_vbaRegistreerVerblijfsrecht_R

Then staat er 1 notificatiebericht voor bijhouders op de queue

!-- Controleer persoon niet is bijgehouden in Afgeleid administratief
Then in kern heeft select count(1)
                   from kern.his_persafgeleidadministrati hpaf
                   join kern.actie ainh on ainh.id = hpaf.actieinh
                   left join kern.actie av on av.id = hpaf.actieverval
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left join kern.pers p on hpaf.pers = p.id
                   left join kern.admhnd a on hpaf.admhnd = a.id
                   left join kern.srtadmhnd sa on a.srt = sa.id
                   where sainh.naam ='Registratie verblijfsrecht' de volgende gegevens:
| veld                      | waarde                 |
| count                     | 0                      |

Scenario: 3. DB reset
          postconditie

Given de database is aangepast met: Update kern.partijRol
                                    set rol = 2
                                    where id = (select pr.id from kern.partijrol pr join kern.partij p on p.id = pr.partij and p.naam = 'Gemeente BRP 1' and pr.rol != 1)

Given maak bijhouding caches leeg