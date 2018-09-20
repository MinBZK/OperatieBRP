/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import java.util.List;
import java.util.Map.Entry;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AbstractAttribuutConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;

/**
 * Deze conversietabel mapt een Lo3AanduidingVerblijfstitelCode op de corresponderen BrpVerblijfsrechtCode en vice
 * versa.
 * 
 */
public abstract class AbstractRedenVerliesNederlanderschapConversietabel extends
        AbstractAttribuutConversietabel<Lo3RedenNederlandschapCode, BrpRedenVerliesNederlandschapCode>
{

    /**
     * Maakt een VerblijfsrechtConversietabel object.
     * 
     * @param conversieLijst
     *            de lijst met alle verblijfstitel conversies
     */
    public AbstractRedenVerliesNederlanderschapConversietabel(
        final List<Entry<Lo3RedenNederlandschapCode, BrpRedenVerliesNederlandschapCode>> conversieLijst)
    {
        super(conversieLijst);
    }

    @Override
    protected final Lo3RedenNederlandschapCode voegOnderzoekToeLo3(final Lo3RedenNederlandschapCode input, final Lo3Onderzoek onderzoek) {
        final Lo3RedenNederlandschapCode resultaat;
        if (nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie.isElementGevuld(input)) {
            resultaat = new Lo3RedenNederlandschapCode(input.getWaarde(), onderzoek);
        } else {
            if (onderzoek != null) {
                resultaat = new Lo3RedenNederlandschapCode(null, onderzoek);
            } else {
                resultaat = null;
            }
        }

        return resultaat;
    }

    @Override
    protected final BrpRedenVerliesNederlandschapCode voegOnderzoekToeBrp(final BrpRedenVerliesNederlandschapCode input, final Lo3Onderzoek onderzoek) {
        final BrpRedenVerliesNederlandschapCode resultaat;
        if (Validatie.isAttribuutGevuld(input)) {
            resultaat = new BrpRedenVerliesNederlandschapCode(input.getWaarde(), onderzoek);
        } else {
            if (onderzoek != null) {
                resultaat = new BrpRedenVerliesNederlandschapCode(null, onderzoek);
            } else {
                resultaat = null;
            }
        }

        return resultaat;
    }
}
