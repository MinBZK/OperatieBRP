drop schema if exists migratietabel;
create schema migratietabel;

drop table if exists migratietabel.gemeente;
create table migratietabel.gemeente
(
  gemeenteCode Integer not null,
  datumBrp Integer,
  constraint gemeenteCode_pkey PRIMARY KEY (gemeenteCode)
);
