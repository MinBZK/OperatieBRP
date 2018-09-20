--
-- Toevoeging wsdl endpoints voor BRPreview deelnemers/afnemers tbv leveringen - mutaties 
--

-- Endpoint voor: 051801, Den Haag, Programma mGBA, https://brp-proeftuin-links.modernodam.nl/brp-kennisgeving-ontvanger/levering/kennisgevingService
UPDATE Kern.Partij SET wsdlendpoint = 'https://brp-proeftuin-links.modernodam.nl/brp-kennisgeving-ontvanger/levering/kennisgevingService' WHERE code = 051801;

-- Endpoint voor: 001401, Groningen, PinkRoccade, https://92.70.53.29:8444/CGS/StUF/0302/BRP/0100/services/OntvangAsynchroonLevering
UPDATE Kern.Partij SET wsdlendpoint = 'https://92.70.53.29:8444/CGS/StUF/0302/BRP/0100/services/OntvangAsynchroonLevering' WHERE code = 001401;

-- Endpoint voor: 093501, Maastricht, OpenGBA, http://srv02.jnc.nl:7080/opentunnel/12332112332112332112/brp/leveren
UPDATE Kern.Partij SET wsdlendpoint = 'http://srv02.jnc.nl:7080/opentunnel/12332112332112332112/brp/leveren' WHERE code = 093501;

-- Endpoint voor: 034401, Utrecht, GemBoxx, https://gemboxxbrp.grexx.net/leveren/kennisgeving.svc
UPDATE Kern.Partij SET wsdlendpoint = 'https://gemboxxbrp.grexx.net/leveren/kennisgeving.svc' WHERE code = 034401;

-- Endpoint voor: 034401, Utrecht, Procura, https://web.procura.nl/gba-webservice/services/Kennisgeving
UPDATE Kern.Partij SET wsdlendpoint = 'https://web.procura.nl/gba-webservice/services/Kennisgeving' WHERE code = 039201;


