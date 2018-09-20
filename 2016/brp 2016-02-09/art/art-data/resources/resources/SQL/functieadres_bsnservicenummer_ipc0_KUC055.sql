select
 functieadres.id,
 functieadres.code,
 functieadres.naam
 from
 kern.functieadres,
 kern.persadres,
 kern.pers
where
  functieadres.id = persadres.srt and
  persadres.pers = pers.id and
 (pers.bsn = ${DataSource Values#|bsn_rOO0|});
