/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import java.util.List;
import java.util.Map.Entry;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AbstractAttribuutConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;

/**
 * Deze conversietabel map een Lo3RedenOpschortingBijhoudingCode op de corresponderen BrpRedenOpschortingBijhoudingCode
 * vice versa.
 */
public abstract class AbstractRedenOpschortingConversietabel extends
        AbstractAttribuutConversietabel<Lo3RedenOpschortingBijhoudingCode, BrpNadereBijhoudingsaardCode>
{

    /**
     * Maakt een RedenOpschortingConversietabel object.
     * 
     * @param conversieLijst
     *            de lijst met alle reden opschorting conversies
     */
    public AbstractRedenOpschortingConversietabel(final List<Entry<Lo3RedenOpschortingBijhoudingCode, BrpNadereBijhoudingsaardCode>> conversieLijst) {
        super(conversieLijst);
    }

    @Override
    protected final Lo3RedenOpschortingBijhoudingCode voegOnderzoekToeLo3(final Lo3RedenOpschortingBijhoudingCode input, final Lo3Onderzoek onderzoek) {
        final Lo3RedenOpschortingBijhoudingCode resultaat;
        if (nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie.isElementGevuld(input)) {
            resultaat = new Lo3RedenOpschortingBijhoudingCode(input.getWaarde(), onderzoek);
        } else {
            if (onderzoek != null) {
                resultaat = new Lo3RedenOpschortingBijhoudingCode(null, onderzoek);
            } else {
                resultaat = null;
            }
        }

        return resultaat;
    }

    @Override
    protected final BrpNadereBijhoudingsaardCode voegOnderzoekToeBrp(final BrpNadereBijhoudingsaardCode input, final Lo3Onderzoek onderzoek) {
        final BrpNadereBijhoudingsaardCode resultaat;
        if (Validatie.isAttribuutGevuld(input)) {
            resultaat = new BrpNadereBijhoudingsaardCode(input.getWaarde(), onderzoek);
        } else {
            if (onderzoek != null) {
                resultaat = new BrpNadereBijhoudingsaardCode(null, onderzoek);
            } else {
                resultaat = null;
            }
        }

        return resultaat;
    }
}
