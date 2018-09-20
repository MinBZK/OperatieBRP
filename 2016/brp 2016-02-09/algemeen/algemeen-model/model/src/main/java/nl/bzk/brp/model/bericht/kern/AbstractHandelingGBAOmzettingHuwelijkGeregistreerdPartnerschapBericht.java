/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractHandelingGBAOmzettingHuwelijkGeregistreerdPartnerschapBericht extends AdministratieveHandelingBericht {

    /**
     * Default constructor instantieert met de juiste SoortAdministratieveHandeling.
     *
     */
    public AbstractHandelingGBAOmzettingHuwelijkGeregistreerdPartnerschapBericht() {
        super(new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.G_B_A_OMZETTING_HUWELIJK_GEREGISTREERD_PARTNERSCHAP));
    }

}
