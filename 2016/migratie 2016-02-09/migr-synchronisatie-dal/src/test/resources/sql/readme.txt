SQL Bestanden:

Handleiding creeeren lokale postgres database:

- creeer database BRP (in onderstaande volgorde)
- run script: 1_brp-struktuur.sql
- run script: 2_brp-vulling-statisch.sql
- run script: 3_brp-vulling-dynamisch.sql
- run script: postgres/blokkering-snapshot.sql [TODO: nog verwerken in BMR]

==========Belangrijk: lees onderstaande opmerking=============

De bronbestanden voor bovenstaande sql scripts staan in:

- postgres/bmr
- data/1_stamgegevens_bmr
- data/2_stamgegevens_niet_bmr

Wanneer deze zijn aangepast dienen de drie (1_brp-struktuur.sql, 2_brp-vulling-statisch.sql, 3_brp-vulling-dynamisch.sql) brp scripts 
opnieuw te worden gegenereerd. Dit kan worden gedaan m.b.v. het volgende shell script uit te voeren in de map src/test/resources/sql:

gen_brp_sql.sh

