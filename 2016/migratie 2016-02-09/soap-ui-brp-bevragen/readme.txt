Om SOAPui te configureren voor babbelen met BRP is gebruikt gemaakt van:
https://www.modernodam.nl/confluence/display/mGBA/Whitebox+aanroepen+met+SoapUI

Dit soapui project opereert als de Migratievoorziening(199902) partij.

De keystores zijn gegenereerd van de migr-s12.modernodam.nl, hier draait een BRP release met een CA. 
Op basis daarvan is de Migratievoorziening keystore gemaakt. Zie BRP distributie voor documentatie omtrent uitgifte certificaten/keystores.






Possible errors:
- Als je MessageExpired terugkrijgt van BRP kan het zo zijn dat je locale tijd te ver uit sync is met de tijd van de migr-s12. 
	Makkelijkste oplossing is dan je eigen tijd even bijwerken.