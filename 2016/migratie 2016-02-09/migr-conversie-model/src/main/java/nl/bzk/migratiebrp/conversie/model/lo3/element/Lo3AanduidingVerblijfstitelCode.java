/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import org.simpleframework.xml.Element;

/**
 * Deze class representeert het LO3 Element 'Aanduiding Verblijfstitel'. Dit is een unieke code die in tabel 56 van LO3
 * een aanduiding verblijfstitel uniek identificeert.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class Lo3AanduidingVerblijfstitelCode extends AbstractLo3Element {
    private static final long serialVersionUID = 1L;

    /**
     * Maakt een Lo3AanduidingVerblijfstitelCode object.
     * 
     * @param waarde
     *            de code die een Aanduiding Verblijfstitle uniek identificeert in LO3, mag niet null zijn
     */
    public Lo3AanduidingVerblijfstitelCode(final String waarde) {
        this(waarde, null);
    }

    /**
     * Constructor met onderzoek.
     * 
     * @param waarde
     *            code
     * @param onderzoek
     *            het onderzoek waar deze code onder valt. Mag NULL zijn.
     */
    public Lo3AanduidingVerblijfstitelCode(@Element(name = "waarde", required = false) final String waarde, @Element(name = "onderzoek",
            required = false) final Lo3Onderzoek onderzoek)
    {
        super(waarde, onderzoek);
    }
}
