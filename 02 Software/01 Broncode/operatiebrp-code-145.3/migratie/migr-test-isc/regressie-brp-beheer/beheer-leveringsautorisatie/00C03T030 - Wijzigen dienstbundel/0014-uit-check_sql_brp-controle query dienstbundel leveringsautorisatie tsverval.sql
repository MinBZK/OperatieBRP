SELECT ht.* FROM autaut.levsautorisatie l
JOIN autaut.dienstbundel t ON l.id = t.levsautorisatie
JOIN autaut.his_dienstbundel ht ON t.id = ht.dienstbundel
WHERE l.naam = 'Automatisch testscript 00C03T030 - Wijzigen dienstbundel' AND ht.tsverval IS NULL AND ht.tsreg IN (
    SELECT ht.tsverval FROM autaut.levsautorisatie l
    JOIN autaut.dienstbundel t ON l.id = t.levsautorisatie
    JOIN autaut.his_dienstbundel ht ON t.id = ht.dienstbundel
    WHERE l.naam = 'Automatisch testscript 00C03T030 - Wijzigen dienstbundel' AND ht.tsverval IS NOT NULL)