CREATE OR REPLACE FUNCTION update_handelingen() RETURNS integer AS $$
DECLARE
    persoon BIGINT;
    admhnd_inhoud BIGINT;
    actie_inhoud BIGINT;
BEGIN
    FOR persoon IN SELECT id FROM kern.pers WHERE srt = 1 ORDER BY id LOOP
        --Ah en actie voor actieinhoud
        EXECUTE 'INSERT INTO kern.admhnd (srt, partij, tsreg, tslev) values (33, 1, now(), now()) RETURNING id' INTO admhnd_inhoud;
        EXECUTE 'INSERT INTO kern.actie(srt, admhnd, partij, tsreg, datontlening) values (1, $1, 2000, now(), null) RETURNING id' INTO actie_inhoud USING admhnd_inhoud;

        --Nieuwe afgeleid administratief inserten
        EXECUTE 'INSERT INTO kern.his_persafgeleidadministrati (pers, tsreg, actieinh, admhnd, tslaatstewijz, sorteervolgorde, indonderzoeknaarnietopgenome, indonverwbijhvoorstelnieting) values ($1, now(), $2, $3, now(), 1, false, false)' USING persoon, actie_inhoud, admhnd_inhoud;

        --Updaten van de A-laag
        EXECUTE 'UPDATE kern.pers SET admhnd = $2, tslaatstewijz = now(), sorteervolgorde = 1 WHERE id = $1' USING persoon, admhnd_inhoud;
    END LOOP;

    RETURN 1;
END;
$$ LANGUAGE plpgsql;

SELECT * FROM update_handelingen();
