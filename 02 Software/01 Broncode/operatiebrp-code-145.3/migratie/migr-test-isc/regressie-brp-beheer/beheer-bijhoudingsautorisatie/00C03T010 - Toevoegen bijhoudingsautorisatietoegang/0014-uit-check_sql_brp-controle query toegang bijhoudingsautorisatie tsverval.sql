SELECT ht.* FROM autaut.bijhautorisatie l
JOIN autaut.toegangbijhautorisatie t ON l.id = t.bijhautorisatie
JOIN autaut.his_toegangbijhautorisatie ht ON t.id = ht.toegangbijhautorisatie
WHERE l.naam = 'Test Toegang Bijhoudingsautorisatie' AND ht.tsverval IS NULL AND ht.tsreg IN (
    SELECT ht.tsverval FROM autaut.bijhautorisatie l
    JOIN autaut.toegangbijhautorisatie t ON l.id = t.bijhautorisatie
    JOIN autaut.his_toegangbijhautorisatie ht ON t.id = ht.toegangbijhautorisatie
    WHERE l.naam = 'Test Toegang Bijhoudingsautorisatie' AND ht.tsverval IS NOT NULL)