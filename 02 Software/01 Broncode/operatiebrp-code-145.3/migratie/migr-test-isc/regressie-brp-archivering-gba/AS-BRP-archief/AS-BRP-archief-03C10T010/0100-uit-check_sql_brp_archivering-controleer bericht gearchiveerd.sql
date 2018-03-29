SELECT id
FROM   ber.ber
WHERE  srt IS NOT NULL
AND    richting = 1
AND    zendendepartij = 189
AND    zendendesysteem = 'TESTZENDER'
AND    ontvangendepartij = 199
AND    referentienr = 'REF-0002'
AND    crossreferentienr = 'REF-0001'
AND    data = 'berichtGegevens-03C10T010'
;
