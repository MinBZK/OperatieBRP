DROP TABLE IF EXISTS proefSynchronisatieBericht;

CREATE TABLE proefSynchronisatieBericht (
    id SERIAL NOT NULL,
    bericht_id BIGINT NOT NULL,
    afzender VARCHAR NOT NULL,
    bericht_datum TIMESTAMP NOT NULL,
    bericht text,
    ms_sequence_number BIGINT NOT NULL,
    verwerkt BOOLEAN,
    mailbox_nr VARCHAR(7) NOT NULL DEFAULT ('3000200'),
    PRIMARY KEY (id)
);

INSERT INTO proefSynchronisatieBericht (bericht_id, afzender, bericht_datum, bericht, ms_sequence_number, mailbox_nr)
SELECT
    ber.lo3_bericht_id,
    CASE WHEN soort_instantie = 'G' THEN to_char(mailbox.code_instantie, 'FM0000')||'01' ELSE to_char(mailbox.code_instantie, 'FM000000') END AS afzender,
    ber.creatie_dt,
    ber.bericht_data,
    ber.dispatch_sequence_number,
    spg.spg_mailbox_nummer
FROM lo3_bericht AS ber, activiteit AS act1, activiteit AS act2, spg_mailbox AS spg, lo3_mailbox mailbox
WHERE
    ber.bericht_activiteit_id = act1.activiteit_id
    AND act1.moeder_id = act2.activiteit_id
    AND (ber.kop_berichtsoort_nummer like 'Lg%' OR ber.kop_berichtsoort_nummer LIKE 'La%' OR ber.kop_berichtsoort_nummer like 'Ap%' OR ber.kop_berichtsoort_nummer LIKE 'Av%')
    AND spg.spg_mailbox_instantie = ber.spg_mailbox_instantie
    AND mailbox.lo3_mailbox_nummer = ber.originator_or_recipient
    AND ber.creatie_dt > '1970-01-01'
ORDER BY
    act2.laatste_actie_dt ASC;
    
CREATE UNIQUE INDEX proefSynchronisatieBericht_bericht_id ON proefSynchronisatieBericht (bericht_id);
CREATE INDEX proefSynchronisatieBericht_afzender ON proefSynchronisatieBericht (afzender);
CREATE INDEX proefSynchronisatieBericht_bericht_datum ON proefSynchronisatieBericht (bericht_datum);
CREATE INDEX proefSynchronisatieBericht_mailbox_verwerkt ON proefSynchronisatieBericht (mailbox_nr desc, verwerkt desc);
