
-- Autorisatiebesluit, doelbindingen en abonnementen
INSERT INTO autaut.autorisatiebesluit (id, srt, besluittekst, autoriseerder, indmodelbesluit, indingetrokken, autorisatiebesluitstatushis, bijhautorisatiebesluitstatus, toestand) VALUES (1, 1, 'Het mag', 364, false, false, 'A', 'A', 4);
INSERT INTO autaut.doelbinding (id, levsautorisatiebesluit, geautoriseerde, protocolleringsniveau, tekstdoelbinding, IndVerstrbeperkingHonoreren, doelbindingstatushis) VALUES (1, 1,  364, 1, 'Is goed', false, 'A');

-- oude cert
--INSERT INTO autaut.certificaat (id, subject, serial, signature) VALUES (1, 'CN=serverkey', 1315331010, '070ce9add25272a4111ca55da1b66e692b94e0689e74628fd834cf3001a7ef43972b5674bb66c954beaf16c16317db635ea92973de0d994f7651005be3c29162fd9125b9f425ad6443a8a298221a74fabe774cf8aa3d070b399d93aa4708944a81aa3cee5f080794e975801cf01ca371348cf56e7b9761256fac6950e3baa9af');
INSERT INTO autaut.certificaat (id, subject, serial, signature) VALUES (1, 'CN=BasIsKoning', 1323167436, '54150f1230a8068661cf321b1ad1d61c430b0bff1076c6197ba5b0a0e49cd7496b0c99e34085c1c37fe30646c0de6e4ea1a07e329093bf5f6f77c50341bcbfba0f79b5d24651ffc27c404d49a30c3f5ae67fee82e9dc5bbe7cacb9fa15e8186c703120ed768d6eacf7d75fb5cfec17a2c10e4381529a367021e8208f9742453c');

INSERT INTO autaut.authenticatiemiddel (id, partij, rol, functie, certificaattbvssl, certificaattbvondertekening, authenticatiemiddelstatushis) VALUES (1, 364, 1, 1, 1, 1, 'A');

INSERT INTO lev.abonnement (id, doelbinding, srtabonnement, abonnementstatushis) VALUES (1, 1, 1, 'A');
INSERT INTO lev.abonnementsrtber (id, abonnement, srtber, abonnementsrtberstatushis) VALUES (1, 1, 1, 'A');

-- Gegevenselementen van abonnementen (vult abonnement 1 met alle gegevenselementen)
INSERT INTO lev.abonnementgegevenselement (id, abonnement, gegevenselement) select id, 1, id from Kern.DbObject where srt = 2;

-- Historisch abonnement
INSERT INTO autaut.autorisatiebesluit (id, srt, besluittekst, autoriseerder, indmodelbesluit, indingetrokken, autorisatiebesluitstatushis, bijhautorisatiebesluitstatus, toestand) VALUES (2, 1, 'Het mag', 364, false, false, 'A', 'A', 4);
INSERT INTO autaut.doelbinding (id, levsautorisatiebesluit, geautoriseerde, protocolleringsniveau, tekstdoelbinding, IndVerstrbeperkingHonoreren, doelbindingstatushis) VALUES (2, 2,  364, 1, 'Is goed', false, 'A');
INSERT INTO lev.abonnement (id, doelbinding, srtabonnement, abonnementstatushis) VALUES (2, 2, 1, 'M');
INSERT INTO lev.abonnementsrtber (id, abonnement, srtber, abonnementsrtberstatushis) VALUES (2, 2, 1, 'M');
