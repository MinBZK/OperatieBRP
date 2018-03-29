/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;

/**
 * Deze class representeert een LO3 Long waarde.
 *
 * Deze class is immutable en threadsafe.
 */
public final class Lo3Long extends AbstractLo3Element {
    private static final long serialVersionUID = 1L;

    /**
     * Maakt een Lo3Long object.
     * @param waarde de string waarde
     */
    public Lo3Long(final Long waarde) {
        this(String.valueOf(waarde), null);
    }

    /**
     * Maakt een Lo3Long object met onderzoek.
     * @param waarde de waarde als Long
     * @param onderzoek het onderzoek waar deze waarde onder valt. Mag null zijn.
     */
    public Lo3Long(
            @Element(name = "waarde", required = false) final String waarde,
            @Element(name = "onderzoek", required = false) final Lo3Onderzoek onderzoek) {
        super(waarde, onderzoek);
    }

    /**
     * Geef de waarde van long waarde.
     * @return long waarde
     */
    public Long getLongWaarde() {
        return super.getWaarde() == null ? null : Long.valueOf(super.getWaarde());
    }

    /**
     * Wrap een bestaande Long in een Lo3Long object.
     * @param waarde De te wrappen Long, mag null zijn.
     * @return Een Lo3Long object, of null als de waarde null was.
     */
    public static Lo3Long wrap(final Long waarde) {
        if (waarde == null) {
            return null;
        }
        return new Lo3Long(waarde);
    }

    /**
     * Unwrap een Lo3Long object om de Long waarde terug te krijgen.
     * @param lo3Long De Lo3Long, mag null zijn.
     * @return Een Long object, of null als de Lo3Long null was.
     */
    public static Long unwrap(final Lo3Long lo3Long) {
        if (lo3Long == null || !lo3Long.isInhoudelijkGevuld()) {
            return null;
        }
        return Long.valueOf(lo3Long.getWaarde());
    }
}
