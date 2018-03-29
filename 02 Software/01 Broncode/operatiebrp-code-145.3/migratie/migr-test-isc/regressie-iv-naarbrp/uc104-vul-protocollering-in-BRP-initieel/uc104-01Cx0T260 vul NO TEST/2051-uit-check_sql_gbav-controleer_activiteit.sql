SELECT COUNT(*) AS aantal_activiteit
FROM public.activiteit 
WHERE activiteit_type='101'
AND activiteit_subtype='1223'
AND toestand='8800'
AND communicatie_partner='510001';