-- ------------------------------------------------------------------------
-- De in dit bestand opgenomen SQL statements zijn nodig voor het
-- aanmaken van de benodigde indexen na het opbouwen van de database.
-- In principe zouden deze door het BMR in de brp.sql moeten worden
-- gegenereerd, maar zolang dat niet zo is, kunnen ze middels dit
-- script worden gedraaid.
-- bolie: woensdag 12 december 2012: aangepast voor 1.0.3
-- Dit bestand was er omdat de performanence voor grote database erg traag maakt.
--
-- Merkop: Dit bestand zal vanaf versie 12 verdwijnen. Alle indexen staat dan in 00-brp-3-addIndexen.sql
--
-- ------------------------------------------------------------------------

-- is al opgenomen -- DROP INDEX IF EXISTS kern.indkern300;
-- is al opgenomen -- CREATE INDEX indkern300 ON Kern.Pers (bsn);

-- is al opgenomen -- DROP INDEX IF EXISTS kern.indkern301;
-- is al opgenomen -- CREATE INDEX indkern301 ON Kern.Pers (anr);

DROP INDEX IF EXISTS autaut.indaut001;
CREATE INDEX indaut001 ON autaut.authenticatiemiddel (
    certificaat ASC NULLS LAST, partij ASC NULLS LAST);

-- is al opgenomen -- DROP INDEX IF EXISTS kern.indkern302;
-- is al opgenomen -- CREATE INDEX indkern302 ON kern.persadres (
-- is al opgenomen --     UPPER(postcode) ASC NULLS LAST,
-- is al opgenomen --     huisnr ASC NULLS LAST, UPPER(huisletter) ASC NULLS FIRST,
-- is al opgenomen --     UPPER(huisnrtoevoeging) ASC NULLS FIRST, srt ASC NULLS LAST);

-- is al opgenomen -- DROP INDEX IF EXISTS kern.indkern303;
-- is al opgenomen -- CREATE INDEX indkern303 ON kern.persadres (
-- is al opgenomen --     gem ASC NULLS LAST, UPPER(nor) ASC NULLS LAST,
-- is al opgenomen --     huisnr ASC NULLS LAST, UPPER(huisletter) ASC NULLS FIRST,
-- is al opgenomen --     UPPER(huisnrtoevoeging) ASC NULLS FIRST, srt ASC NULLS LAST);

-- is al opgenomen -- DROP INDEX IF EXISTS kern.indkern304;
-- is al opgenomen -- CREATE INDEX indkern304 ON kern.persadres (
-- is al opgenomen --     identcodenraand ASC NULLS LAST);

DROP INDEX IF EXISTS kern.indkern305;
CREATE INDEX indkern305 ON kern.persadres (
    identcodeadresseerbaarobject ASC NULLS LAST);
