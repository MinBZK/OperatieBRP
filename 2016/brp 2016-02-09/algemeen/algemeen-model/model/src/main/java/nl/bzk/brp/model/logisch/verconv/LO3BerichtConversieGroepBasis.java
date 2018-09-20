/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.verconv;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.verconv.LO3FoutcodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.verconv.LO3VerwerkingsmeldingAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface LO3BerichtConversieGroepBasis extends Groep {

    /**
     * Retourneert Tijdstip conversie van Conversie.
     *
     * @return Tijdstip conversie.
     */
    DatumTijdAttribuut getTijdstipConversie();

    /**
     * Retourneert Foutcode van Conversie.
     *
     * @return Foutcode.
     */
    LO3FoutcodeAttribuut getFoutcode();

    /**
     * Retourneert Verwerkingsmelding van Conversie.
     *
     * @return Verwerkingsmelding.
     */
    LO3VerwerkingsmeldingAttribuut getVerwerkingsmelding();

}
