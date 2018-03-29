select
--adm.srt||' ('||
sadm.naam--||')' 
as soort_admhnd,
adm.toelichtingontlening as toelichting_ontlening_admhnd,
--act.srt||' ('||
sa.naam--||')' 
as soort_actie,
--act.partij||' ('||
spar.code||' '||spar.naam--||')'  
as partij_actie,
act.datontlening as datum_ontlening_actie,
ab.rechtsgrond as rechtsgrond, ab.rechtsgrondoms as rechtsgrondoms,
--dc.srt||'( '||
sdc.naam--||')' 
as doc_soort,
case when dc.aktenr is not null then 'Aktenummer: '||dc.aktenr
     else 'Docoms: '||dc.oms
     end as document

from
kern.pers p
join kern.admhnd adm on adm.id=p.admhnd
join kern.actie act on act.admhnd=adm.id
left join kern.srtactie sa on sa.id = act.srt
left join kern.srtadmhnd sadm on sadm.id = adm.srt
left join kern.partij spar on spar.id = act.partij
left join kern.actiebron ab on ab.actie = act.id
left join kern.doc dc on dc.id = ab.doc
left join kern.srtdoc sdc on sdc.id = dc.srt
where
p.anr = '{anr}' AND 
p.srt = 1
order by soort_actie asc, datum_ontlening_actie desc, doc_soort desc, document, soort_admhnd, toelichting_ontlening_admhnd, partij_actie, rechtsgrond;