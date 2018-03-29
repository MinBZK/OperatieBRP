SELECT COUNT(*) AS aantal
FROM initvul.initvullingresult_protocollering
WHERE activiteit_id=(SELECT activiteit_id
FROM public.activiteit
WHERE activiteit_type=101
AND activiteit_subtype=1226)
AND pers_id=(SELECT id FROM kern.pers WHERE anr='4869635617')
AND bijhouding_opschort_reden='O'
AND toeganglevsautorisatie_count=1
AND dienst_id=(SELECT id FROM autaut.dienst WHERE dienstbundel=(SELECT id FROM autaut.dienstbundel WHERE naam='Selectie' AND datingang='19910101' AND dateinde IS NULL) AND srt=12 AND datingang='19910101' AND dateinde IS NULL)
AND start_dt='1991-02-01 12:00:00.0'
AND laatste_actie_dt='1991-02-01 12:00:00.0'
AND conversie_resultaat='OK'
AND foutmelding IS NULL;