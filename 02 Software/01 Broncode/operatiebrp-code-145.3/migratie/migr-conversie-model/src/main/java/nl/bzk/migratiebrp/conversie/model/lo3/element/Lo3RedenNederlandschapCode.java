/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;

/**
 * De class representeert een LO3 Reden verkrijging/verlies Nederlandschap code. Deze code verwijst naar tabel 37 (zie
 * LO3.7).
 *
 * Deze class is immutable en threadsafe.
 */
public final class Lo3RedenNederlandschapCode extends AbstractLo3Element {
    /**
     * Einde bijhouding nationaliteit code.
     */
    public static final String EINDE_BIJHOUDING_CODE = "404";
    /**
     * Reden einde bijhouding nationaliteit code.
     */
    public static final Lo3RedenNederlandschapCode EINDE_BIJHOUDING = new Lo3RedenNederlandschapCode(EINDE_BIJHOUDING_CODE);
    private static final long serialVersionUID = 1L;

    /**
     * Maakt een Lo3RedenNederlandschapCode object.
     * @param waarde de code
     */
    public Lo3RedenNederlandschapCode(final String waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een Lo3RedenNederlandschapCode object met onderzoek.
     * @param waarde de code
     * @param onderzoek het onderzoek waar deze code onder valt. Mag NULL zijn.
     */
    public Lo3RedenNederlandschapCode(
            @Element(name = "waarde", required = false) final String waarde,
            @Element(name = "onderzoek", required = false) final Lo3Onderzoek onderzoek) {
        super(waarde, onderzoek);
    }
}
