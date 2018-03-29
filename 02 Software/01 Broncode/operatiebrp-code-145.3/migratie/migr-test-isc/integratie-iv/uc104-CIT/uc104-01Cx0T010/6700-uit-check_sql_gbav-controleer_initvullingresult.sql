SELECT COUNT(*) AS aantal
FROM initvul.initvullingresult_protocollering
WHERE activiteit_id=(SELECT activiteit_id
FROM public.activiteit
WHERE activiteit_type=101
AND activiteit_subtype=1226)
AND
pers_id=(SELECT id 
FROM kern.pers WHERE anr='2125109409')
AND
bijhouding_opschort_reden IS NULL
AND
toeganglevsautorisatie_count=1
AND
dienst_id=(SELECT id
FROM autaut.dienst WHERE dienstbundel=(SELECT id FROM autaut.dienstbundel WHERE naam='Selectie' AND datingang='20130101' AND dateinde IS NULL)
AND srt=12 AND datingang='20130101' AND dateinde IS NULL)
AND
start_dt='2013-01-02 12:00:01.012'
AND
laatste_actie_dt='2013-01-02 12:00:01.012'
AND
conversie_resultaat='OK'
AND
foutmelding IS NULL;