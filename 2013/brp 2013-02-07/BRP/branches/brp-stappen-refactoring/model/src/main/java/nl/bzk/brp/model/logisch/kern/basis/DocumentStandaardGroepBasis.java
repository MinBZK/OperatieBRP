/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Aktenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentIdentificatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentOmschrijving;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.basis.Groep;


/**
 * Vorm van historie: formeel. Motivatie: het objecttype wordt gebruikt voor de verantwoording van een specifieke BRP
 * actie. Denkbaar is dat twee verschillende BRP acties verwijzen naar hetzelfde Document; relevant is welke gegevens er
 * toen geregistreerd stonden bij het Document, vandaar dat formele historie relevant is. NB: dit onderdeel van het
 * model is nog in ontwikkeling. Denkbaar is dat de modellering anders wordt. RvdP 17 jan 2012.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.LogischModelGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:52 CET 2013.
 */
public interface DocumentStandaardGroepBasis extends Groep {

    /**
     * Retourneert Identificatie van Standaard.
     *
     * @return Identificatie.
     */
    DocumentIdentificatie getIdentificatie();

    /**
     * Retourneert Aktenummer van Standaard.
     *
     * @return Aktenummer.
     */
    Aktenummer getAktenummer();

    /**
     * Retourneert Omschrijving van Standaard.
     *
     * @return Omschrijving.
     */
    DocumentOmschrijving getOmschrijving();

    /**
     * Retourneert Partij van Standaard.
     *
     * @return Partij.
     */
    Partij getPartij();

}
