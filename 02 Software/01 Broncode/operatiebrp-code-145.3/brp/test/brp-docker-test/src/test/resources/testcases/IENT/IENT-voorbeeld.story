Meta:
@status             Klaar

Narrative: Reproductie IENT bug ahv databasedump


Scenario:   1. Hoe staat persoonsbeeld in de database ?

!-- verwijs naar een lokaal bestand met dump, de mountpaden staan gedefinieerd op de Docker database component in @DockerMountInfo (zie bv BrpDatabaseDocker)
!-- backups moeten in src/test/resources/testcases/IENT/databasedump komen te staan
Given een databasedump uit bestand DBdump141.backup in database brp

!-- log persoonsbeeld(en)
Then log persoonsbeeld voor persoon met bsn 229676868 met prefix IENT2873
