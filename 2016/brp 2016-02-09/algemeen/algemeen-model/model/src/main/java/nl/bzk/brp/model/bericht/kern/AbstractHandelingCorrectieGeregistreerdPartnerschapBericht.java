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
 * Met deze OT:"Administratieve handeling" worden gegevens van een bestaand OT:"Geregistreerd partnerschap"
 * gecorrigeerd. Hieronder valt ook het laten herleven van het OT:"Geregistreerd partnerschap" na vernietiging van een
 * nietigverklaring of van een ontbinding. _SoortRelatie_, OT:Betrokkenheid, G:"Persoon \
 * Geslachtsnaamcomponent.Identiteit"en  en G:Persoon.Naamgebruik kunnen niet met deze OT:"Administratieve handeling"
 * worden gecorrigeerd.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractHandelingCorrectieGeregistreerdPartnerschapBericht extends AdministratieveHandelingBericht {

    /**
     * Default constructor instantieert met de juiste SoortAdministratieveHandeling.
     *
     */
    public AbstractHandelingCorrectieGeregistreerdPartnerschapBericht() {
        super(new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.CORRECTIE_GEREGISTREERD_PARTNERSCHAP));
    }

}
