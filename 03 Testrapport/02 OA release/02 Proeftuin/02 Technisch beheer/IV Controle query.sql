select count(*), conversie_resultaat from initvul.initvullingresult group by conversie_resultaat
--Verzonden en te verzenden mogen niet voorkomen


--select * from initvul.initvullingresult iv where iv.conversie_resultaat <> 'TOEGEVOEGD'
--190453 Te verzenden, proeftuin rechts 14-2-201715:36
--select * from initvul.initvullingresult iv where iv.conversie_resultaat = 'AFGEKEURD'