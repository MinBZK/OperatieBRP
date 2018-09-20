/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AktenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentIdentificatieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Vorm van historie: formeel. Motivatie: het objecttype wordt gebruikt voor de verantwoording van een specifieke BRP
 * actie. Denkbaar is dat twee verschillende BRP acties verwijzen naar hetzelfde Document; relevant is welke gegevens er
 * toen geregistreerd stonden bij het Document, vandaar dat formele historie relevant is. NB: dit onderdeel van het
 * model is nog in ontwikkeling. Denkbaar is dat de modellering anders wordt.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface DocumentStandaardGroepBasis extends Groep {

    /**
     * Retourneert Identificatie van Standaard.
     *
     * @return Identificatie.
     */
    DocumentIdentificatieAttribuut getIdentificatie();

    /**
     * Retourneert Aktenummer van Standaard.
     *
     * @return Aktenummer.
     */
    AktenummerAttribuut getAktenummer();

    /**
     * Retourneert Omschrijving van Standaard.
     *
     * @return Omschrijving.
     */
    DocumentOmschrijvingAttribuut getOmschrijving();

    /**
     * Retourneert Partij van Standaard.
     *
     * @return Partij.
     */
    PartijAttribuut getPartij();

}
