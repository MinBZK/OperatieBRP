SELECT COUNT(*) AS aantal
FROM   initvul.initvullingresult_protocollering
WHERE  activiteit_id=
    ( SELECT activiteit_id
      FROM   public.activiteit
      WHERE  activiteit_type=101
      AND    activiteit_subtype=1222 
      AND toestand=8000
    )
AND    pers_id=
    ( SELECT id 
      FROM kern.pers 
      WHERE anr='7908421409'
    )
AND    bijhouding_opschort_reden IS NULL
AND    toeganglevsautorisatie_count=0
AND    dienst_id IS NULL
AND    start_dt='2015-01-02 12:00:00.0'
AND    laatste_actie_dt='2015-01-02 12:00:00.0'
AND    conversie_resultaat='FOUT'
AND    foutmelding='Toegang leveringsautorisatie kan niet gevonden worden';