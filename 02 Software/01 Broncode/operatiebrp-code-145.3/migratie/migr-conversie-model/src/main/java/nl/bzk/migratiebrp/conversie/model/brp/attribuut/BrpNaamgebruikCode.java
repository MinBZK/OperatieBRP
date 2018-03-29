/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

/**
 * Deze enum representeert een unieke verwijzing naar de BRP stamtabel 'Naamgebruik'.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpNaamgebruikCode extends AbstractBrpAttribuutMetOnderzoek {

    /**
     * Eigen geslachtsnaam.
     */
    public static final BrpNaamgebruikCode E = new BrpNaamgebruikCode("E");
    /**
     * Geslachtsnaam echtgenoot/geregistreerd partner.
     */
    public static final BrpNaamgebruikCode P = new BrpNaamgebruikCode("P");
    /**
     * Geslachtsnaam echtgenoot/geregistreerd partner voor eigen geslachtsnaam.
     */
    public static final BrpNaamgebruikCode V = new BrpNaamgebruikCode("V");
    /**
     * Geslachtsnaam echtgenoot/geregistreerd partner na eigen geslachtsnaam.
     */
    public static final BrpNaamgebruikCode N = new BrpNaamgebruikCode("N");

    private static final long serialVersionUID = 1L;

    /**
     * Maakt een BrpNaamgebruikCode.
     * @param waarde BRP code
     */
    public BrpNaamgebruikCode(final String waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een BrpNaamgebruikCode object met onderzoek.
     * @param waarde de waarde
     * @param onderzoek het onderzoek waar deze waarde onder valt. Mag NULL zijn.
     */
    public BrpNaamgebruikCode(
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
}
