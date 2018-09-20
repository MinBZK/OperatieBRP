/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import java.text.DecimalFormat;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;

/**
 * Abstract land conversie tabel.
 */
public abstract class AbstractLandConversietabel implements Conversietabel<Lo3LandCode, BrpLandOfGebiedCode> {

    private static final String LAND_CODE_FORMAT = "0000";

    @Override
    public final BrpLandOfGebiedCode converteerNaarBrp(final Lo3LandCode input) {
        final BrpLandOfGebiedCode resultaat;

        if (nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie.isElementGevuld(input)) {
            resultaat = new BrpLandOfGebiedCode(Short.parseShort(input.getWaarde()), input.getOnderzoek());
        } else {
            if (input != null && input.getOnderzoek() != null) {
                resultaat = new BrpLandOfGebiedCode(null, input.getOnderzoek());
            } else {
                resultaat = null;
            }
        }

        return resultaat;
    }

    @Override
    public final Lo3LandCode converteerNaarLo3(final BrpLandOfGebiedCode input) {
        final Lo3LandCode resultaat;

        if (Validatie.isAttribuutGevuld(input)) {
            resultaat = new Lo3LandCode(new DecimalFormat(LAND_CODE_FORMAT).format(input.getWaarde()), input.getOnderzoek());
        } else {
            if (input != null && input.getOnderzoek() != null) {
                resultaat = new Lo3LandCode(null, input.getOnderzoek());
            } else {
                resultaat = null;
            }
        }

        return resultaat;
    }
}
