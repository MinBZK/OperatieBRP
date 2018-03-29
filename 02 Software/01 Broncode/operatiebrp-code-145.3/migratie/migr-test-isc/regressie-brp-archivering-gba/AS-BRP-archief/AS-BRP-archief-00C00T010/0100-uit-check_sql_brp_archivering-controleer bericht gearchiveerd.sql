SELECT id
FROM   ber.ber
WHERE  srt IS NULL
AND    richting = 1
AND    zendendepartij = 189
AND    zendendesysteem = 'TESTSYSTEEM'
AND    ontvangendepartij IS NOT NULL
AND    referentienr = 'REF-0002'
AND    crossreferentienr = 'REF-0001'
AND    data = 'berichtGegevens-00C00T010'
;
