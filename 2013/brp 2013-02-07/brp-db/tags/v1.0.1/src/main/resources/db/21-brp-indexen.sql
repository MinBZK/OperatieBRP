-- ------------------------------------------------------------------------
-- De in dit bestand opgenomen SQL statements zijn nodig voor het
-- aanmaken van de benodigde indexen na het opbouwen van de database.
-- In principe zouden deze door het BMR in de brp.sql moeten worden
-- gegenereerd, maar zolang dat niet zo is, kunnen ze middels dit
-- script worden gedraaid.
-- ------------------------------------------------------------------------

DROP INDEX IF EXISTS autaut.indaut001;
CREATE INDEX indaut001 ON autaut.authenticatiemiddel (
    certificaattbvondertekening ASC NULLS LAST, partij ASC NULLS LAST, authenticatiemiddelstatushis ASC NULLS LAST);

DROP INDEX IF EXISTS kern.indkern001;
CREATE INDEX indkern001 ON kern.pers (bsn ASC NULLS LAST);

DROP INDEX IF EXISTS kern.indkern302;
CREATE INDEX indkern302 ON kern.persadres (
    UPPER(postcode) ASC NULLS LAST,
    huisnr ASC NULLS LAST, UPPER(huisletter) ASC NULLS FIRST,
    UPPER(huisnrtoevoeging) ASC NULLS FIRST, srt ASC NULLS LAST);

DROP INDEX IF EXISTS kern.indkern303;
CREATE INDEX indkern303 ON kern.persadres (
    gem ASC NULLS LAST, UPPER(nor) ASC NULLS LAST,
    huisnr ASC NULLS LAST, UPPER(huisletter) ASC NULLS FIRST,
    UPPER(huisnrtoevoeging) ASC NULLS FIRST, srt ASC NULLS LAST);

DROP INDEX IF EXISTS kern.indkern304;
CREATE INDEX indkern304 ON kern.persadres (
    identcodenraand ASC NULLS LAST);

DROP INDEX IF EXISTS kern.indkern901;
CREATE INDEX indkern901 ON kern.partij (code ASC NULLS LAST);

DROP INDEX IF EXISTS kern.indkern902;
CREATE INDEX indkern902 ON kern.plaats (code ASC NULLS LAST);

DROP INDEX IF EXISTS kern.indkern903;
CREATE INDEX indkern903 ON kern.land (code ASC NULLS LAST);

DROP INDEX IF EXISTS kern.indkern904;
CREATE INDEX indkern904 ON kern.nation (nationcode ASC NULLS LAST);
