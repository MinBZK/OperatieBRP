/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert een BRP gemeente code;
 * 
 * Deze class is immutable en thread safe.
 * 
 */
public final class BrpGemeenteCode extends AbstractBrpAttribuutMetOnderzoek {
    private static final long serialVersionUID = 1L;

    /**
     * Maakt een BrpGemeenteCode object.
     * 
     * @param waarde
     *            de waarde die binnen BRP een gemeente uniek identficeert
     */
    public BrpGemeenteCode(final Short waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een BrpGemeenteCode object.
     * 
     * @param waarde
     *            de waarde die binnen BRP een gemeente uniek identficeert
     * @param onderzoek
     *            het onderzoek waar deze waarde onder valt. Mag NULL zijn.
     */
    public BrpGemeenteCode(
        @Element(name = "waarde", required = false) final Short waarde,
        @Element(name = "onderzoek", required = false) final Lo3Onderzoek onderzoek)
    {
        super(waarde, onderzoek);
    }

    /**
     * Geef de waarde van formatted string code.
     *
     * @return formatted code as string.
     */
    public String getFormattedStringCode() {
        final Short code = getWaarde();
        String result = null;
        if (code != null) {
            result = String.format("%04d", getWaarde());
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpAttribuutMetOnderzoek#getWaarde()
     */
    @Override
    @Element(name = "waarde", required = false)
    public Short getWaarde() {
        return (Short) unwrapImpl(this);
    }

    /**
     * Geeft een kopie van het attribuut terug zonder onderzoek.
     * 
     * @return Geeft een kopie van het attribuut terug zonder onderzoek
     */
    public BrpGemeenteCode verwijderOnderzoek() {
        if (getWaarde() == null) {
            return null;
        }
        return new BrpGemeenteCode(getWaarde());
    }

    /**
     * Wrap de waarde en onderzoek naar een BrpGemeenteCode.
     * 
     * @param waarde
     *            de Short waarde
     * @param onderzoek
     *            het Lo3 onderzoek
     * @return BrpGemeenteCode object met daarin waarde en onderzoek
     */
    public static BrpGemeenteCode wrap(final Short waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        }
        return new BrpGemeenteCode(waarde, onderzoek);
    }
}
