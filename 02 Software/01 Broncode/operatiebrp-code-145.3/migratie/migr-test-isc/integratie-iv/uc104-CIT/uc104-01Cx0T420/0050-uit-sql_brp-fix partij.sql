DELETE FROM kern.his_partijrol
WHERE partijrol = (SELECT id
                   FROM kern.partijrol
                   WHERE partij IN (SELECT id
                                    FROM kern.partij
                                    WHERE code = '510001'));
DELETE FROM kern.partijrol
WHERE partij = (SELECT id
                FROM kern.partij
                WHERE code = '510001');
DELETE FROM kern.his_partij
WHERE partij = (SELECT id
                FROM kern.partij
                WHERE code = '510001');
DELETE FROM kern.his_partijvrijber
WHERE partij = (SELECT id
                FROM kern.partij
                WHERE code = '510001');
DELETE FROM kern.partij
WHERE code = '510001';

INSERT INTO kern.partij (naam, code, datingang, indverstrbeperkingmogelijk, indag)
VALUES ('Afnemer Rotterdam', '510001', '19970801', FALSE, true);
INSERT INTO kern.his_partij (partij, tsreg, naam, datingang, indverstrbeperkingmogelijk)
  SELECT
    id,
    now(),
    naam,
    datingang,
    indverstrbeperkingmogelijk
  FROM kern.partij
  WHERE code = '510001';
