SELECT ht.* FROM autaut.dienstbundel l
JOIN autaut.dienst t ON l.id = t.dienstbundel
JOIN autaut.his_dienst ht ON t.id = ht.dienst
WHERE l.naam = 'Ad hoc' AND ht.tsverval IS NULL AND ht.tsreg IN (
    SELECT ht.tsverval FROM autaut.dienstbundel l
    JOIN autaut.dienst t ON l.id = t.dienstbundel
    JOIN autaut.his_dienst ht ON t.id = ht.dienst
    WHERE l.naam = 'Ad hoc' AND ht.tsverval IS NOT NULL)