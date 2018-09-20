SELECT bericht_inhoud, gbav_pl_id, anummer FROM initvul.initvullingresult
WHERE (:start_datum_leeg OR datumtijd_opname_in_gbav > :start_datum)
  AND (:eind_datum_leeg OR datumtijd_opname_in_gbav < :eind_datum)
  AND (:gemeente_code_leeg OR gemeente_van_inschrijving = :gemeente_code)
  AND (:geen_anummer_beperking OR ((anummer / 100000000) - ((anummer / 1000000000) * 10) = 3
  AND (anummer / 1000000) - ((anummer / 10000000) * 10) = 3
  AND (anummer / 10000) - ((anummer / 100000) * 10) = 3))
  AND conversie_resultaat = :conversie_resultaat
