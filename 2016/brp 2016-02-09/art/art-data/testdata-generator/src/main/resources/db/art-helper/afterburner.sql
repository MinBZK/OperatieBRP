BEGIN;
--RvdP-- Eva en Adam zijn met 'actie 0' begonnen, op 1 januari  1980
insert into kern.admhnd (id, srt, partij, toelichtingontlening, tsreg) values ('0', '1', '2000', 'toelichting','19800101');
insert into kern.actie (id, srt, admhnd, partij, tsreg) values (0, 1, 0, 2000, '19800101');
COMMIT;

BEGIN;
insert into kern.pers (id, srt,  tslaatstewijz, voornamen, voorvoegsel, scheidingsteken, geslnaamstam,  IndNreeks, geslachtsaand) select id+1000000, 2, now(),'Adam', 'van', '-', 'Modernodam',false, 1 from kern.pers where geslnaamstam <> 'Modernodam' AND not exists (select 42 from kern.betr where rol=1 AND Pers = pers.id);
insert into kern.his_perssamengesteldenaam (id, pers, dataanvgel, tsreg, indafgeleid, indnreeks, voornamen, voorvoegsel, scheidingsteken, geslnaamstam, actieinh) select id+1000000, id+1000000, 19980101, now(), true, false, 'Adam', 'van', '-', 'Modernodam', 0 from kern.pers where srt=1 and not exists (select 42 from kern.betr where rol=1 AND Pers = pers.id);
insert into kern.his_persgeboorte (id, pers, tsreg, datgeboorte, landgebiedgeboorte, actieinh) select id+1000000, id+1000000, now(), 19500101, 229, 0 from kern.pers where srt=1 and not exists (select 42 from kern.betr where rol=1 AND Pers = pers.id);
insert into kern.his_persgeslachtsaand (id, pers, dataanvgel, tsreg, geslachtsaand, actieinh) select id+1000000, id+1000000, 19500101 ,now(), 1, 0 from kern.pers where srt=1 and not exists (select 42 from kern.betr where rol=1 AND Pers = pers.id);
--RvdP: Voor iedereen die nog niet betrokken was als 'kind' in een relatie, komt er een 'adam' als vader...

COMMIT;
BEGIN;
--ArKr-- Release 10.1 = insert into kern.pers (id, srt, idsstatushis, geslachtsaandstatushis, samengesteldenaamstatushis, aanschrstatushis, geboortestatushis, overlijdenstatushis, uitslnlkiesrstatushis, euverkiezingenstatushis, bijhaardstatushis, opschortingstatushis, bijhgemstatushis, verblijfstitelstatushis, pkstatushis, inschrstatushis, tslaatstewijz, immigratiestatushis, voornamen, voorvoegsel, scheidingsteken, geslnaam, IndNreeks, geslachtsaand, bvpstatushis) select id+2000000, 2, 'X', 'A', 'A', 'X', 'A', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X','X', now(), 'X', 'Eva', 'van', ' ', 'Modernodam', false, 2, 'X' from kern.pers where geslnaam <> 'Modernodam' AND not exists (select 42 from kern.betr where rol=1 AND Pers = pers.id);
--ArKr-- Release 10.1 -> 12.0 pers, samengesteldenaamstatushis vervangen in afgeleidadministratiefstatus
--GrZw-- 20130806 Release 12.0 -> 13.0 pers: idsstatushis, geslachtsaandstatushis, afgeleidadministratiefstatus, aanschrstatushis, geboortestatushis, overlijdenstatushis, uitslnlkiesrstatushis,euverkiezingenstatushis, bijhaardstatushis, opschortingstatushis, bijhgemstatushis, verblijfstitelstatushis, pkstatushis, inschrstatushis, immigratiestatushis, samengesteldenaamstatushis,bvpstatushis verwijderd
insert into kern.pers (id, srt, tslaatstewijz, voornamen, voorvoegsel, scheidingsteken, geslnaamstam,  IndNreeks, geslachtsaand) select id+2000000, 2, now(), 'Eva', 'van', '-', 'Modernodam', false, 2 from kern.pers where geslnaamstam <> 'Modernodam' AND not exists (select 42 from kern.betr where rol=1 AND Pers = pers.id);
insert into kern.his_perssamengesteldenaam (id, pers, dataanvgel, tsreg, indafgeleid, indnreeks, voornamen, voorvoegsel, scheidingsteken, geslnaamstam, actieinh) select id+2000000, id+2000000, 19980101, now(), true, false, 'Eva', 'van', '-', 'Modernodam', 0 from kern.pers where srt=1 and not exists (select 42 from kern.betr where rol=1 AND Pers = pers.id);
insert into kern.his_persgeboorte (id, pers, tsreg, datgeboorte, landgebiedgeboorte, actieinh) select id+2000000, id+2000000, now(), 19500101, 229, 0 from kern.pers where srt=1 and not exists (select 42 from kern.betr where rol=1 AND Pers = pers.id);
insert into kern.his_persgeslachtsaand (id, pers, dataanvgel, tsreg, geslachtsaand, actieinh) select id+2000000, id+2000000, 19500101 ,now(), 2, 0 from kern.pers where srt=1 and not exists (select 42 from kern.betr where rol=1 AND Pers = pers.id);
COMMIT;
BEGIN;
insert into kern.partijrol (id, partij, rol) values ('1', (select id from kern.partij where code = 710149), '4');

COMMIT;
BEGIN;

--RvdP-- Geboorteactie van Adam en Eva zijn de actie waarmee betreffende persoon is geboren; hiermee is ook de relatie=famrechtbetr ontstaan:
--GrZw-- 20130806 Release 12.0 -> 13.0 huwelijkgeregistreerdpartner, naamskeuzeongeborenvruchtsta,, erkenningongeborenvruchtstat zijn verwijderd
insert into kern.Relatie(id, srt) select 33000000+id, 3 from kern.pers;

COMMIT;
BEGIN;

--RvdP-- eerst de betrokkenheden van Adam en Eva: bron 'personen soort 1 zonder ouders'
--GrZw-- 20130806 Release 12.0 -> 13.0 , ouderschapstatushis, ouderlijkgezagstatushis zijn verwijderd
insert into kern.betr(id, relatie, rol, Pers, indouder)
   select 1000000+id, 33000000+id, 2, id+1000000, true from kern.pers where geslnaamstam <> 'Modernodam' AND not exists (select 42 from kern.betr where rol=1 AND Pers=pers.id);

COMMIT;
BEGIN;

--GrZw-- 20130806 Release 12.0 -> 13.0 , ouderschapstatushis, ouderlijkgezagstatushis zijn verwijderd
insert into kern.betr(id, relatie, rol, Pers, indouder)
   select 2000000+id, 33000000+id, 2, id+2000000, true from kern.pers where geslnaamstam <> 'Modernodam' AND not exists (select 42 from kern.betr where rol=1 AND Pers=pers.id);


COMMIT;
BEGIN;

--RvdP-- nu de betrokkenheden voor iedereen die NOG GEEN OUDERS heeft (hierna heeft iedereen ouders...)
--GrZw-- 20130806 Release 12.0 -> 13.0 , ouderschapstatushis, ouderlijkgezagstatushis zijn verwijderd
insert into kern.betr(id, relatie, rol, Pers)
	select 3000000+id, 33000000+id, 1, id from kern.pers where geslnaamstam <> 'Modernodam' AND not exists (select 42 from kern.betr where rol=1 AND Pers=pers.id);

COMMIT;
BEGIN;

--RvdP-- Betrokkenheid -- historie
insert into kern.His_OuderOuderschap  (id, betr, dataanvgel, tsreg, actieinh, indouder)
	select A.id, A.id, B.datgeboorte, B.tsreg, B.actieinh, true
	from kern.betr A, kern.his_persgeboorte B
	where B.pers < 1000000 --OuCho: Laat de adam en eva's er buiten.
    and not exists (select 42 from kern.pers where geslnaamstam <> 'Modernodam' AND A.Pers=pers.id)
	-- Ok, alleen de Adam's en Eva's...
	and exists (select 42 from kern.betr C where C.relatie = A.relatie AND C.Pers=B.pers)
	and not exists (select 42 from kern.His_OuderOuderschap D where D.id = A.id);
	-- Ok, alleen voor personen die een his_geboorte hebben, die betrokken is in dezelfde relatie.

COMMIT;

--RvdP-- Wens leveranciers: wegnemen van verwarrend feit dat ouders van partners dezelfde naam hebben (maar niet dezelfde persoon zijn).
--RvdP-- Via de volgende queries wordt dit opgelost: hierna heet 'Adam' niet meer 'Adam', maar 'Adam-Xxxx' met Xxxx de naam van zijn zoon of dochter...
--RvdP-- NB: Deze hernoemactie moet NA dat Eva en Adam als 'ouder' zijn gekoppeld. Hierom verplaatst naar (logisch) einde van de afterburner:
begin;
drop table if exists kern.tempA;

create table kern.tempA as
select A.id, A.voornamen as ouder, B.voornamen as kind from
kern.pers A, kern.pers B, kern.betr C, kern.betr D
where A.id=C.pers and D.relatie=C.relatie and C.id <> D.id and D.pers=B.id and D.rol=1 and A.srt = 2 and A.geslnaamstam = 'Modernodam';
commit;

begin;
alter table kern.tempA add PRIMARY KEY (id);
commit;

--begin;
update kern.pers P
set voornamen = voornamen || (select '-' || kind from kern.tempA A where A.id = P.id)
where P.id  in (select id from kern.tempA);

-- C laag moet ook bijgewerkt worden:
update kern.his_perssamengesteldenaam hisSam
set voornamen = voornamen || (select '-' || kind from kern.tempA A where A.id = hisSam.id)
where hissam.pers in (select id from kern.tempA);
--commit;

begin;
drop table kern.tempA;
commit;

BEGIN;

SELECT SETVAL('kern.seq_actie', (select 90000+ coalesce(max(id),0)  FROM "kern"."actie"), true);
SELECT SETVAL('kern.seq_actiebron', (select 90000+ coalesce(max(id),0)  FROM "kern"."actiebron"), true);
SELECT SETVAL('kern.seq_admhnd', (select 90000+ coalesce(max(id),0)  FROM "kern"."admhnd"), true);
SELECT SETVAL('kern.seq_betr', (select 90000+ coalesce(max(id),0)  FROM "kern"."betr"), true);
SELECT SETVAL('kern.seq_doc', (select 90000+ coalesce(max(id),0)  FROM "kern"."doc"), true);
SELECT SETVAL('kern.seq_gegeveninonderzoek', (select 90000+ coalesce(max(id),0)  FROM "kern"."gegeveninonderzoek"), true);
SELECT SETVAL('kern.seq_his_doc', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_doc"), true);
SELECT SETVAL('kern.seq_his_onderzoek', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_onderzoek"), true);
SELECT SETVAL('kern.seq_his_ouderouderlijkgezag', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_ouderouderlijkgezag"), true);
SELECT SETVAL('kern.seq_his_ouderouderschap', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_ouderouderschap"), true);
SELECT SETVAL('kern.seq_his_partij', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_partij"), true);
SELECT SETVAL('kern.seq_his_persnaamgebruik', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persnaamgebruik"), true);
SELECT SETVAL('kern.seq_his_persadres', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persadres"), true);
SELECT SETVAL('kern.seq_his_persafgeleidadministrati', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persafgeleidadministrati"), true);
SELECT SETVAL('kern.seq_his_persbijhouding', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persbijhouding"), true);
SELECT SETVAL('kern.seq_his_persdeelneuverkiezingen', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persdeelneuverkiezingen"), true);
SELECT SETVAL('kern.seq_his_persgeboorte', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persgeboorte"), true);
SELECT SETVAL('kern.seq_his_persgeslachtsaand', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persgeslachtsaand"), true);
SELECT SETVAL('kern.seq_his_persgeslnaamcomp', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persgeslnaamcomp"), true);
SELECT SETVAL('kern.seq_his_persids', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persids"), true);
SELECT SETVAL('kern.seq_his_persmigratie', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persmigratie"), true);
SELECT SETVAL('kern.seq_his_persindicatie', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persindicatie"), true);
SELECT SETVAL('kern.seq_his_persinschr', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persinschr"), true);
SELECT SETVAL('kern.seq_his_persnation', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persnation"), true);
SELECT SETVAL('kern.seq_his_persoverlijden', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persoverlijden"), true);
SELECT SETVAL('kern.seq_his_perspk', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_perspk"), true);
SELECT SETVAL('kern.seq_his_persreisdoc', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persreisdoc"), true);
SELECT SETVAL('kern.seq_his_perssamengesteldenaam', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_perssamengesteldenaam"), true);
SELECT SETVAL('kern.seq_his_persuitslkiesr', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persuitslkiesr"), true);
SELECT SETVAL('kern.seq_his_persverblijfsr', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persverblijfsr"), true);
SELECT SETVAL('kern.seq_his_persverificatie', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persverificatie"), true);
SELECT SETVAL('kern.seq_his_persvoornaam', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persvoornaam"), true);
SELECT SETVAL('kern.seq_his_persverstrbeperking', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persverstrbeperking"), true);
SELECT SETVAL('kern.seq_his_relatie', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_relatie"), true);
SELECT SETVAL('kern.seq_onderzoek', (select 90000+ coalesce(max(id),0)  FROM "kern"."onderzoek"), true);
SELECT SETVAL('kern.seq_pers', (select 90000+ coalesce(max(id),0)  FROM "kern"."pers"), true);
SELECT SETVAL('kern.seq_persadres', (select 90000+ coalesce(max(id),0)  FROM "kern"."persadres"), true);
SELECT SETVAL('kern.seq_persgeslnaamcomp', (select 90000+ coalesce(max(id),0)  FROM "kern"."persgeslnaamcomp"), true);
SELECT SETVAL('kern.seq_persindicatie', (select 90000+ coalesce(max(id),0)  FROM "kern"."persindicatie"), true);
SELECT SETVAL('kern.seq_persnation', (select 90000+ coalesce(max(id),0)  FROM "kern"."persnation"), true);
SELECT SETVAL('kern.seq_personderzoek', (select 90000+ coalesce(max(id),0)  FROM "kern"."personderzoek"), true);
SELECT SETVAL('kern.seq_persreisdoc', (select 90000+ coalesce(max(id),0)  FROM "kern"."persreisdoc"), true);
SELECT SETVAL('kern.seq_persverificatie', (select 90000+ coalesce(max(id),0)  FROM "kern"."persverificatie"), true);
SELECT SETVAL('kern.seq_persvoornaam', (select 90000+ coalesce(max(id),0)  FROM "kern"."persvoornaam"), true);
SELECT SETVAL('kern.seq_regelverantwoording', (select 90000+ coalesce(max(id),0)  FROM "kern"."regelverantwoording"), true);
SELECT SETVAL('kern.seq_relatie', (select 90000+ coalesce(max(id),0)  FROM "kern"."relatie"), true);
SELECT SETVAL('kern.seq_persverstrbeperking', (select 90000+ coalesce(max(id),0)  FROM "kern"."persverstrbeperking"), true);

SELECT SETVAL('autaut.seq_his_authenticatiemiddel', (select 90000+ coalesce(max(id),0)  FROM "autaut"."his_authenticatiemiddel"), true);
SELECT SETVAL('autaut.seq_his_autorisatiebesluit', (select 90000+ coalesce(max(id),0)  FROM "autaut"."his_autorisatiebesluit"), true);
--ArKr-- 20130703 -- Release 10.1 -> 12.0 tabel bestaat niet meer SELECT SETVAL('autaut.seq_his_autorisatiebesluitbijhau', (select 90000+ coalesce(max(id),0)  FROM "autaut"."his_autorisatiebesluitbijhau"), true);
SELECT SETVAL('autaut.seq_his_bijhautorisatie', (select 90000+ coalesce(max(id),0)  FROM "autaut"."his_bijhautorisatie"), true);
--SELECT SETVAL('autaut.seq_his_bijhsituatie', (select 90000+ coalesce(max(id),0)  FROM "autaut"."his_bijhsituatie"), true);
SELECT SETVAL('autaut.seq_his_doelbinding', (select 90000+ coalesce(max(id),0)  FROM "autaut"."his_doelbinding"), true);
SELECT SETVAL('autaut.seq_his_uitgeslotene', (select 90000+ coalesce(max(id),0)  FROM "autaut"."his_uitgeslotene"), true);

SELECT SETVAL('kern.seq_admhndgedeblokkeerdemelding', (select 90000+ coalesce(max(id),0)  FROM "kern"."admhndgedeblokkeerdemelding"), true);
SELECT SETVAL('kern.seq_gedeblokkeerdemelding', (select 90000+ coalesce(max(id),0)  FROM "kern"."gedeblokkeerdemelding"), true);

SELECT SETVAL('brm.seq_his_regelsituatie', (select 90000+ coalesce(max(id),0)  FROM "brm"."his_regelsituatie"), true);

SELECT SETVAL('autaut.seq_his_abonnement', (select 90000+ coalesce(max(id),0)  FROM "autaut"."his_abonnement"), true);
SELECT SETVAL('autaut.seq_abonnementattribuut', (select 90000+ coalesce(max(id),0)  FROM "autaut"."abonnementattribuut"), true);
SELECT SETVAL('autaut.seq_abonnementgroep', (select 90000+ coalesce(max(id),0)  FROM "autaut"."abonnementgroep"), true);

COMMIT;

BEGIN;
-- Bolie vrijdag 4 januari 2013, toegevoegd om de default op false te zetten. de xsd verlangt dat dit gevuld diet te worden.
-- tbv van de Adam's en Eva's
UPDATE kern.his_persbijhouding set IndOnverwDocAanw = false where IndOnverwDocAanw is null;

--GrZw-- 20130806 Release 12.0 -> 13.0 bijhgemstatushis <> 'X' is verwijderd
-- tbv van de Adam's en Eva's
UPDATE kern.pers set IndOnverwDocAanw = false where IndOnverwDocAanw is null;

--GrZw-- 20130806 Release 12.0 -> 13.0 aanschrstatushis <> 'X' is verwijderd
-- tbv van de Adam's en Eva's
UPDATE kern.pers set naamgebruik = 1 where naamgebruik is null;
UPDATE kern.his_persnaamgebruik set naamgebruik = 1 where naamgebruik is null;

--GrZw-- 20130806 Release 12.0 -> 13.0 samengesteldenaamstatushis <> 'X' is verwijderd
-- tbv van de Adam's en Eva's
UPDATE kern.pers set indafgeleid = true where indafgeleid is null;
UPDATE kern.his_perssamengesteldenaam set indafgeleid = true where indafgeleid is null ;

--GrZw-- 20130806 Release 12.0 -> 13.0 geboortestatushis <> 'X' is verwijderd
-- tbv van de Adam's en Eva's
UPDATE kern.pers set datgeboorte='19500101' where datgeboorte is null;
UPDATE kern.pers set landgebiedgeboorte=229 where landgebiedgeboorte is null;

-- Aanmaken dummy afg.adm. info voor niet ingeschrevenen Adam en Eva:
-- Let op uitsluitend Adam en Eva! De rest van de niet-ingeschrevenen is testdata van de testers.
update kern.pers set
tslaatstewijz = '2013-01-01 00:00:00',
admhnd = (select min(id) from kern.admhnd),
sorteervolgorde = 1,
indonverwbijhvoorstelnieting = false
where srt = 2 and geslnaamstam = 'Modernodam';
--bijbehorende historie
insert into kern.his_persafgeleidadministrati (id, pers, tsreg, actieinh, admhnd, tslaatstewijz, sorteervolgorde, indonverwbijhvoorstelnieting)
select id, id, now(), (select min(id) from kern.actie), (select min(id) from kern.admhnd), '2013-01-01 00:00:00', 1, false from kern.pers where srt = 2 and geslnaamstam = 'Modernodam';

COMMIT;

BEGIN;

-- LEVERINGEN SPECIALS
--Zet alle administratieve handelingen als geleverd, zodat de bezemwagen ze niet oppakt
--tbv leveren. initiele testdata vulling moet iet geleverd worden
update kern.admhnd set tslev = tsreg where id >=0;
-- ivm lokaal herladen van de testdata zonder dat een schone database wordt gecreeerd. Worden de archiveer/levering tabellen gevuld met zelfde admhnd id's de SetVal hierboven kijkt niet in de archiveer/levering tabellen.
COMMIT;

BEGIN;

--Abonnementen expressies laden voor een aantal vastgestelde abonnementen (indien aanwezig), autorisatie dient daarbij niet 'Niet verstrekken' en 'Bijhoudingsgegevens' te zijn

INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670000 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670002 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670003 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670004 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670005 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670006 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670007 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670008 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670009 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670010 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670011 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670012 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670013 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670014 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670015 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670016 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670017 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670018 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670019 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670020 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670021 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670022 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670023 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670025 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
--5670026, 5670027 zijn abonnementen op basis dienst attendering
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670029 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670032 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670033 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670034 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670035 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670036 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
--5670037, 5670038 zijn abonnementen op basis dienst attendering
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670040 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670041 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5670046 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671016 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671046 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);

-- onderstaande abonnementen zijn speciaal voor test van autorisatie abonnementgroepen
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671001 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671002 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671003 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671004 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671008 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671009 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671010 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671011 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671012 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671013 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671014 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671020 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671026 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671029 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671030 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671031 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671032 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671033 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671034 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671035 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671036 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671038 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671039 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671037 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671040 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671042 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671044 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671048 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671049 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);

INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671050 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671051 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671052 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671053 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);



-- Bijhouder abonnement dus mag geautoriseerd worden voor elementen (attributen) met autorisatie 'Bijhoudingsgegevens' --> e.autorisatie = 7
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671045 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2);

-- INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671037 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum aanvang geldigheid', 'Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);
-- INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671040 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum aanvang geldigheid', 'Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);


-- vul abo met alle attributen, behalve attributen van de Persoon.Adres.Standaard groep
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 5671041 and e.expressie is not null and e.srt = 3 and (groep != 6063 or e.naam = 'Persoon.Adres.DatumAanvangGeldigheid') and e.elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') and e.autorisatie NOT IN (2,7);



-- opruimen van abonnementattributen die nog geen expressie hebben in de element-verwijzing, komt nog, verwijzingen staan wel in levabo-sheet, worden hier nu dus nog even opgeruimd!
-- Ook worden de abonnementattributen voor formele en materiel historie weggegooid aangezien deze via het groepenfilter worden gezet.
DELETE FROM autaut.abonnementattribuut
WHERE attribuut IN
(SELECT a.attribuut
 FROM autaut.abonnementattribuut a JOIN kern.element e ON (a.attribuut = e.id)
 WHERE e.expressie IS NULL
 OR e.expressie LIKE '%.datum_einde_geldigheid%'
 OR e.expressie LIKE '%.datum_tijd_registratie%'
 OR e.expressie LIKE '%.datum_tijd_verval%');

COMMIT;




BEGIN;
----------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------
--Verwijderen attributen uit autorisatie voor abonnement
----------------------------------------------------------------------------------------------
DELETE FROM autaut.abonnementattribuut WHERE abonnement=5671048 AND attribuut IN
(SELECT a.attribuut FROM autaut.abonnementattribuut a JOIN kern.element e ON (a.attribuut = e.id) WHERE e.srt=3 AND e.naam LIKE '%Onderzoek%');

DELETE FROM autaut.abonnementattribuut WHERE abonnement=5671049 AND attribuut IN
(SELECT a.attribuut FROM autaut.abonnementattribuut a JOIN kern.element e ON (a.attribuut = e.id) WHERE e.srt=3 AND e.naam='Persoon.Adres.Huisnummer');

DELETE FROM autaut.abonnementattribuut WHERE abonnement=5671049 AND attribuut IN
(SELECT a.attribuut FROM autaut.abonnementattribuut a JOIN kern.element e ON (a.attribuut = e.id) WHERE e.srt=3 AND e.naam LIKE '%AfgeleidAdministratief%');

DELETE FROM autaut.abonnementattribuut WHERE abonnement=5671049 AND attribuut IN
(SELECT a.attribuut FROM autaut.abonnementattribuut a JOIN kern.element e ON (a.attribuut = e.id) WHERE e.srt=3 AND e.naam LIKE '%Persoon.Nationaliteit%');

COMMIT;


BEGIN;
----------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------
-- Onderstaande Insert stap vult voor ieder abonnement een abonnementgroep met alle groepen
-- met Alle autorisaties :
-- (indverantwoordingsinfo, indkopiebrondoc, indmaterielehistorie, indformelehistorie) op TRUE
----------------------------------------------------------------------------------------------
-- Abonnementen die de autorisatie willen checken hebben een ID boven de 5671000
----------------------------------------------------------------------------------------------

INSERT INTO autaut.abonnementgroep (abonnement, groep, indnadereverantwoording, inddoc, indmaterielehistorie, indformelehistorie) SELECT a.id, e.id, TRUE, TRUE, TRUE, TRUE from  autaut.abonnement a, kern.element e where a.id < 5671000 and e.srt = 2;

-- autorisatie abonnementen
INSERT INTO autaut.abonnementgroep (abonnement, groep, indnadereverantwoording, inddoc, indmaterielehistorie, indformelehistorie) SELECT a.id, e.id, FALSE, TRUE, TRUE, TRUE from  autaut.abonnement a, kern.element e where a.id = 5671001 and e.srt = 2;
INSERT INTO autaut.abonnementgroep (abonnement, groep, indnadereverantwoording, inddoc, indmaterielehistorie, indformelehistorie) SELECT a.id, e.id, FALSE, TRUE, TRUE, FALSE from  autaut.abonnement a, kern.element e where a.id = 5671002 and e.srt = 2;
INSERT INTO autaut.abonnementgroep (abonnement, groep, indnadereverantwoording, inddoc, indmaterielehistorie, indformelehistorie) SELECT a.id, e.id, TRUE, TRUE, FALSE, FALSE from  autaut.abonnement a, kern.element e where a.id = 5671003 and e.srt = 2;
INSERT INTO autaut.abonnementgroep (abonnement, groep, indnadereverantwoording, inddoc, indmaterielehistorie, indformelehistorie) SELECT a.id, e.id, TRUE, TRUE, TRUE, TRUE from  autaut.abonnement a, kern.element e where a.id = 5671010 and e.srt = 2;
INSERT INTO autaut.abonnementgroep (abonnement, groep, indnadereverantwoording, inddoc, indmaterielehistorie, indformelehistorie) SELECT a.id, e.id, TRUE, TRUE, TRUE, TRUE from  autaut.abonnement a, kern.element e where a.id = 5671011 and e.srt = 2;
INSERT INTO autaut.abonnementgroep (abonnement, groep, indnadereverantwoording, inddoc, indmaterielehistorie, indformelehistorie) SELECT a.id, e.id, TRUE, TRUE, TRUE, TRUE from  autaut.abonnement a, kern.element e where a.id = 5671012 and e.srt = 2;
INSERT INTO autaut.abonnementgroep (abonnement, groep, indnadereverantwoording, inddoc, indmaterielehistorie, indformelehistorie) SELECT a.id, e.id, TRUE, TRUE, TRUE, TRUE from  autaut.abonnement a, kern.element e where a.id = 5671013 and e.srt = 2;
INSERT INTO autaut.abonnementgroep (abonnement, groep, indnadereverantwoording, inddoc, indmaterielehistorie, indformelehistorie) SELECT a.id, e.id, TRUE, TRUE, TRUE, TRUE from  autaut.abonnement a, kern.element e where a.id = 5671016 and e.srt = 2;
INSERT INTO autaut.abonnementgroep (abonnement, groep, indnadereverantwoording, inddoc, indmaterielehistorie, indformelehistorie) SELECT a.id, e.id, TRUE, TRUE, TRUE, TRUE from  autaut.abonnement a, kern.element e where a.id = 5671037 and e.srt = 2;
INSERT INTO autaut.abonnementgroep (abonnement, groep, indnadereverantwoording, inddoc, indmaterielehistorie, indformelehistorie) SELECT a.id, e.id, TRUE, TRUE, TRUE, TRUE from  autaut.abonnement a, kern.element e where a.id = 5671040 and e.srt = 2;
INSERT INTO autaut.abonnementgroep (abonnement, groep, indnadereverantwoording, inddoc, indmaterielehistorie, indformelehistorie) SELECT a.id, e.id, TRUE, TRUE, TRUE, TRUE from  autaut.abonnement a, kern.element e where a.id = 5671046 and e.srt = 2;
INSERT INTO autaut.abonnementgroep (abonnement, groep, indnadereverantwoording, inddoc, indmaterielehistorie, indformelehistorie) SELECT a.id, e.id, TRUE, TRUE, TRUE, TRUE from  autaut.abonnement a, kern.element e where a.id = 5671048 and e.srt = 2;
INSERT INTO autaut.abonnementgroep (abonnement, groep, indnadereverantwoording, inddoc, indmaterielehistorie, indformelehistorie) SELECT a.id, e.id, TRUE, TRUE, TRUE, TRUE from  autaut.abonnement a, kern.element e where a.id = 5671049 and e.srt = 2;

INSERT INTO autaut.abonnementgroep (abonnement, groep, indnadereverantwoording, inddoc, indmaterielehistorie, indformelehistorie) SELECT a.id, e.id, TRUE, TRUE, FALSE, TRUE from  autaut.abonnement a, kern.element e where a.id = 5671050 and e.srt = 2;
INSERT INTO autaut.abonnementgroep (abonnement, groep, indnadereverantwoording, inddoc, indmaterielehistorie, indformelehistorie) SELECT a.id, e.id, TRUE, TRUE, TRUE, FALSE from  autaut.abonnement a, kern.element e where a.id = 5671051 and e.srt = 2;
INSERT INTO autaut.abonnementgroep (abonnement, groep, indnadereverantwoording, inddoc, indmaterielehistorie, indformelehistorie) SELECT a.id, e.id, TRUE, FALSE, FALSE, FALSE from  autaut.abonnement a, kern.element e where a.id = 5671052 and e.srt = 2;
INSERT INTO autaut.abonnementgroep (abonnement, groep, indnadereverantwoording, inddoc, indmaterielehistorie, indformelehistorie) SELECT a.id, e.id, FALSE, TRUE, TRUE, TRUE from  autaut.abonnement a, kern.element e where a.id = 5671053 and e.srt = 2;

COMMIT;

BEGIN;
----------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------
--Verwijderen  groepen uit autorisatie voor abonnement
----------------------------------------------------------------------------------------------
DELETE FROM autaut.abonnementgroep WHERE abonnement=5671048 AND groep IN
(SELECT a.groep FROM autaut.abonnementgroep a JOIN kern.element e ON (a.groep = e.id) WHERE e.srt=2 AND e.naam LIKE '%Onderzoek%');

DELETE FROM autaut.abonnementgroep WHERE abonnement=5671049 AND groep IN
(SELECT a.groep FROM autaut.abonnementgroep a JOIN kern.element e ON (a.groep = e.id) WHERE e.srt=2 AND e.naam LIKE '%AfgeleidAdministratief%');

COMMIT;



BEGIN;
----------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------
-- Onderstaande stappen vult de tabel his_persafgeleidadministrati voor ieder testpersoon
-- zonder record in deze tabel
----------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------
-- stap 1: vullen van een tijdelijke tabel
----------------------------------------------------------------------------------------------
-- creeer een tijdelijke tabel met data die nodig is om kern.his_persafgadmin in te gaan vullen
-- de kolommen bron en id zijn toegevoegd om ervoor te zorgen dat er unieke records per persoon gevonden kunnen worden
-- het zoeken op alleen pers, tsreg en actieinh zorgt niet altijd voor unieke records
CREATE TEMP TABLE _persafgadmin AS
SELECT 'adres' as bron, h.id, pers, tsreg, actieinh FROM kern.his_persadres h join kern.persadres a on a.id = h.persadres
UNION SELECT 'bijhouding' as bron, id, pers, tsreg, actieinh FROM kern.his_persbijhouding hpb
UNION SELECT 'deelneuverkiezingen', id, pers, tsreg, actieinh FROM kern.his_persdeelneuverkiezingen
UNION SELECT 'geboorte', id, pers, tsreg, actieinh FROM kern.his_persgeboorte
UNION SELECT 'geslachtsaand', id, pers, tsreg, actieinh FROM kern.his_persgeslachtsaand
UNION SELECT 'geslnaamcomp', h.id, pers, tsreg, actieinh FROM kern.his_persgeslnaamcomp h join kern.persgeslnaamcomp a on a.id = h.persgeslnaamcomp
UNION SELECT 'ids', id, pers, tsreg, actieinh FROM kern.his_persids
UNION SELECT 'indicatie', h.id, pers, tsreg, actieinh FROM kern.his_persindicatie h join kern.persindicatie a on a.id = h.persindicatie
UNION SELECT 'inschr', id, pers, tsreg, actieinh FROM kern.his_persinschr
UNION SELECT 'migratie', id, pers, tsreg, actieinh FROM kern.his_persmigratie
UNION SELECT 'naamgebruik', id, pers, tsreg, actieinh FROM kern.his_persnaamgebruik
UNION SELECT 'nation', h.id, pers, tsreg, actieinh FROM kern.his_persnation h join kern.persnation a on a.id = h.persnation
UNION SELECT 'nrverwijzing', id, pers, tsreg, actieinh FROM kern.his_persnrverwijzing
UNION SELECT 'onderzoek', h.id, pers, tsreg, actieinh FROM kern.his_personderzoek h join kern.personderzoek a on a.id = h.personderzoek
UNION SELECT 'overlijden', id, pers, tsreg, actieinh FROM kern.his_persoverlijden
UNION SELECT 'pk', id, pers, tsreg, actieinh FROM kern.his_perspk
UNION SELECT 'reisdoc', h.id, pers, tsreg, actieinh FROM kern.his_persreisdoc h join kern.persreisdoc a on a.id = h.persreisdoc
UNION SELECT 'samengesteldenaam', id, pers, tsreg, actieinh FROM kern.his_perssamengesteldenaam
UNION SELECT 'uitslkiesr', id, pers, tsreg, actieinh FROM kern.his_persuitslkiesr
UNION SELECT 'persverblijfsr', id, pers, tsreg, actieinh FROM kern.his_persverblijfsr
UNION SELECT 'verificatie', h.id, geverifieerde, tsreg, actieinh FROM kern.his_persverificatie h join kern.persverificatie a on a.id = h.persverificatie --
UNION SELECT 'verstrbeperking', h.id, pers, tsreg, actieinh FROM kern.his_persverstrbeperking h join kern.persverstrbeperking a on a.id = h.persverstrbeperking
UNION SELECT 'voornaam', h.id, pers, tsreg, actieinh FROM kern.his_persvoornaam h join kern.persvoornaam a on a.id = h.persvoornaam
ORDER BY pers, tsreg desc;

----------------------------------------------------------------------------------------------
-- stap 2: voor alle personen zonder voor his_persafgeleidadministrati record eentje aanmaken
----------------------------------------------------------------------------------------------
-- creeer insert statements voor his_persafgeleidadministrati records die gevuld kunnen worden op basis van de gegevens uit de temp tabel
-- er worden alleen records aangemaakt voor personen die nog geen his_persafgeleidadministrati record hebben
INSERT INTO kern.his_persafgeleidadministrati (pers, tsreg, tsverval, actieinh, actieverval,nadereaandverval, admhnd,tslaatstewijz, sorteervolgorde, indonverwbijhvoorstelnieting, tslaatstewijzgbasystematiek)
SELECT pers, paa.tsreg, NULL, paa.actieinh, NULL, NULL, admhnd, paa.tsreg, 1, FALSE, NULL
FROM _persafgadmin paa join kern.actie a on a.id = paa.actieinh
WHERE (pers, paa.tsreg, actieinh, paa.id, bron) IN (
    -- meest recente record per persoon met max bron
    SELECT pers, tsreg, actieinh, id, max(bron) as bron FROM _persafgadmin
    WHERE (pers, tsreg, actieinh, id) IN (
        -- meest recente record per persoon met max id
        SELECT pers, tsreg, actieinh, max(id) as id FROM _persafgadmin
        WHERE (pers, tsreg, actieinh) IN (
            -- meest recente record per persoon met max actieinh
            SELECT pers, tsreg, max(actieinh) as actieinh FROM _persafgadmin
            WHERE (pers, tsreg) IN (
                -- meest recente record per persoon met max tsreg
                SELECT pers, max(tsreg) as tsreg FROM _persafgadmin
                group by pers order by pers
            ) group by pers, tsreg order by pers
        ) group by pers, tsreg, actieinh order by pers
    ) group by pers, tsreg, actieinh, id order by pers
)  AND pers NOT IN (
    SELECT pers FROM kern.his_persafgeleidadministrati
) order by pers;
COMMIT;

BEGIN;
-- Vullen van his relatie waar dat nog niet gebeurd is, zodat er historie aanwezig is voor de relaties

INSERT INTO kern.his_relatie (relatie, dataanv, gemaanv, wplnaamaanv, blplaatsaanv, blregioaanv, omslocaanv, landgebiedaanv, rdneinde, dateinde, gemeinde, wplnaameinde, blplaatseinde, blregioeinde, omsloceinde, landgebiedeinde, actieinh, tsreg)
SELECT r.id, r.dataanv, r.gemaanv, r.wplnaamaanv, r.blplaatsaanv, r.blregioaanv, r.omslocaanv, r.landgebiedaanv,
r.rdneinde, r.dateinde, r.gemeinde, r.wplnaameinde, r.blplaatseinde, r.blregioeinde, r.omsloceinde,
r.landgebiedeinde, COALESCE(it.actieinhoud, 0), COALESCE(it.tsreg, '1980-01-01 00:00:00+00')
FROM (SELECT rr.id as rrid, min(pa.actieinh) as actieinhoud, min(b.id), min(pa.tsreg) as tsreg
		FROM kern.his_persafgeleidadministrati pa
		JOIN kern.pers p on (p.id = pa.pers)
		JOIN kern.betr b on (b.pers = p.id)
		JOIN kern.relatie rr on (rr.id = b.relatie)
		GROUP BY rr.id
		ORDER BY rr.id) as it
RIGHT OUTER JOIN
	kern.relatie r ON (it.rrid = r.id)
WHERE NOT EXISTS(SELECT * FROM kern.his_relatie WHERE id = r.id);

COMMIT;

BEGIN;
-- Vullen van his betr waar dat nog niet gebeurd is, zodat er historie aanwezig is voor de betrokkenheden

INSERT INTO kern.his_betr (betr, actieinh, tsreg)
SELECT b.id, COALESCE(it.actieinhoud, 0), COALESCE(it.tsreg, '1980-01-01 00:00:00+00')
FROM (SELECT bb.id as bid, min(pa.actieinh) as actieinhoud, min(pa.tsreg) as tsreg
		FROM kern.his_persafgeleidadministrati pa
		JOIN kern.pers p on (p.id = pa.pers)
		JOIN kern.betr bb on (bb.pers = p.id)
		GROUP BY bb.id
		ORDER BY bb.id) as it
RIGHT OUTER JOIN
	kern.betr b ON (it.bid = b.id);

COMMIT;

