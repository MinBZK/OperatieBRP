DROP SCHEMA IF EXISTS Afnemer CASCADE;
CREATE SCHEMA Afnemer;

CREATE SEQUENCE Afnemer.seq_Leverbericht START WITH 1;
CREATE TABLE Afnemer.Leverbericht (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Afnemer.seq_Leverbericht'),
   bericht                       Text                         ,
   abonnement                    Varchar(200)                  ,
   tsontv                        Timestamp with time zone      ,
   zendendepartij                Integer                      ,
   ontvangendepartij             Integer                      ,
   referentienummer              Varchar(36)                   ,
   CONSTRAINT pk_GegevenInTerugmelding PRIMARY KEY (ID)
);
ALTER SEQUENCE Afnemer.seq_Leverbericht;
