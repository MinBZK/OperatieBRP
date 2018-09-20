drop schema if exists autaut cascade;
drop schema if exists ber cascade;
drop schema if exists brm cascade;
drop schema if exists conv cascade;
drop schema if exists ist cascade;
drop schema if exists kern cascade;
drop schema if exists lev cascade;
drop schema if exists verconv cascade;

drop table if exists databasechangelog cascade;
drop table if exists databasechangeloglock cascade;
drop table if exists dbversion cascade;

drop function if exists block_dubbel_r10100convadellijketitelpredikaat() cascade;
drop function if exists block_dubbel_r10110convaangifteadresh() cascade;
drop function if exists block_dubbel_r10301toegangabonnement() cascade;
drop function if exists block_dubbel_r10346persafnemerindicatie() cascade;
drop function if exists block_dubbel_r10731lo3voorkomen() cascade;
drop function if exists block_dubbel_r10964persverstrbeperking() cascade;
drop function if exists block_dubbel_r5749bijhsituatie() cascade;
drop function if exists block_dubbel_r9176actiebron() cascade;
drop function if exists set_currentdatabase_to_utc() cascade;
drop function if exists update_handelingen() cascade;

drop schema if exists blokkering cascade;
drop schema if exists logging cascade;
