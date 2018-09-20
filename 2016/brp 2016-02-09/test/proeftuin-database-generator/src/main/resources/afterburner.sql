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
--RvdP-- Bijhoudingsaard: iedereen die nu in de database zit als ingeSCHRevene, heeft per geboortedatum
--RvdP-- een College als verantwoordelijke. Hiertoe aanpassen:
--RvdP-- 1) his_bijhaard,

-- anjaw: Hoe dient dit geimplementeerd te worden?
-- voor veel personen staat al een bijhouding record met dateinde = null
--
BEGIN;

insert into kern.his_persbijhouding (pers, dataanvgel, tsreg, actieinh, bijhaard, bijhpartij, naderebijhaard, indonverwdocaanw)
  select pg.pers, pg.datgeboorte, pg.tsreg, pg.actieinh, 1, pg.gemgeboorte, 1, false
  from kern.his_persgeboorte pg
  where 0 = (select count(*)
             from kern.his_persbijhouding bh
             where bh.pers = pers)
        and 1 = (select srt
                 from kern.pers
                 where id = pers);

--RvdP-- 2) EN aanpassen bijhaard=actueel, althans: voor soort=1!
update kern.pers set bijhaard = 1 where srt=1;
update kern.pers set naderebijhaard = 1 where bijhaard=1;
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

    END as voornaam
  FROM "kern"."his_perssamengesteldenaam" HSN where HSN.tsverval is null and HSN.dateindegel is null
  order by 1;
--


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
TRUNCATE "kern"."persgeslnaamcomp" CASCADE;
COMMIT;
BEGIN;
ALTER SEQUENCE "kern"."seq_persgeslnaamcomp" RESTART WITH 1;

INSERT INTO "kern"."persgeslnaamcomp"
( -- id , -- wordt gegenereerd a.d.h.v. sequence
--RvdP-- aangepast: ook id vullen...
  id,
  pers,
  volgnr,
  voorvoegsel,
  scheidingsteken,
  stam,
  predicaat,
  adellijketitel
)
  SELECT
    --  id,
    --RvdP-- Aangepast: bron voor id is pers...
    pers			   as id,
    pers             as pers,
    1                as volgnr,
    voorvoegsel      as voorvoegsel,
    scheidingsteken  as scheidingsteken,
    geslnaamstam     as stam,
    predicaat        as predicaat,
    adellijketitel   as adellijketitel
  FROM
    "kern"."his_perssamengesteldenaam" where tsverval is null and dateindegel is null
  order by 1 ;

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
TRUNCATE "kern"."his_persgeslnaamcomp" CASCADE;
COMMIT;
BEGIN;
ALTER SEQUENCE "kern"."seq_his_persgeslnaamcomp" RESTART WITH 1;

INSERT INTO "kern"."his_persgeslnaamcomp"
( id,
  persgeslnaamcomp,
  dataanvgel,
  dateindegel,
  tsreg,
  tsverval,
  actieinh,
  actieverval,
  actieaanpgel,
  voorvoegsel,
  scheidingsteken,
  stam,
  predicaat,
  adellijketitel
)

  SELECT
    HPS.id                      as id,
    HPS.pers                    as persgeslnaamcomp,
    HPS.dataanvgel              as dataanvgel,
    HPS.dateindegel             as dateindegel,
    HPS.tsreg                   as tsreg,
    HPS.tsverval                as tsverval,
    HPS.actieinh                as actieinh,
    HPS.actieverval             as actieverval,
    HPS.actieaanpgel            as actieaanpgel,
    HPS.voorvoegsel             as voorvoegsel,
    HPS.scheidingsteken         as scheidingsteken,
    HPS.geslnaamstam            as stam,
    HPS.predicaat               as predicaat,
    HPS.adellijketitel          as adellijketitel
  FROM
    "kern"."his_perssamengesteldenaam" HPS
  order by HPS.id;
-- end-of-script-part (04)

COMMIT;
BEGIN;

-- <Stap-05>
-- Wijzigen tabel [kern.persadres] met inhoud van [kern.his_persadres]
--
-- Update [kern.persadres] - 00 - adhv [kern.his_persadres]

UPDATE "kern"."persadres"
SET srt = U.srt,
  rdnwijz = U.rdnwijz,
  aangadresh = U.aangadresh,
  dataanvadresh = U.dataanvadresh,
  identcodeadresseerbaarobject = U.identcodeadresseerbaarobject,
  identcodenraand = U.identcodenraand,
  gem = U.gem,
  nor = U.nor,
  afgekortenor = U.afgekortenor,
  gemdeel = U.gemdeel,
  huisnr = U.huisnr,
  huisletter = U.huisletter,
  huisnrtoevoeging = U.huisnrtoevoeging,
  postcode = U.postcode,
  wplnaam = U.wplnaam,
  loctenopzichtevanadres = U.loctenopzichtevanadres,
  locoms = U.locoms,
  bladresregel1 = U.bladresregel1,
  bladresregel2 = U.bladresregel2,
  bladresregel3 = U.bladresregel3,
  bladresregel4 = U.bladresregel4,
  bladresregel5 = U.bladresregel5,
  bladresregel6 = U.bladresregel6,
  landgebied = U.landgebied
FROM "kern"."his_persadres" U
WHERE U.persadres = kern.persadres.id
      AND U.dateindegel IS NULL;

-- end-of-script-part (05)

COMMIT;
BEGIN;

-- <Stap-06>
--

--UPDATE "kern"."pers" SET bsn = ( Select U.bsn from "kern"."his_persids" U where U.pers = pers.id AND U.dateindegel IS NULL)
--                       , anr = ( Select U.anr from "kern"."his_persids" U where U.pers = pers.id AND U.dateindegel IS NULL)
--                       , idsstatushis = 'A'
--WHERE EXISTS ( Select 1 from "kern"."his_persids" U where U.pers = pers.id AND U.dateindegel IS NULL) ;

UPDATE "kern"."pers"
SET bsn = U.bsn
  , anr =  U.anr
FROM "kern"."his_persids" U
WHERE U.pers = pers.id
      AND U.dateindegel IS NULL;

-- end-of-script-part (06)

COMMIT;
BEGIN;

UPDATE "kern"."pers"
SET geslachtsaand = U.geslachtsaand
FROM "kern"."his_persgeslachtsaand" U
WHERE U.pers = pers.id
      AND U.dateindegel IS NULL;

COMMIT;
BEGIN;

UPDATE "kern"."pers"
SET predicaat = U.predicaat
  , voornamen = U.voornamen
  , voorvoegsel = U.voorvoegsel
  , scheidingsteken = U.scheidingsteken
  , adellijketitel = U.adellijketitel
  , geslnaamstam = U.geslnaamstam
  , indnreeks = U.indnreeks
  , indafgeleid = U.indafgeleid
FROM "kern"."his_perssamengesteldenaam" U
WHERE U.pers = pers.id
      AND U.dateindegel IS NULL;

COMMIT;
BEGIN;

--RvdP--
--RvdP-- Tussengevoegd: insert statement voor persaanschrijving.
INSERT INTO kern.his_persnaamgebruik
( id, pers, tsreg, tsverval, actieinh, actieverval, naamgebruik, indnaamgebruikafgeleid,
  predicaatnaamgebruik, voornamennaamgebruik, voorvoegselnaamgebruik, scheidingstekennaamgebruik, geslnaamstamnaamgebruik)
  SELECT
    id,  pers,  tsreg, CONCAT('', dateindegel)::TIMESTAMP,  actieinh,  actieverval,  1 as naamgebruik,
    indafgeleid,  predicaat,  voornamen,  voorvoegsel,  scheidingsteken,  geslnaamstam
  FROM  kern.his_perssamengesteldenaam
  WHERE kern.his_perssamengesteldenaam.pers IN (select pers.id from kern.pers where srt=1)
        AND kern.his_perssamengesteldenaam.dateindegel IS NOT NULL ORDER BY ID ;

INSERT INTO kern.his_persnaamgebruik
( id, pers, tsreg, actieinh, actieverval, naamgebruik, indnaamgebruikafgeleid,
  predicaatnaamgebruik, voornamennaamgebruik, voorvoegselnaamgebruik, scheidingstekennaamgebruik, geslnaamstamnaamgebruik)
  SELECT
    id,  pers, tsreg, actieinh, actieverval, 1 as naamgebruik,
    indafgeleid, predicaat, voornamen, voorvoegsel, scheidingsteken, geslnaamstam
  FROM  kern.his_perssamengesteldenaam
  WHERE kern.his_perssamengesteldenaam.pers IN (select pers.id from kern.pers where srt=1)
        AND kern.his_perssamengesteldenaam.dateindegel IS NULL ORDER BY ID ;

--RvdP-- Einde simpele kopieeractie van samengestelde naam naar aanschrijving. Moet alleen gebeuren voor personen van 'soort=1'

-- <Stap-09>   LET OP! Van [kern.his_persaanschr] is geen data aangeleveerd !!!
--RvdP-- De data voor persaanschrijving is boven toegevoegd
--
-- Update [kern.per] adhv [kern.his_persaanschr]
UPDATE "kern"."pers" SET naamgebruik = U.naamgebruik,
  indnaamgebruikafgeleid = U.indnaamgebruikafgeleid,
  predicaatnaamgebruik = U.predicaatnaamgebruik,
  voornamennaamgebruik = U.voornamennaamgebruik,
  voorvoegselnaamgebruik = U.voorvoegselnaamgebruik,
  scheidingstekennaamgebruik = U.scheidingstekennaamgebruik,
  geslnaamstamnaamgebruik = U.geslnaamstamnaamgebruik
FROM "kern"."his_persnaamgebruik" U
WHERE U.pers = pers.id
      AND U.tsverval IS NULL;


COMMIT;
BEGIN;

-- <Stap-10>
--
-- Update [kern.per] adhv [kern.his_persgeboorte]

UPDATE "kern"."pers"
SET datgeboorte = U.datgeboorte
  , gemgeboorte = U.gemgeboorte
  , wplnaamgeboorte = U.wplnaamgeboorte
  , blplaatsgeboorte = U.blplaatsgeboorte
  , blregiogeboorte = U.blregiogeboorte
  , landgebiedgeboorte = U.landgebiedgeboorte
  , omslocgeboorte = U.omslocgeboorte
FROM "kern"."his_persgeboorte" U
WHERE U.pers = pers.id;

COMMIT;

-- end-of-script-part (10)

COMMIT;
BEGIN;

-- <Stap-11>
--
-- Update [kern.per] adhv [kern.his_persoverlijden]
-- Update [kern.pers] - 01 - adhv [kern.his_persoverlijden]

UPDATE "kern"."pers"
SET datoverlijden = U.datoverlijden
  , gemoverlijden = U.gemoverlijden
  , wplnaamoverlijden = U.wplnaamoverlijden
  , blplaatsoverlijden = U.blplaatsoverlijden
  , blregiooverlijden = U.blregiooverlijden
  , landgebiedoverlijden = U.landgebiedoverlijden
  , omslocoverlijden = U.omslocoverlijden
FROM "kern"."his_persoverlijden" U
WHERE U.pers = pers.id;

-- end-of-script-part (11)

COMMIT;
BEGIN;

update kern.persindicatie set waarde = 't' where exists (
    select 1 from kern.his_persindicatie where persindicatie = kern.persindicatie.id and tsverval is null and waarde = 't'
);
-- <End-of-Script>

UPDATE "kern"."pers"
SET datoverlijden = U.datoverlijden
  , gemoverlijden = U.gemoverlijden
  , wplnaamoverlijden = U.wplnaamoverlijden
  , blplaatsoverlijden = U.blplaatsoverlijden
  , blregiooverlijden = U.blregiooverlijden
  , landgebiedoverlijden = U.landgebiedoverlijden
  , omslocoverlijden = U.omslocoverlijden
  , naderebijhaard = 4
FROM "kern"."his_persoverlijden" U
WHERE U.pers = pers.id;
COMMIT;

--BEGIN;
--RvdP-- Update van kern.relatie waar aanwezig kern.his_huwelijkgeregistreerdpar
--UPDATE "kern"."relatie" SET omslocaanv= (Select H.omslocaanv from kern.his_huwelijkgeregistreerdpar H where H.relatie=relatie.id) where exists (select 1 from kern.his_huwelijkgeregistreerdpar H where H.relatie=relatie.id);
--UPDATE "kern"."relatie" SET landaanv= (Select H.landaanv from kern.his_huwelijkgeregistreerdpar H where H.relatie=relatie.id) where exists (select 1 from kern.his_huwelijkgeregistreerdpar H where H.relatie=relatie.id);
--UPDATE "kern"."relatie" SET dataanv = (select dataanv from kern.his_huwelijkgeregistreerdpar H where H.relatie=relatie.id) where exists (select 1 from kern.his_huwelijkgeregistreerdpar H where H.relatie=relatie.id);

--UPDATE "kern"."relatie"
--SET omslocaanv = H.omslocaanv
--    , landgebiedaanv= H.landgebiedaanv
--    , dataanv = H.dataanv
--FROM kern.his_huwelijkgeregistreerdpar H
--WHERE H.relatie=relatie.id;

--COMMIT;

BEGIN;

UPDATE kern.pers P
SET naderebijhaard = PB.naderebijhaard
  , bijhpartij = PB.bijhpartij
  , indonverwdocaanw = PB.indonverwdocaanw
FROM kern.his_persbijhouding PB
WHERE P.id = PB.pers
      AND PB.actieaanpgel IS NULL;

COMMIT;

BEGIN;
--RvdP-- Nu overgaan naar de niet ingeschreven personen: eerst Adam van Modernodam, dan Eva van Modernodam
insert into kern.pers (id, srt, voornamen, voorvoegsel, scheidingsteken, geslnaamstam, indnreeks, indafgeleid, geslachtsaand, datgeboorte) select id+1000000, 2, 'Adam', 'van', ' ', 'Modernodam', false, true, 1, 19511111 from kern.pers where srt=1 and not exists (select 42 from kern.betr where rol=1 AND Pers = pers.id);
insert into kern.his_perssamengesteldenaam (id, pers, dataanvgel, tsreg, indafgeleid, indnreeks, voornamen, voorvoegsel, scheidingsteken, geslnaamstam, actieinh) select id+1000000, id+1000000, 19980101, now(), true, false, 'Adam', 'van', ' ', 'Modernodam', 1000136 from kern.pers where srt=1 and not exists (select 42 from kern.betr where rol=1 AND Pers = pers.id);
insert into kern.his_persgeboorte (id, pers, tsreg, datgeboorte, landgebiedgeboorte, actieinh) select id+1000000, id+1000000, now(), 19511111, 229, 1000136 from kern.pers where srt=1 and not exists (select 42 from kern.betr where rol=1 AND Pers = pers.id);
insert into kern.his_persgeslachtsaand (id, pers, dataanvgel, tsreg, geslachtsaand, actieinh) select id+1000000, id+1000000, 19511111 ,now(), 1, 1000136 from kern.pers where srt=1 and not exists (select 42 from kern.betr where rol=1 AND Pers = pers.id);

--RvdP: Voor iedereen die nog niet betrokken was als 'kind' in een relatie, komt er een 'adam' als vader...

COMMIT;

BEGIN;
insert into kern.pers (id, srt, voornamen, voorvoegsel, scheidingsteken, geslnaamstam, indnreeks, indafgeleid, geslachtsaand, datgeboorte) select id+2000000, 2, 'Eva', 'van', ' ', 'Modernodam', false, true, 2, 19511111 from kern.pers where srt=1 and not exists (select 42 from kern.betr where rol=1 AND Pers = pers.id);
insert into kern.his_perssamengesteldenaam (id, pers, dataanvgel, tsreg, indafgeleid, indnreeks, voornamen, voorvoegsel, scheidingsteken, geslnaamstam, actieinh) select id+2000000, id+2000000, 19980101, now(), true, false, 'Eva', 'van', ' ', 'Modernodam', 1000136 from kern.pers where srt=1 and not exists (select 42 from kern.betr where rol=1 AND Pers = pers.id);
insert into kern.his_persgeboorte (id, pers, tsreg, datgeboorte, landgebiedgeboorte, actieinh) select id+2000000, id+2000000, now(), 19511111, 229, 1000136 from kern.pers where srt=1 and not exists (select 42 from kern.betr where rol=1 AND Pers = pers.id);
insert into kern.his_persgeslachtsaand (id, pers, dataanvgel, tsreg, geslachtsaand, actieinh) select id+2000000, id+2000000, 19511111 ,now(), 2, 1000136 from kern.pers where srt=1 and not exists (select 42 from kern.betr where rol=1 AND Pers = pers.id);
COMMIT;

BEGIN;
--RvdP-- Geboorteactie van Adam en Eva zijn de actie waarmee betreffende persoon is geboren; hiermee is ook de relatie=famrechtbetr ontstaan:
insert into kern.Relatie(id, srt) select 33000000+id, 3 from kern.pers;
COMMIT;

BEGIN;
--RvdP-- eerst de betrokkenheden van Adam en Eva: bron 'personen soort 1 zonder ouders'
insert into kern.betr(id, relatie, rol, Pers, indouder)
  select 1000000+id, 33000000+id, 2, id+1000000, true from kern.pers where not exists (select 42 from kern.betr where rol=1 AND Pers=pers.id) and srt=1;
COMMIT;

BEGIN;
insert into kern.betr(id, relatie, rol, Pers, indouder)
  select 2000000+id, 33000000+id, 2, id+2000000, true from kern.pers where not exists (select 42 from kern.betr where rol=1 AND Pers=pers.id) and srt=1;
COMMIT;

BEGIN;
--RvdP-- nu de betrokkenheden voor iedereen die NOG GEEN OUDERS heeft (hierna heeft iedereen ouders...)
insert into kern.betr(id, relatie, rol, Pers)
  select 3000000+id, 33000000+id, 1, id from kern.pers where not exists (select 42 from kern.betr where rol=1 AND Pers=pers.id) and srt=1;
COMMIT;

BEGIN;
-- Betrokkenheid - Ouderschap historie
insert into kern.his_ouderouderschap (id, betr, dataanvgel, tsreg, actieinh, indouder)
  select betr_ouder.id, betr_ouder.id, kind_geb.datgeboorte, kind_geb.tsreg, kind_geb.actieinh, true
  from
    kern.betr betr_ouder
    join kern.betr betr_kind on betr_ouder.relatie = betr_kind.relatie
    join kern.his_persgeboorte kind_geb on kind_geb.pers = betr_kind.pers
  where
    -- Ouder
    betr_ouder.rol = 2
    -- Kind
    and betr_kind.rol = 1
    -- Nog geen ouderschap
    and not exists (select 42 from kern.his_ouderouderschap e where e.betr = betr_ouder.id
    );


--RvdP-- Vergeten was: Nationaliteit.
--RvdP-- Gelukkig is dit een simpele: IEDEREEN (van soort=1) is nederlander, per datum geboorte.
--RvdP-- Dus: kopieer kern.pers naar kern.persnation voor soort=1; kopieer his_persgeboorte naar kern.his_persnation
--RvdP-- Reden verkrijging is: reden nr 18 (code: 17).
insert into kern.persnation (id, pers, nation, rdnverk)
  select id, id, 2, 18 from kern.pers where srt=1;
COMMIT;

BEGIN;

insert into kern.his_persnation (id, persnation, dataanvgel, tsreg, actieinh, rdnverk)
  select A.id, A.id, B.datgeboorte, B.tsreg, B.actieinh, 18
  from kern.persnation A, kern.his_persgeboorte B
  where B.pers=A.id
        and exists (select 42 from kern.pers where srt=1 AND pers.id=A.pers);

COMMIT;

BEGIN;
-- RvdP-- Niet gevuld: groep inschrijving...
-- RvdP -- Alsnog toegevoegd, en A-laag van persoon bijgewerkt:
insert into kern.his_persinschr (id, pers, tsreg, actieinh, datinschr, versienr, dattijdstempel)
  select id, pers, tsreg, actieinh, datgeboorte, 1, tsreg from kern.his_persgeboorte HG where
    HG.pers in (select id from kern.pers where srt = 1);

update kern.pers set datinschr = datgeboorte where srt=1;
update kern.pers set versienr = 1 where srt=1;
-- RvdP -- Updaten van status his is niet nodig: deze blijkt al te kloppen (c.q. voor iedereen van 'soort 1' gelijk aan A te zijn en rest gelijk X
COMMIT;

BEGIN;
-- anjaw: vul pers met dattijdstempel (tslaatstewijz is gelijk aan geboortedatum tijdstip)
update kern.pers set dattijdstempel = tslaatstewijz;
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

--HIER VERDER

begin;
-- De glazen bol records zijn nu vervallen.
-- Stap 2: cre�er records met inhoud van de records die vervallen zijn (tsverval <> null),
-- met actieinh hetzelfde, en actieverval wordt actieaanpassingeldigheid
insert into kern.his_persadres
(persadres ,  dataanvgel ,  dateindegel ,  tsreg ,  tsverval ,  actieinh ,  actieverval ,  actieaanpgel ,  srt ,
 rdnwijz ,  aangadresh ,  dataanvadresh ,  identcodeadresseerbaarobject ,  identcodenraand ,  gem ,  nor ,  afgekortenor ,
 gemdeel ,  huisnr ,  huisletter ,  huisnrtoevoeging ,  postcode ,  wplnaam ,  loctenopzichtevanadres ,  locoms ,  bladresregel1 ,
 bladresregel2 ,  bladresregel3 ,  bladresregel4 ,  bladresregel5 ,  bladresregel6 ,  landgebied ,  indpersaangetroffenopadres)
  select
    persadres ,  dataanvgel ,  dateindegel ,  tsverval ,  null ,  actieinh ,  null ,  actieverval ,  srt ,
    rdnwijz ,  aangadresh ,  dataanvadresh ,  identcodeadresseerbaarobject ,  identcodenraand ,  gem ,  nor ,  afgekortenor ,
    gemdeel ,  huisnr ,  huisletter ,  huisnrtoevoeging ,  postcode ,  wplnaam ,  loctenopzichtevanadres ,  locoms ,  bladresregel1 ,
    bladresregel2 ,  bladresregel3 ,  bladresregel4 ,  bladresregel5 ,  bladresregel6 ,  landgebied ,  indpersaangetroffenopadres

  from kern.his_persadres where not tsverval is null;

update kern.his_persadres
set dateindegel=null where tsverval is not null;

-- De glazenbol records HADDEN al een datum aanpassing geldigheid. Die wordt leeggemaakt:
update kern.his_persadres set actieaanpgel = null where actieaanpgel = actieverval;
commit;


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

begin;
update kern.pers P
set voornamen = voornamen || (select '-' || kind from tempA A where A.id = P.id)
where P.srt <> 1;
-- C laag moet ook bijgewerkt worden:
update kern.his_perssamengesteldenaam hisSam
set voornamen = voornamen || (select '-' || kind from tempA A where A.id = hisSam.id)
where hissam.pers in (select id from tempA);
commit;

begin;
drop table tempA;
commit;
--RvdP-- Einde van hernoemactie


begin;
--RvdP-- In de whiteboxfiller programmatuur (dd 13 september 2012) werd het ingevulde scheidingsteken niet correct
--RvdP-- overgenomen uit het excelsheet. Daarom hier hersteld.
--RvdP-- Te herstellen in:
--RvdP 1) persoonstabel, op twee plaatsen;
--RvdP 2) in his-aanshcrijving;
--RvdP 3) in his-perssamengesteldenaam;
--RvdP 4) in persgeslachtsnaamcomponent;
--RvdP 5) in his_persgeslachtsnaamcomponent.
UPDATE kern.pers SET scheidingsteken = ' ', scheidingstekennaamgebruik = ' ' WHERE voorvoegsel IS NOT NULL;
UPDATE kern.his_persnaamgebruik SET scheidingstekennaamgebruik = ' ' where voorvoegselnaamgebruik is not null;
update kern.his_perssamengesteldenaam SET scheidingsteken = ' ' where voorvoegsel is not null;
update kern.persgeslnaamcomp SET scheidingsteken = ' ' where voorvoegsel is not null;
update kern.his_persgeslnaamcomp SET scheidingsteken = ' ' where voorvoegsel is not null;
commit;

BEGIN;

-- Bolie vrijdag 4 januari 2013, toegevoegd om de default op false te zetten. de xsd verlangt dat dit gevuld diet te worden.
UPDATE kern.his_persbijhouding set IndOnverwDocAanw = false where IndOnverwDocAanw is null;
UPDATE kern.pers set IndOnverwDocAanw = false where IndOnverwDocAanw is null;

UPDATE kern.pers set naamgebruik = 1 where naamgebruik is null;
UPDATE kern.his_persnaamgebruik set naamgebruik = 1 where naamgebruik is null;

UPDATE kern.pers set indafgeleid = true where indafgeleid is null;
UPDATE kern.his_perssamengesteldenaam set indafgeleid = true where indafgeleid is null ;

UPDATE kern.pers set datgeboorte='19500101' where datgeboorte is null;
UPDATE kern.pers set landgebiedgeboorte=229 where landgebiedgeboorte is null;

-- Toevoeging voor leveringen - mutaties. Zorgt ervoor dat de bezemwagen (controle-scheduler) niet direct alle administratieve handelingen probeert weg te werken.
UPDATE Kern.Admhnd SET tsreg = '2010-03-18 00:00:00';
UPDATE Kern.Admhnd SET tslev = tsreg;

COMMIT;


BEGIN;

SELECT SETVAL('kern.seq_actie', (select 1 + coalesce(max(id),0) FROM kern.actie), false);
SELECT SETVAL('kern.seq_admhnd', (select 1 + coalesce(max(id),0) FROM kern.admhnd), false);
SELECT SETVAL('kern.seq_his_persafgeleidadministrati', (select 1 + coalesce(max(id),0) FROM kern.his_persafgeleidadministrati), false);

COMMIT;


-- His pers afgeleidadm vullen, voor ieder een
BEGIN;

CREATE OR REPLACE FUNCTION update_handelingen() RETURNS integer AS $$
DECLARE
    persoon RECORD;
    admhnd_inhoud BIGINT;
    admhnd_verval BIGINT;
    actie_inhoud BIGINT;
    actie_verval BIGINT;
BEGIN
    FOR persoon IN SELECT * FROM kern.pers WHERE srt = 1 ORDER BY id LOOP
        --Ah en actie voor actieinhoud
        EXECUTE 'SELECT nextval(' || quote_literal('kern.seq_admhnd') || ')' INTO admhnd_inhoud;
        EXECUTE 'SELECT nextval(' || quote_literal('kern.seq_actie') || ')' INTO actie_inhoud;
        EXECUTE 'INSERT INTO kern.admhnd (id, srt, partij, tsreg, tslev) values ($1, 38, 1, now(), now())' USING admhnd_inhoud;
        EXECUTE 'INSERT INTO kern.actie(id, srt, admhnd, partij, tsreg, datontlening) values ($1, 7, $2, 2000, now(), null)' USING actie_inhoud, admhnd_inhoud;

        --Ah en actie voor actieverval
        EXECUTE 'SELECT nextval(' || quote_literal('kern.seq_admhnd') || ')' INTO admhnd_verval;
        EXECUTE 'SELECT nextval(' || quote_literal('kern.seq_actie') || ')' INTO actie_verval;
        EXECUTE 'INSERT INTO kern.admhnd (id, srt, partij, tsreg, tslev) values ($1, 38, 2000, now(), now())' USING admhnd_verval;
        EXECUTE 'INSERT INTO kern.actie (id, srt, admhnd, partij, tsreg, datontlening) values ($1, 7, $2, 2000, now(), null)' USING actie_verval, admhnd_verval;

        --Oude afgeleid administratief laten vervallen
        EXECUTE 'update kern.his_persafgeleidadministrati set tsverval = now(), actieverval = $1 where actieverval is null and pers = $2' USING actie_verval, persoon.id;

        --Nieuwe afgeleid administratief inserten
        EXECUTE 'INSERT INTO kern.his_persafgeleidadministrati (pers, tsreg, actieinh, admhnd, tslaatstewijz, sorteervolgorde, indonverwbijhvoorstelnieting) values ($1, now(), $2, $3, now(), 1, false)' USING persoon.id, actie_inhoud, admhnd_inhoud;

        --Updaten van de A-laag
        EXECUTE 'UPDATE kern.pers SET admhnd = $2, tslaatstewijz = now(), sorteervolgorde = 1 WHERE id = $1' USING persoon.id, admhnd_inhoud;
    END LOOP;

    RETURN 1;
END;
$$ LANGUAGE plpgsql;

SELECT * FROM update_handelingen();

COMMIT;

-- Afgeleid administratief historie
-- Voor nu per ingezeten persoon één... alleen van geboorte
BEGIN;

INSERT INTO kern.his_persafgeleidadministrati (pers, tsreg, actieinh, admhnd, tslaatstewijz, sorteervolgorde, indonverwbijhvoorstelnieting, tsverval, actieverval)
  SELECT hp.pers, hp.tsreg, hp.actieinh, a.admhnd, hp.tsreg, 1, false, aa.tsreg, aa.actieinh
  FROM kern.his_persgeboorte hp
    JOIN kern.actie a ON (a.id = hp.actieinh)
    JOIN kern.his_persafgeleidadministrati aa ON (aa.pers = hp.pers)
    JOIN kern.pers p ON (p.id = hp.pers)
  WHERE p.srt = 1;

COMMIT;

-- Vullen van historie relaties, voor de fam betr van adam en evas en hun kinderen
BEGIN;

INSERT INTO kern.his_relatie (id, relatie, tsreg, actieinh)
  SELECT rr.id, rr.id, '2005-01-12 01:00:00+01', 1 FROM kern.relatie rr
  WHERE NOT EXISTS (
      SELECT r.id FROM kern.relatie r
        JOIN kern.his_relatie hr ON (r.id = hr.relatie)
      WHERE r.id = rr.id);

COMMIT;

-- Vullen van historie betrokkenheid, voor elke betrokkenheid 1 historie record
BEGIN;

INSERT INTO kern.his_betr (id, betr, tsreg, actieinh)
  SELECT b.id, b.id, hr.tsreg, hr.actieinh
  FROM kern.betr b
    JOIN kern.relatie r ON (b.relatie = r.id)
    JOIN kern.his_relatie hr ON (hr.relatie = r.id)
  ORDER BY b.id;

COMMIT;
