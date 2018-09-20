--(select distinct padres1.srt, padres1.postcode, padres1.huisnr, prs1.bsn, prs1.id, padres1.id from kern.pers prs1, kern.persadres padres1 where prs1.id in (select padres2.id from kern.persadres padres2 where padres2.id in (select id from kern.pers prs2 where prs2.bsn= 500263826 ) or prs1.id = padres2.id ) order by padres1.postcode, padres1.huisnr) 
--select padres2.id from kern.persadres padres2 where padres2.id in (select id from kern.pers prs2 ) 
--select * from kern.pers prs2
--(select distinct padres1.srt, padres1.postcode, padres1.huisnr, prs1.bsn, prs1.id, padres1.id from kern.pers prs1, kern.persadres padres1 where prs1.id in (select padres2.id from kern.persadres padres2 where padres2.id in (select id from kern.pers prs2 where prs2.bsn= 500263826 ) ) order by padres1.postcode, padres1.huisnr) 

--select * from kern.persadres padres2, kern.pers prs2 where padres2.id = prs2.id and prs2.bsn= 500263826 
--select distinct padres1.srt, padres1.postcode, padres1.huisnr, prs1.bsn, prs1.id, padres1.id from kern.pers prs1, kern.persadres padres1 where prs1.id in (select padres2.id from kern.persadres padres2, kern.pers prs2 where padres2.id = prs2.id and prs2.bsn= 500263826)
--select * from kern.persadres padres1 where padres1.id = 5001
--select * from kern.pers where kern.pers.id in (select padres1.id from kern.persadres padres1 where padres1.id in (select pers.id from kern.pers where kern.pers.id = 5001))
--select * from kern.persadres where persadres.id > 5000 and persadres.id < 6000 
--select distinct pers1.* from kern.pers pers1, kern.persadres persad1 where persad1.land = 229 and persadr1.huisnummer = 22 and 
--select distinct * from kern.persadres persadres1, kern.pers pers1 where 
--bbbselect pers3.bsn, padres4.* from kern.pers pers3, kern.persadres padres4 where pers3.id in (select distinct padres3.id  from kern.persadres padres2, kern.pers pers2, kern.persadres padres3 where padres2.PERS in (select id from kern.pers prs2 where prs2.bsn= 500226234 ) and padres2.huisnr = padres3.huisnr and padres2.land = padres3.land and padres2.nor = padres3.nor and padres2.postcode = padres3.postcode)and padres4.id = pers3.id order by pers3.bsn
--select pers3.bsn, padres4.* from kern.pers pers3, kern.persadres padres4 where pers3.id in (select distinct padres3.PERS  from kern.persadres padres2, kern.pers pers2, kern.persadres padres3 where padres2.PERS in (select id from kern.pers prs2 where prs2.bsn = 500141423) and padres2.huisnr = padres3.huisnr and padres2.land = padres3.land and padres2.nor = padres3.nor and padres2.postcode = padres3.postcode)and padres4.id = pers3.id order by pers3.bsn
--select pers3.bsn, padres4.* from kern.pers pers3, kern.persadres padres4 where pers3.id in (select distinct padres3.id  from kern.persadres padres2, kern.pers pers2, kern.persadres padres3 where padres2.huisnr = padres3.huisnr and padres2.huisnr= 3 and padres2.land = padres3.land and padres2.land = 229 and padres2.postcode = padres3.postcode and padres2.postcode='3511BA')and padres4.id = pers3.id order by pers3.bsn
--select pers3.bsn, padres4.* from kern.pers pers3, kern.persadres padres4 where pers3.id in (select distinct padres3.id  from kern.persadres padres2, kern.pers pers2, kern.persadres padres3 where padres2.huisnr = padres3.huisnr and padres2.huisnr= 3 and padres2.land = padres3.land and padres2.land <> 229 and padres2.postcode = padres3.postcode and padres2.postcode='3511BA')and padres4.id = pers3.id order by pers3.bsn
select p1.bsn, pa1.* from kern.persadres pa1, kern.pers p1, kern.persadres pa2, kern.pers p2 where pa2.pers = p2.id and pa1.pers = p1.id and p2.bsn in (500141423) and pa1.huisnr = pa2.huisnr and pa1.huisletter = pa1.huisletter and pa1.nor = pa2.nor and pa1.postcode = pa2.postcode and pa1.land = pa2.land
;
select pers3.bsn, padres4.* from kern.pers pers3, kern.persadres padres4 where pers3.id in (select distinct padres3.PERS from kern.persadres padres2, kern.pers pers2, kern.persadres padres3 where padres2.PERS in (select id from kern.pers prs2 where prs2.bsn = 500141423) and padres2.huisnr = padres3.huisnr 
--and padres2.huisletter = padres3.huisletter 
--and padres2.huisnrtoevoeging = padres3.huisnrtoevoeging 
and padres2.land = padres3.land and padres2.nor = padres3.nor and padres2.postcode = padres3.postcode)and padres4.id = pers3.id order by pers3.id
;
