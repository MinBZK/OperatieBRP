SELECT COUNT(*) AS aantal
FROM initvul.initvullingresult_protocollering
WHERE activiteit_id=
    ( SELECT activiteit_id
      FROM   public.activiteit
      WHERE  activiteit_type=101
      AND    activiteit_subtype=1210 
      AND    toestand=8001
    )
AND bijhouding_opschort_reden IS NULL
AND toeganglevsautorisatie_id=
    ( SELECT id 
      FROM   autaut.toeganglevsautorisatie 
      WHERE  levsautorisatie=
          ( SELECT id 
            FROM   autaut.levsautorisatie 
            WHERE  naam='510001' 
            AND    datingang='20130101' 
            AND    dateinde='20150103'
          )
     )
AND toeganglevsautorisatie_count=1
AND dienst_id=
    ( SELECT id
      FROM   autaut.dienst 
      WHERE  dienstbundel=
          ( SELECT id 
            FROM   autaut.dienstbundel 
            WHERE  naam='Ad hoc'
            AND    datingang='20130101' 
            AND    dateinde='20150103'
            AND    levsautorisatie=
                ( SELECT id 
                  FROM autaut.levsautorisatie 
                  WHERE naam='510001'
                )
          )
      AND    srt=3 
      AND    datingang='20130101' 
      AND    dateinde='20150103'
    )
AND start_dt='2013-01-01 12:00:00'
AND laatste_actie_dt='2013-01-01 12:00:00'
AND conversie_resultaat='OK'
AND foutmelding IS NULL;
