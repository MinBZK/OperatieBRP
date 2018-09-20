CREATE TABLE kern.DBversion (
   ID               smallint       NOT NULL,
   FullVersion  	Varchar(200)   NOT NULL,    /* de volledige omschrijving van de versie om het moment dat de data generator werd gebouwd. */
   ReleaseVersion	Varchar(64),                /* de (svn) release versie. */
   BuildTimestamp	Varchar(32),                 /* de timestamp dat de generator werd gedraaid */
   CONSTRAINT PK_DBVersion PRIMARY KEY (ID)
);

INSERT INTO kern.DBversion (ID, FullVersion, ReleaseVersion, BuildTimestamp) VALUES (1, '${project.version} (${timestamp})', '${project.version}', '${timestamp}');
