SELECT COUNT(*) AS aantal_dienstbundel
FROM autaut.dienstbundel
WHERE levsautorisatie=(SELECT id FROM autaut.levsautorisatie WHERE stelsel=2 AND naam='900050' AND datingang=20000101 AND dateinde=20210101) 
AND naam='Ad hoc'
AND datingang=20000101
AND dateinde=20210101
OR
levsautorisatie=(SELECT id FROM autaut.levsautorisatie WHERE stelsel=2 AND naam='900050' AND datingang=20000101 AND dateinde=20210101) 
AND naam='Selectie'
AND datingang=20000101
AND dateinde=20210101
OR
levsautorisatie=(SELECT id FROM autaut.levsautorisatie WHERE stelsel=2 AND naam='900050' AND datingang=20000101 AND dateinde=20210101) 
AND naam='Spontaan'
AND datingang=20000101
AND dateinde=20210101
AND indnaderepopbeperkingvolconv='FALSE'
AND toelichting='ONGECONVERTEERDE GBA-VOORWAARDEREGEL: (KV 51.01.10 ENVWD (01.01.10 OGAA 51.01.10)) OFVWD (KV 51.01.20 ENVWD (01.01.20 OAGA 51.01.20))';