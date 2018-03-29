/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

/**
 * Deze class representeert een BRP soort document code. Dit is geen enum maar een class omdat het hier een dynamische
 * stamtabel betreft.
 *
 * Deze class is immutable en thread safe.
 */
public final class BrpSoortDocumentCode extends AbstractBrpAttribuutMetOnderzoek {

    /**
     * Documentsoort Overig. Wordt gebruikt als DEF042 van toepassing is
     */
    public static final BrpSoortDocumentCode HISTORIE_CONVERSIE = new BrpSoortDocumentCode("Historie conversie");

    private static final long serialVersionUID = 1L;

    /**
     * Maakt een BrpSoortDocumentCode object.
     * @param waarde de waarde die binnen BRP een soort document uniek identficeert
     */
    public BrpSoortDocumentCode(final String waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een BrpSoortDocumentCode object met onderzoek.
     * @param waarde de waarde die binnen BRP een soort document uniek identficeert
     * @param onderzoek het onderzoek waar deze code onder valt. Mag NULL zijn.
     */
    public BrpSoortDocumentCode(
            @Element(name = "waarde", required = false) final String waarde,
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
    public String getWaarde() {
        return (String) unwrapImpl(this);
    }

    /**
     * Geeft een kopie van het attribuut terug zonder onderzoek.
     * @return Geeft een kopie van het attribuut terug zonder onderzoek
     */
    public BrpSoortDocumentCode verwijderOnderzoek() {
        if (getWaarde() == null) {
            return null;
        }
        return new BrpSoortDocumentCode(getWaarde());
    }
}
