SELECT COUNT(*) AS aantal_01cx0t270c
FROM   initvul.initvullingresult_protocollering
WHERE  activiteit_id=
    ( SELECT activiteit_id
      FROM   public.activiteit
      WHERE  activiteit_type=101
      AND activiteit_subtype=1222 
      AND toestand=8998
    )
AND    bijhouding_opschort_reden IS NULL
AND    toeganglevsautorisatie_id=
    ( SELECT id 
      FROM autaut.toeganglevsautorisatie 
      WHERE levsautorisatie=
          ( SELECT id 
            FROM autaut.levsautorisatie 
            WHERE naam='510221' 
            AND datingang='20130101' 
            AND dateinde='20150102'
          )
    )
AND    toeganglevsautorisatie_count=1
AND    dienst_id=
    ( SELECT id
      FROM autaut.dienst 
      WHERE dienstbundel=
          ( SELECT id 
            FROM autaut.dienstbundel
            WHERE naam='Spontaan' 
            AND datingang='20130101' 
            AND dateinde='20150102'
            AND levsautorisatie=
                ( SELECT id 
                  FROM autaut.levsautorisatie 
                  WHERE naam='510221'
                )
          )
      AND srt=2 
      AND datingang='20130101' 
      AND dateinde='20150102'
    )
AND    start_dt='2015-01-01 12:00:00'
AND    laatste_actie_dt='2015-01-01 12:00:00'
AND    conversie_resultaat='TE_VERZENDEN';