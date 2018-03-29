SELECT COUNT(*) AS aantal_dienst_id
FROM initvul.initvullingresult_protocollering
WHERE dienst_id=(SELECT id
FROM autaut.dienst WHERE dienstbundel=(SELECT id FROM autaut.dienstbundel WHERE naam='Selectie' AND datingang='20130101' AND dateinde IS NULL)
AND srt=12 AND datingang='20130101' AND dateinde IS NULL);