delete from kern.srtdoc;
delete from kern.categoriesrtdoc;

-- Dit is een tijdelijke invulling, omdat het hele model op de schop gaat.
-- Daardoor is categoriesrtdoc ook obsolete geworden.
-- Ook de kolommen in srtdoc is onder revisie.
--

INSERT INTO kern.categoriesrtdoc(id, naam, oms) VALUES ('1', 'Nederlandse Akte', 'Nederlandse Akte' );
INSERT INTO kern.categoriesrtdoc(id, naam, oms) VALUES ('2', 'Buitenlandse Akte', 'Buitenlandse Akte' );
INSERT INTO kern.categoriesrtdoc(id, naam, oms) VALUES ('3', 'Onbekend', 'Onbekend' );


INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('1', 'Geboorteakte', '1');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('2', 'Overlijdensakte', '1');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('3', 'Huwelijksakte', '1');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('4', 'Echtscheidingsakte', '1');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('5', 'Geregistreerd partnerschapsakte', '1');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('6', 'Beëindiging geregistreerd partnerschapsakte', '1');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('7', 'NL akte overig', '1');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('8', 'Besluit', '1');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('9', 'Nederlandse rechterlijke uitspraak', '1');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('10', 'Notariële akte', '1');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('11', 'Consulaire akte', '1');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('12', 'Buitenlandse akte', '2');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('13', 'Buitenlandse rechterlijke uitspraak', '2');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('14', 'Akte van bekendheid', '2');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('15', 'Beëdigde verklaring', '2');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('16', 'Geschrift', '2');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('17', 'Verklaring onder ede of belofte', '2');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('18', 'Mededeling Ministerie inzake, verblijfsrecht', '3');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('19', 'Mededeling Ministerie inzake nationaliteit en geboorte', '3');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('20', 'Uittreksel curateleregister', '3');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('21', 'Uittreksel gezagsregister', '3');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('22', 'Verzoek naamgebruik', '3');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('23', 'Akte Nederlandse Nationaliteit', '3');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('24', 'Rechterlijke uitspraak Nederlandse nationaliteit', '3');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('25', 'Beschikking/uitspraak vreemde nationaliteit', '3');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('26', 'Geschrift vreemde nationaliteit', '3');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('27', 'Verzoek verstrekkingsbeperking', '3');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('28', 'Aangifte met betrekking tot verblijfplaats', '3');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('29', 'Ambtshalve besluit met betrekking tot verblijfplaats', '3');
INSERT INTO kern.srtdoc(id, naam, categoriesrtdoc) VALUES ('30', 'Overig', '3');
