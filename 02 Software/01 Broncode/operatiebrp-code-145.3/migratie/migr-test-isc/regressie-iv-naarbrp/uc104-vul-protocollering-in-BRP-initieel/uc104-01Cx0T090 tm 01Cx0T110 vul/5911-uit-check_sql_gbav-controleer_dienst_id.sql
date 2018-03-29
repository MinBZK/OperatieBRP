SELECT COUNT(*) AS aantal_dienst_id
FROM initvul.initvullingresult_protocollering
WHERE dienst_id=(SELECT id
FROM autaut.dienst WHERE dienstbundel=(SELECT id FROM autaut.dienstbundel WHERE naam='Selectie' AND datingang='20120101' AND dateinde='20140102')
AND srt=12 AND datingang='20120101' AND dateinde='20140102')
OR
dienst_id=(SELECT id
FROM autaut.dienst WHERE dienstbundel=(SELECT id FROM autaut.dienstbundel WHERE naam='Spontaan' AND datingang='20120101' AND dateinde='20140102')
AND srt=2 AND datingang='20120101' AND dateinde='20140102')
OR
dienst_id=(SELECT id
FROM autaut.dienst WHERE dienstbundel=(SELECT id FROM autaut.dienstbundel WHERE naam='Ad hoc' AND datingang='20120101' AND dateinde='20140102')
AND srt=8 AND datingang='20120101' AND dateinde='20140102');