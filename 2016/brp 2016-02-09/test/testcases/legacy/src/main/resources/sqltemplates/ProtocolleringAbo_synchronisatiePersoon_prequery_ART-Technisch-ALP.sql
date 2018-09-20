SELECT COALESCE (
  (SELECT MAX(l.id)
  FROM
    lev.lev l
    JOIN lev.levpers lp ON (lp.lev = l.id)
    JOIN kern.pers p ON (p.id = lp.pers)
  WHERE
    p.bsn = ${burgerservicenummer_zlB1}
    AND l.toegangabonnement IN
      (SELECT id
      FROM autaut.toegangabonnement
      WHERE abonnement = ${abonnement_id}))
  , 0);