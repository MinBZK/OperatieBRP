-- OIN en Bijhoudersrol Partij 'Migratievoorzieningen' 
delete from kern.his_partijrol where partijrol = (select id from kern.partijrol where partij = 2000 and rol=2);
delete from kern.his_partij where partij = 2000;
delete from kern.partijrol where partij = 2000 and rol=2;

UPDATE kern.partij SET oin='199902', datingang=20000101 WHERE id = 2000;
INSERT INTO kern.his_partij(partij,tsreg,naam,datingang,indverstrbeperkingmogelijk) VALUES (2000,now(),'Migratievoorzieningen',20000101,false);
INSERT INTO kern.partijrol(partij,rol) VALUES (2000,2);
INSERT INTO kern.his_partijrol(partijrol,tsreg,datingang) VALUES ((select id from kern.partijrol where partij = 2000 and rol=2), now(),20000101);

delete from autaut.his_bijhautorisatiesrtadmhnd;
delete from autaut.bijhautorisatiesrtadmhnd;
delete from autaut.his_toegangbijhautorisatie;
delete from autaut.toegangbijhautorisatie;
delete from autaut.his_bijhautorisatie where bijhautorisatie = (select id from autaut.bijhautorisatie where naam='gbaHuwelijk');
delete from autaut.bijhautorisatie where naam ='gbaHuwelijk';

INSERT INTO autaut.bijhautorisatie(id, indmodelautorisatie, naam, datingang, dateinde, indblok) 
	VALUES (2, false, 'gbaHuwelijk', 20010101, null, null);
INSERT INTO autaut.his_bijhautorisatie(id, bijhautorisatie, tsreg, tsverval, naam, datingang, dateinde, indblok) 
	VALUES (2, 2, now(), null, 'gbaHuwelijk', 20010101, null, null);

INSERT INTO autaut.bijhautorisatiesrtadmhnd (id,bijhautorisatie,srtadmhnd) 
	VALUES (2, 2,(select id from kern.srtadmhnd where naam = 'GBA - Voltrekking huwelijk in Nederland'));
INSERT INTO autaut.his_bijhautorisatiesrtadmhnd(id, bijhautorisatiesrtadmhnd, tsreg, tsverval) 
	VALUES (2, 2, now(), null);

INSERT INTO autaut.toegangbijhautorisatie (id,bijhautorisatie, geautoriseerde, ondertekenaar, transporteur, datingang, dateinde, indblok) 
	VALUES (2,2, (select id from kern.partijrol where partij = (select id from kern.partij where code='059901') and rol='2'), 2000,2000, 20000101, 20210828, null);
INSERT INTO autaut.his_toegangbijhautorisatie (id, toegangbijhautorisatie, tsreg, datingang, dateinde, indblok) 
	VALUES (2, 2, now(), 20010101, 20210828, null);
