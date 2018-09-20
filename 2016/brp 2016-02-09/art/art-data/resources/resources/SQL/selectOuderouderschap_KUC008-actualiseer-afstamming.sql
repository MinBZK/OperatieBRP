SELECT
  *
FROM kern.his_ouderouderschap
WHERE betr IN (SELECT
                 id
               FROM kern.betr
               WHERE relatie IN (SELECT
                                   relatie
                                 FROM kern.betr
                                 WHERE pers IN (SELECT
                                                  id
                                                FROM kern.pers
                                                WHERE bsn = ${DataSource Values#|objectid.persoon0|})))
ORDER BY dataanvgel ASC, betr ASC;
