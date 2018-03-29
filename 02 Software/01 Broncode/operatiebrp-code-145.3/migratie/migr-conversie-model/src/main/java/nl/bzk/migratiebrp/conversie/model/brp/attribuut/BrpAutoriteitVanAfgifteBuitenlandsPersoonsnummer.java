/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

/**
 * Deze class representeert een BRP autoriteit van afgifte buitenlands persoonsnummer.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer extends AbstractBrpAttribuutMetOnderzoek {
    private static final long serialVersionUID = 1;
    private static final int LENGTE_CODE = 4;

    /**
     * Maakt een {@link BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer}.
     * @param waarde waarde van dit element
     */
    public BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer(final String waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een {@link BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer} met onderzoek.
     * @param waarde waarde van dit element
     * @param onderzoek het onderzoek
     */
    public BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer(@Element(name = "waarde") final String waarde,
                                                            @Element(name = "onderzoek") final Lo3Onderzoek onderzoek) {
        super(waarde, LENGTE_CODE, onderzoek);
    }

    @Override
    @Element(name = "waarde")
    public String getWaarde() {
        return (String) unwrapImpl(this);
    }
}
