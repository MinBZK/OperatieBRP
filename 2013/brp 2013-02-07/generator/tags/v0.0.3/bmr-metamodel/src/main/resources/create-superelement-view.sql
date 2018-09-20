--
-- Creëer een view met een extra kolom "SUPERTYPE" voor rijen in ELEMENT met SOORT = 'OT' (ObjectType). Dit
-- omzeilt een Hibernate mapping issue met betrekking tot TYPE_.
-- TYPE_ wordt gebruikt voor AttributeType om het BasicType aan te duiden, en voor ObjectType om het supertype aan te
-- duiden. Bij polymorfe queries op Type, de superclass van zowel AttributeType en ObjectType, raakt Hibernate daarvan
-- in de war.
-- 
RECREATE VIEW SUPERTYPE AS
    SELECT
        ELEMENT.*,
        CASE
            WHEN ELEMENT.SOORT = 'OT' THEN ELEMENT.TYPE_
            ELSE NULL
        END AS SUPERTYPE
    FROM ELEMENT;