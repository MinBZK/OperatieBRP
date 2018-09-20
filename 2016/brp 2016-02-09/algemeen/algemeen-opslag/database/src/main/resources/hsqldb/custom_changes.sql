-- Methode unaccent toevoegen zodat testen onder hsqldb ook werken
DROP FUNCTION IF EXISTS unaccent;
CREATE FUNCTION unaccent(t VARCHAR(32000))
    RETURNS VARCHAR(32000)
    RETURN TRANSLATE( t, 'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜáçéíóúàèìòùâêîôûãõëü', 'ACEIOUAEIOUAEIOUAOEUaceiouaeiouaeiouaoeu');

-- Tbv van bijhouding is onderstaand srtdoc nodig voor emigratie berichten, onderstaand record moet nog worden toegevoegd aan het BMR.
insert into kern.srtdoc values (9999, 'Ministerieel besluit', 'Een ministerieel besluit', null);

-- Onderzoek expressies
UPDATE kern.element SET expressie = NULL WHERE naam = 'Persoon.Onderzoek.RolNaam';
UPDATE kern.element SET expressie = 'MAP(ONDERZOEKEN(), o, $o.datum_tijd_registratie)' WHERE naam = 'Onderzoek.TijdstipRegistratie';
UPDATE kern.element SET expressie = 'MAP(ONDERZOEKEN(), o, $o.datum_tijd_verval)' WHERE naam = 'Onderzoek.TijdstipVerval';
UPDATE kern.element SET expressie = '{{RMAP(ONDERZOEKEN(), x, $x.verantwoordingInhoud.soort)},{RMAP(ONDERZOEKEN(), x, $x.verantwoordingInhoud.partij)},{RMAP(ONDERZOEKEN(), x, $x.verantwoordingInhoud.tijdstip_registratie)},{RMAP(ONDERZOEKEN(), x, $x.verantwoordingInhoud.datum_ontlening)}}' WHERE naam = 'Onderzoek.ActieInhoud';
UPDATE kern.element SET expressie = '{{RMAP(ONDERZOEKEN(), x, $x.verantwoordingVerval.soort)},{RMAP(ONDERZOEKEN(), x, $x.verantwoordingVerval.partij)},{RMAP(ONDERZOEKEN(), x, $x.verantwoordingVerval.tijdstip_registratie)},{RMAP(ONDERZOEKEN(), x, $x.verantwoordingVerval.datum_ontlening)}}' WHERE naam = 'Onderzoek.ActieVerval';

-- Voor de meeste bestaande testen moeten gemeenten autofiat aan hebben, omdat huwelijken anders niet opgeslagen worden en die testen dan zouden falen.
UPDATE kern.partij SET indautofiat = true WHERE naam LIKE 'Gemeente%';

-- BMR-202: tweetal LO3 Soort melding tuples die nog mee moeten voor de Bernadette release
INSERT INTO VerConv.LO3SrtMelding (ID, Code, Oms, CategorieMelding) VALUES (937, 'PRE113', 'Element 01.10 A-nummer uit een niet onjuiste categorie 02/52 Ouder1, 03/53 Ouder2, 05/55 Huwelijk/geregistreerd partnerschap en 09/59 Kind mag niet overeenkomen met 01.01.10 A-nummer.', 2);
INSERT INTO VerConv.LO3SrtMelding (ID, Code, Oms, CategorieMelding) VALUES (938, 'AUT012', 'Autorisaties: Er mogen niet meerdere lege einddatums binnen een stapel van een autorisatie zijn voor een afnemer.', 2);
