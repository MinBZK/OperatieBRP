/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.attribuuttype;

import javax.persistence.Embeddable;

import nl.bzk.copy.model.basis.AbstractStatischAttribuutType;
import org.apache.commons.lang.StringUtils;

/**
 * Gemeentecode.
 */
@Embeddable
public final class Gemeentecode extends AbstractStatischAttribuutType<Short> {

    private static final int LENGTE_CODE = 4;

    /**
     * Private constructor t.b.v. Hibernate.
     */
    private Gemeentecode() {
        super(null);
    }

    /**
     * De (op dit moment) enige constructor voor deze immutable class.
     *
     * @param waarde de waarde
     */
    public Gemeentecode(final Short waarde) {
        super(waarde);
    }

    @Override
    public String toString() {
        String resultaat;
        if (this.getWaarde() == null) {
            resultaat = StringUtils.EMPTY;
        } else {
            resultaat = this.getWaarde().toString();
            resultaat = StringUtils.leftPad(resultaat, LENGTE_CODE, '0');
        }
        return resultaat;
    }
}
