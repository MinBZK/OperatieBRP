/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.simpleframework.xml.Element;

/**
 * De class representeert een BRP Reden verkrijging Nederlandschap code.
 * 
 * Deze code verwijst naar de stamtabel Reden Verkrijging NL Nationaliteit.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpRedenVerkrijgingNederlandschapCode extends AbstractBrpAttribuutMetOnderzoek {
    private static final long serialVersionUID = 1L;

    /**
     * Maakt een BrpRedenVerkrijgingNederlandschapCode object.
     * 
     * @param waarde
     *            de waarde
     */
    public BrpRedenVerkrijgingNederlandschapCode(final Short waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een BrpRedenVerkrijgingNederlandschapCode object met onderzoek.
     * 
     * @param waarde
     *            de waarde
     * @param onderzoek
     *            het onderzoek waar deze datum onder valt. Mag NULL zijn.
     */
    public BrpRedenVerkrijgingNederlandschapCode(@Element(name = "waarde", required = false) final Short waarde, @Element(name = "onderzoek",
            required = false) final Lo3Onderzoek onderzoek)
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
    public Short getWaarde() {
        return (Short) unwrapImpl(this);
    }
}
