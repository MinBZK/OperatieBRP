SELECT *
FROM public.activiteit 
WHERE activiteit_type='101' AND activiteit_subtype='1223' 
OR activiteit_type='101' AND activiteit_subtype='1226'
OR activiteit_type='202' AND activiteit_subtype='1214'
ORDER BY activiteit_subtype;