---Groep Relatie---
select a.dataanv as DA, a.dateinde as DE, a.tsreg, a.tsverval, a.nadereaandverval, 

ai_v.lo3categorie || ai_v.lo3stapelvolgnr::text || ai_v.lo3voorkomenvolgnr::text as Ai_herkomst, 
av_v.lo3categorie || av_v.lo3stapelvolgnr::text || av_v.lo3voorkomenvolgnr::text as Av_herkomst, 
a.indvoorkomentbvlevmuts, case when a.actievervaltbvlevmuts is null then null else true end as isActieMutsGevuld, 

c.srt, c1.naam as naamsrt, a.gemaanv, a.wplnaamaanv, a.blplaatsaanv, a.blregioaanv, a.omslocaanv, a.landgebiedaanv, a.rdneinde, p.code as coderedeneinde, a.gemeinde, a.wplnaameinde, a.blplaatseinde, a.blregioeinde, a.omsloceinde, a.landgebiedeinde

,d.admhnd as admhndai, f.admhnd as admhndav, g.admhnd as admhndgaav, l.naam as naamai, n.naam as naamav, o.naam as naamgaav
from kern.his_relatie a

join kern.betr b on b.relatie = a.relatie
join kern.relatie c on c.id = a.relatie
join kern.srtrelatie c1 on c1.id = c.srt

left join kern.actie d on d.id = a.actieinh
left join kern.actie f on f.id = a.actieverval
left join kern.actie g on g.id = a.actievervaltbvlevmuts

left join verconv.lo3voorkomen ai_v on ai_v.actie = a.actieinh
left join verconv.lo3voorkomen av_v on av_v.actie = a.actieverval
left join verconv.lo3voorkomen amuts_v on amuts_v.actie = a.actievervaltbvlevmuts

left join kern.admhnd h on h.id = d.admhnd
left join kern.admhnd j on j.id = f.admhnd
left join kern.admhnd k on k.id = g.admhnd

left join kern.srtadmhnd l on l.id = h.srt
left join kern.srtadmhnd n on n.id = j.srt
left join kern.srtadmhnd o on o.id = k.srt
left join kern.rdneinderelatie p on p.id = a.rdneinde

where b.pers = 1
and c.srt = 3	
and b.indouder is true
order by a.tsreg DESC;