CREATE INDEX pers_bsn ON kern.pers USING btree (bsn COLLATE pg_catalog."default" );
CREATE INDEX persadres_pers_idx ON kern.persadres USING btree (pers );
