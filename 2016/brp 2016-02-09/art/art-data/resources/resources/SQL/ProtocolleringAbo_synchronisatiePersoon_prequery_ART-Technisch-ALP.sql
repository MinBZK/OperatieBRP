SELECT COALESCE (
  (SELECT MAX(l.id)
  FROM
    lev.lev l
    JOIN lev.levpers lp ON (lp.lev = l.id)
    JOIN kern.pers p ON (p.id = lp.pers)
  WHERE
    p.bsn = ${DataSource Values#burgerservicenummer_zlB1}
    AND l.toegangabonnement =
      (SELECT id
      FROM autaut.toegangabonnement
      WHERE abonnement = ${DataSource Values#abonnement_id}))
  , 0);

