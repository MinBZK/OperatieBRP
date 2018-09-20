/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert een unieke verwijzing naar de BRP stamtabel 'Reden beëindiging relatie'. Dit is een
 * dynamische stamtabel en dus geen enum maar een class.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpRedenEindeRelatieCode extends AbstractBrpAttribuutMetOnderzoek {

    /**
     * Codering die aangeeft dat de reden van beeinding van een relatie een omzetting van het type van die relatie is.
     */
    public static final BrpRedenEindeRelatieCode OMZETTING = new BrpRedenEindeRelatieCode('Z');

    private static final long serialVersionUID = 1L;

    /**
     * Maakt een BrpRedenEindeRelatieCode.
     * 
     * @param waarde
     *            de unieke verwijzing naar een rij uit de stamtabel
     */
    public BrpRedenEindeRelatieCode(final Character waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een BrpRedenEindeRelatieCode met onderzoek.
     * 
     * @param waarde
     *            de unieke verwijzing naar een rij uit de stamtabel
     * @param onderzoek
     *            het onderzoek waar deze datum onder valt. Mag NULL zijn.
     */
    public BrpRedenEindeRelatieCode(
        @Element(name = "waarde", required = false) final Character waarde,
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
    public Character getWaarde() {
        return (Character) unwrapImpl(this);
    }

    /**
     * Geeft een kopie van het attribuut terug zonder onderzoek.
     * 
     * @return Geeft een kopie van het attribuut terug zonder onderzoek
     */
    public BrpRedenEindeRelatieCode verwijderOnderzoek() {
        if (getWaarde() == null) {
            return null;
        }
        return new BrpRedenEindeRelatieCode(getWaarde());
    }

    /**
     * Wrap de waarde en onderzoek naar een BrpRedenEindeRelatieCode.
     * 
     * @param waarde
     *            de Character waarde
     * @param onderzoek
     *            het Lo3 onderzoek
     * @return BrpRedenEindeRelatieCode object met daarin waarde en onderzoek
     */
    public static BrpRedenEindeRelatieCode wrap(final Character waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        }
        return new BrpRedenEindeRelatieCode(waarde, onderzoek);
    }
}
