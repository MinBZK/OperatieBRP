SELECT count(1) AS aantal
FROM initvul.initvullingresult
WHERE inhoud_na_terugconversie IS NOT NULL;
