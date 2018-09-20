SET search_path = autaut, pg_catalog;

SELECT pg_catalog.setval('seq_authenticatiemiddel', 1, true);
SELECT pg_catalog.setval('seq_certificaat', 1, true);

INSERT INTO certificaat (id, subject, serial, signature) VALUES (1, 'CN=Whitebox, L=Den Haag, ST=Zuid-Holland, C=NL, EMAILADDRESS=support@modernodam.nl, OU=BRP, O=BZK', 18370619939164389406, 'a88168799265bb7106d1212818c7f0cbbd3578d14eefe3d7ffbf57a34f9591add14b0955666e3c2977f484093144da12bf22d6402d09c4a90700cd973ceb8e72834713abd87e22f37279661de6e3d6c537e06dfc461e7a8da81a6549c38ecdf6c6354186199ca47d12e3baf542b6fdba1e2c771c8107ab15043cc0fece32092c7cb6583e0b8109984e6e5792e343c11c80e8eead3990a4734128612e5075adbfce4fd0c99aa7e5925d0bb23f78d8f5949bc2c06d7141a4c968bd6552afbcfdafcf47cd985c1b6cb8ad46b0f8129ae9c8546b283dbcd82782699c2564cfd1a4fd7ab700131a29a0d0607ba2214e7278bcbf546f1c294d8a6be9fce6fbdbf25bae');
INSERT INTO authenticatiemiddel (id, partij, rol, functie, certificaattbvssl, certificaattbvondertekening, ipadres, authenticatiemiddelstatushis) VALUES (1, 364, 1, 1, NULL, 1, NULL, 'A');

commit;
