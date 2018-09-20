/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert een BRP Reden Wijziging Verblijf code. Dit is geen enum maar een class omdat het hier een
 * dynamische stamtabel betreft.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpRedenWijzigingVerblijfCode extends AbstractBrpAttribuutMetOnderzoek {
    private static final long serialVersionUID = 1L;

    /**
     * Maakt een BrpRedenWijzigingVerblijfCode object.
     * 
     * @param waarde
     *            de waarde
     */
    public BrpRedenWijzigingVerblijfCode(final Character waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een BrpRedenWijzigingVerblijfCode met onderzoek.
     * 
     * @param waarde
     *            de unieke verwijzing naar een rij uit de stamtabel
     * @param onderzoek
     *            het onderzoek waar deze datum onder valt. Mag NULL zijn.
     */
    public BrpRedenWijzigingVerblijfCode(
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
}
