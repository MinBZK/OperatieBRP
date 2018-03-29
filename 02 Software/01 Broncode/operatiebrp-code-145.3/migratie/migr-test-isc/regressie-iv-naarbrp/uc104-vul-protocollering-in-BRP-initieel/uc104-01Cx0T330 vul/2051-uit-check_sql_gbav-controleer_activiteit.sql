SELECT COUNT(*) AS aantal_activiteit
FROM public.activiteit 
WHERE activiteit_type='202'
AND activiteit_subtype='1214'
AND start_dt='2012-02-29 12:00:00.0'
AND laatste_actie_dt='2012-02-29 12:00:00.0'
AND communicatie_partner='510001';