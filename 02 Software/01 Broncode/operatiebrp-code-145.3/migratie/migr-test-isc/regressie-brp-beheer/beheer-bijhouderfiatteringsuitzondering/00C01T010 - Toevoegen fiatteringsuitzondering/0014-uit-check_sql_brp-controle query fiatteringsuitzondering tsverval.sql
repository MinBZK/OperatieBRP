SELECT h.* FROM autaut.bijhouderfiatuitz l
JOIN autaut.his_bijhouderfiatuitz h ON l.id = h.bijhouderfiatuitz
WHERE l.datingang = '20160101' AND tsverval IS NULL AND h.tsreg IN (
    SELECT h.tsverval FROM autaut.bijhouderfiatuitz l
    JOIN autaut.his_bijhouderfiatuitz h ON l.id = h.bijhouderfiatuitz
    WHERE l.datingang = '20160101' AND tsverval IS NOT NULL)