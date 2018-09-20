/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert een BRP Character waarde.
 * 
 * Deze class is immutable en threadsafe.
 */
public final class BrpCharacter extends AbstractBrpAttribuutMetOnderzoek {
    private static final long serialVersionUID = 1L;

    /**
     * Maakt een BrpCharacter object .
     * 
     * @param waarde
     *            de character waarde
     */
    public BrpCharacter(final Character waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een BrpCharacter object met onderzoek.
     * 
     * @param waarde
     *            de character waarde
     * @param onderzoek
     *            het onderzoek waar deze waarde onder valt. Mag NULL zijn.
     */
    public BrpCharacter(
        @Element(name = "waarde", required = false) final Character waarde,
        @Element(name = "onderzoek", required = false) final Lo3Onderzoek onderzoek)
    {
        super(waarde, onderzoek);
    }

    /**
     * Wrap de waarde en onderzoek naar een BrpCharacter.
     * 
     * @param waarde
     *            de character waarde
     * @param onderzoek
     *            het Lo3 onderzoek
     * @return BrpCharacter object met daarin waarde en onderzoek
     */
    public static BrpCharacter wrap(final Character waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        }
        return new BrpCharacter(waarde, onderzoek);
    }

    /**
     * Unwrap een BrpCharacter object om de Character waarde terug te krijgen.
     * 
     * @param attribuut
     *            De BrpCharacter, mag null zijn.
     * @return Een Character object, of null als de BrpCharacter null was.
     */
    public static Character unwrap(final BrpCharacter attribuut) {
        return (Character) AbstractBrpAttribuutMetOnderzoek.unwrapImpl(attribuut);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpAttribuutMetOnderzoek#getWaarde()
     */
    @Override
    @Element(name = "waarde", required = false)
    public Character getWaarde() {
        return unwrap(this);
    }

    /**
     * Geeft een kopie van het attribuut terug zonder onderzoek.
     * 
     * @return Geeft een kopie van het attribuut terug zonder onderzoek
     */
    public BrpCharacter verwijderOnderzoek() {
        if (getWaarde() == null) {
            return null;
        }
        return new BrpCharacter(getWaarde());

    }
}
