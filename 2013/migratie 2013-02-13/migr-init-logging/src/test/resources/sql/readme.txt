SQL Bestanden:

- postgres directory: uit BRP gegenereerde bestanden om objecten te creeeren
- data: uit BRP gegenereerde bestanden om data te vullen
- hsqldb: test bestanden om tests op de in memory database te draaien


Handleiding creeeren lokale postgres database:

- creeer database BRP
- run script: postgres\brp-snapshot.sql
- run script: data\insertStatischeStamgegevens.sql
- run script: hsqldb\createConversietabelSchema.sql
