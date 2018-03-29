/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

/**
 * Deze class representeert een BRP Aangever code.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpAangeverCode extends AbstractBrpAttribuutMetOnderzoek {
    private static final long serialVersionUID = 1L;

    /**
     * Maakt een BrpAangeverCode object.
     * @param waarde de waarde
     */
    public BrpAangeverCode(final Character waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een BrpAangeverCode object met onderzoek.
     * @param waarde de waarde
     * @param onderzoek het onderzoek waar deze waarde onder valt. Mag NULL zijn.
     */
    public BrpAangeverCode(
            @Element(name = "waarde", required = false) final Character waarde,
            @Element(name = "onderzoek", required = false) final Lo3Onderzoek onderzoek) {
        super(waarde, onderzoek);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpAttribuutMetOnderzoek#getWaarde()
     */
    @Override
    @Element(name = "waarde", required = false)
    public Character getWaarde() {
        return (Character) unwrapImpl(this);
    }

    /**
     * Geeft een kopie van het attribuut terug zonder onderzoek.
     * @return Geeft een kopie van het attribuut terug zonder onderzoek
     */
    public BrpAangeverCode verwijderOnderzoek() {
        if (getWaarde() == null) {
            return null;
        }
        return new BrpAangeverCode(getWaarde());
    }
}
