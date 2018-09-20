/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import org.simpleframework.xml.Element;

/**
 * Deze class representeert de LO3 Soort Nederlands reisdocument (35.10). Deze soort verwijst binnen LO3 naar een
 * Nederlands reisdocument in Tabel 48 (zie LO3.7).
 * 
 * Deze class is immutable en threadsafe.
 * 
 * 
 * 
 */
public final class Lo3SoortNederlandsReisdocument extends AbstractLo3Element {
    private static final long serialVersionUID = 1L;

    /**
     * Maakt een Lo3SoortNederlandsReisdocument object.
     * 
     * @param waarde
     *            de LO3 Soort Nederlands Reisdocument
     */
    public Lo3SoortNederlandsReisdocument(final String waarde) {
        this(waarde, null);
    }

    /**
     * Maake een Lo3SoortNederlandsReisdocument object met onderzoek.
     * 
     * @param waarde
     *            de LO3 Soort Nederlands Reisdocument
     * @param onderzoek
     *            het onderzoek waar deze code onder valt. Mag NULL zijn.
     */
    public Lo3SoortNederlandsReisdocument(@Element(name = "waarde", required = false) final String waarde, @Element(name = "onderzoek",
            required = false) final Lo3Onderzoek onderzoek)
    {
        super(waarde, onderzoek);
    }
}
