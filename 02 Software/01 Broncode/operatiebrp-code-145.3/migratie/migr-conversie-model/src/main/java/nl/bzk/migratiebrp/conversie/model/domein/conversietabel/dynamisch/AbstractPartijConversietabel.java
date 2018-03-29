/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import java.util.List;
import java.util.Map.Entry;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AbstractAttribuutConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

/**
 * Conversietabel voor LO3 naar BRP codering en vice versa.
 */
public abstract class AbstractPartijConversietabel extends AbstractAttribuutConversietabel<Lo3GemeenteCode, BrpPartijCode> {

    /**
     * Maakt een PartijConversietabel object.
     * @param conversieLijst de lijst met brp gemeenten
     */
    public AbstractPartijConversietabel(final List<Entry<Lo3GemeenteCode, BrpPartijCode>> conversieLijst) {
        super(conversieLijst);
    }

    @Override
    protected final Lo3GemeenteCode voegOnderzoekToeLo3(final Lo3GemeenteCode input, final Lo3Onderzoek onderzoek) {
        final Lo3GemeenteCode resultaat;
        if (Lo3Validatie.isElementGevuld(input)) {
            resultaat = new Lo3GemeenteCode(input.getWaarde(), onderzoek);
        } else {
            if (onderzoek != null) {
                resultaat = new Lo3GemeenteCode(null, onderzoek);
            } else {
                resultaat = null;
            }
        }

        return resultaat;
    }

    @Override
    protected final BrpPartijCode voegOnderzoekToeBrp(final BrpPartijCode input, final Lo3Onderzoek onderzoek) {
        final BrpPartijCode resultaat;
        if (BrpValidatie.isAttribuutGevuld(input)) {
            resultaat = new BrpPartijCode(input.getWaarde(), onderzoek);
        } else {
            if (onderzoek != null) {
                resultaat = new BrpPartijCode(null, onderzoek);
            } else {
                resultaat = null;
            }
        }

        return resultaat;
    }
}
