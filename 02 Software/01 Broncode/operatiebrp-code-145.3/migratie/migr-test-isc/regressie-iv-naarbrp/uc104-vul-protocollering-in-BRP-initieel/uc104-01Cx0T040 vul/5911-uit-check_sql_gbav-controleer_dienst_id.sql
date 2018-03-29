SELECT COUNT(*) AS aantal_dienst_id
FROM initvul.initvullingresult_protocollering
WHERE dienst_id=(SELECT id
FROM autaut.dienst WHERE dienstbundel=(SELECT id FROM autaut.dienstbundel WHERE naam='Spontaan' AND datingang='20040101' AND dateinde='20050102')
AND srt=4 AND datingang='20040101' AND dateinde='20050102');