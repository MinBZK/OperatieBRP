--RvdP-- Versie d.d. 16 augustus 2012, aangepast voor september 2012.
--RvdP-- Laatste aanpassing: iedereen die nederlandse nationaliteit heeft, heeft deze wegens reden 'artikel 3 lid 1'.
--RvdP--
--RvdP-- Samenvoegen van verschillende scripts:
--RvdP-- Scripts van Magnus, voor vullen van verschillende tabellen;
--RvdP-- Scripts geschreven door Roel, voor vullen van andere tabellen & updates.
--RvdP-- Script voor updaten van de sequences, zodat ze allemaal hoog genoeg beginnen, en er geen reeds gebruikte ID's worden uitgegeven.
--RvdP-- Commentaar Roel gemarkeerd met '--RvdP--'
--RvdP-- Alle 'resultaat' select statements zijn ook verwijderd, omdat Bas er last van had ;-0
--RvdP-- Ook moeilijk waren de 'truncate' -- weghalen...
--RvdP-- Nog afwezig was ook: update van kern.relatie adhv kern.his_relatie. Deze toegevoegd...

-- ------------------------------------------------------------------------------- -- 
-- Totaal script om vanuit een situatie waarbij een aantal basis tabellen in       --
-- het schemer [kern] gevuld zijn, de aanleunende tabellen te herleiden            --
-- ------------------------------------------------------------------------------- --
-- De volgende tabellen moeten zijn gevuld                                         --
-- * kern.pers                                                                     --
-- * kern.his_perssamengesteldenaam                                                --
-- * kern.his_persids                                                              --
-- * kern.his_persgeslachtsaand                                                    --
-- * kern.his_persgeboorte                                                         --
-- * kern.his_persoverlijden                                                       --
-- * kern.his_persadres                                                            --
-- * kern.persbijhgem                                                              --
-- * kern.persadres (basis rijen met id's zonder adres data)                       --
-- *                                                                               --
-- ------------------------------------------------------------------------------- --
-- Magnus Rolf, DBA BRP / mGBA - 2012                                              --
-- ChangeLog                                                                       --
-- YYYY-MM-DD  Auteur.......  Wijziging........................................... --
--                                                                                 --
-- ------------------------------------------------------------------------------- --
--
-- Stappen
-- ==================================================================================
-- Stap-I01 - vullen kern.pers                        (eventueel met backup)
-- Stap-I02 - vullen kern.his_perssamengesteldenaam   (idem)
-- Stap-I03 - vullen kern.his_persids
-- Stap-I04 - vullen kern.his_persgeslachtsaand
-- Stap-I05 - vullen kern.his_persgeboorte
-- Stap-I06 - vullen kern.his_persoverlijden
-- Stap-I07 - vullen kern.his_persadres
-- Stap-I08 - vullen kern.persbijhgem
-- Stap-I09 - vullen kern.persadres (basis id's zonder adres data)
--
-- Opmerking: Stappen I01 t/m I09 zijn voorwaardelijk voor de verdere UPDATES
--            en worden door Bas verzorgd.
--
-- ----------------------------------------------------------------------------------
-- Stap-01 - Bijwerken kern.persvoornaam          adhv kern.his_perssamengesteldenaam
-- Stap-02 - Bijwerken kern.his_persvoornaam      adhv kern.his_perssamengesteldenaam
-- Stap-03 - Bijwerken kern.persgeslnaamcomp      adhv kern.his_perssamengesteldenaam
-- Stap-04 - Bijwerken kern.his_persgeslnaamcomp  adhv kern.his_perssamengesteldenaam
--
-- Stap-05 - Update kern.persadres                mbv [kern.his_persadres]
-- Stap-06 - Update kern.persoon                  mbv [kern.his_persids]
-- Stap-07 - Update kern.persoon                  mbv [kern.his_persgelachtsaand]
-- Stap-08 - Update kern.persoon                  mbv [kern.his_perssamengesteldenaam]
-- Stap-09 - Update kern.persoon                  mbv [kern.his_persaanschr] -- Geen DATA aangeleverd !!!
-- Stap-10 - Update kern.persoon                  mbv [kern.his_persgeboorte]
-- Stap-11 - Update kern.persoon                  mbv [kern.his_persoverlijden]
--
--
-- <Stap-01>
-- opnieuw vullen van [persvoornaam] vanuit his_samengesteldenaam
-- van dubbele namen wordt alleen de eerste naam gebruikt
-- -1- verwijdere alle rijen in persvoornaam
-- -2- reset de sequence
-- -3- commit
-- -4- insert nieuwe rijen
-- -5- select inhoud tabel
--
--------------------------------------------------------------------------------


--RvdP-- Tussenstap:
--RvdP-- Bijhoudingsverantwoordelijkheid: iedereen die nu in de database zit als ingeSCHRevene, heeft per geboortedatum
--RvdP-- een College als verantwoordelijke. Hiertoe aanpassen:
--RvdP-- 1) his_bijhoudingsverantwoordelijkheid, 

BEGIN;

insert into kern.his_persbijhverantwoordelijk
(id, pers, dataanvgel, tsreg, actieinh, verantwoordelijke) select id, pers, datgeboorte, tsreg, actieinh, 1 from kern.his_persgeboorte
where pers in (select id from kern.pers where srt=1);
--RvdP-- 2) EN aanpassen actueel=college, althans: voor soort=1!
update kern.pers set verantwoordelijke = 1 where srt=1;
--RvdP-- Einde tussenstap.


TRUNCATE "kern"."persvoornaam" CASCADE;
COMMIT;
BEGIN;

-- RvdP -- We hebben een terugkerend probleem rondom de stamtabelvulling 'soort betrokkenheid'.
-- Nu wederom(!) gepatched: omdraaien van Kind en Partner (POK=>KOP).
update kern.betr set rol = (4-rol) where not rol is null;
--NB: Deze regel moet aan het begin, aan het eind van deze afterburner wordt uitgegaan van KOP bij toevoegen Adam & Eva.

COMMIT;
BEGIN;
ALTER SEQUENCE "kern"."seq_persvoornaam" RESTART WITH 1;

INSERT INTO "kern"."persvoornaam"
  (
--RvdP-- Aangepast: ook id vullen; overlaten aan postgres gaat fout. Bron: 'pers' van HSN:
  id
  , pers
  , volgnr
  , naam
  , persvoornaamstatushis
  )
SELECT 
--RvdP-- Aangepast: ook id vullen; overlaten aan postgres gaat fout. Bron: 'pers' van HSN:
 HSN.pers,		--RvdP-- Bron voor id.
  HSN.pers,                     -- pers,
  1 as volgnr,                  -- volgnr,
  CASE
    WHEN position(' ' in HSN.voornamen) = 0
         THEN HSN.voornamen
    ELSE substr(HSN.voornamen, 0, (position(' ' in HSN.voornamen) + 1) ) 
       
  END as voornaam , 
  'A' as persvoornaamstatushis  -- persvoornaamstatushis
FROM "kern"."his_perssamengesteldenaam" HSN where HSN.tsverval is null and HSN.dateindegel is null
order by 1;
--
INSERT INTO "kern"."persvoornaam"
  (
--KHG-- Aangepast: ook namen die vervallen zijn, maar dan 'X' voor persvoornaamstatushis
  id
  , pers
  , volgnr
  , naam
  , persvoornaamstatushis
  )
SELECT 
 HSN.pers,		--RvdP-- Bron voor id.
  HSN.pers,                     -- pers,
  1 as volgnr,                  -- volgnr,
  CASE
    WHEN position(' ' in HSN.voornamen) = 0
         THEN HSN.voornamen
    ELSE substr(HSN.voornamen, 0, (position(' ' in HSN.voornamen) + 1) ) 
       
  END as voornaam , 
  'X' as persvoornaamstatushis  -- persvoornaamstatushis
FROM "kern"."his_perssamengesteldenaam" HSN where HSN.tsverval is not null or HSN.dateindegel is not null
order by 1;


-- end-of-script-part (01)

-- <Stap-02>
-- opnieuw vullen van [his_persvoornaam] vanuit his_samengesteldenaam
-- van dubbele namen wordt alleen de eerste naam gebruikt
-- -1- verwijdere alle rijen in persvoornaam
-- -2- reset de sequence
-- -3- commit
-- -4- insert nieuwe rijen
-- -5- select inhoud tabel
--
--------------------------------------------------------------------------------
TRUNCATE "kern"."his_persvoornaam" CASCADE;
COMMIT;
BEGIN;
ALTER SEQUENCE "kern"."seq_his_persvoornaam" RESTART WITH 1;

INSERT INTO "kern"."his_persvoornaam" 
  (      id , 
    persvoornaam,
    dataanvgel,
    dateindegel,
    tsreg,
    tsverval,
    actieinh,
    actieverval,
    actieaanpgel,
    naam
   ) 
SELECT
	hsn.id as id,
  hsn.pers         as persvoornaam, 
  HSN.dataanvgel   as dataanvgel,
  HSN.dateindegel  as dateindegel,
  HSN.tsreg        as tsreg,
  HSN.tsverval     as tsverval,
  HSN.actieinh     as actieinh,
  HSN.actieverval  as actieverval,
  HSN.actieaanpgel as actieaanpgel,
  CASE
    WHEN position(' ' in HSN.voornamen) = 0
         THEN HSN.voornamen
    ELSE substr(HSN.voornamen, 0, (position(' ' in HSN.voornamen) + 1) ) 
       
  END as naam  
-- , HSN.voornamen    as naam
FROM 
  "kern"."his_perssamengesteldenaam" HSN ;
--
  
-- end-of-script-part (02)

-- <Stap-03>
-- opnieuw vullen van [persgeslnaamcomp] vanuit his_perssamengesteldenaam
-- dubbele achternamen worden samengesteld
-- -1- verwijdere alle rijen in persgeslnaamcomp
-- -2- reset de sequence
-- -3- commit
-- -4- insert nieuwe rijen
-- -5- select inhoud tabel
--
--------------------------------------------------------------------------------
--TRUNCATE "kern"."persgeslnaamcomp" CASCADE;
--COMMIT;
--BEGIN;
--ALTER SEQUENCE "kern"."seq_persgeslnaamcomp" RESTART WITH 1;

--INSERT INTO "kern"."persgeslnaamcomp" 
--  ( -- id , -- wordt gegenereerd a.d.h.v. sequence
--RvdP-- aangepast: ook id vullen...
--	id,
--  pers,
--    volgnr,
--    voorvoegsel,
--    scheidingsteken,
--    naam,
--    predikaat,
--    adellijketitel,
--    persgeslnaamcompstatushis
--   ) 
--SELECT 
  --  id,
  --RvdP-- Aangepast: bron voor id is pers...
--  pers			   as id,
--  pers             as pers,
--  1                as volgnr,
--  voorvoegsel      as voorvoegsel,
--  scheidingsteken  as scheidingsteken,
--  geslnaam         as naam,
--  predikaat        as predikaat,
--  adellijketitel   as adellijketitel,
--  'A'              as persgeslnaamcompstatushis
--FROM 
--  "kern"."his_perssamengesteldenaam" where tsverval is null and dateindegel is null
--  order by 1 ;
  
-- end-of-script-part (03)

-- <Stap-04>
-- opnieuw vullen van [his_persgeslnaamcomp] vanuit his_perssamengesteldenaam
-- 
-- -1- verwijdere alle rijen in his_persgeslnaamcomp
-- -2- reset de sequence
-- -3- commit
-- -4- insert nieuwe rijen
-- -5- select inhoud tabel
--
--------------------------------------------------------------------------------
--TRUNCATE "kern"."his_persgeslnaamcomp" CASCADE;
--COMMIT;
--BEGIN;
--ALTER SEQUENCE "kern"."seq_his_persgeslnaamcomp" RESTART WITH 1;

--INSERT INTO "kern"."his_persgeslnaamcomp"
--        ( id,  -- id wordt gegenereerd vanuit ssquence
--          persgeslnaamcomp,
--          dataanvgel,
--          dateindegel,
--          tsreg,
--          tsverval,
--          actieinh,
--          actieverval,
--          actieaanpgel,
--          voorvoegsel,
--          scheidingsteken,
--          naam,
--          predikaat,
--          adellijketitel
--        )
  
--SELECT 
--  HPS.id as id,
--  HPS.pers                    as persgeslnaamcomp,
--  HPS.dataanvgel              as dataanvgel,
--  HPS.dateindegel             as dateindegel,
--  HPS.tsreg                   as tsreg,
--  HPS.tsverval                as tsverval,
--  HPS.actieinh                as actieinh,
--  HPS.actieverval             as actieverval,
--  HPS.actieaanpgel            as actieaanpgel,
--  HPS.voorvoegsel             as voorvoegsel,
--  HPS.scheidingsteken         as scheidingsteken,
--  HPS.geslnaam                as naam,
--  HPS.predikaat               as predikaat,
--  HPS.adellijketitel          as adellijketitel
--FROM 
--  "kern"."his_perssamengesteldenaam" HPS
--order by HPS.id;
-- end-of-script-part (04)

--COMMIT;
BEGIN;

-- <Stap-05>
-- Wijzigen tabel [kern.persadres] met inhoud van [kern.his_persadres]
-- 
-- Update [kern.persadres] - 00 - adhv [kern.his_persadres] 
--RvdP-- Code hieronder werkte niet c.q. was fout: niet 'where U.persadres=pers' maar 'where U.persadres=kern.persadres.id'
UPDATE "kern"."persadres" SET srt = ( Select U.srt from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , rdnwijz = ( Select U.rdnwijz from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , aangadresh = ( Select U.aangadresh from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , dataanvadresh = ( Select U.dataanvadresh from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , adresseerbaarobject = ( Select U.adresseerbaarobject from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , identcodenraand = ( Select U.identcodenraand from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , gem = ( Select U.gem from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , nor = ( Select U.nor from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , afgekortenor = ( Select U.afgekortenor from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , gemdeel = ( Select U.gemdeel from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , huisnr = ( Select U.huisnr from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , huisletter = ( Select U.huisletter from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , huisnrtoevoeging = ( Select U.huisnrtoevoeging from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL)
                            , postcode = ( Select U.postcode from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , wpl = ( Select U.wpl from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , loctovadres = ( Select U.loctovadres from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , locoms = ( Select U.locoms from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , bladresregel1 = ( Select U.bladresregel1 from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , bladresregel2 = ( Select U.bladresregel2 from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , bladresregel3 = ( Select U.bladresregel3 from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , bladresregel4 = ( Select U.bladresregel4 from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , bladresregel5 = ( Select U.bladresregel5 from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , bladresregel6 = ( Select U.bladresregel6 from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , land = ( Select U.land from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) 
                            , datvertrekuitnederland = ( Select U.datvertrekuitnederland from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL)
WHERE EXISTS ( Select 1 from "kern"."his_persadres" U where U.persadres = kern.persadres.id AND U.dateindegel IS NULL) ;
-- end-of-script-part (05)

COMMIT;
BEGIN;

-- <Stap-06>
-- 
-- Update [kern.pers] - 01 - adhv [kern.his_persids]                                                                           
--UPDATE "kern"."pers" SET idsstatushis = 'X' WHERE idsstatushis = 'A';                                                   
--UPDATE "kern"."pers" SET bsn = ( Select U.bsn from "kern"."his_persids" U where U.pers = pers.id AND U.dateindegel IS NULL)
--                       , anr = ( Select U.anr from "kern"."his_persids" U where U.pers = pers.id AND U.dateindegel IS NULL)
--                       , idsstatushis = 'A'
--WHERE EXISTS ( Select 1 from "kern"."his_persids" U where U.pers = pers.id AND U.dateindegel IS NULL) ;
-- end-of-script-part (06)

COMMIT;
BEGIN;

-- <Stap-07>
-- 
-- Update [kern.per] adhv [kern.his_persgelachtsaand]                                                                                                                                                                                                                                                                                                                
--UPDATE "kern"."pers" SET geslachtsaandstatushis = 'X' WHERE geslachtsaandstatushis = 'A';                                                                                                                                                                                                                         
--UPDATE "kern"."pers" SET geslachtsaand = ( Select U.geslachtsaand from "kern"."his_persgeslachtsaand" U where U.pers = pers.id AND U.dateindegel IS NULL) 
--                       , geslachtsaandstatushis = 'A' 
--WHERE EXISTS ( Select 1 from "kern"."his_persgeslachtsaand" U where U.pers = pers.id AND U.dateindegel IS NULL) ;
-- end-of-script-part (07)

COMMIT;
BEGIN;

-- <Stap-08>
-- 
-- Update [kern.per] adhv [kern.his_perssamengesteldenaam]                                                                                                                                                                                                                                                                                                         
--UPDATE "kern"."pers" SET samengesteldenaamstatushis = 'X' WHERE samengesteldenaamstatushis = 'A';                                                                                                                                                                                                              
--UPDATE "kern"."pers" SET predikaat = ( Select U.predikaat from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NULL)
--                       , voornamen = ( Select U.voornamen from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NULL)
--                       , voorvoegsel = ( Select U.voorvoegsel from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NULL)
--                       , scheidingsteken = ( Select U.scheidingsteken from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NULL)
--                       , adellijketitel = ( Select U.adellijketitel from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NULL)
--                       , geslnaam = ( Select U.geslnaam from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NULL)
--                       , indnreeksalsgeslnaam = ( Select U.indnreeksalsgeslnaam from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NULL)
--                       , indalgoritmischafgeleid = ( Select U.indalgoritmischafgeleid from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NULL)
--                       , samengesteldenaamstatushis = 'A' 
--WHERE EXISTS ( Select 1 from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NULL) ;
-- khg update voor de is not null
--UPDATE "kern"."pers" SET predikaat = ( Select U.predikaat from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NOT NULL)
--                       , voornamen = ( Select U.voornamen from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NOT NULL)
--                       , voorvoegsel = ( Select U.voorvoegsel from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NOT NULL)
--                       , scheidingsteken = ( Select U.scheidingsteken from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NOT NULL)
--                       , adellijketitel = ( Select U.adellijketitel from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NOT NULL)
--                       , geslnaam = ( Select U.geslnaam from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NOT NULL)
--                       , indnreeksalsgeslnaam = ( Select U.indnreeksalsgeslnaam from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NOT NULL)
--                       , indalgoritmischafgeleid = ( Select U.indalgoritmischafgeleid from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NOT NULL)
--                       , samengesteldenaamstatushis = 'X' 
--WHERE EXISTS ( Select 1 from "kern"."his_perssamengesteldenaam" U where U.pers = pers.id AND U.dateindegel IS NOT NULL) ;
-- end-of-script-part (08)

COMMIT;
BEGIN;

--RvdP--
--RvdP-- Tussengevoegd: insert statement voor persaanschrijving.
--INSERT INTO kern.his_persaanschr
--( id,  pers,  dataanvgel, dateindegel,  tsreg,  tsverval,  actieinh,  actieverval,  actieaanpgel,  gebrgeslnaamegp,  indaanschrmetadellijketitels,  indaanschralgoritmischafgele,
--  predikaataanschr,   voornamenaanschr,  voorvoegselaanschr,  scheidingstekenaanschr,  geslnaamaanschr)
--SELECT
-- id,  pers,  dataanvgel,  dateindegel,  tsreg,  tsverval,  actieinh,  actieverval,  actieaanpgel,   1 as gebrgeslnaamegp, null,
--  indalgoritmischafgeleid,  predikaat,  voornamen,  voorvoegsel,  scheidingsteken,  geslnaam
--FROM  kern.his_perssamengesteldenaam where kern.his_perssamengesteldenaam.pers in (select pers.id from kern.pers where srt=1) ORDER BY ID ;  
--RvdP-- Einde simpele kopieeractie van samengestelde naam naar aanschrijving. Moet alleen gebeuren voor personen van 'soort=1'

-- <Stap-09>   LET OP! Van [kern.his_persaanschr] is geen data aangeleveerd !!!
--RvdP-- De data voor persaanschrijving is boven toegevoegd
-- 
-- Update [kern.per] adhv [kern.his_persaanschr] 
--UPDATE "kern"."pers" SET aanschrstatushis = 'X' WHERE aanschrstatushis = 'A';
--UPDATE "kern"."pers" SET gebrgeslnaamegp = ( Select U.gebrgeslnaamegp from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NULL)
--                       , indaanschrmetadellijketitels = ( Select U.indaanschrmetadellijketitels from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NULL)
--                       , indaanschralgoritmischafgele = ( Select U.indaanschralgoritmischafgele from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NULL)
--                       , predikaataanschr = ( Select U.predikaataanschr from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NULL)
--                       , voornamenaanschr = ( Select U.voornamenaanschr from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NULL)
--                       , voorvoegselaanschr = ( Select U.voorvoegselaanschr from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NULL)
--                       , adellijketitelaanschr = ( Select U.adellijketitelaanschr from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NULL)
--                       , scheidingstekenaanschr = ( Select U.scheidingstekenaanschr from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NULL)
--                       , geslnaamaanschr = ( Select U.geslnaamaanschr from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NULL)
--                       , aanschrstatushis = 'A' 
--WHERE EXISTS ( Select 1 from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NULL) ;
-- khg update voor de is not null
--UPDATE "kern"."pers" SET gebrgeslnaamegp = ( Select U.gebrgeslnaamegp from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NOT NULL)
--                       , indaanschrmetadellijketitels = ( Select U.indaanschrmetadellijketitels from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NOT NULL)
--                       , indaanschralgoritmischafgele = ( Select U.indaanschralgoritmischafgele from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NOT NULL)
--                       , predikaataanschr = ( Select U.predikaataanschr from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NOT NULL)
--                       , voornamenaanschr = ( Select U.voornamenaanschr from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NOT NULL)
--                       , voorvoegselaanschr = ( Select U.voorvoegselaanschr from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NOT NULL)
--                       , scheidingstekenaanschr = ( Select U.scheidingstekenaanschr from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NOT NULL)
--                       , geslnaamaanschr = ( Select U.geslnaamaanschr from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NOT NULL)
--                       , aanschrstatushis = 'X' 
--WHERE EXISTS ( Select 1 from "kern"."his_persaanschr" U where U.pers = pers.id AND U.dateindegel IS NOT NULL) ;
-- end-of-script-part (09)

COMMIT;
BEGIN;

-- <Stap-10>
-- 
-- Update [kern.pers] adhv [kern.his_persgeboorte] 
--UPDATE "kern"."pers" SET geboortestatushis = 'X' WHERE geboortestatushis = 'A';
--UPDATE "kern"."pers" SET datgeboorte = ( Select U.datgeboorte from "kern"."his_persgeboorte" U where U.pers = pers.id )
--                       , gemgeboorte = ( Select U.gemgeboorte from "kern"."his_persgeboorte" U where U.pers = pers.id )
--                       , wplgeboorte = ( Select U.wplgeboorte from "kern"."his_persgeboorte" U where U.pers = pers.id )
--                       , blgeboorteplaats = ( Select U.blgeboorteplaats from "kern"."his_persgeboorte" U where U.pers = pers.id )
--                       , blregiogeboorte = ( Select U.blregiogeboorte from "kern"."his_persgeboorte" U where U.pers = pers.id )
--                       , landgeboorte = ( Select U.landgeboorte from "kern"."his_persgeboorte" U where U.pers = pers.id )
--                       , omsgeboorteloc = ( Select U.omsgeboorteloc from "kern"."his_persgeboorte" U where U.pers = pers.id )
--                       , geboortestatushis = 'A' 
--WHERE EXISTS ( Select 1 from "kern"."his_persgeboorte" U where U.pers = pers.id ) ;
-- end-of-script-part (10)

COMMIT;
BEGIN;

-- <Stap-11>
-- 
-- Update [kern.per] adhv [kern.his_persoverlijden]
-- Update [kern.pers] - 01 - adhv [kern.his_persoverlijden]    
--UPDATE "kern"."pers" SET overlijdenstatushis = 'X' WHERE overlijdenstatushis = 'A';
--UPDATE "kern"."pers" SET datoverlijden = ( Select U.datoverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id )
--                       , gemoverlijden = ( Select U.gemoverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id )
--                       , wploverlijden = ( Select U.wploverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id )
--                       , blplaatsoverlijden = ( Select U.blplaatsoverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id )
--                       , blregiooverlijden = ( Select U.blregiooverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id )
--                       , landoverlijden = ( Select U.landoverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id )
--                       , omslocoverlijden = ( Select U.omslocoverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id )
--                       , overlijdenstatushis = 'A' 
--WHERE EXISTS ( Select 1 from "kern"."his_persoverlijden" U where U.pers = pers.id ) ; 
-- end-of-script-part (11)

COMMIT;
BEGIN;

-- RvdP Was vergeten: update van de actuele laag van persoonindicatie op basis van his_persindicatie.
UPDATE "kern"."persindicatie" SET persindicatiestatushis = 'A' where exists (select 1 from kern.his_persindicatie U where U.dateindegel is null and U.tsverval is null);
-- Gebruik maken van feit dat 'waarde' van de indicatie alleen maar 'true' kan zijn, en dat dus ook is als de status gelijk is aan 'A':
UPDATE "kern"."persindicatie" SET waarde = TRUE where persindicatiestatushis = 'A';
-- <End-of-Script>





-- Update [kern.pers] - 01 - adhv [kern.his_persoverlijden]    
--UPDATE "kern"."pers" SET overlijdenstatushis = 'X' WHERE overlijdenstatushis = 'A';
--UPDATE "kern"."pers" SET datoverlijden = ( Select U.datoverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id ) WHERE EXISTS ( Select 1 from "kern"."his_persoverlijden" U where U.pers = pers.id ) ;
--UPDATE "kern"."pers" SET gemoverlijden = ( Select U.gemoverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id ) WHERE EXISTS ( Select 1 from "kern"."his_persoverlijden" U where U.pers = pers.id ) ;
--UPDATE "kern"."pers" SET wploverlijden = ( Select U.wploverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id ) WHERE EXISTS ( Select 1 from "kern"."his_persoverlijden" U where U.pers = pers.id ) ;
--UPDATE "kern"."pers" SET blplaatsoverlijden = ( Select U.blplaatsoverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id ) WHERE EXISTS ( Select 1 from "kern"."his_persoverlijden" U where U.pers = pers.id ) ;
--UPDATE "kern"."pers" SET blregiooverlijden = ( Select U.blregiooverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id ) WHERE EXISTS ( Select 1 from "kern"."his_persoverlijden" U where U.pers = pers.id ) ;
--UPDATE "kern"."pers" SET landoverlijden = ( Select U.landoverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id ) WHERE EXISTS ( Select 1 from "kern"."his_persoverlijden" U where U.pers = pers.id ) ;
--UPDATE "kern"."pers" SET omslocoverlijden = ( Select U.omslocoverlijden from "kern"."his_persoverlijden" U where U.pers = pers.id ) WHERE EXISTS ( Select 1 from "kern"."his_persoverlijden" U where U.pers = pers.id ) ;
--UPDATE "kern"."pers" SET overlijdenstatushis = 'A' WHERE EXISTS ( Select 1 from "kern"."his_persoverlijden" U where U.pers = pers.id ) ;


COMMIT;
BEGIN;


--RvdP-- Update van kern.relatie waar aanwezig kern.his_relatie
UPDATE "kern"."relatie" SET omslocaanv= (Select H.omslocaanv from kern.his_relatie H where H.relatie=relatie.id) where exists (select 1 from kern.his_relatie H where H.relatie=relatie.id);
UPDATE "kern"."relatie" SET landaanv= (Select H.landaanv from kern.his_relatie H where H.relatie=relatie.id) where exists (select 1 from kern.his_relatie H where H.relatie=relatie.id);
UPDATE "kern"."relatie" SET dataanv = (select dataanv from kern.his_relatie H where H.relatie=relatie.id) where exists (select 1 from kern.his_relatie H where H.relatie=relatie.id);
 

COMMIT;
BEGIN;


--RvdP-- We hebben nu ook opschorting wegens 'fout'. Deze ook doen!
--update kern.pers A set rdnopschortingbijhouding = (select rdnopschortingbijhouding from kern.his_persopschorting B where B.pers = A.id);
--update kern.pers set opschortingstatushis='A' where rdnopschortingbijhouding is not null;


COMMIT;
BEGIN;


--RvdP-- VOLGENDE BLOK NIET MEER NODIG: WE VULLEN NU EXPLICIET WOONPLAATSCODE!
--RvdP-- VOLGENDE BLOK NIET MEER NODIG--RvdP-- Zoals beloofd op de 15e dient ook de woonplaatscode gevuld te zijn.
--RvdP-- VOLGENDE BLOK NIET MEER NODIG--RvdP-- De queries hiervoor zijn:
--RvdP-- VOLGENDE BLOK NIET MEER NODIG-- update kern.persadres set wpl = null;
--RvdP-- VOLGENDE BLOK NIET MEER NODIG-- update kern.his_persadres set wpl=null;
--RvdP-- VOLGENDE BLOK NIET MEER NODIG-- voor Haarlem:
--RvdP-- VOLGENDE BLOK NIET MEER NODIGupdate kern.persadres set wpl=1902 where gem=395;
--RvdP-- VOLGENDE BLOK NIET MEER NODIG-- end Haarlem
--RvdP-- VOLGENDE BLOK NIET MEER NODIG-- voor Utrecht:
--RvdP-- VOLGENDE BLOK NIET MEER NODIGupdate kern.persadres set wpl=2289 where gem= 347;
--RvdP-- VOLGENDE BLOK NIET MEER NODIG-- voor Arnhem:
--RvdP-- VOLGENDE BLOK NIET MEER NODIGupdate kern.persadres set wpl=297 where gem= 205;
--RvdP-- VOLGENDE BLOK NIET MEER NODIG-- voor Groningen:
--RvdP-- VOLGENDE BLOK NIET MEER NODIGupdate kern.persadres set wpl=71 where gem= 17;
--RvdP-- VOLGENDE BLOK NIET MEER NODIG-- voor Rotterdam:
--RvdP-- VOLGENDE BLOK NIET MEER NODIGupdate kern.persadres set wpl=2081 where gem= 602;
--RvdP-- VOLGENDE BLOK NIET MEER NODIG-- voor Den Haag:
--RvdP-- VOLGENDE BLOK NIET MEER NODIGupdate kern.persadres set wpl=246 where gem= 521 ;
--RvdP-- VOLGENDE BLOK NIET MEER NODIGupdate kern.his_persadres A set wpl = B.wpl from kern.persadres B
--RvdP-- VOLGENDE BLOK NIET MEER NODIGwhere B.id = A.persadres and A.dateindegel is null and A.tsverval is null;
--RvdP-- VOLGENDE BLOK NIET MEER NODIG--  -- specifiek persoon: P1 van Utrecht: bijbehorend kern.persadres id is 1000007
--RvdP-- VOLGENDE BLOK NIET MEER NODIG--  update kern.his_persadres set wpl=2289 where id = 1000007;
--RvdP-- VOLGENDE BLOK NIET MEER NODIG--RvdP-- Einde update woonplaats

--RvdP-- Nu overgaan naar de niet ingeschreven personen: eerst Adam van Modernodam, dan Eva van Modernodam
insert into kern.pers (id, srt, idsstatushis, geslachtsaandstatushis, samengesteldenaamstatushis, aanschrstatushis, geboortestatushis, overlijdenstatushis, uitslnlkiesrstatushis, euverkiezingenstatushis, bijhverantwoordelijkheidstat, opschortingstatushis, bijhgemstatushis, verblijfsrstatushis, pkstatushis, inschrstatushis, tijdstiplaatstewijz, immigratiestatushis, voornamen, voorvoegsel, scheidingsteken, geslnaam, indnreeksalsgeslnaam, geslachtsaand) select id+1000000, 2, 'X', 'A', 'A', 'X', 'A', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X','X', now(), 'X', 'Adam', 'van', ' ', 'Modernodam', false, 1 from kern.pers where srt=1 and not exists (select 42 from kern.betr where rol=1 AND Pers = pers.id);
--RvdP: Voor iedereen die nog niet betrokken was als 'kind' in een relatie, komt er een 'adam' als vader...

COMMIT;
BEGIN;
insert into kern.pers (id, srt, idsstatushis, geslachtsaandstatushis, samengesteldenaamstatushis, aanschrstatushis, geboortestatushis, overlijdenstatushis, uitslnlkiesrstatushis, euverkiezingenstatushis, bijhverantwoordelijkheidstat, opschortingstatushis, bijhgemstatushis, verblijfsrstatushis, pkstatushis, inschrstatushis, tijdstiplaatstewijz, immigratiestatushis, voornamen, voorvoegsel, scheidingsteken, geslnaam, indnreeksalsgeslnaam, geslachtsaand) select id+2000000, 2, 'X', 'A', 'A', 'X', 'A', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X','X', now(), 'X', 'Eva', 'van', ' ', 'Modernodam', false,2 from kern.pers where srt=1 and not exists (select 42 from kern.betr where rol=1 AND Pers = pers.id);

COMMIT;
BEGIN;

--RvdP-- Eva en Adam zijn met 'actie 0' begonnen, op 1 januari  1980
insert into kern.actie (id, srt, partij, tijdstipreg) values (0,1, 2000, '19800101');

COMMIT;
BEGIN;

--RvdP-- Geboorteactie van Adam en Eva zijn de actie waarmee betreffende persoon is geboren; hiermee is ook de relatie=famrechtbetr ontstaan:
insert into kern.Relatie(id, srt, relatiestatushis) select 33000000+id, 3, 'A' from kern.pers;

COMMIT;
BEGIN;

--RvdP-- eerst de betrokkenheden van Adam en Eva: bron 'personen soort 1 zonder ouders'
insert into kern.betr(id, relatie, rol, Pers, indouder, ouderschapstatushis, ouderlijkgezagstatushis)
   select 1000000+id, 33000000+id, 2, id+1000000, true, 'A', 'X' from kern.pers where not exists (select 42 from kern.betr where rol=1 AND Pers=pers.id) and srt=1;
   
COMMIT;
BEGIN;

insert into kern.betr(id, relatie, rol, Pers, indouder, ouderschapstatushis, ouderlijkgezagstatushis)
   select 2000000+id, 33000000+id, 2, id+2000000, true, 'A', 'X' from kern.pers where not exists (select 42 from kern.betr where rol=1 AND Pers=pers.id) and srt=1;

   
COMMIT;
BEGIN;

--RvdP-- nu de betrokkenheden voor iedereen die NOG GEEN OUDERS heeft (hierna heeft iedereen ouders...)
insert into kern.betr(id, relatie, rol, Pers, ouderschapstatushis, ouderlijkgezagstatushis)
	select 3000000+id, 33000000+id, 1, id, 'X', 'X' from kern.pers where not exists (select 42 from kern.betr where rol=1 AND Pers=pers.id) and srt=1;

COMMIT;
BEGIN;
	
--RvdP-- Betrokkenheid -- historie
insert into kern.his_betrouderschap (id, betr, dataanvgel, tsreg, actieinh, indouder)
	select A.id, A.id, B.datgeboorte, B.tsreg, B.actieinh, true
	from kern.betr A, kern.his_persgeboorte B
	where not exists (select 42 from kern.pers where srt=1 AND A.Pers=pers.id)
	-- Ok, alleen de Adam's en Eva's...
	and exists (select 42 from kern.betr C where C.relatie = A.relatie AND C.Pers=B.pers);
	-- Ok, alleen voor personen die een his_geboorte hebben, die betrokken is in dezelfde relatie.
		
	
--RvdP-- Vergeten was: Nationaliteit.
--RvdP-- Gelukkig is dit een simpele: IEDEREEN (van soort=1) is nederlander, per datum geboorte.
--RvdP-- Dus: kopieer kern.pers naar kern.persnation voor soort=1; kopieer his_persgeboorte naar kern.his_persnation
--RvdP-- Reden verkrijging is: reden nr 18 (code: 17).
insert into kern.persnation (id, pers, nation, persnationstatushis, rdnverk)
	select id, id, 2, 'A', 18 from kern.pers where srt=1;

COMMIT;
BEGIN;

-- RvdP-- Bijhoudingsgemeente wordt blijkbaar niet gekopieerd van historie naar actueel...
-- RvdP -- Alsnog toegevoegd:
-- khg update voor de is not null
--update kern.pers A set bijhgemstatushis = 'X' where exists (select 1 from kern.his_persbijhgem B where B.pers=A.id and B.dateindegel is not null);
--UPDATE "kern"."pers" SET bijhgem = ( Select U.bijhgem from "kern"."his_persbijhgem" U where U.pers = pers.id AND U.dateindegel is null )
--                       , datinschringem = ( Select U.datinschringem from "kern"."his_persbijhgem" U where U.pers = pers.id AND U.dateindegel is null)
--                       , indonverwdocaanw = ( Select U.indonverwdocaanw from "kern"."his_persbijhgem" U where U.pers = pers.id AND U.dateindegel is null)
--                       , bijhgemstatushis = 'A' 
--WHERE EXISTS ( Select 1 from "kern"."his_persbijhgem" U where U.pers = pers.id AND U.dateindegel is null ) ; 

	
insert into kern.his_persnation (id, persnation, dataanvgel, tsreg, actieinh, rdnverk)
	select A.id, A.id, B.datgeboorte, B.tsreg, B.actieinh, 18 from kern.persnation A, kern.his_persgeboorte B
	where B.pers=A.id and exists (select 42 from kern.pers where srt=1 AND pers.id=A.pers);
	
COMMIT;
BEGIN;
-- RvdP-- Niet gevuld: groep inschrijving...
-- RvdP -- Alsnog toegevoegd, en A-laag van persoon bijgewerkt:
insert into kern.his_persinschr (id, pers, tsreg, actieinh, datinschr, versienr)
select id, pers, tsreg, actieinh, datgeboorte, 1 from kern.his_persgeboorte HG where
HG.pers in (select id from kern.pers where srt = 1);
update kern.pers set datinschr = datgeboorte where srt=1;
update kern.pers set versienr = 1 where srt=1;
-- RvdP -- Updaten van status his is niet nodig: deze blijkt al te kloppen (c.q. voor iedereen van 'soort 1' gelijk aan A te zijn en rest gelijk X
COMMIT;

-- RvdP: Specifiek voor de tabel his_persadres geldt dat we opteren voor de 'vastlegging zoals gebeurd door BRP' ipv
-- vastlegging zoals zou zijn gedaan door migratie vanuit GBA-V. Dat betekent dat de 'glazen bol records' (die "weten" wanneer ze in de toekomst
-- worden aangepast) worden vervangen door TWEE records: een vervallen EN een ingekort record.
-- Stap 1: de glazen-bol records worden eerst vervallen verklaard:
update kern.his_persadres A
set tsverval = (select tsreg from kern.his_persadres B
where A.persadres = B.persadres and B.id = A.id - 1)
where not dateindegel is null 
; -- het minteken: de volgorde is precies verkeerd om: nieuwe records eerst ipv oude eerst

update kern.his_persadres A
set actieverval = (select actieinh from kern.his_persadres B
where A.persadres = B.persadres and B.id = A.id - 1)
where not dateindegel is null 
;

-- De glazen bol records zijn nu vervallen.
-- Stap 2: creëer records met inhoud van de records die vervallen zijn (tsverval <> null),
-- met actieinh hetzelfde, en actieverval wordt actieaanpassingeldigheid
insert into kern.his_persadres
(persadres ,  dataanvgel ,  dateindegel ,  tsreg ,  tsverval ,  actieinh ,  actieverval ,  actieaanpgel ,  srt ,
  rdnwijz ,  aangadresh ,  dataanvadresh ,  adresseerbaarobject ,  identcodenraand ,  gem ,  nor ,  afgekortenor ,
  gemdeel ,  huisnr ,  huisletter ,  huisnrtoevoeging ,  postcode ,  wpl ,  loctovadres ,  locoms ,  bladresregel1 ,
  bladresregel2 ,  bladresregel3 ,  bladresregel4 ,  bladresregel5 ,  bladresregel6 ,  land ,  datvertrekuitnederland )
  select
  persadres ,  dataanvgel ,  dateindegel ,  tsverval ,  null ,  actieinh ,  null ,  actieverval ,  srt ,
  rdnwijz ,  aangadresh ,  dataanvadresh ,  adresseerbaarobject ,  identcodenraand ,  gem ,  nor ,  afgekortenor ,
  gemdeel ,  huisnr ,  huisletter ,  huisnrtoevoeging ,  postcode ,  wpl ,  loctovadres ,  locoms ,  bladresregel1 ,
  bladresregel2 ,  bladresregel3 ,  bladresregel4 ,  bladresregel5 ,  bladresregel6 ,  land ,  datvertrekuitnederland

  from kern.his_persadres where not tsverval is null;

update kern.his_persadres
set dateindegel=null where tsverval is not null;

-- De glazenbol records HADDEN al een datum aanpassing geldigheid. Die wordt leeggemaakt:
update kern.his_persadres set actieaanpgel = null where actieaanpgel = actieverval;



--RvdP-- Wens leveranciers: wegnemen van verwarrend feit dat ouders van partners dezelfde naam hebben (maar niet dezelfde persoon zijn).
--RvdP-- Via de volgende queries wordt dit opgelost: hierna heet 'Adam' niet meer 'Adam', maar 'Adam-Xxxx' met Xxxx de naam van zijn zoon of dochter...
--RvdP-- NB: Deze hernoemactie moet NA dat Eva en Adam als 'ouder' zijn gekoppeld. Hierom verplaatst naar (logisch) einde van de afterburner:
begin;
create table tempA as
select A.id, A.voornamen as ouder, B.voornamen as kind from
kern.pers A, kern.pers B, kern.betr C, kern.betr D
where A.id=C.pers and D.relatie=C.relatie and C.id <> D.id and D.pers=B.id and D.rol=1 and A.srt <>1;
commit;

begin;
alter table tempA add PRIMARY KEY (id);
commit;

--begin;
--update kern.pers P
--set voornamen = voornamen || (select '-' || kind from tempA A where A.id = P.id)
--where P.srt <> 1;
--commit;

begin;
drop table tempA;
commit;
--RvdP-- Einde van hernoemactie
--RvdP-- TODO: ook his_samengestelde naam vullen...


--RvdP-- In de whiteboxfiller programmatuur (dd 13 september 2012) werd het ingevulde scheidingsteken niet correct
--RvdP-- overgenomen uit het excelsheet. Daarom hier hersteld.
--RvdP-- Te herstellen in:
--RvdP 1) persoonstabel, op twee plaatsen;
--RvdP 2) in his_persaanschr;
--RvdP 3) in his_perssamengesteldenaam;
--RvdP 4) in persgeslachtsnaamcomponent;
--RvdP 5) in his_persgeslachtsnaamcomponent.
--update kern.pers set scheidingsteken = ' ', scheidingstekenaanschr = ' ' where voorvoegsel is not null;
--update kern.his_persaanschr set scheidingstekenaanschr = ' ' where voorvoegselaanschr is not null;
--update kern.his_perssamengesteldenaam set scheidingsteken = ' ' where voorvoegsel is not null;
--update kern.persgeslnaamcomp set scheidingsteken = ' ' where voorvoegsel is not null;
--update kern.his_persgeslnaamcomp set scheidingsteken = ' ' where voorvoegsel is not null;









BEGIN;
	
--RvdP-- Toegevoegd: laatste versie van de queries die de sequences ophoogt, zoals gemaild door Magnus d.d. 31 mei 22:45:54
-- SELECT  'SELECT SETVAL('''||TA.schemaname||'.'||S.relname||''', MAX(' ||(C.attname)|| ') ) FROM "'||TA.schemaname||'"."'||T.relname||'" ;'
-- FROM pg_class AS S
--    , pg_depend AS D
--    , pg_class AS T
--    , pg_attribute AS C
--    , pg_tables AS TA
-- WHERE S.relkind       = 'S'
--     AND S.oid         = D.objid
--     AND D.refobjid    = T.oid
--     AND D.refobjid    = C.attrelid
--     AND D.refobjsubid = C.attnum
--     AND T.relname     = TA.tablename
-- ORDER BY S.relname;


-- RESULTAAT van bovenstaande query:

COMMIT;

BEGIN;

SELECT SETVAL('lev.seq_abonnement', MAX(id) ) FROM "lev"."abonnement" ;
SELECT SETVAL('lev.seq_abonnementgegevenselement', MAX(id) ) FROM "lev"."abonnementgegevenselement" ;
SELECT SETVAL('lev.seq_abonnementsrtber', MAX(id) ) FROM "lev"."abonnementsrtber" ;
SELECT SETVAL('kern.seq_actie', MAX(id) ) FROM "kern"."actie" ;
SELECT SETVAL('autaut.seq_authenticatiemiddel', MAX(id) ) FROM "autaut"."authenticatiemiddel" ;
SELECT SETVAL('autaut.seq_autorisatiebesluit', MAX(id) ) FROM "autaut"."autorisatiebesluit" ;
SELECT SETVAL('ber.seq_ber', MAX(id) ) FROM "ber"."ber" ;
SELECT SETVAL('kern.seq_betr', MAX(id) ) FROM "kern"."betr" ;
SELECT SETVAL('autaut.seq_bijhautorisatie', MAX(id) ) FROM "autaut"."bijhautorisatie" ;
SELECT SETVAL('autaut.seq_bijhsituatie', MAX(id) ) FROM "autaut"."bijhsituatie" ;
SELECT SETVAL('kern.seq_bron', MAX(id) ) FROM "kern"."bron" ;
SELECT SETVAL('autaut.seq_certificaat', MAX(id) ) FROM "autaut"."certificaat" ;
SELECT SETVAL('kern.seq_doc', MAX(id) ) FROM "kern"."doc" ;
SELECT SETVAL('autaut.seq_doelbinding', MAX(id) ) FROM "autaut"."doelbinding" ;
SELECT SETVAL('autaut.seq_doelbindinggegevenselement', MAX(id) ) FROM "autaut"."doelbindinggegevenselement" ;
SELECT SETVAL('kern.seq_gegeveninonderzoek', MAX(id) ) FROM "kern"."gegeveninonderzoek" ;
SELECT SETVAL('lev.seq_his_abonnement', MAX(id) ) FROM "lev"."his_abonnement" ;
SELECT SETVAL('lev.seq_his_abonnementsrtber', MAX(id) ) FROM "lev"."his_abonnementsrtber" ;
SELECT SETVAL('autaut.seq_his_authenticatiemiddel', MAX(id) ) FROM "autaut"."his_authenticatiemiddel" ;
SELECT SETVAL('autaut.seq_his_autorisatiebesluit', MAX(id) ) FROM "autaut"."his_autorisatiebesluit" ;
SELECT SETVAL('autaut.seq_his_autorisatiebesluitbijhau', MAX(id) ) FROM "autaut"."his_autorisatiebesluitbijhau" ;
SELECT SETVAL('kern.seq_his_betrouderschap', MAX(id) ) FROM "kern"."his_betrouderschap" ;
SELECT SETVAL('kern.seq_his_betrouderlijkgezag', MAX(id) ) FROM "kern"."his_betrouderlijkgezag" ;
SELECT SETVAL('autaut.seq_his_bijhautorisatie', MAX(id) ) FROM "autaut"."his_bijhautorisatie" ;
SELECT SETVAL('autaut.seq_his_bijhsituatie', MAX(id) ) FROM "autaut"."his_bijhsituatie" ;
SELECT SETVAL('kern.seq_his_doc', MAX(id) ) FROM "kern"."his_doc" ;
SELECT SETVAL('autaut.seq_his_doelbinding', MAX(id) ) FROM "autaut"."his_doelbinding" ;
SELECT SETVAL('kern.seq_his_partijgem', MAX(id) ) FROM "kern"."his_partijgem" ;
SELECT SETVAL('kern.seq_his_multirealiteitregel', MAX(id) ) FROM "kern"."his_multirealiteitregel" ;
SELECT SETVAL('kern.seq_his_onderzoek', MAX(id) ) FROM "kern"."his_onderzoek" ;
SELECT SETVAL('kern.seq_his_partij', MAX(id) ) FROM "kern"."his_partij" ;
SELECT SETVAL('kern.seq_his_persaanschr', MAX(id) ) FROM "kern"."his_persaanschr" ;
SELECT SETVAL('kern.seq_his_persadres', MAX(id) ) FROM "kern"."his_persadres" ;
SELECT SETVAL('kern.seq_his_persbijhgem', MAX(id) ) FROM "kern"."his_persbijhgem" ;
SELECT SETVAL('kern.seq_his_persbijhverantwoordelijk', MAX(id) ) FROM "kern"."his_persbijhverantwoordelijk" ;
SELECT SETVAL('kern.seq_his_perseuverkiezingen', MAX(id) ) FROM "kern"."his_perseuverkiezingen" ;
SELECT SETVAL('kern.seq_his_persgeboorte', MAX(id) ) FROM "kern"."his_persgeboorte" ;
SELECT SETVAL('kern.seq_his_persgeslachtsaand', MAX(id) ) FROM "kern"."his_persgeslachtsaand" ;
SELECT SETVAL('kern.seq_his_persgeslnaamcomp', MAX(id) ) FROM "kern"."his_persgeslnaamcomp" ;
SELECT SETVAL('kern.seq_his_persids', MAX(id) ) FROM "kern"."his_persids" ;
SELECT SETVAL('kern.seq_his_persimmigratie', MAX(id) ) FROM "kern"."his_persimmigratie" ;
SELECT SETVAL('kern.seq_his_persindicatie', MAX(id) ) FROM "kern"."his_persindicatie" ;
SELECT SETVAL('kern.seq_his_persinschr', MAX(id) ) FROM "kern"."his_persinschr" ;
SELECT SETVAL('kern.seq_his_persnation', MAX(id) ) FROM "kern"."his_persnation" ;
SELECT SETVAL('kern.seq_his_persopschorting', MAX(id) ) FROM "kern"."his_persopschorting" ;
SELECT SETVAL('kern.seq_his_persoverlijden', MAX(id) ) FROM "kern"."his_persoverlijden" ;
SELECT SETVAL('kern.seq_his_perspk', MAX(id) ) FROM "kern"."his_perspk" ;
SELECT SETVAL('kern.seq_his_persreisdoc', MAX(id) ) FROM "kern"."his_persreisdoc" ;
SELECT SETVAL('kern.seq_his_perssamengesteldenaam', MAX(id) ) FROM "kern"."his_perssamengesteldenaam" ;
SELECT SETVAL('kern.seq_his_persuitslnlkiesr', MAX(id) ) FROM "kern"."his_persuitslnlkiesr" ;
SELECT SETVAL('kern.seq_his_persverblijfsr', MAX(id) ) FROM "kern"."his_persverblijfsr" ;
SELECT SETVAL('kern.seq_his_persverificatie', MAX(id) ) FROM "kern"."his_persverificatie" ;
SELECT SETVAL('kern.seq_his_persvoornaam', MAX(id) ) FROM "kern"."his_persvoornaam" ;
SELECT SETVAL('brm.seq_his_regelsituatie', MAX(id) ) FROM "brm"."his_regelsituatie" ;
SELECT SETVAL('kern.seq_his_relatie', MAX(id) ) FROM "kern"."his_relatie" ;
SELECT SETVAL('autaut.seq_his_uitgeslotene', MAX(id) ) FROM "autaut"."his_uitgeslotene" ;
SELECT SETVAL('lev.seq_lev', MAX(id) ) FROM "lev"."lev" ;
SELECT SETVAL('lev.seq_levcommunicatie', MAX(id) ) FROM "lev"."levcommunicatie" ;
SELECT SETVAL('lev.seq_levpers', MAX(id) ) FROM "lev"."levpers" ;
SELECT SETVAL('kern.seq_multirealiteitregel', MAX(id) ) FROM "kern"."multirealiteitregel" ;
SELECT SETVAL('kern.seq_onderzoek', MAX(id) ) FROM "kern"."onderzoek" ;
SELECT SETVAL('kern.seq_partij', MAX(id) ) FROM "kern"."partij" ;
SELECT SETVAL('kern.seq_partijrol', MAX(id) ) FROM "kern"."partijrol" ;
SELECT SETVAL('kern.seq_pers', MAX(id) ) FROM "kern"."pers" ;
SELECT SETVAL('kern.seq_persadres', MAX(id) ) FROM "kern"."persadres" ;
SELECT SETVAL('kern.seq_persgeslnaamcomp', MAX(id) ) FROM "kern"."persgeslnaamcomp" ;
SELECT SETVAL('kern.seq_persindicatie', MAX(id) ) FROM "kern"."persindicatie" ;
SELECT SETVAL('kern.seq_persnation', MAX(id) ) FROM "kern"."persnation" ;
SELECT SETVAL('kern.seq_persreisdoc', MAX(id) ) FROM "kern"."persreisdoc" ;
SELECT SETVAL('kern.seq_persverificatie', MAX(id) ) FROM "kern"."persverificatie" ;
SELECT SETVAL('kern.seq_persvoornaam', MAX(id) ) FROM "kern"."persvoornaam" ;
SELECT SETVAL('brm.seq_regelsituatie', MAX(id) ) FROM "brm"."regelsituatie" ;
SELECT SETVAL('kern.seq_relatie', MAX(id) ) FROM "kern"."relatie" ;
SELECT SETVAL('kern.seq_sector', MAX(id) ) FROM "kern"."sector" ;
SELECT SETVAL('autaut.seq_uitgeslotene', MAX(id) ) FROM "autaut"."uitgeslotene" ;

COMMIT;
