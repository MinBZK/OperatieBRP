/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

/**
 * Deze enum representeert de BRP geslachtsaanduiding.
 */
public final class BrpGeslachtsaanduidingCode extends AbstractBrpAttribuutMetOnderzoek {

    /**
     * Man.
     */
    public static final BrpGeslachtsaanduidingCode MAN = new BrpGeslachtsaanduidingCode("M");
    /**
     * Vrouw.
     */
    public static final BrpGeslachtsaanduidingCode VROUW = new BrpGeslachtsaanduidingCode("V");
    /**
     * Onbekend.
     */
    public static final BrpGeslachtsaanduidingCode ONBEKEND = new BrpGeslachtsaanduidingCode("O");

    private static final long serialVersionUID = 1L;

    /**
     * Maakt een BrpBijhoudingsaardCode.
     * @param waarde BRP code
     */
    public BrpGeslachtsaanduidingCode(final String waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een BrpGeslachtsaanduidingCode object met onderzoek.
     * @param waarde de waarde
     * @param onderzoek het onderzoek waar deze code onder valt. Mag NULL zijn.
     */
    public BrpGeslachtsaanduidingCode(
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
    public BrpGeslachtsaanduidingCode verwijderOnderzoek() {
        if (getWaarde() == null) {
            return null;
        }
        return new BrpGeslachtsaanduidingCode(getWaarde());
    }

    /**
     * Wrap de waarde en onderzoek naar een BrpGeslachtsaanduidingCode.
     * @param waarde de String waarde
     * @param onderzoek het Lo3 onderzoek
     * @return BrpGeslachtsaanduidingCode object met daarin waarde en onderzoek
     */
    public static BrpGeslachtsaanduidingCode wrap(final String waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        }
        return new BrpGeslachtsaanduidingCode(waarde, onderzoek);
    }

}
