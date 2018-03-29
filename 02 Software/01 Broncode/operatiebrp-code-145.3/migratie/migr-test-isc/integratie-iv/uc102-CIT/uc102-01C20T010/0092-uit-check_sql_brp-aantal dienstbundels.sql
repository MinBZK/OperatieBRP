SELECT COUNT(*) AS aantal_dienstbundel
FROM autaut.dienstbundel
WHERE levsautorisatie=(SELECT id FROM autaut.levsautorisatie WHERE stelsel=2 AND naam='900050' AND datingang=20000101 AND dateinde=19990101) 
AND naam='Ad hoc'
AND datingang=20000101
AND dateinde=19990101
OR
levsautorisatie=(SELECT id FROM autaut.levsautorisatie WHERE stelsel=2 AND naam='900050' AND datingang=20000101 AND dateinde=19990101) 
AND naam='Selectie'
AND datingang=20000101
AND dateinde=19990101
OR
levsautorisatie=(SELECT id FROM autaut.levsautorisatie WHERE stelsel=2 AND naam='900050' AND datingang=20000101 AND dateinde=19990101) 
AND naam='Spontaan'
AND datingang=20000101
AND dateinde=19990101;