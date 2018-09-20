/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.attribuuttype;

import javax.persistence.Embeddable;

import nl.bzk.copy.model.basis.AbstractGegevensAttribuutType;
import nl.bzk.copy.model.basis.SoortNull;

/**
 * Gemeentedeel.
 */
@Embeddable
public final class Gemeentedeel extends AbstractGegevensAttribuutType<String> {

    /**
     * Private constructor t.b.v. Hibernate.
     */
    private Gemeentedeel() {
        super(null);
    }

    /**
     * De (op dit moment) enige constructor voor deze immutable class.
     *
     * @param waarde de waarde
     */
    public Gemeentedeel(final String waarde) {
        super(waarde);
    }

    /**
     * Constructor die direct de waarde en de meta data als 'in onderzoek' en 'soort null' zet.
     *
     * @param waarde      de waarde van het attribuut.
     * @param inOnderzoek of het attribuut in onderzoek staat of niet.
     * @param soortNull   de reden waarom het attribuut null is.
     */
    public Gemeentedeel(final String waarde, final boolean inOnderzoek, final SoortNull soortNull) {
        super(waarde, inOnderzoek, soortNull);
    }
}
