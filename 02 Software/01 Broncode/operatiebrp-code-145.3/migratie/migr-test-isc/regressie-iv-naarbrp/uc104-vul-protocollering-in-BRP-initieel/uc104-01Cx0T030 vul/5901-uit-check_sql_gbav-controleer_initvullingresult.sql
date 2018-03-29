SELECT COUNT(*) AS aantal
FROM initvul.initvullingresult_protocollering
WHERE activiteit_id=(SELECT activiteit_id FROM public.activiteit WHERE activiteit_type=101 AND activiteit_subtype=1210)
AND pers_id=(SELECT id FROM kern.pers WHERE anr='9191901473')
AND bijhouding_opschort_reden = 'O'
AND toeganglevsautorisatie_id=(SELECT id FROM autaut.toeganglevsautorisatie WHERE levsautorisatie=(SELECT id FROM autaut.levsautorisatie WHERE naam='510001' AND datingang='19980101' AND dateinde='19980301'))
AND toeganglevsautorisatie_count=1
AND start_dt='1998-02-01 12:00:00.0'
AND laatste_actie_dt='1998-02-01 12:00:00.0'
AND conversie_resultaat='TE_VERZENDEN'
AND foutmelding IS NULL;