UPDATE kern.pers SET datgeboorte=[vandaagsql(-16,0,0)] WHERE bsn=${DataSource Values#|objectid.burgerservicenummer_ipv4|};
UPDATE kern.his_persgeboorte SET datgeboorte=[vandaagsql(-16,0,0)] WHERE id in(select id from kern.pers where bsn=${DataSource Values#|objectid.burgerservicenummer_ipv4|});
INSERT INTO kern.persindicatie VALUES(100,(select id from kern.pers where bsn=${DataSource Values#|objectid.burgerservicenummer_ipv4|}),2,TRUE);
INSERT INTO kern.his_persindicatie VALUES(100,100,20140110,NULL,'2014-01-10 00:00:00+00',NULL,NULL,624,NULL,NULL,NULL,NULL,TRUE);
