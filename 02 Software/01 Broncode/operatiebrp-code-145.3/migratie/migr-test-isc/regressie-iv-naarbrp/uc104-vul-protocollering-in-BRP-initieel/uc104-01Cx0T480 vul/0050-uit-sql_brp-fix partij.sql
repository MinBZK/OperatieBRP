DELETE FROM kern.his_partijrol
WHERE partijrol = (SELECT id
                   FROM kern.partijrol
                   WHERE partij IN (SELECT id
                                    FROM kern.partij
                                    WHERE code = '510221'));

DELETE FROM kern.partijrol
WHERE partij = (SELECT id
                FROM kern.partij
                WHERE code = '510221');

DELETE FROM kern.his_partijvrijber
WHERE partij = (SELECT id
                FROM kern.partij
                WHERE code = '510221');

DELETE FROM kern.his_partij
WHERE partij = (SELECT id
                FROM kern.partij
                WHERE code = '510221');

DELETE FROM kern.partij
WHERE code = '510221';

INSERT INTO kern.partij (naam, code, datingang, indverstrbeperkingmogelijk, indag)
VALUES ('Afnemer Voorschoten', '510221', '20010901', FALSE, true);
INSERT INTO kern.his_partij (partij, tsreg, naam, datingang, indverstrbeperkingmogelijk)
  SELECT
    id,
    now(),
    naam,
    datingang,
    indverstrbeperkingmogelijk
  FROM kern.partij
  WHERE code = '510221';
