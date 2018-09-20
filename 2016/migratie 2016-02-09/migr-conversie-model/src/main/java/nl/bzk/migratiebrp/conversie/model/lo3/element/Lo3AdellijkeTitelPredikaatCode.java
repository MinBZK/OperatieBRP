/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import org.simpleframework.xml.Element;

/**
 * Deze class representeert een LO3 Adellijke Titel / Predikaat code.
 * 
 * LO3 element 02.20
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class Lo3AdellijkeTitelPredikaatCode extends AbstractLo3Element {
    private static final long serialVersionUID = 1L;

    /**
     * Maakt een Lo3AdellijkeTitelPredikaatCode object.
     * 
     * @param waarde
     *            de code
     */
    public Lo3AdellijkeTitelPredikaatCode(final String waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een Lo3AdellijkeTitelPredikaatCode object met onderzoek.
     * 
     * @param waarde
     *            de code
     * @param onderzoek
     *            het onderzoek waar deze code onder valt. Mag NULL zijn.
     * 
     */
    public Lo3AdellijkeTitelPredikaatCode(@Element(name = "waarde", required = false) final String waarde, @Element(name = "onderzoek",
            required = false) final Lo3Onderzoek onderzoek)
    {
        super(waarde, onderzoek);
    }
}
