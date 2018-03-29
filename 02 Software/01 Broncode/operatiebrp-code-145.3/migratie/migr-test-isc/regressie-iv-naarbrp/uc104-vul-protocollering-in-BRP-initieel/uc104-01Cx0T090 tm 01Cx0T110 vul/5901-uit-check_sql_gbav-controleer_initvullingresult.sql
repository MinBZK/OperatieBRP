SELECT COUNT(*) AS aantal
FROM initvul.initvullingresult_protocollering
WHERE (activiteit_id=(SELECT activiteit_id FROM public.activiteit WHERE activiteit_type=101 AND activiteit_subtype=1226)
AND pers_id=(SELECT id FROM kern.pers WHERE anr='4191495713')
AND bijhouding_opschort_reden IS NULL
AND toeganglevsautorisatie_id=(SELECT id FROM autaut.toeganglevsautorisatie WHERE levsautorisatie=(SELECT id FROM autaut.levsautorisatie WHERE naam='510001' AND datingang='20120101' AND dateinde='20140102'))
AND toeganglevsautorisatie_count=1
AND start_dt='2013-01-01 12:00:00.0'
AND laatste_actie_dt='2013-01-01 12:00:00.0'
AND conversie_resultaat='TE_VERZENDEN'
AND foutmelding IS NULL)
OR
(activiteit_id=(SELECT activiteit_id FROM public.activiteit WHERE activiteit_type=101 AND activiteit_subtype=1223)
AND pers_id=(SELECT id FROM kern.pers WHERE anr='4191495713')
AND bijhouding_opschort_reden IS NULL
AND toeganglevsautorisatie_id=(SELECT id FROM autaut.toeganglevsautorisatie WHERE levsautorisatie=(SELECT id FROM autaut.levsautorisatie WHERE naam='510001' AND datingang='20120101' AND dateinde='20140102'))
AND toeganglevsautorisatie_count=1
AND start_dt='2014-01-01 12:00:00.0'
AND laatste_actie_dt='2014-01-01 12:00:00.0'
AND conversie_resultaat='TE_VERZENDEN'
AND foutmelding IS NULL)
OR
(activiteit_id=(SELECT activiteit_id FROM public.activiteit WHERE activiteit_type=202 AND activiteit_subtype=1214)
AND pers_id=(SELECT id FROM kern.pers WHERE anr='4191495713')
AND bijhouding_opschort_reden IS NULL
AND toeganglevsautorisatie_id=(SELECT id FROM autaut.toeganglevsautorisatie WHERE levsautorisatie=(SELECT id FROM autaut.levsautorisatie WHERE naam='510001' AND datingang='20120101' AND dateinde='20140102'))
AND toeganglevsautorisatie_count=1
AND start_dt='2012-01-01 12:00:00.0'
AND laatste_actie_dt='2012-01-01 12:00:00.0'
AND conversie_resultaat='TE_VERZENDEN'
AND foutmelding IS NULL);