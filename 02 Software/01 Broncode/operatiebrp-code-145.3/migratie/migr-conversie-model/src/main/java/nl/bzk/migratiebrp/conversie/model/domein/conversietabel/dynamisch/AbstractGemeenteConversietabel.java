/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

/**
 * Abstract gemeente conversie tabel.
 */
public abstract class AbstractGemeenteConversietabel implements Conversietabel<Lo3GemeenteCode, BrpGemeenteCode> {

    @Override
    public final BrpGemeenteCode converteerNaarBrp(final Lo3GemeenteCode input) {
        final BrpGemeenteCode resultaat;

        if (Lo3Validatie.isElementGevuld(input)) {
            resultaat = new BrpGemeenteCode(input.getWaarde(), input.getOnderzoek());
        } else {
            if (input != null && input.getOnderzoek() != null) {
                resultaat = new BrpGemeenteCode(null, input.getOnderzoek());
            } else {
                resultaat = null;
            }
        }

        return resultaat;
    }

    @Override
    public final Lo3GemeenteCode converteerNaarLo3(final BrpGemeenteCode input) {
        final Lo3GemeenteCode resultaat;

        if (BrpValidatie.isAttribuutGevuld(input)) {
            resultaat = new Lo3GemeenteCode(input.getWaarde(), input.getOnderzoek());
        } else {
            if (input != null && input.getOnderzoek() != null) {
                resultaat = new Lo3GemeenteCode(null, input.getOnderzoek());
            } else {
                resultaat = null;
            }
        }

        return resultaat;
    }
}
