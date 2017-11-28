/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.berekening;

import java.time.ZonedDateTime;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.OperatorType;
import nl.bzk.brp.domain.expressie.DatumLiteral;
import nl.bzk.brp.domain.expressie.Datumdeel;
import nl.bzk.brp.domain.expressie.NullLiteral;
import nl.bzk.brp.domain.expressie.PeriodeLiteral;
import org.springframework.stereotype.Component;

/**
 * {@link DatumLiteral} min {@link PeriodeLiteral} berekening.
 */
@Component
@BerekeningConfig(operator = OperatorType.MIN, typeLinks = ExpressieType.DATUM, typeRechts = ExpressieType.PERIODE)
final class DatumMinPeriode implements Berekening<DatumLiteral, PeriodeLiteral> {

    @Override
    public Expressie apply(final DatumLiteral datumLiteral, final PeriodeLiteral periodeLiteral) {
        Expressie resultaat = NullLiteral.INSTANCE;
        if (datumLiteral.isVolledigBekend()) {
            final ZonedDateTime dt = datumLiteral.alsDateTime()
                    .minusYears(periodeLiteral.getJaar().isBekend() ? periodeLiteral.getJaar().getWaarde() : 0)
                    .minusMonths(periodeLiteral.getMaand().isBekend() ? periodeLiteral.getMaand().getWaarde() : 0)
                    .minusDays(periodeLiteral.getDag().isBekend() ? periodeLiteral.getDag().getWaarde() : 0);
            final Datumdeel jaarDeel = periodeLiteral.getJaar().isVraagteken()
                    ? Datumdeel.ONBEKEND_DATUMDEEL
                    : Datumdeel.valueOf(dt.getYear());
            final Datumdeel maandDeel = periodeLiteral.getMaand().isVraagteken()
                    ? Datumdeel.ONBEKEND_DATUMDEEL
                    : Datumdeel.valueOf(dt.getMonthValue());
            final Datumdeel dagDeel = periodeLiteral.getDag().isVraagteken()
                    ? Datumdeel.ONBEKEND_DATUMDEEL
                    : Datumdeel.valueOf(dt.getDayOfMonth());
            resultaat = new DatumLiteral(jaarDeel, maandDeel, dagDeel);
        }
        return resultaat;
    }
}
