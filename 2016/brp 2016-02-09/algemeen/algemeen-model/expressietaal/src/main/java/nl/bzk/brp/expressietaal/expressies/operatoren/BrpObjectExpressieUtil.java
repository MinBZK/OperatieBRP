/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.operatoren;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.ExpressieUtil;
import nl.bzk.brp.expressietaal.expressies.literals.BooleanLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.BrpObjectExpressie;
import nl.bzk.brp.expressietaal.symbols.DefaultSolver;
import nl.bzk.brp.expressietaal.symbols.ExpressieAttribuut;

/**
 * Util klasse die BrpObjectExpressies kan vergelijken.
 * Deze klasse staat los van de BrpObjectExpressie zelf om package cycles te voorkomen.
 */
public final class BrpObjectExpressieUtil {

    private BrpObjectExpressieUtil() {
        // Util klasse, niet instantieren
    }

    /**
     * Vergelijkt twee BRP-objecten en geeft het resultaat als Expressie terug. Als de objecten vergeleken kunnen worden en gelijk zijn, is het resultaat
     * WAAR; als ze ongelijk zijn, is het resultaat ONWAAR. In andere gevallen zal een foutexpressie teruggegeven worden.
     *
     * @param object1 Eerste object om te vergelijken.
     * @param object2 Tweede object om te vergelijken.
     * @return Resultaat van de vergelijking.
     */
    public static Expressie objectenGelijk(final BrpObjectExpressie object1, final BrpObjectExpressie object2) {

        Expressie resultaat = null;

        if (object1.getType(null) == object2.getType(null)) {
            final List<ExpressieAttribuut> attributen = verzamelAttributen(object1.getType(null));

            for (final ExpressieAttribuut attribuut : attributen) {
                final Expressie waarde1 = DefaultSolver.getInstance().bepaalWaarde(object1.getBrpObject(), attribuut);
                if (waarde1.isFout()) {
                    resultaat = waarde1;
                    break;
                }

                final Expressie waarde2 = DefaultSolver.getInstance().bepaalWaarde(object2.getBrpObject(), attribuut);
                if (waarde2.isFout()) {
                    resultaat = waarde2;
                    break;
                }

                if (ExpressieUtil.waardenVerschillend(waarde1, waarde2, null).alsBoolean()) {
                    resultaat = BooleanLiteralExpressie.ONWAAR;
                    break;
                }
            }

            if (resultaat == null) {
                resultaat = BooleanLiteralExpressie.WAAR;
            }
        } else {
            resultaat = BooleanLiteralExpressie.ONWAAR;
        }
        return resultaat;
    }

    /**
     * Verzamelt alle attributen van een expressietype. Alleen BRP-objecttypes (zoals PERSOON en ADRES) hebben attributen.
     *
     * @param parentType Type waarvan attributen verzameld moeten worden.
     * @return Attributen van een expressietype.
     */
    private static List<ExpressieAttribuut> verzamelAttributen(final ExpressieType parentType) {
        final List<ExpressieAttribuut> attributen = new ArrayList<>();
        for (final ExpressieAttribuut attribuut : ExpressieAttribuut.values()) {
            if (attribuut.getParentType() == parentType) {
                attributen.add(attribuut);
            }
        }
        return attributen;
    }
}
