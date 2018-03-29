SELECT COUNT(*) AS aantal_activiteiten
FROM public.activiteit 
WHERE (activiteit_type='101' AND activiteit_subtype='1211' AND communicatie_partner='510248')
OR (activiteit_type='101' AND activiteit_subtype='1213' AND communicatie_partner='510221')
OR (activiteit_type='101' AND activiteit_subtype='1211' AND communicatie_partner='510001')
OR (activiteit_type='101' AND activiteit_subtype='1223' AND communicatie_partner='510001');