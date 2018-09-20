delete from dashboard.bericht_bsn where bericht in (select id from dashboard.berichten WHERE extract(month from tsverzonden) <= 8);
delete from dashboard.berichten WHERE extract(month from tsverzonden) <= 8;
