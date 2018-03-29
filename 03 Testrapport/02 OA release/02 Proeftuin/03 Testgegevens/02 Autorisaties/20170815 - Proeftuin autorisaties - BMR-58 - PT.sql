-- Vulscript autorisaties
-- Bron: Leveringsautorisaties_RepresentatieveTestset_20160927.xls
-- Datum 15-08-2017
-- BMR-58
-- Proeftuin versie

-- N.a.v. wijzigingen proeftuin

-- Schonen tabellen voor herhaaldelijk kunnen uitvoeren script
/*
delete from AutAut.his_persafnemerindicatie;
delete from autaut.persafnemerindicatie;
delete from AutAut.His_ToegangBijhautorisatie;
delete from AutAut.ToegangBijhautorisatie;
delete from AutAut.dienstbundello3rubriek;
delete from AutAut.dienstbundelgroepattr;
delete from AutAut.dienstbundelgroep;
delete from AutAut.his_dienstattendering;
delete from AutAut.His_DienstSel;
delete from AutAut.His_DienstZoeken;
delete from AutAut.his_dienst;
delete from AutAut.dienst;
delete from AutAut.his_dienstbundel;
delete from AutAut.dienstbundel;
delete from AutAut.his_levsautorisatie;
delete from AutAut.his_toeganglevsautorisatie;
delete from AutAut.toeganglevsautorisatie;
delete from AutAut.levsautorisatie;
delete from AutAut.His_BijhautorisatieSrtAdmHnd;
delete from AutAut.BijhautorisatieSrtAdmHnd;
delete from AutAut.His_Bijhautorisatie;
delete from AutAut.His_ToegangBijhautorisatie;
delete from AutAut.ToegangBijhautorisatie;
delete from AutAut.Bijhautorisatie;
*/

-- Zorgen dat sequences goed staan
SELECT setval('AutAut.seq_Bijhautorisatie', 100000, false);
SELECT setval('AutAut.seq_BijhautorisatieSrtAdmHnd', 100000, false);
SELECT setval('AutAut.seq_BijhouderFiatuitz', 100000, false);
SELECT setval('AutAut.seq_Dienst', 100000, false);
SELECT setval('AutAut.seq_Dienstbundel', 100000, false);
SELECT setval('AutAut.seq_DienstbundelGroep', 100000, false);
SELECT setval('AutAut.seq_DienstbundelGroepAttr', 100000, false);
SELECT setval('AutAut.seq_DienstbundelLO3Rubriek', 100000, false);
SELECT setval('AutAut.seq_His_Bijhautorisatie', 100000, false);
SELECT setval('AutAut.seq_His_BijhautorisatieSrtAdmHnd', 100000, false);
SELECT setval('AutAut.seq_His_BijhouderFiatuitz', 100000, false);
SELECT setval('AutAut.seq_His_Dienst', 100000, false);
SELECT setval('AutAut.seq_His_DienstAttendering', 100000, false);
SELECT setval('AutAut.seq_His_DienstSel', 100000, false);
SELECT setval('AutAut.seq_His_DienstZoeken', 100000, false);
SELECT setval('AutAut.seq_His_Dienstbundel', 100000, false);
SELECT setval('AutAut.seq_His_Levsautorisatie', 100000, false);
SELECT setval('AutAut.seq_His_ToegangBijhautorisatie', 100000, false);
SELECT setval('AutAut.seq_His_ToegangLevsautorisatie', 100000, false);
SELECT setval('AutAut.seq_Levsautorisatie', 100000, false);
SELECT setval('AutAut.seq_ToegangBijhautorisatie', 100000, false);
SELECT setval('AutAut.seq_ToegangLevsautorisatie', 100000, false);

-- Bijwerken OIN's van zowel bijhouders als afnemers
update kern.partij set oin = '001401' where code = '001401';  
update kern.partij set oin = '008001' where code = '008001';
update kern.partij set oin = '0010601' where code = '010601';
update kern.partij set oin = '0014101' where code = '014101';
update kern.partij set oin = '0020001' where code = '020001';
update kern.partij set oin = '0059901' where code = '022801';
update kern.partij set oin = '0026801' where code = '026801';
update kern.partij set oin = '0034401' where code = '034401';
update kern.partij set oin = '0035501' where code = '035501';
update kern.partij set oin = '0036101' where code = '036101';
update kern.partij set oin = '0036303' where code = '036301';
update kern.partij set oin = '0039201' where code = '039201';
update kern.partij set oin = '0039401' where code = '039401';
update kern.partij set oin = '0040001' where code = '040001';
update kern.partij set oin = '0040101' where code = '050501';
update kern.partij set oin = '0050501' where code = '051801';
update kern.partij set oin = '0051801' where code = '059901';
update kern.partij set oin = '0063201' where code = '063201';
update kern.partij set oin = '0071801' where code = '071801';
update kern.partij set oin = '0075701' where code = '075701';
update kern.partij set oin = '0075801' where code = '075801';
update kern.partij set oin = '0077201' where code = '077201';
update kern.partij set oin = '0093501' where code = '093501';
update kern.partij set oin = '0099501' where code = '099501';
update kern.partij set oin = '0605101' where code = '605101';
update kern.partij set oin = '0613303' where code = '613303';
update kern.partij set oin = '0650201' where code = '650201';
update kern.partij set oin = '0808601' where code = '808601';
update kern.partij set oin = '0808602' where code = '808602';

-- Alle gemeentes zijn al opgenomen als Bijhoudingsorgaan College en de afnemers als afnemer. Alleen DatumIngang bijwerken
update Kern.PartijRol set DatIngang = 20150101
where DatIngang = 0 and Partij in  (
   select Id
   from Kern.Partij
   where OIN is not null and
      Code in ('001401','008001','010601','014101','020001','022801','026801','034401','035501','036101','036301','039201','039401','040001','050501','051801','059901','063201','071801','075701','075801','077201','093501','099501','605101','613303','650201','808601','808602','051301','066401','051201')
   );

update Kern.His_PartijRol set DatIngang = 20150101
where DatIngang = 0 and PartijRol in  (
   select PR.Id
   from Kern.Partij P
        join Kern.PartijRol PR ON P.ID = PR.Partij
   where P.OIN is not null and
      Code in ('001401','008001','010601','014101','020001','022801','026801','034401','035501','036101','036301','039201','039401','040001','050501','051801','059901','063201','071801','075701','075801','077201','093501','099501','605101','613303','650201','808601','808602','051301','066401','051201')
   );

-- Alle Gemeentes als Afnemer toevoegen, overige Afnemers zouden er al in moeten zitten, maar ervaring leert dat dit nog wel eens is aangepast
insert into Kern.PartijRol(Rol, Partij, DatIngang, IndAg)
   select 1, Id, 20150101, True
   from Kern.Partij P
   where OIN is not null
      and Code in ('001401','008001','010601','014101','020001','022801','026801','034401','035501','036101','036301','039201','039401','040001','050501','051801','059901','063201','071801','075701','075801','077201','093501','099501','605101','613303','650201','808601','808602','051301','066401','051201')
      and not exists(select Id from Kern.PartijRol where Partij = p.Id and Rol = 1);
 
insert into Kern.His_PartijRol (PartijRol, TsReg, DatIngang, DatEinde) 
   select ID, Now(), DatIngang, DatEinde 
   from Kern.PartijRol PR
   where Rol = 1 and
      Partij in (
         select Id
        from Kern.Partij
        where OIN is not null
           and Code in ('001401','008001','010601','014101','020001','022801','026801','034401','035501','036101','036301','039201','039401','040001','050501','051801','059901','063201','071801','075701','075801','077201','093501','099501','605101','613303','650201','808601','808602','051301','066401','051201'))
      and not exists(select id from Kern.His_PartijRol where PartijRol = PR.Id);
   
-- Partijen die gemeente zijn laten overgaan naar de BRP, met uitzondering van Gouda (051301), Goes (066401) en Gorinchem (051201)
update Kern.Partij set 
  DatOvergangNaarBRP = 20161001,
  IndAutoFiat = true
where
  OIN is not null
  and Id in (select Id from Kern.PartijRol where Rol = 2)
  and Code in ('001401','008001','010601','014101','020001','022801','026801','034401','035501','036101','036301','039201','039401','040001','050501','051801','059901','063201','071801','075701','075801','077201','093501','099501','605101','613303','650201','808601','808602','051301','066401','051201')
  and Code not in ('051301', '066401', '051201');

-- Patch voor alle Partijen die al wel een gezette IndAutoFiat hebben (gebeurt 'ergens' in andere scripts) maar geen gezette DatOvergangNaarBRP.
-- Dit geeft anders een foutmelding bij het vullen van de His_PartijBijhouding
update Kern.Partij set 
  DatOvergangNaarBRP = 20161001
where
  DatOvergangNaarBRP is null
  and IndAutoFiat is not null
  and Code in ('001401','008001','010601','014101','020001','022801','026801','034401','035501','036101','036301','039201','039401','040001','050501','051801','059901','063201','071801','075701','075801','077201','093501','099501','605101','613303','650201','808601','808602','051301','066401','051201');

update Kern.Partij set 
  IndAutoFiat = true
where
  DatOvergangNaarBRP is not null
  and IndAutoFiat is null
  and Code in ('001401','008001','010601','014101','020001','022801','026801','034401','035501','036101','036301','039201','039401','040001','050501','051801','059901','063201','071801','075701','075801','077201','093501','099501','0605101','613303','650201','808601','808602','051301','066401','051201');

insert into Kern.His_Partij (Partij, TsReg, Naam, DatIngang, DatEinde, OIN, Srt, IndVerstrbeperkingMogelijk, DatOvergangNaarBRP)
   select ID, Now(), Naam, DatIngang, DatEinde, OIN, Srt, IndVerstrbeperkingMogelijk, DatOvergangNaarBRP
   from Kern.Partij P
   where not exists(select id from Kern.His_Partij where Partij = P.Id);
update Kern.His_Partij hp set DatOvergangNaarBRP = (select DatOvergangNaarBRP from kern.partij where id = hp.partij)
where 
   DatOvergangNaarBRP is null
   and Partij in (select ID from kern.partij where DatOvergangNaarBRP is not null);

insert into Kern.His_PartijBijhouding (Partij, TsReg, IndAutoFiat)
   select ID, Now(), IndAutoFiat
   from Kern.Partij P
   where IndAutoFiat is not null 
      and not exists(select id from Kern.His_PartijBijhouding where Partij = P.Id);
  
-- ** Leveringsautorisaties
insert into AutAut.levsautorisatie (stelsel, indmodelautorisatie, naam, datingang, populatiebeperking, IndAliasSrtAdmHndLeveren, Protocolleringsniveau, IndAg) values
   (1, true, 'Gemeente als bijhouder', 20150101, 'WAAR', False, 1, True), -- 1
   (1, true, 'Gemeente als afnemer', 20150101, 'WAAR', False, 1, True), -- 2
   (1, false, 'Zwitserleven 808601', 20150101, 'WAAR', False, 1, True), -- 3
   (1, false, 'Zwitserleven 808602', 20150101, 'WAAR', False, 1, True), -- 4
   (1, false, 'Vektis CV 605101', 20150101, 'WAAR', False, 1, True), -- 5
   (1, false, 'Waarborgfonds EW/NHG 650201', 20150101, 'WAAR', False, 1, True), -- 6
   (1, false, 'GGD Gooi en vechtstreek 613303 - Afnemerindicatie', 20150101, 'WAAR', False, 1, True), -- 7
   (1, false, 'GGD Gooi en vechtstreek 613303 - Doelbinding', 20150101, 'WAAR', False, 1, True); -- 8
insert into AutAut.His_Levsautorisatie(Levsautorisatie, TsReg, naam, datingang, populatiebeperking, IndAliasSrtAdmHndLeveren, Protocolleringsniveau)
   select Id Levsautorisatie, Now(), naam, datingang, populatiebeperking, IndAliasSrtAdmHndLeveren, Protocolleringsniveau
   from AutAut.levsautorisatie
   where 
      stelsel = 1
      and Id >= 100000;
   
-- ** Dienstbundel
insert into AutAut.dienstbundel (levsautorisatie, naam, datingang, naderepopulatiebeperking, indag) values
   ((select id from AutAut.levsautorisatie where naam = 'Gemeente als bijhouder'), 'Gemeente Bijhouder Volledig', 20150101, 'WAAR', true), -- 1
   ((select id from AutAut.levsautorisatie where naam = 'Gemeente als afnemer'), 'Gemeente Afnemer Synchronisatie', 20150101, 'WAAR', true), -- 2
   ((select id from AutAut.levsautorisatie where naam = 'Gemeente als afnemer'), 'Gemeente Afnemer Bevraging Volledig', 20150101, 'WAAR', true), -- 3
   ((select id from AutAut.levsautorisatie where naam = 'Gemeente als afnemer'), 'Gemeente Afnemer Bevraging Beperkt', 20150101, 'WAAR', true), -- 4
   ((select id from AutAut.levsautorisatie where naam = 'Zwitserleven 808601'), 'Zwitserleven 808601 Synchronisatie', 20150101, 'WAAR', true), -- 5
   ((select id from AutAut.levsautorisatie where naam = 'Zwitserleven 808601'), 'Zwitserleven 808601 Bevraging', 20150101, 'WAAR', true), -- 6
   ((select id from AutAut.levsautorisatie where naam = 'Zwitserleven 808602'), 'Zwitserleven 808602 Synchronisatie', 20150101, 'WAAR', true), -- 7
   ((select id from AutAut.levsautorisatie where naam = 'Zwitserleven 808602'), 'Zwitserleven 808602 Bevraging', 20150101, 'WAAR', true), -- 8
   ((select id from AutAut.levsautorisatie where naam = 'Vektis CV 605101'), 'Vektis CV 605101 Synchronisatie', 20150101, 'WAAR', true), -- 9
   ((select id from AutAut.levsautorisatie where naam = 'Vektis CV 605101'), 'Vektis CV 605101 Bevraging', 20150101, 'WAAR', true), -- 10
   ((select id from AutAut.levsautorisatie where naam = 'Waarborgfonds EW/NHG 650201'), 'Waarborgfonds EW-NHG 650201 Synchronisatie', 20150101, 'WAAR', true), -- 11
   ((select id from AutAut.levsautorisatie where naam = 'Waarborgfonds EW/NHG 650201'), 'Waarborgfonds EW-NHG 650201 Bevraging', 20150101, 'WAAR', true), -- 12
   ((select id from AutAut.levsautorisatie where naam = 'GGD Gooi en vechtstreek 613303 - Afnemerindicatie'), 'GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 20150101, 'WAAR', true), -- 13
   ((select id from AutAut.levsautorisatie where naam = 'GGD Gooi en vechtstreek 613303 - Afnemerindicatie'), 'GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 20150101, 'WAAR', true), -- 14
   ((select id from AutAut.levsautorisatie where naam = 'GGD Gooi en vechtstreek 613303 - Doelbinding'), 'GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 20150101, 'WAAR', true), -- 15
   ((select id from AutAut.levsautorisatie where naam = 'GGD Gooi en vechtstreek 613303 - Doelbinding'), 'GGD Gooi en vechtstreek 613303 Bevraging - Doel', 20150101, 'WAAR', true); -- 16
insert into AutAut.His_Dienstbundel(Dienstbundel, TsReg, naam, datingang, naderepopulatiebeperking)
   select db.ID Levsautorisatie, Now(), db.naam, db.datingang, db.naderepopulatiebeperking
   from AutAut.dienstbundel db
        join AutAut.levsautorisatie la on db.levsautorisatie = la.id
   where
      la.stelsel = 1
      and la.Id >= 100000;

   
-- ** Diensten
insert into AutAut.dienst (dienstbundel, srt, datingang, indag, maxaantalzoekresultaten, indagzoeken) values
   ((select id from AutAut.dienstbundel where naam = 'Gemeente Bijhouder Volledig'), 1, 20150101, true, 100, true), -- 1
   ((select id from AutAut.dienstbundel where naam = 'Gemeente Bijhouder Volledig'), 5, 20150101, true, 100, true), -- 2
   ((select id from AutAut.dienstbundel where naam = 'Gemeente Bijhouder Volledig'), 6, 20150101, true, 100, true), -- 3
   ((select id from AutAut.dienstbundel where naam = 'Gemeente Bijhouder Volledig'), 7, 20150101, true, 100, true), -- 4
   ((select id from AutAut.dienstbundel where naam = 'Gemeente Bijhouder Volledig'), 8, 20150101, true, 100, true), -- 5
   ((select id from AutAut.dienstbundel where naam = 'Gemeente Bijhouder Volledig'), 9, 20150101, true, 100, true), -- 6
   ((select id from AutAut.dienstbundel where naam = 'Gemeente Bijhouder Volledig'), 10, 20150101, true, 100, true), -- 7
   ((select id from AutAut.dienstbundel where naam = 'Gemeente Bijhouder Volledig'), 11, 20150101, true, 100, true); -- 8
   
insert into AutAut.dienst (dienstbundel, srt, datingang, indag, maxaantalzoekresultaten, indagzoeken) values
   ((select id from AutAut.dienstbundel where naam = 'Gemeente Afnemer Synchronisatie'), 2, 20150101, true, 100, true), -- 9
   ((select id from AutAut.dienstbundel where naam = 'Gemeente Afnemer Synchronisatie'), 3, 20150101, true, 100, true), -- 10
   ((select id from AutAut.dienstbundel where naam = 'Gemeente Afnemer Synchronisatie'), 20, 20150101, true, 100, true), -- 11
   ((select id from AutAut.dienstbundel where naam = 'Gemeente Afnemer Synchronisatie'), 9, 20150101, true, 100, true), -- 12
   ((select id from AutAut.dienstbundel where naam = 'Gemeente Afnemer Synchronisatie'), 10, 20150101, true, 100, true), -- 13
   ((select id from AutAut.dienstbundel where naam = 'Gemeente Afnemer Synchronisatie'), 11, 20150101, true, 100, true); -- 14
   
insert into AutAut.dienst (dienstbundel, srt, datingang, indag, maxaantalzoekresultaten, indagzoeken) values
   ((select id from AutAut.dienstbundel where naam = 'Gemeente Afnemer Bevraging Volledig'), 5, 20150101, true, 100, true), -- 15
   ((select id from AutAut.dienstbundel where naam = 'Gemeente Afnemer Bevraging Volledig'), 6, 20150101, true, 100, true), -- 16
   ((select id from AutAut.dienstbundel where naam = 'Gemeente Afnemer Bevraging Volledig'), 7, 20150101, true, 100, true), -- 17
   ((select id from AutAut.dienstbundel where naam = 'Gemeente Afnemer Bevraging Volledig'), 8, 20150101, true, 100, true); -- 18
   
insert into AutAut.dienst (dienstbundel, srt, datingang, indag, maxaantalzoekresultaten, indagzoeken) values
   ((select id from AutAut.dienstbundel where naam = 'Gemeente Afnemer Bevraging Beperkt'), 5, 20150101, true, 100, true), -- 19
   ((select id from AutAut.dienstbundel where naam = 'Gemeente Afnemer Bevraging Beperkt'), 6, 20150101, true, 100, true), -- 20
   ((select id from AutAut.dienstbundel where naam = 'Gemeente Afnemer Bevraging Beperkt'), 8, 20150101, true, 100, true); -- 21
   
insert into AutAut.dienst (dienstbundel, srt, datingang, indag, maxaantalzoekresultaten, indagzoeken) values
   ((select id from AutAut.dienstbundel where naam = 'Zwitserleven 808601 Synchronisatie'), 2, 20150101, true, 100, true), -- 22
   ((select id from AutAut.dienstbundel where naam = 'Zwitserleven 808601 Synchronisatie'), 3, 20150101, true, 100, true), -- 23
   ((select id from AutAut.dienstbundel where naam = 'Zwitserleven 808601 Synchronisatie'), 20, 20150101, true, 100, true), -- 24
   ((select id from AutAut.dienstbundel where naam = 'Zwitserleven 808601 Synchronisatie'), 9, 20150101, true, 100, true), -- 25
   ((select id from AutAut.dienstbundel where naam = 'Zwitserleven 808601 Synchronisatie'), 10, 20150101, true, 100, true), -- 26
   ((select id from AutAut.dienstbundel where naam = 'Zwitserleven 808601 Synchronisatie'), 11, 20150101, true, 100, true); -- 27
   
insert into AutAut.dienst (dienstbundel, srt, datingang, indag, maxaantalzoekresultaten, indagzoeken) values
   ((select id from AutAut.dienstbundel where naam = 'Zwitserleven 808601 Bevraging'), 5, 20150101, true, 100, true), -- 28
   ((select id from AutAut.dienstbundel where naam = 'Zwitserleven 808601 Bevraging'), 6, 20150101, true, 100, true), -- 29
   ((select id from AutAut.dienstbundel where naam = 'Zwitserleven 808601 Bevraging'), 8, 20150101, true, 100, true); -- 30

insert into AutAut.dienst (dienstbundel, srt, datingang, indag, maxaantalzoekresultaten, indagzoeken) values
   ((select id from AutAut.dienstbundel where naam = 'Zwitserleven 808602 Synchronisatie'), 2, 20150101, true, 100, true), -- 31
   ((select id from AutAut.dienstbundel where naam = 'Zwitserleven 808602 Synchronisatie'), 3, 20150101, true, 100, true), -- 32
   ((select id from AutAut.dienstbundel where naam = 'Zwitserleven 808602 Synchronisatie'), 20, 20150101, true, 100, true), -- 33
   ((select id from AutAut.dienstbundel where naam = 'Zwitserleven 808602 Synchronisatie'), 9, 20150101, true, 100, true), -- 34
   ((select id from AutAut.dienstbundel where naam = 'Zwitserleven 808602 Synchronisatie'), 10, 20150101, true, 100, true), -- 35
   ((select id from AutAut.dienstbundel where naam = 'Zwitserleven 808602 Synchronisatie'), 11, 20150101, true, 100, true); -- 36

insert into AutAut.dienst (dienstbundel, srt, datingang, indag, maxaantalzoekresultaten, indagzoeken) values
   ((select id from AutAut.dienstbundel where naam = 'Zwitserleven 808602 Bevraging'), 5, 20150101, true, 100, true), -- 37
   ((select id from AutAut.dienstbundel where naam = 'Zwitserleven 808602 Bevraging'), 6, 20150101, true, 100, true), -- 38
   ((select id from AutAut.dienstbundel where naam = 'Zwitserleven 808602 Bevraging'), 8, 20150101, true, 100, true); -- 39

insert into AutAut.dienst (dienstbundel, srt, datingang, indag, maxaantalzoekresultaten, indagzoeken) values
   ((select id from AutAut.dienstbundel where naam = 'Vektis CV 605101 Synchronisatie'), 2, 20150101, true, 100, true), -- 40
   ((select id from AutAut.dienstbundel where naam = 'Vektis CV 605101 Synchronisatie'), 3, 20150101, true, 100, true), -- 41
   ((select id from AutAut.dienstbundel where naam = 'Vektis CV 605101 Synchronisatie'), 20, 20150101, true, 100, true), -- 42
   ((select id from AutAut.dienstbundel where naam = 'Vektis CV 605101 Synchronisatie'), 9, 20150101, true, 100, true), -- 43
   ((select id from AutAut.dienstbundel where naam = 'Vektis CV 605101 Synchronisatie'), 10, 20150101, true, 100, true), -- 44
   ((select id from AutAut.dienstbundel where naam = 'Vektis CV 605101 Synchronisatie'), 11, 20150101, true, 100, true); -- 45

insert into AutAut.dienst (dienstbundel, srt, datingang, indag, maxaantalzoekresultaten, indagzoeken) values
   ((select id from AutAut.dienstbundel where naam = 'Vektis CV 605101 Bevraging'), 5, 20150101, true, 100, true), -- 46
   ((select id from AutAut.dienstbundel where naam = 'Vektis CV 605101 Bevraging'), 6, 20150101, true, 100, true), -- 47
   ((select id from AutAut.dienstbundel where naam = 'Vektis CV 605101 Bevraging'), 8, 20150101, true, 100, true); -- 48

insert into AutAut.dienst (dienstbundel, srt, datingang, indag, maxaantalzoekresultaten, indagzoeken) values
   ((select id from AutAut.dienstbundel where naam = 'Waarborgfonds EW-NHG 650201 Synchronisatie'), 2, 20150101, true, 100, true), -- 49
   ((select id from AutAut.dienstbundel where naam = 'Waarborgfonds EW-NHG 650201 Synchronisatie'), 3, 20150101, true, 100, true), -- 50
   ((select id from AutAut.dienstbundel where naam = 'Waarborgfonds EW-NHG 650201 Synchronisatie'), 20, 20150101, true, 100, true), -- 51
   ((select id from AutAut.dienstbundel where naam = 'Waarborgfonds EW-NHG 650201 Synchronisatie'), 9, 20150101, true, 100, true), -- 52
   ((select id from AutAut.dienstbundel where naam = 'Waarborgfonds EW-NHG 650201 Synchronisatie'), 10, 20150101, true, 100, true), -- 53
   ((select id from AutAut.dienstbundel where naam = 'Waarborgfonds EW-NHG 650201 Synchronisatie'), 11, 20150101, true, 100, true); -- 54

insert into AutAut.dienst (dienstbundel, srt, datingang, indag, maxaantalzoekresultaten, indagzoeken) values
   ((select id from AutAut.dienstbundel where naam = 'Waarborgfonds EW-NHG 650201 Bevraging'), 5, 20150101, true, 100, true), -- 55
   ((select id from AutAut.dienstbundel where naam = 'Waarborgfonds EW-NHG 650201 Bevraging'), 6, 20150101, true, 100, true), -- 56
   ((select id from AutAut.dienstbundel where naam = 'Waarborgfonds EW-NHG 650201 Bevraging'), 8, 20150101, true, 100, true); -- 57

insert into AutAut.dienst (dienstbundel, srt, datingang, indag, maxaantalzoekresultaten, indagzoeken) values
   ((select id from AutAut.dienstbundel where naam = 'GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd'), 2, 20150101, true, 100, true), -- 58
   ((select id from AutAut.dienstbundel where naam = 'GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd'), 3, 20150101, true, 100, true), -- 59
   ((select id from AutAut.dienstbundel where naam = 'GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd'), 20, 20150101, true, 100, true), -- 60
   ((select id from AutAut.dienstbundel where naam = 'GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd'), 9, 20150101, true, 100, true), -- 61
   ((select id from AutAut.dienstbundel where naam = 'GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd'), 10, 20150101, true, 100, true), -- 62
   ((select id from AutAut.dienstbundel where naam = 'GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd'), 11, 20150101, true, 100, true); -- 63

insert into AutAut.dienst (dienstbundel, srt, datingang, indag, maxaantalzoekresultaten, indagzoeken) values
   ((select id from AutAut.dienstbundel where naam = 'GGD Gooi en vechtstreek 613303 Bevraging - AfnInd'), 5, 20150101, true, 100, true), -- 64
   ((select id from AutAut.dienstbundel where naam = 'GGD Gooi en vechtstreek 613303 Bevraging - AfnInd'), 6, 20150101, true, 100, true), -- 65
   ((select id from AutAut.dienstbundel where naam = 'GGD Gooi en vechtstreek 613303 Bevraging - AfnInd'), 8, 20150101, true, 100, true); -- 66

insert into AutAut.dienst (dienstbundel, srt, datingang, indag, maxaantalzoekresultaten, indagzoeken) values
   ((select id from AutAut.dienstbundel where naam = 'GGD Gooi en vechtstreek 613303 Synchronisatie - Doel'), 1, 20150101, true, 100, true), -- 67
   ((select id from AutAut.dienstbundel where naam = 'GGD Gooi en vechtstreek 613303 Synchronisatie - Doel'), 9, 20150101, true, 100, true), -- 68
   ((select id from AutAut.dienstbundel where naam = 'GGD Gooi en vechtstreek 613303 Synchronisatie - Doel'), 10, 20150101, true, 100, true), -- 69
   ((select id from AutAut.dienstbundel where naam = 'GGD Gooi en vechtstreek 613303 Synchronisatie - Doel'), 11, 20150101, true, 100, true); -- 70

insert into AutAut.dienst (dienstbundel, srt, datingang, indag, maxaantalzoekresultaten, indagzoeken) values
   ((select id from AutAut.dienstbundel where naam = 'GGD Gooi en vechtstreek 613303 Bevraging - Doel'), 5, 20150101, true, 100, true), -- 71
   ((select id from AutAut.dienstbundel where naam = 'GGD Gooi en vechtstreek 613303 Bevraging - Doel'), 6, 20150101, true, 100, true), -- 72
   ((select id from AutAut.dienstbundel where naam = 'GGD Gooi en vechtstreek 613303 Bevraging - Doel'), 8, 20150101, true, 100, true); -- 73

insert into AutAut.his_dienst (Dienst, TsReg, DatIngang)
   select d.id, Now(), d.DatIngang
   from AutAut.Dienst d
        join AutAut.Dienstbundel db on d.dienstbundel = db.id
        join AutAut.levsautorisatie la on db.levsautorisatie = la.id
   where
      la.stelsel = 1
      and la.Id >= 100000;
   
insert into AutAut.his_dienstzoeken (Dienst, TsReg, maxaantalzoekresultaten)
   select d.id, Now(), d.maxaantalzoekresultaten
   from AutAut.Dienst d
        join AutAut.Dienstbundel db on d.dienstbundel = db.id
        join AutAut.levsautorisatie la on db.levsautorisatie = la.id
   where
     la.stelsel = 1
     and maxaantalzoekresultaten is not null
     and la.Id >= 100000;

-- ** Leveringsautorisaties

-- Tijdelijke structuur opzetten voor afleverpunten om deze niet per Toegang te copy/pasten.
create table tmpAfleverpunt (
   Naam Text,
   URL Text
   );
insert into TmpAfleverPunt(Naam, URL) values
/*
   ('Centric-1', 'http://tools-afnemervoorbeeld:8080/afnemervoorbeeld/BrpBerichtVerwerkingService/VerwerkPersoon'),
   ('Exxellence-1', 'http://tools-afnemervoorbeeld:8080/afnemervoorbeeld/BrpBerichtVerwerkingService/VerwerkPersoon'),
   ('Gemboxx-1', 'http://tools-afnemervoorbeeld:8080/afnemervoorbeeld/BrpBerichtVerwerkingService/VerwerkPersoon'),
   ('PinkRoccade-1', 'http://tools-afnemervoorbeeld:8080/afnemervoorbeeld/BrpBerichtVerwerkingService/VerwerkPersoon'),
   ('Procura-1', 'http://tools-afnemervoorbeeld:8080/afnemervoorbeeld/BrpBerichtVerwerkingService/VerwerkPersoon'),
   ('T&T-1', 'http://tools-afnemervoorbeeld:8080/afnemervoorbeeld/BrpBerichtVerwerkingService/VerwerkPersoon'),
   ('T&T-2', 'http://tools-afnemervoorbeeld:8080/afnemervoorbeeld/BrpBerichtVerwerkingService/VerwerkPersoon'),
   ('oBRP-1', 'http://tools-afnemervoorbeeld:8080/afnemervoorbeeld/BrpBerichtVerwerkingService/VerwerkPersoon');
*/
   ('Centric-1', 'http://leverenpiv.centric.eu/TEST/SynchronisatieVerwerking.svc'),
   ('Exxellence-1', 'http://srv02.jnc.nl:7080/opentunnel/12332112332112332112/brp/leveren'),
   ('Gemboxx-1', 'http://gemboxxbrp.grexx.net/brpBerichtVerwerker.svc'),
   ('PinkRoccade-1', 'http://urk.aquima.dev.pinkprivatecloud.nl/gaas-aquima-server/BrpBerichtVerwerkingService'),
   ('Procura-1', 'http://stufui.osano.nl/axis2/services/StUFService.StUFServiceHttpSoap11Endpoint/ontvangKennisgevingAdministratieveHandeling'),
   ('T&T-1', 'http://test.competent.nl/ws/brp/berichtontvangst/brpberichtverwerkingservice/1'),
   ('T&T-2', 'http://demo.competent.nl/ws/brp/berichtontvangst/brpberichtverwerkingservice/1'),
   ('oBRP-1', 'http://192.168.216.40:8080/brp-levering-services-afnemer-voorbeeld/BrpBerichtVerwerkingService');

   
-- Tijdelijke structuur opzetten voor combinatie Partijd / Afleverpunt
create table tmpAfleverpuntPartij (
   Afleverpunt Text,
   PartijCode  Char(6),
   PartijID  Smallint
   );
insert into tmpAfleverpuntPartij (PartijCode, Afleverpunt) values
   ('036101', 'oBRP-1'),
   ('014101', 'PinkRoccade-1'),
   ('036301', 'oBRP-1'),
   ('020001', 'Centric-1'),
   ('010601', 'Procura-1'),
   ('075701', 'Gemboxx-1'),
   ('075801', 'T&T-1'),
   ('040001', 'Exxellence-1'),
   ('050501', 'T&T-1'),
   ('022801', 'PinkRoccade-1'),
   ('077201', 'Gemboxx-1'),
   ('001401', 'PinkRoccade-1'),
   ('039201', 'Procura-1'),
   ('039401', 'PinkRoccade-1'),
   ('008001', 'Gemboxx-1'),
   ('099501', 'Exxellence-1'),
   ('093501', 'Exxellence-1'),
   ('026801', 'T&T-1'),
   ('059901', 'Centric-1'),
   ('051801', 'oBRP-1'),
   ('034401', 'Gemboxx-1'),
   ('071801', 'Procura-1'),
   ('063201', 'Gemboxx-1'),
   ('035501', 'Centric-1'),
   ('613303', 'T&T-1'),
   ('605101', 'T&T-1'),
   ('650201', 'T&T-1'),
   ('808601', 'T&T-1'),
   ('808602', 'T&T-1');
-- PartijCodes mappen op ID
update tmpAfleverpuntPartij tap set PartijID = (select id from kern.partij p where p.code = tap.Partijcode);

-- Functie om een ToegangLevsAutorisatie toe te voegen.
-- OndertekenaarCode en TransporteurCode optioneel (=Partijcode)
CREATE OR REPLACE FUNCTION tmpToegangLeveringsautorisatie(LevAutorisatieNaam Text, aPartijCode Char(6), aPartijRol integer, aOndertekenaarCode Char(6), aTransporteurCode Char(6), aNaderepopulatiebeperking Text)
 RETURNS void AS
   $$
   DECLARE lOndertekenaarID Integer;
   DECLARE lTransporteurID Integer;
   BEGIN
   --SELECT ID INTO lOndertekenaarID FROM Kern.Partij WHERE (aOndertekenaarCode IS NULL AND Code = aPartijCode) OR (aOndertekenaarCode IS NOT NULL AND Code = aOndertekenaarCode);
   --SELECT ID INTO lTransporteurID FROM Kern.Partij WHERE (aTransporteurCode IS NULL AND Code = aPartijCode) OR (aTransporteurCode IS NOT NULL AND Code = aTransporteurCode);
   lOndertekenaarID := NULL;
   lTransporteurID := NULL;
      
   insert into AutAut.ToeganglevsAutorisatie(LevsAutorisatie, Geautoriseerde, Ondertekenaar, Transporteur, DatIngang, Afleverpunt, Naderepopulatiebeperking, IndAg) values
   ( 
      (select id from AutAut.levsautorisatie where naam = LevAutorisatieNaam),
      -- Geautoriseerde
      (select pr.id from kern.partijrol pr join kern.partij p on pr.partij = p.id where p.code = aPartijCode and pr.rol = aPartijrol),
      lOndertekenaarID,
      lTransporteurID,
      20150101,
      (select url from tmpAfleverpuntPartij tap join tmpAfleverpunt ta on tap.afleverpunt = ta.naam where PartijCode = aPartijCode),
      aNaderepopulatiebeperking,
	  True
   );
   END
   $$ LANGUAGE plpgsql;


-- Opvoeren ToegangLeveringsautorisaties
select tmpToegangLeveringsautorisatie('Gemeente als bijhouder', '001401', 2, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="001401"');
select tmpToegangLeveringsautorisatie('Gemeente als bijhouder', '008001', 2, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="008001"');
select tmpToegangLeveringsautorisatie('Gemeente als bijhouder', '010601', 2, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="010601"');
select tmpToegangLeveringsautorisatie('Gemeente als bijhouder', '014101', 2, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="014101"');
select tmpToegangLeveringsautorisatie('Gemeente als bijhouder', '020001', 2, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="020001"');
select tmpToegangLeveringsautorisatie('Gemeente als bijhouder', '022801', 2, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="022801"');
select tmpToegangLeveringsautorisatie('Gemeente als bijhouder', '026801', 2, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="026801"');
select tmpToegangLeveringsautorisatie('Gemeente als bijhouder', '034401', 2, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="034401"');
select tmpToegangLeveringsautorisatie('Gemeente als bijhouder', '035501', 2, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="035501"');
select tmpToegangLeveringsautorisatie('Gemeente als bijhouder', '036101', 2, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="036101"');
select tmpToegangLeveringsautorisatie('Gemeente als bijhouder', '036301', 2, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="036301"');
select tmpToegangLeveringsautorisatie('Gemeente als bijhouder', '039201', 2, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="039201"');
select tmpToegangLeveringsautorisatie('Gemeente als bijhouder', '039401', 2, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="039401"');
select tmpToegangLeveringsautorisatie('Gemeente als bijhouder', '040001', 2, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="040001"');
select tmpToegangLeveringsautorisatie('Gemeente als bijhouder', '050501', 2, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="050501"');
select tmpToegangLeveringsautorisatie('Gemeente als bijhouder', '051801', 2, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Gemeente als bijhouder', '059901', 2, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="059901"');
select tmpToegangLeveringsautorisatie('Gemeente als bijhouder', '063201', 2, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="063201"');
select tmpToegangLeveringsautorisatie('Gemeente als bijhouder', '071801', 2, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="071801"');
select tmpToegangLeveringsautorisatie('Gemeente als bijhouder', '075701', 2, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="075701"');
select tmpToegangLeveringsautorisatie('Gemeente als bijhouder', '075801', 2, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="075801"');
select tmpToegangLeveringsautorisatie('Gemeente als bijhouder', '077201', 2, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="077201"');
select tmpToegangLeveringsautorisatie('Gemeente als bijhouder', '093501', 2, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="093501"');
select tmpToegangLeveringsautorisatie('Gemeente als bijhouder', '099501', 2, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="099501"');
select tmpToegangLeveringsautorisatie('Gemeente als afnemer', '001401', 1, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="001401"');
select tmpToegangLeveringsautorisatie('Gemeente als afnemer', '008001', 1, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="008001"');
select tmpToegangLeveringsautorisatie('Gemeente als afnemer', '010601', 1, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="010601"');
select tmpToegangLeveringsautorisatie('Gemeente als afnemer', '014101', 1, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="014101"');
select tmpToegangLeveringsautorisatie('Gemeente als afnemer', '020001', 1, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="020001"');
select tmpToegangLeveringsautorisatie('Gemeente als afnemer', '022801', 1, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="022801"');
select tmpToegangLeveringsautorisatie('Gemeente als afnemer', '026801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Gemeente als afnemer', '034401', 1, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="034401"');
select tmpToegangLeveringsautorisatie('Gemeente als afnemer', '035501', 1, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="035501"');
select tmpToegangLeveringsautorisatie('Gemeente als afnemer', '036101', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Gemeente als afnemer', '036301', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Gemeente als afnemer', '039201', 1, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="039201"');
select tmpToegangLeveringsautorisatie('Gemeente als afnemer', '039401', 1, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="039401"');
select tmpToegangLeveringsautorisatie('Gemeente als afnemer', '040001', 1, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="040001"');
select tmpToegangLeveringsautorisatie('Gemeente als afnemer', '050501', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Gemeente als afnemer', '051801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Gemeente als afnemer', '059901', 1, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="059901"');
select tmpToegangLeveringsautorisatie('Gemeente als afnemer', '063201', 1, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="063201"');
select tmpToegangLeveringsautorisatie('Gemeente als afnemer', '071801', 1, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="071801"');
select tmpToegangLeveringsautorisatie('Gemeente als afnemer', '075701', 1, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="075701"');
select tmpToegangLeveringsautorisatie('Gemeente als afnemer', '075801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Gemeente als afnemer', '077201', 1, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="077201"');
select tmpToegangLeveringsautorisatie('Gemeente als afnemer', '093501', 1, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="093501"');
select tmpToegangLeveringsautorisatie('Gemeente als afnemer', '099501', 1, null, null, 'WAAR');--'Persoon.Bijhouding.Partijcode="099501"');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '001401', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '008001', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '010601', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '014101', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '020001', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '022801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '026801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '034401', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '035501', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '036101', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '036301', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '039201', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '039401', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '040001', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '050501', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '051801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '059901', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '063201', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '071801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '075701', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '075801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '077201', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '093501', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '099501', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '001401', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '008001', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '010601', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '014101', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '020001', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '022801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '026801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '034401', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '035501', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '036101', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '036301', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '039201', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '039401', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '040001', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '050501', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '051801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '059901', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '063201', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '071801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '075701', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '075801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '077201', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '093501', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '099501', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '001401', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '008001', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '010601', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '014101', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '020001', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '022801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '026801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '034401', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '035501', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '036101', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '036301', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '039201', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '039401', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '040001', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '050501', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '051801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '059901', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '063201', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '071801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '075701', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '075801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '077201', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '093501', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '099501', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '001401', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '008001', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '010601', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '014101', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '020001', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '022801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '026801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '034401', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '035501', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '036101', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '036301', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '039201', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '039401', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '040001', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '050501', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '051801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '059901', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '063201', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '071801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '075701', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '075801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '077201', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '093501', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '099501', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '001401', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '008001', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '010601', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '014101', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '020001', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '022801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '026801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '034401', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '035501', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '036101', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '036301', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '039201', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '039401', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '040001', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '050501', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '051801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '059901', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '063201', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '071801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '075701', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '075801', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '077201', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '093501', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '099501', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Vektis CV 605101', '605101', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Afnemerindicatie', '613303', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('GGD Gooi en vechtstreek 613303 - Doelbinding', '613303', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808601', '808601', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Zwitserleven 808602', '808602', 1, null, null, 'WAAR');
select tmpToegangLeveringsautorisatie('Waarborgfonds EW/NHG 650201', '650201', 1, null, null, 'WAAR');

-- His_ToegangLevsautorisatie aanmaken
insert into AutAut.His_ToegangLevsautorisatie (ToegangLevsautorisatie, TsReg, DatIngang, NaderePopulatiebeperking, Afleverpunt)
   select Tla.Id, Now(), Tla.DatIngang, Tla.NaderePopulatiebeperking, Tla.Afleverpunt
   from AutAut.ToegangLevsAutorisatie tla
        join AutAut.levsautorisatie la on tla.levsautorisatie = la.id
   where
     la.stelsel = 1
     and la.Id >= 100000;

-- ** Dienstbundel Gemeente Bijhouder Volledig
-- Alle groepen toevoegen
insert into AutAut.dienstbundelgroep(dienstbundel, groep, indformelehistorie, indmaterielehistorie, indverantwoording)
select 
   (select id from AutAut.Dienstbundel where naam = 'Gemeente Bijhouder Volledig'),
   id,
   historiepatroon like '%F%' IndFormeleHistorie, 
   historiepatroon like '%M%' IndMaterieleHistorie, 
   historiepatroon like '%F%' IndVerantwoording
from kern.element  g
where srt = 2
   -- Groepen met 'Structuur' en/of 'Via groepsautorisatie' elementen zijn ook relevant
   and exists(select * from kern.element a where a.groep = g.id and a.autorisatie in (1,3,4,5,6,7,8));

-- Attributen, optionele attributen voor materiele historie zitten hier wel in (dus dubbel)
insert into AutAut.dienstbundelgroepattr(dienstbundelgroep, attr)
select dbg.id, a.id
from 
   AutAut.dienstbundelgroep dbg
   join kern.element a on dbg.groep = a.groep
where
   dbg.dienstbundel = (select id from AutAut.Dienstbundel where naam = 'Gemeente Bijhouder Volledig')
   and a.autorisatie in (3,5,6,7,8);


-- Functie om eenvoudig een DienstbundelGroep met Attributen toe te voegen
-- aAttributen: NULL = alle attributen, anders attribuutnamen komma gescheiden opnemen
CREATE OR REPLACE FUNCTION tmpDienstbundelGroepAttr(aDienstbundelNaam Text, aGroepNaam Text, aindformelehistorie boolean, aindmaterielehistorie boolean, aindverantwoording boolean, aAttributen Text)
 RETURNS void AS
   $$
   DECLARE lGroepID Integer;
   DECLARE lDienstBundelGroepID Integer;
   DECLARE lIndFormeleHistorie Boolean;
   DECLARE lIndMaterieleHistorie Boolean;
   DECLARE lIndVerantwoording Boolean;
   DECLARE lAttribuutNaam Text;
   BEGIN
   SELECT ID, historiepatroon like '%F%' IndFormeleHistorie, historiepatroon like '%M%' IndMaterieleHistorie, historiepatroon like '%F%' IndVerantwoording
   INTO lGroepID, lIndFormeleHistorie, lIndMaterieleHistorie, lIndVerantwoording
   FROM Kern.Element
   WHERE
      Naam = aGroepNaam;
   IF NOT FOUND THEN
      RAISE EXCEPTION 'Groep % niet gevonden.', aGroepNaam;
   END IF;

   IF aIndFormeleHistorie AND NOT lIndFormeleHistorie THEN
      RAISE EXCEPTION 'Formele historie fout';
   END IF;
   IF aIndMaterieleHistorie AND NOT lIndMaterieleHistorie THEN
      RAISE EXCEPTION 'Materiele historie fout';
   END IF;
   IF aIndVErantwoording AND NOT lIndVerantwoording THEN
      RAISE EXCEPTION 'Verantwoording fout';
   END IF;

   insert into AutAut.dienstbundelgroep(dienstbundel, groep, indformelehistorie, indmaterielehistorie, indverantwoording)
   values ((select id from AutAut.Dienstbundel where Naam = aDienstbundelNaam), lGroepID, aindformelehistorie, aindmaterielehistorie, aindverantwoording)
   returning id into lDienstBundelGroepID;

   IF aAttributen IS NULL THEN
      insert into AutAut.dienstbundelgroepattr(dienstbundelgroep, attr)
      select lDienstBundelGroepID, a.id
      from 
         kern.element a
      where
         a.groep = lGroepID
         and a.autorisatie in (3,5,6,8);
   ELSE
      FOREACH lAttribuutNaam IN ARRAY string_to_array(aAttributen, ',')
      LOOP
         insert into AutAut.dienstbundelgroepattr(dienstbundelgroep, attr)
         select lDienstBundelGroepID, a.id
         from 
            kern.element a
         where
            a.groep = lGroepID
            and a.autorisatie in (3,5,6,8)
            and a.naam = replace(aGroepNaam, '.Standaard', '') || '.' || lAttribuutNaam;
         IF NOT FOUND THEN
            RAISE EXCEPTION 'Attribuut % niet gevonden.', lAttribuutNaam;
         END IF;
      END LOOP;
   END IF;
   END
  $$ LANGUAGE plpgsql;

-- ** Dienstbundel Gemeente als Afnemer Synchronisatie
select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'Persoon.Identificatienummers', false, true, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'Persoon.SamengesteldeNaam', false, true, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'Persoon.Geboorte', false, false, false, 'Datum');
select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'Persoon.Geslachtsaanduiding', false, false, false, 'Code');
select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'Persoon.Bijhouding', false, false, false, 'PartijCode,BijhoudingsaardCode,NadereBijhoudingsaardCode');

select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'Persoon.Overlijden', false, false, false, 'Datum');
select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'Persoon.Naamgebruik', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'Persoon.Migratie', false, false, false, 'DatumAanvangGeldigheid,SoortCode');

select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'Persoon.Voornaam.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'Persoon.Voornaam.Standaard', false, true, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'Persoon.Geslachtsnaamcomponent.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'Persoon.Geslachtsnaamcomponent.Standaard', false, true, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'Persoon.Adres.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'Persoon.Adres.Standaard', false, false, false, 'SoortCode,DatumAanvangAdreshouding,IdentificatiecodeAdresseerbaarObject,IdentificatiecodeNummeraanduiding,GemeenteCode,NaamOpenbareRuimte,AfgekorteNaamOpenbareRuimte,Gemeentedeel,Huisnummer,Huisletter,Huisnummertoevoeging,Postcode,Woonplaatsnaam,LocatieTenOpzichteVanAdres,Locatieomschrijving,IndicatiePersoonAangetroffenOpAdres');
select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Standaard', false, false, false, null);

select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'Huwelijk.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'Huwelijk.Standaard', false, false, false, 'DatumAanvang,DatumEinde,LandGebiedEindeCode');
select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'GeregistreerdPartnerschap.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'GeregistreerdPartnerschap.Standaard', false, false, false, 'DatumAanvang,DatumEinde,LandGebiedEindeCode');

select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'GerelateerdeGeregistreerdePartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam', false, false, false, 'Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'GerelateerdeHuwelijkspartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam', false, false, false, 'Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');

select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'Onderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'Onderzoek.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'GegevenInOnderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Synchronisatie', 'ActieBron.Identiteit', false, false, false, null);


-- ** Dienstbundel Gemeente als Afnemer Bevraging volledig
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.AfgeleidAdministratief', false, false, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Identificatienummers', false, true, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.SamengesteldeNaam', false, true, true, null);

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Geboorte', false, false, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Geslachtsaanduiding', false, true, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Inschrijving', false, false, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Nummerverwijzing', false, true, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Bijhouding', false, true, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Overlijden', false, false, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Naamgebruik', false, false, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Migratie', false, true, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Verblijfsrecht', false, false, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.UitsluitingKiesrecht', false, false, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.DeelnameEUVerkiezingen', false, false, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Persoonskaart', false, false, true, null);

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Voornaam.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Voornaam.Standaard', false, true, true, null);

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Geslachtsnaamcomponent.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Geslachtsnaamcomponent.Standaard', false, true, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Adres.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Adres.Standaard', false, true, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Nationaliteit.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Nationaliteit.Standaard', false, true, true, null);

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Indicatie.DerdeHeeftGezag.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Indicatie.DerdeHeeftGezag.Standaard', false, true, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Indicatie.OnderCuratele.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Indicatie.OnderCuratele.Standaard', false, true, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Standaard', false, false, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Indicatie.VastgesteldNietNederlander.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Indicatie.VastgesteldNietNederlander.Standaard', false, true, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Indicatie.BehandeldAlsNederlander.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Indicatie.BehandeldAlsNederlander.Standaard', false, true, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Indicatie.SignaleringMetBetrekkingTotVerstrekkenReisdocument.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Indicatie.SignaleringMetBetrekkingTotVerstrekkenReisdocument.Standaard', false, false, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Indicatie.Staatloos.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Indicatie.Staatloos.Standaard', false, true, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Indicatie.BijzondereVerblijfsrechtelijkePositie.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Indicatie.BijzondereVerblijfsrechtelijkePositie.Standaard', false, false, true, null);

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Verstrekkingsbeperking.Identiteit', false, false, true, null);

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'FamilierechtelijkeBetrekking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'FamilierechtelijkeBetrekking.Standaard', false, false, true, null);

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Ouder.Identiteit', false, false, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GerelateerdeOuder.Identiteit', false, false, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GerelateerdeOuder.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GerelateerdeOuder.Persoon.Identificatienummers', false, true, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GerelateerdeOuder.Persoon.SamengesteldeNaam', false, true, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GerelateerdeOuder.Persoon.Geboorte', false, false, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GerelateerdeOuder.Persoon.Geslachtsaanduiding', false, false, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GerelateerdeOuder.Ouderschap', false, true, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GerelateerdeOuder.OuderlijkGezag', false, true, true, null);

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Kind.Identiteit', false, false, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GerelateerdeKind.Identiteit', false, false, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GerelateerdeKind.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GerelateerdeKind.Persoon.Identificatienummers', false, true, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GerelateerdeKind.Persoon.SamengesteldeNaam', false, true, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GerelateerdeKind.Persoon.Geboorte', false, false, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Ouder.Ouderschap', false, true, true, null);

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Huwelijk.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Huwelijk.Standaard', false, false, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GeregistreerdPartnerschap.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GeregistreerdPartnerschap.Standaard', false, false, true, null);

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GerelateerdeGeregistreerdePartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers', false, true, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam', false, true, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GerelateerdeGeregistreerdePartner.Persoon.Geboorte', false, false, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding', false, true, true, null);

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GerelateerdeHuwelijkspartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GerelateerdeHuwelijkspartner.Persoon.Identificatienummers', false, true, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam', false, true, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GerelateerdeHuwelijkspartner.Persoon.Geboorte', false, false, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding', false, true, true, null);

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Reisdocument.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Reisdocument.Standaard', false, false, true, null);

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Verificatie.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Persoon.Verificatie.Standaard', false, false, true, null);

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Onderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Onderzoek.Standaard', false, false, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'GegevenInOnderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'ActieBron.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Volledig', 'Document.Identiteit', false, false, false, null);

-- ** Dienstbundel Gemeente Afnemer Bevraging Beperkt
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Identificatienummers', false, true, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.SamengesteldeNaam', false, true, false, null);

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Geboorte', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Geslachtsaanduiding', false, false, false, 'Code');

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Bijhouding', false, true, false, 'PartijCode,BijhoudingsaardCode,NadereBijhoudingsaardCode');
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Overlijden', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Naamgebruik', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Migratie', false, false, false, 'DatumAanvangGeldigheid,SoortCode,LandGebiedCode,BuitenlandsAdresRegel1,BuitenlandsAdresRegel2,BuitenlandsAdresRegel3,BuitenlandsAdresRegel4,BuitenlandsAdresRegel5,BuitenlandsAdresRegel6');
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Verblijfsrecht', false, false, false, 'AanduidingCode,DatumAanvang,DatumVoorzienEinde');

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Voornaam.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Voornaam.Standaard', false, true, false, null);

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Geslachtsnaamcomponent.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Geslachtsnaamcomponent.Standaard', false, true, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Adres.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Adres.Standaard', false, true, false, 'DatumAanvangGeldigheid,SoortCode,DatumAanvangAdreshouding,IdentificatiecodeAdresseerbaarObject,IdentificatiecodeNummeraanduiding,GemeenteCode,NaamOpenbareRuimte,AfgekorteNaamOpenbareRuimte,Gemeentedeel,Huisnummer,Huisletter,Huisnummertoevoeging,Postcode,Woonplaatsnaam,LocatieTenOpzichteVanAdres,Locatieomschrijving,IndicatiePersoonAangetroffenOpAdres,LandGebiedCode,BuitenlandsAdresRegel1,BuitenlandsAdresRegel2,BuitenlandsAdresRegel3,BuitenlandsAdresRegel4,BuitenlandsAdresRegel5,BuitenlandsAdresRegel6');
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Nationaliteit.Identiteit', false, false, false, null);

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Indicatie.DerdeHeeftGezag.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Indicatie.DerdeHeeftGezag.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Indicatie.OnderCuratele.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Indicatie.OnderCuratele.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Indicatie.BehandeldAlsNederlander.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Indicatie.BehandeldAlsNederlander.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Indicatie.Staatloos.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Indicatie.Staatloos.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Indicatie.VastgesteldNietNederlander.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Persoon.Indicatie.VastgesteldNietNederlander.Standaard', false, false, false, null);


select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'FamilierechtelijkeBetrekking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'FamilierechtelijkeBetrekking.Standaard', false, false, false, null);

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Ouder.Identiteit', false, false, true, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'GerelateerdeOuder.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'GerelateerdeOuder.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'GerelateerdeOuder.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'GerelateerdeOuder.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,Voornamen,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'GerelateerdeOuder.Persoon.Geboorte', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'GerelateerdeOuder.OuderlijkGezag', false, false, false, 'IndicatieOuderHeeftGezag');

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Kind.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'GerelateerdeKind.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'GerelateerdeKind.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'GerelateerdeKind.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'GerelateerdeKind.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,PredicaatCode,Voornamen,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'GerelateerdeKind.Persoon.Geboorte', false, false, false, null);

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Huwelijk.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Huwelijk.Standaard', false, false, false, 'DatumAanvang,DatumEinde,LandGebiedEindeCode');
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'GeregistreerdPartnerschap.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'GeregistreerdPartnerschap.Standaard', false, false, false, 'DatumAanvang,DatumEinde,LandGebiedEindeCode');

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'GerelateerdeGeregistreerdePartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,PredicaatCode,Voornamen,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'GerelateerdeGeregistreerdePartner.Persoon.Geboorte', false, false, false, null);

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'GerelateerdeHuwelijkspartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'GerelateerdeHuwelijkspartner.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,PredicaatCode,Voornamen,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'GerelateerdeHuwelijkspartner.Persoon.Geboorte', false, false, false, null);

select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Onderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'Onderzoek.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'GegevenInOnderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Gemeente Afnemer Bevraging Beperkt', 'ActieBron.Identiteit', false, false, false, null);


-- ** Zwitserleven 808601 Synchronisatie
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,PredicaatCode,Voornamen,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'Persoon.Geboorte', false, false, false, 'Datum');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'Persoon.Geslachtsaanduiding', false, false, false, 'Code');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'Persoon.Bijhouding', false, false, false, 'PartijCode,BijhoudingsaardCode,NadereBijhoudingsaardCode');

select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'Persoon.Overlijden', false, false, false, 'Datum');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'Persoon.Naamgebruik', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'Persoon.Migratie', false, false, false, 'DatumAanvangGeldigheid,SoortCode,LandGebiedCode,BuitenlandsAdresRegel1,BuitenlandsAdresRegel2,BuitenlandsAdresRegel3,BuitenlandsAdresRegel4,BuitenlandsAdresRegel5,BuitenlandsAdresRegel6');

select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'Persoon.Voornaam.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'Persoon.Voornaam.Standaard', false, false, false, 'Naam');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'Persoon.Geslachtsnaamcomponent.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'Persoon.Geslachtsnaamcomponent.Standaard', false, false, false, 'PredicaatCode,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Stam');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'Persoon.Adres.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'Persoon.Adres.Standaard', false, false, false, 'SoortCode,DatumAanvangAdreshouding,IdentificatiecodeAdresseerbaarObject,IdentificatiecodeNummeraanduiding,GemeenteCode,NaamOpenbareRuimte,AfgekorteNaamOpenbareRuimte,Gemeentedeel,Huisnummer,Huisletter,Huisnummertoevoeging,Postcode,Woonplaatsnaam,LocatieTenOpzichteVanAdres,Locatieomschrijving,IndicatiePersoonAangetroffenOpAdres,LandGebiedCode,BuitenlandsAdresRegel1,BuitenlandsAdresRegel2,BuitenlandsAdresRegel3,BuitenlandsAdresRegel4,BuitenlandsAdresRegel5,BuitenlandsAdresRegel6');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Standaard', false, false, false, null);

select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'FamilierechtelijkeBetrekking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'FamilierechtelijkeBetrekking.Standaard', false, false, false, null);

select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'Kind.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'GerelateerdeKind.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'GerelateerdeKind.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'GerelateerdeKind.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'GerelateerdeKind.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,PredicaatCode,Voornamen,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'GerelateerdeKind.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'Huwelijk.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'Huwelijk.Standaard', false, false, false, 'DatumAanvang,RedenEindeCode,DatumEinde');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'GeregistreerdPartnerschap.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'GeregistreerdPartnerschap.Standaard', false, false, false, 'DatumAanvang,RedenEindeCode,DatumEinde');

select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'GerelateerdeGeregistreerdePartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,PredicaatCode,Voornamen,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'GerelateerdeGeregistreerdePartner.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'GerelateerdeHuwelijkspartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'GerelateerdeHuwelijkspartner.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,PredicaatCode,Voornamen,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'GerelateerdeHuwelijkspartner.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'Onderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'Onderzoek.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'GegevenInOnderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Synchronisatie', 'ActieBron.Identiteit', false, false, false, null);


-- ** Dienstbundel Zwitserleven 808601 Bevraging
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.Identificatienummers', false, true, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.SamengesteldeNaam', false, true, false, null);

select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.Geboorte', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.Geslachtsaanduiding', false, true, false, null);

select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.Bijhouding', false, true, false, 'PartijCode,BijhoudingsaardCode,NadereBijhoudingsaardCode');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.Overlijden', false, false, false, 'Datum');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.Naamgebruik', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.Migratie', false, true, false, 'DatumAanvangGeldigheid,SoortCode,LandGebiedCode,BuitenlandsAdresRegel1,BuitenlandsAdresRegel2,BuitenlandsAdresRegel3,BuitenlandsAdresRegel4,BuitenlandsAdresRegel5,BuitenlandsAdresRegel6');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.Persoonskaart', false, false, false, null);

select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.Voornaam.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.Voornaam.Standaard', false, true, false, null);

select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.Geslachtsnaamcomponent.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.Geslachtsnaamcomponent.Standaard', false, true, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.Adres.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.Adres.Standaard', false, true, false, 'DatumAanvangGeldigheid,DatumAanvangAdreshouding,IdentificatiecodeAdresseerbaarObject,IdentificatiecodeNummeraanduiding,GemeenteCode,NaamOpenbareRuimte,AfgekorteNaamOpenbareRuimte,Gemeentedeel,Huisnummer,Huisletter,Huisnummertoevoeging,Postcode,Woonplaatsnaam,LocatieTenOpzichteVanAdres,Locatieomschrijving,IndicatiePersoonAangetroffenOpAdres,LandGebiedCode,BuitenlandsAdresRegel1,BuitenlandsAdresRegel2,BuitenlandsAdresRegel3,BuitenlandsAdresRegel4,BuitenlandsAdresRegel5,BuitenlandsAdresRegel6');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.Nationaliteit.Identiteit', false, false, false, null);

select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.Indicatie.Staatloos.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.Indicatie.Staatloos.Standaard', false, false, false, 'Waarde');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.Indicatie.VastgesteldNietNederlander.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.Indicatie.VastgesteldNietNederlander.Standaard', false, false, false, 'Waarde');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.Indicatie.BehandeldAlsNederlander.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Persoon.Indicatie.BehandeldAlsNederlander.Standaard', false, false, false, 'Waarde');


select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'FamilierechtelijkeBetrekking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'FamilierechtelijkeBetrekking.Standaard', false, false, false, null);

select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Kind.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'GerelateerdeKind.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'GerelateerdeKind.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'GerelateerdeKind.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'GerelateerdeKind.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,PredicaatCode,Voornamen,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'GerelateerdeKind.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Huwelijk.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Huwelijk.Standaard', false, false, false, 'DatumAanvang,RedenEindeCode,DatumEinde');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'GeregistreerdPartnerschap.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'GeregistreerdPartnerschap.Standaard', false, false, false, 'DatumAanvang,RedenEindeCode,DatumEinde');

select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'GerelateerdeGeregistreerdePartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,PredicaatCode,Voornamen,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'GerelateerdeGeregistreerdePartner.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'GerelateerdeHuwelijkspartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'GerelateerdeHuwelijkspartner.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,PredicaatCode,Voornamen,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'GerelateerdeHuwelijkspartner.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Onderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'Onderzoek.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'GegevenInOnderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808601 Bevraging', 'ActieBron.Identiteit', false, false, false, null);



-- ** Zwitserleven 808602 Synchronisatie
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,PredicaatCode,Voornamen,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'Persoon.Geboorte', false, false, false, 'Datum');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'Persoon.Geslachtsaanduiding', false, false, false, 'Code');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'Persoon.Bijhouding', false, false, false, 'PartijCode,BijhoudingsaardCode,NadereBijhoudingsaardCode');

select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'Persoon.Overlijden', false, false, false, 'Datum');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'Persoon.Naamgebruik', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'Persoon.Migratie', false, false, false, 'DatumAanvangGeldigheid,SoortCode,LandGebiedCode,BuitenlandsAdresRegel1,BuitenlandsAdresRegel2,BuitenlandsAdresRegel3,BuitenlandsAdresRegel4,BuitenlandsAdresRegel5,BuitenlandsAdresRegel6');

select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'Persoon.Voornaam.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'Persoon.Voornaam.Standaard', false, false, false, 'Naam');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'Persoon.Geslachtsnaamcomponent.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'Persoon.Geslachtsnaamcomponent.Standaard', false, false, false, 'PredicaatCode,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Stam');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'Persoon.Adres.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'Persoon.Adres.Standaard', false, false, false, 'SoortCode,DatumAanvangAdreshouding,IdentificatiecodeAdresseerbaarObject,IdentificatiecodeNummeraanduiding,GemeenteCode,NaamOpenbareRuimte,AfgekorteNaamOpenbareRuimte,Gemeentedeel,Huisnummer,Huisletter,Huisnummertoevoeging,Postcode,Woonplaatsnaam,LocatieTenOpzichteVanAdres,Locatieomschrijving,IndicatiePersoonAangetroffenOpAdres,LandGebiedCode,BuitenlandsAdresRegel1,BuitenlandsAdresRegel2,BuitenlandsAdresRegel3,BuitenlandsAdresRegel4,BuitenlandsAdresRegel5,BuitenlandsAdresRegel6');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Standaard', false, false, false, null);

select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'Huwelijk.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'Huwelijk.Standaard', false, false, false, 'DatumAanvang,RedenEindeCode,DatumEinde');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'GeregistreerdPartnerschap.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'GeregistreerdPartnerschap.Standaard', false, false, false, 'DatumAanvang,RedenEindeCode,DatumEinde');

select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'GerelateerdeGeregistreerdePartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,PredicaatCode,Voornamen,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'GerelateerdeGeregistreerdePartner.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'GerelateerdeHuwelijkspartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'GerelateerdeHuwelijkspartner.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,PredicaatCode,Voornamen,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'GerelateerdeHuwelijkspartner.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'Onderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'Onderzoek.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'GegevenInOnderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Synchronisatie', 'ActieBron.Identiteit', false, false, false, null);


/* Dienstbundel Zwitserleven 808602 Bevraging */
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Persoon.Identificatienummers', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Persoon.SamengesteldeNaam', false, true, false, null);

select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Persoon.Geboorte', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Persoon.Geslachtsaanduiding', false, true, false, null);

select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Persoon.Bijhouding', false, true, false, 'PartijCode,BijhoudingsaardCode,NadereBijhoudingsaardCode');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Persoon.Overlijden', false, false, false, 'Datum');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Persoon.Naamgebruik', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Persoon.Migratie', false, false, false, 'DatumAanvangGeldigheid,SoortCode,LandGebiedCode,BuitenlandsAdresRegel1,BuitenlandsAdresRegel2,BuitenlandsAdresRegel3,BuitenlandsAdresRegel4,BuitenlandsAdresRegel5,BuitenlandsAdresRegel6');

select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Persoon.Voornaam.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Persoon.Voornaam.Standaard', false, true, false, null);

select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Persoon.Geslachtsnaamcomponent.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Persoon.Geslachtsnaamcomponent.Standaard', false, true, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Persoon.Adres.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Persoon.Adres.Standaard', false, true, false, 'DatumAanvangGeldigheid,DatumAanvangAdreshouding,IdentificatiecodeAdresseerbaarObject,IdentificatiecodeNummeraanduiding,GemeenteCode,NaamOpenbareRuimte,AfgekorteNaamOpenbareRuimte,Gemeentedeel,Huisnummer,Huisletter,Huisnummertoevoeging,Postcode,Woonplaatsnaam,LocatieTenOpzichteVanAdres,Locatieomschrijving,IndicatiePersoonAangetroffenOpAdres,LandGebiedCode,BuitenlandsAdresRegel1,BuitenlandsAdresRegel2,BuitenlandsAdresRegel3,BuitenlandsAdresRegel4,BuitenlandsAdresRegel5,BuitenlandsAdresRegel6');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Persoon.Nationaliteit.Identiteit', false, false, false, null);

select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Persoon.Indicatie.Staatloos.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Persoon.Indicatie.Staatloos.Standaard', false, false, false, 'Waarde');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Persoon.Indicatie.BehandeldAlsNederlander.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Persoon.Indicatie.BehandeldAlsNederlander.Standaard', false, false, false, 'Waarde');


select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'FamilierechtelijkeBetrekking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'FamilierechtelijkeBetrekking.Standaard', false, false, false, null);

select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Kind.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'GerelateerdeKind.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'GerelateerdeKind.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'GerelateerdeKind.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'GerelateerdeKind.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,Voornamen,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'GerelateerdeKind.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Huwelijk.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Huwelijk.Standaard', false, false, false, 'DatumAanvang,RedenEindeCode,DatumEinde');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'GeregistreerdPartnerschap.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'GeregistreerdPartnerschap.Standaard', false, false, false, 'DatumAanvang,RedenEindeCode,DatumEinde');

select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'GerelateerdeGeregistreerdePartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,PredicaatCode,Voornamen,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'GerelateerdeGeregistreerdePartner.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'GerelateerdeHuwelijkspartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'GerelateerdeHuwelijkspartner.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,PredicaatCode,Voornamen,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'GerelateerdeHuwelijkspartner.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Onderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'Onderzoek.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'GegevenInOnderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Zwitserleven 808602 Bevraging', 'ActieBron.Identiteit', false, false, false, null);



-- ** Vektis CV 605101 Synchronisatie
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,Voornamen,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Geboorte', false, false, false, 'Datum');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Geslachtsaanduiding', false, false, false, 'Code');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Bijhouding', false, false, false, 'PartijCode,BijhoudingsaardCode,NadereBijhoudingsaardCode');

select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Overlijden', false, false, false, 'Datum');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Naamgebruik', false, false, false, 'Code,Voornamen,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Migratie', false, false, false, 'DatumAanvangGeldigheid,SoortCode,LandGebiedCode,BuitenlandsAdresRegel1,BuitenlandsAdresRegel2,BuitenlandsAdresRegel3,BuitenlandsAdresRegel4,BuitenlandsAdresRegel5,BuitenlandsAdresRegel6');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Verblijfsrecht', false, false, false, 'AanduidingCode,DatumAanvang,DatumVoorzienEinde');

select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Voornaam.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Voornaam.Standaard', false, false, false, 'Naam');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Geslachtsnaamcomponent.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Geslachtsnaamcomponent.Standaard', false, false, false, 'Voorvoegsel,Scheidingsteken,Stam');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Adres.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Adres.Standaard', false, false, false, 'SoortCode,DatumAanvangAdreshouding,IdentificatiecodeAdresseerbaarObject,IdentificatiecodeNummeraanduiding,GemeenteCode,NaamOpenbareRuimte,AfgekorteNaamOpenbareRuimte,Gemeentedeel,Huisnummer,Huisletter,Huisnummertoevoeging,Postcode,Woonplaatsnaam,LocatieTenOpzichteVanAdres,Locatieomschrijving,IndicatiePersoonAangetroffenOpAdres,LandGebiedCode,BuitenlandsAdresRegel1,BuitenlandsAdresRegel2,BuitenlandsAdresRegel3,BuitenlandsAdresRegel4,BuitenlandsAdresRegel5,BuitenlandsAdresRegel6');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Nationaliteit.Identiteit', false, false, false, null);

select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Indicatie.DerdeHeeftGezag.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Indicatie.DerdeHeeftGezag.Standaard', false, false, false, 'Waarde');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Indicatie.OnderCuratele.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Indicatie.OnderCuratele.Standaard', false, false, false, 'Waarde');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Indicatie.BehandeldAlsNederlander.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Indicatie.BehandeldAlsNederlander.Standaard', false, true, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Indicatie.Staatloos.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Indicatie.Staatloos.Standaard', false, true, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Indicatie.VastgesteldNietNederlander.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Persoon.Indicatie.VastgesteldNietNederlander.Standaard', false, false, false, 'Waarde');

select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'FamilierechtelijkeBetrekking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'FamilierechtelijkeBetrekking.Standaard', false, false, false, null);

select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Kind.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'GerelateerdeKind.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'GerelateerdeKind.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'GerelateerdeKind.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'GerelateerdeKind.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Huwelijk.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Huwelijk.Standaard', false, false, false, 'DatumAanvang,DatumEinde');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'GeregistreerdPartnerschap.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'GeregistreerdPartnerschap.Standaard', false, false, false, 'DatumAanvang,DatumEinde');

select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'GerelateerdeGeregistreerdePartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,Voornamen,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'GerelateerdeGeregistreerdePartner.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'GerelateerdeHuwelijkspartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'GerelateerdeHuwelijkspartner.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,Voornamen,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'GerelateerdeHuwelijkspartner.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Onderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'Onderzoek.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'GegevenInOnderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Synchronisatie', 'ActieBron.Identiteit', false, false, false, null);


-- ** Vektis CV 605101 Bevraging
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Identificatienummers', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.SamengesteldeNaam', false, false, false, 'DatumAanvangGeldigheid,IndicatieNamenreeks,Voornamen,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');

select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Geboorte', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Geslachtsaanduiding', false, false, false, null);

select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Bijhouding', false, true, false, 'PartijCode,BijhoudingsaardCode,NadereBijhoudingsaardCode');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Overlijden', false, false, false, 'Datum');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Naamgebruik', false, false, false, 'Code,Voornamen,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Migratie', false, false, false, 'DatumAanvangGeldigheid,SoortCode,LandGebiedCode,BuitenlandsAdresRegel1,BuitenlandsAdresRegel2,BuitenlandsAdresRegel3,BuitenlandsAdresRegel4,BuitenlandsAdresRegel5,BuitenlandsAdresRegel6');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Verblijfsrecht', false, false, false, 'AanduidingCode,DatumAanvang,DatumVoorzienEinde');

select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Voornaam.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Voornaam.Standaard', false, false, false, null);

select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Geslachtsnaamcomponent.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Geslachtsnaamcomponent.Standaard', false, false, false, 'DatumAanvangGeldigheid,Voorvoegsel,Scheidingsteken,Stam');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Adres.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Adres.Standaard', false, true, false, 'DatumAanvangAdreshouding,IdentificatiecodeAdresseerbaarObject,IdentificatiecodeNummeraanduiding,GemeenteCode,NaamOpenbareRuimte,AfgekorteNaamOpenbareRuimte,Gemeentedeel,Huisnummer,Huisletter,Huisnummertoevoeging,Postcode,Woonplaatsnaam,LocatieTenOpzichteVanAdres,Locatieomschrijving,IndicatiePersoonAangetroffenOpAdres,LandGebiedCode,BuitenlandsAdresRegel1,BuitenlandsAdresRegel2,BuitenlandsAdresRegel3,BuitenlandsAdresRegel4,BuitenlandsAdresRegel5,BuitenlandsAdresRegel6');

select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Nationaliteit.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Nationaliteit.Standaard', false, true, false, '');

select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Indicatie.DerdeHeeftGezag.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Indicatie.DerdeHeeftGezag.Standaard', true, false, true, 'Waarde');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Indicatie.OnderCuratele.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Indicatie.OnderCuratele.Standaard', false, false, true, 'Waarde');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Indicatie.BehandeldAlsNederlander.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Indicatie.BehandeldAlsNederlander.Standaard', false, true, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Indicatie.Staatloos.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Indicatie.Staatloos.Standaard', false, true, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Indicatie.VastgesteldNietNederlander.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Persoon.Indicatie.VastgesteldNietNederlander.Standaard', false, false, false, 'Waarde');

select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'FamilierechtelijkeBetrekking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'FamilierechtelijkeBetrekking.Standaard', false, false, false, null);

select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Ouder.Identiteit', false, false, true, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'GerelateerdeOuder.Identiteit', false, false, true, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'GerelateerdeOuder.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'GerelateerdeOuder.Persoon.Identificatienummers', false, true, true, 'Burgerservicenummer,Administratienummer');

select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Kind.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'GerelateerdeKind.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'GerelateerdeKind.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'GerelateerdeKind.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'GerelateerdeKind.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Huwelijk.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Huwelijk.Standaard', false, false, false, 'DatumAanvang,DatumEinde');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'GeregistreerdPartnerschap.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'GeregistreerdPartnerschap.Standaard', false, false, false, 'DatumAanvang,DatumEinde');

select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'GerelateerdeGeregistreerdePartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,Voornamen,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'GerelateerdeGeregistreerdePartner.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'GerelateerdeHuwelijkspartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'GerelateerdeHuwelijkspartner.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,Voornamen,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'GerelateerdeHuwelijkspartner.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Onderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'Onderzoek.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'GegevenInOnderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Vektis CV 605101 Bevraging', 'ActieBron.Identiteit', false, false, false, null);

-- ** Waarborgfonds EW-NHG 650201 Synchronisatie
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'Persoon.Identificatienummers', false, false, false, 'Administratienummer');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,PredicaatCode,Voornamen,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'Persoon.Geboorte', false, false, false, 'Datum,GemeenteCode,Woonplaatsnaam,BuitenlandsePlaats,BuitenlandseRegio,OmschrijvingLocatie');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'Persoon.Geslachtsaanduiding', false, false, false, 'Code');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'Persoon.Bijhouding', false, false, false, 'PartijCode,BijhoudingsaardCode,NadereBijhoudingsaardCode');

select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'Persoon.Overlijden', false, false, false, 'Datum');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'Persoon.Naamgebruik', false, false, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'Persoon.Migratie', false, false, false, 'DatumAanvangGeldigheid,SoortCode,LandGebiedCode,BuitenlandsAdresRegel1,BuitenlandsAdresRegel2,BuitenlandsAdresRegel3,BuitenlandsAdresRegel4,BuitenlandsAdresRegel5,BuitenlandsAdresRegel6');

select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'Persoon.Voornaam.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'Persoon.Voornaam.Standaard', false, false, false, 'Naam');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'Persoon.Geslachtsnaamcomponent.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'Persoon.Geslachtsnaamcomponent.Standaard', false, false, false, 'PredicaatCode,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Stam');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'Persoon.Adres.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'Persoon.Adres.Standaard', false, false, false, 'SoortCode,DatumAanvangAdreshouding,IdentificatiecodeAdresseerbaarObject,IdentificatiecodeNummeraanduiding,GemeenteCode,NaamOpenbareRuimte,AfgekorteNaamOpenbareRuimte,Gemeentedeel,Huisnummer,Huisletter,Huisnummertoevoeging,Postcode,Woonplaatsnaam,LocatieTenOpzichteVanAdres,Locatieomschrijving,IndicatiePersoonAangetroffenOpAdres,LandGebiedCode,BuitenlandsAdresRegel1,BuitenlandsAdresRegel2,BuitenlandsAdresRegel3,BuitenlandsAdresRegel4,BuitenlandsAdresRegel5,BuitenlandsAdresRegel6');

select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Standaard', false, false, false, null);

select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'Huwelijk.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'Huwelijk.Standaard', false, false, false, 'DatumAanvang,DatumEinde');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'GeregistreerdPartnerschap.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'GeregistreerdPartnerschap.Standaard', false, false, false, 'DatumAanvang,DatumEinde');

select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'GerelateerdeGeregistreerdePartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers', false, false, false, 'Administratienummer');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'GerelateerdeGeregistreerdePartner.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'GerelateerdeHuwelijkspartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'GerelateerdeHuwelijkspartner.Persoon.Identificatienummers', false, false, false, 'Administratienummer');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'GerelateerdeHuwelijkspartner.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'Onderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'Onderzoek.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'GegevenInOnderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Synchronisatie', 'ActieBron.Identiteit', false, false, false, null);


-- ** Waarborgfonds EW-NHG 650201 Bevraging
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'Persoon.Identificatienummers', false, true, false, 'Administratienummer');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'Persoon.SamengesteldeNaam', false, true, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'Persoon.Geboorte', false, false, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'Persoon.Geslachtsaanduiding', false, true, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'Persoon.Bijhouding', false, false, false, 'PartijCode,BijhoudingsaardCode,NadereBijhoudingsaardCode');

select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'Persoon.Overlijden', false, false, false, 'Datum');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'Persoon.Naamgebruik', false, false, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'Persoon.Migratie', false, false, false, 'DatumAanvangGeldigheid,SoortCode,LandGebiedCode,BuitenlandsAdresRegel1,BuitenlandsAdresRegel2,BuitenlandsAdresRegel3,BuitenlandsAdresRegel4,BuitenlandsAdresRegel5,BuitenlandsAdresRegel6');

select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'Persoon.Voornaam.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'Persoon.Voornaam.Standaard', false, true, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'Persoon.Geslachtsnaamcomponent.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'Persoon.Geslachtsnaamcomponent.Standaard', false, true, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'Persoon.Adres.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'Persoon.Adres.Standaard', false, false, false, 'SoortCode,DatumAanvangAdreshouding,IdentificatiecodeAdresseerbaarObject,IdentificatiecodeNummeraanduiding,GemeenteCode,NaamOpenbareRuimte,AfgekorteNaamOpenbareRuimte,Gemeentedeel,Huisnummer,Huisletter,Huisnummertoevoeging,Postcode,Woonplaatsnaam,LocatieTenOpzichteVanAdres,Locatieomschrijving,IndicatiePersoonAangetroffenOpAdres,LandGebiedCode,BuitenlandsAdresRegel1,BuitenlandsAdresRegel2,BuitenlandsAdresRegel3,BuitenlandsAdresRegel4,BuitenlandsAdresRegel5,BuitenlandsAdresRegel6');

select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Standaard', false, false, false, null);

select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'Huwelijk.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'Huwelijk.Standaard', false, false, false, 'DatumAanvang,DatumEinde');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'GeregistreerdPartnerschap.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'GeregistreerdPartnerschap.Standaard', false, false, false, 'DatumAanvang,DatumEinde');

select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'GerelateerdeGeregistreerdePartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers', false, false, false, 'Administratienummer');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'GerelateerdeGeregistreerdePartner.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'GerelateerdeHuwelijkspartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'GerelateerdeHuwelijkspartner.Persoon.Identificatienummers', false, false, false, 'Administratienummer');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'GerelateerdeHuwelijkspartner.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'Onderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'Onderzoek.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'GegevenInOnderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('Waarborgfonds EW-NHG 650201 Bevraging', 'ActieBron.Identiteit', false, false, false, null);

-- * GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,PredicaatCode,Voornamen,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Geboorte', false, false, false, 'Datum,LandGebiedCode');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Geslachtsaanduiding', false, false, false, 'Code');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Bijhouding', false, false, false, 'PartijCode,BijhoudingsaardCode,NadereBijhoudingsaardCode');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Overlijden', false, false, false, 'Datum');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Naamgebruik', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Migratie', false, false, false, 'DatumAanvangGeldigheid,SoortCode');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Voornaam.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Voornaam.Standaard', false, false, false, 'Naam');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Geslachtsnaamcomponent.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Geslachtsnaamcomponent.Standaard', false, false, false, 'PredicaatCode,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Stam');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Adres.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Adres.Standaard', false, false, false, 'DatumAanvangAdreshouding,IdentificatiecodeAdresseerbaarObject,IdentificatiecodeNummeraanduiding,GemeenteCode,NaamOpenbareRuimte,AfgekorteNaamOpenbareRuimte,Gemeentedeel,Huisnummer,Huisletter,Huisnummertoevoeging,Postcode,Woonplaatsnaam,LocatieTenOpzichteVanAdres,Locatieomschrijving,IndicatiePersoonAangetroffenOpAdres');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Nationaliteit.Identiteit', false, false, false, null);

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Indicatie.DerdeHeeftGezag.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Indicatie.DerdeHeeftGezag.Standaard', false, false, false, 'Waarde');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Indicatie.Staatloos.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Indicatie.Staatloos.Standaard', false, false, false, 'Waarde');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Indicatie.VastgesteldNietNederlander.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Indicatie.VastgesteldNietNederlander.Standaard', false, false, false, 'Waarde');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Indicatie.BehandeldAlsNederlander.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Persoon.Indicatie.BehandeldAlsNederlander.Standaard', false, false, false, 'Waarde');


select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'FamilierechtelijkeBetrekking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'FamilierechtelijkeBetrekking.Standaard', false, false, false, null);

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Ouder.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'GerelateerdeOuder.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'GerelateerdeOuder.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'GerelateerdeOuder.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'GerelateerdeOuder.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,Voornamen,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'GerelateerdeOuder.Persoon.Geboorte', false, false, false, 'Datum,LandGebiedCode');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Huwelijk.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Huwelijk.Standaard', false, false, false, 'DatumAanvang,DatumEinde');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'GeregistreerdPartnerschap.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'GeregistreerdPartnerschap.Standaard', false, false, false, 'DatumAanvang,DatumEinde');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'GerelateerdeGeregistreerdePartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam', false, false, false, 'Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'GerelateerdeHuwelijkspartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam', false, false, false, 'Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Onderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'Onderzoek.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'GegevenInOnderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - AfnInd', 'ActieBron.Identiteit', false, false, false, null);

-- * GGD Gooi en vechtstreek 613303 Bevraging - AfnInd
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,PredicaatCode,Voornamen,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Geboorte', false, false, false, 'Datum,LandGebiedCode');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Geslachtsaanduiding', false, false, false, 'Code');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Bijhouding', false, false, false, 'PartijCode,BijhoudingsaardCode,NadereBijhoudingsaardCode');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Overlijden', false, false, true, 'Datum');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Naamgebruik', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Migratie', false, false, false, 'DatumAanvangGeldigheid,SoortCode');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Voornaam.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Voornaam.Standaard', false, false, false, 'Naam');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Geslachtsnaamcomponent.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Geslachtsnaamcomponent.Standaard', false, false, false, 'PredicaatCode,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Stam');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Adres.Standaard', false, true, false, 'SoortCode,DatumAanvangAdreshouding,IdentificatiecodeAdresseerbaarObject,IdentificatiecodeNummeraanduiding,GemeenteCode,NaamOpenbareRuimte,AfgekorteNaamOpenbareRuimte,Gemeentedeel,Huisnummer,Huisletter,Huisnummertoevoeging,Postcode,Woonplaatsnaam,LocatieTenOpzichteVanAdres,Locatieomschrijving,IndicatiePersoonAangetroffenOpAdres');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Adres.Identiteit', false, false, false, '');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Nationaliteit.Identiteit', false, false, false, null);

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Indicatie.DerdeHeeftGezag.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Indicatie.DerdeHeeftGezag.Standaard', false, false, false, 'Waarde');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Indicatie.Staatloos.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Indicatie.Staatloos.Standaard', false, false, false, 'Waarde');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Indicatie.VastgesteldNietNederlander.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Indicatie.VastgesteldNietNederlander.Standaard', false, false, false, 'Waarde');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Indicatie.BehandeldAlsNederlander.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Persoon.Indicatie.BehandeldAlsNederlander.Standaard', false, false, false, 'Waarde');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'FamilierechtelijkeBetrekking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'FamilierechtelijkeBetrekking.Standaard', false, false, false, null);

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Ouder.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'GerelateerdeOuder.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'GerelateerdeOuder.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'GerelateerdeOuder.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'GerelateerdeOuder.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,Voornamen,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'GerelateerdeOuder.Persoon.Geboorte', false, false, false, 'Datum,LandGebiedCode');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Kind.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'GerelateerdeKind.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'GerelateerdeKind.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'GerelateerdeKind.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'GerelateerdeKind.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,Voornamen,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'GerelateerdeKind.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Huwelijk.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Huwelijk.Standaard', false, false, false, 'DatumAanvang,DatumEinde');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'GeregistreerdPartnerschap.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'GeregistreerdPartnerschap.Standaard', false, false, false, 'DatumAanvang,DatumEinde');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'GerelateerdeGeregistreerdePartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam', false, false, false, 'Voornamen,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'GerelateerdeGeregistreerdePartner.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'GerelateerdeHuwelijkspartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'GerelateerdeHuwelijkspartner.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam', false, false, false, 'Voornamen,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'GerelateerdeHuwelijkspartner.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Onderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'Onderzoek.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'GegevenInOnderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - AfnInd', 'ActieBron.Identiteit', false, false, false, null);


-- * GGD Gooi en vechtstreek 613303 Synchronisatie - Doel
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,PredicaatCode,Voornamen,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Geboorte', false, false, false, 'Datum,LandGebiedCode');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Geslachtsaanduiding', false, false, false, 'Code');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Bijhouding', false, false, false, 'PartijCode,BijhoudingsaardCode,NadereBijhoudingsaardCode');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Overlijden', false, false, false, 'Datum');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Naamgebruik', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Migratie', false, false, false, 'DatumAanvangGeldigheid,SoortCode');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Voornaam.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Voornaam.Standaard', false, false, false, 'Naam');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Geslachtsnaamcomponent.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Geslachtsnaamcomponent.Standaard', false, false, false, 'PredicaatCode,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Stam');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Adres.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Adres.Standaard', false, false, false, 'SoortCode,DatumAanvangAdreshouding,IdentificatiecodeAdresseerbaarObject,IdentificatiecodeNummeraanduiding,GemeenteCode,NaamOpenbareRuimte,AfgekorteNaamOpenbareRuimte,Gemeentedeel,Huisnummer,Huisletter,Huisnummertoevoeging,Postcode,Woonplaatsnaam,LocatieTenOpzichteVanAdres,Locatieomschrijving,IndicatiePersoonAangetroffenOpAdres');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Nationaliteit.Identiteit', false, false, false, null);

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Indicatie.DerdeHeeftGezag.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Indicatie.DerdeHeeftGezag.Standaard', false, false, false, 'Waarde');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Indicatie.Staatloos.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Indicatie.Staatloos.Standaard', false, false, false, 'Waarde');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Indicatie.VastgesteldNietNederlander.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Indicatie.VastgesteldNietNederlander.Standaard', false, false, false, 'Waarde');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Indicatie.BehandeldAlsNederlander.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Persoon.Indicatie.BehandeldAlsNederlander.Standaard', false, false, false, 'Waarde');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'FamilierechtelijkeBetrekking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'FamilierechtelijkeBetrekking.Standaard', false, false, false, null);

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Ouder.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'GerelateerdeOuder.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'GerelateerdeOuder.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'GerelateerdeOuder.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'GerelateerdeOuder.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,Voornamen,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'GerelateerdeOuder.Persoon.Geboorte', false, false, false, 'Datum,LandGebiedCode');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Huwelijk.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Huwelijk.Standaard', false, false, false, 'DatumAanvang,DatumEinde');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'GeregistreerdPartnerschap.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'GeregistreerdPartnerschap.Standaard', false, false, false, 'DatumAanvang,DatumEinde');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'GerelateerdeGeregistreerdePartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam', false, false, false, 'Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'GerelateerdeHuwelijkspartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam', false, false, false, 'Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Onderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'Onderzoek.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'GegevenInOnderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Synchronisatie - Doel', 'ActieBron.Identiteit', false, false, false, null);

-- * GGD Gooi en vechtstreek 613303 Bevraging - Doel
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,PredicaatCode,Voornamen,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Persoon.Geboorte', false, false, false, 'Datum,LandGebiedCode');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Persoon.Geslachtsaanduiding', false, false, false, 'Code');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Persoon.Bijhouding', false, false, false, 'PartijCode,BijhoudingsaardCode,NadereBijhoudingsaardCode');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Persoon.Overlijden', false, false, true, 'Datum');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Persoon.Naamgebruik', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Persoon.Migratie', false, false, false, 'DatumAanvangGeldigheid,SoortCode');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Persoon.Voornaam.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Persoon.Voornaam.Standaard', false, false, false, 'Naam');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Persoon.Geslachtsnaamcomponent.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Persoon.Geslachtsnaamcomponent.Standaard', false, false, false, 'PredicaatCode,AdellijkeTitelCode,Voorvoegsel,Scheidingsteken,Stam');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Persoon.Adres.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Persoon.Adres.Standaard', false, true, false, 'SoortCode,DatumAanvangAdreshouding,IdentificatiecodeAdresseerbaarObject,IdentificatiecodeNummeraanduiding,GemeenteCode,NaamOpenbareRuimte,AfgekorteNaamOpenbareRuimte,Gemeentedeel,Huisnummer,Huisletter,Huisnummertoevoeging,Postcode,Woonplaatsnaam,LocatieTenOpzichteVanAdres,Locatieomschrijving,IndicatiePersoonAangetroffenOpAdres');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Persoon.Nationaliteit.Identiteit', false, false, false, null);

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Persoon.Indicatie.DerdeHeeftGezag.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Persoon.Indicatie.DerdeHeeftGezag.Standaard', false, false, false, 'Waarde');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Persoon.Indicatie.Staatloos.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Persoon.Indicatie.Staatloos.Standaard', false, false, false, 'Waarde');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'FamilierechtelijkeBetrekking.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'FamilierechtelijkeBetrekking.Standaard', false, false, false, null);

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Ouder.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'GerelateerdeOuder.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'GerelateerdeOuder.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'GerelateerdeOuder.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'GerelateerdeOuder.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,Voornamen,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'GerelateerdeOuder.Persoon.Geboorte', false, false, false, 'Datum,LandGebiedCode');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Kind.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'GerelateerdeKind.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'GerelateerdeKind.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'GerelateerdeKind.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'GerelateerdeKind.Persoon.SamengesteldeNaam', false, false, false, 'IndicatieNamenreeks,Voornamen,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'GerelateerdeKind.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Huwelijk.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Huwelijk.Standaard', false, false, false, 'DatumAanvang,DatumEinde');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'GeregistreerdPartnerschap.Identiteit', false, false, false, '');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'GeregistreerdPartnerschap.Standaard', false, false, false, 'DatumAanvang,DatumEinde');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'GerelateerdeGeregistreerdePartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam', false, false, false, 'Voornamen,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'GerelateerdeGeregistreerdePartner.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'GerelateerdeHuwelijkspartner.Persoon.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'GerelateerdeHuwelijkspartner.Persoon.Identificatienummers', false, false, false, 'Burgerservicenummer,Administratienummer');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam', false, false, false, 'Voornamen,Voorvoegsel,Scheidingsteken,Geslachtsnaamstam');
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'GerelateerdeHuwelijkspartner.Persoon.Geboorte', false, false, false, 'Datum');

select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Onderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'Onderzoek.Standaard', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'GegevenInOnderzoek.Identiteit', false, false, false, null);
select tmpDienstbundelGroepAttr('GGD Gooi en vechtstreek 613303 Bevraging - Doel', 'ActieBron.Identiteit', false, false, false, null);

-- ** Bijhoudingsautorisatie
insert into AutAut.Bijhautorisatie(IndModelautorisatie, Naam, DatIngang, IndAg) values
    (true, 'Gemeente', 20150101, True); -- 1
insert into AutAut.His_Bijhautorisatie (Bijhautorisatie, TsReg, Naam, DatIngang)
   select Id, Now(), Naam, DatIngang
   from AutAut.BijhAutorisatie
   where Id >= 100000;
   
-- ** Toegang Bijhoudingsautorisatie
insert into AutAut.ToegangBijhautorisatie(Geautoriseerde, Bijhautorisatie, Ondertekenaar, Transporteur, DatIngang, Afleverpunt, IndAg)
   select pr.id, (select Id from AutAut.Bijhautorisatie where naam = 'Gemeente'), null, null, 20150101, ta.url, True
   from
      Kern.Partij P
      join kern.partijrol pr on p.id = pr.partij
      join tmpAfleverpuntPartij tap on tap.PartijID = p.ID
      join tmpAfleverpunt ta on ta.naam = tap.afleverpunt
   where
      pr.rol = 2 and
      p.oin is not null;
insert into AutAut.His_ToegangBijhautorisatie(ToegangBijhautorisatie, TsReg, DatIngang, Afleverpunt)
   select Id, Now(), DatIngang, Afleverpunt
   from AutAut.ToegangBijhautorisatie
   where Id >= 100000;


-- ** Bijhoudingsautorisatie \ Soort administratieve handeling
-- Alle Bijhoudingsautorisaties mogen alle handelingen
insert into AutAut.BijhautorisatieSrtAdmHnd(Bijhautorisatie, SrtAdmHnd, IndAg)
   select ba.id, sa.id, True
   from AutAut.Bijhautorisatie ba 
        cross join Kern.SrtAdmHnd sa
   where ba.Id >= 100000;
   
insert into AutAut.His_BijhautorisatieSrtAdmHnd(BijhautorisatieSrtAdmHnd, TsReg)
   select id, Now()
   from AutAut.BijhautorisatieSrtAdmHnd
   where Id >= 100000;

-- Opruimen tijdelijke tabellen / functies
drop table tmpAfleverpunt;
drop table tmpAfleverpuntPartij;
drop function tmpToegangLeveringsautorisatie(LevAutorisatieNaam Text, aPartijCode Char(6), aPartijRol integer, aOndertekenaarCode Char(6), aTransporteurCode Char(6), aNaderepopulatiebeperking Text);
drop function tmpDienstbundelGroepAttr(aDienstbundelNaam Text, aGroepNaam Text, aindformelehistorie boolean, aindmaterielehistorie boolean, aindverantwoording boolean, aAttributen Text);

-- Bijwerken statistieken (niet altijd gebeurd na inlezen)
ANALYZE verbose;
