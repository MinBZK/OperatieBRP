/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractActieRegistratieOverlijdenBericht extends ActieBericht {

    /**
     * Default constructor instantieert met de juiste SoortActie.
     *
     */
    public AbstractActieRegistratieOverlijdenBericht() {
        super(new SoortActieAttribuut(SoortActie.REGISTRATIE_OVERLIJDEN));
    }

}
