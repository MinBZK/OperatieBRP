-- Voor leveringen is abonnement een verplicht item, dat weer foreign keys heeft naar doelbinding etc.
-- Aangezien dit gedeelte nog niet is geimplementeerd, halen we de verplichting van dit veld tijdelijk weg.

ALTER TABLE lev.lev ALTER COLUMN abonnement DROP NOT NULL;

-- Endpoint voor: 034401, Utrecht, GemBoxx, https://gemboxxbrp.grexx.net/leveren/kennisgeving.svc
UPDATE Kern.Partij SET wsdlendpoint = 'https://gemboxxbrp.grexx.net/leveren/kennisgeving.svc' WHERE code = 034401;

CREATE INDEX admhnd_tsreg
   ON kern.admhnd (tsreg ASC NULLS LAST);


CREATE INDEX admhnd_tsleveringverwerkt
   ON kern.admhnd (tsleveringverwerkt ASC NULLS LAST);
