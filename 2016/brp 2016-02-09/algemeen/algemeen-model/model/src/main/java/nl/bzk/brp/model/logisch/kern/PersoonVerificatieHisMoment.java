/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;

/**
 * Verificatie van gegevens in de BRP.
 *
 * Vooral voor personen die zich in het buitenland bevinden, is de kans groot dat gegevens snel verouderen. Zo is het
 * niet gegarandeerd dat een overlijden van een niet-ingezetene snel wordt gemeld. Om die reden is het, (vooral) voor de
 * populatie waarvan de bijhoudingsverantwoordelijkheid bij de Minister ligt, van belang om te weten of er verificatie
 * heeft plaatsgevonden. Het kan bijvoorbeeld zo zijn dat een Aangewezen bestuursorgaan ('RNI deelnemer') recent nog
 * contact heeft gehad met de persoon, en dat dit tot verificatie van gegevens heeft geleid. Er zijn verschillende
 * soorten verificatie; de bekendste is de 'Attestie de vita' die aangeeft dat de persoon nog in leven was ten tijde van
 * de verificatie. Door verificatiegegevens te registreren, kan de actualiteit van de persoonsgegevens van de
 * niet-ingezetene beter op waarde worden geschat.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisMomentGenerator")
public interface PersoonVerificatieHisMoment extends PersoonVerificatie {

    /**
     * Retourneert Standaard van Persoon \ Verificatie.
     *
     * @return Standaard.
     */
    HisPersoonVerificatieStandaardGroep getStandaard();

}
