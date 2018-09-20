SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = ber, pg_catalog;

--
-- TOC entry 2520 (class 0 OID 309592)
-- Dependencies: 287
-- Data for Name: ber; Type: TABLE DATA; Schema: ber; Owner: brp
--

--Default bericht
INSERT INTO ber.ber VALUES (1, 1, 1, 'data', NULL, NULL, NULL, 1, NULL, 'Inkomend Bericht', '2013-01-17 16:37:28.836', NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL);

--Geboorte bericht
INSERT INTO ber.ber VALUES (2, 1, 1001, 'data', NULL, NULL, NULL, 1, NULL, 'Inkomend Bericht', '2013-01-17 16:37:28.836', NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL);

--Huwelijk bericht
INSERT INTO ber.ber VALUES (3, 1, 630, 'data', NULL, NULL, NULL, 1, NULL, 'Inkomend Bericht', '2013-01-17 16:37:28.836', NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL);

--Overlijden bericht
INSERT INTO ber.ber VALUES (4, 1, 506, 'data', NULL, NULL, NULL, 1, NULL, 'Inkomend Bericht', '2013-01-17 16:37:28.836', NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL);

--Correctie adres bericht
INSERT INTO ber.ber VALUES (5, 1, 9000, 'data', NULL, NULL, NULL, 1, NULL, 'Inkomend Bericht', '2013-01-17 16:37:28.836', NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL);

--Verhuizing bericht
INSERT INTO ber.ber VALUES (6, 1, 5000, 'data', NULL, NULL, NULL, 1, NULL, 'Inkomend Bericht', '2013-01-17 16:37:28.836', NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL);

--Meldingen
INSERT INTO kern.regel(
            id, srt, code, oms, specificatie)
    VALUES (1, 1, 'test', 'test regel', 'test regel');

INSERT INTO ber.melding(
            id, regel, srt, melding, attribuut)
    VALUES (1, 1, 1, 'test melding', null);

INSERT INTO ber.bermelding(
            id, ber, melding)
    VALUES (1, 1, 1);

INSERT INTO ber.melding(
            id, regel, srt, melding, attribuut)
    VALUES (2, 1, 2, 'test melding 2', null);

INSERT INTO ber.bermelding(
            id, ber, melding)
    VALUES (2, 1, 2);


--Huwelijk
INSERT INTO kern.his_huwelijkgeregistreerdpar(
            id, relatie, tsreg, tsverval, actieinh, actieverval, dataanv,
            gemaanv, wplaanv, blplaatsaanv, blregioaanv, omslocaanv, landaanv,
            rdneinde, dateinde, gemeinde, wpleinde, blplaatseinde, blregioeinde,
            omsloceinde, landeinde)
    VALUES (1, 1, '2003-03-27 00:00:00', null, 630, null, 20030327,
            20, 6, null, null, null, 229,
            null, null, null, null, null, null,
            null, null);