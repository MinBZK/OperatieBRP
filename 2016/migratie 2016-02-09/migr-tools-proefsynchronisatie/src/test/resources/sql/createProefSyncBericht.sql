DROP TABLE IF EXISTS proefSynchronisatieBericht;

CREATE TABLE proefSynchronisatieBericht (
    id serial NOT NULL,
    bericht_id BIGINT NOT NULL,
    afzender INTEGER NOT NULL,
    bericht_datum TIMESTAMP NOT NULL,
    bericht text,
    ms_sequence_number BIGINT NOT NULL,
    verwerkt BOOLEAN,
    primary key (id)
);

INSERT INTO proefSynchronisatieBericht (
    bericht_id,
    afzender,
    bericht_datum,
    bericht,
    ms_sequence_number
) 
  SELECT
    ber.lo3_bericht_id,
    CASE WHEN (ber.originator_or_recipient LIKE '%010') THEN 599 ELSE 0 END ,
    ber.tijdstip_verzending_ontvangst,
    ber.bericht_data,
    ber.dispatch_sequence_number
  FROM 
       lo3_bericht ber
    JOIN activiteit act
      ON act.activiteit_id = ber.bericht_activiteit_id
  WHERE 
        act.activiteit_type = 100 --INKOMEND BERICHT
        AND
        (act.activiteit_subtype = 1111 OR act.activiteit_subtype = 1112) --LG01 OF LA01
        AND
        act.toestand = 8000
        AND
        ber.tijdstip_verzending_ontvangst > '1970-01-01'
  ORDER BY 
    ber.tijdstip_verzending_ontvangst 
    ASC;

