-- Update kern.partij met de juiste oin nummers - https://register.digikoppeling.nl/overview/index

BEGIN;

-- Reset sequences
SELECT SETVAL('autaut.seq_bijhautorisatie', (select 1 + coalesce(max(id),0) FROM autaut.bijhautorisatie), false);
SELECT SETVAL('autaut.seq_his_bijhautorisatie', (select 1 + coalesce(max(id),0) FROM autaut.his_bijhautorisatie), false);
SELECT SETVAL('autaut.seq_bijhautorisatiesrtadmhnd', (select 1 + coalesce(max(id),0) FROM autaut.bijhautorisatiesrtadmhnd), false);
SELECT SETVAL('autaut.seq_his_bijhautorisatiesrtadmhnd', (select 1 + coalesce(max(id),0) FROM autaut.his_bijhautorisatiesrtadmhnd), false);

truncate autaut.his_toegangbijhautorisatie cascade ;
truncate autaut.toegangbijhautorisatie cascade ;
truncate autaut.his_bijhautorisatiesrtadmhnd cascade ;
truncate autaut.bijhautorisatiesrtadmhnd cascade ;
truncate autaut.his_bijhautorisatie cascade ;
truncate autaut.bijhautorisatie cascade ;

INSERT INTO autaut.bijhautorisatie(indmodelautorisatie, naam, datingang, indag) VALUES (true, 'Alles', 20010101, true);
INSERT INTO autaut.his_bijhautorisatie(bijhautorisatie, tsreg, naam, datingang) SELECT b.id, (now() at time zone 'UTC'), b.naam, b.datingang FROM autaut.bijhautorisatie b WHERE b.naam = 'Alles';

INSERT INTO autaut.bijhautorisatiesrtadmhnd (bijhautorisatie, srtadmhnd) SELECT b.id, sah.id FROM kern.srtadmhnd sah, autaut.bijhautorisatie b where b.naam = 'Alles';
INSERT INTO autaut.his_bijhautorisatiesrtadmhnd(bijhautorisatiesrtadmhnd, tsreg) SELECT bah.id, (now() at time zone 'UTC') from autaut.bijhautorisatiesrtadmhnd bah JOIN autaut.bijhautorisatie b ON b.id = bah.bijhautorisatie WHERE b.naam = 'Alles';

-- Gemeente Tiel tot administratieve handeling "Voltrekking huwelijk in nederland"
UPDATE kern.partij SET oin='00000001001101857000' WHERE naam='Gemeente Tiel';
insert into autaut.toegangbijhautorisatie (id,bijhautorisatie, geautoriseerde, ondertekenaar, transporteur, datingang, dateinde, indblok) values (3,(select id from autaut.bijhautorisatie b where b.naam = 'Alles'), (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente Tiel') and rol=2),(select id from kern.partij where naam = 'Gemeente Tiel'),(select id from kern.partij where naam = 'Gemeente Tiel'), 20000101, 20210828, null);
insert into autaut.his_toegangbijhautorisatie (id, toegangbijhautorisatie, tsreg, datingang, dateinde, indblok) values (3, 3, (now() at time zone 'UTC'), 20010101, 20210828, null);

-- Gemeente BRP 1 tot administratieve handeling "Voltrekking huwelijk in nederland"
insert into autaut.toegangbijhautorisatie (id,bijhautorisatie, geautoriseerde, ondertekenaar, transporteur, datingang, dateinde, indblok) values (4,(select id from autaut.bijhautorisatie b where b.naam = 'Alles'), (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2),(select id from kern.partij where  naam = 'Gemeente BRP 1'),(select id from kern.partij where naam = 'Gemeente BRP 1'), 20000101, 20210828, null);
insert into autaut.his_toegangbijhautorisatie (id, toegangbijhautorisatie, tsreg, datingang, dateinde, indblok) values (4, 4, (now() at time zone 'UTC'), 20010101, 20210828, null);

 -- Gemeente Amsterdam tot administratieve handeling "Voltrekking huwelijk in nederland"
UPDATE kern.partij SET oin='100000367' WHERE id = 367;
insert into autaut.toegangbijhautorisatie (id,bijhautorisatie, geautoriseerde, ondertekenaar, transporteur, datingang, dateinde, indblok) values (10,(select id from autaut.bijhautorisatie b where b.naam = 'Alles'), (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente Amsterdam') and rol=2),(select id from kern.partij where  naam = 'Gemeente Amsterdam'),(select id from kern.partij where naam = 'Gemeente Amsterdam'), 20000101, 20210828, null);
insert into autaut.his_toegangbijhautorisatie (id, toegangbijhautorisatie, tsreg, datingang, dateinde, indblok) values (10, 10, (now() at time zone 'UTC'), 20010101, 20210828, null);

-- Gemeente BRP 1 tot administratieve handeling "Voltrekking huwelijk in nederland" met ondertekenaar Gemeente BRP 2
insert into autaut.toegangbijhautorisatie (id,bijhautorisatie, geautoriseerde, ondertekenaar, transporteur, datingang, dateinde, indblok) values (5,(select id from autaut.bijhautorisatie b where b.naam = 'Alles'), (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2),(select id from kern.partij where  naam = 'Gemeente BRP 2'),(select id from kern.partij where naam = 'Gemeente BRP 1'), 20000101, 20210828, null);
insert into autaut.his_toegangbijhautorisatie (id, toegangbijhautorisatie, tsreg, datingang, dateinde, indblok) values (5, 5, (now() at time zone 'UTC'), 20010101, 20210828, null);

-- Gemeente BRP 1 tot administratieve handeling "Voltrekking huwelijk in nederland" met transporteur Gemeente BRP 2
insert into autaut.toegangbijhautorisatie (id,bijhautorisatie, geautoriseerde, ondertekenaar, transporteur, datingang, dateinde, indblok) values (6,(select id from autaut.bijhautorisatie b where b.naam = 'Alles'), (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2),(select id from kern.partij where  naam = 'Gemeente BRP 1'),(select id from kern.partij where naam = 'Gemeente BRP 2'), 20000101, 20210828, null);
insert into autaut.his_toegangbijhautorisatie (id, toegangbijhautorisatie, tsreg, datingang, dateinde, indblok) values (6, 6, (now() at time zone 'UTC'), 20010101, 20210828, null);

 -- Gemeente BRP 1 tot administratieve handeling "Aangaan geregistreerd partnerschap in Nederland"
-- insert into autaut.toegangbijhautorisatie (id,bijhautorisatie, geautoriseerde, ondertekenaar, transporteur, datingang, dateinde, indblok) values (7,(select id from autaut.bijhautorisatie b where b.naam = 'Alles'),(select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2),(select id from kern.partij where  naam = 'Gemeente BRP 1'),(select id from kern.partij where naam = 'Gemeente BRP 1'), 20000101, 20210828, null);
-- insert into autaut.his_toegangbijhautorisatie (id, toegangbijhautorisatie, tsreg, datingang, dateinde, indblok) values (7, 7, (now() at time zone 'UTC'), 20010101, 20210828, null);

-- Gemeente BRP 1 tot administratieve handeling "Aangaan geregistreerd partnerschap in Nederland" met ondertekenaar Gemeente BRP 2
-- insert into autaut.toegangbijhautorisatie (id,bijhautorisatie, geautoriseerde, ondertekenaar, transporteur, datingang, dateinde, indblok) values (8,(select id from autaut.bijhautorisatie b where b.naam = 'Alles'),(select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2),(select id from kern.partij where  naam = 'Gemeente BRP 2'),(select id from kern.partij where naam = 'Gemeente BRP 1'), 20000101, 20210828, null);
-- insert into autaut.his_toegangbijhautorisatie (id, toegangbijhautorisatie, tsreg, datingang, dateinde, indblok) values (8, 8, (now() at time zone 'UTC'), 20010101, 20210828, null);

-- Gemeente BRP 1 tot administratieve handeling "Aangaan geregistreerd partnerschap in Nederland" met transporteur Gemeente BRP 2
-- insert into autaut.toegangbijhautorisatie (id,bijhautorisatie, geautoriseerde, ondertekenaar, transporteur, datingang, dateinde, indblok) values (9,(select id from autaut.bijhautorisatie b where b.naam = 'Alles'),(select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2),(select id from kern.partij where  naam = 'Gemeente BRP 1'),(select id from kern.partij where naam = 'Gemeente BRP 2'), 20000101, 20210828, null);
-- insert into autaut.his_toegangbijhautorisatie (id, toegangbijhautorisatie, tsreg, datingang, dateinde, indblok) values (9, 9, (now() at time zone 'UTC'), 20010101, 20210828, null);

-- Gemeente BRP 1 tot administratieve handeling "Aangaan geregistreerd partnerschap in Nederland"
insert into autaut.toegangbijhautorisatie (id,bijhautorisatie, geautoriseerde, ondertekenaar, transporteur, datingang, dateinde, indblok) values (11,(select id from autaut.bijhautorisatie b where b.naam = 'Alles'),(select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2),NULL ,NULL , 20000101, 20210828, null);
insert into autaut.his_toegangbijhautorisatie (id, toegangbijhautorisatie, tsreg, datingang, dateinde, indblok) values (11, 11, (now() at time zone 'UTC'), 20010101,20210828,null);

-- Gemeente BRP 1 tot administratieve handeling "Voltrekking huwelijk in nederland"
-- insert into autaut.toegangbijhautorisatie (id,bijhautorisatie, geautoriseerde, ondertekenaar, transporteur, datingang, dateinde, indblok) values (12,(select id from autaut.bijhautorisatie b where b.naam = 'Alles'), (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2),(select id from kern.partij where  naam = 'Gemeente BRP 1'),(select id from kern.partij where naam = 'Gemeente BRP 1'), 20000101, 20210828, null);
-- insert into autaut.his_toegangbijhautorisatie (id, toegangbijhautorisatie, tsreg, datingang, dateinde, indblok) values (12, 12, (now() at time zone 'UTC'), 20010101, 20210828, null);

-- Gemeente BRP 1 tot administratieve handeling "OntbindingHuwelijk Nederland"
-- insert into autaut.toegangbijhautorisatie (id,bijhautorisatie, geautoriseerde, ondertekenaar, transporteur, datingang, dateinde, indblok) values (13,(select id from autaut.bijhautorisatie b where b.naam = 'Alles'),(select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2),NULL ,NULL , 20000101, 20210828, null);
-- insert into autaut.his_toegangbijhautorisatie (id, toegangbijhautorisatie, tsreg, datingang, dateinde, indblok) values (13, 13, (now() at time zone 'UTC'), 20010101,20210828,null);

-- Gemeente BRP 1 tot administratieve handeling "Huwelijk in Nederland" met ondertekenaar en transporteur Gemeente BRP 3
insert into autaut.toegangbijhautorisatie (id,bijhautorisatie, geautoriseerde, ondertekenaar, transporteur, datingang, dateinde, indblok) values (14,(select id from autaut.bijhautorisatie b where b.naam = 'Alles'),(select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2),(select id from kern.partij where naam = 'Gemeente BRP 3'),(select id from kern.partij where naam = 'Gemeente BRP 3') , 20000101, 20210828, null);
insert into autaut.his_toegangbijhautorisatie (id, toegangbijhautorisatie, tsreg, datingang, dateinde, indblok) values (14, 14, now(), 20010101,20210828,null);

-- Gemeente BRP 1 tot administratieve handeling "Beëindiging geregistreerd partnerschap in buitenland"
-- insert into autaut.toegangbijhautorisatie (id,bijhautorisatie, geautoriseerde, ondertekenaar, transporteur, datingang, dateinde, indblok) values (15,(select id from autaut.bijhautorisatie b where b.naam = 'Alles'),(select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2),NULL ,NULL , 20000101, 20210828, null);
-- insert into autaut.his_toegangbijhautorisatie (id, toegangbijhautorisatie, tsreg, datingang, dateinde, indblok) values (15, 15, now(), 20010101,20210828,null);

-- Gemeente BRP 2 tot administratieve handeling "Huwelijk in Nederland" met ondertekenaar en transporteur Gemeente BRP 2
insert into autaut.toegangbijhautorisatie (id,bijhautorisatie, geautoriseerde, ondertekenaar, transporteur, datingang, dateinde, indblok) values (16,(select id from autaut.bijhautorisatie b where b.naam = 'Alles'),(select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 2') and rol=2),(select id from kern.partij where naam = 'Gemeente BRP 2'),(select id from kern.partij where naam = 'Gemeente BRP 2') , 20000101, 20210828, null);
insert into autaut.his_toegangbijhautorisatie (id, toegangbijhautorisatie, tsreg, datingang, dateinde, indblok) values (16, 16, now(), 20010101,20210828,null);

-- Gemeente BRP 3 tot administratieve handeling "Huwelijk in Nederland" met ondertekenaar Gemeente BRP 3 en transporteur Gemeente BRP 1 handmatig fiat 1
insert into autaut.toegangbijhautorisatie (id,bijhautorisatie, geautoriseerde, ondertekenaar, transporteur, datingang, dateinde, indblok) values (17,(select id from autaut.bijhautorisatie b where b.naam = 'Alles'),(select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 3') and rol=2),(select id from kern.partij where naam = 'Gemeente BRP 3'),(select id from kern.partij where naam = 'Gemeente BRP handmatig fiat 1') , 20000101, 20210828, null);
insert into autaut.his_toegangbijhautorisatie (id, toegangbijhautorisatie, tsreg, datingang, dateinde, indblok) values (17, 17, now(), 20010101,20210828,null);

-- Gemeente voor autorisatietest tot administratieve handeling "Alles"
insert into autaut.toegangbijhautorisatie (id,bijhautorisatie, geautoriseerde, ondertekenaar, transporteur, datingang, dateinde, indblok) values (18,(select id from autaut.bijhautorisatie b where b.naam = 'Alles'),(select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente voor autorisatietest') and rol=2),null,null , 20000101, 20210828, null);
insert into autaut.his_toegangbijhautorisatie (id, toegangbijhautorisatie, tsreg, datingang, dateinde, indblok) values (18, 18, now(), 20010101,20210828,null);

UPDATE kern.partij SET oin='00000001001005650000' WHERE naam='Gemeente Haarlem';
UPDATE kern.partij SET oin='00000001001932603000' WHERE naam='Gemeente Olst';
UPDATE kern.partij SET oin='00000001001721926000' WHERE naam='Gemeente Alkmaar';
UPDATE kern.partij SET oin='00000001002220647000' WHERE naam='Gemeente Utrecht';

UPDATE kern.partij SET oin='00000001001569417000' WHERE naam='Gemeente Delft';
UPDATE kern.partij SET oin='34301' WHERE naam='Gemeente Stoutenburg';
UPDATE kern.partij SET oin='199901' WHERE naam='Minister';


COMMIT;

