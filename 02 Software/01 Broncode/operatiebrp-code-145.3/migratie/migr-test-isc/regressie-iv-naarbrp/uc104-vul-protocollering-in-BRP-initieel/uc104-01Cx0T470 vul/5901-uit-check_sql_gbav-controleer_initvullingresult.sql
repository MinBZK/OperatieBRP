SELECT COUNT(*) AS aantal
FROM initvul.initvullingresult_protocollering
WHERE activiteit_id=(SELECT activiteit_id
FROM public.activiteit
WHERE activiteit_type=102
AND activiteit_subtype=1307)
AND pers_id=(SELECT id FROM kern.pers WHERE anr='2125109409')
AND bijhouding_opschort_reden IS NULL
AND toeganglevsautorisatie_id=(SELECT id FROM autaut.toeganglevsautorisatie WHERE levsautorisatie=(SELECT id FROM autaut.levsautorisatie WHERE naam='510221' AND datingang='20130101' AND dateinde IS NULL))
AND toeganglevsautorisatie_count=1
AND start_dt='2013-01-02 12:00:00.0'
AND laatste_actie_dt='2013-01-02 12:00:00.0'
AND conversie_resultaat='TE_VERZENDEN'
AND foutmelding IS NULL;