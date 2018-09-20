/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bevraging;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;


/**
 * Abstracte klasse voor elk vraag bericht aan de BRP.
 */
public class BevragingsBericht extends BerichtBericht {

    /**
     * Standaard constructor die het soort van het bericht zet.
     *
     * @param soort de soort van het bericht.
     */
    protected BevragingsBericht(final SoortBericht soort) {
        super(new SoortBerichtAttribuut(soort));
    }

    /**
     * Deze methode geeft de bsn die in de vraag gebruikt wordt. (en wel als onderdeel van de zoek criteria in geval van VraagBericht). Dit zou anders
     * moeten zijn.
     *
     * @return de bsn of null als niet van toepassing is.
     */
    public final String getBurgerservicenummerForLocks() {
        if (getZoekcriteriaPersoon() != null && getZoekcriteriaPersoon().getBurgerservicenummer() != null) {
            return String.valueOf(getZoekcriteriaPersoon().getBurgerservicenummer().toString());
        }
        return null;
    }

}
