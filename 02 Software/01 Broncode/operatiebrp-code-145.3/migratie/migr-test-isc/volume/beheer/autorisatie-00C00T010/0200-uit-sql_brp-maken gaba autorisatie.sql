insert into autaut.levsautorisatie(id, stelsel, indmodelautorisatie, naam, protocolleringsniveau, indaliassrtadmhndleveren, datingang) 
  values (nextval('autaut.seq_levsautorisatie'), 2, true, 'GABA', 1, false, 20160101);

insert into autaut.his_levsautorisatie(id, levsautorisatie, tsreg, naam, protocolleringsniveau, indaliassrtadmhndleveren, datingang)
  select nextval('autaut.seq_his_levsautorisatie')
  ,      id
  ,      now()
  ,      naam
  ,      protocolleringsniveau
  ,      indaliassrtadmhndleveren
  ,      datingang
  from   autaut.levsautorisatie
  where  id = currval('autaut.seq_levsautorisatie');

insert into autaut.toeganglevsautorisatie(id, levsautorisatie, geautoriseerde, datingang)
  select nextval('autaut.seq_toeganglevsautorisatie')
  ,      currval('autaut.seq_levsautorisatie')
  ,      partijrol.partij
  ,      20160101
  from   kern.partijrol 
  where partijrol.rol = 2;

insert into autaut.his_toeganglevsautorisatie(id, toeganglevsautorisatie, tsreg, datingang)
   select nextval('autaut.seq_his_toeganglevsautorisatie')
   ,      id
   ,      now()
   ,      datingang
   from   autaut.toeganglevsautorisatie
   where  levsautorisatie = currval('autaut.seq_levsautorisatie');
