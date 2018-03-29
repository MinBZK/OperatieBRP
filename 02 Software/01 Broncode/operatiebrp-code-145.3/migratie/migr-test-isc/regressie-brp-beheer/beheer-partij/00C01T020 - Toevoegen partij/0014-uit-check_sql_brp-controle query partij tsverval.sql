SELECT h.* FROM kern.partij l
JOIN kern.his_partij h ON l.id = h.partij
WHERE l.naam = 'Test Toevoegen Partij' AND tsverval IS NULL AND h.tsreg IN (
    SELECT h.tsverval FROM kern.partij l
    JOIN kern.his_partij h ON l.id = h.partij
    WHERE l.naam = 'Test Toevoegen Partij' AND tsverval IS NOT NULL)