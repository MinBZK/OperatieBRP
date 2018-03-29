/* Eerste deel haalt eventuele records van 00C01T020 Toevoegen partij weg */
DELETE FROM kern.his_partijbijhouding;
DELETE FROM kern.his_partijrol
WHERE partijrol=(SELECT id
FROM kern.partijrol
WHERE partij=(SELECT id
FROM kern.partij
WHERE code='123456')
AND rol='1');
DELETE FROM kern.his_partijrol
WHERE partijrol=(SELECT id
FROM kern.partijrol
WHERE partij=(SELECT id
FROM kern.partij
WHERE code='123456')
AND rol='2');
DELETE FROM kern.his_partijrol
WHERE partijrol=(SELECT id
FROM kern.partijrol
WHERE partij=(SELECT id
FROM kern.partij
WHERE code='123456')
AND rol='3');
DELETE FROM kern.partijrol
WHERE partij=(SELECT id
FROM kern.partij
WHERE code='123456');
DELETE FROM kern.his_partij
WHERE partij=(SELECT id
FROM kern.partij
WHERE code='123456');
DELETE FROM kern.partij
WHERE code='123456';
/* Tweede deel maakt Wijzigen partij 00C01T030 herhaalbaar */
DELETE FROM kern.his_partijrol
WHERE partijrol=(SELECT id 
FROM kern.partijrol
WHERE partij=(SELECT id
FROM kern.partij
WHERE code='111111')
AND rol='1');
DELETE FROM kern.his_partijrol
WHERE partijrol=(SELECT id 
FROM kern.partijrol
WHERE partij=(SELECT id
FROM kern.partij
WHERE code='111111')
AND rol='2');
DELETE FROM kern.his_partijrol
WHERE partijrol=(SELECT id 
FROM kern.partijrol
WHERE partij=(SELECT id
FROM kern.partij
WHERE code='111111')
AND rol='3');
DELETE FROM kern.partijrol
WHERE partij=(SELECT id
FROM kern.partij
WHERE code='111111');
DELETE FROM kern.his_partij
WHERE partij=(SELECT id
FROM kern.partij
WHERE code='111111');
DELETE FROM kern.partij
WHERE code='111111';
