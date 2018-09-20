/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert een Soort betrokkenheid. Deze class is immutable en threadsafe.
 */
public final class BrpSoortBetrokkenheidCode extends AbstractBrpAttribuutMetOnderzoek implements Comparable<BrpSoortBetrokkenheidCode> {

    /**
     * Kind.
     */
    public static final BrpSoortBetrokkenheidCode KIND = new BrpSoortBetrokkenheidCode("K", "Kind");
    /**
     * Ouder.
     */
    public static final BrpSoortBetrokkenheidCode OUDER = new BrpSoortBetrokkenheidCode("O", "Ouder");
    /**
     * Partner.
     */
    public static final BrpSoortBetrokkenheidCode PARTNER = new BrpSoortBetrokkenheidCode("P", "Partner");

    private static final long serialVersionUID = 1L;

    private final String omschrijving;

    /**
     * Maakt een BrpSoortBetrokkenheidCode.
     *
     * @param waarde
     *            BRP waarde
     */
    public BrpSoortBetrokkenheidCode(final String waarde) {
        this(waarde, "");
    }

    /**
     * Maakt een BrpSoortBetrokkenheidCode met onderzoek.
     *
     * @param waarde
     *            BRP waarde
     * @param onderzoek
     *            het onderzoek waar deze waarde onder valt. Mag NULL zijn.
     */
    public BrpSoortBetrokkenheidCode(
        @Element(name = "waarde", required = false) final String waarde,
        @Element(name = "onderzoek", required = false) final Lo3Onderzoek onderzoek)
    {
        this(waarde, "", onderzoek);
    }

    /**
     * Maakt een BrpBijhoudingsaardCode.
     *
     * @param waarde
     *            BRP waarde
     * @param omschrijving
     *            omschrijving
     */
    public BrpSoortBetrokkenheidCode(final String waarde, final String omschrijving) {
        this(waarde, omschrijving, null);
    }

    /**
     * Maakt een BrpSoortBetrokkenheidCode met onderzoek.
     *
     * @param waarde
     *            BRP waarde
     * @param omschrijving
     *            omschrijving
     * @param onderzoek
     *            het onderzoek waar deze waarde onder valt. Mag NULL zijn.
     */
    public BrpSoortBetrokkenheidCode(final String waarde, final String omschrijving, final Lo3Onderzoek onderzoek) {
        super(waarde, onderzoek);
        this.omschrijving = omschrijving;
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

    /**
     * Geef de waarde van omschrijving.
     *
     * @return omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    @Override
    public int compareTo(final BrpSoortBetrokkenheidCode other) {
        return new CompareToBuilder().append(getWaarde(), other.getWaarde()).toComparison();
    }
}
