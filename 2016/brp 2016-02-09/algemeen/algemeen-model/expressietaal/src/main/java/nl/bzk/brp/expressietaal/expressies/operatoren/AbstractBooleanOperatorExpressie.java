/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.operatoren;

import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;

/**
 * Representeert binaire boolean operatoren.
 */
public abstract class AbstractBooleanOperatorExpressie extends AbstractBinaireOperatorExpressie {
    /**
     * Constructor.
     *
     * @param aOperandLinks  Linkeroperand van de operator.
     * @param aOperandRechts Rechteroperand van de operator.
     */
    AbstractBooleanOperatorExpressie(final Expressie aOperandLinks,
                                     final Expressie aOperandRechts)
    {
        super(aOperandLinks, aOperandRechts);
    }

    @Override
    public final ExpressieType getType(final Context context) {
        return ExpressieType.BOOLEAN;
    }
}
