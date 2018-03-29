SELECT COUNT(*) AS aantal_dienst
FROM autaut.dienst
WHERE dienstbundel=(SELECT id FROM autaut.dienstbundel WHERE levsautorisatie=(SELECT id FROM autaut.levsautorisatie WHERE stelsel=2 AND naam='900050' AND datingang=20000101 AND dateinde=20210101)
AND naam='Ad hoc' AND datingang=20000101 AND dateinde=20210101)
AND srt=3 OR srt=5 OR srt=6 OR srt=7 OR srt=8 OR srt=20
AND datingang=20000101
AND dateinde=20210101
OR dienstbundel=(SELECT id FROM autaut.dienstbundel WHERE levsautorisatie=(SELECT id FROM autaut.levsautorisatie WHERE stelsel=2 AND naam='900050' AND datingang=20000101 AND dateinde=20210101)
AND naam='Selectie' AND datingang=20000101 AND dateinde=20210101)
AND srt=12
AND datingang=20000101
AND dateinde=20210101
OR dienstbundel=(SELECT id FROM autaut.dienstbundel WHERE levsautorisatie=(SELECT id FROM autaut.levsautorisatie WHERE stelsel=2 AND naam='900050' AND datingang=20000101 AND dateinde=20210101)
AND naam='Spontaan' AND datingang=20000101 AND dateinde=20210101)
AND srt=2 OR srt=4
AND datingang=20000101
AND dateinde=20210101;