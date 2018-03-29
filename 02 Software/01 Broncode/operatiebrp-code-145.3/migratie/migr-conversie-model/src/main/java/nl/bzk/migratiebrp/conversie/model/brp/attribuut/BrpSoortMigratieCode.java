/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

/**
 * Deze class representeert een Soort migratie. Deze class is immutable en threadsafe.
 */
public final class BrpSoortMigratieCode extends AbstractBrpAttribuutMetOnderzoek {
    /**
     * Immigratie.
     */
    public static final BrpSoortMigratieCode IMMIGRATIE = new BrpSoortMigratieCode("I");
    /**
     * Emigratie.
     */
    public static final BrpSoortMigratieCode EMIGRATIE = new BrpSoortMigratieCode("E");

    private static final long serialVersionUID = 1L;

    /**
     * Maakt een BrpSoortMigratieCode.
     * @param waarde BRP waarde
     */
    public BrpSoortMigratieCode(final String waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een BrpSoortMigratieCode met onderzoek.
     * @param waarde BRP waarde
     * @param onderzoek het onderzoek waar deze waarde onder valt. Mag NULL zijn.
     */
    public BrpSoortMigratieCode(
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
