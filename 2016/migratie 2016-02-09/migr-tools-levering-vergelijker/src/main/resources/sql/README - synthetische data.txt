Om op basis van synthetische data een analyse uit voeren dienen een aantal scripts uitgevoerd te worden.

Op de GBAV database (mag ook dezelfde zijn als SOA):
- leveringsvergelijking-synthetische-data-gbav-create.sql
- leveringsvergelijking-synthetische-data-gbav-vul.sql

Op de BRP database:
- leveringsvergelijking-synthetische-data-brp-create.sql
- leveringsvergelijking-synthetische-data-brp-import-gbav.sql
- leveringsvergelijking-synthetische-data-brp-vul.sql
- leveringsvergelijking-create-en-vul-brp.sql
- leveringsvergelijking-synthetische-data-brp-vergelijking.sql
- leveringsvergelijking-create-berichtcombinaties.sql

Sommige scripts maken gebruik van dblink waar naar een standaard database geconnect wordt. De parameters dienen aangepast te worden
naar die van de juiste database.

In het script leveringsvergelijking-synthetische-data-gbav-vul.sql wordt meegegeven hoeveel berichten er aangemaakt dienen te worden.



