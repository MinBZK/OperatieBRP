UPDATE autaut.dienst
SET dateinde=20000201
WHERE dienstbundel = (SELECT id FROM autaut.dienstbundel WHERE levsautorisatie = (SELECT id FROM autaut.levsautorisatie WHERE naam = '999901'));