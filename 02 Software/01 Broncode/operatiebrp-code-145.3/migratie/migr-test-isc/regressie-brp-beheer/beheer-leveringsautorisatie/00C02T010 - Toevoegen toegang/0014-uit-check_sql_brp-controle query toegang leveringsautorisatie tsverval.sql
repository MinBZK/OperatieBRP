SELECT ht.* FROM autaut.levsautorisatie l
JOIN autaut.toeganglevsautorisatie t ON l.id = t.levsautorisatie
JOIN autaut.his_toeganglevsautorisatie ht ON t.id = ht.toeganglevsautorisatie
WHERE l.naam = 'TEST' AND ht.tsverval IS NULL AND ht.tsreg IN (
    SELECT ht.tsverval FROM autaut.levsautorisatie l
    JOIN autaut.toeganglevsautorisatie t ON l.id = t.levsautorisatie
    JOIN autaut.his_toeganglevsautorisatie ht ON t.id = ht.toeganglevsautorisatie
    WHERE l.naam = 'TEST' AND ht.tsverval IS NOT NULL)