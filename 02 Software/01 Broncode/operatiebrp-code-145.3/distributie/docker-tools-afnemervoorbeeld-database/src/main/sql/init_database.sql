CREATE OR REPLACE FUNCTION set_currentdatabase_to_utc() RETURNS integer AS
   $$
   DECLARE Result integer;
   BEGIN
   EXECUTE 'ALTER DATABASE "' || current_database() || '"' || ' SET TIMEZONE TO ''UTC''';
   RETURN 1;
   END
   $$ LANGUAGE plpgsql;

SELECT set_currentdatabase_to_utc();
DROP FUNCTION set_currentdatabase_to_utc();


-- Schemas ---------------------------------------------------------------------
CREATE SCHEMA Afnemer;

-- Leverbericht tabel ----------------------------------------------------------
CREATE SEQUENCE afnemer.seq_leverbericht;

CREATE TABLE afnemer.leverbericht(
  id integer NOT NULL DEFAULT nextval('afnemer.seq_leverbericht'::regclass),
  bericht text,
  tsontv timestamp with time zone,
  referentienummer character varying(36),
  CONSTRAINT pk_leverbericht PRIMARY KEY (id)
);

-- Vrij bericht tabel ----------------------------------------------------------
CREATE SEQUENCE afnemer.seq_vrijbericht;

CREATE TABLE afnemer.vrijbericht(
  id integer NOT NULL DEFAULT nextval('afnemer.seq_vrijbericht'::regclass),
  bericht text,
  tsontv timestamp with time zone,
  CONSTRAINT pk_vrijbericht PRIMARY KEY (id)
);

-- Notificatie tabel ----------------------------------------------------------
CREATE SEQUENCE afnemer.seq_notificatie;

CREATE TABLE afnemer.notificatie(
  id integer NOT NULL DEFAULT nextval('afnemer.seq_notificatie'::regclass),
  bericht text,
  tsontv timestamp with time zone,
  CONSTRAINT pk_notificatie PRIMARY KEY (id)
);
