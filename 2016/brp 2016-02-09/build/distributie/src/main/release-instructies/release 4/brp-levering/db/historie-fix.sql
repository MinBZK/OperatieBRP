--
-- Deze SQL file bevat INSERT statements om verplichte historische rijen aan te maken voor de adam's en eva's van de whiteboxfiller
--

-- samengestelde naam   
INSERT INTO kern.his_perssamengesteldenaam ( 
    pers
    , dataanvgel
    , tsreg
    , actieinh 
    , indalgoritmischafgeleid
    , indnreeks
    , voornamen
    , voorvoegsel
    , geslnaam
    )
SELECT  kp.id
    , 19500101
    , '1950-01-01 00:00:00'
    , 1
    , TRUE
    , FALSE
    , kp.voornamen
    , kp.voorvoegsel
    , kp.geslnaam
FROM    kern.pers kp
WHERE NOT EXISTS (
    SELECT pers
    FROM kern.his_perssamengesteldenaam
    WHERE pers=kp.id
    );


-- geboorte
INSERT INTO kern.his_persgeboorte ( 
    pers
    , tsreg
    , actieinh 
    , datgeboorte
    , gemgeboorte
    , wplgeboorte
    , landgeboorte
    )
SELECT  kp.id
    , '1950-01-01 00:00:00'
    , 1
    , kp.datgeboorte
    , kp.gemgeboorte
    , kp.wplgeboorte
    , kp.landgeboorte
FROM    kern.pers kp
WHERE NOT EXISTS (
    SELECT pers
    FROM kern.his_persgeboorte
    WHERE pers=kp.id
    );

-- geslacht
INSERT INTO kern.his_persgeslachtsaand ( 
    pers
    , dataanvgel
    , tsreg
    , actieinh 
    , geslachtsaand
    )
SELECT  kp.id
    , 19500101
    , '1950-01-01 00:00:00'
    , 1
    , kp.geslachtsaand
FROM    kern.pers kp
WHERE NOT EXISTS (
    SELECT pers
    FROM kern.his_persgeslachtsaand
    WHERE pers=kp.id
    );
