-- ------------------------------------------------------------------------
-- De in dit bestand opgenomen SQL statements zijn nodig voor het
-- aanmaken van de benodigde indexen na het opbouwen van de database.
-- In principe zouden deze door het BMR in de brp.sql moeten worden
-- gegenereerd, maar zolang dat niet zo is, kunnen ze middels dit
-- script worden gedraaid.
-- ------------------------------------------------------------------------

DROP INDEX IF EXISTS indaut001;
CREATE INDEX indaut001 ON autaut.authenticatiemiddel (
    certificaattbvondertekening ASC NULLS LAST, partij ASC NULLS LAST, authenticatiemiddelstatushis ASC NULLS LAST);

DROP INDEX IF EXISTS indaut002;
CREATE INDEX indaut002 ON autaut.certificaat (
    subject ASC NULLS LAST, serial ASC NULLS LAST, signature ASC NULLS LAST);



DROP INDEX IF EXISTS indkern001;
CREATE INDEX indkern001 ON kern.pers (bsn ASC NULLS LAST);


DROP INDEX IF EXISTS indkern201;
CREATE INDEX indkern201 ON kern.his_betrouderschap (betr ASC NULLS LAST, tsverval DESC NULLS FIRST);


DROP INDEX IF EXISTS indkern301;
CREATE INDEX indkern301 ON kern.persadres (gem ASC NULLS LAST);

DROP INDEX IF EXISTS indkern302;
CREATE INDEX indkern302 ON kern.persadres (
    srt ASC NULLS LAST, UPPER(postcode) ASC NULLS LAST, huisnr ASC NULLS LAST, UPPER(huisletter) ASC NULLS FIRST,
    UPPER(huisnrtoevoeging) ASC NULLS FIRST);

DROP INDEX IF EXISTS indkern303;
CREATE INDEX indkern303 ON kern.persadres (
    srt ASC NULLS LAST, gem ASC NULLS LAST, UPPER(nor) ASC NULLS LAST, huisnr ASC NULLS LAST,
    UPPER(huisletter) ASC NULLS FIRST, UPPER(huisnrtoevoeging) ASC NULLS FIRST);


DROP INDEX IF EXISTS indkern901;
CREATE INDEX indkern901 ON kern.partij (code ASC NULLS LAST);

DROP INDEX IF EXISTS indkern902;
CREATE INDEX indkern902 ON kern.plaats (code ASC NULLS LAST);

DROP INDEX IF EXISTS indkern903;
CREATE INDEX indkern903 ON kern.land (code ASC NULLS LAST);

DROP INDEX IF EXISTS indkern904;
CREATE INDEX indkern904 ON kern.nation (nationcode ASC NULLS LAST);
