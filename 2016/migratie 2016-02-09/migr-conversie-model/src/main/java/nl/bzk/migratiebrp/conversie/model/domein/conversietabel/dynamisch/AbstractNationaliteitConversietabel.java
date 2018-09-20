/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import java.text.DecimalFormat;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;

/**
 * Abstract nationaliteit conversie tabel.
 */
public abstract class AbstractNationaliteitConversietabel implements Conversietabel<Lo3NationaliteitCode, BrpNationaliteitCode> {

    private static final String NATIONALITEIT_CODE_FORMAT = "0000";

    @Override
    public final BrpNationaliteitCode converteerNaarBrp(final Lo3NationaliteitCode input) {
        final BrpNationaliteitCode result;
        if (nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie.isElementGevuld(input)) {
            result = new BrpNationaliteitCode(Short.parseShort(input.getWaarde()), input.getOnderzoek());
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
        if (Validatie.isAttribuutGevuld(input)) {
            result = new Lo3NationaliteitCode(new DecimalFormat(NATIONALITEIT_CODE_FORMAT).format(input.getWaarde()), input.getOnderzoek());
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
