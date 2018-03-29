/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

/**
 * Abstract nationaliteit conversie tabel.
 */
public abstract class AbstractNationaliteitConversietabel implements Conversietabel<Lo3NationaliteitCode, BrpNationaliteitCode> {

    @Override
    public final BrpNationaliteitCode converteerNaarBrp(final Lo3NationaliteitCode input) {
        final BrpNationaliteitCode result;
        if (Lo3Validatie.isElementGevuld(input)) {
            result = new BrpNationaliteitCode(input.getWaarde(), input.getOnderzoek());
        } else {
            if (input != null && input.getOnderzoek() != null) {
                result = new BrpNationaliteitCode(null, input.getOnderzoek());
            } else {
                result = null;
            }
        }
        return result;
    }

    @Override
    public final Lo3NationaliteitCode converteerNaarLo3(final BrpNationaliteitCode input) {
        final Lo3NationaliteitCode result;
        if (BrpValidatie.isAttribuutGevuld(input)) {
            result = new Lo3NationaliteitCode(input.getWaarde(), input.getOnderzoek());
        } else {
            if (input != null && input.getOnderzoek() != null) {
                result = new Lo3NationaliteitCode(null, input.getOnderzoek());
            } else {
                result = null;
            }
        }
        return result;
    }
}
