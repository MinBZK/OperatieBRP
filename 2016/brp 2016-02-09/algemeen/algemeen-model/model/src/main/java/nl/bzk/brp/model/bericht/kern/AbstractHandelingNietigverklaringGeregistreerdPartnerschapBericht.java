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
 * Met deze OT:"Administratieve handeling" wordt de nietigverklaring (met terugwerkende kracht voor beide partners) van
 * een OT:"Geregistreerd partnerschap" geregistreerd, eventueel met wijziging van de G:Persoon.Naamgebruik en/of
 * G:"Persoon \ Geslachtsnaamcomponent.Identiteit".
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractHandelingNietigverklaringGeregistreerdPartnerschapBericht extends AdministratieveHandelingBericht {

    /**
     * Default constructor instantieert met de juiste SoortAdministratieveHandeling.
     *
     */
    public AbstractHandelingNietigverklaringGeregistreerdPartnerschapBericht() {
        super(new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.NIETIGVERKLARING_GEREGISTREERD_PARTNERSCHAP));
    }

}
