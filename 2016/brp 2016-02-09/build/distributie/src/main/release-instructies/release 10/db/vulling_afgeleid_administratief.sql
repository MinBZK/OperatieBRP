CREATE OR REPLACE FUNCTION update_handelingen() RETURNS integer AS $$
DECLARE
    persoon RECORD;
    admhnd_inhoud BIGINT;
    admhnd_verval BIGINT;
    actie_inhoud BIGINT;
    actie_verval BIGINT;
BEGIN
    FOR persoon IN SELECT * FROM kern.pers WHERE srt = 1 ORDER BY id LOOP
	--Ah en actie voor actieinhoud
        EXECUTE 'SELECT nextval(' || quote_literal('kern.seq_admhnd') || ')' INTO admhnd_inhoud;
	EXECUTE 'SELECT nextval(' || quote_literal('kern.seq_actie') || ')' INTO actie_inhoud;
        EXECUTE 'INSERT INTO kern.admhnd (id, srt, partij, tsreg, tslev) values ($1, 33, 1, now(), now())' USING admhnd_inhoud;
	EXECUTE 'INSERT INTO kern.actie(id, srt, admhnd, partij, tsreg, datontlening) values ($1, 1, $2, 2000, now(), null)' USING actie_inhoud, admhnd_inhoud;

	--Ah en actie voor actieverval
        EXECUTE 'SELECT nextval(' || quote_literal('kern.seq_admhnd') || ')' INTO admhnd_verval;
	EXECUTE 'SELECT nextval(' || quote_literal('kern.seq_actie') || ')' INTO actie_verval;
        EXECUTE 'INSERT INTO kern.admhnd (id, srt, partij, tsreg, tslev) values ($1, 33, 2000, now(), now())' USING admhnd_verval;
	EXECUTE 'INSERT INTO kern.actie(id, srt, admhnd, partij, tsreg, datontlening) values ($1, 1, $2, 2000, now(), null)' USING actie_verval, admhnd_verval;

	--Oude afgeleid administratief laten vervallen
	EXECUTE 'update kern.his_persafgeleidadministrati set tsverval = now(), actieverval = $1 where actieverval is null and pers = $2' USING actie_verval, persoon.id;

	--Nieuwe afgeleid administratief inserten
        EXECUTE 'INSERT INTO kern.his_persafgeleidadministrati (pers, tsreg, actieinh, admhnd, tslaatstewijz, sorteervolgorde) values ($1, now(), $2, $3, now(), 1)' USING persoon.id, actie_inhoud, admhnd_inhoud; 

	--Updaten van de A-laag
        EXECUTE 'UPDATE kern.pers SET admhnd = $2, tslaatstewijz = now(), sorteervolgorde = 1 WHERE id = $1' USING persoon.id, admhnd_inhoud;
    END LOOP;

    RETURN 1;
END;
$$ LANGUAGE plpgsql;

SELECT * FROM update_handelingen();
