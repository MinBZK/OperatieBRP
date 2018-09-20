select  pers.bsn Er_is_geprotocolleerd_voor_bsn,   ll.srtsynchronisatie, ll.toegangabonnement, a.naam from lev.lev ll
join autaut.toegangabonnement t on (t.id = ll.toegangabonnement)
join autaut.abonnement a on (a.id = t.abonnement)
join lev.levpers lp on (ll.id = lp.lev)
join kern.pers on (lp.pers = pers.id)
where pers.bsn = ${DataSource Values#burgerservicenummer_zlB1} and ll.id > ${Pre Query#result};