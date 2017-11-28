/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import java.time.ZonedDateTime;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.DatumLiteral;
import nl.bzk.brp.domain.expressie.GetalLiteral;
import nl.bzk.brp.domain.expressie.signatuur.SimpeleSignatuur;
import org.springframework.stereotype.Component;

/**
 * Representeert de functie DATUM(J,M,D). De functie maakt een datum van drie individuele getallen (voor respectievelijk jaar, maand en dag).
 * <p>
 * De functie DATUM(jaar, maand, dag), waarbij jaar, maand en dag getallen zijn, maakt een correcte datum van gegeven jaartal, maand en dag. Als maand of
 * dag buiten de grenzen vallen (bijvoorbeeld als maand 13 is), dan wordt de datum zodanig aangepast dat een correcte datum overblijft.
 * <p>
 * Hieronder is in pseudocode gedefinieerd wat de functie DATUM(jaar, maand, dag) oplevert:
 * <ol>
 * <li>Zolang maand < 1: verlaag jaar met 1, verhoog maand met 12;</li>
 * <li>Zolang maand > 12: verhoog jaar met 1, verlaag maand met 12;</li>
 * <li>Zolang dag < 1: verlaag
 * maand met 1, verhoog dag met het aantal dagen in maand in jaar; als hierdoor maand kleiner dan 1 wordt, verlaag jaar met 1 en verhoog maand met 12;</li>
 * <li>Zolang dag > aantal dagen in maand in jaar: verlaag dag met het aantal dagen in maand in jaar, verhoog maand met 1; als hierdoor maand groter dan 12
 * wordt, verhoog jaar met 1 en verlaag maand met 12.</li>
 * </ol>
 * Voorbeelden:
 * <table>
 * <tr><th>Expressie</th><th>Resultaat</th></tr>
 * <tr><td>DATUM(1970, 2, 20) </td><td>1970/02/20</td></tr>
 * <tr><td>DATUM(1970, 4, 0) </td><td>1970/03/31</td></tr>
 * <tr><td>DATUM(1970, 13, 1)</td><td>1971/01/01</td></tr>
 * <tr><td>DATUM(1970, 12, 32)</td><td>1971/01/01</td></tr>
 * </table>
 * </p>
 */
@Component
@FunctieKeyword("DATUM")
final class DatumFunctie extends AbstractFunctie {

    /**
     * Constructor voor de functie.
     */
    DatumFunctie() {
        super(new SimpeleSignatuur(ExpressieType.GETAL, ExpressieType.GETAL, ExpressieType.GETAL));
    }

    @Override
    public Expressie evalueer(final List<Expressie> argumenten, final Context context) {
        final GetalLiteral jaarArgument = getArgument(argumenten, 0);
        final GetalLiteral maandArgument = getArgument(argumenten, 1);
        final GetalLiteral dagArgument = getArgument(argumenten, 2);
        final ZonedDateTime dateTime = ZonedDateTime.of((int)jaarArgument.getWaarde(), 1, 1, 0, 0, 0, 0, DatumUtil.NL_ZONE_ID)
                .plusMonths(maandArgument.getWaarde() - (long) 1).plusDays(dagArgument.getWaarde() - (long) 1);
        return new DatumLiteral(dateTime);
    }

    @Override
    public ExpressieType getType(final List<Expressie> argumenten, final Context context) {
        return ExpressieType.DATUM;
    }
}
