/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import nl.bzk.brp.model.logisch.kern.basis.PersoonVerificatieBasis;


/**
 * Verificatie van gegevens in de BRP.
 *
 * Vooral voor personen die zich in het buitenland bevinden, is de kans groot dat gegevens snel verouderen. Zo is het
 * niet gegarandeerd dat een overlijden van een niet-ingezetene snel wordt gemeld.
 * Om die reden is het, (vooral) voor de populatie waarvan de bijhoudingsverantwoordelijkheid bij de Minister ligt, van
 * belang om te weten of er verificatie heeft plaatsgevonden. Het kan bijvoorbeeld zo zijn dat een Aangewezen
 * bestuursorgaan ('RNI deelnemer') recent nog contact heeft gehad met de persoon, en dat dit tot verificatie van
 * gegevens heeft geleid.
 * Er zijn verschillende soorten verificatie; de bekendste is de 'Attestie de vita' die aangeeft dat de persoon nog in
 * leven was ten tijde van de verificatie.
 * Door verificatiegegevens te registreren, kan de actualiteit van de persoonsgegevens van de niet-ingezetene beter op
 * waarde worden geschat.
 *
 * Hier speelt een issue: is het een vrij tekstveld oplossing �we vinden XXX voor persoon�, of is het een beschreven
 * waardebereik. Is nog twistpunt tussen makers LO3.8 en makers LO BRP.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.LogischModelGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.1.8.
 * Gegenereerd op: Tue Nov 27 12:05:00 CET 2012.
 */
public interface PersoonVerificatie extends PersoonVerificatieBasis {

}
