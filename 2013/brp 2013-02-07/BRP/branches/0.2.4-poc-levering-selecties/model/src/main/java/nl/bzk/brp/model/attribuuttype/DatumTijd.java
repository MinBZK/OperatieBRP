/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.attribuuttype;

import java.util.Date;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonCreator;
import nl.bzk.brp.model.basis.AbstractGegevensAttribuutType;

/**
 * Attribuut type Datum \ Tijd.
 */
@Embeddable
public final class DatumTijd extends AbstractGegevensAttribuutType<Date> {

    /**
     * Private constructor t.b.v. Hibernate.
     */
    private DatumTijd() {
        super(null);
    }

    /**
     * De (op dit moment) enige constructor voor deze immutable class.
     * @param waarde de waarde
     */
    @JsonCreator
    public DatumTijd(final Date waarde) {
        super(waarde);
    }

    /**
     * Test of deze datum na de opgegeven datum ligt.
     *
     * @param vergelijkingsDatum de datum waarmee vergeleken wordt.
     * @return of deze datum na de opgegeven datum ligt.
     */
    public boolean na(final DatumTijd vergelijkingsDatum) {
        if (vergelijkingsDatum == null || vergelijkingsDatum.getWaarde() == null) {
            throw new IllegalArgumentException("DatumTijd moet aanwezig zijn.");
        }
        return getWaarde().after(vergelijkingsDatum.getWaarde());
    }

    /**
     * Test of deze datum voor de opgegeven datum ligt.
     *
     * @param vergelijkingsDatum de datum waarmee vergeleken wordt.
     * @return of deze datum voor de opgegeven datum ligt.
     */
    public boolean voor(final DatumTijd vergelijkingsDatum) {
        if (vergelijkingsDatum == null || vergelijkingsDatum.getWaarde() == null) {
            throw new IllegalArgumentException("DatumTijd moet aanwezig zijn.");
        }
        return getWaarde().before(vergelijkingsDatum.getWaarde());
    }
}
