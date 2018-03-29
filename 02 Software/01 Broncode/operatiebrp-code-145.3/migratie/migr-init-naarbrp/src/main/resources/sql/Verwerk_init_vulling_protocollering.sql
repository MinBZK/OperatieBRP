SELECT *
FROM initvul.initvullingresult_protocollering
WHERE
  initvul.initvullingresult_protocollering.conversie_resultaat = :conversie_resultaat
ORDER BY initvul.initvullingresult_protocollering.activiteit_id
LIMIT :limit