/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import org.simpleframework.xml.Element;

/**
 * Deze class representeert de LO3 Autoriteit van afgifte Nederlands reisdocument (35.40). De codering verwijst binnen
 * LO3 naar een autoriteit in Tabel 49 (zie LO3.7).
 * 
 * Deze class is immutable en threadsafe.
 * 
 * 
 * 
 */
public final class Lo3AutoriteitVanAfgifteNederlandsReisdocument extends AbstractLo3Element {
    private static final long serialVersionUID = 1L;

    /**
     * Maakt een Lo3AutoriteitVanAfgifteNederlandsReisdocument object.
     * 
     * @param waarde
     *            de LO3 Autoriteit van afgifte Nederlands Reisdocument, deze waarde mag niet null zijn
     * @throws NullPointerException
     *             als autoriteit null is
     * 
     */
    public Lo3AutoriteitVanAfgifteNederlandsReisdocument(final String waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een Lo3AutoriteitVanAfgifteNederlandsReisdocument object met onderzoek.
     * 
     * @param waarde
     *            de LO3 Autoriteit van afgifte Nederlands Reisdocument, deze waarde mag niet null zijn
     * @param onderzoek
     *            het onderzoek waar deze waarde onder valt. Mag NULL zijn.
     * 
     */
    public Lo3AutoriteitVanAfgifteNederlandsReisdocument(@Element(name = "waarde", required = false) final String waarde, @Element(
            name = "onderzoek", required = false) final Lo3Onderzoek onderzoek)
    {
        super(waarde, onderzoek);
    }
}
