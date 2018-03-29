SELECT COUNT(*) AS aantal_dienst_id
FROM initvul.initvullingresult_protocollering
WHERE dienst_id=(SELECT id
FROM autaut.dienst WHERE dienstbundel=(SELECT id FROM autaut.dienstbundel WHERE naam='Ad hoc' AND datingang='20140101' AND dateinde='20150101')
AND srt=3 AND datingang='20140101' AND dateinde='20150101')
AND bijhouding_opschort_reden = 'F'; 