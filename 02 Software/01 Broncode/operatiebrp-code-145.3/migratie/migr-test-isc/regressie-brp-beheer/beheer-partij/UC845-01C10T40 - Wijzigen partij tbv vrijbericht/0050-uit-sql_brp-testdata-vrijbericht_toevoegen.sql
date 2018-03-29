DELETE FROM kern.his_partij where partij=(select id from kern.partij where code = '999991');
DELETE FROM kern.his_partij where partij=(select id from kern.partij where code = '999992');
DELETE FROM kern.his_partij where partij=(select id from kern.partij where code = '999993');
DELETE FROM kern.his_partij where partij=(select id from kern.partij where code = '999994');
DELETE FROM kern.his_partij where partij=(select id from kern.partij where code = '999995');
DELETE FROM kern.his_partij where partij=(select id from kern.partij where code = '999996');
DELETE FROM kern.his_partij where partij=(select id from kern.partij where code = '999997');
DELETE FROM kern.his_partij where partij=(select id from kern.partij where code = '999998');
DELETE FROM kern.his_partij where partij=(select id from kern.partij where code = '999990');
DELETE FROM kern.his_partij where partij=(select id from kern.partij where code = '999989');
DELETE FROM kern.his_partij where partij=(select id from kern.partij where code = '999988');

DELETE FROM kern.his_partijvrijber where partij=(select id from kern.partij where code = '999991');
DELETE FROM kern.his_partijvrijber where partij=(select id from kern.partij where code = '999992');
DELETE FROM kern.his_partijvrijber where partij=(select id from kern.partij where code = '999993');
DELETE FROM kern.his_partijvrijber where partij=(select id from kern.partij where code = '999994');
DELETE FROM kern.his_partijvrijber where partij=(select id from kern.partij where code = '999995');
DELETE FROM kern.his_partijvrijber where partij=(select id from kern.partij where code = '999996');
DELETE FROM kern.his_partijvrijber where partij=(select id from kern.partij where code = '999997');
DELETE FROM kern.his_partijvrijber where partij=(select id from kern.partij where code = '999998');
DELETE FROM kern.his_partijvrijber where partij=(select id from kern.partij where code = '999990');
DELETE FROM kern.his_partijvrijber where partij=(select id from kern.partij where code = '999989');
DELETE FROM kern.his_partijvrijber where partij=(select id from kern.partij where code = '999988');


DELETE FROM kern.his_partijvrijber where transporteurvrijber=(select id from kern.partij where code = '999991');
DELETE FROM kern.his_partijvrijber where transporteurvrijber=(select id from kern.partij where code = '999992');
DELETE FROM kern.his_partijvrijber where transporteurvrijber=(select id from kern.partij where code = '999993');
DELETE FROM kern.his_partijvrijber where transporteurvrijber=(select id from kern.partij where code = '999994');
DELETE FROM kern.his_partijvrijber where transporteurvrijber=(select id from kern.partij where code = '999995');
DELETE FROM kern.his_partijvrijber where transporteurvrijber=(select id from kern.partij where code = '999996');
DELETE FROM kern.his_partijvrijber where transporteurvrijber=(select id from kern.partij where code = '999997');
DELETE FROM kern.his_partijvrijber where transporteurvrijber=(select id from kern.partij where code = '999998');
DELETE FROM kern.his_partijvrijber where transporteurvrijber=(select id from kern.partij where code = '999990');
DELETE FROM kern.his_partijvrijber where transporteurvrijber=(select id from kern.partij where code = '999989');
DELETE FROM kern.his_partijvrijber where transporteurvrijber=(select id from kern.partij where code = '999988');

DELETE FROM beh.vrijberpartij where partij = (select id from kern.partij where code = '999988');
DELETE FROM beh.vrijberpartij where partij = (select id from kern.partij where code = '999989');
DELETE FROM beh.vrijberpartij where partij = (select id from kern.partij where code = '999990');
DELETE FROM beh.vrijberpartij where partij = (select id from kern.partij where code = '999991');
DELETE FROM beh.vrijberpartij where partij = (select id from kern.partij where code = '999992');
DELETE FROM beh.vrijberpartij where partij = (select id from kern.partij where code = '999993');
DELETE FROM beh.vrijberpartij where partij = (select id from kern.partij where code = '999994');
DELETE FROM beh.vrijberpartij where partij = (select id from kern.partij where code = '999995');
DELETE FROM beh.vrijberpartij where partij = (select id from kern.partij where code = '999996');
DELETE FROM beh.vrijberpartij where partij = (select id from kern.partij where code = '999997');
DELETE FROM beh.vrijberpartij where partij = (select id from kern.partij where code = '999998');
UPDATE kern.partij set ondertekenaarvrijber=CAST(NULL AS SMALLINT) where ondertekenaarvrijber=(select id from kern.partij where code = '999991');
UPDATE kern.partij set transporteurvrijber=CAST(NULL AS SMALLINT) where transporteurvrijber=(select id from kern.partij where code = '999991');
delete from kern.his_partijbijhouding;
DELETE FROM kern.partij where code = '999988' or code = '999989' or code = '999990' or code = '999991' or code = '999992' or code = '999993' or code = '999994' or code = '999995' or code = '999996' or code = '999997' or code = '999998';

INSERT INTO kern.partij
(code, naam, datingang, dateinde, oin, srt, indverstrbeperkingmogelijk, datovergangnaarbrp, indag, indautofiat, indagbijhouding, ondertekenaarvrijber, transporteurvrijber, datingangvrijber, dateindevrijber, afleverpuntvrijber, indblokvrijber, indagvrijber) VALUES
(999991, 'Gemeente vrijbericht 1 geldig', 20160101, null, 999991, null, false, 20160101, true, true, false, null, null, 20160101, null, 'http://192.168.212.28:12049/ontvanger', null, true),
(999992, 'Gemeente vrijbericht 2 geen afleverpunt', 20160101, null, 999992, null, false, 20160101, true, true, false, null, null, 20160101, null, null, null, true),
(999993, 'Gemeente vrijbericht 3 geblokkeerd', 20160101, null, 999993, null, false, 20160101, true, true, false, null, null, 20160101, null, 'http://192.168.212.28:12049/ontvanger', true, true),
(999994, 'Gemeente vrijbericht 4 geen overgang BRP', 20160101, null, 999994, null, false, null, true, true, false, null, null, 20160101, null, 'http://192.168.212.28:12049/ontvanger', null, true),
(999995, 'Gemeente vrijbericht 5 datumingang vandaag', (SELECT to_number(to_char(current_date, 'YYYYMMDD'), '99999999')), null, 999995, null, false, 20160101, true, true, false, null, null, 20160101, null, 'http://192.168.212.28:12049/ontvanger', null, true),
(999996, 'Gemeente vrijbericht 6 datumingang morgen', (SELECT to_number(to_char(current_date + interval '1 days', 'YYYYMMDD'), '99999999')), null, 999996, null, false, 20160101, true, true, false, null, null, 20160101, null, 'http://192.168.212.28:12049/ontvanger', null, true),
(999997, 'Gemeente vrijbericht 7 datumingangvrijber morgen', 20160101, null, 999997, null, false, 20160101, true, true, false, null, null, (SELECT to_number(to_char(current_date + interval '1 days', 'YYYYMMDD'), '99999999')), null, 'http://192.168.212.28:12049/ontvanger', null, true),
(999998, 'Gemeente vrijbericht 8 datumeindevrijber gister', 20160101, null, 999998, null, false, 20160101, true, true, false, null, null, 20160101, (SELECT to_number(to_char(current_date - interval '1 days', 'YYYYMMDD'), '99999999')), 'http://192.168.212.28:12049/ontvanger', null, true),
(999990, 'Gemeente vrijbericht 9 datumeinde gister', 20160101, (SELECT to_number(to_char(current_date - interval '1 days', 'YYYYMMDD'), '99999999')), 999990, null, false, 20160101, true, true, false, null, null, 20160101, null, 'http://192.168.212.28:12049/ontvanger', null, true),
(999989, 'Gemeente vrijbericht 10 indagvrijber false', 20160101, null, 999989, null, false, 20160101, true, true, false, null, null, 20160101, null, 'http://192.168.212.28:12049/ontvanger', null, false);
