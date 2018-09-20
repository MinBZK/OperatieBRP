INSERT INTO kern.persindicatie VALUES(99,(select id from kern.pers where bsn=${DataSource Values#|objectid.burgerservicenummer_ipv4|}),9,TRUE);
INSERT INTO kern.his_persindicatie VALUES(100,99,20140110,NULL,'2014-01-10 00:00:00+00',NULL,NULL,624,NULL,NULL,NULL,NULL,TRUE);
