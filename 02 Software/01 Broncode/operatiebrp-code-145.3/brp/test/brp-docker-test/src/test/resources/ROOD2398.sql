
INSERT INTO kern.admhnd (id, srt, partij, toelichtingontlening, tsreg, tslev, statuslev) values (3153643, 37, 2000, null, '1990-02-24 01:00:00+00', null, 2);
INSERT INTO kern.admhnd (id, srt, partij, toelichtingontlening, tsreg, tslev, statuslev) values (24061691, 37, 2000, null, '1995-02-24 01:00:00+00', null, 2);

INSERT INTO kern.actie (id, srt, admhnd, partij, tsreg, datontlening) VALUES (49691910, 7, 3153643, 2000, '1989-02-24 01:00:00+00', NULL);
INSERT INTO kern.actie (id, srt, admhnd, partij, tsreg, datontlening) VALUES (49691919, 7, 3153643, 2000, '1996-03-26 01:00:00+00', NULL);
INSERT INTO kern.actie (id, srt, admhnd, partij, tsreg, datontlening) VALUES (49691926, 7, 3153643, 2000, '1998-08-17 01:00:00+00', NULL);
INSERT INTO kern.actie (id, srt, admhnd, partij, tsreg, datontlening) VALUES (49691930, 7, 3153643, 2000, '2000-09-29 01:00:00+00', NULL);
INSERT INTO kern.actie (id, srt, admhnd, partij, tsreg, datontlening) VALUES (49691935, 7, 3153643, 2000, '2000-11-29 01:00:00+00', NULL);
INSERT INTO kern.actie (id, srt, admhnd, partij, tsreg, datontlening) VALUES (49691941, 7, 3153643, 2000, '2004-03-22 01:00:00+00', NULL);
INSERT INTO kern.actie (id, srt, admhnd, partij, tsreg, datontlening) VALUES (49691949, 7, 3153643, 2000, '2001-08-03 01:00:00+00', NULL);
INSERT INTO kern.actie (id, srt, admhnd, partij, tsreg, datontlening) VALUES (49691952, 7, 3153643, 2000, '2011-11-01 01:00:00+00', NULL);
INSERT INTO kern.actie (id, srt, admhnd, partij, tsreg, datontlening) VALUES (49691959, 7, 3153643, 2000, '2009-01-08 01:00:00+00', NULL);
INSERT INTO kern.actie (id, srt, admhnd, partij, tsreg, datontlening) VALUES (49691962, 7, 3153643, 2000, '2010-11-17 01:00:00+00', NULL);
INSERT INTO kern.actie (id, srt, admhnd, partij, tsreg, datontlening) VALUES (49691967, 7, 3153643, 2000, '2011-10-19 01:00:00+00', NULL);
INSERT INTO kern.actie (id, srt, admhnd, partij, tsreg, datontlening) VALUES (49691976, 7, 3153643, 2000, '2013-05-14 01:00:00+00', NULL);
INSERT INTO kern.actie (id, srt, admhnd, partij, tsreg, datontlening) VALUES (49691979, 7, 3153643, 2000, '2012-11-13 01:00:00+00', NULL);
INSERT INTO kern.actie (id, srt, admhnd, partij, tsreg, datontlening) VALUES (49691983, 7, 3153643, 2000, '2013-08-26 01:00:00+00', NULL);
INSERT INTO kern.actie (id, srt, admhnd, partij, tsreg, datontlening) VALUES (330837755, 7, 24061691, 2000, '2017-06-17 12:07:42.493+00', NULL);

INSERT INTO kern.his_persafgeleidadministrati (id, pers, tsreg, actieinh, admhnd, tslaatstewijz, sorteervolgorde, tslaatstewijzgbasystematiek) values (2, 1, '1990-02-24 01:00:00+00', 49691910, 3153643, '1990-02-24 01:00:00+00', 2, '1990-02-24 01:00:00+00');
INSERT INTO kern.his_persafgeleidadministrati (id, pers, tsreg, actieinh, admhnd, tslaatstewijz, sorteervolgorde, tslaatstewijzgbasystematiek) values (3, 1, '1995-02-24 01:00:00+00', 330837755, 24061691, '1990-02-24 01:00:00+00', 2, '1990-02-24 01:00:00+00');

INSERT INTO kern.his_persbijhouding (id, pers, tsreg, actieinh, tsverval, actieverval, nadereaandverval, actievervaltbvlevmuts, indvoorkomentbvlevmuts, dataanvgel, dateindegel, actieaanpgel, bijhpartij, bijhaard, naderebijhaard) VALUES (15543129, 1, '1989-02-24 01:00:00+00', 49691910, '1989-02-24 01:00:00+00', 49691910, 'O', 330837755, true, 19811226, NULL, NULL, 509, 1, 8);
INSERT INTO kern.his_persbijhouding (id, pers, tsreg, actieinh, tsverval, actieverval, nadereaandverval, actievervaltbvlevmuts, indvoorkomentbvlevmuts, dataanvgel, dateindegel, actieaanpgel, bijhpartij, bijhaard, naderebijhaard) VALUES (15543130, 1, '1996-03-26 01:00:00+00', 49691919, '1996-03-26 01:00:00+00', 49691919, NULL, NULL, NULL, 19811226, NULL, NULL, 509, 1, 8);
INSERT INTO kern.his_persbijhouding (id, pers, tsreg, actieinh, tsverval, actieverval, nadereaandverval, actievervaltbvlevmuts, indvoorkomentbvlevmuts, dataanvgel, dateindegel, actieaanpgel, bijhpartij, bijhaard, naderebijhaard) VALUES (15543131, 1, '1998-08-17 01:00:00+00', 49691926, '1998-08-17 01:00:00+00', 49691926, NULL, NULL, NULL, 19811226, NULL, NULL, 509, 1, 8);
INSERT INTO kern.his_persbijhouding (id, pers, tsreg, actieinh, tsverval, actieverval, nadereaandverval, actievervaltbvlevmuts, indvoorkomentbvlevmuts, dataanvgel, dateindegel, actieaanpgel, bijhpartij, bijhaard, naderebijhaard) VALUES (15543132, 1, '2000-09-29 01:00:00+00', 49691930, '2000-09-29 01:00:00+00', 49691930, NULL, NULL, NULL, 19811226, NULL, NULL, 509, 1, 8);
INSERT INTO kern.his_persbijhouding (id, pers, tsreg, actieinh, tsverval, actieverval, nadereaandverval, actievervaltbvlevmuts, indvoorkomentbvlevmuts, dataanvgel, dateindegel, actieaanpgel, bijhpartij, bijhaard, naderebijhaard) VALUES (15543133, 1, '2000-11-29 01:00:00+00', 49691935, '2000-11-29 01:00:00+00', 49691935, NULL, NULL, NULL, 19811226, NULL, NULL, 509, 1, 8);
INSERT INTO kern.his_persbijhouding (id, pers, tsreg, actieinh, tsverval, actieverval, nadereaandverval, actievervaltbvlevmuts, indvoorkomentbvlevmuts, dataanvgel, dateindegel, actieaanpgel, bijhpartij, bijhaard, naderebijhaard) VALUES (15543134, 1, '2001-08-03 01:00:00+00', 49691949, NULL, NULL, NULL, NULL, NULL, 19811226, 20040317, 49691941, 509, 1, 8);
INSERT INTO kern.his_persbijhouding (id, pers, tsreg, actieinh, tsverval, actieverval, nadereaandverval, actievervaltbvlevmuts, indvoorkomentbvlevmuts, dataanvgel, dateindegel, actieaanpgel, bijhpartij, bijhaard, naderebijhaard) VALUES (15543135, 1, '2004-03-22 01:00:00+00', 49691941, NULL, NULL, NULL, NULL, NULL, 20040317, 20090105, 49691952, 594, 1, 8);
INSERT INTO kern.his_persbijhouding (id, pers, tsreg, actieinh, tsverval, actieverval, nadereaandverval, actievervaltbvlevmuts, indvoorkomentbvlevmuts, dataanvgel, dateindegel, actieaanpgel, bijhpartij, bijhaard, naderebijhaard) VALUES (15543136, 1, '2009-01-08 01:00:00+00', 49691959, '2009-01-08 01:00:00+00', 49691959, NULL, NULL, NULL, 20090105, NULL, NULL, 486, 1, 8);
INSERT INTO kern.his_persbijhouding (id, pers, tsreg, actieinh, tsverval, actieverval, nadereaandverval, actievervaltbvlevmuts, indvoorkomentbvlevmuts, dataanvgel, dateindegel, actieaanpgel, bijhpartij, bijhaard, naderebijhaard) VALUES (15543137, 1, '2010-11-17 01:00:00+00', 49691962, '2010-11-17 01:00:00+00', 49691962, 'O', NULL, NULL, 20090105, NULL, NULL, 486, 1, 8);
INSERT INTO kern.his_persbijhouding (id, pers, tsreg, actieinh, tsverval, actieverval, nadereaandverval, actievervaltbvlevmuts, indvoorkomentbvlevmuts, dataanvgel, dateindegel, actieaanpgel, bijhpartij, bijhaard, naderebijhaard) VALUES (15543138, 1, '2011-10-19 01:00:00+00', 49691967, '2011-10-19 01:00:00+00', 49691967, NULL, NULL, NULL, 20090105, NULL, NULL, 486, 1, 8);
INSERT INTO kern.his_persbijhouding (id, pers, tsreg, actieinh, tsverval, actieverval, nadereaandverval, actievervaltbvlevmuts, indvoorkomentbvlevmuts, dataanvgel, dateindegel, actieaanpgel, bijhpartij, bijhaard, naderebijhaard) VALUES (15543139, 1, '2011-11-01 01:00:00+00', 49691952, NULL, NULL, NULL, NULL, NULL, 20090105, 20121108, 49691976, 486, 1, 8);
INSERT INTO kern.his_persbijhouding (id, pers, tsreg, actieinh, tsverval, actieverval, nadereaandverval, actievervaltbvlevmuts, indvoorkomentbvlevmuts, dataanvgel, dateindegel, actieaanpgel, bijhpartij, bijhaard, naderebijhaard) VALUES (15543141, 1, '2012-11-13 01:00:00+00', 49691979, '2012-11-13 01:00:00+00', 49691979, NULL, NULL, NULL, 20121108, NULL, NULL, 509, 1, 8);
INSERT INTO kern.his_persbijhouding (id, pers, tsreg, actieinh, tsverval, actieverval, nadereaandverval, actievervaltbvlevmuts, indvoorkomentbvlevmuts, dataanvgel, dateindegel, actieaanpgel, bijhpartij, bijhaard, naderebijhaard) VALUES (15543143, 1, '2013-05-14 01:00:00+00', 49691976, NULL, NULL, NULL, NULL, NULL, 20121108, 20130822, 49691983, 509, 1, 8);
INSERT INTO kern.his_persbijhouding (id, pers, tsreg, actieinh, tsverval, actieverval, nadereaandverval, actievervaltbvlevmuts, indvoorkomentbvlevmuts, dataanvgel, dateindegel, actieaanpgel, bijhpartij, bijhaard, naderebijhaard) VALUES (15543145, 1, '2013-08-26 01:00:00+00', 49691983, NULL, NULL, NULL, NULL, NULL, 20130822, NULL, NULL, 1415, 1, 1);




