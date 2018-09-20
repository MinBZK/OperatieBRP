select distinct p1.bsn, pa1.* from kern.persadres pa1, kern.pers p1, kern.persadres pa2, kern.pers p2 where pa2.pers = p2.id and pa1.pers = p1.id and pa1.huisnr = 10 and pa1.postcode = '4811AW' and pa1.huisnr = pa2.huisnr and pa1.postcode = pa2.postcode and pa1.huisletter is null and pa1.huisnrtoevoeging is null and pa1.land = pa2.land and pa2.srt = pa1.srt and pa1.srt = 1 order by pa1.id;

select distinct p1.bsn, pa1.* from kern.persadres pa1, kern.pers p1, kern.persadres pa2, kern.pers p2 where pa2.pers = p2.id and pa1.pers = p1.id and pa1.huisnr = 10 and pa1.postcode = '4811AW' and pa1.huisletter = 'b' and pa1.huisnr = pa2.huisnr and pa1.postcode = pa2.postcode and pa1.huisletter = pa2.huisletter and pa1.huisnrtoevoeging = pa2.huisnrtoevoeging and pa1.huisnrtoevoeging = 'or' and pa1.land = pa2.land and pa2.srt = pa1.srt and pa1.srt = 1 order by pa1.id;

select distinct p1.bsn, pa1.* from kern.persadres pa1, kern.pers p1, kern.persadres pa2, kern.pers p2 where pa2.pers = p2.id and pa1.pers = p1.id and p2.bsn in (500181615) and pa1.huisnr = 11 and pa1.postcode = '4811WA' and pa1.huisletter = 'a' and pa1.huisnr = pa2.huisnr and pa1.postcode = pa2.postcode and pa1.huisletter = pa2.huisletter and  pa1.huisnrtoevoeging is null and pa1.land = pa2.land and pa2.srt = pa1.srt and pa1.srt = 2 order by pa1.id;


select distinct p1.bsn, pa1.* from kern.persadres pa1, kern.pers p1, kern.persadres pa2, kern.pers p2 where pa2.pers = p2.id and pa1.pers = p1.id and p2.bsn in (500181615) and pa1.huisnr = 11 and pa1.postcode = '4811WA' and pa1.huisletter = 'a' and pa1.huisnr = pa2.huisnr and pa1.postcode = pa2.postcode and pa1.huisletter = pa2.huisletter and  pa1.huisnrtoevoeging is null and pa1.land = pa2.land and pa2.srt = pa1.srt and pa1.srt = 2 order by pa1.id;

select distinct p1.bsn, pa1.* from kern.persadres pa1, kern.pers p1, kern.persadres pa2, kern.pers p2 where pa2.pers = p2.id and pa1.pers = p1.id and pa1.gem= pa2.gem and pa1.gem = 5001 and pa1.nor = pa2.nor and pa1.nor = 'Straat-3' and pa1.huisletter = 'a' and pa1.huisnr = 15 and pa1.huisnrtoevoeging ='up' and pa1.huisnr = pa2.huisnr and pa1.huisletter = pa2.huisletter and pa1.huisnrtoevoeging = pa2.huisnrtoevoeging and pa1.land = pa2.land and pa1.srt = 1 order by pa1.id;

select distinct p1.bsn, pa1.* from kern.persadres pa1, kern.pers p1, kern.persadres pa2, kern.pers p2 where pa2.pers = p2.id and pa1.pers = p1.id and pa1.gem= pa2.gem and pa1.gem = 5001 and pa1.adresseerbaarobject = pa2.adresseerbaarobject and pa1.identcodenraand = pa2.identcodenraand  and pa1.nor = pa2.nor and pa1.nor = 'Straat-4' and pa1.postcode = '4811WA' and pa1.huisletter = 'a' and pa1.postcode = pa2.postcode and pa1.huisnr = 16 and pa1.huisnrtoevoeging ='up' and pa1.huisnr = pa2.huisnr and pa1.huisletter = pa2.huisletter and pa1.huisnrtoevoeging = pa2.huisnrtoevoeging and pa1.land = pa2.land  order by pa1.id;

select distinct p1.bsn, pa1.* from kern.persadres pa1, kern.pers p1, kern.persadres pa2, kern.pers p2 where pa2.pers = p2.id and pa1.pers = p1.id and pa1.gem= pa2.gem and pa1.gem = 5001 and pa1.nor = 'Straat-5' and pa1.postcode = '4811WA' and pa1.adresseerbaarobject = pa2.adresseerbaarobject and pa1.nor = pa2.nor and pa1.identcodenraand = pa2.identcodenraand and pa1.postcode = pa2.postcode and pa1.land = pa2.land  order by pa1.id;

select p1.bsn, pa1.* from kern.persadres pa1, kern.pers p1, kern.persadres pa2, kern.pers p2 
where pa2.pers = p2.id
and pa1.pers = p1.id
--and p2.bsn in (500175706)
and pa1.huisnr = pa2.huisnr
and pa1.nor = pa2.nor
and pa1.postcode = pa2.postcode
and pa1.land = pa2.land
;
select p1.bsn, pa1.* from kern.persadres pa1, kern.pers p1, kern.persadres pa2, kern.pers p2 
where pa2.pers = p2.id
and pa1.pers = p1.id
and p2.bsn in (500156621)
and pa1.adresseerbaarobject = pa2.adresseerbaarobject
and pa1.identcodenraand = pa2.identcodenraand
and pa1.land = pa2.land
;
select distinct p1.bsn, pa1.* from kern.persadres pa1, kern.pers p1, kern.persadres pa2, kern.pers p2 
where pa2.pers = p2.id
and pa1.pers = p1.id
--and p2.bsn in (500156621)
and pa1.huisnr = 10
and pa1.postcode = '4811AW'
and pa1.huisnr = pa2.huisnr
and pa1.postcode = pa2.postcode
and pa1.huisletter is null 
and pa1.huisnrtoevoeging is null
and pa1.land = pa2.land
order by pa1.id;

select p1.bsn, pa1.* from kern.persadres pa1, kern.pers p1, kern.persadres pa2, kern.pers p2 where pa2.pers = p2.id and  pa1.loctovadres = pa2.loctovadres and pa1.locoms = pa2.locoms and pa1.pers = p1.id and pa1.gem= pa2.gem and pa1.nor = pa2.nor and pa1.postcode = pa2.postcode and pa1.huisnr = pa2.huisnr and  p2.bsn in (500164083)  order by p1.id

pa1.loctovadres = pa2.loctovadres and pa1.locoms = pa2.locoms and
select p1.bsn, pa1.* from kern.persadres pa1, kern.pers p1, kern.persadres pa2, kern.pers p2 where pa2.pers = p2.id and pa1.pers = p1.id and p2.bsn in (500107464) and pa1.adresseerbaarobject = pa2.adresseerbaarobject and pa1.identcodenraand = pa2.identcodenraand
;
and (pa1.huisletter is null or pa1.huisnrtoevoeging is null or pa1.loctovadres is null or pa1.locoms is null)
or 
(
select p1.bsn, pa1.* from kern.persadres pa1, kern.pers p1, kern.persadres pa2, kern.pers p2 
where pa2.pers = p2.id
and pa1.pers = p1.id
and p2.bsn in (500141423)
--and p2.bsn in (500156621)
and pa1.huisnr = pa2.huisnr
and pa1.huisletter = pa1.huisletter 
and pa1.huisletter is null
and pa1.nor = pa2.nor
and pa1.postcode = pa2.postcode
and pa1.land = pa2.land
)
;