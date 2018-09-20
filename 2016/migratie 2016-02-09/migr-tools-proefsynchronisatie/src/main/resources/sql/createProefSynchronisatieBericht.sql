DROP TABLE IF EXISTS proefSynchronisatieBericht;

CREATE TABLE proefSynchronisatieBericht (
    id SERIAL NOT NULL,
    bericht_id BIGINT NOT NULL,
    afzender INTEGER NOT NULL,
    bericht_datum TIMESTAMP NOT NULL,
    bericht text,
    ms_sequence_number BIGINT NOT NULL,
    verwerkt BOOLEAN,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX proefSynchronisatieBericht_bericht_id ON proefSynchronisatieBericht (bericht_id);
CREATE INDEX proefSynchronisatieBericht_afzender ON proefSynchronisatieBericht (afzender);
CREATE INDEX proefSynchronisatieBericht_bericht_datum ON proefSynchronisatieBericht (bericht_datum);

INSERT INTO proefSynchronisatieBericht (bericht_id, afzender, bericht_datum, bericht, ms_sequence_number)
SELECT
    ber.lo3_bericht_id,
    CASE WHEN (ber.originator_or_recipient LIKE '%010') THEN CAST(substr(ber.originator_or_recipient, 1, 4) AS INT) ELSE CAST(ber.originator_or_recipient AS INT) END ,
    ber.creatie_dt,
    ber.bericht_data,
    ber.dispatch_sequence_number FROM lo3_bericht AS ber, activiteit AS act1, activiteit AS act2
WHERE
    ber.bericht_activiteit_id = act1.activiteit_id
    AND act1.moeder_id = act2.activiteit_id
    AND (ber.kop_berichtsoort_nummer like 'Lg%' OR ber.kop_berichtsoort_nummer LIKE 'La%')
    AND ber.creatie_dt > ?
ORDER BY
    act2.laatste_actie_dt ASC;