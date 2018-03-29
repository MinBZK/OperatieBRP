/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

/**
 * Deze class representeert een BRP Boolean waarde.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpBoolean extends AbstractBrpAttribuutMetOnderzoek {
    private static final long serialVersionUID = 1L;

    /**
     * Maakt een BrpBoolean object .
     * @param waarde de boolean waarde
     */
    public BrpBoolean(final Boolean waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een BrpBoolean object met onderzoek.
     * @param waarde de boolean waarde
     * @param onderzoek het onderzoek waar deze waarde onder valt. Mag NULL zijn.
     */
    public BrpBoolean(
            @Element(name = "waarde", required = false) final Boolean waarde,
            @Element(name = "onderzoek", required = false) final Lo3Onderzoek onderzoek) {
        super(waarde, onderzoek);
    }

    /**
     * Wrap de waarde en onderzoek naar een BrpBoolean.
     * @param waarde de boolean waarde
     * @param onderzoek het Lo3 onderzoek
     * @return BrpBoolean object met daarin waarde en onderzoek
     */
    public static BrpBoolean wrap(final Boolean waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        }
        return new BrpBoolean(waarde, onderzoek);
    }

    /**
     * Unwrap een BrpBoolean object om de Boolean waarde terug te krijgen.
     * @param brpBoolean De BrpBoolean, mag null zijn.
     * @return Een String object, of null als de BrpString null was.
     */
    public static Boolean unwrap(final BrpBoolean brpBoolean) {
        return (Boolean) AbstractBrpAttribuutMetOnderzoek.unwrapImpl(brpBoolean);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpAttribuutMetOnderzoek#getWaarde()
     */
    @Override
    @Element(name = "waarde", required = false)
    public Boolean getWaarde() {
        return unwrap(this);
    }

    /**
     * Controleer of de brpBoolean niet-null is en bovendien de waarde 'true' heeft.
     * @param brpBoolean De brpBoolean, mag null zijn.
     * @return of de brpBoolean niet-null is en bovendien de waarde 'true' heeft.
     */
    public static boolean isTrue(final BrpBoolean brpBoolean) {
        if (brpBoolean == null || brpBoolean.getWaarde() == null) {
            return false;
        }
        return brpBoolean.getWaarde();
    }

    /**
     * Geeft een kopie van het attribuut terug zonder onderzoek.
     * @return Geeft een kopie van het attribuut terug zonder onderzoek
     */
    public BrpBoolean verwijderOnderzoek() {
        if (getWaarde() == null) {
            return null;
        }
        return new BrpBoolean(getWaarde());
    }
}
