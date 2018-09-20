/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.attribuuttype;

import javax.persistence.Embeddable;

import nl.bzk.copy.model.basis.AbstractStatischAttribuutType;

/**
 * ISO 3166-1 alpha 2 code voor landen.
 */
@Embeddable
public final class ISO31661Alpha2 extends AbstractStatischAttribuutType<String> {

    /**
     * Private constructor t.b.v. Hibernate.
     */
    private ISO31661Alpha2() {
        super(null);
    }

    /**
     * De (op dit moment) enige constructor voor deze immutable class.
     *
     * @param waarde de waarde
     */
    public ISO31661Alpha2(final String waarde) {
        super(waarde);
    }
}
