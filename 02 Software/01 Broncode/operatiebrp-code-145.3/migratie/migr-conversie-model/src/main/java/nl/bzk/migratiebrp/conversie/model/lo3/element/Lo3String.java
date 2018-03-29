/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;

/**
 * Deze class representeert een LO3 String waarde.
 *
 * Deze class is immutable en threadsafe.
 */
public final class Lo3String extends AbstractLo3Element {
    private static final long serialVersionUID = 1L;

    /**
     * Maakt een Lo3String object.
     * @param waarde de string waarde
     */
    public Lo3String(final String waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een Lo3String object met onderzoek.
     * @param waarde de string waarde
     * @param onderzoek het onderzoek waar deze waarde onder valt. Mag null zijn.
     */
    public Lo3String(
            @Element(name = "waarde", required = false) final String waarde,
            @Element(name = "onderzoek", required = false) final Lo3Onderzoek onderzoek) {
        super(waarde, onderzoek);
    }

    /**
     * Wrap een bestaande String in een Lo3String object.
     * @param waarde De te wrappen String, mag null zijn.
     * @return Een Lo3String object, of null als de waarde null was.
     */
    public static Lo3String wrap(final String waarde) {
        if (waarde == null) {
            return null;
        }
        return new Lo3String(waarde);
    }

    /**
     * Unwrap een Lo3String object om de String waarde terug te krijgen.
     * @param lo3String De Lo3String, mag null zijn.
     * @return Een String object, of null als de Lo3String null was.
     */
    public static String unwrap(final Lo3String lo3String) {
        if (lo3String == null || !lo3String.isInhoudelijkGevuld()) {
            return null;
        }
        return lo3String.getWaarde();
    }
}
