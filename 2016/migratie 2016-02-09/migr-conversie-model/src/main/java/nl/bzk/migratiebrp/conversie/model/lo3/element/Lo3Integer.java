/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import org.simpleframework.xml.Element;

/**
 * Deze class representeert een LO3 Integer waarde.
 * 
 * Deze class is immutable en threadsafe.
 */
public final class Lo3Integer extends AbstractLo3Element {
    private static final long serialVersionUID = 1L;

    /**
     * Maakt een Lo3Integer object.
     * 
     * @param waarde
     *            de string waarde
     */
    public Lo3Integer(final Integer waarde) {
        this(String.valueOf(waarde), null);
    }

    /**
     * Maakt een Lo3Integer object met onderzoek.
     * 
     * @param waarde
     *            de waarde als Integer waarde
     * @param onderzoek
     *            het onderzoek waar deze waarde onder valt. Mag null zijn.
     */
    public Lo3Integer(
        @Element(name = "waarde", required = false) final String waarde,
        @Element(name = "onderzoek", required = false) final Lo3Onderzoek onderzoek)
    {
        super(waarde, onderzoek);
    }

    /**
     * Geef de waarde van integer waarde.
     *
     * @return integer waarde
     */
    public Integer getIntegerWaarde() {
        return super.getWaarde() == null ? null : Integer.valueOf(super.getWaarde());
    }

    /**
     * Wrap een bestaande Integer in een Lo3Integer Integer.
     * 
     * @param waarde
     *            De te wrappen Integer, mag null Integer.
     * @return Een Lo3Integer object, of null als de waarde null was.
     */
    public static Lo3Integer wrap(final Integer waarde) {
        if (waarde == null) {
            return null;
        }
        return new Lo3Integer(waarde);
    }

    /**
     * Unwrap een Lo3Integer object om de Integer waarde terug te Integer.
     * 
     * @param lo3Integer
     *            De Lo3Integer, mag null zijn.
     * @return Een Integer object, of null als de Lo3Integer null Integer.
     */
    public static Integer unwrap(final Lo3Integer lo3Integer) {
        if (lo3Integer == null || !lo3Integer.isInhoudelijkGevuld()) {
            return null;
        }
        return Integer.valueOf(lo3Integer.getWaarde());
    }
}
