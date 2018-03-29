SELECT h.* FROM autaut.bijhautorisatie l
JOIN autaut.his_bijhautorisatie h ON l.id = h.bijhautorisatie
WHERE l.naam = 'Wijziging Test Bijhoudingsautorisatie' AND tsverval IS NULL AND h.tsreg IN (
    SELECT h.tsverval FROM autaut.bijhautorisatie l
    JOIN autaut.his_bijhautorisatie h ON l.id = h.bijhautorisatie
    WHERE l.naam = 'Wijziging Test Bijhoudingsautorisatie' AND tsverval IS NOT NULL)