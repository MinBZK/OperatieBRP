SELECT *
FROM public.activiteit 
WHERE activiteit_type='101' AND activiteit_subtype='1210' 
OR activiteit_type='101' AND activiteit_subtype='1222'
ORDER BY activiteit_subtype;