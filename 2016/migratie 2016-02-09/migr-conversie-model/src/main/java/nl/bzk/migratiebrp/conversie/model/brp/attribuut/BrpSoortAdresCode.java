/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert een BRP Soort Adres code.
 * 
 */
public final class BrpSoortAdresCode extends AbstractBrpAttribuutMetOnderzoek {

    /**
     * Functieadres class die een Briefadres representeert.
     */
    public static final BrpSoortAdresCode B = new BrpSoortAdresCode("B");
    /**
     * Functieadres class die een Woonadres representeert.
     */
    public static final BrpSoortAdresCode W = new BrpSoortAdresCode("W");

    private static final long serialVersionUID = 1L;

    /**
     * Maakt een BrpSoortAdresCode.
     * 
     * @param waarde
     *            BRP waarde
     */
    public BrpSoortAdresCode(final String waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een BrpSoortAdresCode met onderzoek.
     * 
     * @param waarde
     *            BRP waarde
     * @param onderzoek
     *            het onderzoek waar deze waarde onder valt. Mag NULL zijn.
     */
    public BrpSoortAdresCode(
        @Element(name = "waarde", required = false) final String waarde,
        @Element(name = "onderzoek", required = false) final Lo3Onderzoek onderzoek)
    {
        super(waarde, onderzoek);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpAttribuutMetOnderzoek#getWaarde()
     */
    @Override
    @Element(name = "waarde", required = false)
    public String getWaarde() {
        return (String) unwrapImpl(this);
    }
}
