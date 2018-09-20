Drop table if exists kruistabel;

create table kruistabel (
    BRP_status varchar(60),
    GBAV_status varchar(60),
    count bigint
);
INSERT INTO kruistabel
(select CASE WHEN a.verwerkingssysteem = b.verwerkingssysteem AND
            a.verwerkingssysteem = 'BRP' THEN a.status
            WHEN a.verwerkingssysteem != b.verwerkingssysteem THEN a.status
       END as "BRP", 
       CASE WHEN a.verwerkingssysteem = b.verwerkingssysteem AND
            a.verwerkingssysteem = 'GBA-V' THEN a.status
            WHEN a.verwerkingssysteem != b.verwerkingssysteem THEN b.status
        END as "GBA-V",
        count(a.bericht_id)
from resultaatmeting4 a LEFT join resultaatmeting4 b on a.bericht_id = b.bericht_id
WHERE (a.verwerkingssysteem = 'BRP') 
      --Waar alleen bij BRP het bericht_id voorkomt
      AND ((a.verwerkingssysteem = b.verwerkingssysteem
              AND NOT EXISTS (select * from resultaatmeting4 where bericht_id = a.bericht_id and verwerkingssysteem != a.verwerkingssysteem))
              --Alle resultaten waar het bericht_id in beide verwerkingssystemen voorkomt
              OR a.verwerkingssysteem != b.verwerkingssysteem)
              --Waar alleen bij GBA-V het bericht_id voorkomt:
      OR ((a.verwerkingssysteem = 'GBA-V') AND (a.verwerkingssysteem = b.verwerkingssysteem
              AND NOT EXISTS (select * from resultaatmeting4 where bericht_id = a.bericht_id and verwerkingssysteem != a.verwerkingssysteem)))
group by a.status, b.status, a.verwerkingssysteem, b.verwerkingssysteem);

INSERT INTO kruistabel
(select CASE WHEN a.verwerkingssysteem = b.verwerkingssysteem AND
            a.verwerkingssysteem = 'BRP' THEN a.status
            WHEN a.verwerkingssysteem != b.verwerkingssysteem THEN a.status
       END as "BRP", 
       CASE WHEN a.verwerkingssysteem = b.verwerkingssysteem AND
            a.verwerkingssysteem = 'GBA-V' THEN a.status
            WHEN a.verwerkingssysteem != b.verwerkingssysteem THEN b.status
        END as "GBA-V",
        count(a.bericht_id)
from resultaatmeting3 a LEFT join resultaatmeting3 b on a.bericht_id = b.bericht_id
WHERE (a.verwerkingssysteem = 'BRP') 
      --Waar alleen bij BRP het bericht_id voorkomt
      AND ((a.verwerkingssysteem = b.verwerkingssysteem
              AND NOT EXISTS (select * from resultaatmeting3 where bericht_id = a.bericht_id and verwerkingssysteem != a.verwerkingssysteem))
              --Alle resultaten waar het bericht_id in beide verwerkingssystemen voorkomt
              OR a.verwerkingssysteem != b.verwerkingssysteem)
              --Waar alleen bij GBA-V het bericht_id voorkomt:
      OR ((a.verwerkingssysteem = 'GBA-V') AND (a.verwerkingssysteem = b.verwerkingssysteem
              AND NOT EXISTS (select * from resultaatmeting3 where bericht_id = a.bericht_id and verwerkingssysteem != a.verwerkingssysteem)))
group by a.status, b.status, a.verwerkingssysteem, b.verwerkingssysteem);

SELECT * FROM kruistabel;