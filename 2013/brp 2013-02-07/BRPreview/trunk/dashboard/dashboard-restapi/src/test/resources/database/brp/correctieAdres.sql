-- Toevoeging om een correctieadres in de testset te plaatsen

-- Dit lijkt voldoende (volgens javadoc in PersoonAdresJpaRepository, is een correctieadres van huidig adres zelfde als verhuizing, alleen ander type)
-- Verhuizing ombouwen naar correctie adres
UPDATE kern.admhnd
SET srt = 27
WHERE id = 9000;


-- Archief (is dus niet nodig):

-- Toevoeging aan kern.his_persadres
--INSERT INTO 
--kern.his_persadres(id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject,identcodenraand,gem,nor,afgekortenor,gemdeel,
--huisnr,huisletter,huisnrtoevoeging,postcode,wpl,loctovadres,locoms,datvertrekuitnederland,bladresregel1,bladresregel2,bladresregel3,bladresregel4,bladresregel5,bladresregel6,land)
--SELECT '9001',persadres,dataanvgel,dateindegel,'2005-03-28 00:00:01','2005-03-28 00:00:01',actieinh,'9001',actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,adresseerbaarobject,identcodenraand,gem,nor,afgekortenor,gemdeel,
--huisnr,huisletter,huisnrtoevoeging,postcode,wpl,loctovadres,locoms,datvertrekuitnederland,bladresregel1,bladresregel2,bladresregel3,bladresregel4,bladresregel5,bladresregel6,land 
--FROM kern.his_persadres
--WHERE id=8999;

-- Toevoeging aan kern.admhnd
--INSERT INTO 
--kern.admhnd(id,srt,partij,tsontlening,toelichtingontlening,tsreg)
--SELECT '9001',srt,partij,tsontlening,toelichtingontlening,tsreg
--FROM kern.admhnd
--WHERE id=8999;

-- Toevoeging aan kern.actie
--INSERT INTO 
--kern.actie(id,srt,admhnd,partij,dataanvgel,dateindegel,tsreg)
--SELECT '9001',srt,'9001',partij,dataanvgel,dateindegel,tsreg 
--FROM kern.actie
--WHERE id=8999;
