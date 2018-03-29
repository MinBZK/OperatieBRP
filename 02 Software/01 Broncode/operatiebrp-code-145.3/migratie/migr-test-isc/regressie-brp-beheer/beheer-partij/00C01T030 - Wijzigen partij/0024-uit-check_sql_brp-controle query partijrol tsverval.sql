SELECT h.* FROM kern.partijrol l
JOIN kern.his_partijrol h ON l.id = h.partijrol
WHERE l.datingang = '20161231' AND tsverval IS NULL AND h.tsreg IN (
    SELECT h.tsverval FROM kern.partijrol l
    JOIN kern.his_partijrol h ON l.id = h.partijrol
    WHERE l.datingang = '20161231' AND tsverval IS NOT NULL)