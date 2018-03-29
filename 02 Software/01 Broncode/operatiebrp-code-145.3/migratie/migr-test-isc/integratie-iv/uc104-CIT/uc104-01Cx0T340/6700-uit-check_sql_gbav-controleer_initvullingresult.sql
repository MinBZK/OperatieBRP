SELECT COUNT(*) AS aantal
FROM initvul.initvullingresult_protocollering
WHERE activiteit_id=(SELECT activiteit_id
FROM public.activiteit
WHERE activiteit_type=101
AND activiteit_subtype=1213)
AND pers_id=(SELECT id FROM kern.pers WHERE anr='6237279521')
AND bijhouding_opschort_reden IS NULL
AND toeganglevsautorisatie_count=2
AND dienst_id IS NULL
AND start_dt='1998-01-01 12:00:00.0'
AND laatste_actie_dt='1998-01-01 12:00:00.0'
AND conversie_resultaat='FOUT'
AND foutmelding='Er zijn meerdere Toegang leveringsautorisaties gevonden';