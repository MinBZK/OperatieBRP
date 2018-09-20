/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.attribuuttype;

import nl.bzk.brp.model.basis.AbstractGegevensAttribuutType;
import nl.bzk.brp.model.basis.SoortNull;

/**
 * Burgerservicenummer.
 */
public final class Burgerservicenummer extends AbstractGegevensAttribuutType<String> {

    /**
     * De (op dit moment) enige constructor voor deze immutable class.
     * @param waarde de waarde
     */
    public Burgerservicenummer(final String waarde) {
        super(waarde);
    }

    /**
     * Ondersteunend constructor met 2 parameters.
     * @param waarde de waarde
     * @param inOnderzoek attribbut is in onderzoek
     */
    public Burgerservicenummer(final String waarde, final boolean inOnderzoek) {
        super(waarde, inOnderzoek);
    }

    /**
     * Ondersteunend constructor met 3 parameters.
     * @param waarde de waarde
     * @param inOnderzoek attribbut is in onderzoek
     * @param soortNull welk type null is dit.
     */
    public Burgerservicenummer(final String waarde, final boolean inOnderzoek, final SoortNull soortNull) {
        super(waarde, inOnderzoek, soortNull);
    }

    /**
     * Ondersteunend constructor met 2 parameters.
     * @param waarde de waarde
     * @param soortNull welk type null is dit.
     */
    public Burgerservicenummer(final String waarde, final SoortNull soortNull) {
        super(waarde, false, soortNull);
    }

}
