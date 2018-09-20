/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ber;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.BerichtdataLaxXSDAttribuut;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * Het bijhoudingsplan welke opgesteld is naar aanleiding van een bijhoudingsvoorstel.
 *
 * Het bijhoudingsplan wordt niet gepersisteerd in de database. Uit de Bijhoudings POCs kwam geen businesscase om dit te
 * doen.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface BijhoudingsplanBasis extends BrpObject {

    /**
     * Retourneert Partij bijhoudingsvoorstel van Bijhoudingsplan.
     *
     * @return Partij bijhoudingsvoorstel.
     */
    Short getPartijBijhoudingsvoorstelId();

    /**
     * Retourneert Bericht van Bijhoudingsplan.
     *
     * @return Bericht.
     */
    BerichtdataLaxXSDAttribuut getBericht();

    /**
     * Retourneert Bericht resultaat van Bijhoudingsplan.
     *
     * @return Bericht resultaat.
     */
    BerichtdataLaxXSDAttribuut getBerichtResultaat();

    /**
     * Retourneert Administratieve handeling van Bijhoudingsplan.
     *
     * @return Administratieve handeling.
     */
    Long getAdministratieveHandelingId();

}
