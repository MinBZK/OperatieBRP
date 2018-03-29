/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;

/**
 * Deze class representeert een LO3Character waarde.
 *
 * Deze class is immutable en threadsafe.
 */
public final class Lo3Character extends AbstractLo3Element {
    private static final long serialVersionUID = 1L;

    /**
     * Maakt een Lo3Character object.
     * @param waarde de character waarde
     */
    public Lo3Character(final Character waarde) {
        this(String.valueOf(waarde), null);
    }

    /**
     * Maakt een Lo3Character object met onderzoek.
     * @param waarde de character waarde
     * @param onderzoek het onderzoek waar deze waarde onder valt. Mag null zijn.
     */
    public Lo3Character(
            @Element(name = "waarde", required = false) final String waarde,
            @Element(name = "onderzoek", required = false) final Lo3Onderzoek onderzoek) {
        super(waarde, onderzoek);
    }

    /**
     * Geef de waarde van character waarde.
     * @return character waarde
     */
    public Character getCharacterWaarde() {
        return super.getWaarde() == null ? null : super.getWaarde().charAt(0);
    }

    /**
     * Wrap een bestaande Character in een Lo3Character object.
     * @param waarde De te wrappen Character, mag null zijn.
     * @return Een Lo3Character object, of null als de waarde null was.
     */
    public static Lo3Character wrap(final Character waarde) {
        if (waarde == null) {
            return null;
        }
        return new Lo3Character(waarde);
    }

    /**
     * Unwrap een Lo3Character object om de Character waarde terug te krijgen.
     * @param lo3Character De Lo3Character, mag null zijn.
     * @return Een Character object, of null als de Lo3Character null was.
     */
    public static Character unwrap(final Lo3Character lo3Character) {
        if (lo3Character == null || !lo3Character.isInhoudelijkGevuld()) {
            return null;
        }
        return lo3Character.getWaarde().charAt(0);
    }
}
