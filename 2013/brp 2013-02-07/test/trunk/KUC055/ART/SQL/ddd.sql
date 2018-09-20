--select * from kern.plaats where code = 1418;
--select a.* from kern.gem a where a.id in (select wpl from kern.his_persadres where persadres in (select id from kern.persadres where id in (select id from kern.pers where bsn=330006575))); 
--select * from kern.his_persadres where persadres in (select id from kern.persadres where id in (select id from kern.pers where bsn=330006575));
--select * from kern.partij where id in ( select gem from kern.persadres where id in (select id from kern.pers ));
--select * from kern.partij where code = 0344;
--SELECT * FROM kern.aangadresh WHERE ID IN (select aangadresh from kern.persadres where id in (select id from kern.pers where bsn=527163703))
--SELECT * FROM kern.aangadresh

--SELECT * FROM kern.functieadres WHERE ID IN (select srt from kern.persadres where id in (select id from kern.pers where bsn=527163703))
--select land from kern.persadres where id in (select id from kern.pers where bsn=527163703)

--SELECT * FROM kern.land WHERE ID IN (select land from kern.persadres where id in (select id from kern.pers where bsn=527163703))
select * from kern.his_persadres where persadres in (select id from kern.persadres where id in (select id from kern.pers where bsn=527163703));