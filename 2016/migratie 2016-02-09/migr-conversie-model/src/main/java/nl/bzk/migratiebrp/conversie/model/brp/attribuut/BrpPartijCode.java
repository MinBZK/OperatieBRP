/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert een BRP partij code;
 * 
 * Deze class is immutable en thread safe.
 * 
 */
public final class BrpPartijCode extends AbstractBrpAttribuutMetOnderzoek {

    /**
     * Migratievoorziening partijcode.
     */
    public static final Integer MIGRATIEVOORZIENING_CODE = 199902;
    /**
     * Onbekende partijcode.
     */
    public static final Integer ONBEKEND_CODE = 0;
    /**
     * RNI/Minister partijcode.
     */
    public static final Integer MINISTER_CODE = 199901;
    /**
     * Gebruikt voor vertaling van RNI (paragraaf 10.4 conversie partij).
     */
    public static final BrpPartijCode MINISTER = new BrpPartijCode(MINISTER_CODE);

    /**
     * In het geval dat de partij voor de migratie voorziening niet bekend is wordt deze waarde gebruikt (DEF0044).
     */
    public static final BrpPartijCode MIGRATIEVOORZIENING = new BrpPartijCode(MIGRATIEVOORZIENING_CODE);

    /**
     * In geval van een ontbrekende partij en een verplicht veld wordt deze waarde gebruikt.
     */
    public static final BrpPartijCode ONBEKEND = new BrpPartijCode(ONBEKEND_CODE);

    private static final long serialVersionUID = 1L;

    /**
     * Maakt een BrpPartijCode object.
     * 
     * @param waarde
     *            de waarde die binnen BRP een partij uniek identificeert, mag niet null zijn.
     */
    public BrpPartijCode(final Integer waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een BrpPartijCode object.
     * 
     * @param waarde
     *            de waarde die binnen BRP een partij uniek identificeert, mag niet null zijn.
     * @param onderzoek
     *            het onderzoek waar deze datum onder valt. Mag NULL zijn.
     */
    public BrpPartijCode(
        @Element(name = "waarde", required = false) final Integer waarde,
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
    public Integer getWaarde() {
        return (Integer) unwrapImpl(this);
    }

    /**
     * Geeft een kopie van het attribuut terug zonder onderzoek.
     * 
     * @return Geeft een kopie van het attribuut terug zonder onderzoek
     */
    public BrpPartijCode verwijderOnderzoek() {
        if (getWaarde() == null) {
            return null;
        }
        return new BrpPartijCode(getWaarde());
    }

    /**
     * Wrap de waarde en onderzoek naar een BrpPartijCode.
     * 
     * @param waarde
     *            de String waarde
     * @param onderzoek
     *            het Lo3 onderzoek
     * @return BrpPartijCode object met daarin waarde en onderzoek
     */
    public static BrpPartijCode wrap(final Integer waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        }
        return new BrpPartijCode(waarde, onderzoek);
    }
}
