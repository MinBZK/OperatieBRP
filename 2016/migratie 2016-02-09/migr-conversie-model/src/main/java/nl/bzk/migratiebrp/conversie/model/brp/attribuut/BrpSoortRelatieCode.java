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
 * Deze class representeert een Soort relatie.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpSoortRelatieCode extends AbstractBrpAttribuutMetOnderzoek implements Comparable<BrpSoortRelatieCode> {

    /** Huwelijk in BRP. */
    public static final BrpSoortRelatieCode HUWELIJK = new BrpSoortRelatieCode("H");
    /** Geregistreerd partnerschap in BRP. */
    public static final BrpSoortRelatieCode GEREGISTREERD_PARTNERSCHAP = new BrpSoortRelatieCode("G");
    /** Familierechtelijke betrekking in BRP. */
    public static final BrpSoortRelatieCode FAMILIERECHTELIJKE_BETREKKING = new BrpSoortRelatieCode("F");

    private static final long serialVersionUID = 1L;

    /**
     * Maakt een BrpSoortRelatieCode.
     *
     * @param waarde
     *            BRP waarde
     */
    public BrpSoortRelatieCode(final String waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een BrpSoortRelatieCode met onderzoek.
     *
     * @param waarde
     *            BRP waarde
     * @param onderzoek
     *            het onderzoek waar deze waarde onder valt. Mag NULL zijn.
     */
    public BrpSoortRelatieCode(
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

    @Override
    public int compareTo(final BrpSoortRelatieCode other) {
        return new CompareToBuilder().append(getWaarde(), other.getWaarde()).toComparison();
    }

    /**
     * Geeft een kopie van het attribuut terug zonder onderzoek.
     *
     * @return Geeft een kopie van het attribuut terug zonder onderzoek
     */
    public BrpSoortRelatieCode verwijderOnderzoek() {
        if (getWaarde() == null) {
            return null;
        }
        return new BrpSoortRelatieCode(getWaarde());
    }

    /**
     * Wrap de waarde en onderzoek naar een BrpSoortRelatieCode.
     *
     * @param waarde
     *            de String waarde
     * @param onderzoek
     *            het Lo3 onderzoek
     * @return BrpSoortRelatieCode object met daarin waarde en onderzoek
     */
    public static BrpSoortRelatieCode wrap(final String waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        }
        return new BrpSoortRelatieCode(waarde, onderzoek);
    }
}
