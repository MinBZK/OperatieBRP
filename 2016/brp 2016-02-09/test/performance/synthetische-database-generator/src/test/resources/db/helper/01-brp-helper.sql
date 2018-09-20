DROP SCHEMA IF EXISTS helper CASCADE;
CREATE SCHEMA helper;
CREATE TABLE IF NOT EXISTS helper.brppers (
	id				integer,
	setnr			integer,
	logischpersid	integer,
	bsn				integer
);

CREATE TABLE IF NOT EXISTS helper.brprelatie (
	id				integer,
	setnr			integer,
	srt				smallint,
	logischrelatieid	integer
);

CREATE INDEX brppers_id_idx ON helper.brppers (id);
CREATE INDEX brppers_log_idx ON helper.brppers (setnr, logischpersid);

CREATE INDEX brprelatie_id_idx ON helper.brprelatie (id);
CREATE INDEX brprelatie_log_idx ON helper.brprelatie (setnr, logischrelatieid);

--create index on kern.persadres(addreerbaarObject);
--create index on kern.persadres(identificatie);