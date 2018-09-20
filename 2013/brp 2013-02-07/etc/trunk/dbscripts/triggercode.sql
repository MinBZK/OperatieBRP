-- ------------------------------------------------------------------------
-- Begin van triggercode---------------------------------------------------
-- ------------------------------------------------------------------------
-- Gegenereerd door BRP Meta Register.
-- Gegenereerd op: Monday 16 Jan 2012 08:46:18
-- ------------------------------------------------------------------------
-- ------------------------------------------------------------------------
-- ------------------------------------------------------------------------
-- ------------------------------------------------------------------------
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_AutAut_His_Authenticatiemiddel() returns TRIGGER AS $formele_historie_AutAut_His_Authenticatiemiddel$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.Functie                      is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.Functie                      Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.ActieInh                     = Kern.BRPActie.ID                          ;
IF NEW.Functie                      is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM AutAut.His_Authenticatiemiddel
WHERE NEW.Authenticatiemiddel          = AutAut.His_Authenticatiemiddel.Authenticatiemiddel          and NEW.ID                           <> AutAut.His_Authenticatiemiddel.ID                          
and NEW.TsReg                        > AutAut.His_Authenticatiemiddel.TsReg                        and NEW.TsReg                        < AutAut.His_Authenticatiemiddel.TsVerval                    
AND AutAut.His_Authenticatiemiddel.CertificaatTbvSSL            IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM AutAut.His_Authenticatiemiddel WHERE NEW.Authenticatiemiddel          = AutAut.His_Authenticatiemiddel.Authenticatiemiddel          and NEW.ID                           <> AutAut.His_Authenticatiemiddel.ID                          
and NEW.TsVerval                     > AutAut.His_Authenticatiemiddel.TsReg                        and NEW.TsVerval                     < AutAut.His_Authenticatiemiddel.TsVerval                     AND AutAut.His_Authenticatiemiddel.CertificaatTbvSSL            IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE AutAut.His_Authenticatiemiddel SET CertificaatTbvSSL            = NEW.Functie                     , ActieVerval                  = NEW.ActieInh                    

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Authenticatiemiddel          = AutAut.His_Authenticatiemiddel.Authenticatiemiddel          and NEW.ID                           <> AutAut.His_Authenticatiemiddel.ID                          
and AutAut.His_Authenticatiemiddel.TsReg                        < NEW.TsVerval                     and AutAut.His_Authenticatiemiddel.TsVerval                     > NEW.TsReg                       
AND AutAut.His_Authenticatiemiddel.CertificaatTbvSSL            is null AND AutAut.His_Authenticatiemiddel.ActieVerval                  is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO AutAut.His_Authenticatiemiddel (Authenticatiemiddel         , CertificaatTbvOndertekening , IPAdres                     , TsReg                        , TsVerval                    , ActieInh                    , Functie                     , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT AutAut.His_Authenticatiemiddel.Authenticatiemiddel         , AutAut.His_Authenticatiemiddel.CertificaatTbvOndertekening , AutAut.His_Authenticatiemiddel.IPAdres                     , AutAut.His_Authenticatiemiddel.TsReg                       , NEW.TsReg                       , AutAut.His_Authenticatiemiddel.ActieInh                    , NEW.Functie                     , NEW.ActieInh                     FROM AutAut.His_Authenticatiemiddel
WHERE AutAut.His_Authenticatiemiddel.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO AutAut.His_Authenticatiemiddel (Authenticatiemiddel         , CertificaatTbvOndertekening , IPAdres                     , TsReg                        , TsVerval                    , ActieInh                    , Functie                     , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT AutAut.His_Authenticatiemiddel.Authenticatiemiddel         , AutAut.His_Authenticatiemiddel.CertificaatTbvOndertekening , AutAut.His_Authenticatiemiddel.IPAdres                     , NEW.TsVerval                    , AutAut.His_Authenticatiemiddel.TsVerval                    , AutAut.His_Authenticatiemiddel.ActieInh                    , NEW.Functie                     , NEW.ActieInh                     FROM AutAut.His_Authenticatiemiddel
WHERE AutAut.His_Authenticatiemiddel.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_AutAut_His_Authenticatiemiddel$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on AutAut.His_Authenticatiemiddel RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on AutAut.His_Authenticatiemiddel

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_AutAut_His_Authenticatiemiddel();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_AutAut_His_Autorisatiebesluit() returns TRIGGER AS $formele_historie_AutAut_His_Autorisatiebesluit$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.Functie                      is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.Functie                      Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.ActieInh                     = Kern.BRPActie.ID                          ;
IF NEW.Functie                      is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM AutAut.His_Autorisatiebesluit
WHERE NEW.Authenticatiemiddel          = AutAut.His_Autorisatiebesluit.Authenticatiemiddel          and NEW.ID                           <> AutAut.His_Autorisatiebesluit.ID                          
and NEW.TsReg                        > AutAut.His_Autorisatiebesluit.TsReg                        and NEW.TsReg                        < AutAut.His_Autorisatiebesluit.TsVerval                    
AND AutAut.His_Autorisatiebesluit.CertificaatTbvSSL            IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM AutAut.His_Autorisatiebesluit WHERE NEW.Authenticatiemiddel          = AutAut.His_Autorisatiebesluit.Authenticatiemiddel          and NEW.ID                           <> AutAut.His_Autorisatiebesluit.ID                          
and NEW.TsVerval                     > AutAut.His_Autorisatiebesluit.TsReg                        and NEW.TsVerval                     < AutAut.His_Autorisatiebesluit.TsVerval                     AND AutAut.His_Autorisatiebesluit.CertificaatTbvSSL            IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE AutAut.His_Autorisatiebesluit SET CertificaatTbvSSL            = NEW.Functie                     , ActieVerval                  = NEW.ActieInh                    

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Authenticatiemiddel          = AutAut.His_Autorisatiebesluit.Authenticatiemiddel          and NEW.ID                           <> AutAut.His_Autorisatiebesluit.ID                          
and AutAut.His_Autorisatiebesluit.TsReg                        < NEW.TsVerval                     and AutAut.His_Autorisatiebesluit.TsVerval                     > NEW.TsReg                       
AND AutAut.His_Autorisatiebesluit.CertificaatTbvSSL            is null AND AutAut.His_Autorisatiebesluit.ActieVerval                  is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO AutAut.His_Autorisatiebesluit (Authenticatiemiddel         , CertificaatTbvOndertekening , IPAdres                     , TsReg                        , TsVerval                    , ActieInh                    , Functie                     , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT AutAut.His_Autorisatiebesluit.Authenticatiemiddel         , AutAut.His_Authenticatiemiddel.CertificaatTbvOndertekening , AutAut.His_Authenticatiemiddel.IPAdres                     , AutAut.His_Autorisatiebesluit.TsReg                       , NEW.TsReg                       , AutAut.His_Autorisatiebesluit.ActieInh                    , NEW.Functie                     , NEW.ActieInh                     FROM AutAut.His_Autorisatiebesluit
WHERE AutAut.His_Autorisatiebesluit.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO AutAut.His_Autorisatiebesluit (Authenticatiemiddel         , CertificaatTbvOndertekening , IPAdres                     , TsReg                        , TsVerval                    , ActieInh                    , Functie                     , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT AutAut.His_Autorisatiebesluit.Authenticatiemiddel         , AutAut.His_Authenticatiemiddel.CertificaatTbvOndertekening , AutAut.His_Authenticatiemiddel.IPAdres                     , NEW.TsVerval                    , AutAut.His_Autorisatiebesluit.TsVerval                    , AutAut.His_Autorisatiebesluit.ActieInh                    , NEW.Functie                     , NEW.ActieInh                     FROM AutAut.His_Autorisatiebesluit
WHERE AutAut.His_Autorisatiebesluit.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_AutAut_His_Autorisatiebesluit$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on AutAut.His_Autorisatiebesluit RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on AutAut.His_Autorisatiebesluit

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_AutAut_His_Autorisatiebesluit();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_AutAut_His_AutorisatiebesluitBijhau() returns TRIGGER AS $formele_historie_AutAut_His_AutorisatiebesluitBijhau$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.Functie                      is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.Functie                      Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.ActieInh                     = Kern.BRPActie.ID                          ;
IF NEW.Functie                      is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM AutAut.His_AutorisatiebesluitBijhau
WHERE NEW.Authenticatiemiddel          = AutAut.His_AutorisatiebesluitBijhau.Authenticatiemiddel          and NEW.ID                           <> AutAut.His_AutorisatiebesluitBijhau.ID                          
and NEW.TsReg                        > AutAut.His_AutorisatiebesluitBijhau.TsReg                        and NEW.TsReg                        < AutAut.His_AutorisatiebesluitBijhau.TsVerval                    
AND AutAut.His_AutorisatiebesluitBijhau.CertificaatTbvSSL            IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM AutAut.His_AutorisatiebesluitBijhau WHERE NEW.Authenticatiemiddel          = AutAut.His_AutorisatiebesluitBijhau.Authenticatiemiddel          and NEW.ID                           <> AutAut.His_AutorisatiebesluitBijhau.ID                          
and NEW.TsVerval                     > AutAut.His_AutorisatiebesluitBijhau.TsReg                        and NEW.TsVerval                     < AutAut.His_AutorisatiebesluitBijhau.TsVerval                     AND AutAut.His_AutorisatiebesluitBijhau.CertificaatTbvSSL            IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE AutAut.His_AutorisatiebesluitBijhau SET CertificaatTbvSSL            = NEW.Functie                     , ActieVerval                  = NEW.ActieInh                    

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Authenticatiemiddel          = AutAut.His_AutorisatiebesluitBijhau.Authenticatiemiddel          and NEW.ID                           <> AutAut.His_AutorisatiebesluitBijhau.ID                          
and AutAut.His_AutorisatiebesluitBijhau.TsReg                        < NEW.TsVerval                     and AutAut.His_AutorisatiebesluitBijhau.TsVerval                     > NEW.TsReg                       
AND AutAut.His_AutorisatiebesluitBijhau.CertificaatTbvSSL            is null AND AutAut.His_AutorisatiebesluitBijhau.ActieVerval                  is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO AutAut.His_AutorisatiebesluitBijhau (Authenticatiemiddel         , CertificaatTbvOndertekening , IPAdres                     , TsReg                        , TsVerval                    , ActieInh                    , Functie                     , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT AutAut.His_AutorisatiebesluitBijhau.Authenticatiemiddel         , AutAut.His_Authenticatiemiddel.CertificaatTbvOndertekening , AutAut.His_Authenticatiemiddel.IPAdres                     , AutAut.His_AutorisatiebesluitBijhau.TsReg                       , NEW.TsReg                       , AutAut.His_AutorisatiebesluitBijhau.ActieInh                    , NEW.Functie                     , NEW.ActieInh                     FROM AutAut.His_AutorisatiebesluitBijhau
WHERE AutAut.His_AutorisatiebesluitBijhau.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO AutAut.His_AutorisatiebesluitBijhau (Authenticatiemiddel         , CertificaatTbvOndertekening , IPAdres                     , TsReg                        , TsVerval                    , ActieInh                    , Functie                     , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT AutAut.His_AutorisatiebesluitBijhau.Authenticatiemiddel         , AutAut.His_Authenticatiemiddel.CertificaatTbvOndertekening , AutAut.His_Authenticatiemiddel.IPAdres                     , NEW.TsVerval                    , AutAut.His_AutorisatiebesluitBijhau.TsVerval                    , AutAut.His_AutorisatiebesluitBijhau.ActieInh                    , NEW.Functie                     , NEW.ActieInh                     FROM AutAut.His_AutorisatiebesluitBijhau
WHERE AutAut.His_AutorisatiebesluitBijhau.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_AutAut_His_AutorisatiebesluitBijhau$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on AutAut.His_AutorisatiebesluitBijhau RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on AutAut.His_AutorisatiebesluitBijhau

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_AutAut_His_AutorisatiebesluitBijhau();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_AutAut_His_Bijhautorisatie() returns TRIGGER AS $formele_historie_AutAut_His_Bijhautorisatie$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.Verantwoordelijke            is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.Verantwoordelijke            Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.ActieInh                     = Kern.BRPActie.ID                          ;
IF NEW.Verantwoordelijke            is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM AutAut.His_Bijhautorisatie
WHERE NEW.Bijhautorisatie              = AutAut.His_Bijhautorisatie.Bijhautorisatie              and NEW.ID                           <> AutAut.His_Bijhautorisatie.ID                          
and NEW.TsReg                        > AutAut.His_Bijhautorisatie.TsReg                        and NEW.TsReg                        < AutAut.His_Bijhautorisatie.TsVerval                    
AND AutAut.His_Bijhautorisatie.SrtBijhouding                IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM AutAut.His_Bijhautorisatie WHERE NEW.Bijhautorisatie              = AutAut.His_Bijhautorisatie.Bijhautorisatie              and NEW.ID                           <> AutAut.His_Bijhautorisatie.ID                          
and NEW.TsVerval                     > AutAut.His_Bijhautorisatie.TsReg                        and NEW.TsVerval                     < AutAut.His_Bijhautorisatie.TsVerval                     AND AutAut.His_Bijhautorisatie.SrtBijhouding                IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE AutAut.His_Bijhautorisatie SET SrtBijhouding                = NEW.Verantwoordelijke           , ActieVerval                  = NEW.ActieInh                    

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Bijhautorisatie              = AutAut.His_Bijhautorisatie.Bijhautorisatie              and NEW.ID                           <> AutAut.His_Bijhautorisatie.ID                          
and AutAut.His_Bijhautorisatie.TsReg                        < NEW.TsVerval                     and AutAut.His_Bijhautorisatie.TsVerval                     > NEW.TsReg                       
AND AutAut.His_Bijhautorisatie.SrtBijhouding                is null AND AutAut.His_Bijhautorisatie.ActieVerval                  is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO AutAut.His_Bijhautorisatie (Bijhautorisatie             , GeautoriseerdeSrtPartij     , GeautoriseerdePartij        , Toestand                    , Oms                         , BeperkingPopulatie          , TsReg                        , TsVerval                    , ActieInh                    , Verantwoordelijke           , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT AutAut.His_Bijhautorisatie.Bijhautorisatie             , AutAut.His_Bijhautorisatie.GeautoriseerdeSrtPartij     , AutAut.His_Bijhautorisatie.GeautoriseerdePartij        , AutAut.His_Bijhautorisatie.Toestand                    , AutAut.His_Bijhautorisatie.Oms                         , AutAut.His_Bijhautorisatie.BeperkingPopulatie          , AutAut.His_Bijhautorisatie.TsReg                       , NEW.TsReg                       , AutAut.His_Bijhautorisatie.ActieInh                    , NEW.Verantwoordelijke           , NEW.ActieInh                     FROM AutAut.His_Bijhautorisatie
WHERE AutAut.His_Bijhautorisatie.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO AutAut.His_Bijhautorisatie (Bijhautorisatie             , GeautoriseerdeSrtPartij     , GeautoriseerdePartij        , Toestand                    , Oms                         , BeperkingPopulatie          , TsReg                        , TsVerval                    , ActieInh                    , Verantwoordelijke           , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT AutAut.His_Bijhautorisatie.Bijhautorisatie             , AutAut.His_Bijhautorisatie.GeautoriseerdeSrtPartij     , AutAut.His_Bijhautorisatie.GeautoriseerdePartij        , AutAut.His_Bijhautorisatie.Toestand                    , AutAut.His_Bijhautorisatie.Oms                         , AutAut.His_Bijhautorisatie.BeperkingPopulatie          , NEW.TsVerval                    , AutAut.His_Bijhautorisatie.TsVerval                    , AutAut.His_Bijhautorisatie.ActieInh                    , NEW.Verantwoordelijke           , NEW.ActieInh                     FROM AutAut.His_Bijhautorisatie
WHERE AutAut.His_Bijhautorisatie.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_AutAut_His_Bijhautorisatie$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on AutAut.His_Bijhautorisatie RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on AutAut.His_Bijhautorisatie

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_AutAut_His_Bijhautorisatie();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_AutAut_His_Bijhsituatie() returns TRIGGER AS $formele_historie_AutAut_His_Bijhsituatie$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.Verantwoordelijke            is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.Verantwoordelijke            Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.ActieInh                     = Kern.BRPActie.ID                          ;
IF NEW.Verantwoordelijke            is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM AutAut.His_Bijhsituatie
WHERE NEW.Bijhautorisatie              = AutAut.His_Bijhsituatie.Bijhautorisatie              and NEW.ID                           <> AutAut.His_Bijhsituatie.ID                          
and NEW.TsReg                        > AutAut.His_Bijhsituatie.TsReg                        and NEW.TsReg                        < AutAut.His_Bijhsituatie.TsVerval                    
AND AutAut.His_Bijhsituatie.SrtBijhouding                IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM AutAut.His_Bijhsituatie WHERE NEW.Bijhautorisatie              = AutAut.His_Bijhsituatie.Bijhautorisatie              and NEW.ID                           <> AutAut.His_Bijhsituatie.ID                          
and NEW.TsVerval                     > AutAut.His_Bijhsituatie.TsReg                        and NEW.TsVerval                     < AutAut.His_Bijhsituatie.TsVerval                     AND AutAut.His_Bijhsituatie.SrtBijhouding                IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE AutAut.His_Bijhsituatie SET SrtBijhouding                = NEW.Verantwoordelijke           , ActieVerval                  = NEW.ActieInh                    

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Bijhautorisatie              = AutAut.His_Bijhsituatie.Bijhautorisatie              and NEW.ID                           <> AutAut.His_Bijhsituatie.ID                          
and AutAut.His_Bijhsituatie.TsReg                        < NEW.TsVerval                     and AutAut.His_Bijhsituatie.TsVerval                     > NEW.TsReg                       
AND AutAut.His_Bijhsituatie.SrtBijhouding                is null AND AutAut.His_Bijhsituatie.ActieVerval                  is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO AutAut.His_Bijhsituatie (Bijhautorisatie             , GeautoriseerdeSrtPartij     , GeautoriseerdePartij        , Toestand                    , Oms                         , BeperkingPopulatie          , TsReg                        , TsVerval                    , ActieInh                    , Verantwoordelijke           , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT AutAut.His_Bijhsituatie.Bijhautorisatie             , AutAut.His_Bijhautorisatie.GeautoriseerdeSrtPartij     , AutAut.His_Bijhautorisatie.GeautoriseerdePartij        , AutAut.His_Bijhautorisatie.Toestand                    , AutAut.His_Bijhautorisatie.Oms                         , AutAut.His_Bijhautorisatie.BeperkingPopulatie          , AutAut.His_Bijhsituatie.TsReg                       , NEW.TsReg                       , AutAut.His_Bijhsituatie.ActieInh                    , NEW.Verantwoordelijke           , NEW.ActieInh                     FROM AutAut.His_Bijhsituatie
WHERE AutAut.His_Bijhsituatie.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO AutAut.His_Bijhsituatie (Bijhautorisatie             , GeautoriseerdeSrtPartij     , GeautoriseerdePartij        , Toestand                    , Oms                         , BeperkingPopulatie          , TsReg                        , TsVerval                    , ActieInh                    , Verantwoordelijke           , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT AutAut.His_Bijhsituatie.Bijhautorisatie             , AutAut.His_Bijhautorisatie.GeautoriseerdeSrtPartij     , AutAut.His_Bijhautorisatie.GeautoriseerdePartij        , AutAut.His_Bijhautorisatie.Toestand                    , AutAut.His_Bijhautorisatie.Oms                         , AutAut.His_Bijhautorisatie.BeperkingPopulatie          , NEW.TsVerval                    , AutAut.His_Bijhsituatie.TsVerval                    , AutAut.His_Bijhsituatie.ActieInh                    , NEW.Verantwoordelijke           , NEW.ActieInh                     FROM AutAut.His_Bijhsituatie
WHERE AutAut.His_Bijhsituatie.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_AutAut_His_Bijhsituatie$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on AutAut.His_Bijhsituatie RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on AutAut.His_Bijhsituatie

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_AutAut_His_Bijhsituatie();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_AutAut_His_Doelbinding() returns TRIGGER AS $formele_historie_AutAut_His_Doelbinding$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.ActieInh                     is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.ActieInh                     Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.TsReg                        = Kern.BRPActie.ID                          ;
IF NEW.ActieInh                     is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM AutAut.His_Doelbinding
WHERE NEW.Doelbinding                  = AutAut.His_Doelbinding.Doelbinding                  and NEW.ID                           <> AutAut.His_Doelbinding.ID                          
and NEW.DatAanvGel                   > AutAut.His_Doelbinding.DatAanvGel                   and NEW.DatAanvGel                   < AutAut.His_Doelbinding.DatEindeGel                 
AND AutAut.His_Doelbinding.ActieVerval                  IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM AutAut.His_Doelbinding WHERE NEW.Doelbinding                  = AutAut.His_Doelbinding.Doelbinding                  and NEW.ID                           <> AutAut.His_Doelbinding.ID                          
and NEW.DatEindeGel                  > AutAut.His_Doelbinding.DatAanvGel                   and NEW.DatEindeGel                  < AutAut.His_Doelbinding.DatEindeGel                  AND AutAut.His_Doelbinding.ActieVerval                  IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE AutAut.His_Doelbinding SET ActieVerval                  = NEW.ActieInh                    , TsVerval                     = NEW.TsReg                       

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Doelbinding                  = AutAut.His_Doelbinding.Doelbinding                  and NEW.ID                           <> AutAut.His_Doelbinding.ID                          
and AutAut.His_Doelbinding.DatAanvGel                   < NEW.DatEindeGel                  and AutAut.His_Doelbinding.DatEindeGel                  > NEW.DatAanvGel                  
AND AutAut.His_Doelbinding.ActieVerval                  is null AND AutAut.His_Doelbinding.TsVerval                     is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO AutAut.His_Doelbinding (Doelbinding                 , ActieAanpGel                , Protocolleringsniveau       , TekstDoelbinding            , Populatiecriterium          , IndVerstrbeperkingHonoreren , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT AutAut.His_Doelbinding.Doelbinding                 , AutAut.His_Doelbinding.ActieAanpGel                , AutAut.His_Doelbinding.Protocolleringsniveau       , AutAut.His_Doelbinding.TekstDoelbinding            , AutAut.His_Doelbinding.Populatiecriterium          , AutAut.His_Doelbinding.IndVerstrbeperkingHonoreren , AutAut.His_Doelbinding.DatAanvGel                  , NEW.DatAanvGel                  , AutAut.His_Doelbinding.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM AutAut.His_Doelbinding
WHERE AutAut.His_Doelbinding.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO AutAut.His_Doelbinding (Doelbinding                 , ActieAanpGel                , Protocolleringsniveau       , TekstDoelbinding            , Populatiecriterium          , IndVerstrbeperkingHonoreren , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT AutAut.His_Doelbinding.Doelbinding                 , AutAut.His_Doelbinding.ActieAanpGel                , AutAut.His_Doelbinding.Protocolleringsniveau       , AutAut.His_Doelbinding.TekstDoelbinding            , AutAut.His_Doelbinding.Populatiecriterium          , AutAut.His_Doelbinding.IndVerstrbeperkingHonoreren , NEW.DatEindeGel                 , AutAut.His_Doelbinding.DatEindeGel                 , AutAut.His_Doelbinding.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM AutAut.His_Doelbinding
WHERE AutAut.His_Doelbinding.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_AutAut_His_Doelbinding$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on AutAut.His_Doelbinding RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on AutAut.His_Doelbinding

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_AutAut_His_Doelbinding();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_AutAut_His_Uitgeslotene() returns TRIGGER AS $formele_historie_AutAut_His_Uitgeslotene$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.ActieInh                     is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.ActieInh                     Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.TsReg                        = Kern.BRPActie.ID                          ;
IF NEW.ActieInh                     is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM AutAut.His_Uitgeslotene
WHERE NEW.Doelbinding                  = AutAut.His_Uitgeslotene.Doelbinding                  and NEW.ID                           <> AutAut.His_Uitgeslotene.ID                          
and NEW.DatAanvGel                   > AutAut.His_Uitgeslotene.DatAanvGel                   and NEW.DatAanvGel                   < AutAut.His_Uitgeslotene.DatEindeGel                 
AND AutAut.His_Uitgeslotene.ActieVerval                  IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM AutAut.His_Uitgeslotene WHERE NEW.Doelbinding                  = AutAut.His_Uitgeslotene.Doelbinding                  and NEW.ID                           <> AutAut.His_Uitgeslotene.ID                          
and NEW.DatEindeGel                  > AutAut.His_Uitgeslotene.DatAanvGel                   and NEW.DatEindeGel                  < AutAut.His_Uitgeslotene.DatEindeGel                  AND AutAut.His_Uitgeslotene.ActieVerval                  IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE AutAut.His_Uitgeslotene SET ActieVerval                  = NEW.ActieInh                    , TsVerval                     = NEW.TsReg                       

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Doelbinding                  = AutAut.His_Uitgeslotene.Doelbinding                  and NEW.ID                           <> AutAut.His_Uitgeslotene.ID                          
and AutAut.His_Uitgeslotene.DatAanvGel                   < NEW.DatEindeGel                  and AutAut.His_Uitgeslotene.DatEindeGel                  > NEW.DatAanvGel                  
AND AutAut.His_Uitgeslotene.ActieVerval                  is null AND AutAut.His_Uitgeslotene.TsVerval                     is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO AutAut.His_Uitgeslotene (Doelbinding                 , ActieAanpGel                , Protocolleringsniveau       , TekstDoelbinding            , Populatiecriterium          , IndVerstrbeperkingHonoreren , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT AutAut.His_Uitgeslotene.Doelbinding                 , AutAut.His_Doelbinding.ActieAanpGel                , AutAut.His_Doelbinding.Protocolleringsniveau       , AutAut.His_Doelbinding.TekstDoelbinding            , AutAut.His_Doelbinding.Populatiecriterium          , AutAut.His_Doelbinding.IndVerstrbeperkingHonoreren , AutAut.His_Uitgeslotene.DatAanvGel                  , NEW.DatAanvGel                  , AutAut.His_Uitgeslotene.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM AutAut.His_Uitgeslotene
WHERE AutAut.His_Uitgeslotene.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO AutAut.His_Uitgeslotene (Doelbinding                 , ActieAanpGel                , Protocolleringsniveau       , TekstDoelbinding            , Populatiecriterium          , IndVerstrbeperkingHonoreren , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT AutAut.His_Uitgeslotene.Doelbinding                 , AutAut.His_Doelbinding.ActieAanpGel                , AutAut.His_Doelbinding.Protocolleringsniveau       , AutAut.His_Doelbinding.TekstDoelbinding            , AutAut.His_Doelbinding.Populatiecriterium          , AutAut.His_Doelbinding.IndVerstrbeperkingHonoreren , NEW.DatEindeGel                 , AutAut.His_Uitgeslotene.DatEindeGel                 , AutAut.His_Uitgeslotene.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM AutAut.His_Uitgeslotene
WHERE AutAut.His_Uitgeslotene.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_AutAut_His_Uitgeslotene$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on AutAut.His_Uitgeslotene RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on AutAut.His_Uitgeslotene

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_AutAut_His_Uitgeslotene();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_BRM_His_Regelgedrag() returns TRIGGER AS $formele_historie_BRM_His_Regelgedrag$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.Bijhverantwoordelijkheid     is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.Bijhverantwoordelijkheid     Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.ActieInh                     = Kern.BRPActie.ID                          ;
IF NEW.Bijhverantwoordelijkheid     is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM BRM.His_Regelgedrag
WHERE NEW.Regelgedrag                  = BRM.His_Regelgedrag.Regelgedrag                  and NEW.ID                           <> BRM.His_Regelgedrag.ID                          
and NEW.TsReg                        > BRM.His_Regelgedrag.TsReg                        and NEW.TsReg                        < BRM.His_Regelgedrag.TsVerval                    
AND BRM.His_Regelgedrag.IndOpgeschort                IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM BRM.His_Regelgedrag WHERE NEW.Regelgedrag                  = BRM.His_Regelgedrag.Regelgedrag                  and NEW.ID                           <> BRM.His_Regelgedrag.ID                          
and NEW.TsVerval                     > BRM.His_Regelgedrag.TsReg                        and NEW.TsVerval                     < BRM.His_Regelgedrag.TsVerval                     AND BRM.His_Regelgedrag.IndOpgeschort                IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE BRM.His_Regelgedrag SET IndOpgeschort                = NEW.Bijhverantwoordelijkheid    , ActieVerval                  = NEW.ActieInh                    

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Regelgedrag                  = BRM.His_Regelgedrag.Regelgedrag                  and NEW.ID                           <> BRM.His_Regelgedrag.ID                          
and BRM.His_Regelgedrag.TsReg                        < NEW.TsVerval                     and BRM.His_Regelgedrag.TsVerval                     > NEW.TsReg                       
AND BRM.His_Regelgedrag.IndOpgeschort                is null AND BRM.His_Regelgedrag.ActieVerval                  is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO BRM.His_Regelgedrag (Regelgedrag                 , RdnOpschorting              , Effect                      , IndActief                   , TsReg                        , TsVerval                    , ActieInh                    , Bijhverantwoordelijkheid    , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT BRM.His_Regelgedrag.Regelgedrag                 , BRM.His_Regelgedrag.RdnOpschorting              , BRM.His_Regelgedrag.Effect                      , BRM.His_Regelgedrag.IndActief                   , BRM.His_Regelgedrag.TsReg                       , NEW.TsReg                       , BRM.His_Regelgedrag.ActieInh                    , NEW.Bijhverantwoordelijkheid    , NEW.ActieInh                     FROM BRM.His_Regelgedrag
WHERE BRM.His_Regelgedrag.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO BRM.His_Regelgedrag (Regelgedrag                 , RdnOpschorting              , Effect                      , IndActief                   , TsReg                        , TsVerval                    , ActieInh                    , Bijhverantwoordelijkheid    , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT BRM.His_Regelgedrag.Regelgedrag                 , BRM.His_Regelgedrag.RdnOpschorting              , BRM.His_Regelgedrag.Effect                      , BRM.His_Regelgedrag.IndActief                   , NEW.TsVerval                    , BRM.His_Regelgedrag.TsVerval                    , BRM.His_Regelgedrag.ActieInh                    , NEW.Bijhverantwoordelijkheid    , NEW.ActieInh                     FROM BRM.His_Regelgedrag
WHERE BRM.His_Regelgedrag.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_BRM_His_Regelgedrag$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on BRM.His_Regelgedrag RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on BRM.His_Regelgedrag

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_BRM_His_Regelgedrag();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_Betr() returns TRIGGER AS $formele_historie_Kern_His_Betr$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.Bijhverantwoordelijkheid     is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.Bijhverantwoordelijkheid     Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.ActieInh                     = Kern.BRPActie.ID                          ;
IF NEW.Bijhverantwoordelijkheid     is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_Betr
WHERE NEW.Regelgedrag                  = Kern.His_Betr.Regelgedrag                  and NEW.ID                           <> Kern.His_Betr.ID                          
and NEW.TsReg                        > Kern.His_Betr.TsReg                        and NEW.TsReg                        < Kern.His_Betr.TsVerval                    
AND Kern.His_Betr.IndOpgeschort                IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_Betr WHERE NEW.Regelgedrag                  = Kern.His_Betr.Regelgedrag                  and NEW.ID                           <> Kern.His_Betr.ID                          
and NEW.TsVerval                     > Kern.His_Betr.TsReg                        and NEW.TsVerval                     < Kern.His_Betr.TsVerval                     AND Kern.His_Betr.IndOpgeschort                IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_Betr SET IndOpgeschort                = NEW.Bijhverantwoordelijkheid    , ActieVerval                  = NEW.ActieInh                    

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Regelgedrag                  = Kern.His_Betr.Regelgedrag                  and NEW.ID                           <> Kern.His_Betr.ID                          
and Kern.His_Betr.TsReg                        < NEW.TsVerval                     and Kern.His_Betr.TsVerval                     > NEW.TsReg                       
AND Kern.His_Betr.IndOpgeschort                is null AND Kern.His_Betr.ActieVerval                  is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_Betr (Regelgedrag                 , RdnOpschorting              , Effect                      , IndActief                   , TsReg                        , TsVerval                    , ActieInh                    , Bijhverantwoordelijkheid    , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_Betr.Regelgedrag                 , BRM.His_Regelgedrag.RdnOpschorting              , BRM.His_Regelgedrag.Effect                      , BRM.His_Regelgedrag.IndActief                   , Kern.His_Betr.TsReg                       , NEW.TsReg                       , Kern.His_Betr.ActieInh                    , NEW.Bijhverantwoordelijkheid    , NEW.ActieInh                     FROM Kern.His_Betr
WHERE Kern.His_Betr.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_Betr (Regelgedrag                 , RdnOpschorting              , Effect                      , IndActief                   , TsReg                        , TsVerval                    , ActieInh                    , Bijhverantwoordelijkheid    , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_Betr.Regelgedrag                 , BRM.His_Regelgedrag.RdnOpschorting              , BRM.His_Regelgedrag.Effect                      , BRM.His_Regelgedrag.IndActief                   , NEW.TsVerval                    , Kern.His_Betr.TsVerval                    , Kern.His_Betr.ActieInh                    , NEW.Bijhverantwoordelijkheid    , NEW.ActieInh                     FROM Kern.His_Betr
WHERE Kern.His_Betr.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_Betr$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_Betr RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_Betr

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_Betr();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_BetrOuderlijkGezag() returns TRIGGER AS $formele_historie_Kern_His_BetrOuderlijkGezag$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.ActieInh                     is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.ActieInh                     Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.TsReg                        = Kern.BRPActie.ID                          ;
IF NEW.ActieInh                     is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_BetrOuderlijkGezag
WHERE NEW.Betr                         = Kern.His_BetrOuderlijkGezag.Betr                         and NEW.ID                           <> Kern.His_BetrOuderlijkGezag.ID                          
and NEW.DatAanvGel                   > Kern.His_BetrOuderlijkGezag.DatAanvGel                   and NEW.DatAanvGel                   < Kern.His_BetrOuderlijkGezag.DatEindeGel                 
AND Kern.His_BetrOuderlijkGezag.ActieVerval                  IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_BetrOuderlijkGezag WHERE NEW.Betr                         = Kern.His_BetrOuderlijkGezag.Betr                         and NEW.ID                           <> Kern.His_BetrOuderlijkGezag.ID                          
and NEW.DatEindeGel                  > Kern.His_BetrOuderlijkGezag.DatAanvGel                   and NEW.DatEindeGel                  < Kern.His_BetrOuderlijkGezag.DatEindeGel                  AND Kern.His_BetrOuderlijkGezag.ActieVerval                  IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_BetrOuderlijkGezag SET ActieVerval                  = NEW.ActieInh                    , TsVerval                     = NEW.TsReg                       

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Betr                         = Kern.His_BetrOuderlijkGezag.Betr                         and NEW.ID                           <> Kern.His_BetrOuderlijkGezag.ID                          
and Kern.His_BetrOuderlijkGezag.DatAanvGel                   < NEW.DatEindeGel                  and Kern.His_BetrOuderlijkGezag.DatEindeGel                  > NEW.DatAanvGel                  
AND Kern.His_BetrOuderlijkGezag.ActieVerval                  is null AND Kern.His_BetrOuderlijkGezag.TsVerval                     is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_BetrOuderlijkGezag (Betr                        , ActieAanpGel                , IndOuderHeeftGezag          , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_BetrOuderlijkGezag.Betr                        , Kern.His_BetrOuderlijkGezag.ActieAanpGel                , Kern.His_BetrOuderlijkGezag.IndOuderHeeftGezag          , Kern.His_BetrOuderlijkGezag.DatAanvGel                  , NEW.DatAanvGel                  , Kern.His_BetrOuderlijkGezag.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_BetrOuderlijkGezag
WHERE Kern.His_BetrOuderlijkGezag.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_BetrOuderlijkGezag (Betr                        , ActieAanpGel                , IndOuderHeeftGezag          , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_BetrOuderlijkGezag.Betr                        , Kern.His_BetrOuderlijkGezag.ActieAanpGel                , Kern.His_BetrOuderlijkGezag.IndOuderHeeftGezag          , NEW.DatEindeGel                 , Kern.His_BetrOuderlijkGezag.DatEindeGel                 , Kern.His_BetrOuderlijkGezag.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_BetrOuderlijkGezag
WHERE Kern.His_BetrOuderlijkGezag.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_BetrOuderlijkGezag$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_BetrOuderlijkGezag RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_BetrOuderlijkGezag

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_BetrOuderlijkGezag();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_Doc() returns TRIGGER AS $formele_historie_Kern_His_Doc$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.Ident                        is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.Ident                        Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.ActieInh                     = Kern.BRPActie.ID                          ;
IF NEW.Ident                        is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_Doc
WHERE NEW.Doc                          = Kern.His_Doc.Doc                          and NEW.ID                           <> Kern.His_Doc.ID                          
and NEW.TsReg                        > Kern.His_Doc.TsReg                        and NEW.TsReg                        < Kern.His_Doc.TsVerval                    
AND Kern.His_Doc.Aktenr                       IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_Doc WHERE NEW.Doc                          = Kern.His_Doc.Doc                          and NEW.ID                           <> Kern.His_Doc.ID                          
and NEW.TsVerval                     > Kern.His_Doc.TsReg                        and NEW.TsVerval                     < Kern.His_Doc.TsVerval                     AND Kern.His_Doc.Aktenr                       IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_Doc SET Aktenr                       = NEW.Ident                       , ActieVerval                  = NEW.ActieInh                    

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Doc                          = Kern.His_Doc.Doc                          and NEW.ID                           <> Kern.His_Doc.ID                          
and Kern.His_Doc.TsReg                        < NEW.TsVerval                     and Kern.His_Doc.TsVerval                     > NEW.TsReg                       
AND Kern.His_Doc.Aktenr                       is null AND Kern.His_Doc.ActieVerval                  is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_Doc (Doc                         , Oms                         , Partij                      , TsReg                        , TsVerval                    , ActieInh                    , Ident                       , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_Doc.Doc                         , Kern.His_Doc.Oms                         , Kern.His_Doc.Partij                      , Kern.His_Doc.TsReg                       , NEW.TsReg                       , Kern.His_Doc.ActieInh                    , NEW.Ident                       , NEW.ActieInh                     FROM Kern.His_Doc
WHERE Kern.His_Doc.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_Doc (Doc                         , Oms                         , Partij                      , TsReg                        , TsVerval                    , ActieInh                    , Ident                       , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_Doc.Doc                         , Kern.His_Doc.Oms                         , Kern.His_Doc.Partij                      , NEW.TsVerval                    , Kern.His_Doc.TsVerval                    , Kern.His_Doc.ActieInh                    , NEW.Ident                       , NEW.ActieInh                     FROM Kern.His_Doc
WHERE Kern.His_Doc.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_Doc$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_Doc RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_Doc

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_Doc();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_Gem() returns TRIGGER AS $formele_historie_Kern_His_Gem$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.VoortzettendeGem             is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.VoortzettendeGem             Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.ActieInh                     = Kern.BRPActie.ID                          ;
IF NEW.VoortzettendeGem             is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_Gem
WHERE NEW.Partij                       = Kern.His_Gem.Partij                       and NEW.ID                           <> Kern.His_Gem.ID                          
and NEW.TsReg                        > Kern.His_Gem.TsReg                        and NEW.TsReg                        < Kern.His_Gem.TsVerval                    
AND Kern.His_Gem.Gemcode                      IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_Gem WHERE NEW.Partij                       = Kern.His_Gem.Partij                       and NEW.ID                           <> Kern.His_Gem.ID                          
and NEW.TsVerval                     > Kern.His_Gem.TsReg                        and NEW.TsVerval                     < Kern.His_Gem.TsVerval                     AND Kern.His_Gem.Gemcode                      IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_Gem SET Gemcode                      = NEW.VoortzettendeGem            , ActieVerval                  = NEW.ActieInh                    

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Partij                       = Kern.His_Gem.Partij                       and NEW.ID                           <> Kern.His_Gem.ID                          
and Kern.His_Gem.TsReg                        < NEW.TsVerval                     and Kern.His_Gem.TsVerval                     > NEW.TsReg                       
AND Kern.His_Gem.Gemcode                      is null AND Kern.His_Gem.ActieVerval                  is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_Gem (Partij                      , OnderdeelVan                , TsReg                        , TsVerval                    , ActieInh                    , VoortzettendeGem            , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_Gem.Partij                      , Kern.His_Gem.OnderdeelVan                , Kern.His_Gem.TsReg                       , NEW.TsReg                       , Kern.His_Gem.ActieInh                    , NEW.VoortzettendeGem            , NEW.ActieInh                     FROM Kern.His_Gem
WHERE Kern.His_Gem.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_Gem (Partij                      , OnderdeelVan                , TsReg                        , TsVerval                    , ActieInh                    , VoortzettendeGem            , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_Gem.Partij                      , Kern.His_Gem.OnderdeelVan                , NEW.TsVerval                    , Kern.His_Gem.TsVerval                    , Kern.His_Gem.ActieInh                    , NEW.VoortzettendeGem            , NEW.ActieInh                     FROM Kern.His_Gem
WHERE Kern.His_Gem.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_Gem$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_Gem RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_Gem

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_Gem();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_MultiRealiteitRegel() returns TRIGGER AS $formele_historie_Kern_His_MultiRealiteitRegel$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.VoortzettendeGem             is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.VoortzettendeGem             Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.ActieInh                     = Kern.BRPActie.ID                          ;
IF NEW.VoortzettendeGem             is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_MultiRealiteitRegel
WHERE NEW.Partij                       = Kern.His_MultiRealiteitRegel.Partij                       and NEW.ID                           <> Kern.His_MultiRealiteitRegel.ID                          
and NEW.TsReg                        > Kern.His_MultiRealiteitRegel.TsReg                        and NEW.TsReg                        < Kern.His_MultiRealiteitRegel.TsVerval                    
AND Kern.His_MultiRealiteitRegel.Gemcode                      IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_MultiRealiteitRegel WHERE NEW.Partij                       = Kern.His_MultiRealiteitRegel.Partij                       and NEW.ID                           <> Kern.His_MultiRealiteitRegel.ID                          
and NEW.TsVerval                     > Kern.His_MultiRealiteitRegel.TsReg                        and NEW.TsVerval                     < Kern.His_MultiRealiteitRegel.TsVerval                     AND Kern.His_MultiRealiteitRegel.Gemcode                      IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_MultiRealiteitRegel SET Gemcode                      = NEW.VoortzettendeGem            , ActieVerval                  = NEW.ActieInh                    

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Partij                       = Kern.His_MultiRealiteitRegel.Partij                       and NEW.ID                           <> Kern.His_MultiRealiteitRegel.ID                          
and Kern.His_MultiRealiteitRegel.TsReg                        < NEW.TsVerval                     and Kern.His_MultiRealiteitRegel.TsVerval                     > NEW.TsReg                       
AND Kern.His_MultiRealiteitRegel.Gemcode                      is null AND Kern.His_MultiRealiteitRegel.ActieVerval                  is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_MultiRealiteitRegel (Partij                      , OnderdeelVan                , TsReg                        , TsVerval                    , ActieInh                    , VoortzettendeGem            , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_MultiRealiteitRegel.Partij                      , Kern.His_Gem.OnderdeelVan                , Kern.His_MultiRealiteitRegel.TsReg                       , NEW.TsReg                       , Kern.His_MultiRealiteitRegel.ActieInh                    , NEW.VoortzettendeGem            , NEW.ActieInh                     FROM Kern.His_MultiRealiteitRegel
WHERE Kern.His_MultiRealiteitRegel.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_MultiRealiteitRegel (Partij                      , OnderdeelVan                , TsReg                        , TsVerval                    , ActieInh                    , VoortzettendeGem            , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_MultiRealiteitRegel.Partij                      , Kern.His_Gem.OnderdeelVan                , NEW.TsVerval                    , Kern.His_MultiRealiteitRegel.TsVerval                    , Kern.His_MultiRealiteitRegel.ActieInh                    , NEW.VoortzettendeGem            , NEW.ActieInh                     FROM Kern.His_MultiRealiteitRegel
WHERE Kern.His_MultiRealiteitRegel.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_MultiRealiteitRegel$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_MultiRealiteitRegel RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_MultiRealiteitRegel

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_MultiRealiteitRegel();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_Onderzoek() returns TRIGGER AS $formele_historie_Kern_His_Onderzoek$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.DatBegin                     is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.DatBegin                     Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.ActieInh                     = Kern.BRPActie.ID                          ;
IF NEW.DatBegin                     is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_Onderzoek
WHERE NEW.Onderzoek                    = Kern.His_Onderzoek.Onderzoek                    and NEW.ID                           <> Kern.His_Onderzoek.ID                          
and NEW.TsReg                        > Kern.His_Onderzoek.TsReg                        and NEW.TsReg                        < Kern.His_Onderzoek.TsVerval                    
AND Kern.His_Onderzoek.DatEinde                     IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_Onderzoek WHERE NEW.Onderzoek                    = Kern.His_Onderzoek.Onderzoek                    and NEW.ID                           <> Kern.His_Onderzoek.ID                          
and NEW.TsVerval                     > Kern.His_Onderzoek.TsReg                        and NEW.TsVerval                     < Kern.His_Onderzoek.TsVerval                     AND Kern.His_Onderzoek.DatEinde                     IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_Onderzoek SET DatEinde                     = NEW.DatBegin                    , ActieVerval                  = NEW.ActieInh                    

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Onderzoek                    = Kern.His_Onderzoek.Onderzoek                    and NEW.ID                           <> Kern.His_Onderzoek.ID                          
and Kern.His_Onderzoek.TsReg                        < NEW.TsVerval                     and Kern.His_Onderzoek.TsVerval                     > NEW.TsReg                       
AND Kern.His_Onderzoek.DatEinde                     is null AND Kern.His_Onderzoek.ActieVerval                  is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_Onderzoek (Onderzoek                   , Oms                         , TsReg                        , TsVerval                    , ActieInh                    , DatBegin                    , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_Onderzoek.Onderzoek                   , Kern.His_Onderzoek.Oms                         , Kern.His_Onderzoek.TsReg                       , NEW.TsReg                       , Kern.His_Onderzoek.ActieInh                    , NEW.DatBegin                    , NEW.ActieInh                     FROM Kern.His_Onderzoek
WHERE Kern.His_Onderzoek.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_Onderzoek (Onderzoek                   , Oms                         , TsReg                        , TsVerval                    , ActieInh                    , DatBegin                    , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_Onderzoek.Onderzoek                   , Kern.His_Onderzoek.Oms                         , NEW.TsVerval                    , Kern.His_Onderzoek.TsVerval                    , Kern.His_Onderzoek.ActieInh                    , NEW.DatBegin                    , NEW.ActieInh                     FROM Kern.His_Onderzoek
WHERE Kern.His_Onderzoek.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_Onderzoek$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_Onderzoek RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_Onderzoek

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_Onderzoek();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_Partij() returns TRIGGER AS $formele_historie_Kern_His_Partij$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.DatAanv                      is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.DatAanv                      Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.ActieInh                     = Kern.BRPActie.ID                          ;
IF NEW.DatAanv                      is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_Partij
WHERE NEW.Partij                       = Kern.His_Partij.Partij                       and NEW.ID                           <> Kern.His_Partij.ID                          
and NEW.TsReg                        > Kern.His_Partij.TsReg                        and NEW.TsReg                        < Kern.His_Partij.TsVerval                    
AND Kern.His_Partij.DatEinde                     IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_Partij WHERE NEW.Partij                       = Kern.His_Partij.Partij                       and NEW.ID                           <> Kern.His_Partij.ID                          
and NEW.TsVerval                     > Kern.His_Partij.TsReg                        and NEW.TsVerval                     < Kern.His_Partij.TsVerval                     AND Kern.His_Partij.DatEinde                     IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_Partij SET DatEinde                     = NEW.DatAanv                     , ActieVerval                  = NEW.ActieInh                    

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Partij                       = Kern.His_Partij.Partij                       and NEW.ID                           <> Kern.His_Partij.ID                          
and Kern.His_Partij.TsReg                        < NEW.TsVerval                     and Kern.His_Partij.TsVerval                     > NEW.TsReg                       
AND Kern.His_Partij.DatEinde                     is null AND Kern.His_Partij.ActieVerval                  is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_Partij (Partij                      , Sector                      , TsReg                        , TsVerval                    , ActieInh                    , DatAanv                     , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_Partij.Partij                      , Kern.His_Partij.Sector                      , Kern.His_Partij.TsReg                       , NEW.TsReg                       , Kern.His_Partij.ActieInh                    , NEW.DatAanv                     , NEW.ActieInh                     FROM Kern.His_Partij
WHERE Kern.His_Partij.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_Partij (Partij                      , Sector                      , TsReg                        , TsVerval                    , ActieInh                    , DatAanv                     , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_Partij.Partij                      , Kern.His_Partij.Sector                      , NEW.TsVerval                    , Kern.His_Partij.TsVerval                    , Kern.His_Partij.ActieInh                    , NEW.DatAanv                     , NEW.ActieInh                     FROM Kern.His_Partij
WHERE Kern.His_Partij.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_Partij$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_Partij RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_Partij

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_Partij();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_PersAanschr() returns TRIGGER AS $formele_historie_Kern_His_PersAanschr$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.ActieInh                     is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.ActieInh                     Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.TsReg                        = Kern.BRPActie.ID                          ;
IF NEW.ActieInh                     is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_PersAanschr
WHERE NEW.Pers                         = Kern.His_PersAanschr.Pers                         and NEW.ID                           <> Kern.His_PersAanschr.ID                          
and NEW.DatAanvGel                   > Kern.His_PersAanschr.DatAanvGel                   and NEW.DatAanvGel                   < Kern.His_PersAanschr.DatEindeGel                 
AND Kern.His_PersAanschr.ActieVerval                  IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_PersAanschr WHERE NEW.Pers                         = Kern.His_PersAanschr.Pers                         and NEW.ID                           <> Kern.His_PersAanschr.ID                          
and NEW.DatEindeGel                  > Kern.His_PersAanschr.DatAanvGel                   and NEW.DatEindeGel                  < Kern.His_PersAanschr.DatEindeGel                  AND Kern.His_PersAanschr.ActieVerval                  IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_PersAanschr SET ActieVerval                  = NEW.ActieInh                    , TsVerval                     = NEW.TsReg                       

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Pers                         = Kern.His_PersAanschr.Pers                         and NEW.ID                           <> Kern.His_PersAanschr.ID                          
and Kern.His_PersAanschr.DatAanvGel                   < NEW.DatEindeGel                  and Kern.His_PersAanschr.DatEindeGel                  > NEW.DatAanvGel                  
AND Kern.His_PersAanschr.ActieVerval                  is null AND Kern.His_PersAanschr.TsVerval                     is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_PersAanschr (Pers                        , ActieAanpGel                , GebrGeslnaamEGP             , IndAanschrMetAdellijkeTitels, IndAanschrAlgoritmischAfgele, PredikaatAanschr            , VoornamenAanschr            , VoorvoegselAanschr          , ScheidingstekenAanschr      , GeslnaamAanschr             , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_PersAanschr.Pers                        , Kern.His_PersAanschr.ActieAanpGel                , Kern.His_PersAanschr.GebrGeslnaamEGP             , Kern.His_PersAanschr.IndAanschrMetAdellijkeTitels, Kern.His_PersAanschr.IndAanschrAlgoritmischAfgele, Kern.His_PersAanschr.PredikaatAanschr            , Kern.His_PersAanschr.VoornamenAanschr            , Kern.His_PersAanschr.VoorvoegselAanschr          , Kern.His_PersAanschr.ScheidingstekenAanschr      , Kern.His_PersAanschr.GeslnaamAanschr             , Kern.His_PersAanschr.DatAanvGel                  , NEW.DatAanvGel                  , Kern.His_PersAanschr.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersAanschr
WHERE Kern.His_PersAanschr.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_PersAanschr (Pers                        , ActieAanpGel                , GebrGeslnaamEGP             , IndAanschrMetAdellijkeTitels, IndAanschrAlgoritmischAfgele, PredikaatAanschr            , VoornamenAanschr            , VoorvoegselAanschr          , ScheidingstekenAanschr      , GeslnaamAanschr             , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_PersAanschr.Pers                        , Kern.His_PersAanschr.ActieAanpGel                , Kern.His_PersAanschr.GebrGeslnaamEGP             , Kern.His_PersAanschr.IndAanschrMetAdellijkeTitels, Kern.His_PersAanschr.IndAanschrAlgoritmischAfgele, Kern.His_PersAanschr.PredikaatAanschr            , Kern.His_PersAanschr.VoornamenAanschr            , Kern.His_PersAanschr.VoorvoegselAanschr          , Kern.His_PersAanschr.ScheidingstekenAanschr      , Kern.His_PersAanschr.GeslnaamAanschr             , NEW.DatEindeGel                 , Kern.His_PersAanschr.DatEindeGel                 , Kern.His_PersAanschr.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersAanschr
WHERE Kern.His_PersAanschr.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_PersAanschr$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_PersAanschr RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_PersAanschr

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_PersAanschr();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_PersBijhgem() returns TRIGGER AS $formele_historie_Kern_His_PersBijhgem$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.ActieInh                     is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.ActieInh                     Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.TsReg                        = Kern.BRPActie.ID                          ;
IF NEW.ActieInh                     is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_PersBijhgem
WHERE NEW.Pers                         = Kern.His_PersBijhgem.Pers                         and NEW.ID                           <> Kern.His_PersBijhgem.ID                          
and NEW.DatAanvGel                   > Kern.His_PersBijhgem.DatAanvGel                   and NEW.DatAanvGel                   < Kern.His_PersBijhgem.DatEindeGel                 
AND Kern.His_PersBijhgem.ActieVerval                  IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_PersBijhgem WHERE NEW.Pers                         = Kern.His_PersBijhgem.Pers                         and NEW.ID                           <> Kern.His_PersBijhgem.ID                          
and NEW.DatEindeGel                  > Kern.His_PersBijhgem.DatAanvGel                   and NEW.DatEindeGel                  < Kern.His_PersBijhgem.DatEindeGel                  AND Kern.His_PersBijhgem.ActieVerval                  IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_PersBijhgem SET ActieVerval                  = NEW.ActieInh                    , TsVerval                     = NEW.TsReg                       

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Pers                         = Kern.His_PersBijhgem.Pers                         and NEW.ID                           <> Kern.His_PersBijhgem.ID                          
and Kern.His_PersBijhgem.DatAanvGel                   < NEW.DatEindeGel                  and Kern.His_PersBijhgem.DatEindeGel                  > NEW.DatAanvGel                  
AND Kern.His_PersBijhgem.ActieVerval                  is null AND Kern.His_PersBijhgem.TsVerval                     is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_PersBijhgem (Pers                        , ActieAanpGel                , Bijhgem                     , DatInschrInGem              , IndOnverwDocAanw            , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_PersBijhgem.Pers                        , Kern.His_PersBijhgem.ActieAanpGel                , Kern.His_PersBijhgem.Bijhgem                     , Kern.His_PersBijhgem.DatInschrInGem              , Kern.His_PersBijhgem.IndOnverwDocAanw            , Kern.His_PersBijhgem.DatAanvGel                  , NEW.DatAanvGel                  , Kern.His_PersBijhgem.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersBijhgem
WHERE Kern.His_PersBijhgem.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_PersBijhgem (Pers                        , ActieAanpGel                , Bijhgem                     , DatInschrInGem              , IndOnverwDocAanw            , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_PersBijhgem.Pers                        , Kern.His_PersBijhgem.ActieAanpGel                , Kern.His_PersBijhgem.Bijhgem                     , Kern.His_PersBijhgem.DatInschrInGem              , Kern.His_PersBijhgem.IndOnverwDocAanw            , NEW.DatEindeGel                 , Kern.His_PersBijhgem.DatEindeGel                 , Kern.His_PersBijhgem.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersBijhgem
WHERE Kern.His_PersBijhgem.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_PersBijhgem$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_PersBijhgem RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_PersBijhgem

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_PersBijhgem();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_PersBijhverantwoordelijk() returns TRIGGER AS $formele_historie_Kern_His_PersBijhverantwoordelijk$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.ActieInh                     is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.ActieInh                     Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.TsReg                        = Kern.BRPActie.ID                          ;
IF NEW.ActieInh                     is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_PersBijhverantwoordelijk
WHERE NEW.Pers                         = Kern.His_PersBijhverantwoordelijk.Pers                         and NEW.ID                           <> Kern.His_PersBijhverantwoordelijk.ID                          
and NEW.DatAanvGel                   > Kern.His_PersBijhverantwoordelijk.DatAanvGel                   and NEW.DatAanvGel                   < Kern.His_PersBijhverantwoordelijk.DatEindeGel                 
AND Kern.His_PersBijhverantwoordelijk.ActieVerval                  IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_PersBijhverantwoordelijk WHERE NEW.Pers                         = Kern.His_PersBijhverantwoordelijk.Pers                         and NEW.ID                           <> Kern.His_PersBijhverantwoordelijk.ID                          
and NEW.DatEindeGel                  > Kern.His_PersBijhverantwoordelijk.DatAanvGel                   and NEW.DatEindeGel                  < Kern.His_PersBijhverantwoordelijk.DatEindeGel                  AND Kern.His_PersBijhverantwoordelijk.ActieVerval                  IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_PersBijhverantwoordelijk SET ActieVerval                  = NEW.ActieInh                    , TsVerval                     = NEW.TsReg                       

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Pers                         = Kern.His_PersBijhverantwoordelijk.Pers                         and NEW.ID                           <> Kern.His_PersBijhverantwoordelijk.ID                          
and Kern.His_PersBijhverantwoordelijk.DatAanvGel                   < NEW.DatEindeGel                  and Kern.His_PersBijhverantwoordelijk.DatEindeGel                  > NEW.DatAanvGel                  
AND Kern.His_PersBijhverantwoordelijk.ActieVerval                  is null AND Kern.His_PersBijhverantwoordelijk.TsVerval                     is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_PersBijhverantwoordelijk (Pers                        , ActieAanpGel                , Verantwoordelijke           , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_PersBijhverantwoordelijk.Pers                        , Kern.His_PersBijhverantwoordelijk.ActieAanpGel                , Kern.His_PersBijhverantwoordelijk.Verantwoordelijke           , Kern.His_PersBijhverantwoordelijk.DatAanvGel                  , NEW.DatAanvGel                  , Kern.His_PersBijhverantwoordelijk.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersBijhverantwoordelijk
WHERE Kern.His_PersBijhverantwoordelijk.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_PersBijhverantwoordelijk (Pers                        , ActieAanpGel                , Verantwoordelijke           , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_PersBijhverantwoordelijk.Pers                        , Kern.His_PersBijhverantwoordelijk.ActieAanpGel                , Kern.His_PersBijhverantwoordelijk.Verantwoordelijke           , NEW.DatEindeGel                 , Kern.His_PersBijhverantwoordelijk.DatEindeGel                 , Kern.His_PersBijhverantwoordelijk.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersBijhverantwoordelijk
WHERE Kern.His_PersBijhverantwoordelijk.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_PersBijhverantwoordelijk$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_PersBijhverantwoordelijk RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_PersBijhverantwoordelijk

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_PersBijhverantwoordelijk();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_PersEUVerkiezingen() returns TRIGGER AS $formele_historie_Kern_His_PersEUVerkiezingen$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.IndDeelnEUVerkiezingen       is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.IndDeelnEUVerkiezingen       Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.ActieInh                     = Kern.BRPActie.ID                          ;
IF NEW.IndDeelnEUVerkiezingen       is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_PersEUVerkiezingen
WHERE NEW.Pers                         = Kern.His_PersEUVerkiezingen.Pers                         and NEW.ID                           <> Kern.His_PersEUVerkiezingen.ID                          
and NEW.TsReg                        > Kern.His_PersEUVerkiezingen.TsReg                        and NEW.TsReg                        < Kern.His_PersEUVerkiezingen.TsVerval                    
AND Kern.His_PersEUVerkiezingen.DatAanlAanpDeelnEUVerkiezing IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_PersEUVerkiezingen WHERE NEW.Pers                         = Kern.His_PersEUVerkiezingen.Pers                         and NEW.ID                           <> Kern.His_PersEUVerkiezingen.ID                          
and NEW.TsVerval                     > Kern.His_PersEUVerkiezingen.TsReg                        and NEW.TsVerval                     < Kern.His_PersEUVerkiezingen.TsVerval                     AND Kern.His_PersEUVerkiezingen.DatAanlAanpDeelnEUVerkiezing IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_PersEUVerkiezingen SET DatAanlAanpDeelnEUVerkiezing = NEW.IndDeelnEUVerkiezingen      , ActieVerval                  = NEW.ActieInh                    

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Pers                         = Kern.His_PersEUVerkiezingen.Pers                         and NEW.ID                           <> Kern.His_PersEUVerkiezingen.ID                          
and Kern.His_PersEUVerkiezingen.TsReg                        < NEW.TsVerval                     and Kern.His_PersEUVerkiezingen.TsVerval                     > NEW.TsReg                       
AND Kern.His_PersEUVerkiezingen.DatAanlAanpDeelnEUVerkiezing is null AND Kern.His_PersEUVerkiezingen.ActieVerval                  is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_PersEUVerkiezingen (Pers                        , DatEindeUitslEUKiesr        , TsReg                        , TsVerval                    , ActieInh                    , IndDeelnEUVerkiezingen      , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_PersEUVerkiezingen.Pers                        , Kern.His_PersEUVerkiezingen.DatEindeUitslEUKiesr        , Kern.His_PersEUVerkiezingen.TsReg                       , NEW.TsReg                       , Kern.His_PersEUVerkiezingen.ActieInh                    , NEW.IndDeelnEUVerkiezingen      , NEW.ActieInh                     FROM Kern.His_PersEUVerkiezingen
WHERE Kern.His_PersEUVerkiezingen.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_PersEUVerkiezingen (Pers                        , DatEindeUitslEUKiesr        , TsReg                        , TsVerval                    , ActieInh                    , IndDeelnEUVerkiezingen      , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_PersEUVerkiezingen.Pers                        , Kern.His_PersEUVerkiezingen.DatEindeUitslEUKiesr        , NEW.TsVerval                    , Kern.His_PersEUVerkiezingen.TsVerval                    , Kern.His_PersEUVerkiezingen.ActieInh                    , NEW.IndDeelnEUVerkiezingen      , NEW.ActieInh                     FROM Kern.His_PersEUVerkiezingen
WHERE Kern.His_PersEUVerkiezingen.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_PersEUVerkiezingen$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_PersEUVerkiezingen RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_PersEUVerkiezingen

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_PersEUVerkiezingen();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_PersGeboorte() returns TRIGGER AS $formele_historie_Kern_His_PersGeboorte$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.DatGeboorte                  is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.DatGeboorte                  Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.ActieInh                     = Kern.BRPActie.ID                          ;
IF NEW.DatGeboorte                  is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_PersGeboorte
WHERE NEW.Pers                         = Kern.His_PersGeboorte.Pers                         and NEW.ID                           <> Kern.His_PersGeboorte.ID                          
and NEW.TsReg                        > Kern.His_PersGeboorte.TsReg                        and NEW.TsReg                        < Kern.His_PersGeboorte.TsVerval                    
AND Kern.His_PersGeboorte.GemGeboorte                  IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_PersGeboorte WHERE NEW.Pers                         = Kern.His_PersGeboorte.Pers                         and NEW.ID                           <> Kern.His_PersGeboorte.ID                          
and NEW.TsVerval                     > Kern.His_PersGeboorte.TsReg                        and NEW.TsVerval                     < Kern.His_PersGeboorte.TsVerval                     AND Kern.His_PersGeboorte.GemGeboorte                  IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_PersGeboorte SET GemGeboorte                  = NEW.DatGeboorte                 , ActieVerval                  = NEW.ActieInh                    

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Pers                         = Kern.His_PersGeboorte.Pers                         and NEW.ID                           <> Kern.His_PersGeboorte.ID                          
and Kern.His_PersGeboorte.TsReg                        < NEW.TsVerval                     and Kern.His_PersGeboorte.TsVerval                     > NEW.TsReg                       
AND Kern.His_PersGeboorte.GemGeboorte                  is null AND Kern.His_PersGeboorte.ActieVerval                  is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_PersGeboorte (Pers                        , WplGeboorte                 , BLGeboorteplaats            , BLRegioGeboorte             , LandGeboorte                , OmsGeboorteloc              , TsReg                        , TsVerval                    , ActieInh                    , DatGeboorte                 , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_PersGeboorte.Pers                        , Kern.His_PersGeboorte.WplGeboorte                 , Kern.His_PersGeboorte.BLGeboorteplaats            , Kern.His_PersGeboorte.BLRegioGeboorte             , Kern.His_PersGeboorte.LandGeboorte                , Kern.His_PersGeboorte.OmsGeboorteloc              , Kern.His_PersGeboorte.TsReg                       , NEW.TsReg                       , Kern.His_PersGeboorte.ActieInh                    , NEW.DatGeboorte                 , NEW.ActieInh                     FROM Kern.His_PersGeboorte
WHERE Kern.His_PersGeboorte.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_PersGeboorte (Pers                        , WplGeboorte                 , BLGeboorteplaats            , BLRegioGeboorte             , LandGeboorte                , OmsGeboorteloc              , TsReg                        , TsVerval                    , ActieInh                    , DatGeboorte                 , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_PersGeboorte.Pers                        , Kern.His_PersGeboorte.WplGeboorte                 , Kern.His_PersGeboorte.BLGeboorteplaats            , Kern.His_PersGeboorte.BLRegioGeboorte             , Kern.His_PersGeboorte.LandGeboorte                , Kern.His_PersGeboorte.OmsGeboorteloc              , NEW.TsVerval                    , Kern.His_PersGeboorte.TsVerval                    , Kern.His_PersGeboorte.ActieInh                    , NEW.DatGeboorte                 , NEW.ActieInh                     FROM Kern.His_PersGeboorte
WHERE Kern.His_PersGeboorte.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_PersGeboorte$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_PersGeboorte RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_PersGeboorte

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_PersGeboorte();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_PersGeslachtsaand() returns TRIGGER AS $formele_historie_Kern_His_PersGeslachtsaand$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.ActieInh                     is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.ActieInh                     Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.TsReg                        = Kern.BRPActie.ID                          ;
IF NEW.ActieInh                     is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_PersGeslachtsaand
WHERE NEW.Pers                         = Kern.His_PersGeslachtsaand.Pers                         and NEW.ID                           <> Kern.His_PersGeslachtsaand.ID                          
and NEW.DatAanvGel                   > Kern.His_PersGeslachtsaand.DatAanvGel                   and NEW.DatAanvGel                   < Kern.His_PersGeslachtsaand.DatEindeGel                 
AND Kern.His_PersGeslachtsaand.ActieVerval                  IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_PersGeslachtsaand WHERE NEW.Pers                         = Kern.His_PersGeslachtsaand.Pers                         and NEW.ID                           <> Kern.His_PersGeslachtsaand.ID                          
and NEW.DatEindeGel                  > Kern.His_PersGeslachtsaand.DatAanvGel                   and NEW.DatEindeGel                  < Kern.His_PersGeslachtsaand.DatEindeGel                  AND Kern.His_PersGeslachtsaand.ActieVerval                  IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_PersGeslachtsaand SET ActieVerval                  = NEW.ActieInh                    , TsVerval                     = NEW.TsReg                       

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Pers                         = Kern.His_PersGeslachtsaand.Pers                         and NEW.ID                           <> Kern.His_PersGeslachtsaand.ID                          
and Kern.His_PersGeslachtsaand.DatAanvGel                   < NEW.DatEindeGel                  and Kern.His_PersGeslachtsaand.DatEindeGel                  > NEW.DatAanvGel                  
AND Kern.His_PersGeslachtsaand.ActieVerval                  is null AND Kern.His_PersGeslachtsaand.TsVerval                     is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_PersGeslachtsaand (Pers                        , ActieAanpGel                , Geslachtsaand               , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_PersGeslachtsaand.Pers                        , Kern.His_PersGeslachtsaand.ActieAanpGel                , Kern.His_PersGeslachtsaand.Geslachtsaand               , Kern.His_PersGeslachtsaand.DatAanvGel                  , NEW.DatAanvGel                  , Kern.His_PersGeslachtsaand.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersGeslachtsaand
WHERE Kern.His_PersGeslachtsaand.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_PersGeslachtsaand (Pers                        , ActieAanpGel                , Geslachtsaand               , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_PersGeslachtsaand.Pers                        , Kern.His_PersGeslachtsaand.ActieAanpGel                , Kern.His_PersGeslachtsaand.Geslachtsaand               , NEW.DatEindeGel                 , Kern.His_PersGeslachtsaand.DatEindeGel                 , Kern.His_PersGeslachtsaand.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersGeslachtsaand
WHERE Kern.His_PersGeslachtsaand.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_PersGeslachtsaand$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_PersGeslachtsaand RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_PersGeslachtsaand

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_PersGeslachtsaand();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_PersIDs() returns TRIGGER AS $formele_historie_Kern_His_PersIDs$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.ActieInh                     is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.ActieInh                     Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.TsReg                        = Kern.BRPActie.ID                          ;
IF NEW.ActieInh                     is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_PersIDs
WHERE NEW.Pers                         = Kern.His_PersIDs.Pers                         and NEW.ID                           <> Kern.His_PersIDs.ID                          
and NEW.DatAanvGel                   > Kern.His_PersIDs.DatAanvGel                   and NEW.DatAanvGel                   < Kern.His_PersIDs.DatEindeGel                 
AND Kern.His_PersIDs.ActieVerval                  IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_PersIDs WHERE NEW.Pers                         = Kern.His_PersIDs.Pers                         and NEW.ID                           <> Kern.His_PersIDs.ID                          
and NEW.DatEindeGel                  > Kern.His_PersIDs.DatAanvGel                   and NEW.DatEindeGel                  < Kern.His_PersIDs.DatEindeGel                  AND Kern.His_PersIDs.ActieVerval                  IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_PersIDs SET ActieVerval                  = NEW.ActieInh                    , TsVerval                     = NEW.TsReg                       

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Pers                         = Kern.His_PersIDs.Pers                         and NEW.ID                           <> Kern.His_PersIDs.ID                          
and Kern.His_PersIDs.DatAanvGel                   < NEW.DatEindeGel                  and Kern.His_PersIDs.DatEindeGel                  > NEW.DatAanvGel                  
AND Kern.His_PersIDs.ActieVerval                  is null AND Kern.His_PersIDs.TsVerval                     is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_PersIDs (Pers                        , ActieAanpGel                , BSN                         , ANr                         , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_PersIDs.Pers                        , Kern.His_PersIDs.ActieAanpGel                , Kern.His_PersIDs.BSN                         , Kern.His_PersIDs.ANr                         , Kern.His_PersIDs.DatAanvGel                  , NEW.DatAanvGel                  , Kern.His_PersIDs.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersIDs
WHERE Kern.His_PersIDs.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_PersIDs (Pers                        , ActieAanpGel                , BSN                         , ANr                         , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_PersIDs.Pers                        , Kern.His_PersIDs.ActieAanpGel                , Kern.His_PersIDs.BSN                         , Kern.His_PersIDs.ANr                         , NEW.DatEindeGel                 , Kern.His_PersIDs.DatEindeGel                 , Kern.His_PersIDs.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersIDs
WHERE Kern.His_PersIDs.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_PersIDs$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_PersIDs RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_PersIDs

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_PersIDs();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_PersImmigratie() returns TRIGGER AS $formele_historie_Kern_His_PersImmigratie$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.ActieInh                     is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.ActieInh                     Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.TsReg                        = Kern.BRPActie.ID                          ;
IF NEW.ActieInh                     is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_PersImmigratie
WHERE NEW.Pers                         = Kern.His_PersImmigratie.Pers                         and NEW.ID                           <> Kern.His_PersImmigratie.ID                          
and NEW.DatAanvGel                   > Kern.His_PersImmigratie.DatAanvGel                   and NEW.DatAanvGel                   < Kern.His_PersImmigratie.DatEindeGel                 
AND Kern.His_PersImmigratie.ActieVerval                  IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_PersImmigratie WHERE NEW.Pers                         = Kern.His_PersImmigratie.Pers                         and NEW.ID                           <> Kern.His_PersImmigratie.ID                          
and NEW.DatEindeGel                  > Kern.His_PersImmigratie.DatAanvGel                   and NEW.DatEindeGel                  < Kern.His_PersImmigratie.DatEindeGel                  AND Kern.His_PersImmigratie.ActieVerval                  IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_PersImmigratie SET ActieVerval                  = NEW.ActieInh                    , TsVerval                     = NEW.TsReg                       

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Pers                         = Kern.His_PersImmigratie.Pers                         and NEW.ID                           <> Kern.His_PersImmigratie.ID                          
and Kern.His_PersImmigratie.DatAanvGel                   < NEW.DatEindeGel                  and Kern.His_PersImmigratie.DatEindeGel                  > NEW.DatAanvGel                  
AND Kern.His_PersImmigratie.ActieVerval                  is null AND Kern.His_PersImmigratie.TsVerval                     is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_PersImmigratie (Pers                        , ActieAanpGel                , LandVanwaarGevestigd        , DatVestigingInNederland     , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_PersImmigratie.Pers                        , Kern.His_PersImmigratie.ActieAanpGel                , Kern.His_PersImmigratie.LandVanwaarGevestigd        , Kern.His_PersImmigratie.DatVestigingInNederland     , Kern.His_PersImmigratie.DatAanvGel                  , NEW.DatAanvGel                  , Kern.His_PersImmigratie.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersImmigratie
WHERE Kern.His_PersImmigratie.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_PersImmigratie (Pers                        , ActieAanpGel                , LandVanwaarGevestigd        , DatVestigingInNederland     , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_PersImmigratie.Pers                        , Kern.His_PersImmigratie.ActieAanpGel                , Kern.His_PersImmigratie.LandVanwaarGevestigd        , Kern.His_PersImmigratie.DatVestigingInNederland     , NEW.DatEindeGel                 , Kern.His_PersImmigratie.DatEindeGel                 , Kern.His_PersImmigratie.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersImmigratie
WHERE Kern.His_PersImmigratie.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_PersImmigratie$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_PersImmigratie RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_PersImmigratie

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_PersImmigratie();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_PersInschr() returns TRIGGER AS $formele_historie_Kern_His_PersInschr$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.DatInschr                    is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.DatInschr                    Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.ActieInh                     = Kern.BRPActie.ID                          ;
IF NEW.DatInschr                    is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_PersInschr
WHERE NEW.Pers                         = Kern.His_PersInschr.Pers                         and NEW.ID                           <> Kern.His_PersInschr.ID                          
and NEW.TsReg                        > Kern.His_PersInschr.TsReg                        and NEW.TsReg                        < Kern.His_PersInschr.TsVerval                    
AND Kern.His_PersInschr.Versienr                     IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_PersInschr WHERE NEW.Pers                         = Kern.His_PersInschr.Pers                         and NEW.ID                           <> Kern.His_PersInschr.ID                          
and NEW.TsVerval                     > Kern.His_PersInschr.TsReg                        and NEW.TsVerval                     < Kern.His_PersInschr.TsVerval                     AND Kern.His_PersInschr.Versienr                     IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_PersInschr SET Versienr                     = NEW.DatInschr                   , ActieVerval                  = NEW.ActieInh                    

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Pers                         = Kern.His_PersInschr.Pers                         and NEW.ID                           <> Kern.His_PersInschr.ID                          
and Kern.His_PersInschr.TsReg                        < NEW.TsVerval                     and Kern.His_PersInschr.TsVerval                     > NEW.TsReg                       
AND Kern.His_PersInschr.Versienr                     is null AND Kern.His_PersInschr.ActieVerval                  is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_PersInschr (Pers                        , VorigePers                  , VolgendePers                , TsReg                        , TsVerval                    , ActieInh                    , DatInschr                   , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_PersInschr.Pers                        , Kern.His_PersInschr.VorigePers                  , Kern.His_PersInschr.VolgendePers                , Kern.His_PersInschr.TsReg                       , NEW.TsReg                       , Kern.His_PersInschr.ActieInh                    , NEW.DatInschr                   , NEW.ActieInh                     FROM Kern.His_PersInschr
WHERE Kern.His_PersInschr.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_PersInschr (Pers                        , VorigePers                  , VolgendePers                , TsReg                        , TsVerval                    , ActieInh                    , DatInschr                   , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_PersInschr.Pers                        , Kern.His_PersInschr.VorigePers                  , Kern.His_PersInschr.VolgendePers                , NEW.TsVerval                    , Kern.His_PersInschr.TsVerval                    , Kern.His_PersInschr.ActieInh                    , NEW.DatInschr                   , NEW.ActieInh                     FROM Kern.His_PersInschr
WHERE Kern.His_PersInschr.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_PersInschr$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_PersInschr RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_PersInschr

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_PersInschr();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_PersOpschorting() returns TRIGGER AS $formele_historie_Kern_His_PersOpschorting$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.ActieInh                     is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.ActieInh                     Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.TsReg                        = Kern.BRPActie.ID                          ;
IF NEW.ActieInh                     is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_PersOpschorting
WHERE NEW.Pers                         = Kern.His_PersOpschorting.Pers                         and NEW.ID                           <> Kern.His_PersOpschorting.ID                          
and NEW.DatAanvGel                   > Kern.His_PersOpschorting.DatAanvGel                   and NEW.DatAanvGel                   < Kern.His_PersOpschorting.DatEindeGel                 
AND Kern.His_PersOpschorting.ActieVerval                  IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_PersOpschorting WHERE NEW.Pers                         = Kern.His_PersOpschorting.Pers                         and NEW.ID                           <> Kern.His_PersOpschorting.ID                          
and NEW.DatEindeGel                  > Kern.His_PersOpschorting.DatAanvGel                   and NEW.DatEindeGel                  < Kern.His_PersOpschorting.DatEindeGel                  AND Kern.His_PersOpschorting.ActieVerval                  IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_PersOpschorting SET ActieVerval                  = NEW.ActieInh                    , TsVerval                     = NEW.TsReg                       

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Pers                         = Kern.His_PersOpschorting.Pers                         and NEW.ID                           <> Kern.His_PersOpschorting.ID                          
and Kern.His_PersOpschorting.DatAanvGel                   < NEW.DatEindeGel                  and Kern.His_PersOpschorting.DatEindeGel                  > NEW.DatAanvGel                  
AND Kern.His_PersOpschorting.ActieVerval                  is null AND Kern.His_PersOpschorting.TsVerval                     is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_PersOpschorting (Pers                        , ActieAanpGel                , RdnOpschortingBijhouding    , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_PersOpschorting.Pers                        , Kern.His_PersOpschorting.ActieAanpGel                , Kern.His_PersOpschorting.RdnOpschortingBijhouding    , Kern.His_PersOpschorting.DatAanvGel                  , NEW.DatAanvGel                  , Kern.His_PersOpschorting.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersOpschorting
WHERE Kern.His_PersOpschorting.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_PersOpschorting (Pers                        , ActieAanpGel                , RdnOpschortingBijhouding    , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_PersOpschorting.Pers                        , Kern.His_PersOpschorting.ActieAanpGel                , Kern.His_PersOpschorting.RdnOpschortingBijhouding    , NEW.DatEindeGel                 , Kern.His_PersOpschorting.DatEindeGel                 , Kern.His_PersOpschorting.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersOpschorting
WHERE Kern.His_PersOpschorting.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_PersOpschorting$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_PersOpschorting RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_PersOpschorting

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_PersOpschorting();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_PersOverlijden() returns TRIGGER AS $formele_historie_Kern_His_PersOverlijden$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.DatOverlijden                is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.DatOverlijden                Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.ActieInh                     = Kern.BRPActie.ID                          ;
IF NEW.DatOverlijden                is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_PersOverlijden
WHERE NEW.Pers                         = Kern.His_PersOverlijden.Pers                         and NEW.ID                           <> Kern.His_PersOverlijden.ID                          
and NEW.TsReg                        > Kern.His_PersOverlijden.TsReg                        and NEW.TsReg                        < Kern.His_PersOverlijden.TsVerval                    
AND Kern.His_PersOverlijden.GemOverlijden                IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_PersOverlijden WHERE NEW.Pers                         = Kern.His_PersOverlijden.Pers                         and NEW.ID                           <> Kern.His_PersOverlijden.ID                          
and NEW.TsVerval                     > Kern.His_PersOverlijden.TsReg                        and NEW.TsVerval                     < Kern.His_PersOverlijden.TsVerval                     AND Kern.His_PersOverlijden.GemOverlijden                IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_PersOverlijden SET GemOverlijden                = NEW.DatOverlijden               , ActieVerval                  = NEW.ActieInh                    

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Pers                         = Kern.His_PersOverlijden.Pers                         and NEW.ID                           <> Kern.His_PersOverlijden.ID                          
and Kern.His_PersOverlijden.TsReg                        < NEW.TsVerval                     and Kern.His_PersOverlijden.TsVerval                     > NEW.TsReg                       
AND Kern.His_PersOverlijden.GemOverlijden                is null AND Kern.His_PersOverlijden.ActieVerval                  is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_PersOverlijden (Pers                        , WplOverlijden               , BLPlaatsOverlijden          , BLRegioOverlijden           , LandOverlijden              , OmsLocOverlijden            , TsReg                        , TsVerval                    , ActieInh                    , DatOverlijden               , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_PersOverlijden.Pers                        , Kern.His_PersOverlijden.WplOverlijden               , Kern.His_PersOverlijden.BLPlaatsOverlijden          , Kern.His_PersOverlijden.BLRegioOverlijden           , Kern.His_PersOverlijden.LandOverlijden              , Kern.His_PersOverlijden.OmsLocOverlijden            , Kern.His_PersOverlijden.TsReg                       , NEW.TsReg                       , Kern.His_PersOverlijden.ActieInh                    , NEW.DatOverlijden               , NEW.ActieInh                     FROM Kern.His_PersOverlijden
WHERE Kern.His_PersOverlijden.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_PersOverlijden (Pers                        , WplOverlijden               , BLPlaatsOverlijden          , BLRegioOverlijden           , LandOverlijden              , OmsLocOverlijden            , TsReg                        , TsVerval                    , ActieInh                    , DatOverlijden               , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_PersOverlijden.Pers                        , Kern.His_PersOverlijden.WplOverlijden               , Kern.His_PersOverlijden.BLPlaatsOverlijden          , Kern.His_PersOverlijden.BLRegioOverlijden           , Kern.His_PersOverlijden.LandOverlijden              , Kern.His_PersOverlijden.OmsLocOverlijden            , NEW.TsVerval                    , Kern.His_PersOverlijden.TsVerval                    , Kern.His_PersOverlijden.ActieInh                    , NEW.DatOverlijden               , NEW.ActieInh                     FROM Kern.His_PersOverlijden
WHERE Kern.His_PersOverlijden.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_PersOverlijden$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_PersOverlijden RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_PersOverlijden

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_PersOverlijden();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_PersPK() returns TRIGGER AS $formele_historie_Kern_His_PersPK$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.DatOverlijden                is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.DatOverlijden                Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.ActieInh                     = Kern.BRPActie.ID                          ;
IF NEW.DatOverlijden                is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_PersPK
WHERE NEW.Pers                         = Kern.His_PersPK.Pers                         and NEW.ID                           <> Kern.His_PersPK.ID                          
and NEW.TsReg                        > Kern.His_PersPK.TsReg                        and NEW.TsReg                        < Kern.His_PersPK.TsVerval                    
AND Kern.His_PersPK.GemOverlijden                IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_PersPK WHERE NEW.Pers                         = Kern.His_PersPK.Pers                         and NEW.ID                           <> Kern.His_PersPK.ID                          
and NEW.TsVerval                     > Kern.His_PersPK.TsReg                        and NEW.TsVerval                     < Kern.His_PersPK.TsVerval                     AND Kern.His_PersPK.GemOverlijden                IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_PersPK SET GemOverlijden                = NEW.DatOverlijden               , ActieVerval                  = NEW.ActieInh                    

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Pers                         = Kern.His_PersPK.Pers                         and NEW.ID                           <> Kern.His_PersPK.ID                          
and Kern.His_PersPK.TsReg                        < NEW.TsVerval                     and Kern.His_PersPK.TsVerval                     > NEW.TsReg                       
AND Kern.His_PersPK.GemOverlijden                is null AND Kern.His_PersPK.ActieVerval                  is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_PersPK (Pers                        , WplOverlijden               , BLPlaatsOverlijden          , BLRegioOverlijden           , LandOverlijden              , OmsLocOverlijden            , TsReg                        , TsVerval                    , ActieInh                    , DatOverlijden               , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_PersPK.Pers                        , Kern.His_PersOverlijden.WplOverlijden               , Kern.His_PersOverlijden.BLPlaatsOverlijden          , Kern.His_PersOverlijden.BLRegioOverlijden           , Kern.His_PersOverlijden.LandOverlijden              , Kern.His_PersOverlijden.OmsLocOverlijden            , Kern.His_PersPK.TsReg                       , NEW.TsReg                       , Kern.His_PersPK.ActieInh                    , NEW.DatOverlijden               , NEW.ActieInh                     FROM Kern.His_PersPK
WHERE Kern.His_PersPK.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_PersPK (Pers                        , WplOverlijden               , BLPlaatsOverlijden          , BLRegioOverlijden           , LandOverlijden              , OmsLocOverlijden            , TsReg                        , TsVerval                    , ActieInh                    , DatOverlijden               , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_PersPK.Pers                        , Kern.His_PersOverlijden.WplOverlijden               , Kern.His_PersOverlijden.BLPlaatsOverlijden          , Kern.His_PersOverlijden.BLRegioOverlijden           , Kern.His_PersOverlijden.LandOverlijden              , Kern.His_PersOverlijden.OmsLocOverlijden            , NEW.TsVerval                    , Kern.His_PersPK.TsVerval                    , Kern.His_PersPK.ActieInh                    , NEW.DatOverlijden               , NEW.ActieInh                     FROM Kern.His_PersPK
WHERE Kern.His_PersPK.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_PersPK$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_PersPK RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_PersPK

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_PersPK();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_PersSamengesteldeNaam() returns TRIGGER AS $formele_historie_Kern_His_PersSamengesteldeNaam$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.ActieInh                     is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.ActieInh                     Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.TsReg                        = Kern.BRPActie.ID                          ;
IF NEW.ActieInh                     is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_PersSamengesteldeNaam
WHERE NEW.Pers                         = Kern.His_PersSamengesteldeNaam.Pers                         and NEW.ID                           <> Kern.His_PersSamengesteldeNaam.ID                          
and NEW.DatAanvGel                   > Kern.His_PersSamengesteldeNaam.DatAanvGel                   and NEW.DatAanvGel                   < Kern.His_PersSamengesteldeNaam.DatEindeGel                 
AND Kern.His_PersSamengesteldeNaam.ActieVerval                  IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_PersSamengesteldeNaam WHERE NEW.Pers                         = Kern.His_PersSamengesteldeNaam.Pers                         and NEW.ID                           <> Kern.His_PersSamengesteldeNaam.ID                          
and NEW.DatEindeGel                  > Kern.His_PersSamengesteldeNaam.DatAanvGel                   and NEW.DatEindeGel                  < Kern.His_PersSamengesteldeNaam.DatEindeGel                  AND Kern.His_PersSamengesteldeNaam.ActieVerval                  IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_PersSamengesteldeNaam SET ActieVerval                  = NEW.ActieInh                    , TsVerval                     = NEW.TsReg                       

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Pers                         = Kern.His_PersSamengesteldeNaam.Pers                         and NEW.ID                           <> Kern.His_PersSamengesteldeNaam.ID                          
and Kern.His_PersSamengesteldeNaam.DatAanvGel                   < NEW.DatEindeGel                  and Kern.His_PersSamengesteldeNaam.DatEindeGel                  > NEW.DatAanvGel                  
AND Kern.His_PersSamengesteldeNaam.ActieVerval                  is null AND Kern.His_PersSamengesteldeNaam.TsVerval                     is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_PersSamengesteldeNaam (Pers                        , ActieAanpGel                , Predikaat                   , Voornamen                   , Voorvoegsel                 , Scheidingsteken             , AdellijkeTitel              , Geslnaam                    , IndNreeksAlsGeslnaam        , IndAlgoritmischAfgeleid     , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_PersSamengesteldeNaam.Pers                        , Kern.His_PersSamengesteldeNaam.ActieAanpGel                , Kern.His_PersSamengesteldeNaam.Predikaat                   , Kern.His_PersSamengesteldeNaam.Voornamen                   , Kern.His_PersSamengesteldeNaam.Voorvoegsel                 , Kern.His_PersSamengesteldeNaam.Scheidingsteken             , Kern.His_PersSamengesteldeNaam.AdellijkeTitel              , Kern.His_PersSamengesteldeNaam.Geslnaam                    , Kern.His_PersSamengesteldeNaam.IndNreeksAlsGeslnaam        , Kern.His_PersSamengesteldeNaam.IndAlgoritmischAfgeleid     , Kern.His_PersSamengesteldeNaam.DatAanvGel                  , NEW.DatAanvGel                  , Kern.His_PersSamengesteldeNaam.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersSamengesteldeNaam
WHERE Kern.His_PersSamengesteldeNaam.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_PersSamengesteldeNaam (Pers                        , ActieAanpGel                , Predikaat                   , Voornamen                   , Voorvoegsel                 , Scheidingsteken             , AdellijkeTitel              , Geslnaam                    , IndNreeksAlsGeslnaam        , IndAlgoritmischAfgeleid     , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_PersSamengesteldeNaam.Pers                        , Kern.His_PersSamengesteldeNaam.ActieAanpGel                , Kern.His_PersSamengesteldeNaam.Predikaat                   , Kern.His_PersSamengesteldeNaam.Voornamen                   , Kern.His_PersSamengesteldeNaam.Voorvoegsel                 , Kern.His_PersSamengesteldeNaam.Scheidingsteken             , Kern.His_PersSamengesteldeNaam.AdellijkeTitel              , Kern.His_PersSamengesteldeNaam.Geslnaam                    , Kern.His_PersSamengesteldeNaam.IndNreeksAlsGeslnaam        , Kern.His_PersSamengesteldeNaam.IndAlgoritmischAfgeleid     , NEW.DatEindeGel                 , Kern.His_PersSamengesteldeNaam.DatEindeGel                 , Kern.His_PersSamengesteldeNaam.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersSamengesteldeNaam
WHERE Kern.His_PersSamengesteldeNaam.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_PersSamengesteldeNaam$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_PersSamengesteldeNaam RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_PersSamengesteldeNaam

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_PersSamengesteldeNaam();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_PersUitslNLKiesr() returns TRIGGER AS $formele_historie_Kern_His_PersUitslNLKiesr$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.ActieInh                     is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.ActieInh                     Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.TsReg                        = Kern.BRPActie.ID                          ;
IF NEW.ActieInh                     is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_PersUitslNLKiesr
WHERE NEW.Pers                         = Kern.His_PersUitslNLKiesr.Pers                         and NEW.ID                           <> Kern.His_PersUitslNLKiesr.ID                          
and NEW.DatAanvGel                   > Kern.His_PersUitslNLKiesr.DatAanvGel                   and NEW.DatAanvGel                   < Kern.His_PersUitslNLKiesr.DatEindeGel                 
AND Kern.His_PersUitslNLKiesr.ActieVerval                  IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_PersUitslNLKiesr WHERE NEW.Pers                         = Kern.His_PersUitslNLKiesr.Pers                         and NEW.ID                           <> Kern.His_PersUitslNLKiesr.ID                          
and NEW.DatEindeGel                  > Kern.His_PersUitslNLKiesr.DatAanvGel                   and NEW.DatEindeGel                  < Kern.His_PersUitslNLKiesr.DatEindeGel                  AND Kern.His_PersUitslNLKiesr.ActieVerval                  IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_PersUitslNLKiesr SET ActieVerval                  = NEW.ActieInh                    , TsVerval                     = NEW.TsReg                       

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Pers                         = Kern.His_PersUitslNLKiesr.Pers                         and NEW.ID                           <> Kern.His_PersUitslNLKiesr.ID                          
and Kern.His_PersUitslNLKiesr.DatAanvGel                   < NEW.DatEindeGel                  and Kern.His_PersUitslNLKiesr.DatEindeGel                  > NEW.DatAanvGel                  
AND Kern.His_PersUitslNLKiesr.ActieVerval                  is null AND Kern.His_PersUitslNLKiesr.TsVerval                     is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_PersUitslNLKiesr (Pers                        , ActieAanpGel                , Predikaat                   , Voornamen                   , Voorvoegsel                 , Scheidingsteken             , AdellijkeTitel              , Geslnaam                    , IndNreeksAlsGeslnaam        , IndAlgoritmischAfgeleid     , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_PersUitslNLKiesr.Pers                        , Kern.His_PersSamengesteldeNaam.ActieAanpGel                , Kern.His_PersSamengesteldeNaam.Predikaat                   , Kern.His_PersSamengesteldeNaam.Voornamen                   , Kern.His_PersSamengesteldeNaam.Voorvoegsel                 , Kern.His_PersSamengesteldeNaam.Scheidingsteken             , Kern.His_PersSamengesteldeNaam.AdellijkeTitel              , Kern.His_PersSamengesteldeNaam.Geslnaam                    , Kern.His_PersSamengesteldeNaam.IndNreeksAlsGeslnaam        , Kern.His_PersSamengesteldeNaam.IndAlgoritmischAfgeleid     , Kern.His_PersUitslNLKiesr.DatAanvGel                  , NEW.DatAanvGel                  , Kern.His_PersUitslNLKiesr.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersUitslNLKiesr
WHERE Kern.His_PersUitslNLKiesr.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_PersUitslNLKiesr (Pers                        , ActieAanpGel                , Predikaat                   , Voornamen                   , Voorvoegsel                 , Scheidingsteken             , AdellijkeTitel              , Geslnaam                    , IndNreeksAlsGeslnaam        , IndAlgoritmischAfgeleid     , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_PersUitslNLKiesr.Pers                        , Kern.His_PersSamengesteldeNaam.ActieAanpGel                , Kern.His_PersSamengesteldeNaam.Predikaat                   , Kern.His_PersSamengesteldeNaam.Voornamen                   , Kern.His_PersSamengesteldeNaam.Voorvoegsel                 , Kern.His_PersSamengesteldeNaam.Scheidingsteken             , Kern.His_PersSamengesteldeNaam.AdellijkeTitel              , Kern.His_PersSamengesteldeNaam.Geslnaam                    , Kern.His_PersSamengesteldeNaam.IndNreeksAlsGeslnaam        , Kern.His_PersSamengesteldeNaam.IndAlgoritmischAfgeleid     , NEW.DatEindeGel                 , Kern.His_PersUitslNLKiesr.DatEindeGel                 , Kern.His_PersUitslNLKiesr.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersUitslNLKiesr
WHERE Kern.His_PersUitslNLKiesr.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_PersUitslNLKiesr$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_PersUitslNLKiesr RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_PersUitslNLKiesr

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_PersUitslNLKiesr();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_PersVerblijfsr() returns TRIGGER AS $formele_historie_Kern_His_PersVerblijfsr$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.ActieInh                     is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.ActieInh                     Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.TsReg                        = Kern.BRPActie.ID                          ;
IF NEW.ActieInh                     is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_PersVerblijfsr
WHERE NEW.Pers                         = Kern.His_PersVerblijfsr.Pers                         and NEW.ID                           <> Kern.His_PersVerblijfsr.ID                          
and NEW.DatAanvGel                   > Kern.His_PersVerblijfsr.DatAanvGel                   and NEW.DatAanvGel                   < Kern.His_PersVerblijfsr.DatEindeGel                 
AND Kern.His_PersVerblijfsr.ActieVerval                  IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_PersVerblijfsr WHERE NEW.Pers                         = Kern.His_PersVerblijfsr.Pers                         and NEW.ID                           <> Kern.His_PersVerblijfsr.ID                          
and NEW.DatEindeGel                  > Kern.His_PersVerblijfsr.DatAanvGel                   and NEW.DatEindeGel                  < Kern.His_PersVerblijfsr.DatEindeGel                  AND Kern.His_PersVerblijfsr.ActieVerval                  IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_PersVerblijfsr SET ActieVerval                  = NEW.ActieInh                    , TsVerval                     = NEW.TsReg                       

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Pers                         = Kern.His_PersVerblijfsr.Pers                         and NEW.ID                           <> Kern.His_PersVerblijfsr.ID                          
and Kern.His_PersVerblijfsr.DatAanvGel                   < NEW.DatEindeGel                  and Kern.His_PersVerblijfsr.DatEindeGel                  > NEW.DatAanvGel                  
AND Kern.His_PersVerblijfsr.ActieVerval                  is null AND Kern.His_PersVerblijfsr.TsVerval                     is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_PersVerblijfsr (Pers                        , ActieAanpGel                , Verblijfsr                  , DatAanvVerblijfsr           , DatVoorzEindeVerblijfsr     , DatAanvAaneenslVerblijfsr   , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_PersVerblijfsr.Pers                        , Kern.His_PersVerblijfsr.ActieAanpGel                , Kern.His_PersVerblijfsr.Verblijfsr                  , Kern.His_PersVerblijfsr.DatAanvVerblijfsr           , Kern.His_PersVerblijfsr.DatVoorzEindeVerblijfsr     , Kern.His_PersVerblijfsr.DatAanvAaneenslVerblijfsr   , Kern.His_PersVerblijfsr.DatAanvGel                  , NEW.DatAanvGel                  , Kern.His_PersVerblijfsr.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersVerblijfsr
WHERE Kern.His_PersVerblijfsr.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_PersVerblijfsr (Pers                        , ActieAanpGel                , Verblijfsr                  , DatAanvVerblijfsr           , DatVoorzEindeVerblijfsr     , DatAanvAaneenslVerblijfsr   , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_PersVerblijfsr.Pers                        , Kern.His_PersVerblijfsr.ActieAanpGel                , Kern.His_PersVerblijfsr.Verblijfsr                  , Kern.His_PersVerblijfsr.DatAanvVerblijfsr           , Kern.His_PersVerblijfsr.DatVoorzEindeVerblijfsr     , Kern.His_PersVerblijfsr.DatAanvAaneenslVerblijfsr   , NEW.DatEindeGel                 , Kern.His_PersVerblijfsr.DatEindeGel                 , Kern.His_PersVerblijfsr.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersVerblijfsr
WHERE Kern.His_PersVerblijfsr.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_PersVerblijfsr$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_PersVerblijfsr RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_PersVerblijfsr

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_PersVerblijfsr();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_PersAdres() returns TRIGGER AS $formele_historie_Kern_His_PersAdres$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.ActieInh                     is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.ActieInh                     Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.TsReg                        = Kern.BRPActie.ID                          ;
IF NEW.ActieInh                     is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_PersAdres
WHERE NEW.PersAdres                    = Kern.His_PersAdres.PersAdres                    and NEW.ID                           <> Kern.His_PersAdres.ID                          
and NEW.DatAanvGel                   > Kern.His_PersAdres.DatAanvGel                   and NEW.DatAanvGel                   < Kern.His_PersAdres.DatEindeGel                 
AND Kern.His_PersAdres.ActieVerval                  IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_PersAdres WHERE NEW.PersAdres                    = Kern.His_PersAdres.PersAdres                    and NEW.ID                           <> Kern.His_PersAdres.ID                          
and NEW.DatEindeGel                  > Kern.His_PersAdres.DatAanvGel                   and NEW.DatEindeGel                  < Kern.His_PersAdres.DatEindeGel                  AND Kern.His_PersAdres.ActieVerval                  IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_PersAdres SET ActieVerval                  = NEW.ActieInh                    , TsVerval                     = NEW.TsReg                       

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.PersAdres                    = Kern.His_PersAdres.PersAdres                    and NEW.ID                           <> Kern.His_PersAdres.ID                          
and Kern.His_PersAdres.DatAanvGel                   < NEW.DatEindeGel                  and Kern.His_PersAdres.DatEindeGel                  > NEW.DatAanvGel                  
AND Kern.His_PersAdres.ActieVerval                  is null AND Kern.His_PersAdres.TsVerval                     is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_PersAdres (PersAdres                   , ActieAanpGel                , Srt                         , RdnWijz                     , AangAdresh                  , DatAanvAdresh               , AdresseerbaarObject         , IdentcodeNraand             , Gem                         , NOR                         , AfgekorteNOR                , Gemdeel                     , Huisnr                      , Huisletter                  , Huisnrtoevoeging            , Postcode                    , Wpl                         , LoctovAdres                 , LocOms                      , BLAdresRegel1               , BLAdresRegel2               , BLAdresRegel3               , BLAdresRegel4               , BLAdresRegel5               , BLAdresRegel6               , Land                        , DatVertrekUitNederland      , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_PersAdres.PersAdres                   , Kern.His_PersAdres.ActieAanpGel                , Kern.His_PersAdres.Srt                         , Kern.His_PersAdres.RdnWijz                     , Kern.His_PersAdres.AangAdresh                  , Kern.His_PersAdres.DatAanvAdresh               , Kern.His_PersAdres.AdresseerbaarObject         , Kern.His_PersAdres.IdentcodeNraand             , Kern.His_PersAdres.Gem                         , Kern.His_PersAdres.NOR                         , Kern.His_PersAdres.AfgekorteNOR                , Kern.His_PersAdres.Gemdeel                     , Kern.His_PersAdres.Huisnr                      , Kern.His_PersAdres.Huisletter                  , Kern.His_PersAdres.Huisnrtoevoeging            , Kern.His_PersAdres.Postcode                    , Kern.His_PersAdres.Wpl                         , Kern.His_PersAdres.LoctovAdres                 , Kern.His_PersAdres.LocOms                      , Kern.His_PersAdres.BLAdresRegel1               , Kern.His_PersAdres.BLAdresRegel2               , Kern.His_PersAdres.BLAdresRegel3               , Kern.His_PersAdres.BLAdresRegel4               , Kern.His_PersAdres.BLAdresRegel5               , Kern.His_PersAdres.BLAdresRegel6               , Kern.His_PersAdres.Land                        , Kern.His_PersAdres.DatVertrekUitNederland      , Kern.His_PersAdres.DatAanvGel                  , NEW.DatAanvGel                  , Kern.His_PersAdres.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersAdres
WHERE Kern.His_PersAdres.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_PersAdres (PersAdres                   , ActieAanpGel                , Srt                         , RdnWijz                     , AangAdresh                  , DatAanvAdresh               , AdresseerbaarObject         , IdentcodeNraand             , Gem                         , NOR                         , AfgekorteNOR                , Gemdeel                     , Huisnr                      , Huisletter                  , Huisnrtoevoeging            , Postcode                    , Wpl                         , LoctovAdres                 , LocOms                      , BLAdresRegel1               , BLAdresRegel2               , BLAdresRegel3               , BLAdresRegel4               , BLAdresRegel5               , BLAdresRegel6               , Land                        , DatVertrekUitNederland      , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_PersAdres.PersAdres                   , Kern.His_PersAdres.ActieAanpGel                , Kern.His_PersAdres.Srt                         , Kern.His_PersAdres.RdnWijz                     , Kern.His_PersAdres.AangAdresh                  , Kern.His_PersAdres.DatAanvAdresh               , Kern.His_PersAdres.AdresseerbaarObject         , Kern.His_PersAdres.IdentcodeNraand             , Kern.His_PersAdres.Gem                         , Kern.His_PersAdres.NOR                         , Kern.His_PersAdres.AfgekorteNOR                , Kern.His_PersAdres.Gemdeel                     , Kern.His_PersAdres.Huisnr                      , Kern.His_PersAdres.Huisletter                  , Kern.His_PersAdres.Huisnrtoevoeging            , Kern.His_PersAdres.Postcode                    , Kern.His_PersAdres.Wpl                         , Kern.His_PersAdres.LoctovAdres                 , Kern.His_PersAdres.LocOms                      , Kern.His_PersAdres.BLAdresRegel1               , Kern.His_PersAdres.BLAdresRegel2               , Kern.His_PersAdres.BLAdresRegel3               , Kern.His_PersAdres.BLAdresRegel4               , Kern.His_PersAdres.BLAdresRegel5               , Kern.His_PersAdres.BLAdresRegel6               , Kern.His_PersAdres.Land                        , Kern.His_PersAdres.DatVertrekUitNederland      , NEW.DatEindeGel                 , Kern.His_PersAdres.DatEindeGel                 , Kern.His_PersAdres.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersAdres
WHERE Kern.His_PersAdres.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_PersAdres$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_PersAdres RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_PersAdres

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_PersAdres();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_PersGeslnaamcomp() returns TRIGGER AS $formele_historie_Kern_His_PersGeslnaamcomp$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.ActieInh                     is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.ActieInh                     Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.TsReg                        = Kern.BRPActie.ID                          ;
IF NEW.ActieInh                     is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_PersGeslnaamcomp
WHERE NEW.PersGeslnaamcomp             = Kern.His_PersGeslnaamcomp.PersGeslnaamcomp             and NEW.ID                           <> Kern.His_PersGeslnaamcomp.ID                          
and NEW.DatAanvGel                   > Kern.His_PersGeslnaamcomp.DatAanvGel                   and NEW.DatAanvGel                   < Kern.His_PersGeslnaamcomp.DatEindeGel                 
AND Kern.His_PersGeslnaamcomp.ActieVerval                  IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_PersGeslnaamcomp WHERE NEW.PersGeslnaamcomp             = Kern.His_PersGeslnaamcomp.PersGeslnaamcomp             and NEW.ID                           <> Kern.His_PersGeslnaamcomp.ID                          
and NEW.DatEindeGel                  > Kern.His_PersGeslnaamcomp.DatAanvGel                   and NEW.DatEindeGel                  < Kern.His_PersGeslnaamcomp.DatEindeGel                  AND Kern.His_PersGeslnaamcomp.ActieVerval                  IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_PersGeslnaamcomp SET ActieVerval                  = NEW.ActieInh                    , TsVerval                     = NEW.TsReg                       

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.PersGeslnaamcomp             = Kern.His_PersGeslnaamcomp.PersGeslnaamcomp             and NEW.ID                           <> Kern.His_PersGeslnaamcomp.ID                          
and Kern.His_PersGeslnaamcomp.DatAanvGel                   < NEW.DatEindeGel                  and Kern.His_PersGeslnaamcomp.DatEindeGel                  > NEW.DatAanvGel                  
AND Kern.His_PersGeslnaamcomp.ActieVerval                  is null AND Kern.His_PersGeslnaamcomp.TsVerval                     is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_PersGeslnaamcomp (PersGeslnaamcomp            , ActieAanpGel                , Voorvoegsel                 , Scheidingsteken             , Naam                        , Predikaat                   , AdellijkeTitel              , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_PersGeslnaamcomp.PersGeslnaamcomp            , Kern.His_PersGeslnaamcomp.ActieAanpGel                , Kern.His_PersGeslnaamcomp.Voorvoegsel                 , Kern.His_PersGeslnaamcomp.Scheidingsteken             , Kern.His_PersGeslnaamcomp.Naam                        , Kern.His_PersGeslnaamcomp.Predikaat                   , Kern.His_PersGeslnaamcomp.AdellijkeTitel              , Kern.His_PersGeslnaamcomp.DatAanvGel                  , NEW.DatAanvGel                  , Kern.His_PersGeslnaamcomp.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersGeslnaamcomp
WHERE Kern.His_PersGeslnaamcomp.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_PersGeslnaamcomp (PersGeslnaamcomp            , ActieAanpGel                , Voorvoegsel                 , Scheidingsteken             , Naam                        , Predikaat                   , AdellijkeTitel              , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_PersGeslnaamcomp.PersGeslnaamcomp            , Kern.His_PersGeslnaamcomp.ActieAanpGel                , Kern.His_PersGeslnaamcomp.Voorvoegsel                 , Kern.His_PersGeslnaamcomp.Scheidingsteken             , Kern.His_PersGeslnaamcomp.Naam                        , Kern.His_PersGeslnaamcomp.Predikaat                   , Kern.His_PersGeslnaamcomp.AdellijkeTitel              , NEW.DatEindeGel                 , Kern.His_PersGeslnaamcomp.DatEindeGel                 , Kern.His_PersGeslnaamcomp.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersGeslnaamcomp
WHERE Kern.His_PersGeslnaamcomp.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_PersGeslnaamcomp$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_PersGeslnaamcomp RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_PersGeslnaamcomp

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_PersGeslnaamcomp();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_PersIndicatie() returns TRIGGER AS $formele_historie_Kern_His_PersIndicatie$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.ActieInh                     is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.ActieInh                     Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.TsReg                        = Kern.BRPActie.ID                          ;
IF NEW.ActieInh                     is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_PersIndicatie
WHERE NEW.PersIndicatie                = Kern.His_PersIndicatie.PersIndicatie                and NEW.ID                           <> Kern.His_PersIndicatie.ID                          
and NEW.DatAanvGel                   > Kern.His_PersIndicatie.DatAanvGel                   and NEW.DatAanvGel                   < Kern.His_PersIndicatie.DatEindeGel                 
AND Kern.His_PersIndicatie.ActieVerval                  IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_PersIndicatie WHERE NEW.PersIndicatie                = Kern.His_PersIndicatie.PersIndicatie                and NEW.ID                           <> Kern.His_PersIndicatie.ID                          
and NEW.DatEindeGel                  > Kern.His_PersIndicatie.DatAanvGel                   and NEW.DatEindeGel                  < Kern.His_PersIndicatie.DatEindeGel                  AND Kern.His_PersIndicatie.ActieVerval                  IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_PersIndicatie SET ActieVerval                  = NEW.ActieInh                    , TsVerval                     = NEW.TsReg                       

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.PersIndicatie                = Kern.His_PersIndicatie.PersIndicatie                and NEW.ID                           <> Kern.His_PersIndicatie.ID                          
and Kern.His_PersIndicatie.DatAanvGel                   < NEW.DatEindeGel                  and Kern.His_PersIndicatie.DatEindeGel                  > NEW.DatAanvGel                  
AND Kern.His_PersIndicatie.ActieVerval                  is null AND Kern.His_PersIndicatie.TsVerval                     is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_PersIndicatie (PersIndicatie               , ActieAanpGel                , Waarde                      , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_PersIndicatie.PersIndicatie               , Kern.His_PersIndicatie.ActieAanpGel                , Kern.His_PersIndicatie.Waarde                      , Kern.His_PersIndicatie.DatAanvGel                  , NEW.DatAanvGel                  , Kern.His_PersIndicatie.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersIndicatie
WHERE Kern.His_PersIndicatie.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_PersIndicatie (PersIndicatie               , ActieAanpGel                , Waarde                      , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_PersIndicatie.PersIndicatie               , Kern.His_PersIndicatie.ActieAanpGel                , Kern.His_PersIndicatie.Waarde                      , NEW.DatEindeGel                 , Kern.His_PersIndicatie.DatEindeGel                 , Kern.His_PersIndicatie.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersIndicatie
WHERE Kern.His_PersIndicatie.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_PersIndicatie$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_PersIndicatie RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_PersIndicatie

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_PersIndicatie();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_PersNation() returns TRIGGER AS $formele_historie_Kern_His_PersNation$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.ActieInh                     is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.ActieInh                     Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.TsReg                        = Kern.BRPActie.ID                          ;
IF NEW.ActieInh                     is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_PersNation
WHERE NEW.PersNation                   = Kern.His_PersNation.PersNation                   and NEW.ID                           <> Kern.His_PersNation.ID                          
and NEW.DatAanvGel                   > Kern.His_PersNation.DatAanvGel                   and NEW.DatAanvGel                   < Kern.His_PersNation.DatEindeGel                 
AND Kern.His_PersNation.ActieVerval                  IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_PersNation WHERE NEW.PersNation                   = Kern.His_PersNation.PersNation                   and NEW.ID                           <> Kern.His_PersNation.ID                          
and NEW.DatEindeGel                  > Kern.His_PersNation.DatAanvGel                   and NEW.DatEindeGel                  < Kern.His_PersNation.DatEindeGel                  AND Kern.His_PersNation.ActieVerval                  IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_PersNation SET ActieVerval                  = NEW.ActieInh                    , TsVerval                     = NEW.TsReg                       

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.PersNation                   = Kern.His_PersNation.PersNation                   and NEW.ID                           <> Kern.His_PersNation.ID                          
and Kern.His_PersNation.DatAanvGel                   < NEW.DatEindeGel                  and Kern.His_PersNation.DatEindeGel                  > NEW.DatAanvGel                  
AND Kern.His_PersNation.ActieVerval                  is null AND Kern.His_PersNation.TsVerval                     is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_PersNation (PersNation                  , ActieAanpGel                , RdnVerlies                  , RdnVerk                     , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_PersNation.PersNation                  , Kern.His_PersNation.ActieAanpGel                , Kern.His_PersNation.RdnVerlies                  , Kern.His_PersNation.RdnVerk                     , Kern.His_PersNation.DatAanvGel                  , NEW.DatAanvGel                  , Kern.His_PersNation.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersNation
WHERE Kern.His_PersNation.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_PersNation (PersNation                  , ActieAanpGel                , RdnVerlies                  , RdnVerk                     , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_PersNation.PersNation                  , Kern.His_PersNation.ActieAanpGel                , Kern.His_PersNation.RdnVerlies                  , Kern.His_PersNation.RdnVerk                     , NEW.DatEindeGel                 , Kern.His_PersNation.DatEindeGel                 , Kern.His_PersNation.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersNation
WHERE Kern.His_PersNation.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_PersNation$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_PersNation RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_PersNation

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_PersNation();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_PersReisdoc() returns TRIGGER AS $formele_historie_Kern_His_PersReisdoc$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.ActieInh                     is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.ActieInh                     Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.TsReg                        = Kern.BRPActie.ID                          ;
IF NEW.ActieInh                     is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_PersReisdoc
WHERE NEW.PersReisdoc                  = Kern.His_PersReisdoc.PersReisdoc                  and NEW.ID                           <> Kern.His_PersReisdoc.ID                          
and NEW.DatAanvGel                   > Kern.His_PersReisdoc.DatAanvGel                   and NEW.DatAanvGel                   < Kern.His_PersReisdoc.DatEindeGel                 
AND Kern.His_PersReisdoc.ActieVerval                  IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_PersReisdoc WHERE NEW.PersReisdoc                  = Kern.His_PersReisdoc.PersReisdoc                  and NEW.ID                           <> Kern.His_PersReisdoc.ID                          
and NEW.DatEindeGel                  > Kern.His_PersReisdoc.DatAanvGel                   and NEW.DatEindeGel                  < Kern.His_PersReisdoc.DatEindeGel                  AND Kern.His_PersReisdoc.ActieVerval                  IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_PersReisdoc SET ActieVerval                  = NEW.ActieInh                    , TsVerval                     = NEW.TsReg                       

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.PersReisdoc                  = Kern.His_PersReisdoc.PersReisdoc                  and NEW.ID                           <> Kern.His_PersReisdoc.ID                          
and Kern.His_PersReisdoc.DatAanvGel                   < NEW.DatEindeGel                  and Kern.His_PersReisdoc.DatEindeGel                  > NEW.DatAanvGel                  
AND Kern.His_PersReisdoc.ActieVerval                  is null AND Kern.His_PersReisdoc.TsVerval                     is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_PersReisdoc (PersReisdoc                 , ActieAanpGel                , Nr                          , DatUitgifte                 , AutVanAfgifte               , DatVoorzeEindeGel           , DatInhingVermissing         , RdnVervallen                , LengteHouder                , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_PersReisdoc.PersReisdoc                 , Kern.His_PersReisdoc.ActieAanpGel                , Kern.His_PersReisdoc.Nr                          , Kern.His_PersReisdoc.DatUitgifte                 , Kern.His_PersReisdoc.AutVanAfgifte               , Kern.His_PersReisdoc.DatVoorzeEindeGel           , Kern.His_PersReisdoc.DatInhingVermissing         , Kern.His_PersReisdoc.RdnVervallen                , Kern.His_PersReisdoc.LengteHouder                , Kern.His_PersReisdoc.DatAanvGel                  , NEW.DatAanvGel                  , Kern.His_PersReisdoc.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersReisdoc
WHERE Kern.His_PersReisdoc.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_PersReisdoc (PersReisdoc                 , ActieAanpGel                , Nr                          , DatUitgifte                 , AutVanAfgifte               , DatVoorzeEindeGel           , DatInhingVermissing         , RdnVervallen                , LengteHouder                , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_PersReisdoc.PersReisdoc                 , Kern.His_PersReisdoc.ActieAanpGel                , Kern.His_PersReisdoc.Nr                          , Kern.His_PersReisdoc.DatUitgifte                 , Kern.His_PersReisdoc.AutVanAfgifte               , Kern.His_PersReisdoc.DatVoorzeEindeGel           , Kern.His_PersReisdoc.DatInhingVermissing         , Kern.His_PersReisdoc.RdnVervallen                , Kern.His_PersReisdoc.LengteHouder                , NEW.DatEindeGel                 , Kern.His_PersReisdoc.DatEindeGel                 , Kern.His_PersReisdoc.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersReisdoc
WHERE Kern.His_PersReisdoc.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_PersReisdoc$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_PersReisdoc RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_PersReisdoc

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_PersReisdoc();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_PersVerificatie() returns TRIGGER AS $formele_historie_Kern_His_PersVerificatie$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.ActieInh                     is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.ActieInh                     Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.TsReg                        = Kern.BRPActie.ID                          ;
IF NEW.ActieInh                     is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_PersVerificatie
WHERE NEW.PersReisdoc                  = Kern.His_PersVerificatie.PersReisdoc                  and NEW.ID                           <> Kern.His_PersVerificatie.ID                          
and NEW.DatAanvGel                   > Kern.His_PersVerificatie.DatAanvGel                   and NEW.DatAanvGel                   < Kern.His_PersVerificatie.DatEindeGel                 
AND Kern.His_PersVerificatie.ActieVerval                  IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_PersVerificatie WHERE NEW.PersReisdoc                  = Kern.His_PersVerificatie.PersReisdoc                  and NEW.ID                           <> Kern.His_PersVerificatie.ID                          
and NEW.DatEindeGel                  > Kern.His_PersVerificatie.DatAanvGel                   and NEW.DatEindeGel                  < Kern.His_PersVerificatie.DatEindeGel                  AND Kern.His_PersVerificatie.ActieVerval                  IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_PersVerificatie SET ActieVerval                  = NEW.ActieInh                    , TsVerval                     = NEW.TsReg                       

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.PersReisdoc                  = Kern.His_PersVerificatie.PersReisdoc                  and NEW.ID                           <> Kern.His_PersVerificatie.ID                          
and Kern.His_PersVerificatie.DatAanvGel                   < NEW.DatEindeGel                  and Kern.His_PersVerificatie.DatEindeGel                  > NEW.DatAanvGel                  
AND Kern.His_PersVerificatie.ActieVerval                  is null AND Kern.His_PersVerificatie.TsVerval                     is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_PersVerificatie (PersReisdoc                 , ActieAanpGel                , Nr                          , DatUitgifte                 , AutVanAfgifte               , DatVoorzeEindeGel           , DatInhingVermissing         , RdnVervallen                , LengteHouder                , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_PersVerificatie.PersReisdoc                 , Kern.His_PersReisdoc.ActieAanpGel                , Kern.His_PersReisdoc.Nr                          , Kern.His_PersReisdoc.DatUitgifte                 , Kern.His_PersReisdoc.AutVanAfgifte               , Kern.His_PersReisdoc.DatVoorzeEindeGel           , Kern.His_PersReisdoc.DatInhingVermissing         , Kern.His_PersReisdoc.RdnVervallen                , Kern.His_PersReisdoc.LengteHouder                , Kern.His_PersVerificatie.DatAanvGel                  , NEW.DatAanvGel                  , Kern.His_PersVerificatie.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersVerificatie
WHERE Kern.His_PersVerificatie.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_PersVerificatie (PersReisdoc                 , ActieAanpGel                , Nr                          , DatUitgifte                 , AutVanAfgifte               , DatVoorzeEindeGel           , DatInhingVermissing         , RdnVervallen                , LengteHouder                , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_PersVerificatie.PersReisdoc                 , Kern.His_PersReisdoc.ActieAanpGel                , Kern.His_PersReisdoc.Nr                          , Kern.His_PersReisdoc.DatUitgifte                 , Kern.His_PersReisdoc.AutVanAfgifte               , Kern.His_PersReisdoc.DatVoorzeEindeGel           , Kern.His_PersReisdoc.DatInhingVermissing         , Kern.His_PersReisdoc.RdnVervallen                , Kern.His_PersReisdoc.LengteHouder                , NEW.DatEindeGel                 , Kern.His_PersVerificatie.DatEindeGel                 , Kern.His_PersVerificatie.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersVerificatie
WHERE Kern.His_PersVerificatie.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_PersVerificatie$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_PersVerificatie RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_PersVerificatie

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_PersVerificatie();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_PersVoornaam() returns TRIGGER AS $formele_historie_Kern_His_PersVoornaam$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.ActieInh                     is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.ActieInh                     Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.TsReg                        = Kern.BRPActie.ID                          ;
IF NEW.ActieInh                     is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_PersVoornaam
WHERE NEW.PersVoornaam                 = Kern.His_PersVoornaam.PersVoornaam                 and NEW.ID                           <> Kern.His_PersVoornaam.ID                          
and NEW.DatAanvGel                   > Kern.His_PersVoornaam.DatAanvGel                   and NEW.DatAanvGel                   < Kern.His_PersVoornaam.DatEindeGel                 
AND Kern.His_PersVoornaam.ActieVerval                  IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_PersVoornaam WHERE NEW.PersVoornaam                 = Kern.His_PersVoornaam.PersVoornaam                 and NEW.ID                           <> Kern.His_PersVoornaam.ID                          
and NEW.DatEindeGel                  > Kern.His_PersVoornaam.DatAanvGel                   and NEW.DatEindeGel                  < Kern.His_PersVoornaam.DatEindeGel                  AND Kern.His_PersVoornaam.ActieVerval                  IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_PersVoornaam SET ActieVerval                  = NEW.ActieInh                    , TsVerval                     = NEW.TsReg                       

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.PersVoornaam                 = Kern.His_PersVoornaam.PersVoornaam                 and NEW.ID                           <> Kern.His_PersVoornaam.ID                          
and Kern.His_PersVoornaam.DatAanvGel                   < NEW.DatEindeGel                  and Kern.His_PersVoornaam.DatEindeGel                  > NEW.DatAanvGel                  
AND Kern.His_PersVoornaam.ActieVerval                  is null AND Kern.His_PersVoornaam.TsVerval                     is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_PersVoornaam (PersVoornaam                , ActieAanpGel                , Naam                        , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_PersVoornaam.PersVoornaam                , Kern.His_PersVoornaam.ActieAanpGel                , Kern.His_PersVoornaam.Naam                        , Kern.His_PersVoornaam.DatAanvGel                  , NEW.DatAanvGel                  , Kern.His_PersVoornaam.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersVoornaam
WHERE Kern.His_PersVoornaam.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_PersVoornaam (PersVoornaam                , ActieAanpGel                , Naam                        , DatAanvGel                   , DatEindeGel                 , TsReg                       , ActieInh                    , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_PersVoornaam.PersVoornaam                , Kern.His_PersVoornaam.ActieAanpGel                , Kern.His_PersVoornaam.Naam                        , NEW.DatEindeGel                 , Kern.His_PersVoornaam.DatEindeGel                 , Kern.His_PersVoornaam.TsReg                       , NEW.ActieInh                    , NEW.TsReg                        FROM Kern.His_PersVoornaam
WHERE Kern.His_PersVoornaam.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_PersVoornaam$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_PersVoornaam RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_PersVoornaam

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_PersVoornaam();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Kern_His_Relatie() returns TRIGGER AS $formele_historie_Kern_His_Relatie$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.DatAanv                      is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.DatAanv                      Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.ActieInh                     = Kern.BRPActie.ID                          ;
IF NEW.DatAanv                      is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Kern.His_Relatie
WHERE NEW.Relatie                      = Kern.His_Relatie.Relatie                      and NEW.ID                           <> Kern.His_Relatie.ID                          
and NEW.TsReg                        > Kern.His_Relatie.TsReg                        and NEW.TsReg                        < Kern.His_Relatie.TsVerval                    
AND Kern.His_Relatie.GemAanv                      IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Kern.His_Relatie WHERE NEW.Relatie                      = Kern.His_Relatie.Relatie                      and NEW.ID                           <> Kern.His_Relatie.ID                          
and NEW.TsVerval                     > Kern.His_Relatie.TsReg                        and NEW.TsVerval                     < Kern.His_Relatie.TsVerval                     AND Kern.His_Relatie.GemAanv                      IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Kern.His_Relatie SET GemAanv                      = NEW.DatAanv                     , ActieVerval                  = NEW.ActieInh                    

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Relatie                      = Kern.His_Relatie.Relatie                      and NEW.ID                           <> Kern.His_Relatie.ID                          
and Kern.His_Relatie.TsReg                        < NEW.TsVerval                     and Kern.His_Relatie.TsVerval                     > NEW.TsReg                       
AND Kern.His_Relatie.GemAanv                      is null AND Kern.His_Relatie.ActieVerval                  is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Kern.His_Relatie (Relatie                     , WplAanv                     , BLPlaatsAanv                , BLRegioAanv                 , LandAanv                    , OmsLocAanv                  , RdnEinde                    , DatEinde                    , GemEinde                    , WplEinde                    , BLPlaatsEinde               , BLRegioEinde                , LandEinde                   , OmsLocEinde                 , TsReg                        , TsVerval                    , ActieInh                    , DatAanv                     , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Kern.His_Relatie.Relatie                     , Kern.His_Relatie.WplAanv                     , Kern.His_Relatie.BLPlaatsAanv                , Kern.His_Relatie.BLRegioAanv                 , Kern.His_Relatie.LandAanv                    , Kern.His_Relatie.OmsLocAanv                  , Kern.His_Relatie.RdnEinde                    , Kern.His_Relatie.DatEinde                    , Kern.His_Relatie.GemEinde                    , Kern.His_Relatie.WplEinde                    , Kern.His_Relatie.BLPlaatsEinde               , Kern.His_Relatie.BLRegioEinde                , Kern.His_Relatie.LandEinde                   , Kern.His_Relatie.OmsLocEinde                 , Kern.His_Relatie.TsReg                       , NEW.TsReg                       , Kern.His_Relatie.ActieInh                    , NEW.DatAanv                     , NEW.ActieInh                     FROM Kern.His_Relatie
WHERE Kern.His_Relatie.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Kern.His_Relatie (Relatie                     , WplAanv                     , BLPlaatsAanv                , BLRegioAanv                 , LandAanv                    , OmsLocAanv                  , RdnEinde                    , DatEinde                    , GemEinde                    , WplEinde                    , BLPlaatsEinde               , BLRegioEinde                , LandEinde                   , OmsLocEinde                 , TsReg                        , TsVerval                    , ActieInh                    , DatAanv                     , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Kern.His_Relatie.Relatie                     , Kern.His_Relatie.WplAanv                     , Kern.His_Relatie.BLPlaatsAanv                , Kern.His_Relatie.BLRegioAanv                 , Kern.His_Relatie.LandAanv                    , Kern.His_Relatie.OmsLocAanv                  , Kern.His_Relatie.RdnEinde                    , Kern.His_Relatie.DatEinde                    , Kern.His_Relatie.GemEinde                    , Kern.His_Relatie.WplEinde                    , Kern.His_Relatie.BLPlaatsEinde               , Kern.His_Relatie.BLRegioEinde                , Kern.His_Relatie.LandEinde                   , Kern.His_Relatie.OmsLocEinde                 , NEW.TsVerval                    , Kern.His_Relatie.TsVerval                    , Kern.His_Relatie.ActieInh                    , NEW.DatAanv                     , NEW.ActieInh                     FROM Kern.His_Relatie
WHERE Kern.His_Relatie.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Kern_His_Relatie$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Kern.His_Relatie RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Kern.His_Relatie

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Kern_His_Relatie();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Lev_His_Abonnement() returns TRIGGER AS $formele_historie_Lev_His_Abonnement$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.DatAanv                      is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.DatAanv                      Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.ActieInh                     = Kern.BRPActie.ID                          ;
IF NEW.DatAanv                      is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Lev.His_Abonnement
WHERE NEW.Relatie                      = Lev.His_Abonnement.Relatie                      and NEW.ID                           <> Lev.His_Abonnement.ID                          
and NEW.TsReg                        > Lev.His_Abonnement.TsReg                        and NEW.TsReg                        < Lev.His_Abonnement.TsVerval                    
AND Lev.His_Abonnement.GemAanv                      IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Lev.His_Abonnement WHERE NEW.Relatie                      = Lev.His_Abonnement.Relatie                      and NEW.ID                           <> Lev.His_Abonnement.ID                          
and NEW.TsVerval                     > Lev.His_Abonnement.TsReg                        and NEW.TsVerval                     < Lev.His_Abonnement.TsVerval                     AND Lev.His_Abonnement.GemAanv                      IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Lev.His_Abonnement SET GemAanv                      = NEW.DatAanv                     , ActieVerval                  = NEW.ActieInh                    

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Relatie                      = Lev.His_Abonnement.Relatie                      and NEW.ID                           <> Lev.His_Abonnement.ID                          
and Lev.His_Abonnement.TsReg                        < NEW.TsVerval                     and Lev.His_Abonnement.TsVerval                     > NEW.TsReg                       
AND Lev.His_Abonnement.GemAanv                      is null AND Lev.His_Abonnement.ActieVerval                  is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Lev.His_Abonnement (Relatie                     , WplAanv                     , BLPlaatsAanv                , BLRegioAanv                 , LandAanv                    , OmsLocAanv                  , RdnEinde                    , DatEinde                    , GemEinde                    , WplEinde                    , BLPlaatsEinde               , BLRegioEinde                , LandEinde                   , OmsLocEinde                 , TsReg                        , TsVerval                    , ActieInh                    , DatAanv                     , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Lev.His_Abonnement.Relatie                     , Kern.His_Relatie.WplAanv                     , Kern.His_Relatie.BLPlaatsAanv                , Kern.His_Relatie.BLRegioAanv                 , Kern.His_Relatie.LandAanv                    , Kern.His_Relatie.OmsLocAanv                  , Kern.His_Relatie.RdnEinde                    , Kern.His_Relatie.DatEinde                    , Kern.His_Relatie.GemEinde                    , Kern.His_Relatie.WplEinde                    , Kern.His_Relatie.BLPlaatsEinde               , Kern.His_Relatie.BLRegioEinde                , Kern.His_Relatie.LandEinde                   , Kern.His_Relatie.OmsLocEinde                 , Lev.His_Abonnement.TsReg                       , NEW.TsReg                       , Lev.His_Abonnement.ActieInh                    , NEW.DatAanv                     , NEW.ActieInh                     FROM Lev.His_Abonnement
WHERE Lev.His_Abonnement.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Lev.His_Abonnement (Relatie                     , WplAanv                     , BLPlaatsAanv                , BLRegioAanv                 , LandAanv                    , OmsLocAanv                  , RdnEinde                    , DatEinde                    , GemEinde                    , WplEinde                    , BLPlaatsEinde               , BLRegioEinde                , LandEinde                   , OmsLocEinde                 , TsReg                        , TsVerval                    , ActieInh                    , DatAanv                     , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Lev.His_Abonnement.Relatie                     , Kern.His_Relatie.WplAanv                     , Kern.His_Relatie.BLPlaatsAanv                , Kern.His_Relatie.BLRegioAanv                 , Kern.His_Relatie.LandAanv                    , Kern.His_Relatie.OmsLocAanv                  , Kern.His_Relatie.RdnEinde                    , Kern.His_Relatie.DatEinde                    , Kern.His_Relatie.GemEinde                    , Kern.His_Relatie.WplEinde                    , Kern.His_Relatie.BLPlaatsEinde               , Kern.His_Relatie.BLRegioEinde                , Kern.His_Relatie.LandEinde                   , Kern.His_Relatie.OmsLocEinde                 , NEW.TsVerval                    , Lev.His_Abonnement.TsVerval                    , Lev.His_Abonnement.ActieInh                    , NEW.DatAanv                     , NEW.ActieInh                     FROM Lev.His_Abonnement
WHERE Lev.His_Abonnement.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Lev_His_Abonnement$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Lev.His_Abonnement RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Lev.His_Abonnement

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Lev_His_Abonnement();
-- Start triggeroutput ---------------------------------------------------
-- 
-- 
-- 
-- 
-- De volgende code is gegenereerd vanuit de BMR,
-- derhalve NIET handmatig aanpassen
-- Versie triggercode: Versie 0.000002 - 29  2011
-- ------------------------------------------------------------------------
-- De laag waaruit gegenereerd: 1751
   -- create or replace FUNCTION formele_historie_VB() returns TRIGGER AS $formele_historie_VB$

create or replace FUNCTION
formele_historie_Lev_His_AbonnementSrtBer() returns TRIGGER AS $formele_historie_Lev_His_AbonnementSrtBer$

   -- DECLARE
   --	KOP_id int;
   --	STAART_id int;
   -- BEGIN

DECLARE
KOP_id int; STAART_id int;
BEGIN

        -- Stap 1: vullen van de timestamp van registratie. Deze is afkomstig van de ACTIE tabel.
        -- Bij het aanroepen van deze functie dient de waarde echter nog leeg te zijn: als deze al gevuld is,
        -- dan zal het een insert zijn die al gegenereerd is. Om loops te voorkomen ;-0 dus deze check; als het gevuld
        -- is dan moet de trigger NIETS doen(!)
        -- IF NEW.ta is null THEN

IF NEW.DatAanv                      is null THEN

          -- Stap 1 vervolg: vullen van het ta van het huidige record, met de waarde uit ACTIE:
          -- 	SELECT INTO NEW.ta ACTIE.ta FROM ACTIE where NEW.aa = ACTIE.id;
          -- 	IF NEW.ta is null THEN
          -- 		RAISE EXCEPTION 'PROBLEEM!';
          --	END IF;

SELECT INTO NEW.DatAanv                      Kern.BRPActie.tijdstipreg FROM Kern.BRPActie where NEW.ActieInh                     = Kern.BRPActie.ID                          ;
IF NEW.DatAanv                      is null THEN
RAISE EXCEPTION 'PROBLEEM!';
END IF;

          -- Stap 2: Bepaal een eventueel KOP record
          -- SELECT INTO KOP_id id FROM VB
          -- WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
          -- and NEW.da > VB.da
          -- and NEW.da < VB.de AND VB.te IS NULL;

SELECT INTO KOP_id ID                           FROM Lev.His_AbonnementSrtBer
WHERE NEW.Relatie                      = Lev.His_AbonnementSrtBer.Relatie                      and NEW.ID                           <> Lev.His_AbonnementSrtBer.ID                          
and NEW.TsReg                        > Lev.His_AbonnementSrtBer.TsReg                        and NEW.TsReg                        < Lev.His_AbonnementSrtBer.TsVerval                    
AND Lev.His_AbonnementSrtBer.GemAanv                      IS NULL;


           -- er is een kop als het begin van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)
           -- Stap 3: Bepaal een eventueel STAART record
           -- 	SELECT INTO STAART_id id FROM VB WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
           --			and NEW.de > VB.da and NEW.de < VB.de AND VB.te IS NULL;
           -- er is een staart als het eind van het nieuwe(!) record ligt in de periode da--de
           -- en de oude(!) moet uiteraard nog niet afgesloten zijn(!)

SELECT INTO STAART_id ID                           FROM Lev.His_AbonnementSrtBer WHERE NEW.Relatie                      = Lev.His_AbonnementSrtBer.Relatie                      and NEW.ID                           <> Lev.His_AbonnementSrtBer.ID                          
and NEW.TsVerval                     > Lev.His_AbonnementSrtBer.TsReg                        and NEW.TsVerval                     < Lev.His_AbonnementSrtBer.TsVerval                     AND Lev.His_AbonnementSrtBer.GemAanv                      IS NULL;

           -- Stap 4: Set voor alle records (óók degene die aanleiding zijn geweest voor KOP/STAART)
           --		het te gelijk aan de ta waarde van de bijbehorende ACTIE, idem vullen van de ae:
           -- 	UPDATE VB SET te = NEW.ta, ae = NEW.aa
UPDATE Lev.His_AbonnementSrtBer SET GemAanv                      = NEW.DatAanv                     , ActieVerval                  = NEW.ActieInh                    

            --	WHERE NEW.id_deel = VB.id_deel and NEW.id <> VB.id
            --			and VB.da < NEW.de and VB.de > NEW.da
            --			AND VB.te is null AND VB.ae is null;
            -- ze gaan over hetzelfde, maar het is een ander record)
            -- de periodes VA.da--VB.de en NEW.da--NEW.de hebben overlap als VA.de > NEW.da EN VA.da < NEW.de

WHERE NEW.Relatie                      = Lev.His_AbonnementSrtBer.Relatie                      and NEW.ID                           <> Lev.His_AbonnementSrtBer.ID                          
and Lev.His_AbonnementSrtBer.TsReg                        < NEW.TsVerval                     and Lev.His_AbonnementSrtBer.TsVerval                     > NEW.TsReg                       
AND Lev.His_AbonnementSrtBer.GemAanv                      is null AND Lev.His_AbonnementSrtBer.ActieVerval                  is null;

            -- Stap 2b: Voer een record in, dat gaat over de KOP periode, met de waarde van het KOP record, maar dan met
            -- timestamps van het NEW record:
            -- INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!
            -- 	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, VB.da, NEW.da, VBA.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = KOP_id);
            -- NB: hiervan moet cActieBeinvloedingGeldigheid worden gevuld met het ID van de actie die het ontstaan van
            -- het KOP record heeft veroorzaak; de oorspronkelijke Actie moet worden behouden(!)

INSERT INTO Lev.His_AbonnementSrtBer (Relatie                     , WplAanv                     , BLPlaatsAanv                , BLRegioAanv                 , LandAanv                    , OmsLocAanv                  , RdnEinde                    , DatEinde                    , GemEinde                    , WplEinde                    , BLPlaatsEinde               , BLRegioEinde                , LandEinde                   , OmsLocEinde                 , TsReg                        , TsVerval                    , ActieInh                    , DatAanv                     , BRPActieBeinGeld)
            -- Voor gKolLijstDeel: de rij met 'niet standaard' kolommen; idem: gKolLijstVol is hetzelfde maar nu met prefix 'lTableIdentifier'!');
(SELECT Lev.His_AbonnementSrtBer.Relatie                     , Kern.His_Relatie.WplAanv                     , Kern.His_Relatie.BLPlaatsAanv                , Kern.His_Relatie.BLRegioAanv                 , Kern.His_Relatie.LandAanv                    , Kern.His_Relatie.OmsLocAanv                  , Kern.His_Relatie.RdnEinde                    , Kern.His_Relatie.DatEinde                    , Kern.His_Relatie.GemEinde                    , Kern.His_Relatie.WplEinde                    , Kern.His_Relatie.BLPlaatsEinde               , Kern.His_Relatie.BLRegioEinde                , Kern.His_Relatie.LandEinde                   , Kern.His_Relatie.OmsLocEinde                 , Lev.His_AbonnementSrtBer.TsReg                       , NEW.TsReg                       , Lev.His_AbonnementSrtBer.ActieInh                    , NEW.DatAanv                     , NEW.ActieInh                     FROM Lev.His_AbonnementSrtBer
WHERE Lev.His_AbonnementSrtBer.ID                           = KOP_id);

            -- Stap 3b: Voer een record in, dat gaat over de STAART periode, met de waarde van het STAART record, maar dan met
            -- timestamps van het NEW record:
            -- 	INSERT INTO VB (id_deel, kol1, kol2, kolN , da , de, aa, ta, aag) -- dus nu WEL het autonummer van id_deel en de ta etc)!

INSERT INTO Lev.His_AbonnementSrtBer (Relatie                     , WplAanv                     , BLPlaatsAanv                , BLRegioAanv                 , LandAanv                    , OmsLocAanv                  , RdnEinde                    , DatEinde                    , GemEinde                    , WplEinde                    , BLPlaatsEinde               , BLRegioEinde                , LandEinde                   , OmsLocEinde                 , TsReg                        , TsVerval                    , ActieInh                    , DatAanv                     , BRPActieBeinGeld) -- dus nu WEL het autonummer van id_deel en de ta etc)!

            --	(SELECT VB.id_deel, VB.kol1, VB.kol2, VB.kolN, NEW.de, VB.de, VB.aa, NEW.ta, NEW.aa FROM VB
            --		WHERE VB.id = STAART_id);

(SELECT Lev.His_AbonnementSrtBer.Relatie                     , Kern.His_Relatie.WplAanv                     , Kern.His_Relatie.BLPlaatsAanv                , Kern.His_Relatie.BLRegioAanv                 , Kern.His_Relatie.LandAanv                    , Kern.His_Relatie.OmsLocAanv                  , Kern.His_Relatie.RdnEinde                    , Kern.His_Relatie.DatEinde                    , Kern.His_Relatie.GemEinde                    , Kern.His_Relatie.WplEinde                    , Kern.His_Relatie.BLPlaatsEinde               , Kern.His_Relatie.BLRegioEinde                , Kern.His_Relatie.LandEinde                   , Kern.His_Relatie.OmsLocEinde                 , NEW.TsVerval                    , Lev.His_AbonnementSrtBer.TsVerval                    , Lev.His_AbonnementSrtBer.ActieInh                    , NEW.DatAanv                     , NEW.ActieInh                     FROM Lev.His_AbonnementSrtBer
WHERE Lev.His_AbonnementSrtBer.ID                           = STAART_id);

            -- END IF;
            -- RETURN NEW;
            -- waarde wordt niet genegeerd als het gaat om een BEFORE trigger. Daarom een before trigger
            -- Als het een AFTER trigger wordt, dan moet de update van NEW via een UPDATE statement, voor het zetten
            -- van ta en aa.
            -- END;

END IF;
RETURN NEW;
END;

            -- $formele_historie_VB$ language plpgsql;

$formele_historie_Lev_His_AbonnementSrtBer$ language plpgsql;

            -- Drop the trigger if it exists:

drop trigger if exists formele_historie on Lev.His_AbonnementSrtBer RESTRICT;

            -- create trigger formele_historie BEFORE INSERT on VB
            -- In Postgres hoeft de trigger naam NIET uniek te zijn, alleen uniek
            -- binnen de tabelnaam(!)

create trigger formele_historie BEFORE INSERT on Lev.His_AbonnementSrtBer

            -- FOR EACH ROW EXECUTE PROCEDURE formele_historie_VB();

FOR EACH ROW EXECUTE PROCEDURE formele_historie_Lev_His_AbonnementSrtBer();
