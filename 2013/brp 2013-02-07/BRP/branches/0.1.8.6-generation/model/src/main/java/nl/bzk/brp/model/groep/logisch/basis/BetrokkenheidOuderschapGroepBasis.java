/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.logisch.basis;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.basis.Groep;

/**
 * Interface voor de groep ouderschap van objecttype betrokkenheid.
 */
public interface BetrokkenheidOuderschapGroepBasis extends Groep {

    /**
     * Retourneert indicatie ouder.
     * @return Ja of null.
     */
    Ja getIndOuder();

    /**
     * Retourneeert indicatie adresgevende ouder.
     * @return Ja of null
     */
    Ja getIndAdresGevend();

    /**
     * Retourneert datum aanvang ouderschap.
     * @return Datum.
     */
    Datum getDatumAanvang();

}
