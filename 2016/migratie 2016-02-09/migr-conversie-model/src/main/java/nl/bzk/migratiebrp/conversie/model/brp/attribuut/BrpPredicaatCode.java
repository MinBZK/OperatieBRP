/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert een BRP Predicaat code als onderdeel van het BRP geslachtsnaam component.
 * 
 * Deze class is immutable en threadsafe.
 * 
 * (N.b. spelling aan BRP kant is 'Predicaat'. Aan de Lo3 kant is het nog 'Predikaat'. Dit volgt de spellingsregels van
 * die tijd.)
 */
public final class BrpPredicaatCode extends AbstractBrpAttribuutMetOnderzoek {
    private static final long serialVersionUID = 1L;

    private BrpGeslachtsaanduidingCode geslachtsaanduiding;

    /**
     * Maakt een BrpPredicaatCode object.
     * 
     * @param waarde
     *            de waarde
     */
    public BrpPredicaatCode(final String waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een BrpPredicaatCode object met onderzoek.
     * 
     * @param waarde
     *            de waarde
     * @param onderzoek
     *            het onderzoek waar deze waarde onder valt. Mag NULL zijn.
     */
    public BrpPredicaatCode(
        @Element(name = "waarde", required = false) final String waarde,
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
    public String getWaarde() {
        return (String) unwrapImpl(this);
    }

    /**
     * Geef de waarde van geslachtsaanduiding.
     *
     * @return geslachtsaanduiding
     */
    public BrpGeslachtsaanduidingCode getGeslachtsaanduiding() {
        return geslachtsaanduiding;
    }

    /**
     * Zet de waarde van geslachtsaanduiding.
     *
     * @param brpGeslachtsaanduidingCode
     *            geslachtsaanduiding
     */
    public void setGeslachtsaanduiding(final BrpGeslachtsaanduidingCode brpGeslachtsaanduidingCode) {
        geslachtsaanduiding = brpGeslachtsaanduidingCode;
    }

    /**
     * Geeft een kopie van het attribuut terug zonder onderzoek.
     * 
     * @return Geeft een kopie van het attribuut terug zonder onderzoek
     */
    public BrpPredicaatCode verwijderOnderzoek() {
        if (getWaarde() == null) {
            return null;
        }
        return new BrpPredicaatCode(getWaarde());
    }

    /**
     * Wrap de waarde en onderzoek naar een BrpPredicaatCode.
     * 
     * @param waarde
     *            de String waarde
     * @param onderzoek
     *            het Lo3 onderzoek
     * @return BrpPredicaatCode object met daarin waarde en onderzoek
     */
    public static BrpPredicaatCode wrap(final String waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        }
        return new BrpPredicaatCode(waarde, onderzoek);
    }
}
