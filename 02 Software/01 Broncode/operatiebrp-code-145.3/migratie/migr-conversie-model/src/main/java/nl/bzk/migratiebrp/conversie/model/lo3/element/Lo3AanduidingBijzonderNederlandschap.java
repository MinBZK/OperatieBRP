/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;

/**
 * Aanduiding bijzonder nederlandschap.
 */
public final class Lo3AanduidingBijzonderNederlandschap extends AbstractLo3Element {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * @param waarde code
     */
    public Lo3AanduidingBijzonderNederlandschap(final String waarde) {
        this(waarde, null);
    }

    /**
     * Constructor.
     * @param waarde code
     * @param onderzoek het onderzoek waar deze code onder valt. Mag NULL zijn.
     */
    public Lo3AanduidingBijzonderNederlandschap(@Element(name = "waarde", required = false) final String waarde, @Element(
            name = "onderzoek", required = false) final Lo3Onderzoek onderzoek) {
        super(waarde, onderzoek);
    }
}
