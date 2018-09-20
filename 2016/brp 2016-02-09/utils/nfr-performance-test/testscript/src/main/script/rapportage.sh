#!/bin/bash

echo 'BRP versie 89'
echo 'routering=perf-mgmt02
      mutatielevering1=pap95
      mutatielevering2=pap96
      verzending=pdb98
      loadgenerator=pdb03
      protocollering=pdb04
      database=pdb97'


source omgeving.sh

echo ''
echo 'Totaal aantal personen:'
QUERY='select count(*) from kern.pers'
echo 'Query = ' $QUERY
ssh $user@$database "export PGPASSWORD=brp && psql -Ubrp --no-password -hlocalhost -dnfr_profiel1 -q -c '$QUERY'"

echo ''
echo 'Totaal aantal verstuurde berichten:'
QUERY='select count(*) from ber.ber'
echo 'Query = ' $QUERY
ssh $user@$database "export PGPASSWORD=brp && psql -Ubrp --no-password -hlocalhost -dnfr_profiel1 -q -c '$QUERY'"

echo ''
echo 'Steekproef inhoud verstuurd bericht:'
QUERY='select data from ber.ber where id in (select min(id) from ber.ber)'
echo 'Query = ' $QUERY
ssh $user@$database "export PGPASSWORD=brp && psql -Ubrp --no-password -hlocalhost -dnfr_profiel1 -q -c '$QUERY'"

echo ''
echo 'Totaal aantal toegangabonnementen:'
QUERY='select count(*) as aantal_toegangabonnementen from autaut.toegangabonnement '
echo 'Query = ' $QUERY
ssh $user@$database "export PGPASSWORD=brp && psql -Ubrp --no-password -hlocalhost -dnfr_profiel1 -q -c '$QUERY'"

echo ''
echo 'Attribuutautorisatie:'
QUERY='select e.elementnaam from
       autaut.abonnementattribuut aa,
       kern.element e
       where aa.attribuut = e.id'
echo 'Query = ' $QUERY
ssh $user@$database "export PGPASSWORD=brp && psql -Ubrp --no-password -hlocalhost -dnfr_profiel1 -q -c '$QUERY'"

echo ''
echo 'Totaal aantal verstuurde handelingen: '
QUERY='select count(distinct admhnd) from ber.ber'
echo 'Query = ' $QUERY
ssh $user@$database "export PGPASSWORD=brp && psql -Ubrp --no-password -hlocalhost -dnfr_profiel1 -q -c '$QUERY'"

echo ''
echo 'Aantal afnemerindicaties: '
QUERY='select count(*) from autaut.persafnemerindicatie'
echo 'Query = ' $QUERY
ssh $user@$database "export PGPASSWORD=brp && psql -Ubrp --no-password -hlocalhost -dnfr_profiel1 -q -c '$QUERY'"


echo ''
echo 'Aantal personen met afnemerindicaties != 10: '
QUERY='select count(*) from autaut.persafnemerindicatie group by pers having count(*) != 10'
echo 'Query = ' $QUERY
ssh $user@$database "export PGPASSWORD=brp && psql -Ubrp --no-password -hlocalhost -dnfr_profiel1 -q -c '$QUERY'"


echo ''
echo 'Aantal verstuurde berichten per partij: '
QUERY='select ontvangendepartij, count(*) from ber.ber group by ontvangendepartij'
echo 'Query = ' $QUERY
ssh $user@$database "export PGPASSWORD=brp && psql -Ubrp --no-password -hlocalhost -dnfr_profiel1 -q -c '$QUERY'"

echo ''
echo 'Aantal handelingen dat geleid heeft tot een verhuizing naar een andere gemeente: '
QUERY='select count(*)
       from
       kern.his_persadres pa,
       (select * from kern.his_persadres where actieverval in (select id from kern.actie where admhnd in (select admhnd from kern.pers))) x
       where pa.actieinh = x.actieverval and pa.gem != x.gem'
echo 'Query = ' $QUERY
ssh $user@$database "export PGPASSWORD=brp && psql -Ubrp --no-password -hlocalhost -dnfr_profiel1 -q -c '$QUERY'"


echo ''
echo 'handelingen waarvoor het aantal verstuurde berichten ongelijk is aan 10: '
QUERY='select admhnd, count(*) from ber.ber group by admhnd having count(*) != 10'
echo 'Query = ' $QUERY
ssh $user@$database "export PGPASSWORD=brp && psql -Ubrp --no-password -hlocalhost -dnfr_profiel1 -q -c '$QUERY'"

echo ''
echo 'maximale tijd tussen het versturen van de 10 berichten voor een gegeven handeling: '
QUERY='select max(y.xxx) from (select max(tsverzending) - min(tsverzending) as xxx from ber.ber group by admhnd) as y'
echo 'Query = ' $QUERY
ssh $user@$database "export PGPASSWORD=brp && psql -Ubrp --no-password -hlocalhost -dnfr_profiel1 -q -c '$QUERY'"


echo ''
echo 'tijdstip eerste / laatste  bijhouding: '
QUERY="select
       	min(to_timestamp((cast (toelichtingontlening AS numeric) / 1000))) as ts_eerste_bijhouding,
       	max(to_timestamp((cast (toelichtingontlening AS numeric) / 1000))) as ts_laatste_bijhouding
       from
       	kern.admhnd
       where
       	tslev is not null
       	and id in (select admhnd from kern.pers)"
echo 'Query = ' $QUERY
ssh $user@$database "export PGPASSWORD=brp && psql -Ubrp --no-password -hlocalhost -dnfr_profiel1 -q -c '$QUERY'"



echo ''
echo 'tijdstip eerst / laatst ontvangen bericht: '
QUERY="select
       	min(tsverzending),
       	max(tsverzending)
       from
       	ber.ber"
echo 'Query = ' $QUERY
ssh $user@$database "export PGPASSWORD=brp && psql -Ubrp --no-password -hlocalhost -dnfr_profiel1 -q -c '$QUERY'"


echo ''
echo 'maximale / minimale / gemiddelde doorlooptijd van bijhouding tot laatste bericht verzonden: '
QUERY="select
       	max(t.ts_laatst_verzonden - t.ts_bijhouding) as max_doorlooptijd,
       	min(t.ts_laatst_verzonden - t.ts_bijhouding) as min_doorlooptijd,
       	avg(t.ts_laatst_verzonden - t.ts_bijhouding) as gem_doorlooptijd
       from
       (
       select
       	a.id,
       	to_timestamp((cast (a.toelichtingontlening AS numeric) / 1000)) as ts_bijhouding,
       	max(b.tsverzending) as ts_laatst_verzonden
       from
       	kern.admhnd a,
       	ber.ber b
       where
       	a.id in (select admhnd from ber.ber)
       	and a.id = b.admhnd
       group by 1,2
       ) as t"
echo 'Query = ' $QUERY
ssh $user@$database "export PGPASSWORD=brp && psql -Ubrp --no-password -hlocalhost -dnfr_profiel1 -q -c '$QUERY'"








