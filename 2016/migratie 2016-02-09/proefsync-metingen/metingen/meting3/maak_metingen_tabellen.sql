CREATE TABLE converteerGbavStatusNaarIscStatus (
	statusGbav varchar(60) NOT NULL,
	statusIsc varchar(60) NOT NULL,
	CONSTRAINT converteer_status_prim_key PRIMARY KEY (statusGbav, statusIsc));

INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('0','Ok');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('NOT', 'NOT');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('1103','Nog invullen 1');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('1112','Nog invullen 2');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('9005','Nog invullen 3');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('7005','Nog invullen 4');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('1104','Nog invullen 5');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('1105','Nog invullen 6');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('1106','Nog invullen 7');

INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 1', 'uc202.syncnaarbrp.onduidelijk');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 2', 'esb.verwerken.fout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 3', 'uc811.synchronisatieVraag.maximumherhalingen');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 4', 'uc811.synchronisatieVraag.technischefout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 5', 'uc811.synchronisatieVraag.foutiefbericht');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 6', 'uc811.synchronisatieVraag.protocolfout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 7', 'uc811.synchronisatieVraag.inhoudFout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 8', 'alg.gemeente.maximumherhalingen');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 9', 'alg.gemeente.foutiefbericht');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 10', 'uc311.opvragen.technischefout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 11', 'uc311.opvragen.maximumherhalingen');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 12', 'uc301.opvragen.foutiefbericht');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 13', 'uc301.wa01.maximumherhalingen');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 14', 'uc311.wa01.fout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 15', 'uc311.wa01.foutiefbericht');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 16', 'uc311.wa01.protocolfout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 17', 'alg.gemeente.technischefout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 18', 'alg.gemeente.fout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 19', 'alg.gemeente.ongeldig');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 20', 'uc302.leesuitbrp.maximumherhalingen');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 21', 'uc302.leesuitbrp.technischefout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 22', 'uc302.leesuitbrp.fout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 23', 'uc302.start.fout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 24', 'uc302.blokkering.maximumherhalingen');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 25', 'uc302.blokkering.technischefout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 26', 'uc302.blokkering.fout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 27', 'uc302.ii01.maximumherhalingen');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 28', 'uc302.ii01.technischefout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 29', 'uc302.ii01.protocolfout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 30', 'uc302.ii01.if01');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 31', 'uc302.store.maximumherhalingen');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 32', 'uc302.store.technischefout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 33', 'uc302.store.fout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 34', 'uc302.fiatering.maximumherhalingen');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 35', 'uc302.fiatering.technischefout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 36', 'uc302.fiatering.fout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 37', 'uc302.deblokkering.maximumherhalingen');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 38', 'uc302.iv01.maximumherhalingen');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 39', 'uc302.iv01.technischefout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 40', 'uc302.iv01.if31');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 41', 'uc302.iv01.protocolfout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 42', 'uc301.ii01.bijhouderbrp');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 43', 'uc301.ii01.zoekcriteria');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 44', 'uc301.zoek.maximumherhalingen');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 45', 'uc301.zoek.technischefout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 46', 'uc301.zoek.fout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 47', 'uc301.zoek.meerderepersonen');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 48', 'uc301.zoekbuitengemeente.maximumherhalingen');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 49', 'uc301.zoekbuitengemeente.technischefout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 50', 'uc301.zoekbuitengemeente.fout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 51', 'uc301.zoekbuitengemeente.gevonden');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 52', 'uc301.blokkeren.maximumherhalingen');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 53', 'uc301.blokkeren.technischefout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 54', 'uc301.blokkeren.fout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 55', 'uc301.blokkeren.geblokkeerd');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 56', 'uc301.opvragen.maximumherhalingen');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 57', 'uc301.opvragen.technischefout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 58', 'uc301.opvragen.fout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 59', 'uc301.opvragen.opgeschort');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 60', 'uc301.ib01.maximumherhalingen');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 61', 'uc301.ib01.if21');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 62', 'uc301.ib01.protocolfout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 63', 'uc301.ib01.technischefout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 64', 'uc202.start.fout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 65', 'uc202.lock.technischefout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 66', 'uc202.lock.reedsgelocked');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 67', 'uc202.blokkeringinfo.maximumherhalingen');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 68', 'uc202.blokkeringinfo.foutiefbericht');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 69', 'uc202.blokkeringinfo.technischefout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 70', 'uc202.blokkeringinfo.blokkeringfout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 71', 'uc202.syncnaarbrp.maximumherhalingen');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 72', 'uc202.syncnaarbrp.foutiefbericht');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 73', 'uc202.syncnaarbrp.technischefout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 74', 'uc202.syncnaarbrp.afgekeurd');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 75', 'uc202.deblokkeren.maximumherhalingen');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 76', 'uc202.deblokkeren.foutiefbericht');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 77', 'uc202.deblokkeren.technischefout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 78', 'uc202.unlock.technischefout');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 79', 'foutafhandeling.deblokkeren.maximumherhalingen');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 80', 'foutafhandeling.deblokkeren.onverwachtbericht');
INSERT INTO converteerGbavStatusNaarIscStatus (statusGbav, statusIsc) VALUES ('Nog invullen 81', 'foutafhandeling.deblokkeren.fout');

CREATE TABLE resultaatMeting1 (
    bericht_id Varchar(36) NOT NULL, /* */
    status varchar(60) NOT NULL,
    indicatie_beheerder boolean NOT NULL,
    CONSTRAINT meting1_resultaat_prim_key PRIMARY KEY (bericht_id, status));

CREATE TABLE resultaatMeting2 (
    bericht_id Varchar(36) NOT NULL, /* */
    status varchar(60) NOT NULL,
    indicatie_beheerder boolean NOT NULL,
    CONSTRAINT meting2_resultaat_prim_key PRIMARY KEY (bericht_id, status));
