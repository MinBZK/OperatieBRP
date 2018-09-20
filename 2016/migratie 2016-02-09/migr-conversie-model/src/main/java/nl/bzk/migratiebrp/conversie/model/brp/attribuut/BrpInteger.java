/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert een BRP Integer waarde.
 * 
 * Deze class is immutable en threadsafe.
 */
public final class BrpInteger extends AbstractBrpAttribuutMetOnderzoek implements Comparable<BrpInteger> {
    private static final long serialVersionUID = 1L;

    /**
     * Maakt een BrpInteger object .
     * 
     * @param waarde
     *            de integer waarde
     */
    public BrpInteger(final Integer waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een BrpInteger object met onderzoek.
     * 
     * @param waarde
     *            de integer waarde
     * @param onderzoek
     *            het onderzoek waar deze waarde onder valt. Mag NULL zijn.
     */
    public BrpInteger(
        @Element(name = "waarde", required = false) final Integer waarde,
        @Element(name = "onderzoek", required = false) final Lo3Onderzoek onderzoek)
    {
        super(waarde, onderzoek);
    }

    /**
     * Wrap de waarde en onderzoek naar een BrpInteger.
     * 
     * @param waarde
     *            de Integer waarde
     * @param onderzoek
     *            het Lo3 onderzoek
     * @return BrpInteger object met daarin waarde en onderzoek
     */
    public static BrpInteger wrap(final Integer waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        }
        return new BrpInteger(waarde, onderzoek);
    }

    /**
     * Unwrap een BrpInteger object om de Integer waarde terug te krijgen.
     * 
     * @param attribuut
     *            De BrpInteger, mag null zijn.
     * @return Een Integer object, of null als de BrpInteger null was.
     */
    public static Integer unwrap(final BrpInteger attribuut) {
        return (Integer) AbstractBrpAttribuutMetOnderzoek.unwrapImpl(attribuut);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpAttribuutMetOnderzoek#getWaarde()
     */
    @Override
    @Element(name = "waarde", required = false)
    public Integer getWaarde() {
        return unwrap(this);
    }

    @Override
    public int compareTo(final BrpInteger andereInteger) {
        return getWaarde().compareTo(andereInteger.getWaarde());
    }

    /**
     * Geeft een kopie van het attribuut terug zonder onderzoek.
     * 
     * @return Geeft een kopie van het attribuut terug zonder onderzoek
     */
    public BrpInteger verwijderOnderzoek() {
        if (getWaarde() == null) {
            return null;
        }
        return new BrpInteger(getWaarde());
    }
}
