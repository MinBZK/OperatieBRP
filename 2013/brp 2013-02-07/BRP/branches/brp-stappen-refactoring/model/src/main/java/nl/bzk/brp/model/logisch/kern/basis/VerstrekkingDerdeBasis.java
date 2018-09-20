/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.logisch.kern.Persoon;


/**
 * De expliciete toestemming van betrokkene voor het verstrekken van diens persoonsgegevens aan een derde.
 *
 * Ingeschreven personen in de BRP hebben de mogelijkheid om bepaalde categorie�n van afnemers uit te sluiten van het
 * verkrijgen van diens persoonsgegevens. Naast deze 'uitsluiting' is expliciete 'insluiting' ook mogelijk: hierdoor kan
 * een partij die uit oogpunt van de verstrekkingsbeperking uitgesloten zou zijn, alsnog gegevens ontvangen over de
 * persoon.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.LogischModelGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:52 CET 2013.
 */
public interface VerstrekkingDerdeBasis extends ObjectType {

    /**
     * Retourneert Persoon van Verstrekking derde.
     *
     * @return Persoon.
     */
    Persoon getPersoon();

    /**
     * Retourneert Derde van Verstrekking derde.
     *
     * @return Derde.
     */
    Partij getDerde();

}
