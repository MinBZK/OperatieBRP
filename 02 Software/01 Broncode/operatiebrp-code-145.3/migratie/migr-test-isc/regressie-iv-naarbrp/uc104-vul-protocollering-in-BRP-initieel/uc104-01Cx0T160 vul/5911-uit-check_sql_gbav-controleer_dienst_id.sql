SELECT COUNT(*) AS aantal_dienst_id
FROM initvul.initvullingresult_protocollering
WHERE dienst_id=(SELECT id
FROM autaut.dienst WHERE dienstbundel=(SELECT id FROM autaut.dienstbundel WHERE naam='Ad hoc' AND datingang='20110101' AND dateinde='20130102')
AND srt=8 AND datingang='20110101' AND dateinde='20130102');