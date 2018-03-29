SELECT COUNT(*) AS aantal
FROM initvul.initvullingresult_protocollering
WHERE activiteit_id=(SELECT activiteit_id
FROM public.activiteit
WHERE activiteit_type=101
AND activiteit_subtype=1210)
AND pers_id IS NULL
AND bijhouding_opschort_reden = 'F'
AND toeganglevsautorisatie_count=1
AND dienst_id=(SELECT id FROM autaut.dienst WHERE dienstbundel=(SELECT id FROM autaut.dienstbundel WHERE naam='Ad hoc' AND datingang='20120101' AND dateinde='20130102') AND srt=3 AND datingang='20120101' AND dateinde='20130102')
AND start_dt='2013-01-01 12:00:00.0'
AND laatste_actie_dt='2013-01-01 12:00:00.0'
AND conversie_resultaat='FOUT'
AND foutmelding LIKE 'Persoon is opgeschort met reden ''F''';