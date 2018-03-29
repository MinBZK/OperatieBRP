SELECT COUNT(*) AS aantal_tla
FROM initvul.initvullingresult_protocollering
WHERE toeganglevsautorisatie_id=(SELECT id 
FROM autaut.toeganglevsautorisatie WHERE levsautorisatie=(SELECT id FROM autaut.levsautorisatie WHERE naam='510248' AND datingang='19910101' AND dateinde IS NULL));