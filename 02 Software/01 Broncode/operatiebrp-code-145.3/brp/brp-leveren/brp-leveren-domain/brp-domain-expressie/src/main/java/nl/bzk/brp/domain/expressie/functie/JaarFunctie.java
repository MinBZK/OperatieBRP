/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.DatumLiteral;
import nl.bzk.brp.domain.expressie.GetalLiteral;
import nl.bzk.brp.domain.expressie.NullLiteral;
import org.springframework.stereotype.Component;

/**
 * Representeert de functie JAAR(D). De functie geeft het jaartal van datum D als resultaat.
 */
@Component
@FunctieKeyword("JAAR")
final class JaarFunctie extends AbstractDatumDeelFunctie {

    @Override
    Expressie geefDatumDeelExpressie(final DatumLiteral date) {
        return date.getJaar().isVraagteken()
                ? NullLiteral.INSTANCE
                : new GetalLiteral(date.getJaar().getWaarde());
    }
}
