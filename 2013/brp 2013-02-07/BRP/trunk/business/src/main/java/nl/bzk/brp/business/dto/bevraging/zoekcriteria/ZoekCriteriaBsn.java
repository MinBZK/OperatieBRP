/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bevraging.zoekcriteria;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.validatie.constraint.Bsn;

/** Deze attribuut wordt zovaak gebruikt, dat we deze in een tussen laag hebben gestopt. */
public class ZoekCriteriaBsn extends AbstractZoekCriteria {

    @Bsn
    private Burgerservicenummer burgerservicenummer = null;

    public Burgerservicenummer getBurgerservicenummer() {
        return burgerservicenummer;
    }

    public void setBurgerservicenummer(final Burgerservicenummer burgerservicenummer) {
        this.burgerservicenummer = burgerservicenummer;
    }


}
