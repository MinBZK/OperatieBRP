SELECT id
FROM   ber.ber
WHERE  srt IS NOT NULL
AND    richting = 1
AND    zendendepartij IS NOT NULL
AND    zendendesysteem IS NULL
AND    ontvangendepartij IS NOT NULL
AND    referentienr = 'REF-0002'
AND    crossreferentienr = 'REF-0001'
AND    data = 'berichtGegevens'
;
