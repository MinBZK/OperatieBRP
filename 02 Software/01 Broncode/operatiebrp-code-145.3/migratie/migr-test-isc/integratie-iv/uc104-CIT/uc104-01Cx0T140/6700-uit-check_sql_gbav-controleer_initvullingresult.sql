SELECT COUNT(*) AS aantal
FROM initvul.initvullingresult_protocollering
WHERE activiteit_id=(SELECT activiteit_id
FROM public.activiteit
WHERE activiteit_type=101
AND activiteit_subtype=1213)
AND
pers_id=(SELECT id 
FROM kern.pers WHERE anr='3930727969')
AND
bijhouding_opschort_reden IS NULL
AND
toeganglevsautorisatie_count=1
AND
dienst_id=(SELECT id
FROM autaut.dienst WHERE dienstbundel=(SELECT id FROM autaut.dienstbundel WHERE naam='Ad hoc' AND datingang='20130101' AND dateinde IS NULL)
AND srt=3 AND datingang='20130101' AND dateinde IS NULL)
AND
start_dt='2013-01-01 12:00:00.0'
AND
laatste_actie_dt='2013-01-01 12:00:00.0'
AND
conversie_resultaat='OK'
AND
foutmelding IS NULL;