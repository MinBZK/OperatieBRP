/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatieAttribuut;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractIndicatorIndicatieVolledigeVerstrekkingsbeperkingBericht extends PersoonIndicatieBericht {

    /**
     * Default constructor instantieert met de juiste SoortIndicatie.
     *
     */
    public AbstractIndicatorIndicatieVolledigeVerstrekkingsbeperkingBericht() {
        super(new SoortIndicatieAttribuut(SoortIndicatie.INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING));
    }

}
