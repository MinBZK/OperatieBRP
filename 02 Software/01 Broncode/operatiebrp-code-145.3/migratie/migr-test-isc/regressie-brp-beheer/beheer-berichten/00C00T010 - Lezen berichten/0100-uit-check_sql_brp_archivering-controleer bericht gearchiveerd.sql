SELECT count(*) as aantal
FROM   ber.ber
WHERE  srt = 102
AND    richting = 1
AND    zendendepartij IS NOT NULL
AND    zendendesysteem  = 'GBA-V'
AND    ontvangendepartij IS NOT NULL
AND    referentienr = 'REF-0002'
AND    crossreferentienr = 'REF-0001'
AND    data = 'berichtGegevens'
;
