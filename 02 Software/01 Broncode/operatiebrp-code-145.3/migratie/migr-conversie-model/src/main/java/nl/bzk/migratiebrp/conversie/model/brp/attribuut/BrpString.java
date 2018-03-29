/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

/**
 * Deze class representeert een BRP String waarde.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpString extends AbstractBrpAttribuutMetOnderzoek implements Comparable<BrpString> {
    private static final long serialVersionUID = 1L;

    /**
     * Maakt een BrpString object .
     * @param waarde de string waarde
     */
    public BrpString(final String waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een BrpString object met onderzoek.
     * @param waarde de string waarde
     * @param onderzoek het onderzoek waar deze waarde onder valt. Mag NULL zijn.
     */
    public BrpString(
            @Element(name = "waarde", required = false) final String waarde,
            @Element(name = "onderzoek", required = false) final Lo3Onderzoek onderzoek) {
        super(waarde, onderzoek);
    }

    /**
     * Wrap de waarde en onderzoek naar een BrpString.
     * @param waarde de String waarde
     * @param onderzoek het Lo3 onderzoek
     * @return BrpString object met daarin waarde en onderzoek
     */
    public static BrpString wrap(final String waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        }
        return new BrpString(waarde, onderzoek);
    }

    /**
     * Unwrap een BrpString object om de String waarde terug te krijgen.
     * @param brpString De BrpString, mag null zijn.
     * @return Een String object, of null als de BrpString null was.
     */
    public static String unwrap(final BrpString brpString) {
        return (String) AbstractBrpAttribuutMetOnderzoek.unwrapImpl(brpString);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpAttribuutMetOnderzoek#getWaarde()
     */
    @Override
    @Element(name = "waarde", required = false)
    public String getWaarde() {
        return unwrap(this);
    }

    @Override
    public int compareTo(final BrpString andereString) {
        return getWaarde().compareTo(andereString.getWaarde());
    }

    /**
     * Geeft een kopie van het attribuut terug zonder onderzoek.
     * @return Geeft een kopie van het attribuut terug zonder onderzoek
     */
    public BrpString verwijderOnderzoek() {
        if (getWaarde() == null) {
            return null;
        }
        return new BrpString(getWaarde());
    }
}
