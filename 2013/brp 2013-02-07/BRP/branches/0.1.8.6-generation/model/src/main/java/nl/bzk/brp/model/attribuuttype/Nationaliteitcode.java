/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.attribuuttype;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.attribuuttype.basis.NationaliteitcodeBasis;
import org.apache.commons.lang.StringUtils;

/**
 * Nationaliteitcode.
 */
@Embeddable
public final class Nationaliteitcode extends NationaliteitcodeBasis {

    private static final int LENGTE_CODE = 4;
    /**
     * Private constructor t.b.v. Hibernate.
     */
    private Nationaliteitcode() {
        super(null);
    }

    /**
     * De (op dit moment) enige constructor voor deze immutable class.
     *
     * @param waarde de waarde
     */
    public Nationaliteitcode(final Short waarde) {
        super(waarde);
    }

    @Override
    public String toString() {
        String resultaat;
        if (getWaarde() == null) {
            resultaat = StringUtils.EMPTY;
        } else {
            resultaat = getWaarde().toString();
            resultaat = StringUtils.leftPad(resultaat, LENGTE_CODE, '0');
        }
        return resultaat;
    }
}
