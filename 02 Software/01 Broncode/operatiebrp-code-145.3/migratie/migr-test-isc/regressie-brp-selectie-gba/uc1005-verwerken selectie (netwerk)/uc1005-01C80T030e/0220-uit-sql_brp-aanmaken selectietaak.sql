delete from autaut.his_seltaakstatus;
delete from autaut.his_seltaak;
delete from autaut.seltaak;

insert into autaut.seltaak(id, dienst, toeganglevsautorisatie, volgnr, datplanning, datuitvoer,
  peilmommaterieel, peilmommaterieelresultaat, peilmomformeelresultaat, indsellijstgebruiken, indag,
  status, statusgewijzigddoor, statustoelichting, indagstatus) values(nextval('autaut.seq_seltaak'),
  (select dienst.id 
   from autaut.dienst, autaut.dienstbundel 
   where  dienst.dienstbundel = dienstbundel.id
   and   dienst.srt = 12
   and   dienstbundel.levsautorisatie = (select id from autaut.levsautorisatie where naam = '999901')), 
  (select id 
   from autaut.toeganglevsautorisatie 
   where levsautorisatie = (select id from autaut.levsautorisatie where naam = '999901')),
   1, cast(to_char(now(), 'yyyyMMdd') as integer), null,  null, null, null, false, true, 3, 'test', 'test', true);
  
insert into autaut.his_seltaak(id, seltaak, tsreg, datplanning, datuitvoer,  peilmommaterieel, peilmommaterieelresultaat, peilmomformeelresultaat, indsellijstgebruiken)
   select nextval('autaut.seq_his_seltaak'), id, now(), datplanning, datuitvoer,  peilmommaterieel, peilmommaterieelresultaat, peilmomformeelresultaat, indsellijstgebruiken
   from autaut.seltaak;

insert into autaut.his_seltaakstatus(id, seltaak, tsreg, status, statusgewijzigddoor, statustoelichting)
   select nextval('autaut.seq_his_seltaakstatus'), id, now(), status, statusgewijzigddoor, statustoelichting
   from autaut.seltaak;
