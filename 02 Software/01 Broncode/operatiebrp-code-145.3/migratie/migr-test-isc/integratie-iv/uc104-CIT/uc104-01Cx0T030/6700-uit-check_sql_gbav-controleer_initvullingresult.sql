SELECT COUNT(*) AS aantal
FROM initvul.initvullingresult_protocollering
WHERE activiteit_id=(SELECT activiteit_id
FROM public.activiteit
WHERE activiteit_type=101
AND activiteit_subtype=1210)
AND
pers_id=(SELECT id 
FROM kern.pers WHERE anr='9191901473')
AND
bijhouding_opschort_reden = 'O'
AND
toeganglevsautorisatie_count=1
AND
dienst_id=(SELECT id
FROM autaut.dienst WHERE dienstbundel=(SELECT id FROM autaut.dienstbundel WHERE naam='Ad hoc' AND datingang='19980101' AND dateinde='19980301')
AND srt=3 AND datingang='19980101' AND dateinde='19980301')
AND
start_dt='1998-02-01 12:00:00.001'
AND
laatste_actie_dt='1998-02-01 12:00:00.001'
AND
conversie_resultaat='OK'
AND
foutmelding IS NULL;