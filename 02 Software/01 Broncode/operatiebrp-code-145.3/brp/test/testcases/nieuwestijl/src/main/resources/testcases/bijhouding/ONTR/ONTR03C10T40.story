Meta:
@status                 Klaar
@usecase                UCS-BY.1.ON


Narrative: Verwerking van ontrelateren familierechtelijke betrekkingen

Scenario: 1. DB init scenario om uitgangssituatie te zetten
            preconditie

Given alle personen zijn verwijderd

Scenario: 2. DAG kopie-persoon is groter dan de aanvangsdatum van ouder.ouderschap
             LT: ONTR03C10T40

Given enkel initiele vulling uit bestand /LO3PL-ONTR/ONTR03C10T40-001.xls
Given enkel initiele vulling uit bestand /LO3PL-ONTR/ONTR03C10T40-002.xls

!-- voer GBNL uit om symmetrische FRB te maken
When voer een bijhouding uit ONTR03C10T40a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

!-- voer VHNL uit om symmetrisch huwelijk te maken
When voer een bijhouding uit ONTR03C10T40b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

!-- zet autofiat uizondering voor Gemeente BRP 2 zodat ontrelateren wordt getriggerd bij volgende bijhouding (ontbinding)
Given de database is aangepast met: delete from autaut.his_bijhouderfiatuitz where id =99999
And de database is aangepast met: delete from autaut.bijhouderfiatuitz where id =99999
And de database is aangepast met: insert into autaut.bijhouderfiatuitz (id,bijhouder, datingang, dateinde, bijhouderbijhvoorstel, srtdoc, srtadmhnd, indblok) values (99999, (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 2') and rol=2), null , null , (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2), null, null, null)
And de database is aangepast met: insert into autaut.his_bijhouderfiatuitz (id, bijhouderfiatuitz, tsreg, tsverval, datingang, dateinde, bijhouderbijhvoorstel, srtdoc, srtadmhnd, indblok) values(99999, 99999, now() at time zone 'UTC', null, 19990101, null, (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2), null, null, null)

!-- doe ontbinding huwelijk met naamswijziging
Then heeft $RELATIE_ID$ de waarde van de volgende query: select partnerBetr.relatie from kern.pers partner join kern.betr partnerBetr on partnerBetr.pers = partner.id where partner.bsn = '330276025' and partnerBetr.rol=3
When voer een bijhouding uit ONTR03C10T40c.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Scenario:   3. DB reset scenario om de aangepaste data weer terug te zetten
            postconditie

Given de database is aangepast met: delete from autaut.his_bijhouderfiatuitz where id =99999
And de database is aangepast met: delete from autaut.bijhouderfiatuitz where id =99999

!-- Controleer DAG's van Psuedo-persoon ouder de recentere datum bevat ipv ouder.ouderschap.DAG
Then in kern heeft select hpid.dataanvgel AS DAG_identieficatienummers, hpsam.dataanvgel AS DAG_samengesteldenaam, hpges.dataanvgel AS DAG_geslachtsaand
                   from kern.betr b
                   left outer join kern.his_betr hb on hb.betr = b.id
                   left outer join kern.relatie r on r.id = b.relatie
                   left outer join kern.his_relatie hr on r.id = hr.relatie
                   left outer join kern.pers p on p.id = b.pers
                   left outer join kern.actie av on av.id = hr.actieverval
                   left outer join kern.actie ainh on ainh.id = hr.actieinh
                   left outer join kern.srtactie sainh on ainh.srt = sainh.id
                   left outer join kern.srtactie saav on av.srt = saav.id
                   left outer join kern.his_persids hpid on p.id = hpid.pers
                   left outer join kern.his_perssamengesteldenaam hpsam on hpsam.pers = hpid.pers
                   left outer join kern.his_persgeslachtsaand hpges on hpsam.pers = hpges.pers
                   left outer join kern.his_persgeboorte hpgeb on hpsam.pers = hpgeb.pers
                   where hr.relatie in (select relatie from kern.betr where pers in (select id from kern.pers where bsn='988473033' and srt='1'))
                   and hb.tsverval is null
                   and hpsam.tsverval is null
                   and hpsam.dateindegel is null
                   and p.srt ='2' de volgende gegevens:
| veld                         | waarde     |
| DAG_identieficatienummers    | 20170102   |
| DAG_samengesteldenaam        | 20170620   |
| DAG_geslachtsaand            | 20170102   |