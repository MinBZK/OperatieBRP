-- Statement omdat anders de spring database initializer over z'n nek gaat
SELECT '42';
-- PRE113
INSERT INTO VerConv.LO3SrtMelding (ID, Code, Oms, CategorieMelding) VALUES (937, 'PRE113', 'Element 01.10 A-nummer uit een niet onjuiste categorie 02/52 Ouder1, 03/53 Ouder2, 05/55 Huwelijk/geregistreerd partnerschap en 09/59 Kind mag niet overeenkomen met 01.01.10 A-nummer.', 2);
-- AUT012
INSERT INTO VerConv.LO3SrtMelding (ID, Code, Oms, CategorieMelding) VALUES (938, 'AUT012', 'Autorisaties: Er mogen niet meerdere lege einddatums binnen een stapel van een autorisatie zijn voor een afnemer.', 2);

COMMIT;
