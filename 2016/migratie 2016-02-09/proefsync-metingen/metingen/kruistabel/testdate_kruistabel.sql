truncate table resultaatmeting4;
truncate table resultaatmeting3;

INSERT INTO resultaatmeting4 (verwerkingssysteem, bericht_id, status, indicatie_beheerder) VALUES ('GBA-V', 'eref43211234', 'Lopend', false);
INSERT INTO resultaatmeting4 (verwerkingssysteem, bericht_id, status, indicatie_beheerder) VALUES ('GBA-V', 'eref83211234', 'Lopend', false);
INSERT INTO resultaatmeting4 (verwerkingssysteem, bericht_id, status, indicatie_beheerder) VALUES ('GBA-V', 'eref13211234', 'Pf02', true);
INSERT INTO resultaatmeting4 (verwerkingssysteem, bericht_id, status, indicatie_beheerder) VALUES ('GBA-V', 'eref53211234', 'Pf03', true);
INSERT INTO resultaatmeting4 (verwerkingssysteem, bericht_id, status, indicatie_beheerder) VALUES ('GBA-V', 'eref23211234', 'Pf02', false);

INSERT INTO resultaatmeting4 (verwerkingssysteem, bericht_id, status, indicatie_beheerder) VALUES ('BRP', 'eref93211234', 'Lopend', false);
INSERT INTO resultaatmeting4 (verwerkingssysteem, bericht_id, status, indicatie_beheerder) VALUES ('BRP', 'eref13211234', 'Pf03', true);
INSERT INTO resultaatmeting4 (verwerkingssysteem, bericht_id, status, indicatie_beheerder) VALUES ('BRP', 'eref53211234', 'Pf03', true);
INSERT INTO resultaatmeting4 (verwerkingssysteem, bericht_id, status, indicatie_beheerder) VALUES ('BRP', 'eref23211234', 'Pf02', false);

INSERT INTO resultaatmeting3 (verwerkingssysteem, bericht_id, status, indicatie_beheerder) VALUES ('GBA-V', 'eref12345678', 'Ok', true);
INSERT INTO resultaatmeting3 (verwerkingssysteem, bericht_id, status, indicatie_beheerder) VALUES ('GBA-V', 'eref33211234', 'Ok', true);
INSERT INTO resultaatmeting3 (verwerkingssysteem, bericht_id, status, indicatie_beheerder) VALUES ('GBA-V', 'eref73211234', 'Ok', true);
INSERT INTO resultaatmeting3 (verwerkingssysteem, bericht_id, status, indicatie_beheerder) VALUES ('BRP', 'eref72345678', 'Ok', true);
INSERT INTO resultaatmeting3 (verwerkingssysteem, bericht_id, status, indicatie_beheerder) VALUES ('BRP', 'eref33211234', 'Ok', true);
INSERT INTO resultaatmeting3 (verwerkingssysteem, bericht_id, status, indicatie_beheerder) VALUES ('BRP', 'eref73211234', 'Ok', true);

INSERT INTO resultaatmeting4 (verwerkingssysteem, bericht_id, status, indicatie_beheerder) VALUES ('GBA-V', 'eref12333333', 'NOT', true);
INSERT INTO resultaatmeting4 (verwerkingssysteem, bericht_id, status, indicatie_beheerder) VALUES ('BRP', 'eref12333337', 'NOT', true);
INSERT INTO resultaatmeting4 (verwerkingssysteem, bericht_id, status, indicatie_beheerder) VALUES ('GBA-V', 'eref12333334', 'NOT', true);
INSERT INTO resultaatmeting4 (verwerkingssysteem, bericht_id, status, indicatie_beheerder) VALUES ('BRP', 'eref12333334', 'NOT', true);