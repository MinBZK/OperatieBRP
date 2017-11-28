/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.berekening;

import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.OperatorType;
import nl.bzk.brp.domain.expressie.Datumdeel;
import nl.bzk.brp.domain.expressie.PeriodeLiteral;
import org.springframework.stereotype.Component;

/**
 * {@link PeriodeLiteral} plus {@link PeriodeLiteral} berekening.
 */
@Component
@BerekeningConfig(operator = OperatorType.PLUS, typeLinks = ExpressieType.PERIODE, typeRechts = ExpressieType.PERIODE)
final class PeriodePlusPeriode implements Berekening<PeriodeLiteral, PeriodeLiteral> {

    @Override
    public Expressie apply(final PeriodeLiteral periodeLinks, final PeriodeLiteral periodeRechts) {
        return new PeriodeLiteral(
                Datumdeel.valueOf(periodeLinks.getJaar().getWaarde() + periodeRechts.getJaar().getWaarde()),
                Datumdeel.valueOf(periodeLinks.getMaand().getWaarde() + periodeRechts.getMaand().getWaarde()),
                Datumdeel.valueOf(periodeLinks.getDag().getWaarde() + periodeRechts.getDag().getWaarde()));
    }
}
