UPDATE autaut.toeganglevsautorisatie
SET dateinde=20000201
WHERE levsautorisatie = (SELECT id FROM autaut.levsautorisatie WHERE naam = '999901');