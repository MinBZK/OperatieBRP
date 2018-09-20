update kern.relatie set srt = 2 
where relatie.id in (

select r.id from 
kern.pers p join kern.betr b on p.id = b.pers join kern.relatie r on b.relatie = r.id
where bsn = ${DataSource Values#burgerservicenummer_ipp0} and r.srt = 1

);