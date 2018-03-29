UPDATE autaut.toeganglevsautorisatie
SET naderepopulatiebeperking='Persoon.Bijhouding.PartijCode E= "059901"'
WHERE levsautorisatie = (SELECT id FROM autaut.levsautorisatie WHERE naam = '999901');