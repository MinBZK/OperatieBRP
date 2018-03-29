SELECT count(1) AS aantal
FROM initvul.initvullingresult_aut
WHERE conversie_resultaat = 'FOUT'
AND conversie_melding = 'AUT006';