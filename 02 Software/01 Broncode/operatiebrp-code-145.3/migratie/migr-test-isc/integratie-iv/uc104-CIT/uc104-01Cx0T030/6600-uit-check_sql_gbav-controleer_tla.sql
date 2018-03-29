SELECT COUNT(*) AS aantal_tla
FROM initvul.initvullingresult_protocollering
WHERE toeganglevsautorisatie_id=(SELECT id 
FROM autaut.toeganglevsautorisatie WHERE levsautorisatie=(SELECT id FROM autaut.levsautorisatie WHERE naam='510001' AND datingang='19980101' AND dateinde='19980301'));