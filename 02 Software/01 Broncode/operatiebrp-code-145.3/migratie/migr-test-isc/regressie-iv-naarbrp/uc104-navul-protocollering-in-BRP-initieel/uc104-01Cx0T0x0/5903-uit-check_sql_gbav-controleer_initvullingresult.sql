SELECT COUNT(*) AS aantal
FROM initvul.initvullingresult_protocollering
WHERE (activiteit_id=(SELECT activiteit_id FROM public.activiteit WHERE activiteit_type=101 AND activiteit_subtype=1211 AND toestand=8300 AND uiterlijke_actie_dt='2013-06-03 12:00:00.0')
AND pers_id=(SELECT id FROM kern.pers WHERE anr='9607472929')
AND bijhouding_opschort_reden = 'E'
AND toeganglevsautorisatie_id=(SELECT id FROM autaut.toeganglevsautorisatie WHERE levsautorisatie=(SELECT id FROM autaut.levsautorisatie WHERE naam='510248' AND datingang='20110101' AND dateinde='20150101'))
AND toeganglevsautorisatie_count=1
AND start_dt='2013-06-03 12:00:00.0'
AND laatste_actie_dt='2013-06-03 12:00:00.0'
AND conversie_resultaat='TE_VERZENDEN'
AND foutmelding IS NULL)
OR 
(activiteit_id=(SELECT activiteit_id FROM public.activiteit WHERE activiteit_type=101 AND activiteit_subtype=1213 AND toestand=8500 AND uiterlijke_actie_dt='2012-07-03 12:00:00.0')
AND pers_id=(SELECT id FROM kern.pers WHERE anr='8409813281')
AND bijhouding_opschort_reden IS NULL
AND toeganglevsautorisatie_id=(SELECT id FROM autaut.toeganglevsautorisatie WHERE levsautorisatie=(SELECT id FROM autaut.levsautorisatie WHERE naam='510221' AND datingang='20110101' AND dateinde='20150101'))
AND toeganglevsautorisatie_count=1
AND start_dt='2012-07-03 12:00:00.0'
AND laatste_actie_dt='2012-07-03 12:00:00.0'
AND conversie_resultaat='TE_VERZENDEN'
AND foutmelding IS NULL)
OR 
(activiteit_id=(SELECT activiteit_id FROM public.activiteit WHERE activiteit_type=101 AND activiteit_subtype=1223 AND toestand=8800 AND uiterlijke_actie_dt='2012-06-04 12:00:00.0')
AND pers_id=(SELECT id FROM kern.pers WHERE anr='4191495713')
AND bijhouding_opschort_reden IS NULL
AND toeganglevsautorisatie_id=(SELECT id FROM autaut.toeganglevsautorisatie WHERE levsautorisatie=(SELECT id FROM autaut.levsautorisatie WHERE naam='510001' AND datingang='20110101' AND dateinde='20150101'))
AND toeganglevsautorisatie_count=1
AND start_dt='2012-06-04 12:00:00.0'
AND laatste_actie_dt='2012-06-04 12:00:00.0'
AND conversie_resultaat='TE_VERZENDEN'
AND foutmelding IS NULL)
OR 
(activiteit_id=(SELECT activiteit_id FROM public.activiteit WHERE activiteit_type=101 AND activiteit_subtype=1211 AND toestand=8900 AND uiterlijke_actie_dt='2012-06-04 12:00:00.0')
AND pers_id=(SELECT id FROM kern.pers WHERE anr='7945717537')
AND bijhouding_opschort_reden = 'F'
AND toeganglevsautorisatie_id=(SELECT id FROM autaut.toeganglevsautorisatie WHERE levsautorisatie=(SELECT id FROM autaut.levsautorisatie WHERE naam='510001' AND datingang='20110101' AND dateinde='20150101'))
AND toeganglevsautorisatie_count=1
AND start_dt='2012-06-04 12:00:00.0'
AND laatste_actie_dt='2012-06-04 12:00:00.0'
AND conversie_resultaat='TE_VERZENDEN'
AND foutmelding IS NULL)