SELECT COUNT(*) AS aantal_01cx0t270d
FROM initvul.initvullingresult_protocollering
WHERE activiteit_id=(SELECT activiteit_id
FROM public.activiteit
WHERE activiteit_type=101
AND activiteit_subtype=1222 AND toestand=8999)
AND bijhouding_opschort_reden IS NULL
AND toeganglevsautorisatie_id IS NOT NULL
AND toeganglevsautorisatie_count>=0
AND dienst_id IS NULL
AND start_dt='2014-01-01 12:00:00'
AND laatste_actie_dt='2014-01-01 12:00:00'
AND conversie_resultaat='TE_VERZENDEN';