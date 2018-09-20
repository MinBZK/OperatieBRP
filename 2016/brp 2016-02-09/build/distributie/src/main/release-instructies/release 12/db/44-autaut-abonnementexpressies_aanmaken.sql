CREATE OR REPLACE FUNCTION vullen_abonnement_expressies() RETURNS VOID AS $$
DECLARE
    aborecord RECORD;
BEGIN
    delete from autaut.abonnementexpressie;
    FOR aborecord IN SELECT id FROM autaut.abonnement LOOP
        insert into autaut.abonnementexpressie (abonnement, expressie, actief, rol) select aborecord.id, e.id, true, 1 from autaut.expressie e;
    END LOOP;
END;
$$ LANGUAGE plpgsql;

SELECT * FROM vullen_abonnement_expressies();

DROP FUNCTION vullen_abonnement_expressies();
