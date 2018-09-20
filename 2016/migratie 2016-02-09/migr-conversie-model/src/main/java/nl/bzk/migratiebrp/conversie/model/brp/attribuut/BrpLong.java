/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert een BRP Long waarde.
 * 
 * Deze class is immutable en threadsafe.
 */
public final class BrpLong extends AbstractBrpAttribuutMetOnderzoek implements Comparable<BrpLong> {
    private static final long serialVersionUID = 1L;

    /**
     * Maakt een BrpLong object .
     * 
     * @param waarde
     *            de long waarde
     */
    public BrpLong(final Long waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een BrpLong object met onderzoek.
     * 
     * @param waarde
     *            de long waarde
     * @param onderzoek
     *            het onderzoek waar deze waarde onder valt. Mag NULL zijn.
     */
    public BrpLong(
        @Element(name = "waarde", required = false) final Long waarde,
        @Element(name = "onderzoek", required = false) final Lo3Onderzoek onderzoek)
    {
        super(waarde, onderzoek);
    }

    @Override
    public int compareTo(final BrpLong andereLong) {
        return getWaarde().compareTo(andereLong.getWaarde());
    }

    /**
     * Wrap de waarde en onderzoek naar een BrpLong.
     * 
     * @param waarde
     *            de Long waarde
     * @param onderzoek
     *            het Lo3 onderzoek
     * @return BrpLong object met daarin waarde en onderzoek
     */
    public static BrpLong wrap(final Long waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        }
        return new BrpLong(waarde, onderzoek);
    }

    /**
     * Unwrap een BrpLong object om de Long waarde terug te krijgen.
     * 
     * @param brpLong
     *            De BrpLong, mag null zijn.
     * @return Een Long object, of null als de BrpLong null was.
     */
    public static Long unwrap(final BrpLong brpLong) {
        return (Long) AbstractBrpAttribuutMetOnderzoek.unwrapImpl(brpLong);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpAttribuutMetOnderzoek#getWaarde()
     */
    @Override
    @Element(name = "waarde", required = false)
    public Long getWaarde() {
        return unwrap(this);
    }

    /**
     * Geeft een kopie van het attribuut terug zonder onderzoek.
     * 
     * @return Geeft een kopie van het attribuut terug zonder onderzoek
     */
    public BrpLong verwijderOnderzoek() {
        if (getWaarde() == null) {
            return null;
        }
        return new BrpLong(getWaarde());
    }
}
