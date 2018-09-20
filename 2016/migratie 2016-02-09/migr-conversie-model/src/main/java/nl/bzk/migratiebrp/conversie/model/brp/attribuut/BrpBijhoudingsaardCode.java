/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert een BRP bijhoudingsaard.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpBijhoudingsaardCode extends AbstractBrpAttribuutMetOnderzoek {

    /**
     * Bijhoudingsaard class die een ingezetene representeert.
     */
    public static final BrpBijhoudingsaardCode INGEZETENE = new BrpBijhoudingsaardCode("I");
    /**
     * Bijhoudingsaard class die een register-niet-ingezetenen representeerd.
     */
    public static final BrpBijhoudingsaardCode NIET_INGEZETENE = new BrpBijhoudingsaardCode("N");
    /**
     * Bijhoudingsaard class die een onbekend representeerd.
     */
    public static final BrpBijhoudingsaardCode ONBEKEND = new BrpBijhoudingsaardCode("?");

    private static final long serialVersionUID = 1L;

    /**
     * Maakt een BrpBijhoudingsaardCode.
     * 
     * @param waarde
     *            waarde
     */
    public BrpBijhoudingsaardCode(final String waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een BrpBijhoudingsaardCode met onderzoek.
     * 
     * @param waarde
     *            waarde
     * @param onderzoek
     *            het onderzoek waar deze waarde onder valt. Mag NULL zijn.
     */
    public BrpBijhoudingsaardCode(
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
