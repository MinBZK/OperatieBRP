/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import nl.bzk.migratiebrp.conversie.regels.expressie.impl.BrpType;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Criterium;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.ElementWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Expressie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.KNVOperator;

/**
 * Vertaler voor inverse elementen binnen een vergelijking.
 */
public class GbaVoorwaardeVertalerInverse implements GbaVoorwaardeVertaler {

    private BrpType brpType;

    /**
     * Constructor.
     * @param brpType element
     */
    public GbaVoorwaardeVertalerInverse(final BrpType brpType) {
        this.brpType = brpType;
    }


    @Override
    public Expressie verwerk() throws GbaVoorwaardeOnvertaalbaarExceptie {
        return new ElementWaarde(new Criterium(brpType.getType(), new KNVOperator(), null));
    }
}
