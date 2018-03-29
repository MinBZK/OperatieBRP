/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

/**
 * Abstract implementatie van de autorisatie van afgifte buitenlands persoonsnummer conversietabel.
 */
public abstract class AbstractAutoriteitVanAfgifteBuitenlandsPersoonsnummerConversietabel
        implements Conversietabel<Lo3NationaliteitCode, BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer> {
    @Override
    public final BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer converteerNaarBrp(final Lo3NationaliteitCode input) {
        final BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer resultaat;
        if (Lo3Validatie.isElementGevuld(input)) {
            resultaat = new BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer(input.getWaarde(), input.getOnderzoek());
        } else {
            if (input != null && input.getOnderzoek() != null) {
                resultaat = new BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer(null, input.getOnderzoek());
            } else {
                resultaat = null;
            }
        }
        return resultaat;
    }

    @Override
    public final Lo3NationaliteitCode converteerNaarLo3(final BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer input) {
        final Lo3NationaliteitCode resultaat;
        if (BrpValidatie.isAttribuutGevuld(input)) {
            resultaat = new Lo3NationaliteitCode(input.getWaarde(), input.getOnderzoek());
        } else {
            if (input != null && input.getOnderzoek() != null) {
                resultaat = new Lo3NationaliteitCode(null, input.getOnderzoek());
            } else {
                resultaat = null;
            }
        }
        return resultaat;
    }
}
