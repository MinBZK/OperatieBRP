/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.attribuuttype;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.basis.AbstractGegevensAttribuutType;

/** Datum. */
@SuppressWarnings("serial")
@Embeddable
public final class Datum extends AbstractGegevensAttribuutType<Integer> {

    /** Private constructor t.b.v. Hibernate. */
    private Datum() {
        super(null);
    }

    /**
     * De (op dit moment) enige constructor voor deze immutable class.
     *
     * @param waarde de waarde
     */
    public Datum(final Integer waarde) {
        super(waarde);
    }

    /**
     * Test of deze datum na de opgegeven datum ligt.
     *
     * @param vergelijkingsDatum de datum waarmee vergeleken wordt.
     * @return of deze datum na de opgegeven datum ligt.
     */
    public boolean na(final Datum vergelijkingsDatum) {
        if (vergelijkingsDatum == null || vergelijkingsDatum.getWaarde() == null) {
            throw new IllegalArgumentException("Datum moet aanwezig zijn.");
        }
        return getWaarde() > vergelijkingsDatum.getWaarde();
    }

    /**
     * Test of deze datum voor de opgegeven datum ligt.
     *
     * @param vergelijkingsDatum de datum waarmee vergeleken wordt.
     * @return of deze datum voor de opgegeven datum ligt.
     */
    public boolean voor(final Datum vergelijkingsDatum) {
        if (vergelijkingsDatum == null || vergelijkingsDatum.getWaarde() == null) {
            throw new IllegalArgumentException("Datum moet aanwezig zijn.");
        }
        return getWaarde() < vergelijkingsDatum.getWaarde();
    }
}
