-- authenticatiemiddel werd niet gevuld in toegangabonnement

UPDATE autaut.toegangabonnement ata
SET authenticatiemiddel =
(SELECT MIN(am.id) FROM autaut.authenticatiemiddel am WHERE am.partij =
(SELECT ta.partij FROM autaut.toegangabonnement ta WHERE ta.id = ata.id))
